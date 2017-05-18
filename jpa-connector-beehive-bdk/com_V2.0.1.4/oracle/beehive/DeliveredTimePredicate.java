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
 * <p>Java class for deliveredTimePredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deliveredTimePredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="comparator" type="{http://www.oracle.com/beehive}timeComparator" minOccurs="0"/>
 *         &lt;element name="deliveredTime" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deliveredTimePredicate", propOrder = {
    "comparator",
    "deliveredTime"
})
public class DeliveredTimePredicate
    extends Predicate
{

    protected TimeComparator comparator;
    protected DateTime deliveredTime;

    /**
     * Gets the value of the comparator property.
     * 
     * @return
     *     possible object is
     *     {@link TimeComparator }
     *     
     */
    public TimeComparator getComparator() {
        return comparator;
    }

    /**
     * Sets the value of the comparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeComparator }
     *     
     */
    public void setComparator(TimeComparator value) {
        this.comparator = value;
    }

    /**
     * Gets the value of the deliveredTime property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getDeliveredTime() {
        return deliveredTime;
    }

    /**
     * Sets the value of the deliveredTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setDeliveredTime(DateTime value) {
        this.deliveredTime = value;
    }

}