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
 * <p>Java class for expertiseConnectionRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseConnectionRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}connectionRequest">
 *       &lt;sequence>
 *         &lt;element name="expertiseTopicTitles" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="responseDiscussionForum" type="{http://www.oracle.com/beehive}forum" minOccurs="0"/>
 *         &lt;element name="responseDiscussionTopic" type="{http://www.oracle.com/beehive}topic" minOccurs="0"/>
 *         &lt;element name="searchExpression" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="terms" type="{http://www.oracle.com/beehive}expertiseTerm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="topicBased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="topics" type="{http://www.oracle.com/beehive}expertiseDynamicTopic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseConnectionRequest", propOrder = {
    "expertiseTopicTitles",
    "responseDiscussionForum",
    "responseDiscussionTopic",
    "searchExpression",
    "terms",
    "topicBased",
    "topics"
})
@XmlRootElement(name = "expertiseConnectionRequest")
public class ExpertiseConnectionRequest
    extends ConnectionRequest
{

    @XmlElement(nillable = true)
    protected List<String> expertiseTopicTitles;
    protected Forum responseDiscussionForum;
    protected Topic responseDiscussionTopic;
    protected String searchExpression;
    @XmlElement(nillable = true)
    protected List<ExpertiseTerm> terms;
    protected boolean topicBased;
    @XmlElement(nillable = true)
    protected List<ExpertiseDynamicTopic> topics;

    /**
     * Gets the value of the expertiseTopicTitles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the expertiseTopicTitles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExpertiseTopicTitles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExpertiseTopicTitles() {
        if (expertiseTopicTitles == null) {
            expertiseTopicTitles = new ArrayList<String>();
        }
        return this.expertiseTopicTitles;
    }

    /**
     * Gets the value of the responseDiscussionForum property.
     * 
     * @return
     *     possible object is
     *     {@link Forum }
     *     
     */
    public Forum getResponseDiscussionForum() {
        return responseDiscussionForum;
    }

    /**
     * Sets the value of the responseDiscussionForum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Forum }
     *     
     */
    public void setResponseDiscussionForum(Forum value) {
        this.responseDiscussionForum = value;
    }

    /**
     * Gets the value of the responseDiscussionTopic property.
     * 
     * @return
     *     possible object is
     *     {@link Topic }
     *     
     */
    public Topic getResponseDiscussionTopic() {
        return responseDiscussionTopic;
    }

    /**
     * Sets the value of the responseDiscussionTopic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topic }
     *     
     */
    public void setResponseDiscussionTopic(Topic value) {
        this.responseDiscussionTopic = value;
    }

    /**
     * Gets the value of the searchExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchExpression() {
        return searchExpression;
    }

    /**
     * Sets the value of the searchExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchExpression(String value) {
        this.searchExpression = value;
    }

    /**
     * Gets the value of the terms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the terms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTerms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpertiseTerm }
     * 
     * 
     */
    public List<ExpertiseTerm> getTerms() {
        if (terms == null) {
            terms = new ArrayList<ExpertiseTerm>();
        }
        return this.terms;
    }

    /**
     * Gets the value of the topicBased property.
     * 
     */
    public boolean isTopicBased() {
        return topicBased;
    }

    /**
     * Sets the value of the topicBased property.
     * 
     */
    public void setTopicBased(boolean value) {
        this.topicBased = value;
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
     * {@link ExpertiseDynamicTopic }
     * 
     * 
     */
    public List<ExpertiseDynamicTopic> getTopics() {
        if (topics == null) {
            topics = new ArrayList<ExpertiseDynamicTopic>();
        }
        return this.topics;
    }

}
