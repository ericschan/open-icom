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
 * <p>Java class for instantMessageBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="instantMessageBody">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}simpleContent">
 *       &lt;sequence>
 *         &lt;element name="empty" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="stringContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "instantMessageBody", propOrder = {
    "empty",
    "stringContent",
    "uri"
})
public class InstantMessageBody
    extends SimpleContent
{

    protected boolean empty;
    protected String stringContent;
    protected String uri;

    /**
     * Gets the value of the empty property.
     * 
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Sets the value of the empty property.
     * 
     */
    public void setEmpty(boolean value) {
        this.empty = value;
    }

    /**
     * Gets the value of the stringContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringContent() {
        return stringContent;
    }

    /**
     * Sets the value of the stringContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringContent(String value) {
        this.stringContent = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

}
