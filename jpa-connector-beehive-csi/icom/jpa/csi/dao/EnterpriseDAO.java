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

import icom.info.beehive.BeehiveEnterpriseInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.Enterprise;
import oracle.csi.EnterpriseHandle;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.OrganizationHandle;
import oracle.csi.SnapshotId;
import oracle.csi.controls.CommunityControl;
import oracle.csi.controls.CommunityFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.EnterpriseUpdater;
import oracle.csi.util.UpdateMode;

public class EnterpriseDAO extends CommunityDAO {

	static EnterpriseDAO singleton = new EnterpriseDAO();
	
	public static EnterpriseDAO getInstance() {
		return singleton;
	}
	
	{
		lazyAttributes.add(BeehiveEnterpriseInfo.Attributes.availableCategories);
		lazyAttributes.add(BeehiveEnterpriseInfo.Attributes.availableTags);
	}

	protected EnterpriseDAO() {	
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return EnterpriseHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Enterprise csiEnterprise = null;
		try {
			CommunityControl control = ControlLocator.getInstance().getControl(CommunityControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			EnterpriseHandle csiEnterpriseHandle = (EnterpriseHandle) EntityUtils.getInstance().createHandle(id);
			csiEnterprise = control.loadEnterprise(csiEnterpriseHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiEnterprise;
	}
		
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
	}
	
	protected OrganizationHandle getOrganizationHandle(ManagedIdentifiableProxy obj) {
		return null;
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object key) {
		super.loadAndCopyObjectState(obj, attributeName, key);
		
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (BeehiveEnterpriseInfo.Attributes.availableCategories.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveEnterpriseInfo.Attributes.availableCategories.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(BeehiveEnterpriseInfo.Attributes.availableCategories.name());
			Set<Persistent> categories = null;
			categories = CategoryDAO.getInstance().loadCategories(obj, Projection.EMPTY);
			if (removedObjects != null) {
				Persistent[] array = categories.toArray(new Persistent[0]);
				for (Persistent category : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedCategory = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedCategory.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)category.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							categories.remove(category);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					categories.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, BeehiveEnterpriseInfo.Attributes.availableCategories.name(), categories);
		}
		
		if (BeehiveEnterpriseInfo.Attributes.availableTags.name().equals(attributeName)) {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveEnterpriseInfo.Attributes.availableTags.name());
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(BeehiveEnterpriseInfo.Attributes.availableTags.name());
			Set<Persistent> tags = null;
			tags = LabelDAO.getInstance().loadAvailableLabels(obj, Projection.BASIC);
			if (removedObjects != null) {
				Persistent[] array = tags.toArray(new Persistent[0]);
				for (Persistent tag : array) {
					for (ValueHolder holder : removedObjects) {
						Persistent removedTag = (Persistent) holder.getValue();
						CollabId id1 = getCollabId(((ManagedIdentifiableProxy)(removedTag.getManagedObjectProxy())).getObjectId());
						CollabId id2 = getCollabId(((ManagedIdentifiableProxy)tag.getManagedObjectProxy()).getObjectId());
						if (id1.equals(id2)) {
							tags.remove(tag);
							break;
						}
					}
				}
			}
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					Persistent identifiable = (Persistent) addedObjectsIter.next().getValue();
					tags.add(identifiable);
				}
			}
			assignAttributeValue(pojoIdentifiable, BeehiveEnterpriseInfo.Attributes.availableTags.name(), tags);
		}

	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		EnterpriseUpdater updater = CommunityFactory.getInstance().createEnterpriseUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CommunityControl control = ControlLocator.getInstance().getControl(CommunityControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		EnterpriseHandle enterpriseHandle = (EnterpriseHandle) EntityUtils.getInstance().createHandle(id);
		EnterpriseUpdater enterpriseUpdater = (EnterpriseUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		try {
			Enterprise enterprise = control.updateEnterprise(enterpriseHandle, enterpriseUpdater,
					updateMode, proj);
			assignChangeToken(pojoIdentifiable, enterprise.getSnapshotId().toString());
			return enterprise;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CommunityControl control = ControlLocator.getInstance().getControl(CommunityControl.class);
		EnterpriseUpdater enterpriseUpdater = (EnterpriseUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = getName(pojoIdentifiable);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			Enterprise enterprise = control.createEnterprise(id.getEid(), name, 
					enterpriseUpdater, proj);
			assignChangeToken(pojoIdentifiable, enterprise.getSnapshotId().toString());
			return enterprise;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		
	}
	
}
