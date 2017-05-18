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
 * <p>Java class for categoryConfigurationUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="categoryConfigurationUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="allowAll" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="categoryTemplatesUpdater" type="{http://www.oracle.com/beehive}categoryTemplateListUpdater" minOccurs="0"/>
 *         &lt;element name="final" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inherited" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "categoryConfigurationUpdater", propOrder = {
    "allowAll",
    "categoryTemplatesUpdater",
    "_final",
    "inherited",
    "required"
})
public class CategoryConfigurationUpdater
    extends IdentifiableUpdater
{

    protected Boolean allowAll;
    protected CategoryTemplateListUpdater categoryTemplatesUpdater;
    @XmlElement(name = "final")
    protected Boolean _final;
    protected Boolean inherited;
    protected Boolean required;

    /**
     * Gets the value of the allowAll property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowAll() {
        return allowAll;
    }

    /**
     * Sets the value of the allowAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowAll(Boolean value) {
        this.allowAll = value;
    }

    /**
     * Gets the value of the categoryTemplatesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryTemplateListUpdater }
     *     
     */
    public CategoryTemplateListUpdater getCategoryTemplatesUpdater() {
        return categoryTemplatesUpdater;
    }

    /**
     * Sets the value of the categoryTemplatesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryTemplateListUpdater }
     *     
     */
    public void setCategoryTemplatesUpdater(CategoryTemplateListUpdater value) {
        this.categoryTemplatesUpdater = value;
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
     * Gets the value of the inherited property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInherited() {
        return inherited;
    }

    /**
     * Sets the value of the inherited property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInherited(Boolean value) {
        this.inherited = value;
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
