//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for policyTemplateUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="policyTemplateUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}basicTemplateUpdater">
 *       &lt;sequence>
 *         &lt;element name="ruleTemplateListUpdater" type="{http://www.oracle.com/beehive}ruleTemplateListUpdater" minOccurs="0"/>
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
@XmlType(name = "policyTemplateUpdater", propOrder = {
    "ruleTemplateListUpdater",
    "type"
})
public class PolicyTemplateUpdater
    extends BasicTemplateUpdater
{

    protected RuleTemplateListUpdater ruleTemplateListUpdater;
    protected PolicyType type;

    /**
     * Gets the value of the ruleTemplateListUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link RuleTemplateListUpdater }
     *     
     */
    public RuleTemplateListUpdater getRuleTemplateListUpdater() {
        return ruleTemplateListUpdater;
    }

    /**
     * Sets the value of the ruleTemplateListUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleTemplateListUpdater }
     *     
     */
    public void setRuleTemplateListUpdater(RuleTemplateListUpdater value) {
        this.ruleTemplateListUpdater = value;
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