//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for basicTemplateUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="basicTemplateUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}templateUpdater">
 *       &lt;sequence>
 *         &lt;element name="attributeTemplateListUpdater" type="{http://www.oracle.com/beehive}attributeTemplateListUpdater" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basicTemplateUpdater", propOrder = {
    "attributeTemplateListUpdater",
    "description"
})
@XmlSeeAlso({
    PolicyTemplateUpdater.class,
    CategoryTemplateUpdater.class
})
public class BasicTemplateUpdater
    extends TemplateUpdater
{

    protected AttributeTemplateListUpdater attributeTemplateListUpdater;
    protected String description;

    /**
     * Gets the value of the attributeTemplateListUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public AttributeTemplateListUpdater getAttributeTemplateListUpdater() {
        return attributeTemplateListUpdater;
    }

    /**
     * Sets the value of the attributeTemplateListUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public void setAttributeTemplateListUpdater(AttributeTemplateListUpdater value) {
        this.attributeTemplateListUpdater = value;
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

}