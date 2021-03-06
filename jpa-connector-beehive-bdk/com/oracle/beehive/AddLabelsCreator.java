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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addLabelsCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addLabelsCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="labelBundleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://www.oracle.com/beehive}labelBundleUpdater" minOccurs="0"/>
 *         &lt;element name="labelHandles" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addLabelsCreator", propOrder = {
    "labelBundleName",
    "labelBundleUpdater",
    "labelHandles"
})
public class AddLabelsCreator
    extends EntityCreator
{

    protected String labelBundleName;
    protected LabelBundleUpdater labelBundleUpdater;
    @XmlElement(nillable = true)
    protected List<BeeId> labelHandles;

    /**
     * Gets the value of the labelBundleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelBundleName() {
        return labelBundleName;
    }

    /**
     * Sets the value of the labelBundleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelBundleName(String value) {
        this.labelBundleName = value;
    }

    /**
     * Gets the value of the labelBundleUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LabelBundleUpdater }
     *     
     */
    public LabelBundleUpdater getLabelBundleUpdater() {
        return labelBundleUpdater;
    }

    /**
     * Sets the value of the labelBundleUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelBundleUpdater }
     *     
     */
    public void setLabelBundleUpdater(LabelBundleUpdater value) {
        this.labelBundleUpdater = value;
    }

    /**
     * Gets the value of the labelHandles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the labelHandles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabelHandles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getLabelHandles() {
        if (labelHandles == null) {
            labelHandles = new ArrayList<BeeId>();
        }
        return this.labelHandles;
    }

}
