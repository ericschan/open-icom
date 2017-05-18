//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for multiContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="multiContent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}content">
 *       &lt;sequence>
 *         &lt;element name="multiPartType" type="{http://www.oracle.com/beehive}multiContentType" minOccurs="0"/>
 *         &lt;element name="parts" type="{http://www.oracle.com/beehive}content" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "multiContent", propOrder = {
    "multiPartType",
    "parts"
})
public class MultiContent
    extends Content
{

    protected MultiContentType multiPartType;
    protected List<Content> parts;

    /**
     * Gets the value of the multiPartType property.
     * 
     * @return
     *     possible object is
     *     {@link MultiContentType }
     *     
     */
    public MultiContentType getMultiPartType() {
        return multiPartType;
    }

    /**
     * Sets the value of the multiPartType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiContentType }
     *     
     */
    public void setMultiPartType(MultiContentType value) {
        this.multiPartType = value;
    }

    /**
     * Gets the value of the parts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Content }
     * 
     * 
     */
    public List<Content> getParts() {
        if (parts == null) {
            parts = new ArrayList<Content>();
        }
        return this.parts;
    }

}
