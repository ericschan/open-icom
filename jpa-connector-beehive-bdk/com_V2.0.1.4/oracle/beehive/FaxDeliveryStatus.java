//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for faxDeliveryStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="faxDeliveryStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IN_PROCESS"/>
 *     &lt;enumeration value="TEMP_FAILURE"/>
 *     &lt;enumeration value="PERMANENT_FAILURE"/>
 *     &lt;enumeration value="DELIVERED"/>
 *     &lt;enumeration value="READ"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "faxDeliveryStatus")
@XmlEnum
public enum FaxDeliveryStatus {

    IN_PROCESS,
    TEMP_FAILURE,
    PERMANENT_FAILURE,
    DELIVERED,
    READ;

    public String value() {
        return name();
    }

    public static FaxDeliveryStatus fromValue(String v) {
        return valueOf(v);
    }

}
