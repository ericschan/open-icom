//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.18 at 04:52:34 PM PDT 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searchListParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searchListParameters">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}paginatedPredicateAndSortListParameters">
 *       &lt;sequence>
 *         &lt;element name="includeEstimatedHitsCount" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="projection" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="snippetCountLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchListParameters", propOrder = {
    "includeEstimatedHitsCount",
    "projection",
    "snippetCountLimit"
})
@XmlRootElement(name = "searchListParameters")
public class SearchListParameters
    extends PaginatedPredicateAndSortListParameters
{

    protected boolean includeEstimatedHitsCount;
    protected Object projection;
    protected Integer snippetCountLimit;

    /**
     * Gets the value of the includeEstimatedHitsCount property.
     * 
     */
    public boolean isIncludeEstimatedHitsCount() {
        return includeEstimatedHitsCount;
    }

    /**
     * Sets the value of the includeEstimatedHitsCount property.
     * 
     */
    public void setIncludeEstimatedHitsCount(boolean value) {
        this.includeEstimatedHitsCount = value;
    }

    /**
     * Gets the value of the projection property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getProjection() {
        return projection;
    }

    /**
     * Sets the value of the projection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setProjection(Object value) {
        this.projection = value;
    }

    /**
     * Gets the value of the snippetCountLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSnippetCountLimit() {
        return snippetCountLimit;
    }

    /**
     * Sets the value of the snippetCountLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSnippetCountLimit(Integer value) {
        this.snippetCountLimit = value;
    }

}
