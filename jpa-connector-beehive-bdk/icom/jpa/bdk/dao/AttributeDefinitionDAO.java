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
package icom.jpa.bdk.dao;


import com.oracle.beehive.AssociativeArray;
import com.oracle.beehive.AssociativeArrayEntry;
import com.oracle.beehive.AttributeDefinition;
import com.oracle.beehive.AttributeDefinitionListUpdater;
import com.oracle.beehive.AttributeDefinitionUpdateParameter;
import com.oracle.beehive.AttributeDefinitionUpdater;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.CollabProperties;
import com.oracle.beehive.CollabPropertiesUpdater;
import com.oracle.beehive.CollabProperty;
import com.oracle.beehive.PropertyType;

import icom.info.BeanHandler;
import icom.info.IcomBeanEnumeration;
import icom.info.PropertyDefinitionInfo;
import icom.info.beehive.BeehiveBeanEnumeration;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.Projection;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class AttributeDefinitionDAO extends BdkIdentifiableDAO {
	
	static AttributeDefinitionDAO singleton = new AttributeDefinitionDAO();
	
	public static AttributeDefinitionDAO getInstance() {
		return singleton;
	}

	protected AttributeDefinitionDAO() {
		
	}

	public String getResourceType() {
		return "atdf";
	}
	
	static HashMap<String, String> bdkToPojoPropertyTypeNameMap;
	static HashMap<String, String> pojoToCsiPropertyTypeNameMap;
	
	{
		bdkToPojoPropertyTypeNameMap = new HashMap<String, String>();
		pojoToCsiPropertyTypeNameMap = new HashMap<String, String>();
		bdkToPojoPropertyTypeNameMap.put(PropertyType.STRING.name(), "String");
		bdkToPojoPropertyTypeNameMap.put(PropertyType.BOOLEAN.name(), "Boolean");
		bdkToPojoPropertyTypeNameMap.put(PropertyType.DOUBLE.name(), "Decimal");
		bdkToPojoPropertyTypeNameMap.put(PropertyType.INTEGER.name(), "Integer");
		bdkToPojoPropertyTypeNameMap.put(PropertyType.DATETIME.name(), "DateTime");
		bdkToPojoPropertyTypeNameMap.put(PropertyType.COLLABID.name(), "ID");
		// TODO need to support URI and HTML
		// TODO need to support Any
		/*
		bdkToPojoPropertyTypeNameMap.put(PropertyType.BYTEARRAY.name(), null);
		bdkToPojoPropertyTypeNameMap.put(PropertyType.BYTE.name(), null);
		bdkToPojoPropertyTypeNameMap.put(PropertyType.CHARACTER.name(), null);
		bdkToPojoPropertyTypeNameMap.put(PropertyType.DATE.name(), null);
		bdkToPojoPropertyTypeNameMap.put(PropertyType.FLOAT.name(), null);
		bdkToPojoPropertyTypeNameMap.put(PropertyType.LONG.name(), null);
		bdkToPojoPropertyTypeNameMap.put(PropertyType.PERSON.name(), null);
		bdkToPojoPropertyTypeNameMap.put(PropertyType.TIME.name(), null);
		*/
		for (String key : bdkToPojoPropertyTypeNameMap.keySet()) {
			pojoToCsiPropertyTypeNameMap.put(bdkToPojoPropertyTypeNameMap.get(key), key);
		}
	}

    public void copyObjectState(ManagedObjectProxy managedObj, Object bdkIdentifiable, Projection proj) {
        ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy)managedObj;
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();
        AttributeDefinition bdkAttrDefn = (AttributeDefinition)bdkIdentifiable;

        try {
            assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.name.name(),
                                 bdkAttrDefn.getName());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.description.name(),
                                 bdkAttrDefn.getDescription());
        } catch (Exception ex) {
            // ignore
        }

        try {
            PropertyType type = bdkAttrDefn.getType();
            String pojoPropertyTypeName = bdkToPojoPropertyTypeNameMap.get(type.name());
            if (pojoPropertyTypeName != null) {
                String beehivePackageName = BeehiveBeanEnumeration.BeehivePropertyTypeEnum.getPackageName();
                assignEnumConstant(pojoIdentifiable, PropertyDefinitionInfo.Attributes.propertyType.name(),
                                   beehivePackageName, BeehiveBeanEnumeration.BeehivePropertyTypeEnum.name(),
                                   pojoPropertyTypeName);
            }
        } catch (Exception ex) {
            // ignore
        }

        try {
            Vector<Object> v = new Vector<Object>();
            CollabProperties allowedValues = bdkAttrDefn.getAllowedValues();
            if (allowedValues != null) {
                AssociativeArray properties = allowedValues.getMap();
                List<AssociativeArrayEntry> entries = properties.getEntries();
                for (AssociativeArrayEntry entry : entries) {
                    CollabProperty bdkCollabProperty = (CollabProperty)entry.getValue();
                    v.add(bdkCollabProperty);
                }
            }
            marshallAssignEmbeddableObjects(obj, PropertyDefinitionInfo.Attributes.choices.name(), v,
                                                      Vector.class, proj);

        } catch (Exception ex) {
            // ignore
        }

        try {
            Object value = bdkAttrDefn.getDefaultValue();
            assignPropertyValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.defaultValue.name(), value);
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.minValue.name(),
                                 bdkAttrDefn.getMinimumValue());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.maxValue.name(),
                                 bdkAttrDefn.getMaximumValue());
        } catch (Exception ex) {
            // ignore
        }
        
/*
        try {
                assignAttributeValue(pojoIdentifiable,
                                AttributeDefinitionInfo.Attributes.minimumValueInclusive.name(), new Boolean(
                                                bdkAttrDefn.isMinimumValueInclusive()));
        } catch (CsiRuntimeException ex) {
                // ignore
        }

        try {
                assignAttributeValue(pojoIdentifiable,
                                AttributeDefinitionInfo.Attributes.maximumValueInclusive.name(), new Boolean(
                                                bdkAttrDefn.isMaximumValueInclusive()));
        } catch (CsiRuntimeException ex) {
                // ignore
        }
*/
        
        try {
            if (bdkAttrDefn.isAggregate()) {
                assignEnumConstant(pojoIdentifiable, PropertyDefinitionInfo.Attributes.cardinality.name(),
                                   BeanHandler.getBeanPackageName(), IcomBeanEnumeration.CardinalityEnum.name(),
                                   "Multi");
            } else {
                assignEnumConstant(pojoIdentifiable, PropertyDefinitionInfo.Attributes.cardinality.name(),
                                   BeanHandler.getBeanPackageName(), IcomBeanEnumeration.CardinalityEnum.name(),
                                   "Single");
            }
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.queryable.name(),
                                 new Boolean(bdkAttrDefn.isSearchable()));
        } catch (Exception ex) {
            // ignore
        }

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
		BeeId attrDefId = getBeeId(obj.getObjectId().toString());
		AttributeDefinitionUpdateParameter param = new AttributeDefinitionUpdateParameter();
		param.setAttributeDefinitionHandle(attrDefId);
		AttributeDefinitionUpdater attrDefnUpdater = new AttributeDefinitionUpdater();
		context.setChildUpdater(attrDefnUpdater);
		
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.name.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.name.name());
			attrDefnUpdater.setName(name);
		}
		if (isChanged(obj, PropertyDefinitionInfo.Attributes.propertyType.name())) {
			String typeName = getEnumName(pojoIdentifiable, PropertyDefinitionInfo.Attributes.propertyType.name());
			String bdkPropertyTypeName = pojoToCsiPropertyTypeNameMap.get(typeName);
			if (bdkPropertyTypeName != null) {
				PropertyType bdkPropertyType = PropertyType.valueOf(bdkPropertyTypeName);
				attrDefnUpdater.setType(bdkPropertyType);
			}
		}
		
		param.setAttributeDefinitionUpdater(attrDefnUpdater);
		attrDefnListUpdater.getUpdates().add(param);
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, PropertyDefinitionInfo.Attributes.name.name());
		String typeName = getEnumName(pojoIdentifiable, PropertyDefinitionInfo.Attributes.propertyType.name());
		String bdkPropertyTypeName = pojoToCsiPropertyTypeNameMap.get(typeName);
		if (bdkPropertyTypeName != null) {
			PropertyType bdkPropertyType = PropertyType.valueOf(bdkPropertyTypeName);
			AttributeDefinitionListUpdater attrDefnListUpdater = (AttributeDefinitionListUpdater) context.getUpdater();
			AttributeDefinitionUpdater attrDefnUpdater = new AttributeDefinitionUpdater();
			attrDefnUpdater.setName(name);
			attrDefnUpdater.setType(bdkPropertyType);
			attrDefnListUpdater.getAdds().add(attrDefnUpdater);
			context.setChildUpdater(attrDefnUpdater);
		}
		updateNewOrOldObjectState(obj, context);
	}
	
	public void delete(ManagedIdentifiableProxy obj, DAOContext context) {
		BeeId attributeDefinitionId = getBeeId(obj.getObjectId().toString());
		AttributeDefinitionListUpdater attributeDefinitionListUpdater = (AttributeDefinitionListUpdater) context.getUpdater();
		attributeDefinitionListUpdater.getRemoves().add(attributeDefinitionId);
	}

}
