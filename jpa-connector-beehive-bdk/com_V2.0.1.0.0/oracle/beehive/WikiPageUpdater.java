//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wikiPageUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wikiPageUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extractReferences" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="incrementViewCount" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="updateMode" type="{http://www.oracle.com/beehive}updateMode" minOccurs="0"/>
 *         &lt;element name="updatedBody" type="{http://www.oracle.com/beehive}contentUpdater" minOccurs="0"/>
 *         &lt;element name="versionDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versionUpdateMode" type="{http://www.oracle.com/beehive}versionUpdateMode" minOccurs="0"/>
 *         &lt;element name="wikiPageId" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="wikiPageRenderMode" type="{http://www.oracle.com/beehive}wikiPageRenderMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wikiPageUpdater", propOrder = {
    "description",
    "extractReferences",
    "incrementViewCount",
    "updateMode",
    "updatedBody",
    "versionDescription",
    "versionUpdateMode",
    "wikiPageId",
    "wikiPageRenderMode"
})
@XmlRootElement(name = "wikiPageUpdater")
public class WikiPageUpdater
    extends ArtifactUpdater
{

    protected String description;
    protected Boolean extractReferences;
    protected Boolean incrementViewCount;
    protected UpdateMode updateMode;
    protected ContentUpdater updatedBody;
    protected String versionDescription;
    protected VersionUpdateMode versionUpdateMode;
    protected BeeId wikiPageId;
    protected WikiPageRenderMode wikiPageRenderMode;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the extractReferences property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExtractReferences() {
        return extractReferences;
    }

    /**
     * Sets the value of the extractReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExtractReferences(Boolean value) {
        this.extractReferences = value;
    }

    /**
     * Gets the value of the incrementViewCount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncrementViewCount() {
        return incrementViewCount;
    }

    /**
     * Sets the value of the incrementViewCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncrementViewCount(Boolean value) {
        this.incrementViewCount = value;
    }

    /**
     * Gets the value of the updateMode property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateMode }
     *     
     */
    public UpdateMode getUpdateMode() {
        return updateMode;
    }

    /**
     * Sets the value of the updateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateMode }
     *     
     */
    public void setUpdateMode(UpdateMode value) {
        this.updateMode = value;
    }

    /**
     * Gets the value of the updatedBody property.
     * 
     * @return
     *     possible object is
     *     {@link ContentUpdater }
     *     
     */
    public ContentUpdater getUpdatedBody() {
        return updatedBody;
    }

    /**
     * Sets the value of the updatedBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentUpdater }
     *     
     */
    public void setUpdatedBody(ContentUpdater value) {
        this.updatedBody = value;
    }

    /**
     * Gets the value of the versionDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionDescription() {
        return versionDescription;
    }

    /**
     * Sets the value of the versionDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionDescription(String value) {
        this.versionDescription = value;
    }

    /**
     * Gets the value of the versionUpdateMode property.
     * 
     * @return
     *     possible object is
     *     {@link VersionUpdateMode }
     *     
     */
    public VersionUpdateMode getVersionUpdateMode() {
        return versionUpdateMode;
    }

    /**
     * Sets the value of the versionUpdateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link VersionUpdateMode }
     *     
     */
    public void setVersionUpdateMode(VersionUpdateMode value) {
        this.versionUpdateMode = value;
    }

    /**
     * Gets the value of the wikiPageId property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getWikiPageId() {
        return wikiPageId;
    }

    /**
     * Sets the value of the wikiPageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setWikiPageId(BeeId value) {
        this.wikiPageId = value;
    }

    /**
     * Gets the value of the wikiPageRenderMode property.
     * 
     * @return
     *     possible object is
     *     {@link WikiPageRenderMode }
     *     
     */
    public WikiPageRenderMode getWikiPageRenderMode() {
        return wikiPageRenderMode;
    }

    /**
     * Sets the value of the wikiPageRenderMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link WikiPageRenderMode }
     *     
     */
    public void setWikiPageRenderMode(WikiPageRenderMode value) {
        this.wikiPageRenderMode = value;
    }

}
