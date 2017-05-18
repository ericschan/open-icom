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

import icom.info.PersonInfo;
import icom.info.beehive.BeehiveOrganizationUserInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.CommunityHandle;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.OrganizationHandle;
import oracle.csi.OrganizationUser;
import oracle.csi.OrganizationUserHandle;
import oracle.csi.Principal;
import oracle.csi.SnapshotId;
import oracle.csi.User;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.UserDirectoryControl;
import oracle.csi.controls.UserFactory;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.OrganizationPredicate;
import oracle.csi.filters.OrganizationUserListFilter;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.OrganizationUserUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class OrganizationUserDAO extends UserDAO {
	
	static OrganizationUserDAO singleton = new OrganizationUserDAO();
	
	public static OrganizationUserDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(BeehiveOrganizationUserInfo.Attributes.manager);
		basicAttributes.add(BeehiveOrganizationUserInfo.Attributes.assistant);
	}
	
	{
		fullAttributes.add(BeehiveOrganizationUserInfo.Attributes.memberPrincipals);
	}
	
	{
		lazyAttributes.add(PersonInfo.Attributes.presence);
		lazyAttributes.add(PersonInfo.Attributes.instantMessageFeed);
	}

	protected OrganizationUserDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return OrganizationUserHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		OrganizationUser csiOrganizationUser = null;
		try {
			UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			OrganizationUserHandle organizationUserHandle = (OrganizationUserHandle) EntityUtils.getInstance().createHandle(id);
			csiOrganizationUser = control.loadUser(organizationUserHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiOrganizationUser;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		OrganizationUser csiOrganizationUser = (OrganizationUser) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.memberPrincipals.name(), lastLoadedProjection, proj)) {
			try {
				Set<Principal> csiPrincipals = csiOrganizationUser.getPrincipals();
				HashSet<Persistent> pojoPrincipals = new HashSet<Persistent>();
				Iterator<Principal> csiPrincipalsIter = csiPrincipals.iterator();
				while (csiPrincipalsIter.hasNext()) {
					ManagedIdentifiableProxy principalObj = getEntityProxy(context, csiPrincipalsIter.next());
					pojoPrincipals.add(principalObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, BeehiveOrganizationUserInfo.Attributes.memberPrincipals.name(), pojoPrincipals);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(BeehiveOrganizationUserInfo.Attributes.manager.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveOrganizationUserInfo.Attributes.manager.name(),  proj)) {
			try {
				User csiManager  = csiOrganizationUser.getManager();
				if (csiManager != null) {
					ManagedIdentifiableProxy managerObj = getEntityProxy(context, csiManager);
					Persistent manager = managerObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, BeehiveOrganizationUserInfo.Attributes.manager.name(), manager);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(BeehiveOrganizationUserInfo.Attributes.assistant.name(), lastLoadedProjection) &&
				isPartOfProjection(BeehiveOrganizationUserInfo.Attributes.assistant.name(),  proj)) {
			try {
				User csiAssistant  = csiOrganizationUser.getAssistant();
				if (csiAssistant != null) {
					ManagedIdentifiableProxy assistantObj = getEntityProxy(context, csiAssistant);
					Persistent assistant = assistantObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, BeehiveOrganizationUserInfo.Attributes.assistant.name(), assistant);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		if (PersonInfo.Attributes.presence.name().equals(attributeName)) {
			PresenceDAO.getInstance().loadPresenceOnWatchable(obj, Projection.FULL, attributeName);
		}
		super.loadAndCopyObjectState(obj, attributeName, parameter);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		OrganizationUserUpdater updater = (OrganizationUserUpdater) context.getUpdater();
		Persistent pojoOrganizationUser = obj.getPojoIdentifiable();
		if (isChanged(obj, BeehiveOrganizationUserInfo.Attributes.manager.name())) {
			Persistent manager = (Persistent) getAttributeValue(pojoOrganizationUser, BeehiveOrganizationUserInfo.Attributes.manager.name());
			if (manager != null) {
				CollabId id = getCollabId(((ManagedIdentifiableProxy) manager.getManagedObjectProxy()).getObjectId());
				OrganizationUserHandle handle = (OrganizationUserHandle) EntityUtils.getInstance().createHandle(id);
				updater.setManager(handle);
			}
		}
		if (isChanged(obj, BeehiveOrganizationUserInfo.Attributes.assistant.name())) {
			Persistent assistant = (Persistent) getAttributeValue(pojoOrganizationUser, BeehiveOrganizationUserInfo.Attributes.assistant.name());
			if (assistant != null) {
				CollabId id = getCollabId(((ManagedIdentifiableProxy) assistant.getManagedObjectProxy()).getObjectId());
				OrganizationUserHandle handle = (OrganizationUserHandle) EntityUtils.getInstance().createHandle(id);
				updater.setAssistant(handle);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		OrganizationUserUpdater updater = UserFactory.getInstance().createUserUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		OrganizationUserHandle organizationUserHandle = (OrganizationUserHandle) EntityUtils.getInstance().createHandle(id);
		OrganizationUserUpdater organizationUserUpdater = (OrganizationUserUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoOrganizationUser = obj.getPojoIdentifiable();
		try {
			OrganizationUser user = control.updateUser(organizationUserHandle, organizationUserUpdater,
					updateMode, proj);
			assignChangeToken(pojoOrganizationUser, user.getSnapshotId().toString());
			return user;
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
		UserDirectoryControl control = ControlLocator.getInstance().getControl(
				UserDirectoryControl.class);
		OrganizationUserUpdater organizationUserUpdater = (OrganizationUserUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoOrganizationUser = obj.getPojoIdentifiable();
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(getParent(pojoOrganizationUser).getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CommunityHandle communityHandle = (CommunityHandle) entityHandle;
			CollabId id = getCollabId(obj.getObjectId());
			OrganizationUser user = control.createUser(id.getEid(),
					communityHandle, getName(pojoOrganizationUser), false, 
					organizationUserUpdater, proj);
			assignChangeToken(pojoOrganizationUser, user.getSnapshotId().toString());
			return user;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		OrganizationUserHandle organizationUserHandle = (OrganizationUserHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteUser(organizationUserHandle, DeleteMode.alwaysDelete(), true);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public Set<Persistent> loadActors(ManagedIdentifiableProxy obj, OrganizationHandle csiOrganizationHandle, Projection proj) {	
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoActors = null;
		try {
			UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
			OrganizationUserListFilter userListFilter = UserFactory.getInstance().createUserListFilter();
			if (csiOrganizationHandle != null) {
				OrganizationPredicate predicate = userListFilter.createOrganizationPredicate(csiOrganizationHandle);
				userListFilter.setPredicate(predicate);
			}
			userListFilter.setProjection(proj);
			ListResult<OrganizationUser> csiOrganizationUsers = control.listUsers(userListFilter);
			if (csiOrganizationUsers != null) {
				pojoActors = new HashSet<Persistent>(csiOrganizationUsers.size());
				for (OrganizationUser csiOrganizationUser : csiOrganizationUsers) {
					ManagedIdentifiableProxy actorObj = getEntityProxy(context, csiOrganizationUser);
					actorObj.getProviderProxy().copyLoadedProjection(actorObj, csiOrganizationUser, proj);
					pojoActors.add(actorObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoActors;
	}


}
