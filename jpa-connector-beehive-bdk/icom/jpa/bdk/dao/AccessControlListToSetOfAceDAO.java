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


import com.oracle.beehive.AccessControlFieldsUpdater;

import icom.info.AccessControlListInfo;

import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;


public class AccessControlListToSetOfAceDAO extends BdkAbstractDAO {

	static AccessControlListToSetOfAceDAO singleton = new AccessControlListToSetOfAceDAO();
	
	public static AccessControlListToSetOfAceDAO getInstance() {
		return singleton;
	}
	
	AccessControlListToSetOfAceDAO() {
		
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
        ArrayList<Object> bdkSetOfACE = (ArrayList<Object>)bdkObject;
        Persistent pojoAccessControlList = obj.getPojoObject();

        Persistent pojoEntity = ((ManagedDependentProxy)obj).getParent().getPojoObject();
        assignAttributeValue(pojoAccessControlList, AccessControlListInfo.Attributes.object.name(), pojoEntity);

        Vector<Object> v = new Vector<Object>();
        for (Object bdkAce : bdkSetOfACE) {
            v.add(bdkAce);
        }
        marshallAssignEmbeddableObjects(obj, AccessControlListInfo.Attributes.accessControlEntries.name(), v,
                                                  HashSet.class, proj);
    }

    public void updateObjectState(Object pojoAccessControlList, AccessControlFieldsUpdater updater) {
        ManagedObjectProxy accessControlListObj = ((Persistent)pojoAccessControlList).getManagedObjectProxy();

        if (isChanged(accessControlListObj, AccessControlListInfo.Attributes.accessControlEntries.name())) {
            Collection<Persistent> pojoAccessControlEntries =
                getPersistentCollection(pojoAccessControlList, AccessControlListInfo.Attributes.accessControlEntries.name());
            AttributeChangeSet changeSet = getAttributeChanges(accessControlListObj, pojoAccessControlEntries,
                                AccessControlListInfo.Attributes.accessControlEntries.name());
            for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                AceDAO.getInstance().updateObjectState(addedPojoObject, updater, AceDAO.Operand.ADD);
            }
            for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                AceDAO.getInstance().updateObjectState(removedPojoObject, updater, AceDAO.Operand.REMOVE);
            }
            for (Persistent modifiedPojoObject : changeSet.modifiedPojoObjects) {
                AceDAO.getInstance().updateObjectState(modifiedPojoObject, updater, AceDAO.Operand.MODIFY);
            }
        }
    }
	
}
