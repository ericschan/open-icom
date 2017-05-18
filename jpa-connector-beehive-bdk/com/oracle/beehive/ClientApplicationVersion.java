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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clientApplicationVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clientApplicationVersion">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="configuration" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="patchsets" type="{http://www.oracle.com/beehive}clientApplicationPatchset" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supportedLocales" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versionNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientApplicationVersion", propOrder = {
    "configuration",
    "patchsets",
    "supportedLocales",
    "version",
    "versionNumber"
})
public class ClientApplicationVersion
    extends Entity
{

    protected CollabProperties configuration;
    @XmlElement(nillable = true)
    protected List<ClientApplicationPatchset> patchsets;
    @XmlElement(nillable = true)
    protected List<String> supportedLocales;
    protected String version;
    protected int versionNumber;

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setConfiguration(CollabProperties value) {
        this.configuration = value;
    }

    /**
     * Gets the value of the patchsets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the patchsets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatchsets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClientApplicationPatchset }
     * 
     * 
     */
    public List<ClientApplicationPatchset> getPatchsets() {
        if (patchsets == null) {
            patchsets = new ArrayList<ClientApplicationPatchset>();
        }
        return this.patchsets;
    }

    /**
     * Gets the value of the supportedLocales property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportedLocales property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportedLocales().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSupportedLocales() {
        if (supportedLocales == null) {
            supportedLocales = new ArrayList<String>();
        }
        return this.supportedLocales;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the versionNumber property.
     * 
     */
    public int getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     * 
     */
    public void setVersionNumber(int value) {
        this.versionNumber = value;
    }

}