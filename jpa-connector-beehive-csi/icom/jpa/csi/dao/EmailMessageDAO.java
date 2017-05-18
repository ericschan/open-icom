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
import icom.info.StreamInfo;
import icom.info.UnifiedMessageInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.Content;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.EmailMessage;
import oracle.csi.EmailMessageContent;
import oracle.csi.EmailMessageHandle;
import oracle.csi.EmailMessageType;
import oracle.csi.EmailOperationContext;
import oracle.csi.EmailParticipant;
import oracle.csi.EmailRecipient;
import oracle.csi.Entity;
import oracle.csi.HeterogeneousFolderHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.MessageFlag;
import oracle.csi.RawString;
import oracle.csi.SnapshotId;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EmailControl;
import oracle.csi.controls.EmailFactory;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.EmailDraftUpdater;
import oracle.csi.updaters.EmailMessageContentUpdater;
import oracle.csi.updaters.EmailMessageUpdater;
import oracle.csi.updaters.MessageUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class EmailMessageDAO extends MessageDAO implements StreamDAO {

	static EmailMessageDAO singleton = new EmailMessageDAO();
	
	public static EmailMessageDAO getInstance() {
		return singleton;
	}
	
	{
		//basicAttributes.add(MessageInfo.Attributes.content);
		basicAttributes.add(UnifiedMessageInfo.Attributes.userCreationDate);
		basicAttributes.add(UnifiedMessageInfo.Attributes.priority);
		basicAttributes.add(UnifiedMessageInfo.Attributes.toReceivers);
		basicAttributes.add(UnifiedMessageInfo.Attributes.ccReceivers);
		basicAttributes.add(UnifiedMessageInfo.Attributes.bccReceivers);
		basicAttributes.add(UnifiedMessageInfo.Attributes.flags);
		basicAttributes.add(UnifiedMessageInfo.Attributes.editMode);
		basicAttributes.add(UnifiedMessageInfo.Attributes.channel);
		basicAttributes.add(UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested);
		basicAttributes.add(ContentInfo.Attributes.contentId);
		basicAttributes.add(ContentInfo.Attributes.mediaType);
		basicAttributes.add(ContentInfo.Attributes.contentDisposition);
		basicAttributes.add(UnifiedMessageInfo.Attributes.mimeHeaders);
	}

	public enum DispositionNotificationType {
		SEND_DISPOSITION_NOTIFICATION,
		SEND_NOT_READ_DISPOSITION_NOTIFICATION,
	}
	
	public enum MessageMode {
		DraftCopy, 
		DeliveredCopy,
		NewCopy
	}
	
	static HashMap<String, String> csiToPojoFlagNameMap;
	static HashMap<String, String> pojoToCsiFlagNameMap;
	
	{
		csiToPojoFlagNameMap = new HashMap<String, String>();
		pojoToCsiFlagNameMap = new HashMap<String, String>();
		csiToPojoFlagNameMap.put("ANSWERED", "Answered");
		csiToPojoFlagNameMap.put("FORWARDED", "Forwarded");
		csiToPojoFlagNameMap.put("REDIRECTED", "Redirected");
		csiToPojoFlagNameMap.put("HIDDEN", "Hidden");
		csiToPojoFlagNameMap.put("MARKED_DELETE", "MarkedForDelete");
		csiToPojoFlagNameMap.put("MARKED_FOR_FOLLOWUP", "MarkedForFollowup");
		csiToPojoFlagNameMap.put("MARKED_DRAFT", "MarkedForDraft");
		csiToPojoFlagNameMap.put("MDN_PROCESSED", "MessageDispositionNotificationProcessed");
		for (String key : csiToPojoFlagNameMap.keySet()) {
			pojoToCsiFlagNameMap.put(csiToPojoFlagNameMap.get(key), key);
		}
	}
	
	static HashMap<String, String> csiToPojoDNSRequestNameMap;
	static HashMap<String, String> pojoToCsiDNSRequestNameMap;
	
	{
		csiToPojoDNSRequestNameMap = new HashMap<String, String>();
		pojoToCsiDNSRequestNameMap = new HashMap<String, String>();
		csiToPojoDNSRequestNameMap.put("SUCCESS_NOTIFICATION", "Success");
		csiToPojoDNSRequestNameMap.put("FAILURE_NOTIFICATION", "Failure");
		csiToPojoDNSRequestNameMap.put("FAILURE_NOTIFICATION", "Delay");  // TODO in CSI
		csiToPojoDNSRequestNameMap.put("FAILURE_NOTIFICATION", "Never");  // TODO in CSI
		for (String key : csiToPojoDNSRequestNameMap.keySet()) {
			pojoToCsiDNSRequestNameMap.put(csiToPojoDNSRequestNameMap.get(key), key);
		}
	}
	
	static public HashMap<String, String> csiToPojoChannelTypeNameMap;
	static public HashMap<String, String> pojoToCsiChannelTypeNameMap;
	
	{
		csiToPojoChannelTypeNameMap = new HashMap<String, String>();
		pojoToCsiChannelTypeNameMap = new HashMap<String, String>();
		csiToPojoChannelTypeNameMap.put("EMAIL", "Email");
		csiToPojoChannelTypeNameMap.put("VOICEMAIL", "Voice");
		csiToPojoChannelTypeNameMap.put("FAX", "Fax");
		csiToPojoChannelTypeNameMap.put("NOTIFICATION", "Notification");
		for (String key : csiToPojoChannelTypeNameMap.keySet()) {
			pojoToCsiChannelTypeNameMap.put(csiToPojoChannelTypeNameMap.get(key), key);
		}
	}

	protected EmailMessageDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return EmailMessageHandle.class;
	}
	
	public boolean embedAsNonIdentifiableDependent() {
		return true;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		EmailMessage csiEmailMessage = null;
		try {
			EmailControl control = ControlLocator.getInstance().getControl(EmailControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			EmailMessageHandle csiEmailMessageHandle = (EmailMessageHandle) EntityUtils.getInstance().createHandle(id);
			if (proj == Projection.BASIC) {
				csiEmailMessage = control.loadEmailMessage(csiEmailMessageHandle, Projection.FULL);
			} else {
				csiEmailMessage = control.loadEmailMessage(csiEmailMessageHandle, proj);
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiEmailMessage;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		if (csiObject instanceof EmailMessageContent) {
			EmailMessageContentUtil.copyObjectState(obj, csiObject, proj);
			return;
		}
		
		super.copyObjectState(obj, csiObject, proj);
		
		EmailMessage csiMessage = (EmailMessage) csiObject;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.userCreationDate.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.userCreationDate.name(), proj)) {
			try {
				EmailMessageContent csiEmailMessageContent = csiMessage.getContent();
				// sent time is represented by user created on time of artifact
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userCreationDate.name(), csiEmailMessageContent.getSentDate());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (!isPartOfProjection(EntityInfo.Attributes.name.name(), lastLoadedProjection) &&
				isPartOfProjection(EntityInfo.Attributes.name.name(), proj)) {
			try {
				EmailMessageContent csiEmailMessageContent = csiMessage.getContent();
				RawString csiSubject = csiEmailMessageContent.getSubject();
				if (csiSubject != null) {
					String name = csiSubject.toString(); // TODO handle unsupported character set exception
					// subject is represented by name of entity
					assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name(), name);
				} else {
					assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.toReceivers.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.toReceivers.name(),  proj)) {
			try {
				EmailMessageContent csiEmailMessageContent = csiMessage.getContent();
				Collection<EmailRecipient> csiRecipients = csiEmailMessageContent.getTOReceivers();
				Iterator<EmailRecipient> iter = csiRecipients.iterator();
				Vector<Object> v = new Vector<Object>(csiRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient csiEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiEmailRecipient, obj, UnifiedMessageInfo.Attributes.toReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiEmailRecipient, proj);
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, UnifiedMessageInfo.Attributes.toReceivers.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.toReceivers.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.ccReceivers.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.ccReceivers.name(),  proj)) {
			try {
				EmailMessageContent csiEmailMessageContent = csiMessage.getContent();
				Collection<EmailRecipient> csiRecipients = csiEmailMessageContent.getCCReceivers();
				Iterator<EmailRecipient> iter = csiRecipients.iterator();
				Vector<Object> v = new Vector<Object>(csiRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient csiEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiEmailRecipient, obj, UnifiedMessageInfo.Attributes.ccReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiEmailRecipient, proj);
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, UnifiedMessageInfo.Attributes.ccReceivers.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.ccReceivers.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.bccReceivers.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.bccReceivers.name(),  proj)) {
			try {
				EmailMessageContent csiEmailMessageContent = csiMessage.getContent();
				Collection<EmailRecipient> csiRecipients = csiEmailMessageContent.getBCCReceivers();
				Iterator<EmailRecipient> iter = csiRecipients.iterator();
				Vector<Object> v = new Vector<Object>(csiRecipients.size());
				while (iter.hasNext()) {
					EmailRecipient csiEmailRecipient = iter.next();
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiEmailRecipient, obj, UnifiedMessageInfo.Attributes.bccReceivers.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiEmailRecipient, proj);
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, UnifiedMessageInfo.Attributes.bccReceivers.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.bccReceivers.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(MessageInfo.Attributes.sender.name(), lastLoadedProjection) &&
				isPartOfProjection(MessageInfo.Attributes.sender.name(), proj)) {
			try {
				Persistent particiant = null;
				EmailMessageContent csiEmailMessageContent = csiMessage.getContent();
				EmailParticipant csiSender = csiEmailMessageContent.getSender();
				if (csiSender != null) {
					ManagedObjectProxy unifiedMessageParticipantObj = getNonIdentifiableDependentProxy(context, csiSender, obj, MessageInfo.Attributes.sender.name());
					unifiedMessageParticipantObj.getProviderProxy().copyLoadedProjection(unifiedMessageParticipantObj, csiSender, proj);
					particiant = unifiedMessageParticipantObj.getPojoObject();
				}
				
				Object previousPojoObject = AbstractDAO.getAttributeValue(pojoIdentifiable, MessageInfo.Attributes.sender.name());
				if (previousPojoObject != null) {
					BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
					ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
					beanInfo.detachHierarchy(mop);
				}
				
				assignAttributeValue(pojoIdentifiable, MessageInfo.Attributes.sender.name(), particiant);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (/* always copy content !isPartOfProjection(MessageInfo.Attributes.content.name(), lastLoadedProjection) &&*/
				isPartOfProjection(MessageInfo.Attributes.content.name(), proj)) {
			try {
				EmailMessageContent csiEmailMessageContent = csiMessage.getContent();
				Content csiChildContent = csiEmailMessageContent.getBody();
				if (csiChildContent != null) {
					boolean createDependentProxy = true;
					Object pojoContent = getAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name());
					if (pojoContent != null) {
						ManagedObjectProxy contentObj = (ManagedObjectProxy) getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
						if (contentObj != null) {
							StreamInfo subBeanInfo = (StreamInfo) context.getBeanInfo(pojoContent);
							subBeanInfo.detachHierarchy(contentObj);
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
							assignAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name(), contentObj);
							createDependentProxy = false;
						}
					}
					if (createDependentProxy) {
						ManagedObjectProxy contentObj = getNonIdentifiableDependentProxy(context, csiChildContent, obj, MessageInfo.Attributes.content.name());
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
						assignAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name(), contentObj.getPojoObject());
					}
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.flags.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.flags.name(),  proj)) {
			try {
				Collection<String> enumConstantNames = new ArrayList<String>();
				EnumSet<MessageFlag> csiFlags = csiMessage.getFlags();
				if (csiFlags != null) {
					for (Enum<?> csiFlag : csiFlags) {
						String csiFlagName = csiFlag.name();
						String pojoFlagName = csiToPojoFlagNameMap.get(csiFlagName);
						enumConstantNames.add(pojoFlagName);
					}
	 			}
				EnumSet<?> pojoFlags = instantiateEnumSet(BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageFlagEnum.name(), enumConstantNames);
	 			assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.flags.name(), pojoFlags);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.editMode.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.editMode.name(),  proj)) {
			try {
				String modeName = getMessageMode(csiMessage); // draft, delivered (sent or received), other (new)
				assignEnumConstant(pojoIdentifiable, UnifiedMessageInfo.Attributes.editMode.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), modeName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.channel.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.channel.name(),  proj)) {
			try {
				EmailMessageType type = csiMessage.getType();
				String csiChannelName = null;
				if (type != null) {
					csiChannelName = type.name();
				} else {
					csiChannelName = EmailMessageType.EMAIL.name();
				}
				String pojoChannelName = EmailMessageDAO.csiToPojoChannelTypeNameMap.get(csiChannelName);
				assignEnumConstant(pojoIdentifiable, UnifiedMessageInfo.Attributes.channel.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageChannelEnum.name(), pojoChannelName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (!isPartOfProjection(UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(), lastLoadedProjection) &&
				isPartOfProjection(UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(),  proj)) {
			try {
				boolean receiptRequested = csiMessage.isReceiptRequested();
				assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(), new Boolean(receiptRequested));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}

	String getMessageMode(EmailMessage csiMessage) {
		String modeName = null;
		if (csiMessage.isModifiable()) {
			modeName = MessageMode.DraftCopy.name();
		} else {
			modeName = MessageMode.DeliveredCopy.name();
		}
		return modeName;
	}
	
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object key) {
		super.loadAndCopyObjectState(obj, attributeName, key);
		
		//oracle.bom.ejb.spi.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();

	}
	
	private void updateEmailMessageContentState(ManagedIdentifiableProxy obj, DAOContext context) {
		//if (isChanged(obj, MessageInfo.Attributes.content.name())) {	
		if (obj.isNew()) {
			EmailMessageContentUtil.updateNewObjectState(obj, context);
		} else {
			EmailMessageContentUtil.updateObjectState(obj, context);
		}
		//}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		MessageUpdater messageUpdater = (MessageUpdater) context.getUpdater();
		Persistent pojoUnifiedMessage = obj.getPojoIdentifiable();
		
		/*
		if (isChanged(obj, UnifiedMessageInfo.Attributes.subject.name())) {
			String subject = (String) getAttributeValue(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.subject.name());
			messageUpdater.setName(subject);
		}
		*/
		
		if (messageUpdater instanceof EmailMessageUpdater) {
			EmailMessageUpdater emailMessageUpdater = (EmailMessageUpdater) messageUpdater;
			
			if (isChanged(obj, UnifiedMessageInfo.Attributes.flags.name())) {
				EnumSet<MessageFlag> csiMessageFlags = EnumSet.noneOf(MessageFlag.class);
	 			EnumSet<? extends Enum<?>> pojoFlags = getEnumSet(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.flags.name());
				if (pojoFlags != null) {
		 			for (Enum<?> pojoFlag : pojoFlags) {
						String pojoFlagName = pojoFlag.name();
						String csiFlagName = pojoToCsiFlagNameMap.get(pojoFlagName);
						csiMessageFlags.add(MessageFlag.valueOf(csiFlagName));
					}
				}
				emailMessageUpdater.setFlags(csiMessageFlags);
			}
			// emailMessageUpdater.getContentTrimmer() TODO
		} else if (messageUpdater instanceof EmailDraftUpdater) {
			EmailDraftUpdater emailDraftUpdater = (EmailDraftUpdater) messageUpdater;
			emailDraftUpdater.setType(EmailMessageType.EMAIL);
			
			if (isChanged(obj, UnifiedMessageInfo.Attributes.flags.name())) {
				EnumSet<MessageFlag> csiMessageFlags = EnumSet.noneOf(MessageFlag.class);
	 			EnumSet<? extends Enum<?>> pojoFlags = getEnumSet(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.flags.name());
	 			if (pojoFlags != null) {
					for (Enum<?> pojoFlag : pojoFlags) {
						String pojoFlagName = pojoFlag.name();
						String csiFlagName = pojoToCsiFlagNameMap.get(pojoFlagName);
						csiMessageFlags.add(MessageFlag.valueOf(csiFlagName));
					}
					emailDraftUpdater.setFlags(csiMessageFlags);
	 			}
			}
			
			if (isChanged(obj, UnifiedMessageInfo.Attributes.channel.name())) {
				String pojoChannelName = EmailMessageDAO.getEnumName(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.channel.name());
				if (pojoChannelName != null) {
					String csiChannelName = EmailMessageDAO.pojoToCsiChannelTypeNameMap.get(pojoChannelName);
					emailDraftUpdater.setType(EmailMessageType.valueOf(csiChannelName));
				} else {
					emailDraftUpdater.setType(EmailMessageType.EMAIL);
				}
			}
			
			EmailMessageContentUpdater emailMessageContentUpdater = emailDraftUpdater.getContentUpdater();
			DAOContext childContext = new DAOContext(emailMessageContentUpdater);
			updateEmailMessageContentState(obj, childContext);
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		Persistent pojoUnifiedMessage = obj.getPojoIdentifiable();
		String modeName = getEnumName(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.editMode.name());
		boolean draft = MessageMode.DraftCopy.name().equalsIgnoreCase(modeName);
		DAOContext context = null;
		if (draft) {
			EmailDraftUpdater emailDraftUpdater = EmailFactory.getInstance().createEmailDraftUpdater();
			context = new DAOContext(emailDraftUpdater);
			EmailMessageContentUpdater contentUpdater = emailDraftUpdater.getContentUpdater();
			context.setChildUpdater(contentUpdater);
		} else {
			EmailMessageUpdater emailMessageUpdater = EmailFactory.getInstance().createEmailMessageUpdater();
			context = new DAOContext(emailMessageUpdater);
		}
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		EmailControl control = ControlLocator.getInstance().getControl(EmailControl.class);
		MessageUpdater messageUpdater = (MessageUpdater) context.getUpdater();
		EmailMessage message = null;
		if (messageUpdater instanceof EmailMessageUpdater) {
			try {
				CollabId id = getCollabId(obj.getObjectId());
				EmailMessageHandle handle = (EmailMessageHandle) EntityUtils.getInstance().createHandle(id);
				Object changeToken = obj.getChangeToken();
				UpdateMode updateMode = null;
				if (changeToken != null) {
					SnapshotId sid = getSnapshotId(changeToken);
					updateMode = UpdateMode.optimisticLocking(sid);
				} else {
					updateMode = UpdateMode.alwaysUpdate();
				}
				message = control.updateMessage(handle, (EmailMessageUpdater) messageUpdater, updateMode);
			} catch (CsiException ex) {
				throw new PersistenceException(ex); 
			}
		} else if (messageUpdater instanceof EmailDraftUpdater) {
			try {
				EmailDraftUpdater emailDraftUpdater = (EmailDraftUpdater) messageUpdater;
				CollabId id = getCollabId(obj.getObjectId());
				Object changeToken = obj.getChangeToken();
				UpdateMode updateMode = null;
				if (changeToken != null) {
					SnapshotId sid = getSnapshotId(changeToken);
					updateMode = UpdateMode.optimisticLocking(sid);
				} else {
					updateMode = UpdateMode.alwaysUpdate();
				}
				EmailMessageHandle handle = (EmailMessageHandle) EntityUtils.getInstance().createHandle(id);
				EmailOperationContext opContext = control.beginUpdateDraft(handle, emailDraftUpdater, updateMode);
				flushToOutputStream(obj, opContext);
				//message = control.updateDraft(opContext.getContextId());
				if (proj == Projection.BASIC) {
					message = control.commitOperation(opContext.getContextId(), Projection.FULL);
				} else {
					message = control.commitOperation(opContext.getContextId(), proj);
				}
				assignEnumConstant(obj.getPojoObject(), UnifiedMessageInfo.Attributes.editMode.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), MessageMode.DraftCopy.name());
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
		}
		try {
			SnapshotId sid = message.getSnapshotId();
			if (sid != null) {
				icom.jpa.Identifiable pojoUnifiedMessage = obj.getPojoIdentifiable();
				assignChangeToken(pojoUnifiedMessage, sid.toString());
			}
		} catch (CsiRuntimeException ex) {
			
		}
		return message;
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		EmailDraftUpdater emailDraftUpdater = EmailFactory.getInstance().createEmailDraftUpdater();
		DAOContext context = new DAOContext(emailDraftUpdater);
		EmailMessageContentUpdater contentUpdater = emailDraftUpdater.getContentUpdater();
		context.setChildUpdater(contentUpdater);
		return context;
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		EmailControl control = ControlLocator.getInstance().getControl(EmailControl.class);
		EmailMessage message = null;
		try {
			Persistent pojoUnifiedMessage = obj.getPojoIdentifiable();
			Persistent pojoParentFolder = getParent(pojoUnifiedMessage);
			if (pojoParentFolder == null) {
				throw new PersistenceException("Must specify parent folder for draft message"); 
			}
			CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(pojoParentFolder.getManagedObjectProxy())).getObjectId());
			HeterogeneousFolderHandle parentFolderHandle = (HeterogeneousFolderHandle) EntityUtils.getInstance().createHandle(parentId);
			EmailDraftUpdater emailDraftUpdater = (EmailDraftUpdater) context.getUpdater();
			CollabId id = getCollabId(obj.getObjectId());
			EmailOperationContext opContext = control.beginCreateMessage(id.getEid(), parentFolderHandle, emailDraftUpdater);
			
			flushToOutputStream(obj, opContext);
			
			if (proj == Projection.BASIC) {
				message = control.commitOperation(opContext.getContextId(), Projection.FULL);
			} else {
				message = control.commitOperation(opContext.getContextId(), proj);
			}
			assignEnumConstant(obj.getPojoObject(), UnifiedMessageInfo.Attributes.editMode.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), MessageMode.DraftCopy.name());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		try {
			SnapshotId sid = message.getSnapshotId();
			if (sid != null) {
				icom.jpa.Identifiable pojoUnifiedMessage = obj.getPojoIdentifiable();
				assignChangeToken(pojoUnifiedMessage, sid.toString());
			}
		} catch (CsiRuntimeException ex) {
			
		}
		return message;
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		EmailControl control = ControlLocator.getInstance().getControl(EmailControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		EmailMessageHandle handle = (EmailMessageHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteMessage(handle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void sendEmail(ManagedIdentifiableProxy obj, HeterogeneousFolderHandle sentFolderHandle) {
		EmailControl control = ControlLocator.getInstance().getControl(EmailControl.class);
		if (obj.isDirty()) {
			EmailDraftUpdater emailDraftUpdater = EmailFactory.getInstance().createEmailDraftUpdater();
			DAOContext context = new DAOContext(emailDraftUpdater);
			EmailMessageContentUpdater emailMessageContentUpdater = emailDraftUpdater.getContentUpdater();
			context.setChildUpdater(emailMessageContentUpdater);
			updateNewObjectState(obj, context);
			CollabId id = getCollabId(obj.getObjectId());
			EmailMessageHandle messageHandle = (EmailMessageHandle) EntityUtils.getInstance().createHandle(id);
			EmailOperationContext opContext = null;
			try {
				if (sentFolderHandle != null) {
					Eid eid = id.getEid();
					opContext = control.beginSendDraft(eid, sentFolderHandle, emailDraftUpdater, messageHandle);
				} else {
					opContext = control.beginSendDraft(emailDraftUpdater, messageHandle);
				}
				flushToOutputStream(obj, opContext);
				control.sendDraft(opContext.getContextId());
				assignEnumConstant(obj.getPojoObject(), UnifiedMessageInfo.Attributes.editMode.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), MessageMode.DeliveredCopy.name());
				obj.resetReady();
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
		} else if (obj.isNew()) {
			EmailMessageContentUpdater emailMessageContentUpdater = EmailFactory.getInstance().createEmailMessageContentUpdater();
			DAOContext context = new DAOContext(emailMessageContentUpdater);
			updateEmailMessageContentState(obj, context);
			EmailOperationContext opContext = null;
			try {
				if (sentFolderHandle != null) {
					CollabId id = getCollabId(obj.getObjectId());
					Eid eid = id.getEid();
					opContext = control.beginSend(eid, sentFolderHandle, emailMessageContentUpdater);
				} else {
					opContext = control.beginSend(emailMessageContentUpdater);
				}
				flushToOutputStream(obj, opContext);
				control.send(opContext.getContextId());
				assignEnumConstant(obj.getPojoObject(), UnifiedMessageInfo.Attributes.editMode.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), MessageMode.DeliveredCopy.name());
				obj.resetReady();
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
		}
	}
	
	public EmailMessage sendDispositionNotification(ManagedIdentifiableProxy obj, DispositionNotificationType returnReceipt) {
		EmailMessageUpdater emailMessageUpdater = EmailFactory.getInstance().createEmailMessageUpdater();
		if (returnReceipt != null) {
			if (returnReceipt.equals(DispositionNotificationType.SEND_DISPOSITION_NOTIFICATION)) {
				emailMessageUpdater.setSendReturnReceipt(true);
			} else if (returnReceipt.equals(DispositionNotificationType.SEND_NOT_READ_DISPOSITION_NOTIFICATION)) {
				emailMessageUpdater.setSendNotReadReturnReceipt(true);
			}
			EmailControl control = ControlLocator.getInstance().getControl(EmailControl.class);
			try {
				CollabId id = getCollabId(obj.getObjectId());
				EmailMessageHandle handle = (EmailMessageHandle) EntityUtils.getInstance().createHandle(id);
				Object changeToken = obj.getChangeToken();
				UpdateMode updateMode = null;
				if (changeToken != null) {
					SnapshotId sid = getSnapshotId(changeToken);
					updateMode = UpdateMode.optimisticLocking(sid);
				} else {
					updateMode = UpdateMode.alwaysUpdate();
				}
				EmailMessage message = control.updateMessage(handle, emailMessageUpdater, updateMode);
				Persistent pojoUnifiedMessage = obj.getPojoIdentifiable();
				String pojoFlagName = csiToPojoFlagNameMap.get("MDN_PROCESSED");
				addEnumToEnumSet(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.flags.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageFlagEnum.name(), pojoFlagName);
				return message;
			} catch (CsiException ex) {
				throw new PersistenceException(ex); 
			}
			
		} else {
			throw new RuntimeException("Invalid return receipt directive ");
		}
	}

	public Persistent getContent(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name());
	}
	
	public void flushToOutputStream(ManagedObjectProxy obj, EmailOperationContext opContext) {
		Persistent pojoUnifiedMessage = obj.getPojoObject();
		Persistent pojoContent = getContent(pojoUnifiedMessage);
		if (pojoContent != null) {
			StreamDAO dao = (StreamDAO) obj.getPersistenceContext().getDataAccessObject(pojoContent, pojoUnifiedMessage, MessageInfo.Attributes.content.name());
			dao.flushToOutputStream(pojoContent.getManagedObjectProxy(), opContext);
		}
	}
	
}
