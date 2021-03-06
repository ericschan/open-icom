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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dateTimeRecurrenceSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dateTimeRecurrenceSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="excludeDateTimes" type="{http://www.oracle.com/beehive}dateTime" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="includeDateTimes" type="{http://www.oracle.com/beehive}dateTime" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inclusionRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDateTime" type="{http://www.oracle.com/beehive}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dateTimeRecurrenceSet", propOrder = {
    "excludeDateTimes",
    "includeDateTimes",
    "inclusionRule",
    "startDateTime"
})
public class DateTimeRecurrenceSet {

    protected List<DateTime> excludeDateTimes;
    protected List<DateTime> includeDateTimes;
    protected String inclusionRule;
    protected DateTime startDateTime;

    /**
     * Gets the value of the excludeDateTimes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the excludeDateTimes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExcludeDateTimes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateTime }
     * 
     * 
     */
    public List<DateTime> getExcludeDateTimes() {
        if (excludeDateTimes == null) {
            excludeDateTimes = new ArrayList<DateTime>();
        }
        return this.excludeDateTimes;
    }

    /**
     * Gets the value of the includeDateTimes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includeDateTimes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludeDateTimes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateTime }
     * 
     * 
     */
    public List<DateTime> getIncludeDateTimes() {
        if (includeDateTimes == null) {
            includeDateTimes = new ArrayList<DateTime>();
        }
        return this.includeDateTimes;
    }

    /**
     * Gets the value of the inclusionRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInclusionRule() {
        return inclusionRule;
    }

    /**
     * Sets the value of the inclusionRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInclusionRule(String value) {
        this.inclusionRule = value;
    }

    /**
     * Gets the value of the startDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets the value of the startDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setStartDateTime(DateTime value) {
        this.startDateTime = value;
    }

}
