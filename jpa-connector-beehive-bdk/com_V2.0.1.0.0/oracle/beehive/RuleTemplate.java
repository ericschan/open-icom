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
 * <p>Java class for ruleTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ruleTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributeTemplates" type="{http://www.oracle.com/beehive}attributeTemplate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="collabId" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="ruleDefinition" type="{http://www.oracle.com/beehive}ruleDefinition" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ruleTemplate", propOrder = {
    "attributeTemplates",
    "collabId",
    "ruleDefinition"
})
public class RuleTemplate {

    @XmlElement(nillable = true)
    protected List<AttributeTemplate> attributeTemplates;
    protected BeeId collabId;
    protected RuleDefinition ruleDefinition;

    /**
     * Gets the value of the attributeTemplates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeTemplates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeTemplates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeTemplate }
     * 
     * 
     */
    public List<AttributeTemplate> getAttributeTemplates() {
        if (attributeTemplates == null) {
            attributeTemplates = new ArrayList<AttributeTemplate>();
        }
        return this.attributeTemplates;
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
     * Gets the value of the ruleDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link RuleDefinition }
     *     
     */
    public RuleDefinition getRuleDefinition() {
        return ruleDefinition;
    }

    /**
     * Sets the value of the ruleDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleDefinition }
     *     
     */
    public void setRuleDefinition(RuleDefinition value) {
        this.ruleDefinition = value;
    }

}
