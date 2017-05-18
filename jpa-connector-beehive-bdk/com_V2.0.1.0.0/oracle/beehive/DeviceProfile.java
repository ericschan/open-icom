//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deviceProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deviceProfile">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="activePreferenceProfile" type="{http://www.oracle.com/beehive}preferenceProfile" minOccurs="0"/>
 *         &lt;element name="availablePreferenceProfiles" type="{http://www.oracle.com/beehive}preferenceProfile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="capabilities" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="configuration" type="{http://www.oracle.com/beehive}preferenceProfile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deviceProfile", propOrder = {
    "activePreferenceProfile",
    "availablePreferenceProfiles",
    "capabilities",
    "configuration"
})
public class DeviceProfile
    extends Entity
{

    protected PreferenceProfile activePreferenceProfile;
    protected List<PreferenceProfile> availablePreferenceProfiles;
    protected CollabProperties capabilities;
    protected PreferenceProfile configuration;

    /**
     * Gets the value of the activePreferenceProfile property.
     * 
     * @return
     *     possible object is
     *     {@link PreferenceProfile }
     *     
     */
    public PreferenceProfile getActivePreferenceProfile() {
        return activePreferenceProfile;
    }

    /**
     * Sets the value of the activePreferenceProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceProfile }
     *     
     */
    public void setActivePreferenceProfile(PreferenceProfile value) {
        this.activePreferenceProfile = value;
    }

    /**
     * Gets the value of the availablePreferenceProfiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availablePreferenceProfiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailablePreferenceProfiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreferenceProfile }
     * 
     * 
     */
    public List<PreferenceProfile> getAvailablePreferenceProfiles() {
        if (availablePreferenceProfiles == null) {
            availablePreferenceProfiles = new ArrayList<PreferenceProfile>();
        }
        return this.availablePreferenceProfiles;
    }

    /**
     * Gets the value of the capabilities property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getCapabilities() {
        return capabilities;
    }

    /**
     * Sets the value of the capabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setCapabilities(CollabProperties value) {
        this.capabilities = value;
    }

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link PreferenceProfile }
     *     
     */
    public PreferenceProfile getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceProfile }
     *     
     */
    public void setConfiguration(PreferenceProfile value) {
        this.configuration = value;
    }

}