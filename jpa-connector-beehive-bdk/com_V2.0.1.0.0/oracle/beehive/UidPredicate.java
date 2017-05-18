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
 * <p>Java class for uidPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uidPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="rangeEnd" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rangeStart" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="UIDSet" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uidPredicate", propOrder = {
    "rangeEnd",
    "rangeStart",
    "uidSets"
})
public class UidPredicate
    extends Predicate
{

    protected Long rangeEnd;
    protected Long rangeStart;
    @XmlElement(name = "UIDSet", nillable = true)
    protected List<Long> uidSets;

    /**
     * Gets the value of the rangeEnd property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRangeEnd() {
        return rangeEnd;
    }

    /**
     * Sets the value of the rangeEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRangeEnd(Long value) {
        this.rangeEnd = value;
    }

    /**
     * Gets the value of the rangeStart property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRangeStart() {
        return rangeStart;
    }

    /**
     * Sets the value of the rangeStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRangeStart(Long value) {
        this.rangeStart = value;
    }

    /**
     * Gets the value of the uidSets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uidSets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUIDSets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getUIDSets() {
        if (uidSets == null) {
            uidSets = new ArrayList<Long>();
        }
        return this.uidSets;
    }

}