//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for remoteRepositoryCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="remoteRepositoryCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="attributes" type="{http://www.oracle.com/beehive}remoteRepositoryUpdater" minOccurs="0"/>
 *         &lt;element name="containerHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="schemaHandle" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "remoteRepositoryCreator", propOrder = {
    "attributes",
    "containerHandle",
    "name",
    "schemaHandle"
})
public class RemoteRepositoryCreator
    extends EntityCreator
{

    protected RemoteRepositoryUpdater attributes;
    protected BeeId containerHandle;
    protected String name;
    protected BeeId schemaHandle;

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link RemoteRepositoryUpdater }
     *     
     */
    public RemoteRepositoryUpdater getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteRepositoryUpdater }
     *     
     */
    public void setAttributes(RemoteRepositoryUpdater value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the containerHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getContainerHandle() {
        return containerHandle;
    }

    /**
     * Sets the value of the containerHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setContainerHandle(BeeId value) {
        this.containerHandle = value;
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
     * Gets the value of the schemaHandle property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getSchemaHandle() {
        return schemaHandle;
    }

    /**
     * Sets the value of the schemaHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setSchemaHandle(BeeId value) {
        this.schemaHandle = value;
    }

}
