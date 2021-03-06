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
 * <p>Java class for subscriptionRuleDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subscriptionRuleDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actions" type="{http://www.oracle.com/beehive}subscriptionActionDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="collabId" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="conditions" type="{http://www.oracle.com/beehive}subscriptionConditionDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subscriptionRuleDefinition", propOrder = {
    "actions",
    "collabId",
    "conditions",
    "description",
    "name"
})
public class SubscriptionRuleDefinition {

    @XmlElement(nillable = true)
    protected List<SubscriptionActionDefinition> actions;
    protected BeeId collabId;
    @XmlElement(nillable = true)
    protected List<SubscriptionConditionDefinition> conditions;
    protected String description;
    protected String name;

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
     * {@link SubscriptionActionDefinition }
     * 
     * 
     */
    public List<SubscriptionActionDefinition> getActions() {
        if (actions == null) {
            actions = new ArrayList<SubscriptionActionDefinition>();
        }
        return this.actions;
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
     * Gets the value of the conditions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conditions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConditions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscriptionConditionDefinition }
     * 
     * 
     */
    public List<SubscriptionConditionDefinition> getConditions() {
        if (conditions == null) {
            conditions = new ArrayList<SubscriptionConditionDefinition>();
        }
        return this.conditions;
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

}
