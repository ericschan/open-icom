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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for heterogeneousFolder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="heterogeneousFolder">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}folder">
 *       &lt;sequence>
 *         &lt;element name="elements" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="lastModifiedByName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locks" type="{http://www.oracle.com/beehive}lock" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://www.oracle.com/beehive}pendingWorkflowStatus" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="subFolders" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="viewCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "heterogeneousFolder", propOrder = {
    "elements",
    "lastModifiedByName",
    "locks",
    "path",
    "pendingWorkflowStatus",
    "size",
    "subFolders",
    "viewCount"
})
@XmlRootElement(name = "heterogeneousFolder")
public class HeterogeneousFolder
    extends Folder
{

    protected ListResult elements;
    protected String lastModifiedByName;
    @XmlElement(nillable = true)
    protected List<Lock> locks;
    protected String path;
    protected PendingWorkflowStatus pendingWorkflowStatus;
    protected long size;
    protected ListResult subFolders;
    protected int viewCount;

    /**
     * Gets the value of the elements property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getElements() {
        return elements;
    }

    /**
     * Sets the value of the elements property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setElements(ListResult value) {
        this.elements = value;
    }

    /**
     * Gets the value of the lastModifiedByName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    /**
     * Sets the value of the lastModifiedByName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifiedByName(String value) {
        this.lastModifiedByName = value;
    }

    /**
     * Gets the value of the locks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Lock }
     * 
     * 
     */
    public List<Lock> getLocks() {
        if (locks == null) {
            locks = new ArrayList<Lock>();
        }
        return this.locks;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the pendingWorkflowStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PendingWorkflowStatus }
     *     
     */
    public PendingWorkflowStatus getPendingWorkflowStatus() {
        return pendingWorkflowStatus;
    }

    /**
     * Sets the value of the pendingWorkflowStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PendingWorkflowStatus }
     *     
     */
    public void setPendingWorkflowStatus(PendingWorkflowStatus value) {
        this.pendingWorkflowStatus = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(long value) {
        this.size = value;
    }

    /**
     * Gets the value of the subFolders property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getSubFolders() {
        return subFolders;
    }

    /**
     * Sets the value of the subFolders property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setSubFolders(ListResult value) {
        this.subFolders = value;
    }

    /**
     * Gets the value of the viewCount property.
     * 
     */
    public int getViewCount() {
        return viewCount;
    }

    /**
     * Sets the value of the viewCount property.
     * 
     */
    public void setViewCount(int value) {
        this.viewCount = value;
    }

}
