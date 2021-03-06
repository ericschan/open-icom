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
 * <p>Java class for expertiseTarget complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseTarget">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}expertisePerson">
 *       &lt;sequence>
 *         &lt;element name="firstUpdated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fromCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fromSearcherStrength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lastUpdated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="score" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="scoreDetails" type="{http://www.oracle.com/beehive}expertiseScore" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="toCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="visibility" type="{http://www.oracle.com/beehive}relationshipVisibility" minOccurs="0"/>
 *         &lt;element name="visibilityModifiedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseTarget", propOrder = {
    "firstUpdated",
    "fromCount",
    "fromSearcherStrength",
    "lastUpdated",
    "score",
    "scoreDetails",
    "toCount",
    "visibility",
    "visibilityModifiedOn"
})
public class ExpertiseTarget
    extends ExpertisePerson
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar firstUpdated;
    protected int fromCount;
    protected int fromSearcherStrength;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdated;
    protected int score;
    @XmlElement(nillable = true)
    protected List<ExpertiseScore> scoreDetails;
    protected int toCount;
    protected RelationshipVisibility visibility;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar visibilityModifiedOn;

    /**
     * Gets the value of the firstUpdated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFirstUpdated() {
        return firstUpdated;
    }

    /**
     * Sets the value of the firstUpdated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFirstUpdated(XMLGregorianCalendar value) {
        this.firstUpdated = value;
    }

    /**
     * Gets the value of the fromCount property.
     * 
     */
    public int getFromCount() {
        return fromCount;
    }

    /**
     * Sets the value of the fromCount property.
     * 
     */
    public void setFromCount(int value) {
        this.fromCount = value;
    }

    /**
     * Gets the value of the fromSearcherStrength property.
     * 
     */
    public int getFromSearcherStrength() {
        return fromSearcherStrength;
    }

    /**
     * Sets the value of the fromSearcherStrength property.
     * 
     */
    public void setFromSearcherStrength(int value) {
        this.fromSearcherStrength = value;
    }

    /**
     * Gets the value of the lastUpdated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the value of the lastUpdated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdated(XMLGregorianCalendar value) {
        this.lastUpdated = value;
    }

    /**
     * Gets the value of the score property.
     * 
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     */
    public void setScore(int value) {
        this.score = value;
    }

    /**
     * Gets the value of the scoreDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scoreDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScoreDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseScore }
     * 
     * 
     */
    public List<ExpertiseScore> getScoreDetails() {
        if (scoreDetails == null) {
            scoreDetails = new ArrayList<ExpertiseScore>();
        }
        return this.scoreDetails;
    }

    /**
     * Gets the value of the toCount property.
     * 
     */
    public int getToCount() {
        return toCount;
    }

    /**
     * Sets the value of the toCount property.
     * 
     */
    public void setToCount(int value) {
        this.toCount = value;
    }

    /**
     * Gets the value of the visibility property.
     * 
     * @return
     *     possible object is
     *     {@link RelationshipVisibility }
     *     
     */
    public RelationshipVisibility getVisibility() {
        return visibility;
    }

    /**
     * Sets the value of the visibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationshipVisibility }
     *     
     */
    public void setVisibility(RelationshipVisibility value) {
        this.visibility = value;
    }

    /**
     * Gets the value of the visibilityModifiedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVisibilityModifiedOn() {
        return visibilityModifiedOn;
    }

    /**
     * Sets the value of the visibilityModifiedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVisibilityModifiedOn(XMLGregorianCalendar value) {
        this.visibilityModifiedOn = value;
    }

}
