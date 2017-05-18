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

import icom.info.AddressableInfo;
import icom.info.ScopeInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.CommunityHandle;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityAddress;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.TeamWorkspace;
import oracle.csi.TeamWorkspaceHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.WorkspaceControl;
import oracle.csi.controls.WorkspaceFactory;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.TeamWorkspaceUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class TeamWorkspaceDAO extends WorkspaceDAO {
	
	{
		basicAttributes.add(ScopeInfo.Attributes.description);
		basicAttributes.add(AddressableInfo.Attributes.entityAddresses);
		basicAttributes.add(AddressableInfo.Attributes.primaryAddress);
		//basicAttributes.add(AddressableInfo.Attributes.defaultAddressForScheme);
		//basicAttributes.add(AddressableInfo.Attributes.defaultAddressForType);
	}

	static TeamWorkspaceDAO singleton = new TeamWorkspaceDAO();
	
	public static TeamWorkspaceDAO getInstance() {
		return singleton;
	}
	
	protected TeamWorkspaceDAO() {	
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return TeamWorkspaceHandle.class;
	}

	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		TeamWorkspace csiTeamWorkspace = null;
		try {
			WorkspaceControl control = ControlLocator.getInstance().getControl(WorkspaceControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			TeamWorkspaceHandle teamWorkspaceHandle = (TeamWorkspaceHandle) EntityUtils.getInstance().createHandle(id);
			csiTeamWorkspace = control.loadTeamWorkspace(teamWorkspaceHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiTeamWorkspace;
	}
		
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		
		TeamWorkspace csiWorkspace = (TeamWorkspace) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
			
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(AddressableInfo.Attributes.primaryAddress.name(), lastLoadedProjection, proj)) {
			try {
				Object pojoPrimaryAddress = null;
				EntityAddress csiPrimaryAddress = csiWorkspace.getPrimaryAddress();
				if (csiPrimaryAddress != null) {
					ManagedObjectProxy primaryAddressObj = getNonIdentifiableDependentProxy(context, csiPrimaryAddress, obj, AddressableInfo.Attributes.primaryAddress.name());
					primaryAddressObj.getProviderProxy().copyLoadedProjection(primaryAddressObj, csiPrimaryAddress, proj);
					pojoPrimaryAddress = primaryAddressObj.getPojoObject();
					
				}
				assignAttributeValue(pojoIdentifiable, AddressableInfo.Attributes.primaryAddress.name(), pojoPrimaryAddress);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(AddressableInfo.Attributes.entityAddresses.name(), lastLoadedProjection, proj)) {
			try {
				HashSet<Object> pojoAddresses = new HashSet<Object>();
				Collection<EntityAddress> csiAddresses = csiWorkspace.getAddresses();
				if (csiAddresses != null) {
					for (EntityAddress csiAddress : csiAddresses) {
						ManagedObjectProxy addressObj = getNonIdentifiableDependentProxy(context, csiAddress, obj, AddressableInfo.Attributes.entityAddresses.name());
						addressObj.getProviderProxy().copyLoadedProjection(addressObj, csiAddress, proj);
						Object pojoEntityAddress = addressObj.getPojoObject();
						pojoAddresses.add(pojoEntityAddress);
					}
				}
				assignAttributeValue(pojoIdentifiable, AddressableInfo.Attributes.entityAddresses.name(), pojoAddresses);		
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		super.copyObjectState(obj, csiEntity, proj);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		TeamWorkspaceUpdater updater = (TeamWorkspaceUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, AddressableInfo.Attributes.primaryAddress.name())) {
			Persistent primaryAddress = (Persistent) getAttributeValue(pojoIdentifiable, AddressableInfo.Attributes.primaryAddress.name());
			EntityAddressDAO.getInstance().updateObjectState(primaryAddress, updater, EntityAddressDAO.Operand.SET_PRIMARY);
		}
		
		if (isChanged(obj, AddressableInfo.Attributes.entityAddresses.name())) {
			ArrayList<Persistent> modifiedAddresses = new ArrayList<Persistent>();
			ArrayList<Persistent> addedAddresses = new ArrayList<Persistent>();
			ArrayList<Persistent> removedAddresses = new ArrayList<Persistent>();
			Collection<Object> addresses = getObjectCollection(pojoIdentifiable, AddressableInfo.Attributes.entityAddresses.name());
			if (addresses != null) {
				for (Object address : addresses) {
					Persistent pojo = (Persistent) address;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.hasAttributeChanges()) {
						modifiedAddresses.add(pojo);
					}
				}
			}
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(AddressableInfo.Attributes.entityAddresses.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent address = (Persistent) holder.getValue();
					modifiedAddresses.remove(address);
					addedAddresses.add(address);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(AddressableInfo.Attributes.entityAddresses.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent address = (Persistent) holder.getValue();
					modifiedAddresses.remove(address);
					removedAddresses.add(address);
				}
			}
			
			if (modifiedAddresses.size() > 0) {
				// simply recreate all addresses, since CSI does not support modify
				EntityAddressDAO.getInstance().updateObjectState(addresses, updater);
			} else {
				for (Persistent pojoEntityAddress : addedAddresses) {
					EntityAddressDAO.getInstance().updateObjectState(pojoEntityAddress, updater, EntityAddressDAO.Operand.ADD);
				}
				for (Persistent pojoEntityAddress : removedAddresses) {
					EntityAddressDAO.getInstance().updateObjectState(pojoEntityAddress, updater, EntityAddressDAO.Operand.REMOVE);
				}
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		TeamWorkspaceUpdater updater = WorkspaceFactory.getInstance().createTeamWorkspaceUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		WorkspaceControl control = ControlLocator.getInstance().getControl(WorkspaceControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		TeamWorkspaceHandle teamWorkspaceHandle = (TeamWorkspaceHandle) EntityUtils.getInstance().createHandle(id);
		TeamWorkspaceUpdater teamWorkspaceUpdater = (TeamWorkspaceUpdater) context.getUpdater();
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
			TeamWorkspace workspace = control.updateTeamWorkspace(teamWorkspaceHandle, teamWorkspaceUpdater,
					updateMode, proj);
			assignChangeToken(pojoIdentifiable, workspace.getSnapshotId().toString());
			return workspace;
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
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		WorkspaceControl control = ControlLocator.getInstance().getControl(WorkspaceControl.class);
		TeamWorkspaceUpdater teamWorkspaceUpdater = (TeamWorkspaceUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = getName(pojoIdentifiable);
		Persistent parent = getParent(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		CommunityHandle communityHandle = (CommunityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			TeamWorkspace workspace = control.createTeamWorkspace(id.getEid(), communityHandle, name, 
					teamWorkspaceUpdater, proj);
			assignChangeToken(pojoIdentifiable, workspace.getSnapshotId().toString());
			return workspace;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		WorkspaceControl control = ControlLocator.getInstance().getControl(WorkspaceControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		TeamWorkspaceHandle teamWorkspaceHandle = (TeamWorkspaceHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteWorkspace(teamWorkspaceHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

}
