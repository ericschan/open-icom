//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for notificationUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notificationUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="actionDoer" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="contentMetadata" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="contentUpdater" type="{http://www.oracle.com/beehive}multiContentUpdater" minOccurs="0"/>
 *         &lt;element name="eventDefinition" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="origin" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="recipient" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="schema" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="sourceModifiedOnTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="updatedAttachment" type="{http://www.oracle.com/beehive}contentUpdater" minOccurs="0"/>
 *         &lt;element name="updatedContent" type="{http://www.oracle.com/beehive}multiContentUpdater" minOccurs="0"/>
 *         &lt;element name="wapRequestData" type="{http://www.oracle.com/beehive}wapRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificationUpdater", propOrder = {
    "actionDoer",
    "contentMetadata",
    "contentUpdater",
    "eventDefinition",
    "origin",
    "priority",
    "recipient",
    "schema",
    "source",
    "sourceModifiedOnTime",
    "updatedAttachment",
    "updatedContent",
    "wapRequestData"
})
public class NotificationUpdater
    extends ArtifactUpdater
{

    protected BeeId actionDoer;
    protected CollabProperties contentMetadata;
    protected MultiContentUpdater contentUpdater;
    protected BeeId eventDefinition;
    protected BeeId origin;
    protected Priority priority;
    protected BeeId recipient;
    protected BeeId schema;
    protected BeeId source;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sourceModifiedOnTime;
    protected ContentUpdater updatedAttachment;
    protected MultiContentUpdater updatedContent;
    protected WapRequest wapRequestData;

    /**
     * Gets the value of the actionDoer property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getActionDoer() {
        return actionDoer;
    }

    /**
     * Sets the value of the actionDoer property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setActionDoer(BeeId value) {
        this.actionDoer = value;
    }

    /**
     * Gets the value of the contentMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getContentMetadata() {
        return contentMetadata;
    }

    /**
     * Sets the value of the contentMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setContentMetadata(CollabProperties value) {
        this.contentMetadata = value;
    }

    /**
     * Gets the value of the contentUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link MultiContentUpdater }
     *     
     */
    public MultiContentUpdater getContentUpdater() {
        return contentUpdater;
    }

    /**
     * Sets the value of the contentUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiContentUpdater }
     *     
     */
    public void setContentUpdater(MultiContentUpdater value) {
        this.contentUpdater = value;
    }

    /**
     * Gets the value of the eventDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getEventDefinition() {
        return eventDefinition;
    }

    /**
     * Sets the value of the eventDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setEventDefinition(BeeId value) {
        this.eventDefinition = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setOrigin(BeeId value) {
        this.origin = value;
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
     * Gets the value of the recipient property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setRecipient(BeeId value) {
        this.recipient = value;
    }

    /**
     * Gets the value of the schema property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setSchema(BeeId value) {
        this.schema = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setSource(BeeId value) {
        this.source = value;
    }

    /**
     * Gets the value of the sourceModifiedOnTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSourceModifiedOnTime() {
        return sourceModifiedOnTime;
    }

    /**
     * Sets the value of the sourceModifiedOnTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSourceModifiedOnTime(XMLGregorianCalendar value) {
        this.sourceModifiedOnTime = value;
    }

    /**
     * Gets the value of the updatedAttachment property.
     * 
     * @return
     *     possible object is
     *     {@link ContentUpdater }
     *     
     */
    public ContentUpdater getUpdatedAttachment() {
        return updatedAttachment;
    }

    /**
     * Sets the value of the updatedAttachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentUpdater }
     *     
     */
    public void setUpdatedAttachment(ContentUpdater value) {
        this.updatedAttachment = value;
    }

    /**
     * Gets the value of the updatedContent property.
     * 
     * @return
     *     possible object is
     *     {@link MultiContentUpdater }
     *     
     */
    public MultiContentUpdater getUpdatedContent() {
        return updatedContent;
    }

    /**
     * Sets the value of the updatedContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiContentUpdater }
     *     
     */
    public void setUpdatedContent(MultiContentUpdater value) {
        this.updatedContent = value;
    }

    /**
     * Gets the value of the wapRequestData property.
     * 
     * @return
     *     possible object is
     *     {@link WapRequest }
     *     
     */
    public WapRequest getWapRequestData() {
        return wapRequestData;
    }

    /**
     * Sets the value of the wapRequestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link WapRequest }
     *     
     */
    public void setWapRequestData(WapRequest value) {
        this.wapRequestData = value;
    }

}
