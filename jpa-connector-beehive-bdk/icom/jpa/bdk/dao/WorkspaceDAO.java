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

import com.oracle.beehive.BeeId;
import com.oracle.beehive.ListResult;
import com.oracle.beehive.Workspace;
import com.oracle.beehive.WorkspaceUpdater;

import icom.info.EntityInfo;
import icom.info.SpaceInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;


public abstract class WorkspaceDAO extends ScopeDAO {
	
	{
		fullAttributes.add(SpaceInfo.Attributes.elements);
	}

	protected WorkspaceDAO() {
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        Workspace bdkWorkspace = (Workspace)bdkEntity;
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(SpaceInfo.Attributes.elements.name(), lastLoadedProjection, proj)) {
            try {
                ListResult list = bdkWorkspace.getElements();
                List<Object> elements = list.getElements();
                marshallMergeAssignEntities(obj, SpaceInfo.Attributes.elements.name(), elements, Vector.class);
            } catch (Exception ex) {
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
			Identifiable pojoOwner = (Identifiable) getAttributeValue(pojoEntity, EntityInfo.Attributes.owner.name());
			if (pojoOwner != null) {
				BeeId accessorId = getBeeId(pojoOwner.getObjectId().toString());
				updater.setWorkspaceOwner(accessorId);
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
	
	static public Set<Persistent> loadSpaces(ManagedIdentifiableProxy obj, BeeId bdkCommunityId, Projection proj) {
		// not supported
		return new HashSet<Persistent>();
	}

}
