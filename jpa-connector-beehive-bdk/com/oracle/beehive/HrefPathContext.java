//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for hrefPathContext complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hrefPathContext">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}hrefContext">
 *       &lt;sequence>
 *         &lt;element name="basePathPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="workspacePathPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hrefPathContext", propOrder = {
    "basePathPrefix",
    "workspacePathPrefix"
})
public class HrefPathContext
    extends HrefContext
{

    protected String basePathPrefix;
    protected String workspacePathPrefix;

    /**
     * Gets the value of the basePathPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasePathPrefix() {
        return basePathPrefix;
    }

    /**
     * Sets the value of the basePathPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasePathPrefix(String value) {
        this.basePathPrefix = value;
    }

    /**
     * Gets the value of the workspacePathPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkspacePathPrefix() {
        return workspacePathPrefix;
    }

    /**
     * Sets the value of the workspacePathPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkspacePathPrefix(String value) {
        this.workspacePathPrefix = value;
    }

}
