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


import com.oracle.beehive.Artifact;
import com.oracle.beehive.ArtifactUpdater;
import com.oracle.beehive.AssociativeArray;
import com.oracle.beehive.AssociativeArrayEntry;
import com.oracle.beehive.CollabProperties;
import com.oracle.beehive.CollabPropertiesUpdater;
import com.oracle.beehive.CollabProperty;

import icom.info.ArtifactInfo;
import icom.info.RelationshipBondableInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.persistence.PersistenceException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public abstract class ArtifactDAO extends EntityDAO {
	
	{
		basicAttributes.add(ArtifactInfo.Attributes.description);
		//basicAttributes.add(ArtifactInfo.Attributes.changeStatus);
	}

	{
		metaAttributes.add(ArtifactInfo.Attributes.userCreationDate);
		metaAttributes.add(ArtifactInfo.Attributes.userLastModificationDate);
		metaAttributes.add(ArtifactInfo.Attributes.properties);
		metaAttributes.add(ArtifactInfo.Attributes.viewerProperties);
	}
	
	{
		lazyAttributes.add(RelationshipBondableInfo.Attributes.relationships);
	}
	
	protected ArtifactDAO() {
	}
	
    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        Artifact bdkArtifact = (Artifact)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoObject();

        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(ArtifactInfo.Attributes.userCreationDate.name(), lastLoadedProjection, proj)) {
            try {
                XMLGregorianCalendar bdkUserCreatedOn = bdkArtifact.getUserCreatedOn();
                if (bdkUserCreatedOn != null) {
                    Date pojoUserCreationDate = bdkUserCreatedOn.toGregorianCalendar().getTime();
                    assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userCreationDate.name(),
                                         pojoUserCreationDate);
                } else {
                    assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userCreationDate.name(), null);
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(ArtifactInfo.Attributes.userLastModificationDate.name(), lastLoadedProjection,
                                 proj)) {
            try {
                XMLGregorianCalendar bdkUserLastModificationDate = bdkArtifact.getUserModifiedOn();
                if (bdkUserLastModificationDate != null) {
                    Date pojoUserLastModificationDate = bdkUserLastModificationDate.toGregorianCalendar().getTime();
                    assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userLastModificationDate.name(),
                                         pojoUserLastModificationDate);
                } else {
                    assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userLastModificationDate.name(), null);
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(ArtifactInfo.Attributes.properties.name(), lastLoadedProjection, proj)) {
            try {
                Vector<Object> v = new Vector<Object>();
                CollabProperties propertyMaps = bdkArtifact.getProperties();
                if (propertyMaps != null) {
                    AssociativeArray properties = propertyMaps.getMap();
                    List<AssociativeArrayEntry> entries = properties.getEntries();
                    for (AssociativeArrayEntry entry : entries) {
                        CollabProperty bdkCollabProperty = (CollabProperty)entry.getValue();
                        v.add(bdkCollabProperty);
                    }
                }
                marshallAssignEmbeddableObjects(obj, ArtifactInfo.Attributes.properties.name(), v,
                                                          Vector.class, proj);

            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(ArtifactInfo.Attributes.viewerProperties.name(), lastLoadedProjection, proj)) {
            try {
                Vector<Object> v = new Vector<Object>();
                CollabProperties propertyMaps = bdkArtifact.getViewerProperties();
                if (propertyMaps != null) {
                    AssociativeArray properties = propertyMaps.getMap();
                    List<AssociativeArrayEntry> entries = properties.getEntries();
                    for (AssociativeArrayEntry entry : entries) {
                        CollabProperty bdkCollabProperty = (CollabProperty)entry.getValue();
                        v.add(bdkCollabProperty);
                    }
                }
                marshallAssignEmbeddableObjects(obj, ArtifactInfo.Attributes.viewerProperties.name(), v,
                                                          Vector.class, proj);

            } catch (Exception ex) {
                // ignore
            }
        }

    }

    private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
        ArtifactUpdater updater = (ArtifactUpdater)context.getUpdater();
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();
        if (!(this instanceof TimeManagement)) {
            if (isChanged(obj, ArtifactInfo.Attributes.userCreationDate.name())) {
                Date userCreatedOn = (Date)getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userCreationDate.name());
                if (userCreatedOn != null) {
                    GregorianCalendar gcal = new GregorianCalendar();
                    gcal.setTime(userCreatedOn);
                    try {
                        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
                        updater.setUserCreatedOn(xgcal);
                    } catch (DatatypeConfigurationException ex) {
                        throw new PersistenceException(ex);
                    }
                } else {
                    updater.setUserCreatedOn(null);
                }
            }
        }
        if (!(this instanceof TimeManagement)) {
            if (isChanged(obj, ArtifactInfo.Attributes.userLastModificationDate.name())) {
                Date userModifiedOn = (Date)getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userLastModificationDate.name());
                if (userModifiedOn != null) {
                    GregorianCalendar gcal = new GregorianCalendar();
                    gcal.setTime(userModifiedOn);
                    try {
                        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
                        updater.setUserModifiedOn(xgcal);
                    } catch (DatatypeConfigurationException ex) {
                        throw new PersistenceException(ex);
                    }
                } else {
                    updater.setUserModifiedOn(null);
                }
            }
        }

        if (isChanged(obj, ArtifactInfo.Attributes.properties.name())) {
            Collection<Persistent> properties = getPersistentCollection(pojoIdentifiable, ArtifactInfo.Attributes.properties.name());
            AttributeChangeSet changeSet = getAttributeChanges(obj, properties, ArtifactInfo.Attributes.properties.name());
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

        if (isChanged(obj, ArtifactInfo.Attributes.viewerProperties.name())) {
            Collection<Persistent> properties = getPersistentCollection(pojoIdentifiable, ArtifactInfo.Attributes.viewerProperties.name());
            AttributeChangeSet changeSet = getAttributeChanges(obj, properties, ArtifactInfo.Attributes.viewerProperties.name());
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
