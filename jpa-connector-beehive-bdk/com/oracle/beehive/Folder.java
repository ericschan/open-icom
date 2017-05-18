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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for folder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="folder">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifact">
 *       &lt;sequence>
 *         &lt;element name="availableMarkers" type="{http://www.oracle.com/beehive}marker" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elementCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="policies" type="{http://www.oracle.com/beehive}policy" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "folder", propOrder = {
    "availableMarkers",
    "description",
    "elementCount",
    "policies"
})
@XmlSeeAlso({
    VirtualFolder.class,
    Archive.class,
    AddressBook.class,
    Calendar.class,
    HeterogeneousFolder.class,
    TaskList.class,
    Topic.class,
    RemoteFolder.class,
    Forum.class,
    SubscriptionList.class,
    ReminderList.class,
    Trash.class
})
public abstract class Folder
    extends Artifact
{

    @XmlElement(nillable = true)
    protected List<Marker> availableMarkers;
    protected String description;
    protected int elementCount;
    @XmlElement(nillable = true)
    protected List<Policy> policies;

    /**
     * Gets the value of the availableMarkers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availableMarkers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailableMarkers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Marker }
     * 
     * 
     */
    public List<Marker> getAvailableMarkers() {
        if (availableMarkers == null) {
            availableMarkers = new ArrayList<Marker>();
        }
        return this.availableMarkers;
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
     * Gets the value of the elementCount property.
     * 
     */
    public int getElementCount() {
        return elementCount;
    }

    /**
     * Sets the value of the elementCount property.
     * 
     */
    public void setElementCount(int value) {
        this.elementCount = value;
    }

    /**
     * Gets the value of the policies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Policy }
     * 
     * 
     */
    public List<Policy> getPolicies() {
        if (policies == null) {
            policies = new ArrayList<Policy>();
        }
        return this.policies;
    }

}