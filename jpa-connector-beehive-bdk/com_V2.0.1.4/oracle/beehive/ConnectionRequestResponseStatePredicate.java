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
 * <p>Java class for connectionRequestResponseStatePredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="connectionRequestResponseStatePredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="expertiseRequestResponseState" type="{http://www.oracle.com/beehive}connectionRequestResponseState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "connectionRequestResponseStatePredicate", propOrder = {
    "expertiseRequestResponseState"
})
public class ConnectionRequestResponseStatePredicate
    extends Predicate
{

    protected ConnectionRequestResponseState expertiseRequestResponseState;

    /**
     * Gets the value of the expertiseRequestResponseState property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionRequestResponseState }
     *     
     */
    public ConnectionRequestResponseState getExpertiseRequestResponseState() {
        return expertiseRequestResponseState;
    }

    /**
     * Sets the value of the expertiseRequestResponseState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionRequestResponseState }
     *     
     */
    public void setExpertiseRequestResponseState(ConnectionRequestResponseState value) {
        this.expertiseRequestResponseState = value;
    }

}
