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
 * <p>Java class for expertiseCompanyMatch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseCompanyMatch">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}expertiseCompany">
 *       &lt;sequence>
 *         &lt;element name="peopleKnownAtCompany" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="usersWhoKnowThem" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseCompanyMatch", propOrder = {
    "peopleKnownAtCompany",
    "usersWhoKnowThem"
})
public class ExpertiseCompanyMatch
    extends ExpertiseCompany
{

    protected int peopleKnownAtCompany;
    protected int usersWhoKnowThem;

    /**
     * Gets the value of the peopleKnownAtCompany property.
     * 
     */
    public int getPeopleKnownAtCompany() {
        return peopleKnownAtCompany;
    }

    /**
     * Sets the value of the peopleKnownAtCompany property.
     * 
     */
    public void setPeopleKnownAtCompany(int value) {
        this.peopleKnownAtCompany = value;
    }

    /**
     * Gets the value of the usersWhoKnowThem property.
     * 
     */
    public int getUsersWhoKnowThem() {
        return usersWhoKnowThem;
    }

    /**
     * Sets the value of the usersWhoKnowThem property.
     * 
     */
    public void setUsersWhoKnowThem(int value) {
        this.usersWhoKnowThem = value;
    }

}
