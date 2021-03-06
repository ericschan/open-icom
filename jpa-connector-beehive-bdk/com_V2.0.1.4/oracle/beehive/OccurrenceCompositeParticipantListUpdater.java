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
 * <p>Java class for occurrenceCompositeParticipantListUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrenceCompositeParticipantListUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="add" type="{http://www.oracle.com/beehive}occurrenceCompositeParticipantUpdater" minOccurs="0"/>
 *         &lt;element name="compositeParticipantsToRemove" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="remove" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occurrenceCompositeParticipantListUpdater", propOrder = {
    "add",
    "compositeParticipantsToRemoves",
    "remove"
})
public class OccurrenceCompositeParticipantListUpdater
    extends IdentifiableUpdater
{

    protected OccurrenceCompositeParticipantUpdater add;
    @XmlElement(name = "compositeParticipantsToRemove")
    protected List<BeeId> compositeParticipantsToRemoves;
    protected BeeId remove;

    /**
     * Gets the value of the add property.
     * 
     * @return
     *     possible object is
     *     {@link OccurrenceCompositeParticipantUpdater }
     *     
     */
    public OccurrenceCompositeParticipantUpdater getAdd() {
        return add;
    }

    /**
     * Sets the value of the add property.
     * 
     * @param value
     *     allowed object is
     *     {@link OccurrenceCompositeParticipantUpdater }
     *     
     */
    public void setAdd(OccurrenceCompositeParticipantUpdater value) {
        this.add = value;
    }

    /**
     * Gets the value of the compositeParticipantsToRemoves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compositeParticipantsToRemoves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompositeParticipantsToRemoves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getCompositeParticipantsToRemoves() {
        if (compositeParticipantsToRemoves == null) {
            compositeParticipantsToRemoves = new ArrayList<BeeId>();
        }
        return this.compositeParticipantsToRemoves;
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

}
