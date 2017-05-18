//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bondCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bondCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="bondName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bondType" type="{http://www.oracle.com/beehive}bondType" minOccurs="0"/>
 *         &lt;element name="bu" type="{http://www.oracle.com/beehive}bondUpdater" minOccurs="0"/>
 *         &lt;element name="root" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="target" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bondCreator", propOrder = {
    "bondName",
    "bondType",
    "bu",
    "root",
    "target"
})
@XmlRootElement(name = "bondCreator")
public class BondCreator
    extends EntityCreator
{

    protected String bondName;
    protected BondType bondType;
    protected BondUpdater bu;
    protected BeeId root;
    protected BeeId target;

    /**
     * Gets the value of the bondName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBondName() {
        return bondName;
    }

    /**
     * Sets the value of the bondName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBondName(String value) {
        this.bondName = value;
    }

    /**
     * Gets the value of the bondType property.
     * 
     * @return
     *     possible object is
     *     {@link BondType }
     *     
     */
    public BondType getBondType() {
        return bondType;
    }

    /**
     * Sets the value of the bondType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BondType }
     *     
     */
    public void setBondType(BondType value) {
        this.bondType = value;
    }

    /**
     * Gets the value of the bu property.
     * 
     * @return
     *     possible object is
     *     {@link BondUpdater }
     *     
     */
    public BondUpdater getBu() {
        return bu;
    }

    /**
     * Sets the value of the bu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BondUpdater }
     *     
     */
    public void setBu(BondUpdater value) {
        this.bu = value;
    }

    /**
     * Gets the value of the root property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getRoot() {
        return root;
    }

    /**
     * Sets the value of the root property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setRoot(BeeId value) {
        this.root = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setTarget(BeeId value) {
        this.target = value;
    }

}