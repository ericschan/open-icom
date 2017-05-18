//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for communityUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="communityUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}scopeUpdater">
 *       &lt;sequence>
 *         &lt;element name="quotaConfiguration" type="{http://www.oracle.com/beehive}communityQuotaConfiguration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "communityUpdater", propOrder = {
    "quotaConfiguration"
})
@XmlSeeAlso({
    OrganizationUpdater.class,
    EnterpriseUpdater.class
})
public abstract class CommunityUpdater
    extends ScopeUpdater
{

    protected CommunityQuotaConfiguration quotaConfiguration;

    /**
     * Gets the value of the quotaConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link CommunityQuotaConfiguration }
     *     
     */
    public CommunityQuotaConfiguration getQuotaConfiguration() {
        return quotaConfiguration;
    }

    /**
     * Sets the value of the quotaConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunityQuotaConfiguration }
     *     
     */
    public void setQuotaConfiguration(CommunityQuotaConfiguration value) {
        this.quotaConfiguration = value;
    }

}