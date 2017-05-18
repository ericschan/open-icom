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
 * <p>Java class for objectChangeEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="objectChangeEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}changeEvent">
 *       &lt;sequence>
 *         &lt;element name="collabId" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="snapshotId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "objectChangeEvent", propOrder = {
    "collabId",
    "snapshotId"
})
@XmlSeeAlso({
    InstantMessageEvent.class,
    PresenceRosterChangeEvent.class,
    PresenceChangeEvent.class
})
public class ObjectChangeEvent
    extends ChangeEvent
{

    protected BeeId collabId;
    protected String snapshotId;

    /**
     * Gets the value of the collabId property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getCollabId() {
        return collabId;
    }

    /**
     * Sets the value of the collabId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setCollabId(BeeId value) {
        this.collabId = value;
    }

    /**
     * Gets the value of the snapshotId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSnapshotId() {
        return snapshotId;
    }

    /**
     * Sets the value of the snapshotId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSnapshotId(String value) {
        this.snapshotId = value;
    }

}
