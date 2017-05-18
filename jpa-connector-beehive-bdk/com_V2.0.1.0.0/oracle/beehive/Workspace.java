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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for workspace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workspace">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.oracle.com/beehive}scope">
 *       &lt;sequence>
 *         &lt;element name="defaultAddressBook" type="{http://www.oracle.com/beehive}addressBook" minOccurs="0"/>
 *         &lt;element name="defaultCalendar" type="{http://www.oracle.com/beehive}calendar" minOccurs="0"/>
 *         &lt;element name="defaultConference" type="{http://www.oracle.com/beehive}conference" minOccurs="0"/>
 *         &lt;element name="defaultDocumentsFolder" type="{http://www.oracle.com/beehive}heterogeneousFolder" minOccurs="0"/>
 *         &lt;element name="defaultSensitivity" type="{http://www.oracle.com/beehive}sensitivity" minOccurs="0"/>
 *         &lt;element name="defaultTaskList" type="{http://www.oracle.com/beehive}taskList" minOccurs="0"/>
 *         &lt;element name="defaultWikiFolder" type="{http://www.oracle.com/beehive}heterogeneousFolder" minOccurs="0"/>
 *         &lt;element name="defaultWikiPage" type="{http://www.oracle.com/beehive}wikiPage" minOccurs="0"/>
 *         &lt;element name="elements" type="{http://www.oracle.com/beehive}listResult" minOccurs="0"/>
 *         &lt;element name="inbox" type="{http://www.oracle.com/beehive}heterogeneousFolder" minOccurs="0"/>
 *         &lt;element name="lastModifiedByName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locks" type="{http://www.oracle.com/beehive}lock" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="logoURI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryContact" type="{http://www.oracle.com/beehive}accessor" minOccurs="0"/>
 *         &lt;element name="publicSummaryURI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quotaStatus" type="{http://www.oracle.com/beehive}quotaStatus" minOccurs="0"/>
 *         &lt;element name="shortWorkspaceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="summaryURI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trash" type="{http://www.oracle.com/beehive}trash" minOccurs="0"/>
 *         &lt;element name="workspaceOwner" type="{http://www.oracle.com/beehive}accessor" minOccurs="0"/>
 *         &lt;element name="workspaceTemplate" type="{http://www.oracle.com/beehive}workspaceTemplate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workspace", propOrder = {
    "defaultAddressBook",
    "defaultCalendar",
    "defaultConference",
    "defaultDocumentsFolder",
    "defaultSensitivity",
    "defaultTaskList",
    "defaultWikiFolder",
    "defaultWikiPage",
    "elements",
    "inbox",
    "lastModifiedByName",
    "locks",
    "logoURI",
    "path",
    "primaryContact",
    "publicSummaryURI",
    "quotaStatus",
    "shortWorkspaceId",
    "summaryURI",
    "trash",
    "workspaceOwner",
    "workspaceTemplate"
})
@XmlSeeAlso({
    TeamWorkspace.class,
    PersonalWorkspace.class
})
public abstract class Workspace
    extends Scope
{

    protected AddressBook defaultAddressBook;
    protected Calendar defaultCalendar;
    protected Conference defaultConference;
    protected HeterogeneousFolder defaultDocumentsFolder;
    protected Sensitivity defaultSensitivity;
    protected TaskList defaultTaskList;
    protected HeterogeneousFolder defaultWikiFolder;
    protected WikiPage defaultWikiPage;
    protected ListResult elements;
    protected HeterogeneousFolder inbox;
    protected String lastModifiedByName;
    @XmlElement(nillable = true)
    protected List<Lock> locks;
    protected String logoURI;
    protected String path;
    protected Accessor primaryContact;
    protected String publicSummaryURI;
    protected QuotaStatus quotaStatus;
    protected int shortWorkspaceId;
    protected String summaryURI;
    protected Trash trash;
    protected Accessor workspaceOwner;
    protected WorkspaceTemplate workspaceTemplate;

    /**
     * Gets the value of the defaultAddressBook property.
     * 
     * @return
     *     possible object is
     *     {@link AddressBook }
     *     
     */
    public AddressBook getDefaultAddressBook() {
        return defaultAddressBook;
    }

    /**
     * Sets the value of the defaultAddressBook property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressBook }
     *     
     */
    public void setDefaultAddressBook(AddressBook value) {
        this.defaultAddressBook = value;
    }

    /**
     * Gets the value of the defaultCalendar property.
     * 
     * @return
     *     possible object is
     *     {@link Calendar }
     *     
     */
    public Calendar getDefaultCalendar() {
        return defaultCalendar;
    }

    /**
     * Sets the value of the defaultCalendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calendar }
     *     
     */
    public void setDefaultCalendar(Calendar value) {
        this.defaultCalendar = value;
    }

    /**
     * Gets the value of the defaultConference property.
     * 
     * @return
     *     possible object is
     *     {@link Conference }
     *     
     */
    public Conference getDefaultConference() {
        return defaultConference;
    }

    /**
     * Sets the value of the defaultConference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Conference }
     *     
     */
    public void setDefaultConference(Conference value) {
        this.defaultConference = value;
    }

    /**
     * Gets the value of the defaultDocumentsFolder property.
     * 
     * @return
     *     possible object is
     *     {@link HeterogeneousFolder }
     *     
     */
    public HeterogeneousFolder getDefaultDocumentsFolder() {
        return defaultDocumentsFolder;
    }

    /**
     * Sets the value of the defaultDocumentsFolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeterogeneousFolder }
     *     
     */
    public void setDefaultDocumentsFolder(HeterogeneousFolder value) {
        this.defaultDocumentsFolder = value;
    }

    /**
     * Gets the value of the defaultSensitivity property.
     * 
     * @return
     *     possible object is
     *     {@link Sensitivity }
     *     
     */
    public Sensitivity getDefaultSensitivity() {
        return defaultSensitivity;
    }

    /**
     * Sets the value of the defaultSensitivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sensitivity }
     *     
     */
    public void setDefaultSensitivity(Sensitivity value) {
        this.defaultSensitivity = value;
    }

    /**
     * Gets the value of the defaultTaskList property.
     * 
     * @return
     *     possible object is
     *     {@link TaskList }
     *     
     */
    public TaskList getDefaultTaskList() {
        return defaultTaskList;
    }

    /**
     * Sets the value of the defaultTaskList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskList }
     *     
     */
    public void setDefaultTaskList(TaskList value) {
        this.defaultTaskList = value;
    }

    /**
     * Gets the value of the defaultWikiFolder property.
     * 
     * @return
     *     possible object is
     *     {@link HeterogeneousFolder }
     *     
     */
    public HeterogeneousFolder getDefaultWikiFolder() {
        return defaultWikiFolder;
    }

    /**
     * Sets the value of the defaultWikiFolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeterogeneousFolder }
     *     
     */
    public void setDefaultWikiFolder(HeterogeneousFolder value) {
        this.defaultWikiFolder = value;
    }

    /**
     * Gets the value of the defaultWikiPage property.
     * 
     * @return
     *     possible object is
     *     {@link WikiPage }
     *     
     */
    public WikiPage getDefaultWikiPage() {
        return defaultWikiPage;
    }

    /**
     * Sets the value of the defaultWikiPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link WikiPage }
     *     
     */
    public void setDefaultWikiPage(WikiPage value) {
        this.defaultWikiPage = value;
    }

    /**
     * Gets the value of the elements property.
     * 
     * @return
     *     possible object is
     *     {@link ListResult }
     *     
     */
    public ListResult getElements() {
        return elements;
    }

    /**
     * Sets the value of the elements property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListResult }
     *     
     */
    public void setElements(ListResult value) {
        this.elements = value;
    }

    /**
     * Gets the value of the inbox property.
     * 
     * @return
     *     possible object is
     *     {@link HeterogeneousFolder }
     *     
     */
    public HeterogeneousFolder getInbox() {
        return inbox;
    }

    /**
     * Sets the value of the inbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeterogeneousFolder }
     *     
     */
    public void setInbox(HeterogeneousFolder value) {
        this.inbox = value;
    }

    /**
     * Gets the value of the lastModifiedByName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    /**
     * Sets the value of the lastModifiedByName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifiedByName(String value) {
        this.lastModifiedByName = value;
    }

    /**
     * Gets the value of the locks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Lock }
     * 
     * 
     */
    public List<Lock> getLocks() {
        if (locks == null) {
            locks = new ArrayList<Lock>();
        }
        return this.locks;
    }

    /**
     * Gets the value of the logoURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoURI() {
        return logoURI;
    }

    /**
     * Sets the value of the logoURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoURI(String value) {
        this.logoURI = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the primaryContact property.
     * 
     * @return
     *     possible object is
     *     {@link Accessor }
     *     
     */
    public Accessor getPrimaryContact() {
        return primaryContact;
    }

    /**
     * Sets the value of the primaryContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Accessor }
     *     
     */
    public void setPrimaryContact(Accessor value) {
        this.primaryContact = value;
    }

    /**
     * Gets the value of the publicSummaryURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicSummaryURI() {
        return publicSummaryURI;
    }

    /**
     * Sets the value of the publicSummaryURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicSummaryURI(String value) {
        this.publicSummaryURI = value;
    }

    /**
     * Gets the value of the quotaStatus property.
     * 
     * @return
     *     possible object is
     *     {@link QuotaStatus }
     *     
     */
    public QuotaStatus getQuotaStatus() {
        return quotaStatus;
    }

    /**
     * Sets the value of the quotaStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuotaStatus }
     *     
     */
    public void setQuotaStatus(QuotaStatus value) {
        this.quotaStatus = value;
    }

    /**
     * Gets the value of the shortWorkspaceId property.
     * 
     */
    public int getShortWorkspaceId() {
        return shortWorkspaceId;
    }

    /**
     * Sets the value of the shortWorkspaceId property.
     * 
     */
    public void setShortWorkspaceId(int value) {
        this.shortWorkspaceId = value;
    }

    /**
     * Gets the value of the summaryURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryURI() {
        return summaryURI;
    }

    /**
     * Sets the value of the summaryURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryURI(String value) {
        this.summaryURI = value;
    }

    /**
     * Gets the value of the trash property.
     * 
     * @return
     *     possible object is
     *     {@link Trash }
     *     
     */
    public Trash getTrash() {
        return trash;
    }

    /**
     * Sets the value of the trash property.
     * 
     * @param value
     *     allowed object is
     *     {@link Trash }
     *     
     */
    public void setTrash(Trash value) {
        this.trash = value;
    }

    /**
     * Gets the value of the workspaceOwner property.
     * 
     * @return
     *     possible object is
     *     {@link Accessor }
     *     
     */
    public Accessor getWorkspaceOwner() {
        return workspaceOwner;
    }

    /**
     * Sets the value of the workspaceOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link Accessor }
     *     
     */
    public void setWorkspaceOwner(Accessor value) {
        this.workspaceOwner = value;
    }

    /**
     * Gets the value of the workspaceTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link WorkspaceTemplate }
     *     
     */
    public WorkspaceTemplate getWorkspaceTemplate() {
        return workspaceTemplate;
    }

    /**
     * Sets the value of the workspaceTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkspaceTemplate }
     *     
     */
    public void setWorkspaceTemplate(WorkspaceTemplate value) {
        this.workspaceTemplate = value;
    }

}
