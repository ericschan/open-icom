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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for conferenceSettingsUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="conferenceSettingsUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="addedProperties" type="{http://www.oracle.com/beehive}conferenceProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="properties" type="{http://www.oracle.com/beehive}conferenceProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removedProperties" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://www.oracle.com/beehive}conferenceRoleListUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conferenceSettingsUpdater", propOrder = {
    "addedProperties",
    "properties",
    "removedProperties",
    "roles"
})
public class ConferenceSettingsUpdater
    extends IdentifiableUpdater
{

    protected List<ConferenceProperty> addedProperties;
    protected List<ConferenceProperty> properties;
    protected List<String> removedProperties;
    protected ConferenceRoleListUpdater roles;

    /**
     * Gets the value of the addedProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addedProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddedProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConferenceProperty }
     * 
     * 
     */
    public List<ConferenceProperty> getAddedProperties() {
        if (addedProperties == null) {
            addedProperties = new ArrayList<ConferenceProperty>();
        }
        return this.addedProperties;
    }

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConferenceProperty }
     * 
     * 
     */
    public List<ConferenceProperty> getProperties() {
        if (properties == null) {
            properties = new ArrayList<ConferenceProperty>();
        }
        return this.properties;
    }

    /**
     * Gets the value of the removedProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removedProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemovedProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRemovedProperties() {
        if (removedProperties == null) {
            removedProperties = new ArrayList<String>();
        }
        return this.removedProperties;
    }

    /**
     * Gets the value of the roles property.
     * 
     * @return
     *     possible object is
     *     {@link ConferenceRoleListUpdater }
     *     
     */
    public ConferenceRoleListUpdater getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceRoleListUpdater }
     *     
     */
    public void setRoles(ConferenceRoleListUpdater value) {
        this.roles = value;
    }

}