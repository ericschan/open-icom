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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruleActionDefinitionUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ruleActionDefinitionUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributeDefinitionListUpdater" type="{http://www.oracle.com/beehive}attributeDefinitionListUpdater" minOccurs="0"/>
 *         &lt;element name="attributeTemplateListUpdater" type="{http://www.oracle.com/beehive}attributeTemplateListUpdater" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="events" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.oracle.com/beehive}policyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ruleActionDefinitionUpdater", propOrder = {
    "action",
    "attributeDefinitionListUpdater",
    "attributeTemplateListUpdater",
    "description",
    "events",
    "name",
    "type"
})
public class RuleActionDefinitionUpdater
    extends IdentifiableUpdater
{

    protected String action;
    protected AttributeDefinitionListUpdater attributeDefinitionListUpdater;
    protected AttributeTemplateListUpdater attributeTemplateListUpdater;
    protected String description;
    protected List<BeeId> events;
    protected String name;
    protected PolicyType type;

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the attributeDefinitionListUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDefinitionListUpdater }
     *     
     */
    public AttributeDefinitionListUpdater getAttributeDefinitionListUpdater() {
        return attributeDefinitionListUpdater;
    }

    /**
     * Sets the value of the attributeDefinitionListUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDefinitionListUpdater }
     *     
     */
    public void setAttributeDefinitionListUpdater(AttributeDefinitionListUpdater value) {
        this.attributeDefinitionListUpdater = value;
    }

    /**
     * Gets the value of the attributeTemplateListUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public AttributeTemplateListUpdater getAttributeTemplateListUpdater() {
        return attributeTemplateListUpdater;
    }

    /**
     * Sets the value of the attributeTemplateListUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeTemplateListUpdater }
     *     
     */
    public void setAttributeTemplateListUpdater(AttributeTemplateListUpdater value) {
        this.attributeTemplateListUpdater = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the events property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the events property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEvents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getEvents() {
        if (events == null) {
            events = new ArrayList<BeeId>();
        }
        return this.events;
    }

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyType }
     *     
     */
    public PolicyType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyType }
     *     
     */
    public void setType(PolicyType value) {
        this.type = value;
    }

}
