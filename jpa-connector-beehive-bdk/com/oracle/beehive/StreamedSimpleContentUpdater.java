//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for streamedSimpleContentUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="streamedSimpleContentUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}simpleContentBaseUpdater">
 *       &lt;sequence>
 *         &lt;element name="contentStream" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="contentStreamId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.oracle.com/beehive}rawString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "streamedSimpleContentUpdater", propOrder = {
    "contentStream",
    "contentStreamId",
    "name"
})
public class StreamedSimpleContentUpdater
    extends SimpleContentBaseUpdater
{

    protected byte[] contentStream;
    protected String contentStreamId;
    protected RawString name;

    /**
     * Gets the value of the contentStream property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContentStream() {
        return contentStream;
    }

    /**
     * Sets the value of the contentStream property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContentStream(byte[] value) {
        this.contentStream = ((byte[]) value);
    }

    /**
     * Gets the value of the contentStreamId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentStreamId() {
        return contentStreamId;
    }

    /**
     * Sets the value of the contentStreamId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentStreamId(String value) {
        this.contentStreamId = value;
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

}
