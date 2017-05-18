//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for simpleContentBaseUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="simpleContentBaseUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}contentUpdater">
 *       &lt;sequence>
 *         &lt;element name="characterEncoding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contentEncoding" type="{http://www.oracle.com/beehive}contentEncodingType" minOccurs="0"/>
 *         &lt;element name="contentLanguage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simpleContentBaseUpdater", propOrder = {
    "characterEncoding",
    "contentEncoding",
    "contentLanguage"
})
@XmlSeeAlso({
    SimpleContentUpdater.class,
    IdentifiableSimpleContentUpdater.class,
    StreamedSimpleContentUpdater.class
})
public abstract class SimpleContentBaseUpdater
    extends ContentUpdater
{

    protected String characterEncoding;
    protected ContentEncodingType contentEncoding;
    protected String contentLanguage;

    /**
     * Gets the value of the characterEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    /**
     * Sets the value of the characterEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterEncoding(String value) {
        this.characterEncoding = value;
    }

    /**
     * Gets the value of the contentEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link ContentEncodingType }
     *     
     */
    public ContentEncodingType getContentEncoding() {
        return contentEncoding;
    }

    /**
     * Sets the value of the contentEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentEncodingType }
     *     
     */
    public void setContentEncoding(ContentEncodingType value) {
        this.contentEncoding = value;
    }

    /**
     * Gets the value of the contentLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentLanguage() {
        return contentLanguage;
    }

    /**
     * Sets the value of the contentLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentLanguage(String value) {
        this.contentLanguage = value;
    }

}
