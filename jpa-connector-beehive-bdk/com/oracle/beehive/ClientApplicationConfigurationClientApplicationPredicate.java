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
 * <p>Java class for clientApplicationConfigurationClientApplicationPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clientApplicationConfigurationClientApplicationPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}clientApplicationConfigurationPredicate">
 *       &lt;sequence>
 *         &lt;element name="clientApplication" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientApplicationConfigurationClientApplicationPredicate", propOrder = {
    "clientApplication"
})
public class ClientApplicationConfigurationClientApplicationPredicate
    extends ClientApplicationConfigurationPredicate
{

    protected BeeId clientApplication;

    /**
     * Gets the value of the clientApplication property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getClientApplication() {
        return clientApplication;
    }

    /**
     * Sets the value of the clientApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setClientApplication(BeeId value) {
        this.clientApplication = value;
    }

}