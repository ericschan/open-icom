//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for voiceMessageCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voiceMessageCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="fh" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="u" type="{http://www.oracle.com/beehive}voiceMessageUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voiceMessageCreator", propOrder = {
    "fh",
    "u"
})
@XmlRootElement(name = "voiceMessageCreator")
public class VoiceMessageCreator
    extends EntityCreator
{

    protected BeeId fh;
    protected VoiceMessageUpdater u;

    /**
     * Gets the value of the fh property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getFh() {
        return fh;
    }

    /**
     * Sets the value of the fh property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setFh(BeeId value) {
        this.fh = value;
    }

    /**
     * Gets the value of the u property.
     * 
     * @return
     *     possible object is
     *     {@link VoiceMessageUpdater }
     *     
     */
    public VoiceMessageUpdater getU() {
        return u;
    }

    /**
     * Sets the value of the u property.
     * 
     * @param value
     *     allowed object is
     *     {@link VoiceMessageUpdater }
     *     
     */
    public void setU(VoiceMessageUpdater value) {
        this.u = value;
    }

}
