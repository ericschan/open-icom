//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for occurrenceICalUidPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrenceICalUidPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}occurrencePredicate">
 *       &lt;sequence>
 *         &lt;element name="ICalUid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occurrenceICalUidPredicate", propOrder = {
    "iCalUid"
})
public class OccurrenceICalUidPredicate
    extends OccurrencePredicate
{

    @XmlElement(name = "ICalUid")
    protected String iCalUid;

    /**
     * Gets the value of the iCalUid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICalUid() {
        return iCalUid;
    }

    /**
     * Sets the value of the iCalUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICalUid(String value) {
        this.iCalUid = value;
    }

}
