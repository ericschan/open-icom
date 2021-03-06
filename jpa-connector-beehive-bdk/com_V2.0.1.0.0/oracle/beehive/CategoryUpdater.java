//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoryUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="categoryUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}markerUpdater">
 *       &lt;sequence>
 *         &lt;element name="abstract" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="attributeDefinitionsUpdater" type="{http://www.oracle.com/beehive}attributeDefinitionListUpdater" minOccurs="0"/>
 *         &lt;element name="defaultTemplate" type="{http://www.oracle.com/beehive}categoryTemplateUpdater" minOccurs="0"/>
 *         &lt;element name="taxonomylevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryUpdater", propOrder = {
    "_abstract",
    "attributeDefinitionsUpdater",
    "defaultTemplate",
    "taxonomylevel"
})
public class CategoryUpdater
    extends MarkerUpdater
{

    @XmlElement(name = "abstract")
    protected Boolean _abstract;
    protected AttributeDefinitionListUpdater attributeDefinitionsUpdater;
    protected CategoryTemplateUpdater defaultTemplate;
    protected String taxonomylevel;

    /**
     * Gets the value of the abstract property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAbstract() {
        return _abstract;
    }

    /**
     * Sets the value of the abstract property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAbstract(Boolean value) {
        this._abstract = value;
    }

    /**
     * Gets the value of the attributeDefinitionsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDefinitionListUpdater }
     *     
     */
    public AttributeDefinitionListUpdater getAttributeDefinitionsUpdater() {
        return attributeDefinitionsUpdater;
    }

    /**
     * Sets the value of the attributeDefinitionsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDefinitionListUpdater }
     *     
     */
    public void setAttributeDefinitionsUpdater(AttributeDefinitionListUpdater value) {
        this.attributeDefinitionsUpdater = value;
    }

    /**
     * Gets the value of the defaultTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryTemplateUpdater }
     *     
     */
    public CategoryTemplateUpdater getDefaultTemplate() {
        return defaultTemplate;
    }

    /**
     * Sets the value of the defaultTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryTemplateUpdater }
     *     
     */
    public void setDefaultTemplate(CategoryTemplateUpdater value) {
        this.defaultTemplate = value;
    }

    /**
     * Gets the value of the taxonomylevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxonomylevel() {
        return taxonomylevel;
    }

    /**
     * Sets the value of the taxonomylevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxonomylevel(String value) {
        this.taxonomylevel = value;
    }

}
