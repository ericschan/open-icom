//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for announcement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="announcement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}topic">
 *       &lt;sequence>
 *         &lt;element name="activatesOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="announcementStatus" type="{http://www.oracle.com/beehive}announcementStatus" minOccurs="0"/>
 *         &lt;element name="expiresOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "announcement", propOrder = {
    "activatesOn",
    "announcementStatus",
    "expiresOn"
})
@XmlRootElement(name = "announcement")
public class Announcement
    extends Topic
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar activatesOn;
    protected AnnouncementStatus announcementStatus;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiresOn;

    /**
     * Gets the value of the activatesOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActivatesOn() {
        return activatesOn;
    }

    /**
     * Sets the value of the activatesOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActivatesOn(XMLGregorianCalendar value) {
        this.activatesOn = value;
    }

    /**
     * Gets the value of the announcementStatus property.
     * 
     * @return
     *     possible object is
     *     {@link AnnouncementStatus }
     *     
     */
    public AnnouncementStatus getAnnouncementStatus() {
        return announcementStatus;
    }

    /**
     * Sets the value of the announcementStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnouncementStatus }
     *     
     */
    public void setAnnouncementStatus(AnnouncementStatus value) {
        this.announcementStatus = value;
    }

    /**
     * Gets the value of the expiresOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiresOn() {
        return expiresOn;
    }

    /**
     * Sets the value of the expiresOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiresOn(XMLGregorianCalendar value) {
        this.expiresOn = value;
    }

}
