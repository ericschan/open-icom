//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeTemplateCreateParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributeTemplateCreateParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributeDefinitionHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="attributeDefinitionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributeTemplateUpdater" type="{http://www.oracle.com/beehive}attributeTemplateUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributeTemplateCreateParameter", propOrder = {
    "attributeDefinitionHandle",
    "attributeDefinitionName",
    "attributeTemplateUpdater"
})
public class AttributeTemplateCreateParameter {

    protected BeeId attributeDefinitionHandle;
    protected String attributeDefinitionName;
    protected AttributeTemplateUpdater attributeTemplateUpdater;

    /**
     * Gets the value of the attributeDefinitionHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getAttributeDefinitionHandle() {
        return attributeDefinitionHandle;
    }

    /**
     * Sets the value of the attributeDefinitionHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setAttributeDefinitionHandle(BeeId value) {
        this.attributeDefinitionHandle = value;
    }

    /**
     * Gets the value of the attributeDefinitionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeDefinitionName() {
        return attributeDefinitionName;
    }

    /**
     * Sets the value of the attributeDefinitionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeDefinitionName(String value) {
        this.attributeDefinitionName = value;
    }

    /**
     * Gets the value of the attributeTemplateUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeTemplateUpdater }
     *     
     */
    public AttributeTemplateUpdater getAttributeTemplateUpdater() {
        return attributeTemplateUpdater;
    }

    /**
     * Sets the value of the attributeTemplateUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeTemplateUpdater }
     *     
     */
    public void setAttributeTemplateUpdater(AttributeTemplateUpdater value) {
        this.attributeTemplateUpdater = value;
    }

}
