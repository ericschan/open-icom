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
 * <p>Java class for discussionsMessageDeletePermissionsUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="discussionsMessageDeletePermissionsUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="timeLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="when" type="{http://www.oracle.com/beehive}discussionsMessageCondition" minOccurs="0"/>
 *         &lt;element name="who" type="{http://www.oracle.com/beehive}discussionsMessageActor" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "discussionsMessageDeletePermissionsUpdater", propOrder = {
    "timeLimit",
    "when",
    "who"
})
public class DiscussionsMessageDeletePermissionsUpdater
    extends IdentifiableUpdater
{

    protected Integer timeLimit;
    protected DiscussionsMessageCondition when;
    protected DiscussionsMessageActor who;

    /**
     * Gets the value of the timeLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the value of the timeLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeLimit(Integer value) {
        this.timeLimit = value;
    }

    /**
     * Gets the value of the when property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessageCondition }
     *     
     */
    public DiscussionsMessageCondition getWhen() {
        return when;
    }

    /**
     * Sets the value of the when property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessageCondition }
     *     
     */
    public void setWhen(DiscussionsMessageCondition value) {
        this.when = value;
    }

    /**
     * Gets the value of the who property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessageActor }
     *     
     */
    public DiscussionsMessageActor getWho() {
        return who;
    }

    /**
     * Sets the value of the who property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessageActor }
     *     
     */
    public void setWho(DiscussionsMessageActor value) {
        this.who = value;
    }

}
