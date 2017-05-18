//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for weekShift complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="weekShift">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="endDay" type="{http://www.oracle.com/beehive}weekDay"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startDay" type="{http://www.oracle.com/beehive}weekDay"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}shiftType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "weekShift", propOrder = {
    "endDay",
    "endTime",
    "startDay",
    "startTime",
    "type"
})
public class WeekShift {

    @XmlElement(required = true)
    protected WeekDay endDay;
    @XmlElement(required = true)
    protected String endTime;
    @XmlElement(required = true)
    protected WeekDay startDay;
    @XmlElement(required = true)
    protected String startTime;
    @XmlElement(required = true)
    protected ShiftType type;

    /**
     * Gets the value of the endDay property.
     * 
     * @return
     *     possible object is
     *     {@link WeekDay }
     *     
     */
    public WeekDay getEndDay() {
        return endDay;
    }

    /**
     * Sets the value of the endDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeekDay }
     *     
     */
    public void setEndDay(WeekDay value) {
        this.endDay = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the startDay property.
     * 
     * @return
     *     possible object is
     *     {@link WeekDay }
     *     
     */
    public WeekDay getStartDay() {
        return startDay;
    }

    /**
     * Sets the value of the startDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeekDay }
     *     
     */
    public void setStartDay(WeekDay value) {
        this.startDay = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ShiftType }
     *     
     */
    public ShiftType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShiftType }
     *     
     */
    public void setType(ShiftType value) {
        this.type = value;
    }

}
