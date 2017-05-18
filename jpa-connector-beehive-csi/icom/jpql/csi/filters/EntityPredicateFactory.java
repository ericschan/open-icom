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
import icom.jpql.stack.Operand;
import icom.jpql.stack.OrderByItem;
import icom.jpql.stack.PrimaryOperand;
import icom.jpql.stack.SimpleExpression;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import oracle.csi.Accessor;
import oracle.csi.Actor;
import oracle.csi.Addressable;
import oracle.csi.CollabId;
import oracle.csi.DateTime;
import oracle.csi.EmailParticipant;
import oracle.csi.Entity;
import oracle.csi.EntityAddress;
import oracle.csi.IdFormatException;
import oracle.csi.Identifiable;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Label;
import oracle.csi.Participant;
import oracle.csi.controls.Control;
import oracle.csi.controls.DateTimeFactory;
import oracle.csi.controls.EntityUtils;
import oracle.csi.filters.EntityListFilter;
import oracle.csi.filters.ListFilter;
import oracle.csi.filters.Predicate;
import oracle.csi.filters.SortCriteria;
import oracle.csi.projections.Projection;

public abstract class EntityPredicateFactory extends IdentifiablePredicatefactory {
	
	{
		attributeTypeMapper.put("createdBy".toLowerCase(), Actor.class);
		attributeTypeMapper.put("lastModifiedBy".toLowerCase(), Actor.class);
		attributeTypeMapper.put("owner".toLowerCase(), Accessor.class);
		attributeTypeMapper.put("parent".toLowerCase(), Entity.class);
		attributeTypeMapper.put("attachedMarkers".toLowerCase(), Label.class);
		attributeTypeMapper.put("name".toLowerCase(), String.class);
		attributeTypeMapper.put("creationDate".toLowerCase(), Date.class);
		attributeTypeMapper.put("lastModificationDate".toLowerCase(), Date.class);
	}
	
	{
		equalsPredicateMethodMapper.put("createdBy".toLowerCase(), "createCreatedByPredicate");
		equalsPredicateMethodMapper.put("lastModifiedBy".toLowerCase(), "createModifiedByPredicate");
		equalsPredicateMethodMapper.put("owner".toLowerCase(), "createOwnerPredicate");
		equalsPredicateMethodMapper.put("attachedMarkers".toLowerCase(), "createLabelPredicate");
		equalsPredicateMethodMapper.put("name".toLowerCase(), "createNamePredicate");
	}
	
	{
		likePredicateMethodMapper.put("name".toLowerCase(), "createNamePredicate");
	}
	
	{
		betweenPredicateMethodMapper.put("creationDate".toLowerCase(), "createCreatedOnPredicate");
		betweenPredicateMethodMapper.put("lastModificationDate".toLowerCase(), "createModifiedOnPredicate");
	}
	
	{
		greaterThanOrEqualPredicateMethodMapper.put("creationDate".toLowerCase(), "createCreatedOnPredicate");
		greaterThanOrEqualPredicateMethodMapper.put("lastModificationDate".toLowerCase(), "createModifiedOnPredicate");
	}
	
	{
		lessThanOrEqualPredicateMethodMapper.put("creationDate".toLowerCase(), "createCreatedOnPredicate");
		lessThanOrEqualPredicateMethodMapper.put("lastModificationDate".toLowerCase(), "createModifiedOnPredicate");
	}
	
	{
		sortCriteriaMethodMapper.put("name".toLowerCase(), "createNameSortCriteria");
		sortCriteriaMethodMapper.put("lastModificationDate".toLowerCase(), "createModifiedOnSortCriteria");
	}
	
	abstract public String resolveClassName();
	
	public Object resolveAttributeType(String attribute) {
		return attributeTypeMapper.get(attribute.toLowerCase());
	}
	
	public Method getPredicateMethod(Object listFilter, String methodName) {
		return ListFilterHelper.getInstance().getPredicateMethod(listFilter, methodName);
	}
	
	public Method getEqualsPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = equalsPredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the equals predicate: attribName=" + attribName);
    }
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getLikePredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = likePredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the like predicate: attribName=" + attribName);
    }
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getBetweenPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = betweenPredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the between predicate: attribName=" + attribName);
    }
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getGreaterThanOrEqualsPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = greaterThanOrEqualPredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the greater than or equals predicate: attribName=" + attribName);
    }		
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getLessThanOrEqualsPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
	  String methodName = lessThanOrEqualPredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the less than or equals predicate: attribName=" + attribName);
    }
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getGreaterThanPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = greaterThanPredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the greater than predicate: attribName=" + attribName);
    }
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getLessThanPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = lessThanPredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the less than predicate: attribName=" + attribName);
    }		
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getMemberOfPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = collectionMemberPredicateMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the member of predicate: attribName=" + attribName);
    }
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Method getInPredicateMethod(Object listFilter, SimpleExpression expression) {
	  String attribName = expression.primary.simpleAttributeName.toLowerCase();
		String methodName = inPredicateMethodMapper.get(attribName);
		if (methodName == null) {
		  throw new QLException("failed to get the in predicate: attribName=" + attribName);
		}
		return getPredicateMethod(listFilter, methodName);
	}
	
	public Predicate createPredicate(Object listFilter, Method method, ArgumentCollection arguments) {
		int argCount = 0;
		if (arguments.firstArgument != null) {
			if (arguments.secondArgument != null) {
				if (arguments.thirdArgument != null) {
					if (arguments.extendedArguments != null) {
						argCount = 3 + arguments.extendedArguments.size();
					} else {
						argCount = 3;
					}
				} else {
					argCount = 2;
				}
			} else {
				argCount = 1;
			}
		}
		Object[] args = new Object[argCount];
		if (arguments.firstArgument != null && ! arguments.firstArgument.setNull) {
			args[0] = arguments.firstArgument.value;
		}
		if (arguments.secondArgument != null && ! arguments.secondArgument.setNull) {
			args[1] = arguments.secondArgument.value;
		}
		if (arguments.thirdArgument != null && ! arguments.thirdArgument.setNull) {
			args[2] = arguments.secondArgument.value;
		}
		if (arguments.extendedArguments != null) {
			int argIndex = 3;
			for (Argument param : arguments.extendedArguments) {
				args[argIndex] = param.value;
				argIndex++;
			}
		}
		Predicate predicate = ListFilterHelper.getInstance().createPredicate(method, listFilter, args);
		return predicate;
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
	
	public Predicate createEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Method method = getEqualsPredicateMethod(listFilter, expression);
		ArgumentCollection arguments = new ArgumentCollection();
		Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
		arguments.firstArgument = firstArgument;
		Predicate predicate = createPredicate(listFilter, method, arguments);
		return predicate;
	}
	
	final public Predicate createNotEqualsPredicate(Object listFilter, SimpleExpression expression) {
		Predicate predicate = createEqualsPredicate(listFilter, expression);
		predicate = createNotPredicate(listFilter, predicate);
		return predicate;
	}
	
	public Predicate createLikePredicate(Object listFilter, SimpleExpression expression) {
		Method method = getLikePredicateMethod(listFilter, expression);
		ArgumentCollection arguments = new ArgumentCollection();
		Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
		arguments.firstArgument = firstArgument;
		Predicate predicate = createPredicate(listFilter, method, arguments);
		return predicate;
	}
	
	public Predicate createBetweenPredicate(Object listFilter, SimpleExpression expression) {
		Method method = getBetweenPredicateMethod(listFilter, expression);
		ArgumentCollection arguments = new ArgumentCollection();
		Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
		arguments.firstArgument = firstArgument;
		Argument secondArgument = convertOperandToArgument(expression.primary, expression.secondOperand);
		arguments.secondArgument = secondArgument;
		Predicate predicate = createPredicate(listFilter, method, arguments);
		return predicate;
	}
	
	public Predicate createGreaterThanOrEqualsPredicate(Object listFilter, SimpleExpression expression) {
		if (! ("CreatedOn".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			   "ModifiedOn".equalsIgnoreCase(expression.primary.simpleAttributeName))) {
			throw new QLException("Not supported");
		}
		Method method = getGreaterThanOrEqualsPredicateMethod(listFilter, expression);
		ArgumentCollection arguments = new ArgumentCollection();
		Argument unusedArgument = new Argument();
		unusedArgument.setNull = true;
		Argument argument = convertOperandToArgument(expression.primary, expression.firstOperand);
		if (expression.primary.isLeftNode) {	
			arguments.firstArgument = unusedArgument;
			arguments.secondArgument = argument;
		} else {
			arguments.firstArgument = argument;
			arguments.secondArgument = unusedArgument;
		}
		Predicate predicate = createPredicate(listFilter, method, arguments);
		return predicate;
	}
	
	public Predicate createLessThanOrEqualsPredicate(Object listFilter, SimpleExpression expression) {
		if (! ("CreatedOn".equalsIgnoreCase(expression.primary.simpleAttributeName) ||
			   "ModifiedOn".equalsIgnoreCase(expression.primary.simpleAttributeName))) {
			throw new QLException("Not supported");
		}
		Method method = getLessThanOrEqualsPredicateMethod(listFilter, expression);
		ArgumentCollection arguments = new ArgumentCollection();
		Argument unusedArgument = new Argument();
		unusedArgument.setNull = true;
		Argument argument = convertOperandToArgument(expression.primary, expression.firstOperand);
		if (expression.primary.isLeftNode) { // the attribute is on the right of "<=" operator
			arguments.firstArgument = argument;
			arguments.secondArgument = unusedArgument;
		} else {
			arguments.firstArgument = unusedArgument;
			arguments.secondArgument = argument;
		}
		Predicate predicate = createPredicate(listFilter, method, arguments);
		return predicate;
	}
	
	public Predicate createGreaterThanPredicate(Object listFilter, SimpleExpression expression) {
		throw new QLException("Not supported");
	}
	
	public Predicate createLessThanPredicate(Object listFilter, SimpleExpression expression) {
		throw new QLException("Not supported");
	}
	
	public Predicate createMemberOfPredicate(Object listFilter, SimpleExpression expression) {
		Method method = getMemberOfPredicateMethod(listFilter, expression);
		ArgumentCollection arguments = new ArgumentCollection();
		Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
		arguments.firstArgument = firstArgument;
		Predicate predicate = createPredicate(listFilter, method, arguments);
		return predicate;
	}
	
	public Predicate createInPredicate(Object listFilter, SimpleExpression expression) {
		throw new QLException("Not supported");
	}
	
	public Predicate createIsConditionPredicate(Object listFilter, SimpleExpression expression) {
		throw new QLException("Not supported");
	}
	
	public Predicate createNotPredicate(Object listFilter, Predicate predicate) {
		Class<? extends Object> cl = listFilter.getClass();
		Method method = null;
		try {
			Class<?>[] args = new Class[1];
			args[0] = Predicate.class;
			method = cl.getMethod("createNotPredicate", args);
		} catch (NoSuchMethodException ex) {
			throw new QLException("illegal access exception of method", ex);
		}
		Object[] args = new Object[1];
		args[0] = predicate;
		Predicate notPredicate = ListFilterHelper.getInstance().createPredicate(method, listFilter, args);
		return notPredicate;
	}
	
	public Predicate createMatchAnyPredicate(Object listFilter, Set<Predicate> matchAnyPredicateList) {
		Class<? extends Object> cl = listFilter.getClass();
		Method method = null;
		try {
			Class<?>[] args = new Class[1];
			args[0] = Set.class;
			method = cl.getMethod("createMatchAnyPredicate", args);
		} catch (NoSuchMethodException ex) {
			throw new QLException("illegal access exception of method", ex);
		}
		Object[] args = new Object[1];
		args[0] = matchAnyPredicateList;
		Predicate matchAnyPredicate = ListFilterHelper.getInstance().createPredicate(method, listFilter, args);
		return matchAnyPredicate;
		
	}
	
	public Predicate createMatchAllPredicate(Object listFilter, Set<Predicate> matchAllPredicateList) {
		Class<? extends Object> cl = listFilter.getClass();
		Method method = null;
		try {
			Class<?>[] args = new Class[1];
			args[0] = Set.class;
			method = cl.getMethod("createMatchAllPredicate", args);
		} catch (NoSuchMethodException ex) {
			throw new QLException("illegal access exception of method", ex);
		}
		Object[] args = new Object[1];
		args[0] = matchAllPredicateList;
		Predicate matchAllPredicate = ListFilterHelper.getInstance().createPredicate(method, listFilter, args);
		return matchAllPredicate;
	}
	
	public Method getSortCriteriaMethod(Object listFilter, OrderByItem item) {
	  String attribName = item.simpleAttributeName.toLowerCase();
		String methodName = sortCriteriaMethodMapper.get(attribName);
    if (methodName == null) {
      throw new QLException("failed to get the sort criteria: attribName=" + attribName);
    }
		return ListFilterHelper.getInstance().getSortCriteriaMethod(listFilter, methodName);
	}
	
	public SortCriteria createSortCriteria(Object listFilter, OrderByItem item) {
		SortCriteria sortCriteria = null;
		Method method = getSortCriteriaMethod(listFilter, item);
		Object[] args = new Object[1];
		args[0] = new Boolean(item.ascending);
		sortCriteria = ListFilterHelper.getInstance().createSortCriteria(method, listFilter, args);
		return sortCriteria;
	}
	
	public SortCriteria createSortCriteria(Object listFilter, List<SortCriteria> compoundSortCriteriaList) {
		Class<? extends Object> cl = listFilter.getClass();
		Method method = null;
		try {
			Class<?>[] args = new Class[2];
			args[0] = List.class;
			args[1] = boolean.class;
			method = cl.getMethod("createCompoundSortCriteria", args);
		} catch (NoSuchMethodException ex) {
			throw new QLException("illegal access exception of method", ex);
		}
		Object[] args = new Object[2];
		args[0] = compoundSortCriteriaList;
		args[1] = new Boolean(true);
		SortCriteria sortCriteria = ListFilterHelper.getInstance().createSortCriteria(method, listFilter, args);
		return sortCriteria;
		
	}
	
	public void setPredicateInListFilter(Predicate predicate, EntityListFilter<Entity> listFilter) {
		listFilter.setPredicate(predicate);	
	}
	
	public void setSortCriteriaInListFilter(SortCriteria sortCriteria, EntityListFilter<Entity> listFilter) {
		listFilter.setSortCriteria(sortCriteria);
	}
	
	public void setMaxCountInListFilter(int limit, ListFilter listFilter) {
		listFilter.setCountLimit(limit);
	}
	
	public void setProjectionInListFilter(Projection projection, EntityListFilter<Entity> listFilter) {
		listFilter.setProjection(projection);
	}
	
	private Control getControl() {
		return ListFilterHelper.getInstance().getControl(controlClassName);
	}
	
	private Object getCsiFactory() {
		return ListFilterHelper.getInstance().getFactory(factoryClassName);
	}
	
	public EntityListFilter<Entity> getEntityListFilter() {
		Object factory = getCsiFactory();
		return ListFilterHelper.getInstance().getListFilter(factory, listFilterCreateMethodName);
	}
	
	private Method getListMethod(Control control, Class<?> args[]) {
		return ListFilterHelper.getInstance().getListMethod(control, args, listMethodName);
	}
	
	public Class<? extends Object>[] getMethodSignatureForListMethod(ListFilter listFilter, CsiQueryContext context) {
		Class<?>[] args = new Class[1];
		Class<?>[] listFilterFaces = listFilter.getClass().getInterfaces();
		for (Class<? extends Object> face : listFilterFaces) {
			if (ListFilter.class.isAssignableFrom(face)) {
				args[0] = face;
				break;
			}
		}
		if (args[0] == null) {
			throw new QLException("Not Supported");
		}
		
		return args;
	}
	
	public List<Object> invokeListMethod(ListFilter listFilter, CsiQueryContext context, Class<?> args[]) {
		Control control = getControl();
		if (control == null) {
			throw new QLException("Not Supported");
		}
		Method listMethod = getListMethod(control, args);
		List<Object> list = invokeListMethod(control, listMethod, listFilter, context);
		return list;
	}
	
	protected List<Object> invokeListMethod(Control control, Method listMethod, ListFilter listFilter, CsiQueryContext context) {
		Object[] args = new Object[1];
		args[0] = listFilter;
		List<Object> list = ListFilterHelper.getInstance().invokeListMethod(control, listMethod, args);
		return list;
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
		} else if (Identifiable.class.isAssignableFrom((Class<?>) type)) {
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
	
	static Locale convertStringToLocale(String localeName) {
		String[] localeParts = localeName.split("_");
		if (localeParts.length == 1) {
			return new Locale(localeParts[0]);
		} else if (localeParts.length == 2) {
			return new Locale(localeParts[0], localeParts[1]);
		} else if (localeParts.length == 3) {
			return new Locale(localeParts[0], localeParts[1], localeParts[2]);
		}
		return new Locale(localeName);
	}
	
	static public Object resolveAttributeValueWithType(Object value, Object type) {
		if (isEntityClass(type)) {
			if (value instanceof String) {
				String str = (String) value;
	    		CollabId collabId = CollabId.parseCollabId(str);
	    		IdentifiableHandle csiHandle = EntityUtils.getInstance().createHandle(collabId);
	    		return csiHandle; 
			} else if (value instanceof IdentifiableHandle) {
				return value;
			}
		} else if (isParticipantClass(type)) {
			if (value instanceof String) {
				String str = (String) value;
				Participant csiparticipant = null;
				try {
					CollabId collabId = CollabId.parseCollabId(str);
					Addressable csiAddressable = (Addressable) EntityUtils.getInstance().createEmptyEntity(collabId);
					EmailParticipant emParticipant = new EmailParticipant(csiAddressable);
					csiparticipant = emParticipant.toParticipant();
				} catch (IdFormatException ex1) {
					try {
						URI address = new URI(str);
						csiparticipant = EmailParticipant.createParticipant(address, null);
					} catch (URISyntaxException ex2) {
						throw new QLException("Not Supported", ex1);
					}
				}
				return csiparticipant;
			}
		} else if (isDateClass(type)) {
			if (value instanceof String) {
				String str = (String) value;
				try {
					Date dt = DateFormat.getDateTimeInstance().parse(str);
					return dt;
				} catch (ParseException ex) {
				}
			}
		} else if (isDateTimeClass(type)) {
			if (value instanceof String) {
				String str = (String) value;
				try {
					Date d = DateFormat.getDateTimeInstance().parse(str);
					DateTime dt = DateTimeFactory.getInstance().createDateTime(d, null, false, false);
					return dt;
				} catch (ParseException ex) {
				}
			}
		} else if (isLocaleClass(type)) {
			if (value instanceof String) {
				String str = (String) value;
				Locale locale = convertStringToLocale(str); 
				return locale;
			}
		}
		return value;
	}

}
