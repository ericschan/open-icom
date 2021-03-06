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
 * <p>Java class for onlineInstantMessageSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="onlineInstantMessageSet">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="closeable" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="onlineInstantMessages" type="{http://www.oracle.com/beehive}instantMessage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="presenceConnection" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "onlineInstantMessageSet", propOrder = {
    "closeable",
    "onlineInstantMessages",
    "presenceConnection"
})
public class OnlineInstantMessageSet
    extends Entity
{

    protected Object closeable;
    @XmlElement(nillable = true)
    protected List<InstantMessage> onlineInstantMessages;
    protected Object presenceConnection;

    /**
     * Gets the value of the closeable property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCloseable() {
        return closeable;
    }

    /**
     * Sets the value of the closeable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCloseable(Object value) {
        this.closeable = value;
    }

    /**
     * Gets the value of the onlineInstantMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the onlineInstantMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOnlineInstantMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstantMessage }
     * 
     * 
     */
    public List<InstantMessage> getOnlineInstantMessages() {
        if (onlineInstantMessages == null) {
            onlineInstantMessages = new ArrayList<InstantMessage>();
        }
        return this.onlineInstantMessages;
    }

    /**
     * Gets the value of the presenceConnection property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPresenceConnection() {
        return presenceConnection;
    }

    /**
     * Sets the value of the presenceConnection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPresenceConnection(Object value) {
        this.presenceConnection = value;
    }

}
