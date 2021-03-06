//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeTemplateListUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributeTemplateListUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}identifiableUpdater">
 *       &lt;sequence>
 *         &lt;element name="add" type="{http://www.oracle.com/beehive}attributeTemplateCreateParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="addWithDefinitionName" type="{http://www.oracle.com/beehive}attributeTemplateCreateParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="remove" type="{http://www.oracle.com/beehive}beeId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="update" type="{http://www.oracle.com/beehive}attributeTemplateUpdateParameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributeTemplateListUpdater", propOrder = {
    "adds",
    "addWithDefinitionNames",
    "removes",
    "updates"
})
public class AttributeTemplateListUpdater
    extends IdentifiableUpdater
{

    @XmlElement(name = "add")
    protected List<AttributeTemplateCreateParameter> adds;
    @XmlElement(name = "addWithDefinitionName")
    protected List<AttributeTemplateCreateParameter> addWithDefinitionNames;
    @XmlElement(name = "remove")
    protected List<BeeId> removes;
    @XmlElement(name = "update")
    protected List<AttributeTemplateUpdateParameter> updates;

    /**
     * Gets the value of the adds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeTemplateCreateParameter }
     * 
     * 
     */
    public List<AttributeTemplateCreateParameter> getAdds() {
        if (adds == null) {
            adds = new ArrayList<AttributeTemplateCreateParameter>();
        }
        return this.adds;
    }

    /**
     * Gets the value of the addWithDefinitionNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addWithDefinitionNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddWithDefinitionNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeTemplateCreateParameter }
     * 
     * 
     */
    public List<AttributeTemplateCreateParameter> getAddWithDefinitionNames() {
        if (addWithDefinitionNames == null) {
            addWithDefinitionNames = new ArrayList<AttributeTemplateCreateParameter>();
        }
        return this.addWithDefinitionNames;
    }

    /**
     * Gets the value of the removes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeeId }
     * 
     * 
     */
    public List<BeeId> getRemoves() {
        if (removes == null) {
            removes = new ArrayList<BeeId>();
        }
        return this.removes;
    }

    /**
     * Gets the value of the updates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeTemplateUpdateParameter }
     * 
     * 
     */
    public List<AttributeTemplateUpdateParameter> getUpdates() {
        if (updates == null) {
            updates = new ArrayList<AttributeTemplateUpdateParameter>();
        }
        return this.updates;
    }

}
