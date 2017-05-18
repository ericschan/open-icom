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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for delegatedRoleCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="delegatedRoleCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="delegatedPrincipalHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="delegatedScopeHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="privileges" type="{http://www.oracle.com/beehive}privilege" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateMode" type="{http://www.oracle.com/beehive}updateMode" minOccurs="0"/>
 *         &lt;element name="updater" type="{http://www.oracle.com/beehive}delegatedRoleUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delegatedRoleCreator", propOrder = {
    "delegatedPrincipalHandle",
    "delegatedScopeHandle",
    "description",
    "name",
    "privileges",
    "updateMode",
    "updater"
})
public class DelegatedRoleCreator
    extends EntityCreator
{

    protected BeeId delegatedPrincipalHandle;
    protected BeeId delegatedScopeHandle;
    protected String description;
    protected String name;
    @XmlElement(nillable = true)
    protected List<Privilege> privileges;
    protected UpdateMode updateMode;
    protected DelegatedRoleUpdater updater;

    /**
     * Gets the value of the delegatedPrincipalHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getDelegatedPrincipalHandle() {
        return delegatedPrincipalHandle;
    }

    /**
     * Sets the value of the delegatedPrincipalHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setDelegatedPrincipalHandle(BeeId value) {
        this.delegatedPrincipalHandle = value;
    }

    /**
     * Gets the value of the delegatedScopeHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getDelegatedScopeHandle() {
        return delegatedScopeHandle;
    }

    /**
     * Sets the value of the delegatedScopeHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setDelegatedScopeHandle(BeeId value) {
        this.delegatedScopeHandle = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the privileges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privileges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivileges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Privilege }
     * 
     * 
     */
    public List<Privilege> getPrivileges() {
        if (privileges == null) {
            privileges = new ArrayList<Privilege>();
        }
        return this.privileges;
    }

    /**
     * Gets the value of the updateMode property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateMode }
     *     
     */
    public UpdateMode getUpdateMode() {
        return updateMode;
    }

    /**
     * Sets the value of the updateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateMode }
     *     
     */
    public void setUpdateMode(UpdateMode value) {
        this.updateMode = value;
    }

    /**
     * Gets the value of the updater property.
     * 
     * @return
     *     possible object is
     *     {@link DelegatedRoleUpdater }
     *     
     */
    public DelegatedRoleUpdater getUpdater() {
        return updater;
    }

    /**
     * Sets the value of the updater property.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegatedRoleUpdater }
     *     
     */
    public void setUpdater(DelegatedRoleUpdater value) {
        this.updater = value;
    }

}
