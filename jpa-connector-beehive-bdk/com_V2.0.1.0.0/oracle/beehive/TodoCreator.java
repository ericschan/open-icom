//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for todoCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="todoCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="taskListHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="todoUpdater" type="{http://www.oracle.com/beehive}todoUpdater" minOccurs="0"/>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "todoCreator", propOrder = {
    "taskListHandle",
    "todoUpdater",
    "uid"
})
public class TodoCreator
    extends EntityCreator
{

    protected BeeId taskListHandle;
    protected TodoUpdater todoUpdater;
    protected String uid;

    /**
     * Gets the value of the taskListHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getTaskListHandle() {
        return taskListHandle;
    }

    /**
     * Sets the value of the taskListHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setTaskListHandle(BeeId value) {
        this.taskListHandle = value;
    }

    /**
     * Gets the value of the todoUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link TodoUpdater }
     *     
     */
    public TodoUpdater getTodoUpdater() {
        return todoUpdater;
    }

    /**
     * Sets the value of the todoUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoUpdater }
     *     
     */
    public void setTodoUpdater(TodoUpdater value) {
        this.todoUpdater = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

}
