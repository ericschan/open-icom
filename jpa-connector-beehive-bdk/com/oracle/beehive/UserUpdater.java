//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.08 at 05:31:42 PM PST 
//


package com.oracle.beehive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userUpdater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userUpdater">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}actorUpdater">
 *       &lt;sequence>
 *         &lt;element name="company" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creationStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DSTimeZone" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="datasourceIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datasourceUserIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extendedEnterpriseUser" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="externalInbox" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="familyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="familyNameUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="givenName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="givenNameUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="jobTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobTitleUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleNameUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="nameUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nicknameUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="officeLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="personalWorkspace" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *         &lt;element name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prefixUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="profession" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffixUpdater" type="{http://www.oracle.com/beehive}lStringUpdater" minOccurs="0"/>
 *         &lt;element name="timeZone" type="{http://www.oracle.com/beehive}beeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userUpdater", propOrder = {
    "company",
    "creationStatus",
    "dsTimeZone",
    "datasourceIdentifier",
    "datasourceUserIdentifier",
    "department",
    "extendedEnterpriseUser",
    "externalInbox",
    "familyName",
    "familyNameUpdater",
    "firstName",
    "givenName",
    "givenNameUpdater",
    "jobTitle",
    "jobTitleUpdater",
    "lastName",
    "locale",
    "middleName",
    "middleNameUpdater",
    "nameUpdater",
    "nickname",
    "nicknameUpdater",
    "officeLocation",
    "parent",
    "personalWorkspace",
    "prefix",
    "prefixUpdater",
    "profession",
    "suffix",
    "suffixUpdater",
    "timeZone"
})
@XmlSeeAlso({
    OrganizationUserUpdater.class
})
public abstract class UserUpdater
    extends ActorUpdater
{

    protected String company;
    protected String creationStatus;
    @XmlElement(name = "DSTimeZone")
    protected BeeId dsTimeZone;
    protected String datasourceIdentifier;
    protected String datasourceUserIdentifier;
    protected String department;
    protected Boolean extendedEnterpriseUser;
    protected Boolean externalInbox;
    protected String familyName;
    protected LStringUpdater familyNameUpdater;
    protected String firstName;
    protected String givenName;
    protected LStringUpdater givenNameUpdater;
    protected String jobTitle;
    protected LStringUpdater jobTitleUpdater;
    protected String lastName;
    protected String locale;
    protected String middleName;
    protected LStringUpdater middleNameUpdater;
    protected LStringUpdater nameUpdater;
    protected String nickname;
    protected LStringUpdater nicknameUpdater;
    protected String officeLocation;
    protected BeeId parent;
    protected BeeId personalWorkspace;
    protected String prefix;
    protected LStringUpdater prefixUpdater;
    protected String profession;
    protected String suffix;
    protected LStringUpdater suffixUpdater;
    protected BeeId timeZone;

    /**
     * Gets the value of the company property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the value of the company property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompany(String value) {
        this.company = value;
    }

    /**
     * Gets the value of the creationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationStatus() {
        return creationStatus;
    }

    /**
     * Sets the value of the creationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationStatus(String value) {
        this.creationStatus = value;
    }

    /**
     * Gets the value of the dsTimeZone property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getDSTimeZone() {
        return dsTimeZone;
    }

    /**
     * Sets the value of the dsTimeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setDSTimeZone(BeeId value) {
        this.dsTimeZone = value;
    }

    /**
     * Gets the value of the datasourceIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasourceIdentifier() {
        return datasourceIdentifier;
    }

    /**
     * Sets the value of the datasourceIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasourceIdentifier(String value) {
        this.datasourceIdentifier = value;
    }

    /**
     * Gets the value of the datasourceUserIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasourceUserIdentifier() {
        return datasourceUserIdentifier;
    }

    /**
     * Sets the value of the datasourceUserIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasourceUserIdentifier(String value) {
        this.datasourceUserIdentifier = value;
    }

    /**
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * Gets the value of the extendedEnterpriseUser property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExtendedEnterpriseUser() {
        return extendedEnterpriseUser;
    }

    /**
     * Sets the value of the extendedEnterpriseUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExtendedEnterpriseUser(Boolean value) {
        this.extendedEnterpriseUser = value;
    }

    /**
     * Gets the value of the externalInbox property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExternalInbox() {
        return externalInbox;
    }

    /**
     * Sets the value of the externalInbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExternalInbox(Boolean value) {
        this.externalInbox = value;
    }

    /**
     * Gets the value of the familyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the value of the familyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * Gets the value of the familyNameUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getFamilyNameUpdater() {
        return familyNameUpdater;
    }

    /**
     * Sets the value of the familyNameUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setFamilyNameUpdater(LStringUpdater value) {
        this.familyNameUpdater = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the givenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the givenNameUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getGivenNameUpdater() {
        return givenNameUpdater;
    }

    /**
     * Sets the value of the givenNameUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setGivenNameUpdater(LStringUpdater value) {
        this.givenNameUpdater = value;
    }

    /**
     * Gets the value of the jobTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the value of the jobTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    /**
     * Gets the value of the jobTitleUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getJobTitleUpdater() {
        return jobTitleUpdater;
    }

    /**
     * Sets the value of the jobTitleUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setJobTitleUpdater(LStringUpdater value) {
        this.jobTitleUpdater = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the middleNameUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getMiddleNameUpdater() {
        return middleNameUpdater;
    }

    /**
     * Sets the value of the middleNameUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setMiddleNameUpdater(LStringUpdater value) {
        this.middleNameUpdater = value;
    }

    /**
     * Gets the value of the nameUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getNameUpdater() {
        return nameUpdater;
    }

    /**
     * Sets the value of the nameUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setNameUpdater(LStringUpdater value) {
        this.nameUpdater = value;
    }

    /**
     * Gets the value of the nickname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the value of the nickname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Gets the value of the nicknameUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getNicknameUpdater() {
        return nicknameUpdater;
    }

    /**
     * Sets the value of the nicknameUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setNicknameUpdater(LStringUpdater value) {
        this.nicknameUpdater = value;
    }

    /**
     * Gets the value of the officeLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeLocation() {
        return officeLocation;
    }

    /**
     * Sets the value of the officeLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeLocation(String value) {
        this.officeLocation = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setParent(BeeId value) {
        this.parent = value;
    }

    /**
     * Gets the value of the personalWorkspace property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getPersonalWorkspace() {
        return personalWorkspace;
    }

    /**
     * Sets the value of the personalWorkspace property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setPersonalWorkspace(BeeId value) {
        this.personalWorkspace = value;
    }

    /**
     * Gets the value of the prefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

    /**
     * Gets the value of the prefixUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getPrefixUpdater() {
        return prefixUpdater;
    }

    /**
     * Sets the value of the prefixUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setPrefixUpdater(LStringUpdater value) {
        this.prefixUpdater = value;
    }

    /**
     * Gets the value of the profession property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfession() {
        return profession;
    }

    /**
     * Sets the value of the profession property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfession(String value) {
        this.profession = value;
    }

    /**
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
    }

    /**
     * Gets the value of the suffixUpdater property.
     * 
     * @return
     *     possible object is
     *     {@link LStringUpdater }
     *     
     */
    public LStringUpdater getSuffixUpdater() {
        return suffixUpdater;
    }

    /**
     * Sets the value of the suffixUpdater property.
     * 
     * @param value
     *     allowed object is
     *     {@link LStringUpdater }
     *     
     */
    public void setSuffixUpdater(LStringUpdater value) {
        this.suffixUpdater = value;
    }

    /**
     * Gets the value of the timeZone property.
     * 
     * @return
     *     possible object is
     *     {@link BeeId }
     *     
     */
    public BeeId getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the value of the timeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeeId }
     *     
     */
    public void setTimeZone(BeeId value) {
        this.timeZone = value;
    }

}
