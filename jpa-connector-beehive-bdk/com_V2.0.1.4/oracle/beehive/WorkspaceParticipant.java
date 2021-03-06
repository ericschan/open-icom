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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for workspaceParticipant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workspaceParticipant">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}baseParticipant">
 *       &lt;sequence>
 *         &lt;element name="properties" type="{http://www.oracle.com/beehive}collabProperties" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://www.oracle.com/beehive}assignedRole" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workspaceParticipant", propOrder = {
    "properties",
    "roles"
})
@XmlSeeAlso({
    UserWorkspaceParticipant.class,
    GroupWorkspaceParticipant.class
})
public abstract class WorkspaceParticipant
    extends BaseParticipant
{

    protected CollabProperties properties;
    protected List<AssignedRole> roles;

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link CollabProperties }
     *     
     */
    public CollabProperties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollabProperties }
     *     
     */
    public void setProperties(CollabProperties value) {
        this.properties = value;
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
     * {@link AssignedRole }
     * 
     * 
     */
    public List<AssignedRole> getRoles() {
        if (roles == null) {
            roles = new ArrayList<AssignedRole>();
        }
        return this.roles;
    }

}
