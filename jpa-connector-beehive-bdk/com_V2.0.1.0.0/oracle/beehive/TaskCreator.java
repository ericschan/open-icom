//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for taskCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="taskCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="taskList" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="taskUpdater" type="{http://www.oracle.com/beehive}taskUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taskCreator", propOrder = {
    "taskList",
    "taskUpdater"
})
@XmlRootElement(name = "taskCreator")
public class TaskCreator
    extends EntityCreator
{

    protected BeeId taskList;
    protected TaskUpdater taskUpdater;

    /**
     * Gets the value of the taskList property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getTaskList() {
        return taskList;
    }

    /**
     * Sets the value of the taskList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setTaskList(BeeId value) {
        this.taskList = value;
    }

    /**
     * Gets the value of the taskUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link TaskUpdater }
     *     
     */
    public TaskUpdater getTaskUpdater() {
        return taskUpdater;
    }

    /**
     * Sets the value of the taskUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskUpdater }
     *     
     */
    public void setTaskUpdater(TaskUpdater value) {
        this.taskUpdater = value;
    }

}
