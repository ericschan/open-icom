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
 * <p>Java class for labelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="labelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="USER_DEFINED_LABEL"/>
 *     &lt;enumeration value="SYSTEM_LABEL"/>
 *     &lt;enumeration value="SHARED_LABEL"/>
 *     &lt;enumeration value="PRE_DEFINED_LABEL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "labelType")
@XmlEnum
public enum LabelType {

    USER_DEFINED_LABEL,
    SYSTEM_LABEL,
    SHARED_LABEL,
    PRE_DEFINED_LABEL;

    public String value() {
        return name();
    }

    public static LabelType fromValue(String v) {
        return valueOf(v);
    }

}
