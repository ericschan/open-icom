//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
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
 * <p>Java class for todoWorkspaceParticipant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="todoWorkspaceParticipant">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}todoCompositeParticipant">
 *       &lt;sequence>
 *         &lt;element name="assigneeRepliedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="assigneeWorkflowTaskId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="completed" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="deliveryChannels" type="{http://www.oracle.com/beehive}participantDeliveryChannel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deliveryResults" type="{http://www.oracle.com/beehive}participantDeliveryResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="directlyInvited" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="mileage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantStatus" type="{http://www.oracle.com/beehive}todoParticipantStatus" minOccurs="0"/>
 *         &lt;element name="percentComplete" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timeAllocated" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="timeSpent" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "todoWorkspaceParticipant", propOrder = {
    "assigneeRepliedOn",
    "assigneeWorkflowTaskId",
    "billingInfo",
    "comment",
    "completed",
    "deliveryChannels",
    "deliveryResults",
    "directlyInvited",
    "mileage",
    "participantStatus",
    "percentComplete",
    "timeAllocated",
    "timeSpent"
})
public class TodoWorkspaceParticipant
    extends TodoCompositeParticipant
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar assigneeRepliedOn;
    protected String assigneeWorkflowTaskId;
    protected String billingInfo;
    protected String comment;
    protected DateTime completed;
    @XmlElement(nillable = true)
    protected List<ParticipantDeliveryChannel> deliveryChannels;
    @XmlElement(nillable = true)
    protected List<ParticipantDeliveryResult> deliveryResults;
    protected boolean directlyInvited;
    protected String mileage;
    protected TodoParticipantStatus participantStatus;
    protected int percentComplete;
    protected Long timeAllocated;
    protected Long timeSpent;

    /**
     * Gets the value of the assigneeRepliedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssigneeRepliedOn() {
        return assigneeRepliedOn;
    }

    /**
     * Sets the value of the assigneeRepliedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssigneeRepliedOn(XMLGregorianCalendar value) {
        this.assigneeRepliedOn = value;
    }

    /**
     * Gets the value of the assigneeWorkflowTaskId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssigneeWorkflowTaskId() {
        return assigneeWorkflowTaskId;
    }

    /**
     * Sets the value of the assigneeWorkflowTaskId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssigneeWorkflowTaskId(String value) {
        this.assigneeWorkflowTaskId = value;
    }

    /**
     * Gets the value of the billingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingInfo() {
        return billingInfo;
    }

    /**
     * Sets the value of the billingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingInfo(String value) {
        this.billingInfo = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the completed property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getCompleted() {
        return completed;
    }

    /**
     * Sets the value of the completed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setCompleted(DateTime value) {
        this.completed = value;
    }

    /**
     * Gets the value of the deliveryChannels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveryChannels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeliveryChannels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParticipantDeliveryChannel }
     * 
     * 
     */
    public List<ParticipantDeliveryChannel> getDeliveryChannels() {
        if (deliveryChannels == null) {
            deliveryChannels = new ArrayList<ParticipantDeliveryChannel>();
        }
        return this.deliveryChannels;
    }

    /**
     * Gets the value of the deliveryResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveryResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeliveryResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParticipantDeliveryResult }
     * 
     * 
     */
    public List<ParticipantDeliveryResult> getDeliveryResults() {
        if (deliveryResults == null) {
            deliveryResults = new ArrayList<ParticipantDeliveryResult>();
        }
        return this.deliveryResults;
    }

    /**
     * Gets the value of the directlyInvited property.
     * 
     */
    public boolean isDirectlyInvited() {
        return directlyInvited;
    }

    /**
     * Sets the value of the directlyInvited property.
     * 
     */
    public void setDirectlyInvited(boolean value) {
        this.directlyInvited = value;
    }

    /**
     * Gets the value of the mileage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMileage() {
        return mileage;
    }

    /**
     * Sets the value of the mileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMileage(String value) {
        this.mileage = value;
    }

    /**
     * Gets the value of the participantStatus property.
     * 
     * @return
     *     possible object is
     *     {@link TodoParticipantStatus }
     *     
     */
    public TodoParticipantStatus getParticipantStatus() {
        return participantStatus;
    }

    /**
     * Sets the value of the participantStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoParticipantStatus }
     *     
     */
    public void setParticipantStatus(TodoParticipantStatus value) {
        this.participantStatus = value;
    }

    /**
     * Gets the value of the percentComplete property.
     * 
     */
    public int getPercentComplete() {
        return percentComplete;
    }

    /**
     * Sets the value of the percentComplete property.
     * 
     */
    public void setPercentComplete(int value) {
        this.percentComplete = value;
    }

    /**
     * Gets the value of the timeAllocated property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTimeAllocated() {
        return timeAllocated;
    }

    /**
     * Sets the value of the timeAllocated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTimeAllocated(Long value) {
        this.timeAllocated = value;
    }

    /**
     * Gets the value of the timeSpent property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTimeSpent() {
        return timeSpent;
    }

    /**
     * Sets the value of the timeSpent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTimeSpent(Long value) {
        this.timeSpent = value;
    }

}