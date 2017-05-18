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

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;

import javax.persistence.PersistenceException;

import oracle.csi.ActorHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Principal;
import oracle.csi.PrincipalHandle;
import oracle.csi.SnapshotId;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.PrincipalFactory;
import oracle.csi.controls.UserDirectoryControl;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.PrincipalUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class PrincipalDAO extends BaseAccessorDAO {
	
	static PrincipalDAO singleton = new PrincipalDAO();
	
	public static PrincipalDAO getInstance() {
		return singleton;
	}
	
	{
		//basicAttributes.add(PrincipalInfo.Attributes.activatingActorSufficient);
	}

	protected PrincipalDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return PrincipalHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Principal csiPrincipal = null;
		try {
			UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			PrincipalHandle csiPrincipalHandle = (PrincipalHandle) EntityUtils.getInstance().createHandle(id);
			csiPrincipal = control.loadPrincipal(csiPrincipalHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiPrincipal;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		/*
		Principal csiPrincipal = (Principal) csiEntity;
		icom.jpa.spi.Persistent pojoIdentifiable = obj.getPojoObject();
		Projection lastLoadedProjection = obj.getProviderProxy().getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(PrincipalInfo.Attributes.activatingActorSufficient.name(), lastLoadedProjection) &&
				isPartOfProjection(PrincipalInfo.Attributes.activatingActorSufficient.name(),  proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PrincipalInfo.Attributes.activatingActorSufficient.name(), csiPrincipal.isActivatingActorSufficient());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		*/
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		PrincipalUpdater updater = PrincipalFactory.getInstance().createPrincipalUpdater();
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
		PrincipalHandle csiPrincipalHandle = (PrincipalHandle) EntityUtils.getInstance().createHandle(id);
		PrincipalUpdater principalUpdater = (PrincipalUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoPrincipal = obj.getPojoIdentifiable();
		try {
			Principal principal = control.updatePrincipal(csiPrincipalHandle, principalUpdater,	updateMode, proj);
			assignChangeToken(pojoPrincipal, principal.getSnapshotId().toString());
			return principal;
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
		PrincipalUpdater principalUpdater = (PrincipalUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoPrincipal = obj.getPojoIdentifiable();
		String name = getName(pojoPrincipal);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(getParent(pojoPrincipal).getManagedObjectProxy())).getObjectId());
		ActorHandle actorHandle = (ActorHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			Principal principal = control.createPrincipal(id.getEid(),	actorHandle, name,  
					principalUpdater, proj);
			assignChangeToken(pojoPrincipal, principal.getSnapshotId().toString());
			return principal;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		PrincipalHandle csiPrincipalHandle = (PrincipalHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deletePrincipal(csiPrincipalHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

}
