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

import icom.info.AbstractBeanInfo;
import icom.info.ArtifactInfo;
import icom.info.BeanHandler;
import icom.info.BeanInfo;
import icom.info.ContentInfo;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.MessageInfo;
import icom.info.UnifiedMessageInfo;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiDataAccessIdentifiableStateObject;
import icom.jpa.csi.CsiDataAccessNonIdentifiableStateObject;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Vector;

import oracle.csi.Content;
import oracle.csi.ContentDispositionType;
import oracle.csi.CsiRuntimeException;
import oracle.csi.EmailDeliveryDirective;
import oracle.csi.EmailMessageContent;
import oracle.csi.EmailOperationContext;
import oracle.csi.EmailParticipant;
import oracle.csi.EmailRecipient;
import oracle.csi.MimeHeader;
import oracle.csi.MimeHeaders;
import oracle.csi.Priority;
import oracle.csi.RawString;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.EmailMessageContentUpdater;
import oracle.csi.updaters.MultiContentUpdater;
import oracle.csi.updaters.OnlineContentUpdater;
import oracle.csi.updaters.StreamedSimpleContentUpdater;

public class EmailMessageContentUtil {
	
	private static enum ContentClasses { MultiContent, SimpleContent, OnlineContent, UnifiedMessage };
	
	static public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		EmailMessageContent csiEmailMessageContent = (EmailMessageContent) csiObject;
		PersistenceContext context = obj.getPersistenceContext();
		Object pojoObject = obj.getPojoObject();

			try {
				// sent time is represented by user created on time of artifact
				AbstractDAO.assignAttributeValue(pojoObject, ArtifactInfo.Attributes.userCreationDate.name(), csiEmailMessageContent.getSentDate());
			} catch (CsiRuntimeException ex) {
				// ignore
			}

			try {
				RawString csiSubject = csiEmailMessageContent.getSubject();
				String name = csiSubject.toString(); // TODO handle unsupported character set exception
				// subject is represented by name of entity
				AbstractDAO.assignAttributeValue(pojoObject, EntityInfo.Attributes.name.name(), name);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
			try {
				Priority csiPriority = csiEmailMessageContent.getPriority();
				String pojoPriorityName = EntityDAO.csiToPojoPriorityNameMap.get(csiPriority.name());
				AbstractDAO.assignEnumConstant(pojoObject, UnifiedMessageInfo.Attributes.priority.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PriorityEnum.name(), pojoPriorityName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
			Boolean mdnRequested = false;
			Boolean dnsFailureRequested = false;
			Boolean dnsSuccessRequested = false;
			Boolean dnsDelayRequested = false;
			Boolean dnsNeverRequested = false;

			try {
				Collection<EmailRecipient> csiRecipients = csiEmailMessageContent.getTOReceivers();
				Iterator<EmailRecipient> iter = csiRecipients.iterator();
				Vector<Persistent> v = new Vector<Persistent>(csiRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient csiEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiEmailRecipient, obj, UnifiedMessageInfo.Attributes.toReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiEmailRecipient, proj);
					EnumSet<EmailDeliveryDirective> directives = csiEmailRecipient.getDirectives();
					if (directives.contains(EmailDeliveryDirective.DISPOSITION_NOTIFICATION)) {
						mdnRequested = true;
					}
					if (directives.contains(EmailDeliveryDirective.FAILURE_NOTIFICATION)) {
						dnsFailureRequested = true;
					}
					if (directives.contains(EmailDeliveryDirective.SUCCESS_NOTIFICATION)) {
						dnsSuccessRequested = true;
					}
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = AbstractDAO.getObjectCollection(pojoObject, UnifiedMessageInfo.Attributes.toReceivers.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.toReceivers.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}

			try {
				Collection<EmailRecipient> csiRecipients = csiEmailMessageContent.getCCReceivers();
				Iterator<EmailRecipient> iter = csiRecipients.iterator();
				Vector<Persistent> v = new Vector<Persistent>(csiRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient csiEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiEmailRecipient, obj, UnifiedMessageInfo.Attributes.ccReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiEmailRecipient, proj);
					EnumSet<EmailDeliveryDirective> directives = csiEmailRecipient.getDirectives();
					if (directives.contains(EmailDeliveryDirective.DISPOSITION_NOTIFICATION)) {
						mdnRequested = true;
					}
					if (directives.contains(EmailDeliveryDirective.FAILURE_NOTIFICATION)) {
						dnsFailureRequested = true;
					}
					if (directives.contains(EmailDeliveryDirective.SUCCESS_NOTIFICATION)) {
						dnsSuccessRequested = true;
					}
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = AbstractDAO.getObjectCollection(pojoObject, UnifiedMessageInfo.Attributes.ccReceivers.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.ccReceivers.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		
			try {
				Collection<EmailRecipient> csiRecipients = csiEmailMessageContent.getBCCReceivers();
				Iterator<EmailRecipient> iter = csiRecipients.iterator();
				Vector<Persistent> v = new Vector<Persistent>(csiRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient csiEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiEmailRecipient, obj, UnifiedMessageInfo.Attributes.bccReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiEmailRecipient, proj);
					EnumSet<EmailDeliveryDirective> directives = csiEmailRecipient.getDirectives();
					if (directives.contains(EmailDeliveryDirective.DISPOSITION_NOTIFICATION)) {
						mdnRequested = true;
					}
					if (directives.contains(EmailDeliveryDirective.FAILURE_NOTIFICATION)) {
						dnsFailureRequested = true;
					}
					if (directives.contains(EmailDeliveryDirective.SUCCESS_NOTIFICATION)) {
						dnsSuccessRequested = true;
					}
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = AbstractDAO.getObjectCollection(pojoObject, UnifiedMessageInfo.Attributes.bccReceivers.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.bccReceivers.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
			try {
				Collection<EmailParticipant> csiEmailParticipants = csiEmailMessageContent.getReplyTo();
				Iterator<EmailParticipant> iter = csiEmailParticipants.iterator();
				Vector<Persistent> v = new Vector<Persistent>(csiEmailParticipants.size());
				while (iter.hasNext()) {
					EmailParticipant csiEmailParticipant = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiEmailParticipant, obj, UnifiedMessageInfo.Attributes.replyTo.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiEmailParticipant, proj);
				}
				
				Collection<Object> previousPojoObjects = AbstractDAO.getObjectCollection(pojoObject, UnifiedMessageInfo.Attributes.replyTo.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.replyTo.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
			AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(), mdnRequested);
			
			try {
				Collection<String> enumConstantNames = new ArrayList<String>();
				if (dnsFailureRequested) {
					String pojoDNSRequestName = EmailMessageDAO.csiToPojoDNSRequestNameMap.get(EmailDeliveryDirective.FAILURE_NOTIFICATION.name());
					enumConstantNames.add(pojoDNSRequestName);
				}	
				if (dnsSuccessRequested) {
					String pojoDNSRequestName = EmailMessageDAO.csiToPojoDNSRequestNameMap.get(EmailDeliveryDirective.SUCCESS_NOTIFICATION.name());
					enumConstantNames.add(pojoDNSRequestName);
				}
				if (dnsDelayRequested) {
					// TODO when CSI supports it
				}
				if (dnsNeverRequested) {
					// TODO when CSI supports it
				}
				EnumSet<?> pojoDNSrequestSet = EmailMessageDAO.instantiateEnumSet(BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageDeliveryStatusNotificationRequestEnum.name(), enumConstantNames);
				EmailMessageDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.messageDeliveryStatusNotificationRequests.name(), pojoDNSrequestSet);
			} catch (CsiRuntimeException ex) {
				// ignore
			}

			try {
				EmailParticipant csiSender = csiEmailMessageContent.getSender();
				if (csiSender != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiSender, obj, MessageInfo.Attributes.sender.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiSender, proj);
					
					Object previousPojoObject = AbstractDAO.getAttributeValue(pojoObject, MessageInfo.Attributes.sender.name());
					if (previousPojoObject != null) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
					
					AbstractDAO.assignAttributeValue(pojoObject, MessageInfo.Attributes.sender.name(), participantObj.getPojoObject());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
			try {
				EmailParticipant csiFrom = csiEmailMessageContent.getFrom();
				if (csiFrom != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiFrom, obj, UnifiedMessageInfo.Attributes.envelopeSender.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiFrom, proj);
					AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.envelopeSender.name(), participantObj.getPojoObject());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}

			try {
				Content csiChildContent = csiEmailMessageContent.getBody();
				if (csiChildContent != null) {
					boolean createDependentProxy = true;
					Object pojoContent = AbstractDAO.getAttributeValue(pojoObject, MessageInfo.Attributes.content.name());
					if (pojoContent != null) {
						ManagedObjectProxy contentObj = (ManagedObjectProxy) AbstractDAO.getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
						if (contentObj != null) {
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
							createDependentProxy = false;
						}
					}
					if (createDependentProxy) {
						ManagedObjectProxy contentObj = getNonIdentifiableDependentProxy(context, csiChildContent, obj, MessageInfo.Attributes.content.name());
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
						AbstractDAO.assignAttributeValue(pojoObject, MessageInfo.Attributes.content.name(), contentObj.getPojoObject());
					}
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
			try {
				AbstractDAO.assignAttributeValue(pojoObject, ContentInfo.Attributes.contentId.name(), csiEmailMessageContent.getContentId());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		
			try {
				AbstractDAO.assignAttributeValue(pojoObject, ContentInfo.Attributes.mediaType.name(), csiEmailMessageContent.getMediaType());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		
			try {
				ContentDispositionType csiDispositionType = csiEmailMessageContent.getContentDisposition();
				if (csiDispositionType != null) {
					String csiDispositionTypeName = csiDispositionType.name();
					String pojoDispositionTypeName = ContentDAO.csiToPojoContentDispositionMap.get(csiDispositionTypeName);
					AbstractDAO.assignEnumConstant(pojoObject, ContentInfo.Attributes.contentDisposition.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ContentDispositionTypeEnum.name(), pojoDispositionTypeName);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
			
			try {
				Vector<Object> v = new Vector<Object>();
				MimeHeaders csiHeaders = csiEmailMessageContent.getMimeHeaders();
				if (csiHeaders != null) {
					MimeHeader[] csiHeaderArray = csiHeaders.getAll();
					for (MimeHeader csiHeader : csiHeaderArray) {
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, IcomBeanEnumeration.Property.name(), obj, UnifiedMessageInfo.Attributes.mimeHeaders.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, csiHeader, proj);
						v.add(propertyObj.getPojoObject());
					}
				}
				
				Collection<Object> previousProperties = AbstractDAO.getObjectCollection(pojoObject, UnifiedMessageInfo.Attributes.mimeHeaders.name());
				if (previousProperties != null) {
					for (Object pojoProperty : previousProperties) {
						if (pojoProperty instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
							ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.mimeHeaders.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}

	}
	
	static public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(PersistenceContext context, Object state, ManagedObjectProxy parent, String parentAttributeName) {
		DataAccessStateObject dataAccessStateObject = wrapDataAccessStateObject(state);
		return context.getNonIdentifiableDependentProxy(dataAccessStateObject, parent, parentAttributeName);
	}
	
	static public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(PersistenceContext context, String beanClassName, ManagedObjectProxy parent, String parentAttributeName) {
		return context.getNonIdentifiableDependentProxy(beanClassName, parent, parentAttributeName);
	}
	
	static DataAccessStateObject wrapDataAccessStateObject(Object state) {
		if (state instanceof oracle.csi.Identifiable) {
			return new CsiDataAccessIdentifiableStateObject((oracle.csi.Identifiable) state);
		} else {
			return new CsiDataAccessNonIdentifiableStateObject(state);
		}
	}
	
	static private void updateNewOrOldObjectState(ManagedObjectProxy obj, DAOContext context) {
		EmailMessageContentUpdater contentUpdater = (EmailMessageContentUpdater) context.getUpdater();
		EmailMessageDAO unifiedMessageDAO = EmailMessageDAO.getInstance();
		Object pojoUnifiedMessage = obj.getPojoObject();
		
		Boolean mdnRequested;
		mdnRequested = (Boolean) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name());
		if (mdnRequested == null) {
			mdnRequested = false;
		}
		EnumSet<EmailDeliveryDirective> directives = null;
		if (mdnRequested) {
			if (directives == null) {
				directives = EnumSet.noneOf(EmailDeliveryDirective.class);
			}
			directives.add(EmailDeliveryDirective.DISPOSITION_NOTIFICATION);
		}
		
		EnumSet<? extends Enum<?>> pojoDNSRequestSet = EmailMessageDAO.getEnumSet(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.messageDeliveryStatusNotificationRequests.name());
		if (pojoDNSRequestSet != null) {
			for (Enum<?> pojoDNSRequest : pojoDNSRequestSet) {
				String pojoDNSRequestName = pojoDNSRequest.name();
				String csiDNSRequestName = EmailMessageDAO.pojoToCsiDNSRequestNameMap.get(pojoDNSRequestName);
				if (directives == null) {
					directives = EnumSet.noneOf(EmailDeliveryDirective.class);
				}
				directives.add(EmailDeliveryDirective.valueOf(csiDNSRequestName));
			}
		}

		if (obj.isNew() || unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name())) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.toReceivers.name());
			if (participants != null) {
				for (Object participant : participants) {
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.setDirectives(directives);
					}
					contentUpdater.addTOReceiver(recipient);
				}
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.toReceivers.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.toReceivers.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.setDirectives(directives);
					}
					contentUpdater.addTOReceiver(recipient);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.toReceivers.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					contentUpdater.removeTOReceiver(recipient);
				}
			}
		}
		
		if (obj.isNew() || unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name())) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.ccReceivers.name());
			if (participants != null) {
				for (Object participant : participants) {
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.setDirectives(directives);
					}
					contentUpdater.addCCReceiver(recipient);
				}
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.ccReceivers.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.ccReceivers.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.setDirectives(directives);
					}
					contentUpdater.addCCReceiver(recipient);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.ccReceivers.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					contentUpdater.removeCCReceiver(recipient);
				}
			}
		}
		
		if (obj.isNew() || unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name())) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.bccReceivers.name());
			if (participants != null) {
				for (Object participant : participants) {
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.setDirectives(directives);
					}
					contentUpdater.addBCCReceiver(recipient);
				}
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.bccReceivers.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.bccReceivers.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.setDirectives(directives);
					}
					contentUpdater.addBCCReceiver(recipient);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.bccReceivers.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					contentUpdater.removeBCCReceiver(recipient);
				}
			}
		}
		
		if (obj.isNew()) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.replyTo.name());
			if (participants != null) {
				for (Object participant : participants) {
					EmailParticipant csiEmailParticipant = EmailParticipantUtil.updateEmailParticipant(participant);
					contentUpdater.addReplyTo(csiEmailParticipant);
				}
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.replyTo.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.replyTo.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailParticipant csiEmailParticipant = EmailParticipantUtil.updateEmailParticipant(participant);
					contentUpdater.addReplyTo(csiEmailParticipant);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.replyTo.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent participant = (Persistent) holder.getValue();
					EmailParticipant csiEmailParticipant = EmailParticipantUtil.updateEmailParticipant(participant);
					contentUpdater.removeReplyTo(csiEmailParticipant);
				}
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.userCreationDate.name())) {
			Date sentTime = (Date) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, ArtifactInfo.Attributes.userCreationDate.name());
			contentUpdater.setSentDate(sentTime);
		}
		
		if (unifiedMessageDAO.isChanged(obj, MessageInfo.Attributes.sender.name())) {
			Object participant = EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, MessageInfo.Attributes.sender.name());
			if (participant != null) {
				EmailParticipant csiEmailParticipant = EmailParticipantUtil.updateEmailParticipant(participant);
				contentUpdater.setSender(csiEmailParticipant);
			} else {
				contentUpdater.setSender(null);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.envelopeSender.name())) {
			Object participant = EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.envelopeSender.name());
			if (participant != null) {
				EmailParticipant csiEmailParticipant = EmailParticipantUtil.updateEmailParticipant(participant);
				contentUpdater.setEnvelopeSender(csiEmailParticipant);
			} else {
				contentUpdater.setEnvelopeSender(null);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, EntityInfo.Attributes.name.name())) {
			String subject = (String) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, EntityInfo.Attributes.name.name());
			if (subject != null) {
				RawString rawSubject = new RawString(subject);
				contentUpdater.setSubject(rawSubject);
			} else {
				contentUpdater.setSubject(new RawString(""));
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.priority.name())) {
			String pojoPriorityName = EmailMessageDAO.getEnumName(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.priority.name());
			if (pojoPriorityName != null) {
				String csiPriorityName = EntityDAO.pojoToCsiPriorityNameMap.get(pojoPriorityName);
				contentUpdater.setPriority(Priority.valueOf(csiPriorityName));
			} else {
				contentUpdater.setPriority(Priority.NONE);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, ContentInfo.Attributes.contentId.name())) {
			String contentId = (String) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, ContentInfo.Attributes.contentId.name());
			contentUpdater.setContentId(contentId);
		}
		
		if (unifiedMessageDAO.isChanged(obj, ContentInfo.Attributes.contentDisposition.name())) {
			String pojoDispositionTypeName = EmailMessageDAO.getEnumName(pojoUnifiedMessage, ContentInfo.Attributes.contentDisposition.name());
			if (pojoDispositionTypeName != null) {
				String csiDispositionTypeName = ContentDAO.pojoToCsiContentDispositionMap.get(pojoDispositionTypeName);
				contentUpdater.setContentDisposition(ContentDispositionType.valueOf(csiDispositionTypeName));
			} else {
				contentUpdater.setContentDisposition(null);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, ContentInfo.Attributes.mediaType.name())) {
			String mediaType = (String) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, ContentInfo.Attributes.mediaType.name());
			contentUpdater.setMediaType(mediaType);
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.mimeHeaders.name())) {
			MimeHeaders headers = new MimeHeaders();
			Collection<Object> properties = EmailMessageDAO.getObjectCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.mimeHeaders.name());
			if (properties != null) {
				for (Object pojoProperty : properties) {
					MimeHeader header = MimeHeaderDAO.getInstance().updateObjectState(pojoProperty);
					headers.add(header);
				}
			}
			contentUpdater.setMimeHeaders(headers);
		} 
		
		/*
		contentUpdater.addInReplyTos(inReplyTos);
		contentUpdater.addReplyTo(replyTo);
		contentUpdater.addThreadReferences(references);
		contentUpdater.setMailer(mailer);
		contentUpdater.setSensitivity(sensitivity);
		*/
	}
	
	static public void updateObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
		updateChildContentState(obj, context, false);
	}
	
	static public void updateNewObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
		updateChildContentState(obj, context, true);
	}
	
	static private void updateChildContentState(ManagedObjectProxy obj, DAOContext context, boolean isNew) {
		Persistent pojoUnifiedMessage = obj.getPojoObject();
		Persistent pojoContent = EmailMessageDAO.getInstance().getContent(pojoUnifiedMessage);
		if (pojoContent != null) {
			EmailMessageContentUpdater contentUpdater = (EmailMessageContentUpdater) context.getUpdater();
			ManagedObjectProxy pojoContentProxy = AbstractDAO.getManagedObjectProxy(pojoContent);
			String contentClassName = pojoContent.getClass().getSimpleName();
			if (contentClassName.equals(ContentClasses.MultiContent.name())) {
				MultiContentUpdater childContentUpdater = contentUpdater.getBodyUpdater(MultiContentUpdater.class);
				DAOContext childContext = new DAOContext(childContentUpdater);
				EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
				childContext.setOperationContext(opContext);
				if (isNew) {
					MultiContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
				} else {
					MultiContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
				}
			} else if (contentClassName.equals(ContentClasses.SimpleContent.name())) {
				StreamedSimpleContentUpdater childContentUpdater = contentUpdater.getBodyUpdater(StreamedSimpleContentUpdater.class);
				DAOContext childContext = new DAOContext(childContentUpdater);
				EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
				childContext.setOperationContext(opContext);
				if (isNew) {
					SimpleContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
				} else {
					SimpleContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
				}
			} else if (contentClassName.equals(ContentClasses.OnlineContent.name())) {
				OnlineContentUpdater childContentUpdater = contentUpdater.getBodyUpdater(OnlineContentUpdater.class);
				DAOContext childContext = new DAOContext(childContentUpdater);
				EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
				childContext.setOperationContext(opContext);
				if (isNew) {
					OnlineContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
				} else {
					OnlineContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
				}
			} else if (contentClassName.equals(ContentClasses.UnifiedMessage.name())) {
				EmailMessageContentUpdater childContentUpdater = contentUpdater.getBodyUpdater(EmailMessageContentUpdater.class);
				DAOContext childContext = new DAOContext(childContentUpdater);
				EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
				childContext.setOperationContext(opContext);
				if (isNew) {
					updateNewObjectState(pojoContentProxy, childContext);
				} else {
					updateObjectState(pojoContentProxy, childContext);
				}
			}
		}
	}
	
	/*
	Participant updateParticipant(Object pojoParticipant) {
		Participant csiParticipant = null;
		oracle.bom.ejb.spi.Identifiable pojoAddressable = (oracle.bom.ejb.spi.Identifiable) AbstractDAO.getAttributeValue(pojoParticipant, ParticipantDAO.Attributes.participant.name());
		URI address = (URI) AbstractDAO.getAttributeValue(pojoParticipant, ParticipantDAO.Attributes.address.name());
		String name = (String) AbstractDAO.getAttributeValue(pojoParticipant, ParticipantDAO.Attributes.name.name());
		if (pojoAddressable != null) {
			CollabId id = ((ManagedIdentifiableProxy)pojoAddressable.getManagedObjectProxy()).getId();
			Addressable addressable = (Addressable) EntityUtils.getInstance().createEmptyEntity(id);
			csiParticipant = EmailParticipant.createParticipant(addressable, name);
		} else if (address != null) {
			csiParticipant = EmailParticipant.createParticipant(address, name);
		} else {
			throw new RuntimeException("No addressable or address for message participant");
		}
		return csiParticipant;
	}
	*/	

}
