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
 * <p>Java class for categoryApplicationUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="categoryApplicationUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="attributeValuesUpdater" type="{http://www.oracle.com/beehive}attributeApplicationListUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryApplicationUpdater", propOrder = {
    "attributeValuesUpdater"
})
public class CategoryApplicationUpdater
    extends ArtifactUpdater
{

    protected AttributeApplicationListUpdater attributeValuesUpdater;

    /**
     * Gets the value of the attributeValuesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeApplicationListUpdater }
     *     
     */
    public AttributeApplicationListUpdater getAttributeValuesUpdater() {
        return attributeValuesUpdater;
    }

    /**
     * Sets the value of the attributeValuesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeApplicationListUpdater }
     *     
     */
    public void setAttributeValuesUpdater(AttributeApplicationListUpdater value) {
        this.attributeValuesUpdater = value;
    }

}
