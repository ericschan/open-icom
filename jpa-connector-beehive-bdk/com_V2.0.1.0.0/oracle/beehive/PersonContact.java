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
 * <p>Java class for personContact complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personContact">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}addressBookElement">
 *       &lt;sequence>
 *         &lt;element name="bookmark" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="company" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="familyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="familyNames" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="givenName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="givenNames" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="jobTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobTitles" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleNames" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="names" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nicknames" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="officeLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prefixes" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="profession" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffixes" type="{http://www.oracle.com/beehive}lString" minOccurs="0"/>
 *         &lt;element name="timeZone" type="{http://www.oracle.com/beehive}timeZone" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personContact", propOrder = {
    "bookmark",
    "company",
    "department",
    "familyName",
    "familyNames",
    "givenName",
    "givenNames",
    "jobTitle",
    "jobTitles",
    "locale",
    "middleName",
    "middleNames",
    "names",
    "nickname",
    "nicknames",
    "officeLocation",
    "prefix",
    "prefixes",
    "profession",
    "suffix",
    "suffixes",
    "timeZone"
})
@XmlRootElement(name = "personContact")
public class PersonContact
    extends AddressBookElement
{

    protected Object bookmark;
    protected String company;
    protected String department;
    protected String familyName;
    protected LString familyNames;
    protected String givenName;
    protected LString givenNames;
    protected String jobTitle;
    protected LString jobTitles;
    protected String locale;
    protected String middleName;
    protected LString middleNames;
    protected LString names;
    protected String nickname;
    protected LString nicknames;
    protected String officeLocation;
    protected String prefix;
    protected LString prefixes;
    protected String profession;
    protected String suffix;
    protected LString suffixes;
    protected TimeZone timeZone;

    /**
     * Gets the value of the bookmark property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBookmark() {
        return bookmark;
    }

    /**
     * Sets the value of the bookmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBookmark(Object value) {
        this.bookmark = value;
    }

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
     * Gets the value of the familyNames property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getFamilyNames() {
        return familyNames;
    }

    /**
     * Sets the value of the familyNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setFamilyNames(LString value) {
        this.familyNames = value;
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
     * Gets the value of the givenNames property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getGivenNames() {
        return givenNames;
    }

    /**
     * Sets the value of the givenNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setGivenNames(LString value) {
        this.givenNames = value;
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
     * Gets the value of the jobTitles property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getJobTitles() {
        return jobTitles;
    }

    /**
     * Sets the value of the jobTitles property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setJobTitles(LString value) {
        this.jobTitles = value;
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
     * Gets the value of the middleNames property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getMiddleNames() {
        return middleNames;
    }

    /**
     * Sets the value of the middleNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setMiddleNames(LString value) {
        this.middleNames = value;
    }

    /**
     * Gets the value of the names property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setNames(LString value) {
        this.names = value;
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
     * Gets the value of the nicknames property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getNicknames() {
        return nicknames;
    }

    /**
     * Sets the value of the nicknames property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setNicknames(LString value) {
        this.nicknames = value;
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
     * Gets the value of the prefixes property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getPrefixes() {
        return prefixes;
    }

    /**
     * Sets the value of the prefixes property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setPrefixes(LString value) {
        this.prefixes = value;
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
     * Gets the value of the suffixes property.
     * 
     * @return
     *     possible object is
     *     {@link LString }
     *     
     */
    public LString getSuffixes() {
        return suffixes;
    }

    /**
     * Sets the value of the suffixes property.
     * 
     * @param value
     *     allowed object is
     *     {@link LString }
     *     
     */
    public void setSuffixes(LString value) {
        this.suffixes = value;
    }

    /**
     * Gets the value of the timeZone property.
     * 
     * @return
     *     possible object is
     *     {@link TimeZone }
     *     
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the value of the timeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeZone }
     *     
     */
    public void setTimeZone(TimeZone value) {
        this.timeZone = value;
    }

}