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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for meetingUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meetingUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="includeOnlineConference" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inviteeParticipantStatus" type="{http://www.oracle.com/beehive}occurrenceParticipantStatus" minOccurs="0"/>
 *         &lt;element name="inviteePrimaryClientReminderTrigger" type="{http://www.oracle.com/beehive}timedTrigger" minOccurs="0"/>
 *         &lt;element name="inviteePriority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="inviteePropertiesUpdater" type="{http://www.oracle.com/beehive}collabPropertiesUpdater" minOccurs="0"/>
 *         &lt;element name="inviteeTransparency" type="{http://www.oracle.com/beehive}transparency" minOccurs="0"/>
 *         &lt;element name="locationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantUpdaters" type="{http://www.oracle.com/beehive}meetingParticipantUpdater" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}occurrenceStatus" minOccurs="0"/>
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
@XmlType(name = "meetingUpdater", propOrder = {
    "end",
    "includeOnlineConference",
    "inviteeParticipantStatus",
    "inviteePrimaryClientReminderTrigger",
    "inviteePriority",
    "inviteePropertiesUpdater",
    "inviteeTransparency",
    "locationName",
    "participantUpdaters",
    "start",
    "status",
    "textDescription",
    "xhtmlFragmentDescription"
})
@XmlRootElement(name = "meetingUpdater")
public class MeetingUpdater
    extends ArtifactUpdater
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar end;
    protected Boolean includeOnlineConference;
    protected OccurrenceParticipantStatus inviteeParticipantStatus;
    protected TimedTrigger inviteePrimaryClientReminderTrigger;
    protected Priority inviteePriority;
    protected CollabPropertiesUpdater inviteePropertiesUpdater;
    protected Transparency inviteeTransparency;
    protected String locationName;
    @XmlElement(nillable = true)
    protected List<MeetingParticipantUpdater> participantUpdaters;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar start;
    protected OccurrenceStatus status;
    protected String textDescription;
    protected String xhtmlFragmentDescription;

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnd(XMLGregorianCalendar value) {
        this.end = value;
    }

    /**
     * Gets the value of the includeOnlineConference property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeOnlineConference() {
        return includeOnlineConference;
    }

    /**
     * Sets the value of the includeOnlineConference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeOnlineConference(Boolean value) {
        this.includeOnlineConference = value;
    }

    /**
     * Gets the value of the inviteeParticipantStatus property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceParticipantStatus }
     *     
     */
    public OccurrenceParticipantStatus getInviteeParticipantStatus() {
        return inviteeParticipantStatus;
    }

    /**
     * Sets the value of the inviteeParticipantStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceParticipantStatus }
     *     
     */
    public void setInviteeParticipantStatus(OccurrenceParticipantStatus value) {
        this.inviteeParticipantStatus = value;
    }

    /**
     * Gets the value of the inviteePrimaryClientReminderTrigger property.
     * 
     * @return
     *     possible object is
     *     {@link TimedTrigger }
     *     
     */
    public TimedTrigger getInviteePrimaryClientReminderTrigger() {
        return inviteePrimaryClientReminderTrigger;
    }

    /**
     * Sets the value of the inviteePrimaryClientReminderTrigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimedTrigger }
     *     
     */
    public void setInviteePrimaryClientReminderTrigger(TimedTrigger value) {
        this.inviteePrimaryClientReminderTrigger = value;
    }

    /**
     * Gets the value of the inviteePriority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getInviteePriority() {
        return inviteePriority;
    }

    /**
     * Sets the value of the inviteePriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setInviteePriority(Priority value) {
        this.inviteePriority = value;
    }

    /**
     * Gets the value of the inviteePropertiesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public CollabPropertiesUpdater getInviteePropertiesUpdater() {
        return inviteePropertiesUpdater;
    }

    /**
     * Sets the value of the inviteePropertiesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public void setInviteePropertiesUpdater(CollabPropertiesUpdater value) {
        this.inviteePropertiesUpdater = value;
    }

    /**
     * Gets the value of the inviteeTransparency property.
     * 
     * @return
     *     possible object is
     *     {@link Transparency }
     *     
     */
    public Transparency getInviteeTransparency() {
        return inviteeTransparency;
    }

    /**
     * Sets the value of the inviteeTransparency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transparency }
     *     
     */
    public void setInviteeTransparency(Transparency value) {
        this.inviteeTransparency = value;
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
     * Gets the value of the participantUpdaters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participantUpdaters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipantUpdaters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeetingParticipantUpdater }
     * 
     * 
     */
    public List<MeetingParticipantUpdater> getParticipantUpdaters() {
        if (participantUpdaters == null) {
            participantUpdaters = new ArrayList<MeetingParticipantUpdater>();
        }
        return this.participantUpdaters;
    }

    /**
     * Gets the value of the start property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStart(XMLGregorianCalendar value) {
        this.start = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceStatus }
     *     
     */
    public OccurrenceStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceStatus }
     *     
     */
    public void setStatus(OccurrenceStatus value) {
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