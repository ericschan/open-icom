//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeTemplateUpdateParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributeTemplateUpdateParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributeTemplateHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
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
@XmlType(name = "attributeTemplateUpdateParameter", propOrder = {
    "attributeTemplateHandle",
    "attributeTemplateUpdater"
})
public class AttributeTemplateUpdateParameter {

    protected BeeId attributeTemplateHandle;
    protected AttributeTemplateUpdater attributeTemplateUpdater;

    /**
     * Gets the value of the attributeTemplateHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getAttributeTemplateHandle() {
        return attributeTemplateHandle;
    }

    /**
     * Sets the value of the attributeTemplateHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setAttributeTemplateHandle(BeeId value) {
        this.attributeTemplateHandle = value;
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
