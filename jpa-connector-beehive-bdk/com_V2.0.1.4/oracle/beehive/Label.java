//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for label complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="label">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}marker">
 *       &lt;sequence>
 *         &lt;element name="applicationCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="labelType" type="{http://www.oracle.com/beehive}labelType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "label", propOrder = {
    "applicationCount",
    "labelType"
})
@XmlRootElement(name = "label")
public class Label
    extends Marker
{

    protected long applicationCount;
    protected LabelType labelType;

    /**
     * Gets the value of the applicationCount property.
     * 
     */
    public long getApplicationCount() {
        return applicationCount;
    }

    /**
     * Sets the value of the applicationCount property.
     * 
     */
    public void setApplicationCount(long value) {
        this.applicationCount = value;
    }

    /**
     * Gets the value of the labelType property.
     * 
     * @return
     *     possible object is
     *     {@link LabelType }
     *     
     */
    public LabelType getLabelType() {
        return labelType;
    }

    /**
     * Sets the value of the labelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelType }
     *     
     */
    public void setLabelType(LabelType value) {
        this.labelType = value;
    }

}
