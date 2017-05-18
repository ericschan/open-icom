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

import icom.info.ParticipantInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiAbstractDAO;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.net.URI;

import oracle.csi.Addressable;
import oracle.csi.AddressableHandle;
import oracle.csi.CollabId;
import oracle.csi.Participant;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.ParticipantUpdater;

public class ParticipantDAO extends CsiAbstractDAO {

	static ParticipantDAO singleton = new ParticipantDAO();
	
	public static ParticipantDAO getInstance() {
		return singleton;
	}
	
	enum Operand { ADD, REMOVE, MODIFY };
	
	ParticipantDAO() {
		super();
	}
	
	public void copyObjectState(PersistenceContext context, Persistent pojoParticipant, Participant csiParticipant) {
		Addressable csiAddressable = csiParticipant.getParticipant();
		if (csiAddressable != null) {
			ManagedIdentifiableProxy addressableObj = getEntityProxy(context, csiAddressable);
			Persistent pojoAddressable = addressableObj.getPojoIdentifiable();
			AbstractDAO.assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.participant.name(), pojoAddressable);
		}
		URI address = csiParticipant.getAddress();
		AbstractDAO.assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.address.name(), address);
		String name = csiParticipant.getName();
		AbstractDAO.assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.name.name(), name);
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiObject, Projection proj) {
		Persistent pojoParticipant = managedObj.getPojoObject();
		Participant csiParticipant = (Participant) csiObject;
		PersistenceContext context = managedObj.getPersistenceContext();
		copyObjectState(context, pojoParticipant, csiParticipant);
	}
	
	public void updateObjectState(Object pojoParticipant, ParticipantUpdater updater, Operand operand) {
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
	}
}
