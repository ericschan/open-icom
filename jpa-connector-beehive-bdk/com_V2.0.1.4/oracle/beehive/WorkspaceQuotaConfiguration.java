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
 * <p>Java class for workspaceQuotaConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workspaceQuotaConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}quotaConfiguration">
 *       &lt;sequence>
 *         &lt;element name="storageSpaceSoftQuota" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workspaceQuotaConfiguration", propOrder = {
    "storageSpaceSoftQuota"
})
public class WorkspaceQuotaConfiguration
    extends QuotaConfiguration
{

    protected long storageSpaceSoftQuota;

    /**
     * Gets the value of the storageSpaceSoftQuota property.
     * 
     */
    public long getStorageSpaceSoftQuota() {
        return storageSpaceSoftQuota;
    }

    /**
     * Sets the value of the storageSpaceSoftQuota property.
     * 
     */
    public void setStorageSpaceSoftQuota(long value) {
        this.storageSpaceSoftQuota = value;
    }

}
