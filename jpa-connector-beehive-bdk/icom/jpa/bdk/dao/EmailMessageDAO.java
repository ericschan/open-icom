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


import com.oracle.beehive.BeeId;
import com.oracle.beehive.Content;
import com.oracle.beehive.EmailDraftUpdater;
import com.oracle.beehive.EmailMessage;
import com.oracle.beehive.EmailMessageContent;
import com.oracle.beehive.EmailMessageContentUpdater;
import com.oracle.beehive.EmailMessageType;
import com.oracle.beehive.EmailMessageUpdater;
import com.oracle.beehive.EmailParticipant;
import com.oracle.beehive.EmailRecipient;
import com.oracle.beehive.Entity;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.EntityUpdater;
import com.oracle.beehive.MessageFlag;
import com.oracle.beehive.MessageUpdater;
import com.oracle.beehive.RawString;

import icom.info.AbstractBeanInfo;
import icom.info.ArtifactInfo;
import icom.info.BeanHandler;
import icom.info.ContentInfo;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.MessageInfo;
import icom.info.StreamInfo;
import icom.info.UnifiedMessageInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.persistence.PersistenceException;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;


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
		basicAttributes.add(UnifiedMessageInfo.Attributes.size);
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
	
	static HashMap<String, String> bdkToPojoFlagNameMap;
	static HashMap<String, String> pojoToBdkFlagNameMap;
	
	{
		bdkToPojoFlagNameMap = new HashMap<String, String>();
		pojoToBdkFlagNameMap = new HashMap<String, String>();
		bdkToPojoFlagNameMap.put("ANSWERED", "Answered");
		bdkToPojoFlagNameMap.put("FORWARDED", "Forwarded");
		bdkToPojoFlagNameMap.put("REDIRECTED", "Redirected");
		bdkToPojoFlagNameMap.put("HIDDEN", "Hidden");
		bdkToPojoFlagNameMap.put("MARKED_DELETE", "MarkedForDelete");
		bdkToPojoFlagNameMap.put("MARKED_FOR_FOLLOWUP", "MarkedForFollowup");
		bdkToPojoFlagNameMap.put("MARKED_DRAFT", "MarkedForDraft");
		bdkToPojoFlagNameMap.put("MDN_PROCESSED", "MessageDispositionNotificationProcessed");
		for (String key : bdkToPojoFlagNameMap.keySet()) {
			pojoToBdkFlagNameMap.put(bdkToPojoFlagNameMap.get(key), key);
		}
	}
	
	static HashMap<String, String> bdkToPojoDNSRequestNameMap;
	static HashMap<String, String> pojoToBdkDNSRequestNameMap;
	
	{
		bdkToPojoDNSRequestNameMap = new HashMap<String, String>();
		pojoToBdkDNSRequestNameMap = new HashMap<String, String>();
		bdkToPojoDNSRequestNameMap.put("SUCCESS_NOTIFICATION", "Success");
		bdkToPojoDNSRequestNameMap.put("FAILURE_NOTIFICATION", "Failure");
		bdkToPojoDNSRequestNameMap.put("FAILURE_NOTIFICATION", "Delay");  // TODO in CSI
		bdkToPojoDNSRequestNameMap.put("FAILURE_NOTIFICATION", "Never");  // TODO in CSI
		for (String key : bdkToPojoDNSRequestNameMap.keySet()) {
			pojoToBdkDNSRequestNameMap.put(bdkToPojoDNSRequestNameMap.get(key), key);
		}
	}
	
	static public HashMap<String, String> bdkToPojoChannelTypeNameMap;
	static public HashMap<String, String> pojoToBdkChannelTypeNameMap;
	
	{
		bdkToPojoChannelTypeNameMap = new HashMap<String, String>();
		pojoToBdkChannelTypeNameMap = new HashMap<String, String>();
		bdkToPojoChannelTypeNameMap.put("EMAIL", "Email");
		bdkToPojoChannelTypeNameMap.put("VOICEMAIL", "Voice");
		bdkToPojoChannelTypeNameMap.put("FAX", "Fax");
		bdkToPojoChannelTypeNameMap.put("NOTIFY_OTHERS", "Notification");
		for (String key : bdkToPojoChannelTypeNameMap.keySet()) {
			pojoToBdkChannelTypeNameMap.put(bdkToPojoChannelTypeNameMap.get(key), key);
		}
	}

	protected EmailMessageDAO() {
	}
	
	public String getResourceType() {
		return "emsg";
	}
	
	public boolean embedAsNonIdentifiableDependent() {
		return true;
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
        if (bdkObject instanceof EmailMessageContent) {
            EmailMessageContentUtil.copyObjectState(obj, bdkObject, proj);
            return;
        }

        super.copyObjectState(obj, bdkObject, proj);

        EmailMessage bdkMessage = (EmailMessage)bdkObject;
        Persistent pojoIdentifiable = obj.getPojoObject();
        PersistenceContext context = obj.getPersistenceContext();
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(UnifiedMessageInfo.Attributes.userCreationDate.name(), lastLoadedProjection, proj)) {
            try {
                EmailMessageContent bdkEmailMessageContent = bdkMessage.getContent();
                // sent time is represented by user created on time of artifact
                if (bdkEmailMessageContent != null) {
                    assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.userCreationDate.name(),
                                         bdkEmailMessageContent.getSentDate());
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(EntityInfo.Attributes.name.name(), lastLoadedProjection, proj)) {
            try {
                EmailMessageContent bdkEmailMessageContent = bdkMessage.getContent();
                RawString bdkSubject = bdkEmailMessageContent.getSubject();
                if (bdkSubject != null) {
                    String name = bdkSubject.getString(); // TODO handle unsupported character set exception
                    // subject is represented by name of entity
                    assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name(), name);
                } else {
                    assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name(), null);
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(UnifiedMessageInfo.Attributes.toReceivers.name(), lastLoadedProjection, proj)) {
            try {
                EmailMessageContent bdkEmailMessageContent = bdkMessage.getContent();
                Collection<EmailRecipient> bdkRecipients = bdkEmailMessageContent.getTOReceivers();
                marshallAssignEmbeddableObjects(obj, UnifiedMessageInfo.Attributes.toReceivers.name(), bdkRecipients,
                                         Vector.class, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(UnifiedMessageInfo.Attributes.ccReceivers.name(), lastLoadedProjection, proj)) {
            try {
                EmailMessageContent bdkEmailMessageContent = bdkMessage.getContent();
                Collection<EmailRecipient> bdkRecipients = bdkEmailMessageContent.getCCReceivers();
                marshallAssignEmbeddableObjects(obj, UnifiedMessageInfo.Attributes.ccReceivers.name(), bdkRecipients,
                                         Vector.class, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(UnifiedMessageInfo.Attributes.bccReceivers.name(), lastLoadedProjection, proj)) {
            try {
                EmailMessageContent bdkEmailMessageContent = bdkMessage.getContent();
                Collection<EmailRecipient> bdkRecipients = bdkEmailMessageContent.getBCCReceivers();
                marshallAssignEmbeddableObjects(obj, UnifiedMessageInfo.Attributes.bccReceivers.name(), bdkRecipients,
                                         Vector.class, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(MessageInfo.Attributes.sender.name(), lastLoadedProjection, proj)) {
            try {
                EmailMessageContent bdkEmailMessageContent = bdkMessage.getContent();
                EmailParticipant bdkSender = bdkEmailMessageContent.getSender();
                marshallAssignEmbeddableObject(obj, MessageInfo.Attributes.sender.name(), bdkSender, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if ( /* always copy content !isPartOfProjection(MessageInfo.Attributes.content.name(), lastLoadedProjection) &&*/
            isPartOfProjection(MessageInfo.Attributes.content.name(), proj)) {
            try {
                EmailMessageContent bdkEmailMessageContent = bdkMessage.getContent();
                Content bdkChildContent = bdkEmailMessageContent.getBody();
                if (bdkChildContent != null) {
                    boolean createDependentProxy = true;
                    Object pojoContent = getAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name());
                    if (pojoContent != null) {
                        ManagedObjectProxy contentObj =
                            (ManagedObjectProxy)getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
                        if (contentObj != null) {
                            StreamInfo subBeanInfo = (StreamInfo)context.getBeanInfo(pojoContent);
                            subBeanInfo.detachHierarchy(contentObj);
                            contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
                            assignAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name(), contentObj);
                            createDependentProxy = false;
                        }
                    }
                    if (createDependentProxy) {
                        ManagedObjectProxy contentObj =
                            getNonIdentifiableDependentProxy(context, bdkChildContent, obj, MessageInfo.Attributes.content.name());
                        contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
                        assignAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name(),
                                             contentObj.getPojoObject());
                    }
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (!isPartOfProjection(UnifiedMessageInfo.Attributes.flags.name(), lastLoadedProjection) &&
            isPartOfProjection(UnifiedMessageInfo.Attributes.flags.name(), proj)) {
            try {
                Collection<String> enumConstantNames = new ArrayList<String>();
                List<MessageFlag> bdkFlags = bdkMessage.getFlags();
                if (bdkFlags != null) {
                    for (Enum<?> bdkFlag : bdkFlags) {
                        String bdkFlagName = bdkFlag.name();
                        String pojoFlagName = bdkToPojoFlagNameMap.get(bdkFlagName);
                        enumConstantNames.add(pojoFlagName);
                    }
                }
                EnumSet<?> pojoFlags =
                    instantiateEnumSet(BeanHandler.getBeanPackageName(), IcomBeanEnumeration.UnifiedMessageFlagEnum.name(),
                                       enumConstantNames);
                assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.flags.name(), pojoFlags);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (!isPartOfProjection(UnifiedMessageInfo.Attributes.editMode.name(), lastLoadedProjection) &&
            isPartOfProjection(UnifiedMessageInfo.Attributes.editMode.name(), proj)) {
            try {
                String modeName = getMessageMode(bdkMessage); // draft, delivered (sent or received), other (new)
                assignEnumConstant(pojoIdentifiable, UnifiedMessageInfo.Attributes.editMode.name(),
                                   BeanHandler.getBeanPackageName(),
                                   IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), modeName);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (!isPartOfProjection(UnifiedMessageInfo.Attributes.channel.name(), lastLoadedProjection) &&
            isPartOfProjection(UnifiedMessageInfo.Attributes.channel.name(), proj)) {
            try {
                EmailMessageType type = bdkMessage.getType();
                String bdkChannelName = null;
                if (type != null) {
                    bdkChannelName = type.name();
                } else {
                    bdkChannelName = EmailMessageType.EMAIL.name();
                }
                String pojoChannelName = EmailMessageDAO.bdkToPojoChannelTypeNameMap.get(bdkChannelName);
                assignEnumConstant(pojoIdentifiable, UnifiedMessageInfo.Attributes.channel.name(),
                                   BeanHandler.getBeanPackageName(),
                                   IcomBeanEnumeration.UnifiedMessageChannelEnum.name(), pojoChannelName);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (!isPartOfProjection(UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(),
                                lastLoadedProjection) &&
            isPartOfProjection(UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(), proj)) {
            try {
                /*
                boolean receiptRequested = bdkMessage.isReceiptRequested();
                assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.messageDispositionNotificationRequested.name(), new Boolean(receiptRequested));
                */
            } catch (Exception ex) {
                // ignore
            }
        }

        if (!isPartOfProjection(UnifiedMessageInfo.Attributes.size.name(), lastLoadedProjection) &&
            isPartOfProjection(UnifiedMessageInfo.Attributes.size.name(), proj)) {
            try {
                long size = bdkMessage.getSize();
                assignAttributeValue(pojoIdentifiable, UnifiedMessageInfo.Attributes.size.name(), new Long(size));
            } catch (Exception ex) {
                // ignore
            }
        }

    }

	String getMessageMode(EmailMessage bdkMessage) {
		String modeName = MessageMode.NewCopy.name();
		/*
		if (bdkMessage.isModifiable()) {
			modeName = MessageMode.DraftCopy.name();
		} else {
			modeName = MessageMode.DeliveredCopy.name();
		}
		*/
		return modeName;
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object key) {
		super.loadAndCopyObjectState(obj, attributeName, key);
	}
	
	private void updateEmailMessageContentState(ManagedIdentifiableProxy obj, DAOContext context) {
		//if (isChanged(obj, MessageInfo.Attributes.content.name())) {
		String uploadScopeId = generateUUID();
		context.setUploadScopeId(uploadScopeId);
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
				EnumSet<MessageFlag> bdkMessageFlags = EnumSet.noneOf(MessageFlag.class);
	 			EnumSet<? extends Enum<?>> pojoFlags = getEnumSet(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.flags.name());
				if (pojoFlags != null) {
		 			for (Enum<?> pojoFlag : pojoFlags) {
						String pojoFlagName = pojoFlag.name();
						String bdkFlagName = pojoToBdkFlagNameMap.get(pojoFlagName);
						bdkMessageFlags.add(MessageFlag.valueOf(bdkFlagName));
					}
				}
				emailMessageUpdater.getFlags().addAll(bdkMessageFlags);
			}
			// emailMessageUpdater.getContentTrimmer() TODO
		} else if (messageUpdater instanceof EmailDraftUpdater) {
			EmailDraftUpdater emailDraftUpdater = (EmailDraftUpdater) messageUpdater;
			emailDraftUpdater.setType(EmailMessageType.EMAIL);
			
			if (isChanged(obj, UnifiedMessageInfo.Attributes.flags.name())) {
				EnumSet<MessageFlag> bdkMessageFlags = EnumSet.noneOf(MessageFlag.class);
	 			EnumSet<? extends Enum<?>> pojoFlags = getEnumSet(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.flags.name());
	 			if (pojoFlags != null) {
					for (Enum<?> pojoFlag : pojoFlags) {
						String pojoFlagName = pojoFlag.name();
						String bdkFlagName = pojoToBdkFlagNameMap.get(pojoFlagName);
						bdkMessageFlags.add(MessageFlag.valueOf(bdkFlagName));
					}
					emailDraftUpdater.getFlags().addAll(bdkMessageFlags);
	 			}
			}
			
			if (isChanged(obj, UnifiedMessageInfo.Attributes.channel.name())) {
				String pojoChannelName = EmailMessageDAO.getEnumName(pojoUnifiedMessage, UnifiedMessageInfo.Attributes.channel.name());
				if (pojoChannelName != null) {
					String bdkChannelName = EmailMessageDAO.pojoToBdkChannelTypeNameMap.get(pojoChannelName);
					emailDraftUpdater.setType(EmailMessageType.valueOf(bdkChannelName));
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
		boolean isDraft = MessageMode.DraftCopy.name().equalsIgnoreCase(modeName);
		DAOContext context = null;
		if (isDraft) {
			EmailDraftUpdater emailDraftUpdater = new EmailDraftUpdater();
			context = new DAOContext(emailDraftUpdater);
			EmailMessageContentUpdater contentUpdater = new EmailMessageContentUpdater();
			emailDraftUpdater.setContentUpdater(contentUpdater);
			context.setChildUpdater(contentUpdater);
		} else {
			EmailMessageUpdater emailMessageUpdater = new EmailMessageUpdater();
			context = new DAOContext(emailMessageUpdater);
		}
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		EntityUpdater updater = (EntityUpdater) context.getUpdater();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String resourceType = id.getResourceType();
			PutMethod putMethod = null;
			if (updater instanceof EmailMessageUpdater) {
				String collabId = id.getId();
				Object changeToken = obj.getChangeToken();
				String snapshotId = changeToken.toString();
				String params = "snapshotid=" + snapshotId;
				putMethod = preparePutMethod(resourceType, collabId, userContext.antiCSRF, proj, params);
			} else if (updater instanceof EmailDraftUpdater) {
				//  Not supported
			}
			Entity bdkEntity = (Entity) bdkHttpUtil.execute(getBdkClass(obj), putMethod, updater, userContext.httpClient);
			if (proj != Projection.EMPTY) {
				String changeToken = bdkEntity.getSnapshotId();
				assignChangeToken(obj.getPojoIdentifiable(), changeToken);
			}
			return bdkEntity;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		EmailDraftUpdater updater = new EmailDraftUpdater();
		DAOContext context = new DAOContext(updater);
		EmailMessageContentUpdater contentUpdater = new EmailMessageContentUpdater();
		context.setChildUpdater(contentUpdater);
		// TODO EmailDraftCreator
		return context;
	}
	
	protected String getCreateObjectParameters(ManagedIdentifiableProxy obj, DAOContext context) {
		return null;
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		EmailDraftUpdater emailDraftUpdater = (EmailDraftUpdater) context.getUpdater();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String resourceType = id.getResourceType();
			resourceType += "/append";
			Persistent pojoUnifiedMessage = obj.getPojoIdentifiable();
			Persistent pojoParentFolder = getParent(pojoUnifiedMessage);
			if (pojoParentFolder == null) {
				throw new PersistenceException("Must specify parent folder for draft message"); 
			}
			BeeId folderId = getBeeId(((ManagedIdentifiableProxy)(pojoParentFolder.getManagedObjectProxy())).getObjectId().toString());
			String params = "folder=" + folderId;
			Date receivedDate = (Date) getAttributeValue(pojoUnifiedMessage, MessageInfo.Attributes.deliveredTime.name());
			if (receivedDate != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				String formatedDate = format.format(receivedDate);
				params += "&received_date=" + formatedDate;
			}
			String uploadScopeId = context.getUploadScopeId();
			if (uploadScopeId != null) {
				params += "&uploadscope=scope" + uploadScopeId;
			}
			PostMethod postMethod = preparePostMethod(resourceType, userContext.antiCSRF, proj, params);
			Entity bdkEntity = (Entity) bdkHttpUtil.execute(getBdkClass(obj), postMethod, emailDraftUpdater, userContext.httpClient);
			String objectId = bdkEntity.getCollabId().getId();
			obj.setObjectId(objectId);
			assignObjectId(obj.getPojoIdentifiable(), objectId);
			if (proj != Projection.EMPTY) {
				String changeToken = bdkEntity.getSnapshotId();
				assignChangeToken(obj.getPojoIdentifiable(), changeToken);
			}
			assignEnumConstant(obj.getPojoObject(), UnifiedMessageInfo.Attributes.editMode.name(), BeanHandler.getBeanPackageName(),
				IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), MessageMode.DraftCopy.name());
			// re-cache the object with the server assigned id
			// the server may assign a new object id rather than use the client assigned id
			obj.getPersistenceContext().recacheIdentifiableDependent(obj);  
			return bdkEntity;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void sendEmail(ManagedIdentifiableProxy obj, BeeId sentFolderId) {
		String resourceType = getResourceType();
		resourceType += "/send";
		if (obj != null && obj.isDirty()) {
			EmailDraftUpdater emailDraftUpdater = new EmailDraftUpdater();
			DAOContext context = new DAOContext(emailDraftUpdater);
			EmailMessageContentUpdater emailMessageContentUpdater = new EmailMessageContentUpdater();
			emailDraftUpdater.setContentUpdater(emailMessageContentUpdater);
			context.setChildUpdater(emailMessageContentUpdater);
			updateObjectState(obj, context);
			String params = null;
			String uploadScopeId = context.getUploadScopeId();
			if (uploadScopeId != null) {
				params = "uploadscope=scope" + uploadScopeId;
			}
			if (sentFolderId != null) {
				if (params == null) {
					params += "sent_folder=" + sentFolderId.getId();
				} else {
					params += "&sent_folder=" + sentFolderId.getId();
				}
			}
			try {
				BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
				PostMethod postMethod = preparePostMethod(resourceType, userContext.antiCSRF, params);
				bdkHttpUtil.execute(postMethod, emailDraftUpdater, userContext.httpClient);
			} catch (Exception ex) {
				throw new PersistenceException(ex);
			}
		} else if (obj != null && obj.isNew()) {
			EmailMessageContentUpdater emailMessageContentUpdater = new EmailMessageContentUpdater();
			DAOContext context = new DAOContext(emailMessageContentUpdater);
			updateEmailMessageContentState(obj, context);
			String params = null;
			String uploadScopeId = context.getUploadScopeId();
			if (uploadScopeId != null) {
				params = "uploadscope=scope" + uploadScopeId;
			}
			if (sentFolderId != null) {
				if (params == null) {
					params += "sent_folder=" + sentFolderId.getId();
				} else {
					params += "&sent_folder=" + sentFolderId.getId();
				}
			}
			try {	
				BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
				PostMethod postMethod = preparePostMethod(resourceType, userContext.antiCSRF, params);
				bdkHttpUtil.execute(postMethod, emailMessageContentUpdater, userContext.httpClient);
			} catch (Exception ex) {
				throw new PersistenceException(ex);
			}
		} else if (obj == null) {
		    throw new PersistenceException("Message must have manged proxy");
		}
		assignEnumConstant(obj.getPojoObject(), UnifiedMessageInfo.Attributes.editMode.name(), BeanHandler.getBeanPackageName(),
				IcomBeanEnumeration.UnifiedMessageEditModeEnum.name(), MessageMode.DeliveredCopy.name());
		obj.resetReady();
	}
	
	public EmailMessage sendDispositionNotification(ManagedIdentifiableProxy obj, DispositionNotificationType returnReceipt) {
		// Not supported in BDK
		return null;
	}

	public Persistent getContent(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, MessageInfo.Attributes.content.name());
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return null;
	}
	
	protected MessageUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return null;
	}
	
	protected MessageUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		return null;
	}
	
	protected EntityCreator getBdkCreator(ManagedObjectProxy obj) {
		return null;
	}
	
}
