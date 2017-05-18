//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for notificationSchemaUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notificationSchemaUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityUpdater">
 *       &lt;sequence>
 *         &lt;element name="attributesUpdater" type="{http://www.oracle.com/beehive}attributeDefinitionListUpdater" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventMapping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="renderers" type="{http://www.oracle.com/beehive}notificationContentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="renderersUpdater" type="{http://www.oracle.com/beehive}notificationContentInfoListUpdater" minOccurs="0"/>
 *         &lt;element name="resourcePackage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificationSchemaUpdater", propOrder = {
    "attributesUpdater",
    "description",
    "eventMapping",
    "renderers",
    "renderersUpdater",
    "resourcePackage",
    "subject"
})
public class NotificationSchemaUpdater
    extends EntityUpdater
{

    protected AttributeDefinitionListUpdater attributesUpdater;
    protected String description;
    protected String eventMapping;
    protected List<NotificationContentInfo> renderers;
    protected NotificationContentInfoListUpdater renderersUpdater;
    protected String resourcePackage;
    protected String subject;

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
     * Gets the value of the eventMapping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventMapping() {
        return eventMapping;
    }

    /**
     * Sets the value of the eventMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventMapping(String value) {
        this.eventMapping = value;
    }

    /**
     * Gets the value of the renderers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the renderers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRenderers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NotificationContentInfo }
     * 
     * 
     */
    public List<NotificationContentInfo> getRenderers() {
        if (renderers == null) {
            renderers = new ArrayList<NotificationContentInfo>();
        }
        return this.renderers;
    }

    /**
     * Gets the value of the renderersUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationContentInfoListUpdater }
     *     
     */
    public NotificationContentInfoListUpdater getRenderersUpdater() {
        return renderersUpdater;
    }

    /**
     * Sets the value of the renderersUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationContentInfoListUpdater }
     *     
     */
    public void setRenderersUpdater(NotificationContentInfoListUpdater value) {
        this.renderersUpdater = value;
    }

    /**
     * Gets the value of the resourcePackage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcePackage() {
        return resourcePackage;
    }

    /**
     * Sets the value of the resourcePackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcePackage(String value) {
        this.resourcePackage = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

}
