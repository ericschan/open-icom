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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for occurrenceUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrenceUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="allowAllInviteesToPublishBonds" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="applyPrimaryDefaultReminder" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="attachmentsUpdater" type="{http://www.oracle.com/beehive}attachmentListUpdater" minOccurs="0"/>
 *         &lt;element name="companyNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="compositeParticipantsUpdater" type="{http://www.oracle.com/beehive}occurrenceCompositeParticipantListUpdater" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dialInInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enableNotification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="end" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="equipment" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ICalCategories" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ICalClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ICalPriority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ICalRecurrenceId" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="ICalSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ICalUid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initalizedWithSeriesAttributes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="journalEntryType" type="{http://www.oracle.com/beehive}journalEntryType" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.oracle.com/beehive}location" minOccurs="0"/>
 *         &lt;element name="operationContext" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="organizer" type="{http://www.oracle.com/beehive}participant" minOccurs="0"/>
 *         &lt;element name="organizerUpdater" type="{http://www.oracle.com/beehive}participantUpdater" minOccurs="0"/>
 *         &lt;element name="participantsUpdater" type="{http://www.oracle.com/beehive}occurrenceParticipantListUpdater" minOccurs="0"/>
 *         &lt;element name="primaryInvitationUpdater" type="{http://www.oracle.com/beehive}invitationUpdater" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="sensitivity" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="start" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}occurrenceStatus" minOccurs="0"/>
 *         &lt;element name="tranparency" type="{http://www.oracle.com/beehive}transparency" minOccurs="0"/>
 *         &lt;element name="transparency" type="{http://www.oracle.com/beehive}transparency" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}occurrenceType" minOccurs="0"/>
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
@XmlType(name = "occurrenceUpdater", propOrder = {
    "allowAllInviteesToPublishBonds",
    "applyPrimaryDefaultReminder",
    "attachmentsUpdater",
    "companyNames",
    "compositeParticipantsUpdater",
    "description",
    "dialInInfo",
    "eid",
    "enableNotification",
    "end",
    "equipments",
    "iCalCategories",
    "iCalClass",
    "iCalPriority",
    "iCalRecurrenceId",
    "iCalSequence",
    "iCalUid",
    "initalizedWithSeriesAttributes",
    "journalEntryType",
    "location",
    "operationContext",
    "organizer",
    "organizerUpdater",
    "participantsUpdater",
    "primaryInvitationUpdater",
    "priority",
    "sensitivity",
    "start",
    "status",
    "tranparency",
    "transparency",
    "type",
    "url"
})
public class OccurrenceUpdater
    extends ArtifactUpdater
{

    protected boolean allowAllInviteesToPublishBonds;
    protected boolean applyPrimaryDefaultReminder;
    protected AttachmentListUpdater attachmentsUpdater;
    @XmlElement(nillable = true)
    protected List<String> companyNames;
    protected OccurrenceCompositeParticipantListUpdater compositeParticipantsUpdater;
    protected String description;
    protected String dialInInfo;
    protected String eid;
    protected boolean enableNotification;
    protected DateTime end;
    @XmlElement(name = "equipment", nillable = true)
    protected List<String> equipments;
    @XmlElement(name = "ICalCategories")
    protected List<String> iCalCategories;
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
    protected boolean initalizedWithSeriesAttributes;
    protected JournalEntryType journalEntryType;
    protected Location location;
    protected Object operationContext;
    protected Participant organizer;
    protected ParticipantUpdater organizerUpdater;
    protected OccurrenceParticipantListUpdater participantsUpdater;
    protected InvitationUpdater primaryInvitationUpdater;
    protected Priority priority;
    protected BeeId sensitivity;
    protected DateTime start;
    protected OccurrenceStatus status;
    protected Transparency tranparency;
    protected Transparency transparency;
    protected OccurrenceType type;
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
     * Gets the value of the compositeParticipantsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceCompositeParticipantListUpdater }
     *     
     */
    public OccurrenceCompositeParticipantListUpdater getCompositeParticipantsUpdater() {
        return compositeParticipantsUpdater;
    }

    /**
     * Sets the value of the compositeParticipantsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceCompositeParticipantListUpdater }
     *     
     */
    public void setCompositeParticipantsUpdater(OccurrenceCompositeParticipantListUpdater value) {
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
     * Gets the value of the eid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEid() {
        return eid;
    }

    /**
     * Sets the value of the eid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEid(String value) {
        this.eid = value;
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
     * Gets the value of the initalizedWithSeriesAttributes property.
     * 
     */
    public boolean isInitalizedWithSeriesAttributes() {
        return initalizedWithSeriesAttributes;
    }

    /**
     * Sets the value of the initalizedWithSeriesAttributes property.
     * 
     */
    public void setInitalizedWithSeriesAttributes(boolean value) {
        this.initalizedWithSeriesAttributes = value;
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
     *     {@link OccurrenceParticipantListUpdater }
     *     
     */
    public OccurrenceParticipantListUpdater getParticipantsUpdater() {
        return participantsUpdater;
    }

    /**
     * Sets the value of the participantsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceParticipantListUpdater }
     *     
     */
    public void setParticipantsUpdater(OccurrenceParticipantListUpdater value) {
        this.participantsUpdater = value;
    }

    /**
     * Gets the value of the primaryInvitationUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link InvitationUpdater }
     *     
     */
    public InvitationUpdater getPrimaryInvitationUpdater() {
        return primaryInvitationUpdater;
    }

    /**
     * Sets the value of the primaryInvitationUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvitationUpdater }
     *     
     */
    public void setPrimaryInvitationUpdater(InvitationUpdater value) {
        this.primaryInvitationUpdater = value;
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
     * Gets the value of the tranparency property.
     * 
     * @return
     *     possible object is
     *     {@link Transparency }
     *     
     */
    public Transparency getTranparency() {
        return tranparency;
    }

    /**
     * Sets the value of the tranparency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transparency }
     *     
     */
    public void setTranparency(Transparency value) {
        this.tranparency = value;
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