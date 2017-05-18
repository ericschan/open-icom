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
import icom.info.BeanHandler;
import icom.info.BeanInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.PresenceInfo;
import icom.jpa.ManagedIdentifiableDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.UserContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.Activity;
import oracle.csi.ActivityHandle;
import oracle.csi.CollabId;
import oracle.csi.ContactMethod;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Presence;
import oracle.csi.PresenceHandle;
import oracle.csi.WatchableHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.PresenceControl;
import oracle.csi.controls.PresenceFactory;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.ActivityListUpdater;

public class PresenceDAO extends CsiIdentifiableDAO {

	static PresenceDAO singleton = new PresenceDAO();
	
	public static PresenceDAO getInstance() {
		return singleton;
	}
	
	{
		fullAttributes.add(PresenceInfo.Attributes.editMode);
		fullAttributes.add(PresenceInfo.Attributes.lastModificationDate);
		fullAttributes.add(PresenceInfo.Attributes.activities);
		fullAttributes.add(PresenceInfo.Attributes.contactMethods);
	}
	
	enum PresenceEditMode {
		PresentityCopy,
		ViewerCopy
	}

	protected PresenceDAO() {
		
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return PresenceHandle.class;
	}
	
	class PresenceDAOContext {
		ActivityListUpdater activityListUpdater;
	}
	
	public PresenceHandle resolvePresence(WatchableHandle presentity) {
		PresenceControl control = ControlLocator.getInstance().getControl(PresenceControl.class);
		return control.resolvePresence(presentity);
	}

	void loadPresenceOnWatchable(ManagedIdentifiableProxy watchableObj, Projection projection, String parentAttributeName) {
		PersistenceContext context = watchableObj.getPersistenceContext();
		CollabId watchableObjId = getCollabId(watchableObj.getObjectId());
		WatchableHandle watchableHandle = (WatchableHandle) EntityUtils.getInstance().createHandle(watchableObjId);
		PresenceHandle presenceHandle = resolvePresence(watchableHandle);
		Persistent pojoPresence = null;
		try {
			PresenceControl control = ControlLocator.getInstance().getControl(PresenceControl.class);
			Presence csiPresence = control.loadPresence(presenceHandle, projection);
			ManagedIdentifiableProxy presenceObj = getEntityProxy(context, csiPresence);
			pojoPresence = presenceObj.getPojoIdentifiable();
			presenceObj.getProviderProxy().copyLoadedProjection(presenceObj, csiPresence, projection);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		watchableObj.getProviderProxy().copyLazyAttribute(watchableObj, parentAttributeName, null, pojoPresence);
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		Presence csiPresence = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, csiPresence, proj);
		return proj;
	}
	
	public Presence loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		PresenceControl control = ControlLocator.getInstance().getControl(PresenceControl.class);
		ManagedIdentifiableProxy presenceObj = (ManagedIdentifiableProxy) obj;
		CollabId id = getCollabId(presenceObj.getObjectId());
		PresenceHandle presenceHandle = (PresenceHandle) EntityUtils.getInstance().createHandle(id);
		try {
			Presence csiPresence = control.loadPresence(presenceHandle, proj);
			return csiPresence;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

	public void copyObjectState(ManagedObjectProxy managedObj, Object csiIdentifiable, Projection proj) {
		PersistenceContext context = managedObj.getPersistenceContext();
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		Presence csiPresence = (Presence) csiIdentifiable;
		
		try {
			UserContext uc = context.getUserContext();
			CollabId actorId = (CollabId) uc.getActorId();
			boolean isPresentity = false;		
			if (actorId.getType().getStringIdentifier().equals("syac")) {
				assignEnumConstant(pojoIdentifiable, PresenceInfo.Attributes.editMode.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PresenceEditModeEnum.name(), 
						PresenceEditMode.PresentityCopy.name());
				isPresentity = true;
			} else {
				Eid actorEid = actorId.getEid();
				CollabId presenceId = csiPresence.getCollabId();
				Eid presenceEid = presenceId.getEid();
				if (actorEid.equals(presenceEid)) {
					assignEnumConstant(pojoIdentifiable, PresenceInfo.Attributes.editMode.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PresenceEditModeEnum.name(), 
							PresenceEditMode.PresentityCopy.name());
					isPresentity = true;
				}
			}

			if (!isPresentity) {
				assignEnumConstant(pojoIdentifiable, PresenceInfo.Attributes.editMode.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PresenceEditModeEnum.name(), 
						PresenceEditMode.ViewerCopy.name());
			}
		} catch (Exception ex) {}
		
		if (isPartOfProjection(PresenceInfo.Attributes.lastModificationDate.name(), proj)) {
			try {
				long timestamp = csiPresence.getModificationTimestamp();
				Date dt = new Date(timestamp);
				assignAttributeValue(pojoIdentifiable, PresenceInfo.Attributes.lastModificationDate.name(), dt);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(PresenceInfo.Attributes.contactMethods.name(), proj)) {
			List<ContactMethod> csiContactMethods = csiPresence.getContactMethods();
			Collection<Object> pojoContactMethods = new Vector<Object>();
			for (ContactMethod csiContactMethod : csiContactMethods) {
				ManagedObjectProxy contactMethodObj = getNonIdentifiableDependentProxy(context, csiContactMethod, obj, PresenceInfo.Attributes.contactMethods.name());
				ContactMethodDAO.getInstance().copyObjectState(contactMethodObj, csiContactMethod, proj);
				pojoContactMethods.add(contactMethodObj.getPojoObject());
			}
			
			Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, PresenceInfo.Attributes.contactMethods.name());
			if (previousPojoObjects != null) {
				for (Object previousPojoObject : previousPojoObjects) {
					if (previousPojoObject instanceof Persistent) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
				}
			}
			
			assignAttributeValue(pojoIdentifiable, PresenceInfo.Attributes.contactMethods.name(), pojoContactMethods);
		}
		
		if (isPartOfProjection(PresenceInfo.Attributes.activities.name(), proj)) {
			Collection<Activity> csiCurrentActivities = csiPresence.getCurrentActivities();
			Collection<Object> pojoCurrentActivities = new Vector<Object>();
			for (Activity csiActivity : csiCurrentActivities) {
				ManagedIdentifiableDependentProxy activityObj = getIdentifiableDependentProxy(context, csiActivity, obj, PresenceInfo.Attributes.activities.name());
				ActivityDAO.getInstance().copyObjectState(activityObj, csiActivity, proj);
				pojoCurrentActivities.add(activityObj.getPojoObject());
			}
			
			Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, PresenceInfo.Attributes.activities.name());
			if (previousPojoObjects != null) {
				for (Object previousPojoObject : previousPojoObjects) {
					if (previousPojoObject instanceof Persistent) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
				}
			}
			
			assignAttributeValue(pojoIdentifiable, PresenceInfo.Attributes.activities.name(), pojoCurrentActivities);
		}
	}
	
	public void save(ManagedIdentifiableProxy obj) {
		if (obj.isDirty()) {
			if (! obj.hasAttributeChanges()) {
				return;
			}
			saveDirty(obj);
		} else if (obj.isNew()) {
			saveNew(obj);
		}
	}
	
	public void saveDirty(ManagedIdentifiableProxy obj) {
		PresenceDAOContext context = beginActivityListUpdate(obj);
		if (context != null) {
			updateActivityList(obj, context);
			concludeActivityListUpdate(obj, context);
		}
	}
	
	public void saveNew(ManagedIdentifiableProxy obj) {
		PresenceDAOContext context = beginActivityListUpdate(obj);
		if (context != null) {
			updateActivityList(obj, context);
			concludeActivityListUpdate(obj, context);
		}
	}
	
	public PresenceDAOContext beginActivityListUpdate(ManagedIdentifiableProxy obj) {
		ActivityListUpdater lisUpdater = PresenceFactory.getInstance().createActivityListUpdater();
		PresenceDAOContext context = new PresenceDAOContext();
		context.activityListUpdater = lisUpdater;
		return context;
	}

	private void updateActivityList(ManagedIdentifiableProxy obj, PresenceDAOContext context) {
		ActivityListUpdater lisUpdater = context.activityListUpdater;
		if (isChanged(obj, PresenceInfo.Attributes.activities.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(PresenceInfo.Attributes.activities.name());
			if (addedObjects != null) {
				for (ValueHolder addedObjectHolder : addedObjects) {
					Persistent activity = (Persistent) addedObjectHolder.getValue();
					CollabId id = getCollabId(((ManagedIdentifiableProxy)activity.getManagedObjectProxy()).getObjectId());
					ActivityHandle activityHandle = (ActivityHandle) EntityUtils.getInstance().createHandle(id);
					ActivityDAO.getInstance().updateObjectState(activity, lisUpdater, activityHandle, ActivityDAO.Operand.ADD);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(PresenceInfo.Attributes.activities.name());
			if (removedObjects != null) {
				for (ValueHolder removedObjectHolder : removedObjects) {
					Persistent activity = (Persistent) removedObjectHolder.getValue();
					CollabId id = getCollabId(((ManagedIdentifiableProxy)activity.getManagedObjectProxy()).getObjectId());
					ActivityHandle activityHandle = (ActivityHandle) EntityUtils.getInstance().createHandle(id);
					ActivityDAO.getInstance().updateObjectState(activity, lisUpdater, activityHandle, ActivityDAO.Operand.REMOVE);
				}
			}
			/*
			Collection<ValueHolder> modifiedObjects = obj.getModifiedObjects(PresenceInfo.Attributes.activities.name());
			if (modifiedObjects != null) {
				for (ValueHolder modifiedObjectHolder : modifiedObjects) {
					icom.jpa.spi.Persistent activity = modifiedObjectHolder.getPersistent();
					CollabId id = getCollabId(((ManagedIdentifiableProxy)activity.getManagedObjectProxy()).getObjectId());
					ActivityHandle activityHandle = (ActivityHandle) EntityUtils.getInstance().createHandle(id);
					ActivityDAO.getInstance().updateObjectState(activity, lisUpdater, activityHandle, ActivityDAO.Operand.Operand.MODIFY);
				}
			}
			*/
		}
	}
	
	public void concludeActivityListUpdate(ManagedIdentifiableProxy obj, PresenceDAOContext context) {
		ActivityListUpdater updater = context.activityListUpdater;
		PresenceControl control = ControlLocator.getInstance().getControl(PresenceControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		PresenceHandle presenceHandle = (PresenceHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.updateActivities(presenceHandle, updater);
		} catch (CsiException ex) {
			throw new PersistenceException(ex); 
		}
	}

}
