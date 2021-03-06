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
 * <p>Java class for conferenceVariantType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="conferenceVariantType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="RICHTEXT"/>
 *     &lt;enumeration value="INTEGER"/>
 *     &lt;enumeration value="BYTE"/>
 *     &lt;enumeration value="LONG"/>
 *     &lt;enumeration value="BOOLEAN"/>
 *     &lt;enumeration value="TIMESTAMP"/>
 *     &lt;enumeration value="URI"/>
 *     &lt;enumeration value="TIMEOFFSET"/>
 *     &lt;enumeration value="FLOAT"/>
 *     &lt;enumeration value="DOUBLE"/>
 *     &lt;enumeration value="DATE"/>
 *     &lt;enumeration value="PERMISSION"/>
 *     &lt;enumeration value="PARTICIPANT"/>
 *     &lt;enumeration value="STRING_ARRAY"/>
 *     &lt;enumeration value="INTEGER_ARRAY"/>
 *     &lt;enumeration value="BYTE_ARRAY"/>
 *     &lt;enumeration value="LONG_ARRAY"/>
 *     &lt;enumeration value="BOOLEAN_ARRAY"/>
 *     &lt;enumeration value="TIMESTAMP_ARRAY"/>
 *     &lt;enumeration value="URI_ARRAY"/>
 *     &lt;enumeration value="TIMEOFFSET_ARRAY"/>
 *     &lt;enumeration value="FLOAT_ARRAY"/>
 *     &lt;enumeration value="DOUBLE_ARRAY"/>
 *     &lt;enumeration value="DATE_ARRAY"/>
 *     &lt;enumeration value="PERMISSION_ARRAY"/>
 *     &lt;enumeration value="PARTICIPANT_ARRAY"/>
 *     &lt;enumeration value="OBJECT"/>
 *     &lt;enumeration value="OBJECT_ARRAY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "conferenceVariantType")
@XmlEnum
public enum ConferenceVariantType {

    STRING,
    RICHTEXT,
    INTEGER,
    BYTE,
    LONG,
    BOOLEAN,
    TIMESTAMP,
    URI,
    TIMEOFFSET,
    FLOAT,
    DOUBLE,
    DATE,
    PERMISSION,
    PARTICIPANT,
    STRING_ARRAY,
    INTEGER_ARRAY,
    BYTE_ARRAY,
    LONG_ARRAY,
    BOOLEAN_ARRAY,
    TIMESTAMP_ARRAY,
    URI_ARRAY,
    TIMEOFFSET_ARRAY,
    FLOAT_ARRAY,
    DOUBLE_ARRAY,
    DATE_ARRAY,
    PERMISSION_ARRAY,
    PARTICIPANT_ARRAY,
    OBJECT,
    OBJECT_ARRAY;

    public String value() {
        return name();
    }

    public static ConferenceVariantType fromValue(String v) {
        return valueOf(v);
    }

}
