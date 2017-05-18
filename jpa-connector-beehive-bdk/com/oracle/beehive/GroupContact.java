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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for groupContact complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="groupContact">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}addressBookElement">
 *       &lt;sequence>
 *         &lt;element name="agents" type="{http://www.oracle.com/beehive}agent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bookmark" type="{http://www.oracle.com/beehive}group" minOccurs="0"/>
 *         &lt;element name="elements" type="{http://www.oracle.com/beehive}addressBookElement" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="externalMembers" type="{http://www.oracle.com/beehive}externalContact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="groupName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memberDisplayedInPeopleList" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="members" type="{http://www.oracle.com/beehive}accessor" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groupContact", propOrder = {
    "agents",
    "bookmark",
    "elements",
    "externalMembers",
    "groupName",
    "memberDisplayedInPeopleList",
    "members"
})
@XmlRootElement(name = "groupContact")
public class GroupContact
    extends AddressBookElement
{

    @XmlElement(nillable = true)
    protected List<Agent> agents;
    protected Group bookmark;
    @XmlElement(nillable = true)
    protected List<AddressBookElement> elements;
    @XmlElement(nillable = true)
    protected List<ExternalContact> externalMembers;
    protected String groupName;
    protected boolean memberDisplayedInPeopleList;
    @XmlElement(nillable = true)
    protected List<Accessor> members;

    /**
     * Gets the value of the agents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Agent }
     * 
     * 
     */
    public List<Agent> getAgents() {
        if (agents == null) {
            agents = new ArrayList<Agent>();
        }
        return this.agents;
    }

    /**
     * Gets the value of the bookmark property.
     * 
     * @return
     *     possible object is
     *     {@link Group }
     *     
     */
    public Group getBookmark() {
        return bookmark;
    }

    /**
     * Sets the value of the bookmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link Group }
     *     
     */
    public void setBookmark(Group value) {
        this.bookmark = value;
    }

    /**
     * Gets the value of the elements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressBookElement }
     * 
     * 
     */
    public List<AddressBookElement> getElements() {
        if (elements == null) {
            elements = new ArrayList<AddressBookElement>();
        }
        return this.elements;
    }

    /**
     * Gets the value of the externalMembers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalMembers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalMembers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalContact }
     * 
     * 
     */
    public List<ExternalContact> getExternalMembers() {
        if (externalMembers == null) {
            externalMembers = new ArrayList<ExternalContact>();
        }
        return this.externalMembers;
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
     * Gets the value of the memberDisplayedInPeopleList property.
     * 
     */
    public boolean isMemberDisplayedInPeopleList() {
        return memberDisplayedInPeopleList;
    }

    /**
     * Sets the value of the memberDisplayedInPeopleList property.
     * 
     */
    public void setMemberDisplayedInPeopleList(boolean value) {
        this.memberDisplayedInPeopleList = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the members property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMembers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Accessor }
     * 
     * 
     */
    public List<Accessor> getMembers() {
        if (members == null) {
            members = new ArrayList<Accessor>();
        }
        return this.members;
    }

}