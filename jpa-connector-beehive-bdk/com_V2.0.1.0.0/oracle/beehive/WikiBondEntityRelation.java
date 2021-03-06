//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wikiBondEntityRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wikiBondEntityRelation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}bondEntityRelation">
 *       &lt;sequence>
 *         &lt;element name="rootDeleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="rootName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rootParent" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="rootPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rootWorkspacePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="targetName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="targetParent" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="targetPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="targetWorkspacePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wikiBondEntityRelation", propOrder = {
    "rootDeleted",
    "rootName",
    "rootParent",
    "rootPath",
    "rootWorkspacePath",
    "targetName",
    "targetParent",
    "targetPath",
    "targetWorkspacePath"
})
public class WikiBondEntityRelation
    extends BondEntityRelation
{

    protected boolean rootDeleted;
    protected String rootName;
    protected BeeId rootParent;
    protected String rootPath;
    protected String rootWorkspacePath;
    protected String targetName;
    protected BeeId targetParent;
    protected String targetPath;
    protected String targetWorkspacePath;

    /**
     * Gets the value of the rootDeleted property.
     * 
     */
    public boolean isRootDeleted() {
        return rootDeleted;
    }

    /**
     * Sets the value of the rootDeleted property.
     * 
     */
    public void setRootDeleted(boolean value) {
        this.rootDeleted = value;
    }

    /**
     * Gets the value of the rootName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootName() {
        return rootName;
    }

    /**
     * Sets the value of the rootName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootName(String value) {
        this.rootName = value;
    }

    /**
     * Gets the value of the rootParent property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getRootParent() {
        return rootParent;
    }

    /**
     * Sets the value of the rootParent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setRootParent(BeeId value) {
        this.rootParent = value;
    }

    /**
     * Gets the value of the rootPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * Sets the value of the rootPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootPath(String value) {
        this.rootPath = value;
    }

    /**
     * Gets the value of the rootWorkspacePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootWorkspacePath() {
        return rootWorkspacePath;
    }

    /**
     * Sets the value of the rootWorkspacePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootWorkspacePath(String value) {
        this.rootWorkspacePath = value;
    }

    /**
     * Gets the value of the targetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * Sets the value of the targetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetName(String value) {
        this.targetName = value;
    }

    /**
     * Gets the value of the targetParent property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getTargetParent() {
        return targetParent;
    }

    /**
     * Sets the value of the targetParent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setTargetParent(BeeId value) {
        this.targetParent = value;
    }

    /**
     * Gets the value of the targetPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetPath() {
        return targetPath;
    }

    /**
     * Sets the value of the targetPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetPath(String value) {
        this.targetPath = value;
    }

    /**
     * Gets the value of the targetWorkspacePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetWorkspacePath() {
        return targetWorkspacePath;
    }

    /**
     * Sets the value of the targetWorkspacePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetWorkspacePath(String value) {
        this.targetWorkspacePath = value;
    }

}
