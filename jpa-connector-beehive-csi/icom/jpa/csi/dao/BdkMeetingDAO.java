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
import icom.info.OccurrenceInfo;
import icom.info.PropertyInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.persistence.PersistenceException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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
import oracle.csi.Invitation;
import oracle.csi.InvitationHandle;
import oracle.csi.Occurrence;
import oracle.csi.OccurrenceHandle;
import oracle.csi.OccurrenceParticipantStatus;
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
import oracle.csi.creators.BdkMeetingCreator;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttachmentListUpdater;
import oracle.csi.updaters.BdkMeetingParticipantUpdater;
import oracle.csi.updaters.BdkMeetingUpdater;
import oracle.csi.updaters.CollabPropertiesUpdater;
import oracle.csi.updaters.IdentifiableSimpleContentUpdater;
import oracle.csi.updaters.NotSetException;
import oracle.csi.updaters.OccurrenceUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class BdkMeetingDAO extends ArtifactDAO implements TimeManagement {
	
	static BdkMeetingDAO singleton = new BdkMeetingDAO();
	
	public static BdkMeetingDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(ArtifactInfo.Attributes.description);
		basicAttributes.add(OccurrenceInfo.Attributes.editMode);
		basicAttributes.add(OccurrenceInfo.Attributes.startDate);
		basicAttributes.add(OccurrenceInfo.Attributes.startDateResolution);
		basicAttributes.add(OccurrenceInfo.Attributes.endDate);
		basicAttributes.add(OccurrenceInfo.Attributes.endDateResolution);
		basicAttributes.add(OccurrenceInfo.Attributes.location);
		basicAttributes.add(OccurrenceInfo.Attributes.organizer);  // read-only
		basicAttributes.add(OccurrenceInfo.Attributes.occurrenceSeries);   // read-only
		basicAttributes.add(OccurrenceInfo.Attributes.fromRecurringOccurrenceSeries);  // read-only
		basicAttributes.add(OccurrenceInfo.Attributes.exceptionToOccurrenceSeries);    // read-only
		basicAttributes.add(OccurrenceInfo.Attributes.occurrenceStatus);
		basicAttributes.add(OccurrenceInfo.Attributes.occurrenceType);     // read-only
		basicAttributes.add(OccurrenceInfo.Attributes.attendee);           // read-only
		basicAttributes.add(OccurrenceInfo.Attributes.priority);
		basicAttributes.add(OccurrenceInfo.Attributes.attendeeParticipantStatus);
		basicAttributes.add(OccurrenceInfo.Attributes.transparency);
		basicAttributes.add(OccurrenceInfo.Attributes.conferences);
	}
	
	{
		metaAttributes.add(OccurrenceInfo.Attributes.attendeeProperties);
	}
	
	{
		//fullAttributes.add(OccurrenceInfo.Attributes.attachments);
		fullAttributes.add(OccurrenceInfo.Attributes.participants);
	}
	
	{
		lazyAttributes.add(OccurrenceInfo.Attributes.attachments);
	}
	
	enum DateTimeResolution {
		Year,
		Date,
		Time
	}
	
	enum PojoOccurrenceType {
		Meeting,
		DayEvent,
		Holiday,
		JournalEntry,
		OtherOccurrenceType
	}
	
	static HashMap<String, String> csiToPojoOccurrenceType;
	static HashMap<String, String> pojoToCsiOccurrenceType;
	
	{
		csiToPojoOccurrenceType = new HashMap<String, String>();
		pojoToCsiOccurrenceType = new HashMap<String, String>();
		csiToPojoOccurrenceType.put(OccurrenceType.MEETING.name(), PojoOccurrenceType.Meeting.name());
		csiToPojoOccurrenceType.put(OccurrenceType.DAY_EVENT.name(), PojoOccurrenceType.DayEvent.name());
		csiToPojoOccurrenceType.put(OccurrenceType.HOLIDAY.name(), PojoOccurrenceType.Holiday.name()); 
		csiToPojoOccurrenceType.put(OccurrenceType.JOURNAL_ENTRY.name(), PojoOccurrenceType.JournalEntry.name());
		for (String key : csiToPojoOccurrenceType.keySet()) {
			pojoToCsiOccurrenceType.put(csiToPojoOccurrenceType.get(key), key);
		}
	}
	
	enum PojoOccurrenceStatus {
		Cancelled,
		Tentative,
		Confirmed
	}
	
	static HashMap<String, String> csiToPojoOccurrenceStatus;
	static HashMap<String, String> pojoToCsiOccurrenceStatus;
	
	{
		csiToPojoOccurrenceStatus = new HashMap<String, String>();
		pojoToCsiOccurrenceStatus = new HashMap<String, String>();
		csiToPojoOccurrenceStatus.put(OccurrenceStatus.CANCELLED.name(), PojoOccurrenceStatus.Cancelled.name());
		csiToPojoOccurrenceStatus.put(OccurrenceStatus.TENTATIVE.name(), PojoOccurrenceStatus.Tentative.name());
		csiToPojoOccurrenceStatus.put(OccurrenceStatus.CONFIRMED.name(), PojoOccurrenceStatus.Confirmed.name()); 
		for (String key : csiToPojoOccurrenceStatus.keySet()) {
			pojoToCsiOccurrenceStatus.put(csiToPojoOccurrenceStatus.get(key), key);
		}
	}
	
	enum PojoOccurrenceParticipantStatus {
		NeedsAction,
		Accepted,
		Declined,
		Tentative
	}
	
	static HashMap<String, String> csiToPojoOccurrenceParticipantStatus;
	static HashMap<String, String> pojoToCsiOccurrenceParticipantStatus;
	
	{
		csiToPojoOccurrenceParticipantStatus = new HashMap<String, String>();
		pojoToCsiOccurrenceParticipantStatus = new HashMap<String, String>();
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.ACCEPTED.name(), PojoOccurrenceParticipantStatus.Accepted.name());
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.DECLINED.name(), PojoOccurrenceParticipantStatus.Declined.name());
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.NEEDS_ACTION.name(), PojoOccurrenceParticipantStatus.NeedsAction.name()); 
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.TENTATIVE.name(), PojoOccurrenceParticipantStatus.Tentative.name()); 
		for (String key : csiToPojoOccurrenceParticipantStatus.keySet()) {
			pojoToCsiOccurrenceParticipantStatus.put(csiToPojoOccurrenceParticipantStatus.get(key), key);
		}
	}
	
	enum OccurrenceParticipationTransparency {
		Opaque,
		Transparent,
		Tentative,
		OutOfOffice,
		DefaultTransparency
	}
	
	static HashMap<String, String> csiToPojoTransparency;
	static HashMap<String, String> pojoToCsiTransparency;
	
	{
		csiToPojoTransparency = new HashMap<String, String>();
		pojoToCsiTransparency = new HashMap<String, String>();
		csiToPojoTransparency.put(Transparency.OPAQUE.name(), OccurrenceParticipationTransparency.Opaque.name());
		csiToPojoTransparency.put(Transparency.TRANSPARENT.name(), OccurrenceParticipationTransparency.Transparent.name());
		csiToPojoTransparency.put(Transparency.TENTATIVE.name(), OccurrenceParticipationTransparency.Tentative.name()); 
		csiToPojoTransparency.put(Transparency.OUT_OF_OFFICE.name(), OccurrenceParticipationTransparency.OutOfOffice.name());
		csiToPojoTransparency.put(Transparency.DEFAULT_TRANSPARENCY.name(), OccurrenceParticipationTransparency.DefaultTransparency.name()); 
		for (String key : csiToPojoTransparency.keySet()) {
			pojoToCsiTransparency.put(csiToPojoTransparency.get(key), key);
		}
	}
	
	enum OccurrenceEditMode {
		OrganizerCopy,
		AttendeeCopy
	}
	
	protected BdkMeetingDAO() {	
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return InvitationHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		BdkMeeting csiBdkMeeting = null;
		try {
			BdkCalendarControl control = ControlLocator.getInstance().getControl(BdkCalendarControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			InvitationHandle csiBdkMeetingHandle = (InvitationHandle) EntityUtils.getInstance().createHandle(id);
			csiBdkMeeting = control.loadMeeting(csiBdkMeetingHandle, proj);		
		} catch (CsiException ex) {
		}
		return csiBdkMeeting;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		super.copyObjectState(managedObj, csiEntity, proj);
		
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		BdkMeeting csiBdkMeeting = (BdkMeeting) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ArtifactInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(), csiBdkMeeting.getTextDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.editMode.name(), lastLoadedProjection, proj)) {
			try {
				boolean isInvitationOnly = csiBdkMeeting.isInvitationOnly();
				String modeName = null;
				if (isInvitationOnly) {
					modeName = OccurrenceEditMode.AttendeeCopy.name();
				} else {
					modeName = OccurrenceEditMode.OrganizerCopy.name();
				}
				assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.editMode.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceEditModeEnum.name(), 
						modeName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.occurrenceSeries.name(), lastLoadedProjection, proj)) {
			try {
				BdkMeetingSeries csiBdkMeetingSeries = csiBdkMeeting.getSeries();
				if (csiBdkMeetingSeries != null) {
					ManagedIdentifiableProxy occurrenceSeriesObj = getEntityProxy(context, csiBdkMeetingSeries);
					Persistent occurrenceSeriesPojoEntity = occurrenceSeriesObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.occurrenceSeries.name(), occurrenceSeriesPojoEntity);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.fromRecurringOccurrenceSeries.name(), lastLoadedProjection, proj)) {
			try {
				boolean isFromRecurring = csiBdkMeeting.isFromRecurring();
				assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.fromRecurringOccurrenceSeries.name(), new Boolean(isFromRecurring));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.exceptionToOccurrenceSeries.name(), lastLoadedProjection, proj)) {
			try {
				boolean isException = csiBdkMeeting.isException();
				assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.exceptionToOccurrenceSeries.name(), new Boolean(isException));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.startDate.name(), lastLoadedProjection, proj)) {
			try {
				XMLGregorianCalendar csiStartTime = csiBdkMeeting.getStart();
				if (csiStartTime != null) {
					Date pojoStartTime = csiStartTime.toGregorianCalendar().getTime();
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.startDate.name(), pojoStartTime);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.startDateResolution.name(), lastLoadedProjection, proj)) {
			try {
				boolean startDateOnly = csiBdkMeeting.isStartDateOnly();
				if (startDateOnly) {
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.startDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Date.name());
				} else {
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.startDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Time.name());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.endDate.name(), lastLoadedProjection, proj)) {
			try {
				XMLGregorianCalendar csiEndTime = csiBdkMeeting.getEnd();
				if (csiEndTime != null) {
					Date pojoEndTime = csiEndTime.toGregorianCalendar().getTime();
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.endDate.name(), pojoEndTime);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.endDateResolution.name(), lastLoadedProjection, proj)) {
			try {
				boolean endDateOnly = csiBdkMeeting.isEndDateOnly();
				if (endDateOnly) {
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.endDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Date.name());
				} else {
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.endDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Time.name());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.location.name(), lastLoadedProjection, proj)) {
			try {
				String location = csiBdkMeeting.getLocationName();
				ManagedObjectProxy locationObj = getNonIdentifiableDependentProxy(context, IcomBeanEnumeration.Location.name(), obj, OccurrenceInfo.Attributes.location.name());
				Persistent pojoLocation = locationObj.getPojoObject();
				assignAttributeValue(pojoLocation, LocationInfo.Attributes.name.name(), location);
				assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.location.name(), pojoLocation);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.organizer.name(), lastLoadedProjection, proj)) {
			try {
				Participant csiParticipant = csiBdkMeeting.getOrganizer();
				if (csiParticipant != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiParticipant, obj, OccurrenceInfo.Attributes.organizer.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiParticipant, proj);
					
					Object previousPojoObject = AbstractDAO.getAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.organizer.name());
					if (previousPojoObject != null) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
					
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.organizer.name(), participantObj.getPojoObject());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.participants.name(), lastLoadedProjection, proj)) {
			try {
				Collection<BdkMeetingParticipant> csiBdkMeetingParticipants = csiBdkMeeting.getParticipants();
				Vector<Object> v = new Vector<Object>(csiBdkMeetingParticipants.size());
				for (BdkMeetingParticipant csiBdkMeetingParticipant : csiBdkMeetingParticipants) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiBdkMeetingParticipant, obj, OccurrenceInfo.Attributes.participants.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiBdkMeetingParticipant, proj);
					v.add(participantObj.getPojoObject());
				}
				
				Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, OccurrenceInfo.Attributes.participants.name());
				if (previousPojoObjects != null) {
					for (Object previousPojoObject : previousPojoObjects) {
						if (previousPojoObject instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.participants.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}		
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.occurrenceStatus.name(), lastLoadedProjection, proj)) {
			try {
				OccurrenceStatus status = csiBdkMeeting.getStatus();
				if (status != null) {
					String pojoOccurrenceStatusName = csiToPojoOccurrenceStatus.get(status.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.occurrenceStatus.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceStatusEnum.name(), pojoOccurrenceStatusName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.occurrenceStatus.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.occurrenceType.name(), lastLoadedProjection, proj)) {
			try {
				OccurrenceType type = csiBdkMeeting.getType();
				if (type != null) {
					String pojoOccurrenceTypeName = csiToPojoOccurrenceType.get(type.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.occurrenceType.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceTypeEnum.name(), pojoOccurrenceTypeName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.occurrenceType.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.attendee.name(), lastLoadedProjection, proj)) {
			try {
				Participant csiInvitee = csiBdkMeeting.getInvitee();
				if (csiInvitee != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiInvitee, obj, OccurrenceInfo.Attributes.attendee.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiInvitee, proj);
					
					Object previousPojoObject = AbstractDAO.getAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.attendee.name());
					if (previousPojoObject != null) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
					
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.attendee.name(), participantObj.getPojoObject());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.priority.name(), lastLoadedProjection, proj)) {
			try {
				Priority priority = csiBdkMeeting.getInviteePriority();
				if (priority != null) {
					String pojoPriorityName = csiToPojoPriorityNameMap.get(priority.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.priority.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PriorityEnum.name(), pojoPriorityName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.priority.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.attendeeParticipantStatus.name(), lastLoadedProjection, proj)) {
			try {
				OccurrenceParticipantStatus status = csiBdkMeeting.getInviteeParticipantStatus();
				if (status != null) {
					String pojoParticipantStatusName = csiToPojoOccurrenceParticipantStatus.get(status.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.attendeeParticipantStatus.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceParticipantStatusEnum.name(), pojoParticipantStatusName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.attendeeParticipantStatus.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.transparency.name(), lastLoadedProjection, proj)) {
			try {
				Transparency transparency = csiBdkMeeting.getInviteeEffectiveTransparency();
				if (transparency != null) {
					String pojoTransparencyName = csiToPojoTransparency.get(transparency.name());
					assignEnumConstant(pojoIdentifiable, OccurrenceInfo.Attributes.transparency.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceParticipantTransparencyEnum.name(), pojoTransparencyName);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.transparency.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.attendeeProperties.name(), lastLoadedProjection, proj)) {
			try {
				Vector<Object> v = new Vector<Object>();
				CollabProperties propertyMaps = csiBdkMeeting.getInviteeProperties();
				if (propertyMaps != null) {
					Collection<CollabProperty> attendeeProperties = propertyMaps.values();
					Iterator<CollabProperty> iter = attendeeProperties.iterator();
					while (iter.hasNext()) {
						CollabProperty csiCollabProperty = iter.next();
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, csiCollabProperty, obj, OccurrenceInfo.Attributes.attendeeProperties.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, csiCollabProperty, proj);
						v.add(propertyObj.getPojoObject());
					}
				}
				
				Collection<Object> previousProperties = getObjectCollection(pojoIdentifiable, OccurrenceInfo.Attributes.attendeeProperties.name());
				if (previousProperties != null) {
					for (Object pojoProperty : previousProperties) {
						if (pojoProperty instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.attendeeProperties.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(OccurrenceInfo.Attributes.conferences.name(), lastLoadedProjection, proj)) {
			try {
				Conference conference = csiBdkMeeting.getOnlineConference();
				if (conference != null) {
					Collection<Persistent> pojoConferences = new Vector<Persistent>(1);
					ManagedIdentifiableProxy conferenceObj = getEntityProxy(context, conference);
					Persistent conferencePojoEntity = conferenceObj.getPojoIdentifiable();
					pojoConferences.add(conferencePojoEntity);
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.conferences.name(), pojoConferences);
				} else {
					assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.conferences.name(), new Vector<Persistent>(1));
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	public void loadAttachments(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoIdentifiable = obj.getPojoObject();
		Invitation csiInvitation = null;
		try {
			CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			InvitationHandle csiInvitationHandle = (InvitationHandle) EntityUtils.getInstance().createHandle(id);
			csiInvitation = control.loadInvitation(csiInvitationHandle, proj);
			Set<Attachable> csiAttachments = csiInvitation.getAttachments();
			if (csiAttachments != null) {
				Set<Persistent> pojoNewAttachments = new HashSet<Persistent>(csiAttachments.size());
				for (Attachable csiAttachment : csiAttachments) {
					String pojoClassName = IcomBeanEnumeration.AttachedItem.name();
					ManagedObjectProxy attachmentObj = getNonIdentifiableDependentProxy(context, pojoClassName, obj, OccurrenceInfo.Attributes.attachments.name());
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
				assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.attachments.name(), pojoNewAttachments);
			} else {
				assignAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.attachments.name(), new HashSet<Persistent>());
			}
		} catch (CsiException ex) {
		}
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		if (OccurrenceInfo.Attributes.attachments.name().equals(attributeName)) {
			loadAttachments(obj, Projection.FULL);
		}
		super.loadAndCopyObjectState(obj, attributeName, parameter);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		BdkMeetingUpdater updater = (BdkMeetingUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, ArtifactInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name());
			updater.setTextDescription(description);
		}
		
		if (isChanged(obj, OccurrenceInfo.Attributes.startDate.name())) {
			Date startDate = (Date) getAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.startDate.name());
			if (startDate != null) {
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(startDate);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					updater.setStart(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
			} else {
				updater.setStart(null);
			}
		}
		
		// TODO OccurrenceInfo.Attributes.startDateResolution
		
		if (isChanged(obj, OccurrenceInfo.Attributes.endDate.name())) {
			Date endDate = (Date) getAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.endDate.name());
			if (endDate != null) {
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(endDate);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					updater.setEnd(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
			} else {
				updater.setEnd(null);
			}
		}
		
		// TODO OccurrenceInfo.Attributes.endDateResolution
		
		if (isChanged(obj, OccurrenceInfo.Attributes.location.name())) {
			Persistent pojoLocation = (Persistent) getAttributeValue(pojoIdentifiable, OccurrenceInfo.Attributes.location.name());
			if (pojoLocation != null) {
				String location = (String) getAttributeValue(pojoLocation, LocationInfo.Attributes.name.name());
				updater.setLocationName(location);
			} else {
				updater.setLocationName(null);
			}
		}
		
		if (isChanged(obj, OccurrenceInfo.Attributes.occurrenceStatus.name())) {
			String occurrenceStatusName = getEnumName(pojoIdentifiable, OccurrenceInfo.Attributes.occurrenceStatus.name());
			String csiOccurrenceStatusName = pojoToCsiOccurrenceStatus.get(occurrenceStatusName);
			if (csiOccurrenceStatusName != null) {
				OccurrenceStatus status = OccurrenceStatus.valueOf(csiOccurrenceStatusName);
				updater.setStatus(status);
			}
		}
		
		// TODO OccurrenceInfo.Attributes.occurrencePriority
		
		// TODO OccurrenceInfo.Attributes.occurrenceType
		
		if (isChanged(obj, OccurrenceInfo.Attributes.participants.name())) {
			Collection<BdkMeetingParticipantUpdater> participants = new ArrayList<BdkMeetingParticipantUpdater>();
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(OccurrenceInfo.Attributes.participants.name());
			if (addedObjects != null) {
				for (ValueHolder holder: addedObjects) {
					Persistent participant = (Persistent) holder.getValue();
					BdkMeetingParticipantUpdater participantUpdater = new BdkMeetingParticipantUpdater();
					BdkMeetingParticipantDAO.getInstance().updateObjectState(participant, participantUpdater, BdkMeetingParticipantDAO.Operand.ADD);
					participants.add(participantUpdater);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(OccurrenceInfo.Attributes.participants.name());
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

		if (isChanged(obj, OccurrenceInfo.Attributes.priority.name())) {
			String attendeePriorityName = getEnumName(pojoIdentifiable, OccurrenceInfo.Attributes.priority.name());
			String csiPriorityName = pojoToCsiPriorityNameMap.get(attendeePriorityName);
			if (csiPriorityName != null) {
				Priority priority = Priority.valueOf(csiPriorityName);
				updater.setInviteePriority(priority);
			}
		}
		
		if (isChanged(obj, OccurrenceInfo.Attributes.attendeeParticipantStatus.name())) {
			String attendeeParticipantStatusName = getEnumName(pojoIdentifiable, OccurrenceInfo.Attributes.attendeeParticipantStatus.name());
			String csiParticipantStatusName = pojoToCsiOccurrenceParticipantStatus.get(attendeeParticipantStatusName);
			if (csiParticipantStatusName != null) {
				OccurrenceParticipantStatus status = OccurrenceParticipantStatus.valueOf(csiParticipantStatusName);
				updater.setInviteeParticipantStatus(status);
			}
		}
		
		if (isChanged(obj, OccurrenceInfo.Attributes.transparency.name())) {
			String attendeeEffectiveTransparencyName = getEnumName(pojoIdentifiable, OccurrenceInfo.Attributes.transparency.name());
			String csiTransparencyName = pojoToCsiTransparency.get(attendeeEffectiveTransparencyName);
			if (csiTransparencyName != null) {
				Transparency transparency = Transparency.valueOf(csiTransparencyName);
				updater.setInviteeTransparency(transparency);
			}
		}
		
		if (isChanged(obj, OccurrenceInfo.Attributes.attendeeProperties.name())) {
			ArrayList<Persistent> modifiedAttendeeProperties = new ArrayList<Persistent>();
			Collection<Object> properties = getObjectCollection(pojoIdentifiable, OccurrenceInfo.Attributes.attendeeProperties.name());
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
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(OccurrenceInfo.Attributes.attendeeProperties.name());
				if (addedObjects != null) {
					Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
					while (addedObjectsIter.hasNext()) {
						ValueHolder holder = addedObjectsIter.next();
						Persistent pojoProperty = (Persistent) holder.getValue();
						CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, attendeePropertiesUpdater, CollabPropertyDAO.Operand.ADD);
						modifiedAttendeeProperties.remove(pojoProperty);
					}
				}
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(OccurrenceInfo.Attributes.attendeeProperties.name());
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
		
		// TODO OccurrenceInfo.Attributes.conference

	}
	
	void updateAttachments(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, OccurrenceInfo.Attributes.attachments.name())) {
			Persistent pojoIdentifiable = (Persistent) obj.getPojoObject();
			
			CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			InvitationHandle csiInvitationHandle = (InvitationHandle) EntityUtils.getInstance().createHandle(id);
			Invitation csiInvitation = null;
			try {
				csiInvitation = control.loadInvitation(csiInvitationHandle, Projection.BASIC);
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
			Occurrence csiOccurence = csiInvitation.getSource();
			CollabId occurrenceId = csiOccurence.getCollabId();
			OccurrenceHandle occurrenceHandle = (OccurrenceHandle) EntityUtils.getInstance().createHandle(occurrenceId);
			UpdateMode updateMode = UpdateMode.alwaysUpdate();

			ArrayList<Persistent> modifiedAttachments = new ArrayList<Persistent>();
			Collection<Object> attachments = getObjectCollection(pojoIdentifiable, OccurrenceInfo.Attributes.attachments.name());
			if (attachments != null) {
				for (Object attachment : attachments) {
					Persistent pojo = (Persistent) attachment;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.hasAttributeChanges()) {
						modifiedAttachments.add(pojo);
					}
				}
			}
			
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(OccurrenceInfo.Attributes.attachments.name());
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
					OccurrenceUpdater updater = CalendarFactory.getInstance().createOccurrenceUpdater();
					AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
					IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(eid);
					simpleContentUpdater.setName(rawname);
					DAOContext daoContext = new DAOContext(simpleContentUpdater);
					CalendarOperationContext opContext = null;
					try {
						opContext = control.beginUpdateOccurrence(occurrenceHandle, updater, updateMode);
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
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(OccurrenceInfo.Attributes.attachments.name());
			if (removedObjects != null) {
				OccurrenceUpdater updater = CalendarFactory.getInstance().createOccurrenceUpdater();
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
				OccurrenceUpdater updater = CalendarFactory.getInstance().createOccurrenceUpdater();
				AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
				IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(eid);
				String name = (String) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name());
				RawString rawname = new RawString(name);
				simpleContentUpdater.setName(rawname);
				DAOContext daoContext = new DAOContext(simpleContentUpdater);
				CalendarOperationContext opContext = null;
				try {
					opContext = control.beginUpdateOccurrence(occurrenceHandle, updater, updateMode);
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
		BdkMeetingUpdater updater = new BdkMeetingUpdater();
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
		InvitationHandle invitationHandle = (InvitationHandle) EntityUtils.getInstance().createHandle(id);
		BdkMeetingUpdater updater = (BdkMeetingUpdater) context.getUpdater();
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
			BdkMeeting bdkMeeting = control.updateMeeting(invitationHandle, updater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, bdkMeeting.getSnapshotId().toString());
			return bdkMeeting;
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
		BdkMeetingUpdater updater = (BdkMeetingUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			BdkMeetingCreator creator = new BdkMeetingCreator();
			creator.setCalendar((CalendarHandle) entityHandle);
			/*
			CollabId id = getCollabId(obj.getObjectId());
			creator.setEid(id.getEid());
			*/
			creator.setMeetingUpdater(updater);
			String pojoTypeName = getEnumName(pojoIdentifiable, OccurrenceInfo.Attributes.occurrenceType.name());
			if (pojoTypeName != null) {
				String csiTypeName = pojoToCsiOccurrenceType.get(pojoTypeName);
				OccurrenceType type = OccurrenceType.valueOf(csiTypeName);
				creator.setType(type);
			}
			BdkMeeting bdkMeeting = control.createMeeting(creator, proj);
			String cidStr = bdkMeeting.getCollabId().toString();
			assignObjectId(pojoIdentifiable, cidStr);
			assignChangeToken(pojoIdentifiable, bdkMeeting.getSnapshotId().toString());
			obj.setObjectId(cidStr);
			PersistenceContext persistentContext = obj.getPersistenceContext();
			persistentContext.recacheIdentifiableDependent((ManagedIdentifiableProxy) pojoIdentifiable.getManagedObjectProxy());
			return bdkMeeting;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		InvitationHandle invitationHandle = (InvitationHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteInvitation(invitationHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

}
