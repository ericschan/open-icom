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

import icom.info.AccessControlListInfo;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiAbstractDAO;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import oracle.csi.ACE;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AccessControlFieldsUpdater;

public class AccessControlListToSetOfAceDAO extends CsiAbstractDAO {

	static AccessControlListToSetOfAceDAO singleton = new AccessControlListToSetOfAceDAO();
	
	public static AccessControlListToSetOfAceDAO getInstance() {
		return singleton;
	}
	
	AccessControlListToSetOfAceDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		Set<ACE> csiSetOfACE = (Set<ACE>) csiObject;
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoAccessControlList = obj.getPojoObject();
		
		Persistent pojoEntity = ((ManagedDependentProxy)obj).getParent().getPojoObject();	
		assignAttributeValue(pojoAccessControlList, AccessControlListInfo.Attributes.object.name(), pojoEntity);
		
		Collection<Object> pojoAccessControlEntries = new HashSet<Object>(csiSetOfACE.size());
		for (ACE csiACE : csiSetOfACE) {
			ManagedObjectProxy accessControlEntryObj = getNonIdentifiableDependentProxy(context, csiACE, obj, AccessControlListInfo.Attributes.accessControlEntries.name());
			accessControlEntryObj.getProviderProxy().copyLoadedProjection(accessControlEntryObj, csiACE, proj);
			Persistent pojoaccessControlEntry = accessControlEntryObj.getPojoObject();
			pojoAccessControlEntries.add(pojoaccessControlEntry);
		}
		assignAttributeValue(pojoAccessControlList, AccessControlListInfo.Attributes.accessControlEntries.name(), pojoAccessControlEntries);
	}
	
	public void updateObjectState(Object pojoAccessControlList, AccessControlFieldsUpdater updater) {
		ManagedObjectProxy accessControlListObj = ((Persistent)pojoAccessControlList).getManagedObjectProxy();
		
		if (isChanged(accessControlListObj, AccessControlListInfo.Attributes.accessControlEntries.name())) {
			ArrayList<Persistent> modifiedAccessControlEntries = new ArrayList<Persistent>();
			Collection<Persistent> pojoAccessControlEntries = getPersistentCollection(pojoAccessControlList, AccessControlListInfo.Attributes.accessControlEntries.name());
			if (pojoAccessControlEntries != null) {
				for (Persistent pojoAccessControlEntry : pojoAccessControlEntries) {
					ManagedObjectProxy accessControlEntryObj = pojoAccessControlEntry.getManagedObjectProxy();
					if (accessControlEntryObj.hasAttributeChanges()) {
						modifiedAccessControlEntries.add(pojoAccessControlEntry);
					}
				}
			}
			Collection<ValueHolder> addedObjects = accessControlListObj.getAddedObjects(AccessControlListInfo.Attributes.accessControlEntries.name());
			if (addedObjects != null) {
				for (ValueHolder holder : addedObjects) {
					Persistent pojoAccessControlEntry = (Persistent) holder.getValue();
					AceDAO.getInstance().updateObjectState(pojoAccessControlEntry, updater, AceDAO.Operand.ADD);
					modifiedAccessControlEntries.remove(pojoAccessControlEntry);
				}
			}
			Collection<ValueHolder> removedObjects = accessControlListObj.getRemovedObjects(AccessControlListInfo.Attributes.accessControlEntries.name());
			if (removedObjects != null) {
				for (ValueHolder holder : removedObjects) {
					Persistent pojoAccessControlEntry = (Persistent) holder.getValue();
					AceDAO.getInstance().updateObjectState(pojoAccessControlEntry, updater, AceDAO.Operand.REMOVE);
					modifiedAccessControlEntries.remove(pojoAccessControlEntry);
				}
			}
			for (Persistent modifiedAccessControlEntry : modifiedAccessControlEntries) {
				AceDAO.getInstance().updateObjectState(modifiedAccessControlEntry, updater, AceDAO.Operand.MODIFY);
			}
		}
	}
	
}
