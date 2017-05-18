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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for auditRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auditRecord">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifact">
 *       &lt;sequence>
 *         &lt;element name="actor" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="actorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="auxInfo" type="{http://www.oracle.com/beehive}auditInfoMap" minOccurs="0"/>
 *         &lt;element name="clientAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientAuxInfo" type="{http://www.oracle.com/beehive}auditInfoMap" minOccurs="0"/>
 *         &lt;element name="clientInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientOS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="container" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="containerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="contentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="effectiveActor" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="effectiveActorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logonProtocol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logonSessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logonTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="logonType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policies" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="policyNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="principal" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="principalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="principalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auditRecord", propOrder = {
    "actor",
    "actorName",
    "auditTime",
    "auxInfo",
    "clientAddress",
    "clientAuxInfo",
    "clientInfo",
    "clientName",
    "clientOS",
    "clientType",
    "container",
    "containerName",
    "content",
    "contentName",
    "effectiveActor",
    "effectiveActorName",
    "eventName",
    "eventStatus",
    "locale",
    "logonProtocol",
    "logonSessionId",
    "logonTime",
    "logonType",
    "messageText",
    "policies",
    "policyNames",
    "principal",
    "principalName",
    "principalType",
    "serviceName"
})
public class AuditRecord
    extends Artifact
{

    protected BeeId actor;
    protected String actorName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar auditTime;
    protected AuditInfoMap auxInfo;
    protected String clientAddress;
    protected AuditInfoMap clientAuxInfo;
    protected String clientInfo;
    protected String clientName;
    protected String clientOS;
    protected String clientType;
    protected BeeId container;
    protected String containerName;
    protected BeeId content;
    protected String contentName;
    protected BeeId effectiveActor;
    protected String effectiveActorName;
    protected String eventName;
    protected String eventStatus;
    protected String locale;
    protected String logonProtocol;
    protected String logonSessionId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar logonTime;
    protected String logonType;
    protected String messageText;
    @XmlElement(nillable = true)
    protected List<BeeId> policies;
    @XmlElement(nillable = true)
    protected List<String> policyNames;
    protected BeeId principal;
    protected String principalName;
    protected String principalType;
    protected String serviceName;

    /**
     * Gets the value of the actor property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getActor() {
        return actor;
    }

    /**
     * Sets the value of the actor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setActor(BeeId value) {
        this.actor = value;
    }

    /**
     * Gets the value of the actorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActorName() {
        return actorName;
    }

    /**
     * Sets the value of the actorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActorName(String value) {
        this.actorName = value;
    }

    /**
     * Gets the value of the auditTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuditTime() {
        return auditTime;
    }

    /**
     * Sets the value of the auditTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuditTime(XMLGregorianCalendar value) {
        this.auditTime = value;
    }

    /**
     * Gets the value of the auxInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AuditInfoMap }
     *     
     */
    public AuditInfoMap getAuxInfo() {
        return auxInfo;
    }

    /**
     * Sets the value of the auxInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditInfoMap }
     *     
     */
    public void setAuxInfo(AuditInfoMap value) {
        this.auxInfo = value;
    }

    /**
     * Gets the value of the clientAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientAddress() {
        return clientAddress;
    }

    /**
     * Sets the value of the clientAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientAddress(String value) {
        this.clientAddress = value;
    }

    /**
     * Gets the value of the clientAuxInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AuditInfoMap }
     *     
     */
    public AuditInfoMap getClientAuxInfo() {
        return clientAuxInfo;
    }

    /**
     * Sets the value of the clientAuxInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditInfoMap }
     *     
     */
    public void setClientAuxInfo(AuditInfoMap value) {
        this.clientAuxInfo = value;
    }

    /**
     * Gets the value of the clientInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientInfo() {
        return clientInfo;
    }

    /**
     * Sets the value of the clientInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientInfo(String value) {
        this.clientInfo = value;
    }

    /**
     * Gets the value of the clientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the value of the clientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientName(String value) {
        this.clientName = value;
    }

    /**
     * Gets the value of the clientOS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientOS() {
        return clientOS;
    }

    /**
     * Sets the value of the clientOS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientOS(String value) {
        this.clientOS = value;
    }

    /**
     * Gets the value of the clientType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * Sets the value of the clientType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientType(String value) {
        this.clientType = value;
    }

    /**
     * Gets the value of the container property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getContainer() {
        return container;
    }

    /**
     * Sets the value of the container property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setContainer(BeeId value) {
        this.container = value;
    }

    /**
     * Gets the value of the containerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerName() {
        return containerName;
    }

    /**
     * Sets the value of the containerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerName(String value) {
        this.containerName = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setContent(BeeId value) {
        this.content = value;
    }

    /**
     * Gets the value of the contentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentName() {
        return contentName;
    }

    /**
     * Sets the value of the contentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentName(String value) {
        this.contentName = value;
    }

    /**
     * Gets the value of the effectiveActor property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getEffectiveActor() {
        return effectiveActor;
    }

    /**
     * Sets the value of the effectiveActor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setEffectiveActor(BeeId value) {
        this.effectiveActor = value;
    }

    /**
     * Gets the value of the effectiveActorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveActorName() {
        return effectiveActorName;
    }

    /**
     * Sets the value of the effectiveActorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveActorName(String value) {
        this.effectiveActorName = value;
    }

    /**
     * Gets the value of the eventName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the value of the eventName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventName(String value) {
        this.eventName = value;
    }

    /**
     * Gets the value of the eventStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventStatus() {
        return eventStatus;
    }

    /**
     * Sets the value of the eventStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventStatus(String value) {
        this.eventStatus = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the logonProtocol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogonProtocol() {
        return logonProtocol;
    }

    /**
     * Sets the value of the logonProtocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogonProtocol(String value) {
        this.logonProtocol = value;
    }

    /**
     * Gets the value of the logonSessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogonSessionId() {
        return logonSessionId;
    }

    /**
     * Sets the value of the logonSessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogonSessionId(String value) {
        this.logonSessionId = value;
    }

    /**
     * Gets the value of the logonTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLogonTime() {
        return logonTime;
    }

    /**
     * Sets the value of the logonTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLogonTime(XMLGregorianCalendar value) {
        this.logonTime = value;
    }

    /**
     * Gets the value of the logonType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogonType() {
        return logonType;
    }

    /**
     * Sets the value of the logonType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogonType(String value) {
        this.logonType = value;
    }

    /**
     * Gets the value of the messageText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Sets the value of the messageText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageText(String value) {
        this.messageText = value;
    }

    /**
     * Gets the value of the policies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getPolicies() {
        if (policies == null) {
            policies = new ArrayList<BeeId>();
        }
        return this.policies;
    }

    /**
     * Gets the value of the policyNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policyNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicyNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPolicyNames() {
        if (policyNames == null) {
            policyNames = new ArrayList<String>();
        }
        return this.policyNames;
    }

    /**
     * Gets the value of the principal property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getPrincipal() {
        return principal;
    }

    /**
     * Sets the value of the principal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setPrincipal(BeeId value) {
        this.principal = value;
    }

    /**
     * Gets the value of the principalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrincipalName() {
        return principalName;
    }

    /**
     * Sets the value of the principalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrincipalName(String value) {
        this.principalName = value;
    }

    /**
     * Gets the value of the principalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrincipalType() {
        return principalType;
    }

    /**
     * Sets the value of the principalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrincipalType(String value) {
        this.principalType = value;
    }

    /**
     * Gets the value of the serviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

}
