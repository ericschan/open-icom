//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for advancedTemplateUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="advancedTemplateUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}templateUpdater">
 *       &lt;sequence>
 *         &lt;element name="transportableFormatUpdater" type="{http://www.oracle.com/beehive}simpleContentUpdater" minOccurs="0"/>
 *         &lt;element name="updatedTransportableFormat" type="{http://www.oracle.com/beehive}simpleContentUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "advancedTemplateUpdater", propOrder = {
    "transportableFormatUpdater",
    "updatedTransportableFormat"
})
@XmlSeeAlso({
    ArtifactTemplateUpdater.class,
    WorkspaceTemplateUpdater.class
})
public abstract class AdvancedTemplateUpdater
    extends TemplateUpdater
{

    protected SimpleContentUpdater transportableFormatUpdater;
    protected SimpleContentUpdater updatedTransportableFormat;

    /**
     * Gets the value of the transportableFormatUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleContentUpdater }
     *     
     */
    public SimpleContentUpdater getTransportableFormatUpdater() {
        return transportableFormatUpdater;
    }

    /**
     * Sets the value of the transportableFormatUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleContentUpdater }
     *     
     */
    public void setTransportableFormatUpdater(SimpleContentUpdater value) {
        this.transportableFormatUpdater = value;
    }

    /**
     * Gets the value of the updatedTransportableFormat property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleContentUpdater }
     *     
     */
    public SimpleContentUpdater getUpdatedTransportableFormat() {
        return updatedTransportableFormat;
    }

    /**
     * Sets the value of the updatedTransportableFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleContentUpdater }
     *     
     */
    public void setUpdatedTransportableFormat(SimpleContentUpdater value) {
        this.updatedTransportableFormat = value;
    }

}
