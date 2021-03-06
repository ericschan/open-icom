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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for baseAccessor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseAccessor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}directoryEntry">
 *       &lt;sequence>
 *         &lt;element name="assignedRoles" type="{http://www.oracle.com/beehive}assignedRole" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bonds" type="{http://www.oracle.com/beehive}bond" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="effectiveGroups" type="{http://www.oracle.com/beehive}group" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="groups" type="{http://www.oracle.com/beehive}group" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseAccessor", propOrder = {
    "assignedRoles",
    "bonds",
    "effectiveGroups",
    "groups"
})
@XmlSeeAlso({
    Principal.class,
    Accessor.class
})
public abstract class BaseAccessor
    extends DirectoryEntry
{

    @XmlElement(nillable = true)
    protected List<AssignedRole> assignedRoles;
    @XmlElement(nillable = true)
    protected List<Bond> bonds;
    @XmlElement(nillable = true)
    protected List<Group> effectiveGroups;
    @XmlElement(nillable = true)
    protected List<Group> groups;

    /**
     * Gets the value of the assignedRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assignedRoles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssignedRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssignedRole }
     * 
     * 
     */
    public List<AssignedRole> getAssignedRoles() {
        if (assignedRoles == null) {
            assignedRoles = new ArrayList<AssignedRole>();
        }
        return this.assignedRoles;
    }

    /**
     * Gets the value of the bonds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bonds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBonds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bond }
     * 
     * 
     */
    public List<Bond> getBonds() {
        if (bonds == null) {
            bonds = new ArrayList<Bond>();
        }
        return this.bonds;
    }

    /**
     * Gets the value of the effectiveGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the effectiveGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEffectiveGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Group }
     * 
     * 
     */
    public List<Group> getEffectiveGroups() {
        if (effectiveGroups == null) {
            effectiveGroups = new ArrayList<Group>();
        }
        return this.effectiveGroups;
    }

    /**
     * Gets the value of the groups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Group }
     * 
     * 
     */
    public List<Group> getGroups() {
        if (groups == null) {
            groups = new ArrayList<Group>();
        }
        return this.groups;
    }

}
