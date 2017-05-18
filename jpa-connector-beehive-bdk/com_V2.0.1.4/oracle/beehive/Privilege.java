//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for privilege.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="privilege">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADDRESS_BOOK_MGR"/>
 *     &lt;enumeration value="ADDRESS_BOOK_USER"/>
 *     &lt;enumeration value="ARCHIVE_MGR"/>
 *     &lt;enumeration value="AUDIT_ADMIN"/>
 *     &lt;enumeration value="AUDITOR"/>
 *     &lt;enumeration value="BYPASS"/>
 *     &lt;enumeration value="CALENDAR_MGR"/>
 *     &lt;enumeration value="CALENDAR_USER"/>
 *     &lt;enumeration value="CONF_MGR"/>
 *     &lt;enumeration value="CONF_USER"/>
 *     &lt;enumeration value="CONTENT_MGR"/>
 *     &lt;enumeration value="CONTENT_USER"/>
 *     &lt;enumeration value="DELEGATE"/>
 *     &lt;enumeration value="DIAGNOSE"/>
 *     &lt;enumeration value="DM_MGR"/>
 *     &lt;enumeration value="EMAIL_MGR"/>
 *     &lt;enumeration value="EMAIL_USER"/>
 *     &lt;enumeration value="EXCEED_QUOTA"/>
 *     &lt;enumeration value="EXPERTISE_MGR"/>
 *     &lt;enumeration value="FEED_MGR"/>
 *     &lt;enumeration value="FORUM_MGR"/>
 *     &lt;enumeration value="FORUM_WRITER"/>
 *     &lt;enumeration value="FORUM_USER"/>
 *     &lt;enumeration value="IM_MGR"/>
 *     &lt;enumeration value="IM_USER"/>
 *     &lt;enumeration value="LOGIN"/>
 *     &lt;enumeration value="MARKER_MGR"/>
 *     &lt;enumeration value="MODIFY_ACL"/>
 *     &lt;enumeration value="NOTIFICATION_MGR"/>
 *     &lt;enumeration value="NOTIFICATION_USER"/>
 *     &lt;enumeration value="ORGANIZATION_MGR"/>
 *     &lt;enumeration value="POLICY_MGR"/>
 *     &lt;enumeration value="PREFERENCE_MGR"/>
 *     &lt;enumeration value="PROTOCOL_USER"/>
 *     &lt;enumeration value="QUOTA_MGR"/>
 *     &lt;enumeration value="READALL"/>
 *     &lt;enumeration value="RECORDS_MGR"/>
 *     &lt;enumeration value="RESOURCE_MGR"/>
 *     &lt;enumeration value="ROLE_MGR"/>
 *     &lt;enumeration value="S2S"/>
 *     &lt;enumeration value="SECURITY"/>
 *     &lt;enumeration value="SHARED_LABEL_MODIFIER"/>
 *     &lt;enumeration value="SUBSCRIPTION_MGR"/>
 *     &lt;enumeration value="SUBSCRIPTION_USER"/>
 *     &lt;enumeration value="SYSTEM_MONITOR"/>
 *     &lt;enumeration value="SYSTEM_OPER"/>
 *     &lt;enumeration value="TASK_MGR"/>
 *     &lt;enumeration value="TASK_USER"/>
 *     &lt;enumeration value="TIMEZONE_MGR"/>
 *     &lt;enumeration value="USER_MGR"/>
 *     &lt;enumeration value="VERSION_MGR"/>
 *     &lt;enumeration value="VOICE_USER"/>
 *     &lt;enumeration value="WEBADMIN_USER"/>
 *     &lt;enumeration value="WIKI_USER"/>
 *     &lt;enumeration value="WIKI_MGR"/>
 *     &lt;enumeration value="WORKFLOW_MGR"/>
 *     &lt;enumeration value="WORKFLOWTASK_MGR"/>
 *     &lt;enumeration value="WORKSPACE_ADD"/>
 *     &lt;enumeration value="WORKSPACE_MGR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "privilege")
@XmlEnum
public enum Privilege {

    ADDRESS_BOOK_MGR("ADDRESS_BOOK_MGR"),
    ADDRESS_BOOK_USER("ADDRESS_BOOK_USER"),
    ARCHIVE_MGR("ARCHIVE_MGR"),
    AUDIT_ADMIN("AUDIT_ADMIN"),
    AUDITOR("AUDITOR"),
    BYPASS("BYPASS"),
    CALENDAR_MGR("CALENDAR_MGR"),
    CALENDAR_USER("CALENDAR_USER"),
    CONF_MGR("CONF_MGR"),
    CONF_USER("CONF_USER"),
    CONTENT_MGR("CONTENT_MGR"),
    CONTENT_USER("CONTENT_USER"),
    DELEGATE("DELEGATE"),
    DIAGNOSE("DIAGNOSE"),
    DM_MGR("DM_MGR"),
    EMAIL_MGR("EMAIL_MGR"),
    EMAIL_USER("EMAIL_USER"),
    EXCEED_QUOTA("EXCEED_QUOTA"),
    EXPERTISE_MGR("EXPERTISE_MGR"),
    FEED_MGR("FEED_MGR"),
    FORUM_MGR("FORUM_MGR"),
    FORUM_WRITER("FORUM_WRITER"),
    FORUM_USER("FORUM_USER"),
    IM_MGR("IM_MGR"),
    IM_USER("IM_USER"),
    LOGIN("LOGIN"),
    MARKER_MGR("MARKER_MGR"),
    MODIFY_ACL("MODIFY_ACL"),
    NOTIFICATION_MGR("NOTIFICATION_MGR"),
    NOTIFICATION_USER("NOTIFICATION_USER"),
    ORGANIZATION_MGR("ORGANIZATION_MGR"),
    POLICY_MGR("POLICY_MGR"),
    PREFERENCE_MGR("PREFERENCE_MGR"),
    PROTOCOL_USER("PROTOCOL_USER"),
    QUOTA_MGR("QUOTA_MGR"),
    READALL("READALL"),
    RECORDS_MGR("RECORDS_MGR"),
    RESOURCE_MGR("RESOURCE_MGR"),
    ROLE_MGR("ROLE_MGR"),
    @XmlEnumValue("S2S")
    S_2_S("S2S"),
    SECURITY("SECURITY"),
    SHARED_LABEL_MODIFIER("SHARED_LABEL_MODIFIER"),
    SUBSCRIPTION_MGR("SUBSCRIPTION_MGR"),
    SUBSCRIPTION_USER("SUBSCRIPTION_USER"),
    SYSTEM_MONITOR("SYSTEM_MONITOR"),
    SYSTEM_OPER("SYSTEM_OPER"),
    TASK_MGR("TASK_MGR"),
    TASK_USER("TASK_USER"),
    TIMEZONE_MGR("TIMEZONE_MGR"),
    USER_MGR("USER_MGR"),
    VERSION_MGR("VERSION_MGR"),
    VOICE_USER("VOICE_USER"),
    WEBADMIN_USER("WEBADMIN_USER"),
    WIKI_USER("WIKI_USER"),
    WIKI_MGR("WIKI_MGR"),
    WORKFLOW_MGR("WORKFLOW_MGR"),
    WORKFLOWTASK_MGR("WORKFLOWTASK_MGR"),
    WORKSPACE_ADD("WORKSPACE_ADD"),
    WORKSPACE_MGR("WORKSPACE_MGR");
    private final String value;

    Privilege(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Privilege fromValue(String v) {
        for (Privilege c: Privilege.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
