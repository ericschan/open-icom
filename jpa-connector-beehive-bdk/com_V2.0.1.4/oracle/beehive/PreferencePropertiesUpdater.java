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
 * <p>Java class for preferencePropertiesUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="preferencePropertiesUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="propertiesToAdd" type="{http://www.oracle.com/beehive}preferenceProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="propertiesToRemove" type="{http://www.oracle.com/beehive}preferenceProperty" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preferencePropertiesUpdater", propOrder = {
    "propertiesToAdds",
    "propertiesToRemoves"
})
public class PreferencePropertiesUpdater
    extends IdentifiableUpdater
{

    @XmlElement(name = "propertiesToAdd")
    protected List<PreferenceProperty> propertiesToAdds;
    @XmlElement(name = "propertiesToRemove")
    protected List<PreferenceProperty> propertiesToRemoves;

    /**
     * Gets the value of the propertiesToAdds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertiesToAdds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertiesToAdds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreferenceProperty }
     * 
     * 
     */
    public List<PreferenceProperty> getPropertiesToAdds() {
        if (propertiesToAdds == null) {
            propertiesToAdds = new ArrayList<PreferenceProperty>();
        }
        return this.propertiesToAdds;
    }

    /**
     * Gets the value of the propertiesToRemoves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertiesToRemoves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertiesToRemoves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreferenceProperty }
     * 
     * 
     */
    public List<PreferenceProperty> getPropertiesToRemoves() {
        if (propertiesToRemoves == null) {
            propertiesToRemoves = new ArrayList<PreferenceProperty>();
        }
        return this.propertiesToRemoves;
    }

}
