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
import icom.info.RoleInfo;
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

import javax.persistence.PersistenceException;

import oracle.csi.AccessorHandle;
import oracle.csi.AssignedRole;
import oracle.csi.AssignedRoleHandle;
import oracle.csi.BaseAccessor;
import oracle.csi.BaseAccessorHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.RoleDefinition;
import oracle.csi.RoleDefinitionHandle;
import oracle.csi.Scope;
import oracle.csi.ScopeHandle;
import oracle.csi.SnapshotId;
import oracle.csi.controls.AccessControl;
import oracle.csi.controls.AccessFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.filters.AssignedRoleListFilter;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.ScopePredicate;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AssignedRoleUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class AssignedRoleDAO extends EntityDAO {
	
	static AssignedRoleDAO singleton = new AssignedRoleDAO();
	
	public static AssignedRoleDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(RoleInfo.Attributes.description);
		basicAttributes.add(RoleInfo.Attributes.roleDefinition);
		basicAttributes.add(RoleInfo.Attributes.memberAccessors);
		basicAttributes.add(RoleInfo.Attributes.assignedScope);
	}
	
	{

	}

	protected AssignedRoleDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return AssignedRoleHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		AssignedRole csiAssignedRole = null;
		try {
			AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
			CollabId collabId = getCollabId(obj.getObjectId());
			AssignedRoleHandle csiAssignedRoleHandle = (AssignedRoleHandle) EntityUtils.getInstance().createHandle(collabId);
			csiAssignedRole = control.loadAssignedRole(csiAssignedRoleHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiAssignedRole;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, csiEntity, proj);
		
		AssignedRole csiAssignedRole = (AssignedRole) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(RoleInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, RoleInfo.Attributes.description.name(), csiAssignedRole.getDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(RoleInfo.Attributes.roleDefinition.name(), lastLoadedProjection, proj)) {
			try {
				RoleDefinition csiRoleDefinition  = csiAssignedRole.getRoleDefinition();
				if (csiRoleDefinition != null) {
					ManagedIdentifiableProxy roleDefinitionObj = getEntityProxy(context, csiRoleDefinition);
					Persistent roleDefinition = roleDefinitionObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, RoleInfo.Attributes.roleDefinition.name(), roleDefinition);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(RoleInfo.Attributes.memberAccessors.name(), lastLoadedProjection, proj)) {
			/*
			try {
				Set<BaseAccessor> csiMemberBaseAccessors = csiAssignedRole.getAccessors();
				HashSet<icom.jpa.spi.Identifiable> pojoMemberAccessors = new HashSet<icom.jpa.spi.Identifiable>();
				Iterator<BaseAccessor> csiMemberBaseAccessorsIter = csiMemberBaseAccessors.iterator();
				while (csiMemberBaseAccessorsIter.hasNext()) {
					ManagedIdentifiableProxy memberAccessorObj = getEntityProxy(context, csiMemberBaseAccessorsIter.next());
					pojoMemberAccessors.add(memberAccessorObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, RoleInfo.Attributes.memberAccessors.name(), pojoMemberAccessors);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			*/
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(RoleInfo.Attributes.memberAccessors.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(RoleInfo.Attributes.memberAccessors.name());
				Set<BaseAccessor> csiMemberBaseAccessors = csiAssignedRole.getAccessors();
				HashSet<Persistent> pojoMemberActors = new HashSet<Persistent>(csiMemberBaseAccessors.size());
				for (BaseAccessor csiBaseAccessor : csiMemberBaseAccessors) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent accessor = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(accessor.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiBaseAccessor.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiBaseAccessor);
						pojoMemberActors.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						pojoMemberActors.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, RoleInfo.Attributes.memberAccessors.name(), pojoMemberActors);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
		}
		
		if (isBetweenProjections(RoleInfo.Attributes.assignedScope.name(), lastLoadedProjection, proj)) {
			try {
				Scope csiScope  = csiAssignedRole.getAssignedScope();
				if (csiScope != null) {
					ManagedIdentifiableProxy scopeObj = getEntityProxy(context, csiScope);
					Persistent scope = scopeObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, RoleInfo.Attributes.assignedScope.name(), scope);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		AssignedRoleUpdater updater = (AssignedRoleUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, RoleInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, RoleInfo.Attributes.description.name());
			updater.setDescription(description);
		}
		
		if (isChanged(obj, RoleInfo.Attributes.memberAccessors.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(RoleInfo.Attributes.memberAccessors.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent memberAccessor = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberAccessorObj = (ManagedIdentifiableProxy) memberAccessor.getManagedObjectProxy();
					CollabId id = getCollabId(memberAccessorObj.getObjectId());
					AccessorHandle accessorHandle = (AccessorHandle) EntityUtils.getInstance().createHandle(id);
					updater.addAccessor(accessorHandle);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(RoleInfo.Attributes.memberAccessors.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent memberAccessor = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberAccessorObj = (ManagedIdentifiableProxy) memberAccessor.getManagedObjectProxy();
					CollabId id = getCollabId(memberAccessorObj.getObjectId());
					AccessorHandle accessorHandle = (AccessorHandle) EntityUtils.getInstance().createHandle(id);
					updater.removeAccessor(accessorHandle);
				}
			}
		}
		
		if (isChanged(obj, RoleInfo.Attributes.assignedScope.name())) {
			Persistent assignedScope = (Persistent) getAttributeValue(pojoIdentifiable, RoleInfo.Attributes.assignedScope.name());
			if (assignedScope != null) {
				ManagedIdentifiableProxy assignedScopeObj = (ManagedIdentifiableProxy) assignedScope.getManagedObjectProxy();
				CollabId id = getCollabId(assignedScopeObj.getObjectId());
				ScopeHandle assignedScopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(id);
				updater.setAssignedScope(assignedScopeHandle);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		AssignedRoleUpdater updater = AccessFactory.getInstance().createAssignedRoleUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		AssignedRoleHandle assignedRoleHandle = (AssignedRoleHandle) EntityUtils.getInstance().createHandle(id);
		AssignedRoleUpdater assignedRoleUpdater = (AssignedRoleUpdater) context.getUpdater();
		Object changeToken = ((ManagedIdentifiableProxy)obj.getPojoIdentifiable().getManagedObjectProxy()).getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoRole = obj.getPojoIdentifiable();
		try {
			AssignedRole assignedRole = control.updateAssignedRole(assignedRoleHandle, assignedRoleUpdater, updateMode, proj);
			assignChangeToken(pojoRole, assignedRole.getSnapshotId().toString());
			return assignedRole;
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
		AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
		icom.jpa.Identifiable pojoRole = obj.getPojoIdentifiable();
		
		CollabId id = getCollabId(obj.getObjectId());
		Eid eid = id.getEid();
		
		Persistent parentScope = (Persistent) getAttributeValue(pojoRole, EntityInfo.Attributes.parent.name());
		ManagedIdentifiableProxy parentScopeObj = (ManagedIdentifiableProxy) parentScope.getManagedObjectProxy();
		CollabId parentScopeId = getCollabId(parentScopeObj.getObjectId());
		ScopeHandle parentScopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(parentScopeId);
		
		String name = (String) getAttributeValue(pojoRole, EntityInfo.Attributes.name.name());
		String description = (String) getAttributeValue(pojoRole, RoleInfo.Attributes.description.name());
		
		Persistent roleDefinition = (Persistent) getAttributeValue(pojoRole, RoleInfo.Attributes.roleDefinition.name());
		ManagedIdentifiableProxy roleDefinitionObj = (ManagedIdentifiableProxy) roleDefinition.getManagedObjectProxy();
		CollabId roleDefinitionId = getCollabId(roleDefinitionObj.getObjectId());
		RoleDefinitionHandle roleDefinitionHandle = (RoleDefinitionHandle) EntityUtils.getInstance().createHandle(roleDefinitionId);
		
		Persistent assignedScope = (Persistent) getAttributeValue(pojoRole, RoleInfo.Attributes.assignedScope.name());
		ManagedIdentifiableProxy assignedScopeObj = (ManagedIdentifiableProxy) assignedScope.getManagedObjectProxy();
		CollabId assignedScopeId = getCollabId(assignedScopeObj.getObjectId());
		ScopeHandle assignedScopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(assignedScopeId);
		
		AssignedRoleUpdater assignedRoleUpdater = (AssignedRoleUpdater) context.getUpdater();
		
		UpdateMode updateMode = null;
		Object changeToken = ((ManagedIdentifiableProxy)pojoRole.getManagedObjectProxy()).getChangeToken();
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		
		Collection<icom.jpa.Identifiable> pojoAccessors = getIdentifiableCollection(pojoRole, RoleInfo.Attributes.memberAccessors.name());
		Set<BaseAccessorHandle> accessorHandles = null;
		if (pojoAccessors != null) {
			accessorHandles = new HashSet<BaseAccessorHandle>(pojoAccessors.size());
			for (Persistent pojoAccessor : pojoAccessors) {
				ManagedIdentifiableProxy accessorObj = (ManagedIdentifiableProxy) pojoAccessor.getManagedObjectProxy();
				CollabId accessorId = getCollabId(accessorObj.getObjectId());
				BaseAccessorHandle accessorHandle = (BaseAccessorHandle) EntityUtils.getInstance().createHandle(accessorId);
				accessorHandles.add(accessorHandle);
			}
		}
		
		try {
			AssignedRole assignedRole = control.createAssignedRole(eid, parentScopeHandle, name, description, roleDefinitionHandle, assignedScopeHandle, accessorHandles, assignedRoleUpdater, updateMode, proj);	
			assignChangeToken(pojoRole, assignedRole.getSnapshotId().toString());
			return assignedRole;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

	// need to override the method in EntityDAO, owner of workspace coordinator role assignment is set in workspace updater
	protected void updateOwnerOnEntity(ManagedIdentifiableProxy obj, DAOContext context) {
		String name = (String) getAttributeValue(obj.getPojoIdentifiable(), EntityInfo.Attributes.name.name());
		if (name == null || ! name.equals("workspace-coordinator")) {
			super.updateOwnerOnEntity(obj, context);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		AssignedRoleHandle assignedRoleHandle = (AssignedRoleHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteAssignedRole(assignedRoleHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			String name = (String) getAttributeValue(obj.getPojoIdentifiable(), EntityInfo.Attributes.name.name());
			if (name == null || ! name.equals("workspace-coordinator")) {
				throw new PersistenceException(ex);
			}
		}
	}
	
	public Set<Persistent> loadRoles(ManagedIdentifiableProxy obj, ScopeHandle csiScopeHandle, Projection proj) {	
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoRoles = null;
		try {
			AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
			AssignedRoleListFilter assignedRoleListFilter = AccessFactory.getInstance().createAssignedRoleListFilter();
			if (csiScopeHandle != null) {
				ScopePredicate predicate = assignedRoleListFilter.createScopePredicate(csiScopeHandle);
				assignedRoleListFilter.setPredicate(predicate);
			}
			assignedRoleListFilter.setProjection(proj);
			ListResult<AssignedRole> csiAssignedRoles = control.listAssignedRoles(assignedRoleListFilter);
			if (csiAssignedRoles != null) {
				pojoRoles = new HashSet<Persistent>(csiAssignedRoles.size());
				for (AssignedRole csiAssignedRole : csiAssignedRoles) {
					ManagedIdentifiableProxy roleObj = getEntityProxy(context, csiAssignedRole);
					roleObj.getProviderProxy().copyLoadedProjection(roleObj, csiAssignedRole, proj);
					pojoRoles.add(roleObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoRoles;
	}

}
