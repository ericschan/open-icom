//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accessorUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accessorUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}baseAccessorUpdater">
 *       &lt;sequence>
 *         &lt;element name="addresses" type="{http://www.oracle.com/beehive}addressesListUpdater" minOccurs="0"/>
 *         &lt;element name="memberships" type="{http://www.oracle.com/beehive}communityListUpdater" minOccurs="0"/>
 *         &lt;element name="preferenceProfiles" type="{http://www.oracle.com/beehive}preferenceProfilesListUpdater" minOccurs="0"/>
 *         &lt;element name="primaryAddress" type="{http://www.oracle.com/beehive}entityAddress" minOccurs="0"/>
 *         &lt;element name="propertiesUpdater" type="{http://www.oracle.com/beehive}collabPropertiesUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accessorUpdater", propOrder = {
    "addresses",
    "memberships",
    "preferenceProfiles",
    "primaryAddress",
    "propertiesUpdater"
})
@XmlSeeAlso({
    ActorUpdater.class,
    GroupUpdater.class
})
public abstract class AccessorUpdater
    extends BaseAccessorUpdater
{

    protected AddressesListUpdater addresses;
    protected CommunityListUpdater memberships;
    protected PreferenceProfilesListUpdater preferenceProfiles;
    protected EntityAddress primaryAddress;
    protected CollabPropertiesUpdater propertiesUpdater;

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link AddressesListUpdater }
     *     
     */
    public AddressesListUpdater getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressesListUpdater }
     *     
     */
    public void setAddresses(AddressesListUpdater value) {
        this.addresses = value;
    }

    /**
     * Gets the value of the memberships property.
     * 
     * @return
     *     possible object is
     *     {@link CommunityListUpdater }
     *     
     */
    public CommunityListUpdater getMemberships() {
        return memberships;
    }

    /**
     * Sets the value of the memberships property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunityListUpdater }
     *     
     */
    public void setMemberships(CommunityListUpdater value) {
        this.memberships = value;
    }

    /**
     * Gets the value of the preferenceProfiles property.
     * 
     * @return
     *     possible object is
     *     {@link PreferenceProfilesListUpdater }
     *     
     */
    public PreferenceProfilesListUpdater getPreferenceProfiles() {
        return preferenceProfiles;
    }

    /**
     * Sets the value of the preferenceProfiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceProfilesListUpdater }
     *     
     */
    public void setPreferenceProfiles(PreferenceProfilesListUpdater value) {
        this.preferenceProfiles = value;
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
     * Gets the value of the propertiesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public CollabPropertiesUpdater getPropertiesUpdater() {
        return propertiesUpdater;
    }

    /**
     * Sets the value of the propertiesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public void setPropertiesUpdater(CollabPropertiesUpdater value) {
        this.propertiesUpdater = value;
    }

}
