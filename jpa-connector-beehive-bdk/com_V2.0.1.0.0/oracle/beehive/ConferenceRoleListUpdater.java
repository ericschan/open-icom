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
 * <p>Java class for conferenceRoleListUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="conferenceRoleListUpdater">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="add" type="{http://www.oracle.com/beehive}conferenceRoleUpdater" minOccurs="0"/>
 *         &lt;element name="remove" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="update" type="{http://www.oracle.com/beehive}conferenceRoleUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conferenceRoleListUpdater", propOrder = {
    "add",
    "remove",
    "update"
})
public class ConferenceRoleListUpdater {

    protected ConferenceRoleUpdater add;
    protected String remove;
    protected ConferenceRoleUpdater update;

    /**
     * Gets the value of the add property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceRoleUpdater }
     *     
     */
    public ConferenceRoleUpdater getAdd() {
        return add;
    }

    /**
     * Sets the value of the add property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceRoleUpdater }
     *     
     */
    public void setAdd(ConferenceRoleUpdater value) {
        this.add = value;
    }

    /**
     * Gets the value of the remove property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemove() {
        return remove;
    }

    /**
     * Sets the value of the remove property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemove(String value) {
        this.remove = value;
    }

    /**
     * Gets the value of the update property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceRoleUpdater }
     *     
     */
    public ConferenceRoleUpdater getUpdate() {
        return update;
    }

    /**
     * Sets the value of the update property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceRoleUpdater }
     *     
     */
    public void setUpdate(ConferenceRoleUpdater value) {
        this.update = value;
    }

}
