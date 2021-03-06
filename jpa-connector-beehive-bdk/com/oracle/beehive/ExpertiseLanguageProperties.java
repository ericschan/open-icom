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
 * <p>Java class for expertiseLanguageProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseLanguageProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="defaultMaxTermsPerTopic" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="groupingMaxAnyPathInclusionRatio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="groupingMaxAssocWords" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="groupingMaxAvgCohForAsPhrase" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="groupingMaxAvgRatioForInclusion" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="groupingMaxEdgeCombineAvgCoh" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="groupingMaxKeywordsPerSet" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="groupingMaxPeakRatioForInclusion" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="groupingMaxWeight" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="groupingMinEdgeCombineOcc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="groupingMinEdgeCombineWeight" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="groupingMinUngroupableDiscard" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="groupingMinWordsForAcceptance" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="languageCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minCoveredWordsToDisqualify" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="precedence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseLanguageProperties", propOrder = {
    "defaultMaxTermsPerTopic",
    "groupingMaxAnyPathInclusionRatio",
    "groupingMaxAssocWords",
    "groupingMaxAvgCohForAsPhrase",
    "groupingMaxAvgRatioForInclusion",
    "groupingMaxEdgeCombineAvgCoh",
    "groupingMaxKeywordsPerSet",
    "groupingMaxPeakRatioForInclusion",
    "groupingMaxWeight",
    "groupingMinEdgeCombineOcc",
    "groupingMinEdgeCombineWeight",
    "groupingMinUngroupableDiscard",
    "groupingMinWordsForAcceptance",
    "languageCode",
    "minCoveredWordsToDisqualify",
    "precedence"
})
public class ExpertiseLanguageProperties {

    protected int defaultMaxTermsPerTopic;
    protected double groupingMaxAnyPathInclusionRatio;
    protected int groupingMaxAssocWords;
    protected float groupingMaxAvgCohForAsPhrase;
    protected double groupingMaxAvgRatioForInclusion;
    protected float groupingMaxEdgeCombineAvgCoh;
    protected int groupingMaxKeywordsPerSet;
    protected double groupingMaxPeakRatioForInclusion;
    protected double groupingMaxWeight;
    protected int groupingMinEdgeCombineOcc;
    protected float groupingMinEdgeCombineWeight;
    protected int groupingMinUngroupableDiscard;
    protected int groupingMinWordsForAcceptance;
    protected int languageCode;
    protected int minCoveredWordsToDisqualify;
    protected int precedence;

    /**
     * Gets the value of the defaultMaxTermsPerTopic property.
     * 
     */
    public int getDefaultMaxTermsPerTopic() {
        return defaultMaxTermsPerTopic;
    }

    /**
     * Sets the value of the defaultMaxTermsPerTopic property.
     * 
     */
    public void setDefaultMaxTermsPerTopic(int value) {
        this.defaultMaxTermsPerTopic = value;
    }

    /**
     * Gets the value of the groupingMaxAnyPathInclusionRatio property.
     * 
     */
    public double getGroupingMaxAnyPathInclusionRatio() {
        return groupingMaxAnyPathInclusionRatio;
    }

    /**
     * Sets the value of the groupingMaxAnyPathInclusionRatio property.
     * 
     */
    public void setGroupingMaxAnyPathInclusionRatio(double value) {
        this.groupingMaxAnyPathInclusionRatio = value;
    }

    /**
     * Gets the value of the groupingMaxAssocWords property.
     * 
     */
    public int getGroupingMaxAssocWords() {
        return groupingMaxAssocWords;
    }

    /**
     * Sets the value of the groupingMaxAssocWords property.
     * 
     */
    public void setGroupingMaxAssocWords(int value) {
        this.groupingMaxAssocWords = value;
    }

    /**
     * Gets the value of the groupingMaxAvgCohForAsPhrase property.
     * 
     */
    public float getGroupingMaxAvgCohForAsPhrase() {
        return groupingMaxAvgCohForAsPhrase;
    }

    /**
     * Sets the value of the groupingMaxAvgCohForAsPhrase property.
     * 
     */
    public void setGroupingMaxAvgCohForAsPhrase(float value) {
        this.groupingMaxAvgCohForAsPhrase = value;
    }

    /**
     * Gets the value of the groupingMaxAvgRatioForInclusion property.
     * 
     */
    public double getGroupingMaxAvgRatioForInclusion() {
        return groupingMaxAvgRatioForInclusion;
    }

    /**
     * Sets the value of the groupingMaxAvgRatioForInclusion property.
     * 
     */
    public void setGroupingMaxAvgRatioForInclusion(double value) {
        this.groupingMaxAvgRatioForInclusion = value;
    }

    /**
     * Gets the value of the groupingMaxEdgeCombineAvgCoh property.
     * 
     */
    public float getGroupingMaxEdgeCombineAvgCoh() {
        return groupingMaxEdgeCombineAvgCoh;
    }

    /**
     * Sets the value of the groupingMaxEdgeCombineAvgCoh property.
     * 
     */
    public void setGroupingMaxEdgeCombineAvgCoh(float value) {
        this.groupingMaxEdgeCombineAvgCoh = value;
    }

    /**
     * Gets the value of the groupingMaxKeywordsPerSet property.
     * 
     */
    public int getGroupingMaxKeywordsPerSet() {
        return groupingMaxKeywordsPerSet;
    }

    /**
     * Sets the value of the groupingMaxKeywordsPerSet property.
     * 
     */
    public void setGroupingMaxKeywordsPerSet(int value) {
        this.groupingMaxKeywordsPerSet = value;
    }

    /**
     * Gets the value of the groupingMaxPeakRatioForInclusion property.
     * 
     */
    public double getGroupingMaxPeakRatioForInclusion() {
        return groupingMaxPeakRatioForInclusion;
    }

    /**
     * Sets the value of the groupingMaxPeakRatioForInclusion property.
     * 
     */
    public void setGroupingMaxPeakRatioForInclusion(double value) {
        this.groupingMaxPeakRatioForInclusion = value;
    }

    /**
     * Gets the value of the groupingMaxWeight property.
     * 
     */
    public double getGroupingMaxWeight() {
        return groupingMaxWeight;
    }

    /**
     * Sets the value of the groupingMaxWeight property.
     * 
     */
    public void setGroupingMaxWeight(double value) {
        this.groupingMaxWeight = value;
    }

    /**
     * Gets the value of the groupingMinEdgeCombineOcc property.
     * 
     */
    public int getGroupingMinEdgeCombineOcc() {
        return groupingMinEdgeCombineOcc;
    }

    /**
     * Sets the value of the groupingMinEdgeCombineOcc property.
     * 
     */
    public void setGroupingMinEdgeCombineOcc(int value) {
        this.groupingMinEdgeCombineOcc = value;
    }

    /**
     * Gets the value of the groupingMinEdgeCombineWeight property.
     * 
     */
    public float getGroupingMinEdgeCombineWeight() {
        return groupingMinEdgeCombineWeight;
    }

    /**
     * Sets the value of the groupingMinEdgeCombineWeight property.
     * 
     */
    public void setGroupingMinEdgeCombineWeight(float value) {
        this.groupingMinEdgeCombineWeight = value;
    }

    /**
     * Gets the value of the groupingMinUngroupableDiscard property.
     * 
     */
    public int getGroupingMinUngroupableDiscard() {
        return groupingMinUngroupableDiscard;
    }

    /**
     * Sets the value of the groupingMinUngroupableDiscard property.
     * 
     */
    public void setGroupingMinUngroupableDiscard(int value) {
        this.groupingMinUngroupableDiscard = value;
    }

    /**
     * Gets the value of the groupingMinWordsForAcceptance property.
     * 
     */
    public int getGroupingMinWordsForAcceptance() {
        return groupingMinWordsForAcceptance;
    }

    /**
     * Sets the value of the groupingMinWordsForAcceptance property.
     * 
     */
    public void setGroupingMinWordsForAcceptance(int value) {
        this.groupingMinWordsForAcceptance = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     */
    public int getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     */
    public void setLanguageCode(int value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the minCoveredWordsToDisqualify property.
     * 
     */
    public int getMinCoveredWordsToDisqualify() {
        return minCoveredWordsToDisqualify;
    }

    /**
     * Sets the value of the minCoveredWordsToDisqualify property.
     * 
     */
    public void setMinCoveredWordsToDisqualify(int value) {
        this.minCoveredWordsToDisqualify = value;
    }

    /**
     * Gets the value of the precedence property.
     * 
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Sets the value of the precedence property.
     * 
     */
    public void setPrecedence(int value) {
        this.precedence = value;
    }

}
