//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for quotaConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="quotaConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="storageSpaceHardQuota" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quotaConfiguration", propOrder = {
    "storageSpaceHardQuota"
})
@XmlSeeAlso({
    WorkspaceQuotaConfiguration.class,
    CommunityQuotaConfiguration.class
})
public abstract class QuotaConfiguration {

    protected long storageSpaceHardQuota;

    /**
     * Gets the value of the storageSpaceHardQuota property.
     * 
     */
    public long getStorageSpaceHardQuota() {
        return storageSpaceHardQuota;
    }

    /**
     * Sets the value of the storageSpaceHardQuota property.
     * 
     */
    public void setStorageSpaceHardQuota(long value) {
        this.storageSpaceHardQuota = value;
    }

}
