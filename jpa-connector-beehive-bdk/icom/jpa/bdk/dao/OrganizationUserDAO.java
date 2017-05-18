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


import com.oracle.beehive.Actor;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.OrganizationPredicate;
import com.oracle.beehive.OrganizationUser;
import com.oracle.beehive.OrganizationUserCreator;
import com.oracle.beehive.OrganizationUserUpdater;
import com.oracle.beehive.Principal;
import com.oracle.beehive.User;

import icom.info.EntityInfo;
import icom.info.PersonInfo;
import icom.info.PersonalInfo;
import icom.info.beehive.BeehiveOrganizationUserInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;


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
	}

	protected OrganizationUserDAO() {
	}

	public String getResourceType() {
		return "user";
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        OrganizationUser bdkOrganizationUser = (OrganizationUser)bdkEntity;
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.memberPrincipals.name(), lastLoadedProjection,
                                 proj)) {
            try {
                List<Principal> bdkPrincipals = bdkOrganizationUser.getPrincipals();
                marshallMergeAssignEntities(obj, BeehiveOrganizationUserInfo.Attributes.memberPrincipals.name(),
                                          bdkPrincipals, HashSet.class);
            } catch (Exception ex) {
                // ignore
            }
        }
        if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.manager.name(), lastLoadedProjection, proj)) {
            try {
                User bdkManager = bdkOrganizationUser.getManager();
                marshallAssignEntity(obj, BeehiveOrganizationUserInfo.Attributes.manager.name(), bdkManager);
            } catch (Exception ex) {
                // ignore
            }
        }
        if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.assistant.name(), lastLoadedProjection,
                                 proj)) {
            try {
                User bdkAssistant = bdkOrganizationUser.getAssistant();
                marshallAssignEntity(obj, BeehiveOrganizationUserInfo.Attributes.assistant.name(), bdkAssistant);
            } catch (Exception ex) {
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
				BeeId userId = getBeeId(((ManagedIdentifiableProxy) manager.getManagedObjectProxy()).getObjectId().toString());
				updater.setManager(userId);
			}
		}
		if (isChanged(obj, BeehiveOrganizationUserInfo.Attributes.assistant.name())) {
			Persistent assistant = (Persistent) getAttributeValue(pojoOrganizationUser, BeehiveOrganizationUserInfo.Attributes.assistant.name());
			if (assistant != null) {
				BeeId userId = getBeeId(((ManagedIdentifiableProxy) assistant.getManagedObjectProxy()).getObjectId().toString());
				updater.setAssistant(userId);
			}
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);

		OrganizationUserCreator creator = (OrganizationUserCreator) context.getCreator();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		String familyName = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.familyName.name());
		creator.setFamilyName(familyName);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setParent(parentHandle);
		}
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public Set<Persistent> loadUsers(ManagedIdentifiableProxy obj, BeeId communityId, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		OrganizationPredicate predicate = new OrganizationPredicate();
		predicate.setOrganizationMatch(communityId);
		List<Object> bdkOrganizationUsers = listEntities(context, Actor.class, predicate, getResourceType(), proj);
		try {
			if (bdkOrganizationUsers != null) {
				Set<Persistent> pojoUsers = new HashSet<Persistent>(bdkOrganizationUsers.size());
				for (Object bdkObject : bdkOrganizationUsers) {
					OrganizationUser bdkOrganizationUser = (OrganizationUser) bdkObject;
					ManagedIdentifiableProxy userObj = getEntityProxy(context, bdkOrganizationUser);
					userObj.getProviderProxy().copyLoadedProjection(userObj, bdkOrganizationUser, proj);
					pojoUsers.add(userObj.getPojoIdentifiable());
				}
				return pojoUsers;
			} else {
				return new HashSet<Persistent>();
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return OrganizationUser.class;
	}
	
	protected OrganizationUserUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new OrganizationUserUpdater();
	}
	
	protected OrganizationUserUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		OrganizationUserUpdater updater = getBdkUpdater(obj);
		((OrganizationUserCreator)creator).setData(updater);
		return updater;
	}
	
	protected OrganizationUserCreator getBdkCreator(ManagedObjectProxy obj) {
		return new OrganizationUserCreator();
	}

}
