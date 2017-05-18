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


import com.oracle.beehive.Actor;
import com.oracle.beehive.ActorListUpdater;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.CommunityListUpdater;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.Group;
import com.oracle.beehive.GroupCreator;
import com.oracle.beehive.GroupListUpdater;
import com.oracle.beehive.GroupUpdater;
import com.oracle.beehive.MemberUpdateMode;
import com.oracle.beehive.ParentPredicate;
import com.oracle.beehive.TeamWorkspace;

import icom.info.EntityInfo;
import icom.info.GroupInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;
import icom.jpa.rt.PersistenceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;


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

	public String getResourceType() {
		return "grup";
	}
	
	public void saveDirty(ManagedIdentifiableProxy obj) {
		if (obj.getProviderClassName() == null || ! obj.getProviderClassName().equals("AllUsersGroup")) {
			super.saveDirty(obj);
		} else {
			saveAccessControlFieldsOnDirtyEntity(obj);
			updateMetadataOnEntity(obj);
		}
	}

    public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntity, Projection proj) {
        ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy)managedObj;
        super.copyObjectState(obj, bdkEntity, proj);

        Group bdkGroup = (Group)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();

        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(GroupInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, GroupInfo.Attributes.description.name(),
                                     bdkGroup.getDescription());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(GroupInfo.Attributes.assignedScopes.name(), lastLoadedProjection, proj)) {
            // TODO, not currently supported
        }

        if (isBetweenProjections(GroupInfo.Attributes.memberActors.name(), lastLoadedProjection, proj)) {
            try {
                List<Actor> bdkMemberActors = bdkGroup.getMemberActors();
                marshallMergeAssignEntities(obj, GroupInfo.Attributes.memberActors.name(), bdkMemberActors,
                                          HashSet.class);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(GroupInfo.Attributes.memberGroups.name(), lastLoadedProjection, proj)) {
            try {
                List<Group> bdkSubGroups = bdkGroup.getSubGroups();
                marshallMergeAssignEntities(obj, GroupInfo.Attributes.memberGroups.name(), bdkSubGroups, HashSet.class);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(GroupInfo.Attributes.teamSpace.name(), lastLoadedProjection, proj)) {
            try {
                TeamWorkspace bdkTeamWorkspace = bdkGroup.getTeamWorkspace();
                marshallAssignEntity(obj, GroupInfo.Attributes.teamSpace.name(), bdkTeamWorkspace);
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
        GroupUpdater updater = (GroupUpdater)context.getUpdater();
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();

        if (isChanged(obj, GroupInfo.Attributes.description.name())) {
            String description = (String)getAttributeValue(pojoIdentifiable, GroupInfo.Attributes.description.name());
            updater.setDescription(description);
        }

        if (isChanged(obj, GroupInfo.Attributes.assignedScopes.name())) {
            CommunityListUpdater communityListUpdater = new CommunityListUpdater();
            AttributeChangeSet changeSet = getAttributeChanges(obj, null, GroupInfo.Attributes.assignedScopes.name());
            for (Persistent assignedCommunity : changeSet.addedPojoObjects) {
                ManagedIdentifiableProxy memberCommunityObj = (ManagedIdentifiableProxy)assignedCommunity.getManagedObjectProxy();
                BeeId communityId = getBeeId(memberCommunityObj.getObjectId().toString());
                communityListUpdater.getAdds().add(communityId);
            }
            for (Persistent assignedCommunity : changeSet.removedPojoObjects) {
                ManagedIdentifiableProxy memberCommunityObj =
                    (ManagedIdentifiableProxy)assignedCommunity.getManagedObjectProxy();
                BeeId communityId = getBeeId(memberCommunityObj.getObjectId().toString());
                communityListUpdater.getRemoves().add(communityId);
            }
            updater.setMemberships(communityListUpdater);
        }

        if (isChanged(obj, GroupInfo.Attributes.memberActors.name())) {
            ActorListUpdater actorListUpdater = new ActorListUpdater();
            AttributeChangeSet changeSet = getAttributeChanges(obj, null, GroupInfo.Attributes.memberActors.name());
            for (Persistent memberActor : changeSet.addedPojoObjects) {
                ManagedIdentifiableProxy memberActorObj = (ManagedIdentifiableProxy)memberActor.getManagedObjectProxy();
                BeeId actorId = getBeeId(memberActorObj.getObjectId().toString());
                actorListUpdater.getAdds().add(actorId);
            }
            for (Persistent memberActor : changeSet.removedPojoObjects) {
                ManagedIdentifiableProxy memberActorObj =
                    (ManagedIdentifiableProxy)memberActor.getManagedObjectProxy();
                BeeId actorId = getBeeId(memberActorObj.getObjectId().toString());
                actorListUpdater.getRemoves().add(actorId);
            }
            updater.setMembers(actorListUpdater);
        }

        if (isChanged(obj, GroupInfo.Attributes.memberGroups.name())) {
            GroupListUpdater groupListUpdater = new GroupListUpdater();
            AttributeChangeSet changeSet = getAttributeChanges(obj, null, GroupInfo.Attributes.memberGroups.name());
            for (Persistent memberGroup : changeSet.addedPojoObjects) {
                ManagedIdentifiableProxy memberGroupObj = (ManagedIdentifiableProxy)memberGroup.getManagedObjectProxy();
                BeeId groupId = getBeeId(memberGroupObj.getObjectId().toString());
                groupListUpdater.getAdds().add(groupId);
            }
            for (Persistent memberGroup : changeSet.removedPojoObjects) {
                ManagedIdentifiableProxy memberGroupObj = (ManagedIdentifiableProxy)memberGroup.getManagedObjectProxy();
                BeeId groupId = getBeeId(memberGroupObj.getObjectId().toString());
                groupListUpdater.getRemoves().add(groupId);
            }
            updater.setSubGroups(groupListUpdater);
        }

        if (isChanged(obj, GroupInfo.Attributes.teamSpace.name())) {
            Persistent teamSpace = (Persistent)getAttributeValue(pojoIdentifiable, GroupInfo.Attributes.teamSpace.name());
            if (teamSpace != null) {
                updater.setTeamWorkspace(true);
            }
        }
    }

	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		GroupCreator creator = (GroupCreator) context.getCreator();
		Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setName(name);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setScope(parentHandle);
		}
		MemberUpdateMode mode = MemberUpdateMode.IGNORE_ERRORS;
		creator.setMode(mode);
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public Set<Persistent> loadGroups(ManagedIdentifiableProxy obj, BeeId bdkScopeId, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		ParentPredicate predicate = new ParentPredicate();
		predicate.setParentMatch(bdkScopeId);
		List<Object> bdkGroups = listEntities(context, Group.class, predicate, getResourceType(), proj);
		try {
			if (bdkGroups != null) {
				Set<Persistent> pojoGroups = new HashSet<Persistent>(bdkGroups.size());
				for (Object bdkObject : bdkGroups) {
					Group bdkGroup = (Group) bdkObject;
					ManagedIdentifiableProxy userObj = getEntityProxy(context, bdkGroup);
					userObj.getProviderProxy().copyLoadedProjection(userObj, bdkGroup, proj);
					pojoGroups.add(userObj.getPojoIdentifiable());
				}
				return pojoGroups;
			} else {
				return new HashSet<Persistent>();
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Group.class;
	}
	
	protected GroupUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new GroupUpdater();
	}
	
	protected GroupUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		GroupUpdater updater = getBdkUpdater(obj);
		((GroupCreator)creator).setData(updater);
		return updater;
	}
	
	protected GroupCreator getBdkCreator(ManagedObjectProxy obj) {
		return new GroupCreator();
	}

}
