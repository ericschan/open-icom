//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operationStatusCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="operationStatusCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EXECUTING"/>
 *     &lt;enumeration value="COMPLETED"/>
 *     &lt;enumeration value="FAILED"/>
 *     &lt;enumeration value="TIMED_OUT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "operationStatusCode")
@XmlEnum
public enum OperationStatusCode {

    EXECUTING,
    COMPLETED,
    FAILED,
    TIMED_OUT;

    public String value() {
        return name();
    }

    public static OperationStatusCode fromValue(String v) {
        return valueOf(v);
    }

}
