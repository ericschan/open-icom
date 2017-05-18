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
import icom.info.UnifiedMessageParticipantInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.util.EnumSet;

import oracle.csi.Addressable;
import oracle.csi.AddressableHandle;
import oracle.csi.CollabId;
import oracle.csi.EmailDeliveryDirective;
import oracle.csi.EmailRecipient;
import oracle.csi.InetMailAddress;
import oracle.csi.RawString;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;

public class EmailRecipientUtil {
	
	static public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		EmailRecipient csiParticipant = (EmailRecipient) csiObject;
		Object pojoParticipant = obj.getPojoObject();
		Addressable csiAddressable = csiParticipant.getParticipant();
		if (csiAddressable != null) {
			PersistenceContext context = obj.getPersistenceContext();
			ManagedIdentifiableProxy addressableObj = EmailMessageDAO.getInstance().getEntityProxy(context, csiAddressable);
			Persistent pojoAddressable = addressableObj.getPojoIdentifiable();
			AbstractDAO.assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.participant.name(), pojoAddressable);
		}
		InetMailAddress csiAddress = csiParticipant.getAddress();
		if (csiAddress != null) {
			String localPart = csiAddress.getLocalPart();
			AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.localPart.name(), localPart);
			String domainPart = csiAddress.getDomainPart();
			AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.domainPart.name(), domainPart);
			RawString csiDisplayPart = csiAddress.getDisplayPart();
			if (csiDisplayPart != null) {
				byte[] displayPart = csiDisplayPart.getBytes();
				AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.displayPart.name(), displayPart);
				String displayCharSet = csiDisplayPart.getCharset();
				AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.displayCharacterSet.name(), displayCharSet);
			}
		}
	}
	
	static EmailRecipient updateEmailRecipient(Object unifiedMessageParticipant) {
		EmailRecipient recipient = null;
		Persistent pojoAddressable = (Persistent) AbstractDAO.getAttributeValue(unifiedMessageParticipant, ParticipantInfo.Attributes.participant.name());
		String fullAddress = (String) AbstractDAO.getAttributeValue(unifiedMessageParticipant, UnifiedMessageParticipantInfo.Attributes.fullAddress.name());
		if (pojoAddressable != null) {
			CollabId id = EmailMessageDAO.getInstance().getCollabId(((ManagedIdentifiableProxy)pojoAddressable.getManagedObjectProxy()).getObjectId());
			EnumSet<EmailDeliveryDirective> directives = null;
			if (fullAddress != null) {
				Addressable addressable = (Addressable) EntityUtils.getInstance().createEmptyEntity(id);
				InetMailAddress inetMailAddress = new InetMailAddress(fullAddress);
				recipient = new EmailRecipient(addressable, inetMailAddress, null);
			} else {
				AddressableHandle addressableHandle = (AddressableHandle) EntityUtils.getInstance().createHandle(id);
				recipient = new EmailRecipient(addressableHandle, directives);
			}
			return recipient;
		} else if (fullAddress != null) {
			EnumSet<EmailDeliveryDirective> directives = null;
			InetMailAddress inetMailAddress = new InetMailAddress(fullAddress);
			recipient = new EmailRecipient(inetMailAddress, directives);
		} else {
			throw new RuntimeException("No addressable or address for message participant");
		}
		return recipient;
	}
	
	
}
