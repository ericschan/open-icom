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
 * <p>Java class for connectionRequestResponseState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="connectionRequestResponseState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACCEPTED"/>
 *     &lt;enumeration value="DECLINED"/>
 *     &lt;enumeration value="IGNORED"/>
 *     &lt;enumeration value="PENDING"/>
 *     &lt;enumeration value="NOT_APPLICABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "connectionRequestResponseState")
@XmlEnum
public enum ConnectionRequestResponseState {

    ACCEPTED,
    DECLINED,
    IGNORED,
    PENDING,
    NOT_APPLICABLE;

    public String value() {
        return name();
    }

    public static ConnectionRequestResponseState fromValue(String v) {
        return valueOf(v);
    }

}
