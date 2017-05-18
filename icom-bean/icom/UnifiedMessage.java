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
package icom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="UnifiedMessage", namespace="http://docs.oasis-open.org/ns/icom/message/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/message/201008")
public class UnifiedMessage extends Message implements MimeConvertible, Parental {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier envelopeSender
	 * @associates Participant
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	Participant envelopeSender;
	
	/**
	 * @$clientQualifier toReceiver
	 * @associates UnifiedMessageParticipant
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="toReceiver")
	Collection<UnifiedMessageParticipant> toReceivers;
	
	/**
	 * @$clientQualifier ccReceiver
	 * @associates UnifiedMessageParticipant
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="ccReceiver")
	Collection<UnifiedMessageParticipant> ccReceivers;
	
	/**
	 * @$clientQualifier bccReceiver
	 * @associates UnifiedMessageParticipant
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="bccReceiver")
	Collection<UnifiedMessageParticipant> bccReceivers;
	
	/**
	 * @$clientQualifier replyTo
	 * @associates UnifiedMessageParticipant
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="replyTo")
	Collection<UnifiedMessageParticipant> replyTo;
	
	// Date sentTime is mapped to userCreatedOn
	
	/**
	 * @$clientQualifier priority
	 * @associates Priority
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=PriorityEnum.class, namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Priority priority;
	
	/**
	 * @$clientQualifier flag
	 * @associates UnifiedMessageFlag
	 * @link aggregationByValue
	 */
	@XmlElement(name="flag")
	EnumSet<UnifiedMessageFlagEnum> flags;
	
	/**
	 * @$clientQualifier channel
	 * @associates UnifiedMessageChannel
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=UnifiedMessageChannelEnum.class)
	UnifiedMessageChannel channel;
	
	/**
	 * @$clientQualifier editMode
	 * @associates UnifiedMessageMode
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=UnifiedMessageEditModeEnum.class)
	UnifiedMessageEditMode editMode;
	
	// The following three attributes are for MIME encapsulated UnifiedMessage
	String contentId;
	
	String mediaType;
	
	/**
	 * @$clientQualifier contentDisposition
	 * @associates ContentDispositionType
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=ContentDispositionTypeEnum.class)
	ContentDispositionType contentDisposition;
	
	/**
	 * @$clientQualifier mimeHeader
	 * @associates Property
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="mimeHeader")
	Collection<Property> mimeHeaders;
	
	Boolean messageDispositionNotificationRequested;
	
	/**
	 * @$clientQualifier messageDeliveryStatusNotificationRequest
	 * @associates UnifiedMessageDeliveryStatusNotificationRequest
	 * @link aggregationByValue
	 */
	@XmlElement(name="messageDeliveryStatusNotificationRequest", type=UnifiedMessageDeliveryStatusNotificationRequestEnum.class)
	EnumSet<UnifiedMessageDeliveryStatusNotificationRequestEnum> messageDeliveryStatusNotificationRequests;
	
	Long size;
	
	UnifiedMessage() {
		super();
	}

	public UnifiedMessage(Id id, HeterogeneousFolder parent, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn, userCreatedOn);
	}
	
	public UnifiedMessage(HeterogeneousFolder parent, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn, userCreatedOn);
		editMode = UnifiedMessageEditModeEnum.NewCopy;
	}
	
	public UnifiedMessage(Date createdOn, Date userCreatedOn) {
	    this(null, createdOn, userCreatedOn);
	}

	public Participant getEnvelopeSender() {
		return envelopeSender;
	}
	
	public void setEnvelopeSender(Participant envelopSender) {
		this.envelopeSender = envelopSender;
	}
	
	public Date getSentTime() {
		return getUserCreationDate();
	}
	
	public void setSentTime(Date sentTime) {
		setUserCreationDate(sentTime);
	}
	
	public String getSubject() {
		return getName();
	}
	
	public void setSubject(String subject) {
		setName(subject);
	}
	
	public Collection<UnifiedMessageParticipant> getToReceivers() {
		if (toReceivers != null) {
			return Collections.unmodifiableCollection(new ArrayList<UnifiedMessageParticipant>(toReceivers));
		} else {
			return null;
		}
	}
	
	public boolean addToReceiver(UnifiedMessageParticipant participant) {
		if (toReceivers == null) {
			toReceivers = new Vector<UnifiedMessageParticipant>();
		}
		boolean r = toReceivers.add(participant);
		return r;
	}
	
	public boolean removeToReceiver(UnifiedMessageParticipant participant) {
		if (toReceivers == null) {
			return false;
		}
		boolean r = toReceivers.remove(participant);
		return r;
	}
	
	public Collection<UnifiedMessageParticipant> getCcReceivers() {
		if (ccReceivers != null) {
			return Collections.unmodifiableCollection(new ArrayList<UnifiedMessageParticipant>(ccReceivers));
		} else {
			return null;
		}
	}
	
	public boolean addCcReceiver(UnifiedMessageParticipant participant) {
		if (ccReceivers == null) {
			ccReceivers = new Vector<UnifiedMessageParticipant>();
		}
		boolean r = ccReceivers.add(participant);
		return r;
	}
	
	public boolean removeCcReceiver(UnifiedMessageParticipant participant) {
		if (ccReceivers == null) {
			return false;
		}
		boolean r = ccReceivers.remove(participant);
		return r;
	}
	
	public Collection<UnifiedMessageParticipant> getBccReceivers() {
		if (bccReceivers != null) {
			return Collections.unmodifiableCollection(new ArrayList<UnifiedMessageParticipant>(bccReceivers));
		} else {
			return null;
		}
	}
	
	public boolean addBccReceiver(UnifiedMessageParticipant participant) {
		if (bccReceivers == null) {
			bccReceivers = new Vector<UnifiedMessageParticipant>();
		}
		boolean r = bccReceivers.add(participant);
		return r;
	}
	
	public boolean removeBccReceiver(UnifiedMessageParticipant participant) {
		if (bccReceivers == null) {
			return false;
		}
		boolean r = bccReceivers.remove(participant);
		return r;
	}
	
	public Collection<UnifiedMessageParticipant> getReplyTo() {
		if (replyTo != null) {
			return Collections.unmodifiableCollection(new ArrayList<UnifiedMessageParticipant>(replyTo));
		} else {
			return null;
		}
	}
	
	public boolean addReplyTo(UnifiedMessageParticipant participant) {
		if (replyTo == null) {
			replyTo = new Vector<UnifiedMessageParticipant>();
		}
		boolean r = replyTo.add(participant);
		return r;
	}
	
	public boolean removeReplyTo(UnifiedMessageParticipant participant) {
		if (replyTo == null) {
			return false;
		}
		boolean r = replyTo.remove(participant);
		return r;
	}
	
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public Set<UnifiedMessageFlagEnum> getFlags() {
		if (flags != null) {
			return Collections.unmodifiableSet(new HashSet<UnifiedMessageFlagEnum>(flags));
		} else {
			return null;
		}
	}
	
	public void setFlags(EnumSet<UnifiedMessageFlagEnum> flags) {
		this.flags = flags.clone();
	}
	
	public boolean addFlag(UnifiedMessageFlagEnum flag) {
		if (flags == null) {
			flags = EnumSet.noneOf(UnifiedMessageFlagEnum.class);
		}
		boolean r = flags.add(flag);
		return r;
	}
	
	public boolean removeFlag(UnifiedMessageFlagEnum flag) {
		if (flags == null) {
			return false;
		}
		boolean r = flags.remove(flag);
		return r;
	}
	
	public boolean isEditable() {
		UnifiedMessageEditMode mode = getEditMode();
		return (mode == UnifiedMessageEditModeEnum.DraftCopy) || (mode == UnifiedMessageEditModeEnum.NewCopy);
	}

	public String getContentId() {
		return contentId;
	}
	
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getMediaType() {
		return mediaType;
	}
	
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public ContentDispositionType getContentDisposition() {
		return contentDisposition;
	}
	
	public void setContentDisposition(ContentDispositionType contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	
	public UnifiedMessageChannel getChannel() {
		return channel;
	}
	
	public void setChannel(UnifiedMessageChannel channel) {
		this.channel = channel;
	}

	public UnifiedMessageEditMode getEditMode() {
		return editMode;
	}
	
	public Collection<Property> getMimeHeaders() {
		if (mimeHeaders != null) {
			return Collections.unmodifiableCollection(new ArrayList<Property>(mimeHeaders));
		} else {
			return null;
		}
	}
	
	public boolean addMimeHeader(Property header) {
		if (mimeHeaders == null) {
			mimeHeaders = new Vector<Property>();
		}
		boolean r = mimeHeaders.add(header);
		return r;
	}
	
	public boolean removeMimeHeader(String headerName) {
		Property theHeader = null;
		for (Property header : mimeHeaders) {
			if (header.getPropertyDefinition().getName().equals(headerName)) {
				theHeader = header;
				break;
			}
		}
		return removeMimeHeader(theHeader);
	}
	
	public boolean removeMimeHeader(Property header) {
		boolean r = false;
		if (mimeHeaders != null) {
			r = mimeHeaders.remove(header);
		}
		return r;
	}
	
	public Boolean isMessageDispositionNotificationRequested() {
		return messageDispositionNotificationRequested;
	}
	
	public void setMessageDispositionNotificationRequested(Boolean mdnRequested) {
		this.messageDispositionNotificationRequested = mdnRequested;
	}

	public EnumSet<UnifiedMessageDeliveryStatusNotificationRequestEnum> getMessageDeliveryStatusNotificationRequests() {
		return messageDeliveryStatusNotificationRequests;
	}
	
	public void setMessageDeliveryStatusNotificationRequests(EnumSet<UnifiedMessageDeliveryStatusNotificationRequestEnum> dnsRequests) {
		this.messageDeliveryStatusNotificationRequests = dnsRequests;
	}
	 
    public Long getSize() {
        return size;
    }
	
	public UnifiedMessage clone() {
		UnifiedMessage clone = (UnifiedMessage) super.clone();
		if (envelopeSender != null) {
			clone.envelopeSender = envelopeSender.clone();
		}
	    if (toReceivers != null) {
	    	clone.toReceivers = new Vector<UnifiedMessageParticipant>(toReceivers.size());
	    	for (UnifiedMessageParticipant p : toReceivers) {
	    		clone.toReceivers.add(p.clone());
	    	}
	    }
	    if (ccReceivers != null) {
	    	clone.ccReceivers = new Vector<UnifiedMessageParticipant>(ccReceivers.size());
	    	for (UnifiedMessageParticipant p : ccReceivers) {
	    		clone.ccReceivers.add(p.clone());
	    	}
	    }
	    if (bccReceivers != null) {
	    	clone.bccReceivers = new Vector<UnifiedMessageParticipant>(bccReceivers.size());
	    	for (UnifiedMessageParticipant p : bccReceivers) {
	    		clone.bccReceivers.add(p.clone());
	    	}
	    }
	    if (replyTo != null) {
	    	clone.replyTo = new Vector<UnifiedMessageParticipant>(replyTo.size());
	    	for (UnifiedMessageParticipant p : replyTo) {
	    		clone.replyTo.add(p.clone());
	    	}
	    }
	    clone.priority = priority;
	    clone.channel = channel;
	    clone.editMode = editMode;
	    clone.contentId = contentId;
	    clone.mediaType = mediaType;
	    clone.contentDisposition = contentDisposition;
	    if (flags != null) {
	    	clone.flags = flags.clone();
	    }
	    if (mimeHeaders != null) {
	    	clone.mimeHeaders = new Vector<Property>(mimeHeaders.size());
	    	for (Property p : mimeHeaders) {
	    		clone.mimeHeaders.add(p.clone());
	    	}
	    }
	    clone.messageDispositionNotificationRequested = messageDispositionNotificationRequested;
	    if (messageDeliveryStatusNotificationRequests != null) {
	    	clone.messageDeliveryStatusNotificationRequests = messageDeliveryStatusNotificationRequests.clone();
	    }
	    return clone;
    }
	
}
