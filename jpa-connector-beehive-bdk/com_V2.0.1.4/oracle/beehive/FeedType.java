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
 * <p>Java class for feedType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="feedType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ANNOUNCEMENT"/>
 *     &lt;enumeration value="FOLDER"/>
 *     &lt;enumeration value="FORUM"/>
 *     &lt;enumeration value="LABEL"/>
 *     &lt;enumeration value="RECENT"/>
 *     &lt;enumeration value="TOPIC"/>
 *     &lt;enumeration value="VERSION"/>
 *     &lt;enumeration value="POPULAR"/>
 *     &lt;enumeration value="TAG"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "feedType")
@XmlEnum
public enum FeedType {

    ANNOUNCEMENT,
    FOLDER,
    FORUM,
    LABEL,
    RECENT,
    TOPIC,
    VERSION,
    POPULAR,
    TAG;

    public String value() {
        return name();
    }

    public static FeedType fromValue(String v) {
        return valueOf(v);
    }

}
