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
import icom.info.MarkerInfo;
import icom.info.TagInfo;
import icom.info.beehive.BeehiveTagInfo;
import icom.jpa.bdk.dao.LabelDAO;
import icom.jpql.stack.SimpleExpression;

import com.oracle.beehive.AppliedOnLabelAppCountPredicate;
import com.oracle.beehive.AppliedOnLabelAppCountSortCriteria;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Entity;
import com.oracle.beehive.Label;
import com.oracle.beehive.LabelType;
import com.oracle.beehive.LabelTypePredicate;
import com.oracle.beehive.Predicate;

public class LabelPredicateFactory extends ArtifactPredicateFactory {
	
	{
		attributeTypeMapper.put(MarkerInfo.Attributes.markedEntities.name().toLowerCase(), Entity.class);
		attributeTypeMapper.put(TagInfo.Attributes.applicationCount.name().toLowerCase(), Long.class);
		attributeTypeMapper.put(BeehiveTagInfo.Attributes.type.name().toLowerCase(), LabelType.class);
	}
	
	{
		equalsPredicateMapper.put(BeehiveTagInfo.Attributes.type.name().toLowerCase(), LabelTypePredicate.class);
	}
	
	{
		greaterThanOrEqualPredicateMapper.put(TagInfo.Attributes.applicationCount.name().toLowerCase(), AppliedOnLabelAppCountPredicate.class);
	}
	
	{
		lessThanOrEqualPredicateMapper.put(TagInfo.Attributes.applicationCount.name().toLowerCase(), AppliedOnLabelAppCountPredicate.class);
	}
	
	{
		sortCriteriaMapper.put(TagInfo.Attributes.applicationCount.name().toLowerCase(), AppliedOnLabelAppCountSortCriteria.class);
	}
	
	static LabelPredicateFactory singleton = new LabelPredicateFactory();
	
	public static LabelPredicateFactory getInstance() {
		return singleton;
	}
	
	public LabelPredicateFactory() {
	}
	
	public String getResourceId(BeeId id) {
		return null;
	}
	
	public String getResourceType() {
		return LabelDAO.getInstance().getResourceType();
	}

	public String resolveDataAccessStateObjectClassName() {
		return Label.class.getSimpleName();
	}
	
	{predicateConstructorMapper.put(
			
		LabelTypePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				LabelTypePredicate predicate = new LabelTypePredicate();
				predicate.setLabelType((LabelType) arguments.firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument argument = new Argument();
				if (! (expression.firstOperand.value instanceof LabelType)) {
					if (expression.firstOperand.value instanceof String) {
						String bdkLabelTypeName = (String) expression.firstOperand.value;
						argument.value = LabelType.valueOf(bdkLabelTypeName);
					} else {
						throw new QLException("Not Supported");
					}
				}
				argument.type = LabelType.class;
				arguments.firstArgument = argument;
				return createPredicate(arguments);
			}
		}
			
	);}
	
	
	{predicateConstructorMapper.put(
			
		AppliedOnLabelAppCountPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				AppliedOnLabelAppCountPredicate predicate = new AppliedOnLabelAppCountPredicate();
				predicate.setCount((Integer) arguments.firstArgument.value);
				predicate.setIsGreater((Boolean) arguments.secondArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (! (predicateMapper == greaterThanOrEqualPredicateMapper)
					|| (predicateMapper == lessThanOrEqualPredicateMapper)) {
					throw new QLException("Not supported");
				}
				ArgumentCollection arguments = new ArgumentCollection();
				
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				
				Argument secondArgument = new Argument();
				if (predicateMapper == greaterThanOrEqualPredicateMapper) {
					if (expression.primary.isLeftNode) { // the attribute is on the left of ">=" operator
						secondArgument.value = new Boolean(true);
					} else {
						secondArgument.value = new Boolean(false);
					}
				} else if (predicateMapper == lessThanOrEqualPredicateMapper) {
					if (expression.primary.isLeftNode) { // the attribute is on the left of "<=" operator
						secondArgument.value = new Boolean(false);
					} else {
						secondArgument.value = new Boolean(true);
					}
				}
				arguments.secondArgument = secondArgument;
				
				return createPredicate(arguments);
			}
		}
	);}

}
