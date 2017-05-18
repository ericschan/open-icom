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

import icom.info.EntityInfo;
import icom.info.TagApplicationInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.info.beehive.BeehiveTagApplicationInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.dao.MetaDataApplicationDAO;
import icom.jpa.rt.ManagedIdentifiableDependentProxyImpl;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.Actor;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Label;
import oracle.csi.LabelApplication;
import oracle.csi.LabelApplicationHandle;
import oracle.csi.LabelApplicationType;
import oracle.csi.LabelHandle;
import oracle.csi.LabelType;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.LabelControl;
import oracle.csi.controls.LabelFactory;
import oracle.csi.filters.LabelAppListFilter;
import oracle.csi.filters.LabelTypePredicate;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.MatchAnyPredicate;
import oracle.csi.filters.Predicate;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.LabelApplicationUpdater;
import oracle.csi.util.UpdateMode;

public class LabelApplicationDAO extends CsiIdentifiableDAO implements MetaDataApplicationDAO {
	
	static LabelApplicationDAO singleton = new LabelApplicationDAO();
	
	public static LabelApplicationDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(TagApplicationInfo.Attributes.attachedEntity);
		basicAttributes.add(TagApplicationInfo.Attributes.tag);
		basicAttributes.add(TagApplicationInfo.Attributes.appliedBy);
		basicAttributes.add(TagApplicationInfo.Attributes.applicationDate);
		basicAttributes.add(BeehiveTagApplicationInfo.Attributes.type);
	}

	protected LabelApplicationDAO() {
		
	}

	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return LabelApplicationHandle.class;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		LabelApplication csiLabelApplication = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, csiLabelApplication, proj);
		return proj;
	}
	
	public LabelApplication loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		ManagedIdentifiableDependentProxyImpl depObj = (ManagedIdentifiableDependentProxyImpl) obj;
		LabelApplication csiLabelApplication = null;
		Persistent pojoTagApp = depObj.getPojoIdentifiable();
		Persistent pojoTag = (Persistent)getAttributeValue(pojoTagApp, TagApplicationInfo.Attributes.tag.name());
		if (pojoTag != null) {
			CollabId labelId = getCollabId(((ManagedIdentifiableProxy)pojoTag.getManagedObjectProxy()).getObjectId());
			LabelHandle labelHandle = (LabelHandle) EntityUtils.getInstance().createHandle(labelId);
		
			ManagedIdentifiableProxy parentObj = (ManagedIdentifiableProxy) depObj.getParent();
			CollabId id = getCollabId(parentObj.getObjectId());
			EntityHandle labeledEntityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(id);
		
			try {
				LabelAppListFilter listFilter = LabelFactory.getInstance().createLabelAppListFilter();
				Predicate pred = listFilter.createLabelPredicate(labelHandle);
				listFilter.setPredicate(pred);
				ListResult<LabelApplication> csiLabelApplicationList = control.listEntityLabelApplication(labeledEntityHandle, listFilter);
				if (csiLabelApplicationList.size() > 0) {
					csiLabelApplication = csiLabelApplicationList.get(0);
				}
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
		}
		return csiLabelApplication;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiIdentifiable, Projection proj) {
		LabelApplication csiLabelApplication = (LabelApplication) csiIdentifiable;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
		
		if (/* !isPartOfProjection(TagApplicationInfo.Attributes.taggedEntity.name(), lastLoadedProjection) && */
				isPartOfProjection(TagApplicationInfo.Attributes.attachedEntity.name(),  proj)) {
			try {
				Entity csiLabeledEntity  = csiLabelApplication.getLabeledEntity();
				ManagedIdentifiableProxy labeledEntityObj = getEntityProxy(context, csiLabeledEntity);
				Persistent labeledPojoEntity = labeledEntityObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, TagApplicationInfo.Attributes.attachedEntity.name(), labeledPojoEntity);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (/* !isPartOfProjection(TagApplicationInfo.Attributes.tag.name(), lastLoadedProjection) && */
				isPartOfProjection(TagApplicationInfo.Attributes.tag.name(),  proj)) {
			try {
				Label csiLabel  = csiLabelApplication.getLabel();
				ManagedIdentifiableProxy labelObj = getEntityProxy(context, csiLabel);
				Persistent pojoLabel = labelObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, TagApplicationInfo.Attributes.tag.name(), pojoLabel);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (/* !isPartOfProjection(BeehiveTagApplicationInfo.Attributes.type.name(), lastLoadedProjection) && */
				isPartOfProjection(BeehiveTagApplicationInfo.Attributes.type.name(),  proj)) {
			try {
				LabelApplicationType csiLabelApplicationType  = csiLabelApplication.getLabelApplicationType();
				String labelApplicationTypeName = null;
				if (csiLabelApplicationType != null) {
					labelApplicationTypeName = csiLabelApplicationType.name();
				}
				assignEnumConstant(pojoIdentifiable, BeehiveTagApplicationInfo.Attributes.type.name(), BeehiveBeanEnumeration.BeehiveTagApplicationType.getPackageName(), BeehiveBeanEnumeration.BeehiveTagApplicationType.name(), labelApplicationTypeName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (/*!isPartOfProjection(TagApplicationInfo.Attributes.appliedBy.name(), lastLoadedProjection) && */
				isPartOfProjection(TagApplicationInfo.Attributes.appliedBy.name(),  proj)) {
			try {
				Actor csiActor  = csiLabelApplication.getAppliedBy();
				if (csiActor != null) {
					ManagedIdentifiableProxy actorObj = getEntityProxy(context, csiActor);
					Persistent pojoActor = actorObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, TagApplicationInfo.Attributes.appliedBy.name(), pojoActor);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		if (/* !isPartOfProjection(TagApplicationInfo.Attributes.appliedOn.name(), lastLoadedProjection) && */
				isPartOfProjection(TagApplicationInfo.Attributes.applicationDate.name(),  proj)) {
			try {
				Date date  = csiLabelApplication.getAppliedOn();
				assignAttributeValue(pojoIdentifiable, TagApplicationInfo.Attributes.applicationDate.name(), date);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	public void save(ManagedIdentifiableProxy obj) {
		if (obj.isDirty()) {
			if (! obj.hasAttributeChanges()) {
				return;
			}
			saveDirty(obj);
		} else if (obj.isNew()) {
			saveNew(obj);
		}
	}
	
	public void saveDirty(ManagedIdentifiableProxy obj) {
		DAOContext context = beginUpdateObject(obj);
		if (context != null) {
			updateObjectState(obj, context);
			concludeUpdateObject(obj, context, Projection.FULL);
		}
	}
	
	public void saveNew(ManagedIdentifiableProxy obj) {
		DAOContext context = beginCreateObject(obj);
		if (context != null) {
			updateNewObjectState(obj, context);
			concludeCreateObject(obj, context, Projection.FULL);
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		LabelApplicationUpdater updater = (LabelApplicationUpdater) context.getUpdater();
		Persistent pojoTagApp = obj.getPojoIdentifiable();
		
		if (isChanged(obj, BeehiveTagApplicationInfo.Attributes.type.name())) {
			LabelApplicationType labelAppType = LabelApplicationType.PRIVATE;
			String tagApplicationTypeName = getEnumName(pojoTagApp, BeehiveTagApplicationInfo.Attributes.type.name());
			if (tagApplicationTypeName != null) {
				labelAppType = LabelApplicationType.valueOf(tagApplicationTypeName);
			}
			updater.setLabelApplicationType(labelAppType);
		}
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		LabelApplicationUpdater updater = LabelFactory.getInstance().createLabelApplicationUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public LabelApplication concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		LabelApplicationUpdater updater = (LabelApplicationUpdater) context.getUpdater();
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		
		icom.jpa.Identifiable pojoTagApp = obj.getPojoIdentifiable();
		CollabId labelApplicationId = getCollabId(((ManagedIdentifiableProxy)pojoTagApp.getManagedObjectProxy()).getObjectId());
		LabelApplicationHandle labelAppHandle = (LabelApplicationHandle) EntityUtils.getInstance().createHandle(labelApplicationId);

		try {
			UpdateMode updateMode = UpdateMode.alwaysUpdate();
			LabelApplication csiLabelApp = control.updateLabelApplication(labelAppHandle, updater, updateMode, proj);
			copyObjectState(obj, csiLabelApp, proj);
			CollabId newCollabId = csiLabelApp.getCollabId();
			CsiIdentifiableDAO.assignObjectId(pojoTagApp, newCollabId.toString());
			return csiLabelApp;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	// called from DocumentDAO
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public LabelApplication concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		
		icom.jpa.Identifiable pojoTagApp = obj.getPojoIdentifiable();
		Persistent pojoTag = (Persistent)getAttributeValue(pojoTagApp, TagApplicationInfo.Attributes.tag.name());
		CollabId labelId = getCollabId(((ManagedIdentifiableProxy)pojoTag.getManagedObjectProxy()).getObjectId());
		LabelHandle labelHandle = (LabelHandle) EntityUtils.getInstance().createHandle(labelId);
		
		Persistent pojoTaggedEntity = (Persistent)getAttributeValue(pojoTagApp, TagApplicationInfo.Attributes.attachedEntity.name());
		CollabId entityId = getCollabId(((ManagedIdentifiableProxy)pojoTaggedEntity.getManagedObjectProxy()).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(entityId);
		
		LabelApplicationType labelAppType = LabelApplicationType.PRIVATE;
		String tagApplicationTypeName = getEnumName(pojoTagApp, BeehiveTagApplicationInfo.Attributes.type.name());
		if (tagApplicationTypeName != null) {
			labelAppType = LabelApplicationType.valueOf(tagApplicationTypeName);
		}
		
		try {
			LabelApplication csiLabelApp = control.applyLabelToEntity(labelHandle, entityHandle, labelAppType);
			copyObjectState(obj, csiLabelApp, proj);
			CollabId newCollabId = csiLabelApp.getCollabId();
			CsiIdentifiableDAO.assignObjectId(pojoTagApp, newCollabId.toString());
			return csiLabelApp;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		LabelApplicationHandle labelAppHandle = (LabelApplicationHandle) EntityUtils.getInstance().createHandle(id);
		
		Vector<LabelApplicationHandle> lApplnHandles = new Vector<LabelApplicationHandle>(1);
		lApplnHandles.add(labelAppHandle);
		
		try {
			control.removeLabelApplications(lApplnHandles);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

	void loadLabelApplicationsOnEntity(ManagedIdentifiableProxy obj, Projection projection) {
		HashSet<Persistent> v = new HashSet<Persistent>();
		LabelControl control = ControlLocator.getInstance().getControl(LabelControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(id);
		PersistenceContext context = obj.getPersistenceContext();
		try {
			LabelAppListFilter labelAppFilter = LabelFactory.getInstance().createLabelAppListFilter();
			Set<Predicate> typePreds = new HashSet<Predicate>();
			LabelType[] labelTypes = LabelType.values();
			for (LabelType lType : labelTypes) {
				LabelTypePredicate pred = labelAppFilter.createLabelTypePredicate(lType);
				typePreds.add(pred);
			}
			MatchAnyPredicate pred = labelAppFilter.createMatchAnyPredicate(typePreds);
			labelAppFilter.setPredicate(pred);
			labelAppFilter.setProjection(projection);
			Collection<LabelApplication> csiLabelApps = control.listEntityLabelApplication(entityHandle, labelAppFilter);
			if (csiLabelApps != null) {
				Iterator<LabelApplication> iter = csiLabelApps.iterator();
				while (iter.hasNext()) {
					LabelApplication csiLabelApplication = iter.next();
					ManagedIdentifiableProxy labelAppObj = getIdentifiableDependentProxy(context, csiLabelApplication, obj, EntityInfo.Attributes.tagApplications.name());
					labelAppObj.getProviderProxy().copyLoadedProjection(labelAppObj, csiLabelApplication, projection);
					Persistent pojoLabelApplication = labelAppObj.getPojoIdentifiable();
					v.add(pojoLabelApplication);
				}
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		obj.getProviderProxy().copyLazyAttribute(obj, EntityInfo.Attributes.tagApplications.name(), null, v);
	}

}
