//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for organizationUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="organizationUser">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}user">
 *       &lt;sequence>
 *         &lt;element name="assistant" type="{http://www.oracle.com/beehive}organizationUser" minOccurs="0"/>
 *         &lt;element name="directReports" type="{http://www.oracle.com/beehive}organizationUser" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="manager" type="{http://www.oracle.com/beehive}user" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "organizationUser", propOrder = {
    "assistant",
    "directReports",
    "manager"
})
@XmlRootElement(name = "organizationUser")
public class OrganizationUser
    extends User
{

    protected OrganizationUser assistant;
    protected List<OrganizationUser> directReports;
    protected User manager;

    /**
     * Gets the value of the assistant property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationUser }
     *     
     */
    public OrganizationUser getAssistant() {
        return assistant;
    }

    /**
     * Sets the value of the assistant property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationUser }
     *     
     */
    public void setAssistant(OrganizationUser value) {
        this.assistant = value;
    }

    /**
     * Gets the value of the directReports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the directReports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDirectReports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganizationUser }
     * 
     * 
     */
    public List<OrganizationUser> getDirectReports() {
        if (directReports == null) {
            directReports = new ArrayList<OrganizationUser>();
        }
        return this.directReports;
    }

    /**
     * Gets the value of the manager property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getManager() {
        return manager;
    }

    /**
     * Sets the value of the manager property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setManager(User value) {
        this.manager = value;
    }

}
