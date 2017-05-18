//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for labelApplication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="labelApplication">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableSnapshot">
 *       &lt;sequence>
 *         &lt;element name="appliedBy" type="{http://www.oracle.com/beehive}actor" minOccurs="0"/>
 *         &lt;element name="appliedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="label" type="{http://www.oracle.com/beehive}label" minOccurs="0"/>
 *         &lt;element name="labelApplicationType" type="{http://www.oracle.com/beehive}labelApplicationType" minOccurs="0"/>
 *         &lt;element name="labeledEntity" type="{http://www.oracle.com/beehive}entity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "labelApplication", propOrder = {
    "appliedBy",
    "appliedOn",
    "description",
    "label",
    "labelApplicationType",
    "labeledEntity"
})
@XmlRootElement(name = "labelApplication")
public class LabelApplication
    extends IdentifiableSnapshot
{

    protected Actor appliedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar appliedOn;
    protected String description;
    protected Label label;
    protected LabelApplicationType labelApplicationType;
    protected Entity labeledEntity;

    /**
     * Gets the value of the appliedBy property.
     * 
     * @return
     *     possible object is
     *     {@link Actor }
     *     
     */
    public Actor getAppliedBy() {
        return appliedBy;
    }

    /**
     * Sets the value of the appliedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Actor }
     *     
     */
    public void setAppliedBy(Actor value) {
        this.appliedBy = value;
    }

    /**
     * Gets the value of the appliedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAppliedOn() {
        return appliedOn;
    }

    /**
     * Sets the value of the appliedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAppliedOn(XMLGregorianCalendar value) {
        this.appliedOn = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link Label }
     *     
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link Label }
     *     
     */
    public void setLabel(Label value) {
        this.label = value;
    }

    /**
     * Gets the value of the labelApplicationType property.
     * 
     * @return
     *     possible object is
     *     {@link LabelApplicationType }
     *     
     */
    public LabelApplicationType getLabelApplicationType() {
        return labelApplicationType;
    }

    /**
     * Sets the value of the labelApplicationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelApplicationType }
     *     
     */
    public void setLabelApplicationType(LabelApplicationType value) {
        this.labelApplicationType = value;
    }

    /**
     * Gets the value of the labeledEntity property.
     * 
     * @return
     *     possible object is
     *     {@link Entity }
     *     
     */
    public Entity getLabeledEntity() {
        return labeledEntity;
    }

    /**
     * Sets the value of the labeledEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entity }
     *     
     */
    public void setLabeledEntity(Entity value) {
        this.labeledEntity = value;
    }

}
