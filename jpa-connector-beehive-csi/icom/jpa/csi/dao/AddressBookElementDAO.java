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

import icom.info.AddressableInfo;
import icom.info.AttachmentInfo;
import icom.info.PersonContactInfo;
import icom.info.IcomBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oracle.csi.AddressBookElement;
import oracle.csi.Attachable;
import oracle.csi.CollabId;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.EntityAddress;
import oracle.csi.Identifiable;
import oracle.csi.IdentifiableSimpleContentHandle;
import oracle.csi.RawString;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AddressBookElementUpdater;
import oracle.csi.updaters.AttachmentListUpdater;
import oracle.csi.updaters.IdentifiableSimpleContentUpdater;

public abstract class AddressBookElementDAO extends ArtifactDAO {
	
	{
		basicAttributes.add(AddressableInfo.Attributes.entityAddresses);
		basicAttributes.add(AddressableInfo.Attributes.primaryAddress);
	}
	
	{
		fullAttributes.add(PersonContactInfo.Attributes.attachments);
	}

	protected AddressBookElementDAO() {	
	}

	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		PersistenceContext context = obj.getPersistenceContext();
		AddressBookElement csiAddressBookElement = (AddressBookElement) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(AddressableInfo.Attributes.primaryAddress.name(), lastLoadedProjection, proj)) {
			try {
				Object pojoPrimaryAddress = null;
				EntityAddress csiPrimaryAddress = csiAddressBookElement.getPrimaryAddress();
				if (csiPrimaryAddress != null) {
					ManagedObjectProxy primaryAddressObj = getNonIdentifiableDependentProxy(context, csiPrimaryAddress, obj, AddressableInfo.Attributes.primaryAddress.name());
					primaryAddressObj.getProviderProxy().copyLoadedProjection(primaryAddressObj, csiPrimaryAddress, proj);
					pojoPrimaryAddress = primaryAddressObj.getPojoObject();
					
				}
				assignAttributeValue(pojoIdentifiable, AddressableInfo.Attributes.primaryAddress.name(), pojoPrimaryAddress);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(AddressableInfo.Attributes.entityAddresses.name(), lastLoadedProjection, proj)) {
			try {
				HashSet<Object> pojoAddresses = new HashSet<Object>();
				Collection<EntityAddress> csiAddresses = csiAddressBookElement.getAddresses();
				if (csiAddresses != null) {
					for (EntityAddress csiAddress : csiAddresses) {
						ManagedObjectProxy addressObj = getNonIdentifiableDependentProxy(context, csiAddress, obj, AddressableInfo.Attributes.entityAddresses.name());
						addressObj.getProviderProxy().copyLoadedProjection(addressObj, csiAddress, proj);
						Object pojoEntityAddress = addressObj.getPojoObject();
						pojoAddresses.add(pojoEntityAddress);
					}
				}
				assignAttributeValue(pojoIdentifiable, AddressableInfo.Attributes.entityAddresses.name(), pojoAddresses);		
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(PersonContactInfo.Attributes.attachments.name(), lastLoadedProjection, proj)) {
			try {
				Set<Attachable> csiAttachments = csiAddressBookElement.getAttachments();
				if (csiAttachments != null) {
					Set<Persistent> pojoNewAttachments = new HashSet<Persistent>(csiAttachments.size());
					for (Attachable csiAttachment : csiAttachments) {
						String pojoClassName = IcomBeanEnumeration.AttachedItem.name();
						ManagedObjectProxy attachmentObj = getNonIdentifiableDependentProxy(context, pojoClassName, obj, PersonContactInfo.Attributes.attachments.name());
						Persistent pojoAttachment = attachmentObj.getPojoObject();
						RawString rawname = csiAttachment.getName();
						String name = null;
						try {
							name = rawname.convertToString();
						} catch (UnsupportedEncodingException ex) {
							name = rawname.getString();
						}
						assignAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name(), name);
						ManagedObjectProxy contentObj = getIdentifiableDependentProxy(context, csiAttachment, attachmentObj, AttachmentInfo.Attributes.content.name());
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, (Identifiable) csiAttachment, proj);
						pojoNewAttachments.add(pojoAttachment);
					}
					assignAttributeValue(pojoIdentifiable, PersonContactInfo.Attributes.attachments.name(), pojoNewAttachments);
				} else {
					assignAttributeValue(pojoIdentifiable, PersonContactInfo.Attributes.attachments.name(), new HashSet<Persistent>());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		AddressBookElementUpdater updater = (AddressBookElementUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, AddressableInfo.Attributes.primaryAddress.name())) {
			Persistent primaryAddress = (Persistent) getAttributeValue(pojoIdentifiable, AddressableInfo.Attributes.primaryAddress.name());
			EntityAddressDAO.getInstance().updateObjectState(primaryAddress, updater, EntityAddressDAO.Operand.SET_PRIMARY);
		}
		
		if (isChanged(obj, AddressableInfo.Attributes.entityAddresses.name())) {
			ArrayList<Persistent> modifiedAddresses = new ArrayList<Persistent>();
			ArrayList<Persistent> addedAddresses = new ArrayList<Persistent>();
			ArrayList<Persistent> removedAddresses = new ArrayList<Persistent>();
			Collection<Object> addresses = getObjectCollection(pojoIdentifiable, AddressableInfo.Attributes.entityAddresses.name());
			if (addresses != null) {
				for (Object address : addresses) {
					Persistent pojo = (Persistent) address;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.hasAttributeChanges()) {
						modifiedAddresses.add(pojo);
					}
				}
			}
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(AddressableInfo.Attributes.entityAddresses.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent address = (Persistent) holder.getValue();
					modifiedAddresses.remove(address);
					addedAddresses.add(address);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(AddressableInfo.Attributes.entityAddresses.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent address = (Persistent) holder.getValue();
					modifiedAddresses.remove(address);
					removedAddresses.add(address);
				}
			}
			
			if (modifiedAddresses.size() > 0) {
				// simply recreate all addresses, since CSI does not support modify
				EntityAddressDAO.getInstance().updateObjectState(addresses, updater);
			} else {
				for (Persistent pojoEntityAddress : addedAddresses) {
					EntityAddressDAO.getInstance().updateObjectState(pojoEntityAddress, updater, EntityAddressDAO.Operand.ADD);
				}
				for (Persistent pojoEntityAddress : removedAddresses) {
					EntityAddressDAO.getInstance().updateObjectState(pojoEntityAddress, updater, EntityAddressDAO.Operand.REMOVE);
				}
			}
		}
	}
	
	protected void updateAttachments(ManagedIdentifiableProxy obj, DAOContext context) {
		AddressBookElementUpdater updater = (AddressBookElementUpdater) context.getUpdater();
		Persistent pojoContact = obj.getPojoIdentifiable();
		
		if (isChanged(obj, PersonContactInfo.Attributes.attachments.name())) {		
			ArrayList<Persistent> modifiedAttachments = new ArrayList<Persistent>();
			Collection<Object> attachments = getObjectCollection(pojoContact, PersonContactInfo.Attributes.attachments.name());
			if (attachments != null) {
				for (Object attachment : attachments) {
					Persistent pojo = (Persistent) attachment;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.hasAttributeChanges()) {
						modifiedAttachments.add(pojo);
					}
				}
			}
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(PersonContactInfo.Attributes.attachments.name());
			if (addedObjects != null) {
				for (ValueHolder holder : addedObjects) {
					Persistent pojoAttachment = (Persistent) holder.getValue();
					String name = (String) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name());
					RawString rawname = new RawString(name);
					Persistent pojoSimpleContent = (Persistent) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.content.name());
					ManagedIdentifiableProxy simpleContentObj = (ManagedIdentifiableProxy) pojoSimpleContent.getManagedObjectProxy();
					CollabId contentId = getCollabId(simpleContentObj.getObjectId());
					Eid contentEid = contentId.getEid();
					AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
					IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(contentEid);
					simpleContentUpdater.setName(rawname);
					DAOContext childContext = new DAOContext(simpleContentUpdater);
					childContext.setOperationContext(context.getOperationContext());
					SimpleContentDAO.getInstance().updateObjectState(simpleContentObj, childContext);
					modifiedAttachments.remove(pojoSimpleContent);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(PersonContactInfo.Attributes.attachments.name());
			if (removedObjects != null) {
				AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
				for (ValueHolder holder : removedObjects) {
					Persistent pojoSimpleContent = (Persistent) holder.getValue();
					ManagedIdentifiableProxy simpleContentObj = (ManagedIdentifiableProxy) pojoSimpleContent.getManagedObjectProxy();
					CollabId contentId = getCollabId(simpleContentObj.getObjectId());
					IdentifiableSimpleContentHandle identifiableSimpleContentHandle = (IdentifiableSimpleContentHandle) EntityUtils.getInstance().createHandle(contentId);
					attachmentListUpdater.removeAttachment(identifiableSimpleContentHandle);
					modifiedAttachments.remove(pojoSimpleContent);
				}
			}
			for (Persistent pojoAttachment : modifiedAttachments) {
				Persistent pojoSimpleContent = (Persistent) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.content.name());
				ManagedIdentifiableProxy simpleContentObj = (ManagedIdentifiableProxy) pojoSimpleContent.getManagedObjectProxy();
				CollabId contentId = getCollabId(simpleContentObj.getObjectId());
				Eid contentEid = contentId.getEid();
				AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
				IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(contentEid);
				String name = (String) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name());
				RawString rawname = new RawString(name);
				simpleContentUpdater.setName(rawname);
				DAOContext childContext = new DAOContext(simpleContentUpdater);
				childContext.setOperationContext(context.getOperationContext());
				SimpleContentDAO.getInstance().updateObjectState(simpleContentObj, childContext);
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


}
