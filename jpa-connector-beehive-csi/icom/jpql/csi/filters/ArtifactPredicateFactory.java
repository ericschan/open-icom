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
import icom.ql.parser.TypeHelper;

import java.lang.reflect.Method;

import oracle.csi.filters.Predicate;

public abstract class ArtifactPredicateFactory extends EntityPredicateFactory {

	{
		attributeTypeMapper.put("New".toLowerCase(), Boolean.class);
		attributeTypeMapper.put("Read".toLowerCase(), Boolean.class);
		attributeTypeMapper.put("Unread".toLowerCase(), Boolean.class);
	}
	
	{
		isConditionPredicateMethodMapper.put("New".toLowerCase(), "createIsNewPredicate");
		isConditionPredicateMethodMapper.put("Read".toLowerCase(), "createIsReadPredicate");
		isConditionPredicateMethodMapper.put("Unread".toLowerCase(), "createIsUnreadPredicate");	
	}
	
	public Predicate createIsConditionPredicate(Object listFilter, SimpleExpression expression) {
		String methodName = isConditionPredicateMethodMapper.get(expression.primary.simpleAttributeName.toLowerCase());
		Method method = null;
		Class<?> cl = listFilter.getClass();	
		try {
			Class<?>[] args = new Class[0];
			method = cl.getMethod(methodName, args);
		} catch (NoSuchMethodException ex) {
			throw new QLException("illegal access exception of method", ex);
		}
		Object[] args = new Object[0];
		Predicate isConditionPredicate = ListFilterHelper.getInstance().createPredicate(method, listFilter, args);
		return isConditionPredicate;
	}
	
	public Predicate createEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("New".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			"Read".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			"Unread".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			TypeHelper helper = TypeHelper.getInstance();
			if (expression.firstOperand.type == helper.getBooleanClassType() || 
				expression.firstOperand.type == helper.getBooleanType()) {
				predicate = createIsConditionPredicate(listFilter, expression);
				if (!(Boolean) expression.firstOperand.value) {
					predicate = createNotPredicate(listFilter, predicate);
				}
			} else {
				predicate = super.createEqualsPredicate(listFilter, expression);
			}
		} else {
			predicate = super.createEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
}
