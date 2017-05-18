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
 * <p>Java class for installedClientApplication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="installedClientApplication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientApplication" type="{http://www.oracle.com/beehive}clientApplication" minOccurs="0"/>
 *         &lt;element name="configuration" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="patchsetNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versionNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "installedClientApplication", propOrder = {
    "clientApplication",
    "configuration",
    "patchsetNumber",
    "version",
    "versionNumber"
})
public class InstalledClientApplication {

    protected ClientApplication clientApplication;
    protected CollabProperties configuration;
    protected int patchsetNumber;
    protected String version;
    protected int versionNumber;

    /**
     * Gets the value of the clientApplication property.
     * 
     * @return
     *     possible object is
     *     {@link ClientApplication }
     *     
     */
    public ClientApplication getClientApplication() {
        return clientApplication;
    }

    /**
     * Sets the value of the clientApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientApplication }
     *     
     */
    public void setClientApplication(ClientApplication value) {
        this.clientApplication = value;
    }

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
     * Gets the value of the patchsetNumber property.
     * 
     */
    public int getPatchsetNumber() {
        return patchsetNumber;
    }

    /**
     * Sets the value of the patchsetNumber property.
     * 
     */
    public void setPatchsetNumber(int value) {
        this.patchsetNumber = value;
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
