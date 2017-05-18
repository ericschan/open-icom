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
 * <p>Java class for auditStringPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auditStringPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}predicate">
 *       &lt;sequence>
 *         &lt;element name="stringMatch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auditStringPredicate", propOrder = {
    "stringMatch"
})
@XmlSeeAlso({
    AuditNamePredicate.class,
    AuditContentParentNamePredicate.class,
    AuditLocalePredicate.class,
    AuditActorNamePredicate.class,
    AuditSessionIdPredicate.class,
    AuditTagPredicate.class,
    AuditServiceNamePredicate.class,
    AuditLogonProtocolPredicate.class,
    AuditLogonTypePredicate.class,
    AuditMessageTextPredicate.class,
    AuditClientOsPredicate.class,
    AuditEffectiveActorNamePredicate.class,
    AuditEventNamePredicate.class,
    AuditClientAddressPredicate.class,
    AuditContentNamePredicate.class,
    AuditPrincipalTypePredicate.class,
    AuditEventTypePredicate.class,
    AuditPrincipalNamePredicate.class,
    AuditClientNamePredicate.class,
    AuditEventStatusPredicate.class,
    AuditClientTypePredicate.class
})
public class AuditStringPredicate
    extends Predicate
{

    protected String stringMatch;

    /**
     * Gets the value of the stringMatch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringMatch() {
        return stringMatch;
    }

    /**
     * Sets the value of the stringMatch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringMatch(String value) {
        this.stringMatch = value;
    }

}
