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
 * <p>Java class for workflowSchemaUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workflowSchemaUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityUpdater">
 *       &lt;sequence>
 *         &lt;element name="applicationImage" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="attributesUpdater" type="{http://www.oracle.com/beehive}attributeDefinitionListUpdater" minOccurs="0"/>
 *         &lt;element name="blocking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="defaultTemplatesUpdater" type="{http://www.oracle.com/beehive}attributeTemplateListUpdater" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workflowSchemaUpdater", propOrder = {
    "applicationImage",
    "attributesUpdater",
    "blocking",
    "defaultTemplatesUpdater",
    "description",
    "enabled"
})
public class WorkflowSchemaUpdater
    extends EntityUpdater
{

    protected byte[] applicationImage;
    protected AttributeDefinitionListUpdater attributesUpdater;
    protected boolean blocking;
    protected AttributeTemplateListUpdater defaultTemplatesUpdater;
    protected String description;
    protected boolean enabled;

    /**
     * Gets the value of the applicationImage property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getApplicationImage() {
        return applicationImage;
    }

    /**
     * Sets the value of the applicationImage property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setApplicationImage(byte[] value) {
        this.applicationImage = ((byte[]) value);
    }

    /**
     * Gets the value of the attributesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDefinitionListUpdater }
     *     
     */
    public AttributeDefinitionListUpdater getAttributesUpdater() {
        return attributesUpdater;
    }

    /**
     * Sets the value of the attributesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDefinitionListUpdater }
     *     
     */
    public void setAttributesUpdater(AttributeDefinitionListUpdater value) {
        this.attributesUpdater = value;
    }

    /**
     * Gets the value of the blocking property.
     * 
     */
    public boolean isBlocking() {
        return blocking;
    }

    /**
     * Sets the value of the blocking property.
     * 
     */
    public void setBlocking(boolean value) {
        this.blocking = value;
    }

    /**
     * Gets the value of the defaultTemplatesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public AttributeTemplateListUpdater getDefaultTemplatesUpdater() {
        return defaultTemplatesUpdater;
    }

    /**
     * Sets the value of the defaultTemplatesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public void setDefaultTemplatesUpdater(AttributeTemplateListUpdater value) {
        this.defaultTemplatesUpdater = value;
    }

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
     * Gets the value of the enabled property.
     * 
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     */
    public void setEnabled(boolean value) {
        this.enabled = value;
    }

}