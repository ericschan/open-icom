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
 * <p>Java class for dateRelation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dateRelation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BEFORE"/>
 *     &lt;enumeration value="ON_OR_BEFORE"/>
 *     &lt;enumeration value="AFTER"/>
 *     &lt;enumeration value="ON_OR_AFTER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dateRelation")
@XmlEnum
public enum DateRelation {

    BEFORE,
    ON_OR_BEFORE,
    AFTER,
    ON_OR_AFTER;

    public String value() {
        return name();
    }

    public static DateRelation fromValue(String v) {
        return valueOf(v);
    }

}
