//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for category complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="category">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}marker">
 *       &lt;sequence>
 *         &lt;element name="abstract" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="attributeDefinitions" type="{http://www.oracle.com/beehive}attributeDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="defaultTemplate" type="{http://www.oracle.com/beehive}categoryTemplate" minOccurs="0"/>
 *         &lt;element name="parentCategory" type="{http://www.oracle.com/beehive}category" minOccurs="0"/>
 *         &lt;element name="subCategories" type="{http://www.oracle.com/beehive}category" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="taxonomyLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "category", propOrder = {
    "_abstract",
    "attributeDefinitions",
    "defaultTemplate",
    "parentCategory",
    "subCategories",
    "taxonomyLevel"
})
@XmlRootElement(name = "category")
public class Category
    extends Marker
{

    @XmlElement(name = "abstract")
    protected boolean _abstract;
    protected List<AttributeDefinition> attributeDefinitions;
    protected CategoryTemplate defaultTemplate;
    protected Category parentCategory;
    @XmlElement(nillable = true)
    protected List<Category> subCategories;
    protected String taxonomyLevel;

    /**
     * Gets the value of the abstract property.
     * 
     */
    public boolean isAbstract() {
        return _abstract;
    }

    /**
     * Sets the value of the abstract property.
     * 
     */
    public void setAbstract(boolean value) {
        this._abstract = value;
    }

    /**
     * Gets the value of the attributeDefinitions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeDefinitions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeDefinitions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeDefinition }
     * 
     * 
     */
    public List<AttributeDefinition> getAttributeDefinitions() {
        if (attributeDefinitions == null) {
            attributeDefinitions = new ArrayList<AttributeDefinition>();
        }
        return this.attributeDefinitions;
    }

    /**
     * Gets the value of the defaultTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryTemplate }
     *     
     */
    public CategoryTemplate getDefaultTemplate() {
        return defaultTemplate;
    }

    /**
     * Sets the value of the defaultTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryTemplate }
     *     
     */
    public void setDefaultTemplate(CategoryTemplate value) {
        this.defaultTemplate = value;
    }

    /**
     * Gets the value of the parentCategory property.
     * 
     * @return
     *     possible object is
     *     {@link Category }
     *     
     */
    public Category getParentCategory() {
        return parentCategory;
    }

    /**
     * Sets the value of the parentCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Category }
     *     
     */
    public void setParentCategory(Category value) {
        this.parentCategory = value;
    }

    /**
     * Gets the value of the subCategories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subCategories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Category }
     * 
     * 
     */
    public List<Category> getSubCategories() {
        if (subCategories == null) {
            subCategories = new ArrayList<Category>();
        }
        return this.subCategories;
    }

    /**
     * Gets the value of the taxonomyLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxonomyLevel() {
        return taxonomyLevel;
    }

    /**
     * Sets the value of the taxonomyLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxonomyLevel(String value) {
        this.taxonomyLevel = value;
    }

}
