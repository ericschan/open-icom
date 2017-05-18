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
import icom.info.BeanInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.beehive.BeehivePropertyTemplateInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;
import java.util.Vector;

import oracle.csi.AttributeDefinition;
import oracle.csi.AttributeDefinitionHandle;
import oracle.csi.AttributeTemplate;
import oracle.csi.AttributeTemplateHandle;
import oracle.csi.CollabId;
import oracle.csi.CollabProperties;
import oracle.csi.CollabProperty;
import oracle.csi.CsiRuntimeException;
import oracle.csi.IdentifiableHandle;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttributeTemplateListUpdater;
import oracle.csi.updaters.AttributeTemplateUpdater;
import oracle.csi.updaters.CollabPropertiesUpdater;

public class AttributeTemplateDAO extends CsiIdentifiableDAO {
	
	static AttributeTemplateDAO singleton = new AttributeTemplateDAO();
	
	public static AttributeTemplateDAO getInstance() {
		return singleton;
	}
	
	protected AttributeTemplateDAO() {
		
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return AttributeTemplateHandle.class;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiIdentifiable, Projection proj) {
		PersistenceContext context = managedObj.getPersistenceContext();
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		AttributeTemplate csiAttrTemplate = (AttributeTemplate) csiIdentifiable;
		
		try {
			AttributeDefinition csiAttrDefn = csiAttrTemplate.getAttributeDefinition();
			ManagedIdentifiableProxy attrDefnObj = getIdentifiableDependentProxy(context, csiAttrDefn, obj, BeehivePropertyTemplateInfo.Attributes.propertyDefinition.name());
			if (attrDefnObj.isPooled(null)) {
				AttributeDefinitionDAO.getInstance().copyObjectState(attrDefnObj, csiAttrDefn, proj);
				attrDefnObj.resetReady();
			}
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.propertyDefinition.name(),
					attrDefnObj.getPojoIdentifiable());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			Collection<Object> pojoPropertyChoices = new Vector<Object>();
			CollabProperties allowedValues = csiAttrTemplate.getAllowedValues();
			if (allowedValues != null) {
				Collection<CollabProperty> collabPropertyCollection = allowedValues
						.values();
				for (CollabProperty collabProperty : collabPropertyCollection) {
					ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(obj, BeehivePropertyTemplateInfo.Attributes.choices.name());
					CollabPropertyDAO.getInstance().copyObjectState(propertyObj, collabProperty, proj);
					pojoPropertyChoices.add(propertyObj.getPojoObject());
				}
			}
			
			Collection<Object> previousPropertyChoices = getObjectCollection(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.choices.name());
			if (previousPropertyChoices != null) {
				for (Object pojoPropertyChoice : previousPropertyChoices) {
					if (pojoPropertyChoice instanceof Persistent) {
						BeanInfo beanInfo = context.getBeanInfo(pojoPropertyChoice);
						ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoPropertyChoice, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
				}
			}
			
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.choices.name(), pojoPropertyChoices);
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			Object value = csiAttrTemplate.getDefaultValue();
			assignPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.defaultValue.name(), value);
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.minValue.name(), 
					csiAttrTemplate.getMinimumValue());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.maxValue.name(), 
					csiAttrTemplate.getMaximumValue());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.minimumValueInclusive.name(),	
					csiAttrTemplate.isMinimumValueInclusive());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.maximumValueInclusive.name(), 
					csiAttrTemplate.isMaximumValueInclusive());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.required.name(), 
					csiAttrTemplate.isMandatory());
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.finale.name(),
					csiAttrTemplate.isFinal());
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.forceDefault.name(),
					csiAttrTemplate.isForceDefault());
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.prompted.name(),
					csiAttrTemplate.isPrompted());
		} catch (CsiRuntimeException ex) {
			// ignore
		}
	}
	
	private ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(ManagedObjectProxy parent, String parentAttributeName) {
		return getNonIdentifiableDependentProxy(parent.getPersistenceContext(), IcomBeanEnumeration.PropertyChoiceType.name(), parent, parentAttributeName);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		AttributeTemplateUpdater attrTemplateUpdater = (AttributeTemplateUpdater) context.getChildUpdater();
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.choices.name())) {
			Collection<Object> pojoProperties = getObjectCollection(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.choices.name());
			if (pojoProperties != null) {
				CollabPropertiesUpdater collabPropertiesUpdater = attrTemplateUpdater.getAllowedValuesUpdater();
				for (Object o : pojoProperties) {
					CollabPropertyDAO.getInstance().updateObjectState(o, collabPropertiesUpdater, CollabPropertyDAO.Operand.ADD);
				}
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.defaultValue.name())) {
			Object defaultValue = getPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.defaultValue.name());
			attrTemplateUpdater.setDefaultValue(defaultValue);
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.minValue.name()) || isChanged(obj, BeehivePropertyTemplateInfo.Attributes.minimumValueInclusive.name())) {
			Object minimumValue = getPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.minValue.name());
			Boolean minimumValueInclusive = (Boolean) getAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.minimumValueInclusive.name());
			if (minimumValueInclusive != null) {
				attrTemplateUpdater.setMinimumValue(minimumValue, minimumValueInclusive);
			} else {
				attrTemplateUpdater.setMinimumValue(minimumValue, false);
			}
		}
		
		if (isChanged(obj, BeehivePropertyTemplateInfo.Attributes.maxValue.name()) || isChanged(obj, BeehivePropertyTemplateInfo.Attributes.maximumValueInclusive.name())) {
			Object maximumValue = getPropertyValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.maxValue.name());
			Boolean maximumValueInclusive = (Boolean) getAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.maximumValueInclusive.name());
			if (maximumValueInclusive != null) {
				attrTemplateUpdater.setMaximumValue(maximumValue, maximumValueInclusive);
			} else {
				attrTemplateUpdater.setMaximumValue(maximumValue, false);
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
		CollabId id = getCollabId(obj.getObjectId());
		AttributeTemplateHandle attrTemplateHandle = (AttributeTemplateHandle) EntityUtils.getInstance().createHandle(id);
		AttributeTemplateUpdater attrTemplateUpdater = attributeTemplateListUpdater.updateAttributeTemplate(attrTemplateHandle);
		context.setChildUpdater(attrTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		AttributeTemplateListUpdater attributeTemplateListUpdater = (AttributeTemplateListUpdater) context.getUpdater();
		Persistent pojoAttrDef = (Persistent) getAttributeValue(pojoIdentifiable, BeehivePropertyTemplateInfo.Attributes.propertyDefinition.name());
		CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoAttrDef.getManagedObjectProxy()).getObjectId());
		AttributeDefinitionHandle attrDefHandle = (AttributeDefinitionHandle) EntityUtils.getInstance().createHandle(id);
		AttributeTemplateUpdater attrTemplateUpdater = attributeTemplateListUpdater.addAttributeTemplate(attrDefHandle);
		context.setChildUpdater(attrTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void delete(ManagedIdentifiableProxy obj, DAOContext context) {
		CollabId id = getCollabId(obj.getObjectId());
		AttributeTemplateHandle attributeTemplateHandle = (AttributeTemplateHandle) EntityUtils.getInstance().createHandle(id);
		AttributeTemplateListUpdater attributeTemplateListUpdater = (AttributeTemplateListUpdater) context.getUpdater();
		attributeTemplateListUpdater.removeAttributeTemplate(attributeTemplateHandle);
	}

}
