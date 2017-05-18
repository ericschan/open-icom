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

import icom.info.GroupInfo;
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

import oracle.csi.Actor;
import oracle.csi.ActorHandle;
import oracle.csi.CollabId;
import oracle.csi.Community;
import oracle.csi.CommunityHandle;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.Group;
import oracle.csi.GroupHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.ScopeHandle;
import oracle.csi.SnapshotId;
import oracle.csi.TeamWorkspace;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.GroupFactory;
import oracle.csi.controls.UserDirectoryControl;
import oracle.csi.filters.GroupListFilter;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.ParentPredicate;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.GroupUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.MemberUpdateMode;
import oracle.csi.util.UpdateMode;

public class GroupDAO extends AccessorDAO {
	
	static GroupDAO singleton = new GroupDAO();
	
	public static GroupDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(GroupInfo.Attributes.description);
	}
	
	{
		fullAttributes.add(GroupInfo.Attributes.assignedScopes);
		fullAttributes.add(GroupInfo.Attributes.memberActors);
		fullAttributes.add(GroupInfo.Attributes.memberGroups);
		fullAttributes.add(GroupInfo.Attributes.teamSpace);
		
	}

	protected GroupDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return GroupHandle.class;
	}
	
	public void saveDirty(ManagedIdentifiableProxy obj) {
		if (obj.getProviderClassName() == null || ! obj.getProviderClassName().equals("AllUsersGroup")) {
			super.saveDirty(obj);
		} else {
			saveAccessControlFieldsOnDirtyEntity(obj);
			updateMetadataOnEntity(obj);
		}
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Group csiGroup = null;
		try {
			UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			GroupHandle csiGroupHandle = (GroupHandle) EntityUtils.getInstance().createHandle(id);
			csiGroup = control.loadGroup(csiGroupHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiGroup;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, csiEntity, proj);
		
		Group csiGroup = (Group) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(GroupInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.description.name(), csiGroup.getDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(GroupInfo.Attributes.assignedScopes.name(), lastLoadedProjection, proj)) {
			/*
			try {
				Set<Community> csiCommunities = csiGroup.getMemberships();
				HashSet<Persistent> pojoCommunities = new HashSet<Persistent>();
				Iterator<Community> csiCommunitiesIter = csiCommunities.iterator();
				while (csiCommunitiesIter.hasNext()) {
					ManagedIdentifiableProxy communityObj = getEntityProxy(context, csiCommunitiesIter.next());
					pojoCommunities.add(communityObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.assignedScopes.name(), pojoCommunities);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			*/
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(GroupInfo.Attributes.assignedScopes.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(GroupInfo.Attributes.assignedScopes.name());
				Set<Community> csiCommunities = csiGroup.getMemberships();
				HashSet<Persistent> pojoCommunities = new HashSet<Persistent>(csiCommunities.size());
				for (Community csiCommunity : csiCommunities) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedCommunity = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedCommunity.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiCommunity.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiCommunity);
						pojoCommunities.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						pojoCommunities.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.assignedScopes.name(), pojoCommunities);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(GroupInfo.Attributes.memberActors.name(), lastLoadedProjection, proj)) {
			/*
			try {
				Set<Actor> csiMemberActors = csiGroup.getMemberActors();
				HashSet<Persistent> pojoMemberActors = new HashSet<Persistent>();
				Iterator<Actor> csiMemberActorsIter = csiMemberActors.iterator();
				while (csiMemberActorsIter.hasNext()) {
					ManagedIdentifiableProxy memberActorObj = getEntityProxy(context, csiMemberActorsIter.next());
					pojoMemberActors.add(memberActorObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.memberActors.name(), pojoMemberActors);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			*/
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(GroupInfo.Attributes.memberActors.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(GroupInfo.Attributes.memberActors.name());
				Set<Actor> csiMemberActors = csiGroup.getMemberActors();
				HashSet<Persistent> pojoMemberActors = new HashSet<Persistent>(csiMemberActors.size());
				for (Actor csiActor : csiMemberActors) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedActor = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedActor.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiActor.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiActor);
						pojoMemberActors.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						pojoMemberActors.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.memberActors.name(), pojoMemberActors);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(GroupInfo.Attributes.memberGroups.name(), lastLoadedProjection, proj)) {
			/*
			try {
				Set<Group> csiSubGroups = csiGroup.getSubGroups();
				HashSet<Persistent> pojoSubGroups = new HashSet<Persistent>();
				Iterator<Group> csiSubGroupsIter = csiSubGroups.iterator();
				while (csiSubGroupsIter.hasNext()) {
					ManagedIdentifiableProxy subGroupObj = getEntityProxy(context, csiSubGroupsIter.next());
					pojoSubGroups.add(subGroupObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.memberGroups.name(), pojoSubGroups);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			*/
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(GroupInfo.Attributes.memberGroups.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(GroupInfo.Attributes.memberGroups.name());
				Set<Group> csiSubGroups = csiGroup.getSubGroups();
				HashSet<Persistent> pojoMemberGroups = new HashSet<Persistent>(csiSubGroups.size());
				for (Group csiSubGroup : csiSubGroups) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedGroup = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedGroup.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiSubGroup.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiSubGroup);
						pojoMemberGroups.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						pojoMemberGroups.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.memberGroups.name(), pojoMemberGroups);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(GroupInfo.Attributes.teamSpace.name(), lastLoadedProjection, proj)) {
			try {
				TeamWorkspace csiTeamWorkspace  = csiGroup.getTeamWorkspace();
				if (csiTeamWorkspace != null) {
					ManagedIdentifiableProxy teamWorkspaceObj = getEntityProxy(context, csiTeamWorkspace);
					Persistent teamWorkspace = teamWorkspaceObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.teamSpace.name(), teamWorkspace);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		GroupUpdater updater = (GroupUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, GroupInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, GroupInfo.Attributes.description.name());
			updater.setDescription(description);
		}
		
		if (isChanged(obj, GroupInfo.Attributes.assignedScopes.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(GroupInfo.Attributes.assignedScopes.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent assignedCommunity = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberCommunityObj = (ManagedIdentifiableProxy) assignedCommunity.getManagedObjectProxy();
					CollabId id = getCollabId(memberCommunityObj.getObjectId());
					CommunityHandle communityHandle = (CommunityHandle) EntityUtils.getInstance().createHandle(id);
					updater.addMembership(communityHandle);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(GroupInfo.Attributes.assignedScopes.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent assignedCommunity = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberCommunityObj = (ManagedIdentifiableProxy) assignedCommunity.getManagedObjectProxy();
					CollabId id = getCollabId(memberCommunityObj.getObjectId());
					CommunityHandle communityHandle = (CommunityHandle) EntityUtils.getInstance().createHandle(id);
					updater.removeMembership(communityHandle);
				}
			}
		}
		
		if (isChanged(obj, GroupInfo.Attributes.memberActors.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(GroupInfo.Attributes.memberActors.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent memberActor = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberActorObj = (ManagedIdentifiableProxy) memberActor.getManagedObjectProxy();
					CollabId id = getCollabId(memberActorObj.getObjectId());
					ActorHandle actorHandle = (ActorHandle) EntityUtils.getInstance().createHandle(id);
					updater.addMemberActor(actorHandle);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(GroupInfo.Attributes.memberActors.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent memberActor = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberActorObj = (ManagedIdentifiableProxy) memberActor.getManagedObjectProxy();
					CollabId id = getCollabId(memberActorObj.getObjectId());
					ActorHandle actorHandle = (ActorHandle) EntityUtils.getInstance().createHandle(id);
					updater.removeMemberActor(actorHandle);
				}
			}
		}
		
		if (isChanged(obj, GroupInfo.Attributes.memberGroups.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(GroupInfo.Attributes.memberGroups.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent memberGroup = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberGroupObj = (ManagedIdentifiableProxy) memberGroup.getManagedObjectProxy();
					CollabId id = getCollabId(memberGroupObj.getObjectId());
					GroupHandle groupHandle = (GroupHandle) EntityUtils.getInstance().createHandle(id);
					updater.addSubGroup(groupHandle);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(GroupInfo.Attributes.memberGroups.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent memberGroup = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberGroupObj = (ManagedIdentifiableProxy) memberGroup.getManagedObjectProxy();
					CollabId id = getCollabId(memberGroupObj.getObjectId());
					GroupHandle groupHandle = (GroupHandle) EntityUtils.getInstance().createHandle(id);
					updater.removeSubGroup(groupHandle);
				}
			}
		}
		
		if (isChanged(obj, GroupInfo.Attributes.teamSpace.name())) {
			Persistent teamSpace = (Persistent) getAttributeValue(pojoIdentifiable, GroupInfo.Attributes.teamSpace.name());
			if (teamSpace != null) {
				updater.setTeamWorkspace(true);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		GroupUpdater updater = GroupFactory.getInstance().createGroupUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		GroupHandle groupHandle = (GroupHandle) EntityUtils.getInstance().createHandle(id);
		GroupUpdater groupUpdater = (GroupUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoGroup = obj.getPojoIdentifiable();
		try {
			Group group = control.updateGroup(groupHandle, groupUpdater, updateMode, proj, MemberUpdateMode.IGNORE_ERRORS);
			assignChangeToken(pojoGroup, group.getSnapshotId().toString());
			return group;
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
		UserDirectoryControl control = ControlLocator.getInstance().getControl(
				UserDirectoryControl.class);
		GroupUpdater groupUpdater = (GroupUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoGroup = obj.getPojoIdentifiable();
		String name = getName(pojoGroup);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(getParent(pojoGroup).getManagedObjectProxy())).getObjectId());
		ScopeHandle scopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			Group group = control.createGroup(id.getEid(),	scopeHandle, name, 
					groupUpdater, proj, MemberUpdateMode.IGNORE_ERRORS);
			assignChangeToken(pojoGroup, group.getSnapshotId().toString());
			return group;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		GroupHandle groupHandle = (GroupHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteGroup(groupHandle, DeleteMode.alwaysDelete(), false);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public Set<Persistent> loadGroups(ManagedIdentifiableProxy obj, ScopeHandle csiScopeHandle, Projection proj) {	
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoGroups = null;
		try {
			UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
			GroupListFilter groupListFilter = GroupFactory.getInstance().createGroupListFilter();
			if (csiScopeHandle != null) {
				ParentPredicate predicate = groupListFilter.createParentPredicate(csiScopeHandle);
				groupListFilter.setPredicate(predicate);
			}
			groupListFilter.setProjection(proj);
			ListResult<Group> csiGroups = control.listGroups(groupListFilter);
			if (csiGroups != null) {
				pojoGroups = new HashSet<Persistent>(csiGroups.size());
				for (Group csiGroup : csiGroups) {
					ManagedIdentifiableProxy groupObj = getEntityProxy(context, csiGroup);
					groupObj.getProviderProxy().copyLoadedProjection(groupObj, csiGroup, proj);
					pojoGroups.add(groupObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoGroups;
	}

}
