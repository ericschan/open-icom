//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subscriptionSchema complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subscriptionSchema">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entitySchema">
 *       &lt;sequence>
 *         &lt;element name="containerSubscriptionRuleDefinitions" type="{http://www.oracle.com/beehive}subscriptionRuleDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="entitySubscriptionRuleDefinitions" type="{http://www.oracle.com/beehive}subscriptionRuleDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sourceEntityClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subscriptionSchema", propOrder = {
    "containerSubscriptionRuleDefinitions",
    "entitySubscriptionRuleDefinitions",
    "sourceEntityClass"
})
public class SubscriptionSchema
    extends EntitySchema
{

    @XmlElement(nillable = true)
    protected List<SubscriptionRuleDefinition> containerSubscriptionRuleDefinitions;
    @XmlElement(nillable = true)
    protected List<SubscriptionRuleDefinition> entitySubscriptionRuleDefinitions;
    protected String sourceEntityClass;

    /**
     * Gets the value of the containerSubscriptionRuleDefinitions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the containerSubscriptionRuleDefinitions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContainerSubscriptionRuleDefinitions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscriptionRuleDefinition }
     * 
     * 
     */
    public List<SubscriptionRuleDefinition> getContainerSubscriptionRuleDefinitions() {
        if (containerSubscriptionRuleDefinitions == null) {
            containerSubscriptionRuleDefinitions = new ArrayList<SubscriptionRuleDefinition>();
        }
        return this.containerSubscriptionRuleDefinitions;
    }

    /**
     * Gets the value of the entitySubscriptionRuleDefinitions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entitySubscriptionRuleDefinitions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntitySubscriptionRuleDefinitions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscriptionRuleDefinition }
     * 
     * 
     */
    public List<SubscriptionRuleDefinition> getEntitySubscriptionRuleDefinitions() {
        if (entitySubscriptionRuleDefinitions == null) {
            entitySubscriptionRuleDefinitions = new ArrayList<SubscriptionRuleDefinition>();
        }
        return this.entitySubscriptionRuleDefinitions;
    }

    /**
     * Gets the value of the sourceEntityClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceEntityClass() {
        return sourceEntityClass;
    }

    /**
     * Sets the value of the sourceEntityClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceEntityClass(String value) {
        this.sourceEntityClass = value;
    }

}
