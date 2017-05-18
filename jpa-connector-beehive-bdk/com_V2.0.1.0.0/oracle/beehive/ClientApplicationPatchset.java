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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clientApplicationPatchset complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clientApplicationPatchset">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entity">
 *       &lt;sequence>
 *         &lt;element name="modules" type="{http://www.oracle.com/beehive}clientApplicationModule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="patchsetNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientApplicationPatchset", propOrder = {
    "modules",
    "patchsetNumber"
})
public class ClientApplicationPatchset
    extends Entity
{

    @XmlElement(nillable = true)
    protected List<ClientApplicationModule> modules;
    protected int patchsetNumber;

    /**
     * Gets the value of the modules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClientApplicationModule }
     * 
     * 
     */
    public List<ClientApplicationModule> getModules() {
        if (modules == null) {
            modules = new ArrayList<ClientApplicationModule>();
        }
        return this.modules;
    }

    /**
     * Gets the value of the patchsetNumber property.
     * 
     */
    public int getPatchsetNumber() {
        return patchsetNumber;
    }

    /**
     * Sets the value of the patchsetNumber property.
     * 
     */
    public void setPatchsetNumber(int value) {
        this.patchsetNumber = value;
    }

}
