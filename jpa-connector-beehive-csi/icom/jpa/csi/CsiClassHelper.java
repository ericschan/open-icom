/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER
 * 
 * Copyright (c) 2010, Oracle Corporation All Rights Reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License ("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can obtain
 * a copy of the License at http://openjdk.java.net/legal/gplv2+ce.html.
 * See the License for the specific language governing permission and
 * limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at openICOM/bootstrap/legal/LICENSE.txt.
 * Oracle designates this particular file as subject to the "Classpath" exception
 * as provided by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [ ] replaced by your own
 * identifying information:  "Portions Copyrighted [year]
 * [name of copyright owner].
 *
 * Contributor(s): Oracle Corporation
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package icom.jpa.csi;

import icom.info.BeanHandler;
import icom.info.IcomBeanEnumeration;
import icom.info.UnifiedMessageInfo;
import icom.info.UnknownEntityInfo;
import icom.info.UnknownIdentifiableInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.Manageable;
import icom.jpa.csi.dao.AccessControlListToSetOfAceDAO;
import icom.jpa.csi.dao.AceDAO;
import icom.jpa.csi.dao.AttachmentDAO;
import icom.jpa.csi.dao.BdkFreeBusyDAO;
import icom.jpa.csi.dao.BdkMeetingDAO;
import icom.jpa.csi.dao.BdkMeetingSeriesDAO;
import icom.jpa.csi.dao.DocumentToSimpleContentDAO;
import icom.jpa.csi.dao.EmailMessageDAO;
import icom.jpa.csi.dao.EmailParticipantOrRecipientDAO;
import icom.jpa.csi.dao.EntityDAO;
import icom.jpa.csi.dao.GroupDAO;
import icom.jpa.csi.dao.LocationDAO;
import icom.jpa.csi.dao.MimeHeaderDAO;
import icom.jpa.csi.dao.PhysicalLocationDAO;
import icom.jpa.csi.dao.PropertyChoiceTypeToCollabPropertyDAO;
import icom.jpa.csi.dao.SimpleContentDAO;
import icom.jpa.csi.dao.UnknownEntityDAO;
import icom.jpa.csi.dao.UnknownIdentifiableDAO;
import icom.jpa.csi.dao.VersionSeriesToDocumentDAO;
import icom.jpa.dao.DataAccessClassHelper;
import icom.jpa.dao.ProviderClassPojoClassMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.PersistenceException;

import oracle.csi.ACE;
import oracle.csi.AddressBook;
import oracle.csi.AllUsersGroup;
import oracle.csi.AssignedRole;
import oracle.csi.AttributeApplication;
import oracle.csi.AttributeDefinition;
import oracle.csi.AttributeTemplate;
import oracle.csi.BdkFreeBusy;
import oracle.csi.BdkFreeBusyInterval;
import oracle.csi.BdkMeeting;
import oracle.csi.BdkMeetingParticipant;
import oracle.csi.BdkMeetingSeries;
import oracle.csi.BdkParticipantFreeBusy;
import oracle.csi.BdkTask;
import oracle.csi.Bond;
import oracle.csi.Category;
import oracle.csi.CategoryApplication;
import oracle.csi.CategoryConfiguration;
import oracle.csi.CategoryTemplate;
import oracle.csi.CollabProperty;
import oracle.csi.DiscussionsMessage;
import oracle.csi.Document;
import oracle.csi.EmailMessage;
import oracle.csi.EmailMessageContent;
import oracle.csi.EmailParticipant;
import oracle.csi.EmailRecipient;
import oracle.csi.Enterprise;
import oracle.csi.Group;
import oracle.csi.GroupContact;
import oracle.csi.HeterogeneousFolder;
import oracle.csi.IdentifiableSimpleContent;
import oracle.csi.Invitation;
import oracle.csi.InvitationSeries;
import oracle.csi.Label;
import oracle.csi.LabelApplication;
import oracle.csi.MimeHeader;
import oracle.csi.Organization;
import oracle.csi.OrganizationUser;
import oracle.csi.PersonContact;
import oracle.csi.PersonalWorkspace;
import oracle.csi.Principal;
import oracle.csi.ResourceContact;
import oracle.csi.RoleDefinition;
import oracle.csi.SimpleContent;
import oracle.csi.SystemActor;
import oracle.csi.TeamWorkspace;
import oracle.csi.Trash;
import oracle.csi.VersionControlConfiguration;

public class CsiClassHelper implements DataAccessClassHelper {
	
	static CsiClassHelper singleton = new CsiClassHelper();
	
	public static CsiClassHelper getInstance() {
		return singleton;
	}
	
	CsiClassHelper() {
		super();
	}
	
	static final String daoPackageName = EntityDAO.class.getPackage().getName();  // "icom.jpa.csi.dao";
	static final String icomBeanInfoPackageName = "icom.info";
	static final String beehiveBeanInfoPackageName = "icom.info.beehive";

	private static Map<String, String> csiToPojoMapper = new HashMap<String, String>();
	private static Map<String, String> pojoToDaoMapper = new HashMap<String, String>();
	
	private static HashSet<String> pojoIcomBeanNames = new HashSet<String>();
	private static HashSet<String> pojoBeehiveBeanNames = new HashSet<String>();
	
	static {
		csiToPojoMapper.put(ACE.class.getSimpleName(), IcomBeanEnumeration.AccessControlEntry.name());
		csiToPojoMapper.put(Bond.class.getSimpleName(), IcomBeanEnumeration.Relationship.name());
		csiToPojoMapper.put(IdentifiableSimpleContent.class.getSimpleName(), IcomBeanEnumeration.SimpleContent.name());
		csiToPojoMapper.put(CollabProperty.class.getSimpleName(), IcomBeanEnumeration.Property.name());
		csiToPojoMapper.put(EmailMessageContent.class.getSimpleName(), IcomBeanEnumeration.UnifiedMessage.name());
		csiToPojoMapper.put(EmailMessage.class.getSimpleName(), IcomBeanEnumeration.UnifiedMessage.name());
		csiToPojoMapper.put(EmailParticipant.class.getSimpleName(), IcomBeanEnumeration.UnifiedMessageParticipant.name());
		csiToPojoMapper.put(EmailRecipient.class.getSimpleName(), IcomBeanEnumeration.UnifiedMessageParticipant.name());
		csiToPojoMapper.put(BdkMeetingSeries.class.getSimpleName(), IcomBeanEnumeration.OccurrenceSeries.name());
		csiToPojoMapper.put(BdkMeeting.class.getSimpleName(), IcomBeanEnumeration.Occurrence.name());
		csiToPojoMapper.put(BdkMeetingParticipant.class.getSimpleName(), IcomBeanEnumeration.OccurrenceParticipant.name());
		csiToPojoMapper.put(BdkTask.class.getSimpleName(), IcomBeanEnumeration.Task.name());
		csiToPojoMapper.put(BdkFreeBusy.class.getSimpleName(), IcomBeanEnumeration.FreeBusy.name());
		csiToPojoMapper.put(BdkParticipantFreeBusy.class.getSimpleName(), IcomBeanEnumeration.FreeBusy.name());
		csiToPojoMapper.put(BdkFreeBusyInterval.class.getSimpleName(), IcomBeanEnumeration.FreeBusyInterval.name());
		csiToPojoMapper.put(DiscussionsMessage.class.getSimpleName(), IcomBeanEnumeration.DiscussionMessage.name());
		csiToPojoMapper.put(Invitation.class.getSimpleName(), IcomBeanEnumeration.Occurrence.name());
		csiToPojoMapper.put(InvitationSeries.class.getSimpleName(), IcomBeanEnumeration.OccurrenceSeries.name());
		csiToPojoMapper.put(PersonContact.class.getSimpleName(), IcomBeanEnumeration.PersonContact.name());
		csiToPojoMapper.put(AddressBook.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveAddressBook.name());
		csiToPojoMapper.put(ResourceContact.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveResourceContact.name());
		csiToPojoMapper.put(GroupContact.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveGroupContact.name());
		csiToPojoMapper.put(Enterprise.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveEnterprise.name());
		csiToPojoMapper.put(Organization.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveCommunity.name());
		csiToPojoMapper.put(HeterogeneousFolder.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveHeterogeneousFolder.name());
		csiToPojoMapper.put(Trash.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveTrash.name());
		csiToPojoMapper.put(Label.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveTag.name());
		csiToPojoMapper.put(LabelApplication.class.getSimpleName(),  BeehiveBeanEnumeration.BeehiveTagApplication.name());
		csiToPojoMapper.put(AttributeApplication.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveProperty.name());
		csiToPojoMapper.put(AttributeDefinition.class.getSimpleName(), BeehiveBeanEnumeration.BeehivePropertyDefinition.name());
		csiToPojoMapper.put(AttributeTemplate.class.getSimpleName(), BeehiveBeanEnumeration.BeehivePropertyTemplate.name());
		csiToPojoMapper.put(TeamWorkspace.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveTeamSpace.name());
		csiToPojoMapper.put(PersonalWorkspace.class.getSimpleName(), BeehiveBeanEnumeration.BeehivePersonalSpace.name());
		csiToPojoMapper.put(RoleDefinition.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveRoleDefinition.name());
		csiToPojoMapper.put(AssignedRole.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveRole.name());
		csiToPojoMapper.put(Group.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveGroup.name());
		csiToPojoMapper.put(AllUsersGroup.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveGroup.name());
		csiToPojoMapper.put(Category.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveCategory.name());
		csiToPojoMapper.put(CategoryApplication.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveCategoryApplication.name());
		csiToPojoMapper.put(CategoryTemplate.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveCategoryApplicationTemplate.name());
		csiToPojoMapper.put(CategoryConfiguration.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveCategoryConfiguration.name());
		csiToPojoMapper.put(VersionControlConfiguration.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveVersionControlConfiguration.name());
		csiToPojoMapper.put(OrganizationUser.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveOrganizationUser.name());
		csiToPojoMapper.put(Principal.class.getSimpleName(), BeehiveBeanEnumeration.BeehivePrincipal.name());
		csiToPojoMapper.put(SystemActor.class.getSimpleName(), BeehiveBeanEnumeration.BeehiveSystemActor.name());
	
		for (String key : csiToPojoMapper.keySet()) {
			pojoToDaoMapper.put(csiToPojoMapper.get(key), key + "DAO");
		}
		// the following defines unique reverse mapping for those with the two to one (2 csi to 1 pojo) mappings above
		pojoToDaoMapper.put(IcomBeanEnumeration.UnifiedMessage.name(), EmailMessageDAO.class.getSimpleName());
		pojoToDaoMapper.put(IcomBeanEnumeration.UnifiedMessageParticipant.name(), EmailParticipantOrRecipientDAO.class.getSimpleName());
		pojoToDaoMapper.put(IcomBeanEnumeration.SimpleContent.name(), SimpleContentDAO.class.getSimpleName());
		pojoToDaoMapper.put(IcomBeanEnumeration.AccessControlEntry.name(), AceDAO.class.getSimpleName());
		pojoToDaoMapper.put(IcomBeanEnumeration.OccurrenceSeries.name(), BdkMeetingSeriesDAO.class.getSimpleName());
		pojoToDaoMapper.put(IcomBeanEnumeration.Occurrence.name(), BdkMeetingDAO.class.getSimpleName());
		pojoToDaoMapper.put(IcomBeanEnumeration.FreeBusy.name(), BdkFreeBusyDAO.class.getSimpleName());
		pojoToDaoMapper.put(BeehiveBeanEnumeration.BeehiveGroup.name(), GroupDAO.class.getSimpleName());
		
		csiToPojoMapper.put(MimeHeader.class.getSimpleName(), IcomBeanEnumeration.Property.name());
		
		for (IcomBeanEnumeration e : IcomBeanEnumeration.values()) {
			pojoIcomBeanNames.add(e.name());
		}
		
		for (BeehiveBeanEnumeration e : BeehiveBeanEnumeration.values()) {
			pojoBeehiveBeanNames.add(e.name());
		}
	}
	
	private static Map<String, ProviderClassPojoClassMap> specialMappings = new HashMap<String, ProviderClassPojoClassMap>();
	
	static {
		ProviderClassPojoClassMap map;
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.AttachedItem.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.AttachedItem.name();
		map.providerSimpleClassName = "Attachment Stubbed";
		map.daoSimpleClassName = AttachmentDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.VersionSeries.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.VersionSeries.name();
		map.providerSimpleClassName = Document.class.getSimpleName();
		map.daoSimpleClassName = VersionSeriesToDocumentDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.PropertyChoiceType.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.PropertyChoiceType.name();
		map.providerSimpleClassName = CollabProperty.class.getSimpleName();
		map.daoSimpleClassName = PropertyChoiceTypeToCollabPropertyDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.Property.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.Property.name();
		map.providerSimpleClassName = MimeHeader.class.getSimpleName();
		map.daoSimpleClassName = MimeHeaderDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.AccessControlList.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.AccessControlList.name();
		map.providerSimpleClassName = "Set<ACE>";
		map.daoSimpleClassName = AccessControlListToSetOfAceDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.Document.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.Document.name();
		map.providerSimpleClassName = SimpleContent.class.getSimpleName();
		map.daoSimpleClassName = DocumentToSimpleContentDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.Location.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.Location.name();
		map.providerSimpleClassName = "Location";
		map.daoSimpleClassName = LocationDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
		map = new ProviderClassPojoClassMap();
		map.pojoPackageName = IcomBeanEnumeration.GeoCoordinates.getPackageName();
		map.pojoSimpleClassName = IcomBeanEnumeration.GeoCoordinates.name();
		map.providerSimpleClassName = "PhysicalLocation";
		map.daoSimpleClassName = PhysicalLocationDAO.class.getSimpleName();
		specialMappings.put(map.pojoSimpleClassName, map);
		
	}
	
	public Class<?> mapPojoToBeanInfo(Class<? extends Object> pojoClass) {
		String pojoClassName = pojoClass.getSimpleName();
		String beanInfoPackageName = null;
		if (pojoBeehiveBeanNames.contains(pojoClassName)) {
			beanInfoPackageName = beehiveBeanInfoPackageName;
		} else {
			beanInfoPackageName = icomBeanInfoPackageName;
		}
		String beanInfoClassName = beanInfoPackageName + "." + pojoClassName + "Info";
		try {
			Class<?> beanInfoClass = (Class<?>) Class.forName(beanInfoClassName);
			return beanInfoClass;
		} catch (ClassNotFoundException ex) {
			if (Manageable.class.isAssignableFrom(pojoClass)) {
				return UnknownEntityInfo.class;
			} else {
				return UnknownIdentifiableInfo.class;
			}
		} catch (Throwable ex) {
			throw new PersistenceException("entity class not valid :" + pojoClassName);
		}
	}
	
	public Class<?> mapPojoToDao(Class<?> pojoClass) {
		String pojoClassName = pojoClass.getSimpleName();
		String daoSimpleClassName = mapPojoClassNameToDaoClassName(pojoClassName);
		String daoClassName = daoPackageName + "." + daoSimpleClassName;
		try {
			Class<?> daoClass = (Class<?>) Class.forName(daoClassName);
			return daoClass;
		} catch (ClassNotFoundException ex) {
			if (Manageable.class.isAssignableFrom(pojoClass)) {
				return UnknownEntityDAO.class;
			} else {
				return UnknownIdentifiableDAO.class;
			}
		} catch (Throwable ex) {
			throw new PersistenceException("dao class not valid :" + daoClassName);
		}
	}
	
	public Class<?> mapPojoToDao(ProviderClassPojoClassMap map) {
		if (map.daoSimpleClassName == null) {
			map.daoSimpleClassName = mapPojoClassNameToDaoClassName(map.pojoSimpleClassName);
		}
		String daoClassName = daoPackageName + "." + map.daoSimpleClassName;
		try {
			Class<?> daoClass = (Class<?>) Class.forName(daoClassName);
			return daoClass;
		} catch (ClassNotFoundException ex) {
			if (Manageable.class.isAssignableFrom(map.pojoClass)) {
				return UnknownEntityDAO.class;
			} else {
				return UnknownIdentifiableDAO.class;
			}
		} catch (Throwable ex) {
			throw new PersistenceException("dao class not valid :" + daoClassName);
		}
	}

	public Class<?> mapPojoToDao(Class<?> pojoClass, Class<?> pojoParentClass, String parentAttribute) {
		String daoSimpleClassName = null;
		String pojoClassName = pojoClass.getSimpleName();
		String pojoParentClassName = pojoParentClass.getSimpleName();
		if (pojoClassName.equals(IcomBeanEnumeration.Document.name()) 
				&& pojoParentClassName.equals(IcomBeanEnumeration.MultiContent.name())) {
			daoSimpleClassName = DocumentToSimpleContentDAO.class.getSimpleName();
		} else if (pojoClassName.equals(IcomBeanEnumeration.Property.name()) 
				&& pojoParentClassName.equals(IcomBeanEnumeration.UnifiedMessage.name())
				&& parentAttribute.equals(UnifiedMessageInfo.Attributes.mimeHeaders.name())) {
			daoSimpleClassName = MimeHeaderDAO.class.getSimpleName();
		} else if (pojoClassName.equals(IcomBeanEnumeration.PropertyChoiceType.name())) {
			daoSimpleClassName = PropertyChoiceTypeToCollabPropertyDAO.class.getSimpleName();
		} else {
			daoSimpleClassName = mapPojoClassNameToDaoClassName(pojoClassName);
		}
		String daoClassName = daoPackageName + "." + daoSimpleClassName;
		try {
			Class<?> daoClass = (Class<?>) Class.forName(daoClassName);
			return daoClass;
		} catch (ClassNotFoundException ex) {
			if (Manageable.class.isAssignableFrom(pojoClass)) {
				return UnknownEntityDAO.class;
			} else {
				return UnknownIdentifiableDAO.class;
			}
		} catch (Throwable ex) {
			throw new PersistenceException("dao class not valid :" + daoClassName);
		}
	}
	
	public String mapPojoClassNameToDaoClassName(String pojoClassName) {
		String daoClassName = pojoToDaoMapper.get(pojoClassName);
		if (daoClassName != null) {
			return daoClassName;
		} else {
			return pojoClassName + "DAO";
		}
	}
	
	public ProviderClassPojoClassMap mapDocumentAttachment() {
		String pojoClassName = IcomBeanEnumeration.Document.getPackageName() + "." + IcomBeanEnumeration.Document.name();
		ProviderClassPojoClassMap map = new ProviderClassPojoClassMap();
		map.providerSimpleClassName = SimpleContent.class.getSimpleName();
		try {
			map.pojoClass = (Class<?>) Class.forName(pojoClassName);
			return map;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("entity class not found :" + pojoClassName);
		} catch (Throwable ex) {
			throw new PersistenceException("entity class not valid :" + pojoClassName);
		}
	}
	
	public ProviderClassPojoClassMap mapProviderClassToPojoClass(String pojoClassname) {
		ProviderClassPojoClassMap map = specialMappings.get(pojoClassname);
		if (map != null) {
			String pojoClassName = map.pojoPackageName + "." + map.pojoSimpleClassName;
			try {
				map.pojoClass = (Class<?>) Class.forName(pojoClassName);
				return map;
			} catch (ClassNotFoundException ex) {
				throw new PersistenceException("entity class not found :" + pojoClassName);
			} catch (Throwable ex) {
				throw new PersistenceException("entity class not valid :" + pojoClassName);
			}
		} else {
			return null;
		}
	}
	
	public ProviderClassPojoClassMap mapProviderClassToPojoClass(Class<?> csiClass) {
		ProviderClassPojoClassMap map = new ProviderClassPojoClassMap();
		map.providerSimpleClassName = null;
		if (oracle.csi.Entity.class.isAssignableFrom(csiClass)) {
			map.providerSimpleClassName = csiClass.getSimpleName();
		}
		if (map.providerSimpleClassName == null) {
			String packageName = csiClass.getPackage().getName();
			if (packageName.startsWith("oracle.csi")) {
				map.providerSimpleClassName = csiClass.getSimpleName();
			}
		}
		if (map.providerSimpleClassName == null) {
			Class<?> interfaces[] = csiClass.getInterfaces();
			for (Class<?> csiInterface: interfaces) {
				if (oracle.csi.Entity.class.isAssignableFrom(csiInterface)) {
					map.providerSimpleClassName = csiInterface.getSimpleName();
					break;
				}	
			}
		}
		if (map.providerSimpleClassName == null) {
			Class<?> interfaces[] = csiClass.getInterfaces();
			for (Class<?> csiInterface: interfaces) {
				if (oracle.csi.Identifiable.class.isAssignableFrom(csiInterface)) {
					map.providerSimpleClassName = csiInterface.getSimpleName();
					if (csiClass.getSimpleName().contains(map.providerSimpleClassName)) {
						break;
					}
				}	
			}
		}
		if (map.providerSimpleClassName == null) {
			Class<?> interfaces[] = csiClass.getInterfaces();
			for (Class<?> csiInterface: interfaces) {
				String packageName = csiInterface.getPackage().getName();
				if (packageName.startsWith("oracle.csi")) {
					map.providerSimpleClassName = csiInterface.getSimpleName();
					break;
				}
			}
		}
		if (map.providerSimpleClassName == null) {
			throw new PersistenceException("cannot find the ejb interface for " + csiClass.getName());
		}
		
		String pojoClassName = null;
		
		String icomPackagePrefix = BeanHandler.getBeanPackageName() + '.';
		String beehivePackagePrefix = BeehiveBeanEnumeration.BeehiveEnterprise.getPackageName() + ".";
		
		if (pojoClassName == null) {
			String pojoSimpleClassName = csiToPojoMapper.get(map.providerSimpleClassName);
			if (pojoSimpleClassName != null) {
				String packagePrefix = null;
				if (pojoBeehiveBeanNames.contains(pojoSimpleClassName)) {
					packagePrefix = beehivePackagePrefix;
				} else {
					packagePrefix = icomPackagePrefix;
				}
				pojoClassName = packagePrefix + pojoSimpleClassName;
			}
		}
		
		if (pojoClassName == null) {
			// for the case where provide class name is same as pojo class name
			String packagePrefix = null;
			if (pojoIcomBeanNames.contains(map.providerSimpleClassName)) {
				packagePrefix = icomPackagePrefix;
			} else if (pojoBeehiveBeanNames.contains(map.providerSimpleClassName)) {
				packagePrefix = beehivePackagePrefix;
			}
			if (packagePrefix != null) {
				pojoClassName = packagePrefix + map.providerSimpleClassName;
			}
		}

		if (pojoClassName == null) {
			pojoClassName = icomPackagePrefix + "UnknownEntity";
			if (oracle.csi.Folder.class.isAssignableFrom(csiClass)) {
				pojoClassName = icomPackagePrefix + "UnknownFolder";
			} else if (oracle.csi.Artifact.class.isAssignableFrom(csiClass)) {
				pojoClassName = icomPackagePrefix + "UnknownArtifact";
			} if (oracle.csi.BaseAccessor.class.isAssignableFrom(csiClass)) {
				pojoClassName = icomPackagePrefix + "UnknownSubject";
			} 
			/*
			else {
				Class<?> interfaces[] = csiClass.getInterfaces();
				for (Class<?> csiInterface: interfaces) {
					if (oracle.csi.Identifiable.class.isAssignableFrom(csiInterface)) {}
						pojoClassName = packagePrefix + "UnknownIdentifiable";
						break;
					}
				}
			}
			*/
		}
		
		try {
			map.pojoClass = (Class<?>) Class.forName(pojoClassName);
			return map;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("entity class not found :" + pojoClassName);
		} catch (Throwable ex) {
			throw new PersistenceException("entity class not valid :" + pojoClassName);
		}
	}
	
}
