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

import icom.info.BeanHandler;
import icom.info.FreeBusyInfo;
import icom.jpa.Dependent;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiClassHelper;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.ProviderClassPojoClassMap;
import icom.jpa.rt.PersistenceContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.persistence.PersistenceException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import oracle.csi.ActorHandle;
import oracle.csi.BdkFreeBusyInterval;
import oracle.csi.BdkParticipantFreeBusy;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Participant;
import oracle.csi.controls.BdkCalendarControl;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.updaters.ParticipantUpdater;

public class BdkFreeBusyDAO {

	static BdkFreeBusyDAO singleton = new BdkFreeBusyDAO();
	
	public static BdkFreeBusyDAO getInstance() {
		return singleton;
	}
	
	BdkFreeBusyDAO() {
		super();
	}
	
	public CollabId getCollabId(Object id) {
		CollabId collabId = CollabId.parseCollabId(id.toString());
		return collabId;
	}
	
	public Object loadFreeBusyOfActor(ManagedIdentifiableProxy actorObj, Date startDate, Date endDate) {
		CollabId actorObjId = getCollabId(actorObj.getObjectId());
		ActorHandle actorHandle = (ActorHandle) EntityUtils.getInstance().createHandle(actorObjId);
		ParticipantUpdater participantUpdater = new ParticipantUpdater();
		participantUpdater.setParticipant(actorHandle);
		XMLGregorianCalendar xgcalStartDate = null;
		try {
			GregorianCalendar gcalStartDate = new GregorianCalendar();
			gcalStartDate.setTime(startDate);
			xgcalStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalStartDate);
		} catch (DatatypeConfigurationException ex) {
			throw new PersistenceException(ex);
		}
		XMLGregorianCalendar xgcalEndDate = null;
		try {
			GregorianCalendar gcalEndDate = new GregorianCalendar();
			gcalEndDate.setTime(endDate);
			xgcalEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalEndDate);
		} catch (DatatypeConfigurationException ex) {
			throw new PersistenceException(ex);
		}
		BdkParticipantFreeBusy csiBdkFreeBusy = null;
		try {
			BdkCalendarControl control = ControlLocator.getInstance().getControl(BdkCalendarControl.class);
			csiBdkFreeBusy = control.computeParticipantFreeBusy(participantUpdater, xgcalStartDate, xgcalEndDate);
			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		Object pojoFreeBusy = null;
		ProviderClassPojoClassMap map = null;
		try {
			map = CsiClassHelper.getInstance().mapProviderClassToPojoClass(csiBdkFreeBusy.getClass());
			pojoFreeBusy = BeanHandler.instantiatePojoObject(map.pojoClass);
		} catch (Throwable ex) {
			throw new PersistenceException("entity class not valid :" + map.pojoSimpleClassName);
		}
		PersistenceContext context = actorObj.getPersistenceContext();
		copyObjectState(context, pojoFreeBusy, csiBdkFreeBusy);
		return pojoFreeBusy;
	}

	public void copyObjectState(PersistenceContext context, Object pojoFreeBusy, BdkParticipantFreeBusy csiBdkFreeBusy) {
		try {
			XMLGregorianCalendar bdkCreatedOn = csiBdkFreeBusy.getCreatedOn();
			if (bdkCreatedOn != null) {
				Date pojoCreationTime = bdkCreatedOn.toGregorianCalendar().getTime();
				AbstractDAO.assignAttributeValue(pojoFreeBusy, FreeBusyInfo.Attributes.creationDate.name(), pojoCreationTime);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		try {
			XMLGregorianCalendar bdkStart = csiBdkFreeBusy.getStart();
			if (bdkStart != null) {
				Date pojoStartTime = bdkStart.toGregorianCalendar().getTime();
				AbstractDAO.assignAttributeValue(pojoFreeBusy, FreeBusyInfo.Attributes.startDate.name(), pojoStartTime);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		try {
			XMLGregorianCalendar bdkEnd = csiBdkFreeBusy.getEnd();
			if (bdkEnd != null) {
				Date pojoEndTime = bdkEnd.toGregorianCalendar().getTime();
				AbstractDAO.assignAttributeValue(pojoFreeBusy, FreeBusyInfo.Attributes.endDate.name(), pojoEndTime);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		try {
			List<BdkFreeBusyInterval> intervals = csiBdkFreeBusy.getIntervals();
			if (intervals != null) {
				Vector<Object> v = new Vector<Object>(intervals.size());
				for (BdkFreeBusyInterval csiBdkFreeBusyInterval : intervals) {
					Object pojoFreeBusyInterval = null;
					ProviderClassPojoClassMap map = null;
					try {
						map = CsiClassHelper.getInstance().mapProviderClassToPojoClass(csiBdkFreeBusyInterval.getClass());
						pojoFreeBusyInterval = BeanHandler.instantiatePojoObject(map.pojoClass);
					} catch (Throwable ex) {
						throw new PersistenceException("entity class not valid :" + map.pojoSimpleClassName);
					}
					BdkFreeBusyIntervalDAO.getInstance().copyObjectState(context, pojoFreeBusyInterval, csiBdkFreeBusyInterval);
					v.add(pojoFreeBusyInterval);
				}
				AbstractDAO.assignAttributeValue(pojoFreeBusy, FreeBusyInfo.Attributes.intervals.name(), v);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		try {
			Participant csiParticipant = csiBdkFreeBusy.getParticipant();
			Persistent pojoParticipant = null;
			ProviderClassPojoClassMap map = null;
			try {
				map = CsiClassHelper.getInstance().mapProviderClassToPojoClass(csiParticipant.getClass());
				pojoParticipant = (Dependent) BeanHandler.instantiatePojoObject(map.pojoClass);
			} catch (Throwable ex) {
				throw new PersistenceException("entity class not valid :" + map.pojoSimpleClassName);
			}
			ParticipantDAO.getInstance().copyObjectState(context, pojoParticipant, csiParticipant);
			ArrayList<Object> pojoParticipants =  new ArrayList<Object>(1);
			pojoParticipants.add(pojoParticipant);
			AbstractDAO.assignAttributeValue(pojoFreeBusy, FreeBusyInfo.Attributes.participants.name(), pojoParticipants);
		} catch (CsiRuntimeException ex) {
			// ignore
		}
	}
}
