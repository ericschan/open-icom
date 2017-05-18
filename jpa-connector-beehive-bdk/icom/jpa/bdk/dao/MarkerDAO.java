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


import com.oracle.beehive.Marker;
import com.oracle.beehive.MarkerUpdater;

import icom.info.ArtifactInfo;
import icom.info.MarkerInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;

import java.util.List;
import java.util.Vector;


public abstract class MarkerDAO extends ArtifactDAO {
	
	{
		basicAttributes.add(ArtifactInfo.Attributes.description);
	}
	
	{
		fullAttributes.add(MarkerInfo.Attributes.markedEntities);
	}
	
	protected MarkerDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        Marker bdkMarker = (Marker)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoObject();

        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(ArtifactInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(),
                                     bdkMarker.getDescription());
            } catch (Exception ex) {
                // ignore
            }
        }
        if (isBetweenProjections(MarkerInfo.Attributes.markedEntities.name(), lastLoadedProjection, proj)) {
            try {
                List<? extends Object> bdkMarkedEntities = bdkMarker.getEntities();
                marshallMergeAssignEntities(obj, MarkerInfo.Attributes.markedEntities.name(), bdkMarkedEntities,
                                          Vector.class);
            } catch (Exception ex) {
                // ignore
            }
        }
    }
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		MarkerUpdater updater = (MarkerUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, ArtifactInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name());
			updater.setDescription(description);
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

}
