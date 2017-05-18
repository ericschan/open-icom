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

import icom.info.ClassDefinitionInfo;
import icom.info.EntityInfo;
import icom.info.RoleDefinitionInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.info.beehive.BeehiveRoleDefinitionInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.PersistenceException;

import oracle.csi.AccessType;
import oracle.csi.AccessTypes;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Privilege;
import oracle.csi.RoleDefinition;
import oracle.csi.RoleDefinitionHandle;
import oracle.csi.ScopeHandle;
import oracle.csi.SnapshotId;
import oracle.csi.controls.AccessControl;
import oracle.csi.controls.AccessFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.RoleDefinitionListFilter;
import oracle.csi.filters.ScopePredicate;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.RoleDefinitionUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class RoleDefinitionDAO extends EntityDAO {

	static RoleDefinitionDAO singleton = new RoleDefinitionDAO();
	
	public static RoleDefinitionDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(ClassDefinitionInfo.Attributes.description);
		basicAttributes.add(RoleDefinitionInfo.Attributes.privileges);
		basicAttributes.add(BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes);
		basicAttributes.add(BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes);
		basicAttributes.add(BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled);
	}
	
	{

	}

	protected RoleDefinitionDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return RoleDefinitionHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		RoleDefinition csiRoleDefinition = null;
		try {
			AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			RoleDefinitionHandle csiRoleDefinitionHandle = (RoleDefinitionHandle) EntityUtils.getInstance().createHandle(id);
			csiRoleDefinition = control.loadRoleDefinition(csiRoleDefinitionHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiRoleDefinition;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, csiEntity, proj);
		
		RoleDefinition csiRoleDefinition = (RoleDefinition) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		//PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ClassDefinitionInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ClassDefinitionInfo.Attributes.description.name(), csiRoleDefinition.getDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(RoleDefinitionInfo.Attributes.privileges.name(), lastLoadedProjection, proj)) {
			try {
				Set<Privilege> csiPrivileges = csiRoleDefinition.getPrivileges();
				Set<Enum<?>> pojoPrivileges = null;
				if (csiPrivileges != null) {
					pojoPrivileges = new HashSet<Enum<?>>(csiPrivileges.size());
					for (Privilege csiPrivilege : csiPrivileges) {
						String privilegeName = csiPrivilege.name();
						String beehivePackageName = BeehiveBeanEnumeration.BeehivePrivilegeEnum.getPackageName();
						Enum<?> pojoPrivilege = instantiateEnum(beehivePackageName, BeehiveBeanEnumeration.BeehivePrivilegeEnum.name(), privilegeName);
						pojoPrivileges.add(pojoPrivilege);
					}
				}
				assignAttributeValue(pojoIdentifiable, RoleDefinitionInfo.Attributes.privileges.name(), pojoPrivileges);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (isBetweenProjections(BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name(), lastLoadedProjection, proj)) {
			try {
				AccessTypes csiAccessTypes = csiRoleDefinition.getAccessTypes();
				Set<AccessType> csiGrantedAccessTypes = csiAccessTypes.getGrantAccessTypes();
				Set<Enum<?>> pojoGrantedAccessTypes = null;
				if (csiGrantedAccessTypes != null) {
					pojoGrantedAccessTypes = new HashSet<Enum<?>>(csiGrantedAccessTypes.size());
					for (AccessType csiAccessType : csiGrantedAccessTypes) {
						String csiAccessTypeName = csiAccessType.name();
						String pojoAccessTypeName = AceDAO.csiToPojoAccessTypeNameMap.get(csiAccessTypeName);
						String beehivePackageName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.getPackageName();
						Enum<?> pojoAccessType = instantiateEnum(beehivePackageName, BeehiveBeanEnumeration.BeehiveAccessTypeEnum.name(), pojoAccessTypeName);
						pojoGrantedAccessTypes.add(pojoAccessType);
					}
				}
				assignAttributeValue(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name(), pojoGrantedAccessTypes);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (isBetweenProjections(BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name(), lastLoadedProjection, proj)) {
			try {
				AccessTypes csiAccessTypes = csiRoleDefinition.getAccessTypes();
				Set<AccessType> csiDeniedAccessTypes = csiAccessTypes.getDenyAccessTypes();
				Set<Enum<?>> pojoDeniedAccessTypes = null;
				if (csiDeniedAccessTypes != null) {
					pojoDeniedAccessTypes = new HashSet<Enum<?>>(csiDeniedAccessTypes.size());
					for (AccessType csiAccessType : csiDeniedAccessTypes) {
						String csiAccessTypeName = csiAccessType.name();
						String pojoAccessTypeName = AceDAO.csiToPojoAccessTypeNameMap.get(csiAccessTypeName);
						String beehivePackageName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.getPackageName();
						Enum<?> pojoAccessType = instantiateEnum(beehivePackageName, BeehiveBeanEnumeration.BeehiveAccessTypeEnum.name(), pojoAccessTypeName);
						pojoDeniedAccessTypes.add(pojoAccessType);
					}
				}
				assignAttributeValue(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name(), pojoDeniedAccessTypes);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (isBetweenProjections(BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled.name(), lastLoadedProjection, proj)) {
			try {
				Boolean alwaysEnabled = csiRoleDefinition.isAlwaysEnabled();
				assignAttributeValue(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled.name(), alwaysEnabled);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		RoleDefinitionUpdater updater = (RoleDefinitionUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, ClassDefinitionInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, ClassDefinitionInfo.Attributes.description.name());
			updater.setDescription(description);
		}
		
		if (isChanged(obj, RoleDefinitionInfo.Attributes.privileges.name())) {
			Set<Privilege> csiPrivileges = null;
			Set<Enum<?>> pojoPrivileges = getSetOfEnum(pojoIdentifiable, RoleDefinitionInfo.Attributes.privileges.name());
			if (pojoPrivileges != null) {
				csiPrivileges = new HashSet<Privilege>(pojoPrivileges.size()); 
				for (Enum<?> pojoPrivilege : pojoPrivileges) {
					String privilegeName = pojoPrivilege.name();
					Privilege csiPrivilege = Privilege.valueOf(privilegeName);
					csiPrivileges.add(csiPrivilege);
				}
			}
			updater.addPrivileges(csiPrivileges);
		}
		
		if (isChanged(obj, BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name())
		 || isChanged(obj, BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name())) {
			Set<AccessType> csiGrantedAccessTypes = null;
			Set<Enum<?>> pojoGrantedAccessTypes = getSetOfEnum(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name());
			if (pojoGrantedAccessTypes != null) {
				csiGrantedAccessTypes = new HashSet<AccessType>(pojoGrantedAccessTypes.size()); 
				for (Enum<?> pojoAccessType : pojoGrantedAccessTypes) {
					String pojoAccessTypeName = pojoAccessType.name();
					String csiAccessTypeName = AceDAO.pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
					AccessType csiAccessType = AccessType.valueOf(csiAccessTypeName);
					csiGrantedAccessTypes.add(csiAccessType);
				}
			}
			Set<AccessType> csiDeniedAccessTypes = null;
			Set<Enum<?>> pojoDeniedAccessTypes = getSetOfEnum(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name());
			if (pojoDeniedAccessTypes != null) {
				csiDeniedAccessTypes = new HashSet<AccessType>(pojoDeniedAccessTypes.size()); 
				for (Enum<?> pojoAccessType : pojoDeniedAccessTypes) {
					String pojoAccessTypeName = pojoAccessType.name();
					String csiAccessTypeName = AceDAO.pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
					AccessType csiAccessType = AccessType.valueOf(csiAccessTypeName);
					csiDeniedAccessTypes.add(csiAccessType);
				}
			}
			AccessTypes csiAccessTypes = new AccessTypes(csiGrantedAccessTypes, csiDeniedAccessTypes);
			updater.setAccessTypes(csiAccessTypes);
		}
		
		if (isChanged(obj, BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled.name())) {
			Boolean alwaysEnabled = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled.name());
			if (alwaysEnabled != null) {
				updater.setAlwaysEnabled(alwaysEnabled);
			} else {
				updater.setAlwaysEnabled(true);
			}
		}
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		RoleDefinitionUpdater updater = AccessFactory.getInstance().createRoleDefinitionUpdater();
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
		RoleDefinitionHandle roleDefinitionHandle = (RoleDefinitionHandle) EntityUtils.getInstance().createHandle(id);
		RoleDefinitionUpdater roleDefinitionUpdater = (RoleDefinitionUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoRole = obj.getPojoIdentifiable();
		try {
			RoleDefinition roleDefinition = control.updateRoleDefinition(roleDefinitionHandle, roleDefinitionUpdater, updateMode, proj);
			assignChangeToken(pojoRole, roleDefinition.getSnapshotId().toString());
			return roleDefinition;
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
		icom.jpa.Identifiable pojoRoleDefinition = obj.getPojoIdentifiable();
		
		CollabId id = getCollabId(obj.getObjectId());
		Eid eid = id.getEid();
		
		Persistent parentScope = (Persistent) getAttributeValue(pojoRoleDefinition, EntityInfo.Attributes.parent.name());
		ManagedIdentifiableProxy parentScopeObj = (ManagedIdentifiableProxy) parentScope.getManagedObjectProxy();
		CollabId parentScopeId = getCollabId(parentScopeObj.getObjectId());
		ScopeHandle parentScopeHandle = (ScopeHandle) EntityUtils.getInstance().createHandle(parentScopeId);
		
		String name = (String) getAttributeValue(pojoRoleDefinition, EntityInfo.Attributes.name.name());
		String description = (String) getAttributeValue(pojoRoleDefinition, ClassDefinitionInfo.Attributes.description.name());
		
		Set<Privilege> csiPrivileges = null;
		Set<Enum<?>> pojoPrivileges = getSetOfEnum(pojoRoleDefinition, RoleDefinitionInfo.Attributes.privileges.name());
		if (pojoPrivileges != null) {
			csiPrivileges = new HashSet<Privilege>(pojoPrivileges.size()); 
			for (Enum<?> pojoPrivilege : pojoPrivileges) {
				String privilegeName = pojoPrivilege.name();
				Privilege csiPrivilege = Privilege.valueOf(privilegeName);
				csiPrivileges.add(csiPrivilege);
			}
		}
		
		Set<AccessType> csiGrantedAccessTypes = null;
		Set<Enum<?>> pojoGrantedAccessTypes = getSetOfEnum(pojoRoleDefinition, BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name());
		if (pojoGrantedAccessTypes != null) {
			csiGrantedAccessTypes = new HashSet<AccessType>(pojoGrantedAccessTypes.size()); 
			for (Enum<?> pojoGrantedAccessType : pojoGrantedAccessTypes) {
				String pojoAccessTypeName = pojoGrantedAccessType.name();
				String csiAccessTypeName = AceDAO.pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
				AccessType csiAccessType = AccessType.valueOf(csiAccessTypeName);
				csiGrantedAccessTypes.add(csiAccessType);
			}
		}
		Set<AccessType> csiDeniedAccessTypes = null;
		Set<Enum<?>> pojoDeniedAccessTypes = getSetOfEnum(pojoRoleDefinition, BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name());
		if (pojoDeniedAccessTypes != null) {
			csiDeniedAccessTypes = new HashSet<AccessType>(pojoDeniedAccessTypes.size()); 
			for (Enum<?> pojoDeniedAccessType : pojoDeniedAccessTypes) {
				String pojoAccessTypeName = pojoDeniedAccessType.name();
				String csiAccessTypeName = AceDAO.pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
				AccessType csiAccessType = AccessType.valueOf(csiAccessTypeName);
				csiDeniedAccessTypes.add(csiAccessType);
			}
		}
		AccessTypes accessTypes = new AccessTypes(csiGrantedAccessTypes, csiDeniedAccessTypes);
		
		Boolean alwaysEnabled = (Boolean) getAttributeValue(pojoRoleDefinition, BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled.name());
		if (alwaysEnabled == null) {
			alwaysEnabled = true;
		}
		
		RoleDefinitionUpdater roleDefinitionUpdater = (RoleDefinitionUpdater) context.getUpdater();
		
		Object changeToken = ((ManagedIdentifiableProxy)pojoRoleDefinition.getManagedObjectProxy()).getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		
		try {
			RoleDefinition roleDefinition = control.createRoleDefinition(eid, parentScopeHandle, name, description, csiPrivileges, accessTypes, alwaysEnabled, roleDefinitionUpdater, updateMode, proj);	
			assignChangeToken(pojoRoleDefinition, roleDefinition.getSnapshotId().toString());
			return roleDefinition;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		RoleDefinitionHandle roleDefinitionHandle = (RoleDefinitionHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteRoleDefinition(roleDefinitionHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public Set<Persistent> loadRoleDefinitions(ManagedIdentifiableProxy obj, ScopeHandle csiScopeHandle, Projection proj) {	
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoRoleDefinitions = null;
		try {
			AccessControl control = ControlLocator.getInstance().getControl(AccessControl.class);
			RoleDefinitionListFilter roleDefinitionListFilter = AccessFactory.getInstance().createRoleDefinitionListFilter();
			if (csiScopeHandle != null) {
				ScopePredicate predicate = roleDefinitionListFilter.createScopePredicate(csiScopeHandle);
				roleDefinitionListFilter.setPredicate(predicate);
			}
			roleDefinitionListFilter.setProjection(proj);
			ListResult<RoleDefinition> csiRoleDefinitions = control.listRoleDefinitions(roleDefinitionListFilter);
			if (csiRoleDefinitions != null) {
				pojoRoleDefinitions = new HashSet<Persistent>(csiRoleDefinitions.size());
				for (RoleDefinition csiRoleDefinition : csiRoleDefinitions) {
					ManagedIdentifiableProxy roleDefinitionObj = getEntityProxy(context, csiRoleDefinition);
					roleDefinitionObj.getProviderProxy().copyLoadedProjection(roleDefinitionObj, csiRoleDefinition, proj);
					pojoRoleDefinitions.add(roleDefinitionObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoRoleDefinitions;
	}

}
