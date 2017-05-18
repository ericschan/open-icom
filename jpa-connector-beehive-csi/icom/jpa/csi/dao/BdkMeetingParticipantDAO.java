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
import icom.info.IcomBeanEnumeration;
import icom.info.OccurrenceParticipantInfo;
import icom.info.ParticipantInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiAbstractDAO;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.net.URI;
import java.util.HashMap;

import oracle.csi.Addressable;
import oracle.csi.AddressableHandle;
import oracle.csi.BdkMeetingParticipant;
import oracle.csi.CollabId;
import oracle.csi.OccurrenceParticipantStatus;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.BdkMeetingParticipantUpdater;

public class BdkMeetingParticipantDAO extends CsiAbstractDAO {

	static BdkMeetingParticipantDAO singleton = new BdkMeetingParticipantDAO();
	
	public static BdkMeetingParticipantDAO getInstance() {
		return singleton;
	}
	
	enum Operand { ADD, REMOVE, MODIFY };
	
	public enum PojoOccurrenceParticipantStatus {
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
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.NEEDS_ACTION.name(), PojoOccurrenceParticipantStatus.NeedsAction.name());
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.ACCEPTED.name(), PojoOccurrenceParticipantStatus.Accepted.name());
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.DECLINED.name(), PojoOccurrenceParticipantStatus.Declined.name()); 
		csiToPojoOccurrenceParticipantStatus.put(OccurrenceParticipantStatus.TENTATIVE.name(), PojoOccurrenceParticipantStatus.Tentative.name());
		for (String key : csiToPojoOccurrenceParticipantStatus.keySet()) {
			pojoToCsiOccurrenceParticipantStatus.put(csiToPojoOccurrenceParticipantStatus.get(key), key);
		}
	}
	
	BdkMeetingParticipantDAO() {
		super();
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiObject, Projection proj) {
		Persistent pojoParticipant = managedObj.getPojoObject();
		BdkMeetingParticipant csiBdkMeetingParticipant = (BdkMeetingParticipant) csiObject;
		Addressable csiAddressable = csiBdkMeetingParticipant.getParticipant();
		if (csiAddressable != null) {
			PersistenceContext context = managedObj.getPersistenceContext();
			ManagedIdentifiableProxy addressableObj = getEntityProxy(context, csiAddressable);
			Persistent pojoAddressable = addressableObj.getPojoIdentifiable();
			assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.participant.name(), pojoAddressable);
		}
		URI address = csiBdkMeetingParticipant.getAddress();
		AbstractDAO.assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.address.name(), address);
		String name = csiBdkMeetingParticipant.getName();
		AbstractDAO.assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.name.name(), name);
		OccurrenceParticipantStatus status = csiBdkMeetingParticipant.getParticipantStatus();
		if (status != null) {
			String pojoStatusName = csiToPojoOccurrenceParticipantStatus.get(status.name());
			assignEnumConstant(pojoParticipant, OccurrenceParticipantInfo.Attributes.participantStatus.name(), 
					BeanHandler.getBeanPackageName(), IcomBeanEnumeration.OccurrenceParticipantStatusEnum.name(), 
					pojoStatusName);
		} else {
			assignAttributeValue(pojoParticipant, OccurrenceParticipantInfo.Attributes.participantStatus.name(), null);
		}
	}
	
	public void updateObjectState(Object pojoParticipant, BdkMeetingParticipantUpdater updater, Operand operand) {
		Persistent pojoAddressable = (Persistent) getAttributeValue(pojoParticipant, ParticipantInfo.Attributes.participant.name());
		if (pojoAddressable != null) {
			CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoAddressable.getManagedObjectProxy()).getObjectId());
			AddressableHandle addressableHandle = (AddressableHandle) EntityUtils.getInstance().createHandle(id);
			updater.setParticipant(addressableHandle);
		}
		URI address = (URI) getAttributeValue(pojoParticipant, ParticipantInfo.Attributes.address.name());
		updater.setAddress(address);
		String name = (String) getAttributeValue(pojoParticipant, ParticipantInfo.Attributes.name.name());
		updater.setName(name);
		if (operand == Operand.ADD) {
			updater.setOperation(BdkMeetingParticipantUpdater.Operation.ADD);
		} else if (operand == Operand.REMOVE) {
			updater.setOperation(BdkMeetingParticipantUpdater.Operation.REMOVE);
		}
	}
}
