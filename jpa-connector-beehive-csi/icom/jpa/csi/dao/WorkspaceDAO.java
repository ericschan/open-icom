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

import icom.info.EntityInfo;
import icom.info.SpaceInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.AccessorHandle;
import oracle.csi.CollabId;
import oracle.csi.CommunityHandle;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Folder;
import oracle.csi.Workspace;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.WorkspaceControl;
import oracle.csi.controls.WorkspaceFactory;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.ParentPredicate;
import oracle.csi.filters.WorkspaceListFilter;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.WorkspaceUpdater;

public abstract class WorkspaceDAO extends ScopeDAO {
	
	{
		fullAttributes.add(SpaceInfo.Attributes.elements);
	}

	protected WorkspaceDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Workspace csiWorkspace = (Workspace) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(SpaceInfo.Attributes.elements.name(), lastLoadedProjection) &&
				isPartOfProjection(SpaceInfo.Attributes.elements.name(),  proj)) {
			try {
				ListResult<? extends Folder> list = csiWorkspace.getElements();
				Vector<Persistent> v = new Vector<Persistent>();
				Iterator<? extends Folder> iter = list.iterator();
				while (iter.hasNext()) {
					ManagedIdentifiableProxy childObj = getEntityProxy(context, iter.next());
					v.add(childObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, SpaceInfo.Attributes.elements.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}

	}
	
	// override the method in EntityDAO, owner of workspace is set in workspace updater
	protected void updateOwnerOnEntity(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		WorkspaceUpdater updater = (WorkspaceUpdater) context.getUpdater();
		Persistent pojoEntity = obj.getPojoIdentifiable();
		if (isChanged(obj, EntityInfo.Attributes.owner.name())) {
			icom.jpa.Identifiable pojoOwner = (icom.jpa.Identifiable) getAttributeValue(pojoEntity, EntityInfo.Attributes.owner.name());
			if (pojoOwner != null) {
				CollabId accessorId = getCollabId(pojoOwner.getObjectId());
				AccessorHandle accessorHandle = (AccessorHandle) EntityUtils.getInstance().createHandle(accessorId);
				updater.setWorkspaceOwner(accessorHandle);
			}
		}
	}
			
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		
	}
	
	static public Set<Persistent> loadSpaces(ManagedIdentifiableProxy obj, CommunityHandle csiCommunityHandle, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoSpaces = null;
		try {
			WorkspaceControl control = ControlLocator.getInstance().getControl(WorkspaceControl.class);
			WorkspaceListFilter workspaceListFilter = WorkspaceFactory.getInstance().createWorkspaceListFilter();
			ParentPredicate predicate = workspaceListFilter.createParentPredicate(csiCommunityHandle);
			workspaceListFilter.setPredicate(predicate);
			workspaceListFilter.setProjection(proj);
			ListResult<Workspace> csiWorkspaces = control.listWorkspaces(workspaceListFilter);
			if (csiWorkspaces != null) {
				pojoSpaces = new HashSet<Persistent>(csiWorkspaces.size());
				for (Workspace csiWorkspace : csiWorkspaces) {
					ManagedIdentifiableProxy workspaceObj = PersonalWorkspaceDAO.getInstance().getEntityProxy(context, csiWorkspace);
					workspaceObj.getProviderProxy().copyLoadedProjection(workspaceObj, csiWorkspace, proj);
					pojoSpaces.add(workspaceObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoSpaces;
	}

}
