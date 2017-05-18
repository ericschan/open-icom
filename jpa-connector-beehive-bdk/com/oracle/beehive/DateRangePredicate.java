//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for dateRangePredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dateRangePredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dateRangePredicate", propOrder = {
    "endDate",
    "startDate"
})
@XmlSeeAlso({
    ExpiresOnPredicate.class,
    LastPostOnPredicate.class,
    TodoDuePredicate.class,
    TaskIntersectingPredicate.class,
    AssignmentDuePredicate.class,
    DeviceLogUploadedOnPredicate.class,
    SimpleAlarmDuePredicate.class,
    TodoIntersectingPredicate.class,
    StartOnPredicate.class,
    EndOnPredicate.class,
    ConnectionRequestRespondByPredicate.class,
    ArchivedOnPredicate.class,
    AssignmentStartPredicate.class,
    AssignmentAssigneeDuePredicate.class,
    ReminderTriggerPredicate.class,
    LabelBundleAppliedOnPredicate.class,
    DeletedOnPredicate.class,
    AssignmentIntersectingPredicate.class,
    CreatedOnPredicate.class,
    InvitationIntersectingPredicate.class,
    CompletedOnPredicate.class,
    InvitationStartPredicate.class,
    ModifiedOnPredicate.class,
    DeviceCommandExecutedOnPredicate.class,
    TaskDuePredicate.class,
    ActivatesOnPredicate.class,
    TaskStartPredicate.class,
    LabelAppliedOnPredicate.class,
    TodoStartPredicate.class,
    OccurrenceIntersectingPredicate.class,
    AssignmentAssigneeStartPredicate.class
})
public class DateRangePredicate
    extends Predicate
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

}
