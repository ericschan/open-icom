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

import icom.info.AddressBookInfo;
import icom.info.EntityInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.AddressBook;
import oracle.csi.AddressBookHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.WorkspaceHandle;
import oracle.csi.controls.AddressBookControl;
import oracle.csi.controls.AddressBookFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.filters.ListResult;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AddressBookUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class AddressBookDAO extends FolderDAO {

	static AddressBookDAO singleton = new AddressBookDAO();
	
	public static AddressBookDAO getInstance() {
		return singleton;
	}
	
	{
		//basicAttributes.add(AddressBookInfo.Attributes.contactDefinition);
	}
	
	{
		fullAttributes.add(AddressBookInfo.Attributes.contacts);
		fullAttributes.add(AddressBookInfo.Attributes.addressBooks);
	}

	protected AddressBookDAO() {	
	}

	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return AddressBookHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		AddressBook csiAddressBook = null;
		try {
			AddressBookControl control = ControlLocator.getInstance().getControl(AddressBookControl.class);
			CollabId collabId = getCollabId(obj.getObjectId());
			AddressBookHandle csiAddressBookHandle = (AddressBookHandle) EntityUtils.getInstance().createHandle(collabId);
			csiAddressBook = control.loadAddressBook(csiAddressBookHandle, proj);			
		} catch (CsiException ex) {
		}
		return csiAddressBook;
	}
		
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		super.copyObjectState(managedObj, csiEntity, proj);
		
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		AddressBook csiAddressBook = (AddressBook) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		/*
		if (isBetweenProjections(AddressBookInfo.Attributes.contactDefinition.name(), lastLoadedProjection, proj)) {
			ContactDefinitionDAO.getInstance().loadContactDefinition(obj, Projection.FULL, AddressBookInfo.Attributes.contactDefinition.name());
		}
		*/
		
		if (isBetweenProjections(AddressBookInfo.Attributes.addressBooks.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(AddressBookInfo.Attributes.addressBooks.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(AddressBookInfo.Attributes.addressBooks.name());
				ListResult<? extends Entity> list = csiAddressBook.getSubAddressBooks();
				Vector<Persistent> v = new Vector<Persistent>(list.size());
				for (Entity csiSubAddressBooks : list) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent subAddressBook = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(subAddressBook.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiSubAddressBooks.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiSubAddressBooks);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, AddressBookInfo.Attributes.addressBooks.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(AddressBookInfo.Attributes.contacts.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(AddressBookInfo.Attributes.contacts.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(AddressBookInfo.Attributes.contacts.name());
				ListResult<? extends Entity> plist = csiAddressBook.getPersonContacts();
				
				Vector<Persistent> v = new Vector<Persistent>(plist.size());
				for (Entity csiPersonContact : plist) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent contact = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(contact.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiPersonContact.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiPersonContact);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, AddressBookInfo.Attributes.contacts.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		AddressBookUpdater updater = AddressBookFactory.getInstance().createAddressBookUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		AddressBookControl control = ControlLocator.getInstance().getControl(AddressBookControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		AddressBookHandle addressBookHandle = (AddressBookHandle) EntityUtils.getInstance().createHandle(id);
		AddressBookUpdater updater = (AddressBookUpdater) context.getUpdater();
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
			AddressBook addressBook = control.updateAddressBook(addressBookHandle, updater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, addressBook.getSnapshotId().toString());
			return addressBook;
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
		AddressBookUpdater updater = (AddressBookUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		CollabId id = getCollabId(obj.getObjectId());
		Persistent parent = getParent(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
			if (entityHandle instanceof WorkspaceHandle) {
				WorkspaceHandle workspaceHandle = (WorkspaceHandle) entityHandle;
				AddressBook addressBook = control.createAddressBook(id.getEid(), workspaceHandle, name, updater, proj);
				assignChangeToken(pojoIdentifiable, addressBook.getSnapshotId().toString());
				return addressBook;
			} else if (entityHandle instanceof AddressBookHandle) {
				AddressBookHandle addressBookHandle = (AddressBookHandle) entityHandle;
				AddressBook addressBook = control.createAddressBook(id.getEid(), addressBookHandle, name, updater, proj);
				assignChangeToken(pojoIdentifiable, addressBook.getSnapshotId().toString());
				return addressBook;
			} else {
				throw new PersistenceException("parent of address book must be workspace or address book");
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		AddressBookControl control = ControlLocator.getInstance().getControl(AddressBookControl.class);
		CollabId collabId = getCollabId(obj.getObjectId());
		AddressBookHandle csiAddressBookHandle = (AddressBookHandle) EntityUtils.getInstance().createHandle(collabId);
		try {
			control.deleteAddressBook(csiAddressBookHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

}
