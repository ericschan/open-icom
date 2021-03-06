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
 * <p>Java class for compoundSortCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="compoundSortCriteria">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}sortCriteria">
 *       &lt;sequence>
 *         &lt;element name="sortCriteriaList" type="{http://www.oracle.com/beehive}sortCriteria" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compoundSortCriteria", propOrder = {
    "sortCriteriaLists"
})
public class CompoundSortCriteria
    extends SortCriteria
{

    @XmlElement(name = "sortCriteriaList")
    protected List<SortCriteria> sortCriteriaLists;

    /**
     * Gets the value of the sortCriteriaLists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortCriteriaLists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortCriteriaLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SortCriteria }
     * 
     * 
     */
    public List<SortCriteria> getSortCriteriaLists() {
        if (sortCriteriaLists == null) {
            sortCriteriaLists = new ArrayList<SortCriteria>();
        }
        return this.sortCriteriaLists;
    }

}
