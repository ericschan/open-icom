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


import com.oracle.beehive.Enterprise;
import com.oracle.beehive.EnterpriseCreator;
import com.oracle.beehive.EnterpriseUpdater;
import com.oracle.beehive.EntityCreator;

import icom.info.beehive.BeehiveEnterpriseInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.Projection;

import java.util.Set;


public class EnterpriseDAO extends CommunityDAO {

	static EnterpriseDAO singleton = new EnterpriseDAO();
	
	public static EnterpriseDAO getInstance() {
		return singleton;
	}
	
	{
		lazyAttributes.add(BeehiveEnterpriseInfo.Attributes.availableCategories);
		lazyAttributes.add(BeehiveEnterpriseInfo.Attributes.availableTags);
	}

	protected EnterpriseDAO() {	
	}

	public String getResourceType() {
		return "enpr";
	}
		
	public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
		super.copyObjectState(obj, bdkEntity, proj);
	}

    public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object key) {
        super.loadAndCopyObjectState(obj, attributeName, key);

        if (BeehiveEnterpriseInfo.Attributes.availableCategories.name().equals(attributeName)) {
            Set<Persistent> categories = CategoryDAO.getInstance().loadCategories(obj, Projection.EMPTY);
            mergeAssignIdentifiables(obj, BeehiveEnterpriseInfo.Attributes.availableCategories.name(), categories);
        }

        if (BeehiveEnterpriseInfo.Attributes.availableTags.name().equals(attributeName)) {
            Set<Persistent> tags = LabelDAO.getInstance().loadAvailableLabels(obj, Projection.EMPTY);
            mergeAssignIdentifiables(obj, BeehiveEnterpriseInfo.Attributes.availableTags.name(), tags);
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
		return Enterprise.class;
	}
	
	protected EnterpriseUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new EnterpriseUpdater();
	}
	
	protected EnterpriseUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		EnterpriseUpdater updater = getBdkUpdater(obj);
		((EnterpriseCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected EnterpriseCreator getBdkCreator(ManagedObjectProxy obj) {
		return new EnterpriseCreator();
	}
	
}
