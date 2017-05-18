//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for emailMessageContentUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="emailMessageContentUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}contentUpdater">
 *       &lt;sequence>
 *         &lt;element name="bccReceivers" type="{http://www.oracle.com/beehive}emailRecipientListUpdater" minOccurs="0"/>
 *         &lt;element name="bodyUpdater" type="{http://www.oracle.com/beehive}contentUpdater" minOccurs="0"/>
 *         &lt;element name="ccReceivers" type="{http://www.oracle.com/beehive}emailRecipientListUpdater" minOccurs="0"/>
 *         &lt;element name="envelopeSender" type="{http://www.oracle.com/beehive}emailParticipant" minOccurs="0"/>
 *         &lt;element name="inReplyTos" type="{http://www.oracle.com/beehive}stringListUpdater" minOccurs="0"/>
 *         &lt;element name="mailer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="replyTos" type="{http://www.oracle.com/beehive}emailParticipantListUpdater" minOccurs="0"/>
 *         &lt;element name="sender" type="{http://www.oracle.com/beehive}emailParticipant" minOccurs="0"/>
 *         &lt;element name="sensitivity" type="{http://www.oracle.com/beehive}messageSensitivity" minOccurs="0"/>
 *         &lt;element name="sentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="streamSubject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://www.oracle.com/beehive}rawString" minOccurs="0"/>
 *         &lt;element name="toReceivers" type="{http://www.oracle.com/beehive}emailRecipientListUpdater" minOccurs="0"/>
 *         &lt;element name="threadReferences" type="{http://www.oracle.com/beehive}stringListUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emailMessageContentUpdater", propOrder = {
    "bccReceivers",
    "bodyUpdater",
    "ccReceivers",
    "envelopeSender",
    "inReplyTos",
    "mailer",
    "priority",
    "replyTos",
    "sender",
    "sensitivity",
    "sentDate",
    "streamSubject",
    "subject",
    "toReceivers",
    "threadReferences"
})
@XmlRootElement(name = "emailMessageContentUpdater")
public class EmailMessageContentUpdater
    extends ContentUpdater
{

    protected EmailRecipientListUpdater bccReceivers;
    protected ContentUpdater bodyUpdater;
    protected EmailRecipientListUpdater ccReceivers;
    protected EmailParticipant envelopeSender;
    protected StringListUpdater inReplyTos;
    protected String mailer;
    protected Priority priority;
    protected EmailParticipantListUpdater replyTos;
    protected EmailParticipant sender;
    protected MessageSensitivity sensitivity;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sentDate;
    protected String streamSubject;
    protected RawString subject;
    protected EmailRecipientListUpdater toReceivers;
    protected StringListUpdater threadReferences;

    /**
     * Gets the value of the bccReceivers property.
     * 
     * @return
     *     possible object is
     *     {@link EmailRecipientListUpdater }
     *     
     */
    public EmailRecipientListUpdater getBccReceivers() {
        return bccReceivers;
    }

    /**
     * Sets the value of the bccReceivers property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailRecipientListUpdater }
     *     
     */
    public void setBccReceivers(EmailRecipientListUpdater value) {
        this.bccReceivers = value;
    }

    /**
     * Gets the value of the bodyUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link ContentUpdater }
     *     
     */
    public ContentUpdater getBodyUpdater() {
        return bodyUpdater;
    }

    /**
     * Sets the value of the bodyUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentUpdater }
     *     
     */
    public void setBodyUpdater(ContentUpdater value) {
        this.bodyUpdater = value;
    }

    /**
     * Gets the value of the ccReceivers property.
     * 
     * @return
     *     possible object is
     *     {@link EmailRecipientListUpdater }
     *     
     */
    public EmailRecipientListUpdater getCcReceivers() {
        return ccReceivers;
    }

    /**
     * Sets the value of the ccReceivers property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailRecipientListUpdater }
     *     
     */
    public void setCcReceivers(EmailRecipientListUpdater value) {
        this.ccReceivers = value;
    }

    /**
     * Gets the value of the envelopeSender property.
     * 
     * @return
     *     possible object is
     *     {@link EmailParticipant }
     *     
     */
    public EmailParticipant getEnvelopeSender() {
        return envelopeSender;
    }

    /**
     * Sets the value of the envelopeSender property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailParticipant }
     *     
     */
    public void setEnvelopeSender(EmailParticipant value) {
        this.envelopeSender = value;
    }

    /**
     * Gets the value of the inReplyTos property.
     * 
     * @return
     *     possible object is
     *     {@link StringListUpdater }
     *     
     */
    public StringListUpdater getInReplyTos() {
        return inReplyTos;
    }

    /**
     * Sets the value of the inReplyTos property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringListUpdater }
     *     
     */
    public void setInReplyTos(StringListUpdater value) {
        this.inReplyTos = value;
    }

    /**
     * Gets the value of the mailer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailer() {
        return mailer;
    }

    /**
     * Sets the value of the mailer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailer(String value) {
        this.mailer = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the replyTos property.
     * 
     * @return
     *     possible object is
     *     {@link EmailParticipantListUpdater }
     *     
     */
    public EmailParticipantListUpdater getReplyTos() {
        return replyTos;
    }

    /**
     * Sets the value of the replyTos property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailParticipantListUpdater }
     *     
     */
    public void setReplyTos(EmailParticipantListUpdater value) {
        this.replyTos = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link EmailParticipant }
     *     
     */
    public EmailParticipant getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailParticipant }
     *     
     */
    public void setSender(EmailParticipant value) {
        this.sender = value;
    }

    /**
     * Gets the value of the sensitivity property.
     * 
     * @return
     *     possible object is
     *     {@link MessageSensitivity }
     *     
     */
    public MessageSensitivity getSensitivity() {
        return sensitivity;
    }

    /**
     * Sets the value of the sensitivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageSensitivity }
     *     
     */
    public void setSensitivity(MessageSensitivity value) {
        this.sensitivity = value;
    }

    /**
     * Gets the value of the sentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSentDate() {
        return sentDate;
    }

    /**
     * Sets the value of the sentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSentDate(XMLGregorianCalendar value) {
        this.sentDate = value;
    }

    /**
     * Gets the value of the streamSubject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreamSubject() {
        return streamSubject;
    }

    /**
     * Sets the value of the streamSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreamSubject(String value) {
        this.streamSubject = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link RawString }
     *     
     */
    public RawString getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link RawString }
     *     
     */
    public void setSubject(RawString value) {
        this.subject = value;
    }

    /**
     * Gets the value of the toReceivers property.
     * 
     * @return
     *     possible object is
     *     {@link EmailRecipientListUpdater }
     *     
     */
    public EmailRecipientListUpdater getToReceivers() {
        return toReceivers;
    }

    /**
     * Sets the value of the toReceivers property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailRecipientListUpdater }
     *     
     */
    public void setToReceivers(EmailRecipientListUpdater value) {
        this.toReceivers = value;
    }

    /**
     * Gets the value of the threadReferences property.
     * 
     * @return
     *     possible object is
     *     {@link StringListUpdater }
     *     
     */
    public StringListUpdater getThreadReferences() {
        return threadReferences;
    }

    /**
     * Sets the value of the threadReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringListUpdater }
     *     
     */
    public void setThreadReferences(StringListUpdater value) {
        this.threadReferences = value;
    }

}