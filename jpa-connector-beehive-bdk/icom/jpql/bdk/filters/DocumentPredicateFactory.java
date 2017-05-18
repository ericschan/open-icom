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

import icom.info.DocumentInfo;
import icom.info.EntityInfo;
import icom.jpa.bdk.dao.DocumentDAO;
import icom.jpql.stack.SimpleExpression;

import java.util.Locale;
import java.util.Map;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.ContentLanguagePredicate;
import com.oracle.beehive.ContentPredicate;
import com.oracle.beehive.CreatedBySortCriteria;
import com.oracle.beehive.CreatedOnSortCriteria;
import com.oracle.beehive.Document;
import com.oracle.beehive.MatchAllPredicate;
import com.oracle.beehive.MatchNotPredicate;
import com.oracle.beehive.MediaTypePredicate;
import com.oracle.beehive.MediaTypeSortCriteria;
import com.oracle.beehive.ModifiedBySortCriteria;
import com.oracle.beehive.PathPredicate;
import com.oracle.beehive.Predicate;
import com.oracle.beehive.SizePredicate;
import com.oracle.beehive.SizeSortCriteria;

public class DocumentPredicateFactory extends ArtifactPredicateFactory {
	
	static DocumentPredicateFactory singleton = new DocumentPredicateFactory();
	
	public static DocumentPredicateFactory getInstance() {
		return singleton;
	}
	
	public DocumentPredicateFactory() {
	}
	
	public String getResourceId(BeeId id) {
		return null;
	}
	
	public String getResourceType() {
		return DocumentDAO.getInstance().getResourceType();
	}
	
	public String resolveDataAccessStateObjectClassName() {
		return Document.class.getSimpleName();
	}
	
	{
		attributeTypeMapper.put(DocumentInfo.Attributes.content.name().toLowerCase(), String.class);
		attributeTypeMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), Long.class);
		attributeTypeMapper.put("Path".toLowerCase(), String.class);
		attributeTypeMapper.put("MediaType".toLowerCase(), String.class);
		attributeTypeMapper.put("ContentLanguage".toLowerCase(), Locale.class);
	}
	
	{
	    equalsPredicateMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
		equalsPredicateMapper.put("Path".toLowerCase(), PathPredicate.class);
		equalsPredicateMapper.put("ContentLanguage".toLowerCase(), ContentLanguagePredicate.class);
		equalsPredicateMapper.put("MediaType".toLowerCase(), MediaTypePredicate.class);
	}
		
	{
		likePredicateMapper.put(DocumentInfo.Attributes.content.name().toLowerCase(), ContentPredicate.class);
		likePredicateMapper.put("Path".toLowerCase(), PathPredicate.class);
	}
	
	{
	    betweenPredicateMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
    }
	
	{
        greaterThanPredicateMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
    }
    
    {
        lessThanPredicateMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
    }
	
	{
	    greaterThanOrEqualPredicateMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
	}
	
	{
	    lessThanOrEqualPredicateMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
	}
	
	{
		sortCriteriaMapper.put(EntityInfo.Attributes.createdBy.name().toLowerCase(), CreatedBySortCriteria.class);
		sortCriteriaMapper.put(EntityInfo.Attributes.lastModifiedBy.name().toLowerCase(), ModifiedBySortCriteria.class);
		sortCriteriaMapper.put(EntityInfo.Attributes.creationDate.name().toLowerCase(), CreatedOnSortCriteria.class);
		sortCriteriaMapper.put(DocumentInfo.Attributes.size.name().toLowerCase(), SizeSortCriteria.class);
		sortCriteriaMapper.put("MediaType".toLowerCase(), MediaTypeSortCriteria.class);
	}
	
	{predicateConstructorMapper.put(
		
		SizePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				return null;
			}
			
			Predicate createBetweenPredicate(ArgumentCollection arguments) {
			    SizePredicate abovePredicate = new SizePredicate();
                long aboveSsize = ((Number)arguments.firstArgument.value).longValue();    
                abovePredicate.setSize(aboveSsize);
                abovePredicate.setIsGreater(true);
                
                SizePredicate belowPredicate = new SizePredicate();
                long belowSize = ((Number)arguments.secondArgument.value).longValue();    
                belowPredicate.setSize(belowSize);
                belowPredicate.setIsGreater(false);
                
                MatchAllPredicate predicate = new MatchAllPredicate();
                predicate.getPredicates().add(abovePredicate);
                predicate.getPredicates().add(belowPredicate);
                return predicate;
            }
			
			Predicate createGreaterPredicate(ArgumentCollection arguments) {
                SizePredicate predicate = new SizePredicate();
                long size = ((Number)arguments.firstArgument.value).longValue();    
                predicate.setSize(size);
                predicate.setIsGreater(true);
                return predicate;
            }
			
			Predicate createLessOrEqualPredicate(ArgumentCollection arguments) {
			    MatchNotPredicate predicate = new MatchNotPredicate();
			    predicate.setPredicate(createGreaterPredicate(arguments));
			    return predicate;
            }
			
			Predicate createLessPredicate(ArgumentCollection arguments) {
                SizePredicate predicate = new SizePredicate();
                long size = ((Number)arguments.firstArgument.value).longValue();    
                predicate.setSize(size);
                predicate.setIsGreater(false);
                return predicate;
            }
			
			Predicate createGreaterOrEqualPredicate(ArgumentCollection arguments) {
                MatchNotPredicate predicate = new MatchNotPredicate();
                predicate.setPredicate(createLessPredicate(arguments));
                return predicate;
            }
			
			Predicate createEqualsPredicate(ArgumentCollection arguments) {
                MatchAllPredicate predicate = new MatchAllPredicate();
                predicate.getPredicates().add(createLessOrEqualPredicate(arguments));
                predicate.getPredicates().add(createGreaterOrEqualPredicate(arguments));
                return predicate;
            }
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				
				if (predicateMapper == betweenPredicateMapper) {
				    Argument secondArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
	                arguments.secondArgument = secondArgument;
	                return createBetweenPredicate(arguments);
				} else if (predicateMapper == greaterThanPredicateMapper) {
					if (expression.primary.isLeftNode) { // the attribute is on the right of ">" operator
					    return createGreaterPredicate(arguments);
					} else {
						return createLessPredicate(arguments);
					}
				} else if (predicateMapper == lessThanPredicateMapper) {
					if (expression.primary.isLeftNode) { // the attribute is on the right of "<" operator
					    return createLessPredicate(arguments);
					} else {
					    return createGreaterPredicate(arguments);
					}
				} else if (predicateMapper == greaterThanOrEqualPredicateMapper) {
                    if (expression.primary.isLeftNode) { // the attribute is on the right of ">=" operator
                        return createGreaterOrEqualPredicate(arguments);
                    } else {
                        return createLessOrEqualPredicate(arguments);
                    }
                } else if (predicateMapper == lessThanOrEqualPredicateMapper) {
                    if (expression.primary.isLeftNode) { // the attribute is on the right of "<=" operator
                        return createLessOrEqualPredicate(arguments);
                    } else {
                        return createGreaterOrEqualPredicate(arguments);
                    }
                } else if (predicateMapper == equalsPredicateMapper) {
                    return createEqualsPredicate(arguments);
                }
                return null; 
			}
		}
		
	);}

	public boolean hasContainerAsArgumentOfListMethod() {
		return true;
	}

}
