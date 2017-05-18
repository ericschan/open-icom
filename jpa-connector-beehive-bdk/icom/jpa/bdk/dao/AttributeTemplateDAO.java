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
import com.oracle.beehive.AttributeTemplate;
import com.oracle.beehive.AttributeTemplateCreateParameter;
import com.oracle.beehive.AttributeTemplateListUpdater;
import com.oracle.beehive.AttributeTemplateUpdateParameter;
import com.oracle.beehive.AttributeTemplateUpdater;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.CollabProperties;
import com.oracle.beehive.CollabPropertiesUpdater;
import com.oracle.beehive.CollabProperty;

import icom.info.PropertyDefinitionInfo;
import icom.info.beehive.BeehivePropertyTemplateInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;
import java.util.List;
import java.util.Vector;


public class AttributeTemplateDAO extends BdkIdentifiableDAO {
	
	static AttributeTemplateDAO singleton = new AttributeTemplateDAO();
	
	public static AttributeTemplateDAO getInstance() {
		return singleton;
	}
	
	protected AttributeTemplateDAO() {
		
	}

	public String getResourceType() {
		return "attp";
	}

    public void copyObjectState(ManagedObjectProxy managedObj, Object bdkIdentifiable, Projection proj) {
        PersistenceContext context = managedObj.getPersistenceContext();
        ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy)managedObj;
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();
        AttributeTemplate bdkAttrTemplate = (AttributeTemplate)bdkIdentifiable;

        try {
            AttributeDefinition bdkAttrDefn = bdkAttrTemplate.getAttributeDefinition();
            ManagedIdentifiableProxy attrDefnObj =
                getIdentifiableDependentProxy(context, bdkAttrDefn, obj, BeehivePropertyTemplateInfo.Attributes.propertyDefinition.name());
            if (attrDefnObj.isPooled(null)) {
                AttributeDefinitionDAO.getInstance().copyObjectState(attrDefnObj, bdkAttrDefn, proj);
                attrDefnObj.resetReady();
            }
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.propertyDefinition.name(),
                                 attrDefnObj.getPojoIdentifiable());
        } catch (Exception ex) {
            // ignore
        }

        try {
            Vector<Object> v = new Vector<Object>();
            CollabProperties allowedValues = bdkAttrTemplate.getAllowedValues();
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
            Object value = bdkAttrTemplate.getDefaultValue();
            assignPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.defaultValue.name(), value);
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.minValue.name(),
                                 bdkAttrTemplate.getMinimumValue());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.maxValue.name(),
                                 bdkAttrTemplate.getMaximumValue());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.minimumValueInclusive.name(),
                                 bdkAttrTemplate.isMinimumValueInclusive());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.maximumValueInclusive.name(),
                                 bdkAttrTemplate.isMaximumValueInclusive());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.required.name(),
                                 bdkAttrTemplate.isMandatory());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.finale.name(),
                                 bdkAttrTemplate.isFinal());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.forceDefault.name(),
                                 bdkAttrTemplate.isForceDefault());
        } catch (Exception ex) {
            // ignore
        }

        try {
            assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.prompted.name(),
                                 bdkAttrTemplate.isPrompted());
        } catch (Exception ex) {
            // ignore
        }
    }
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		AttributeTemplateUpdater attrTemplateUpdater = (AttributeTemplateUpdater) context.getChildUpdater();
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.choices.name())) {
			Collection<Object> pojoProperties = getObjectCollection(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.choices.name());
			if (pojoProperties != null) {
				CollabPropertiesUpdater collabPropertiesUpdater = new CollabPropertiesUpdater();
				attrTemplateUpdater.setAllowedValuesUpdater(collabPropertiesUpdater);
				for (Object o : pojoProperties) {
					CollabPropertyDAO.getInstance().updateObjectState(o, collabPropertiesUpdater, CollabPropertyDAO.Operand.ADD);
				}
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.defaultValue.name())) {
			Object defaultValue = getPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.defaultValue.name());
			attrTemplateUpdater.setDefaultValue(defaultValue);
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.minValue.name()) || 
				isChanged(obj, BeehivePropertyTemplateInfo.Attributes.minimumValueInclusive.name())) {
			Object minimumValue = getPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.minValue.name());
			Boolean minimumValueInclusive = (Boolean) getAttributeValue(pojoIdentifiable, 
					BeehivePropertyTemplateInfo.Attributes.minimumValueInclusive.name());
			if (minimumValueInclusive != null) {
				attrTemplateUpdater.setMinimumValue(minimumValue);
				attrTemplateUpdater.setMinimumValueInclusive(minimumValueInclusive);
			} else {
				attrTemplateUpdater.setMinimumValue(minimumValue);
				attrTemplateUpdater.setMinimumValueInclusive(false);
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.maxValue.name()) || 
				isChanged(obj, BeehivePropertyTemplateInfo.Attributes.maximumValueInclusive.name())) {
			Object maximumValue = getPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.maxValue.name());
			Boolean maximumValueInclusive = (Boolean) getAttributeValue(pojoIdentifiable, 
					BeehivePropertyTemplateInfo.Attributes.maximumValueInclusive.name());
			if (maximumValueInclusive != null) {
				attrTemplateUpdater.setMaximumValue(maximumValue);
				attrTemplateUpdater.setMaximumValueInclusive(maximumValueInclusive);
			} else {
				attrTemplateUpdater.setMaximumValue(maximumValue);
				attrTemplateUpdater.setMaximumValueInclusive(false);
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.required.name())) {
			Boolean mandatory = (Boolean) getAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.required.name());
			if (mandatory != null) {
				attrTemplateUpdater.setMandatory(mandatory);
			} else {
				attrTemplateUpdater.setMandatory(false);
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.finale.name())) {
			Boolean isFinal = (Boolean) getAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.finale.name());
			if (isFinal != null) {
				attrTemplateUpdater.setFinal(isFinal);
			} else {
				attrTemplateUpdater.setFinal(false);
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.prompted.name())) {
			Boolean prompted = (Boolean) getAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.prompted.name());
			if (prompted != null) {
				attrTemplateUpdater.setPrompted(prompted);
			} else {
				attrTemplateUpdater.setPrompted(false);
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.forceDefault.name())) {
			Boolean forceDefault = (Boolean) getAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.forceDefault.name());
			if (forceDefault != null) {
				attrTemplateUpdater.setForceDefault(forceDefault);
			} else {
				attrTemplateUpdater.setForceDefault(false);
			}
		}
		
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		AttributeTemplateListUpdater attributeTemplateListUpdater = (AttributeTemplateListUpdater) context.getUpdater();
		AttributeTemplateUpdateParameter param = new AttributeTemplateUpdateParameter();
		BeeId attrTemplateId = getBeeId(obj.getObjectId().toString());
		param.setAttributeTemplateHandle(attrTemplateId);
		AttributeTemplateUpdater attrTemplateUpdater = new AttributeTemplateUpdater();
		param.setAttributeTemplateUpdater(attrTemplateUpdater);
		attributeTemplateListUpdater.getUpdates().add(param);
		context.setChildUpdater(attrTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		AttributeTemplateListUpdater attributeTemplateListUpdater = (AttributeTemplateListUpdater) context.getUpdater();
		AttributeTemplateCreateParameter param = new AttributeTemplateCreateParameter();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		Identifiable pojoAttrDef = (Identifiable) getAttributeValue(pojoIdentifiable, 
				BeehivePropertyTemplateInfo.Attributes.propertyDefinition.name());
		BeeId attrDefinitionId = getBeeId(pojoAttrDef.getObjectId().toString());
		param.setAttributeDefinitionHandle(attrDefinitionId);
		String defName = (String) getAttributeValue(pojoAttrDef, PropertyDefinitionInfo.Attributes.name.name());
		param.setAttributeDefinitionName(defName);
		AttributeTemplateUpdater attrTemplateUpdater = new AttributeTemplateUpdater();
		param.setAttributeTemplateUpdater(attrTemplateUpdater);
		attributeTemplateListUpdater.getAdds().add(param);
		context.setChildUpdater(attrTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void delete(ManagedIdentifiableProxy obj, DAOContext context) {
		BeeId attrTemplateId = getBeeId(obj.getObjectId().toString());
		AttributeTemplateListUpdater attributeTemplateListUpdater = (AttributeTemplateListUpdater) context.getUpdater();
		attributeTemplateListUpdater.getRemoves().add(attrTemplateId);
	}

}
