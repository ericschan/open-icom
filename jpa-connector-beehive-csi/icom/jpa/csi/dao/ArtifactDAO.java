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

import icom.info.AbstractBeanInfo;
import icom.info.ArtifactInfo;
import icom.info.BeanInfo;
import icom.info.PropertyInfo;
import icom.info.RelationshipBondableInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import oracle.csi.Artifact;
import oracle.csi.CollabProperties;
import oracle.csi.CollabProperty;
import oracle.csi.CsiRuntimeException;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.ArtifactUpdater;
import oracle.csi.updaters.CollabPropertiesUpdater;

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
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		PersistenceContext context = obj.getPersistenceContext();
		Artifact csiArtifact = (Artifact) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ArtifactInfo.Attributes.userCreationDate.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userCreationDate.name(), csiArtifact.getUserCreatedOn());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(ArtifactInfo.Attributes.userLastModificationDate.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userLastModificationDate.name(), csiArtifact.getUserModifiedOn());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(ArtifactInfo.Attributes.properties.name(), lastLoadedProjection, proj)) {
			try {
				Vector<Object> v = new Vector<Object>();
				CollabProperties propertyMaps = csiArtifact.getProperties();
				if (propertyMaps != null) {
					Collection<CollabProperty> properties = propertyMaps.values();
					Iterator<CollabProperty> iter = properties.iterator();
					while (iter.hasNext()) {
						CollabProperty csiCollabProperty = iter.next();
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, csiCollabProperty, obj, ArtifactInfo.Attributes.properties.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, csiCollabProperty, proj);
						v.add(propertyObj.getPojoObject());
					}
				}
				
				Collection<Object> previousProperties = getObjectCollection(pojoIdentifiable, ArtifactInfo.Attributes.properties.name());
				if (previousProperties != null) {
					for (Object pojoProperty : previousProperties) {
						if (pojoProperty instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.properties.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(ArtifactInfo.Attributes.viewerProperties.name(), lastLoadedProjection, proj)) {
			try {
				Vector<Object> v = new Vector<Object>();
				CollabProperties propertyMaps = csiArtifact.getViewerProperties();
				if (propertyMaps != null) {
					Collection<CollabProperty> viewerProperties = propertyMaps.values();
					Iterator<CollabProperty> iter = viewerProperties.iterator();
					while (iter.hasNext()) {
						CollabProperty csiCollabProperty = iter.next();
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, csiCollabProperty, obj, ArtifactInfo.Attributes.viewerProperties.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, csiCollabProperty, proj);
						v.add(propertyObj.getPojoObject());
					}	
				}
				
				Collection<Object> previousProperties = getObjectCollection(pojoIdentifiable, ArtifactInfo.Attributes.viewerProperties.name());
				if (previousProperties != null) {
					for (Object pojoProperty : previousProperties) {
						if (pojoProperty instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.viewerProperties.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		ArtifactUpdater updater = (ArtifactUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		if (!(this instanceof TimeManagement)) {
			if (isChanged(obj, ArtifactInfo.Attributes.userCreationDate.name())) {
				Date userCreatedOn = (Date) getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userCreationDate.name());
				updater.setUserCreatedOn(userCreatedOn);
			}
		}
		if (!(this instanceof TimeManagement)) {
			if (isChanged(obj, ArtifactInfo.Attributes.userLastModificationDate.name())) {
				Date userModifiedOn = (Date) getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userLastModificationDate.name());
				updater.setUserModifiedOn(userModifiedOn);
			}
		}
		if (isChanged(obj, ArtifactInfo.Attributes.properties.name())) {
			ArrayList<Persistent> modifiedProperties = new ArrayList<Persistent>();
			Collection<Object> properties = getObjectCollection(pojoIdentifiable, ArtifactInfo.Attributes.properties.name());
			if (properties != null) {
				for (Object property : properties) {
					Persistent pojo = (Persistent) property;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.containsAttributeChangeRecord(PropertyInfo.Attributes.value.name())) {
						modifiedProperties.add(pojo);
					}
				}
			}
			CollabPropertiesUpdater propertiesUpdater = updater.getPropertiesUpdater();
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(ArtifactInfo.Attributes.properties.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent pojoProperty = (Persistent) holder.getValue();
					CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, propertiesUpdater, CollabPropertyDAO.Operand.ADD);
					modifiedProperties.remove(pojoProperty);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ArtifactInfo.Attributes.properties.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent pojoProperty = (Persistent) holder.getValue();
					CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, propertiesUpdater, CollabPropertyDAO.Operand.REMOVE);
					modifiedProperties.remove(pojoProperty);
				}
			}
			for (Persistent modifiedProperty : modifiedProperties) {
				CollabPropertyDAO.getInstance().updateObjectState(modifiedProperty, propertiesUpdater, CollabPropertyDAO.Operand.MODIFY);
			}
		}
		if (isChanged(obj, ArtifactInfo.Attributes.viewerProperties.name())) {
			ArrayList<Persistent> modifiedViewerProperties = new ArrayList<Persistent>();
			Collection<Object> properties = getObjectCollection(pojoIdentifiable, ArtifactInfo.Attributes.viewerProperties.name());
			if (properties != null) {
				for (Object property : properties) {
					Persistent pojo = (Persistent) property;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.containsAttributeChangeRecord(PropertyInfo.Attributes.value.name())) {
						modifiedViewerProperties.add(pojo);
					}
				}
			}
			CollabPropertiesUpdater viewerPropertiesUpdater = updater.getViewerPropertiesUpdater();
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(ArtifactInfo.Attributes.viewerProperties.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent pojoProperty = (Persistent) holder.getValue();
					CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, viewerPropertiesUpdater, CollabPropertyDAO.Operand.ADD);
					modifiedViewerProperties.remove(pojoProperty);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ArtifactInfo.Attributes.viewerProperties.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent pojoProperty = (Persistent) holder.getValue();
					CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, viewerPropertiesUpdater, CollabPropertyDAO.Operand.REMOVE);
					modifiedViewerProperties.remove(pojoProperty);
				}
			}
			for (Persistent modifiedViewerProperty : modifiedViewerProperties) {
				CollabPropertyDAO.getInstance().updateObjectState(modifiedViewerProperty, viewerPropertiesUpdater, CollabPropertyDAO.Operand.MODIFY);
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
