//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for presenceChangeEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="presenceChangeEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}objectChangeEvent">
 *       &lt;sequence>
 *         &lt;element name="changedPresence" type="{http://www.oracle.com/beehive}presence" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "presenceChangeEvent", propOrder = {
    "changedPresence"
})
public class PresenceChangeEvent
    extends ObjectChangeEvent
{

    protected Presence changedPresence;

    /**
     * Gets the value of the changedPresence property.
     * 
     * @return
     *     possible object is
     *     {@link Presence }
     *     
     */
    public Presence getChangedPresence() {
        return changedPresence;
    }

    /**
     * Sets the value of the changedPresence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Presence }
     *     
     */
    public void setChangedPresence(Presence value) {
        this.changedPresence = value;
    }

}
