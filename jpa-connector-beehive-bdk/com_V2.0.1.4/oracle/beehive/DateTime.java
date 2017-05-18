//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for dateTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dateTime">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="floatingTime" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="scheduledTimeZone" type="{http://www.oracle.com/beehive}timeZone" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="timestampTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dateTime", propOrder = {
    "dateOnly",
    "floatingTime",
    "scheduledTimeZone",
    "timestamp",
    "timestampTime"
})
public class DateTime {

    protected boolean dateOnly;
    protected boolean floatingTime;
    protected TimeZone scheduledTimeZone;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    protected long timestampTime;

    /**
     * Gets the value of the dateOnly property.
     * 
     */
    public boolean isDateOnly() {
        return dateOnly;
    }

    /**
     * Sets the value of the dateOnly property.
     * 
     */
    public void setDateOnly(boolean value) {
        this.dateOnly = value;
    }

    /**
     * Gets the value of the floatingTime property.
     * 
     */
    public boolean isFloatingTime() {
        return floatingTime;
    }

    /**
     * Sets the value of the floatingTime property.
     * 
     */
    public void setFloatingTime(boolean value) {
        this.floatingTime = value;
    }

    /**
     * Gets the value of the scheduledTimeZone property.
     * 
     * @return
     *     possible object is
     *     {@link TimeZone }
     *     
     */
    public TimeZone getScheduledTimeZone() {
        return scheduledTimeZone;
    }

    /**
     * Sets the value of the scheduledTimeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeZone }
     *     
     */
    public void setScheduledTimeZone(TimeZone value) {
        this.scheduledTimeZone = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the timestampTime property.
     * 
     */
    public long getTimestampTime() {
        return timestampTime;
    }

    /**
     * Sets the value of the timestampTime property.
     * 
     */
    public void setTimestampTime(long value) {
        this.timestampTime = value;
    }

}
