//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for taskListCalDavResourceNamePredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="taskListCalDavResourceNamePredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}taskListPredicate">
 *       &lt;sequence>
 *         &lt;element name="calDavResourceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taskListCalDavResourceNamePredicate", propOrder = {
    "calDavResourceName"
})
public class TaskListCalDavResourceNamePredicate
    extends TaskListPredicate
{

    protected String calDavResourceName;

    /**
     * Gets the value of the calDavResourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalDavResourceName() {
        return calDavResourceName;
    }

    /**
     * Sets the value of the calDavResourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalDavResourceName(String value) {
        this.calDavResourceName = value;
    }

}
