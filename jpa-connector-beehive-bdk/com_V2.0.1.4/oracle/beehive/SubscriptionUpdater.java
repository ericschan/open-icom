//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subscriptionUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subscriptionUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="materializedSubscription" type="{http://www.oracle.com/beehive}materializedSubscription" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subscriptionUpdater", propOrder = {
    "enabled",
    "materializedSubscription"
})
@XmlRootElement(name = "subscriptionUpdater")
public class SubscriptionUpdater
    extends ArtifactUpdater
{

    protected Boolean enabled;
    protected MaterializedSubscription materializedSubscription;

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the materializedSubscription property.
     * 
     * @return
     *     possible object is
     *     {@link MaterializedSubscription }
     *     
     */
    public MaterializedSubscription getMaterializedSubscription() {
        return materializedSubscription;
    }

    /**
     * Sets the value of the materializedSubscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaterializedSubscription }
     *     
     */
    public void setMaterializedSubscription(MaterializedSubscription value) {
        this.materializedSubscription = value;
    }

}