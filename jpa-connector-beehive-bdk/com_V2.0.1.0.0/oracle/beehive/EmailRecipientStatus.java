//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for emailRecipientStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="emailRecipientStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IN_PROCESS"/>
 *     &lt;enumeration value="DELAYED"/>
 *     &lt;enumeration value="UNDELIVERABLE"/>
 *     &lt;enumeration value="DELIVERED"/>
 *     &lt;enumeration value="DISPLAYED"/>
 *     &lt;enumeration value="DELETED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "emailRecipientStatus")
@XmlEnum
public enum EmailRecipientStatus {

    IN_PROCESS,
    DELAYED,
    UNDELIVERABLE,
    DELIVERED,
    DISPLAYED,
    DELETED;

    public String value() {
        return name();
    }

    public static EmailRecipientStatus fromValue(String v) {
        return valueOf(v);
    }

}
