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
 * <p>Java class for calendarCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="calendarCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="calendarUpdater" type="{http://www.oracle.com/beehive}calendarUpdater" minOccurs="0"/>
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
@XmlType(name = "calendarCreator", propOrder = {
    "calendarUpdater",
    "workspaceHandle"
})
public class CalendarCreator
    extends EntityCreator
{

    protected CalendarUpdater calendarUpdater;
    protected BeeId workspaceHandle;

    /**
     * Gets the value of the calendarUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CalendarUpdater }
     *     
     */
    public CalendarUpdater getCalendarUpdater() {
        return calendarUpdater;
    }

    /**
     * Sets the value of the calendarUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalendarUpdater }
     *     
     */
    public void setCalendarUpdater(CalendarUpdater value) {
        this.calendarUpdater = value;
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
