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
import icom.jpql.stack.SimpleExpression;

import java.lang.reflect.Method;
import java.util.List;

import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.LabelType;
import oracle.csi.controls.Control;
import oracle.csi.filters.LabelListFilter;
import oracle.csi.filters.ListFilter;
import oracle.csi.filters.Predicate;

public class LabelPredicateFactory extends ArtifactPredicateFactory {
	
	{
		attributeTypeMapper.put("Entities".toLowerCase(), Entity.class);
		attributeTypeMapper.put("Type".toLowerCase(), LabelType.class);
	}
	
	{
		equalsPredicateMethodMapper.put("Type".toLowerCase(), "createLabelTypePredicate");
	}
	
	static LabelPredicateFactory singleton = new LabelPredicateFactory();
	
	public static LabelPredicateFactory getInstance() {
		return singleton;
	}
	
	public LabelPredicateFactory() {
		controlClassName = "oracle.csi.controls.LabelControl";
		factoryClassName = "oracle.csi.controls.LabelFactory";
		listFilterCreateMethodName = "createLabelListFilter";
		listMethodName = "listMyLabels";
		listFilter = "oracle.csi.filters.LabelListFilter";
	}

	public String resolveClassName() {
		return "Label";
	}
	
	public Predicate createEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("Type".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Argument argument = new Argument();
			if (! (expression.firstOperand.value instanceof LabelType)) {
				if (expression.firstOperand.value instanceof String) {
					argument.value = LabelType.valueOf((String) expression.firstOperand.value);
				} else {
					throw new QLException("Not Supported");
				}
			}
			argument.type = LabelType.class;
			arguments.firstArgument = argument;
			Method method = getEqualsPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	public Class<?>[] getArgumentsForListMethod(ListFilter listFilter, IdentifiableHandle container) {
		Class<?>[] args = new Class[1];
		args[0] = LabelListFilter.class;
		return args;
	}
	
	protected List<Object> invokeListMethod(Control control, Method listMethod, ListFilter listFilter, IdentifiableHandle container) {
		Object[] args = new Object[1];
		args[0] = listFilter;
		List<Object> list = ListFilterHelper.getInstance().invokeListMethod(control, listMethod, args);
		return list;
	}

	public boolean hasContainerAsFirstArgumentOfListMethod() {
		return false;
	}

}
