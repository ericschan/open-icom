//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for connectionRequestResolutionStatusPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="connectionRequestResolutionStatusPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="expertiseRequestResolutionStatus" type="{http://www.oracle.com/beehive}connectionRequestResolutionStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "connectionRequestResolutionStatusPredicate", propOrder = {
    "expertiseRequestResolutionStatus"
})
public class ConnectionRequestResolutionStatusPredicate
    extends Predicate
{

    protected ConnectionRequestResolutionStatus expertiseRequestResolutionStatus;

    /**
     * Gets the value of the expertiseRequestResolutionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionRequestResolutionStatus }
     *     
     */
    public ConnectionRequestResolutionStatus getExpertiseRequestResolutionStatus() {
        return expertiseRequestResolutionStatus;
    }

    /**
     * Sets the value of the expertiseRequestResolutionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionRequestResolutionStatus }
     *     
     */
    public void setExpertiseRequestResolutionStatus(ConnectionRequestResolutionStatus value) {
        this.expertiseRequestResolutionStatus = value;
    }

}
