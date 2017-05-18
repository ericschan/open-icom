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
package icom.jpa.csi.dao;

import icom.info.AbstractBeanInfo;
import icom.info.BeanHandler;
import icom.info.BeanInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.PropertyDefinitionInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import oracle.csi.AttributeDefinition;
import oracle.csi.AttributeDefinitionHandle;
import oracle.csi.CollabId;
import oracle.csi.CollabProperties;
import oracle.csi.CollabProperty;
import oracle.csi.CsiRuntimeException;
import oracle.csi.IdentifiableHandle;
import oracle.csi.PropertyType;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttributeDefinitionListUpdater;
import oracle.csi.updaters.AttributeDefinitionUpdater;
import oracle.csi.updaters.CollabPropertiesUpdater;

public class AttributeDefinitionDAO extends CsiIdentifiableDAO {
	
	static AttributeDefinitionDAO singleton = new AttributeDefinitionDAO();
	
	public static AttributeDefinitionDAO getInstance() {
		return singleton;
	}

	protected AttributeDefinitionDAO() {
		
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return AttributeDefinitionHandle.class;
	}
	
	static HashMap<String, String> csiToPojoPropertyTypeNameMap;
	static HashMap<String, String> pojoToCsiPropertyTypeNameMap;
	
	{
		csiToPojoPropertyTypeNameMap = new HashMap<String, String>();
		pojoToCsiPropertyTypeNameMap = new HashMap<String, String>();
		csiToPojoPropertyTypeNameMap.put(PropertyType.STRING.name(), "String");
		csiToPojoPropertyTypeNameMap.put(PropertyType.BOOLEAN.name(), "Boolean");
		csiToPojoPropertyTypeNameMap.put(PropertyType.DOUBLE.name(), "Decimal");
		csiToPojoPropertyTypeNameMap.put(PropertyType.INTEGER.name(), "Integer");
		csiToPojoPropertyTypeNameMap.put(PropertyType.DATETIME.name(), "DateTime");
		csiToPojoPropertyTypeNameMap.put(PropertyType.COLLABID.name(), "ID");
		// TODO need to support URI and HTML
		// TODO need to support Any
		/*
		csiToPojoPropertyTypeNameMap.put(PropertyType.BYTEARRAY.name(), null);
		csiToPojoPropertyTypeNameMap.put(PropertyType.BYTE.name(), null);
		csiToPojoPropertyTypeNameMap.put(PropertyType.CHARACTER.name(), null);
		csiToPojoPropertyTypeNameMap.put(PropertyType.DATE.name(), null);
		csiToPojoPropertyTypeNameMap.put(PropertyType.FLOAT.name(), null);
		csiToPojoPropertyTypeNameMap.put(PropertyType.LONG.name(), null);
		csiToPojoPropertyTypeNameMap.put(PropertyType.PERSON.name(), null);
		csiToPojoPropertyTypeNameMap.put(PropertyType.TIME.name(), null);
		*/
		for (String key : csiToPojoPropertyTypeNameMap.keySet()) {
			pojoToCsiPropertyTypeNameMap.put(csiToPojoPropertyTypeNameMap.get(key), key);
		}
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiIdentifiable, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		PersistenceContext context = obj.getPersistenceContext();
		AttributeDefinition csiAttrDefn = (AttributeDefinition) csiIdentifiable;

		try {
			assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.name.name(),
					csiAttrDefn.getName());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.description.name(), csiAttrDefn.getDescription());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			PropertyType type = csiAttrDefn.getType();
			String pojoPropertyTypeName = csiToPojoPropertyTypeNameMap.get(type.name());
			if (pojoPropertyTypeName != null) {
				String beehivePackageName = BeehiveBeanEnumeration.BeehivePropertyTypeEnum.getPackageName();
				assignEnumConstant(pojoIdentifiable, PropertyDefinitionInfo.Attributes.propertyType.name(), beehivePackageName, BeehiveBeanEnumeration.BeehivePropertyTypeEnum.name(), pojoPropertyTypeName);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			Collection<Object> pojoPropertyChoices = new Vector<Object>();
			CollabProperties allowedValues = csiAttrDefn.getAllowedValues();
			if (allowedValues != null) {
				Collection<CollabProperty> collabPropertyCollection = allowedValues.values();
				for (CollabProperty collabProperty : collabPropertyCollection) {
					ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(obj, PropertyDefinitionInfo.Attributes.choices.name());
					PropertyChoiceTypeToCollabPropertyDAO.getInstance().copyObjectState(propertyObj, collabProperty, proj);
					pojoPropertyChoices.add(propertyObj.getPojoObject());
				}
			}
			
			Collection<Object> previousPropertyChoices = getObjectCollection(pojoIdentifiable, PropertyDefinitionInfo.Attributes.choices.name());
			if (previousPropertyChoices != null) {
				for (Object pojoPropertyChoice : previousPropertyChoices) {
					if (pojoPropertyChoice instanceof Persistent) {
						BeanInfo beanInfo = context.getBeanInfo(pojoPropertyChoice);
						ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoPropertyChoice, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
				}
			}
			
			assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.choices.name(), pojoPropertyChoices);
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			Object value = csiAttrDefn.getDefaultValue();
			assignPropertyValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.defaultValue.name(), value);
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.minValue.name(), csiAttrDefn.getMinimumValue());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.maxValue.name(), csiAttrDefn.getMaximumValue());
		} catch (CsiRuntimeException ex) {
			// ignore
		}
/*
		try {
			assignAttributeValue(pojoIdentifiable,
					AttributeDefinitionInfo.Attributes.minimumValueInclusive.name(), new Boolean(
							csiAttrDefn.isMinimumValueInclusive()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable,
					AttributeDefinitionInfo.Attributes.maximumValueInclusive.name(), new Boolean(
							csiAttrDefn.isMaximumValueInclusive()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}
*/
		try {
			if (csiAttrDefn.isAggregate()) {
				assignEnumConstant(pojoIdentifiable, PropertyDefinitionInfo.Attributes.cardinality.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.CardinalityEnum.name(), "Multi");
			} else {
				assignEnumConstant(pojoIdentifiable, PropertyDefinitionInfo.Attributes.cardinality.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.CardinalityEnum.name(), "Single");
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable,
					PropertyDefinitionInfo.Attributes.queryable.name(), new Boolean(csiAttrDefn.isSearchable()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}

	}

	private ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(ManagedObjectProxy parent, String parentAttributeName) {
		return getNonIdentifiableDependentProxy(parent.getPersistenceContext(), IcomBeanEnumeration.PropertyChoiceType.name(), parent, parentAttributeName);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();		
		AttributeDefinitionUpdater attrDefnUpdater = (AttributeDefinitionUpdater) context.getChildUpdater();
		
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.description.name());
			attrDefnUpdater.setDescription(description);
		}
		
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.choices.name())) {
			Collection<Object> pojoProperties = getObjectCollection(pojoIdentifiable, PropertyDefinitionInfo.Attributes.choices.name());
			if (pojoProperties != null) {
				CollabPropertiesUpdater collabPropertiesUpdater = attrDefnUpdater.getAllowedValuesUpdater();
				for (Object o : pojoProperties) {
					PropertyChoiceTypeToCollabPropertyDAO.getInstance().updateObjectState(o, collabPropertiesUpdater, PropertyChoiceTypeToCollabPropertyDAO.Operand.ADD);
				}
			}
		}
		
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.defaultValue.name())) {
			Object defaultValue = getPropertyValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.defaultValue.name());
			attrDefnUpdater.setDefaultValue(defaultValue);
		}
/*
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.minValue.name()) || isChanged(obj, PropertyDefinitionInfo.Attributes.minimumValueInclusive.name())) {
			Object minimumValue = getPropertyValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.minValue.name());
			Boolean minimumValueInclusive = (Boolean) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.minimumValueInclusive.name());
			if (minimumValueInclusive != null) {
				attrDefnUpdater.setMinimumValue(minimumValue, minimumValueInclusive);
			} else {
				attrDefnUpdater.setMinimumValue(minimumValue, false);
			}
		}
		
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.maxValue.name()) || isChanged(obj, PropertyDefinitionInfo.Attributes.maximumValueInclusive.name())) {
			Object maximumValue = getPropertyValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.maxValue.name());
			Boolean maximumValueInclusive = (Boolean) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.maximumValueInclusive.name());
			if (maximumValueInclusive != null) {
				attrDefnUpdater.setMaximumValue(maximumValue, maximumValueInclusive);
			} else {
				attrDefnUpdater.setMaximumValue(maximumValue, false);
			}
		}
*/	
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.queryable.name())) {
			Boolean searchable = (Boolean) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.queryable.name());
			if (searchable != null) {
				attrDefnUpdater.setSearchable(searchable);
			} else {
				attrDefnUpdater.setSearchable(false);
			}
		}
		
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.cardinality.name())) {
			String cardinalityName = getEnumName(pojoIdentifiable, PropertyDefinitionInfo.Attributes.cardinality.name());
			if (cardinalityName.equalsIgnoreCase("Multi")) {
				attrDefnUpdater.setAggregate(true);
			} else {
				attrDefnUpdater.setAggregate(false);
			}
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		AttributeDefinitionListUpdater attrDefnListUpdater = (AttributeDefinitionListUpdater) context.getUpdater();
		CollabId id = getCollabId(obj.getObjectId());
		AttributeDefinitionHandle attrDefHandle = (AttributeDefinitionHandle) EntityUtils.getInstance().createHandle(id);
		AttributeDefinitionUpdater attrDefnUpdater = attrDefnListUpdater.updateAttributeDefinition(attrDefHandle);
		context.setChildUpdater(attrDefnUpdater);
		
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.name.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.name.name());
			attrDefnUpdater.setName(name);
		}
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.propertyType.name())) {
			String typeName = getEnumName(pojoIdentifiable, PropertyDefinitionInfo.Attributes.propertyType.name());
			String csiPropertyTypeName = pojoToCsiPropertyTypeNameMap.get(typeName);
			if (csiPropertyTypeName != null) {
				PropertyType csiPropertyType = PropertyType.valueOf(csiPropertyTypeName);
				attrDefnUpdater.setType(csiPropertyType);
			}
		}
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.name.name());
		String typeName = getEnumName(pojoIdentifiable, PropertyDefinitionInfo.Attributes.propertyType.name());
		String csiPropertyTypeName = pojoToCsiPropertyTypeNameMap.get(typeName);
		if (csiPropertyTypeName != null) {
			PropertyType csiPropertyType = PropertyType.valueOf(csiPropertyTypeName);
			AttributeDefinitionListUpdater attrDefnListUpdater = (AttributeDefinitionListUpdater) context.getUpdater();
			AttributeDefinitionUpdater attrDefnUpdater = attrDefnListUpdater.addAttributeDefinition(name, csiPropertyType);
			context.setChildUpdater(attrDefnUpdater);
		}
		updateNewOrOldObjectState(obj, context);
	}
	
	public void delete(ManagedIdentifiableProxy obj, DAOContext context) {
		CollabId id = getCollabId(obj.getObjectId());
		AttributeDefinitionHandle attributeDefinitionHandle = (AttributeDefinitionHandle) EntityUtils.getInstance().createHandle(id);
		AttributeDefinitionListUpdater attributeDefinitionListUpdater = (AttributeDefinitionListUpdater) context.getUpdater();
		attributeDefinitionListUpdater.removeAttributeDefinition(attributeDefinitionHandle);
	}

}
