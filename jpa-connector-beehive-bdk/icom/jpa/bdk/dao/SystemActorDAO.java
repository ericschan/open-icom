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


import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.EntityUpdater;
import com.oracle.beehive.Principal;
import com.oracle.beehive.SystemActor;

import icom.info.beehive.BeehiveSystemActorInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;

import java.util.HashSet;
import java.util.List;


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

	public String getResourceType() {
		return "syac";
	}
	
    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        SystemActor bdkSystemActor = (SystemActor)bdkEntity;
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(BeehiveSystemActorInfo.Attributes.memberPrincipals.name(), lastLoadedProjection,
                                 proj)) {
            try {
                List<Principal> bdkPrincipals = bdkSystemActor.getPrincipals();
                marshallMergeAssignEntities(obj, BeehiveSystemActorInfo.Attributes.memberPrincipals.name(),
                                            bdkPrincipals, HashSet.class);
            } catch (Exception ex) {
                // ignore
            }
        }
    }
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return SystemActor.class;
	}
	
	protected EntityUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return null;
	}
	
	protected EntityUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		return null;
	}
	
	protected EntityCreator getBdkCreator(ManagedObjectProxy obj) {
		return null;
	}

}
