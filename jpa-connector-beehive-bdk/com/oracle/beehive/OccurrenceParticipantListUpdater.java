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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for occurrenceParticipantListUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrenceParticipantListUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="add" type="{http://www.oracle.com/beehive}occurrenceParticipantUpdater" minOccurs="0"/>
 *         &lt;element name="participantsToRemove" type="{http://www.oracle.com/beehive}internalParticipant" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="update" type="{http://www.oracle.com/beehive}occurrenceParticipantUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occurrenceParticipantListUpdater", propOrder = {
    "add",
    "participantsToRemoves",
    "update"
})
public class OccurrenceParticipantListUpdater
    extends IdentifiableUpdater
{

    protected OccurrenceParticipantUpdater add;
    @XmlElement(name = "participantsToRemove")
    protected List<InternalParticipant> participantsToRemoves;
    protected OccurrenceParticipantUpdater update;

    /**
     * Gets the value of the add property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceParticipantUpdater }
     *     
     */
    public OccurrenceParticipantUpdater getAdd() {
        return add;
    }

    /**
     * Sets the value of the add property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceParticipantUpdater }
     *     
     */
    public void setAdd(OccurrenceParticipantUpdater value) {
        this.add = value;
    }

    /**
     * Gets the value of the participantsToRemoves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participantsToRemoves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipantsToRemoves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InternalParticipant }
     * 
     * 
     */
    public List<InternalParticipant> getParticipantsToRemoves() {
        if (participantsToRemoves == null) {
            participantsToRemoves = new ArrayList<InternalParticipant>();
        }
        return this.participantsToRemoves;
    }

    /**
     * Gets the value of the update property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceParticipantUpdater }
     *     
     */
    public OccurrenceParticipantUpdater getUpdate() {
        return update;
    }

    /**
     * Sets the value of the update property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceParticipantUpdater }
     *     
     */
    public void setUpdate(OccurrenceParticipantUpdater value) {
        this.update = value;
    }

}