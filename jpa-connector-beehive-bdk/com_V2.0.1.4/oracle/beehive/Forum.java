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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for forum complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="forum">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}folder">
 *       &lt;sequence>
 *         &lt;element name="activePreferenceProfile" type="{http://www.oracle.com/beehive}preferenceProfile" minOccurs="0"/>
 *         &lt;element name="announcementAndTopicCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="announcements" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="announcementsAndTopics" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="availablePreferenceProfiles" type="{http://www.oracle.com/beehive}preferenceProfile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="discussionsChangeStatus" type="{http://www.oracle.com/beehive}changeStatus" minOccurs="0"/>
 *         &lt;element name="discussionsMessageDeletePermissions" type="{http://www.oracle.com/beehive}discussionsMessageDeletePermissions" minOccurs="0"/>
 *         &lt;element name="discussionsMessageUpdatePermissions" type="{http://www.oracle.com/beehive}discussionsMessageUpdatePermissions" minOccurs="0"/>
 *         &lt;element name="elements" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="lastPost" type="{http://www.oracle.com/beehive}discussionsMessage" minOccurs="0"/>
 *         &lt;element name="locks" type="{http://www.oracle.com/beehive}lock" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="messageCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="subForums" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="topicCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="topics" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="topicsMessageCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="viewCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "forum", propOrder = {
    "activePreferenceProfile",
    "announcementAndTopicCount",
    "announcements",
    "announcementsAndTopics",
    "availablePreferenceProfiles",
    "discussionsChangeStatus",
    "discussionsMessageDeletePermissions",
    "discussionsMessageUpdatePermissions",
    "elements",
    "lastPost",
    "locks",
    "messageCount",
    "subForums",
    "topicCount",
    "topics",
    "topicsMessageCount",
    "viewCount"
})
@XmlSeeAlso({
    DiscussThisForum.class
})
public class Forum
    extends Folder
{

    protected PreferenceProfile activePreferenceProfile;
    protected int announcementAndTopicCount;
    protected ListResult announcements;
    protected ListResult announcementsAndTopics;
    protected List<PreferenceProfile> availablePreferenceProfiles;
    protected ChangeStatus discussionsChangeStatus;
    protected DiscussionsMessageDeletePermissions discussionsMessageDeletePermissions;
    protected DiscussionsMessageUpdatePermissions discussionsMessageUpdatePermissions;
    protected ListResult elements;
    protected DiscussionsMessage lastPost;
    @XmlElement(nillable = true)
    protected List<Lock> locks;
    protected int messageCount;
    protected ListResult subForums;
    protected int topicCount;
    protected ListResult topics;
    protected int topicsMessageCount;
    protected int viewCount;

    /**
     * Gets the value of the activePreferenceProfile property.
     * 
     * @return
     *     possible object is
     *     {@link PreferenceProfile }
     *     
     */
    public PreferenceProfile getActivePreferenceProfile() {
        return activePreferenceProfile;
    }

    /**
     * Sets the value of the activePreferenceProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenceProfile }
     *     
     */
    public void setActivePreferenceProfile(PreferenceProfile value) {
        this.activePreferenceProfile = value;
    }

    /**
     * Gets the value of the announcementAndTopicCount property.
     * 
     */
    public int getAnnouncementAndTopicCount() {
        return announcementAndTopicCount;
    }

    /**
     * Sets the value of the announcementAndTopicCount property.
     * 
     */
    public void setAnnouncementAndTopicCount(int value) {
        this.announcementAndTopicCount = value;
    }

    /**
     * Gets the value of the announcements property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getAnnouncements() {
        return announcements;
    }

    /**
     * Sets the value of the announcements property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setAnnouncements(ListResult value) {
        this.announcements = value;
    }

    /**
     * Gets the value of the announcementsAndTopics property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getAnnouncementsAndTopics() {
        return announcementsAndTopics;
    }

    /**
     * Sets the value of the announcementsAndTopics property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setAnnouncementsAndTopics(ListResult value) {
        this.announcementsAndTopics = value;
    }

    /**
     * Gets the value of the availablePreferenceProfiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availablePreferenceProfiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailablePreferenceProfiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreferenceProfile }
     * 
     * 
     */
    public List<PreferenceProfile> getAvailablePreferenceProfiles() {
        if (availablePreferenceProfiles == null) {
            availablePreferenceProfiles = new ArrayList<PreferenceProfile>();
        }
        return this.availablePreferenceProfiles;
    }

    /**
     * Gets the value of the discussionsChangeStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeStatus }
     *     
     */
    public ChangeStatus getDiscussionsChangeStatus() {
        return discussionsChangeStatus;
    }

    /**
     * Sets the value of the discussionsChangeStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeStatus }
     *     
     */
    public void setDiscussionsChangeStatus(ChangeStatus value) {
        this.discussionsChangeStatus = value;
    }

    /**
     * Gets the value of the discussionsMessageDeletePermissions property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessageDeletePermissions }
     *     
     */
    public DiscussionsMessageDeletePermissions getDiscussionsMessageDeletePermissions() {
        return discussionsMessageDeletePermissions;
    }

    /**
     * Sets the value of the discussionsMessageDeletePermissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessageDeletePermissions }
     *     
     */
    public void setDiscussionsMessageDeletePermissions(DiscussionsMessageDeletePermissions value) {
        this.discussionsMessageDeletePermissions = value;
    }

    /**
     * Gets the value of the discussionsMessageUpdatePermissions property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessageUpdatePermissions }
     *     
     */
    public DiscussionsMessageUpdatePermissions getDiscussionsMessageUpdatePermissions() {
        return discussionsMessageUpdatePermissions;
    }

    /**
     * Sets the value of the discussionsMessageUpdatePermissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessageUpdatePermissions }
     *     
     */
    public void setDiscussionsMessageUpdatePermissions(DiscussionsMessageUpdatePermissions value) {
        this.discussionsMessageUpdatePermissions = value;
    }

    /**
     * Gets the value of the elements property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getElements() {
        return elements;
    }

    /**
     * Sets the value of the elements property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setElements(ListResult value) {
        this.elements = value;
    }

    /**
     * Gets the value of the lastPost property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessage }
     *     
     */
    public DiscussionsMessage getLastPost() {
        return lastPost;
    }

    /**
     * Sets the value of the lastPost property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessage }
     *     
     */
    public void setLastPost(DiscussionsMessage value) {
        this.lastPost = value;
    }

    /**
     * Gets the value of the locks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Lock }
     * 
     * 
     */
    public List<Lock> getLocks() {
        if (locks == null) {
            locks = new ArrayList<Lock>();
        }
        return this.locks;
    }

    /**
     * Gets the value of the messageCount property.
     * 
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * Sets the value of the messageCount property.
     * 
     */
    public void setMessageCount(int value) {
        this.messageCount = value;
    }

    /**
     * Gets the value of the subForums property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getSubForums() {
        return subForums;
    }

    /**
     * Sets the value of the subForums property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setSubForums(ListResult value) {
        this.subForums = value;
    }

    /**
     * Gets the value of the topicCount property.
     * 
     */
    public int getTopicCount() {
        return topicCount;
    }

    /**
     * Sets the value of the topicCount property.
     * 
     */
    public void setTopicCount(int value) {
        this.topicCount = value;
    }

    /**
     * Gets the value of the topics property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getTopics() {
        return topics;
    }

    /**
     * Sets the value of the topics property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setTopics(ListResult value) {
        this.topics = value;
    }

    /**
     * Gets the value of the topicsMessageCount property.
     * 
     */
    public int getTopicsMessageCount() {
        return topicsMessageCount;
    }

    /**
     * Sets the value of the topicsMessageCount property.
     * 
     */
    public void setTopicsMessageCount(int value) {
        this.topicsMessageCount = value;
    }

    /**
     * Gets the value of the viewCount property.
     * 
     */
    public int getViewCount() {
        return viewCount;
    }

    /**
     * Sets the value of the viewCount property.
     * 
     */
    public void setViewCount(int value) {
        this.viewCount = value;
    }

}
