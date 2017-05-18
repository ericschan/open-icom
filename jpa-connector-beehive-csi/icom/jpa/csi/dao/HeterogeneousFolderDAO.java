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

import icom.info.HeterogeneousFolderInfo;
import icom.info.beehive.BeehiveExtentInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.Artifact;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.HeterogeneousFolder;
import oracle.csi.HeterogeneousFolderHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.WorkspaceHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.HeterogeneousFolderControl;
import oracle.csi.controls.HeterogeneousFolderFactory;
import oracle.csi.filters.ListResult;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.HeterogeneousFolderUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class HeterogeneousFolderDAO extends FolderDAO {
	
	static HeterogeneousFolderDAO singleton = new HeterogeneousFolderDAO();
	
	public static HeterogeneousFolderDAO getInstance() {
		return singleton;
	}
	
	{
		fullAttributes.add(HeterogeneousFolderInfo.Attributes.elements);
	}
	
	{
		lazyAttributes.add(BeehiveExtentInfo.Attributes.versionControlConfiguration);
		lazyAttributes.add(BeehiveExtentInfo.Attributes.categoryConfiguration);
	}
	
	protected HeterogeneousFolderDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return HeterogeneousFolderHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		HeterogeneousFolder csiHeterogeneousFolder = null;
		try {
			HeterogeneousFolderControl control = ControlLocator.getInstance().getControl(HeterogeneousFolderControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			HeterogeneousFolderHandle heterogeneousFolderHandle = (HeterogeneousFolderHandle) EntityUtils.getInstance().createHandle(id);
			csiHeterogeneousFolder = control.loadHeterogeneousFolder(heterogeneousFolderHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiHeterogeneousFolder;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		HeterogeneousFolder csiFolder = (HeterogeneousFolder) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(HeterogeneousFolderInfo.Attributes.elements.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(HeterogeneousFolderInfo.Attributes.elements.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(HeterogeneousFolderInfo.Attributes.elements.name());
				ListResult<? extends Artifact> list = csiFolder.getElements();
				Vector<Persistent> v = new Vector<Persistent>(list.size());
				for (Artifact csiArtifact : list) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedArtifact = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedArtifact.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiArtifact.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiArtifact);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, HeterogeneousFolderInfo.Attributes.elements.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		super.loadAndCopyObjectState(obj, attributeName, parameter);
		if (BeehiveExtentInfo.Attributes.versionControlConfiguration.name().equals(attributeName)) {
			VersionControlConfigurationDAO.getInstance().loadVersionControlConfiguration(obj, Projection.FULL);
		}
		if (BeehiveExtentInfo.Attributes.categoryConfiguration.name().equals(attributeName)) {
			CategoryConfigurationDAO.getInstance().loadCategoryConfiguration(obj, Projection.FULL);
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		HeterogeneousFolderUpdater updater = HeterogeneousFolderFactory
				.getInstance().createHeterogeneousFolderUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		HeterogeneousFolderControl control = ControlLocator.getInstance().getControl(HeterogeneousFolderControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		HeterogeneousFolderHandle heterogeneousFolderHandle = (HeterogeneousFolderHandle) EntityUtils
				.getInstance().createHandle(id);
		HeterogeneousFolderUpdater heterogeneousFolderUpdater = (HeterogeneousFolderUpdater) context.getUpdater();
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
			HeterogeneousFolder folder = control.updateHeterogeneousFolder(heterogeneousFolderHandle,
					heterogeneousFolderUpdater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, folder.getSnapshotId().toString());
			return folder;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	private void updateMetadataOnNewAndOldEntity(ManagedIdentifiableProxy obj) {
		Persistent pojoScope = obj.getPojoIdentifiable();
		
		Persistent pojoVersionControlConfig = getVersionControlConfiguration(pojoScope);
		if (pojoVersionControlConfig != null) {
			VersionControlConfigurationDAO.getInstance().save((ManagedIdentifiableProxy)pojoVersionControlConfig.getManagedObjectProxy());
		} else {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveExtentInfo.Attributes.versionControlConfiguration.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent versionControlConfig = (Persistent) holder.getValue();
					ManagedIdentifiableProxy versionControlConfigObj = (ManagedIdentifiableProxy)versionControlConfig.getManagedObjectProxy();
					VersionControlConfigurationDAO.getInstance().delete(versionControlConfigObj);
				}
			}
		}
		
		Persistent pojoCatConfig = getCategoryConfiguration(pojoScope);
		if (pojoCatConfig != null) {
			CategoryConfigurationDAO.getInstance().save((ManagedIdentifiableProxy)pojoCatConfig.getManagedObjectProxy());
		} else {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveExtentInfo.Attributes.categoryConfiguration.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent catConfig = (Persistent) holder.getValue();
					ManagedIdentifiableProxy catConfigObj = (ManagedIdentifiableProxy)catConfig.getManagedObjectProxy();
					CategoryConfigurationDAO.getInstance().delete(catConfigObj);
				}
			}
		}
	}
	
	protected void updateMetadataOnEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnEntity(obj);
		updateMetadataOnNewAndOldEntity(obj);
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		HeterogeneousFolderControl control = ControlLocator.getInstance()
				.getControl(HeterogeneousFolderControl.class);
		HeterogeneousFolderUpdater heterogeneousFolderUpdater = (HeterogeneousFolderUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		String name = getName(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance()
				.createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			if (entityHandle instanceof HeterogeneousFolderHandle) {
				HeterogeneousFolderHandle heterogeneousFolderHandle = (HeterogeneousFolderHandle) entityHandle;
				HeterogeneousFolder folder = control.createHeterogeneousFolder(id.getEid(),
						heterogeneousFolderHandle, name, heterogeneousFolderUpdater, proj);
				assignChangeToken(pojoIdentifiable, folder.getSnapshotId().toString());
				return folder;
			} else if (entityHandle instanceof WorkspaceHandle) {
				WorkspaceHandle workspaceHandle = (WorkspaceHandle) entityHandle;
				HeterogeneousFolder folder = control.createHeterogeneousFolder(id.getEid(),
						workspaceHandle, name,
						heterogeneousFolderUpdater, proj);
				assignChangeToken(pojoIdentifiable, folder.getSnapshotId().toString());
				return folder;
			}
			return null;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected void updateMetadataOnNewEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnNewEntity(obj);
		updateMetadataOnNewAndOldEntity(obj);
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		HeterogeneousFolderControl control = ControlLocator.getInstance().getControl(HeterogeneousFolderControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		HeterogeneousFolderHandle heterogeneousFolderHandle = (HeterogeneousFolderHandle) EntityUtils
					.getInstance().createHandle(id);
		try {
			control.deleteHeterogeneousFolder(heterogeneousFolderHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			//throw new PersistenceException(ex);
		}
	}
	
	public Persistent getVersionControlConfiguration(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveExtentInfo.Attributes.versionControlConfiguration.name());
	}
	
	public Persistent getCategoryConfiguration(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveExtentInfo.Attributes.categoryConfiguration.name());
	}
	
}
