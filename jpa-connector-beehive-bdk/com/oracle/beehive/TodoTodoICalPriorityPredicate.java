//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for todoTodoICalPriorityPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="todoTodoICalPriorityPredicate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}todoPredicate">
 *       &lt;sequence>
 *         &lt;element name="ICalPriority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "todoTodoICalPriorityPredicate", propOrder = {
    "iCalPriority"
})
public class TodoTodoICalPriorityPredicate
    extends TodoPredicate
{

    @XmlElement(name = "ICalPriority")
    protected int iCalPriority;

    /**
     * Gets the value of the iCalPriority property.
     * 
     */
    public int getICalPriority() {
        return iCalPriority;
    }

    /**
     * Sets the value of the iCalPriority property.
     * 
     */
    public void setICalPriority(int value) {
        this.iCalPriority = value;
    }

}
