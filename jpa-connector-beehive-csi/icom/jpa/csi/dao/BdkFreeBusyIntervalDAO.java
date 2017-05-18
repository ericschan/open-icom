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
import icom.info.FreeBusyIntervalInfo;
import icom.info.IcomBeanEnumeration;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.util.Date;
import java.util.HashMap;

import javax.xml.datatype.XMLGregorianCalendar;

import oracle.csi.BdkFreeBusyInterval;
import oracle.csi.CsiRuntimeException;
import oracle.csi.FreeBusyType;

public class BdkFreeBusyIntervalDAO {

	static BdkFreeBusyIntervalDAO singleton = new BdkFreeBusyIntervalDAO();
	
	public static BdkFreeBusyIntervalDAO getInstance() {
		return singleton;
	}
	
	enum PojoFreeBusyType {
		Free,
		Busy,
		Tentative,
		OutsideAvailableHours,
		OutOfOffice,
		OtherFreeBusyType
	}
	
	static HashMap<String, String> csiToPojoFreeBusyType;
	static HashMap<String, String> pojoToCsiFreeBusyType;
	
	{
		csiToPojoFreeBusyType = new HashMap<String, String>();
		pojoToCsiFreeBusyType = new HashMap<String, String>();
		csiToPojoFreeBusyType.put(FreeBusyType.FREE.name(), PojoFreeBusyType.Free.name());
		csiToPojoFreeBusyType.put(FreeBusyType.BUSY.name(), PojoFreeBusyType.Busy.name());
		csiToPojoFreeBusyType.put(FreeBusyType.TENTATIVE.name(), PojoFreeBusyType.Tentative.name());
		csiToPojoFreeBusyType.put(FreeBusyType.OUTSIDE_AVAILABLE_HOURS.name(), PojoFreeBusyType.OutsideAvailableHours.name());
		csiToPojoFreeBusyType.put(FreeBusyType.OUT_OF_OFFICE.name(), PojoFreeBusyType.OutOfOffice.name());
		csiToPojoFreeBusyType.put(FreeBusyType.UNKNOWN.name(), PojoFreeBusyType.OtherFreeBusyType.name());
		for (String key : csiToPojoFreeBusyType.keySet()) {
			pojoToCsiFreeBusyType.put(csiToPojoFreeBusyType.get(key), key);
		}
	}
	
	BdkFreeBusyIntervalDAO() {
		super();
	}
	
	public void copyObjectState(PersistenceContext context, Object pojoFreeBusyInterval, BdkFreeBusyInterval csiBdkFreeBusyInterval) {
		try {
			XMLGregorianCalendar bdkStart = csiBdkFreeBusyInterval.getStart();
			if (bdkStart != null) {
				Date pojoStartTime = bdkStart.toGregorianCalendar().getTime();
				AbstractDAO.assignAttributeValue(pojoFreeBusyInterval, FreeBusyIntervalInfo.Attributes.startDate.name(), pojoStartTime);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		try {
			XMLGregorianCalendar bdkEnd = csiBdkFreeBusyInterval.getEnd();
			if (bdkEnd != null) {
				Date pojoEndTime = bdkEnd.toGregorianCalendar().getTime();
				AbstractDAO.assignAttributeValue(pojoFreeBusyInterval, FreeBusyIntervalInfo.Attributes.endDate.name(), pojoEndTime);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		try {
			FreeBusyType csiType = csiBdkFreeBusyInterval.getFreeBusyType();
			if (csiType != null) {
				String csiTypeName = csiType.name();
				String pojoTypeName = csiToPojoFreeBusyType.get(csiTypeName);
				AbstractDAO.assignEnumConstant(pojoFreeBusyInterval, FreeBusyIntervalInfo.Attributes.freeBusyType.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.FreeBusyTypeEnum.name(), pojoTypeName);
			} else {
				AbstractDAO.assignEnumConstant(pojoFreeBusyInterval, FreeBusyIntervalInfo.Attributes.freeBusyType.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.FreeBusyTypeEnum.name(), PojoFreeBusyType.OtherFreeBusyType.name());
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
	}
	
}
