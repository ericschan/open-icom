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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for bondUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bondUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityUpdater">
 *       &lt;sequence>
 *         &lt;element name="changeStatus" type="{http://www.oracle.com/beehive}changeStatus" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propertiesUpdater" type="{http://www.oracle.com/beehive}collabPropertiesUpdater" minOccurs="0"/>
 *         &lt;element name="removeBondEntityRelations" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="userCreatedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bondUpdater", propOrder = {
    "changeStatus",
    "description",
    "propertiesUpdater",
    "removeBondEntityRelations",
    "userCreatedOn"
})
@XmlRootElement(name = "bondUpdater")
public class BondUpdater
    extends EntityUpdater
{

    protected ChangeStatus changeStatus;
    protected String description;
    protected CollabPropertiesUpdater propertiesUpdater;
    protected List<BeeId> removeBondEntityRelations;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar userCreatedOn;

    /**
     * Gets the value of the changeStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeStatus }
     *     
     */
    public ChangeStatus getChangeStatus() {
        return changeStatus;
    }

    /**
     * Sets the value of the changeStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeStatus }
     *     
     */
    public void setChangeStatus(ChangeStatus value) {
        this.changeStatus = value;
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
     * Gets the value of the removeBondEntityRelations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removeBondEntityRelations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoveBondEntityRelations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getRemoveBondEntityRelations() {
        if (removeBondEntityRelations == null) {
            removeBondEntityRelations = new ArrayList<BeeId>();
        }
        return this.removeBondEntityRelations;
    }

    /**
     * Gets the value of the userCreatedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUserCreatedOn() {
        return userCreatedOn;
    }

    /**
     * Sets the value of the userCreatedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUserCreatedOn(XMLGregorianCalendar value) {
        this.userCreatedOn = value;
    }

}
