//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for meetingSeries complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meetingSeries">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="attachments" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *         &lt;element name="invitationOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invitee" type="{http://www.oracle.com/beehive}participant" minOccurs="0"/>
 *         &lt;element name="inviteeEffectiveTransparency" type="{http://www.oracle.com/beehive}transparency" minOccurs="0"/>
 *         &lt;element name="inviteeParticipantStatus" type="{http://www.oracle.com/beehive}occurrenceParticipantStatus" minOccurs="0"/>
 *         &lt;element name="inviteePriority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="inviteeProperties" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="locationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="meetings" type="{http://www.oracle.com/beehive}meeting" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="onlineConference" type="{http://www.oracle.com/beehive}conference" minOccurs="0"/>
 *         &lt;element name="onlineConferenceJoinURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizer" type="{http://www.oracle.com/beehive}participant" minOccurs="0"/>
 *         &lt;element name="participants" type="{http://www.oracle.com/beehive}meetingParticipant" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="properties" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="recurrenceRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recurrenceStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="recurrenceStartDateOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{http://www.oracle.com/beehive}sensitivity" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}occurrenceStatus" minOccurs="0"/>
 *         &lt;element name="textDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}occurrenceType" minOccurs="0"/>
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
@XmlType(name = "meetingSeries", propOrder = {
    "attachments",
    "duration",
    "invitationOnly",
    "invitee",
    "inviteeEffectiveTransparency",
    "inviteeParticipantStatus",
    "inviteePriority",
    "inviteeProperties",
    "locationName",
    "meetings",
    "onlineConference",
    "onlineConferenceJoinURL",
    "organizer",
    "participants",
    "properties",
    "recurrenceRule",
    "recurrenceStart",
    "recurrenceStartDateOnly",
    "sensitivity",
    "status",
    "textDescription",
    "type",
    "xhtmlFragmentDescription"
})
@XmlRootElement(name = "meetingSeries")
public class MeetingSeries
    extends Entity
{

    protected List<Object> attachments;
    protected Duration duration;
    protected boolean invitationOnly;
    protected Participant invitee;
    protected Transparency inviteeEffectiveTransparency;
    protected OccurrenceParticipantStatus inviteeParticipantStatus;
    protected Priority inviteePriority;
    protected CollabProperties inviteeProperties;
    protected String locationName;
    protected List<Meeting> meetings;
    protected Conference onlineConference;
    protected String onlineConferenceJoinURL;
    protected Participant organizer;
    protected List<MeetingParticipant> participants;
    protected CollabProperties properties;
    protected String recurrenceRule;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar recurrenceStart;
    protected boolean recurrenceStartDateOnly;
    protected Sensitivity sensitivity;
    protected OccurrenceStatus status;
    protected String textDescription;
    protected OccurrenceType type;
    protected String xhtmlFragmentDescription;

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
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDuration(Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the invitationOnly property.
     * 
     */
    public boolean isInvitationOnly() {
        return invitationOnly;
    }

    /**
     * Sets the value of the invitationOnly property.
     * 
     */
    public void setInvitationOnly(boolean value) {
        this.invitationOnly = value;
    }

    /**
     * Gets the value of the invitee property.
     * 
     * @return
     *     possible object is
     *     {@link Participant }
     *     
     */
    public Participant getInvitee() {
        return invitee;
    }

    /**
     * Sets the value of the invitee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Participant }
     *     
     */
    public void setInvitee(Participant value) {
        this.invitee = value;
    }

    /**
     * Gets the value of the inviteeEffectiveTransparency property.
     * 
     * @return
     *     possible object is
     *     {@link Transparency }
     *     
     */
    public Transparency getInviteeEffectiveTransparency() {
        return inviteeEffectiveTransparency;
    }

    /**
     * Sets the value of the inviteeEffectiveTransparency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transparency }
     *     
     */
    public void setInviteeEffectiveTransparency(Transparency value) {
        this.inviteeEffectiveTransparency = value;
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
     * Gets the value of the inviteeProperties property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getInviteeProperties() {
        return inviteeProperties;
    }

    /**
     * Sets the value of the inviteeProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setInviteeProperties(CollabProperties value) {
        this.inviteeProperties = value;
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
     * Gets the value of the meetings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meetings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeetings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Meeting }
     * 
     * 
     */
    public List<Meeting> getMeetings() {
        if (meetings == null) {
            meetings = new ArrayList<Meeting>();
        }
        return this.meetings;
    }

    /**
     * Gets the value of the onlineConference property.
     * 
     * @return
     *     possible object is
     *     {@link Conference }
     *     
     */
    public Conference getOnlineConference() {
        return onlineConference;
    }

    /**
     * Sets the value of the onlineConference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Conference }
     *     
     */
    public void setOnlineConference(Conference value) {
        this.onlineConference = value;
    }

    /**
     * Gets the value of the onlineConferenceJoinURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnlineConferenceJoinURL() {
        return onlineConferenceJoinURL;
    }

    /**
     * Sets the value of the onlineConferenceJoinURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnlineConferenceJoinURL(String value) {
        this.onlineConferenceJoinURL = value;
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
     * Gets the value of the participants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeetingParticipant }
     * 
     * 
     */
    public List<MeetingParticipant> getParticipants() {
        if (participants == null) {
            participants = new ArrayList<MeetingParticipant>();
        }
        return this.participants;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setProperties(CollabProperties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the recurrenceRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    /**
     * Sets the value of the recurrenceRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurrenceRule(String value) {
        this.recurrenceRule = value;
    }

    /**
     * Gets the value of the recurrenceStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRecurrenceStart() {
        return recurrenceStart;
    }

    /**
     * Sets the value of the recurrenceStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRecurrenceStart(XMLGregorianCalendar value) {
        this.recurrenceStart = value;
    }

    /**
     * Gets the value of the recurrenceStartDateOnly property.
     * 
     */
    public boolean isRecurrenceStartDateOnly() {
        return recurrenceStartDateOnly;
    }

    /**
     * Sets the value of the recurrenceStartDateOnly property.
     * 
     */
    public void setRecurrenceStartDateOnly(boolean value) {
        this.recurrenceStartDateOnly = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceType }
     *     
     */
    public OccurrenceType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceType }
     *     
     */
    public void setType(OccurrenceType value) {
        this.type = value;
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
