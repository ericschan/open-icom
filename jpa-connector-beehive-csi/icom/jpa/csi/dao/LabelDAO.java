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

import icom.info.TagInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.info.beehive.BeehiveTagInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Label;
import oracle.csi.LabelHandle;
import oracle.csi.LabelType;
import oracle.csi.SnapshotId;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.LabelControl;
import oracle.csi.controls.LabelFactory;
import oracle.csi.filters.LabelListFilter;
import oracle.csi.filters.LabelTypePredicate;
import oracle.csi.filters.MatchAnyPredicate;
import oracle.csi.filters.Predicate;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.LabelUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

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
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return LabelHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Label csiLabel = null;
		try {
			LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			LabelHandle labelHandle = (LabelHandle) EntityUtils.getInstance().createHandle(id);
			csiLabel = control.loadLabel(labelHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiLabel;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, csiEntity, proj);
		
		Label csiLabel = (Label) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (!isPartOfProjection(TagInfo.Attributes.applicationCount.name(), lastLoadedProjection) &&
				isPartOfProjection(TagInfo.Attributes.applicationCount.name(), proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, TagInfo.Attributes.applicationCount.name(), 
						new Long(csiLabel.getApplicationCount()));
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (/* !isPartOfProjection(BeehiveTagInfo.Attributes.type.name(), lastLoadedProjection) && */
				isPartOfProjection(BeehiveTagInfo.Attributes.type.name(),  proj)) {
			try {
				LabelType csiLabelType  = csiLabel.getLabelType();
				String labelTypeName = null;
				if (csiLabelType != null) {
					labelTypeName = csiLabelType.name();
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
			} catch (CsiRuntimeException ex) {
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

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		LabelUpdater labelUpdater = LabelFactory.getInstance().createLabelUpdater();
		DAOContext context = new DAOContext(labelUpdater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		LabelHandle labelHandle = (LabelHandle) EntityUtils.getInstance().createHandle(id);
		LabelUpdater labelUpdater = (LabelUpdater) context.getUpdater();
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
			Label label = control.updateLabel(labelHandle, labelUpdater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, label.getSnapshotId().toString());
			return label;
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
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		LabelUpdater labelUpdater = (LabelUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		
		String name = getName(pojoIdentifiable);
		try {
			CollabId id = getCollabId(obj.getObjectId());
			Label label = control.createLabel(id.getEid(), name, labelUpdater, proj);
			assignChangeToken(pojoIdentifiable, label.getSnapshotId().toString());
			return label;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		LabelHandle labelHandle = (LabelHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteLabel(labelHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public Set<Persistent> loadAvailableLabels(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		Set<Persistent> pojoTags = null;
		try {
			LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
			LabelListFilter labelListFilter = LabelFactory.getInstance().createLabelListFilter();
			
			Set<Predicate> predList = new HashSet<Predicate>();
			LabelType[] labelTypes = LabelType.values();
			for (LabelType lType : labelTypes) {
				LabelTypePredicate pred = labelListFilter.createLabelTypePredicate(lType);
				predList.add(pred);
			}
			MatchAnyPredicate predicate = labelListFilter.createMatchAnyPredicate(predList);
			labelListFilter.setPredicate(predicate);
			labelListFilter.setProjection(proj);
			Collection<Label> csiLabels = control.listAllLabels(labelListFilter);
			if (csiLabels != null) {
				pojoTags = new HashSet<Persistent>(csiLabels.size());
				for (Label csiLabel : csiLabels) {
					ManagedIdentifiableProxy tagObj = getEntityProxy(context, csiLabel);
					tagObj.getProviderProxy().copyLoadedProjection(tagObj, csiLabel, proj);
					pojoTags.add(tagObj.getPojoIdentifiable());
				}
			} else {
				return new HashSet<Persistent>();
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return pojoTags;
	}
	
}
