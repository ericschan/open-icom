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
 * <p>Java class for workspaceParticipantListUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workspaceParticipantListUpdater">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="add" type="{http://www.oracle.com/beehive}workspaceParticipantUpdater" minOccurs="0"/>
 *         &lt;element name="remove" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="update" type="{http://www.oracle.com/beehive}workspaceParticipantUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workspaceParticipantListUpdater", propOrder = {
    "add",
    "remove",
    "update"
})
public class WorkspaceParticipantListUpdater {

    protected WorkspaceParticipantUpdater add;
    protected BeeId remove;
    protected WorkspaceParticipantUpdater update;

    /**
     * Gets the value of the add property.
     * 
     * @return
     *     possible object is
     *     {@link WorkspaceParticipantUpdater }
     *     
     */
    public WorkspaceParticipantUpdater getAdd() {
        return add;
    }

    /**
     * Sets the value of the add property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkspaceParticipantUpdater }
     *     
     */
    public void setAdd(WorkspaceParticipantUpdater value) {
        this.add = value;
    }

    /**
     * Gets the value of the remove property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getRemove() {
        return remove;
    }

    /**
     * Sets the value of the remove property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setRemove(BeeId value) {
        this.remove = value;
    }

    /**
     * Gets the value of the update property.
     * 
     * @return
     *     possible object is
     *     {@link WorkspaceParticipantUpdater }
     *     
     */
    public WorkspaceParticipantUpdater getUpdate() {
        return update;
    }

    /**
     * Sets the value of the update property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkspaceParticipantUpdater }
     *     
     */
    public void setUpdate(WorkspaceParticipantUpdater value) {
        this.update = value;
    }

}