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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for multiWeekBusinessHours complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="multiWeekBusinessHours">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}businessHours">
 *       &lt;sequence>
 *         &lt;element name="alternatingWeekBusinessHours" type="{http://www.oracle.com/beehive}weekBusinessHours" maxOccurs="unbounded"/>
 *         &lt;element name="alternatingWeekBusinessHouse" type="{http://www.oracle.com/beehive}weekBusinessHours" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="start" type="{http://www.oracle.com/beehive}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "multiWeekBusinessHours", propOrder = {
    "alternatingWeekBusinessHours",
    "alternatingWeekBusinessHouses",
    "start"
})
public class MultiWeekBusinessHours
    extends BusinessHours
{

    @XmlElement(required = true)
    protected List<WeekBusinessHours> alternatingWeekBusinessHours;
    @XmlElement(name = "alternatingWeekBusinessHouse")
    protected List<WeekBusinessHours> alternatingWeekBusinessHouses;
    @XmlElement(required = true)
    protected DateTime start;

    /**
     * Gets the value of the alternatingWeekBusinessHours property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alternatingWeekBusinessHours property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlternatingWeekBusinessHours().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WeekBusinessHours }
     * 
     * 
     */
    public List<WeekBusinessHours> getAlternatingWeekBusinessHours() {
        if (alternatingWeekBusinessHours == null) {
            alternatingWeekBusinessHours = new ArrayList<WeekBusinessHours>();
        }
        return this.alternatingWeekBusinessHours;
    }

    /**
     * Gets the value of the alternatingWeekBusinessHouses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alternatingWeekBusinessHouses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlternatingWeekBusinessHouses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WeekBusinessHours }
     * 
     * 
     */
    public List<WeekBusinessHours> getAlternatingWeekBusinessHouses() {
        if (alternatingWeekBusinessHouses == null) {
            alternatingWeekBusinessHouses = new ArrayList<WeekBusinessHours>();
        }
        return this.alternatingWeekBusinessHouses;
    }

    /**
     * Gets the value of the start property.
     * 
     * @return
     *     possible object is
     *     {@link DateTime }
     *     
     */
    public DateTime getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTime }
     *     
     */
    public void setStart(DateTime value) {
        this.start = value;
    }

}
