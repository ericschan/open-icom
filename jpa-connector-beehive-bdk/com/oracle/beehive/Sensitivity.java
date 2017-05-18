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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sensitivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sensitivity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="delegatable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensitivityACL" type="{http://www.oracle.com/beehive}ace" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sensitivityOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sensitivity", propOrder = {
    "delegatable",
    "description",
    "sensitivityACLs",
    "sensitivityOnly"
})
@XmlRootElement(name = "sensitivity")
public class Sensitivity
    extends Entity
{

    protected boolean delegatable;
    protected String description;
    @XmlElement(name = "sensitivityACL", nillable = true)
    protected List<Ace> sensitivityACLs;
    protected boolean sensitivityOnly;

    /**
     * Gets the value of the delegatable property.
     * 
     */
    public boolean isDelegatable() {
        return delegatable;
    }

    /**
     * Sets the value of the delegatable property.
     * 
     */
    public void setDelegatable(boolean value) {
        this.delegatable = value;
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
     * Gets the value of the sensitivityACLs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sensitivityACLs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSensitivityACLs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ace }
     * 
     * 
     */
    public List<Ace> getSensitivityACLs() {
        if (sensitivityACLs == null) {
            sensitivityACLs = new ArrayList<Ace>();
        }
        return this.sensitivityACLs;
    }

    /**
     * Gets the value of the sensitivityOnly property.
     * 
     */
    public boolean isSensitivityOnly() {
        return sensitivityOnly;
    }

    /**
     * Sets the value of the sensitivityOnly property.
     * 
     */
    public void setSensitivityOnly(boolean value) {
        this.sensitivityOnly = value;
    }

}