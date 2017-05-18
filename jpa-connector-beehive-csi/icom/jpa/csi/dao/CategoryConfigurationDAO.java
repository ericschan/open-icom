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

import icom.info.beehive.BeehiveCategoryConfigurationInfo;
import icom.info.beehive.BeehiveExtentInfo;
import icom.jpa.Manageable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.dao.MetaDataApplicationDAO;
import icom.jpa.rt.ManagedIdentifiableDependentProxyImpl;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.CategoryConfiguration;
import oracle.csi.CategoryConfigurationHandle;
import oracle.csi.CategoryTemplate;
import oracle.csi.CollabId;
import oracle.csi.ContainerHandle;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.HeterogeneousFolderHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.ScopeHandle;
import oracle.csi.controls.CategoryControl;
import oracle.csi.controls.CategoryFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.CategoryConfigurationUpdater;
import oracle.csi.updaters.CategoryTemplateListUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class CategoryConfigurationDAO extends CsiIdentifiableDAO implements MetaDataApplicationDAO {

	static CategoryConfigurationDAO singleton = new CategoryConfigurationDAO();
	
	public static CategoryConfigurationDAO getInstance() {
		return singleton;
	}
	
	protected CategoryConfigurationDAO() {
		
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return CategoryConfigurationHandle.class;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		CategoryConfiguration csiCategoryConfiguration = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, csiCategoryConfiguration, proj);
		return proj;
	}
	
	public CategoryConfiguration loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		ManagedIdentifiableDependentProxyImpl depObj = (ManagedIdentifiableDependentProxyImpl) obj;
		ManagedIdentifiableProxy parentObj = (ManagedIdentifiableProxy) depObj.getParent();
		CollabId id = getCollabId(parentObj.getObjectId());
		ContainerHandle containerHandle = (ContainerHandle) EntityUtils.getInstance().createHandle(id);
		CategoryConfiguration csiCategoryConfiguration = null;
		try {
			if (containerHandle instanceof ScopeHandle) {
				csiCategoryConfiguration = control.getCategoryConfiguration((ScopeHandle) containerHandle, proj);
			} else {
				csiCategoryConfiguration = control.getCategoryConfiguration((HeterogeneousFolderHandle) containerHandle, proj);
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiCategoryConfiguration;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiIdentifiable, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		CategoryConfiguration csiCategoryConfiguration = (CategoryConfiguration) csiIdentifiable;
		
		try {
			PersistenceContext context = obj.getPersistenceContext();
			oracle.csi.ContainerHandle containerHandle = csiCategoryConfiguration.getContainer();
			CollabId id = containerHandle.getCollabId();
			Manageable pojoContainer = context.getReference(id);
			assignAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.extent.name(), pojoContainer);
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.allowAll.name(),
					new Boolean(csiCategoryConfiguration.isAllowAll()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			assignAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.finale.name(),
					new Boolean(csiCategoryConfiguration.isFinal()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			assignAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.required.name(),
					new Boolean(csiCategoryConfiguration.isRequired()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			PersistenceContext context = obj.getPersistenceContext();
			Vector<Persistent> v = new Vector<Persistent>();
			Collection<CategoryTemplate> csiCatTemplateList = csiCategoryConfiguration.getCategoryTemplates();
			if (csiCatTemplateList != null) {
				for (CategoryTemplate csiCatTemplate : csiCatTemplateList) {
					ManagedIdentifiableProxy childObj = getIdentifiableDependentProxy(context, csiCatTemplate, obj, BeehiveCategoryConfigurationInfo.Attributes.categoryApplicationTemplates.name());
					v.add(childObj.getPojoIdentifiable());
				}
			}
			assignAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.categoryApplicationTemplates.name(), v);	
		} catch (CsiRuntimeException ex) {
			// ignore
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
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		CategoryConfigurationUpdater updater = (CategoryConfigurationUpdater) context.getUpdater();
		CategoryTemplateListUpdater templateListUpdater = updater.getCategoryTemplatesUpdater();
		
		if (isChanged(obj, BeehiveCategoryConfigurationInfo.Attributes.finale.name())) {
			Boolean isFinal = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.finale.name());
			if (isFinal != null) {
				updater.setFinal(isFinal);
			} else {
				updater.setFinal(false);
			}
		}
		
		if (isChanged(obj, BeehiveCategoryConfigurationInfo.Attributes.required.name())) {
			Boolean required = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.required.name());
			if (required != null) {
				updater.setRequired(required);
			} else {
				updater.setRequired(false);
			}
		}
		
		if (isChanged(obj, BeehiveCategoryConfigurationInfo.Attributes.allowAll.name())) {
			Boolean allowAll = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.allowAll.name());
			if (allowAll != null) {
				updater.setAllowAll(allowAll);
			} else {
				updater.setAllowAll(false);
			}
		}
		
		DAOContext subContext = new DAOContext(templateListUpdater);
		
		Collection<icom.jpa.Identifiable> pojoCatAppTemplates = null;
		pojoCatAppTemplates = getIdentifiableCollection(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.categoryApplicationTemplates.name());
		if (pojoCatAppTemplates != null) {
			Iterator<icom.jpa.Identifiable> pojoCatAppTemplatesIter = pojoCatAppTemplates.iterator();
			while (pojoCatAppTemplatesIter.hasNext()) {
				Persistent pojoCatAppTemplate = pojoCatAppTemplatesIter.next();
				ManagedIdentifiableProxy catAppTemplateObj = (ManagedIdentifiableProxy) pojoCatAppTemplate.getManagedObjectProxy();
				if (catAppTemplateObj.isDirty()) {
					CategoryTemplateDAO.getInstance().updateObjectState(catAppTemplateObj, subContext);
				} else if (catAppTemplateObj.isNew()) {
					CategoryTemplateDAO.getInstance().updateNewObjectState(catAppTemplateObj, subContext);
				} else if (catAppTemplateObj.isDeleted()) {
					throw new IllegalStateException("Deleted template should have been removed from the list");
				}
			}
		}
		
		Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveCategoryConfigurationInfo.Attributes.categoryApplicationTemplates.name());
		if (removedObjects != null) {
			for (ValueHolder addedObjectHolder : removedObjects) {
				Persistent catAppTemplate = (Persistent) addedObjectHolder.getValue();
				ManagedIdentifiableProxy catAppTemplateObj = (ManagedIdentifiableProxy) catAppTemplate.getManagedObjectProxy();
				CategoryTemplateDAO.getInstance().delete(catAppTemplateObj, subContext);
			}
		}
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		CategoryConfigurationUpdater updater = CategoryFactory.getInstance().createCategoryConfigurationUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public CategoryConfiguration concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryConfigurationUpdater updater = (CategoryConfigurationUpdater) context.getUpdater();
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		Persistent pojoCatConfig = obj.getPojoIdentifiable();
		CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoCatConfig.getManagedObjectProxy()).getObjectId());
		CategoryConfigurationHandle catConfigHandle = (CategoryConfigurationHandle) EntityUtils.getInstance().createHandle(id);
		
		try {
			UpdateMode updateMode = UpdateMode.alwaysUpdate();
			CategoryConfiguration csiCatConfig = null;
			csiCatConfig = control.updateCategoryConfiguration(catConfigHandle, updater, updateMode);
			copyObjectState(obj, csiCatConfig, proj);
			return csiCatConfig;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public CategoryConfiguration concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryConfigurationUpdater updater = (CategoryConfigurationUpdater) context.getUpdater();
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		Persistent pojoCatConfig = obj.getPojoIdentifiable();
		Persistent pojoContainer = getContainer(pojoCatConfig);
		CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoContainer.getManagedObjectProxy()).getObjectId());
		ContainerHandle containerHandle = (ContainerHandle) EntityUtils.getInstance().createHandle(id);
		
		try {
			CategoryConfiguration csiCatConfig = null;
			if (containerHandle instanceof ScopeHandle) {
				csiCatConfig = control.createCategoryConfiguration((ScopeHandle) containerHandle, updater, proj);
			} else {
				csiCatConfig = control.createCategoryConfiguration((HeterogeneousFolderHandle) containerHandle, updater, proj);
			}
			copyObjectState(obj, csiCatConfig, proj);
			return csiCatConfig;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		CategoryConfigurationHandle categoryConfigurationHandle = (CategoryConfigurationHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteCategoryConfiguration(categoryConfigurationHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	/*
	public void committedObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<oracle.bom.ejb.spi.Identifiable> pojoCategoryApplicationTemplates;
		pojoCategoryApplicationTemplates = (Collection<oracle.bom.ejb.spi.Identifiable>) getCollection(pojoIdentifiable, CategoryConfigurationInfo.Attributes.categoryApplicationTemplates.name());
		if (pojoCategoryApplicationTemplates != null) {
			Iterator<oracle.bom.ejb.spi.Identifiable> pojoCategoryApplicationTemplatesIter = pojoCategoryApplicationTemplates.iterator();
			while (pojoCategoryApplicationTemplatesIter.hasNext()) {
				oracle.bom.ejb.spi.Identifiable pojoCategoryApplicationTemplate = pojoCategoryApplicationTemplatesIter.next();
				CategoryApplicationTemplateDAO.getInstance().committedObject(pojoCategoryApplicationTemplate);
			}
		}
	}
	
	public void rolledbackObject(oracle.bom.ejb.spi.Identifiable pojoIdentifiable) {
		Collection<oracle.bom.ejb.spi.Identifiable> pojoCategoryApplicationTemplates;
		pojoCategoryApplicationTemplates = (Collection<oracle.bom.ejb.spi.Identifiable>) getCollection(pojoIdentifiable, CategoryConfigurationInfo.Attributes.categoryApplicationTemplates.name());
		if (pojoCategoryApplicationTemplates != null) {
			Iterator<oracle.bom.ejb.spi.Identifiable> pojoCategoryApplicationTemplatesIter = pojoCategoryApplicationTemplates.iterator();
			while (pojoCategoryApplicationTemplatesIter.hasNext()) {
				oracle.bom.ejb.spi.Identifiable pojoCategoryApplicationTemplate = pojoCategoryApplicationTemplatesIter.next();
				CategoryApplicationTemplateDAO.getInstance().rolledbackObject(pojoCategoryApplicationTemplate);
			}
		}
	}
	*/
	
	void loadCategoryConfiguration(ManagedIdentifiableProxy containerObj, Projection proj) {
		PersistenceContext context = containerObj.getPersistenceContext();
		CategoryControl control = ControlLocator.getInstance().getControl(CategoryControl.class);
		CollabId id = getCollabId(containerObj.getObjectId());
		ContainerHandle containerHandle = (ContainerHandle) EntityUtils.getInstance().createHandle(id);
		CategoryConfiguration csiCategoryConfiguration = null;
		try {
			if (containerHandle instanceof ScopeHandle) {
				csiCategoryConfiguration = control.getCategoryConfiguration((ScopeHandle) containerHandle, proj);
			} else {
				csiCategoryConfiguration = control.getCategoryConfiguration((HeterogeneousFolderHandle) containerHandle, proj);
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		if (csiCategoryConfiguration != null) {
			try {
				String attributeName = BeehiveExtentInfo.Attributes.categoryConfiguration.name();
				oracle.csi.ContainerHandle configContainerHandle = csiCategoryConfiguration.getContainer();
				CollabId configContainerId = configContainerHandle.getCollabId();
				Manageable pojoConfigContainer = context.getReference(configContainerId);
				ManagedIdentifiableProxy configContainerObj = (ManagedIdentifiableProxy) pojoConfigContainer.getManagedObjectProxy();
				ManagedIdentifiableProxy categoryConfigurationObj = getIdentifiableDependentProxy(context, csiCategoryConfiguration, configContainerObj, attributeName);
				categoryConfigurationObj.getProviderProxy().copyLoadedProjection(categoryConfigurationObj, csiCategoryConfiguration, proj);
				Persistent pojoCategoryConfig = categoryConfigurationObj.getPojoIdentifiable();
				configContainerObj.getProviderProxy().copyLazyAttribute(configContainerObj, attributeName, null, pojoCategoryConfig);
				if (containerObj.getPojoIdentifiable() != pojoConfigContainer) {
					containerObj.getProviderProxy().copyLazyAttribute(containerObj, attributeName, null, null);
				}
			} catch (CsiRuntimeException ex) {
			}
		}
	}
	
	public Persistent getContainer(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveCategoryConfigurationInfo.Attributes.extent.name());
	}
	
}
