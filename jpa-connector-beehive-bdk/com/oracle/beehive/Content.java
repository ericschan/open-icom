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
 * <p>Java class for content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="content">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}snapshot">
 *       &lt;sequence>
 *         &lt;element name="contentDisposition" type="{http://www.oracle.com/beehive}contentDispositionType" minOccurs="0"/>
 *         &lt;element name="contentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mimeHeaders" type="{http://www.oracle.com/beehive}mimeHeadersWrapper" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}contentType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "content", propOrder = {
    "contentDisposition",
    "contentId",
    "mediaType",
    "mimeHeaders",
    "type"
})
@XmlSeeAlso({
    FaxMessageContent.class,
    EmailMessageContent.class,
    VoiceMessageContent.class,
    OnlineContent.class,
    IdentifiableSimpleContent.class,
    SimpleContent.class,
    MultiContent.class
})
public abstract class Content
    extends Snapshot
{

    protected ContentDispositionType contentDisposition;
    protected String contentId;
    protected String mediaType;
    protected MimeHeadersWrapper mimeHeaders;
    protected ContentType type;

    /**
     * Gets the value of the contentDisposition property.
     * 
     * @return
     *     possible object is
     *     {@link ContentDispositionType }
     *     
     */
    public ContentDispositionType getContentDisposition() {
        return contentDisposition;
    }

    /**
     * Sets the value of the contentDisposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentDispositionType }
     *     
     */
    public void setContentDisposition(ContentDispositionType value) {
        this.contentDisposition = value;
    }

    /**
     * Gets the value of the contentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * Sets the value of the contentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentId(String value) {
        this.contentId = value;
    }

    /**
     * Gets the value of the mediaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets the value of the mediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaType(String value) {
        this.mediaType = value;
    }

    /**
     * Gets the value of the mimeHeaders property.
     * 
     * @return
     *     possible object is
     *     {@link MimeHeadersWrapper }
     *     
     */
    public MimeHeadersWrapper getMimeHeaders() {
        return mimeHeaders;
    }

    /**
     * Sets the value of the mimeHeaders property.
     * 
     * @param value
     *     allowed object is
     *     {@link MimeHeadersWrapper }
     *     
     */
    public void setMimeHeaders(MimeHeadersWrapper value) {
        this.mimeHeaders = value;
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

}
