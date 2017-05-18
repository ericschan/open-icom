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

import icom.info.beehive.BeehiveSystemActorInfo;
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
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Principal;
import oracle.csi.SystemActor;
import oracle.csi.SystemActorHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.UserDirectoryControl;
import oracle.csi.projections.Projection;

public class SystemActorDAO extends ActorDAO {
	
	{
		fullAttributes.add(BeehiveSystemActorInfo.Attributes.memberPrincipals);
	}
	
	static SystemActorDAO singleton = new SystemActorDAO();
	
	public static SystemActorDAO getInstance() {
		return singleton;
	}

	protected SystemActorDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return SystemActorHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		SystemActor csiSystemActor = null;
		try {
			UserDirectoryControl control = ControlLocator.getInstance().getControl(UserDirectoryControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			SystemActorHandle systemActorHandle = (SystemActorHandle) EntityUtils.getInstance().createHandle(id);
			csiSystemActor = control.loadSystemActor(systemActorHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiSystemActor;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		SystemActor csiSystemActor = (SystemActor) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(BeehiveSystemActorInfo.Attributes.memberPrincipals.name(), lastLoadedProjection, proj)) {
			try {
				Set<Principal> csiPrincipals = csiSystemActor.getPrincipals();
				HashSet<Persistent> pojoPrincipals = new HashSet<Persistent>();
				Iterator<Principal> csiPrincipalsIter = csiPrincipals.iterator();
				while (csiPrincipalsIter.hasNext()) {
					ManagedIdentifiableProxy principalObj = getEntityProxy(context, csiPrincipalsIter.next());
					pojoPrincipals.add(principalObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, BeehiveSystemActorInfo.Attributes.memberPrincipals.name(), pojoPrincipals);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		return null;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		return null;
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		return null;
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		
	}

}
