//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for personalWorkspaceCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personalWorkspaceCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="owner" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="templateInstantiator" type="{http://www.oracle.com/beehive}personalWorkspaceTemplateInstantiator" minOccurs="0"/>
 *         &lt;element name="updater" type="{http://www.oracle.com/beehive}personalWorkspaceUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personalWorkspaceCreator", propOrder = {
    "name",
    "owner",
    "parent",
    "templateInstantiator",
    "updater"
})
public class PersonalWorkspaceCreator
    extends EntityCreator
{

    protected String name;
    protected BeeId owner;
    protected BeeId parent;
    protected PersonalWorkspaceTemplateInstantiator templateInstantiator;
    protected PersonalWorkspaceUpdater updater;

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
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setOwner(BeeId value) {
        this.owner = value;
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
     * Gets the value of the templateInstantiator property.
     * 
     * @return
     *     possible object is
     *     {@link PersonalWorkspaceTemplateInstantiator }
     *     
     */
    public PersonalWorkspaceTemplateInstantiator getTemplateInstantiator() {
        return templateInstantiator;
    }

    /**
     * Sets the value of the templateInstantiator property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalWorkspaceTemplateInstantiator }
     *     
     */
    public void setTemplateInstantiator(PersonalWorkspaceTemplateInstantiator value) {
        this.templateInstantiator = value;
    }

    /**
     * Gets the value of the updater property.
     * 
     * @return
     *     possible object is
     *     {@link PersonalWorkspaceUpdater }
     *     
     */
    public PersonalWorkspaceUpdater getUpdater() {
        return updater;
    }

    /**
     * Sets the value of the updater property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalWorkspaceUpdater }
     *     
     */
    public void setUpdater(PersonalWorkspaceUpdater value) {
        this.updater = value;
    }

}
