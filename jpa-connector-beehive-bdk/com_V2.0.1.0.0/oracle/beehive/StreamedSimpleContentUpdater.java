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
 * <p>Java class for streamedSimpleContentUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="streamedSimpleContentUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}simpleContentBaseUpdater">
 *       &lt;sequence>
 *         &lt;element name="contentStreamId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.oracle.com/beehive}rawString" minOccurs="0"/>
 *         &lt;element name="streamSubject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "contentStreamId",
    "name",
    "streamSubject"
})
public class StreamedSimpleContentUpdater
    extends SimpleContentBaseUpdater
{

    protected String contentStreamId;
    protected RawString name;
    protected String streamSubject;

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

    /**
     * Gets the value of the streamSubject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreamSubject() {
        return streamSubject;
    }

    /**
     * Sets the value of the streamSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreamSubject(String value) {
        this.streamSubject = value;
    }

}
