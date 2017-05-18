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
import icom.jpa.csi.dao.EmailMessageDAO;
import icom.jpa.csi.dao.EntityDAO;
import icom.jpql.csi.CsiQueryContext;
import icom.jpql.stack.Operand;
import icom.jpql.stack.SimpleExpression;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import oracle.csi.DateTime;
import oracle.csi.EmailMessageType;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Participant;
import oracle.csi.Priority;
import oracle.csi.controls.Control;
import oracle.csi.controls.DateTimeFactory;
import oracle.csi.filters.EmailMessageListFilter;
import oracle.csi.filters.ListFilter;
import oracle.csi.filters.Predicate;

public class EmailPredicateFactory extends ArtifactPredicateFactory {
	
	{
		attributeTypeMapper.put("Sender".toLowerCase(), Participant.class);
		attributeTypeMapper.put("ToReceivers".toLowerCase(), Participant.class);
		attributeTypeMapper.put("CCReceivers".toLowerCase(), Participant.class);
		attributeTypeMapper.put("BCCReceivers".toLowerCase(), Participant.class);
		attributeTypeMapper.put("size".toLowerCase(), Integer.class);
		attributeTypeMapper.put("deliveredTime".toLowerCase(), DateTime.class);
		attributeTypeMapper.put("sentTime".toLowerCase(), Date.class);
		attributeTypeMapper.put("receivedTime".toLowerCase(), Date.class);
		attributeTypeMapper.put("content".toLowerCase(), String.class);
		attributeTypeMapper.put("channel".toLowerCase(), EmailMessageType.class);
		attributeTypeMapper.put("priority".toLowerCase(), Priority.class);
		attributeTypeMapper.put("mode".toLowerCase(), Boolean.class);
		attributeTypeMapper.put("Uid".toLowerCase(), Long.class);
		attributeTypeMapper.put("mimeHeaders".toLowerCase(), Map.class);
	}
	
	{
		likePredicateMethodMapper.put("Content".toLowerCase(), "createContentPredicate");
	}
	
	{
		equalsPredicateMethodMapper.put("Sender".toLowerCase(), "createFromPredicate");	
		equalsPredicateMethodMapper.put("mode".toLowerCase(), "createIsModifiablePredicate");	
		equalsPredicateMethodMapper.put("channel".toLowerCase(), "createEmailMessageTypePredicate");
		equalsPredicateMethodMapper.put("Priority".toLowerCase(), "createPriorityPredicate");
		equalsPredicateMethodMapper.put("Uid".toLowerCase(), "createUIDPredicate");
		equalsPredicateMethodMapper.put("mimeHeaders".toLowerCase(), "createHeaderPredicate");	
	}
	
	{
		betweenPredicateMethodMapper.put("ReceivedTime".toLowerCase(), "createReceiveTimePredicate");
		betweenPredicateMethodMapper.put("Uid".toLowerCase(), "createUIDPredicate");
	}
	
	{
		greaterThanOrEqualPredicateMethodMapper.put("ReceivedTime".toLowerCase(), "createReceiveTimePredicate");
	}
	
	{
		lessThanOrEqualPredicateMethodMapper.put("ReceivedTime".toLowerCase(), "createReceiveTimePredicate");
	}

	{
		greaterThanPredicateMethodMapper.put("Size".toLowerCase(), "createSizePredicate");
		greaterThanPredicateMethodMapper.put("DeliveredTime".toLowerCase(), "createDeliveredTimePredicate");
		greaterThanPredicateMethodMapper.put("SentTime".toLowerCase(), "createSentTimePredicate");
		greaterThanPredicateMethodMapper.put("ReceivedTime".toLowerCase(), "createReceiveTimePredicate");
		greaterThanPredicateMethodMapper.put("Uid".toLowerCase(), "createRecentPredicate");
	}
	
	{
		lessThanPredicateMethodMapper.put("Size".toLowerCase(), "createSizePredicate");
		lessThanPredicateMethodMapper.put("DeliveredTime".toLowerCase(), "createDeliveredTimePredicate");
		lessThanPredicateMethodMapper.put("SentTime".toLowerCase(), "createSentTimePredicate");
		lessThanPredicateMethodMapper.put("ReceivedTime".toLowerCase(), "createReceiveTimePredicate");
		lessThanPredicateMethodMapper.put("Uid".toLowerCase(), "createRecentPredicate");
	}
	
	{
		collectionMemberPredicateMethodMapper.put("ToReceivers".toLowerCase(), "createToPredicate");
		collectionMemberPredicateMethodMapper.put("CCReceivers".toLowerCase(), "createCCPredicate");
		collectionMemberPredicateMethodMapper.put("BCCReceivers".toLowerCase(), "createBCCPredicate");
	}
	
	{
		inPredicateMethodMapper.put("Uid".toLowerCase(), "createUIDPredicate");
	}
	
	{
		sortCriteriaMethodMapper.put("ChangeStatus".toLowerCase(), "createChangeStatusSortCriteria");
		sortCriteriaMethodMapper.put("DeliveredTime".toLowerCase(), "createDeliveredTimeSortCriteria");
		sortCriteriaMethodMapper.put("channel".toLowerCase(), "createEmailMessageTypeSortCriteria");
		sortCriteriaMethodMapper.put("HasAttachment".toLowerCase(), "createHasAttachmentSortCriteria");
		sortCriteriaMethodMapper.put("Priority".toLowerCase(), "createPrioritySortCriteria");
		sortCriteriaMethodMapper.put("Sender".toLowerCase(), "createSenderSortCriteria");
		sortCriteriaMethodMapper.put("SentTime".toLowerCase(), "createSentTimeSortCriteria");
		sortCriteriaMethodMapper.put("Size".toLowerCase(), "createSizeSortCriteria");
		sortCriteriaMethodMapper.put("ToReceivers".toLowerCase(), "createToSortCriteria");
	}
	
	static EmailPredicateFactory singleton = new EmailPredicateFactory();
	
	public static EmailPredicateFactory getInstance() {
		return singleton;
	}
	
	public EmailPredicateFactory() {
		controlClassName = "oracle.csi.controls.EmailControl";
		factoryClassName = "oracle.csi.controls.EmailFactory";
		listFilterCreateMethodName = "createFilter";
		listMethodName = "listEmailMessages";
		listFilter = "oracle.csi.filters.EmailMessageListFilter";
	}
	
	public String resolveClassName() {
		return "EmailMessage";
	}
	
	public Predicate createEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("Channel".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Argument argument = new Argument();
			if (! (expression.firstOperand.value instanceof EmailMessageType)) {
				if (expression.firstOperand.value instanceof String) {
					String csiChannelTypeName = EmailMessageDAO.pojoToCsiChannelTypeNameMap.get((String) expression.firstOperand.value);
					argument.value = EmailMessageType.valueOf(csiChannelTypeName);
				} else {
					throw new QLException("Not Supported");
				}
			}
			argument.type = EmailMessageType.class;
			arguments.firstArgument = argument;
			Method method = getEqualsPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else if ("Priority".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Argument argument = new Argument();
			if (! (expression.firstOperand.value instanceof Priority)) {
				if (expression.firstOperand.value instanceof String) {
					String csiPriorityName = EntityDAO.pojoToCsiPriorityNameMap.get((String) expression.firstOperand.value);
					argument.value = Priority.valueOf(csiPriorityName);
				} else {
					throw new QLException("Not Supported");
				}
			}
			argument.type = Priority.class;
			arguments.firstArgument = argument;
			Method method = getEqualsPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else if ("Mode".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
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
			Method method = getEqualsPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else if ("Uid".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			Long value = getLongValue(expression.firstOperand.value);
			ArgumentCollection arguments = new ArgumentCollection();
			Argument firstArgument = new Argument();
			HashSet<Long> hset = new HashSet<Long>();
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
			Method method = getEqualsPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else if ("Headers".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
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
			Method method = getEqualsPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	public Predicate createBetweenPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("Uid".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Argument firstArgument = new Argument();
			HashSet<Long> hset = new HashSet<Long>();
			firstArgument.value = hset;  // use empty set when we check between range
			firstArgument.type = HashSet.class;
			arguments.firstArgument = firstArgument;
			Argument secondArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
			secondArgument.value = getLongValue(secondArgument.value);
			arguments.secondArgument = secondArgument;
			Argument thirdArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
			thirdArgument.value = getLongValue(thirdArgument.value);
			arguments.thirdArgument = thirdArgument;
			Method method = getBetweenPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createBetweenPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	private Long getLongValue(Object obj) {
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
	
	public Predicate createGreaterThanOrEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("ReceivedTime".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getGreaterThanOrEqualsPredicateMethod(listFilter, expression);
			Argument unusedArgument = new Argument();
			unusedArgument.setNull = true;
			if (expression.primary.isLeftNode) {
				arguments.firstArgument = unusedArgument;
				Argument secondArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.secondArgument = secondArgument;
			} else {
				arguments.secondArgument = unusedArgument;
			}
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createGreaterThanOrEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	public Predicate createLessThanOrEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("ReceivedTime".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getLessThanOrEqualsPredicateMethod(listFilter, expression);
			Argument unusedArgument = new Argument();
			unusedArgument.setNull = true;
			if (expression.primary.isLeftNode) { // the attribute is on the right of "<=" operator
				arguments.secondArgument = unusedArgument;
			} else {
				arguments.firstArgument = unusedArgument;
				Argument secondArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.secondArgument = secondArgument;
			}
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createGreaterThanOrEqualsPredicate(listFilter, expression);
		}
		return predicate;
	}
	
	public Predicate createGreaterThanPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("DeliveredTime".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			"SentTime".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getGreaterThanPredicateMethod(listFilter, expression);
			if ("DeliveredTime".equalsIgnoreCase(expression.primary.simpleAttributeName) &&
				expression.firstOperand.value instanceof Date) {
				Date d = (Date) expression.firstOperand.value;
				DateTime dt = DateTimeFactory.getInstance().createDateTime(d, null, false, false);
				Argument firstArgument = new Argument();
				firstArgument.value = dt;
				firstArgument.name = expression.firstOperand.name;
				firstArgument.type = expression.firstOperand.type;
				arguments.firstArgument = firstArgument;
			} else {
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
			}
			Argument secondArgument = new Argument();
			if (! expression.primary.isLeftNode) {
				secondArgument.value = EmailMessageListFilter.TimeComparator.BEFORE;
			} else {
				secondArgument.value = EmailMessageListFilter.TimeComparator.SINCE;
			}
			arguments.secondArgument = secondArgument;
			predicate = createPredicate(listFilter, method, arguments);
		} else if ("Size".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getGreaterThanPredicateMethod(listFilter, expression);
			Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
			arguments.firstArgument = firstArgument;
			Argument secondArgument = new Argument();
			if (! expression.primary.isLeftNode) {
				secondArgument.value = new Boolean(false);
			} else {
				secondArgument.value = new Boolean(true);
			}
			arguments.secondArgument = secondArgument;
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createGreaterThanPredicate(listFilter, expression);
		}
		return predicate ;
	}
	
	public Predicate createLessThanPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("DeliveredTime".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			"SentTime".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getLessThanPredicateMethod(listFilter, expression);
			if ("DeliveredTime".equalsIgnoreCase(expression.primary.simpleAttributeName) &&
				expression.firstOperand.value instanceof Date) {
				Date d = (Date) expression.firstOperand.value;
				DateTime dt = DateTimeFactory.getInstance().createDateTime(d, null, false, false);
				Argument firstArgument = new Argument();
				firstArgument.value = dt;
				firstArgument.name = expression.firstOperand.name;
				firstArgument.type = expression.firstOperand.type;
				arguments.firstArgument = firstArgument;
			} else {
				Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
				arguments.firstArgument = firstArgument;
			}
			Argument secondArgument = new Argument();
			if (! expression.primary.isLeftNode) {
				secondArgument.value = EmailMessageListFilter.TimeComparator.BEFORE;
			} else {
				secondArgument.value = EmailMessageListFilter.TimeComparator.SINCE;
			}
			arguments.secondArgument = secondArgument;
			predicate = createPredicate(listFilter, method, arguments);
		} else if ("Size".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
			Method method = getLessThanPredicateMethod(listFilter, expression);
			Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
			arguments.firstArgument = firstArgument;
			Argument secondArgument = new Argument();
			if (! expression.primary.isLeftNode) {
				secondArgument.value = new Boolean(true);
			} else {
				secondArgument.value = new Boolean(false);
			}
			arguments.secondArgument = secondArgument;
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createLessThanPredicate(listFilter, expression);
		}
		return predicate ;
	}
	
	public Predicate createInPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = null;
		if ("Uid".equalsIgnoreCase(expression.primary.simpleAttributeName)) {
			ArgumentCollection arguments = new ArgumentCollection();
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
			Method method = getInPredicateMethod(listFilter, expression);
			predicate = createPredicate(listFilter, method, arguments);
		} else {
			predicate = super.createInPredicate(listFilter, expression);
		}
		return predicate ;
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
		args[1] = EmailMessageListFilter.class;
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
