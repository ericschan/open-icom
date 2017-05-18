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
 * <p>Java class for journalEntryType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="journalEntryType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CONVERSATION"/>
 *     &lt;enumeration value="DOCUMENT"/>
 *     &lt;enumeration value="EMAILMESSAGE"/>
 *     &lt;enumeration value="FAX"/>
 *     &lt;enumeration value="LETTER"/>
 *     &lt;enumeration value="MEETING"/>
 *     &lt;enumeration value="MEETINGCANCELLATION"/>
 *     &lt;enumeration value="MEETINGREQUEST"/>
 *     &lt;enumeration value="MEETINGRESPONSE"/>
 *     &lt;enumeration value="SPREADSHEET"/>
 *     &lt;enumeration value="PRESENTATION"/>
 *     &lt;enumeration value="WORDPROCESSING"/>
 *     &lt;enumeration value="NOTE"/>
 *     &lt;enumeration value="PHONECALL"/>
 *     &lt;enumeration value="REMOTESESSION"/>
 *     &lt;enumeration value="TASK"/>
 *     &lt;enumeration value="TASKREQUEST"/>
 *     &lt;enumeration value="TASKRESPONSE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "journalEntryType")
@XmlEnum
public enum JournalEntryType {

    CONVERSATION,
    DOCUMENT,
    EMAILMESSAGE,
    FAX,
    LETTER,
    MEETING,
    MEETINGCANCELLATION,
    MEETINGREQUEST,
    MEETINGRESPONSE,
    SPREADSHEET,
    PRESENTATION,
    WORDPROCESSING,
    NOTE,
    PHONECALL,
    REMOTESESSION,
    TASK,
    TASKREQUEST,
    TASKRESPONSE;

    public String value() {
        return name();
    }

    public static JournalEntryType fromValue(String v) {
        return valueOf(v);
    }

}
