//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for invalidQueryParameterValueFailure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="invalidQueryParameterValueFailure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive/rest}failure">
 *       &lt;sequence>
 *         &lt;element name="queryParameterName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queryParameterValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invalidQueryParameterValueFailure", propOrder = {
    "queryParameterName",
    "queryParameterValue"
})
@XmlRootElement(name = "invalidQueryParameterValueFailure")
public class InvalidQueryParameterValueFailure
    extends Failure
{

    protected String queryParameterName;
    protected String queryParameterValue;

    /**
     * Gets the value of the queryParameterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryParameterName() {
        return queryParameterName;
    }

    /**
     * Sets the value of the queryParameterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryParameterName(String value) {
        this.queryParameterName = value;
    }

    /**
     * Gets the value of the queryParameterValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryParameterValue() {
        return queryParameterValue;
    }

    /**
     * Sets the value of the queryParameterValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryParameterValue(String value) {
        this.queryParameterValue = value;
    }

}
