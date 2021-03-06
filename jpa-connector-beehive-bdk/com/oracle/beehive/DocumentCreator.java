//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for documentCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="documentCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="conflictResolutionMode" type="{http://www.oracle.com/beehive}conflictResolutionMode"/>
 *         &lt;element name="ignorePendingConflicts" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parent" type="{http://www.oracle.com/beehive}beeId"/>
 *         &lt;element name="updater" type="{http://www.oracle.com/beehive}documentUpdater"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentCreator", propOrder = {
    "conflictResolutionMode",
    "ignorePendingConflicts",
    "name",
    "parent",
    "updater"
})
@XmlRootElement(name = "documentCreator")
public class DocumentCreator
    extends EntityCreator
{

    @XmlElement(required = true)
    protected ConflictResolutionMode conflictResolutionMode;
    protected Boolean ignorePendingConflicts;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected BeeId parent;
    @XmlElement(required = true)
    protected DocumentUpdater updater;

    /**
     * Gets the value of the conflictResolutionMode property.
     * 
     * @return
     *     possible object is
     *     {@link ConflictResolutionMode }
     *     
     */
    public ConflictResolutionMode getConflictResolutionMode() {
        return conflictResolutionMode;
    }

    /**
     * Sets the value of the conflictResolutionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConflictResolutionMode }
     *     
     */
    public void setConflictResolutionMode(ConflictResolutionMode value) {
        this.conflictResolutionMode = value;
    }

    /**
     * Gets the value of the ignorePendingConflicts property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIgnorePendingConflicts() {
        return ignorePendingConflicts;
    }

    /**
     * Sets the value of the ignorePendingConflicts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIgnorePendingConflicts(Boolean value) {
        this.ignorePendingConflicts = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setParent(BeeId value) {
        this.parent = value;
    }

    /**
     * Gets the value of the updater property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentUpdater }
     *     
     */
    public DocumentUpdater getUpdater() {
        return updater;
    }

    /**
     * Sets the value of the updater property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentUpdater }
     *     
     */
    public void setUpdater(DocumentUpdater value) {
        this.updater = value;
    }

}
