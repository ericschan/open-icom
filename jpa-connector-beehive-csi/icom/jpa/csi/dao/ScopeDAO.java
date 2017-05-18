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

import icom.info.RelationshipBondableInfo;
import icom.info.ScopeInfo;
import icom.info.beehive.BeehiveExtentInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import oracle.csi.CollabId;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Scope;
import oracle.csi.ScopeHandle;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.ScopeUpdater;

public abstract class ScopeDAO extends EntityDAO {
	
	{
		basicAttributes.add(ScopeInfo.Attributes.description);
	}
	
	{
		
	}
	
	{
		lazyAttributes.add(BeehiveExtentInfo.Attributes.versionControlConfiguration);
		lazyAttributes.add(BeehiveExtentInfo.Attributes.categoryConfiguration);
		lazyAttributes.add(RelationshipBondableInfo.Attributes.relationships);
		lazyAttributes.add(ScopeInfo.Attributes.groups);
		lazyAttributes.add(ScopeInfo.Attributes.roleDefinitions);
		lazyAttributes.add(ScopeInfo.Attributes.roles);
	}

	protected ScopeDAO() {
	}

	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Scope csiScope = (Scope) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(ScopeInfo.Attributes.description.name(), lastLoadedProjection) &&
				isPartOfProjection(ScopeInfo.Attributes.description.name(),  proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ScopeInfo.Attributes.description.name(), csiScope.getDescription());
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
		
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (ScopeInfo.Attributes.groups.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ScopeInfo.Attributes.groups.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(ScopeInfo.Attributes.groups.name());
			CollabId id = getCollabId(obj.getObjectId());
			ScopeHandle csiScopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(id);
			Set<Persistent> groups = null;
			groups = GroupDAO.getInstance().loadGroups(obj, csiScopeHandle, Projection.EMPTY);
			if (removedObjects != null) {
				Persistent[] array = groups.toArray(new Persistent[0]);
				for (Persistent group : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedGroup = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedGroup.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)group.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							groups.remove(group);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					groups.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, ScopeInfo.Attributes.groups.name(), groups);
		}
		
		if (ScopeInfo.Attributes.roles.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ScopeInfo.Attributes.roles.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(ScopeInfo.Attributes.roles.name());
			CollabId id = getCollabId(obj.getObjectId());
			ScopeHandle csiScopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(id);
			Set<Persistent> roles = null;
			roles = AssignedRoleDAO.getInstance().loadRoles(obj, csiScopeHandle, Projection.EMPTY);
			if (removedObjects != null) {
				Persistent[] array = roles.toArray(new Persistent[0]);
				for (Persistent role : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedRole = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedRole.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)role.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							roles.remove(role);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					roles.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, ScopeInfo.Attributes.roles.name(), roles);
		}
		
		if (ScopeInfo.Attributes.roleDefinitions.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ScopeInfo.Attributes.roleDefinitions.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(ScopeInfo.Attributes.roleDefinitions.name());
			CollabId id = getCollabId(obj.getObjectId());
			ScopeHandle csiScopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(id);
			Set<Persistent> roleDefinitions = null;
			roleDefinitions = RoleDefinitionDAO.getInstance().loadRoleDefinitions(obj, csiScopeHandle, Projection.EMPTY);
			if (removedObjects != null) {
				Persistent[] array = roleDefinitions.toArray(new Persistent[0]);
				for (Persistent roleDefinition : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedRoleDefn = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedRoleDefn.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)roleDefinition.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							roleDefinitions.remove(roleDefinition);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					roleDefinitions.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, ScopeInfo.Attributes.roleDefinitions.name(), roleDefinitions);
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		ScopeUpdater updater = (ScopeUpdater) context.getUpdater();
		Persistent pojoScope = obj.getPojoIdentifiable();
		if (isChanged(obj, ScopeInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoScope, ScopeInfo.Attributes.description.name());
			updater.setDescription(description);
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

	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	protected void updateMetadataOnEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnEntity(obj);
		updateMetadataOnNewAndOldEntity(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	protected void updateMetadataOnNewEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnNewEntity(obj);
		updateMetadataOnNewAndOldEntity(obj);
	}
	
	public Persistent getVersionControlConfiguration(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveExtentInfo.Attributes.versionControlConfiguration.name());
	}
	
	public Persistent getCategoryConfiguration(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveExtentInfo.Attributes.categoryConfiguration.name());
	}

}
