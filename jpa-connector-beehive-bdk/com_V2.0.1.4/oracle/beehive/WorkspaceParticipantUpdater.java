//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for workspaceParticipantUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workspaceParticipantUpdater">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operation" type="{http://www.oracle.com/beehive}workspaceParticipantUpdaterOperation" minOccurs="0"/>
 *         &lt;element name="participant" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="propertiesUpdater" type="{http://www.oracle.com/beehive}collabPropertiesUpdater" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workspaceParticipantUpdater", propOrder = {
    "operation",
    "participant",
    "propertiesUpdater",
    "roles"
})
public class WorkspaceParticipantUpdater {

    protected WorkspaceParticipantUpdaterOperation operation;
    protected BeeId participant;
    protected CollabPropertiesUpdater propertiesUpdater;
    protected List<BeeId> roles;

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link WorkspaceParticipantUpdaterOperation }
     *     
     */
    public WorkspaceParticipantUpdaterOperation getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkspaceParticipantUpdaterOperation }
     *     
     */
    public void setOperation(WorkspaceParticipantUpdaterOperation value) {
        this.operation = value;
    }

    /**
     * Gets the value of the participant property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getParticipant() {
        return participant;
    }

    /**
     * Sets the value of the participant property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setParticipant(BeeId value) {
        this.participant = value;
    }

    /**
     * Gets the value of the propertiesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public CollabPropertiesUpdater getPropertiesUpdater() {
        return propertiesUpdater;
    }

    /**
     * Sets the value of the propertiesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabPropertiesUpdater }
     *     
     */
    public void setPropertiesUpdater(CollabPropertiesUpdater value) {
        this.propertiesUpdater = value;
    }

    /**
     * Gets the value of the roles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getRoles() {
        if (roles == null) {
            roles = new ArrayList<BeeId>();
        }
        return this.roles;
    }

}
