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
 * <p>Java class for contactReachabilityStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="contactReachabilityStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NOT_REACHABLE"/>
 *     &lt;enumeration value="REACHABLE"/>
 *     &lt;enumeration value="CHATTY"/>
 *     &lt;enumeration value="AWAY"/>
 *     &lt;enumeration value="EXTENDED_AWAY"/>
 *     &lt;enumeration value="DO_NOT_DISTURB"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "contactReachabilityStatus")
@XmlEnum
public enum ContactReachabilityStatus {

    NOT_REACHABLE,
    REACHABLE,
    CHATTY,
    AWAY,
    EXTENDED_AWAY,
    DO_NOT_DISTURB;

    public String value() {
        return name();
    }

    public static ContactReachabilityStatus fromValue(String v) {
        return valueOf(v);
    }

}
