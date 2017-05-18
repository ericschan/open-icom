//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for expertiseSearchClause complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseSearchClause">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="asStemIds" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="boolexprLanguage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="enforcement" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="expression" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="matchLanguage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="matchType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="stem" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseSearchClause", propOrder = {
    "asStemIds",
    "boolexprLanguage",
    "enforcement",
    "expression",
    "matchLanguage",
    "matchType",
    "stem",
    "weight"
})
public class ExpertiseSearchClause {

    protected boolean asStemIds;
    protected int boolexprLanguage;
    protected int enforcement;
    protected String expression;
    protected int matchLanguage;
    protected int matchType;
    protected boolean stem;
    protected float weight;

    /**
     * Gets the value of the asStemIds property.
     * 
     */
    public boolean isAsStemIds() {
        return asStemIds;
    }

    /**
     * Sets the value of the asStemIds property.
     * 
     */
    public void setAsStemIds(boolean value) {
        this.asStemIds = value;
    }

    /**
     * Gets the value of the boolexprLanguage property.
     * 
     */
    public int getBoolexprLanguage() {
        return boolexprLanguage;
    }

    /**
     * Sets the value of the boolexprLanguage property.
     * 
     */
    public void setBoolexprLanguage(int value) {
        this.boolexprLanguage = value;
    }

    /**
     * Gets the value of the enforcement property.
     * 
     */
    public int getEnforcement() {
        return enforcement;
    }

    /**
     * Sets the value of the enforcement property.
     * 
     */
    public void setEnforcement(int value) {
        this.enforcement = value;
    }

    /**
     * Gets the value of the expression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the value of the expression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpression(String value) {
        this.expression = value;
    }

    /**
     * Gets the value of the matchLanguage property.
     * 
     */
    public int getMatchLanguage() {
        return matchLanguage;
    }

    /**
     * Sets the value of the matchLanguage property.
     * 
     */
    public void setMatchLanguage(int value) {
        this.matchLanguage = value;
    }

    /**
     * Gets the value of the matchType property.
     * 
     */
    public int getMatchType() {
        return matchType;
    }

    /**
     * Sets the value of the matchType property.
     * 
     */
    public void setMatchType(int value) {
        this.matchType = value;
    }

    /**
     * Gets the value of the stem property.
     * 
     */
    public boolean isStem() {
        return stem;
    }

    /**
     * Sets the value of the stem property.
     * 
     */
    public void setStem(boolean value) {
        this.stem = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     */
    public void setWeight(float value) {
        this.weight = value;
    }

}
