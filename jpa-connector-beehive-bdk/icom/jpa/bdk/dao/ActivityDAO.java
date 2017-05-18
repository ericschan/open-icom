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

import icom.info.ActivityInfo;
import icom.info.BeanHandler;
import icom.info.IcomBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.Manageable;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;

import java.util.Date;
import java.util.HashMap;

import com.oracle.beehive.Activity;
import com.oracle.beehive.ActivityListUpdater;
import com.oracle.beehive.ActivityType;
import com.oracle.beehive.ActivityUpdater;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Note;

public class ActivityDAO extends BdkIdentifiableDAO  {

	static ActivityDAO singleton = new ActivityDAO();
	
	public static ActivityDAO getInstance() {
		return singleton;
	}
	
	public String getResourceType() {
		return "acty";
	}
	
	enum Operand { ADD, REMOVE };
	
	enum PojopojoToCsiActivityType {
		OnThePhone,
		Conference,
		Meeting,
		Travel,
		Steering,
		Meal,
		OutOfOffice,
		Holiday,
		Vacation,
		OutOfContact,
		Other
	}
	
	static HashMap<String, String> bdkToPojoActivityType;
	static HashMap<String, String> pojoToCsiActivityType;
	
	{
		bdkToPojoActivityType = new HashMap<String, String>();
		pojoToCsiActivityType = new HashMap<String, String>();
		bdkToPojoActivityType.put(ActivityType.ON_THE_PHONE.name(), PojopojoToCsiActivityType.OnThePhone.name());
		bdkToPojoActivityType.put(ActivityType.CONFERENCE.name(), PojopojoToCsiActivityType.Conference.name());
		bdkToPojoActivityType.put(ActivityType.MEETING.name(), PojopojoToCsiActivityType.Meeting.name());
		bdkToPojoActivityType.put(ActivityType.TRAVEL.name(), PojopojoToCsiActivityType.Travel.name());
		bdkToPojoActivityType.put(ActivityType.STEERING.name(), PojopojoToCsiActivityType.Steering.name());
		bdkToPojoActivityType.put(ActivityType.MEAL.name(), PojopojoToCsiActivityType.Meal.name());
		bdkToPojoActivityType.put(ActivityType.OUT_OF_OFFICE.name(), PojopojoToCsiActivityType.OutOfOffice.name());
		bdkToPojoActivityType.put(ActivityType.HOLIDAY.name(), PojopojoToCsiActivityType.Holiday.name());
		bdkToPojoActivityType.put(ActivityType.VACATION.name(), PojopojoToCsiActivityType.Vacation.name());
		bdkToPojoActivityType.put(ActivityType.OUT_OF_CONTACT.name(), PojopojoToCsiActivityType.OutOfContact.name());
		bdkToPojoActivityType.put(ActivityType.OTHER.name(), PojopojoToCsiActivityType.Other.name());
		for (String key : bdkToPojoActivityType.keySet()) {
			pojoToCsiActivityType.put(bdkToPojoActivityType.get(key), key);
		}
	}
	
	ActivityDAO() {
		super();
	}

	public void copyObjectState(ManagedObjectProxy obj, Object stateObject,	Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		Persistent pojoActivity = obj.getPojoObject();
		Activity activity = (Activity) stateObject;
		
		try {
			long startMillisecs = activity.getStart();
			Date start = new Date(startMillisecs);
			assignAttributeValue(pojoActivity, ActivityInfo.Attributes.startDate.name(), start);
		} catch (Exception ex) {
			// ignore
		}
		
		try {
			long endMillisecs = activity.getEnd();
			Date end = new Date(endMillisecs);
			assignAttributeValue(pojoActivity, ActivityInfo.Attributes.endDate.name(), end);
		} catch (Exception ex) {
			// ignore
		}
			
		try {
			ActivityType bdkType = activity.getType();
			if (bdkType != null) {
				String bdkTypeName = bdkType.name();
				String pojoTypeName = bdkToPojoActivityType.get(bdkTypeName);
				assignEnumConstant(pojoActivity, ActivityInfo.Attributes.activityType.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ActivityTypeEnum.name(), pojoTypeName);
			} else {
				assignEnumConstant(pojoActivity, ActivityInfo.Attributes.activityType.name(), 
						BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ActivityTypeEnum.name(), PojopojoToCsiActivityType.Other.name());
			}
		} catch (Exception ex) {
			// ignore
		}
		
		try {
			Note note = activity.getNote();
			String noteStr = note.getUnlocalizedString();
			assignAttributeValue(pojoActivity, ActivityInfo.Attributes.note.name(), noteStr);
		} catch (Exception ex) {
			// ignore
		}	
		
		try {
			BeeId entityId = activity.getReference();
			if (entityId != null) {
				Manageable pojoEntity = getReference(context, entityId.getId());
				assignAttributeValue(pojoActivity, ActivityInfo.Attributes.reference.name(), pojoEntity);
			}
		} catch (Exception ex) {
			// ignore
		}	
		
	}

	public void updateObjectState(Object pojoActivity, ActivityListUpdater listUpdater, BeeId activityId, Operand operand) {
		String activityTypeName = getEnumName(pojoActivity, ActivityInfo.Attributes.activityType.name());
		String bdkActivityTypeName = pojoToCsiActivityType.get(activityTypeName);
		Date startDate = (Date) getAttributeValue(pojoActivity, ActivityInfo.Attributes.startDate.name());
		Date endDate = (Date) getAttributeValue(pojoActivity, ActivityInfo.Attributes.endDate.name());
		if (operand == Operand.ADD) {
			ActivityUpdater updater = new ActivityUpdater();
			String note = (String) getAttributeValue(pojoActivity, ActivityInfo.Attributes.note.name());
			updater.setNote(note);
			Persistent entity = (Persistent) getAttributeValue(pojoActivity, ActivityInfo.Attributes.reference.name());
			if (entity != null) {
				BeeId entityId = getBeeId(((ManagedIdentifiableProxy)entity.getManagedObjectProxy()).getObjectId().toString());
				updater.setReference(entityId);
			}
			if (startDate != null) {
				updater.setStart(startDate.getTime());
			}
			if (endDate != null) {
				updater.setEnd(endDate.getTime());
			}
			updater.setType(ActivityType.valueOf(bdkActivityTypeName));
			listUpdater.getAdds().add(updater);
		} else if (operand == Operand.REMOVE) {
			listUpdater.getRemoves().add(activityId);
		}
	}
}
