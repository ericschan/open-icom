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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for principal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="principal">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}baseAccessor">
 *       &lt;sequence>
 *         &lt;element name="activatingActorSufficient" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="principalType" type="{http://www.oracle.com/beehive}principalType" minOccurs="0"/>
 *         &lt;element name="sufficientPrincipals" type="{http://www.oracle.com/beehive}principal" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "principal", propOrder = {
    "activatingActorSufficient",
    "primary",
    "principalType",
    "sufficientPrincipals"
})
@XmlSeeAlso({
    DelegatedPrincipal.class
})
public class Principal
    extends BaseAccessor
{

    protected boolean activatingActorSufficient;
    protected boolean primary;
    protected PrincipalType principalType;
    protected List<Principal> sufficientPrincipals;

    /**
     * Gets the value of the activatingActorSufficient property.
     * 
     */
    public boolean isActivatingActorSufficient() {
        return activatingActorSufficient;
    }

    /**
     * Sets the value of the activatingActorSufficient property.
     * 
     */
    public void setActivatingActorSufficient(boolean value) {
        this.activatingActorSufficient = value;
    }

    /**
     * Gets the value of the primary property.
     * 
     */
    public boolean isPrimary() {
        return primary;
    }

    /**
     * Sets the value of the primary property.
     * 
     */
    public void setPrimary(boolean value) {
        this.primary = value;
    }

    /**
     * Gets the value of the principalType property.
     * 
     * @return
     *     possible object is
     *     {@link PrincipalType }
     *     
     */
    public PrincipalType getPrincipalType() {
        return principalType;
    }

    /**
     * Sets the value of the principalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrincipalType }
     *     
     */
    public void setPrincipalType(PrincipalType value) {
        this.principalType = value;
    }

    /**
     * Gets the value of the sufficientPrincipals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sufficientPrincipals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSufficientPrincipals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Principal }
     * 
     * 
     */
    public List<Principal> getSufficientPrincipals() {
        if (sufficientPrincipals == null) {
            sufficientPrincipals = new ArrayList<Principal>();
        }
        return this.sufficientPrincipals;
    }

}
