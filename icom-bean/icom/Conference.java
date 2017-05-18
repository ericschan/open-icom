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

import java.util.Date;
import java.util.List;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Conference", namespace="http://docs.oasis-open.org/ns/icom/conference/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/conference/201008")
public class Conference extends Folder {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier organizer
	 * @associates Participant
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Participant organizer;
	
	Date scheduledStartDate;
	
	Date scheduledEndDate;
	
	/**
	 * @$clientQualifier conferenceType
	 * @associates ConferenceType
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=ConferenceTypeEnum.class)
	ConferenceType conferenceType;
	
	/**
	 * @$clientQualifier conferenceStatus
	 * @associates ConferenceStatus
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	@XmlElement(type=ConferenceStatusEnum.class)
	ConferenceStatus conferenceStatus;
	
	/**
	 * @$clientQualifier runningSession
	 * @associates ConferenceSession
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	ConferenceSession runningSession;
	
	/**
	 * @$clientQualifier conferenceSetting
	 * @associates ConferenceSetting
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	ConferenceSetting conferenceSetting;
	
	/**
	 * @$clientQualifier transcript
	 * @associates Document
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Document.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
    @XmlElement(name="transcript")
	List<Document> transcripts;

	Conference() {
		super();
	}

    public Conference(Id id, Space parent, Date createdOn, Date userCreatedOn) {
        super(id, parent, createdOn, userCreatedOn);
    }

    public Conference(Space parent, Date createdOn, Date userCreatedOn) {
        super(parent, createdOn, userCreatedOn);
        transcripts = new Vector<Document>();
    }
	
	public Participant getOrganizer() {
		return organizer;
	}

	public Date getScheduledStartDate() {
		return scheduledStartDate;
	}

	public Date getScheduledEndDate() {
		return scheduledEndDate;
	}

	public ConferenceType getConferenceType() {
		return conferenceType;
	}

    public void setConferenceType(ConferenceType conferenceType) {
        this.conferenceType = conferenceType;
    }

	public ConferenceStatus getConferenceStatus() {
		return conferenceStatus;
	}

    public void setConferenceStatus(ConferenceStatus conferenceStatus) {
        this.conferenceStatus = conferenceStatus;
    }

    public ConferenceSetting getConferenceSetting() {
        return conferenceSetting;
    }

    public void setConferenceSetting(ConferenceSetting conferenceSetting) {
        this.conferenceSetting = conferenceSetting;
    }

	public List<Document> getElements() {
		return null;
	}
	
}
