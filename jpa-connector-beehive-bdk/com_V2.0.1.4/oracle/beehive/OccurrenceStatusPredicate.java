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
 * <p>Java class for occurrenceStatusPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrenceStatusPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}occurrencePredicate">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}occurrenceStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occurrenceStatusPredicate", propOrder = {
    "status"
})
public class OccurrenceStatusPredicate
    extends OccurrencePredicate
{

    protected OccurrenceStatus status;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceStatus }
     *     
     */
    public OccurrenceStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceStatus }
     *     
     */
    public void setStatus(OccurrenceStatus value) {
        this.status = value;
    }

}
