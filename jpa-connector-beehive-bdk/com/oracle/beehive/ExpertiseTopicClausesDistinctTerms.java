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
 * <p>Java class for expertiseTopicClausesDistinctTerms.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="expertiseTopicClausesDistinctTerms">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALLOW_OVERLAP"/>
 *     &lt;enumeration value="FAVOR_DISTINCT"/>
 *     &lt;enumeration value="ONLY_DISTINCT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "expertiseTopicClausesDistinctTerms")
@XmlEnum
public enum ExpertiseTopicClausesDistinctTerms {

    ALLOW_OVERLAP,
    FAVOR_DISTINCT,
    ONLY_DISTINCT;

    public String value() {
        return name();
    }

    public static ExpertiseTopicClausesDistinctTerms fromValue(String v) {
        return valueOf(v);
    }

}
