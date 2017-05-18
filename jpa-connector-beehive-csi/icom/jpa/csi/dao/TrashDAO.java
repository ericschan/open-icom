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

import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Trash;
import oracle.csi.TrashHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.WorkspaceControl;
import oracle.csi.projections.Projection;

public class TrashDAO extends FolderDAO {

	static TrashDAO singleton = new TrashDAO();
	
	public static TrashDAO getInstance() {
		return singleton;
	}
	
	protected TrashDAO() {	
	}

	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return TrashHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Trash csiTrash = null;
		try {
			WorkspaceControl control = ControlLocator.getInstance().getControl(WorkspaceControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			TrashHandle csiTrashHandle = (TrashHandle) EntityUtils.getInstance().createHandle(id);
			csiTrash = control.loadTrash(csiTrashHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiTrash;
	}
		
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, csiEntity, proj);
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
		WorkspaceControl control = ControlLocator.getInstance().getControl(WorkspaceControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		TrashHandle csiTrashHandle = (TrashHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.emptyTrash(csiTrashHandle);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
}
