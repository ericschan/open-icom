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
import icom.info.TaskInfo;
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

import oracle.csi.Assignment;
import oracle.csi.AssignmentHandle;
import oracle.csi.Attachable;
import oracle.csi.BdkTask;
import oracle.csi.BomTypeId;
import oracle.csi.CollabId;
import oracle.csi.CollabProperties;
import oracle.csi.CollabProperty;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.Identifiable;
import oracle.csi.IdentifiableHandle;
import oracle.csi.IdentifiableSimpleContentHandle;
import oracle.csi.Participant;
import oracle.csi.Priority;
import oracle.csi.RawString;
import oracle.csi.SnapshotId;
import oracle.csi.TaskListHandle;
import oracle.csi.TaskOperationContext;
import oracle.csi.Todo;
import oracle.csi.TodoHandle;
import oracle.csi.TodoParticipantStatus;
import oracle.csi.TodoStatus;
import oracle.csi.controls.BdkTaskControl;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.TaskControl;
import oracle.csi.controls.TaskFactory;
import oracle.csi.creators.BdkTaskCreator;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttachmentListUpdater;
import oracle.csi.updaters.BdkTaskUpdater;
import oracle.csi.updaters.IdentifiableSimpleContentUpdater;
import oracle.csi.updaters.TodoUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;
import oracle.ocs.cspi.controls.OcsIdFactory;

public class BdkTaskDAO extends ArtifactDAO implements TimeManagement {

	static BdkTaskDAO singleton = new BdkTaskDAO();
	
	public static BdkTaskDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(ArtifactInfo.Attributes.description);
		basicAttributes.add(TaskInfo.Attributes.editMode);
		basicAttributes.add(TaskInfo.Attributes.startDate);
		basicAttributes.add(TaskInfo.Attributes.startDateResolution);
		basicAttributes.add(TaskInfo.Attributes.dueDate);
		basicAttributes.add(TaskInfo.Attributes.dueDateResolution);
		basicAttributes.add(TaskInfo.Attributes.organizer);  // read-only
		basicAttributes.add(TaskInfo.Attributes.taskStatus);
		basicAttributes.add(TaskInfo.Attributes.location);
		basicAttributes.add(TaskInfo.Attributes.assignee);           // read-only
		basicAttributes.add(TaskInfo.Attributes.completionDate);
		basicAttributes.add(TaskInfo.Attributes.completionDateResolution);
		basicAttributes.add(TaskInfo.Attributes.percentComplete);
		basicAttributes.add(TaskInfo.Attributes.participantStatus);
		basicAttributes.add(TaskInfo.Attributes.priority);
	}
	
	{
		metaAttributes.add(TaskInfo.Attributes.assigneeProperties);
	}
	
	{
		//fullAttributes.add(TaskInfo.Attributes.attachments);
	}
	
	{
		lazyAttributes.add(TaskInfo.Attributes.attachments);
	}
	
	enum PojoTaskStatus {
		NeedsAction,
		InProgress,
		Completed,
		Cancelled
	}
	
	static HashMap<String, String> csiToPojoTaskStatus;
	static HashMap<String, String> pojoToCsiTaskStatus;
	
	{
		csiToPojoTaskStatus = new HashMap<String, String>();
		pojoToCsiTaskStatus = new HashMap<String, String>();
		csiToPojoTaskStatus.put(TodoStatus.NEEDS_ACTION.name(), PojoTaskStatus.NeedsAction.name());
		csiToPojoTaskStatus.put(TodoStatus.CANCELLED.name(), PojoTaskStatus.Cancelled.name());
		csiToPojoTaskStatus.put(TodoStatus.COMPLETED.name(), PojoTaskStatus.Completed.name());
		csiToPojoTaskStatus.put(TodoStatus.IN_PROCESS.name(), PojoTaskStatus.InProgress.name()); 
		for (String key : csiToPojoTaskStatus.keySet()) {
			pojoToCsiTaskStatus.put(csiToPojoTaskStatus.get(key), key);
		}
	}
	
	enum PojoTaskParticipantStatus {
		NeedsAction,
		Accepted,
		Declined,
		InProgress,
		Completed,
		WaitingOnOther,
		Tentative,
		Deferred
	}
	
	static HashMap<String, String> csiToPojoTaskParticipantStatus;
	static HashMap<String, String> pojoToCsiTaskParticipantStatus;
	
	{
		csiToPojoTaskParticipantStatus = new HashMap<String, String>();
		pojoToCsiTaskParticipantStatus = new HashMap<String, String>();
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.ACCEPTED.name(), PojoTaskParticipantStatus.Accepted.name());
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.DECLINED.name(), PojoTaskParticipantStatus.Declined.name());
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.NEEDS_ACTION.name(), PojoTaskParticipantStatus.NeedsAction.name()); 
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.COMPLETED.name(), PojoTaskParticipantStatus.Completed.name());
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.IN_PROCESS.name(), PojoTaskParticipantStatus.InProgress.name());
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.WAITING_ON_OTHER.name(), PojoTaskParticipantStatus.WaitingOnOther.name()); 
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.TENTATIVE.name(), PojoTaskParticipantStatus.Tentative.name()); 
		csiToPojoTaskParticipantStatus.put(TodoParticipantStatus.DEFERRED.name(), PojoTaskParticipantStatus.Deferred.name());
		for (String key : csiToPojoTaskParticipantStatus.keySet()) {
			pojoToCsiTaskParticipantStatus.put(csiToPojoTaskParticipantStatus.get(key), key);
		}
	}
	
	enum TaskEditMode {
		OrganizerCopy,
		AssigneeCopy
	}
	
	
	protected BdkTaskDAO() {	
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return AssignmentHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		BdkTask csiBdkTask = null;
		try {
			BdkTaskControl control = ControlLocator.getInstance().getControl(BdkTaskControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			AssignmentHandle csiBdkTaskHandle = (AssignmentHandle) EntityUtils.getInstance().createHandle(id);
			csiBdkTask = control.loadTask(csiBdkTaskHandle, proj);		
		} catch (CsiException ex) {
		}
		return csiBdkTask;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		super.copyObjectState(managedObj, csiEntity, proj);
		
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		BdkTask csiBdkTask = (BdkTask) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ArtifactInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(), csiBdkTask.getTextDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.editMode.name(), lastLoadedProjection, proj)) {
			try {
				boolean isAssignmentOnly = csiBdkTask.isAssignmentOnly();
				String modeName = null;
				if (isAssignmentOnly) {
					modeName = TaskEditMode.AssigneeCopy.name();
				} else {
					modeName = TaskEditMode.OrganizerCopy.name();
				}
				assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.editMode.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.TaskEditModeEnum.name(), 
						modeName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.startDate.name(), lastLoadedProjection, proj)) {
			try {
				XMLGregorianCalendar csiStartTime = csiBdkTask.getAssigneeStart();
				if (csiStartTime != null) {
					Date pojoStartTime = csiStartTime.toGregorianCalendar().getTime();
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.startDate.name(), pojoStartTime);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.startDateResolution.name(), lastLoadedProjection, proj)) {
			try {
				boolean startDateOnly = csiBdkTask.isAssigneeStartDateOnly();
				if (startDateOnly) {
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.startDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Date.name());
				} else {
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.startDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Time.name());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.dueDate.name(), lastLoadedProjection, proj)) {
			try {
				XMLGregorianCalendar csiDueTime = csiBdkTask.getAssigneeDue();
				if (csiDueTime != null) {
					Date pojoDueTime = csiDueTime.toGregorianCalendar().getTime();
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.dueDate.name(), pojoDueTime);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.dueDateResolution.name(), lastLoadedProjection, proj)) {
			try {
				boolean dueDateOnly = csiBdkTask.isAssigneeDueDateOnly();
				if (dueDateOnly) {
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.dueDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Date.name());
				} else {
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.dueDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Time.name());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.organizer.name(), lastLoadedProjection, proj)) {
			try {
				Participant csiParticipant = csiBdkTask.getOrganizer();
				if (csiParticipant != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiParticipant, obj, TaskInfo.Attributes.organizer.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiParticipant, proj);
					
					Object previousPojoObject = AbstractDAO.getAttributeValue(pojoIdentifiable, TaskInfo.Attributes.organizer.name());
					if (previousPojoObject != null) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
					
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.organizer.name(), participantObj.getPojoObject());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.taskStatus.name(), lastLoadedProjection, proj)) {
			try {
				TodoStatus status = csiBdkTask.getStatus();
				if (status != null) {
					String pojoTaskStatusName = csiToPojoTaskStatus.get(status.name());
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.taskStatus.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.TaskStatusEnum.name(), pojoTaskStatusName);
				} else {
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.taskStatus.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.location.name(), lastLoadedProjection, proj)) {
			try {
				String location = csiBdkTask.getLocationName();
				ManagedObjectProxy locationObj = getNonIdentifiableDependentProxy(context, IcomBeanEnumeration.Location.name(), obj, TaskInfo.Attributes.location.name());
				Persistent pojoLocation = locationObj.getPojoObject();
				assignAttributeValue(pojoLocation, LocationInfo.Attributes.name.name(), location);
				assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.location.name(), pojoLocation);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		/*
		if (isBetweenProjections(TaskInfo.Attributes.attachments.name(), lastLoadedProjection, proj)) {
			try {
				Set<Attachable> csiAttachments = csiBdkTask.getAttachments();
				if (csiAttachments != null) {
					Set<Persistent> pojoNewAttachments = new HashSet<Persistent>(csiAttachments.size());
					for (Attachable csiAttachment : csiAttachments) {
						ManagedObjectProxy contentObj = getIdentifiableDependentProxy(context, csiAttachment, obj, TaskInfo.Attributes.attachments.name());
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, (Identifiable) csiAttachment, proj);
						pojoNewAttachments.add(contentObj.getPojoObject());
					}
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.attachments.name(), pojoNewAttachments);
				} else {
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.attachments.name(), new HashSet<Persistent>());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		*/
		
		if (isBetweenProjections(TaskInfo.Attributes.assignee.name(), lastLoadedProjection, proj)) {
			try {
				Participant csiAssignee = csiBdkTask.getAssignee();
				if (csiAssignee != null) {
					ManagedObjectProxy participantObj = getNonIdentifiableDependentProxy(context, csiAssignee, obj, TaskInfo.Attributes.assignee.name());
					participantObj.getProviderProxy().copyLoadedProjection(participantObj, csiAssignee, proj);
					
					Object previousPojoObject = AbstractDAO.getAttributeValue(pojoIdentifiable, TaskInfo.Attributes.assignee.name());
					if (previousPojoObject != null) {
						BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
						ManagedObjectProxy mop = (ManagedObjectProxy) AbstractDAO.getAttributeValue(previousPojoObject, AbstractBeanInfo.Attributes.mop.name());
						beanInfo.detachHierarchy(mop);
					}
					
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.assignee.name(), participantObj.getPojoObject());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.completionDate.name(), lastLoadedProjection, proj)) {
			try {
				XMLGregorianCalendar csiCompletionTime = csiBdkTask.getAssigneeCompleted();
				if (csiCompletionTime != null) {
					Date pojoStartTime = csiCompletionTime.toGregorianCalendar().getTime();
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.completionDate.name(), pojoStartTime);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.completionDateResolution.name(), lastLoadedProjection, proj)) {
			try {
				boolean completionDateOnly = csiBdkTask.isAssigneeStartDateOnly();
				if (completionDateOnly) {
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.completionDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Date.name());
				} else {
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.completionDateResolution.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.DateTimeResolutionEnum.name(), 
							DateTimeResolution.Time.name());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.percentComplete.name(), lastLoadedProjection, proj)) {
			try {
				int percent = csiBdkTask.getAssigneePercentComplete();
				assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.percentComplete.name(), new Integer(percent));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.priority.name(), lastLoadedProjection, proj)) {
			try {
				Priority priority = csiBdkTask.getAssigneePriority();
				if (priority != null) {
					String pojoPriorityName = csiToPojoPriorityNameMap.get(priority.name());
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.priority.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.PriorityEnum.name(), pojoPriorityName);
				} else {
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.priority.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.participantStatus.name(), lastLoadedProjection, proj)) {
			try {
				TodoParticipantStatus status = csiBdkTask.getAssigneeParticipantStatus();
				if (status != null) {
					String pojoParticipantStatusName = csiToPojoTaskParticipantStatus.get(status.name());
					assignEnumConstant(pojoIdentifiable, TaskInfo.Attributes.participantStatus.name(), 
							BeanHandler.getBeanPackageName(), IcomBeanEnumeration.TaskParticipantStatusEnum.name(), pojoParticipantStatusName);
				} else {
					assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.participantStatus.name(), null);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(TaskInfo.Attributes.assigneeProperties.name(), lastLoadedProjection, proj)) {
			try {
				Vector<Object> v = new Vector<Object>();
				CollabProperties propertyMaps = csiBdkTask.getAssigneeProperties();
				if (propertyMaps != null) {
					Collection<CollabProperty> assigneeProperties = propertyMaps.values();
					Iterator<CollabProperty> iter = assigneeProperties.iterator();
					while (iter.hasNext()) {
						CollabProperty csiCollabProperty = iter.next();
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, csiCollabProperty, obj, TaskInfo.Attributes.assigneeProperties.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, csiCollabProperty, proj);
						v.add(propertyObj.getPojoObject());
					}
				}
				
				Collection<Object> previousProperties = getObjectCollection(pojoIdentifiable, TaskInfo.Attributes.assigneeProperties.name());
				if (previousProperties != null) {
					for (Object pojoProperty : previousProperties) {
						if (pojoProperty instanceof Persistent) {
							BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
							ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
							beanInfo.detachHierarchy(mop);
						}
					}
				}
				
				assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.assigneeProperties.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	public static Eid swapEid(Eid eid) {
	    String sEid = eid.toString();
	    int l = sEid.length();
	    int mid = l / 2;
	    StringBuilder sb = new StringBuilder(l);
	    sb.append(sEid.substring(mid, l));
	    sb.append(sEid.substring(0, mid));
	    return Eid.parseEid(sb.toString());
	  }
	
	public CollabId createObjectId(Eid eid, String bomTypeName) {
		CollabId id = OcsIdFactory.getInstance().generateNewCollabId(BomTypeId.lookup(bomTypeName), eid);
		return id;
	}
	
	public void loadAttachments(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoIdentifiable = obj.getPojoObject();
		Set<Attachable> csiAttachments = null;
		try {
			TaskControl control = ControlLocator.getInstance().getControl(TaskControl.class);
			BdkTask task = (BdkTask) loadObjectState(obj, Projection.BASIC);
			Participant assignee = task.getAssignee();
			if (assignee == null) {
				Eid eid = task.getCollabId().getEid();
				eid = swapEid(eid);
				CollabId id = createObjectId(eid, "todo");
				TodoHandle csiTodoHandle = (TodoHandle) EntityUtils.getInstance().createHandle(id);
				Todo csiTodo = control.loadTodo(csiTodoHandle, proj);
				csiAttachments = csiTodo.getAttachments();
			} else {
				CollabId id = task.getCollabId();
				AssignmentHandle csiAssignmentHandle = (AssignmentHandle) EntityUtils.getInstance().createHandle(id);
				Assignment csiAssignment = control.loadAssignment(csiAssignmentHandle, proj);
				csiAttachments = csiAssignment.getAttachments();
			}
			
			if (csiAttachments != null) {
				Set<Persistent> pojoNewAttachments = new HashSet<Persistent>(csiAttachments.size());
				for (Attachable csiAttachment : csiAttachments) {
					String pojoClassName = IcomBeanEnumeration.AttachedItem.name();
					ManagedObjectProxy attachmentObj = getNonIdentifiableDependentProxy(context, pojoClassName, obj, TaskInfo.Attributes.attachments.name());
					Persistent pojoAttachment = attachmentObj.getPojoObject();
					RawString rawname = csiAttachment.getName();
					String name = null;
					try {
						name = rawname.convertToString();
					} catch (UnsupportedEncodingException ex) {
						name = rawname.getString();
					}
					assignAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name(), name);
					ManagedObjectProxy contentObj = getIdentifiableDependentProxy(context, csiAttachment, attachmentObj, AttachmentInfo.Attributes.content.name());
					contentObj.getProviderProxy().copyLoadedProjection(contentObj, (Identifiable) csiAttachment, proj);
					pojoNewAttachments.add(pojoAttachment);
				}
				assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.attachments.name(), pojoNewAttachments);
			} else {
				assignAttributeValue(pojoIdentifiable, TaskInfo.Attributes.attachments.name(), new HashSet<Persistent>());
			}
		} catch (CsiException ex) {
		}
	}
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		if (TaskInfo.Attributes.attachments.name().equals(attributeName)) {
			loadAttachments(obj, Projection.FULL);
		}
		super.loadAndCopyObjectState(obj, attributeName, parameter);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		BdkTaskUpdater updater = (BdkTaskUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, ArtifactInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name());
			updater.setTextDescription(description);
		}
		
		if (isChanged(obj, TaskInfo.Attributes.startDate.name())) {
			Date startDate = (Date) getAttributeValue(pojoIdentifiable, TaskInfo.Attributes.startDate.name());
			if (startDate != null) {
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(startDate);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					updater.setAssigneeStart(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
			} else {
				updater.setAssigneeStart(null);
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.startDateResolution.name())) {
			String dateResolutionName = getEnumName(pojoIdentifiable, TaskInfo.Attributes.startDateResolution.name());
			if (dateResolutionName != null) {
				DateTimeResolution dtResolution = DateTimeResolution.valueOf(dateResolutionName);
				if (dtResolution == DateTimeResolution.Time) {
					updater.setAssigneeStartDateOnly(true);
				} else {
					updater.setAssigneeStartDateOnly(false);
				}
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.dueDate.name())) {
			Date dueDate = (Date) getAttributeValue(pojoIdentifiable, TaskInfo.Attributes.dueDate.name());
			if (dueDate != null) {
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(dueDate);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					updater.setAssigneeDue(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
			} else {
				updater.setAssigneeDue(null);
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.dueDateResolution.name())) {
			String dateResolutionName = getEnumName(pojoIdentifiable, TaskInfo.Attributes.dueDateResolution.name());
			if (dateResolutionName != null) {
				DateTimeResolution dtResolution = DateTimeResolution.valueOf(dateResolutionName);
				if (dtResolution == DateTimeResolution.Time) {
					updater.setAssigneeDueDateOnly(true);
				} else {
					updater.setAssigneeDueDateOnly(false);
				}
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.taskStatus.name())) {
			String taskStatusName = getEnumName(pojoIdentifiable, TaskInfo.Attributes.taskStatus.name());
			String csiTaskStatusName = pojoToCsiTaskStatus.get(taskStatusName);
			if (csiTaskStatusName != null) {
				TodoStatus status = TodoStatus.valueOf(csiTaskStatusName);
				updater.setStatus(status);
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.location.name())) {
			Persistent pojoLocation = (Persistent) getAttributeValue(pojoIdentifiable, TaskInfo.Attributes.location.name());
			if (pojoLocation != null) {
				String location = (String) getAttributeValue(pojoLocation, LocationInfo.Attributes.name.name());
				updater.setLocationName(location);
			} else {
				updater.setLocationName(null);
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.completionDate.name())) {
			Date assigneeCompletionDate = (Date) getAttributeValue(pojoIdentifiable, TaskInfo.Attributes.completionDate.name());
			if (assigneeCompletionDate != null) {
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(assigneeCompletionDate);
				try {
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					updater.setAssigneeCompleted(xgcal);
				} catch (DatatypeConfigurationException ex) {
					throw new PersistenceException(ex);
				}
			} else {
				updater.setAssigneeCompleted(null);
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.completionDateResolution.name())) {
			String dateResolutionName = getEnumName(pojoIdentifiable, TaskInfo.Attributes.completionDateResolution.name());
			if (dateResolutionName != null) {
				DateTimeResolution dtResolution = DateTimeResolution.valueOf(dateResolutionName);
				if (dtResolution == DateTimeResolution.Time) {
					updater.setAssigneeCompletedDateOnly(true);
				} else {
					updater.setAssigneeCompletedDateOnly(false);
				}
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.percentComplete.name())) {
			Integer percent = (Integer) getAttributeValue(pojoIdentifiable, TaskInfo.Attributes.percentComplete.name());
			if (percent != null) {
				updater.setAssigneePercentComplete(percent);
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.priority.name())) {
			String assigneePriorityName = getEnumName(pojoIdentifiable, TaskInfo.Attributes.priority.name());
			String csiPriorityName = pojoToCsiPriorityNameMap.get(assigneePriorityName);
			if (csiPriorityName != null) {
				Priority priority = Priority.valueOf(csiPriorityName);
				updater.setAssigneePriority(priority);
			}
		}
		
		if (isChanged(obj, TaskInfo.Attributes.participantStatus.name())) {
			String assigneeParticipantStatusName = getEnumName(pojoIdentifiable, TaskInfo.Attributes.participantStatus.name());
			String csiParticipantStatusName = pojoToCsiTaskParticipantStatus.get(assigneeParticipantStatusName);
			if (csiParticipantStatusName != null) {
				TodoParticipantStatus status = TodoParticipantStatus.valueOf(csiParticipantStatusName);
				updater.setAssigneeParticipantStatus(status);
			}
		}
		
	}
	
	void updateAttachments(ManagedIdentifiableProxy obj) {
		if (isChanged(obj, TaskInfo.Attributes.attachments.name())) {
			icom.jpa.Identifiable pojoIdentifiable = (icom.jpa.Identifiable) obj.getPojoObject();
			
			TaskControl control = ControlLocator.getInstance().getControl(TaskControl.class);
			TodoHandle todoHandle = null;
			UpdateMode updateMode = UpdateMode.alwaysUpdate();
			BdkTask task = (BdkTask) loadObjectState(obj, Projection.BASIC);
			Participant assignee = task.getAssignee();
			if (assignee != null) {
				CollabId id = getCollabId(obj.getObjectId());
				AssignmentHandle csiAssignmentHandle = (AssignmentHandle) EntityUtils.getInstance().createHandle(id);
				Assignment csiAssignment = null;
				try {
					csiAssignment = control.loadAssignment(csiAssignmentHandle, Projection.BASIC);
				} catch (CsiException ex) {
					throw new PersistenceException(ex);
				}
				Todo csiTodo = csiAssignment.getSource();
				CollabId todoId = csiTodo.getCollabId();
				todoHandle = (TodoHandle) EntityUtils.getInstance().createHandle(todoId);
			} else {
				Eid eid = task.getCollabId().getEid();
				eid = swapEid(eid);
				CollabId id = createObjectId(eid, "todo");
				TodoHandle csiTodoHandle = (TodoHandle) EntityUtils.getInstance().createHandle(id);
				Todo csiTodo = null;
				try {
					csiTodo = control.loadTodo(csiTodoHandle, Projection.BASIC);
				} catch (CsiException ex) {
					throw new PersistenceException(ex);
				}
				CollabId todoId = csiTodo.getCollabId();
				todoHandle = (TodoHandle) EntityUtils.getInstance().createHandle(todoId);
			}

			ArrayList<Persistent> modifiedAttachments = new ArrayList<Persistent>();
			Collection<Object> attachments = getObjectCollection(pojoIdentifiable, TaskInfo.Attributes.attachments.name());
			if (attachments != null) {
				for (Object attachment : attachments) {
					Persistent pojo = (Persistent) attachment;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.hasAttributeChanges()) {
						modifiedAttachments.add(pojo);
					}
				}
			}
			
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(TaskInfo.Attributes.attachments.name());
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
					TodoUpdater updater = TaskFactory.getInstance().createTodoUpdater();
					AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
					IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(eid);
					simpleContentUpdater.setName(rawname);
					DAOContext daoContext = new DAOContext(simpleContentUpdater);
					TaskOperationContext opContext = null;
					try {
						opContext = control.beginUpdateTodo(todoHandle, updater, updateMode);
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
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(TaskInfo.Attributes.attachments.name());
			if (removedObjects != null) {
				TodoUpdater updater = TaskFactory.getInstance().createTodoUpdater();
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
				TodoUpdater updater = TaskFactory.getInstance().createTodoUpdater();
				AttachmentListUpdater attachmentListUpdater = updater.getAttachmentsUpdater();
				IdentifiableSimpleContentUpdater simpleContentUpdater = attachmentListUpdater.includeAttachment(eid);
				String name = (String) getAttributeValue(pojoAttachment, AttachmentInfo.Attributes.name.name());
				RawString rawname = new RawString(name);
				simpleContentUpdater.setName(rawname);
				DAOContext daoContext = new DAOContext(simpleContentUpdater);
				TaskOperationContext opContext = null;
				try {
					opContext = control.beginUpdateTodo(todoHandle, updater, updateMode);
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
		BdkTaskUpdater updater = new BdkTaskUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		BdkTaskControl control = ControlLocator.getInstance().getControl(BdkTaskControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		AssignmentHandle assignmentHandle = (AssignmentHandle) EntityUtils.getInstance().createHandle(id);
		BdkTaskUpdater updater = (BdkTaskUpdater) context.getUpdater();
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
			BdkTask bdkTask = control.updateTask(assignmentHandle, updater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, bdkTask.getSnapshotId().toString());
			return bdkTask;
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
		BdkTaskControl control = ControlLocator.getInstance().getControl(BdkTaskControl.class);
		BdkTaskUpdater updater = (BdkTaskUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			BdkTaskCreator creator = new BdkTaskCreator();
			creator.setTaskList((TaskListHandle) entityHandle);
			/*
			CollabId id = getCollabId(obj.getObjectId());
			creator.setEid(id.getEid());
			*/
			creator.setTaskUpdater(updater);
			BdkTask bdkTask = control.createTask(creator, proj);
			String cidStr = bdkTask.getCollabId().toString();
			assignObjectId(pojoIdentifiable, cidStr);
			assignChangeToken(pojoIdentifiable, bdkTask.getSnapshotId().toString());
			obj.setObjectId(cidStr);
			PersistenceContext persistentContext = obj.getPersistenceContext();
			persistentContext.recacheIdentifiableDependent((ManagedIdentifiableProxy) pojoIdentifiable.getManagedObjectProxy());
			return bdkTask;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		TaskControl control = ControlLocator.getInstance().getControl(TaskControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		AssignmentHandle assignmentHandle = (AssignmentHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteAssignment(assignmentHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
}
