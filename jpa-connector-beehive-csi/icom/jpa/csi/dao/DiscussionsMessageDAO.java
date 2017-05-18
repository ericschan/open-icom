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
import icom.info.DiscussionMessageInfo;
import icom.info.MessageInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.Content;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.DiscussionsMessage;
import oracle.csi.DiscussionsMessageHandle;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.TopicHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.DiscussionsMessageFactory;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.TopicControl;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.DiscussionsMessageUpdater;
import oracle.csi.updaters.SimpleContentUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class DiscussionsMessageDAO extends MessageDAO {

	static DiscussionsMessageDAO singleton = new DiscussionsMessageDAO();
	
	public static DiscussionsMessageDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(DiscussionMessageInfo.Attributes.inReplyTo);
	}
	
	protected DiscussionsMessageDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return DiscussionsMessageHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		DiscussionsMessage csiDiscussionsMessage = null;
		try {
			TopicControl control = ControlLocator.getInstance().getControl(TopicControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			DiscussionsMessageHandle discussionsMessageHandle = (DiscussionsMessageHandle) EntityUtils.getInstance().createHandle(id);
			csiDiscussionsMessage = control.loadDiscussionsMessage(discussionsMessageHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiDiscussionsMessage;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		DiscussionsMessage csiDiscussionsMessage = (DiscussionsMessage) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(DiscussionMessageInfo.Attributes.inReplyTo.name(), lastLoadedProjection, proj)) {
			try {
				DiscussionsMessage csiParentDiscussionsMessage = csiDiscussionsMessage.getParentMessage();
				if (csiParentDiscussionsMessage != null) {
					ManagedIdentifiableProxy discussionsMessageObj = getEntityProxy(context, csiParentDiscussionsMessage);
					Persistent discussionMessagePojoEntity = discussionsMessageObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, DiscussionMessageInfo.Attributes.inReplyTo.name(), discussionMessagePojoEntity);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (/* always copy content  !isPartOfProjection(MessageInfo.Attributes.content.name(), lastLoadedProjection) && */
				isPartOfProjection(MessageInfo.Attributes.content.name(),  proj)) {
			try {
				Content csiChildContent  = csiDiscussionsMessage.getBody();
				if (csiChildContent != null) {
					boolean createDependentProxy = true;
					Object pojoContent = getAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name());
					if (pojoContent != null) {
						ManagedIdentifiableProxy contentObj = (ManagedIdentifiableProxy) getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
						if (contentObj != null) {
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
							createDependentProxy = false;
						}
					}
					if (createDependentProxy) {
						ManagedObjectProxy contentObj = getIdentifiableDependentProxy(context, csiChildContent, obj, MessageInfo.Attributes.content.name());
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
						assignAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name(), contentObj.getPojoObject());
					}
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
		DiscussionsMessageUpdater updater = (DiscussionsMessageUpdater) context.getUpdater();
		Persistent pojoDiscussionsMessage = obj.getPojoIdentifiable();
		if (isChanged(obj, MessageInfo.Attributes.content.name())) {
			Persistent pojoContent = (Persistent) getAttributeValue(pojoDiscussionsMessage, MessageInfo.Attributes.content.name());
			if (pojoContent != null) {
				SimpleContentUpdater contentUpdater = updater.getBodyUpdater(SimpleContentUpdater.class);
				DAOContext childContext = new DAOContext(contentUpdater);
				SimpleContentDAO.getInstance().updateObjectStateForDiscussionMessage(pojoContent.getManagedObjectProxy(), childContext);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		DiscussionsMessageUpdater updater = DiscussionsMessageFactory.getInstance().createDiscussionsMessageUpdater();
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
		DiscussionsMessageHandle discussionsMessageHandle = (DiscussionsMessageHandle) EntityUtils.getInstance().createHandle(id);
		DiscussionsMessageUpdater updater = (DiscussionsMessageUpdater) context.getUpdater();
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
			DiscussionsMessage discussion = control.updateDiscussionsMessage(discussionsMessageHandle, updater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, discussion.getSnapshotId().toString());
			return discussion;
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
		DiscussionsMessageUpdater updater = (DiscussionsMessageUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		String name = getName(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			TopicHandle topicHandle = (TopicHandle) entityHandle;
			DiscussionsMessage discussions = control.createDiscussionsMessage(id.getEid(), topicHandle, name, updater, proj);
			assignChangeToken(pojoIdentifiable, discussions.getSnapshotId().toString());
			return discussions;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		TopicControl control = ControlLocator.getInstance().getControl(TopicControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		DiscussionsMessageHandle discussionsMessageHandle = (DiscussionsMessageHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteDiscussionsMessage(discussionsMessageHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			//throw new PersistenceException(ex);
		}
	}

}
