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
 * <p>Java class for topic complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="topic">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}folder">
 *       &lt;sequence>
 *         &lt;element name="correctAnswer" type="{http://www.oracle.com/beehive}discussionsMessage" minOccurs="0"/>
 *         &lt;element name="discussionsChangeStatus" type="{http://www.oracle.com/beehive}changeStatus" minOccurs="0"/>
 *         &lt;element name="discussionsMessages" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="elements" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="firstPost" type="{http://www.oracle.com/beehive}discussionsMessage" minOccurs="0"/>
 *         &lt;element name="lastPost" type="{http://www.oracle.com/beehive}discussionsMessage" minOccurs="0"/>
 *         &lt;element name="locks" type="{http://www.oracle.com/beehive}lock" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="messageCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nextTopic" type="{http://www.oracle.com/beehive}topic" minOccurs="0"/>
 *         &lt;element name="previousTopic" type="{http://www.oracle.com/beehive}topic" minOccurs="0"/>
 *         &lt;element name="question" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="questionStatus" type="{http://www.oracle.com/beehive}topicQuestionStatus" minOccurs="0"/>
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
@XmlType(name = "topic", propOrder = {
    "correctAnswer",
    "discussionsChangeStatus",
    "discussionsMessages",
    "elements",
    "firstPost",
    "lastPost",
    "locks",
    "messageCount",
    "nextTopic",
    "previousTopic",
    "question",
    "questionStatus",
    "viewCount"
})
@XmlSeeAlso({
    Announcement.class
})
public class Topic
    extends Folder
{

    protected DiscussionsMessage correctAnswer;
    protected ChangeStatus discussionsChangeStatus;
    protected ListResult discussionsMessages;
    protected ListResult elements;
    protected DiscussionsMessage firstPost;
    protected DiscussionsMessage lastPost;
    @XmlElement(nillable = true)
    protected List<Lock> locks;
    protected int messageCount;
    protected Topic nextTopic;
    protected Topic previousTopic;
    protected boolean question;
    protected TopicQuestionStatus questionStatus;
    protected int viewCount;

    /**
     * Gets the value of the correctAnswer property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessage }
     *     
     */
    public DiscussionsMessage getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the value of the correctAnswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessage }
     *     
     */
    public void setCorrectAnswer(DiscussionsMessage value) {
        this.correctAnswer = value;
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
     * Gets the value of the discussionsMessages property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getDiscussionsMessages() {
        return discussionsMessages;
    }

    /**
     * Sets the value of the discussionsMessages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setDiscussionsMessages(ListResult value) {
        this.discussionsMessages = value;
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
     * Gets the value of the firstPost property.
     * 
     * @return
     *     possible object is
     *     {@link DiscussionsMessage }
     *     
     */
    public DiscussionsMessage getFirstPost() {
        return firstPost;
    }

    /**
     * Sets the value of the firstPost property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscussionsMessage }
     *     
     */
    public void setFirstPost(DiscussionsMessage value) {
        this.firstPost = value;
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
     * Gets the value of the nextTopic property.
     * 
     * @return
     *     possible object is
     *     {@link Topic }
     *     
     */
    public Topic getNextTopic() {
        return nextTopic;
    }

    /**
     * Sets the value of the nextTopic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topic }
     *     
     */
    public void setNextTopic(Topic value) {
        this.nextTopic = value;
    }

    /**
     * Gets the value of the previousTopic property.
     * 
     * @return
     *     possible object is
     *     {@link Topic }
     *     
     */
    public Topic getPreviousTopic() {
        return previousTopic;
    }

    /**
     * Sets the value of the previousTopic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topic }
     *     
     */
    public void setPreviousTopic(Topic value) {
        this.previousTopic = value;
    }

    /**
     * Gets the value of the question property.
     * 
     */
    public boolean isQuestion() {
        return question;
    }

    /**
     * Sets the value of the question property.
     * 
     */
    public void setQuestion(boolean value) {
        this.question = value;
    }

    /**
     * Gets the value of the questionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link TopicQuestionStatus }
     *     
     */
    public TopicQuestionStatus getQuestionStatus() {
        return questionStatus;
    }

    /**
     * Sets the value of the questionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TopicQuestionStatus }
     *     
     */
    public void setQuestionStatus(TopicQuestionStatus value) {
        this.questionStatus = value;
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
