//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for todoCompositeParticipantUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="todoCompositeParticipantUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}baseParticipantUpdater">
 *       &lt;sequence>
 *         &lt;element name="role" type="{http://www.oracle.com/beehive}participantRole" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "todoCompositeParticipantUpdater", propOrder = {
    "role"
})
@XmlSeeAlso({
    TodoWorkspaceParticipantUpdater.class
})
public class TodoCompositeParticipantUpdater
    extends BaseParticipantUpdater
{

    protected ParticipantRole role;

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantRole }
     *     
     */
    public ParticipantRole getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantRole }
     *     
     */
    public void setRole(ParticipantRole value) {
        this.role = value;
    }

}
