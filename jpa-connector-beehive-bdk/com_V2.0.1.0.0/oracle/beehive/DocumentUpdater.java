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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for documentUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="documentUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}artifactUpdater">
 *       &lt;sequence>
 *         &lt;element name="categoryApplicationsUpdater" type="{http://www.oracle.com/beehive}categoryApplicationListUpdater" minOccurs="0"/>
 *         &lt;element name="contentUpdater" type="{http://www.oracle.com/beehive}contentUpdater" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versionUpdateMode" type="{http://www.oracle.com/beehive}versionUpdateMode" minOccurs="0"/>
 *         &lt;element name="workflowAttributesUpdater" type="{http://www.oracle.com/beehive}attributeApplicationListUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentUpdater", propOrder = {
    "categoryApplicationsUpdater",
    "contentUpdater",
    "description",
    "versionUpdateMode",
    "workflowAttributesUpdater"
})
@XmlRootElement(name = "documentUpdater")
public class DocumentUpdater
    extends ArtifactUpdater
{

    protected CategoryApplicationListUpdater categoryApplicationsUpdater;
    protected ContentUpdater contentUpdater;
    protected String description;
    protected VersionUpdateMode versionUpdateMode;
    protected AttributeApplicationListUpdater workflowAttributesUpdater;

    /**
     * Gets the value of the categoryApplicationsUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryApplicationListUpdater }
     *     
     */
    public CategoryApplicationListUpdater getCategoryApplicationsUpdater() {
        return categoryApplicationsUpdater;
    }

    /**
     * Sets the value of the categoryApplicationsUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryApplicationListUpdater }
     *     
     */
    public void setCategoryApplicationsUpdater(CategoryApplicationListUpdater value) {
        this.categoryApplicationsUpdater = value;
    }

    /**
     * Gets the value of the contentUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link ContentUpdater }
     *     
     */
    public ContentUpdater getContentUpdater() {
        return contentUpdater;
    }

    /**
     * Sets the value of the contentUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentUpdater }
     *     
     */
    public void setContentUpdater(ContentUpdater value) {
        this.contentUpdater = value;
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
     * Gets the value of the versionUpdateMode property.
     * 
     * @return
     *     possible object is
     *     {@link VersionUpdateMode }
     *     
     */
    public VersionUpdateMode getVersionUpdateMode() {
        return versionUpdateMode;
    }

    /**
     * Sets the value of the versionUpdateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link VersionUpdateMode }
     *     
     */
    public void setVersionUpdateMode(VersionUpdateMode value) {
        this.versionUpdateMode = value;
    }

    /**
     * Gets the value of the workflowAttributesUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeApplicationListUpdater }
     *     
     */
    public AttributeApplicationListUpdater getWorkflowAttributesUpdater() {
        return workflowAttributesUpdater;
    }

    /**
     * Sets the value of the workflowAttributesUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeApplicationListUpdater }
     *     
     */
    public void setWorkflowAttributesUpdater(AttributeApplicationListUpdater value) {
        this.workflowAttributesUpdater = value;
    }

}
