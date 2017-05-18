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
package icom.jpa.csi.dao;

import icom.info.AbstractBeanInfo;
import icom.info.ArtifactInfo;
import icom.info.AttachmentInfo;
import icom.info.BeanHandler;
import icom.info.BeanInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.LocationInfo;
import icom.info.OccurrenceSeriesInfo;
import icom.info.PropertyInfo;
import icom.info.RelationshipBondableInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.csi.dao.BdkMeetingDAO.OccurrenceEditMode;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.persistence.PersistenceException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import oracle.csi.Attachable;
import oracle.csi.BdkMeeting;
import oracle.csi.BdkMeetingParticipant;
import oracle.csi.BdkMeetingSeries;
import oracle.csi.CalendarHandle;
import oracle.csi.CalendarOperationContext;
import oracle.csi.CollabId;
import oracle.csi.CollabProperties;
import oracle.csi.CollabProperty;
import oracle.csi.Conference;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.Identifiable;
import oracle.csi.IdentifiableHandle;
import oracle.csi.IdentifiableSimpleContentHandle;
import oracle.csi.InvitationSeries;
import oracle.csi.InvitationSeriesHandle;
import oracle.csi.OccurrenceParticipantStatus;
import oracle.csi.OccurrenceSeries;
import oracle.csi.OccurrenceSeriesHandle;
import oracle.csi.OccurrenceStatus;
import oracle.csi.OccurrenceType;
import oracle.csi.Participant;
import oracle.csi.Priority;
import oracle.csi.RawString;
import oracle.csi.SnapshotId;
import oracle.csi.Transparency;
import oracle.csi.controls.BdkCalendarControl;
import oracle.csi.controls.CalendarControl;
import oracle.csi.controls.CalendarFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.creators.BdkMeetingSeriesCreator;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttachmentListUpdater;
import oracle.csi.updaters.BdkMeetingParticipantUpdater;
import oracle.csi.updaters.BdkMeetingSeriesUpdater;
import oracle.csi.updaters.CollabPropertiesUpdater;
import oracle.csi.updaters.IdentifiableSimpleContentUpdater;
import oracle.csi.updaters.NotSetException;
import oracle.csi.updaters.OccurrenceSeriesUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class BdkMeetingSeriesDAO extends EntityDAO implements TimeManagement {

	static BdkMeetingSeriesDAO singleton = new BdkMeetingSeriesDAO();
	
	public static BdkMeetingSeriesDAO getInstance() {
		return singleton;
	}
	
	{
		// TODO BdkMeetingSeries should be changed to Artifact in CSI, the following are temporary fix
		basicAttributes.add(ArtifactInfo.Attributes.userCreationDate);
		basicAttributes.add(ArtifactInfo.Attributes.userLastModificationDate);
		basicAttributes.add(ArtifactInfo.Attributes.properties);
		basicAttributes.add(ArtifactInfo.Attributes.viewerProperties);
		
	}
	
	{
		basicAttributes.add(ArtifactInfo.Attributes.description);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.editMode);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.recurrenceStartDate);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.recurrenceStartDateResolution);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.duration);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.recurrenceRule);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.location);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.organizer);  // read-only
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.occurrenceStatus);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.occurrenceType);     // read-only
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.attendee);           // read-only
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.priority);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.attendeeParticipantStatus);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.transparency);
		basicAttributes.add(OccurrenceSeriesInfo.Attributes.conferences);
	}
	
	{
		metaAttributes.add(OccurrenceSeriesInfo.Attributes.attendeeProperties);
	}
	
	{
		fullAttributes.add(OccurrenceSeriesInfo.Attributes.occurrences);
		//fullAttributes.add(OccurrenceSeriesInfo.Attributes.attachments);
		fullAttributes.add(OccurrenceSeriesInfo.Attributes.participants);
	}
	
	{
		lazyAttributes.add(OccurrenceSeriesInfo.Attributes.attachments);
		lazyAttributes.add(RelationshipBondableInfo.Attributes.relationships);
	}
	
	protected BdkMeetingSeriesDAO() {	
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return InvitationSeriesHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		BdkMeetingSeries csiBdkMeetingSeries = null;
		try {
			BdkCalendarControl control = ControlLocator.getInstance().getControl(BdkCalendarControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			InvitationSeriesHandle csiBdkMeetingSeriesHandle = (InvitationSeriesHandle) EntityUtils.getInstance().createHandle(id);
			csiBdkMeetingSeries = control.loadMeetingSeries(csiBdkMeetingSeriesHandle, proj);		
		} catch (CsiException ex) {
		}
		return csiBdkMeetingSeries;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		super.copyObjectState(managedObj, csiEntity, proj);
		
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		BdkMeetingSeries csiBdkMeetingSeries = (BdkMeetingSeries) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ArtifactInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(), csiBdkMeetingSeries.getTextDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.editMode.name(), lastLoadedProjection, proj)) {
			try {
				boolean isInvitationOnly = csiBdkMeetingSeries.isInvitationOnly();
				String modeName = null;
				if (isInvitationOnly) {
					modeName = OccurrenceEditMode.AttendeeCopy.name();
				} else {
					modeName = OccurrenceEditMode.OrganizerCopy.name();
				}
				assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.editMode.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceEditModeEnum.name(), 
						modeName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.recurrenceStartDate.name(), lastLoadedProjection, proj)) {
			try {
				XMLGregorianCalendar csiRecurrenceStartTime = csiBdkMeetingSeries.getRecurrenceStart();
				if (csiRecurrenceStartTime != null) {
					Date pojoRecurrenceStartTime = csiRecurrenceStartTime.toGregorianCalendar().getTime();
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.recurrenceStartDate.name(), pojoRecurrenceStartTime);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.recurrenceStartDate.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.recurrenceStartDateResolution.name(), lastLoadedProjection, proj)) {
			try {
				boolean startDateOnly = csiBdkMeetingSeries.isRecurrenceStartDateOnly();
				if (startDateOnly) {
					assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.recurrenceStartDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							BdkMeetingDAO.DateTimeResolution.Date.name());
				} else {
					assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.recurrenceStartDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							BdkMeetingDAO.DateTimeResolution.Time.name());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.location.name(), lastLoadedProjection, proj)) {
			try {
				String location = csiBdkMeetingSeries.getLocationName();
				ManagedObjectProxy locationObj = getNonIdentifiableDependentProxy(context, IcomBeanEnumeration.Location.name(), obj, OccurrenceSeriesInfo.Attributes.location.name());
				Persistent pojoLocation = locationObj.getPojoObject();
				assignAttributeValue(pojoLocation, LocationInfo.Attributes.name.name(), location);
				assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.location.name(), pojoLocation);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.organizer.name(), lastLoadedProjection, proj)) {
			try {
				Participant csiParticipant = csiBdkMeetingSeries.getOrganizer();
				if (csiParticipant != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiParticipant, obj, OccurrenceSeriesInfo.Attributes.organizer.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiParticipant, proj);
					
					Object previousPojoObject = AbstractDAO.getAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.organizer.name());
					if (previousPojoObject != null) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
					
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.organizer.name(), participantObj.getPojoObject());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.participants.name(), lastLoadedProjection, proj)) {
			try {
				Collection<BdkMeetingParticipant> csiBdkMeetingParticipants = csiBdkMeetingSeries.getParticipants();
				Vector<Object> v = new Vector<Object>(csiBdkMeetingParticipants.size());
				for (BdkMeetingParticipant csiBdkMeetingParticipant : csiBdkMeetingParticipants) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiBdkMeetingParticipant, obj, OccurrenceSeriesInfo.Attributes.participants.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiBdkMeetingParticipant, proj);
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.participants.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.participants.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}		
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.occurrenceStatus.name(), lastLoadedProjection, proj)) {
			try {
				OccurrenceStatus status = csiBdkMeetingSeries.getStatus();
				if (status != null) {
					String pojoOccurrenceStatusName = BdkMeetingDAO.csiToPojoOccurrenceStatus.get(status.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrenceStatus.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceStatusEnum.name(), pojoOccurrenceStatusName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrenceStatus.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.occurrenceType.name(), lastLoadedProjection, proj)) {
			try {
				OccurrenceType type = csiBdkMeetingSeries.getType();
				if (type != null) {
					String pojoOccurrenceTypeName = BdkMeetingDAO.csiToPojoOccurrenceType.get(type.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrenceType.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceTypeEnum.name(), pojoOccurrenceTypeName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrenceType.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.occurrences.name(), lastLoadedProjection, proj)) {
			try {
				Collection<BdkMeeting> csiBdkMeetings = csiBdkMeetingSeries.getMeetings();
				if (csiBdkMeetings != null) {
					Collection<Persistent> pojoOccurrences = new Vector<Persistent>(csiBdkMeetings.size());
					for (BdkMeeting csiBdkMeeting : csiBdkMeetings) {
						ManagedIdentifiableProxy occurrenceObj = getEntityProxy(context, csiBdkMeeting);
						Persistent occurrencePojoEntity = occurrenceObj.getPojoIdentifiable();
						pojoOccurrences.add(occurrencePojoEntity);
					}
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrences.name(), pojoOccurrences);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrences.name(), new Vector<Persistent>());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.attendee.name(), lastLoadedProjection, proj)) {
			try {
				
				Object previousPojoObject = AbstractDAO.getAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendee.name());
				if (previousPojoObject != null) {
					BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
					ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
					beanInfo.detachHierarchy(mop);
				}
				
				Participant csiInvitee = csiBdkMeetingSeries.getInvitee();
				if (csiInvitee != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiInvitee, obj, OccurrenceSeriesInfo.Attributes.attendee.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiInvitee, proj);
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendee.name(), participantObj.getPojoObject());
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendee.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.priority.name(), lastLoadedProjection, proj)) {
			try {
				Priority priority = csiBdkMeetingSeries.getInviteePriority();
				if (priority != null) {
					String pojoPriorityName = csiToPojoPriorityNameMap.get(priority.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.priority.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PriorityEnum.name(), pojoPriorityName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.priority.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.attendeeParticipantStatus.name(), lastLoadedProjection, proj)) {
			try {
				OccurrenceParticipantStatus status = csiBdkMeetingSeries.getInviteeParticipantStatus();
				if (status != null) {
					String pojoParticipantStatusName = BdkMeetingDAO.csiToPojoOccurrenceParticipantStatus.get(status.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendeeParticipantStatus.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceParticipantStatusEnum.name(), pojoParticipantStatusName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendeeParticipantStatus.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.transparency.name(), lastLoadedProjection, proj)) {
			try {
				Transparency transparency = csiBdkMeetingSeries.getInviteeEffectiveTransparency();
				if (transparency != null) {
					String pojoTransparencyName = BdkMeetingDAO.csiToPojoTransparency.get(transparency.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.transparency.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceParticipantTransparencyEnum.name(), pojoTransparencyName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.transparency.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.attendeeProperties.name(), lastLoadedProjection, proj)) {
			try {
				Vector<Object> v = new Vector<Object>();
				CollabProperties propertyMaps = csiBdkMeetingSeries.getInviteeProperties();
				if (propertyMaps != null) {
					Collection<CollabProperty> attendeeProperties = propertyMaps.values();
					Iterator<CollabProperty> iter = attendeeProperties.iterator();
					while (iter.hasNext()) {
						CollabProperty csiCollabProperty = iter.next();
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, csiCollabProperty, obj, OccurrenceSeriesInfo.Attributes.attendeeProperties.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, csiCollabProperty, proj);
						v.add(propertyObj.getPojoObject());
					}
				}
				
				Collection<Object> previousProperties = getObjectCollection(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendeeProperties.name());
				if (previousProperties != null) {
					for (Object pojoProperty : previousProperties) {
						if (pojoProperty instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendeeProperties.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceSeriesInfo.Attributes.conferences.name(), lastLoadedProjection, proj)) {
			try {
				Conference conference = csiBdkMeetingSeries.getOnlineConference();
				if (conference != null) {
					Collection<Persistent> pojoConferences = new Vector<Persistent>(1);
					ManagedIdentifiableProxy conferenceObj = getEntityProxy(context, conference);
					Persistent conferencePojoEntity = conferenceObj.getPojoIdentifiable();
					pojoConferences.add(conferencePojoEntity);
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.conferences.name(), pojoConferences);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.conferences.name(), new Vector<Persistent>(1));
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}

	}
	
	public void loadAttachments(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoIdentifiable = obj.getPojoObject();
		InvitationSeries csiInvitationSeries = null;
		try {
			CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			InvitationSeriesHandle csiInvitationSeriesHandle = (InvitationSeriesHandle) EntityUtils.getInstance().createHandle(id);
			csiInvitationSeries = control.loadInvitationSeries(csiInvitationSeriesHandle, proj);
			Set<Attachable> csiAttachments = csiInvitationSeries.getAttachments();
			if (csiAttachments != null) {
				Set<Persistent> pojoNewAttachments = new HashSet<Persistent>(csiAttachments.size());
				for (Attachable csiAttachment : csiAttachments) {
					String pojoClassName = IcomBeanEnumeration.AttachedItem.name();
					ManagedObjectProxy attachmentObj = getNonIdentifiableDependentProxy(context, pojoClassName, obj, OccurrenceSeriesInfo.Attributes.attachments.name());
					Persistent pojoAttachment = attachmentObj.getPojoObject();
					RawString rawname = csiAttachment.getName();
					String name = null;
					if (rawname != null) {
						try {
							name = rawname.convertToString();
						} catch (UnsupportedEncodingException ex) {
							name = rawname.getString();
						}
					}
					assignAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name(), name);
					ManagedObjectProxy contentObj = getIdentifiableDependentProxy(context, csiAttachment, attachmentObj, AttachmentInfo.Attributes.content.name());
					contentObj.getProviderProxy().copyLoadedProjection(contentObj, (Identifiable) csiAttachment, proj);
					pojoNewAttachments.add(pojoAttachment);
				}
				assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attachments.name(), pojoNewAttachments);
			} else {
				assignAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attachments.name(), new HashSet<Persistent>());
			}
		} catch (CsiException ex) {
		}
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		if (OccurrenceSeriesInfo.Attributes.attachments.name().equals(attributeName)) {
			loadAttachments(obj, Projection.FULL);
		}
		super.loadAndCopyObjectState(obj, attributeName, parameter);
	}

	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		BdkMeetingSeriesUpdater updater = (BdkMeetingSeriesUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.recurrenceStartDate.name())) {
			Date recurrenceStartDate = (Date) getAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.recurrenceStartDate.name());
			if (recurrenceStartDate != null) {
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(recurrenceStartDate);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					// TODO updater.setRecurrenceStartDate(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
			} else {
				// TODO updater.setRecurrenceStartDate(null);
			}
		}
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.location.name())) {
			Persistent pojoLocation = (Persistent) getAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.location.name());
			if (pojoLocation != null) {
				String location = (String) getAttributeValue(pojoLocation, LocationInfo.Attributes.name.name());
				updater.setLocationName(location);
			} else {
				updater.setLocationName(null);
			}
		}
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.occurrenceStatus.name())) {
			String occurrenceStatusName = getEnumName(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrenceStatus.name());
			String csiOccurrenceStatusName = BdkMeetingDAO.pojoToCsiOccurrenceStatus.get(occurrenceStatusName);
			if (csiOccurrenceStatusName != null) {
				OccurrenceStatus status = OccurrenceStatus.valueOf(csiOccurrenceStatusName);
				updater.setStatus(status);
			}
		}
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.participants.name())) {
			Collection<BdkMeetingParticipantUpdater> participants = new ArrayList<BdkMeetingParticipantUpdater>();
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(OccurrenceSeriesInfo.Attributes.participants.name());
			if (addedObjects != null) {
				for (ValueHolder holder: addedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					BdkMeetingParticipantUpdater participantUpdater = new BdkMeetingParticipantUpdater();
					BdkMeetingParticipantDAO.getInstance().updateObjectState(participant, participantUpdater, BdkMeetingParticipantDAO.Operand.ADD);
					participants.add(participantUpdater);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(OccurrenceSeriesInfo.Attributes.participants.name());
			if (removedObjects != null) {
				for (ValueHolder holder: removedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					BdkMeetingParticipantUpdater participantUpdater = new BdkMeetingParticipantUpdater();
					BdkMeetingParticipantDAO.getInstance().updateObjectState(participant, participantUpdater, BdkMeetingParticipantDAO.Operand.REMOVE);
					participants.add(participantUpdater);
				}
			}
			updater.setParticipantUpdaters(participants);
		}
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.priority.name())) {
			String attendeePriorityName = getEnumName(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.priority.name());
			String csiPriorityName = pojoToCsiPriorityNameMap.get(attendeePriorityName);
			if (csiPriorityName != null) {
				Priority priority = Priority.valueOf(csiPriorityName);
				updater.setInviteePriority(priority);
			}
		}
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.attendeeParticipantStatus.name())) {
			String attendeeParticipantStatusName = getEnumName(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendeeParticipantStatus.name());
			String csiParticipantStatusName = BdkMeetingDAO.pojoToCsiOccurrenceParticipantStatus.get(attendeeParticipantStatusName);
			if (csiParticipantStatusName != null) {
				OccurrenceParticipantStatus status = OccurrenceParticipantStatus.valueOf(csiParticipantStatusName);
				updater.setInviteeParticipantStatus(status);
			}
		}
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.transparency.name())) {
			String attendeeEffectiveTransparencyName = getEnumName(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.transparency.name());
			String csiTransparencyName = BdkMeetingDAO.pojoToCsiTransparency.get(attendeeEffectiveTransparencyName);
			if (csiTransparencyName != null) {
				Transparency transparency = Transparency.valueOf(csiTransparencyName);
				updater.setInviteeTransparency(transparency);
			}
		}
		
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.attendeeProperties.name())) {
			ArrayList<Persistent> modifiedAttendeeProperties = new ArrayList<Persistent>();
			Collection<Object> properties = getObjectCollection(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attendeeProperties.name());
			if (properties != null) {
				for (Object property : properties) {
					Persistent pojo = (Persistent) property;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.containsAttributeChangeRecord(PropertyInfo.Attributes.value.name())) {
						modifiedAttendeeProperties.add(pojo);
					}
				}
			}
			
			try {
				CollabPropertiesUpdater attendeePropertiesUpdater = updater.getInviteePropertiesUpdater();
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(OccurrenceSeriesInfo.Attributes.attendeeProperties.name());
				if (addedObjects != null) {
					Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
					while (addedObjectsIter.hasNext()) {
						ValueHolder holder = addedObjectsIter.next();
						Persistent pojoProperty = (Persistent) holder.getValue();
						CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, attendeePropertiesUpdater, CollabPropertyDAO.Operand.ADD);
						modifiedAttendeeProperties.remove(pojoProperty);
					}
				}
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(OccurrenceSeriesInfo.Attributes.attendeeProperties.name());
				if (removedObjects != null) {
					Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
					while (removedObjectsIter.hasNext()) {
						ValueHolder holder = removedObjectsIter.next();
						Persistent pojoProperty = (Persistent) holder.getValue();
						CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, attendeePropertiesUpdater, CollabPropertyDAO.Operand.REMOVE);
						modifiedAttendeeProperties.remove(pojoProperty);
					}
				}
				for (Persistent modifiedAttendeeProperty : modifiedAttendeeProperties) {
					CollabPropertyDAO.getInstance().updateObjectState(modifiedAttendeeProperty, attendeePropertiesUpdater, CollabPropertyDAO.Operand.MODIFY);
				}
			} catch (NotSetException ex) {
				
			}
		}
	}
	
	void updateAttachments(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, OccurrenceSeriesInfo.Attributes.attachments.name())) {
			Persistent pojoIdentifiable = (Persistent) obj.getPojoObject();
			
			CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			InvitationSeriesHandle csiInvitationSeriesHandle = (InvitationSeriesHandle) EntityUtils.getInstance().createHandle(id);
			InvitationSeries csiInvitationSeries = null;
			try {
				csiInvitationSeries = control.loadInvitationSeries(csiInvitationSeriesHandle, Projection.BASIC);
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
			OccurrenceSeries csiOccurenceSeries = csiInvitationSeries.getSource();
			CollabId occurrenceSeriesId = csiOccurenceSeries.getCollabId();
			OccurrenceSeriesHandle occurrenceSeriesHandle = (OccurrenceSeriesHandle) EntityUtils.getInstance().createHandle(occurrenceSeriesId);
			//SnapshotId occurrenceSeriesSid = csiOccurenceSeries.getSnapshotId();
			//UpdateMode updateMode = UpdateMode.optimisticLocking(occurrenceSeriesSid);
			UpdateMode updateMode = UpdateMode.alwaysUpdate();

			ArrayList<Persistent> modifiedAttachments = new ArrayList<Persistent>();
			Collection<Object> attachments = getObjectCollection(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.attachments.name());
			if (attachments != null) {
				for (Object attachment : attachments) {
					Persistent pojo = (Persistent) attachment;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.hasAttributeChanges()) {
						modifiedAttachments.add(pojo);
					}
				}
			}
			
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(OccurrenceSeriesInfo.Attributes.attachments.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent pojoAttachment = (Persistent) holder.getValue();
					String name = (String) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name());
					RawString rawname = new RawString(name);
					Persistent pojoSimpleContent = (Persistent) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.content.name());
					ManagedIdentifiableProxy simpleContentObj = (ManagedIdentifiableProxy) pojoSimpleContent.getManagedObjectProxy();
					CollabId cid = getCollabId(simpleContentObj.getObjectId());
					Eid eid = cid.getEid();
					OccurrenceSeriesUpdater updater = CalendarFactory.getInstance().createOccurrenceSeriesUpdater();
					AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
					IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(eid);
					simpleContentUpdater.setName(rawname);
					DAOContext daoContext = new DAOContext(simpleContentUpdater);
					CalendarOperationContext opContext = null;
					try {
						opContext = control.beginUpdateOccurrenceSeries(occurrenceSeriesHandle, updater, updateMode);
						daoContext.setOperationContext(opContext);
					} catch (Exception ex) {
						throw new PersistenceException(ex);
					}
					SimpleContentDAO.getInstance().updateObjectState(simpleContentObj, daoContext);
					try {
						control.commitOperation(opContext, Projection.EMPTY);
					} catch (Exception ex) {
						throw new PersistenceException(ex);
					}
					modifiedAttachments.remove(pojoSimpleContent);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(OccurrenceSeriesInfo.Attributes.attachments.name());
			if (removedObjects != null) {
				OccurrenceSeriesUpdater updater = CalendarFactory.getInstance().createOccurrenceSeriesUpdater();
				AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent pojoSimpleContent = (Persistent) holder.getValue();
					ManagedIdentifiableProxy simpleContentObj = (ManagedIdentifiableProxy) pojoSimpleContent.getManagedObjectProxy();
					CollabId cid = getCollabId(simpleContentObj.getObjectId());
					IdentifiableSimpleContentHandle csiIdentifiableSimpleContentHandle = (IdentifiableSimpleContentHandle) EntityUtils.getInstance().createHandle(cid);
					attachmentListUpdater.removeAttachment(csiIdentifiableSimpleContentHandle);
					modifiedAttachments.remove(pojoSimpleContent);
				}
			}
			for (Persistent pojoAttachment : modifiedAttachments) {
				Persistent pojoSimpleContent = (Persistent) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.content.name());
				ManagedIdentifiableProxy simpleContentObj = (ManagedIdentifiableProxy) pojoSimpleContent.getManagedObjectProxy();
				CollabId cid = getCollabId(simpleContentObj.getObjectId());
				Eid eid = cid.getEid();
				OccurrenceSeriesUpdater updater = CalendarFactory.getInstance().createOccurrenceSeriesUpdater();
				AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
				IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(eid);
				String name = (String) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name());
				RawString rawname = new RawString(name);
				simpleContentUpdater.setName(rawname);
				DAOContext daoContext = new DAOContext(simpleContentUpdater);
				CalendarOperationContext opContext = null;
				try {
					opContext = control.beginUpdateOccurrenceSeries(occurrenceSeriesHandle, updater, updateMode);
					daoContext.setOperationContext(opContext);
				} catch (Exception ex) {
					throw new PersistenceException(ex);
				}
				SimpleContentDAO.getInstance().updateObjectState(simpleContentObj, daoContext);
				try {
					control.commitOperation(opContext, Projection.EMPTY);
				} catch (Exception ex) {
					throw new PersistenceException(ex);
				}
			}
		}
	}
	
	protected void updateMetadataOnEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnEntity(obj);
		updateAttachments(obj);
	}
	
	protected void updateMetadataOnNewEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnNewEntity(obj);
		updateAttachments(obj);
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		BdkMeetingSeriesUpdater updater = new BdkMeetingSeriesUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		BdkCalendarControl control = ControlLocator.getInstance().getControl(BdkCalendarControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		InvitationSeriesHandle invitationSeriesHandle = (InvitationSeriesHandle) EntityUtils.getInstance().createHandle(id);
		BdkMeetingSeriesUpdater updater = (BdkMeetingSeriesUpdater) context.getUpdater();
		Object changeToken = obj.getChangeToken();
		UpdateMode updateMode = null;
		if (changeToken != null) {
			SnapshotId sid = getSnapshotId(changeToken);
			updateMode = UpdateMode.optimisticLocking(sid);
		} else {
			updateMode = UpdateMode.alwaysUpdate();
		}
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		try {
			BdkMeetingSeries bdkMeetingSeries = control.updateMeetingSeries(invitationSeriesHandle, updater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, bdkMeetingSeries.getSnapshotId().toString());
			return bdkMeetingSeries;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		BdkCalendarControl control = ControlLocator.getInstance().getControl(BdkCalendarControl.class);
		BdkMeetingSeriesUpdater updater = (BdkMeetingSeriesUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			BdkMeetingSeriesCreator creator = new BdkMeetingSeriesCreator();
			creator.setCalendar((CalendarHandle) entityHandle);
			/*
			CollabId id = getCollabId(obj.getObjectId());
			creator.setEid(id.getEid());
			 */
			creator.setMeetingSeriesUpdater(updater);
			
			String pojoTypeName = getEnumName(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.occurrenceType.name());
			if (pojoTypeName != null) {
				String csiTypeName = BdkMeetingDAO.pojoToCsiOccurrenceType.get(pojoTypeName);
				OccurrenceType type = OccurrenceType.valueOf(csiTypeName);
				creator.setType(type);
			}
			
			Date startDate = (Date) getAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.recurrenceStartDate.name());
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(startDate);
			try {
				XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
				creator.setRecurrenceStart(xgcal);
			} catch (DatatypeConfigurationException ex) {
				
			}
			
			String recurrenceRule = (String) getAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.recurrenceRule.name());
			creator.setRecurrenceRule(recurrenceRule);
			
			Duration duration = (Duration) getAttributeValue(pojoIdentifiable, OccurrenceSeriesInfo.Attributes.duration.name());
			creator.setDuration(duration);
			
			BdkMeetingSeries bdkMeetingSeries = control.createMeetingSeries(creator, proj);
			String cidStr = bdkMeetingSeries.getCollabId().toString();
			assignObjectId(pojoIdentifiable, cidStr);
			assignChangeToken(pojoIdentifiable, bdkMeetingSeries.getSnapshotId().toString());
			obj.setObjectId(cidStr);
			PersistenceContext persistentContext = obj.getPersistenceContext();
			persistentContext.recacheIdentifiableDependent((ManagedIdentifiableProxy) pojoIdentifiable.getManagedObjectProxy());
			return bdkMeetingSeries;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		InvitationSeriesHandle invitationSeriesHandle = (InvitationSeriesHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteInvitationSeries(invitationSeriesHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
}
