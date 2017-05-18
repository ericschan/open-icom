//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="addedFlags" type="{http://www.oracle.com/beehive}messageFlag" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="flags" type="{http://www.oracle.com/beehive}messageFlag" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="overriddenFlags" type="{http://www.oracle.com/beehive}messageFlag" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removedFlags" type="{http://www.oracle.com/beehive}messageFlag" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageUpdater", propOrder = {
    "addedFlags",
    "flags",
    "overriddenFlags",
    "removedFlags"
})
@XmlSeeAlso({
    VoiceMessageUpdater.class,
    EmailMessageUpdater.class,
    FaxMessageUpdater.class,
    EmailDraftUpdater.class,
    DiscussionsDraftUpdater.class,
    DiscussionsMessageUpdater.class
})
public abstract class MessageUpdater
    extends ArtifactUpdater
{

    protected List<MessageFlag> addedFlags;
    protected List<MessageFlag> flags;
    protected List<MessageFlag> overriddenFlags;
    protected List<MessageFlag> removedFlags;

    /**
     * Gets the value of the addedFlags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addedFlags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddedFlags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageFlag }
     * 
     * 
     */
    public List<MessageFlag> getAddedFlags() {
        if (addedFlags == null) {
            addedFlags = new ArrayList<MessageFlag>();
        }
        return this.addedFlags;
    }

    /**
     * Gets the value of the flags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageFlag }
     * 
     * 
     */
    public List<MessageFlag> getFlags() {
        if (flags == null) {
            flags = new ArrayList<MessageFlag>();
        }
        return this.flags;
    }

    /**
     * Gets the value of the overriddenFlags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the overriddenFlags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOverriddenFlags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageFlag }
     * 
     * 
     */
    public List<MessageFlag> getOverriddenFlags() {
        if (overriddenFlags == null) {
            overriddenFlags = new ArrayList<MessageFlag>();
        }
        return this.overriddenFlags;
    }

    /**
     * Gets the value of the removedFlags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removedFlags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemovedFlags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageFlag }
     * 
     * 
     */
    public List<MessageFlag> getRemovedFlags() {
        if (removedFlags == null) {
            removedFlags = new ArrayList<MessageFlag>();
        }
        return this.removedFlags;
    }

}
