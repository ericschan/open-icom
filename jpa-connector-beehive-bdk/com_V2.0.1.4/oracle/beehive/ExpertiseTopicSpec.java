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
 * <p>Java class for expertiseTopicSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseTopicSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applyStopWords" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="buildProperties" type="{http://www.oracle.com/beehive}expertiseTopicsBuildProperties" minOccurs="0"/>
 *         &lt;element name="returnOnlyTopNTopicPerKeywordSet" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseTopicSpec", propOrder = {
    "applyStopWords",
    "buildProperties",
    "returnOnlyTopNTopicPerKeywordSet"
})
public class ExpertiseTopicSpec {

    protected boolean applyStopWords;
    protected ExpertiseTopicsBuildProperties buildProperties;
    protected int returnOnlyTopNTopicPerKeywordSet;

    /**
     * Gets the value of the applyStopWords property.
     * 
     */
    public boolean isApplyStopWords() {
        return applyStopWords;
    }

    /**
     * Sets the value of the applyStopWords property.
     * 
     */
    public void setApplyStopWords(boolean value) {
        this.applyStopWords = value;
    }

    /**
     * Gets the value of the buildProperties property.
     * 
     * @return
     *     possible object is
     *     {@link ExpertiseTopicsBuildProperties }
     *     
     */
    public ExpertiseTopicsBuildProperties getBuildProperties() {
        return buildProperties;
    }

    /**
     * Sets the value of the buildProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpertiseTopicsBuildProperties }
     *     
     */
    public void setBuildProperties(ExpertiseTopicsBuildProperties value) {
        this.buildProperties = value;
    }

    /**
     * Gets the value of the returnOnlyTopNTopicPerKeywordSet property.
     * 
     */
    public int getReturnOnlyTopNTopicPerKeywordSet() {
        return returnOnlyTopNTopicPerKeywordSet;
    }

    /**
     * Sets the value of the returnOnlyTopNTopicPerKeywordSet property.
     * 
     */
    public void setReturnOnlyTopNTopicPerKeywordSet(int value) {
        this.returnOnlyTopNTopicPerKeywordSet = value;
    }

}
