//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ace">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accessTypes" type="{http://www.oracle.com/beehive}accessTypes"/>
 *         &lt;element name="accessor" type="{http://www.oracle.com/beehive}beeId"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ace", propOrder = {
    "accessTypes",
    "accessor"
})
public class Ace {

    @XmlElement(required = true)
    protected AccessTypes accessTypes;
    @XmlElement(required = true)
    protected BeeId accessor;

    /**
     * Gets the value of the accessTypes property.
     * 
     * @return
     *     possible object is
     *     {@link AccessTypes }
     *     
     */
    public AccessTypes getAccessTypes() {
        return accessTypes;
    }

    /**
     * Sets the value of the accessTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessTypes }
     *     
     */
    public void setAccessTypes(AccessTypes value) {
        this.accessTypes = value;
    }

    /**
     * Gets the value of the accessor property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getAccessor() {
        return accessor;
    }

    /**
     * Sets the value of the accessor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setAccessor(BeeId value) {
        this.accessor = value;
    }

}
