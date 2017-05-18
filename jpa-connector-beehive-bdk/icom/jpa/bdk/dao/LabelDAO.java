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

import icom.info.EntityInfo;
import icom.info.TagInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.info.beehive.BeehiveTagInfo;
import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.UserContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.CreatedByPredicate;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.Label;
import com.oracle.beehive.LabelCreator;
import com.oracle.beehive.LabelType;
import com.oracle.beehive.LabelUpdater;
import com.oracle.beehive.MatchAnyPredicate;
import com.oracle.beehive.OwnerPredicate;

public class LabelDAO extends MarkerDAO {
	
	static LabelDAO singleton = new LabelDAO();
	
	public static LabelDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(TagInfo.Attributes.applicationCount);
		basicAttributes.add(BeehiveTagInfo.Attributes.type);
	}

	protected LabelDAO() {
	}

	public String getResourceType() {
		return "labl";
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, bdkEntity, proj);
		
		Label bdkLabel = (Label) bdkEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		BdkProjectionManager projManager = (BdkProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(TagInfo.Attributes.applicationCount.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, TagInfo.Attributes.applicationCount.name(), 
						new Long(bdkLabel.getApplicationCount()));
			} catch (Exception ex) {
				// ignore
			}
		}
		
		if (/* !isPartOfProjection(BeehiveTagInfo.Attributes.type.name(), lastLoadedProjection) && */
				isPartOfProjection(BeehiveTagInfo.Attributes.type.name(),  proj)) {
			try {
				LabelType bdkLabelType  = bdkLabel.getLabelType();
				String labelTypeName = null;
				if (bdkLabelType != null) {
					labelTypeName = bdkLabelType.name();
					if (labelTypeName.equals(LabelType.USER_DEFINED_LABEL.name())) {
						labelTypeName = "USER_DEFINED";
					} else if (labelTypeName.equals(LabelType.PRE_DEFINED_LABEL.name())) {
						labelTypeName = "SYSTEM";
					} else if (labelTypeName.equals(LabelType.SYSTEM_LABEL.name())) {
						labelTypeName = "SYSTEM";
					} else if (labelTypeName.equals(LabelType.SHARED_LABEL.name())) {
						labelTypeName = "SHARED";
					} 
				}
				assignEnumConstant(pojoIdentifiable, BeehiveTagInfo.Attributes.type.name(), BeehiveBeanEnumeration.BeehiveTagType.getPackageName(), BeehiveBeanEnumeration.BeehiveTagType.name(), labelTypeName);
			} catch (Exception ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		LabelUpdater updater = (LabelUpdater) context.getUpdater();
		Persistent pojoTag = obj.getPojoIdentifiable();
		
		if (isChanged(obj, BeehiveTagInfo.Attributes.type.name())) {
			LabelType labelType = LabelType.USER_DEFINED_LABEL;
			String tagTypeName = getEnumName(pojoTag, BeehiveTagInfo.Attributes.type.name());
			if (tagTypeName != null) {
				labelType = LabelType.valueOf(tagTypeName + "_LABEL");
			}
			updater.setLabelType(labelType);
		}
	}

	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		LabelCreator creator = (LabelCreator) context.getCreator();
		Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setLabelName(name);
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public Set<Persistent> loadAvailableLabels(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		MatchAnyPredicate map = null;
		try {
			UserContext userContext = context.getUserContext();
			if (userContext != null) {
				BeeId actorId = getBeeId(userContext.getActorId().toString());
				CreatedByPredicate cbp = new CreatedByPredicate();
				cbp.setCreatedBy(actorId);
				OwnerPredicate op = new OwnerPredicate();
				op.setOwnerMatch(actorId);
				map = new MatchAnyPredicate();
				map.getPredicates().add(cbp);
				map.getPredicates().add(op);
			}
		} catch (Throwable ex) {
			// ignore
		}
		List<Object> bdkLabels = listEntities(context, Label.class, map, getResourceType(), proj);
		try {
			if (bdkLabels != null) {
				Set<Persistent> pojoTags = new HashSet<Persistent>(bdkLabels.size());
				for (Object bdkObject : bdkLabels) {
					Label bdkLabel = (Label) bdkObject;
					ManagedIdentifiableProxy tagObj = getEntityProxy(context, bdkLabel);
					tagObj.getProviderProxy().copyLoadedProjection(tagObj, bdkLabel, proj);
					pojoTags.add(tagObj.getPojoIdentifiable());
				}
				return pojoTags;
			} else {
				return new HashSet<Persistent>();
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Label.class;
	}

	protected LabelUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new LabelUpdater();
	}

	protected LabelUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		LabelUpdater updater = getBdkUpdater(obj);
		((LabelCreator)creator).setUpdater(updater);
		return updater;
	}

	protected LabelCreator getBdkCreator(ManagedObjectProxy obj) {
		return new LabelCreator();
	}
	
}
