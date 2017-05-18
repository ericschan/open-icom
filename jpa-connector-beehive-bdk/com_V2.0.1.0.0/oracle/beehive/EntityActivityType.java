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
 * <p>Java class for entityActivityType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="entityActivityType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GENERAL_CREATE"/>
 *     &lt;enumeration value="GENERAL_UPDATE"/>
 *     &lt;enumeration value="GENERAL_MOVE"/>
 *     &lt;enumeration value="GENERAL_DELETE"/>
 *     &lt;enumeration value="GENERAL_UNDELETE"/>
 *     &lt;enumeration value="GENERAL_TAG"/>
 *     &lt;enumeration value="GENERAL_UNTAG"/>
 *     &lt;enumeration value="TOPIC_CREATE_REPLY"/>
 *     &lt;enumeration value="TOPIC_UPDATE_MESSAGE"/>
 *     &lt;enumeration value="COMMENT_CREATE_TOPIC"/>
 *     &lt;enumeration value="COMMENT_CREATE_REPLY"/>
 *     &lt;enumeration value="COMMENT_UPDATE_TOPIC"/>
 *     &lt;enumeration value="COMMENT_UPDATE_MESSAGE"/>
 *     &lt;enumeration value="BONDABLE_BOND_FOLLOWUP"/>
 *     &lt;enumeration value="BONDABLE_UNBOND_FOLLOWUP"/>
 *     &lt;enumeration value="BONDABLE_BOND_RELATED"/>
 *     &lt;enumeration value="BONDABLE_UNBOND_RELATED"/>
 *     &lt;enumeration value="TASK_CANCEL"/>
 *     &lt;enumeration value="TASK_COMPLETE"/>
 *     &lt;enumeration value="ANNOUNCEMENT_ACTIVATE"/>
 *     &lt;enumeration value="LOCKABLE_LOCK"/>
 *     &lt;enumeration value="LOCKABLE_UNLOCK"/>
 *     &lt;enumeration value="VERSIONABLE_CHECKIN"/>
 *     &lt;enumeration value="VERSIONABLE_CHECKOUT"/>
 *     &lt;enumeration value="VERSIONABLE_CHECKOUT_CANCEL"/>
 *     &lt;enumeration value="WORKSPACE_PARTICIPANT_ADD"/>
 *     &lt;enumeration value="WORKSPACE_PARTICIPANT_REMOVE"/>
 *     &lt;enumeration value="WORKSPACE_PARTICIPANT_UPDATE"/>
 *     &lt;enumeration value="MEETING_COMPLETE"/>
 *     &lt;enumeration value="CONNECTION_CREATE"/>
 *     &lt;enumeration value="CONNECTION_DELETE"/>
 *     &lt;enumeration value="CONNECTION_OTHER_VIEW"/>
 *     &lt;enumeration value="FOLLOWING_CREATE"/>
 *     &lt;enumeration value="FOLLOWING_DELETE"/>
 *     &lt;enumeration value="FOLLOWING_OTHER_VIEW"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "entityActivityType")
@XmlEnum
public enum EntityActivityType {

    GENERAL_CREATE,
    GENERAL_UPDATE,
    GENERAL_MOVE,
    GENERAL_DELETE,
    GENERAL_UNDELETE,
    GENERAL_TAG,
    GENERAL_UNTAG,
    TOPIC_CREATE_REPLY,
    TOPIC_UPDATE_MESSAGE,
    COMMENT_CREATE_TOPIC,
    COMMENT_CREATE_REPLY,
    COMMENT_UPDATE_TOPIC,
    COMMENT_UPDATE_MESSAGE,
    BONDABLE_BOND_FOLLOWUP,
    BONDABLE_UNBOND_FOLLOWUP,
    BONDABLE_BOND_RELATED,
    BONDABLE_UNBOND_RELATED,
    TASK_CANCEL,
    TASK_COMPLETE,
    ANNOUNCEMENT_ACTIVATE,
    LOCKABLE_LOCK,
    LOCKABLE_UNLOCK,
    VERSIONABLE_CHECKIN,
    VERSIONABLE_CHECKOUT,
    VERSIONABLE_CHECKOUT_CANCEL,
    WORKSPACE_PARTICIPANT_ADD,
    WORKSPACE_PARTICIPANT_REMOVE,
    WORKSPACE_PARTICIPANT_UPDATE,
    MEETING_COMPLETE,
    CONNECTION_CREATE,
    CONNECTION_DELETE,
    CONNECTION_OTHER_VIEW,
    FOLLOWING_CREATE,
    FOLLOWING_DELETE,
    FOLLOWING_OTHER_VIEW;

    public String value() {
        return name();
    }

    public static EntityActivityType fromValue(String v) {
        return valueOf(v);
    }

}
