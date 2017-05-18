//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for conferenceUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="conferenceUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="conferenceType" type="{http://www.oracle.com/beehive}conferenceType" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="settingsUpdater" type="{http://www.oracle.com/beehive}conferenceSettingsUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conferenceUpdater", propOrder = {
    "conferenceType",
    "description",
    "settingsUpdater"
})
@XmlSeeAlso({
    ConferenceTemplateUpdater.class
})
public class ConferenceUpdater
    extends ArtifactUpdater
{

    protected ConferenceType conferenceType;
    protected String description;
    protected ConferenceSettingsUpdater settingsUpdater;

    /**
     * Gets the value of the conferenceType property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceType }
     *     
     */
    public ConferenceType getConferenceType() {
        return conferenceType;
    }

    /**
     * Sets the value of the conferenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceType }
     *     
     */
    public void setConferenceType(ConferenceType value) {
        this.conferenceType = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the settingsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceSettingsUpdater }
     *     
     */
    public ConferenceSettingsUpdater getSettingsUpdater() {
        return settingsUpdater;
    }

    /**
     * Sets the value of the settingsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceSettingsUpdater }
     *     
     */
    public void setSettingsUpdater(ConferenceSettingsUpdater value) {
        this.settingsUpdater = value;
    }

}
