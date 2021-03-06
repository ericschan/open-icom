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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contactMethod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="contactMethod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activeConnectionIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="creationTimestamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="note" type="{http://www.oracle.com/beehive}note" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="status" type="{http://www.oracle.com/beehive}contactReachabilityStatus" minOccurs="0"/>
 *         &lt;element name="statusTimestamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="URI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uriScheme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactMethod", propOrder = {
    "activeConnectionIds",
    "creationTimestamp",
    "note",
    "priority",
    "status",
    "statusTimestamp",
    "uri",
    "uriScheme"
})
public class ContactMethod {

    protected List<String> activeConnectionIds;
    protected long creationTimestamp;
    protected Note note;
    protected int priority;
    protected ContactReachabilityStatus status;
    protected long statusTimestamp;
    @XmlElement(name = "URI")
    protected String uri;
    protected String uriScheme;

    /**
     * Gets the value of the activeConnectionIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activeConnectionIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActiveConnectionIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getActiveConnectionIds() {
        if (activeConnectionIds == null) {
            activeConnectionIds = new ArrayList<String>();
        }
        return this.activeConnectionIds;
    }

    /**
     * Gets the value of the creationTimestamp property.
     * 
     */
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Sets the value of the creationTimestamp property.
     * 
     */
    public void setCreationTimestamp(long value) {
        this.creationTimestamp = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setNote(Note value) {
        this.note = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     */
    public void setPriority(int value) {
        this.priority = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ContactReachabilityStatus }
     *     
     */
    public ContactReachabilityStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactReachabilityStatus }
     *     
     */
    public void setStatus(ContactReachabilityStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the statusTimestamp property.
     * 
     */
    public long getStatusTimestamp() {
        return statusTimestamp;
    }

    /**
     * Sets the value of the statusTimestamp property.
     * 
     */
    public void setStatusTimestamp(long value) {
        this.statusTimestamp = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURI() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURI(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the uriScheme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUriScheme() {
        return uriScheme;
    }

    /**
     * Sets the value of the uriScheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUriScheme(String value) {
        this.uriScheme = value;
    }

}
