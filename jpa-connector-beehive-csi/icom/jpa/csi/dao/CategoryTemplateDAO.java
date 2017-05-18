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

import icom.info.beehive.BeehiveCategoryApplicationTemplateInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.AttributeTemplate;
import oracle.csi.Category;
import oracle.csi.CategoryHandle;
import oracle.csi.CategoryTemplate;
import oracle.csi.CategoryTemplateHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.IdentifiableHandle;
import oracle.csi.controls.CategoryControl;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttributeTemplateListUpdater;
import oracle.csi.updaters.CategoryTemplateListUpdater;
import oracle.csi.updaters.CategoryTemplateUpdater;

public class CategoryTemplateDAO extends CsiIdentifiableDAO {
	
	static CategoryTemplateDAO singleton = new CategoryTemplateDAO();
	
	public static CategoryTemplateDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.category);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.finale);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.required);
	}

	protected CategoryTemplateDAO() {
		
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return CategoryTemplateHandle.class;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		CategoryTemplate csiCategoryTemplate = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, csiCategoryTemplate, proj);
		return proj;
	}
	
	public CategoryTemplate loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		CategoryTemplate csiCategoryTemplate = null;
		try {
			CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			CategoryTemplateHandle categoryTemplateHandle = (CategoryTemplateHandle) EntityUtils.getInstance().createHandle(id);
			csiCategoryTemplate = control.loadCategoryTemplate(categoryTemplateHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiCategoryTemplate;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiIdentifiable, Projection proj) {
		Persistent pojoIdentifiable = obj.getPojoObject();
		CategoryTemplate csiCategoryApplicationTemplate = (CategoryTemplate) csiIdentifiable;
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.category.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.category.name(),  proj)) {
			try {
				Category csiCategory  = csiCategoryApplicationTemplate.getCategory();
				ManagedIdentifiableProxy categoryObj = getEntityProxy(context, csiCategory);
				Persistent pojoCategory = categoryObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.category.name(), pojoCategory);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name(), proj)) {
			try {
				Vector<Persistent> v = new Vector<Persistent>();
				Collection<AttributeTemplate> list = csiCategoryApplicationTemplate.getAttributeTemplates();
				if (list != null) {
					Iterator<AttributeTemplate> iter = list.iterator();
					while (iter.hasNext()) {
						AttributeTemplate csiAttrTemplate = iter.next();
						ManagedIdentifiableProxy childObj = getIdentifiableDependentProxy(context, csiAttrTemplate, obj, BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name());
						childObj.getProviderProxy().copyLoadedProjection(childObj, csiAttrTemplate, proj);
						v.add(childObj.getPojoIdentifiable());
					}
				}
				assignAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name(), v);		
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name(), proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name(), 
						new Boolean(csiCategoryApplicationTemplate.isCopyOnVersion()));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name(), proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name(), 
						new Boolean(csiCategoryApplicationTemplate.isFinal()));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.required.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveCategoryApplicationTemplateInfo.Attributes.required.name(), proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.required.name(), 
						new Boolean(csiCategoryApplicationTemplate.isRequired()));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		CategoryTemplateUpdater categoryTemplateUpdater = (CategoryTemplateUpdater) context.getChildUpdater();
		
		if (isChanged(obj, BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name())) {
			Boolean copyOnVersion = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name());
			if (copyOnVersion != null) {
				categoryTemplateUpdater.setCopyOnVersion(copyOnVersion);
			} else {
				categoryTemplateUpdater.setCopyOnVersion(false);
			}
		}
		
		if (isChanged(obj, BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name())) {
			Boolean isFinal = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name());
			if (isFinal != null) {
				categoryTemplateUpdater.setFinal(isFinal);
			} else {
				categoryTemplateUpdater.setFinal(false);
			}
		}
		
		if (isChanged(obj, BeehiveCategoryApplicationTemplateInfo.Attributes.required.name())) {
			Boolean required = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.required.name());
			if (required != null) {
				categoryTemplateUpdater.setRequired(required);
			} else {
				categoryTemplateUpdater.setRequired(false);
			}
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoIdentifiable.getManagedObjectProxy()).getObjectId());
		CategoryTemplateHandle categoryTemplateHandle = (CategoryTemplateHandle) EntityUtils.getInstance().createHandle(id);
		CategoryTemplateListUpdater categoryTemplateListUpdater = (CategoryTemplateListUpdater) context.getUpdater();
		CategoryTemplateUpdater categoryTemplateUpdater = categoryTemplateListUpdater.updateCategoryTemplate(categoryTemplateHandle);
		context.setChildUpdater(categoryTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
		
		AttributeTemplateListUpdater attributeTemplateListUpdater = categoryTemplateUpdater.getAttributeTemplatesUpdater();
		DAOContext subContext = new DAOContext(attributeTemplateListUpdater);
		
		Collection<icom.jpa.Identifiable> pojoAttrTemplates;
		pojoAttrTemplates = getIdentifiableCollection(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name());
		if (pojoAttrTemplates != null) {
			Iterator<icom.jpa.Identifiable> pojoAttrTemplatesIter = pojoAttrTemplates.iterator();
			while (pojoAttrTemplatesIter.hasNext()) {
				Persistent pojoAttrTemplate = pojoAttrTemplatesIter.next();
				ManagedObjectProxy attrTemplateObj = pojoAttrTemplate.getManagedObjectProxy();
				if (attrTemplateObj.isDirty()) {
					AttributeTemplateDAO.getInstance().updateObjectState((ManagedIdentifiableProxy) attrTemplateObj, subContext);
				} else if (attrTemplateObj.isNew()) {
					AttributeTemplateDAO.getInstance().updateNewObjectState((ManagedIdentifiableProxy) attrTemplateObj, subContext);
				} else if (attrTemplateObj.isDeleted()) {
					throw new IllegalStateException("Deleted template should have been removed from the list");
				}
			}
		}
		
		Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name());
		if (removedObjects != null) {
			for (ValueHolder addedObjectHolder : removedObjects) {
				Persistent attrTemplate = (Persistent) addedObjectHolder.getValue();
				AttributeTemplateDAO.getInstance().updateObjectState((ManagedIdentifiableProxy) attrTemplate.getManagedObjectProxy(), subContext);
			}
		}
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		CategoryTemplateListUpdater categoryTemplateListUpdater = (CategoryTemplateListUpdater) context.getUpdater();
		Persistent pojoCategory = (Persistent) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.category.name());
		CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoCategory.getManagedObjectProxy()).getObjectId());
		CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(id);
		CategoryTemplateUpdater categoryTemplateUpdater = categoryTemplateListUpdater.addCategoryTemplate(categoryHandle);
		context.setChildUpdater(categoryTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
		
		Collection<icom.jpa.Identifiable> pojoAttrTemplates;
		pojoAttrTemplates = getIdentifiableCollection(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name());
		if (pojoAttrTemplates != null) {
			AttributeTemplateListUpdater attributeTemplateListUpdater = categoryTemplateUpdater.getAttributeTemplatesUpdater();
			DAOContext subContext = new DAOContext(attributeTemplateListUpdater);
			Iterator<icom.jpa.Identifiable> pojoAttrTemplatesIter = pojoAttrTemplates.iterator();
			while (pojoAttrTemplatesIter.hasNext()) {
				Persistent attrTemplate = pojoAttrTemplatesIter.next();
				AttributeTemplateDAO.getInstance().updateNewObjectState((ManagedIdentifiableProxy) attrTemplate.getManagedObjectProxy(), subContext);
			}
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj, DAOContext context) {
		CategoryTemplateListUpdater categoryTemplateListUpdater = (CategoryTemplateListUpdater) context.getUpdater();
		CollabId id = getCollabId(obj.getObjectId());
		CategoryTemplateHandle categoryTemplateHandle = (CategoryTemplateHandle) EntityUtils.getInstance().createHandle(id);
		categoryTemplateListUpdater.removeCategoryTemplate(categoryTemplateHandle);
	}
	
	/*
	public void committedObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<oracle.bom.ejb.spi.Identifiable> pojoAttributeTemplates;
		pojoAttributeTemplates = (Collection<oracle.bom.ejb.spi.Identifiable>) getCollection(pojoIdentifiable, CategoryApplicationTemplateInfo.Attributes.attributeTemplates.name());
		if (pojoAttributeTemplates != null) {
			Iterator<oracle.bom.ejb.spi.Identifiable> pojoAttributeTemplatesIter = pojoAttributeTemplates.iterator();
			while (pojoAttributeTemplatesIter.hasNext()) {
				oracle.bom.ejb.spi.Identifiable pojoAttributeTemplate = pojoAttributeTemplatesIter.next();
				AttributeTemplateDAO.getInstance().committedObject(pojoAttributeTemplate);
			}
		}
	}
	
	public void rolledbackObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<oracle.bom.ejb.spi.Identifiable> pojoAttributeTemplates;
		pojoAttributeTemplates = (Collection<oracle.bom.ejb.spi.Identifiable>) getCollection(pojoIdentifiable, CategoryApplicationTemplateInfo.Attributes.attributeTemplates.name());
		if (pojoAttributeTemplates != null) {
			Iterator<oracle.bom.ejb.spi.Identifiable> pojoAttributeTemplatesIter = pojoAttributeTemplates.iterator();
			while (pojoAttributeTemplatesIter.hasNext()) {
				oracle.bom.ejb.spi.Identifiable pojoAttributeTemplate = pojoAttributeTemplatesIter.next();
				AttributeTemplateDAO.getInstance().rolledbackObject(pojoAttributeTemplate);
			}
		}
	}
	*/
	
}
