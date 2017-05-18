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
import icom.info.MessageInfo;
import icom.info.UnifiedMessageInfo;
import icom.jpa.bdk.dao.EmailMessageDAO;
import icom.jpa.bdk.dao.EntityDAO;
import icom.jpql.stack.Operand;
import icom.jpql.stack.SimpleExpression;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.oracle.beehive.BccPredicate;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.CcPredicate;
import com.oracle.beehive.ChangeStatusSortCriteria;
import com.oracle.beehive.ContentPredicate;
import com.oracle.beehive.DateTime;
import com.oracle.beehive.DeliveredTimePredicate;
import com.oracle.beehive.DeliveredTimeSortCriteria;
import com.oracle.beehive.EmailMessage;
import com.oracle.beehive.EmailMessageType;
import com.oracle.beehive.EmailMessageTypePredicate;
import com.oracle.beehive.EmailMessageTypeSortCriteria;
import com.oracle.beehive.FromPredicate;
import com.oracle.beehive.HasAttachmentSortCriteria;
import com.oracle.beehive.HeaderPredicate;
import com.oracle.beehive.IsModifiablePredicate;
import com.oracle.beehive.MatchAllPredicate;
import com.oracle.beehive.MatchNotPredicate;
import com.oracle.beehive.Participant;
import com.oracle.beehive.Predicate;
import com.oracle.beehive.Priority;
import com.oracle.beehive.PriorityPredicate;
import com.oracle.beehive.PrioritySortCriteria;
import com.oracle.beehive.RecentPredicate;
import com.oracle.beehive.SenderSortCriteria;
import com.oracle.beehive.SentTimePredicate;
import com.oracle.beehive.SentTimeSortCriteria;
import com.oracle.beehive.SizePredicate;
import com.oracle.beehive.SizeSortCriteria;
import com.oracle.beehive.TimeComparator;
import com.oracle.beehive.ToPredicate;
import com.oracle.beehive.ToSortCriteria;
import com.oracle.beehive.UidPredicate;

public class EmailPredicateFactory extends ArtifactPredicateFactory {
	
	static EmailPredicateFactory singleton = new EmailPredicateFactory();
	
	public static EmailPredicateFactory getInstance() {
		return singleton;
	}
	
	public EmailPredicateFactory() {
	}
	
	public String getResourceId(BeeId id) {
		return null;
	}
	
	public String getResourceType() {
		return EmailMessageDAO.getInstance().getResourceType();
	}
	
	public String resolveDataAccessStateObjectClassName() {
		return EmailMessage.class.getSimpleName();
	}
	
	{
		attributeTypeMapper.put(MessageInfo.Attributes.sender.name().toLowerCase(), Participant.class);
		attributeTypeMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), DateTime.class);
		attributeTypeMapper.put(MessageInfo.Attributes.content.name().toLowerCase(), String.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.toReceivers.name().toLowerCase(), Participant.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.ccReceivers.name().toLowerCase(), Participant.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.bccReceivers.name().toLowerCase(), Participant.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.channel.name().toLowerCase(), EmailMessageType.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.priority.name().toLowerCase(), Priority.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.editMode.name().toLowerCase(), Boolean.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.mimeHeaders.name().toLowerCase(), Map.class);
		attributeTypeMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), Long.class);
		attributeTypeMapper.put("sentTime".toLowerCase(), Date.class);
		attributeTypeMapper.put("Uid".toLowerCase(), Long.class);
	}
	
	{
		likePredicateMapper.put(MessageInfo.Attributes.content.name().toLowerCase(), ContentPredicate.class);
	}
	
	{
		equalsPredicateMapper.put(MessageInfo.Attributes.sender.name().toLowerCase(), FromPredicate.class);	
		equalsPredicateMapper.put(UnifiedMessageInfo.Attributes.editMode.name().toLowerCase(), IsModifiablePredicate.class);	
		equalsPredicateMapper.put(UnifiedMessageInfo.Attributes.channel.name().toLowerCase(), EmailMessageTypePredicate.class);
		equalsPredicateMapper.put(UnifiedMessageInfo.Attributes.priority.name().toLowerCase(), PriorityPredicate.class);
		equalsPredicateMapper.put(UnifiedMessageInfo.Attributes.mimeHeaders.name().toLowerCase(), HeaderPredicate.class);
		equalsPredicateMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
		equalsPredicateMapper.put("Uid".toLowerCase(), UidPredicate.class);
	}
	
	{
	    betweenPredicateMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), SentTimePredicate.class);
	    betweenPredicateMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
		betweenPredicateMapper.put("Uid".toLowerCase(), UidPredicate.class);
	}

	{
		//greaterThanPredicateMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), DeliveredTimePredicate.class);
	    greaterThanPredicateMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), SentTimePredicate.class);
		//greaterThanPredicateMapper.put("SentTime".toLowerCase(), SentTimePredicate.class);
		greaterThanPredicateMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
		greaterThanPredicateMapper.put("Uid".toLowerCase(), RecentPredicate.class);
	}
	
	{
		//lessThanPredicateMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), DeliveredTimePredicate.class);
	    lessThanPredicateMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), SentTimePredicate.class);
		//lessThanPredicateMapper.put("SentTime".toLowerCase(), SentTimePredicate.class);
		lessThanPredicateMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
		lessThanPredicateMapper.put("Uid".toLowerCase(), RecentPredicate.class);
	}
	
	{
	    greaterThanOrEqualPredicateMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), SentTimePredicate.class);
        greaterThanOrEqualPredicateMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
    }
    
    {
        lessThanOrEqualPredicateMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), SentTimePredicate.class);
        lessThanOrEqualPredicateMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), SizePredicate.class);
    }
	
	{
		collectionMemberPredicateMapper.put(UnifiedMessageInfo.Attributes.toReceivers.name().toLowerCase(), ToPredicate.class);
		collectionMemberPredicateMapper.put(UnifiedMessageInfo.Attributes.ccReceivers.name().toLowerCase(), CcPredicate.class);
		collectionMemberPredicateMapper.put(UnifiedMessageInfo.Attributes.bccReceivers.name().toLowerCase(), BccPredicate.class);
	}
	
	{
		inPredicateMapper.put("Uid".toLowerCase(), UidPredicate.class);
	}
	
	{
		sortCriteriaMapper.put(MessageInfo.Attributes.sender.name().toLowerCase(), SenderSortCriteria.class);
		sortCriteriaMapper.put(MessageInfo.Attributes.deliveredTime.name().toLowerCase(), DeliveredTimeSortCriteria.class);
		sortCriteriaMapper.put(UnifiedMessageInfo.Attributes.toReceivers.name().toLowerCase(), ToSortCriteria.class);
		sortCriteriaMapper.put(UnifiedMessageInfo.Attributes.channel.name().toLowerCase(), EmailMessageTypeSortCriteria.class);
		sortCriteriaMapper.put(UnifiedMessageInfo.Attributes.priority.name().toLowerCase(), PrioritySortCriteria.class);
		sortCriteriaMapper.put(UnifiedMessageInfo.Attributes.size.name().toLowerCase(), SizeSortCriteria.class);
		sortCriteriaMapper.put("ChangeStatus".toLowerCase(), ChangeStatusSortCriteria.class);
        sortCriteriaMapper.put("HasAttachment".toLowerCase(), HasAttachmentSortCriteria.class);
        sortCriteriaMapper.put("SentTime".toLowerCase(), SentTimeSortCriteria.class);
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
	
	{predicateConstructorMapper.put(
			
		EmailMessageTypePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				EmailMessageTypePredicate predicate = new EmailMessageTypePredicate();
				predicate.setType((EmailMessageType) arguments.firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument argument = new Argument();
				if (! (expression.firstOperand.value instanceof EmailMessageType)) {
					if (expression.firstOperand.value instanceof String) {
						String bdkChannelTypeName = EmailMessageDAO.pojoToBdkChannelTypeNameMap.get((String) expression.firstOperand.value);
						argument.value = EmailMessageType.valueOf(bdkChannelTypeName);
					} else {
						throw new QLException("Not Supported");
					}
				}
				argument.type = EmailMessageType.class;
				arguments.firstArgument = argument;
				return createPredicate(arguments);
			}
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		PriorityPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				PriorityPredicate predicate = new PriorityPredicate();
				predicate.setPriority((Priority) arguments.firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument argument = new Argument();
				if (! (expression.firstOperand.value instanceof Priority)) {
					if (expression.firstOperand.value instanceof String) {
						String bdkPriorityName = EntityDAO.pojoToBdkPriorityNameMap.get((String) expression.firstOperand.value);
						argument.value = Priority.valueOf(bdkPriorityName);
					} else {
						throw new QLException("Not Supported");
					}
				}
				argument.type = Priority.class;
				arguments.firstArgument = argument;
				return createPredicate(arguments);
			}
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		IsModifiablePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				IsModifiablePredicate predicate = new IsModifiablePredicate();
				predicate.setIsModifiable((Boolean) arguments.firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument argument = new Argument();
				if (! (expression.firstOperand.value instanceof Boolean)) {
					if (expression.firstOperand.value instanceof String) {
						if (EmailMessageDAO.MessageMode.DeliveredCopy.name().equalsIgnoreCase((String) expression.firstOperand.value)) {
							argument.value = new Boolean(false);
						} else {
							argument.value = new Boolean(true);
						}
					} else {
						throw new QLException("Not Supported");
					}
				}
				argument.type = Boolean.class;
				arguments.firstArgument = argument;
				return createPredicate(arguments);
			}
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		UidPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				UidPredicate predicate = new UidPredicate();
				predicate.getUIDSets().addAll((HashSet<Long>) arguments.firstArgument.value);
				if (! arguments.secondArgument.setNull) {
					predicate.setRangeStart((Long) arguments.secondArgument.value);
				} else {
					predicate.setRangeStart(null);
				}
				if (! arguments.thirdArgument.setNull) {
					predicate.setRangeEnd((Long) arguments.thirdArgument.value);
				} else {
					predicate.setRangeEnd(null);
				}
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				
				if (predicateMapper == equalsPredicateMapper) {
					Argument firstArgument = new Argument();
					HashSet<Long> hset = new HashSet<Long>();
					Long value = getLongValue(expression.firstOperand.value);
					hset.add(value);
					firstArgument.value = hset;
					firstArgument.type = HashSet.class;
					arguments.firstArgument = firstArgument;
					Argument unusedParam = new Argument();
					unusedParam.setNull = true;
					unusedParam.value = null;
					unusedParam.type = Long.class;
					arguments.secondArgument = unusedParam;
					arguments.thirdArgument = unusedParam;
				} else if (predicateMapper == betweenPredicateMapper) {
					Argument firstArgument = new Argument();
					HashSet<Long> hset = new HashSet<Long>();
					firstArgument.value = hset;
					firstArgument.type = HashSet.class;
					arguments.firstArgument = firstArgument;
					Argument secondArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
					secondArgument.value = getLongValue(secondArgument.value);
					arguments.secondArgument = secondArgument;
					Argument thirdArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
					thirdArgument.value = getLongValue(thirdArgument.value);
					arguments.thirdArgument = thirdArgument;
				} else if (predicateMapper == inPredicateMapper) {
					Argument firstArgument = new Argument();
					HashSet<Long> hset = new HashSet<Long>();
					if (expression.firstOperand != null) {
						Long value = getLongValue(expression.firstOperand.value);
						hset.add(value);
					}
					if (expression.secondOperand != null) {
						Long value = getLongValue(expression.secondOperand.value);
						hset.add(value);
					}
					if (expression.thirdOperand != null) {
						Long value = getLongValue(expression.thirdOperand.value);
						hset.add(value);
					}
					if (expression.extendedOperands != null) {
						for (Operand operand : expression.extendedOperands) {
							Long value = getLongValue(operand.value);
							hset.add(value);
						}
					}
					firstArgument.value = hset;
					firstArgument.type = HashSet.class;
					arguments.firstArgument = firstArgument;
					Argument unusedArgument = new Argument();
					unusedArgument.setNull = true;
					unusedArgument.value = null;
					unusedArgument.type = Long.class;
					arguments.secondArgument = unusedArgument;
					arguments.thirdArgument = unusedArgument;
				}
				return createPredicate(arguments);
			}
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		HeaderPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				HeaderPredicate predicate = new HeaderPredicate();
				predicate.setHeaderKeyword((String) arguments.firstArgument.value);
				predicate.setHeaderVallue((String) arguments.secondArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				if (expression.primary.pathExpressionAttributeNames == null) {
					throw new QLException("Not Supported");
				}
				
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = new Argument();
				firstArgument.name = "headers key";
				firstArgument.value = expression.primary.pathExpressionAttributeNames.get(1);
				firstArgument.type = String.class;
				arguments.firstArgument = firstArgument;
				
				Argument secondArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.secondArgument = secondArgument;
				
				return createPredicate(arguments);
			}
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		DeliveredTimePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				DeliveredTimePredicate predicate = new DeliveredTimePredicate();
				Date d = (Date) arguments.firstArgument.value;
				DateTime dt = new DateTime();
				dt.setTimestampTime(d.getTime());
				predicate.setDeliveredTime((DateTime) dt);
				predicate.setComparator((TimeComparator) arguments.secondArgument.value);
				return predicate;
			}
			
			Predicate createBetweenPredicateInternal(SimpleExpression expression) {
                ArgumentCollection sinceArguments = new ArgumentCollection();
                Argument firstSinceArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                sinceArguments.firstArgument = firstSinceArgument;
                Argument secondSinceArgument = new Argument();
                secondSinceArgument.value = TimeComparator.SINCE;
                secondSinceArgument.type = TimeComparator.class;
                sinceArguments.secondArgument = secondSinceArgument;
                Predicate since = createPredicate(sinceArguments);
                
                ArgumentCollection beforeArguments = new ArgumentCollection();
                Argument firstBeforeArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
                beforeArguments.firstArgument = firstBeforeArgument;
                Argument secondBeforeArgument = new Argument();
                secondBeforeArgument.value = TimeComparator.BEFORE;
                secondBeforeArgument.type = TimeComparator.class;
                beforeArguments.secondArgument = secondBeforeArgument;
                Predicate before = createPredicate(beforeArguments);
                
                MatchAllPredicate predicate = new MatchAllPredicate();
                predicate.getPredicates().add(since);
                predicate.getPredicates().add(before);
                return predicate;
            }
            
            Predicate createGreaterPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                Argument secondArgument = new Argument();
                if (expression.primary.isLeftNode) { // the attribute is on the right of ">" operator
                    secondArgument.value = TimeComparator.SINCE;
                } else {
                    secondArgument.value = TimeComparator.BEFORE;
                }
                secondArgument.type = TimeComparator.class;
                arguments.secondArgument = secondArgument;
                return createPredicate(arguments);
            }

            Predicate createLessPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                Argument secondArgument = new Argument();
                if (expression.primary.isLeftNode) { // the attribute is on the right of "<" operator
                    secondArgument.value = TimeComparator.BEFORE;
                } else {
                    secondArgument.value = TimeComparator.SINCE;
                }
                secondArgument.type = TimeComparator.class;
                arguments.secondArgument = secondArgument;
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
			
		SentTimePredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				SentTimePredicate predicate = new SentTimePredicate();
				Date sentTime = (Date) arguments.firstArgument.value;
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(sentTime);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					predicate.setDate(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
				predicate.setComparator((TimeComparator) arguments.secondArgument.value);
				return predicate;
			}
			
			Predicate createBetweenPredicateInternal(SimpleExpression expression) {
                ArgumentCollection sinceArguments = new ArgumentCollection();
                Argument firstSinceArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                sinceArguments.firstArgument = firstSinceArgument;
                Argument secondSinceArgument = new Argument();
                secondSinceArgument.value = TimeComparator.SINCE;
                secondSinceArgument.type = TimeComparator.class;
                sinceArguments.secondArgument = secondSinceArgument;
                Predicate since = createPredicate(sinceArguments);
                
                ArgumentCollection beforeArguments = new ArgumentCollection();
                Argument firstBeforeArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
                beforeArguments.firstArgument = firstBeforeArgument;
                Argument secondBeforeArgument = new Argument();
                secondBeforeArgument.value = TimeComparator.BEFORE;
                secondBeforeArgument.type = TimeComparator.class;
                beforeArguments.secondArgument = secondBeforeArgument;
                Predicate before = createPredicate(beforeArguments);
                
                MatchAllPredicate predicate = new MatchAllPredicate();
                predicate.getPredicates().add(since);
                predicate.getPredicates().add(before);
                return predicate;
            }
            
            Predicate createGreaterPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                Argument secondArgument = new Argument();
                if (expression.primary.isLeftNode) { // the attribute is on the right of ">" operator
                    secondArgument.value = TimeComparator.SINCE;
                } else {
                    secondArgument.value = TimeComparator.BEFORE;
                }
                secondArgument.type = TimeComparator.class;
                arguments.secondArgument = secondArgument;
                return createPredicate(arguments);
            }

            Predicate createLessPredicateInternal(SimpleExpression expression) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                Argument secondArgument = new Argument();
                if (expression.primary.isLeftNode) { // the attribute is on the right of "<" operator
                    secondArgument.value = TimeComparator.BEFORE;
                } else {
                    secondArgument.value = TimeComparator.SINCE;
                }
                secondArgument.type = TimeComparator.class;
                arguments.secondArgument = secondArgument;
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
			
		ContentPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				ContentPredicate predicate = new ContentPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setKeyword((String) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		FromPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				FromPredicate predicate = new FromPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setFrom((Participant) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		ToPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				ToPredicate predicate = new ToPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setTo((Participant) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		CcPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				CcPredicate predicate = new CcPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setCC((Participant) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	{predicateConstructorMapper.put(
			
		BccPredicate.class, new PredicateConstructor() {
			
			public Predicate createPredicate(ArgumentCollection arguments) {
				BccPredicate predicate = new BccPredicate();
				Argument firstArgument = arguments.firstArgument;
				predicate.setBCC((Participant) firstArgument.value);
				return predicate;
			}
			
			public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
				ArgumentCollection arguments = new ArgumentCollection();
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
				return createPredicate(arguments);
			}	
		}
		
	);}
	
	public boolean hasContainerAsArgumentOfListMethod() {
		return true;
	}

}
