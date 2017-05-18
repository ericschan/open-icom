/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER
 * 
 * Copyright (c) 2010, Oracle Corporation All Rights Reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License ("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can obtain
 * a copy of the License at http://openjdk.java.net/legal/gplv2+ce.html.
 * See the License for the specific language governing permission and
 * limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at openICOM/bootstrap/legal/LICENSE.txt.
 * Oracle designates this particular file as subject to the "Classpath" exception
 * as provided by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [ ] replaced by your own
 * identifying information:  "Portions Copyrighted [year]
 * [name of copyright owner].
 *
 * Contributor(s): Oracle Corporation
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package icom.jpql.csi.filters;

import icom.QLException;
import icom.jpql.csi.CsiQueryContext;
import icom.jpql.stack.SimpleExpression;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.controls.Control;
import oracle.csi.filters.DocumentListFilter;
import oracle.csi.filters.ListFilter;
import oracle.csi.filters.Predicate;

public class DocumentPredicateFactory extends ArtifactPredicateFactory {
	
	{
		attributeTypeMapper.put("Content".toLowerCase(), String.class);
		attributeTypeMapper.put("Size".toLowerCase(), Long.class);
		attributeTypeMapper.put("Path".toLowerCase(), String.class);
		attributeTypeMapper.put("MediaType".toLowerCase(), String.class);
		attributeTypeMapper.put("ContentLanguage".toLowerCase(), Locale.class);
	}
	
	{
		equalsPredicateMethodMapper.put("Path".toLowerCase(), "createPathPredicate");
		equalsPredicateMethodMapper.put("ContentLanguage".toLowerCase(), "createContentLanguagePredicate");
		equalsPredicateMethodMapper.put("MediaType".toLowerCase(), "createMediaTypePredicate");
	}
		
	{
		likePredicateMethodMapper.put("Content".toLowerCase(), "createContentPredicate");
		likePredicateMethodMapper.put("Path".toLowerCase(), "createPathPredicate");
	}
	
	{
		greaterThanPredicateMethodMapper.put("Size".toLowerCase(), "createSizePredicate");
	}
	
	{
		lessThanPredicateMethodMapper.put("Size".toLowerCase(), "createSizePredicate");
	}
	
	{
		sortCriteriaMethodMapper.put("CreatedBy".toLowerCase(), "createCreatedBySortCriteria");
		sortCriteriaMethodMapper.put("lastModifiedBy".toLowerCase(), "createModifiedBySortCriteria");
		sortCriteriaMethodMapper.put("creationDate".toLowerCase(), "createCreatedOnSortCriteria");
		sortCriteriaMethodMapper.put("MediaType".toLowerCase(), "createMediaTypeSortCriteria");
		sortCriteriaMethodMapper.put("Size".toLowerCase(), "createSizeSortCriteria");
	}
	
	static DocumentPredicateFactory singleton = new DocumentPredicateFactory();
	
	public static DocumentPredicateFactory getInstance() {
		return singleton;
	}
	
	public DocumentPredicateFactory() {
		controlClassName = "oracle.csi.controls.DocumentControl";
		factoryClassName = "oracle.csi.controls.DocumentFactory";
		listFilterCreateMethodName = "createDocumentListFilter";
		listMethodName = "listDocuments";
		listFilter = "oracle.csi.filters.DocumentListFilter";
	}
	
	public String resolveClassName() {
		return "Document";
	}
	
	public Predicate createGreaterThanPredicate(Object listFilter, SimpleExpression expression) {
		if (expression.primary.simpleAttributeName.equalsIgnoreCase("size")) {
			ArgumentCollection arguments = new ArgumentCollection();
			Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
			arguments.firstArgument = firstArgument;
			Method method = getGreaterThanPredicateMethod(listFilter, expression);
			Argument secondArgument = new Argument();
			if (expression.primary.isLeftNode) {
				secondArgument.value = new Boolean(false);
			} else {
				secondArgument.value = new Boolean(true);
			}
			arguments.secondArgument = secondArgument;
			Predicate predicate = createPredicate(listFilter, method, arguments);
			return predicate;
		} else {
			return super.createGreaterThanPredicate(listFilter, expression);
		}
	}
	
	public Predicate createLessThanPredicate(Object listFilter, SimpleExpression expression) {
		if (expression.primary.simpleAttributeName.equalsIgnoreCase("size")) {
			ArgumentCollection arguments = new ArgumentCollection();
			Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
			arguments.firstArgument = firstArgument;
			Method method = getLessThanPredicateMethod(listFilter, expression);
			Argument secondArgument = new Argument();
			if (expression.primary.isLeftNode) {
				secondArgument.value = new Boolean(true);
			} else {
				secondArgument.value = new Boolean(false);
			}
			arguments.secondArgument = secondArgument;
			Predicate predicate = createPredicate(listFilter, method, arguments);
			return predicate;
		} else {
			return super.createLessThanPredicate(listFilter, expression);
		}
	}
	
	public Class<? extends Object>[] getMethodSignatureForListMethod(ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle container = context.getContainer();
		if (container == null) {
			throw new QLException("Not Supported");
		}
		
		Class<?>[] args = new Class[2];
		Class<?>[] containerFaces = container.getClass().getInterfaces();
		for (Class<? extends Object> face : containerFaces) {
			if (EntityHandle.class.isAssignableFrom(face)) {
				args[0] = face;
				break;
			}
		}
		if (args[0] == null) {
			throw new QLException("Not Supported");
		}
		args[1] = DocumentListFilter.class;

		return args;
	}
	
	protected List<Object> invokeListMethod(Control control, Method listMethod, ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle container = context.getContainer();
		if (container == null) {
			throw new QLException("Not Supported");
		}
		Object[] args = new Object[2];
		args[0] = container;
		args[1] = listFilter;
		List<Object> list = ListFilterHelper.getInstance().invokeListMethod(control, listMethod, args);
		return list;
	}
	
	public boolean hasContainerAsArgumentOfListMethod() {
		return true;
	}

}
