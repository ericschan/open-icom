//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
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
 * <p>Java class for roleDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roleDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="accessTypes" type="{http://www.oracle.com/beehive}accessTypes" minOccurs="0"/>
 *         &lt;element name="alwaysEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="privileges" type="{http://www.oracle.com/beehive}privilege" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roleDefinition", propOrder = {
    "accessTypes",
    "alwaysEnabled",
    "description",
    "privileges"
})
@XmlRootElement(name = "roleDefinition")
public class RoleDefinition
    extends Entity
{

    protected AccessTypes accessTypes;
    protected boolean alwaysEnabled;
    protected String description;
    @XmlElement(nillable = true)
    protected List<Privilege> privileges;

    /**
     * Gets the value of the accessTypes property.
     * 
     * @return
     *     possible object is
     *     {@link AccessTypes }
     *     
     */
    public AccessTypes getAccessTypes() {
        return accessTypes;
    }

    /**
     * Sets the value of the accessTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessTypes }
     *     
     */
    public void setAccessTypes(AccessTypes value) {
        this.accessTypes = value;
    }

    /**
     * Gets the value of the alwaysEnabled property.
     * 
     */
    public boolean isAlwaysEnabled() {
        return alwaysEnabled;
    }

    /**
     * Sets the value of the alwaysEnabled property.
     * 
     */
    public void setAlwaysEnabled(boolean value) {
        this.alwaysEnabled = value;
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
     * Gets the value of the privileges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privileges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivileges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Privilege }
     * 
     * 
     */
    public List<Privilege> getPrivileges() {
        if (privileges == null) {
            privileges = new ArrayList<Privilege>();
        }
        return this.privileges;
    }

}
