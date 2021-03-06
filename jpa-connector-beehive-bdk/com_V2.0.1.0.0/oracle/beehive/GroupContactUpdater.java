//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for groupContactUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="groupContactUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}addressBookElementUpdater">
 *       &lt;sequence>
 *         &lt;element name="agents" type="{http://www.oracle.com/beehive}agentListUpdater" minOccurs="0"/>
 *         &lt;element name="elements" type="{http://www.oracle.com/beehive}addressBookElementListUpdater" minOccurs="0"/>
 *         &lt;element name="externalContactsUpdater" type="{http://www.oracle.com/beehive}externalContactListUpdater" minOccurs="0"/>
 *         &lt;element name="groupName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="members" type="{http://www.oracle.com/beehive}accessorListUpdater" minOccurs="0"/>
 *         &lt;element name="membersDisplayedInPeopleList" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groupContactUpdater", propOrder = {
    "agents",
    "elements",
    "externalContactsUpdater",
    "groupName",
    "members",
    "membersDisplayedInPeopleList"
})
@XmlRootElement(name = "groupContactUpdater")
public class GroupContactUpdater
    extends AddressBookElementUpdater
{

    protected AgentListUpdater agents;
    protected AddressBookElementListUpdater elements;
    protected ExternalContactListUpdater externalContactsUpdater;
    protected String groupName;
    protected AccessorListUpdater members;
    protected Boolean membersDisplayedInPeopleList;

    /**
     * Gets the value of the agents property.
     * 
     * @return
     *     possible object is
     *     {@link AgentListUpdater }
     *     
     */
    public AgentListUpdater getAgents() {
        return agents;
    }

    /**
     * Sets the value of the agents property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentListUpdater }
     *     
     */
    public void setAgents(AgentListUpdater value) {
        this.agents = value;
    }

    /**
     * Gets the value of the elements property.
     * 
     * @return
     *     possible object is
     *     {@link AddressBookElementListUpdater }
     *     
     */
    public AddressBookElementListUpdater getElements() {
        return elements;
    }

    /**
     * Sets the value of the elements property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressBookElementListUpdater }
     *     
     */
    public void setElements(AddressBookElementListUpdater value) {
        this.elements = value;
    }

    /**
     * Gets the value of the externalContactsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalContactListUpdater }
     *     
     */
    public ExternalContactListUpdater getExternalContactsUpdater() {
        return externalContactsUpdater;
    }

    /**
     * Sets the value of the externalContactsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalContactListUpdater }
     *     
     */
    public void setExternalContactsUpdater(ExternalContactListUpdater value) {
        this.externalContactsUpdater = value;
    }

    /**
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * @return
     *     possible object is
     *     {@link AccessorListUpdater }
     *     
     */
    public AccessorListUpdater getMembers() {
        return members;
    }

    /**
     * Sets the value of the members property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessorListUpdater }
     *     
     */
    public void setMembers(AccessorListUpdater value) {
        this.members = value;
    }

    /**
     * Gets the value of the membersDisplayedInPeopleList property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMembersDisplayedInPeopleList() {
        return membersDisplayedInPeopleList;
    }

    /**
     * Sets the value of the membersDisplayedInPeopleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMembersDisplayedInPeopleList(Boolean value) {
        this.membersDisplayedInPeopleList = value;
    }

}
