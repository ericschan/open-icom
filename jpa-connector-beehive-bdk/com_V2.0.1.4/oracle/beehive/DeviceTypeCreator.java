//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deviceTypeCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deviceTypeCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="deviceClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dph" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="os" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updater" type="{http://www.oracle.com/beehive}deviceTypeUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deviceTypeCreator", propOrder = {
    "deviceClass",
    "dph",
    "os",
    "processor",
    "updater"
})
public class DeviceTypeCreator
    extends EntityCreator
{

    protected String deviceClass;
    protected BeeId dph;
    protected String os;
    protected String processor;
    protected DeviceTypeUpdater updater;

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
     * Gets the value of the dph property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getDph() {
        return dph;
    }

    /**
     * Sets the value of the dph property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setDph(BeeId value) {
        this.dph = value;
    }

    /**
     * Gets the value of the os property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOs() {
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
    public void setOs(String value) {
        this.os = value;
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
     * Gets the value of the updater property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceTypeUpdater }
     *     
     */
    public DeviceTypeUpdater getUpdater() {
        return updater;
    }

    /**
     * Sets the value of the updater property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceTypeUpdater }
     *     
     */
    public void setUpdater(DeviceTypeUpdater value) {
        this.updater = value;
    }

}