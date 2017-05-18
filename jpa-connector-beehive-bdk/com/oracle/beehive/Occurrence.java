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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for occurrence complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrence">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifact">
 *       &lt;sequence>
 *         &lt;element name="allowAllInviteesToPublishBonds" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="attachments" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="companyNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="compositeParticipants" type="{http://www.oracle.com/beehive}occurrenceCompositeParticipant" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dialInInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="end" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="equipment" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="explicitlyModifiedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fromInclusionRule" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasCompositeParticipants" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasMultipleInstances" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasMultipleParticipants" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasPendingReminders" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ICalClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ICalPriority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ICalRecurrenceId" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="ICalSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ICalUid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="internalOrganizer" type="{http://www.oracle.com/beehive}actor" minOccurs="0"/>
 *         &lt;element name="invitationListModifiedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="isFromInclusionRule" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="journalEntryType" type="{http://www.oracle.com/beehive}journalEntryType" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.oracle.com/beehive}location" minOccurs="0"/>
 *         &lt;element name="occurrenceExceptionToSeries" type="{http://www.oracle.com/beehive}exceptionToSeries" minOccurs="0"/>
 *         &lt;element name="organizer" type="{http://www.oracle.com/beehive}participant" minOccurs="0"/>
 *         &lt;element name="participants" type="{http://www.oracle.com/beehive}occurrenceParticipant" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="publishedBonds" type="{http://www.oracle.com/beehive}bond" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="receptionChannel" type="{http://www.oracle.com/beehive}participantDeliveryChannel" minOccurs="0"/>
 *         &lt;element name="reminders" type="{http://www.oracle.com/beehive}reminder" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.oracle.com/beehive}sensitivity" minOccurs="0"/>
 *         &lt;element name="series" type="{http://www.oracle.com/beehive}occurrenceSeries" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="start" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}occurrenceStatus" minOccurs="0"/>
 *         &lt;element name="transparency" type="{http://www.oracle.com/beehive}transparency" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}occurrenceType" minOccurs="0"/>
 *         &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occurrence", propOrder = {
    "allowAllInviteesToPublishBonds",
    "attachments",
    "companyNames",
    "compositeParticipants",
    "description",
    "dialInInfo",
    "end",
    "equipments",
    "explicitlyModifiedOn",
    "fromInclusionRule",
    "hasCompositeParticipants",
    "hasMultipleInstances",
    "hasMultipleParticipants",
    "hasPendingReminders",
    "iCalClass",
    "iCalPriority",
    "iCalRecurrenceId",
    "iCalSequence",
    "iCalUid",
    "internalOrganizer",
    "invitationListModifiedOn",
    "isFromInclusionRule",
    "journalEntryType",
    "location",
    "occurrenceExceptionToSeries",
    "organizer",
    "participants",
    "priority",
    "publishedBonds",
    "receptionChannel",
    "reminders",
    "sensitivity",
    "series",
    "size",
    "start",
    "status",
    "transparency",
    "type",
    "url"
})
public class Occurrence
    extends Artifact
{

    protected boolean allowAllInviteesToPublishBonds;
    @XmlElement(nillable = true)
    protected List<Object> attachments;
    @XmlElement(nillable = true)
    protected List<String> companyNames;
    @XmlElement(nillable = true)
    protected List<OccurrenceCompositeParticipant> compositeParticipants;
    protected String description;
    protected String dialInInfo;
    protected DateTime end;
    @XmlElement(name = "equipment", nillable = true)
    protected List<String> equipments;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar explicitlyModifiedOn;
    protected boolean fromInclusionRule;
    protected boolean hasCompositeParticipants;
    protected boolean hasMultipleInstances;
    protected boolean hasMultipleParticipants;
    protected boolean hasPendingReminders;
    @XmlElement(name = "ICalClass")
    protected String iCalClass;
    @XmlElement(name = "ICalPriority")
    protected int iCalPriority;
    @XmlElement(name = "ICalRecurrenceId")
    protected DateTime iCalRecurrenceId;
    @XmlElement(name = "ICalSequence")
    protected int iCalSequence;
    @XmlElement(name = "ICalUid")
    protected String iCalUid;
    protected Actor internalOrganizer;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invitationListModifiedOn;
    protected boolean isFromInclusionRule;
    protected JournalEntryType journalEntryType;
    protected Location location;
    protected ExceptionToSeries occurrenceExceptionToSeries;
    protected Participant organizer;
    @XmlElement(nillable = true)
    protected List<OccurrenceParticipant> participants;
    protected Priority priority;
    @XmlElement(nillable = true)
    protected List<Bond> publishedBonds;
    protected ParticipantDeliveryChannel receptionChannel;
    protected List<Reminder> reminders;
    protected Sensitivity sensitivity;
    protected OccurrenceSeries series;
    protected long size;
    protected DateTime start;
    protected OccurrenceStatus status;
    protected Transparency transparency;
    protected OccurrenceType type;
    @XmlElement(name = "URL")
    protected String url;

    /**
     * Gets the value of the allowAllInviteesToPublishBonds property.
     * 
     */
    public boolean isAllowAllInviteesToPublishBonds() {
        return allowAllInviteesToPublishBonds;
    }

    /**
     * Sets the value of the allowAllInviteesToPublishBonds property.
     * 
     */
    public void setAllowAllInviteesToPublishBonds(boolean value) {
        this.allowAllInviteesToPublishBonds = value;
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
     * Gets the value of the compositeParticipants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compositeParticipants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompositeParticipants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OccurrenceCompositeParticipant }
     * 
     * 
     */
    public List<OccurrenceCompositeParticipant> getCompositeParticipants() {
        if (compositeParticipants == null) {
            compositeParticipants = new ArrayList<OccurrenceCompositeParticipant>();
        }
        return this.compositeParticipants;
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
     * Gets the value of the dialInInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialInInfo() {
        return dialInInfo;
    }

    /**
     * Sets the value of the dialInInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialInInfo(String value) {
        this.dialInInfo = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setEnd(DateTime value) {
        this.end = value;
    }

    /**
     * Gets the value of the equipments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equipments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquipments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEquipments() {
        if (equipments == null) {
            equipments = new ArrayList<String>();
        }
        return this.equipments;
    }

    /**
     * Gets the value of the explicitlyModifiedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExplicitlyModifiedOn() {
        return explicitlyModifiedOn;
    }

    /**
     * Sets the value of the explicitlyModifiedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExplicitlyModifiedOn(XMLGregorianCalendar value) {
        this.explicitlyModifiedOn = value;
    }

    /**
     * Gets the value of the fromInclusionRule property.
     * 
     */
    public boolean isFromInclusionRule() {
        return fromInclusionRule;
    }

    /**
     * Sets the value of the fromInclusionRule property.
     * 
     */
    public void setFromInclusionRule(boolean value) {
        this.fromInclusionRule = value;
    }

    /**
     * Gets the value of the hasCompositeParticipants property.
     * 
     */
    public boolean isHasCompositeParticipants() {
        return hasCompositeParticipants;
    }

    /**
     * Sets the value of the hasCompositeParticipants property.
     * 
     */
    public void setHasCompositeParticipants(boolean value) {
        this.hasCompositeParticipants = value;
    }

    /**
     * Gets the value of the hasMultipleInstances property.
     * 
     */
    public boolean isHasMultipleInstances() {
        return hasMultipleInstances;
    }

    /**
     * Sets the value of the hasMultipleInstances property.
     * 
     */
    public void setHasMultipleInstances(boolean value) {
        this.hasMultipleInstances = value;
    }

    /**
     * Gets the value of the hasMultipleParticipants property.
     * 
     */
    public boolean isHasMultipleParticipants() {
        return hasMultipleParticipants;
    }

    /**
     * Sets the value of the hasMultipleParticipants property.
     * 
     */
    public void setHasMultipleParticipants(boolean value) {
        this.hasMultipleParticipants = value;
    }

    /**
     * Gets the value of the hasPendingReminders property.
     * 
     */
    public boolean isHasPendingReminders() {
        return hasPendingReminders;
    }

    /**
     * Sets the value of the hasPendingReminders property.
     * 
     */
    public void setHasPendingReminders(boolean value) {
        this.hasPendingReminders = value;
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
     * Gets the value of the iCalRecurrenceId property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getICalRecurrenceId() {
        return iCalRecurrenceId;
    }

    /**
     * Sets the value of the iCalRecurrenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setICalRecurrenceId(DateTime value) {
        this.iCalRecurrenceId = value;
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
     * Gets the value of the iCalUid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICalUid() {
        return iCalUid;
    }

    /**
     * Sets the value of the iCalUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICalUid(String value) {
        this.iCalUid = value;
    }

    /**
     * Gets the value of the internalOrganizer property.
     * 
     * @return
     *     possible object is
     *     {@link Actor }
     *     
     */
    public Actor getInternalOrganizer() {
        return internalOrganizer;
    }

    /**
     * Sets the value of the internalOrganizer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Actor }
     *     
     */
    public void setInternalOrganizer(Actor value) {
        this.internalOrganizer = value;
    }

    /**
     * Gets the value of the invitationListModifiedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvitationListModifiedOn() {
        return invitationListModifiedOn;
    }

    /**
     * Sets the value of the invitationListModifiedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvitationListModifiedOn(XMLGregorianCalendar value) {
        this.invitationListModifiedOn = value;
    }

    /**
     * Gets the value of the isFromInclusionRule property.
     * 
     */
    public boolean isIsFromInclusionRule() {
        return isFromInclusionRule;
    }

    /**
     * Sets the value of the isFromInclusionRule property.
     * 
     */
    public void setIsFromInclusionRule(boolean value) {
        this.isFromInclusionRule = value;
    }

    /**
     * Gets the value of the journalEntryType property.
     * 
     * @return
     *     possible object is
     *     {@link JournalEntryType }
     *     
     */
    public JournalEntryType getJournalEntryType() {
        return journalEntryType;
    }

    /**
     * Sets the value of the journalEntryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JournalEntryType }
     *     
     */
    public void setJournalEntryType(JournalEntryType value) {
        this.journalEntryType = value;
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
     * Gets the value of the occurrenceExceptionToSeries property.
     * 
     * @return
     *     possible object is
     *     {@link ExceptionToSeries }
     *     
     */
    public ExceptionToSeries getOccurrenceExceptionToSeries() {
        return occurrenceExceptionToSeries;
    }

    /**
     * Sets the value of the occurrenceExceptionToSeries property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExceptionToSeries }
     *     
     */
    public void setOccurrenceExceptionToSeries(ExceptionToSeries value) {
        this.occurrenceExceptionToSeries = value;
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
     * {@link OccurrenceParticipant }
     * 
     * 
     */
    public List<OccurrenceParticipant> getParticipants() {
        if (participants == null) {
            participants = new ArrayList<OccurrenceParticipant>();
        }
        return this.participants;
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
     * Gets the value of the publishedBonds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the publishedBonds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublishedBonds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bond }
     * 
     * 
     */
    public List<Bond> getPublishedBonds() {
        if (publishedBonds == null) {
            publishedBonds = new ArrayList<Bond>();
        }
        return this.publishedBonds;
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
     * Gets the value of the reminders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reminders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReminders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Reminder }
     * 
     * 
     */
    public List<Reminder> getReminders() {
        if (reminders == null) {
            reminders = new ArrayList<Reminder>();
        }
        return this.reminders;
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
     * Gets the value of the series property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceSeries }
     *     
     */
    public OccurrenceSeries getSeries() {
        return series;
    }

    /**
     * Sets the value of the series property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceSeries }
     *     
     */
    public void setSeries(OccurrenceSeries value) {
        this.series = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(long value) {
        this.size = value;
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
     * Gets the value of the transparency property.
     * 
     * @return
     *     possible object is
     *     {@link Transparency }
     *     
     */
    public Transparency getTransparency() {
        return transparency;
    }

    /**
     * Sets the value of the transparency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transparency }
     *     
     */
    public void setTransparency(Transparency value) {
        this.transparency = value;
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
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
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
    public void setURL(String value) {
        this.url = value;
    }

}
