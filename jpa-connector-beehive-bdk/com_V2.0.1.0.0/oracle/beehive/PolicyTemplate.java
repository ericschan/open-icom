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
 * <p>Java class for policyTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="policyTemplate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}basicTemplate">
 *       &lt;sequence>
 *         &lt;element name="ruleTemplates" type="{http://www.oracle.com/beehive}ruleTemplate" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "policyTemplate", propOrder = {
    "ruleTemplates",
    "type"
})
public class PolicyTemplate
    extends BasicTemplate
{

    @XmlElement(nillable = true)
    protected List<RuleTemplate> ruleTemplates;
    protected PolicyType type;

    /**
     * Gets the value of the ruleTemplates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ruleTemplates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRuleTemplates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RuleTemplate }
     * 
     * 
     */
    public List<RuleTemplate> getRuleTemplates() {
        if (ruleTemplates == null) {
            ruleTemplates = new ArrayList<RuleTemplate>();
        }
        return this.ruleTemplates;
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
