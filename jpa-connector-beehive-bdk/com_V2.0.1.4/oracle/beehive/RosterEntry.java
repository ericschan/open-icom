//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rosterEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rosterEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="presentity" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.oracle.com/beehive}mutualSubscriptionState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rosterEntry", propOrder = {
    "presentity",
    "state"
})
@XmlSeeAlso({
    PresenceRosterEntry.class
})
public class RosterEntry {

    protected BeeId presentity;
    protected MutualSubscriptionState state;

    /**
     * Gets the value of the presentity property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getPresentity() {
        return presentity;
    }

    /**
     * Sets the value of the presentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setPresentity(BeeId value) {
        this.presentity = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link MutualSubscriptionState }
     *     
     */
    public MutualSubscriptionState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link MutualSubscriptionState }
     *     
     */
    public void setState(MutualSubscriptionState value) {
        this.state = value;
    }

}
