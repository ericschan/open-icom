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
 * <p>Java class for faxMessageContentUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="faxMessageContentUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}contentUpdater">
 *       &lt;sequence>
 *         &lt;element name="callerPhoneNumber" type="{http://www.oracle.com/beehive}entityAddress" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}contentType" minOccurs="0"/>
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
@XmlType(name = "faxMessageContentUpdater", propOrder = {
    "callerPhoneNumber",
    "type",
    "uri"
})
public class FaxMessageContentUpdater
    extends ContentUpdater
{

    protected EntityAddress callerPhoneNumber;
    protected ContentType type;
    protected String uri;

    /**
     * Gets the value of the callerPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link EntityAddress }
     *     
     */
    public EntityAddress getCallerPhoneNumber() {
        return callerPhoneNumber;
    }

    /**
     * Sets the value of the callerPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityAddress }
     *     
     */
    public void setCallerPhoneNumber(EntityAddress value) {
        this.callerPhoneNumber = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ContentType }
     *     
     */
    public ContentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentType }
     *     
     */
    public void setType(ContentType value) {
        this.type = value;
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
