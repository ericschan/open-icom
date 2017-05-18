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
import icom.info.CategoryApplicationInfo;
import icom.info.EntityInfo;
import icom.info.beehive.BeehiveCategoryApplicationInfo;
import icom.info.beehive.BeehivePropertyInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.dao.MetaDataApplicationDAO;
import icom.jpa.rt.ManagedIdentifiableDependentProxyImpl;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.AttributeApplication;
import oracle.csi.AttributeTemplateHandle;
import oracle.csi.Category;
import oracle.csi.CategoryApplication;
import oracle.csi.CategoryApplicationHandle;
import oracle.csi.CategoryHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.controls.CategoryControl;
import oracle.csi.controls.CategoryFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttributeApplicationListUpdater;
import oracle.csi.updaters.CategoryApplicationUpdater;
import oracle.csi.util.UpdateMode;

public class CategoryApplicationDAO extends CsiIdentifiableDAO implements MetaDataApplicationDAO {
	
	static CategoryApplicationDAO singleton = new CategoryApplicationDAO();
	
	public static CategoryApplicationDAO getInstance() {
		return singleton;
	}
	
	{

	}
	
	{
		fullAttributes.add(CategoryApplicationInfo.Attributes.category);
		fullAttributes.add(CategoryApplicationInfo.Attributes.properties);
		fullAttributes.add(CategoryApplicationInfo.Attributes.attachedEntity);
	}
	
	{
		lazyAttributes.add(BeehiveCategoryApplicationInfo.Attributes.categoryApplicationTemplate);
	}

	protected CategoryApplicationDAO() {
		
	}

	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return CategoryApplicationHandle.class;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		CategoryApplication csiCategoryApplication = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, csiCategoryApplication, proj);
		return proj;
	}
	
	public CategoryApplication loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		ManagedIdentifiableDependentProxyImpl depObj = (ManagedIdentifiableDependentProxyImpl) obj;
		CategoryApplication csiCategoryApplication = null;
		Persistent pojoCatApp = depObj.getPojoIdentifiable();
		Persistent pojoCat = getCategory(pojoCatApp);
		if (pojoCat != null) {
			CollabId catId = getCollabId(((ManagedIdentifiableProxy)pojoCat.getManagedObjectProxy()).getObjectId());
			CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(catId);
		
			ManagedIdentifiableProxy parentObj = (ManagedIdentifiableProxy) depObj.getParent();
			CollabId id = getCollabId(parentObj.getObjectId());
			EntityHandle categorizedEntityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(id);

			try {
				csiCategoryApplication = control.getCategoryApplication(categoryHandle, categorizedEntityHandle, proj);
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
		}
		return csiCategoryApplication;
	}

	public void copyObjectState(ManagedObjectProxy obj, Object csiIdentifiable, Projection proj) {
		CategoryApplication csiCategoryApplication = (CategoryApplication) csiIdentifiable;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
		//Projection lastLoadedProjection = obj.getProviderProxy().getLastLoadedProjection(obj);
		
		if (/* !isPartOfProjection(CategoryApplicationInfo.Attributes.categorizedEntity.name(), lastLoadedProjection) && */
				isPartOfProjection(CategoryApplicationInfo.Attributes.attachedEntity.name(),  proj)) {
			try {
				Entity csiCategorizedEntity  = csiCategoryApplication.getCategorizedEntity();
				ManagedIdentifiableProxy categorizedEntityObj = getEntityProxy(context, csiCategorizedEntity);
				Persistent categorizedPojoEntity = categorizedEntityObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, CategoryApplicationInfo.Attributes.attachedEntity.name(), categorizedPojoEntity);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (/* !isPartOfProjection(CategoryApplicationInfo.Attributes.category.name(), lastLoadedProjection) && */
				isPartOfProjection(CategoryApplicationInfo.Attributes.category.name(),  proj)) {
			try {
				Category csiCategory  = csiCategoryApplication.getCategory();
				ManagedIdentifiableProxy categoryObj = getEntityProxy(context, csiCategory);
				Persistent pojoCategory = categoryObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, CategoryApplicationInfo.Attributes.category.name(), pojoCategory);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (/* !isPartOfProjection(CategoryApplicationInfo.Attributes.properties.name(), lastLoadedProjection) && */
				isPartOfProjection(CategoryApplicationInfo.Attributes.properties.name(), proj)) {
			try {
				Collection<AttributeApplication> list = csiCategoryApplication.getAttributeApplications();
				Vector<Object> v = new Vector<Object>();
				if (list != null) {
					Iterator<AttributeApplication> iter = list.iterator();
					while (iter.hasNext()) {
						Object pojoAttribute = AttributeApplicationDAO.getInstance().copyObjectState(obj, iter.next());
						assignAttributeValue(pojoAttribute, BeehivePropertyInfo.TransientAttributes.categoryApplication.name(), pojoIdentifiable);
						v.add(pojoAttribute);
					}
				}
				
				Collection<Object> previousProperties = getObjectCollection(pojoIdentifiable, CategoryApplicationInfo.Attributes.properties.name());
				if (previousProperties != null) {
					for (Object pojoProperty : previousProperties) {
						if (pojoProperty instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, CategoryApplicationInfo.Attributes.properties.name(), v);		
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	public void save(ManagedIdentifiableProxy obj) {
		if (obj.isDirty()) {
			if (! obj.hasAttributeChanges()) {
				return;
			}
			saveDirty(obj);
		} else if (obj.isNew()) {
			saveNew(obj);
		}
	}
	
	public void saveDirty(ManagedIdentifiableProxy obj) {
		DAOContext context = beginUpdateObject(obj);
		if (context != null) {
			updateObjectState(obj, context);
			concludeUpdateObject(obj, context, Projection.FULL);
		}
	}
	
	public void saveNew(ManagedIdentifiableProxy obj) {
		DAOContext context = beginCreateObject(obj);
		if (context != null) {
			updateNewObjectState(obj, context);
			concludeCreateObject(obj, context, Projection.FULL);
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		CategoryApplicationUpdater updater = (CategoryApplicationUpdater) context.getUpdater();
		if (updater != null) {
			AttributeApplicationListUpdater attrListUpdater = updater.getAttributeValuesUpdater();
			
			if (isChanged(obj, CategoryApplicationInfo.Attributes.properties.name())) {
				
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(CategoryApplicationInfo.Attributes.properties.name());
				if (addedObjects != null) {
					for (ValueHolder addedObjectHolder : addedObjects) {
						//Persistent attrTemplate = (Persistent) addedObjectHolder.getKey();
					    Object attribute = addedObjectHolder.getValue();
					    Persistent attrTemplate = (Persistent) getAttributeValue((Persistent)attribute, BeehivePropertyInfo.TransientAttributes.propertyTemplate.name());
						CollabId id = getCollabId(((ManagedIdentifiableProxy)attrTemplate.getManagedObjectProxy()).getObjectId());
						AttributeTemplateHandle attrTemplateHandle = (AttributeTemplateHandle) 
							EntityUtils.getInstance().createHandle(id);
						AttributeApplicationDAO.getInstance().updateObjectState(attribute, attrListUpdater, attrTemplateHandle, AttributeApplicationDAO.Operand.ADD);
					}
				}
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CategoryApplicationInfo.Attributes.properties.name());
				if (removedObjects != null) {
					for (ValueHolder removedObjectHolder : removedObjects) {
						//Persistent attrTemplate = (Persistent) removedObjectHolder.getKey();
					    Object attribute = removedObjectHolder.getValue();
					    Persistent attrTemplate = (Persistent) getAttributeValue((Persistent)attribute, BeehivePropertyInfo.TransientAttributes.propertyTemplate.name());
						CollabId id = getCollabId(((ManagedIdentifiableProxy)attrTemplate.getManagedObjectProxy()).getObjectId());
						AttributeTemplateHandle attrTemplateHandle = (AttributeTemplateHandle) 
							EntityUtils.getInstance().createHandle(id);
						AttributeApplicationDAO.getInstance().updateObjectState(attribute, attrListUpdater, attrTemplateHandle, AttributeApplicationDAO.Operand.REMOVE);
					}
				}
				Collection<ValueHolder> modifiedObjects = obj.getModifiedObjects(CategoryApplicationInfo.Attributes.properties.name());
				if (modifiedObjects != null) {
					for (ValueHolder modifiedObjectHolder : modifiedObjects) {
						//Persistent attrTemplate = (Persistent) modifiedObjectHolder.getKey();
					    Object attribute = modifiedObjectHolder.getValue();
					    Persistent attrTemplate = (Persistent) getAttributeValue((Persistent)attribute, BeehivePropertyInfo.TransientAttributes.propertyTemplate.name());
						CollabId id = getCollabId(((ManagedIdentifiableProxy)attrTemplate.getManagedObjectProxy()).getObjectId());
						AttributeTemplateHandle attrTemplateHandle = (AttributeTemplateHandle) EntityUtils.getInstance().createHandle(id);
						AttributeApplicationDAO.getInstance().updateObjectState(attribute, attrListUpdater, attrTemplateHandle, AttributeApplicationDAO.Operand.MODIFY);
					}
				}
			}
		}
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		CategoryApplicationUpdater updater = CategoryFactory.getInstance().createCategoryApplicationUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	// called from DocumentDAO
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public CategoryApplication concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryApplicationUpdater updater = (CategoryApplicationUpdater) context.getUpdater();
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		icom.jpa.Identifiable pojoCatApp = obj.getPojoIdentifiable();
		
		Persistent pojoCat = getCategory(pojoCatApp);
		CollabId catId = getCollabId(((ManagedIdentifiableProxy)pojoCat.getManagedObjectProxy()).getObjectId());
		CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(catId);
		
		Persistent pojoEntity = getCategorizedEntity(pojoCatApp);
		CollabId entityId = getCollabId(((ManagedIdentifiableProxy)pojoEntity.getManagedObjectProxy()).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(entityId);
		
		try {
			UpdateMode updateMode = UpdateMode.alwaysUpdate();
			CategoryApplication csiCatApp = control.updateCategoryApplication(categoryHandle, entityHandle, updater, 
					updateMode, proj);
			copyObjectState(obj, csiCatApp, proj);
			CollabId newCollabId = csiCatApp.getCollabId();
			CsiIdentifiableDAO.assignObjectId(pojoCatApp, newCollabId.toString());
			return csiCatApp;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	// called from DocumentDAO
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public CategoryApplication concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryApplicationUpdater updater = (CategoryApplicationUpdater) context.getUpdater();
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		
		icom.jpa.Identifiable pojoCatApp = obj.getPojoIdentifiable();
		
		Persistent pojoCat = getCategory(pojoCatApp);
		CollabId catId = getCollabId(((ManagedIdentifiableProxy)pojoCat.getManagedObjectProxy()).getObjectId());
		CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(catId);
		
		Persistent pojoEntity = getCategorizedEntity(pojoCatApp);
		CollabId entityId = getCollabId(((ManagedIdentifiableProxy)pojoEntity.getManagedObjectProxy()).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(entityId);
		Vector<EntityHandle> ehandles = new Vector<EntityHandle>(1);
		ehandles.add(entityHandle);
		
		try {
			control.applyCategoryToEntities(categoryHandle, ehandles, updater);
			CategoryApplication csiCatApp = control.getCategoryApplication(categoryHandle, entityHandle, proj);
			copyObjectState(obj, csiCatApp, proj);
			CollabId newCollabId = csiCatApp.getCollabId();
			CsiIdentifiableDAO.assignObjectId(pojoCatApp, newCollabId.toString());
			obj.getPersistenceContext().recacheIdentifiableDependent(obj);
			return csiCatApp;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		Persistent pojoCatApp = obj.getPojoIdentifiable();
		
		Persistent pojoCat = getCategory(pojoCatApp);
		CollabId catId = getCollabId(((ManagedIdentifiableProxy)pojoCat.getManagedObjectProxy()).getObjectId());
		CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(catId);
		
		Persistent pojoEntity = getCategorizedEntity(pojoCatApp);
		CollabId entityId = getCollabId(((ManagedIdentifiableProxy)pojoEntity.getManagedObjectProxy()).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(entityId);
		Vector<EntityHandle> ehandles = new Vector<EntityHandle>(1);
		ehandles.add(entityHandle);
		
		try {
			control.removeCategoryfromEntities(categoryHandle, ehandles);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		
	}

	/*
	public void committedObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<Object> pojoAttributes = (Collection<Object>) getObjectCollection(pojoIdentifiable, CategoryApplicationInfo.Attributes.attributes.name());
		if (pojoAttributes != null) {
			Iterator<Object> pojoAttributesIter = pojoAttributes.iterator();
			while (pojoAttributesIter.hasNext()) {
				Object pojoAttribute = pojoAttributesIter.next();
				AttributeDAO.getInstance().committedObject(pojoAttribute);
			}
		}
	}
	
	public void rolledbackObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<Object> pojoAttributes = (Collection<Object>) getObjectCollection(pojoIdentifiable, CategoryApplicationInfo.Attributes.attributes.name());
		if (pojoAttributes != null) {
			Iterator<Object> pojoAttributesIter = pojoAttributes.iterator();
			while (pojoAttributesIter.hasNext()) {
				Object pojoAttribute = pojoAttributesIter.next();
				AttributeDAO.getInstance().rolledbackObject(pojoAttribute);
			}
		}
	}
	*/
	
	void loadCategoryApplicationsOnEntity(ManagedIdentifiableProxy obj, Projection projection) {
		HashSet<Persistent> v = new HashSet<Persistent>();
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		CollabId entityId = getCollabId(obj.getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(entityId);
		PersistenceContext context = obj.getPersistenceContext();
		try {
			Collection<CategoryApplication> csiCatApps = control.getCategoryApplications(entityHandle, projection);
			if (csiCatApps != null) {
				Iterator<CategoryApplication> iter = csiCatApps.iterator();
				while (iter.hasNext()) {
					CategoryApplication csiCategoryApplication = iter.next();
					ManagedIdentifiableProxy catAppObj = getIdentifiableDependentProxy(context, csiCategoryApplication, obj, EntityInfo.Attributes.categoryApplications.name());
					catAppObj.getProviderProxy().copyLoadedProjection(catAppObj, csiCategoryApplication, projection);
					Persistent pojoCategoryApplication = catAppObj.getPojoIdentifiable();
					v.add(pojoCategoryApplication);
				}
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		obj.getProviderProxy().copyLazyAttribute(obj, EntityInfo.Attributes.categoryApplications.name(), null, v);
	}
	
	public Persistent getCategory(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, CategoryApplicationInfo.Attributes.category.name());
	}
	
	public Persistent getCategorizedEntity(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, CategoryApplicationInfo.Attributes.attachedEntity.name());
	}

	public Persistent getCategoryApplicationTemplate(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationInfo.Attributes.categoryApplicationTemplate.name());
	}

}
