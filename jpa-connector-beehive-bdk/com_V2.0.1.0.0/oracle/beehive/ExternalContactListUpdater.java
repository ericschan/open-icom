//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for externalContactListUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="externalContactListUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="externalContactsToAdd" type="{http://www.oracle.com/beehive}externalContactUpdater" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="externalContactsToRemove" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "externalContactListUpdater", propOrder = {
    "externalContactsToAdds",
    "externalContactsToRemoves"
})
public class ExternalContactListUpdater
    extends IdentifiableUpdater
{

    @XmlElement(name = "externalContactsToAdd")
    protected List<ExternalContactUpdater> externalContactsToAdds;
    @XmlElement(name = "externalContactsToRemove")
    protected List<BeeId> externalContactsToRemoves;

    /**
     * Gets the value of the externalContactsToAdds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalContactsToAdds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalContactsToAdds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalContactUpdater }
     * 
     * 
     */
    public List<ExternalContactUpdater> getExternalContactsToAdds() {
        if (externalContactsToAdds == null) {
            externalContactsToAdds = new ArrayList<ExternalContactUpdater>();
        }
        return this.externalContactsToAdds;
    }

    /**
     * Gets the value of the externalContactsToRemoves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalContactsToRemoves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalContactsToRemoves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getExternalContactsToRemoves() {
        if (externalContactsToRemoves == null) {
            externalContactsToRemoves = new ArrayList<BeeId>();
        }
        return this.externalContactsToRemoves;
    }

}
