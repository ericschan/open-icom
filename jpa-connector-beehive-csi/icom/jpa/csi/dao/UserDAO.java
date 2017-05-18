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

import icom.info.PersonalInfo;
import icom.info.PersonInfo;
import icom.info.beehive.BeehiveOrganizationUserInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import oracle.csi.CollabId;
import oracle.csi.CsiRuntimeException;
import oracle.csi.PersonalWorkspace;
import oracle.csi.PersonalWorkspaceHandle;
import oracle.csi.TimeZone;
import oracle.csi.User;
import oracle.csi.Workspace;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.UserUpdater;

public abstract class UserDAO extends ActorDAO {
	
	{
		basicAttributes.add(PersonalInfo.Attributes.givenName);
		basicAttributes.add(PersonalInfo.Attributes.middleName);
		basicAttributes.add(PersonalInfo.Attributes.familyName);
		basicAttributes.add(PersonalInfo.Attributes.prefix);
		basicAttributes.add(PersonalInfo.Attributes.suffix);
		basicAttributes.add(PersonalInfo.Attributes.nicknames);
		basicAttributes.add(PersonalInfo.Attributes.company);
		basicAttributes.add(PersonalInfo.Attributes.department);
		basicAttributes.add(PersonalInfo.Attributes.profession);
		basicAttributes.add(PersonalInfo.Attributes.jobTitle);
		basicAttributes.add(PersonalInfo.Attributes.officeLocation);
		basicAttributes.add(PersonalInfo.Attributes.timeZone);
	}
	
	{
		fullAttributes.add(PersonInfo.Attributes.personalSpace);
		fullAttributes.add(BeehiveOrganizationUserInfo.Attributes.accessibleSpaces);
		fullAttributes.add(BeehiveOrganizationUserInfo.Attributes.favoriteSpaces);
	}
	
	protected UserDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		User csiUser = (User) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(PersonalInfo.Attributes.givenName.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.givenName.name(), csiUser.getGivenName());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.middleName.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.middleName.name(), csiUser.getMiddleName());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.familyName.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.familyName.name(), csiUser.getFamilyName());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.prefix.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.prefix.name(), csiUser.getPrefix());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.suffix.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.suffix.name(), csiUser.getSuffix());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.nicknames.name(), lastLoadedProjection, proj)) {
			try {
				String nickname = csiUser.getNickname();
				Collection<String> nicknames = new Vector<String>(1);
				if (nickname != null ) {
					nicknames.add(nickname);
				}
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.nicknames.name(), nicknames);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.jobTitle.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.jobTitle.name(), csiUser.getJobTitle());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.department.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.department.name(), csiUser.getDepartment());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.officeLocation.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.officeLocation.name(), csiUser.getOfficeLocation());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.company.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.company.name(), csiUser.getCompany());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.profession.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.profession.name(), csiUser.getProfession());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.timeZone.name(), lastLoadedProjection, proj)) {
			try {
				TimeZone tz = csiUser.getTimeZone();
				CollabId id = tz.getCollabId();
				//assignAttributeValue(pojoIdentifiable, PersonInfo.Attributes.timeZone.name(), );
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonInfo.Attributes.personalSpace.name(), lastLoadedProjection, proj)) {
			try {
				PersonalWorkspace csiPersonalWorkspace  = csiUser.getPersonalWorkspace();
				ManagedIdentifiableProxy personalWorkspaceObj = getEntityProxy(context, csiPersonalWorkspace);
				Persistent personalWorkspace = personalWorkspaceObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, PersonInfo.Attributes.personalSpace.name(), personalWorkspace);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.accessibleSpaces.name(), lastLoadedProjection, proj)) {
			try {
				Set<Workspace> awSet = csiUser.getAccessibleWorkspaces();
				HashSet<Persistent> aw = new HashSet<Persistent>();
				Iterator<Workspace> awIter = awSet.iterator();
				while (awIter.hasNext()) {
					ManagedIdentifiableProxy childObj = getEntityProxy(context, awIter.next());
					aw.add(childObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, BeehiveOrganizationUserInfo.Attributes.accessibleSpaces.name(), aw);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(BeehiveOrganizationUserInfo.Attributes.favoriteSpaces.name(), lastLoadedProjection, proj)) {
			try {
				Set<Workspace> fwSet = csiUser.getFavoriteWorkspaces();
				HashSet<Persistent> fw = new HashSet<Persistent>();
				Iterator<Workspace> fwIter = fwSet.iterator();
				while (fwIter.hasNext()) {
					ManagedIdentifiableProxy childObj = getEntityProxy(context, fwIter.next());
					fw.add(childObj.getPojoIdentifiable());
				}
				assignAttributeValue(pojoIdentifiable, BeehiveOrganizationUserInfo.Attributes.favoriteSpaces.name(), fw);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		UserUpdater updater = (UserUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		if (isChanged(obj, PersonalInfo.Attributes.givenName.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.givenName.name());
			updater.setGivenName(name);
		}
		if (isChanged(obj, PersonalInfo.Attributes.middleName.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.middleName.name());
			updater.setMiddleName(name);
		}
		if (isChanged(obj, PersonalInfo.Attributes.familyName.name())) {
			String name = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.familyName.name());
			updater.setFamilyName(name);
		}
		if (isChanged(obj, PersonalInfo.Attributes.nicknames.name())) {
			Collection<Object> nicknames = (Collection<Object>) getObjectCollection(pojoIdentifiable, PersonalInfo.Attributes.nicknames.name());
			if (nicknames != null) {
				updater.setNickname((String) nicknames.iterator().next());
			} else {
				updater.setNickname((String) null);
			}
		}
		if (isChanged(obj, PersonalInfo.Attributes.prefix.name())) {
			String prefix = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.prefix.name());
			updater.setPrefix(prefix);
		}
		if (isChanged(obj, PersonalInfo.Attributes.suffix.name())) {
			String suffix = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.suffix.name());
			updater.setSuffix(suffix);
		}
		if (isChanged(obj, PersonalInfo.Attributes.company.name())) {
			String company = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.company.name());
			updater.setCompany(company);
		}
		if (isChanged(obj, PersonalInfo.Attributes.department.name())) {
			String department = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.department.name());
			updater.setDepartment(department);
		}
		if (isChanged(obj, PersonalInfo.Attributes.jobTitle.name())) {
			String jobTitle = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.jobTitle.name());
			updater.setJobTitle(jobTitle);
		}
		if (isChanged(obj, PersonalInfo.Attributes.officeLocation.name())) {
			String officeLocation = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.officeLocation.name());
			updater.setOfficeLocation(officeLocation);
		}
		if (isChanged(obj, PersonalInfo.Attributes.profession.name())) {
			String profession = (String) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.profession.name());
			updater.setProfession(profession);
		}
		if (isChanged(obj, PersonalInfo.Attributes.timeZone.name())) {
			//Object timeZone = getAttributeValue(pojoIdentifiable, PersonInfo.Attributes.timeZone.name());
			//updater.setTimeZone();
		}
		
		
		if (isChanged(obj, PersonInfo.Attributes.personalSpace.name())) {
			Persistent personalWorkspace = (Persistent) getAttributeValue(pojoIdentifiable, PersonInfo.Attributes.personalSpace.name());
			if (personalWorkspace != null) {
				CollabId id = getCollabId(((ManagedIdentifiableProxy)personalWorkspace.getManagedObjectProxy()).getObjectId());
				PersonalWorkspaceHandle handle = (PersonalWorkspaceHandle) EntityUtils.getInstance().createHandle(id);
				updater.setPersonalWorkspace(handle);
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

}
