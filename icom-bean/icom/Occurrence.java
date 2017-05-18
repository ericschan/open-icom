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
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@javax.persistence.Entity
@XmlType(name="Occurrence", namespace="http://docs.oasis-open.org/ns/icom/calendar/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/calendar/201008")
public class Occurrence extends Artifact {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier editMode
	 * @associates OccurrenceEditMode
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=OccurrenceEditModeEnum.class)
	OccurrenceEditMode editMode;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Date startDate;
	
	@XmlElement(type=DateTimeResolutionEnum.class, namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	DateTimeResolution startDateResolution;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Date endDate;
	
	@XmlElement(type=DateTimeResolutionEnum.class, namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	DateTimeResolution endDateResolution;
	
	/**
     * @$clientQualifier location
     * @associates Location
     * @clientNavigability NAVIGABLE
     */
    @Embedded
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Location location;
	
	/**
	 * @$clientQualifier organizer
	 * @associates Participant
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Participant organizer;
	
	/**
	 * @$clientQualifier participant
	 * @associates OccurrenceParticipant
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="participant", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Collection<OccurrenceParticipant> participants;
	
	/**
	 * @$clientQualifier occurrenceSeries
	 * @associates OccurrenceSeries
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	OccurrenceSeries occurrenceSeries;
	
	Boolean fromRecurringOccurrenceSeries;
	
	Boolean exceptionToOccurrenceSeries;
	
	/**
	 * @$clientQualifier occurrenceStatus
	 * @associates OccurrenceStatus
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=OccurrenceStatusEnum.class)
	OccurrenceStatus occurrenceStatus;
	
	/**
	 * @$clientQualifier occurrenceType
	 * @associates OccurrenceType
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=OccurrenceTypeEnum.class)
	OccurrenceType occurrenceType;
	
	/**
	 * @$clientQualifier attachment
	 * @associates AttachedItem
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="attachment", namespace="http://docs.oasis-open.org/ns/icom/content/201008")
	Set<AttachedItem> attachments;
	
	/**
	 * @$clientQualifier attendee
	 * @associates Participant
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	Participant attendee;
	
	/**
	 * @$clientQualifier priority
	 * @associates Priority
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=PriorityEnum.class, namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Priority priority;
	
	/**
	 * @$clientQualifier attendeeParticipantStatus
	 * @associates OccurrenceParticipantStatus
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=OccurrenceParticipantStatusEnum.class)
	OccurrenceParticipantStatus attendeeParticipantStatus;
	
	/**
	 * @$clientQualifier transparency
	 * @associates OccurrenceParticipantTransparency
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=OccurrenceParticipantTransparencyEnum.class)
	OccurrenceParticipantTransparency transparency;
	
	/**
	 * @$clientQualifier attendeeProperty
	 * @associates Property
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="attendeeProperty")
	Collection<Property> attendeeProperties;
	
	/**
	 * @$clientQualifier conference
	 * @associates Conference
	 * @link aggregation
	 */
	@ManyToMany
	@XmlElement(name="conference", namespace="http://docs.oasis-open.org/ns/icom/conference/201008")
	Collection<Conference> conferences;
	
	Occurrence() {
		
	}
	
	public Occurrence(Id id, Calendar parent, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn, userCreatedOn);
		parent.addElement(this);
	}
	
	public Occurrence(Calendar parent, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn, userCreatedOn);
		participants = new Vector<OccurrenceParticipant>();
		attachments = new HashSet<AttachedItem>();
		attendeeProperties = new Vector<Property>();
		editMode = OccurrenceEditModeEnum.OrganizerCopy;
		parent.addElement(this);
	}
	
	public void delete() {
		if (parent instanceof Calendar) {
			((Calendar)parent).removeElement(this);
		}
		if(occurrenceSeries != null) {
			occurrenceSeries.removeOccurrence(this);
		}
		super.delete();
	}
	
	public boolean isEditable() {
		return getEditMode() == OccurrenceEditModeEnum.OrganizerCopy;
	}
	
	public OccurrenceEditMode getEditMode() {
		return editMode;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public DateTimeResolution getStartDateResolution() {
		return startDateResolution;
	}
	
	public void setStartDateResolution(DateTimeResolution startDateResolution) {
		this.startDateResolution = startDateResolution;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public DateTimeResolution getEndDateResolution() {
		return endDateResolution;
	}
	
	public void setEndDateResolution(DateTimeResolution endDateResolution) {
		this.endDateResolution = endDateResolution;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Participant getOrganizer() {
		return organizer;
	}
	
	public Collection<OccurrenceParticipant> getParticipants() {
		if (participants != null) {
			return Collections.unmodifiableCollection(new ArrayList<OccurrenceParticipant>(participants));
		} else {
			return null;
		}
	}
	
	public boolean addParticipant(OccurrenceParticipant participant) {
		if (participants == null) {
			participants = new Vector<OccurrenceParticipant>();
		}
		boolean r = participants.add(participant);
		return r;
	}
	
	public boolean removeParticipant(OccurrenceParticipant participant) {
		if (participants == null) {
			return false;
		}
		boolean r = participants.remove(participant);
		return r;
	}
	
	public OccurrenceSeries getOccurrenceSeries() {
		return occurrenceSeries;
	}
	
	public void setOccurrenceSeries(OccurrenceSeries occurrenceSeries) {
		this.occurrenceSeries = occurrenceSeries;
	}
	
	public Boolean isFromRecurringOccurrenceSeries() {
		return fromRecurringOccurrenceSeries;
	}
	
	public Boolean isExceptionToOccurrenceSeries() {
		return exceptionToOccurrenceSeries;
	}
	
	public OccurrenceStatus getOccurrenceStatus() {
		return occurrenceStatus;
	}
	
	public void setOccurrenceStatus(OccurrenceStatus occurrenceStatus) {
		this.occurrenceStatus = occurrenceStatus;
	}
	
	public OccurrenceType getOccurrenceType() {
		return occurrenceType;
	}
	
	public void setOccurrenceType(OccurrenceType occurrenceType) {
		this.occurrenceType = occurrenceType;
	}
	
	public Set<AttachedItem> getAttachments() {
		if (attachments != null) {
			return Collections.unmodifiableSet(new HashSet<AttachedItem>(attachments));
		} else {
			return null;
		}
	}
	
	public boolean addAttachment(AttachedItem attachment) {
		if (attachments == null) {
			attachments = new HashSet<AttachedItem>();
		}
		boolean r = attachments.add(attachment);
		return r;
	}
	
	public boolean removeAttachment(AttachedItem attachment) {
		boolean r = false;
		if (attachments != null) {
			r = attachments.remove(attachment);
		}
		return r;
	}
	
	public Participant getAttendee() {
		return attendee;
	}
	
	public void setAttendee(Participant attendee) {
		this.attendee = attendee;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public OccurrenceParticipantStatus getAttendeeParticipantStatus() {
		return attendeeParticipantStatus;
	}
	
	public void setAttendeeParticipantStatus(OccurrenceParticipantStatus attendeeParticipantStatus) {
		this.attendeeParticipantStatus = attendeeParticipantStatus;
	}
	
	public OccurrenceParticipantTransparency getTransparency() {
		return transparency;
	}
	
	public void setTransparency(OccurrenceParticipantTransparency transparency) {
		this.transparency = transparency;
	}

	public Collection<Property> getAttendeeProperties() {
		if (attendeeProperties != null) {
			return Collections.unmodifiableCollection(new ArrayList<Property>(attendeeProperties));
		} else {
			return null;
		}
	}
	
	public boolean addAttendeeProperty(Property property) {
		boolean r = false;
		if (attendeeProperties != null) {
			r = attendeeProperties.add(property);
		}
		return r;
	}
	
	public boolean removeAttendeeProperty(Property property) {
		boolean r = false;
		if (attendeeProperties != null) {
			r = attendeeProperties.remove(property);
		}
		return r;
	}
	
	public Collection<Conference> getConferences() {
		if (conferences != null) {
			return Collections.unmodifiableCollection(conferences);
		} else {
			return null;
		}
	}

    public boolean addConference(Conference conference) {
        boolean r = false;
        if (conferences != null) {
            r = conferences.add(conference);
        }
        return r;
    }

    public boolean removeConference(Conference conference) {
        boolean r = false;
        if (conferences != null) {
            r = conferences.remove(conference);
        }
        return r;
    }
	
}
