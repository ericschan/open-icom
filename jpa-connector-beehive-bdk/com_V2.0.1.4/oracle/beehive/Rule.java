//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actionPreferences" type="{http://www.oracle.com/beehive}associativeArray" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://www.oracle.com/beehive}attributeApplication" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="collabId" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="definition" type="{http://www.oracle.com/beehive}ruleDefinition" minOccurs="0"/>
 *         &lt;element name="ruleTemplate" type="{http://www.oracle.com/beehive}ruleTemplate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rule", propOrder = {
    "actionPreferences",
    "attributes",
    "collabId",
    "definition",
    "ruleTemplate"
})
public class Rule {

    protected AssociativeArray actionPreferences;
    @XmlElement(nillable = true)
    protected List<AttributeApplication> attributes;
    protected BeeId collabId;
    protected RuleDefinition definition;
    protected RuleTemplate ruleTemplate;

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
     * {@link AttributeApplication }
     * 
     * 
     */
    public List<AttributeApplication> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<AttributeApplication>();
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
     * Gets the value of the definition property.
     * 
     * @return
     *     possible object is
     *     {@link RuleDefinition }
     *     
     */
    public RuleDefinition getDefinition() {
        return definition;
    }

    /**
     * Sets the value of the definition property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleDefinition }
     *     
     */
    public void setDefinition(RuleDefinition value) {
        this.definition = value;
    }

    /**
     * Gets the value of the ruleTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link RuleTemplate }
     *     
     */
    public RuleTemplate getRuleTemplate() {
        return ruleTemplate;
    }

    /**
     * Sets the value of the ruleTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleTemplate }
     *     
     */
    public void setRuleTemplate(RuleTemplate value) {
        this.ruleTemplate = value;
    }

}