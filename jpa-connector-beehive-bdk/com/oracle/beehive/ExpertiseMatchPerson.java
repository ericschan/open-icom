//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for expertiseMatchPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="expertiseMatchPerson">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}expertisePerson">
 *       &lt;sequence>
 *         &lt;element name="numContacts" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "expertiseMatchPerson", propOrder = {
    "numContacts"
})
public class ExpertiseMatchPerson
    extends ExpertisePerson
{

    protected int numContacts;

    /**
     * Gets the value of the numContacts property.
     * 
     */
    public int getNumContacts() {
        return numContacts;
    }

    /**
     * Sets the value of the numContacts property.
     * 
     */
    public void setNumContacts(int value) {
        this.numContacts = value;
    }

}
