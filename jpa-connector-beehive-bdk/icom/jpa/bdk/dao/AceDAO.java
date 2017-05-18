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

import icom.info.AccessControlEntryInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.Manageable;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.oracle.beehive.AccessControlFieldsUpdater;
import com.oracle.beehive.AccessType;
import com.oracle.beehive.AccessTypes;
import com.oracle.beehive.Ace;
import com.oracle.beehive.BeeId;

public class AceDAO extends BdkAbstractDAO {

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
	
	static HashMap<String, String> bdkToPojoAccessTypeNameMap;
	static HashMap<String, String> pojoToCsiAccessTypeNameMap;
	
	{
		bdkToPojoAccessTypeNameMap = new HashMap<String, String>();
		pojoToCsiAccessTypeNameMap = new HashMap<String, String>();
		bdkToPojoAccessTypeNameMap.put(AccessType.READ.name(), PojoAccessType.Read.name());
		bdkToPojoAccessTypeNameMap.put(AccessType.WRITE.name(), PojoAccessType.Write.name());
		bdkToPojoAccessTypeNameMap.put(AccessType.DELETE.name(), PojoAccessType.Delete.name());
		bdkToPojoAccessTypeNameMap.put(AccessType.EXECUTE.name(), PojoAccessType.Execute.name());
		bdkToPojoAccessTypeNameMap.put(AccessType.DISCOVER.name(), PojoAccessType.Discover.name());
		for (String key : bdkToPojoAccessTypeNameMap.keySet()) {
			pojoToCsiAccessTypeNameMap.put(bdkToPojoAccessTypeNameMap.get(key), key);
		}
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
		Ace bdkACE = (Ace) bdkObject; 
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoAccessControlEntry = obj.getPojoObject();
		
		BeeId baseAccessorId = bdkACE.getAccessor();
		if (baseAccessorId != null) {
			Manageable pojoAccessor = getReference(context, baseAccessorId.getId());
			assignAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.subject.name(), pojoAccessor);
		}
		
		String packageName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.getPackageName();
		String pojoAccessTypeEnumClassName = BeehiveBeanEnumeration.BeehiveAccessTypeEnum.name();
		
		AccessTypes accessTypes = bdkACE.getAccessTypes();
		
		List<AccessType> grantedAccessTypes = accessTypes.getGrantAccessTypes();
		if (grantedAccessTypes != null) {
			Set<Enum<?>> pojoGrantedAccessTypes = new HashSet<Enum<?>>(grantedAccessTypes.size());
			for (AccessType accessType: grantedAccessTypes) {
				String bdkAccessTypeName = accessType.name();
				String pojoAccessTypeName = bdkToPojoAccessTypeNameMap.get(bdkAccessTypeName);
				Enum<?> pojoAccessType = instantiateEnum(packageName, pojoAccessTypeEnumClassName, pojoAccessTypeName);
				pojoGrantedAccessTypes.add(pojoAccessType);
			}
			assignAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.grants.name(), pojoGrantedAccessTypes);
		}
		
		List<AccessType> deniedAccessTypes = accessTypes.getDenyAccessTypes();
		if (deniedAccessTypes != null) {
			Set<Enum<?>> pojoDeniedAccessTypes = new HashSet<Enum<?>>(deniedAccessTypes.size());
			for (AccessType accessType: deniedAccessTypes) {
				String bdkAccessTypeName = accessType.name();
				String pojoAccessTypeName = bdkToPojoAccessTypeNameMap.get(bdkAccessTypeName);
				Enum<?> pojoAccessType = instantiateEnum(packageName, pojoAccessTypeEnumClassName, pojoAccessTypeName);
				pojoDeniedAccessTypes.add(pojoAccessType);
			}
			assignAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.denies.name(), pojoDeniedAccessTypes);
		}
	}

	public void updateObjectState(Object pojoAccessControlEntry, AccessControlFieldsUpdater updater, Operand operand) {
		icom.jpa.Identifiable pojoAccessor = (icom.jpa.Identifiable) getAttributeValue(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.subject.name());
		BeeId baseAccessorId = getBeeId(pojoAccessor.getObjectId().toString());
		if (operand == Operand.REMOVE) {
			// TODO
			return;
		}
		
		Ace ace = new Ace();
		ace.setAccessor(baseAccessorId);
			
		Set<Enum<?>> grants = getSetOfEnum(pojoAccessControlEntry, AccessControlEntryInfo.Attributes.grants.name());
		Set<AccessType> grantAccessTypes = null;
		if (grants != null) {
			grantAccessTypes = new HashSet<AccessType>(grants.size());
			for (Enum<?> pojoAccessType : grants) {
				String pojoAccessTypeName = pojoAccessType.name();
				String bdkAccessTypeName = pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
				AccessType bdkAccessType = AccessType.valueOf(bdkAccessTypeName);
				grantAccessTypes.add(bdkAccessType);
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
				String bdkAccessTypeName = pojoToCsiAccessTypeNameMap.get(pojoAccessTypeName);
				AccessType bdkAccessType = AccessType.valueOf(bdkAccessTypeName);
				denyAccessTypes.add(bdkAccessType);
			}
		} else {
			denyAccessTypes = new HashSet<AccessType>(0);
		}
		
		AccessTypes bdkAccessTypes = new AccessTypes();
		bdkAccessTypes.getGrantAccessTypes().addAll(grantAccessTypes);
		bdkAccessTypes.getDenyAccessTypes().addAll(denyAccessTypes);
		ace.setAccessTypes(bdkAccessTypes);
		
		if (operand == Operand.ADD) {
			updater.getLocalACLs().add(ace);
		} else if (operand == Operand.MODIFY) {
			// TODO
		}
	}
	
}
