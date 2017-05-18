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

import icom.info.EntityInfo;
import icom.info.RoleDefinitionInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.info.beehive.BeehiveRoleDefinitionInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.Identifiable;
import icom.jpa.Persistent;
import icom.jpa.ManagedObjectProxy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import com.oracle.beehive.AccessType;
import com.oracle.beehive.AccessTypes;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.Privilege;
import com.oracle.beehive.RoleDefinition;
import com.oracle.beehive.RoleDefinitionCreator;
import com.oracle.beehive.RoleDefinitionUpdater;
import com.oracle.beehive.ScopePredicate;


public class RoleDefinitionDAO extends EntityDAO {

	static RoleDefinitionDAO singleton = new RoleDefinitionDAO();
	
	public static RoleDefinitionDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(RoleDefinitionInfo.Attributes.description);
		basicAttributes.add(RoleDefinitionInfo.Attributes.privileges);
		basicAttributes.add(BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes);
		basicAttributes.add(BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes);
		basicAttributes.add(BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled);
	}
	
	{

	}

	protected RoleDefinitionDAO() {
	}

	public String getResourceType() {
		return "acrd";
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, bdkEntity, proj);
		
		RoleDefinition bdkRoleDefinition = (RoleDefinition) bdkEntity;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		//PersistenceContext context = obj.getPersistenceContext();
		BdkProjectionManager projManager = (BdkProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(RoleDefinitionInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, RoleDefinitionInfo.Attributes.description.name(), bdkRoleDefinition.getDescription());
			} catch (Exception ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(RoleDefinitionInfo.Attributes.privileges.name(), lastLoadedProjection, proj)) {
			try {
				List<Privilege> bdkPrivileges = bdkRoleDefinition.getPrivileges();
				Set<Enum<?>> pojoPrivileges = null;
				if (bdkPrivileges != null) {
					pojoPrivileges = new HashSet<Enum<?>>(bdkPrivileges.size());
					for (Privilege bdkPrivilege : bdkPrivileges) {
						String privilegeName = bdkPrivilege.name();
						String beehivePackageName = BeehiveBeanEnumeration.BeehivePrivilegeEnum.getPackageName();
						Enum<?> pojoPrivilege = instantiateEnum(beehivePackageName, BeehiveBeanEnumeration.BeehivePrivilegeEnum.name(), privilegeName);
						pojoPrivileges.add(pojoPrivilege);
					}
				}
				assignAttributeValue(pojoIdentifiable, RoleDefinitionInfo.Attributes.privileges.name(), pojoPrivileges);
			} catch (Exception ex) {
				// ignore
			}
		}
		if (isBetweenProjections(BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name(), lastLoadedProjection, proj)) {
			try {
				AccessTypes bdkAccessTypes = bdkRoleDefinition.getAccessTypes();
				List<AccessType> bdkGrantedAccessTypes = bdkAccessTypes.getGrantAccessTypes();
				Set<Enum<?>> pojoGrantedAccessTypes = null;
				if (bdkGrantedAccessTypes != null) {
					pojoGrantedAccessTypes = new HashSet<Enum<?>>(bdkGrantedAccessTypes.size());
					for (AccessType bdkAccessType : bdkGrantedAccessTypes) {
						String bdkAccessTypeName = bdkAccessType.name();
						String pojoAccessTypeName = AceDAO.bdkToPojoAccessTypeNameMap.get(bdkAccessTypeName);
						String beehivePackageName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.getPackageName();
						Enum<?> pojoAccessType = instantiateEnum(beehivePackageName, BeehiveBeanEnumeration.BeehiveAccessTypeEnum.name(), pojoAccessTypeName);
						pojoGrantedAccessTypes.add(pojoAccessType);
					}
				}
				assignAttributeValue(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name(), pojoGrantedAccessTypes);
			} catch (Exception ex) {
				// ignore
			}
		}
		if (isBetweenProjections(BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name(), lastLoadedProjection, proj)) {
			try {
				AccessTypes bdkAccessTypes = bdkRoleDefinition.getAccessTypes();
				List<AccessType> bdkDeniedAccessTypes = bdkAccessTypes.getDenyAccessTypes();
				Set<Enum<?>> pojoDeniedAccessTypes = null;
				if (bdkDeniedAccessTypes != null) {
					pojoDeniedAccessTypes = new HashSet<Enum<?>>(bdkDeniedAccessTypes.size());
					for (AccessType bdkAccessType : bdkDeniedAccessTypes) {
						String bdkAccessTypeName = bdkAccessType.name();
						String pojoAccessTypeName = AceDAO.bdkToPojoAccessTypeNameMap.get(bdkAccessTypeName);
						String beehivePackageName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.getPackageName();
						Enum<?> pojoAccessType = instantiateEnum(beehivePackageName, BeehiveBeanEnumeration.BeehiveAccessTypeEnum.name(), pojoAccessTypeName);
						pojoDeniedAccessTypes.add(pojoAccessType);
					}
				}
				assignAttributeValue(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name(), pojoDeniedAccessTypes);
			} catch (Exception ex) {
				// ignore
			}
		}
		if (isBetweenProjections(BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled.name(), lastLoadedProjection, proj)) {
			try {
				Boolean alwaysEnabled = bdkRoleDefinition.isAlwaysEnabled();
				assignAttributeValue(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.alwaysEnabled.name(), alwaysEnabled);
			} catch (Exception ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		RoleDefinitionUpdater updater = (RoleDefinitionUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, RoleDefinitionInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, RoleDefinitionInfo.Attributes.description.name());
			updater.setDescription(description);
		}
		
		if (isChanged(obj, RoleDefinitionInfo.Attributes.privileges.name())) {
			Set<Privilege> bdkPrivileges = null;
			Set<Enum<?>> pojoPrivileges = getSetOfEnum(pojoIdentifiable, RoleDefinitionInfo.Attributes.privileges.name());
			if (pojoPrivileges != null) {
				bdkPrivileges = new HashSet<Privilege>(pojoPrivileges.size()); 
				for (Enum<?> pojoPrivilege : pojoPrivileges) {
					String privilegeName = pojoPrivilege.name();
					Privilege bdkPrivilege = Privilege.valueOf(privilegeName);
					bdkPrivileges.add(bdkPrivilege);
				}
			}
			if (bdkPrivileges != null) {
				// TODO updater.getPrivileges().addAll(bdkPrivileges);
			}
		}
		
		if (isChanged(obj, BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name())
		 || isChanged(obj, BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name())) {
			Set<AccessType> bdkGrantedAccessTypes = null;
			Set<Enum<?>> pojoGrantedAccessTypes = getSetOfEnum(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.grantedAccessTypes.name());
			if (pojoGrantedAccessTypes != null) {
				bdkGrantedAccessTypes = new HashSet<AccessType>(pojoGrantedAccessTypes.size()); 
				for (Enum<?> pojoAccessType : pojoGrantedAccessTypes) {
					String pojoAccessTypeName = pojoAccessType.name();
					String bdkAccessTypeName = AceDAO.pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
					AccessType bdkAccessType = AccessType.valueOf(bdkAccessTypeName);
					bdkGrantedAccessTypes.add(bdkAccessType);
				}
			}
			Set<AccessType> bdkDeniedAccessTypes = null;
			Set<Enum<?>> pojoDeniedAccessTypes = getSetOfEnum(pojoIdentifiable, BeehiveRoleDefinitionInfo.Attributes.deniedAccessTypes.name());
			if (pojoDeniedAccessTypes != null) {
				bdkDeniedAccessTypes = new HashSet<AccessType>(pojoDeniedAccessTypes.size()); 
				for (Enum<?> pojoAccessType : pojoDeniedAccessTypes) {
					String pojoAccessTypeName = pojoAccessType.name();
					String bdkAccessTypeName = AceDAO.pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
					AccessType bdkAccessType = AccessType.valueOf(bdkAccessTypeName);
					bdkDeniedAccessTypes.add(bdkAccessType);
				}
			}
			if (bdkGrantedAccessTypes != null || bdkDeniedAccessTypes != null) {
				AccessTypes bdkAccessTypes = new AccessTypes();
				if (bdkGrantedAccessTypes != null) {
					bdkAccessTypes.getGrantAccessTypes().addAll(bdkGrantedAccessTypes);
				}
				if (bdkDeniedAccessTypes != null) {
					bdkAccessTypes.getDenyAccessTypes().addAll(bdkDeniedAccessTypes);
				}
				updater.setAccessTypes(bdkAccessTypes);
			}
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
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		RoleDefinitionCreator creator = (RoleDefinitionCreator) context.getCreator();
		Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setName(name);
		String description = (String) getAttributeValue(pojoIdentifiable, RoleDefinitionInfo.Attributes.description.name());
		creator.setDescription(description);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setScopeHandle(parentHandle);
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
	
	public Set<Persistent> loadRoleDefinitions(ManagedIdentifiableProxy obj, BeeId scopeId, Projection proj) {	
		PersistenceContext context = obj.getPersistenceContext();
		ScopePredicate predicate = new ScopePredicate();
		predicate.setScopeHandle(scopeId);
		List<Object> bdkRoleDefinitions = listEntities(context, RoleDefinition.class, predicate, getResourceType(), proj);
		try {
			if (bdkRoleDefinitions != null) {
				Set<Persistent> pojoRoleDefinitions = new HashSet<Persistent>(bdkRoleDefinitions.size());
				for (Object bdkObject : bdkRoleDefinitions) {
					RoleDefinition bdkRoleDefinition = (RoleDefinition) bdkObject;
					ManagedIdentifiableProxy roleDefinitionObj = getEntityProxy(context, bdkRoleDefinition);
					roleDefinitionObj.getProviderProxy().copyLoadedProjection(roleDefinitionObj, bdkRoleDefinition, proj);
					pojoRoleDefinitions.add(roleDefinitionObj.getPojoIdentifiable());
				}
				return pojoRoleDefinitions;
			} else {
				return new HashSet<Persistent>();
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return RoleDefinition.class;
	}
	
	protected RoleDefinitionUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new RoleDefinitionUpdater();
	}
	
	protected RoleDefinitionUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		RoleDefinitionUpdater updater = getBdkUpdater(obj);
		((RoleDefinitionCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected RoleDefinitionCreator getBdkCreator(ManagedObjectProxy obj) {
		return new RoleDefinitionCreator();
	}

}
