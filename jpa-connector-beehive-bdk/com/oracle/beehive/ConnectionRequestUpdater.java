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
 * <p>Java class for connectionRequestUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="connectionRequestUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}entityUpdater">
 *       &lt;sequence>
 *         &lt;element name="requestState" type="{http://www.oracle.com/beehive}connectionRequestState" minOccurs="0"/>
 *         &lt;element name="resolutionStatus" type="{http://www.oracle.com/beehive}connectionRequestResolutionStatus" minOccurs="0"/>
 *         &lt;element name="responseState" type="{http://www.oracle.com/beehive}connectionRequestResponseState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "connectionRequestUpdater", propOrder = {
    "requestState",
    "resolutionStatus",
    "responseState"
})
public class ConnectionRequestUpdater
    extends EntityUpdater
{

    protected ConnectionRequestState requestState;
    protected ConnectionRequestResolutionStatus resolutionStatus;
    protected ConnectionRequestResponseState responseState;

    /**
     * Gets the value of the requestState property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionRequestState }
     *     
     */
    public ConnectionRequestState getRequestState() {
        return requestState;
    }

    /**
     * Sets the value of the requestState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionRequestState }
     *     
     */
    public void setRequestState(ConnectionRequestState value) {
        this.requestState = value;
    }

    /**
     * Gets the value of the resolutionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionRequestResolutionStatus }
     *     
     */
    public ConnectionRequestResolutionStatus getResolutionStatus() {
        return resolutionStatus;
    }

    /**
     * Sets the value of the resolutionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionRequestResolutionStatus }
     *     
     */
    public void setResolutionStatus(ConnectionRequestResolutionStatus value) {
        this.resolutionStatus = value;
    }

    /**
     * Gets the value of the responseState property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionRequestResponseState }
     *     
     */
    public ConnectionRequestResponseState getResponseState() {
        return responseState;
    }

    /**
     * Sets the value of the responseState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionRequestResponseState }
     *     
     */
    public void setResponseState(ConnectionRequestResponseState value) {
        this.responseState = value;
    }

}
