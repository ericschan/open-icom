//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}list">
 *       &lt;sequence>
 *         &lt;element name="countLimitExceeded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listResult", propOrder = {
    "countLimitExceeded"
})
@XmlRootElement(name = "listResult")
public class ListResult
    extends List
{

    protected boolean countLimitExceeded;

    /**
     * Gets the value of the countLimitExceeded property.
     * 
     */
    public boolean isCountLimitExceeded() {
        return countLimitExceeded;
    }

    /**
     * Sets the value of the countLimitExceeded property.
     * 
     */
    public void setCountLimitExceeded(boolean value) {
        this.countLimitExceeded = value;
    }

}
