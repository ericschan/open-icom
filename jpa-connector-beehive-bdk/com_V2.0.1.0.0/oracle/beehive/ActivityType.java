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
 * <p>Java class for activityType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="activityType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ON_THE_PHONE"/>
 *     &lt;enumeration value="CONFERENCE"/>
 *     &lt;enumeration value="MEETING"/>
 *     &lt;enumeration value="TRAVEL"/>
 *     &lt;enumeration value="STEERING"/>
 *     &lt;enumeration value="MEAL"/>
 *     &lt;enumeration value="OUT_OF_OFFICE"/>
 *     &lt;enumeration value="HOLIDAY"/>
 *     &lt;enumeration value="VACATION"/>
 *     &lt;enumeration value="OUT_OF_CONTACT"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "activityType")
@XmlEnum
public enum ActivityType {

    ON_THE_PHONE,
    CONFERENCE,
    MEETING,
    TRAVEL,
    STEERING,
    MEAL,
    OUT_OF_OFFICE,
    HOLIDAY,
    VACATION,
    OUT_OF_CONTACT,
    OTHER;

    public String value() {
        return name();
    }

    public static ActivityType fromValue(String v) {
        return valueOf(v);
    }

}