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
 * <p>Java class for updateOccurrenceKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateOccurrenceKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="occurrenceHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="recurrenceId" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOccurrenceKey", propOrder = {
    "occurrenceHandle",
    "recurrenceId"
})
public class UpdateOccurrenceKey {

    protected BeeId occurrenceHandle;
    protected DateTime recurrenceId;

    /**
     * Gets the value of the occurrenceHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getOccurrenceHandle() {
        return occurrenceHandle;
    }

    /**
     * Sets the value of the occurrenceHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setOccurrenceHandle(BeeId value) {
        this.occurrenceHandle = value;
    }

    /**
     * Gets the value of the recurrenceId property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getRecurrenceId() {
        return recurrenceId;
    }

    /**
     * Sets the value of the recurrenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setRecurrenceId(DateTime value) {
        this.recurrenceId = value;
    }

}
