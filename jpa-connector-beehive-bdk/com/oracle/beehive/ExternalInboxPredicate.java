//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for externalInboxPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="externalInboxPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="isExternalInboxMatch" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "externalInboxPredicate", propOrder = {
    "isExternalInboxMatch"
})
public class ExternalInboxPredicate
    extends Predicate
{

    protected Boolean isExternalInboxMatch;

    /**
     * Gets the value of the isExternalInboxMatch property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsExternalInboxMatch() {
        return isExternalInboxMatch;
    }

    /**
     * Sets the value of the isExternalInboxMatch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsExternalInboxMatch(Boolean value) {
        this.isExternalInboxMatch = value;
    }

}
