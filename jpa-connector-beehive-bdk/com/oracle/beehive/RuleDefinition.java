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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruleDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ruleDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actionPreferences" type="{http://www.oracle.com/beehive}associativeArray" minOccurs="0"/>
 *         &lt;element name="actions" type="{http://www.oracle.com/beehive}ruleActionDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attributeDefinitions" type="{http://www.oracle.com/beehive}attributeDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://www.oracle.com/beehive}attributeDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="collabId" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="condition" type="{http://www.oracle.com/beehive}ruleConditionDefinition" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventDefinition" type="{http://www.oracle.com/beehive}eventDefinition" minOccurs="0"/>
 *         &lt;element name="handle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ruleDefinition", propOrder = {
    "actionPreferences",
    "actions",
    "attributeDefinitions",
    "attributes",
    "collabId",
    "condition",
    "description",
    "eventDefinition",
    "handle",
    "name",
    "priority"
})
public class RuleDefinition {

    protected AssociativeArray actionPreferences;
    @XmlElement(nillable = true)
    protected List<RuleActionDefinition> actions;
    protected List<AttributeDefinition> attributeDefinitions;
    protected List<AttributeDefinition> attributes;
    protected BeeId collabId;
    protected RuleConditionDefinition condition;
    protected String description;
    protected EventDefinition eventDefinition;
    protected BeeId handle;
    protected String name;
    protected int priority;

    /**
     * Gets the value of the actionPreferences property.
     * 
     * @return
     *     possible object is
     *     {@link AssociativeArray }
     *     
     */
    public AssociativeArray getActionPreferences() {
        return actionPreferences;
    }

    /**
     * Sets the value of the actionPreferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociativeArray }
     *     
     */
    public void setActionPreferences(AssociativeArray value) {
        this.actionPreferences = value;
    }

    /**
     * Gets the value of the actions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RuleActionDefinition }
     * 
     * 
     */
    public List<RuleActionDefinition> getActions() {
        if (actions == null) {
            actions = new ArrayList<RuleActionDefinition>();
        }
        return this.actions;
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
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeDefinition }
     * 
     * 
     */
    public List<AttributeDefinition> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<AttributeDefinition>();
        }
        return this.attributes;
    }

    /**
     * Gets the value of the collabId property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getCollabId() {
        return collabId;
    }

    /**
     * Sets the value of the collabId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setCollabId(BeeId value) {
        this.collabId = value;
    }

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link RuleConditionDefinition }
     *     
     */
    public RuleConditionDefinition getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleConditionDefinition }
     *     
     */
    public void setCondition(RuleConditionDefinition value) {
        this.condition = value;
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
     * Gets the value of the eventDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link EventDefinition }
     *     
     */
    public EventDefinition getEventDefinition() {
        return eventDefinition;
    }

    /**
     * Sets the value of the eventDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventDefinition }
     *     
     */
    public void setEventDefinition(EventDefinition value) {
        this.eventDefinition = value;
    }

    /**
     * Gets the value of the handle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getHandle() {
        return handle;
    }

    /**
     * Sets the value of the handle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setHandle(BeeId value) {
        this.handle = value;
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
     * Gets the value of the priority property.
     * 
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     */
    public void setPriority(int value) {
        this.priority = value;
    }

}
