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
 * <p>Java class for personContactCreator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personContactCreator">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityCreator">
 *       &lt;sequence>
 *         &lt;element name="addressBook" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="bookmark" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="updater" type="{http://www.oracle.com/beehive}personContactUpdater" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personContactCreator", propOrder = {
    "addressBook",
    "bookmark",
    "updater"
})
@XmlRootElement(name = "personContactCreator")
public class PersonContactCreator
    extends EntityCreator
{

    protected BeeId addressBook;
    protected BeeId bookmark;
    protected PersonContactUpdater updater;

    /**
     * Gets the value of the addressBook property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getAddressBook() {
        return addressBook;
    }

    /**
     * Sets the value of the addressBook property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setAddressBook(BeeId value) {
        this.addressBook = value;
    }

    /**
     * Gets the value of the bookmark property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getBookmark() {
        return bookmark;
    }

    /**
     * Sets the value of the bookmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setBookmark(BeeId value) {
        this.bookmark = value;
    }

    /**
     * Gets the value of the updater property.
     * 
     * @return
     *     possible object is
     *     {@link PersonContactUpdater }
     *     
     */
    public PersonContactUpdater getUpdater() {
        return updater;
    }

    /**
     * Sets the value of the updater property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonContactUpdater }
     *     
     */
    public void setUpdater(PersonContactUpdater value) {
        this.updater = value;
    }

}
