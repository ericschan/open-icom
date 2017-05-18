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

import icom.info.AccessControlEntryInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiAbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import oracle.csi.ACE;
import oracle.csi.AccessType;
import oracle.csi.AccessTypes;
import oracle.csi.BaseAccessor;
import oracle.csi.BaseAccessorHandle;
import oracle.csi.CollabId;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AccessControlFieldsUpdater;

public class AceDAO extends CsiAbstractDAO {

	static AceDAO singleton = new AceDAO();
	
	public static AceDAO getInstance() {
		return singleton;
	}
	
	enum Operand { ADD, REMOVE, MODIFY };
	
	AceDAO() {
		
	}
	
	enum PojoAccessType {
		Read,
		Write,
		Delete,
		Execute,
		Discover
	}
	
	static HashMap<String, String> csiToPojoAccessTypeNameMap;
	static HashMap<String, String> pojoToCsiAccessTypeNameMap;
	
	{
		csiToPojoAccessTypeNameMap = new HashMap<String, String>();
		pojoToCsiAccessTypeNameMap = new HashMap<String, String>();
		csiToPojoAccessTypeNameMap.put(AccessType.READ.name(), PojoAccessType.Read.name());
		csiToPojoAccessTypeNameMap.put(AccessType.WRITE.name(), PojoAccessType.Write.name());
		csiToPojoAccessTypeNameMap.put(AccessType.DELETE.name(), PojoAccessType.Delete.name());
		csiToPojoAccessTypeNameMap.put(AccessType.EXECUTE.name(), PojoAccessType.Execute.name());
		csiToPojoAccessTypeNameMap.put(AccessType.DISCOVER.name(), PojoAccessType.Discover.name());
		for (String key : csiToPojoAccessTypeNameMap.keySet()) {
			pojoToCsiAccessTypeNameMap.put(csiToPojoAccessTypeNameMap.get(key), key);
		}
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		ACE csiACE = (ACE) csiObject; 
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoAccessControlEntry = obj.getPojoObject();
		
		BaseAccessorHandle baseAccessorHandle = csiACE.getAccessor();
		if (baseAccessorHandle != null) {
			BaseAccessor csiBaseAccessor = (BaseAccessor) EntityUtils.getInstance().createEmptyEntity(baseAccessorHandle.getCollabId());
			ManagedIdentifiableProxy accessorObj = getEntityProxy(context, csiBaseAccessor);
			Persistent pojoAccessor = accessorObj.getPojoIdentifiable();
			assignAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.subject.name(), pojoAccessor);
		}
		
		String packageName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.getPackageName();
		String pojoAccessTypeEnumClassName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.name();
		
		AccessTypes accessTypes = csiACE.getAccessTypes();
		
		Set<AccessType> grantedAccessTypes = accessTypes.getGrantAccessTypes();
		if (grantedAccessTypes != null) {
			Set<Enum<?>> pojoGrantedAccessTypes = new HashSet<Enum<?>>(grantedAccessTypes.size());
			for (AccessType accessType: grantedAccessTypes) {
				String csiAccessTypeName = accessType.name();
				String pojoAccessTypeName = csiToPojoAccessTypeNameMap.get(csiAccessTypeName);
				Enum<?> pojoAccessType = instantiateEnum(packageName, pojoAccessTypeEnumClassName, pojoAccessTypeName);
				pojoGrantedAccessTypes.add(pojoAccessType);
			}
			assignAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.grants.name(), pojoGrantedAccessTypes);
		}
		
		Set<AccessType> deniedAccessTypes = accessTypes.getDenyAccessTypes();
		if (deniedAccessTypes != null) {
			Set<Enum<?>> pojoDeniedAccessTypes = new HashSet<Enum<?>>(deniedAccessTypes.size());
			for (AccessType accessType: deniedAccessTypes) {
				String csiAccessTypeName = accessType.name();
				String pojoAccessTypeName = csiToPojoAccessTypeNameMap.get(csiAccessTypeName);
				Enum<?> pojoAccessType = instantiateEnum(packageName, pojoAccessTypeEnumClassName, pojoAccessTypeName);
				pojoDeniedAccessTypes.add(pojoAccessType);
			}
			assignAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.denies.name(), pojoDeniedAccessTypes);
		}
	}

	public void updateObjectState(Object pojoAccessControlEntry, AccessControlFieldsUpdater updater, Operand operand) {
		icom.jpa.Identifiable pojoAccessor = (icom.jpa.Identifiable) getAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.subject.name());
		CollabId baseAccessorId = getCollabId(pojoAccessor.getObjectId());
		BaseAccessorHandle csiBaseAccessorHandle = (BaseAccessorHandle) EntityUtils.getInstance().createHandle(baseAccessorId);
		if (operand == Operand.REMOVE) {
			updater.removeLocalACE(csiBaseAccessorHandle);
			return;
		}
		
		ACE ace = new ACE();
		ace.setAccessor(csiBaseAccessorHandle);
			
		Set<Enum<?>> grants = getSetOfEnum(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.grants.name());
		Set<AccessType> grantAccessTypes = null;
		if (grants != null) {
			grantAccessTypes = new HashSet<AccessType>(grants.size());
			for (Enum<?> pojoAccessType : grants) {
				String pojoAccessTypeName = pojoAccessType.name();
				String csiAccessTypeName = pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
				AccessType csiAccessType = AccessType.valueOf(csiAccessTypeName);
				grantAccessTypes.add(csiAccessType);
			}
		} else {
			grantAccessTypes = new HashSet<AccessType>(0);
		}
		
		Set<Enum<?>> denies = getSetOfEnum(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.denies.name());
		Set<AccessType> denyAccessTypes = null;
		if (denies != null) {
			denyAccessTypes = new HashSet<AccessType>(grants.size());
			for (Enum<?> pojoAccessType : denies) {
				String pojoAccessTypeName = pojoAccessType.name();
				String csiAccessTypeName = pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
				AccessType csiAccessType = AccessType.valueOf(csiAccessTypeName);
				denyAccessTypes.add(csiAccessType);
			}
		} else {
			denyAccessTypes = new HashSet<AccessType>(0);
		}
		
		AccessTypes csiAccessTypes = new AccessTypes(grantAccessTypes, denyAccessTypes);
		ace.setAccessTypes(csiAccessTypes);
		
		if (operand == Operand.ADD) {
			updater.addLocalACE(ace);
		} else if (operand == Operand.MODIFY) {
			updater.replaceLocalACE(ace);
		}
	}
	
}
