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
 * <p>Java class for expertiseMatchProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseMatchProfile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="averageMatchStrength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="averageStrength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="clauses" type="{http://www.oracle.com/beehive}expertiseClauseMatch" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="owner" type="{http://www.oracle.com/beehive}entity" minOccurs="0"/>
 *         &lt;element name="peakStrength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="topics" type="{http://www.oracle.com/beehive}expertiseTopic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseMatchProfile", propOrder = {
    "averageMatchStrength",
    "averageStrength",
    "clauses",
    "owner",
    "peakStrength",
    "topics"
})
public class ExpertiseMatchProfile {

    protected int averageMatchStrength;
    protected int averageStrength;
    @XmlElement(nillable = true)
    protected List<ExpertiseClauseMatch> clauses;
    protected Entity owner;
    protected int peakStrength;
    @XmlElement(nillable = true)
    protected List<ExpertiseTopic> topics;

    /**
     * Gets the value of the averageMatchStrength property.
     * 
     */
    public int getAverageMatchStrength() {
        return averageMatchStrength;
    }

    /**
     * Sets the value of the averageMatchStrength property.
     * 
     */
    public void setAverageMatchStrength(int value) {
        this.averageMatchStrength = value;
    }

    /**
     * Gets the value of the averageStrength property.
     * 
     */
    public int getAverageStrength() {
        return averageStrength;
    }

    /**
     * Sets the value of the averageStrength property.
     * 
     */
    public void setAverageStrength(int value) {
        this.averageStrength = value;
    }

    /**
     * Gets the value of the clauses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clauses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClauses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseClauseMatch }
     * 
     * 
     */
    public List<ExpertiseClauseMatch> getClauses() {
        if (clauses == null) {
            clauses = new ArrayList<ExpertiseClauseMatch>();
        }
        return this.clauses;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link Entity }
     *     
     */
    public Entity getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entity }
     *     
     */
    public void setOwner(Entity value) {
        this.owner = value;
    }

    /**
     * Gets the value of the peakStrength property.
     * 
     */
    public int getPeakStrength() {
        return peakStrength;
    }

    /**
     * Sets the value of the peakStrength property.
     * 
     */
    public void setPeakStrength(int value) {
        this.peakStrength = value;
    }

    /**
     * Gets the value of the topics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseTopic }
     * 
     * 
     */
    public List<ExpertiseTopic> getTopics() {
        if (topics == null) {
            topics = new ArrayList<ExpertiseTopic>();
        }
        return this.topics;
    }

}
