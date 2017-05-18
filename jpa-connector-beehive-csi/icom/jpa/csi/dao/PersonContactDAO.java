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

import java.util.Collection;
import java.util.Vector;

import icom.info.PersonContactInfo;
import icom.info.EntityInfo;
import icom.info.PersonalInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import javax.persistence.PersistenceException;

import oracle.csi.AddressBookElement;
import oracle.csi.AddressBookElementOperationContext;
import oracle.csi.AddressBookHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Person;
import oracle.csi.PersonContact;
import oracle.csi.PersonContactHandle;
import oracle.csi.PersonHandle;
import oracle.csi.SnapshotId;
import oracle.csi.TimeZone;
import oracle.csi.TimeZoneHandle;
import oracle.csi.controls.AddressBookControl;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.PersonContactFactory;
import oracle.csi.controls.TimeZoneControl;
import oracle.csi.controls.TimeZoneFactory;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.NamePredicate;
import oracle.csi.filters.TimeZoneListFilter;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.PersonContactUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class PersonContactDAO extends AddressBookElementDAO {
	
	static PersonContactDAO singleton = new PersonContactDAO();
	
	public static PersonContactDAO getInstance() {
		return singleton;
	}
	
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
		basicAttributes.add(PersonContactInfo.Attributes.bookmark);
	}

	protected PersonContactDAO() {	
	}

	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return PersonContactHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		PersonContact csiPersonContact = null;
		try {
			AddressBookControl control = ControlLocator.getInstance().getControl(AddressBookControl.class);
			CollabId collabId = getCollabId(obj.getObjectId());
			PersonContactHandle csiPersonContactHandle = (PersonContactHandle) EntityUtils.getInstance().createHandle(collabId);
			csiPersonContact = control.loadPersonContact(csiPersonContactHandle, proj);			
		} catch (CsiException ex) {
		}
		return csiPersonContact;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		super.copyObjectState(managedObj, csiEntity, proj);
		
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		PersonContact csiPersonContact = (PersonContact) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(PersonalInfo.Attributes.givenName.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.givenName.name(), csiPersonContact.getGivenName());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.middleName.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.middleName.name(), csiPersonContact.getMiddleName());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.familyName.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.familyName.name(), csiPersonContact.getFamilyName());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.prefix.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.prefix.name(), csiPersonContact.getPrefix());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.suffix.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.suffix.name(), csiPersonContact.getSuffix());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.nicknames.name(), lastLoadedProjection, proj)) {
			try {
				String nickname = csiPersonContact.getNickname();
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
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.jobTitle.name(), csiPersonContact.getJobTitle());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.department.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.department.name(), csiPersonContact.getDepartment());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.officeLocation.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.officeLocation.name(), csiPersonContact.getOfficeLocation());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.company.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.company.name(), csiPersonContact.getCompany());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.profession.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.profession.name(), csiPersonContact.getProfession());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonalInfo.Attributes.timeZone.name(), lastLoadedProjection, proj)) {
			try {
				TimeZone csiTimeZone = csiPersonContact.getTimeZone();
				String csiTimeZoneName = csiTimeZone.getName(); // e.g. "America/Los_Angeles"
				java.util.TimeZone tz = java.util.TimeZone.getTimeZone(csiTimeZoneName);
				assignAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.timeZone.name(), tz);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonContactInfo.Attributes.bookmark.name(), lastLoadedProjection, proj)) {
			try {
				Person csiPerson = csiPersonContact.getBookmark();
				if (csiPerson != null) {
					ManagedIdentifiableProxy personObj = getEntityProxy(context, csiPerson);
					personObj.getProviderProxy().copyLoadedProjection(personObj, csiPerson, proj);
					Persistent pojoPerson = personObj.getPojoObject();
					assignAttributeValue(pojoIdentifiable, PersonContactInfo.Attributes.bookmark.name(), pojoPerson);
				} else {
					assignAttributeValue(pojoIdentifiable, PersonContactInfo.Attributes.bookmark.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		PersonContactUpdater updater = (PersonContactUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoObject();
		
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
			java.util.TimeZone tz = (java.util.TimeZone) getAttributeValue(pojoIdentifiable, PersonalInfo.Attributes.timeZone.name());
			if (tz != null) {
				TimeZoneControl control = ControlLocator.getInstance().getControl(TimeZoneControl.class);
				TimeZoneListFilter tzListFilter = TimeZoneFactory.getInstance().createTimeZoneListFilter();
				NamePredicate pred = tzListFilter.createNamePredicate(tz.getDisplayName());
				tzListFilter.setPredicate(pred);
				tzListFilter.setProjection(Projection.FULL);
				try {
					ListResult<TimeZone> csiTZs = control.listTimeZones(tzListFilter);
					if (csiTZs.size() > 0) {
						TimeZone csiTz = csiTZs.get(0);
						TimeZoneHandle timeZoneHandle = (TimeZoneHandle) EntityUtils.getInstance().createHandle(csiTz.getCollabId());
						updater.setTimeZone(timeZoneHandle);
					}
				} catch (CsiException ex) {
					
				}
			} else {
				updater.setTimeZone(null);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		PersonContactUpdater personContactUpdater = PersonContactFactory.getInstance().createPersonContactUpdater();
		DAOContext context = new DAOContext(personContactUpdater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		AddressBookControl control = ControlLocator.getInstance().getControl(AddressBookControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		PersonContactHandle personContactHandle = (PersonContactHandle) EntityUtils.getInstance().createHandle(id);
		PersonContactUpdater updater = (PersonContactUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();	
		try {
			AddressBookElementOperationContext operationContext = control.beginUpdatePersonContact(personContactHandle, updater, updateMode);
			DAOContext attachmentUpdateContext = new DAOContext(updater);
			attachmentUpdateContext.setOperationContext(operationContext);
			updateAttachments(obj, context);
			AddressBookElement csiAddressBookElement = control.commitOperation(operationContext, proj);
			assignChangeToken(pojoIdentifiable, csiAddressBookElement.getSnapshotId().toString());
			return csiAddressBookElement;
			
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		AddressBookControl control = ControlLocator.getInstance().getControl(AddressBookControl.class);
		PersonContactUpdater updater = (PersonContactUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoContact = obj.getPojoIdentifiable();
		CollabId contactId = getCollabId(obj.getObjectId());
		Persistent pojoAddressBook = (Persistent) getAttributeValue(pojoContact, EntityInfo.Attributes.parent.name());
		CollabId addressBookId = getCollabId(((ManagedIdentifiableProxy)pojoAddressBook.getManagedObjectProxy()).getObjectId());
		AddressBookHandle addressBookHandle = (AddressBookHandle) EntityUtils.getInstance().createHandle(addressBookId);
		try {
			AddressBookElement csiAddressBookElement = null;
			Persistent pojoBookmarkedPerson = (Persistent) getAttributeValue(pojoContact, PersonContactInfo.Attributes.bookmark.name());
			if (pojoBookmarkedPerson != null) {
				CollabId personId = getCollabId(((ManagedIdentifiableProxy)pojoBookmarkedPerson.getManagedObjectProxy()).getObjectId());
				PersonHandle bookmarkedPersonHandle = (PersonHandle) EntityUtils.getInstance().createHandle(personId);
				csiAddressBookElement = control.createPersonContact(contactId.getEid(), addressBookHandle, bookmarkedPersonHandle, updater, proj);
			} else {
				csiAddressBookElement = control.createPersonContact(contactId.getEid(), addressBookHandle, updater, proj);
			}
			assignChangeToken(pojoContact, csiAddressBookElement.getSnapshotId().toString());
			/*
			PersonContactHandle personContactHandle = (PersonContactHandle) EntityUtils.getInstance().createHandle(contactId);
			PersonContactUpdater personContactUpdater = PersonContactFactory.getInstance().createPersonContactUpdater();
			AddressBookElementOperationContext operationContext = control.beginUpdatePersonContact(personContactHandle, personContactUpdater, UpdateMode.alwaysUpdate());
			DAOContext attachmentUpdateContext = new DAOContext(personContactUpdater);
			attachmentUpdateContext.setOperationContext(operationContext);
			updateAttachments(obj, context);
			AddressBookElement csiAddressBookElement = control.commitOperation(operationContext, proj);
			assignChangeToken(pojoContact, csiAddressBookElement.getSnapshotId().toString());
			*/
			return csiAddressBookElement;
			
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		AddressBookControl control = ControlLocator.getInstance().getControl(AddressBookControl.class);
		CollabId collabId = getCollabId(obj.getObjectId());
		PersonContactHandle personContactHandle = (PersonContactHandle) EntityUtils.getInstance().createHandle(collabId);
		try {
			control.deletePersonContact(personContactHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

}
