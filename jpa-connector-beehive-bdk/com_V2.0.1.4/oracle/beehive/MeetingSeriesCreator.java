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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for meetingSeriesCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meetingSeriesCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="calendar" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *         &lt;element ref="{http://www.oracle.com/beehive}meetingSeriesUpdater" minOccurs="0"/>
 *         &lt;element name="recurrenceRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recurrenceStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}occurrenceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meetingSeriesCreator", propOrder = {
    "calendar",
    "duration",
    "meetingSeriesUpdater",
    "recurrenceRule",
    "recurrenceStart",
    "type"
})
@XmlRootElement(name = "meetingSeriesCreator")
public class MeetingSeriesCreator
    extends EntityCreator
{

    protected BeeId calendar;
    protected Duration duration;
    protected MeetingSeriesUpdater meetingSeriesUpdater;
    protected String recurrenceRule;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar recurrenceStart;
    protected OccurrenceType type;

    /**
     * Gets the value of the calendar property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getCalendar() {
        return calendar;
    }

    /**
     * Sets the value of the calendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setCalendar(BeeId value) {
        this.calendar = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDuration(Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the meetingSeriesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link MeetingSeriesUpdater }
     *     
     */
    public MeetingSeriesUpdater getMeetingSeriesUpdater() {
        return meetingSeriesUpdater;
    }

    /**
     * Sets the value of the meetingSeriesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeetingSeriesUpdater }
     *     
     */
    public void setMeetingSeriesUpdater(MeetingSeriesUpdater value) {
        this.meetingSeriesUpdater = value;
    }

    /**
     * Gets the value of the recurrenceRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    /**
     * Sets the value of the recurrenceRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurrenceRule(String value) {
        this.recurrenceRule = value;
    }

    /**
     * Gets the value of the recurrenceStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRecurrenceStart() {
        return recurrenceStart;
    }

    /**
     * Sets the value of the recurrenceStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRecurrenceStart(XMLGregorianCalendar value) {
        this.recurrenceStart = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceType }
     *     
     */
    public OccurrenceType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceType }
     *     
     */
    public void setType(OccurrenceType value) {
        this.type = value;
    }

}