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
import icom.jpa.bdk.BdkDataAccessNonIdentifiableStateObject;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.persistence.PersistenceException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.oracle.beehive.Content;
import com.oracle.beehive.ContentDispositionType;
import com.oracle.beehive.EmailDeliveryDirective;
import com.oracle.beehive.EmailMessageContent;
import com.oracle.beehive.EmailMessageContentUpdater;
import com.oracle.beehive.EmailParticipant;
import com.oracle.beehive.EmailParticipantListUpdater;
import com.oracle.beehive.EmailRecipient;
import com.oracle.beehive.EmailRecipientListUpdater;
import com.oracle.beehive.MimeHeader;
import com.oracle.beehive.MimeHeadersWrapper;
import com.oracle.beehive.MultiContentUpdater;
import com.oracle.beehive.OnlineContentUpdater;
import com.oracle.beehive.Priority;
import com.oracle.beehive.RawString;
import com.oracle.beehive.StreamedSimpleContentUpdater;

public class EmailMessageContentUtil {
	
	private static enum ContentClasses { MultiContent, SimpleContent, OnlineContent, UnifiedMessage };
	
	static public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
		EmailMessageContent bdkEmailMessageContent = (EmailMessageContent) bdkObject;
		PersistenceContext context = obj.getPersistenceContext();
		Object pojoObject = obj.getPojoObject();

			try {
				// sent time is represented by user created on time of artifact
				AbstractDAO.assignAttributeValue(pojoObject, ArtifactInfo.Attributes.userCreationDate.name(), bdkEmailMessageContent.getSentDate());
			} catch (Exception ex) {
				// ignore
			}

			try {
				RawString bdkSubject = bdkEmailMessageContent.getSubject();
				String name = bdkSubject.getString(); // TODO handle unsupported character set exception
				// subject is represented by name of entity
				AbstractDAO.assignAttributeValue(pojoObject, EntityInfo.Attributes.name.name(), name);
			} catch (Exception ex) {
				// ignore
			}
			
			try {
				Priority bdkPriority = bdkEmailMessageContent.getPriority();
				String pojoPriorityName = EntityDAO.bdkToPojoPriorityNameMap.get(bdkPriority.name());
				AbstractDAO.assignEnumConstant(pojoObject, UnifiedMessageInfo.Attributes.priority.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PriorityEnum.name(), pojoPriorityName);
			} catch (Exception ex) {
				// ignore
			}
			
			Boolean mdnRequested = false;
			Boolean dnsFailureRequested = false;
			Boolean dnsSuccessRequested = false;
			Boolean dnsDelayRequested = false;
			Boolean dnsNeverRequested = false;

			try {
				Collection<EmailRecipient> bdkRecipients = bdkEmailMessageContent.getTOReceivers();
				Iterator<EmailRecipient> iter = bdkRecipients.iterator();
				Vector<Persistent> v = new Vector<Persistent>(bdkRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient bdkEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, bdkEmailRecipient, obj, UnifiedMessageInfo.Attributes.toReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, bdkEmailRecipient, proj);
					List<EmailDeliveryDirective> directives = bdkEmailRecipient.getDirectives();
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
			} catch (Exception ex) {
				// ignore
			}

			try {
				Collection<EmailRecipient> bdkRecipients = bdkEmailMessageContent.getCCReceivers();
				Iterator<EmailRecipient> iter = bdkRecipients.iterator();
				Vector<Persistent> v = new Vector<Persistent>(bdkRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient bdkEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, bdkEmailRecipient, obj, UnifiedMessageInfo.Attributes.ccReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, bdkEmailRecipient, proj);
					List<EmailDeliveryDirective> directives = bdkEmailRecipient.getDirectives();
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
			} catch (Exception ex) {
				// ignore
			}
		
			try {
				Collection<EmailRecipient> bdkRecipients = bdkEmailMessageContent.getBCCReceivers();
				Iterator<EmailRecipient> iter = bdkRecipients.iterator();
				Vector<Persistent> v = new Vector<Persistent>(bdkRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient bdkEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, bdkEmailRecipient, obj, UnifiedMessageInfo.Attributes.bccReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, bdkEmailRecipient, proj);
					List<EmailDeliveryDirective> directives = bdkEmailRecipient.getDirectives();
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
			} catch (Exception ex) {
				// ignore
			}
			
			try {
				Collection<EmailParticipant> bdkEmailParticipants = bdkEmailMessageContent.getReplyTos();
				Iterator<EmailParticipant> iter = bdkEmailParticipants.iterator();
				Vector<Persistent> v = new Vector<Persistent>(bdkEmailParticipants.size());
				while (iter.hasNext()) {
					EmailParticipant bdkEmailParticipant = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, bdkEmailParticipant, obj, UnifiedMessageInfo.Attributes.replyTo.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, bdkEmailParticipant, proj);
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
			} catch (Exception ex) {
				// ignore
			}
			
			AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(), mdnRequested);
			
			try {
				Collection<String> enumConstantNames = new ArrayList<String>();
				if (dnsFailureRequested) {
					String pojoDNSRequestName = EmailMessageDAO.bdkToPojoDNSRequestNameMap.get(EmailDeliveryDirective.FAILURE_NOTIFICATION.name());
					enumConstantNames.add(pojoDNSRequestName);
				}	
				if (dnsSuccessRequested) {
					String pojoDNSRequestName = EmailMessageDAO.bdkToPojoDNSRequestNameMap.get(EmailDeliveryDirective.SUCCESS_NOTIFICATION.name());
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
			} catch (Exception ex) {
				// ignore
			}

			try {
				EmailParticipant bdkSender = bdkEmailMessageContent.getSender();
				if (bdkSender != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, bdkSender, obj, MessageInfo.Attributes.sender.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, bdkSender, proj);
					
					Object previousPojoObject = AbstractDAO.getAttributeValue(pojoObject, MessageInfo.Attributes.sender.name());
					if (previousPojoObject != null) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
					
					AbstractDAO.assignAttributeValue(pojoObject, MessageInfo.Attributes.sender.name(), participantObj.getPojoObject());
				}
			} catch (Exception ex) {
				// ignore
			}
			
			try {
				EmailParticipant bdkFrom = bdkEmailMessageContent.getFrom();
				if (bdkFrom != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, bdkFrom, obj, UnifiedMessageInfo.Attributes.envelopeSender.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, bdkFrom, proj);
					AbstractDAO.assignAttributeValue(pojoObject, UnifiedMessageInfo.Attributes.envelopeSender.name(), participantObj.getPojoObject());
				}
			} catch (Exception ex) {
				// ignore
			}

			try {
				Content bdkChildContent = bdkEmailMessageContent.getBody();
				if (bdkChildContent != null) {
					boolean createDependentProxy = true;
					Object pojoContent = AbstractDAO.getAttributeValue(pojoObject, MessageInfo.Attributes.content.name());
					if (pojoContent != null) {
						ManagedObjectProxy contentObj = (ManagedObjectProxy) AbstractDAO.getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
						if (contentObj != null) {
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
							createDependentProxy = false;
						}
					}
					if (createDependentProxy) {
						ManagedObjectProxy contentObj = getNonIdentifiableDependentProxy(context, bdkChildContent, obj, MessageInfo.Attributes.content.name());
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
						AbstractDAO.assignAttributeValue(pojoObject, MessageInfo.Attributes.content.name(), contentObj.getPojoObject());
					}
				}
			} catch (Exception ex) {
				// ignore
			}
			
			try {
				AbstractDAO.assignAttributeValue(pojoObject, ContentInfo.Attributes.contentId.name(), bdkEmailMessageContent.getContentId());
			} catch (Exception ex) {
				// ignore
			}
		
			try {
				AbstractDAO.assignAttributeValue(pojoObject, ContentInfo.Attributes.mediaType.name(), bdkEmailMessageContent.getMediaType());
			} catch (Exception ex) {
				// ignore
			}
		
			try {
				ContentDispositionType bdkDispositionType = bdkEmailMessageContent.getContentDisposition();
				if (bdkDispositionType != null) {
					String bdkDispositionTypeName = bdkDispositionType.name();
					String pojoDispositionTypeName = ContentDAO.bdkToPojoContentDispositionMap.get(bdkDispositionTypeName);
					AbstractDAO.assignEnumConstant(pojoObject, ContentInfo.Attributes.contentDisposition.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ContentDispositionTypeEnum.name(), pojoDispositionTypeName);
				}
			} catch (Exception ex) {
				// ignore
			}
			
			try {
				Vector<Object> v = new Vector<Object>();
				List<MimeHeader> bdkHeaders = bdkEmailMessageContent.getMimeHeaders().getHeaders();
				if (bdkHeaders != null) {
					for (MimeHeader bdkHeader : bdkHeaders) {
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, IcomBeanEnumeration.Property.name(), obj, UnifiedMessageInfo.Attributes.mimeHeaders.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, bdkHeader, proj);
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
			} catch (Exception ex) {
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
		return new BdkDataAccessNonIdentifiableStateObject(state);
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
				String bdkDNSRequestName = EmailMessageDAO.pojoToBdkDNSRequestNameMap.get(pojoDNSRequestName);
				if (directives == null) {
					directives = EnumSet.noneOf(EmailDeliveryDirective.class);
				}
				directives.add(EmailDeliveryDirective.valueOf(bdkDNSRequestName));
			}
		}

		if (obj.isNew() || unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name())) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.toReceivers.name());
			EmailRecipientListUpdater listUpdater = new EmailRecipientListUpdater();
			if (participants != null) {
				for (Object participant : participants) {
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
				contentUpdater.setToReceivers(listUpdater);
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.toReceivers.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.toReceivers.name());
			EmailRecipientListUpdater listUpdater = new EmailRecipientListUpdater();
			if (addedObjects != null) {
				for (ValueHolder holder : addedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.toReceivers.name());
			if (removedObjects != null) {
				for (ValueHolder holder : removedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					listUpdater.getRemoves().add(recipient);
				}
			}
			contentUpdater.setToReceivers(listUpdater);
		}
		
		if (obj.isNew() || unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name())) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.ccReceivers.name());
			EmailRecipientListUpdater listUpdater = new EmailRecipientListUpdater();
			if (participants != null) {
				for (Object participant : participants) {
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
				contentUpdater.setCcReceivers(listUpdater);
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.ccReceivers.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.ccReceivers.name());
			EmailRecipientListUpdater listUpdater = new EmailRecipientListUpdater();
			if (addedObjects != null) {
				for (ValueHolder holder : addedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.ccReceivers.name());
			if (removedObjects != null) {
				for (ValueHolder holder : removedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					listUpdater.getRemoves().add(recipient);
				}
			}
			contentUpdater.setCcReceivers(listUpdater);
		}
		
		if (obj.isNew() || unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name())) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.bccReceivers.name());
			EmailRecipientListUpdater listUpdater = new EmailRecipientListUpdater();
			if (participants != null) {
				for (Object participant : participants) {
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
				contentUpdater.setBccReceivers(listUpdater);
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.bccReceivers.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.bccReceivers.name());
			EmailRecipientListUpdater listUpdater = new EmailRecipientListUpdater();
			if (addedObjects != null) {
				for (ValueHolder holder : addedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.bccReceivers.name());
			if (removedObjects != null) {
				for (ValueHolder holder : removedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					listUpdater.getRemoves().add(recipient);
				}
			}
			contentUpdater.setBccReceivers(listUpdater);
		}
		
		if (obj.isNew()) {
			Collection<?> participants = (Collection<?>) EmailMessageDAO.getIdentifiableCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.replyTo.name());
			EmailParticipantListUpdater listUpdater = new EmailParticipantListUpdater();
			if (participants != null) {
				for (Object participant : participants) {
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
				contentUpdater.setReplyTos(listUpdater);
			}
		} else if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.replyTo.name())) {
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(UnifiedMessageInfo.Attributes.replyTo.name());
			EmailParticipantListUpdater listUpdater = new EmailParticipantListUpdater();
			if (addedObjects != null) {
				for (ValueHolder holder : addedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					if (directives != null) {
						recipient.getDirectives().addAll(directives);
					}
					listUpdater.getAdds().add(recipient);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(UnifiedMessageInfo.Attributes.replyTo.name());
			if (removedObjects != null) {
				for (ValueHolder holder : removedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					EmailRecipient recipient = EmailRecipientUtil.updateEmailRecipient(participant);
					listUpdater.getRemoves().add(recipient);
				}
			}
			contentUpdater.setReplyTos(listUpdater);
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.userCreationDate.name())) {
			Date sentTime = (Date) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, ArtifactInfo.Attributes.userCreationDate.name());
			if (sentTime != null) {
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(sentTime);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					contentUpdater.setSentDate(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
			} else {
				contentUpdater.setSentDate(null);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, MessageInfo.Attributes.sender.name())) {
			Object participant = EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, MessageInfo.Attributes.sender.name());
			if (participant != null) {
				EmailParticipant bdkEmailParticipant = EmailParticipantUtil.updateEmailParticipant(participant);
				contentUpdater.setSender(bdkEmailParticipant);
			} else {
				contentUpdater.setSender(null);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.envelopeSender.name())) {
			Object participant = EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.envelopeSender.name());
			if (participant != null) {
				EmailParticipant bdkEmailParticipant = EmailParticipantUtil.updateEmailParticipant(participant);
				contentUpdater.setEnvelopeSender(bdkEmailParticipant);
			} else {
				contentUpdater.setEnvelopeSender(null);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, EntityInfo.Attributes.name.name())) {
			String subject = (String) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, EntityInfo.Attributes.name.name());
			if (subject != null) {
				RawString rawSubject = new RawString();
				rawSubject.setString(subject);
				contentUpdater.setSubject(rawSubject);
			} else {
				contentUpdater.setSubject(new RawString());
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.priority.name())) {
			String pojoPriorityName = EmailMessageDAO.getEnumName(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.priority.name());
			if (pojoPriorityName != null) {
				String bdkPriorityName = EntityDAO.pojoToBdkPriorityNameMap.get(pojoPriorityName);
				contentUpdater.setPriority(Priority.valueOf(bdkPriorityName));
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
				String bdkDispositionTypeName = ContentDAO.pojoToBdkContentDispositionMap.get(pojoDispositionTypeName);
				contentUpdater.setContentDisposition(ContentDispositionType.valueOf(bdkDispositionTypeName));
			} else {
				contentUpdater.setContentDisposition(null);
			}
		}
		
		if (unifiedMessageDAO.isChanged(obj, ContentInfo.Attributes.mediaType.name())) {
			String mediaType = (String) EmailMessageDAO.getAttributeValue(pojoUnifiedMessage, ContentInfo.Attributes.mediaType.name());
			contentUpdater.setMediaType(mediaType);
		}
		
		if (unifiedMessageDAO.isChanged(obj, UnifiedMessageInfo.Attributes.mimeHeaders.name())) {
			MimeHeadersWrapper headers = new MimeHeadersWrapper();
			Collection<Object> properties = EmailMessageDAO.getObjectCollection(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.mimeHeaders.name());
			if (properties != null) {
				for (Object pojoProperty : properties) {
					MimeHeader header = MimeHeaderDAO.getInstance().updateObjectState(pojoProperty);
					headers.getHeaders().add(header);
				}
				contentUpdater.setMimeHeaders(headers);
			}
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
		String uploadScopeId = context.getUploadScopeId();
		Persistent pojoUnifiedMessage = obj.getPojoObject();
		Persistent pojoContent = EmailMessageDAO.getInstance().getContent(pojoUnifiedMessage);
		if (pojoContent != null) {
			EmailMessageContentUpdater contentUpdater = (EmailMessageContentUpdater) context.getUpdater();
			ManagedObjectProxy pojoContentProxy = AbstractDAO.getManagedObjectProxy(pojoContent);
			String contentClassName = pojoContent.getClass().getSimpleName();
			if (contentClassName.equals(ContentClasses.MultiContent.name())) {
				MultiContentUpdater childContentUpdater = new MultiContentUpdater();
				contentUpdater.setBodyUpdater(childContentUpdater);
				DAOContext childContext = new DAOContext(childContentUpdater);
				childContext.setUploadScopeId(uploadScopeId);
				if (isNew) {
					MultiContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
				} else {
					MultiContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
				}
			} else if (contentClassName.equals(ContentClasses.SimpleContent.name())) {
				StreamedSimpleContentUpdater childContentUpdater = new StreamedSimpleContentUpdater();
				contentUpdater.setBodyUpdater(childContentUpdater);
				DAOContext childContext = new DAOContext(childContentUpdater);
				childContext.setUploadScopeId(uploadScopeId);
				if (isNew) {
					SimpleContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
				} else {
					SimpleContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
				}
			} else if (contentClassName.equals(ContentClasses.OnlineContent.name())) {
				OnlineContentUpdater childContentUpdater = new OnlineContentUpdater();
				contentUpdater.setBodyUpdater(childContentUpdater);
				DAOContext childContext = new DAOContext(childContentUpdater);
				childContext.setUploadScopeId(uploadScopeId);
				if (isNew) {
					OnlineContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
				} else {
					OnlineContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
				}
			} else if (contentClassName.equals(ContentClasses.UnifiedMessage.name())) {
				EmailMessageContentUpdater childContentUpdater = new EmailMessageContentUpdater();
				contentUpdater.setBodyUpdater(childContentUpdater);
				DAOContext childContext = new DAOContext(childContentUpdater);
				childContext.setUploadScopeId(uploadScopeId);
				if (isNew) {
					updateNewObjectState(pojoContentProxy, childContext);
				} else {
					updateObjectState(pojoContentProxy, childContext);
				}
			}
		}
	}

}
