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

import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.IdentifiableInfo;
import icom.info.RelationshipBondableInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.PersistenceException;

import oracle.csi.ACE;
import oracle.csi.AccessControlFields;
import oracle.csi.Accessor;
import oracle.csi.AccessorHandle;
import oracle.csi.Actor;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.Marker;
import oracle.csi.Priority;
import oracle.csi.SnapshotId;
import oracle.csi.controls.AccessControl;
import oracle.csi.controls.AccessFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AccessControlFieldsUpdater;
import oracle.csi.updaters.EntityUpdater;

public abstract class EntityDAO extends CsiIdentifiableDAO  {
	
	{
		basicAttributes.add(EntityInfo.Attributes.name);
		basicAttributes.add(EntityInfo.Attributes.parent);
		basicAttributes.add(EntityInfo.Attributes.lastModificationDate);
		basicAttributes.add(EntityInfo.Attributes.lastModifiedBy);
	}

	{
		metaAttributes.add(EntityInfo.Attributes.createdBy);
		metaAttributes.add(EntityInfo.Attributes.creationDate);
		//metaAttributes.add(EntityInfo.Attributes.attachedSubscriptions);
	}
	
	{
		//fullAttributes.add(EntityInfo.Attributes.attachedReminders);
		fullAttributes.add(EntityInfo.Attributes.attachedMarkers);
	}
	
	{
		securityAttributes.add(EntityInfo.Attributes.owner);
		securityAttributes.add(EntityInfo.Attributes.accessControlList);
	}
	
	{
		lazyAttributes.add(EntityInfo.Attributes.categoryApplications);
		lazyAttributes.add(EntityInfo.Attributes.tagApplications);
	}
	
	static public HashMap<String, String> csiToPojoPriorityNameMap;
	static public HashMap<String, String> pojoToCsiPriorityNameMap;
	
	{
		csiToPojoPriorityNameMap = new HashMap<String, String>();
		pojoToCsiPriorityNameMap = new HashMap<String, String>();
		csiToPojoPriorityNameMap.put(Priority.NONE.name(), "Normal");
		csiToPojoPriorityNameMap.put(Priority.LOW.name(), "Low");
		csiToPojoPriorityNameMap.put(Priority.MEDIUM.name(), "Medium");
		csiToPojoPriorityNameMap.put(Priority.HIGH.name(), "High");
		for (String key : csiToPojoPriorityNameMap.keySet()) {
			pojoToCsiPriorityNameMap.put(csiToPojoPriorityNameMap.get(key), key);
		}
	}
	
	enum DateTimeResolution {
		Year,
		Date,
		Time
	}
	
	protected EntityDAO() {
	}

	public Projection load(ManagedObjectProxy obj, String attributeName) {
		return load(obj, attributeName, null);
	}
	
	protected boolean loadBasicWithLazy = false;
	
	public Projection loadFull(ManagedObjectProxy obj) {
		Projection proj = Projection.FULL;
		Entity csiEntity = loadObjectState((ManagedIdentifiableProxy) obj, proj);
		if (csiEntity != null) {
			copyObjectState((ManagedIdentifiableProxy) obj, csiEntity, proj);
		}
		return proj;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection proj = getProjection(attributeName);
		if (proj != null) {
			if (Projection.SECURITY == proj) {
				AccessControlFields csiAccessControlFields = loadAccessControlFields((ManagedIdentifiableProxy) obj);
				if (csiAccessControlFields != null) {
					copyAccessControlFields((ManagedIdentifiableProxy) obj, csiAccessControlFields);
				}
			} else {
				Entity csiEntity = loadObjectState((ManagedIdentifiableProxy) obj, proj);
				if (csiEntity != null) {
					copyObjectState((ManagedIdentifiableProxy) obj, csiEntity, proj);
				}
			}
		} else {
			// if projection is null, it is lazy loading of a specific attribute
			if (loadBasicWithLazy) { // may load basic projection for some type of objects
				if (obj.isPooled(EntityInfo.Attributes.name.name())) {
					Projection basicproj = Projection.BASIC;
					Entity csiEntity = loadObjectState((ManagedIdentifiableProxy) obj, basicproj);
					if (csiEntity != null) {
						projManager.copyLoadedProjection(obj, csiEntity, basicproj);
					}
				}
			}
			// lazy loading an attribute
			loadAndCopyObjectState((ManagedIdentifiableProxy) obj, attributeName, key);
		}
		return proj;
	}
	
	public abstract Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj);
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObj, Projection proj) {
		Entity csiEntity = (Entity) csiObj;
		icom.jpa.Identifiable pojoIdentifiable = (icom.jpa.Identifiable) obj.getPojoObject();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		PersistenceContext context = obj.getPersistenceContext();
		
		if (isPartOfProjection(IdentifiableInfo.Attributes.changeToken.name(),  proj)) {
			try {
				SnapshotId sid = csiEntity.getSnapshotId();
				if (sid != null) {
					assignChangeToken(pojoIdentifiable, sid.toString());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.name.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.name.name(), proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name(), csiEntity.getName());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.createdBy.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.createdBy.name(),  proj)) {
			try {
				Actor csiActor  = csiEntity.getCreator();
				if (csiActor != null) {
					ManagedIdentifiableProxy actorObj = getEntityProxy(context, csiActor);
					Persistent pojoActor = actorObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.createdBy.name(), pojoActor);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.creationDate.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.creationDate.name(),  proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.creationDate.name(), csiEntity.getCreatedOn());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.lastModifiedBy.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.lastModifiedBy.name(),  proj)) {
			try {
				Actor csiActor  = csiEntity.getModifiedBy();
				if (csiActor != null) {
					ManagedIdentifiableProxy actorObj = getEntityProxy(context, csiActor);
					Persistent pojoActor = actorObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.lastModifiedBy.name(), pojoActor);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.lastModificationDate.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.lastModificationDate.name(),  proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.lastModificationDate.name(), csiEntity.getModifiedOn());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.parent.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.parent.name(),  proj)) {
			try {
				Entity csiEntityParent  = csiEntity.getParent();
				Persistent parentPojoEntity = null;
				if (csiEntityParent != null) {
					ManagedIdentifiableProxy parentObj = getEntityProxy(context, csiEntityParent);
					parentPojoEntity = parentObj.getPojoIdentifiable();
				}
				assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name(), parentPojoEntity);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.attachedMarkers.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.attachedMarkers.name(), proj)) {
			try {
				HashSet<Persistent> v = new HashSet<Persistent>();
				Set<Marker> csiAttachedMarkers  = csiEntity.getAttachedMarkers();
				if (csiAttachedMarkers != null) {
					Iterator<Marker> iter = csiAttachedMarkers.iterator();
					while (iter.hasNext()) {
						Marker csiMarker = iter.next();
						ManagedIdentifiableProxy markerObj = getEntityProxy(context, csiMarker);
						Persistent pojoAttachedMarker = markerObj.getPojoIdentifiable();
						v.add(pojoAttachedMarker);
					}
				}
				assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.attachedMarkers.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		if (EntityInfo.Attributes.categoryApplications.name().equals(attributeName)) {
			CategoryApplicationDAO.getInstance().loadCategoryApplicationsOnEntity(obj, Projection.FULL);
		}
		if (EntityInfo.Attributes.tagApplications.name().equals(attributeName)) {
			LabelApplicationDAO.getInstance().loadLabelApplicationsOnEntity(obj, Projection.FULL);
		}
		if (RelationshipBondableInfo.Attributes.relationships.name().equals(attributeName)) {
			BondDAO.getInstance().loadBondsOnBondableEntity(obj, Projection.FULL);
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
		if (! obj.isDirtyDependent()) {
			DAOContext context = beginUpdateObject(obj);
			if (context != null) {
				updateObjectState(obj, context);
				concludeUpdateObject(obj, context, Projection.BASIC);
			}
		}
		saveAccessControlFieldsOnDirtyEntity(obj);
		updateMetadataOnEntity(obj);
	}
	
	public void saveNew(ManagedIdentifiableProxy obj) {
		DAOContext context = beginCreateObject(obj);
		if (context != null) {
			updateNewObjectState(obj, context);
			Entity csiEntity = concludeCreateObject(obj, context, Projection.BASIC);
			if (csiEntity != null) {
				copyObjectState(obj, csiEntity, Projection.BASIC);
			}
		}
		saveAccessControlFieldsOnNewEntity(obj);
		updateMetadataOnNewEntity(obj);
	}
	
	public abstract DAOContext beginUpdateObject(ManagedIdentifiableProxy obj);
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		EntityUpdater updater = (EntityUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		if (isChanged(obj, EntityInfo.Attributes.name.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
			updater.setName(name);
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public abstract Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj);
	
	public abstract DAOContext beginCreateObject(ManagedIdentifiableProxy obj);
	
	public abstract Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj);

	abstract public void delete(ManagedIdentifiableProxy obj);
	
	public AccessControlFields loadAccessControlFields(ManagedIdentifiableProxy obj) {
		AccessControlFields csiAccessControlFields = null;
		try {
			AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(id);
			csiAccessControlFields = control.loadEntityFields(entityHandle);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiAccessControlFields;
	}
	
	public void copyAccessControlFields(ManagedObjectProxy obj, AccessControlFields csiAccessControlFields) {
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoEntity = obj.getPojoObject();
		
		try {
			AccessorHandle accessorHandle = csiAccessControlFields.getOwner();
			if (accessorHandle != null) {
				Accessor csiAccessor = (Accessor) EntityUtils.getInstance().createEmptyEntity(accessorHandle.getCollabId());
				ManagedIdentifiableProxy ownerObj = getEntityProxy(context, csiAccessor);
				Persistent pojoOwner = ownerObj.getPojoIdentifiable();
				assignAttributeValue(pojoEntity, EntityInfo.Attributes.owner.name(), pojoOwner);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			Set<ACE> aceSet = csiAccessControlFields.getLocalACL();
			if (aceSet != null) {
				ManagedObjectProxy accessControlListObj = getNonIdentifiableDependentProxy(context, IcomBeanEnumeration.AccessControlList.name(), obj, EntityInfo.Attributes.accessControlList.name());
				accessControlListObj.getProviderProxy().copyLoadedProjection(accessControlListObj, aceSet, Projection.SECURITY);
				Persistent pojoAccessControlList = accessControlListObj.getPojoObject();
				assignAttributeValue(pojoEntity, EntityInfo.Attributes.accessControlList.name(), pojoAccessControlList);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
	}
	
	public void saveAccessControlFieldsOnDirtyEntity(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, EntityInfo.Attributes.owner.name()) ||
			isChanged(obj, EntityInfo.Attributes.accessControlList.name())) {
			DAOContext daoContext = beginUpdateAccessControlFields(obj);
			updateOwnerOnEntity(obj, daoContext);
			updateAccessControlFieldsOnEntity(obj, daoContext);
			concludeUpdateAccessControlFields(obj, daoContext);
		}
	}
	
	public void saveAccessControlFieldsOnNewEntity(ManagedIdentifiableProxy obj) {
		Persistent pojoEntity = obj.getPojoObject();
		Persistent pojoOwner = (Persistent) getAttributeValue(pojoEntity, EntityInfo.Attributes.owner.name());
		Persistent pojoAccessControlList = (Persistent) getAttributeValue(pojoEntity, EntityInfo.Attributes.accessControlList.name());
		if (pojoOwner != null || pojoAccessControlList != null) {
			DAOContext daoContext = beginCreateAccessControlFields(obj);
			updateOwnerOnEntity(obj, daoContext);
			createAccessControlFieldsOnEntity(obj, daoContext);
			concludeCreateAccessControlFields(obj, daoContext);
		}
	}
	
	public DAOContext beginUpdateAccessControlFields(ManagedIdentifiableProxy obj) {
		AccessControlFieldsUpdater updater = AccessFactory.getInstance().createAccessControlFieldsUpdater();
		DAOContext daoContext = new DAOContext(updater);
		return daoContext;
	}
	
	protected void updateOwnerOnEntity(ManagedIdentifiableProxy obj, DAOContext context) {
		AccessControlFieldsUpdater updater = (AccessControlFieldsUpdater) context.getUpdater();
		Persistent pojoEntity = obj.getPojoObject();
		if (isChanged(obj, EntityInfo.Attributes.owner.name())) {
			icom.jpa.Identifiable pojoOwner = (icom.jpa.Identifiable) getAttributeValue(pojoEntity, EntityInfo.Attributes.owner.name());
			if (pojoOwner != null) {
				CollabId accessorId = getCollabId(pojoOwner.getObjectId());
				AccessorHandle accessorHandle = (AccessorHandle) EntityUtils.getInstance().createHandle(accessorId);
				updater.setOwner(accessorHandle);
			}
		}
	}
	
	protected void updateAccessControlFieldsOnEntity(ManagedIdentifiableProxy obj, DAOContext context) {
		AccessControlFieldsUpdater updater = (AccessControlFieldsUpdater) context.getUpdater();
		Persistent pojoEntity = obj.getPojoObject();
		if (isChanged(obj, EntityInfo.Attributes.accessControlList.name())) {
			Persistent pojoAccessControlList = (Persistent) getAttributeValue(pojoEntity, EntityInfo.Attributes.accessControlList.name());
			if (pojoAccessControlList != null) {
				AccessControlListToSetOfAceDAO.getInstance().updateObjectState(pojoAccessControlList, updater);
			}
		}
	}
	
	public void concludeUpdateAccessControlFields(ManagedIdentifiableProxy obj, DAOContext context) {
		AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
		AccessControlFieldsUpdater updater = (AccessControlFieldsUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoEntity = obj.getPojoIdentifiable();
		CollabId entityId = getCollabId(pojoEntity.getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(entityId);
		try {
			control.updateEntityFields(entityHandle, updater);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateAccessControlFields(ManagedIdentifiableProxy obj) {
		return beginUpdateAccessControlFields(obj);
	}
	
	protected void createAccessControlFieldsOnEntity(ManagedIdentifiableProxy obj, DAOContext context) {
		updateAccessControlFieldsOnEntity(obj, context);
	}
	
	public void concludeCreateAccessControlFields(ManagedIdentifiableProxy obj, DAOContext context) {
		concludeUpdateAccessControlFields(obj, context);
	}
	
	// DocumentDAO overrides this method
	protected void updateCategoryApplication(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, EntityInfo.Attributes.categoryApplications.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(EntityInfo.Attributes.categoryApplications.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent catApp = (Persistent) holder.getValue();
					ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)catApp.getManagedObjectProxy();
					CategoryApplicationDAO.getInstance().saveNew(catAppObj);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(EntityInfo.Attributes.categoryApplications.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent catApp = (Persistent) holder.getValue();
					ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)catApp.getManagedObjectProxy();
					CategoryApplicationDAO.getInstance().delete(catAppObj);
				}
			}
		}
		
		Collection<icom.jpa.Identifiable> catApps = getCategoryApplications(obj.getPojoIdentifiable());
		if (catApps != null) {
			Iterator<icom.jpa.Identifiable> catAppsIter = catApps.iterator();
			while (catAppsIter.hasNext()) {
				Persistent pojoCatApp = catAppsIter.next();
				ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)pojoCatApp.getManagedObjectProxy();
				if (catAppObj.isDirty()) {
					CategoryApplicationDAO.getInstance().saveDirty(catAppObj);
				}
			}
		}
	}
	
	// DocumentDAO overrides this method
	protected void updateTagApplication(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, EntityInfo.Attributes.tagApplications.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(EntityInfo.Attributes.tagApplications.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent tagApp = (Persistent) holder.getValue();
					ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy)tagApp.getManagedObjectProxy();
					LabelApplicationDAO.getInstance().saveNew(tagAppObj);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(EntityInfo.Attributes.tagApplications.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent tagApp = (Persistent) holder.getValue();
					ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy)tagApp.getManagedObjectProxy();
					LabelApplicationDAO.getInstance().delete(tagAppObj);
				}
			}
		}
		
		Collection<icom.jpa.Identifiable> tagApps = getCategoryApplications(obj.getPojoIdentifiable());
		if (tagApps != null) {
			Iterator<icom.jpa.Identifiable> tagAppsIter = tagApps.iterator();
			while (tagAppsIter.hasNext()) {
				Persistent pojoTagApp = tagAppsIter.next();
				ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy)pojoTagApp.getManagedObjectProxy();
				if (tagAppObj.isDirty()) {
					LabelApplicationDAO.getInstance().saveDirty(tagAppObj);
				}
			}
		}
	}
	
	protected void updateMetadataOnEntity(ManagedIdentifiableProxy obj) {
		updateCategoryApplication(obj);
		updateTagApplication(obj);
	}

	// DocumentDAO overrides this method
	protected void updateCategoryApplicationOnNewEntity(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, EntityInfo.Attributes.categoryApplications.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(EntityInfo.Attributes.categoryApplications.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent catApp = (Persistent) holder.getValue();
					ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy) catApp.getManagedObjectProxy();
					CategoryApplicationDAO.getInstance().saveNew(catAppObj);
				}
			}
		}
	}
	
	// DocumentDAO overrides this method
	protected void updateTagApplicationOnNewEntity(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, EntityInfo.Attributes.tagApplications.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(EntityInfo.Attributes.tagApplications.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent tagApp = (Persistent) holder.getValue();
					ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy) tagApp.getManagedObjectProxy();
					LabelApplicationDAO.getInstance().saveNew(tagAppObj);
				}
			}
		}
	}
	
	protected void updateMetadataOnNewEntity(ManagedIdentifiableProxy obj) {
		updateCategoryApplicationOnNewEntity(obj);
		updateTagApplicationOnNewEntity(obj);
	}

	public String getName(Persistent pojoIdentifiable) {
		return (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
	}
	
	public Persistent getParent(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
	}
	
	public Persistent getOwner(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.owner.name());
	}
	
	public Collection<icom.jpa.Identifiable> getCategoryApplications(Persistent pojoIdentifiable) {
		return getIdentifiableCollection(pojoIdentifiable, EntityInfo.Attributes.categoryApplications.name());
	}
	
}
