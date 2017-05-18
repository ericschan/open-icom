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

import icom.info.AnnouncementInfo;
import icom.info.BeanHandler;
import icom.info.IcomBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.PersistenceException;

import oracle.csi.Announcement;
import oracle.csi.AnnouncementHandle;
import oracle.csi.AnnouncementStatus;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.ForumHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.TopicControl;
import oracle.csi.controls.TopicFactory;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AnnouncementUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class AnnouncementDAO extends TopicDAO {

	static AnnouncementDAO singleton = new AnnouncementDAO();
	
	public static AnnouncementDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(AnnouncementInfo.Attributes.activationDate);
		basicAttributes.add(AnnouncementInfo.Attributes.expirationDate);
		basicAttributes.add(AnnouncementInfo.Attributes.status);
	}
	
	static HashMap<String, String> csiToPojoAnnouncementStatusNameMap;
	static HashMap<String, String> pojoToCsiAnnouncementStatusNameMap;
	
	enum PojoAnnouncementStatus {
		Pending,
		Active,
		Expired
	}
	
	{
		csiToPojoAnnouncementStatusNameMap = new HashMap<String, String>();
		pojoToCsiAnnouncementStatusNameMap = new HashMap<String, String>();
		csiToPojoAnnouncementStatusNameMap.put(AnnouncementStatus.ACTIVE.name(), PojoAnnouncementStatus.Active.name());
		csiToPojoAnnouncementStatusNameMap.put(AnnouncementStatus.EXPIRED.name(), PojoAnnouncementStatus.Expired.name());
		csiToPojoAnnouncementStatusNameMap.put(AnnouncementStatus.PENDING.name(), PojoAnnouncementStatus.Pending.name());
		for (String key : csiToPojoAnnouncementStatusNameMap.keySet()) {
			pojoToCsiAnnouncementStatusNameMap.put(csiToPojoAnnouncementStatusNameMap.get(key), key);
		}
	}
	
	protected AnnouncementDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return AnnouncementHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Announcement csiAnnouncement = null;
		try {
			TopicControl control = ControlLocator.getInstance().getControl(TopicControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			AnnouncementHandle announcementHandle = (AnnouncementHandle) EntityUtils.getInstance().createHandle(id);
			csiAnnouncement = control.loadAnnouncement(announcementHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiAnnouncement;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Announcement csiAnnouncement = (Announcement) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(AnnouncementInfo.Attributes.activationDate.name(), lastLoadedProjection, proj)) {
			try {
				Date csiActivationDate = csiAnnouncement.getActivatesOn();
				assignAttributeValue(pojoIdentifiable, AnnouncementInfo.Attributes.activationDate.name(), csiActivationDate);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(AnnouncementInfo.Attributes.expirationDate.name(), lastLoadedProjection, proj)) {
			try {
				Date csiExpirationDate = csiAnnouncement.getExpiresOn();
				assignAttributeValue(pojoIdentifiable, AnnouncementInfo.Attributes.expirationDate.name(), csiExpirationDate);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(AnnouncementInfo.Attributes.status.name(), lastLoadedProjection, proj)) {
			try {
				AnnouncementStatus csiAnnouncementStatus = csiAnnouncement.getAnnouncementStatus();
				if (csiAnnouncementStatus != null) {
					String csiAnnouncementStatusName = csiAnnouncementStatus.name();
					String pojoAnnouncementStatusName = csiToPojoAnnouncementStatusNameMap.get(csiAnnouncementStatusName);
					assignEnumConstant(pojoIdentifiable, AnnouncementInfo.Attributes.status.name(), BeanHandler.getBeanPackageName(), 
							IcomBeanEnumeration.AnnouncementStatusEnum.name(), pojoAnnouncementStatusName);
				} else {
					assignAttributeValue(pojoIdentifiable, AnnouncementInfo.Attributes.status.name(), null);
				} 
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		super.loadAndCopyObjectState(obj, attributeName, parameter);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		AnnouncementUpdater updater = TopicFactory.getInstance().createAnnouncementUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		TopicControl control = ControlLocator.getInstance().getControl(TopicControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		AnnouncementHandle announcementHandle = (AnnouncementHandle) EntityUtils.getInstance().createHandle(id);
		AnnouncementUpdater announcementUpdater = (AnnouncementUpdater) context.getUpdater();
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
			Announcement announcement = control.updateAnnouncement(announcementHandle, announcementUpdater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, announcement.getSnapshotId().toString());
			return announcement;
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
		TopicControl control = ControlLocator.getInstance().getControl(TopicControl.class);
		AnnouncementUpdater announcementUpdater = (AnnouncementUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		String name = getName(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			ForumHandle forumHandle = (ForumHandle) entityHandle;
			Announcement announcement = control.createAnnouncement(id.getEid(), forumHandle, name, announcementUpdater, proj);
			assignChangeToken(pojoIdentifiable, announcement.getSnapshotId().toString());
			return announcement;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		TopicControl control = ControlLocator.getInstance().getControl(TopicControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		AnnouncementHandle announcementHandle = (AnnouncementHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteAnnouncement(announcementHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			//throw new PersistenceException(ex);
		}
	}
}
