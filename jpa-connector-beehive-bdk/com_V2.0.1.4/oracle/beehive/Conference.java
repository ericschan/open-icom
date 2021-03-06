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
 * <p>Java class for conference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="conference">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifact">
 *       &lt;sequence>
 *         &lt;element name="conferenceType" type="{http://www.oracle.com/beehive}conferenceType" minOccurs="0"/>
 *         &lt;element name="creatorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="guestRoleCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="hostNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isHost" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isInvited" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isPublic" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="keyProtectedRoleCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="runningSession" type="{http://www.oracle.com/beehive}conferenceSession" minOccurs="0"/>
 *         &lt;element name="settings" type="{http://www.oracle.com/beehive}conferenceSettings" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.oracle.com/beehive}conferenceState" minOccurs="0"/>
 *         &lt;element name="visibility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conference", propOrder = {
    "conferenceType",
    "creatorName",
    "description",
    "endTime",
    "guestRoleCount",
    "hostNames",
    "isHost",
    "isInvited",
    "isPublic",
    "keyProtectedRoleCount",
    "runningSession",
    "settings",
    "startTime",
    "state",
    "visibility"
})
@XmlRootElement(name = "conference")
public class Conference
    extends Artifact
{

    protected ConferenceType conferenceType;
    protected String creatorName;
    protected String description;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    protected Integer guestRoleCount;
    @XmlElement(nillable = true)
    protected List<String> hostNames;
    protected Boolean isHost;
    protected Boolean isInvited;
    protected Boolean isPublic;
    protected Integer keyProtectedRoleCount;
    protected ConferenceSession runningSession;
    protected ConferenceSettings settings;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    protected ConferenceState state;
    protected String visibility;

    /**
     * Gets the value of the conferenceType property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceType }
     *     
     */
    public ConferenceType getConferenceType() {
        return conferenceType;
    }

    /**
     * Sets the value of the conferenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceType }
     *     
     */
    public void setConferenceType(ConferenceType value) {
        this.conferenceType = value;
    }

    /**
     * Gets the value of the creatorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * Sets the value of the creatorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorName(String value) {
        this.creatorName = value;
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
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the guestRoleCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGuestRoleCount() {
        return guestRoleCount;
    }

    /**
     * Sets the value of the guestRoleCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGuestRoleCount(Integer value) {
        this.guestRoleCount = value;
    }

    /**
     * Gets the value of the hostNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getHostNames() {
        if (hostNames == null) {
            hostNames = new ArrayList<String>();
        }
        return this.hostNames;
    }

    /**
     * Gets the value of the isHost property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsHost() {
        return isHost;
    }

    /**
     * Sets the value of the isHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsHost(Boolean value) {
        this.isHost = value;
    }

    /**
     * Gets the value of the isInvited property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsInvited() {
        return isInvited;
    }

    /**
     * Sets the value of the isInvited property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsInvited(Boolean value) {
        this.isInvited = value;
    }

    /**
     * Gets the value of the isPublic property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPublic() {
        return isPublic;
    }

    /**
     * Sets the value of the isPublic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPublic(Boolean value) {
        this.isPublic = value;
    }

    /**
     * Gets the value of the keyProtectedRoleCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKeyProtectedRoleCount() {
        return keyProtectedRoleCount;
    }

    /**
     * Sets the value of the keyProtectedRoleCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKeyProtectedRoleCount(Integer value) {
        this.keyProtectedRoleCount = value;
    }

    /**
     * Gets the value of the runningSession property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceSession }
     *     
     */
    public ConferenceSession getRunningSession() {
        return runningSession;
    }

    /**
     * Sets the value of the runningSession property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceSession }
     *     
     */
    public void setRunningSession(ConferenceSession value) {
        this.runningSession = value;
    }

    /**
     * Gets the value of the settings property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceSettings }
     *     
     */
    public ConferenceSettings getSettings() {
        return settings;
    }

    /**
     * Sets the value of the settings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceSettings }
     *     
     */
    public void setSettings(ConferenceSettings value) {
        this.settings = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceState }
     *     
     */
    public ConferenceState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceState }
     *     
     */
    public void setState(ConferenceState value) {
        this.state = value;
    }

    /**
     * Gets the value of the visibility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * Sets the value of the visibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisibility(String value) {
        this.visibility = value;
    }

}
