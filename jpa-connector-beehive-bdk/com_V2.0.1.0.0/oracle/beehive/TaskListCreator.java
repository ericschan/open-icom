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
 * <p>Java class for taskListCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="taskListCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="taskListUpdater" type="{http://www.oracle.com/beehive}taskListUpdater" minOccurs="0"/>
 *         &lt;element name="workspaceHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taskListCreator", propOrder = {
    "taskListUpdater",
    "workspaceHandle"
})
public class TaskListCreator
    extends EntityCreator
{

    protected TaskListUpdater taskListUpdater;
    protected BeeId workspaceHandle;

    /**
     * Gets the value of the taskListUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link TaskListUpdater }
     *     
     */
    public TaskListUpdater getTaskListUpdater() {
        return taskListUpdater;
    }

    /**
     * Sets the value of the taskListUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskListUpdater }
     *     
     */
    public void setTaskListUpdater(TaskListUpdater value) {
        this.taskListUpdater = value;
    }

    /**
     * Gets the value of the workspaceHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getWorkspaceHandle() {
        return workspaceHandle;
    }

    /**
     * Sets the value of the workspaceHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setWorkspaceHandle(BeeId value) {
        this.workspaceHandle = value;
    }

}