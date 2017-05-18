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

import icom.info.BeanHandler;
import icom.info.ContactMethodInfo;
import icom.info.IcomBeanEnumeration;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.oracle.beehive.ContactMethod;
import com.oracle.beehive.ContactReachabilityStatus;
import com.oracle.beehive.Note;

public class ContactMethodDAO extends BdkAbstractDAO {
	
	static ContactMethodDAO singleton = new ContactMethodDAO();
	
	public static ContactMethodDAO getInstance() {
		return singleton;
	}
	
	enum PojoContactReachabilityStatus {
		Reachable,
		NotReachable,
		Chatty,
		Away,
		ExtendedAway,
		DoNotDisturb
	}
	
	static HashMap<String, String> bdkToPojoContactReachabilityStatus;
	static HashMap<String, String> pojoToCsiContactReachabilityStatus;
	
	{
		bdkToPojoContactReachabilityStatus = new HashMap<String, String>();
		pojoToCsiContactReachabilityStatus = new HashMap<String, String>();
		bdkToPojoContactReachabilityStatus.put(ContactReachabilityStatus.REACHABLE.name(), PojoContactReachabilityStatus.Reachable.name());
		bdkToPojoContactReachabilityStatus.put(ContactReachabilityStatus.NOT_REACHABLE.name(), PojoContactReachabilityStatus.NotReachable.name());
		bdkToPojoContactReachabilityStatus.put(ContactReachabilityStatus.CHATTY.name(), PojoContactReachabilityStatus.Chatty.name());
		bdkToPojoContactReachabilityStatus.put(ContactReachabilityStatus.AWAY.name(), PojoContactReachabilityStatus.Away.name());
		bdkToPojoContactReachabilityStatus.put(ContactReachabilityStatus.EXTENDED_AWAY.name(), PojoContactReachabilityStatus.ExtendedAway.name());
		bdkToPojoContactReachabilityStatus.put(ContactReachabilityStatus.DO_NOT_DISTURB.name(), PojoContactReachabilityStatus.DoNotDisturb.name());
		for (String key : bdkToPojoContactReachabilityStatus.keySet()) {
			pojoToCsiContactReachabilityStatus.put(bdkToPojoContactReachabilityStatus.get(key), key);
		}
	}
	
	ContactMethodDAO() {
		super();
	}

	public void copyObjectState(ManagedObjectProxy obj, Object stateObject,	Projection proj) {
		Persistent pojoContactMethod = obj.getPojoObject();
		ContactMethod contactMethod = (ContactMethod) stateObject;
		
		try {
			long creationTimestamp = contactMethod.getCreationTimestamp();
			Date creationTime = new Date(creationTimestamp);
			assignAttributeValue(pojoContactMethod, ContactMethodInfo.Attributes.creationDate.name(), creationTime);
		} catch (Exception ex) {
			// ignore
		}
		
		try {
			long statusTimestamp = contactMethod.getStatusTimestamp();
			Date statusTime = new Date(statusTimestamp);
			assignAttributeValue(pojoContactMethod, ContactMethodInfo.Attributes.lastModificationDate.name(), statusTime);
		} catch (Exception ex) {
			// ignore
		}
		
		try {
			ContactReachabilityStatus bdkStatus = contactMethod.getStatus();
			if (bdkStatus != null) {
				String bdkStatusName = bdkStatus.name();
				String pojoStatusName = bdkToPojoContactReachabilityStatus.get(bdkStatusName);
				assignEnumConstant(pojoContactMethod, ContactMethodInfo.Attributes.contactStatus.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ContactReachabilityStatusEnum.name(), pojoStatusName);
			} else {
				assignEnumConstant(pojoContactMethod, ContactMethodInfo.Attributes.contactStatus.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ContactReachabilityStatusEnum.name(), PojoContactReachabilityStatus.NotReachable.name());
			}
		} catch (Exception ex) {
			// ignore
		}
		
		try {
			String uri = contactMethod.getURI();
			assignAttributeValue(pojoContactMethod, ContactMethodInfo.Attributes.contactEndpoints.name(), uri);
		} catch (Exception ex) {
			// ignore
		}

		try {
			Integer priority = contactMethod.getPriority();
			assignAttributeValue(pojoContactMethod, ContactMethodInfo.Attributes.contactPriority.name(), priority);
		} catch (Exception ex) {
			// ignore
		}
		
		try {
			List<String> connIds = contactMethod.getActiveConnectionIds();
			if (connIds != null) {
				Collection<String> pojoConnIds = new ArrayList<String>(connIds.size());
				for (String id : connIds) {
					pojoConnIds.add(id);
				}
				assignAttributeValue(pojoContactMethod, ContactMethodInfo.Attributes.activeConnectionIds.name(), pojoConnIds);
			} else {
				assignAttributeValue(pojoContactMethod, ContactMethodInfo.Attributes.activeConnectionIds.name(), new ArrayList<String>());
			}
		} catch (Exception ex) {
			// ignore
		}
		
		try {
			Note note = contactMethod.getNote();
			String noteStr = note.getUnlocalizedString();
			assignAttributeValue(pojoContactMethod, ContactMethodInfo.Attributes.note.name(), noteStr);
		} catch (Exception ex) {
			// ignore
		}	
				
	}

}
