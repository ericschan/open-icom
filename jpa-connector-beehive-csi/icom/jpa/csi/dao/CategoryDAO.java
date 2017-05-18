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

import icom.info.CategoryInfo;
import icom.info.beehive.BeehiveCategoryInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.AttributeDefinition;
import oracle.csi.Category;
import oracle.csi.CategoryHandle;
import oracle.csi.CategoryTemplate;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.controls.CategoryControl;
import oracle.csi.controls.CategoryFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttributeDefinitionListUpdater;
import oracle.csi.updaters.CategoryUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class CategoryDAO extends MarkerDAO {
	
	static CategoryDAO singleton = new CategoryDAO();
	
	public static CategoryDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(CategoryInfo.Attributes.propertyDefinitions);
		basicAttributes.add(CategoryInfo.Attributes.abstrazt);
	}
	
	{
		fullAttributes.add(CategoryInfo.Attributes.superCategories);
		fullAttributes.add(CategoryInfo.Attributes.subCategories);
		fullAttributes.add(BeehiveCategoryInfo.Attributes.defaultTemplate);
	}

	protected CategoryDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return CategoryHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Category csiCategory = null;
		try {
			CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(id);
			csiCategory = control.loadCategory(categoryHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiCategory;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Category csiCategory = (Category) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(CategoryInfo.Attributes.superCategories.name(), lastLoadedProjection) &&
				isPartOfProjection(CategoryInfo.Attributes.superCategories.name(), proj)) {
			try {
				Category csiSuperCategory = csiCategory.getParentCategory();
				if (csiSuperCategory != null) {
					ManagedIdentifiableProxy superCategoryObj = getEntityProxy(context, csiSuperCategory);
					Persistent superCategoryPojoEntity = superCategoryObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.superCategories.name(), superCategoryPojoEntity);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(CategoryInfo.Attributes.subCategories.name(), lastLoadedProjection) &&
				isPartOfProjection(CategoryInfo.Attributes.subCategories.name(), proj)) {
			try {
				HashSet<Persistent> v = new HashSet<Persistent>();
				Collection<Category> list = csiCategory.getSubCategories();
				if (list != null) {
					Iterator<Category> iter = list.iterator();
					while (iter.hasNext()) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, iter.next());
						v.add(childObj.getPojoIdentifiable());
					}
				}
				assignAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.subCategories.name(), v);		
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(CategoryInfo.Attributes.propertyDefinitions.name(), lastLoadedProjection) &&
				isPartOfProjection(CategoryInfo.Attributes.propertyDefinitions.name(), proj)) {
			try {
				Vector<Persistent> v = new Vector<Persistent>();
				Collection<AttributeDefinition> list = csiCategory.getAttributeDefinitions();
				if (list != null) {
					Iterator<AttributeDefinition> iter = list.iterator();
					while (iter.hasNext()) {
						AttributeDefinition csiAttrDefn = iter.next();
						ManagedIdentifiableProxy childObj = getIdentifiableDependentProxy(context, csiAttrDefn, obj, CategoryInfo.Attributes.propertyDefinitions.name());
						if (childObj.isPooled(null)) {
							childObj.getProviderProxy().copyLoadedProjection(childObj, csiAttrDefn, proj);
						}
						v.add(childObj.getPojoIdentifiable());
					}
				}
				assignAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.propertyDefinitions.name(), v);		
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(CategoryInfo.Attributes.abstrazt.name(), lastLoadedProjection) &&
				isPartOfProjection(CategoryInfo.Attributes.abstrazt.name(), proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.abstrazt.name(), 
						new Boolean(csiCategory.isAbstract()));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(BeehiveCategoryInfo.Attributes.defaultTemplate.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveCategoryInfo.Attributes.defaultTemplate.name(), proj)) {
			try {
				CategoryTemplate csiCategoryTemplate = csiCategory.getDefaultTemplate();
				if (csiCategoryTemplate != null) {
					ManagedIdentifiableProxy categoryApplicationTemplateObj = getEntityProxy(context, csiCategoryTemplate);
					Persistent categoryApplicationTemplatePojoEntity = categoryApplicationTemplateObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, BeehiveCategoryInfo.Attributes.defaultTemplate.name(), categoryApplicationTemplatePojoEntity);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, CategoryInfo.Attributes.abstrazt.name())) {
			CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
			Boolean abztract = (Boolean) getAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.abstrazt.name());
			categoryUpdater.setAbstract(abztract);
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		CategoryUpdater categoryUpdater = CategoryFactory.getInstance().createCategoryUpdater();
		DAOContext context = new DAOContext(categoryUpdater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
		
		CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
		AttributeDefinitionListUpdater attrDefnListUpdater = categoryUpdater.getAttributeDefinitionsUpdater();
		DAOContext subContext = new DAOContext(attrDefnListUpdater);
		
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		Collection<icom.jpa.Identifiable> pojoAttrDefns;
		pojoAttrDefns = getIdentifiableCollection(pojoIdentifiable, CategoryInfo.Attributes.propertyDefinitions.name());
		if (pojoAttrDefns != null) {
			Iterator<icom.jpa.Identifiable> pojoAttrDefnsIter = pojoAttrDefns.iterator();
			while (pojoAttrDefnsIter.hasNext()) {
				Persistent pojoAttrDefinition = pojoAttrDefnsIter.next();
				ManagedIdentifiableProxy attrDefinitionObj = (ManagedIdentifiableProxy) pojoAttrDefinition.getManagedObjectProxy();
				if (attrDefinitionObj.isNew()) {
					AttributeDefinitionDAO.getInstance().updateNewObjectState(attrDefinitionObj, subContext);
				} else if (attrDefinitionObj.isDirty()) {
					AttributeDefinitionDAO.getInstance().updateObjectState(attrDefinitionObj, subContext);
				} else if (attrDefinitionObj.isDeleted()) {
					throw new IllegalStateException("Deleted attribute definition should have been removed from the list");
				}
			}
		}
		
		Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CategoryInfo.Attributes.propertyDefinitions.name());
		if (removedObjects != null) {
			for (ValueHolder addedObjectHolder : removedObjects) {
				Persistent attrDefinition = (Persistent) addedObjectHolder.getValue();
				AttributeDefinitionDAO.getInstance().delete((ManagedIdentifiableProxy) attrDefinition.getManagedObjectProxy(), subContext);
			}
		}
		
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(id);
		CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		try {
			Category category = control.updateCategory(categoryHandle,
					categoryUpdater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, category.getSnapshotId().toString());
			return category;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
		
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		Collection<icom.jpa.Identifiable> pojoAttrDefns;
		pojoAttrDefns = getIdentifiableCollection(pojoIdentifiable, CategoryInfo.Attributes.propertyDefinitions.name());
		if (pojoAttrDefns != null) {
			CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
			AttributeDefinitionListUpdater attrDefnListUpdater = categoryUpdater.getAttributeDefinitionsUpdater();
			DAOContext subContext = new DAOContext(attrDefnListUpdater);
			Iterator<icom.jpa.Identifiable> pojoAttrDefnsIter = pojoAttrDefns.iterator();
			while (pojoAttrDefnsIter.hasNext()) {
				Persistent attrDefinition = pojoAttrDefnsIter.next();
				AttributeDefinitionDAO.getInstance().updateNewObjectState((ManagedIdentifiableProxy) attrDefinition.getManagedObjectProxy(), subContext);
			}
		}
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryControl control = ControlLocator.getInstance()
				.getControl(CategoryControl.class);
		CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		
		String name = getName(pojoIdentifiable);
		Persistent superCategory = null;
		//superCategory = (Persistent) getAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.superCategories.name());
		// changed cardinality of superCategory to multi for ICOM V1
		CategoryHandle superCategoryHandle = null;
		Collection<icom.jpa.Identifiable> superCategories;
		superCategories = getIdentifiableCollection(pojoIdentifiable, CategoryInfo.Attributes.superCategories.name());
		if (superCategories != null) {
			superCategory = superCategories.iterator().next();
		}
		
		if (superCategory != null) {
			CollabId superCategoryId = getCollabId(((ManagedIdentifiableProxy)superCategory.getManagedObjectProxy()).getObjectId());
			superCategoryHandle = (CategoryHandle) EntityUtils.getInstance()
					.createHandle(superCategoryId);
		}
		try {
			Category category = null;
			CollabId id = getCollabId(obj.getObjectId());
			if (superCategoryHandle != null) {
				category = control.createSubCategory(id.getEid(), 
					superCategoryHandle, name, categoryUpdater, proj);
			} else {
				category = control.createCategory(id.getEid(), 
					name, categoryUpdater, proj);
			}
			assignChangeToken(pojoIdentifiable, category.getSnapshotId().toString());
			return category;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		CategoryHandle categoryHandle = (CategoryHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteCategory(categoryHandle, true, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	/*
	public void committedObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<oracle.bom.ejb.spi.Identifiable> pojoAttrDefns = null;
		pojoAttrDefns = (Collection<oracle.bom.ejb.spi.Identifiable>) getCollection(pojoIdentifiable, CategoryInfo.Attributes.attributes.name());
		Iterator<oracle.bom.ejb.spi.Identifiable> pojoAttrDefnsIter = pojoAttrDefns.iterator();
		while (pojoAttrDefnsIter.hasNext()) {
			oracle.bom.ejb.spi.Identifiable attrDefinition = pojoAttrDefnsIter.next();
			attrDefinition.getManagedObjectProxy().committed();
		}
	}
	
	public void rolledbackObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<oracle.bom.ejb.spi.Identifiable> pojoAttrDefns = null;
		pojoAttrDefns = (Collection<oracle.bom.ejb.spi.Identifiable>) getCollection(pojoIdentifiable, CategoryInfo.Attributes.attributes.name());
		if (pojoAttrDefns != null) {
			Iterator<oracle.bom.ejb.spi.Identifiable> pojoAttrDefnsIter = pojoAttrDefns.iterator();
			while (pojoAttrDefnsIter.hasNext()) {
				oracle.bom.ejb.spi.Identifiable attrDefinition = pojoAttrDefnsIter.next();
				attrDefinition.getManagedObjectProxy().rolledback();
			}
		}
	}
	*/
	
	public Set<Persistent> loadCategories(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoCategories = null;
		try {
			CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
			Collection<Category> csiCategories = control.getAllCategories(false, proj);
			if (csiCategories != null) {
				pojoCategories = new HashSet<Persistent>(csiCategories.size());
				for (Category csiCat : csiCategories) {
					ManagedIdentifiableProxy catObj = getEntityProxy(context, csiCat);
					catObj.getProviderProxy().copyLoadedProjection(catObj, csiCat, proj);
					pojoCategories.add(catObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoCategories;
	}
	
}
