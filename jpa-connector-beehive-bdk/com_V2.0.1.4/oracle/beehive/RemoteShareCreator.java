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
 * <p>Java class for remoteShareCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="remoteShareCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="templateHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="updater" type="{http://www.oracle.com/beehive}remoteShareUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "remoteShareCreator", propOrder = {
    "name",
    "parent",
    "templateHandle",
    "updater"
})
@XmlRootElement(name = "remoteShareCreator")
public class RemoteShareCreator
    extends EntityCreator
{

    protected String name;
    protected BeeId parent;
    protected BeeId templateHandle;
    protected RemoteShareUpdater updater;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setParent(BeeId value) {
        this.parent = value;
    }

    /**
     * Gets the value of the templateHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getTemplateHandle() {
        return templateHandle;
    }

    /**
     * Sets the value of the templateHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setTemplateHandle(BeeId value) {
        this.templateHandle = value;
    }

    /**
     * Gets the value of the updater property.
     * 
     * @return
     *     possible object is
     *     {@link RemoteShareUpdater }
     *     
     */
    public RemoteShareUpdater getUpdater() {
        return updater;
    }

    /**
     * Sets the value of the updater property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteShareUpdater }
     *     
     */
    public void setUpdater(RemoteShareUpdater value) {
        this.updater = value;
    }

}
