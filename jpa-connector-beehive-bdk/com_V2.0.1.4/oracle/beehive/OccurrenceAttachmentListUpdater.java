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
 * <p>Java class for occurrenceAttachmentListUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="occurrenceAttachmentListUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}baseOccurrenceAttachmentListUpdater">
 *       &lt;sequence>
 *         &lt;element name="seriesAttachmentsToAdd" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="seriesAttachmentsToRemove" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "occurrenceAttachmentListUpdater", propOrder = {
    "seriesAttachmentsToAdds",
    "seriesAttachmentsToRemoves"
})
public class OccurrenceAttachmentListUpdater
    extends BaseOccurrenceAttachmentListUpdater
{

    @XmlElement(name = "seriesAttachmentsToAdd")
    protected List<String> seriesAttachmentsToAdds;
    @XmlElement(name = "seriesAttachmentsToRemove")
    protected List<String> seriesAttachmentsToRemoves;

    /**
     * Gets the value of the seriesAttachmentsToAdds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the seriesAttachmentsToAdds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeriesAttachmentsToAdds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSeriesAttachmentsToAdds() {
        if (seriesAttachmentsToAdds == null) {
            seriesAttachmentsToAdds = new ArrayList<String>();
        }
        return this.seriesAttachmentsToAdds;
    }

    /**
     * Gets the value of the seriesAttachmentsToRemoves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the seriesAttachmentsToRemoves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeriesAttachmentsToRemoves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSeriesAttachmentsToRemoves() {
        if (seriesAttachmentsToRemoves == null) {
            seriesAttachmentsToRemoves = new ArrayList<String>();
        }
        return this.seriesAttachmentsToRemoves;
    }

}
