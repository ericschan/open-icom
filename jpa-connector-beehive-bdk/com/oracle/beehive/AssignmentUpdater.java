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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for assignmentUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="assignmentUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="assigneeBillingInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assigneeComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assigneeCompleted" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="assigneeDue" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="assigneeICalCategories" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="assigneeICalClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assigneeICalPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="assigneeMileage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assigneeParticipantStatus" type="{http://www.oracle.com/beehive}todoParticipantStatus" minOccurs="0"/>
 *         &lt;element name="assigneePercentComplete" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="assigneePriority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="assigneePropertiesUpdater" type="{http://www.oracle.com/beehive}collabPropertiesUpdater" minOccurs="0"/>
 *         &lt;element name="assigneeSortOrdinal" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="assigneeStart" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *         &lt;element name="assigneeTimeAllocated" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="assigneeTimeSpent" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="calDavResourceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publishAssignmentBonds" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sensitivity" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assignmentUpdater", propOrder = {
    "assigneeBillingInfo",
    "assigneeComment",
    "assigneeCompleted",
    "assigneeDue",
    "assigneeICalCategories",
    "assigneeICalClass",
    "assigneeICalPriority",
    "assigneeMileage",
    "assigneeParticipantStatus",
    "assigneePercentComplete",
    "assigneePriority",
    "assigneePropertiesUpdater",
    "assigneeSortOrdinal",
    "assigneeStart",
    "assigneeTimeAllocated",
    "assigneeTimeSpent",
    "calDavResourceName",
    "publishAssignmentBonds",
    "sensitivity"
})
public class AssignmentUpdater
    extends ArtifactUpdater
{

    protected String assigneeBillingInfo;
    protected String assigneeComment;
    protected DateTime assigneeCompleted;
    protected DateTime assigneeDue;
    protected List<String> assigneeICalCategories;
    protected String assigneeICalClass;
    protected Integer assigneeICalPriority;
    protected String assigneeMileage;
    protected TodoParticipantStatus assigneeParticipantStatus;
    protected Integer assigneePercentComplete;
    protected Priority assigneePriority;
    protected CollabPropertiesUpdater assigneePropertiesUpdater;
    protected Integer assigneeSortOrdinal;
    protected DateTime assigneeStart;
    protected Long assigneeTimeAllocated;
    protected Long assigneeTimeSpent;
    protected String calDavResourceName;
    protected Boolean publishAssignmentBonds;
    protected BeeId sensitivity;

    /**
     * Gets the value of the assigneeBillingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssigneeBillingInfo() {
        return assigneeBillingInfo;
    }

    /**
     * Sets the value of the assigneeBillingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssigneeBillingInfo(String value) {
        this.assigneeBillingInfo = value;
    }

    /**
     * Gets the value of the assigneeComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssigneeComment() {
        return assigneeComment;
    }

    /**
     * Sets the value of the assigneeComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssigneeComment(String value) {
        this.assigneeComment = value;
    }

    /**
     * Gets the value of the assigneeCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getAssigneeCompleted() {
        return assigneeCompleted;
    }

    /**
     * Sets the value of the assigneeCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setAssigneeCompleted(DateTime value) {
        this.assigneeCompleted = value;
    }

    /**
     * Gets the value of the assigneeDue property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getAssigneeDue() {
        return assigneeDue;
    }

    /**
     * Sets the value of the assigneeDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setAssigneeDue(DateTime value) {
        this.assigneeDue = value;
    }

    /**
     * Gets the value of the assigneeICalCategories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assigneeICalCategories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssigneeICalCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAssigneeICalCategories() {
        if (assigneeICalCategories == null) {
            assigneeICalCategories = new ArrayList<String>();
        }
        return this.assigneeICalCategories;
    }

    /**
     * Gets the value of the assigneeICalClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssigneeICalClass() {
        return assigneeICalClass;
    }

    /**
     * Sets the value of the assigneeICalClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssigneeICalClass(String value) {
        this.assigneeICalClass = value;
    }

    /**
     * Gets the value of the assigneeICalPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAssigneeICalPriority() {
        return assigneeICalPriority;
    }

    /**
     * Sets the value of the assigneeICalPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAssigneeICalPriority(Integer value) {
        this.assigneeICalPriority = value;
    }

    /**
     * Gets the value of the assigneeMileage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssigneeMileage() {
        return assigneeMileage;
    }

    /**
     * Sets the value of the assigneeMileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssigneeMileage(String value) {
        this.assigneeMileage = value;
    }

    /**
     * Gets the value of the assigneeParticipantStatus property.
     * 
     * @return
     *     possible object is
     *     {@link TodoParticipantStatus }
     *     
     */
    public TodoParticipantStatus getAssigneeParticipantStatus() {
        return assigneeParticipantStatus;
    }

    /**
     * Sets the value of the assigneeParticipantStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoParticipantStatus }
     *     
     */
    public void setAssigneeParticipantStatus(TodoParticipantStatus value) {
        this.assigneeParticipantStatus = value;
    }

    /**
     * Gets the value of the assigneePercentComplete property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAssigneePercentComplete() {
        return assigneePercentComplete;
    }

    /**
     * Sets the value of the assigneePercentComplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAssigneePercentComplete(Integer value) {
        this.assigneePercentComplete = value;
    }

    /**
     * Gets the value of the assigneePriority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getAssigneePriority() {
        return assigneePriority;
    }

    /**
     * Sets the value of the assigneePriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setAssigneePriority(Priority value) {
        this.assigneePriority = value;
    }

    /**
     * Gets the value of the assigneePropertiesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public CollabPropertiesUpdater getAssigneePropertiesUpdater() {
        return assigneePropertiesUpdater;
    }

    /**
     * Sets the value of the assigneePropertiesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public void setAssigneePropertiesUpdater(CollabPropertiesUpdater value) {
        this.assigneePropertiesUpdater = value;
    }

    /**
     * Gets the value of the assigneeSortOrdinal property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAssigneeSortOrdinal() {
        return assigneeSortOrdinal;
    }

    /**
     * Sets the value of the assigneeSortOrdinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAssigneeSortOrdinal(Integer value) {
        this.assigneeSortOrdinal = value;
    }

    /**
     * Gets the value of the assigneeStart property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getAssigneeStart() {
        return assigneeStart;
    }

    /**
     * Sets the value of the assigneeStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setAssigneeStart(DateTime value) {
        this.assigneeStart = value;
    }

    /**
     * Gets the value of the assigneeTimeAllocated property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAssigneeTimeAllocated() {
        return assigneeTimeAllocated;
    }

    /**
     * Sets the value of the assigneeTimeAllocated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAssigneeTimeAllocated(Long value) {
        this.assigneeTimeAllocated = value;
    }

    /**
     * Gets the value of the assigneeTimeSpent property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAssigneeTimeSpent() {
        return assigneeTimeSpent;
    }

    /**
     * Sets the value of the assigneeTimeSpent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAssigneeTimeSpent(Long value) {
        this.assigneeTimeSpent = value;
    }

    /**
     * Gets the value of the calDavResourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalDavResourceName() {
        return calDavResourceName;
    }

    /**
     * Sets the value of the calDavResourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalDavResourceName(String value) {
        this.calDavResourceName = value;
    }

    /**
     * Gets the value of the publishAssignmentBonds property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPublishAssignmentBonds() {
        return publishAssignmentBonds;
    }

    /**
     * Sets the value of the publishAssignmentBonds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPublishAssignmentBonds(Boolean value) {
        this.publishAssignmentBonds = value;
    }

    /**
     * Gets the value of the sensitivity property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getSensitivity() {
        return sensitivity;
    }

    /**
     * Sets the value of the sensitivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setSensitivity(BeeId value) {
        this.sensitivity = value;
    }

}
