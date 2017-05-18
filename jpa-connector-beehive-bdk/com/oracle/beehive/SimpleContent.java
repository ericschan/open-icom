//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for simpleContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="simpleContent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}content">
 *       &lt;sequence>
 *         &lt;element name="characterEncoding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contentBytes" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="contentEncoding" type="{http://www.oracle.com/beehive}contentEncodingType" minOccurs="0"/>
 *         &lt;element name="contentLanguage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.oracle.com/beehive}rawString" minOccurs="0"/>
 *         &lt;element name="partIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simpleContent", propOrder = {
    "characterEncoding",
    "contentBytes",
    "contentEncoding",
    "contentLanguage",
    "name",
    "partIdentifier",
    "size"
})
@XmlSeeAlso({
    InstantMessageBody.class
})
public class SimpleContent
    extends Content
{

    protected String characterEncoding;
    protected byte[] contentBytes;
    protected ContentEncodingType contentEncoding;
    protected String contentLanguage;
    protected RawString name;
    protected String partIdentifier;
    protected long size;

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
     * Gets the value of the contentBytes property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContentBytes() {
        return contentBytes;
    }

    /**
     * Sets the value of the contentBytes property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContentBytes(byte[] value) {
        this.contentBytes = ((byte[]) value);
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

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link RawString }
     *     
     */
    public RawString getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link RawString }
     *     
     */
    public void setName(RawString value) {
        this.name = value;
    }

    /**
     * Gets the value of the partIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartIdentifier() {
        return partIdentifier;
    }

    /**
     * Sets the value of the partIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartIdentifier(String value) {
        this.partIdentifier = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(long value) {
        this.size = value;
    }

}
