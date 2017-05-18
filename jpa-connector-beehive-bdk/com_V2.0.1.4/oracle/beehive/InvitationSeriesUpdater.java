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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for invitationSeriesUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="invitationSeriesUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityUpdater">
 *       &lt;sequence>
 *         &lt;element name="calDavResourceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invitationsUpdater" type="{http://www.oracle.com/beehive}invitationListUpdater" minOccurs="0"/>
 *         &lt;element name="inviteeICalCategories" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inviteeICalClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inviteeICalPriority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="inviteeParticipantStatus" type="{http://www.oracle.com/beehive}occurrenceParticipantStatus" minOccurs="0"/>
 *         &lt;element name="inviteePriority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="inviteePropertiesUpdater" type="{http://www.oracle.com/beehive}collabPropertiesUpdater" minOccurs="0"/>
 *         &lt;element name="inviteeSeriesPropertiesUpdater" type="{http://www.oracle.com/beehive}collabPropertiesUpdater" minOccurs="0"/>
 *         &lt;element name="inviteeTransparency" type="{http://www.oracle.com/beehive}transparency" minOccurs="0"/>
 *         &lt;element name="publishInvitationBonds" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sensitivity" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="seriesUpdateMode" type="{http://www.oracle.com/beehive}seriesUpdateMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invitationSeriesUpdater", propOrder = {
    "calDavResourceName",
    "invitationsUpdater",
    "inviteeICalCategories",
    "inviteeICalClass",
    "inviteeICalPriority",
    "inviteeParticipantStatus",
    "inviteePriority",
    "inviteePropertiesUpdater",
    "inviteeSeriesPropertiesUpdater",
    "inviteeTransparency",
    "publishInvitationBonds",
    "sensitivity",
    "seriesUpdateMode"
})
public class InvitationSeriesUpdater
    extends EntityUpdater
{

    protected String calDavResourceName;
    protected InvitationListUpdater invitationsUpdater;
    protected List<String> inviteeICalCategories;
    protected String inviteeICalClass;
    protected int inviteeICalPriority;
    protected OccurrenceParticipantStatus inviteeParticipantStatus;
    protected Priority inviteePriority;
    protected CollabPropertiesUpdater inviteePropertiesUpdater;
    protected CollabPropertiesUpdater inviteeSeriesPropertiesUpdater;
    protected Transparency inviteeTransparency;
    protected boolean publishInvitationBonds;
    protected BeeId sensitivity;
    protected SeriesUpdateMode seriesUpdateMode;

    /**
     * Gets the value of the calDavResourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalDavResourceName() {
        return calDavResourceName;
    }

    /**
     * Sets the value of the calDavResourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalDavResourceName(String value) {
        this.calDavResourceName = value;
    }

    /**
     * Gets the value of the invitationsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link InvitationListUpdater }
     *     
     */
    public InvitationListUpdater getInvitationsUpdater() {
        return invitationsUpdater;
    }

    /**
     * Sets the value of the invitationsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvitationListUpdater }
     *     
     */
    public void setInvitationsUpdater(InvitationListUpdater value) {
        this.invitationsUpdater = value;
    }

    /**
     * Gets the value of the inviteeICalCategories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inviteeICalCategories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInviteeICalCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInviteeICalCategories() {
        if (inviteeICalCategories == null) {
            inviteeICalCategories = new ArrayList<String>();
        }
        return this.inviteeICalCategories;
    }

    /**
     * Gets the value of the inviteeICalClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInviteeICalClass() {
        return inviteeICalClass;
    }

    /**
     * Sets the value of the inviteeICalClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInviteeICalClass(String value) {
        this.inviteeICalClass = value;
    }

    /**
     * Gets the value of the inviteeICalPriority property.
     * 
     */
    public int getInviteeICalPriority() {
        return inviteeICalPriority;
    }

    /**
     * Sets the value of the inviteeICalPriority property.
     * 
     */
    public void setInviteeICalPriority(int value) {
        this.inviteeICalPriority = value;
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
     * Gets the value of the inviteeSeriesPropertiesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public CollabPropertiesUpdater getInviteeSeriesPropertiesUpdater() {
        return inviteeSeriesPropertiesUpdater;
    }

    /**
     * Sets the value of the inviteeSeriesPropertiesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public void setInviteeSeriesPropertiesUpdater(CollabPropertiesUpdater value) {
        this.inviteeSeriesPropertiesUpdater = value;
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
     * Gets the value of the publishInvitationBonds property.
     * 
     */
    public boolean isPublishInvitationBonds() {
        return publishInvitationBonds;
    }

    /**
     * Sets the value of the publishInvitationBonds property.
     * 
     */
    public void setPublishInvitationBonds(boolean value) {
        this.publishInvitationBonds = value;
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
     * Gets the value of the seriesUpdateMode property.
     * 
     * @return
     *     possible object is
     *     {@link SeriesUpdateMode }
     *     
     */
    public SeriesUpdateMode getSeriesUpdateMode() {
        return seriesUpdateMode;
    }

    /**
     * Sets the value of the seriesUpdateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeriesUpdateMode }
     *     
     */
    public void setSeriesUpdateMode(SeriesUpdateMode value) {
        this.seriesUpdateMode = value;
    }

}
