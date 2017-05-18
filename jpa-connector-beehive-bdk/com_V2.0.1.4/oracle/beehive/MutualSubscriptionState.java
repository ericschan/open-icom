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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mutualSubscriptionState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mutualSubscriptionState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="from" type="{http://www.oracle.com/beehive}subscriptionState" minOccurs="0"/>
 *         &lt;element name="fromAttributes" type="{http://www.oracle.com/beehive}subscriptionAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="to" type="{http://www.oracle.com/beehive}subscriptionState" minOccurs="0"/>
 *         &lt;element name="toAttributes" type="{http://www.oracle.com/beehive}subscriptionAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mutualSubscriptionState", propOrder = {
    "from",
    "fromAttributes",
    "to",
    "toAttributes"
})
public class MutualSubscriptionState {

    protected SubscriptionState from;
    protected List<SubscriptionAttribute> fromAttributes;
    protected SubscriptionState to;
    protected List<SubscriptionAttribute> toAttributes;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionState }
     *     
     */
    public SubscriptionState getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionState }
     *     
     */
    public void setFrom(SubscriptionState value) {
        this.from = value;
    }

    /**
     * Gets the value of the fromAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fromAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFromAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscriptionAttribute }
     * 
     * 
     */
    public List<SubscriptionAttribute> getFromAttributes() {
        if (fromAttributes == null) {
            fromAttributes = new ArrayList<SubscriptionAttribute>();
        }
        return this.fromAttributes;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionState }
     *     
     */
    public SubscriptionState getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionState }
     *     
     */
    public void setTo(SubscriptionState value) {
        this.to = value;
    }

    /**
     * Gets the value of the toAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscriptionAttribute }
     * 
     * 
     */
    public List<SubscriptionAttribute> getToAttributes() {
        if (toAttributes == null) {
            toAttributes = new ArrayList<SubscriptionAttribute>();
        }
        return this.toAttributes;
    }

}