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
 * <p>Java class for groupUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="groupUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}accessorUpdater">
 *       &lt;sequence>
 *         &lt;element name="agents" type="{http://www.oracle.com/beehive}agentListUpdater" minOccurs="0"/>
 *         &lt;element name="datasourceGroupIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datasourceIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupOwner" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="members" type="{http://www.oracle.com/beehive}actorListUpdater" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="subGroups" type="{http://www.oracle.com/beehive}groupListUpdater" minOccurs="0"/>
 *         &lt;element name="teamWorkspace" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groupUpdater", propOrder = {
    "agents",
    "datasourceGroupIdentifier",
    "datasourceIdentifier",
    "description",
    "groupOwner",
    "members",
    "parent",
    "subGroups",
    "teamWorkspace"
})
@XmlSeeAlso({
    DynamicGroupUpdater.class
})
public class GroupUpdater
    extends AccessorUpdater
{

    protected AgentListUpdater agents;
    protected String datasourceGroupIdentifier;
    protected String datasourceIdentifier;
    protected String description;
    protected BeeId groupOwner;
    protected ActorListUpdater members;
    protected BeeId parent;
    protected GroupListUpdater subGroups;
    protected Boolean teamWorkspace;

    /**
     * Gets the value of the agents property.
     * 
     * @return
     *     possible object is
     *     {@link AgentListUpdater }
     *     
     */
    public AgentListUpdater getAgents() {
        return agents;
    }

    /**
     * Sets the value of the agents property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentListUpdater }
     *     
     */
    public void setAgents(AgentListUpdater value) {
        this.agents = value;
    }

    /**
     * Gets the value of the datasourceGroupIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasourceGroupIdentifier() {
        return datasourceGroupIdentifier;
    }

    /**
     * Sets the value of the datasourceGroupIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasourceGroupIdentifier(String value) {
        this.datasourceGroupIdentifier = value;
    }

    /**
     * Gets the value of the datasourceIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasourceIdentifier() {
        return datasourceIdentifier;
    }

    /**
     * Sets the value of the datasourceIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasourceIdentifier(String value) {
        this.datasourceIdentifier = value;
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
     * Gets the value of the groupOwner property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getGroupOwner() {
        return groupOwner;
    }

    /**
     * Sets the value of the groupOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setGroupOwner(BeeId value) {
        this.groupOwner = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * @return
     *     possible object is
     *     {@link ActorListUpdater }
     *     
     */
    public ActorListUpdater getMembers() {
        return members;
    }

    /**
     * Sets the value of the members property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActorListUpdater }
     *     
     */
    public void setMembers(ActorListUpdater value) {
        this.members = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setParent(BeeId value) {
        this.parent = value;
    }

    /**
     * Gets the value of the subGroups property.
     * 
     * @return
     *     possible object is
     *     {@link GroupListUpdater }
     *     
     */
    public GroupListUpdater getSubGroups() {
        return subGroups;
    }

    /**
     * Sets the value of the subGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupListUpdater }
     *     
     */
    public void setSubGroups(GroupListUpdater value) {
        this.subGroups = value;
    }

    /**
     * Gets the value of the teamWorkspace property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTeamWorkspace() {
        return teamWorkspace;
    }

    /**
     * Sets the value of the teamWorkspace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTeamWorkspace(Boolean value) {
        this.teamWorkspace = value;
    }

}
