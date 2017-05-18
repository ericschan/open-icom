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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoryTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="categoryTemplate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}basicTemplate">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.oracle.com/beehive}category" minOccurs="0"/>
 *         &lt;element name="copyOnVersion" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="final" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isMissing" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="missing" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryTemplate", propOrder = {
    "category",
    "copyOnVersion",
    "_final",
    "isMissing",
    "missing",
    "required"
})
@XmlRootElement(name = "categoryTemplate")
public class CategoryTemplate
    extends BasicTemplate
{

    protected Category category;
    protected boolean copyOnVersion;
    @XmlElement(name = "final")
    protected boolean _final;
    protected boolean isMissing;
    protected boolean missing;
    protected boolean required;

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link Category }
     *     
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link Category }
     *     
     */
    public void setCategory(Category value) {
        this.category = value;
    }

    /**
     * Gets the value of the copyOnVersion property.
     * 
     */
    public boolean isCopyOnVersion() {
        return copyOnVersion;
    }

    /**
     * Sets the value of the copyOnVersion property.
     * 
     */
    public void setCopyOnVersion(boolean value) {
        this.copyOnVersion = value;
    }

    /**
     * Gets the value of the final property.
     * 
     */
    public boolean isFinal() {
        return _final;
    }

    /**
     * Sets the value of the final property.
     * 
     */
    public void setFinal(boolean value) {
        this._final = value;
    }

    /**
     * Gets the value of the isMissing property.
     * 
     */
    public boolean isIsMissing() {
        return isMissing;
    }

    /**
     * Sets the value of the isMissing property.
     * 
     */
    public void setIsMissing(boolean value) {
        this.isMissing = value;
    }

    /**
     * Gets the value of the missing property.
     * 
     */
    public boolean isMissing() {
        return missing;
    }

    /**
     * Sets the value of the missing property.
     * 
     */
    public void setMissing(boolean value) {
        this.missing = value;
    }

    /**
     * Gets the value of the required property.
     * 
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the value of the required property.
     * 
     */
    public void setRequired(boolean value) {
        this.required = value;
    }

}
