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

import icom.QLException;
import icom.info.EntityInfo;
import icom.jpql.stack.Operand;
import icom.jpql.stack.OrderByItem;
import icom.jpql.stack.PrimaryOperand;
import icom.jpql.stack.SimpleExpression;
import icom.ql.JPQLException;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.oracle.beehive.Accessor;
import com.oracle.beehive.Actor;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.CompoundSortCriteria;
import com.oracle.beehive.CreatedByPredicate;
import com.oracle.beehive.CreatedOnPredicate;
import com.oracle.beehive.DateTime;
import com.oracle.beehive.Entity;
import com.oracle.beehive.EntityAddress;
import com.oracle.beehive.Label;
import com.oracle.beehive.LabelPredicate;
import com.oracle.beehive.MatchAllPredicate;
import com.oracle.beehive.MatchAnyPredicate;
import com.oracle.beehive.MatchNotPredicate;
import com.oracle.beehive.ModifiedByPredicate;
import com.oracle.beehive.ModifiedOnPredicate;
import com.oracle.beehive.ModifiedOnSortCriteria;
import com.oracle.beehive.NamePredicate;
import com.oracle.beehive.NameSortCriteria;
import com.oracle.beehive.OwnerPredicate;
import com.oracle.beehive.Participant;
import com.oracle.beehive.Predicate;
import com.oracle.beehive.SortCriteria;

public abstract class EntityPredicateFactory extends IdentifiablePredicatefactory {
	
	{
		attributeTypeMapper.put(EntityInfo.Attributes.createdBy.name().toLowerCase(), Actor.class);
		attributeTypeMapper.put(EntityInfo.Attributes.lastModifiedBy.name().toLowerCase(), Actor.class);
		attributeTypeMapper.put(EntityInfo.Attributes.owner.name().toLowerCase(), Accessor.class);
		attributeTypeMapper.put(EntityInfo.Attributes.parent.name().toLowerCase(), Entity.class);
		attributeTypeMapper.put(EntityInfo.Attributes.attachedMarkers.name().toLowerCase(), Label.class);
		attributeTypeMapper.put(EntityInfo.Attributes.name.name().toLowerCase(), String.class);
		attributeTypeMapper.put(EntityInfo.Attributes.creationDate.name().toLowerCase(), Date.class);
		attributeTypeMapper.put(EntityInfo.Attributes.lastModificationDate.name().toLowerCase(), Date.class);
	}
	
	{
		equalsPredicateMapper.put(EntityInfo.Attributes.createdBy.name().toLowerCase(), CreatedByPredicate.class);
		equalsPredicateMapper.put(EntityInfo.Attributes.lastModifiedBy.name().toLowerCase(), ModifiedByPredicate.class);
		equalsPredicateMapper.put(EntityInfo.Attributes.owner.name().toLowerCase(), OwnerPredicate.class);
		equalsPredicateMapper.put(EntityInfo.Attributes.attachedMarkers.name().toLowerCase(), LabelPredicate.class);
		equalsPredicateMapper.put(EntityInfo.Attributes.name.name().toLowerCase(), NamePredicate.class);
	}
	
	{
		likePredicateMapper.put(EntityInfo.Attributes.name.name().toLowerCase(), NamePredicate.class);
	}
	
	{
		betweenPredicateMapper.put(EntityInfo.Attributes.creationDate.name().toLowerCase(), CreatedOnPredicate.class);
		betweenPredicateMapper.put(EntityInfo.Attributes.lastModificationDate.name().toLowerCase(), ModifiedOnPredicate.class);
	}
	
	{
        greaterThanPredicateMapper.put(EntityInfo.Attributes.creationDate.name().toLowerCase(), CreatedOnPredicate.class);
        greaterThanPredicateMapper.put(EntityInfo.Attributes.lastModificationDate.name().toLowerCase(), ModifiedOnPredicate.class);
    }
    
    {
        lessThanPredicateMapper.put(EntityInfo.Attributes.creationDate.name().toLowerCase(), CreatedOnPredicate.class);
        lessThanPredicateMapper.put(EntityInfo.Attributes.lastModificationDate.name().toLowerCase(), ModifiedOnPredicate.class);
    }
	
	{
		greaterThanOrEqualPredicateMapper.put(EntityInfo.Attributes.creationDate.name().toLowerCase(), CreatedOnPredicate.class);
		greaterThanOrEqualPredicateMapper.put(EntityInfo.Attributes.lastModificationDate.name().toLowerCase(), ModifiedOnPredicate.class);
	}
	
	{
		lessThanOrEqualPredicateMapper.put(EntityInfo.Attributes.creationDate.name().toLowerCase(), CreatedOnPredicate.class);
		lessThanOrEqualPredicateMapper.put(EntityInfo.Attributes.lastModificationDate.name().toLowerCase(), ModifiedOnPredicate.class);
	}
	
	{
		sortCriteriaMapper.put(EntityInfo.Attributes.name.name().toLowerCase(), NameSortCriteria.class);
		sortCriteriaMapper.put(EntityInfo.Attributes.lastModificationDate.name().toLowerCase(), ModifiedOnSortCriteria.class);
	}
	
	{predicateConstructorMapper.put(
			
		CreatedByPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				CreatedByPredicate predicate = new CreatedByPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setCreatedBy((BeeId) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (predicateMapper != equalsPredicateMapper) {
					throw new QLException("Not supported");
				}
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
		
		ModifiedByPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				ModifiedByPredicate predicate = new ModifiedByPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setModifiedBy((BeeId) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (predicateMapper != equalsPredicateMapper) {
					throw new QLException("Not supported");
				}
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
		
		OwnerPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				OwnerPredicate predicate = new OwnerPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setOwnerMatch((BeeId) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (predicateMapper != equalsPredicateMapper) {
					throw new QLException("Not supported");
				}
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		LabelPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				LabelPredicate predicate = new LabelPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setLabelMatch((BeeId) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (predicateMapper != equalsPredicateMapper) {
					throw new QLException("Not supported");
				}
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		NamePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				NamePredicate predicate = new NamePredicate();
				Argument firstArgument = arguments.firstArgument;
				boolean patternMatch = false;
				if (arguments.secondArgument != null) {
				    patternMatch = (Boolean) arguments.secondArgument.value;
				}
				if (patternMatch) {
				    predicate.setMatch((String) firstArgument.value);
				    predicate.setPattern((String) firstArgument.value);
					predicate.setPatternMatch(true);
				} else {
				    predicate.setMatch((String) firstArgument.value);
				    predicate.setPatternMatch(false);
				}
				predicate.setCaseSensitiveMatch(false);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				if (predicateMapper == likePredicateMapper) {
					arguments.secondArgument = new Argument();
					arguments.secondArgument.name = "";
					arguments.secondArgument.value = new Boolean(true);
				}
				return createPredicate(arguments);
			}
		}
	);}
	
	{predicateConstructorMapper.put(
			
		CreatedOnPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				CreatedOnPredicate predicate = new CreatedOnPredicate();
				Argument firstArgument = arguments.firstArgument;
				if (! firstArgument.setNull) {
					Date createdOnDateLow = (Date) firstArgument.value;
					GregorianCalendar gcalLow = new GregorianCalendar();
					gcalLow.setTime(createdOnDateLow);
					try {
						XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalLow);
						predicate.setStartDate(xgcal);
					} catch (DatatypeConfigurationException ex) {
						throw new PersistenceException(ex);
					}
				} else {
					predicate.setStartDate(null);
				}
				Argument secondArgument =  arguments.secondArgument;
				if (! secondArgument.setNull) {
					Date createdOnDateHigh = (Date) secondArgument.value;
					GregorianCalendar gcalHigh = new GregorianCalendar();
					gcalHigh.setTime(createdOnDateHigh);
					try {
						XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalHigh);
						predicate.setEndDate(xgcal);
					} catch (DatatypeConfigurationException ex) {
						throw new PersistenceException(ex);
					}
				} else {
					predicate.setEndDate(null);
				}
				return predicate;
			}
			
            Predicate createBetweenPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                Argument secondArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
                arguments.secondArgument = secondArgument;
                return createPredicate(arguments);
            }
			
            Predicate createGreaterPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument unusedArgument = new Argument();
                unusedArgument.setNull = true;
                Argument argument = convertOperandToArgument(expression.primary, expression.firstOperand);
                if (expression.primary.isLeftNode) { // the attribute is on the right of ">" operator
                    arguments.firstArgument = unusedArgument;
                    arguments.secondArgument = argument;
                } else {
                    arguments.firstArgument = argument;
                    arguments.secondArgument = unusedArgument;
                }
                return createPredicate(arguments);
            }

            Predicate createLessPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument unusedArgument = new Argument();
                unusedArgument.setNull = true;
                Argument argument = convertOperandToArgument(expression.primary, expression.firstOperand);
                if (expression.primary.isLeftNode) { // the attribute is on the right of "<" operator
                    arguments.firstArgument = argument;
                    arguments.secondArgument = unusedArgument;
                } else {
                    arguments.firstArgument = unusedArgument;
                    arguments.secondArgument = argument;
                }
                return createPredicate(arguments);
            }
            
            Predicate createLessOrEqualPredicateInternal(SimpleExpression expression) {
                MatchNotPredicate predicate = new MatchNotPredicate();
                predicate.setPredicate(createGreaterPredicateInternal(expression));
                return predicate;
            }
            
            Predicate createGreaterOrEqualPredicateInternal(SimpleExpression expression) {
                MatchNotPredicate predicate = new MatchNotPredicate();
                predicate.setPredicate(createLessPredicateInternal(expression));
                return predicate;
            }
            
            Predicate createEqualsPredicateInternal(SimpleExpression expression) {
                MatchAllPredicate predicate = new MatchAllPredicate();
                predicate.getPredicates().add(createLessOrEqualPredicateInternal(expression));
                predicate.getPredicates().add(createGreaterOrEqualPredicateInternal(expression));
                return predicate;
            }
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (predicateMapper == betweenPredicateMapper) {
					return createBetweenPredicateInternal(expression);
				} else if (predicateMapper == greaterThanPredicateMapper) {
					return createGreaterPredicateInternal(expression);
				} else if (predicateMapper == lessThanPredicateMapper) {
					return createLessPredicateInternal(expression);
				} else if (predicateMapper == greaterThanOrEqualPredicateMapper) {
				    return createGreaterOrEqualPredicateInternal(expression);
				} else if (predicateMapper == lessThanOrEqualPredicateMapper) {
				    return createLessOrEqualPredicateInternal(expression);
				} else if (predicateMapper == equalsPredicateMapper) {
                    return createEqualsPredicateInternal(expression);
                }
				return null;
			}
		}
	
	);}
	
	{predicateConstructorMapper.put(
			
		ModifiedOnPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				ModifiedOnPredicate predicate = new ModifiedOnPredicate();
				Argument firstArgument = arguments.firstArgument;
				if (! firstArgument.setNull) {
					Date createdOnDateLow = (Date) firstArgument.value;
					GregorianCalendar gcalLow = new GregorianCalendar();
					gcalLow.setTime(createdOnDateLow);
					try {
						XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalLow);
						predicate.setStartDate(xgcal);
					} catch (DatatypeConfigurationException ex) {
						throw new PersistenceException(ex);
					}
				} else {
					predicate.setStartDate(null);
				}
				Argument secondArgument =  arguments.secondArgument;
				if (! secondArgument.setNull) {
					Date createdOnDateHigh = (Date) secondArgument.value;
					GregorianCalendar gcalHigh = new GregorianCalendar();
					gcalHigh.setTime(createdOnDateHigh);
					try {
						XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalHigh);
						predicate.setEndDate(xgcal);
					} catch (DatatypeConfigurationException ex) {
						throw new PersistenceException(ex);
					}
				} else {
					predicate.setEndDate(null);
				}
				return predicate;
			}
			
			Predicate createBetweenPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                Argument secondArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
                arguments.secondArgument = secondArgument;
                return createPredicate(arguments);
            }
			
			Predicate createGreaterPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument unusedArgument = new Argument();
                unusedArgument.setNull = true;
                Argument argument = convertOperandToArgument(expression.primary, expression.firstOperand);
                if (expression.primary.isLeftNode) { // the attribute is on the right of ">" operator
                    arguments.firstArgument = unusedArgument;
                    arguments.secondArgument = argument;
                } else {
                    arguments.firstArgument = argument;
                    arguments.secondArgument = unusedArgument;
                }
                return createPredicate(arguments);
            }

            Predicate createLessPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument unusedArgument = new Argument();
                unusedArgument.setNull = true;
                Argument argument = convertOperandToArgument(expression.primary, expression.firstOperand);
                if (expression.primary.isLeftNode) { // the attribute is on the right of "<" operator
                    arguments.firstArgument = argument;
                    arguments.secondArgument = unusedArgument;
                } else {
                    arguments.firstArgument = unusedArgument;
                    arguments.secondArgument = argument;
                }
                return createPredicate(arguments);
            }
            
            Predicate createLessOrEqualPredicateInternal(SimpleExpression expression) {
                MatchNotPredicate predicate = new MatchNotPredicate();
                predicate.setPredicate(createGreaterPredicateInternal(expression));
                return predicate;
            }
            
            Predicate createGreaterOrEqualPredicateInternal(SimpleExpression expression) {
                MatchNotPredicate predicate = new MatchNotPredicate();
                predicate.setPredicate(createLessPredicateInternal(expression));
                return predicate;
            }
            
            Predicate createEqualsPredicateInternal(SimpleExpression expression) {
                MatchAllPredicate predicate = new MatchAllPredicate();
                predicate.getPredicates().add(createLessOrEqualPredicateInternal(expression));
                predicate.getPredicates().add(createGreaterOrEqualPredicateInternal(expression));
                return predicate;
            }
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
			    if (predicateMapper == betweenPredicateMapper) {
                    return createBetweenPredicateInternal(expression);
                } else if (predicateMapper == greaterThanPredicateMapper) {
                    return createGreaterPredicateInternal(expression);
                } else if (predicateMapper == lessThanPredicateMapper) {
                    return createLessPredicateInternal(expression);
                } else if (predicateMapper == greaterThanOrEqualPredicateMapper) {
                    return createGreaterOrEqualPredicateInternal(expression);
                } else if (predicateMapper == lessThanOrEqualPredicateMapper) {
                    return createLessOrEqualPredicateInternal(expression);
                } else if (predicateMapper == equalsPredicateMapper) {
                    return createEqualsPredicateInternal(expression);
                }
                return null;
			}
		}
	
	);}
	
	abstract public String getResourceId(BeeId id);
	
	abstract public String getResourceType();
	
	abstract public String resolveDataAccessStateObjectClassName();
	
	public Object resolveAttributeType(String attribute) {
		return attributeTypeMapper.get(attribute.toLowerCase());
	}
	
	protected Argument convertOperandToArgument(PrimaryOperand primaryOperand, Operand operand) {
		Argument argument = new Argument();
		if (operand.isLiteral) {
			Object attributeType =  resolveAttributeType(primaryOperand.simpleAttributeName);
			argument.name = operand.name;
			argument.value = resolveAttributeValueWithType(operand.value, attributeType);
			argument.type = attributeType;
		} else {
			argument.name = operand.name;
			argument.value = operand.value;
			argument.type = operand.type;
		}
		return argument;
	}
	
	public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> mapper, String symbol) {
        String attribName = expression.primary.simpleAttributeName.toLowerCase();
        Class<?> predicateClass = mapper.get(attribName);
        PredicateConstructor constructor = predicateConstructorMapper.get(predicateClass);
        if (constructor == null) {
            String unsupportedExpression = attribName + " " + symbol;
            String queryInfo = expression.context.getParser().getQueryInfo();
            throw JPQLException.expressionNotSupported(queryInfo, unsupportedExpression);
        }
        Predicate predicate = constructor.createPredicate(expression, mapper);
        return predicate;
    }

	public Predicate createEqualsPredicate(SimpleExpression expression) {
	    return createPredicate(expression, equalsPredicateMapper, "=");
	}
	
	final public Predicate createNotEqualsPredicate(SimpleExpression expression) {
		Predicate predicate = createEqualsPredicate(expression);
		predicate = createNotPredicate(predicate);
		return predicate;
	}
	
	public Predicate createLikePredicate(SimpleExpression expression) {
	    return createPredicate(expression, likePredicateMapper, "like");
	}
	
	public Predicate createBetweenPredicate(SimpleExpression expression) {
	    return createPredicate(expression, betweenPredicateMapper, "between");
	}
	
	public Predicate createGreaterThanOrEqualsPredicate(SimpleExpression expression) {
	    return createPredicate(expression, greaterThanOrEqualPredicateMapper, ">=");
	}
	
	public Predicate createLessThanOrEqualsPredicate(SimpleExpression expression) {
	    return createPredicate(expression, lessThanOrEqualPredicateMapper, "<=");
	}
	
	public Predicate createGreaterThanPredicate(SimpleExpression expression) {
	    return createPredicate(expression, greaterThanPredicateMapper, ">");
	}
	
	public Predicate createLessThanPredicate(SimpleExpression expression) {
	    return createPredicate(expression, lessThanPredicateMapper, "<");
	}
	
	public Predicate createMemberOfPredicate(SimpleExpression expression) {
	    return createPredicate(expression, collectionMemberPredicateMapper, "member");
	}
	
	public Predicate createInPredicate(SimpleExpression expression) {
	    return createPredicate(expression, inPredicateMapper, "in");
	}
	
	public Predicate createIsConditionPredicate(SimpleExpression expression) {
	    return createPredicate(expression, isConditionPredicateMapper, "is");
	}
	
	public Predicate createNotPredicate(Predicate predicate) {
		MatchNotPredicate notPredicate = new MatchNotPredicate();
		notPredicate.setPredicate(predicate);
		return notPredicate;
	}
	
	public Predicate createMatchAnyPredicate(Set<Predicate> matchAnyPredicateList) {
		MatchAnyPredicate matchAnyPredicate = new MatchAnyPredicate();
		matchAnyPredicate.getPredicates().addAll(matchAnyPredicateList);
		return matchAnyPredicate;
	}
	
	public Predicate createMatchAllPredicate(Set<Predicate> matchAllPredicateList) {
		MatchAllPredicate matchAllPredicate = new MatchAllPredicate();
		matchAllPredicate.getPredicates().addAll(matchAllPredicateList);
		return matchAllPredicate;
	}
	
	public SortCriteria createSortCriteria(OrderByItem item) {
		String attribName = item.simpleAttributeName.toLowerCase();
		Class<?> sortCriteriaClass = sortCriteriaMapper.get(attribName);
		SortCriteria sortCriteria = null;
		try {
			sortCriteria = (SortCriteria) sortCriteriaClass.newInstance();
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of identifiable class " + sortCriteriaClass.getCanonicalName(), ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of identifiable class " + sortCriteriaClass.getCanonicalName(), ex);
		}	
		sortCriteria.setAscending(item.ascending);
		return sortCriteria;
	}
	
	public SortCriteria createSortCriteria(List<SortCriteria> compoundSortCriteriaList) {
		CompoundSortCriteria compoundSortCriteria = new CompoundSortCriteria();
		compoundSortCriteria.getSortCriteriaLists().addAll(compoundSortCriteriaList);
		compoundSortCriteria.setAscending(true);
		return compoundSortCriteria;
		
	}

	public boolean isCollectionValuedAttribute(Class<? extends Object> ownerClass, String attribute) {
		try {
			Field field = ownerClass.getField(attribute);
			Class<? extends Object> type = field.getType();
			if (Iterable.class.isAssignableFrom(type)) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchFieldException ex) {
			throw new QLException("Not Supported", ex);
		} catch (SecurityException ex) {
			throw new QLException("Not Supported", ex);
		}
		
	}
	
	static public boolean isEntityClass(Object type) {
		if (Entity.class.isAssignableFrom((Class<?>) type)) {
			return true;
		}
		return false;
	}
	
	static public boolean isEntityAddressClass(Object type) {
        if (EntityAddress.class.isAssignableFrom((Class<?>) type)) {
            return true;
        }
        return false;
    }
	
	static public boolean isParticipantClass(Object type) {
		if (Participant.class.isAssignableFrom((Class<?>) type)) {
			return true;
		}
		return false;
	}
	
	static public boolean isDateClass(Object type) {
		if (Date.class.isAssignableFrom((Class<?>) type)) {
			return true;
		}
		return false;
	}
	
	static public boolean isDateTimeClass(Object type) {
		if (DateTime.class.isAssignableFrom((Class<?>) type)) {
			return true;
		}
		return false;
	}
	
	static public boolean isLocaleClass(Object type) {
		if (Locale.class.isAssignableFrom((Class<?>) type)) {
			return true;
		}
		return false;
	}

	static public Object resolveAttributeValueWithType(Object value, Object type) {
		if (isEntityClass(type)) {
		    
		} else if (isEntityAddressClass(type)) {

		} else if (isParticipantClass(type)) {
		    
		} else if (isDateClass(type)) {
			
		} else if (isDateTimeClass(type)) {
			
		} else if (isLocaleClass(type)) {
			
		}
		return value;
	}
	
	protected Long getLongValue(Object obj) {
		Long value = null;
		if (! ((obj instanceof Long) || (obj instanceof Integer))) {
			if (obj instanceof String) {
				value = Long.valueOf((String) obj);
			} else {
				throw new QLException("Not Supported");
			}
		}
		if ((obj instanceof Integer)) {
			value = Long.valueOf((Integer) obj);
		} else {
			value = (Long) obj;
		}
		return value;
	}
	
	public boolean hasContainerAsArgumentOfListMethod() {
		return false;
	}
	
	public boolean hasWorkspaceAsArgumentOfListMethod() {
		return false;
	}
	
	public boolean hasMarkerAsArgumentOfListMethod() {
		return false;
	}

}
