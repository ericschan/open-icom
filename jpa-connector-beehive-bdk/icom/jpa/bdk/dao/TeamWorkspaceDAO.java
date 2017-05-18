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


import com.oracle.beehive.BeeId;
import com.oracle.beehive.EntityAddress;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.TeamWorkspace;
import com.oracle.beehive.TeamWorkspaceCreator;
import com.oracle.beehive.TeamWorkspaceUpdater;

import icom.info.AddressableInfo;
import icom.info.EntityInfo;
import icom.info.ScopeInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;

import java.util.Collection;
import java.util.HashSet;


public class TeamWorkspaceDAO extends WorkspaceDAO {
	
	{
		basicAttributes.add(ScopeInfo.Attributes.description);
		basicAttributes.add(AddressableInfo.Attributes.entityAddresses);
		basicAttributes.add(AddressableInfo.Attributes.primaryAddress);
		//basicAttributes.add(AddressableInfo.Attributes.defaultAddressForScheme);
		//basicAttributes.add(AddressableInfo.Attributes.defaultAddressForType);
	}

	static TeamWorkspaceDAO singleton = new TeamWorkspaceDAO();
	
	public static TeamWorkspaceDAO getInstance() {
		return singleton;
	}
	
	protected TeamWorkspaceDAO() {	
	}

	public String getResourceType() {
		return "wstm";
	}

    public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntity, Projection proj) {
        ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy)managedObj;

        TeamWorkspace bdkWorkspace = (TeamWorkspace)bdkEntity;
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(AddressableInfo.Attributes.primaryAddress.name(), lastLoadedProjection, proj)) {
            try {
                EntityAddress bdkPrimaryAddress = bdkWorkspace.getPrimaryAddress();
                marshallAssignEmbeddableObject(obj, AddressableInfo.Attributes.primaryAddress.name(),
                                               bdkPrimaryAddress, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(AddressableInfo.Attributes.entityAddresses.name(), lastLoadedProjection, proj)) {
            try {
                Collection<EntityAddress> bdkAddresses = bdkWorkspace.getAddresses();
                marshallAssignEmbeddableObjects(obj, AddressableInfo.Attributes.entityAddresses.name(), bdkAddresses,
                                                HashSet.class, proj);
            } catch (Exception ex) {
                // ignore
            }
        }
        super.copyObjectState(obj, bdkEntity, proj);
    }

    private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
        TeamWorkspaceUpdater updater = (TeamWorkspaceUpdater)context.getUpdater();
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();

        if (isChanged(obj, AddressableInfo.Attributes.primaryAddress.name())) {
            Persistent primaryAddress = (Persistent)getAttributeValue(pojoIdentifiable, AddressableInfo.Attributes.primaryAddress.name());
            EntityAddressDAO.getInstance().updateObjectState(primaryAddress, updater,
                                                             EntityAddressDAO.Operand.SET_PRIMARY);
        }

        if (isChanged(obj, AddressableInfo.Attributes.entityAddresses.name())) {
            Collection<Persistent> addresses = getPersistentCollection(pojoIdentifiable, AddressableInfo.Attributes.entityAddresses.name());
            AttributeChangeSet changeSet = getAttributeChanges(obj, addresses, AddressableInfo.Attributes.entityAddresses.name());
            if (changeSet.modifiedPojoObjects.size() > 0) {
                // simply recreate all addresses, since CSI does not support modify
                EntityAddressDAO.getInstance().updateObjectState(addresses, updater);
            } else {
                for (Persistent pojoEntityAddress : changeSet.addedPojoObjects) {
                    EntityAddressDAO.getInstance().updateObjectState(pojoEntityAddress, updater,
                                                                     EntityAddressDAO.Operand.ADD);
                }
                for (Persistent pojoEntityAddress : changeSet.removedPojoObjects) {
                    EntityAddressDAO.getInstance().updateObjectState(pojoEntityAddress, updater,
                                                                     EntityAddressDAO.Operand.REMOVE);
                }
            }
        }
    }
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		TeamWorkspaceCreator creator = (TeamWorkspaceCreator) context.getCreator();
		Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setName(name);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setParent(parentHandle);
		}
		
		updateNewOrOldObjectState(obj, context);
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return TeamWorkspace.class;
	}
	
	protected TeamWorkspaceUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new TeamWorkspaceUpdater();
	}
	
	protected TeamWorkspaceUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		TeamWorkspaceUpdater updater = getBdkUpdater(obj);
		((TeamWorkspaceCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected TeamWorkspaceCreator getBdkCreator(ManagedObjectProxy obj) {
		return new TeamWorkspaceCreator();
	}

}
