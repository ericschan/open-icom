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


import com.oracle.beehive.Accessor;
import com.oracle.beehive.AccessorUpdater;
import com.oracle.beehive.AssociativeArray;
import com.oracle.beehive.AssociativeArrayEntry;
import com.oracle.beehive.CollabProperties;
import com.oracle.beehive.CollabPropertiesUpdater;
import com.oracle.beehive.CollabProperty;
import com.oracle.beehive.EntityAddress;

import icom.info.AddressableInfo;
import icom.info.SubjectInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;


public abstract class AccessorDAO extends BaseAccessorDAO {
	
	{
		basicAttributes.add(AddressableInfo.Attributes.entityAddresses);
		basicAttributes.add(AddressableInfo.Attributes.primaryAddress);
		//basicAttributes.add(AddressableInfo.Attributes.defaultAddressForScheme);
		//basicAttributes.add(AddressableInfo.Attributes.defaultAddressForType);
	}

	{
		fullAttributes.add(SubjectInfo.Attributes.properties);
	}
	
	protected AccessorDAO() {
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        Accessor bdkAccessor = (Accessor)bdkEntity;
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(AddressableInfo.Attributes.primaryAddress.name(), lastLoadedProjection, proj)) {
            try {
                EntityAddress bdkPrimaryAddress = bdkAccessor.getPrimaryAddress();
                marshallAssignEmbeddableObject(obj, AddressableInfo.Attributes.primaryAddress.name(),
                                                          bdkPrimaryAddress, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(AddressableInfo.Attributes.entityAddresses.name(), lastLoadedProjection, proj)) {
            try {
                Collection<EntityAddress> bdkAddresses = bdkAccessor.getAddresses();
                marshallAssignEmbeddableObjects(obj, AddressableInfo.Attributes.entityAddresses.name(),
                                                          bdkAddresses, HashSet.class, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(SubjectInfo.Attributes.properties.name(), lastLoadedProjection, proj)) {
            try {
                Vector<Object> v = new Vector<Object>();
                CollabProperties propertyMaps = bdkAccessor.getProperties();
                if (propertyMaps != null) {
                    AssociativeArray properties = propertyMaps.getMap();
                    List<AssociativeArrayEntry> entries = properties.getEntries();
                    for (AssociativeArrayEntry entry : entries) {
                        CollabProperty bdkCollabProperty = (CollabProperty)entry.getValue();
                        v.add(bdkCollabProperty);
                    }
                }
                marshallAssignEmbeddableObjects(obj, SubjectInfo.Attributes.properties.name(), v,
                                                          Vector.class, proj);

            } catch (Exception ex) {
                // ignore
            }
        }

    }

    private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
        AccessorUpdater updater = (AccessorUpdater)context.getUpdater();
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

        if (isChanged(obj, SubjectInfo.Attributes.properties.name())) {
            Collection<Persistent> properties = getPersistentCollection(pojoIdentifiable, SubjectInfo.Attributes.properties.name());
            AttributeChangeSet changeSet = getAttributeChanges(obj, properties, SubjectInfo.Attributes.properties.name());
            CollabPropertiesUpdater propertiesUpdater = updater.getPropertiesUpdater();
            for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                CollabPropertyDAO.getInstance().updateObjectState(addedPojoObject, propertiesUpdater,
                                                                  CollabPropertyDAO.Operand.ADD);
            }
            for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                CollabPropertyDAO.getInstance().updateObjectState(removedPojoObject, propertiesUpdater,
                                                                  CollabPropertyDAO.Operand.REMOVE);
            }
            for (Persistent modifiedPojoObject : changeSet.modifiedPojoObjects) {
                CollabPropertyDAO.getInstance().updateObjectState(modifiedPojoObject, propertiesUpdater,
                                                                  CollabPropertyDAO.Operand.MODIFY);
            }
        }
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
