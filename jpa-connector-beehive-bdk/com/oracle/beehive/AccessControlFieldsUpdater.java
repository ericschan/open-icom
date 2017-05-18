//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accessControlFieldsUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accessControlFieldsUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="creator" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="emptySet" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="localACL" type="{http://www.oracle.com/beehive}ace" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modifiedBy" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="owner" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="ownerAccessTypes" type="{http://www.oracle.com/beehive}accessTypes" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="sensitivity" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accessControlFieldsUpdater", propOrder = {
    "creator",
    "emptySet",
    "localACLs",
    "modifiedBy",
    "owner",
    "ownerAccessTypes",
    "parent",
    "sensitivity"
})
@XmlRootElement(name = "accessControlFieldsUpdater")
public class AccessControlFieldsUpdater
    extends IdentifiableUpdater
{

    protected BeeId creator;
    protected Boolean emptySet;
    @XmlElement(name = "localACL")
    protected List<Ace> localACLs;
    protected BeeId modifiedBy;
    protected BeeId owner;
    protected AccessTypes ownerAccessTypes;
    protected BeeId parent;
    protected BeeId sensitivity;

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setCreator(BeeId value) {
        this.creator = value;
    }

    /**
     * Gets the value of the emptySet property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEmptySet() {
        return emptySet;
    }

    /**
     * Sets the value of the emptySet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmptySet(Boolean value) {
        this.emptySet = value;
    }

    /**
     * Gets the value of the localACLs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the localACLs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocalACLs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ace }
     * 
     * 
     */
    public List<Ace> getLocalACLs() {
        if (localACLs == null) {
            localACLs = new ArrayList<Ace>();
        }
        return this.localACLs;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setModifiedBy(BeeId value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setOwner(BeeId value) {
        this.owner = value;
    }

    /**
     * Gets the value of the ownerAccessTypes property.
     * 
     * @return
     *     possible object is
     *     {@link AccessTypes }
     *     
     */
    public AccessTypes getOwnerAccessTypes() {
        return ownerAccessTypes;
    }

    /**
     * Sets the value of the ownerAccessTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessTypes }
     *     
     */
    public void setOwnerAccessTypes(AccessTypes value) {
        this.ownerAccessTypes = value;
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
     * Gets the value of the sensitivity property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getSensitivity() {
        return sensitivity;
    }

    /**
     * Sets the value of the sensitivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setSensitivity(BeeId value) {
        this.sensitivity = value;
    }

}