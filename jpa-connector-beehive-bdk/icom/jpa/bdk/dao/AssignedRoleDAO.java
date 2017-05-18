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


import com.oracle.beehive.AssignedRole;
import com.oracle.beehive.AssignedRoleCreator;
import com.oracle.beehive.AssignedRoleUpdater;
import com.oracle.beehive.BaseAccessor;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.RoleDefinition;
import com.oracle.beehive.Scope;
import com.oracle.beehive.ScopePredicate;

import icom.info.EntityInfo;
import icom.info.RoleInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;


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

	public String getResourceType() {
		return "acar";
	}

    public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntity, Projection proj) {
        ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy)managedObj;
        super.copyObjectState(obj, bdkEntity, proj);

        AssignedRole bdkAssignedRole = (AssignedRole)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();

        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(RoleInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, RoleInfo.Attributes.description.name(),
                                     bdkAssignedRole.getDescription());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(RoleInfo.Attributes.roleDefinition.name(), lastLoadedProjection, proj)) {
            try {
                RoleDefinition bdkRoleDefinition = bdkAssignedRole.getRoleDefinition();
                marshallAssignEntity(obj, RoleInfo.Attributes.roleDefinition.name(),
                                                      bdkRoleDefinition);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(RoleInfo.Attributes.memberAccessors.name(), lastLoadedProjection, proj)) {
            try {
                List<BaseAccessor> bdkMemberBaseAccessors = bdkAssignedRole.getAccessors();
                marshallMergeAssignEntities(obj, RoleInfo.Attributes.memberAccessors.name(),
                                                                  bdkMemberBaseAccessors, HashSet.class);
            } catch (Exception ex) {
                // ignore
            }

        }

        if (isBetweenProjections(RoleInfo.Attributes.assignedScope.name(), lastLoadedProjection, proj)) {
            try {
                Scope bdkScope = bdkAssignedRole.getAssignedScope();
                marshallAssignEntity(obj, RoleInfo.Attributes.assignedScope.name(), bdkScope);
            } catch (Exception ex) {
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
			// TODO not supported yet
		}
		
		if (isChanged(obj, RoleInfo.Attributes.assignedScope.name())) {
			Persistent assignedScope = (Persistent) getAttributeValue(pojoIdentifiable, RoleInfo.Attributes.assignedScope.name());
			if (assignedScope != null) {
				ManagedIdentifiableProxy assignedScopeObj = (ManagedIdentifiableProxy) assignedScope.getManagedObjectProxy();
				BeeId scopeId = getBeeId(assignedScopeObj.getObjectId().toString());
				updater.setAssignedScope(scopeId);
			}
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		AssignedRoleCreator creator = (AssignedRoleCreator) context.getCreator();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setName(name);
		String description = (String) getAttributeValue(pojoIdentifiable, RoleInfo.Attributes.description.name());
		creator.setDescription(description);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setScopeHandle(parentHandle);
		}
		Identifiable assignedScope = (Identifiable) getAttributeValue(pojoIdentifiable, RoleInfo.Attributes.assignedScope.name());
		if (assignedScope != null) {
			BeeId assignedScopeHandle = getBeeId(assignedScope.getObjectId().getObjectId().toString());
			creator.setAssignedScopeHandle(assignedScopeHandle);
		}
		Identifiable roleDefinition = (Identifiable) getAttributeValue(pojoIdentifiable, RoleInfo.Attributes.roleDefinition.name());
		if (roleDefinition != null) {
			BeeId roleDefinitionHandle = getBeeId(roleDefinition.getObjectId().getObjectId().toString());
			creator.setRoleDefinitionHandle(roleDefinitionHandle);
		}
		/*
		UpdateMode updateMode = null;
		Object changeToken = obj.getChangeToken();
		if (changeToken != null) {
			String sid = changeToken.toString();
			updateMode.setSnapshotId(sid);
			creator.setUpdateMode(updateMode);
		}
		*/

		updateNewOrOldObjectState(obj, context);
	}

	// need to override the method in EntityDAO, owner of workspace coordinator role assignment is set in workspace updater
	protected void updateOwnerOnEntity(ManagedIdentifiableProxy obj, DAOContext context) {
		String name = (String) getAttributeValue(obj.getPojoIdentifiable(), EntityInfo.Attributes.name.name());
		if (name == null || ! name.equals("workspace-coordinator")) {
			super.updateOwnerOnEntity(obj, context);
		}
	}
	
	public Set<Persistent> loadRoles(ManagedIdentifiableProxy obj, BeeId scopeId, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		ScopePredicate predicate = new ScopePredicate();
		predicate.setScopeHandle(scopeId);
		List<Object> bdkAssignedRoles = listEntities(context, AssignedRole.class, predicate, getResourceType(), proj);
		try {
			if (bdkAssignedRoles != null) {
				Set<Persistent> pojoRoles = new HashSet<Persistent>(bdkAssignedRoles.size());
				for (Object bdkObject : bdkAssignedRoles) {
					AssignedRole bdkAssignedRole = (AssignedRole) bdkObject;
					ManagedIdentifiableProxy roleObj = getEntityProxy(context, bdkAssignedRole);
					roleObj.getProviderProxy().copyLoadedProjection(roleObj, bdkAssignedRole, proj);
					pojoRoles.add(roleObj.getPojoIdentifiable());
				}
				return pojoRoles;
			} else {
				return new HashSet<Persistent>();
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return AssignedRole.class;
	}
	
	protected AssignedRoleUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new AssignedRoleUpdater();
	}
	
	protected AssignedRoleUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		AssignedRoleUpdater updater = getBdkUpdater(obj);
		((AssignedRoleCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected AssignedRoleCreator getBdkCreator(ManagedObjectProxy obj) {
		return new AssignedRoleCreator();
	}

}
