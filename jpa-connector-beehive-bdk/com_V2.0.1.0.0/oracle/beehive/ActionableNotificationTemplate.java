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
 * <p>Java class for actionableNotificationTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="actionableNotificationTemplate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entitySchema">
 *       &lt;sequence>
 *         &lt;element name="actionString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="classBinding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="methodBinding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resourceBundleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actionableNotificationTemplate", propOrder = {
    "actionString",
    "classBinding",
    "methodBinding",
    "resourceBundleName"
})
public class ActionableNotificationTemplate
    extends EntitySchema
{

    protected String actionString;
    protected String classBinding;
    protected String methodBinding;
    protected String resourceBundleName;

    /**
     * Gets the value of the actionString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionString() {
        return actionString;
    }

    /**
     * Sets the value of the actionString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionString(String value) {
        this.actionString = value;
    }

    /**
     * Gets the value of the classBinding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassBinding() {
        return classBinding;
    }

    /**
     * Sets the value of the classBinding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassBinding(String value) {
        this.classBinding = value;
    }

    /**
     * Gets the value of the methodBinding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodBinding() {
        return methodBinding;
    }

    /**
     * Sets the value of the methodBinding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodBinding(String value) {
        this.methodBinding = value;
    }

    /**
     * Gets the value of the resourceBundleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceBundleName() {
        return resourceBundleName;
    }

    /**
     * Sets the value of the resourceBundleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceBundleName(String value) {
        this.resourceBundleName = value;
    }

}
