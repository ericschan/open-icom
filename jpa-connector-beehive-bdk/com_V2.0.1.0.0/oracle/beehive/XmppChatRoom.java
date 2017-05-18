//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xmppChatRoom complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xmppChatRoom">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="conferenceHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="empty" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="lock" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="locked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="members" type="{http://www.oracle.com/beehive}associativeArray" minOccurs="0"/>
 *         &lt;element name="numLocalMembers" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ownerJid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerParticipantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passwordProtected" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="persistent" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="searchable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xmppChatRoom", propOrder = {
    "conferenceHandle",
    "empty",
    "lock",
    "locked",
    "members",
    "numLocalMembers",
    "ownerJid",
    "ownerParticipantId",
    "password",
    "passwordProtected",
    "persistent",
    "searchable"
})
public class XmppChatRoom
    extends Entity
{

    protected BeeId conferenceHandle;
    protected boolean empty;
    protected boolean lock;
    protected boolean locked;
    protected AssociativeArray members;
    protected int numLocalMembers;
    protected String ownerJid;
    protected String ownerParticipantId;
    protected String password;
    protected boolean passwordProtected;
    protected boolean persistent;
    protected boolean searchable;

    /**
     * Gets the value of the conferenceHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getConferenceHandle() {
        return conferenceHandle;
    }

    /**
     * Sets the value of the conferenceHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setConferenceHandle(BeeId value) {
        this.conferenceHandle = value;
    }

    /**
     * Gets the value of the empty property.
     * 
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Sets the value of the empty property.
     * 
     */
    public void setEmpty(boolean value) {
        this.empty = value;
    }

    /**
     * Gets the value of the lock property.
     * 
     */
    public boolean isLock() {
        return lock;
    }

    /**
     * Sets the value of the lock property.
     * 
     */
    public void setLock(boolean value) {
        this.lock = value;
    }

    /**
     * Gets the value of the locked property.
     * 
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the value of the locked property.
     * 
     */
    public void setLocked(boolean value) {
        this.locked = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * @return
     *     possible object is
     *     {@link AssociativeArray }
     *     
     */
    public AssociativeArray getMembers() {
        return members;
    }

    /**
     * Sets the value of the members property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociativeArray }
     *     
     */
    public void setMembers(AssociativeArray value) {
        this.members = value;
    }

    /**
     * Gets the value of the numLocalMembers property.
     * 
     */
    public int getNumLocalMembers() {
        return numLocalMembers;
    }

    /**
     * Sets the value of the numLocalMembers property.
     * 
     */
    public void setNumLocalMembers(int value) {
        this.numLocalMembers = value;
    }

    /**
     * Gets the value of the ownerJid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerJid() {
        return ownerJid;
    }

    /**
     * Sets the value of the ownerJid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerJid(String value) {
        this.ownerJid = value;
    }

    /**
     * Gets the value of the ownerParticipantId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerParticipantId() {
        return ownerParticipantId;
    }

    /**
     * Sets the value of the ownerParticipantId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerParticipantId(String value) {
        this.ownerParticipantId = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the passwordProtected property.
     * 
     */
    public boolean isPasswordProtected() {
        return passwordProtected;
    }

    /**
     * Sets the value of the passwordProtected property.
     * 
     */
    public void setPasswordProtected(boolean value) {
        this.passwordProtected = value;
    }

    /**
     * Gets the value of the persistent property.
     * 
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * Sets the value of the persistent property.
     * 
     */
    public void setPersistent(boolean value) {
        this.persistent = value;
    }

    /**
     * Gets the value of the searchable property.
     * 
     */
    public boolean isSearchable() {
        return searchable;
    }

    /**
     * Sets the value of the searchable property.
     * 
     */
    public void setSearchable(boolean value) {
        this.searchable = value;
    }

}