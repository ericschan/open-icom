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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for expertiseSearchClauseList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseSearchClauseList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bands" type="{http://www.oracle.com/beehive}expertiseSearchBand" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="enforcement" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ignoredTextStartPos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="skippedCommonWords" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="termPositions" type="{http://www.oracle.com/beehive}expertiseClauseTermPosition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="textPositions" type="{http://www.oracle.com/beehive}expertiseClauseTextPosition" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseSearchClauseList", propOrder = {
    "bands",
    "enforcement",
    "ignoredTextStartPos",
    "skippedCommonWords",
    "termPositions",
    "textPositions"
})
@XmlRootElement(name = "expertiseSearchClauseList")
public class ExpertiseSearchClauseList {

    @XmlElement(nillable = true)
    protected List<ExpertiseSearchBand> bands;
    protected int enforcement;
    protected int ignoredTextStartPos;
    @XmlElement(nillable = true)
    protected List<String> skippedCommonWords;
    @XmlElement(nillable = true)
    protected List<ExpertiseClauseTermPosition> termPositions;
    @XmlElement(nillable = true)
    protected List<ExpertiseClauseTextPosition> textPositions;

    /**
     * Gets the value of the bands property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bands property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBands().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseSearchBand }
     * 
     * 
     */
    public List<ExpertiseSearchBand> getBands() {
        if (bands == null) {
            bands = new ArrayList<ExpertiseSearchBand>();
        }
        return this.bands;
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
     * Gets the value of the ignoredTextStartPos property.
     * 
     */
    public int getIgnoredTextStartPos() {
        return ignoredTextStartPos;
    }

    /**
     * Sets the value of the ignoredTextStartPos property.
     * 
     */
    public void setIgnoredTextStartPos(int value) {
        this.ignoredTextStartPos = value;
    }

    /**
     * Gets the value of the skippedCommonWords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the skippedCommonWords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSkippedCommonWords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSkippedCommonWords() {
        if (skippedCommonWords == null) {
            skippedCommonWords = new ArrayList<String>();
        }
        return this.skippedCommonWords;
    }

    /**
     * Gets the value of the termPositions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the termPositions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTermPositions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseClauseTermPosition }
     * 
     * 
     */
    public List<ExpertiseClauseTermPosition> getTermPositions() {
        if (termPositions == null) {
            termPositions = new ArrayList<ExpertiseClauseTermPosition>();
        }
        return this.termPositions;
    }

    /**
     * Gets the value of the textPositions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textPositions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextPositions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseClauseTextPosition }
     * 
     * 
     */
    public List<ExpertiseClauseTextPosition> getTextPositions() {
        if (textPositions == null) {
            textPositions = new ArrayList<ExpertiseClauseTextPosition>();
        }
        return this.textPositions;
    }

}