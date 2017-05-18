//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for externalResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="externalResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}agent">
 *       &lt;sequence>
 *         &lt;element name="bookingInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="defaultAddressForScheme" type="{http://www.oracle.com/beehive}entityAddress" minOccurs="0"/>
 *         &lt;element name="defaultAddressForType" type="{http://www.oracle.com/beehive}entityAddress" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="properties" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="resourceType" type="{http://www.oracle.com/beehive}resourceType" minOccurs="0"/>
 *         &lt;element name="staticLocation" type="{http://www.oracle.com/beehive}location" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "externalResource", propOrder = {
    "bookingInfo",
    "capacity",
    "defaultAddressForScheme",
    "defaultAddressForType",
    "description",
    "properties",
    "resourceType",
    "staticLocation"
})
@XmlRootElement(name = "externalResource")
public class ExternalResource
    extends Agent
{

    protected String bookingInfo;
    protected Integer capacity;
    protected EntityAddress defaultAddressForScheme;
    protected EntityAddress defaultAddressForType;
    protected String description;
    protected CollabProperties properties;
    protected ResourceType resourceType;
    protected Location staticLocation;

    /**
     * Gets the value of the bookingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingInfo() {
        return bookingInfo;
    }

    /**
     * Sets the value of the bookingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingInfo(String value) {
        this.bookingInfo = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapacity(Integer value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the defaultAddressForScheme property.
     * 
     * @return
     *     possible object is
     *     {@link EntityAddress }
     *     
     */
    public EntityAddress getDefaultAddressForScheme() {
        return defaultAddressForScheme;
    }

    /**
     * Sets the value of the defaultAddressForScheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityAddress }
     *     
     */
    public void setDefaultAddressForScheme(EntityAddress value) {
        this.defaultAddressForScheme = value;
    }

    /**
     * Gets the value of the defaultAddressForType property.
     * 
     * @return
     *     possible object is
     *     {@link EntityAddress }
     *     
     */
    public EntityAddress getDefaultAddressForType() {
        return defaultAddressForType;
    }

    /**
     * Sets the value of the defaultAddressForType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityAddress }
     *     
     */
    public void setDefaultAddressForType(EntityAddress value) {
        this.defaultAddressForType = value;
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
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setProperties(CollabProperties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setResourceType(ResourceType value) {
        this.resourceType = value;
    }

    /**
     * Gets the value of the staticLocation property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getStaticLocation() {
        return staticLocation;
    }

    /**
     * Sets the value of the staticLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setStaticLocation(Location value) {
        this.staticLocation = value;
    }

}
