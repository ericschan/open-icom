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
 * <p>Java class for invitationInvitationExceptionToSeriesPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="invitationInvitationExceptionToSeriesPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}invitationPredicate">
 *       &lt;sequence>
 *         &lt;element name="invitationExceptionToSeries" type="{http://www.oracle.com/beehive}exceptionToSeries" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invitationInvitationExceptionToSeriesPredicate", propOrder = {
    "invitationExceptionToSeries"
})
public class InvitationInvitationExceptionToSeriesPredicate
    extends InvitationPredicate
{

    protected ExceptionToSeries invitationExceptionToSeries;

    /**
     * Gets the value of the invitationExceptionToSeries property.
     * 
     * @return
     *     possible object is
     *     {@link ExceptionToSeries }
     *     
     */
    public ExceptionToSeries getInvitationExceptionToSeries() {
        return invitationExceptionToSeries;
    }

    /**
     * Sets the value of the invitationExceptionToSeries property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExceptionToSeries }
     *     
     */
    public void setInvitationExceptionToSeries(ExceptionToSeries value) {
        this.invitationExceptionToSeries = value;
    }

}
