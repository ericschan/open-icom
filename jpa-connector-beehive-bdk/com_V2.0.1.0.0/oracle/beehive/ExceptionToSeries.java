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
 * <p>Java class for exceptionToSeries.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="exceptionToSeries">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NON_EXCEPTION"/>
 *     &lt;enumeration value="INDIRECTLY_MODIFIED_EXCEPTION"/>
 *     &lt;enumeration value="DIRECTLY_MODIFIED_EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "exceptionToSeries")
@XmlEnum
public enum ExceptionToSeries {

    NON_EXCEPTION,
    INDIRECTLY_MODIFIED_EXCEPTION,
    DIRECTLY_MODIFIED_EXCEPTION;

    public String value() {
        return name();
    }

    public static ExceptionToSeries fromValue(String v) {
        return valueOf(v);
    }

}