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

import oracle.csi.Actor;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Label;
import oracle.csi.LabelApplicationType;
import oracle.csi.LabelHandle;
import oracle.csi.Workspace;
import oracle.csi.WorkspaceHandle;
import oracle.csi.controls.Control;
import oracle.csi.filters.LabeledEntityListFilter;
import oracle.csi.filters.ListFilter;
import oracle.csi.filters.Predicate;

public class LabeledEntityPredicateFactory extends MarkedEntityPredicateFactory {
	
	{
		attributeTypeMapper.put("Label".toLowerCase(), Label.class);
		attributeTypeMapper.put("Workspace".toLowerCase(), Workspace.class);
		attributeTypeMapper.put("NumberOfAppliedLabel".toLowerCase(), Long.class);
		attributeTypeMapper.put("LabelAppliedBy".toLowerCase(), Actor.class);
		attributeTypeMapper.put("AppliedLabelType".toLowerCase(), LabelApplicationType.class);
	}
	
	{
		equalsPredicateMethodMapper.put("LabelAppliedBy".toLowerCase(), "createLabelAppCreatorPredicate");
		equalsPredicateMethodMapper.put("AppliedLabelType".toLowerCase(), "createLabelAppTypePredicate");
	} 
	
	{
		greaterThanOrEqualPredicateMethodMapper.put("NumberOfAppliedLabel".toLowerCase(), "createAppliedOnLabelAppCountPredicate");
	}
	
	{
		lessThanOrEqualPredicateMethodMapper.put("NumberOfAppliedLabel".toLowerCase(), "createAppliedOnLabelAppCountPredicate");
	}
	
	{
		sortCriteriaMethodMapper.put("NumberOfAppliedLabel".toLowerCase(), "createAppliedOnLabelAppCountSortCriteria");
	}
	
	static LabeledEntityPredicateFactory singleton = new LabeledEntityPredicateFactory();
	
	public static LabeledEntityPredicateFactory getInstance() {
		return singleton;
	}
	
	public LabeledEntityPredicateFactory() {
		controlClassName = "oracle.csi.controls.LabelControl";
		factoryClassName = "oracle.csi.controls.LabelFactory";
		listFilterCreateMethodName = "createLabeledEntityListFilter";
		listMethodName = "listLabeledEntities";
		listFilter = "oracle.csi.filters.LabeledEntityListFilter";
	}
	
	public String resolveClassName() {
		return "Entity";
	}
	
	public Predicate createEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("AppliedLabelType".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Argument argument = new Argument();
			if (! (expression.firstOperand.value instanceof LabelApplicationType)) {
				if (expression.firstOperand.value instanceof String) {
					argument.value = LabelApplicationType.valueOf((String) expression.firstOperand.value);
				} else {
					throw new QLException("Not Supported");
				}
			}
			argument.type = LabelApplicationType.class;
			arguments.firstArgument = argument;
			Method method = getEqualsPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	public Predicate createGreaterThanOrEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("NumberOfAppliedLabel".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getGreaterThanOrEqualsPredicateMethod(listFilter, expression);
			Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
			arguments.firstArgument = firstArgument;
			Argument secondArgument = new Argument();
			if (expression.primary.isLeftNode) { // the attribute is on the left of ">=" operator
				secondArgument.value = new Boolean(true);
			} else {
				secondArgument.value = new Boolean(false);
			}
			arguments.secondArgument = secondArgument;
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createGreaterThanOrEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	public Predicate createLessThanOrEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("NumberOfAppliedLabel".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getGreaterThanOrEqualsPredicateMethod(listFilter, expression);
			Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
			arguments.firstArgument = firstArgument;
			Argument secondArgument = new Argument();
			if (expression.primary.isLeftNode) { // the attribute is on the left of "<=" operator
				secondArgument.value = new Boolean(false);
			} else {
				secondArgument.value = new Boolean(true);
			}
			arguments.secondArgument = secondArgument;
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createGreaterThanOrEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	public Class<? extends Object>[] getMethodSignatureForListMethod(ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle label = context.getMarker();
		if (label == null) {
			throw new QLException("Not Supported");
		}
		Class<?>[] args = null;
		int listFilterArgPosition = 1;
		
		IdentifiableHandle workspace = context.getWorkspace();
		if (workspace == null) {
			args = new Class[2];
		} else {
			args = new Class[3];
			listFilterArgPosition = 2;
		}
		
		Class<?>[] labelFaces = label.getClass().getInterfaces();
		for (Class<? extends Object> face : labelFaces) {
			if (LabelHandle.class.isAssignableFrom(face)) {
				args[0] = face;
				break;
			}
		}
		if (args[0] == null) {
			throw new QLException("Not Supported");
		}
		
		if (workspace != null) {
			args[1] = WorkspaceHandle.class;
		}	
		
		args[listFilterArgPosition] = LabeledEntityListFilter.class;
		
		return args;
	}
	
	protected List<Object> invokeListMethod(Control control, Method listMethod, ListFilter listFilter, CsiQueryContext context) {
		IdentifiableHandle label = context.getMarker();
		if (label == null) {
			throw new QLException("Not Supported");
		}
		Object[] args = null;
		int listFilterArgPosition = 1;
		
		WorkspaceHandle workspace = (WorkspaceHandle) context.getWorkspace();
		if (workspace == null) {
			args = new Object[2];
		} else {
			args = new Object[3];
			listFilterArgPosition = 2;
		}
		
		args[0] = label;
		if (workspace != null) {
			args[1] = workspace;
		}
		args[listFilterArgPosition] = listFilter;
		List<Object> list = ListFilterHelper.getInstance().invokeListMethod(control, listMethod, args);
		return list;
	}

	
	public boolean hasWorkspaceAsArgumentOfListMethod() {
		return true;
	}
	
	public boolean hasMarkerAsArgumentOfListMethod() {
		return true;
	}

}
