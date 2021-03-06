//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addressBookElementUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addressBookElementUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="addresses" type="{http://www.oracle.com/beehive}addressesListUpdater" minOccurs="0"/>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attachmentsUpdater" type="{http://www.oracle.com/beehive}attachmentListUpdater" minOccurs="0"/>
 *         &lt;element name="favorite" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="primaryAddress" type="{http://www.oracle.com/beehive}entityAddress" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.oracle.com/beehive}priority" minOccurs="0"/>
 *         &lt;element name="sensitivity" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="speedDial" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addressBookElementUpdater", propOrder = {
    "addresses",
    "alias",
    "attachmentsUpdater",
    "favorite",
    "primaryAddress",
    "priority",
    "sensitivity",
    "speedDial"
})
@XmlSeeAlso({
    ResourceContactUpdater.class,
    PersonContactUpdater.class,
    GroupContactUpdater.class
})
public class AddressBookElementUpdater
    extends ArtifactUpdater
{

    protected AddressesListUpdater addresses;
    protected String alias;
    protected AttachmentListUpdater attachmentsUpdater;
    protected Object favorite;
    protected EntityAddress primaryAddress;
    protected Priority priority;
    protected BeeId sensitivity;
    protected Integer speedDial;

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link AddressesListUpdater }
     *     
     */
    public AddressesListUpdater getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressesListUpdater }
     *     
     */
    public void setAddresses(AddressesListUpdater value) {
        this.addresses = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the attachmentsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentListUpdater }
     *     
     */
    public AttachmentListUpdater getAttachmentsUpdater() {
        return attachmentsUpdater;
    }

    /**
     * Sets the value of the attachmentsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentListUpdater }
     *     
     */
    public void setAttachmentsUpdater(AttachmentListUpdater value) {
        this.attachmentsUpdater = value;
    }

    /**
     * Gets the value of the favorite property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getFavorite() {
        return favorite;
    }

    /**
     * Sets the value of the favorite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setFavorite(Object value) {
        this.favorite = value;
    }

    /**
     * Gets the value of the primaryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link EntityAddress }
     *     
     */
    public EntityAddress getPrimaryAddress() {
        return primaryAddress;
    }

    /**
     * Sets the value of the primaryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityAddress }
     *     
     */
    public void setPrimaryAddress(EntityAddress value) {
        this.primaryAddress = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
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

    /**
     * Gets the value of the speedDial property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSpeedDial() {
        return speedDial;
    }

    /**
     * Sets the value of the speedDial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSpeedDial(Integer value) {
        this.speedDial = value;
    }

}
