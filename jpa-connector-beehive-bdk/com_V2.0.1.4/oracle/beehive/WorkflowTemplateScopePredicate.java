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
 * <p>Java class for workflowTemplateScopePredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workflowTemplateScopePredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="hierarchy" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="templateKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workflowTemplateScopePredicate", propOrder = {
    "hierarchies",
    "templateKey"
})
public class WorkflowTemplateScopePredicate
    extends Predicate
{

    @XmlElement(name = "hierarchy", nillable = true)
    protected List<BeeId> hierarchies;
    protected String templateKey;

    /**
     * Gets the value of the hierarchies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hierarchies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHierarchies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getHierarchies() {
        if (hierarchies == null) {
            hierarchies = new ArrayList<BeeId>();
        }
        return this.hierarchies;
    }

    /**
     * Gets the value of the templateKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateKey() {
        return templateKey;
    }

    /**
     * Sets the value of the templateKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateKey(String value) {
        this.templateKey = value;
    }

}