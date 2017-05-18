//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for todoUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="todoUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="allowAllAssigneesToPublishBonds" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="applyPrimaryDefaultReminder" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="attachmentsUpdater" type="{http://www.oracle.com/beehive}attachmentListUpdater" minOccurs="0"/>
 *         &lt;element name="companyNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="completed" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="compositeParticipantsUpdater" type="{http://www.oracle.com/beehive}todoCompositeParticipantListUpdater" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="due" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="enableNotification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ICalCategories" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ICalClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ICalPriority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ICalSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="location" type="{http://www.oracle.com/beehive}location" minOccurs="0"/>
 *         &lt;element name="operationContext" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="organizer" type="{http://www.oracle.com/beehive}participant" minOccurs="0"/>
 *         &lt;element name="organizerUpdater" type="{http://www.oracle.com/beehive}participantUpdater" minOccurs="0"/>
 *         &lt;element name="participantsUpdater" type="{http://www.oracle.com/beehive}todoParticipantListUpdater" minOccurs="0"/>
 *         &lt;element name="percentComplete" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="primaryAssignmentUpdater" type="{http://www.oracle.com/beehive}assignmentUpdater" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="receptionChannel" type="{http://www.oracle.com/beehive}participantDeliveryChannel" minOccurs="0"/>
 *         &lt;element name="sensitivity" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="start" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}todoStatus" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}todoType" minOccurs="0"/>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "todoUpdater", propOrder = {
    "allowAllAssigneesToPublishBonds",
    "applyPrimaryDefaultReminder",
    "attachmentsUpdater",
    "companyNames",
    "completed",
    "compositeParticipantsUpdater",
    "description",
    "due",
    "enableNotification",
    "iCalCategories",
    "iCalClass",
    "iCalPriority",
    "iCalSequence",
    "location",
    "operationContext",
    "organizer",
    "organizerUpdater",
    "participantsUpdater",
    "percentComplete",
    "primaryAssignmentUpdater",
    "priority",
    "receptionChannel",
    "sensitivity",
    "start",
    "status",
    "type",
    "uid",
    "url"
})
public class TodoUpdater
    extends ArtifactUpdater
{

    protected boolean allowAllAssigneesToPublishBonds;
    protected boolean applyPrimaryDefaultReminder;
    protected AttachmentListUpdater attachmentsUpdater;
    @XmlElement(nillable = true)
    protected List<String> companyNames;
    protected DateTime completed;
    protected TodoCompositeParticipantListUpdater compositeParticipantsUpdater;
    protected String description;
    protected DateTime due;
    protected boolean enableNotification;
    @XmlElement(name = "ICalCategories", nillable = true)
    protected List<String> iCalCategories;
    @XmlElement(name = "ICalClass")
    protected String iCalClass;
    @XmlElement(name = "ICalPriority")
    protected int iCalPriority;
    @XmlElement(name = "ICalSequence")
    protected int iCalSequence;
    protected Location location;
    protected Object operationContext;
    protected Participant organizer;
    protected ParticipantUpdater organizerUpdater;
    protected TodoParticipantListUpdater participantsUpdater;
    protected int percentComplete;
    protected AssignmentUpdater primaryAssignmentUpdater;
    protected Priority priority;
    protected ParticipantDeliveryChannel receptionChannel;
    protected BeeId sensitivity;
    protected DateTime start;
    protected TodoStatus status;
    protected TodoType type;
    protected String uid;
    protected String url;

    /**
     * Gets the value of the allowAllAssigneesToPublishBonds property.
     * 
     */
    public boolean isAllowAllAssigneesToPublishBonds() {
        return allowAllAssigneesToPublishBonds;
    }

    /**
     * Sets the value of the allowAllAssigneesToPublishBonds property.
     * 
     */
    public void setAllowAllAssigneesToPublishBonds(boolean value) {
        this.allowAllAssigneesToPublishBonds = value;
    }

    /**
     * Gets the value of the applyPrimaryDefaultReminder property.
     * 
     */
    public boolean isApplyPrimaryDefaultReminder() {
        return applyPrimaryDefaultReminder;
    }

    /**
     * Sets the value of the applyPrimaryDefaultReminder property.
     * 
     */
    public void setApplyPrimaryDefaultReminder(boolean value) {
        this.applyPrimaryDefaultReminder = value;
    }

    /**
     * Gets the value of the attachmentsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentListUpdater }
     *     
     */
    public AttachmentListUpdater getAttachmentsUpdater() {
        return attachmentsUpdater;
    }

    /**
     * Sets the value of the attachmentsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentListUpdater }
     *     
     */
    public void setAttachmentsUpdater(AttachmentListUpdater value) {
        this.attachmentsUpdater = value;
    }

    /**
     * Gets the value of the companyNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the companyNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompanyNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCompanyNames() {
        if (companyNames == null) {
            companyNames = new ArrayList<String>();
        }
        return this.companyNames;
    }

    /**
     * Gets the value of the completed property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getCompleted() {
        return completed;
    }

    /**
     * Sets the value of the completed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setCompleted(DateTime value) {
        this.completed = value;
    }

    /**
     * Gets the value of the compositeParticipantsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link TodoCompositeParticipantListUpdater }
     *     
     */
    public TodoCompositeParticipantListUpdater getCompositeParticipantsUpdater() {
        return compositeParticipantsUpdater;
    }

    /**
     * Sets the value of the compositeParticipantsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoCompositeParticipantListUpdater }
     *     
     */
    public void setCompositeParticipantsUpdater(TodoCompositeParticipantListUpdater value) {
        this.compositeParticipantsUpdater = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the due property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getDue() {
        return due;
    }

    /**
     * Sets the value of the due property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setDue(DateTime value) {
        this.due = value;
    }

    /**
     * Gets the value of the enableNotification property.
     * 
     */
    public boolean isEnableNotification() {
        return enableNotification;
    }

    /**
     * Sets the value of the enableNotification property.
     * 
     */
    public void setEnableNotification(boolean value) {
        this.enableNotification = value;
    }

    /**
     * Gets the value of the iCalCategories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iCalCategories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getICalCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getICalCategories() {
        if (iCalCategories == null) {
            iCalCategories = new ArrayList<String>();
        }
        return this.iCalCategories;
    }

    /**
     * Gets the value of the iCalClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICalClass() {
        return iCalClass;
    }

    /**
     * Sets the value of the iCalClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICalClass(String value) {
        this.iCalClass = value;
    }

    /**
     * Gets the value of the iCalPriority property.
     * 
     */
    public int getICalPriority() {
        return iCalPriority;
    }

    /**
     * Sets the value of the iCalPriority property.
     * 
     */
    public void setICalPriority(int value) {
        this.iCalPriority = value;
    }

    /**
     * Gets the value of the iCalSequence property.
     * 
     */
    public int getICalSequence() {
        return iCalSequence;
    }

    /**
     * Sets the value of the iCalSequence property.
     * 
     */
    public void setICalSequence(int value) {
        this.iCalSequence = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setLocation(Location value) {
        this.location = value;
    }

    /**
     * Gets the value of the operationContext property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOperationContext() {
        return operationContext;
    }

    /**
     * Sets the value of the operationContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOperationContext(Object value) {
        this.operationContext = value;
    }

    /**
     * Gets the value of the organizer property.
     * 
     * @return
     *     possible object is
     *     {@link Participant }
     *     
     */
    public Participant getOrganizer() {
        return organizer;
    }

    /**
     * Sets the value of the organizer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Participant }
     *     
     */
    public void setOrganizer(Participant value) {
        this.organizer = value;
    }

    /**
     * Gets the value of the organizerUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantUpdater }
     *     
     */
    public ParticipantUpdater getOrganizerUpdater() {
        return organizerUpdater;
    }

    /**
     * Sets the value of the organizerUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantUpdater }
     *     
     */
    public void setOrganizerUpdater(ParticipantUpdater value) {
        this.organizerUpdater = value;
    }

    /**
     * Gets the value of the participantsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link TodoParticipantListUpdater }
     *     
     */
    public TodoParticipantListUpdater getParticipantsUpdater() {
        return participantsUpdater;
    }

    /**
     * Sets the value of the participantsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoParticipantListUpdater }
     *     
     */
    public void setParticipantsUpdater(TodoParticipantListUpdater value) {
        this.participantsUpdater = value;
    }

    /**
     * Gets the value of the percentComplete property.
     * 
     */
    public int getPercentComplete() {
        return percentComplete;
    }

    /**
     * Sets the value of the percentComplete property.
     * 
     */
    public void setPercentComplete(int value) {
        this.percentComplete = value;
    }

    /**
     * Gets the value of the primaryAssignmentUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AssignmentUpdater }
     *     
     */
    public AssignmentUpdater getPrimaryAssignmentUpdater() {
        return primaryAssignmentUpdater;
    }

    /**
     * Sets the value of the primaryAssignmentUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignmentUpdater }
     *     
     */
    public void setPrimaryAssignmentUpdater(AssignmentUpdater value) {
        this.primaryAssignmentUpdater = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the receptionChannel property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantDeliveryChannel }
     *     
     */
    public ParticipantDeliveryChannel getReceptionChannel() {
        return receptionChannel;
    }

    /**
     * Sets the value of the receptionChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantDeliveryChannel }
     *     
     */
    public void setReceptionChannel(ParticipantDeliveryChannel value) {
        this.receptionChannel = value;
    }

    /**
     * Gets the value of the sensitivity property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getSensitivity() {
        return sensitivity;
    }

    /**
     * Sets the value of the sensitivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setSensitivity(BeeId value) {
        this.sensitivity = value;
    }

    /**
     * Gets the value of the start property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setStart(DateTime value) {
        this.start = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TodoStatus }
     *     
     */
    public TodoStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoStatus }
     *     
     */
    public void setStatus(TodoStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link TodoType }
     *     
     */
    public TodoType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoType }
     *     
     */
    public void setType(TodoType value) {
        this.type = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

}
