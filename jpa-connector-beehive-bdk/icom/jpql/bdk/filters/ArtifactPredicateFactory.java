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
package icom.jpql.bdk.filters;

import java.util.Map;

import icom.QLException;
import icom.jpql.stack.SimpleExpression;
import icom.ql.parser.TypeHelper;

import com.oracle.beehive.ChangeStatus;
import com.oracle.beehive.ChangeStatusPredicate;
import com.oracle.beehive.Predicate;

public abstract class ArtifactPredicateFactory extends EntityPredicateFactory {

	{
		attributeTypeMapper.put("New".toLowerCase(), Boolean.class);
		attributeTypeMapper.put("Read".toLowerCase(), Boolean.class);
		attributeTypeMapper.put("Unread".toLowerCase(), Boolean.class);
	}
	
	{
		isConditionPredicateMapper.put("New".toLowerCase(), ChangeStatusPredicate.class);
		isConditionPredicateMapper.put("Read".toLowerCase(), ChangeStatusPredicate.class);
		isConditionPredicateMapper.put("Unread".toLowerCase(), ChangeStatusPredicate.class);	
	}
	
	{predicateConstructorMapper.put(
			
		ChangeStatusPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				ChangeStatusPredicate predicate = new ChangeStatusPredicate();
				predicate.setChangeStatus((ChangeStatus) arguments.firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (! (predicateMapper == isConditionPredicateMapper)
					|| (predicateMapper == equalsPredicateMapper)) {
					throw new QLException("Not supported");
				}
				
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument =  new Argument();
				if (expression.primary.simpleAttributeName.equalsIgnoreCase("New")) {
					firstArgument.value = ChangeStatus.NEW;
				} else if (expression.primary.simpleAttributeName.equalsIgnoreCase("Read")) {
					firstArgument.value = ChangeStatus.READ;
				} else if (expression.primary.simpleAttributeName.equalsIgnoreCase("Unread")) {
					firstArgument.value = ChangeStatus.UNREAD;
				}
				arguments.firstArgument = firstArgument;
				Predicate predicate = createPredicate(arguments);
				TypeHelper helper = TypeHelper.getInstance();
				if (expression.firstOperand.type == helper.getBooleanClassType() || 
					expression.firstOperand.type == helper.getBooleanType()) {
					if (!(Boolean) expression.firstOperand.value) {
						predicate = createNotPredicate(predicate);
					}	
				}
				return predicate;
			}	
		}
		
	);}
	
	public Predicate createEqualsPredicate(SimpleExpression expression) {
		if ("New".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			"Read".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			"Unread".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			return createIsConditionPredicate(expression);
		} else {
			return super.createEqualsPredicate(expression);
		}
	}
	
}
