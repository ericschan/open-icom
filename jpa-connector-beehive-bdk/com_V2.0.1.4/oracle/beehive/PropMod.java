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
 * <p>Java class for propMod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="propMod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="collabProperty" type="{http://www.oracle.com/beehive}collabProperty"/>
 *         &lt;element name="operation" type="{http://www.oracle.com/beehive}PropModOperation"/>
 *         &lt;element name="propertyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "propMod", propOrder = {
    "collabProperty",
    "operation",
    "propertyName"
})
public class PropMod {

    @XmlElement(required = true)
    protected CollabProperty collabProperty;
    @XmlElement(required = true)
    protected PropModOperation operation;
    @XmlElement(required = true)
    protected String propertyName;

    /**
     * Gets the value of the collabProperty property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperty }
     *     
     */
    public CollabProperty getCollabProperty() {
        return collabProperty;
    }

    /**
     * Sets the value of the collabProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperty }
     *     
     */
    public void setCollabProperty(CollabProperty value) {
        this.collabProperty = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link PropModOperation }
     *     
     */
    public PropModOperation getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropModOperation }
     *     
     */
    public void setOperation(PropModOperation value) {
        this.operation = value;
    }

    /**
     * Gets the value of the propertyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the value of the propertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyName(String value) {
        this.propertyName = value;
    }

}
