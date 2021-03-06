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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="message">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifact">
 *       &lt;sequence>
 *         &lt;element name="body" type="{http://www.oracle.com/beehive}content" minOccurs="0"/>
 *         &lt;element name="deliveredTime" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="receivers" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="references" type="{http://www.oracle.com/beehive}artifact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sender" type="{http://www.oracle.com/beehive}actor" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "message", propOrder = {
    "body",
    "deliveredTime",
    "receivers",
    "references",
    "sender"
})
@XmlSeeAlso({
    VoiceMessage.class,
    DiscussionsMessage.class,
    DiscussionsDraft.class,
    EmailMessage.class,
    FaxMessage.class
})
public abstract class Message
    extends Artifact
{

    protected Content body;
    protected DateTime deliveredTime;
    protected List<Object> receivers;
    protected List<Artifact> references;
    protected Actor sender;

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link Content }
     *     
     */
    public Content getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link Content }
     *     
     */
    public void setBody(Content value) {
        this.body = value;
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

    /**
     * Gets the value of the receivers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receivers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceivers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getReceivers() {
        if (receivers == null) {
            receivers = new ArrayList<Object>();
        }
        return this.receivers;
    }

    /**
     * Gets the value of the references property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the references property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferences().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Artifact }
     * 
     * 
     */
    public List<Artifact> getReferences() {
        if (references == null) {
            references = new ArrayList<Artifact>();
        }
        return this.references;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link Actor }
     *     
     */
    public Actor getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Actor }
     *     
     */
    public void setSender(Actor value) {
        this.sender = value;
    }

}
