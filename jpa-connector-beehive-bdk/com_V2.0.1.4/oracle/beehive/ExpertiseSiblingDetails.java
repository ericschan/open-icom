//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for expertiseSiblingDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseSiblingDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="afterKeyword" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="averageCohesiveness" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseSiblingDetails", propOrder = {
    "afterKeyword",
    "averageCohesiveness"
})
public class ExpertiseSiblingDetails {

    protected boolean afterKeyword;
    protected float averageCohesiveness;

    /**
     * Gets the value of the afterKeyword property.
     * 
     */
    public boolean isAfterKeyword() {
        return afterKeyword;
    }

    /**
     * Sets the value of the afterKeyword property.
     * 
     */
    public void setAfterKeyword(boolean value) {
        this.afterKeyword = value;
    }

    /**
     * Gets the value of the averageCohesiveness property.
     * 
     */
    public float getAverageCohesiveness() {
        return averageCohesiveness;
    }

    /**
     * Sets the value of the averageCohesiveness property.
     * 
     */
    public void setAverageCohesiveness(float value) {
        this.averageCohesiveness = value;
    }

}