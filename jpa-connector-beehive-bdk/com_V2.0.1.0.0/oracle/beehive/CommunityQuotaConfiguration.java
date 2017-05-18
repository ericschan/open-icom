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
 * <p>Java class for communityQuotaConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="communityQuotaConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}quotaConfiguration">
 *       &lt;sequence>
 *         &lt;element name="defaultPersonalWorkspaceQuotaConfiguration" type="{http://www.oracle.com/beehive}workspaceQuotaConfiguration" minOccurs="0"/>
 *         &lt;element name="defaultStorageSpaceHardQuotaForSubOrganizations" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="defaultTeamWorkspaceQuotaConfiguration" type="{http://www.oracle.com/beehive}workspaceQuotaConfiguration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "communityQuotaConfiguration", propOrder = {
    "defaultPersonalWorkspaceQuotaConfiguration",
    "defaultStorageSpaceHardQuotaForSubOrganizations",
    "defaultTeamWorkspaceQuotaConfiguration"
})
public class CommunityQuotaConfiguration
    extends QuotaConfiguration
{

    protected WorkspaceQuotaConfiguration defaultPersonalWorkspaceQuotaConfiguration;
    protected long defaultStorageSpaceHardQuotaForSubOrganizations;
    protected WorkspaceQuotaConfiguration defaultTeamWorkspaceQuotaConfiguration;

    /**
     * Gets the value of the defaultPersonalWorkspaceQuotaConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link WorkspaceQuotaConfiguration }
     *     
     */
    public WorkspaceQuotaConfiguration getDefaultPersonalWorkspaceQuotaConfiguration() {
        return defaultPersonalWorkspaceQuotaConfiguration;
    }

    /**
     * Sets the value of the defaultPersonalWorkspaceQuotaConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkspaceQuotaConfiguration }
     *     
     */
    public void setDefaultPersonalWorkspaceQuotaConfiguration(WorkspaceQuotaConfiguration value) {
        this.defaultPersonalWorkspaceQuotaConfiguration = value;
    }

    /**
     * Gets the value of the defaultStorageSpaceHardQuotaForSubOrganizations property.
     * 
     */
    public long getDefaultStorageSpaceHardQuotaForSubOrganizations() {
        return defaultStorageSpaceHardQuotaForSubOrganizations;
    }

    /**
     * Sets the value of the defaultStorageSpaceHardQuotaForSubOrganizations property.
     * 
     */
    public void setDefaultStorageSpaceHardQuotaForSubOrganizations(long value) {
        this.defaultStorageSpaceHardQuotaForSubOrganizations = value;
    }

    /**
     * Gets the value of the defaultTeamWorkspaceQuotaConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link WorkspaceQuotaConfiguration }
     *     
     */
    public WorkspaceQuotaConfiguration getDefaultTeamWorkspaceQuotaConfiguration() {
        return defaultTeamWorkspaceQuotaConfiguration;
    }

    /**
     * Sets the value of the defaultTeamWorkspaceQuotaConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkspaceQuotaConfiguration }
     *     
     */
    public void setDefaultTeamWorkspaceQuotaConfiguration(WorkspaceQuotaConfiguration value) {
        this.defaultTeamWorkspaceQuotaConfiguration = value;
    }

}
