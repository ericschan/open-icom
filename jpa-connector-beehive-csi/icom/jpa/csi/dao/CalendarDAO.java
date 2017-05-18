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

import icom.info.CalendarInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.Artifact;
import oracle.csi.Calendar;
import oracle.csi.CalendarHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.TimeZone;
import oracle.csi.TimeZoneHandle;
import oracle.csi.WorkspaceHandle;
import oracle.csi.controls.CalendarControl;
import oracle.csi.controls.CalendarFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.TimeZoneControl;
import oracle.csi.controls.TimeZoneFactory;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.NamePredicate;
import oracle.csi.filters.TimeZoneListFilter;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.CalendarUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class CalendarDAO extends FolderDAO implements TimeManagement {

	static CalendarDAO singleton = new CalendarDAO();
	
	public static CalendarDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(CalendarInfo.Attributes.timeZone);
	}
	
	{
		fullAttributes.add(CalendarInfo.Attributes.recurrences);
		fullAttributes.add(CalendarInfo.Attributes.elements);
	}
	
	protected CalendarDAO() {	
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return CalendarHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Calendar csiCalendar = null;
		try {
			CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			CalendarHandle csiCalendarHandle = (CalendarHandle) EntityUtils.getInstance().createHandle(id);
			csiCalendar = control.loadCalendar(csiCalendarHandle, proj);			
		} catch (CsiException ex) {
		}
		return csiCalendar;
	}
		
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		super.copyObjectState(managedObj, csiEntity, proj);
		
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		Calendar csiCalendar = (Calendar) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(CalendarInfo.Attributes.timeZone.name(), lastLoadedProjection, proj)) {
			try {
				TimeZone csiTimeZone = csiCalendar.getTimeZone();
				String csiTimeZoneName = csiTimeZone.getName(); // e.g. "America/Los_Angeles"
				java.util.TimeZone tz = java.util.TimeZone.getTimeZone(csiTimeZoneName);
				assignAttributeValue(pojoIdentifiable, CalendarInfo.Attributes.timeZone.name(), tz);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(CalendarInfo.Attributes.recurrences.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CalendarInfo.Attributes.recurrences.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(CalendarInfo.Attributes.recurrences.name());
				ListResult<? extends Entity> list = csiCalendar.getInvitationSeries();
				Vector<Persistent> v = new Vector<Persistent>(list.size());
				for (Entity csiInvitationSeries : list) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent invitationSeries = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(invitationSeries.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiInvitationSeries.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiInvitationSeries);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, CalendarInfo.Attributes.recurrences.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(CalendarInfo.Attributes.elements.name(), lastLoadedProjection, proj)) {
			try {
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CalendarInfo.Attributes.elements.name());
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(CalendarInfo.Attributes.elements.name());
				ListResult<? extends Artifact> list = csiCalendar.getInvitations();
				Vector<Persistent> v = new Vector<Persistent>(list.size());
				for (Artifact csiArtifact : list) {
					boolean isRemoved = false;
					if (removedObjects != null) {
						for (ValueHolder holder : removedObjects) {
							Persistent artifact = (Persistent) holder.getValue();
							CollabId id = getCollabId(((ManagedIdentifiableProxy)(artifact.getManagedObjectProxy())).getObjectId());
							if (id.equals(csiArtifact.getCollabId())) {
								isRemoved = true;
								break;
							}
						}	
					}
					if (! isRemoved) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiArtifact);
						v.add(childObj.getPojoIdentifiable());
					}	
				}
				if (addedObjects != null) {
					for (ValueHolder holder : addedObjects) {
						Persistent identifiable = (Persistent) holder.getValue();
						v.add(identifiable);
					}
				}
				assignAttributeValue(pojoIdentifiable, CalendarInfo.Attributes.elements.name(), v);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		CalendarUpdater updater = (CalendarUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoObject();
		
		if (isChanged(obj, CalendarInfo.Attributes.timeZone.name())) {
			java.util.TimeZone tz = (java.util.TimeZone) getAttributeValue(pojoIdentifiable, CalendarInfo.Attributes.timeZone.name());
			if (tz != null) {
				TimeZoneControl control = ControlLocator.getInstance().getControl(TimeZoneControl.class);
				TimeZoneListFilter tzListFilter = TimeZoneFactory.getInstance().createTimeZoneListFilter();
				NamePredicate pred = tzListFilter.createNamePredicate(tz.getDisplayName());
				tzListFilter.setPredicate(pred);
				tzListFilter.setProjection(Projection.FULL);
				try {
					ListResult<TimeZone> csiTZs = control.listTimeZones(tzListFilter);
					if (csiTZs.size() > 0) {
						TimeZone csiTz = csiTZs.get(0);
						TimeZoneHandle timeZoneHandle = (TimeZoneHandle) EntityUtils.getInstance().createHandle(csiTz.getCollabId());
						updater.setTimeZone(timeZoneHandle);
					}
				} catch (CsiException ex) {
					
				}
			} else {
				updater.setTimeZone(null);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		CalendarUpdater updater = CalendarFactory.getInstance().createCalendarUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
		
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
		
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		CalendarHandle calendarHandle = (CalendarHandle) EntityUtils.getInstance().createHandle(id);
		CalendarUpdater updater = (CalendarUpdater) context.getUpdater();
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
			Calendar calendar = control.updateCalendar(calendarHandle,	updater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, calendar.getSnapshotId().toString());
			return calendar;
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
		CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
		CalendarUpdater calendarUpdater = (CalendarUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent parent = getParent(pojoIdentifiable);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(parent.getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			WorkspaceHandle workspaceHandle = (WorkspaceHandle) entityHandle;
			Calendar calendar = control.createCalendar(id.getEid(), workspaceHandle, calendarUpdater, proj);
			assignChangeToken(pojoIdentifiable, calendar.getSnapshotId().toString());
			return calendar;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		CalendarControl control = ControlLocator.getInstance().getControl(CalendarControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		CalendarHandle csiCalendarHandle = (CalendarHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteCalendar(csiCalendarHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

}
