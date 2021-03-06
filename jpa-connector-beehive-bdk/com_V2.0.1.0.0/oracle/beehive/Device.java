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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for device complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="device">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="activePreferenceProfile" type="{http://www.oracle.com/beehive}preferenceProfile" minOccurs="0"/>
 *         &lt;element name="addresses" type="{http://www.oracle.com/beehive}entityAddress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="availablePreferenceProfiles" type="{http://www.oracle.com/beehive}preferenceProfile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="devInfDTDVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceInfo" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="deviceType" type="{http://www.oracle.com/beehive}deviceType" minOccurs="0"/>
 *         &lt;element name="installedClientApplications" type="{http://www.oracle.com/beehive}installedClientApplication" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="manufacturer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pendingCommands" type="{http://www.oracle.com/beehive}deviceCommand" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="primaryAddress" type="{http://www.oracle.com/beehive}entityAddress" minOccurs="0"/>
 *         &lt;element name="processor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="softwareVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}deviceStatus" minOccurs="0"/>
 *         &lt;element name="user" type="{http://www.oracle.com/beehive}user" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "device", propOrder = {
    "activePreferenceProfile",
    "addresses",
    "availablePreferenceProfiles",
    "devInfDTDVersion",
    "deviceClass",
    "deviceId",
    "deviceInfo",
    "deviceType",
    "installedClientApplications",
    "manufacturer",
    "model",
    "os",
    "pendingCommands",
    "primaryAddress",
    "processor",
    "softwareVersion",
    "status",
    "user"
})
public class Device
    extends Entity
{

    protected PreferenceProfile activePreferenceProfile;
    @XmlElement(nillable = true)
    protected List<EntityAddress> addresses;
    protected List<PreferenceProfile> availablePreferenceProfiles;
    protected String devInfDTDVersion;
    protected String deviceClass;
    protected String deviceId;
    protected CollabProperties deviceInfo;
    protected DeviceType deviceType;
    @XmlElement(nillable = true)
    protected List<InstalledClientApplication> installedClientApplications;
    protected String manufacturer;
    protected String model;
    @XmlElement(name = "OS")
    protected String os;
    @XmlElement(nillable = true)
    protected List<DeviceCommand> pendingCommands;
    protected EntityAddress primaryAddress;
    protected String processor;
    protected String softwareVersion;
    protected DeviceStatus status;
    protected User user;

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
     * Gets the value of the devInfDTDVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevInfDTDVersion() {
        return devInfDTDVersion;
    }

    /**
     * Sets the value of the devInfDTDVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevInfDTDVersion(String value) {
        this.devInfDTDVersion = value;
    }

    /**
     * Gets the value of the deviceClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceClass() {
        return deviceClass;
    }

    /**
     * Sets the value of the deviceClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceClass(String value) {
        this.deviceClass = value;
    }

    /**
     * Gets the value of the deviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

    /**
     * Gets the value of the deviceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getDeviceInfo() {
        return deviceInfo;
    }

    /**
     * Sets the value of the deviceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setDeviceInfo(CollabProperties value) {
        this.deviceInfo = value;
    }

    /**
     * Gets the value of the deviceType property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceType }
     *     
     */
    public DeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the value of the deviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceType }
     *     
     */
    public void setDeviceType(DeviceType value) {
        this.deviceType = value;
    }

    /**
     * Gets the value of the installedClientApplications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the installedClientApplications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstalledClientApplications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstalledClientApplication }
     * 
     * 
     */
    public List<InstalledClientApplication> getInstalledClientApplications() {
        if (installedClientApplications == null) {
            installedClientApplications = new ArrayList<InstalledClientApplication>();
        }
        return this.installedClientApplications;
    }

    /**
     * Gets the value of the manufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the os property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOS() {
        return os;
    }

    /**
     * Sets the value of the os property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOS(String value) {
        this.os = value;
    }

    /**
     * Gets the value of the pendingCommands property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pendingCommands property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPendingCommands().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeviceCommand }
     * 
     * 
     */
    public List<DeviceCommand> getPendingCommands() {
        if (pendingCommands == null) {
            pendingCommands = new ArrayList<DeviceCommand>();
        }
        return this.pendingCommands;
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
     * Gets the value of the processor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessor() {
        return processor;
    }

    /**
     * Sets the value of the processor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessor(String value) {
        this.processor = value;
    }

    /**
     * Gets the value of the softwareVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * Sets the value of the softwareVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftwareVersion(String value) {
        this.softwareVersion = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceStatus }
     *     
     */
    public DeviceStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceStatus }
     *     
     */
    public void setStatus(DeviceStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

}
