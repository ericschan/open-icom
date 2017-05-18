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

import icom.info.CommunityInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.Community;
import oracle.csi.CommunityHandle;
import oracle.csi.CsiException;
import oracle.csi.Organization;
import oracle.csi.OrganizationHandle;
import oracle.csi.controls.CommunityControl;
import oracle.csi.controls.CommunityFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.OrganizationListFilter;
import oracle.csi.projections.Projection;

public abstract class CommunityDAO extends ScopeDAO {
	
	{
		lazyAttributes.add(CommunityInfo.Attributes.actors);
		lazyAttributes.add(CommunityInfo.Attributes.communities);
		lazyAttributes.add(CommunityInfo.Attributes.spaces);
	}

	public enum SecurityAttributes {
		
	}
	
	public enum LazyAttributes {
		
	}

	protected CommunityDAO() {
	}

	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		//Community csiCommunity = (Community) csiEntity;
	}
	
	protected abstract OrganizationHandle getOrganizationHandle(ManagedIdentifiableProxy obj);
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object key) {
		super.loadAndCopyObjectState(obj, attributeName, key);
		
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (CommunityInfo.Attributes.actors.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CommunityInfo.Attributes.actors.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(CommunityInfo.Attributes.actors.name());
			OrganizationHandle csiOrganizationHandle = getOrganizationHandle(obj);
			Set<Persistent> actors = null;
			actors = OrganizationUserDAO.getInstance().loadActors(obj, csiOrganizationHandle, Projection.EMPTY);
			if (removedObjects != null) {
				Persistent[] array = actors.toArray(new Persistent[0]);
				for (Persistent actor : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedActor = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedActor.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)actor.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							actors.remove(actor);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					actors.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, CommunityInfo.Attributes.actors.name(), actors);
		}
		
		if (CommunityInfo.Attributes.communities.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CommunityInfo.Attributes.communities.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(CommunityInfo.Attributes.communities.name());
			Set<Persistent> subCommunities = null;
			subCommunities = loadCommunities(obj, Projection.EMPTY);
			if (removedObjects != null) {
				Persistent[] array = subCommunities.toArray(new Persistent[0]);
				for (Persistent community : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedCommunity = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedCommunity.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)community.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							subCommunities.remove(community);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					subCommunities.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, CommunityInfo.Attributes.communities.name(), subCommunities);
		}
		
		if (CommunityInfo.Attributes.spaces.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CommunityInfo.Attributes.spaces.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(CommunityInfo.Attributes.spaces.name());
			Set<Persistent> spaces = null;
			CollabId id = getCollabId(obj.getObjectId());
			CommunityHandle csiCommunityHandle = (CommunityHandle) EntityUtils.getInstance().createHandle(id);
			spaces = WorkspaceDAO.loadSpaces(obj, csiCommunityHandle, Projection.EMPTY);
			if (removedObjects != null) {
				Persistent[] array = spaces.toArray(new Persistent[0]);
				for (Persistent space : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedSpace = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedSpace.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)space.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							spaces.remove(space);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					spaces.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, CommunityInfo.Attributes.spaces.name(), spaces);
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}
			
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Set<Persistent> loadCommunities(ManagedIdentifiableProxy obj, Projection proj) {
		CollabId id = getCollabId(obj.getObjectId());
		CommunityHandle csiCommunityHandle = (CommunityHandle) EntityUtils.getInstance().createHandle(id);
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoCommunities = null;
		try {
			CommunityControl control = ControlLocator.getInstance().getControl(CommunityControl.class);
			OrganizationListFilter communityListFilter = CommunityFactory.getInstance().createOrganizationListFilter();
			communityListFilter.setProjection(proj);
			ListResult<Organization> csiCommunities = control.listOrganizations(csiCommunityHandle, communityListFilter);
			if (csiCommunities != null) {
				pojoCommunities = new HashSet<Persistent>(csiCommunities.size());
				for (Community csiCommunity : csiCommunities) {
					ManagedIdentifiableProxy communityObj = getEntityProxy(context, csiCommunity);
					communityObj.getProviderProxy().copyLoadedProjection(communityObj, csiCommunity, proj);
					pojoCommunities.add(communityObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoCommunities;
	}

}
