//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for relationshipListParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="relationshipListParameters">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}listParameters">
 *       &lt;sequence>
 *         &lt;element name="relationshipSpec" type="{http://www.oracle.com/beehive}relationshipSpec" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "relationshipListParameters", propOrder = {
    "relationshipSpec"
})
@XmlSeeAlso({
    CompanyRelationshipParameters.class
})
public class RelationshipListParameters
    extends ListParameters
{

    protected RelationshipSpec relationshipSpec;

    /**
     * Gets the value of the relationshipSpec property.
     * 
     * @return
     *     possible object is
     *     {@link RelationshipSpec }
     *     
     */
    public RelationshipSpec getRelationshipSpec() {
        return relationshipSpec;
    }

    /**
     * Sets the value of the relationshipSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationshipSpec }
     *     
     */
    public void setRelationshipSpec(RelationshipSpec value) {
        this.relationshipSpec = value;
    }

}
