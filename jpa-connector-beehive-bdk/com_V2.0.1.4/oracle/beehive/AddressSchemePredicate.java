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
 * <p>Java class for addressSchemePredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addressSchemePredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="addressSchemeMatch" type="{http://www.oracle.com/beehive}entityAddressScheme" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addressSchemePredicate", propOrder = {
    "addressSchemeMatch"
})
public class AddressSchemePredicate
    extends Predicate
{

    protected EntityAddressScheme addressSchemeMatch;

    /**
     * Gets the value of the addressSchemeMatch property.
     * 
     * @return
     *     possible object is
     *     {@link EntityAddressScheme }
     *     
     */
    public EntityAddressScheme getAddressSchemeMatch() {
        return addressSchemeMatch;
    }

    /**
     * Sets the value of the addressSchemeMatch property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityAddressScheme }
     *     
     */
    public void setAddressSchemeMatch(EntityAddressScheme value) {
        this.addressSchemeMatch = value;
    }

}
