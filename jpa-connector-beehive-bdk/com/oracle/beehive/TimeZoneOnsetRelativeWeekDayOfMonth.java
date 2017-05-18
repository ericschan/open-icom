//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for timeZoneOnsetRelativeWeekDayOfMonth complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="timeZoneOnsetRelativeWeekDayOfMonth">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}timeZoneOnsetMovableDay">
 *       &lt;sequence>
 *         &lt;element name="day" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="relation" type="{http://www.oracle.com/beehive}dateRelation"/>
 *         &lt;element name="weekDay" type="{http://www.oracle.com/beehive}weekDay"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timeZoneOnsetRelativeWeekDayOfMonth", propOrder = {
    "day",
    "relation",
    "weekDay"
})
public class TimeZoneOnsetRelativeWeekDayOfMonth
    extends TimeZoneOnsetMovableDay
{

    protected int day;
    @XmlElement(required = true)
    protected DateRelation relation;
    @XmlElement(required = true)
    protected WeekDay weekDay;

    /**
     * Gets the value of the day property.
     * 
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the value of the day property.
     * 
     */
    public void setDay(int value) {
        this.day = value;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link DateRelation }
     *     
     */
    public DateRelation getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateRelation }
     *     
     */
    public void setRelation(DateRelation value) {
        this.relation = value;
    }

    /**
     * Gets the value of the weekDay property.
     * 
     * @return
     *     possible object is
     *     {@link WeekDay }
     *     
     */
    public WeekDay getWeekDay() {
        return weekDay;
    }

    /**
     * Sets the value of the weekDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeekDay }
     *     
     */
    public void setWeekDay(WeekDay value) {
        this.weekDay = value;
    }

}