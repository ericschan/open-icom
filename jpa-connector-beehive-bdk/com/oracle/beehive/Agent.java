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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}directoryEntry">
 *       &lt;sequence>
 *         &lt;element name="addresses" type="{http://www.oracle.com/beehive}entityAddress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bonds" type="{http://www.oracle.com/beehive}bond" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="effectiveGroups" type="{http://www.oracle.com/beehive}group" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="groups" type="{http://www.oracle.com/beehive}group" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="primaryAddress" type="{http://www.oracle.com/beehive}entityAddress" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}provisioningStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agent", propOrder = {
    "addresses",
    "bonds",
    "effectiveGroups",
    "groups",
    "primaryAddress",
    "status"
})
@XmlSeeAlso({
    ExternalPerson.class,
    ExternalResource.class
})
public abstract class Agent
    extends DirectoryEntry
{

    protected List<EntityAddress> addresses;
    @XmlElement(nillable = true)
    protected List<Bond> bonds;
    protected List<Group> effectiveGroups;
    protected List<Group> groups;
    protected EntityAddress primaryAddress;
    protected ProvisioningStatus status;

    /**
     * Gets the value of the addresses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addresses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddresses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntityAddress }
     * 
     * 
     */
    public List<EntityAddress> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<EntityAddress>();
        }
        return this.addresses;
    }

    /**
     * Gets the value of the bonds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bonds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBonds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bond }
     * 
     * 
     */
    public List<Bond> getBonds() {
        if (bonds == null) {
            bonds = new ArrayList<Bond>();
        }
        return this.bonds;
    }

    /**
     * Gets the value of the effectiveGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the effectiveGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEffectiveGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Group }
     * 
     * 
     */
    public List<Group> getEffectiveGroups() {
        if (effectiveGroups == null) {
            effectiveGroups = new ArrayList<Group>();
        }
        return this.effectiveGroups;
    }

    /**
     * Gets the value of the groups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Group }
     * 
     * 
     */
    public List<Group> getGroups() {
        if (groups == null) {
            groups = new ArrayList<Group>();
        }
        return this.groups;
    }

    /**
     * Gets the value of the primaryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link EntityAddress }
     *     
     */
    public EntityAddress getPrimaryAddress() {
        return primaryAddress;
    }

    /**
     * Sets the value of the primaryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityAddress }
     *     
     */
    public void setPrimaryAddress(EntityAddress value) {
        this.primaryAddress = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ProvisioningStatus }
     *     
     */
    public ProvisioningStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisioningStatus }
     *     
     */
    public void setStatus(ProvisioningStatus value) {
        this.status = value;
    }

}