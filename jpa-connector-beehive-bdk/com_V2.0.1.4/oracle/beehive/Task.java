//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for task complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="task">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifact">
 *       &lt;sequence>
 *         &lt;element name="assignee" type="{http://www.oracle.com/beehive}participant" minOccurs="0"/>
 *         &lt;element name="assigneeCompleted" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="assigneeCompletedDateOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="assigneeDue" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="assigneeDueDateOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="assigneeParticipantStatus" type="{http://www.oracle.com/beehive}todoParticipantStatus" minOccurs="0"/>
 *         &lt;element name="assigneePercentComplete" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="assigneePrimaryClientReminderTrigger" type="{http://www.oracle.com/beehive}timedTrigger" minOccurs="0"/>
 *         &lt;element name="assigneePriority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="assigneeProperties" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="assigneeStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="assigneeStartDateOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="assignmentOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="attachments" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="locationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizer" type="{http://www.oracle.com/beehive}participant" minOccurs="0"/>
 *         &lt;element ref="{http://www.oracle.com/beehive}sensitivity" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}todoStatus" minOccurs="0"/>
 *         &lt;element name="textDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xhtmlFragmentDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "task", propOrder = {
    "assignee",
    "assigneeCompleted",
    "assigneeCompletedDateOnly",
    "assigneeDue",
    "assigneeDueDateOnly",
    "assigneeParticipantStatus",
    "assigneePercentComplete",
    "assigneePrimaryClientReminderTrigger",
    "assigneePriority",
    "assigneeProperties",
    "assigneeStart",
    "assigneeStartDateOnly",
    "assignmentOnly",
    "attachments",
    "locationName",
    "organizer",
    "sensitivity",
    "status",
    "textDescription",
    "xhtmlFragmentDescription"
})
@XmlRootElement(name = "task")
public class Task
    extends Artifact
{

    protected Participant assignee;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar assigneeCompleted;
    protected boolean assigneeCompletedDateOnly;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar assigneeDue;
    protected boolean assigneeDueDateOnly;
    protected TodoParticipantStatus assigneeParticipantStatus;
    protected int assigneePercentComplete;
    protected TimedTrigger assigneePrimaryClientReminderTrigger;
    protected Priority assigneePriority;
    protected CollabProperties assigneeProperties;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar assigneeStart;
    protected boolean assigneeStartDateOnly;
    protected boolean assignmentOnly;
    protected List<Object> attachments;
    protected String locationName;
    protected Participant organizer;
    protected Sensitivity sensitivity;
    protected TodoStatus status;
    protected String textDescription;
    protected String xhtmlFragmentDescription;

    /**
     * Gets the value of the assignee property.
     * 
     * @return
     *     possible object is
     *     {@link Participant }
     *     
     */
    public Participant getAssignee() {
        return assignee;
    }

    /**
     * Sets the value of the assignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Participant }
     *     
     */
    public void setAssignee(Participant value) {
        this.assignee = value;
    }

    /**
     * Gets the value of the assigneeCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssigneeCompleted() {
        return assigneeCompleted;
    }

    /**
     * Sets the value of the assigneeCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssigneeCompleted(XMLGregorianCalendar value) {
        this.assigneeCompleted = value;
    }

    /**
     * Gets the value of the assigneeCompletedDateOnly property.
     * 
     */
    public boolean isAssigneeCompletedDateOnly() {
        return assigneeCompletedDateOnly;
    }

    /**
     * Sets the value of the assigneeCompletedDateOnly property.
     * 
     */
    public void setAssigneeCompletedDateOnly(boolean value) {
        this.assigneeCompletedDateOnly = value;
    }

    /**
     * Gets the value of the assigneeDue property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssigneeDue() {
        return assigneeDue;
    }

    /**
     * Sets the value of the assigneeDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssigneeDue(XMLGregorianCalendar value) {
        this.assigneeDue = value;
    }

    /**
     * Gets the value of the assigneeDueDateOnly property.
     * 
     */
    public boolean isAssigneeDueDateOnly() {
        return assigneeDueDateOnly;
    }

    /**
     * Sets the value of the assigneeDueDateOnly property.
     * 
     */
    public void setAssigneeDueDateOnly(boolean value) {
        this.assigneeDueDateOnly = value;
    }

    /**
     * Gets the value of the assigneeParticipantStatus property.
     * 
     * @return
     *     possible object is
     *     {@link TodoParticipantStatus }
     *     
     */
    public TodoParticipantStatus getAssigneeParticipantStatus() {
        return assigneeParticipantStatus;
    }

    /**
     * Sets the value of the assigneeParticipantStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoParticipantStatus }
     *     
     */
    public void setAssigneeParticipantStatus(TodoParticipantStatus value) {
        this.assigneeParticipantStatus = value;
    }

    /**
     * Gets the value of the assigneePercentComplete property.
     * 
     */
    public int getAssigneePercentComplete() {
        return assigneePercentComplete;
    }

    /**
     * Sets the value of the assigneePercentComplete property.
     * 
     */
    public void setAssigneePercentComplete(int value) {
        this.assigneePercentComplete = value;
    }

    /**
     * Gets the value of the assigneePrimaryClientReminderTrigger property.
     * 
     * @return
     *     possible object is
     *     {@link TimedTrigger }
     *     
     */
    public TimedTrigger getAssigneePrimaryClientReminderTrigger() {
        return assigneePrimaryClientReminderTrigger;
    }

    /**
     * Sets the value of the assigneePrimaryClientReminderTrigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimedTrigger }
     *     
     */
    public void setAssigneePrimaryClientReminderTrigger(TimedTrigger value) {
        this.assigneePrimaryClientReminderTrigger = value;
    }

    /**
     * Gets the value of the assigneePriority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getAssigneePriority() {
        return assigneePriority;
    }

    /**
     * Sets the value of the assigneePriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setAssigneePriority(Priority value) {
        this.assigneePriority = value;
    }

    /**
     * Gets the value of the assigneeProperties property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getAssigneeProperties() {
        return assigneeProperties;
    }

    /**
     * Sets the value of the assigneeProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setAssigneeProperties(CollabProperties value) {
        this.assigneeProperties = value;
    }

    /**
     * Gets the value of the assigneeStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssigneeStart() {
        return assigneeStart;
    }

    /**
     * Sets the value of the assigneeStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssigneeStart(XMLGregorianCalendar value) {
        this.assigneeStart = value;
    }

    /**
     * Gets the value of the assigneeStartDateOnly property.
     * 
     */
    public boolean isAssigneeStartDateOnly() {
        return assigneeStartDateOnly;
    }

    /**
     * Sets the value of the assigneeStartDateOnly property.
     * 
     */
    public void setAssigneeStartDateOnly(boolean value) {
        this.assigneeStartDateOnly = value;
    }

    /**
     * Gets the value of the assignmentOnly property.
     * 
     */
    public boolean isAssignmentOnly() {
        return assignmentOnly;
    }

    /**
     * Sets the value of the assignmentOnly property.
     * 
     */
    public void setAssignmentOnly(boolean value) {
        this.assignmentOnly = value;
    }

    /**
     * Gets the value of the attachments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAttachments() {
        if (attachments == null) {
            attachments = new ArrayList<Object>();
        }
        return this.attachments;
    }

    /**
     * Gets the value of the locationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the value of the locationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationName(String value) {
        this.locationName = value;
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
     * Gets the value of the sensitivity property.
     * 
     * @return
     *     possible object is
     *     {@link Sensitivity }
     *     
     */
    public Sensitivity getSensitivity() {
        return sensitivity;
    }

    /**
     * Sets the value of the sensitivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sensitivity }
     *     
     */
    public void setSensitivity(Sensitivity value) {
        this.sensitivity = value;
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
     * Gets the value of the textDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextDescription() {
        return textDescription;
    }

    /**
     * Sets the value of the textDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextDescription(String value) {
        this.textDescription = value;
    }

    /**
     * Gets the value of the xhtmlFragmentDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXhtmlFragmentDescription() {
        return xhtmlFragmentDescription;
    }

    /**
     * Sets the value of the xhtmlFragmentDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXhtmlFragmentDescription(String value) {
        this.xhtmlFragmentDescription = value;
    }

}
