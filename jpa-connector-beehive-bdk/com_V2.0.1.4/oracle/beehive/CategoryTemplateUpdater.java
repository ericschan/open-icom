//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoryTemplateUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="categoryTemplateUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}basicTemplateUpdater">
 *       &lt;sequence>
 *         &lt;element name="attributeTemplatesUpdater" type="{http://www.oracle.com/beehive}attributeTemplateListUpdater" minOccurs="0"/>
 *         &lt;element name="copyOnVersion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="final" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryTemplateUpdater", propOrder = {
    "attributeTemplatesUpdater",
    "copyOnVersion",
    "_final",
    "required"
})
public class CategoryTemplateUpdater
    extends BasicTemplateUpdater
{

    protected AttributeTemplateListUpdater attributeTemplatesUpdater;
    protected Boolean copyOnVersion;
    @XmlElement(name = "final")
    protected Boolean _final;
    protected Boolean required;

    /**
     * Gets the value of the attributeTemplatesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public AttributeTemplateListUpdater getAttributeTemplatesUpdater() {
        return attributeTemplatesUpdater;
    }

    /**
     * Sets the value of the attributeTemplatesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public void setAttributeTemplatesUpdater(AttributeTemplateListUpdater value) {
        this.attributeTemplatesUpdater = value;
    }

    /**
     * Gets the value of the copyOnVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyOnVersion() {
        return copyOnVersion;
    }

    /**
     * Sets the value of the copyOnVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyOnVersion(Boolean value) {
        this.copyOnVersion = value;
    }

    /**
     * Gets the value of the final property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFinal() {
        return _final;
    }

    /**
     * Sets the value of the final property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFinal(Boolean value) {
        this._final = value;
    }

    /**
     * Gets the value of the required property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequired(Boolean value) {
        this.required = value;
    }

}
