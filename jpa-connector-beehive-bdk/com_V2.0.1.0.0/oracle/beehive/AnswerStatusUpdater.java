//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for answerStatusUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="answerStatusUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="answerStatus" type="{http://www.oracle.com/beehive}discussionsMessageAnswerStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "answerStatusUpdater", propOrder = {
    "answerStatus"
})
public class AnswerStatusUpdater
    extends IdentifiableUpdater
{

    protected DiscussionsMessageAnswerStatus answerStatus;

    /**
     * Gets the value of the answerStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessageAnswerStatus }
     *     
     */
    public DiscussionsMessageAnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    /**
     * Sets the value of the answerStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessageAnswerStatus }
     *     
     */
    public void setAnswerStatus(DiscussionsMessageAnswerStatus value) {
        this.answerStatus = value;
    }

}
