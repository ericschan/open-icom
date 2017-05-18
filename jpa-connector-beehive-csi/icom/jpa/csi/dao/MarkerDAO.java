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

import icom.info.ArtifactInfo;
import icom.info.MarkerInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import oracle.csi.CollabId;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.Marker;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.MarkerUpdater;

public abstract class MarkerDAO extends ArtifactDAO {
	
	{
		basicAttributes.add(ArtifactInfo.Attributes.description);
	}
	
	{
		fullAttributes.add(MarkerInfo.Attributes.markedEntities);
	}
	
	protected MarkerDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Marker csiMarker = (Marker) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(ArtifactInfo.Attributes.description.name(), lastLoadedProjection) &&
				isPartOfProjection(ArtifactInfo.Attributes.description.name(),  proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(), csiMarker.getDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(MarkerInfo.Attributes.markedEntities.name(), lastLoadedProjection) &&
				isPartOfProjection(MarkerInfo.Attributes.markedEntities.name(),  proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(MarkerInfo.Attributes.markedEntities.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(MarkerInfo.Attributes.markedEntities.name());
				Vector<Persistent> v = new Vector<Persistent>();
				Collection<Entity> list = csiMarker.getEntities();
				Iterator<Entity> iter = list.iterator();
				while (iter.hasNext()) {
					Entity csiMarkedEntity = iter.next();
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedEntity = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedEntity.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiMarkedEntity.getCollabId())) {
								isRemoved = true;
								break;
							}
						}
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiMarkedEntity);
						v.add(childObj.getPojoIdentifiable());
					}
					if (addedObjects != null) {
						Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
						while (addedObjectsIter.hasNext()) {
							Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
							v.add(identifiable);
						}
					}
				}
				assignAttributeValue(pojoIdentifiable, MarkerInfo.Attributes.markedEntities.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		MarkerUpdater updater = (MarkerUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, ArtifactInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name());
			updater.setDescription(description);
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
