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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Task", namespace="http://docs.oasis-open.org/ns/icom/task/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/task/201008")
public class Task extends Artifact {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier editMode
	 * @associates TaskEditMode
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=TaskEditModeEnum.class)
	TaskEditMode editMode;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Date startDate;
	
	@XmlElement(type=DateTimeResolutionEnum.class, namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	DateTimeResolution startDateResolution;
	
	Date dueDate;
	
	@XmlElement(type=DateTimeResolutionEnum.class)
	DateTimeResolution dueDateResolution;
	
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
	 * @$clientQualifier taskStatus
	 * @associates TaskStatus
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=TaskStatusEnum.class)
	TaskStatus taskStatus;
	
	/**
	 * @$clientQualifier attachment
	 * @associates AttachedItem
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="attachment", namespace="http://docs.oasis-open.org/ns/icom/content/201008")
	Set<AttachedItem> attachments;
	
	/**
	 * @$clientQualifier assignee
	 * @associates Participant
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	Participant assignee;
	
	Date completionDate;
	
	@XmlElement(type=DateTimeResolutionEnum.class)
	DateTimeResolution completionDateResolution;
	
	Integer percentComplete;
	
	/**
	 * @$clientQualifier priority
	 * @associates Priority
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=PriorityEnum.class, namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Priority priority;
	
	/**
	 * @$clientQualifier participationStatus
	 * @associates TaskParticipantStatus
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=TaskParticipantStatusEnum.class)
	TaskParticipantStatus participantStatus;
	
	/**
	 * @$clientQualifier assigneeProperty
	 * @associates Property
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="assigneeProperty")
	Collection<Property> assigneeProperties;
	
	Task() {
		
	}
	
	public Task(Id id, TaskList parent, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn, userCreatedOn);
		parent.addElement(this);
	}
	
	public Task(TaskList parent, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn, userCreatedOn);
		attachments = new HashSet<AttachedItem>();
		assigneeProperties = new Vector<Property>();
		editMode = TaskEditModeEnum.OrganizerCopy;
		parent.addElement(this);
	}
	
	public boolean isEditable() {
		return getEditMode() == TaskEditModeEnum.OrganizerCopy;
	}
	
	public TaskEditMode getEditMode() {
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

	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public DateTimeResolution getDueDateResolution() {
		return dueDateResolution;
	}
	
	public void setDueDateResolution(DateTimeResolution dueDateResolution) {
		this.dueDateResolution = dueDateResolution;
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
	
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}
	
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
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
	
	public Participant getAssignee() {
		return assignee;
	}
	
	public void setAssignee(Participant assignee) {
		this.assignee = assignee;
	}
	
	public Date getCompletionDate() {
		return completionDate;
	}
	
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	
	public DateTimeResolution getCompletionDateResolution() {
		return completionDateResolution;
	}
	
	public void setCompletionDateResolution(DateTimeResolution completionDateResolution) {
		this.completionDateResolution = completionDateResolution;
	}
	
	public Integer getPercentComplete() {
		return percentComplete;
	}
	
	public void setPercentComplete(Integer percentComplete) {
		this.percentComplete = percentComplete;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public TaskParticipantStatus getParticipantStatus() {
		return participantStatus;
	}
	
	public void setParticipantStatus(TaskParticipantStatus participantStatus) {
		this.participantStatus = participantStatus;
	}

	public Collection<Property> getAssigneeProperties() {
		if (assigneeProperties != null) {
			return Collections.unmodifiableCollection(new ArrayList<Property>(assigneeProperties));
		} else {
			return null;
		}
	}
	
	public boolean addAssigneeProperty(Property property) {
		boolean r = false;
		if (assigneeProperties != null) {
			r = assigneeProperties.add(property);
		}
		return r;
	}
	
	public boolean removeAssigneeProperty(Property property) {
		boolean r = false;
		if (assigneeProperties != null) {
			r = assigneeProperties.remove(property);
		}
		return r;
	}
	
}
