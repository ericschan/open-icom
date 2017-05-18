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

import icom.info.EntityInfo;
import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.Community;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.Organization;
import com.oracle.beehive.OrganizationCreator;
import com.oracle.beehive.OrganizationUpdater;
import com.oracle.beehive.Predicate;

public class OrganizationDAO extends CommunityDAO {

	static OrganizationDAO singleton = new OrganizationDAO();
	
	public static OrganizationDAO getInstance() {
		return singleton;
	}

	protected OrganizationDAO() {	
	}

	public String getResourceType() {
		return "orgn";
	}
		
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntity, Projection proj) {
		super.copyObjectState(managedObj, bdkEntity, proj);
		//Organization bdkOrganization = (Organization) bdkEntity;
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object key) {
		super.loadAndCopyObjectState(obj, attributeName, key);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		OrganizationCreator creator = (OrganizationCreator) context.getCreator();
		Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setName(name);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setParent(parentHandle);
		}
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public Set<Persistent> loadCommunities(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		BeeId communityId = getBeeId(obj.getObjectId().toString());
		String params = "parent=" + communityId.getId();
		List<Object> bdkCommunities = listEntities(context, Community.class, (Predicate) null, getResourceType(), proj, params);
		try {
			if (bdkCommunities != null) {
				Set<Persistent> pojoCommunities = new HashSet<Persistent>(bdkCommunities.size());
				for (Object bdkObject : bdkCommunities) {
					Community bdkCommunity = (Community) bdkObject;
					ManagedIdentifiableProxy communityObj = getEntityProxy(context, bdkCommunity);
					communityObj.getProviderProxy().copyLoadedProjection(communityObj, bdkCommunity, proj);
					pojoCommunities.add(communityObj.getPojoIdentifiable());
				}
				return pojoCommunities;
			} else {
				return new HashSet<Persistent>();
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Organization.class;
	}
	
	protected OrganizationUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new OrganizationUpdater();
	}
	
	protected OrganizationUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		OrganizationUpdater updater = getBdkUpdater(obj);
		((OrganizationCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected OrganizationCreator getBdkCreator(ManagedObjectProxy obj) {
		return new OrganizationCreator();
	}

}
