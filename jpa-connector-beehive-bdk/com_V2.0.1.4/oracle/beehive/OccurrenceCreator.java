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
 * <p>Java class for occurrenceCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrenceCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="calendarHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="occurUpdater" type="{http://www.oracle.com/beehive}occurrenceUpdater" minOccurs="0"/>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occurrenceCreator", propOrder = {
    "calendarHandle",
    "occurUpdater",
    "uid"
})
public class OccurrenceCreator
    extends EntityCreator
{

    protected BeeId calendarHandle;
    protected OccurrenceUpdater occurUpdater;
    protected String uid;

    /**
     * Gets the value of the calendarHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getCalendarHandle() {
        return calendarHandle;
    }

    /**
     * Sets the value of the calendarHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setCalendarHandle(BeeId value) {
        this.calendarHandle = value;
    }

    /**
     * Gets the value of the occurUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceUpdater }
     *     
     */
    public OccurrenceUpdater getOccurUpdater() {
        return occurUpdater;
    }

    /**
     * Sets the value of the occurUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceUpdater }
     *     
     */
    public void setOccurUpdater(OccurrenceUpdater value) {
        this.occurUpdater = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

}