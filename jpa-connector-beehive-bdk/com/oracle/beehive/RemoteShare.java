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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for remoteShare complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="remoteShare">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}remoteFolder">
 *       &lt;sequence>
 *         &lt;element name="attributes" type="{http://www.oracle.com/beehive}attributeApplication" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="temlate" type="{http://www.oracle.com/beehive}remoteRepository" minOccurs="0"/>
 *         &lt;element name="template" type="{http://www.oracle.com/beehive}remoteRepository" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "remoteShare", propOrder = {
    "attributes",
    "temlate",
    "template"
})
@XmlRootElement(name = "remoteShare")
public class RemoteShare
    extends RemoteFolder
{

    @XmlElement(nillable = true)
    protected List<AttributeApplication> attributes;
    protected RemoteRepository temlate;
    protected RemoteRepository template;

    /**
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeApplication }
     * 
     * 
     */
    public List<AttributeApplication> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<AttributeApplication>();
        }
        return this.attributes;
    }

    /**
     * Gets the value of the temlate property.
     * 
     * @return
     *     possible object is
     *     {@link RemoteRepository }
     *     
     */
    public RemoteRepository getTemlate() {
        return temlate;
    }

    /**
     * Sets the value of the temlate property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteRepository }
     *     
     */
    public void setTemlate(RemoteRepository value) {
        this.temlate = value;
    }

    /**
     * Gets the value of the template property.
     * 
     * @return
     *     possible object is
     *     {@link RemoteRepository }
     *     
     */
    public RemoteRepository getTemplate() {
        return template;
    }

    /**
     * Sets the value of the template property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteRepository }
     *     
     */
    public void setTemplate(RemoteRepository value) {
        this.template = value;
    }

}
