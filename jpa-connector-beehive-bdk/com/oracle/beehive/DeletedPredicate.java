//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deletedPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deletedPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="deletedPredicateEnum" type="{http://www.oracle.com/beehive}deletedPredicateEnum" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deletedPredicate", propOrder = {
    "deletedPredicateEnum",
    "value"
})
public class DeletedPredicate
    extends Predicate
{

    protected DeletedPredicateEnum deletedPredicateEnum;
    protected String value;

    /**
     * Gets the value of the deletedPredicateEnum property.
     * 
     * @return
     *     possible object is
     *     {@link DeletedPredicateEnum }
     *     
     */
    public DeletedPredicateEnum getDeletedPredicateEnum() {
        return deletedPredicateEnum;
    }

    /**
     * Sets the value of the deletedPredicateEnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeletedPredicateEnum }
     *     
     */
    public void setDeletedPredicateEnum(DeletedPredicateEnum value) {
        this.deletedPredicateEnum = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
