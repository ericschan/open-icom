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


import com.oracle.beehive.AccessControlFields;
import com.oracle.beehive.AccessControlFieldsUpdater;
import com.oracle.beehive.Accessor;
import com.oracle.beehive.Ace;
import com.oracle.beehive.Actor;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Entity;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.EntityUpdater;
import com.oracle.beehive.Marker;
import com.oracle.beehive.Priority;

import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.IdentifiableInfo;
import icom.info.RelationshipBondableInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkDataAccessUtilsImpl;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.persistence.PersistenceException;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;


public abstract class EntityDAO extends BdkIdentifiableDAO  {
	
	{
	    basicAttributes.add(EntityInfo.Attributes.objectId);
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
	
	static public HashMap<String, String> bdkToPojoPriorityNameMap;
	static public HashMap<String, String> pojoToBdkPriorityNameMap;
	
	{
		bdkToPojoPriorityNameMap = new HashMap<String, String>();
		pojoToBdkPriorityNameMap = new HashMap<String, String>();
		bdkToPojoPriorityNameMap.put(Priority.NONE.name(), "Normal");
		bdkToPojoPriorityNameMap.put(Priority.LOW.name(), "Low");
		bdkToPojoPriorityNameMap.put(Priority.MEDIUM.name(), "Medium");
		bdkToPojoPriorityNameMap.put(Priority.HIGH.name(), "High");
		for (String key : bdkToPojoPriorityNameMap.keySet()) {
			pojoToBdkPriorityNameMap.put(bdkToPojoPriorityNameMap.get(key), key);
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
        
    protected boolean isLoadBasicWithLazy(String attributeName) {
        return loadBasicWithLazy;
    }
	
	public Projection loadFull(ManagedObjectProxy obj) {
		Projection proj = Projection.FULL;
		Entity bdkEntity = loadObjectState((ManagedIdentifiableProxy) obj, proj);
		if (bdkEntity != null) {
			copyObjectState((ManagedIdentifiableProxy) obj, bdkEntity, proj);
		}
		return proj;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		BdkProjectionManager projManager = (BdkProjectionManager) obj.getProviderProxy();
		Projection proj = getProjection(attributeName);
		if (proj != null) {
			if (Projection.SECURITY == proj) {
				AccessControlFields bdkAccessControlFields = loadAccessControlFields((ManagedIdentifiableProxy) obj);
				if (bdkAccessControlFields != null) {
					copyAccessControlFields((ManagedIdentifiableProxy) obj, bdkAccessControlFields);
				}
			} else {
				Entity bdkEntity = loadObjectState((ManagedIdentifiableProxy) obj, proj);
				if (bdkEntity != null) {
					copyObjectState((ManagedIdentifiableProxy) obj, bdkEntity, proj);
				}
			}
		} else {
			// if projection is null, it is lazy loading of a specific attribute
			if (isLoadBasicWithLazy(attributeName)) { // may load basic projection for some type of objects
				if (obj.isPooled(EntityInfo.Attributes.name.name())) {
					Projection basicproj = Projection.BASIC;
					Entity bdkEntity = loadObjectState((ManagedIdentifiableProxy) obj, basicproj);
					if (bdkEntity != null) {
						projManager.copyLoadedProjection(obj, bdkEntity, basicproj);
					}
				}
			}
			// lazy loading an attribute
			loadAndCopyObjectState((ManagedIdentifiableProxy) obj, attributeName, key);
		}
		return proj;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		BdkDataAccessUtilsImpl dataAccessUtils = (BdkDataAccessUtilsImpl) context.getDataAccessUtils();
		String collabId = obj.getObjectId().toString();
		Entity bdkEntity = dataAccessUtils.loadBdkEntity(context, collabId, proj);
		return bdkEntity;
	}
	
	public Entity loadObjectState(PersistenceContext context, String collabId, Projection proj) {
		BdkDataAccessUtilsImpl dataAccessUtils = (BdkDataAccessUtilsImpl) context.getDataAccessUtils();
		Entity bdkEntity = dataAccessUtils.loadBdkEntity(context, collabId, proj);
		return bdkEntity;
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkObj, Projection proj) {
        Entity bdkEntity = (Entity)bdkObj;
        Identifiable pojoIdentifiable = (Identifiable)obj.getPojoObject();
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isPartOfProjection(IdentifiableInfo.Attributes.changeToken.name(), proj)) {
            String sid = bdkEntity.getSnapshotId();
            if (sid != null) {
                assignChangeToken(pojoIdentifiable, sid);
            }
        }
        if (isBetweenProjections(EntityInfo.Attributes.name.name(), lastLoadedProjection, proj)) {
            assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name(), bdkEntity.getName());
        }
        if (isBetweenProjections(EntityInfo.Attributes.createdBy.name(), lastLoadedProjection, proj)) {
            Actor bdkActor = bdkEntity.getCreator();
            marshallAssignEntity(obj, EntityInfo.Attributes.createdBy.name(), bdkActor);
        }
        if (isBetweenProjections(EntityInfo.Attributes.creationDate.name(), lastLoadedProjection, proj)) {
            try {
                XMLGregorianCalendar bdkCreatedOn = bdkEntity.getCreatedOn();
                if (bdkCreatedOn != null) {
                    Date pojoCreationDate = bdkCreatedOn.toGregorianCalendar().getTime();
                    assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.creationDate.name(),
                                         pojoCreationDate);
                } else {
                    assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.creationDate.name(), null);
                }
            } catch (Exception ex) {
                // ignore
            }
        }
        if (isBetweenProjections(EntityInfo.Attributes.lastModifiedBy.name(), lastLoadedProjection, proj)) {
            Actor bdkActor = bdkEntity.getModifiedBy();
            marshallAssignEntity(obj, EntityInfo.Attributes.lastModifiedBy.name(), bdkActor);
        }
        if (isBetweenProjections(EntityInfo.Attributes.lastModificationDate.name(), lastLoadedProjection, proj)) {
            try {
                XMLGregorianCalendar bdkModifiedOn = bdkEntity.getModifiedOn();
                if (bdkModifiedOn != null) {
                    Date pojoLastModificationDate = bdkModifiedOn.toGregorianCalendar().getTime();
                    assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.lastModificationDate.name(),
                                         pojoLastModificationDate);
                } else {
                    assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.lastModificationDate.name(), null);
                }
            } catch (Exception ex) {
                // ignore
            }
        }
        if (isBetweenProjections(EntityInfo.Attributes.parent.name(), lastLoadedProjection, proj)) {
            Entity bdkParentEntity = bdkEntity.getParent();
            marshallAssignEntity(obj, EntityInfo.Attributes.parent.name(), bdkParentEntity);
        }
        if (isBetweenProjections(EntityInfo.Attributes.attachedMarkers.name(), lastLoadedProjection, proj)) {
            List<Marker> bdkAttachedMarkers = bdkEntity.getAttachedMarkers();
            marshallMergeAssignEntities(obj, EntityInfo.Attributes.attachedMarkers.name(), bdkAttachedMarkers,
                                      HashSet.class);
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
			Entity bdkEntity = concludeCreateObject(obj, context, Projection.BASIC);
			if (bdkEntity != null) {
				copyObjectState(obj, bdkEntity, Projection.BASIC);
			}
		}
		saveAccessControlFieldsOnNewEntity(obj);
		updateMetadataOnNewEntity(obj);
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		EntityUpdater updater = getBdkUpdater(obj);
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
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
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		EntityUpdater updater = (EntityUpdater) context.getUpdater();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			String params = getCreateObjectParameters(obj, context);
			PutMethod putMethod = preparePutMethod(resourceType, collabId, userContext.antiCSRF, proj, params);
			Entity bdkEntity = (Entity) bdkHttpUtil.execute(getBdkClass(obj), putMethod, updater, userContext.httpClient);
			if (proj != Projection.EMPTY) {
				String changeToken = bdkEntity.getSnapshotId();
				assignChangeToken(obj.getPojoIdentifiable(), changeToken);
			}
			return bdkEntity;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		EntityCreator creator = getBdkCreator(obj);
		EntityUpdater updater = getBdkUpdater(obj, creator);
		DAOContext context = new DAOContext(creator, updater);
		return context;
	}
	
	protected String getCreateObjectParameters(ManagedIdentifiableProxy obj, DAOContext context) {
		return null;
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		EntityCreator creator = (EntityCreator) context.getCreator();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String resourceType = id.getResourceType();
			String params = getCreateObjectParameters(obj, context);
			PostMethod postMethod = preparePostMethod(resourceType, userContext.antiCSRF, proj, params);
			Entity bdkEntity = (Entity) bdkHttpUtil.execute(getBdkClass(obj), postMethod, creator, userContext.httpClient);
			String objectId = bdkEntity.getCollabId().getId();
			obj.setObjectId(objectId);
			assignObjectId(obj.getPojoIdentifiable(), objectId);
			if (proj != Projection.EMPTY) {
				String changeToken = bdkEntity.getSnapshotId();
				assignChangeToken(obj.getPojoIdentifiable(), changeToken);
			}
			// re-cache the object with the server assigned id
			// the server may assign a new object id rather than use the client assigned id
			obj.getPersistenceContext().recacheIdentifiableDependent(obj);  
			return bdkEntity;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

	public void delete(ManagedIdentifiableProxy obj) {
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			DeleteMethod deleteMethod = prepareDeleteMethod(resourceType, collabId, userContext.antiCSRF);
			bdkHttpUtil.execute(getBdkClass(obj), deleteMethod, userContext.httpClient);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected abstract Class<?> getBdkClass(ManagedObjectProxy obj);	
	protected abstract EntityUpdater getBdkUpdater(ManagedObjectProxy obj);
	protected abstract EntityUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator);
	protected abstract EntityCreator getBdkCreator(ManagedObjectProxy obj);
	
	public AccessControlFields loadAccessControlFields(ManagedIdentifiableProxy obj) {
		BeeId id = getBeeId(obj.getObjectId().toString());
		String collabId = id.getId();
		String resourceType = id.getResourceType();
		GetMethod method = prepareGetMethod(resourceType + "/ac", collabId, null);
		
		PersistenceContext context = obj.getPersistenceContext();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
			AccessControlFields bdkAccessControlFields = (AccessControlFields) bdkHttpUtil.execute(AccessControlFields.class, method, userContext.httpClient);
			return bdkAccessControlFields;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void copyAccessControlFields(ManagedObjectProxy obj, AccessControlFields bdkAccessControlFields) {
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoEntity = obj.getPojoObject();
		
		try {
			BeeId accessorId = bdkAccessControlFields.getOwner();
			if (accessorId != null) {
				String collabId = accessorId.getId();
				String resourceType = accessorId.getResourceType();
				BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
				GetMethod method = prepareGetMethod(resourceType, collabId, Projection.EMPTY);
				Accessor bdkAccessor = (Accessor) bdkHttpUtil.execute(Accessor.class, method, userContext.httpClient);
				ManagedIdentifiableProxy ownerObj = getEntityProxy(context, bdkAccessor);
				Persistent pojoOwner = ownerObj.getPojoIdentifiable();
				assignAttributeValue(pojoEntity, EntityInfo.Attributes.owner.name(), pojoOwner);
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
		
		try {
			List<Ace> aceSet = bdkAccessControlFields.getLocalACLs();
			if (aceSet != null) {
				ManagedObjectProxy accessControlListObj = getNonIdentifiableDependentProxy(context, IcomBeanEnumeration.AccessControlList.name(), obj, EntityInfo.Attributes.accessControlList.name());
				accessControlListObj.getProviderProxy().copyLoadedProjection(accessControlListObj, aceSet, Projection.SECURITY);
				Persistent pojoAccessControlList = accessControlListObj.getPojoObject();
				assignAttributeValue(pojoEntity, EntityInfo.Attributes.accessControlList.name(), pojoAccessControlList);
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
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

	public DAOContext beginUpdateAccessControlFields(ManagedIdentifiableProxy obj) {
		AccessControlFieldsUpdater updater = new AccessControlFieldsUpdater();
		DAOContext daoContext = new DAOContext(updater);
		return daoContext;
	}
	
	protected void updateOwnerOnEntity(ManagedIdentifiableProxy obj, DAOContext context) {
		AccessControlFieldsUpdater updater = (AccessControlFieldsUpdater) context.getUpdater();
		Persistent pojoEntity = obj.getPojoObject();
		if (isChanged(obj, EntityInfo.Attributes.owner.name())) {
			Identifiable pojoOwner = (Identifiable) getAttributeValue(pojoEntity, EntityInfo.Attributes.owner.name());
			if (pojoOwner != null) {
				BeeId accessorId = getBeeId(pojoOwner.getObjectId().toString());
				updater.setOwner(accessorId);
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
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			PutMethod putMethod = preparePutMethod(resourceType + "/ac", collabId, userContext.antiCSRF, Projection.EMPTY);
			AccessControlFieldsUpdater updater = (AccessControlFieldsUpdater) context.getUpdater();
			bdkHttpUtil.execute(AccessControlFields.class, putMethod, updater, userContext.httpClient);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
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
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();
        if (isChanged(obj, EntityInfo.Attributes.categoryApplications.name())) {
            Collection<Persistent> catApps = getCategoryApplications(pojoIdentifiable);
            AttributeChangeSet changeSet = getAttributeChanges(obj, catApps, EntityInfo.Attributes.categoryApplications.name());
            for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)addedPojoObject.getManagedObjectProxy();
                CategoryApplicationDAO.getInstance().saveNew(catAppObj);
            }
            for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)removedPojoObject.getManagedObjectProxy();
                CategoryApplicationDAO.getInstance().delete(catAppObj);
            }
            for (Persistent modifiedPojoObject : changeSet.modifiedPojoObjects) {
                ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)modifiedPojoObject.getManagedObjectProxy();
                CategoryApplicationDAO.getInstance().saveDirty(catAppObj);
            }
        }
    }

    // DocumentDAO overrides this method
    protected void updateTagApplication(ManagedIdentifiableProxy obj) {
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();
        if (isChanged(obj, EntityInfo.Attributes.tagApplications.name())) {
            Collection<Persistent> tagApps = getTagApplications(pojoIdentifiable);
            AttributeChangeSet changeSet = getAttributeChanges(obj, tagApps, EntityInfo.Attributes.tagApplications.name());
            for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy)addedPojoObject.getManagedObjectProxy();
                LabelApplicationDAO.getInstance().saveNew(tagAppObj);
            }
            for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy)removedPojoObject.getManagedObjectProxy();
                LabelApplicationDAO.getInstance().delete(tagAppObj);
            }
            for (Persistent modifiedPojoObject : changeSet.modifiedPojoObjects) {
                ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy)modifiedPojoObject.getManagedObjectProxy();
                LabelApplicationDAO.getInstance().saveDirty(tagAppObj);
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
                for (ValueHolder holder : addedObjects) {
                    Persistent catApp = (Persistent)holder.getValue();
                    ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)catApp.getManagedObjectProxy();
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
                for (ValueHolder holder : addedObjects) {
                    Persistent tagApp = (Persistent)holder.getValue();
                    ManagedIdentifiableProxy tagAppObj = (ManagedIdentifiableProxy)tagApp.getManagedObjectProxy();
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

    public Collection<Persistent> getCategoryApplications(Persistent pojoIdentifiable) {
        return getPersistentCollection(pojoIdentifiable, EntityInfo.Attributes.categoryApplications.name());
    }

    public Collection<Persistent> getTagApplications(Persistent pojoIdentifiable) {
        return getPersistentCollection(pojoIdentifiable, EntityInfo.Attributes.tagApplications.name());
    }
	
}
