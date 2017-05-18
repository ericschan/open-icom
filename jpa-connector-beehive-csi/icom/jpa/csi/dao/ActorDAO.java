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

import icom.info.ActorInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oracle.csi.Actor;
import oracle.csi.CollabId;
import oracle.csi.Community;
import oracle.csi.CommunityHandle;
import oracle.csi.CsiRuntimeException;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.ActorUpdater;

public abstract class ActorDAO extends AccessorDAO {
	
	{
		
	}

	{
		fullAttributes.add(ActorInfo.Attributes.assignedCommunities);
	}
	
	{

	}

	protected ActorDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Actor csiActor = (Actor) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ActorInfo.Attributes.assignedCommunities.name(), lastLoadedProjection, proj)) {
			/*
			try {
				Set<Community> csiCommunities = csiActor.getMemberships();
				HashSet<icom.jpa.spi.Identifiable> pojoCommunities = new HashSet<icom.jpa.spi.Identifiable>();
				Iterator<Community> csiCommunitiesIter = csiCommunities.iterator();
				while (csiCommunitiesIter.hasNext()) {
					ManagedIdentifiableProxy communityObj = getEntityProxy(context, csiCommunitiesIter.next());
					pojoCommunities.add(communityObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, ActorInfo.Attributes.assignedCommunities.name(), pojoCommunities);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			*/
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ActorInfo.Attributes.assignedCommunities.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(ActorInfo.Attributes.assignedCommunities.name());
				Set<Community> csiCommunities = csiActor.getMemberships();
				HashSet<Persistent> pojoCommunities = new HashSet<Persistent>(csiCommunities.size());
				for (Community csiCommunity : csiCommunities) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedCommunity = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedCommunity.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiCommunity.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiCommunity);
						pojoCommunities.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						pojoCommunities.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, ActorInfo.Attributes.assignedCommunities.name(), pojoCommunities);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}

	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		ActorUpdater updater = (ActorUpdater) context.getUpdater();
		
		if (isChanged(obj, ActorInfo.Attributes.assignedCommunities.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(ActorInfo.Attributes.assignedCommunities.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent assignedCommunity = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberCommunityObj = (ManagedIdentifiableProxy) assignedCommunity.getManagedObjectProxy();
					CollabId id = getCollabId(memberCommunityObj.getObjectId());
					CommunityHandle communityHandle = (CommunityHandle) EntityUtils.getInstance().createHandle(id);
					updater.addMembership(communityHandle);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ActorInfo.Attributes.assignedCommunities.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent assignedCommunity = (Persistent) holder.getValue();
					ManagedIdentifiableProxy memberCommunityObj = (ManagedIdentifiableProxy) assignedCommunity.getManagedObjectProxy();
					CollabId id = getCollabId(memberCommunityObj.getObjectId());
					CommunityHandle communityHandle = (CommunityHandle) EntityUtils.getInstance().createHandle(id);
					updater.removeMembership(communityHandle);
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
		updateNewOrOldObjectState(obj, context);
	}

}
