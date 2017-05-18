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
package icom.jpa.bdk.dao;

import icom.info.IcomBeanEnumeration;
import icom.info.ParticipantInfo;
import icom.info.UnifiedMessageParticipantInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.net.URI;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.EmailParticipant;
import com.oracle.beehive.InetMailAddress;
import com.oracle.beehive.RawString;

public class EmailParticipantUtil {
	
	static public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
		EmailParticipant bdkParticipant = (EmailParticipant) bdkObject;
		Object pojoParticipant = obj.getPojoObject();
		Object bdkAddressable = bdkParticipant.getParticipant();
		if (bdkAddressable != null) {
			PersistenceContext context = obj.getPersistenceContext();
			ManagedIdentifiableProxy addressableObj = EmailMessageDAO.getInstance().getEntityProxy(context, bdkAddressable);
			Persistent pojoAddressable = addressableObj.getPojoIdentifiable();
			AbstractDAO.assignAttributeValue(pojoParticipant, ParticipantInfo.Attributes.participant.name(), pojoAddressable);
		}
		InetMailAddress bdkAddress = bdkParticipant.getAddress();
		if (bdkAddress != null) {
			String localPart = bdkAddress.getLocalPart();
			AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.localPart.name(), localPart);
			String domainPart = bdkAddress.getDomainPart();
			AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.domainPart.name(), domainPart);
			RawString bdkDisplayPart = bdkAddress.getDisplayPart();
			if (bdkDisplayPart != null) {
				byte[] displayPart = bdkDisplayPart.getBytes();
				AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.displayPart.name(), displayPart);
				String displayCharSet = bdkDisplayPart.getCharset();
				AbstractDAO.assignAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.displayCharacterSet.name(), displayCharSet);
			}
		}
	}
	
	static EmailParticipant updateEmailParticipant(Object pojoParticipant) {
		EmailParticipant bdkParticipant = null;
		String fullAddress = null;
		URI address = null;
		Persistent pojoAddressable = (Persistent) AbstractDAO.getAttributeValue(pojoParticipant, ParticipantInfo.Attributes.participant.name());
		if (pojoParticipant.getClass().getSimpleName().equals(IcomBeanEnumeration.UnifiedMessageParticipant.name())) {
			fullAddress = (String) AbstractDAO.getAttributeValue(pojoParticipant, UnifiedMessageParticipantInfo.Attributes.fullAddress.name());
			if (fullAddress == null) {
				address = (URI) AbstractDAO.getAttributeValue(pojoParticipant, ParticipantInfo.Attributes.address.name());
			}
		} else {
			address = (URI) AbstractDAO.getAttributeValue(pojoParticipant, ParticipantInfo.Attributes.address.name());
		}
		if (pojoAddressable != null) {
			ManagedIdentifiableProxy addressableObj = (ManagedIdentifiableProxy) pojoAddressable.getManagedObjectProxy();
			BeeId addressableId = EmailMessageDAO.getInstance().getBeeId(addressableObj.getObjectId().toString());
			if (fullAddress != null) {
				InetMailAddress inetMailAddress = new InetMailAddress();
				inetMailAddress.setCanonicalAddress(fullAddress);
				bdkParticipant = new EmailParticipant();
				bdkParticipant.setParticipantHandle(addressableId);
				bdkParticipant.setAddress(inetMailAddress);
			} else if (address != null) {
				InetMailAddress inetMailAddress = new InetMailAddress();
				inetMailAddress.setCanonicalAddress(address.toString());
				bdkParticipant = new EmailParticipant();
				bdkParticipant.setParticipantHandle(addressableId);
				bdkParticipant.setAddress(inetMailAddress);
			} else {
				bdkParticipant = new EmailParticipant();
				bdkParticipant.setParticipantHandle(addressableId);
			}
			return bdkParticipant;
		} else if (fullAddress != null) {
			InetMailAddress inetMailAddress = new InetMailAddress();
			inetMailAddress.setCanonicalAddress(fullAddress);
			bdkParticipant = new EmailParticipant();
			bdkParticipant.setAddress(inetMailAddress);
		} else if (address != null) {
			InetMailAddress inetMailAddress = new InetMailAddress();
			inetMailAddress.setCanonicalAddress(address.toString());
			bdkParticipant = new EmailParticipant();
			bdkParticipant.setAddress(inetMailAddress);
		} else {
			//throw new RuntimeException("No addressable or address for message participant");
		}
		return bdkParticipant;
	}
	
}
