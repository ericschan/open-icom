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


import com.oracle.beehive.Activity;
import com.oracle.beehive.ActivityListUpdater;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.ContactMethod;
import com.oracle.beehive.Presence;

import icom.info.BeanHandler;
import icom.info.IcomBeanEnumeration;
import icom.info.PresenceInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.UserContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.PersistenceException;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;


public class PresenceDAO extends BdkIdentifiableDAO {

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

	public String getResourceType() {
		return "prce";
	}
	
	class PresenceDAOContext {
		ActivityListUpdater activityListUpdater;
	}

	void loadPresenceOnWatchable(ManagedIdentifiableProxy watchableObj, Projection projection, String parentAttributeName) {
		PersistenceContext context = watchableObj.getPersistenceContext();
		BeeId watchableObjId = getBeeId(watchableObj.getObjectId().toString());
		String collabId = watchableObjId.getId();
		String resourceType = "presence";
		Persistent pojoPresence = null;
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
			GetMethod method = prepareGetMethod(resourceType, collabId, projection);
			Presence bdkPresence = (Presence) bdkHttpUtil.execute(Presence.class, method, userContext.httpClient);
			ManagedIdentifiableProxy presenceObj = getEntityProxy(context, bdkPresence);
			pojoPresence = presenceObj.getPojoIdentifiable();
			presenceObj.getProviderProxy().copyLoadedProjection(presenceObj, bdkPresence, projection);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
		watchableObj.getProviderProxy().copyLazyAttribute(watchableObj, parentAttributeName, null, pojoPresence);
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		Presence bdkPresence = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, bdkPresence, proj);
		return proj;
	}
	
	public Presence loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		BeeId presenceObjId = getBeeId(obj.getObjectId().toString());
		String collabId = presenceObjId.getId();
		String resourceType = "presence";
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
			GetMethod method = prepareGetMethod(resourceType, collabId, proj);
			Presence bdkPresence = (Presence) bdkHttpUtil.execute(Presence.class, method, userContext.httpClient);
			return bdkPresence;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

    public void copyObjectState(ManagedObjectProxy managedObj, Object bdkIdentifiable, Projection proj) {
        PersistenceContext context = managedObj.getPersistenceContext();
        ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy)managedObj;
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();
        Presence bdkPresence = (Presence)bdkIdentifiable;

        try {
            UserContext uc = context.getUserContext();
            BeeId actorId = getBeeId(uc.getActorId().toString());
            boolean isPresentity = false;
            if (actorId.getResourceType().equals("syac")) {
                assignEnumConstant(pojoIdentifiable, PresenceInfo.Attributes.editMode.name(),
                                   BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PresenceEditModeEnum.name(),
                                   PresenceEditMode.PresentityCopy.name());
                isPresentity = true;
            } else {
                BeeId presenceId = bdkPresence.getCollabId();
                String presenceEid = presenceId.getId().substring(15);
                String actorEid = actorId.getId().substring(15);
                if (actorEid.equals(presenceEid)) {
                    assignEnumConstant(pojoIdentifiable, PresenceInfo.Attributes.editMode.name(),
                                       BeanHandler.getBeanPackageName(),
                                       IcomBeanEnumeration.PresenceEditModeEnum.name(),
                                       PresenceEditMode.PresentityCopy.name());
                    isPresentity = true;
                }
            }

            if (!isPresentity) {
                assignEnumConstant(pojoIdentifiable, PresenceInfo.Attributes.editMode.name(),
                                   BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PresenceEditModeEnum.name(),
                                   PresenceEditMode.ViewerCopy.name());
            }
        } catch (Exception ex) {
        }

        if (isPartOfProjection(PresenceInfo.Attributes.lastModificationDate.name(), proj)) {
            try {
                long timestamp = bdkPresence.getModificationTimestamp();
                Date dt = new Date(timestamp);
                assignAttributeValue(pojoIdentifiable, PresenceInfo.Attributes.lastModificationDate.name(), dt);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(PresenceInfo.Attributes.contactMethods.name(), proj)) {
            List<ContactMethod> bdkContactMethods = bdkPresence.getContactMethods();
            marshallAssignEmbeddableObjects(obj, PresenceInfo.Attributes.contactMethods.name(), bdkContactMethods,
                                            Vector.class, proj);
        }

        if (isPartOfProjection(PresenceInfo.Attributes.activities.name(), proj)) {
            Collection<Activity> bdkCurrentActivities = bdkPresence.getCurrentActivities();
            marshallMergeAssignIdentifiableDependents(obj, PresenceInfo.Attributes.activities.name(),
                                                      bdkCurrentActivities, Vector.class, proj);
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
		ActivityListUpdater lisUpdater = new ActivityListUpdater();
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
					BeeId activityId = getBeeId(((ManagedIdentifiableProxy)activity.getManagedObjectProxy()).getObjectId().toString());
					ActivityDAO.getInstance().updateObjectState(activity, lisUpdater, activityId, ActivityDAO.Operand.ADD);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(PresenceInfo.Attributes.activities.name());
			if (removedObjects != null) {
				for (ValueHolder removedObjectHolder : removedObjects) {
					Persistent activity = (Persistent) removedObjectHolder.getValue();
					BeeId activityId = getBeeId(((ManagedIdentifiableProxy)activity.getManagedObjectProxy()).getObjectId().toString());
					ActivityDAO.getInstance().updateObjectState(activity, lisUpdater, activityId, ActivityDAO.Operand.REMOVE);
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
		ActivityListUpdater lisUpdater = context.activityListUpdater;
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			String resourceType = "my/presence/activities";
			PutMethod putMethod = preparePutMethod(resourceType, userContext.antiCSRF, null);
			bdkHttpUtil.execute(putMethod, lisUpdater, userContext.httpClient);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

}
