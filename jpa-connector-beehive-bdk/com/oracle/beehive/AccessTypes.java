//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accessTypes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accessTypes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="denyAccessTypes" type="{http://www.oracle.com/beehive}accessType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="grantAccessTypes" type="{http://www.oracle.com/beehive}accessType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accessTypes", propOrder = {
    "denyAccessTypes",
    "grantAccessTypes"
})
public class AccessTypes {

    protected List<AccessType> denyAccessTypes;
    protected List<AccessType> grantAccessTypes;

    /**
     * Gets the value of the denyAccessTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the denyAccessTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDenyAccessTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccessType }
     * 
     * 
     */
    public List<AccessType> getDenyAccessTypes() {
        if (denyAccessTypes == null) {
            denyAccessTypes = new ArrayList<AccessType>();
        }
        return this.denyAccessTypes;
    }

    /**
     * Gets the value of the grantAccessTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grantAccessTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrantAccessTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccessType }
     * 
     * 
     */
    public List<AccessType> getGrantAccessTypes() {
        if (grantAccessTypes == null) {
            grantAccessTypes = new ArrayList<AccessType>();
        }
        return this.grantAccessTypes;
    }

}
