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

import icom.info.AbstractBeanInfo;
import icom.info.BeanHandler;
import icom.info.BeanInfo;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.PropertyInfo;
import icom.info.RelationshipBondableInfo;
import icom.info.RelationshipInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.Bond;
import oracle.csi.BondEntityRelation;
import oracle.csi.BondHandle;
import oracle.csi.BondType;
import oracle.csi.Bondable;
import oracle.csi.BondableHandle;
import oracle.csi.CollabId;
import oracle.csi.CollabProperties;
import oracle.csi.CollabProperty;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.controls.BondControl;
import oracle.csi.controls.BondFactory;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.filters.BondListFilter;
import oracle.csi.filters.BondTypePredicate;
import oracle.csi.filters.ListResult;
import oracle.csi.filters.MatchAnyPredicate;
import oracle.csi.filters.Predicate;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.BondUpdater;
import oracle.csi.updaters.CollabPropertiesUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class BondDAO extends EntityDAO {
	
	static BondDAO singleton = new BondDAO();
	
	public static BondDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(RelationshipInfo.Attributes.relationshipDefinition);
		basicAttributes.add(RelationshipInfo.Attributes.sourceEntity);
		basicAttributes.add(RelationshipInfo.Attributes.targetEntities);
	}
	
	{	
		fullAttributes.add(RelationshipInfo.Attributes.properties);
	}
	
	
	static HashMap<String, String> csiToPojoNameMap;
	static HashMap<String, String> pojoToCsiNameMap;
	
	{
		csiToPojoNameMap = new HashMap<String, String>();
		pojoToCsiNameMap = new HashMap<String, String>();
		csiToPojoNameMap.put(BondType.DISCUSS_THIS.name(), "Discussion Thread");
		csiToPojoNameMap.put(BondType.FOLLOW_UP.name(), "Follow Up Thread");
		csiToPojoNameMap.put(BondType.REFERENCED_MATERIALS.name(), "Referenced Materials");
		csiToPojoNameMap.put(BondType.RELATED_MATERIALS.name(), "Related Materials");
		for (String key : csiToPojoNameMap.keySet()) {
			pojoToCsiNameMap.put(csiToPojoNameMap.get(key), key);
		}
	}


	protected BondDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return BondHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Bond csiBond = null;
		try {
			BondControl control = ControlLocator.getInstance().getControl(BondControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			BondHandle csiBondHandle = (BondHandle) EntityUtils.getInstance().createHandle(id);
			csiBond = control.loadBond(csiBondHandle, proj);	
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiBond;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Bond csiBond = (Bond) csiEntity;
		Persistent pojoRelationship = obj.getPojoObject();
			
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(RelationshipInfo.Attributes.relationshipDefinition.name(), lastLoadedProjection, proj)) {
			try {
				BondType type = csiBond.getBondType();
				String relationshipType = csiToPojoNameMap.get(type.name());
				Object pojoRelationshipDefinition = BeanHandler.instantiatePojoObject(BeanHandler.getBeanPackageName() + "." + IcomBeanEnumeration.RelationshipDefinition.name());
				assignAttributeValue(pojoRelationshipDefinition, EntityInfo.Attributes.name.name(), relationshipType);
				assignAttributeValue(pojoRelationship, RelationshipInfo.Attributes.relationshipDefinition.name(), pojoRelationshipDefinition);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(RelationshipInfo.Attributes.sourceEntity.name(), lastLoadedProjection, proj)) {
			try {
				Bondable csiBondable = csiBond.getRoot();
				if (csiBondable != null) {
					ManagedIdentifiableProxy sourceObj = getEntityProxy(context, csiBondable);
					Persistent bondablePojoEntity = sourceObj.getPojoIdentifiable();
					assignAttributeValue(pojoRelationship, RelationshipInfo.Attributes.sourceEntity.name(), bondablePojoEntity);
				}	
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(RelationshipInfo.Attributes.targetEntities.name(), lastLoadedProjection, proj)) {
			try {
				ListResult<BondEntityRelation> csiBondEntityRelationList = csiBond.getBondedEntities();
				if (csiBondEntityRelationList != null) {
					HashSet<Persistent> v = new HashSet<Persistent>(csiBondEntityRelationList.size());
					for (BondEntityRelation csiRelation: csiBondEntityRelationList) {
						Bondable csiTargetBondable = csiRelation.getEntity();
						ManagedIdentifiableProxy targetObj = getEntityProxy(context, csiTargetBondable);
						Persistent pojoTargetEntity = targetObj.getPojoObject();
						v.add(pojoTargetEntity);
					}
					assignAttributeValue(pojoRelationship, RelationshipInfo.Attributes.targetEntities.name(), v);
				}	
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(RelationshipInfo.Attributes.properties.name(), lastLoadedProjection, proj)) {
			try {
				CollabProperties propertyMaps = csiBond.getProperties();
				if (propertyMaps != null) {
					Collection<CollabProperty> properties = propertyMaps.values();
					Iterator<CollabProperty> iter = properties.iterator();
					Vector<Object> v = new Vector<Object>();
					while (iter.hasNext()) {
						CollabProperty csiCollabProperty = iter.next();
						ManagedObjectProxy propertyObj = getNonIdentifiableDependentProxy(context, csiCollabProperty, obj, RelationshipInfo.Attributes.properties.name());
						propertyObj.getProviderProxy().copyLoadedProjection(propertyObj, csiCollabProperty, proj);
						v.add(propertyObj.getPojoObject());
					}
					
					Collection<Object> previousProperties = getObjectCollection(pojoRelationship, RelationshipInfo.Attributes.properties.name());
					if (previousProperties != null) {
						for (Object pojoProperty : previousProperties) {
							if (pojoProperty instanceof Persistent) {
								BeanInfo beanInfo = context.getBeanInfo(pojoProperty);
								ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(pojoProperty, AbstractBeanInfo.Attributes.mop.name());
								beanInfo.detachHierarchy(mop);
							}
						}
					}
					
					assignAttributeValue(pojoRelationship, RelationshipInfo.Attributes.properties.name(), v);
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}

	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		BondUpdater bondUpdater = (BondUpdater) context.getUpdater();
		
		if (isChanged(obj, RelationshipInfo.Attributes.targetEntities.name())) {	
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(RelationshipInfo.Attributes.targetEntities.name());
			if (addedObjects != null) {
				for (ValueHolder addedObjectHolder : addedObjects) {
					Persistent pojoBondable = (Persistent) addedObjectHolder.getValue();
					CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoBondable.getManagedObjectProxy()).getObjectId());
					BondableHandle bondableHandle = (BondableHandle) EntityUtils.getInstance().createHandle(id);
					bondUpdater.addBondEntityRelation(bondableHandle);
				}
			}
			
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(RelationshipInfo.Attributes.targetEntities.name());
			if (removedObjects != null) {
				for (ValueHolder removedObjectHolder : removedObjects) {
					Persistent pojoBondable = (Persistent) removedObjectHolder.getValue();
					CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoBondable.getManagedObjectProxy()).getObjectId());
					BondableHandle bondableHandle = (BondableHandle) EntityUtils.getInstance().createHandle(id);
					bondUpdater.removeBondEntityRelation(bondableHandle);
				}
			}
		}

		if (isChanged(obj, RelationshipInfo.Attributes.properties.name())) {
			ArrayList<Persistent> modifiedProperties = new ArrayList<Persistent>();
			Collection<Object> properties = getObjectCollection(pojoIdentifiable, RelationshipInfo.Attributes.properties.name());
			if (properties != null) {
				for (Object property : properties) {
					Persistent pojo = (Persistent) property;
					ManagedObjectProxy proxy = pojo.getManagedObjectProxy();
					if (proxy.containsAttributeChangeRecord(PropertyInfo.Attributes.value.name())) {
						modifiedProperties.add(pojo);
					}
				}
			}
			CollabPropertiesUpdater propertiesUpdater = bondUpdater.getPropertiesUpdater();
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(RelationshipInfo.Attributes.properties.name());
			if (addedObjects != null) {
				Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
				while (addedObjectsIter.hasNext()) {
					ValueHolder holder = addedObjectsIter.next();
					Persistent pojoProperty = (Persistent) holder.getValue();
					CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, propertiesUpdater, CollabPropertyDAO.Operand.ADD);
					modifiedProperties.remove(pojoProperty);
				}
			}
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(RelationshipInfo.Attributes.properties.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent pojoProperty = (Persistent) holder.getValue();
					CollabPropertyDAO.getInstance().updateObjectState(pojoProperty, propertiesUpdater, CollabPropertyDAO.Operand.REMOVE);
					modifiedProperties.remove(pojoProperty);
				}
			}
			for (Persistent modifiedProperty : modifiedProperties) {
				CollabPropertyDAO.getInstance().updateObjectState(modifiedProperty, propertiesUpdater, CollabPropertyDAO.Operand.MODIFY);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		BondUpdater bondUpdater = BondFactory.getInstance().createBondUpdater();
		DAOContext context = new DAOContext(bondUpdater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		BondControl control = ControlLocator.getInstance().getControl(BondControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		BondHandle bondHandle = (BondHandle) EntityUtils.getInstance().createHandle(id);
		BondUpdater bondUpdater = (BondUpdater) context.getUpdater();
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
			Bond bond = control.updateBond(bondHandle, bondUpdater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, bond.getSnapshotId().toString());
			return bond;
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
		BondControl control = ControlLocator.getInstance().getControl(BondControl.class);
		BondUpdater bondUpdater = (BondUpdater) context.getUpdater();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		try {
			String bondName = getName(pojoIdentifiable);
			Persistent pojoBondable = (Persistent) getAttributeValue(pojoIdentifiable, RelationshipInfo.Attributes.sourceEntity.name());
			ManagedIdentifiableProxy bondableObj = (ManagedIdentifiableProxy) pojoBondable.getManagedObjectProxy();
			CollabId bondableId = getCollabId(bondableObj.getObjectId());
			BondableHandle bondableSourceHandle = (BondableHandle) EntityUtils.getInstance().createHandle(bondableId);
			BondableHandle bondableTargetHandle = null;
			Collection<ValueHolder> addedObjects = obj.getAddedObjects(RelationshipInfo.Attributes.targetEntities.name());
			if (addedObjects != null) {
				for (ValueHolder addedObject : addedObjects) {
					Persistent pojoBondableTarget = (Persistent) addedObject.getValue();
					if (pojoBondableTarget != pojoBondable) {
						CollabId targetId = getCollabId(((ManagedIdentifiableProxy)pojoBondableTarget.getManagedObjectProxy()).getObjectId());
						bondableTargetHandle = (BondableHandle) EntityUtils.getInstance().createHandle(targetId);
						break;
					}
				}
			}
			Persistent pojoRelationshipDefinition = (Persistent) getAttributeValue(pojoIdentifiable, RelationshipInfo.Attributes.relationshipDefinition.name());
			String relationshipType = (String) getAttributeValue(pojoRelationshipDefinition, EntityInfo.Attributes.name.name());
			String bondTypeName = pojoToCsiNameMap.get(relationshipType);
			BondType bondType = BondType.RELATED_MATERIALS;
			if (bondTypeName != null) {
				bondType = BondType.valueOf(bondTypeName);
			}
			CollabId id = getCollabId(obj.getObjectId());
			Bond bond = control.createBond(id.getEid(), bondName, bondType, bondableSourceHandle, bondableTargetHandle, bondUpdater, proj);
			assignChangeToken(pojoIdentifiable, bond.getSnapshotId().toString());
			return bond;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		BondControl control = ControlLocator.getInstance().getControl(BondControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		BondHandle bondHandle = (BondHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteBond(bondHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void loadBondsOnBondableEntity(ManagedIdentifiableProxy obj, Projection projection) {
		HashSet<Persistent> v = new HashSet<Persistent>();
		BondControl control = ControlLocator.getInstance().getControl(BondControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		BondableHandle bondableHandle = (BondableHandle) EntityUtils.getInstance().createHandle(id);
		PersistenceContext context = obj.getPersistenceContext();
		try {
			BondListFilter bondListFilter = BondFactory.getInstance().createBondListFilter();
			HashSet<Predicate> typePreds = new HashSet<Predicate>();
			BondType[] bTypes = BondType.values();
			for (BondType bType : bTypes) {
				BondTypePredicate pred = bondListFilter.createBondTypePredicate(bType);
				typePreds.add(pred);
			}
			MatchAnyPredicate pred = bondListFilter.createMatchAnyPredicate(typePreds);
			bondListFilter.setPredicate(pred);
			bondListFilter.setProjection(projection);
			ListResult<Bond> csiBonds = control.listEntityBonds(bondableHandle, bondListFilter);
			if (csiBonds != null) {
				Iterator<Bond> iter = csiBonds.iterator();
				while (iter.hasNext()) {
					Bond csiBond = iter.next();
					ManagedIdentifiableProxy bondObj = getEntityProxy(context, csiBond);
					bondObj.getProviderProxy().copyLoadedProjection(bondObj, csiBond, projection);
					Persistent pojoBond = bondObj.getPojoIdentifiable();
					v.add(pojoBond);
				}
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		obj.getProviderProxy().copyLazyAttribute(obj, RelationshipBondableInfo.Attributes.relationships.name(), null, v);
	}

}
