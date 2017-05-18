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

import icom.info.ForumInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.Artifact;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.DiscussionsMessage;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.Forum;
import oracle.csi.ForumHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.WorkspaceHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.ForumControl;
import oracle.csi.controls.ForumFactory;
import oracle.csi.filters.ListResult;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.ForumUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class ForumDAO extends FolderDAO {

	static ForumDAO singleton = new ForumDAO();
	
	public static ForumDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(ForumInfo.Attributes.lastPost);
	}
	
	{
		fullAttributes.add(ForumInfo.Attributes.forums);
		fullAttributes.add(ForumInfo.Attributes.topics);
		fullAttributes.add(ForumInfo.Attributes.announcements);
	}
	
	protected ForumDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return ForumHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Forum csiForum = null;
		try {
			ForumControl control = ControlLocator.getInstance().getControl(ForumControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			ForumHandle forumHandle = (ForumHandle) EntityUtils.getInstance().createHandle(id);
			csiForum = control.loadForum(forumHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiForum;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Forum csiForum = (Forum) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ForumInfo.Attributes.lastPost.name(), lastLoadedProjection, proj)) {
			try {
				DiscussionsMessage csiDiscussionMessage = csiForum.getLastPost();
				if (csiDiscussionMessage != null) {
					ManagedIdentifiableProxy discussionObj = getEntityProxy(context, csiDiscussionMessage);
					Persistent pojoDiscussionMessage = discussionObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, ForumInfo.Attributes.lastPost.name(), pojoDiscussionMessage);
				} else {
					assignAttributeValue(pojoIdentifiable, ForumInfo.Attributes.lastPost.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(ForumInfo.Attributes.forums.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ForumInfo.Attributes.forums.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(ForumInfo.Attributes.forums.name());
				ListResult<? extends Artifact> list = csiForum.getSubForums();
				Vector<Persistent> v = new Vector<Persistent>(list.size());
				for (Artifact csiArtifact : list) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedArtifact = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedArtifact.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiArtifact.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiArtifact);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, ForumInfo.Attributes.forums.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(ForumInfo.Attributes.topics.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ForumInfo.Attributes.topics.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(ForumInfo.Attributes.topics.name());
				ListResult<? extends Artifact> list = csiForum.getTopics();
				Vector<Persistent> v = new Vector<Persistent>(list.size());
				for (Artifact csiArtifact : list) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedArtifact = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedArtifact.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiArtifact.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiArtifact);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, ForumInfo.Attributes.topics.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(ForumInfo.Attributes.announcements.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(ForumInfo.Attributes.announcements.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(ForumInfo.Attributes.announcements.name());
				ListResult<? extends Artifact> list = csiForum.getAnnouncements();
				Vector<Persistent> v = new Vector<Persistent>(list.size());
				for (Artifact csiArtifact : list) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent removedArtifact = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(removedArtifact.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiArtifact.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiArtifact);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, ForumInfo.Attributes.announcements.name(), v);
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
		ForumUpdater updater = ForumFactory.getInstance().createForumUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		ForumControl control = ControlLocator.getInstance().getControl(ForumControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		ForumHandle forumHandle = (ForumHandle) EntityUtils.getInstance().createHandle(id);
		ForumUpdater forumUpdater = (ForumUpdater) context.getUpdater();
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
			Forum forum = control.updateForum(forumHandle, forumUpdater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, forum.getSnapshotId().toString());
			return forum;
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
		ForumControl control = ControlLocator.getInstance().getControl(ForumControl.class);
		ForumUpdater forumUpdater = (ForumUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		String name = getName(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			WorkspaceHandle workspaceHandle = (WorkspaceHandle) entityHandle;
			Forum forum = control.createForum(id.getEid(), workspaceHandle, name, forumUpdater, proj);
			assignChangeToken(pojoIdentifiable, forum.getSnapshotId().toString());
			return forum;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		ForumControl control = ControlLocator.getInstance().getControl(ForumControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		ForumHandle forumHandle = (ForumHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteForum(forumHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			//throw new PersistenceException(ex);
		}
	}

}
