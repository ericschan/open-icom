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
 * <p>Java class for expertiseClauseMatch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseClauseMatch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clause" type="{http://www.oracle.com/beehive}expertiseSearchClause" minOccurs="0"/>
 *         &lt;element name="matchedTerms" type="{http://www.oracle.com/beehive}expertiseTerm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="matchedWords" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numMatchedTerms" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="strength" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseClauseMatch", propOrder = {
    "clause",
    "matchedTerms",
    "matchedWords",
    "numMatchedTerms",
    "strength"
})
public class ExpertiseClauseMatch {

    protected ExpertiseSearchClause clause;
    @XmlElement(nillable = true)
    protected List<ExpertiseTerm> matchedTerms;
    @XmlElement(nillable = true)
    protected List<String> matchedWords;
    protected int numMatchedTerms;
    protected int strength;

    /**
     * Gets the value of the clause property.
     * 
     * @return
     *     possible object is
     *     {@link ExpertiseSearchClause }
     *     
     */
    public ExpertiseSearchClause getClause() {
        return clause;
    }

    /**
     * Sets the value of the clause property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpertiseSearchClause }
     *     
     */
    public void setClause(ExpertiseSearchClause value) {
        this.clause = value;
    }

    /**
     * Gets the value of the matchedTerms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matchedTerms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatchedTerms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseTerm }
     * 
     * 
     */
    public List<ExpertiseTerm> getMatchedTerms() {
        if (matchedTerms == null) {
            matchedTerms = new ArrayList<ExpertiseTerm>();
        }
        return this.matchedTerms;
    }

    /**
     * Gets the value of the matchedWords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matchedWords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatchedWords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMatchedWords() {
        if (matchedWords == null) {
            matchedWords = new ArrayList<String>();
        }
        return this.matchedWords;
    }

    /**
     * Gets the value of the numMatchedTerms property.
     * 
     */
    public int getNumMatchedTerms() {
        return numMatchedTerms;
    }

    /**
     * Sets the value of the numMatchedTerms property.
     * 
     */
    public void setNumMatchedTerms(int value) {
        this.numMatchedTerms = value;
    }

    /**
     * Gets the value of the strength property.
     * 
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Sets the value of the strength property.
     * 
     */
    public void setStrength(int value) {
        this.strength = value;
    }

}