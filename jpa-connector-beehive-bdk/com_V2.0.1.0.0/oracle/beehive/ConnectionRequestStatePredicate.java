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
 * <p>Java class for connectionRequestStatePredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="connectionRequestStatePredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="expertiseRequestState" type="{http://www.oracle.com/beehive}connectionRequestState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "connectionRequestStatePredicate", propOrder = {
    "expertiseRequestState"
})
public class ConnectionRequestStatePredicate
    extends Predicate
{

    protected ConnectionRequestState expertiseRequestState;

    /**
     * Gets the value of the expertiseRequestState property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionRequestState }
     *     
     */
    public ConnectionRequestState getExpertiseRequestState() {
        return expertiseRequestState;
    }

    /**
     * Sets the value of the expertiseRequestState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionRequestState }
     *     
     */
    public void setExpertiseRequestState(ConnectionRequestState value) {
        this.expertiseRequestState = value;
    }

}
