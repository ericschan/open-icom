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
 * <p>Java class for documentSubscription complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="documentSubscription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}materializedSubscription">
 *       &lt;sequence>
 *         &lt;element name="actions" type="{http://www.oracle.com/beehive}documentSubscriptionAction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="conditions" type="{http://www.oracle.com/beehive}documentSubscriptionCondition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="triggerType" type="{http://www.oracle.com/beehive}subscriptionTriggerType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentSubscription", propOrder = {
    "actions",
    "conditions",
    "triggerType"
})
public class DocumentSubscription
    extends MaterializedSubscription
{

    protected List<DocumentSubscriptionAction> actions;
    protected List<DocumentSubscriptionCondition> conditions;
    protected SubscriptionTriggerType triggerType;

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
     * {@link DocumentSubscriptionAction }
     * 
     * 
     */
    public List<DocumentSubscriptionAction> getActions() {
        if (actions == null) {
            actions = new ArrayList<DocumentSubscriptionAction>();
        }
        return this.actions;
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
     * {@link DocumentSubscriptionCondition }
     * 
     * 
     */
    public List<DocumentSubscriptionCondition> getConditions() {
        if (conditions == null) {
            conditions = new ArrayList<DocumentSubscriptionCondition>();
        }
        return this.conditions;
    }

    /**
     * Gets the value of the triggerType property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionTriggerType }
     *     
     */
    public SubscriptionTriggerType getTriggerType() {
        return triggerType;
    }

    /**
     * Sets the value of the triggerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionTriggerType }
     *     
     */
    public void setTriggerType(SubscriptionTriggerType value) {
        this.triggerType = value;
    }

}
