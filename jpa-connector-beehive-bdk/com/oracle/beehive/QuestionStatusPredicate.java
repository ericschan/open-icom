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
 * <p>Java class for questionStatusPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="questionStatusPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="questionStatus" type="{http://www.oracle.com/beehive}topicQuestionStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "questionStatusPredicate", propOrder = {
    "questionStatus"
})
public class QuestionStatusPredicate
    extends Predicate
{

    protected TopicQuestionStatus questionStatus;

    /**
     * Gets the value of the questionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link TopicQuestionStatus }
     *     
     */
    public TopicQuestionStatus getQuestionStatus() {
        return questionStatus;
    }

    /**
     * Sets the value of the questionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TopicQuestionStatus }
     *     
     */
    public void setQuestionStatus(TopicQuestionStatus value) {
        this.questionStatus = value;
    }

}
