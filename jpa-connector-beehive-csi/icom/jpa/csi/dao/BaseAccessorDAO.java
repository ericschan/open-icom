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

import icom.info.AccessorInfo;
import icom.info.RelationshipBondableInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import oracle.csi.AssignedRole;
import oracle.csi.BaseAccessor;
import oracle.csi.CollabId;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Group;
import oracle.csi.projections.Projection;

public abstract class BaseAccessorDAO extends EntityDAO {
	
	{
		fullAttributes.add(AccessorInfo.Attributes.assignedGroups);
		fullAttributes.add(AccessorInfo.Attributes.assignedRoles);
	}
	
	{
		lazyAttributes.add(RelationshipBondableInfo.Attributes.relationships);
	}

	protected BaseAccessorDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		BaseAccessor csiBaseAccessor = (BaseAccessor) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(AccessorInfo.Attributes.assignedGroups.name(), lastLoadedProjection, proj)) {
			/*
			try {
				Set<Group> csiGroups = csiBaseAccessor.getGroups();
				HashSet<icom.jpa.spi.Identifiable> pojoGroups = new HashSet<icom.jpa.spi.Identifiable>();
				Iterator<Group> csiGroupsIter = csiGroups.iterator();
				while (csiGroupsIter.hasNext()) {
					ManagedIdentifiableProxy groupObj = getEntityProxy(context, csiGroupsIter.next());
					pojoGroups.add(groupObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, AccessorInfo.Attributes.assignedGroups.name(), pojoGroups);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			*/
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(AccessorInfo.Attributes.assignedGroups.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(AccessorInfo.Attributes.assignedGroups.name());
				Set<Group> csiSuperGroups = csiBaseAccessor.getGroups();
				HashSet<Persistent> pojoAssignedGroups = new HashSet<Persistent>(csiSuperGroups.size());
				for (Group csiSuperGroup : csiSuperGroups) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent group = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(group.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiSuperGroup.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiSuperGroup);
						pojoAssignedGroups.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						pojoAssignedGroups.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, AccessorInfo.Attributes.assignedGroups.name(), pojoAssignedGroups);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(AccessorInfo.Attributes.assignedRoles.name(), lastLoadedProjection, proj)) {
			/*
			try {
				Set<AssignedRole> csiAssignedRoles = csiBaseAccessor.getAssignedRoles();
				HashSet<icom.jpa.spi.Identifiable> pojoRoles = new HashSet<icom.jpa.spi.Identifiable>();
				Iterator<AssignedRole> csiAssignedRolesIter = csiAssignedRoles.iterator();
				while (csiAssignedRolesIter.hasNext()) {
					ManagedIdentifiableProxy roleObj = getEntityProxy(context, csiAssignedRolesIter.next());
					pojoRoles.add(roleObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, AccessorInfo.Attributes.assignedRoles.name(), pojoRoles);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			*/
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(AccessorInfo.Attributes.assignedRoles.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(AccessorInfo.Attributes.assignedRoles.name());
				Set<AssignedRole> csiAssignedRoles = csiBaseAccessor.getAssignedRoles();
				HashSet<Persistent> pojoAssignedRoles = new HashSet<Persistent>(csiAssignedRoles.size());
				for (AssignedRole csiAssignedRole : csiAssignedRoles) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent role = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(role.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiAssignedRole.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiAssignedRole);
						pojoAssignedRoles.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						pojoAssignedRoles.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, AccessorInfo.Attributes.assignedRoles.name(), pojoAssignedRoles);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

}
