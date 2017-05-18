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


import com.oracle.beehive.AssociativeArray;
import com.oracle.beehive.AssociativeArrayEntry;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Bond;
import com.oracle.beehive.BondCreator;
import com.oracle.beehive.BondType;
import com.oracle.beehive.BondTypePredicate;
import com.oracle.beehive.BondUpdater;
import com.oracle.beehive.CollabProperties;
import com.oracle.beehive.CollabPropertiesUpdater;
import com.oracle.beehive.CollabProperty;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.ListResult;
import com.oracle.beehive.MatchAnyPredicate;
import com.oracle.beehive.Predicate;

import icom.info.ArtifactInfo;
import icom.info.BeanHandler;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.RelationshipBondableInfo;
import icom.info.RelationshipInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;


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
	
	
	static HashMap<String, String> bdkToPojoNameMap;
	static HashMap<String, String> pojoToCsiNameMap;
	
	{
		bdkToPojoNameMap = new HashMap<String, String>();
		pojoToCsiNameMap = new HashMap<String, String>();
		bdkToPojoNameMap.put(BondType.DISCUSS_THIS.name(), "Discussion Thread");
		bdkToPojoNameMap.put(BondType.FOLLOW_UP.name(), "Follow Up Thread");
		bdkToPojoNameMap.put(BondType.REFERENCED_MATERIALS.name(), "Referenced Materials");
		bdkToPojoNameMap.put(BondType.RELATED_MATERIALS.name(), "Related Materials");
		for (String key : bdkToPojoNameMap.keySet()) {
			pojoToCsiNameMap.put(bdkToPojoNameMap.get(key), key);
		}
	}

	protected BondDAO() {
	}

	public String getResourceType() {
		return "bond";
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        Bond bdkBond = (Bond)bdkEntity;
        Persistent pojoRelationship = obj.getPojoObject();

        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(RelationshipInfo.Attributes.relationshipDefinition.name(), lastLoadedProjection,
                                 proj)) {
            try {
                BondType type = bdkBond.getBondType();
                String relationshipType = bdkToPojoNameMap.get(type.name());
                Object pojoRelationshipDefinition =
                    BeanHandler.instantiatePojoObject(BeanHandler.getBeanPackageName() + "." +
                                                      IcomBeanEnumeration.RelationshipDefinition.name());
                assignAttributeValue(pojoRelationshipDefinition, EntityInfo.Attributes.name.name(), relationshipType);
                assignAttributeValue(pojoRelationship, RelationshipInfo.Attributes.relationshipDefinition.name(),
                                     pojoRelationshipDefinition);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(RelationshipInfo.Attributes.sourceEntity.name(), lastLoadedProjection, proj)) {
            try {
                Object bdkBondable = bdkBond.getRoot();
                marshallAssignEntity(obj, RelationshipInfo.Attributes.sourceEntity.name(), bdkBondable);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(RelationshipInfo.Attributes.targetEntities.name(), lastLoadedProjection, proj)) {
            try {
                ListResult bdkBondEntityRelationList = bdkBond.getBondedEntities();
                if (bdkBondEntityRelationList != null) {
                    List<Object> elements = bdkBondEntityRelationList.getElements();
                    marshallMergeAssignEntities(obj,
                                              RelationshipInfo.Attributes.targetEntities.name(),
                                              elements, HashSet.class);
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(RelationshipInfo.Attributes.properties.name(), lastLoadedProjection, proj)) {
            try {
                Vector<Object> v = new Vector<Object>();
                CollabProperties propertyMaps = bdkBond.getProperties();
                if (propertyMaps != null) {
                    AssociativeArray properties = propertyMaps.getMap();
                    List<AssociativeArrayEntry> entries = properties.getEntries();
                    for (AssociativeArrayEntry entry : entries) {
                        CollabProperty bdkCollabProperty = (CollabProperty)entry.getValue();
                        v.add(bdkCollabProperty);
                    }
                }
                marshallAssignEmbeddableObjects(obj, RelationshipInfo.Attributes.properties.name(), v,
                                                          Vector.class, proj);

            } catch (Exception ex) {
                // ignore
            }
        }
    }

    private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
        Persistent pojoIdentifiable = obj.getPojoIdentifiable();
        BondUpdater bondUpdater = (BondUpdater)context.getUpdater();

        if (isChanged(obj, RelationshipInfo.Attributes.targetEntities.name())) {
            AttributeChangeSet changeSet = getAttributeChanges(obj, null, RelationshipInfo.Attributes.targetEntities.name());
            /*
            for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                BeeId bondableEntityId =
                    getBeeId(((ManagedIdentifiableProxy)addedPojoObject.getManagedObjectProxy()).getObjectId().toString());
                // TODO add bondableEntityId to bond updater
                //bondUpdater.getAddBondEntityRelations().add(bondableEntityId);
            }
            */
            for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                BeeId bondableEntityId =
                    getBeeId(((ManagedIdentifiableProxy)removedPojoObject.getManagedObjectProxy()).getObjectId().toString());
                bondUpdater.getRemoveBondEntityRelations().add(bondableEntityId);
            }
        }

        if (isChanged(obj, RelationshipInfo.Attributes.properties.name())) {
            Collection<Persistent> properties =
                getPersistentCollection(pojoIdentifiable, RelationshipInfo.Attributes.properties.name());
            AttributeChangeSet changeSet = getAttributeChanges(obj, properties, ArtifactInfo.Attributes.properties.name());
            CollabPropertiesUpdater propertiesUpdater = bondUpdater.getPropertiesUpdater();
            for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                CollabPropertyDAO.getInstance().updateObjectState(addedPojoObject, propertiesUpdater,
                                                                  CollabPropertyDAO.Operand.ADD);
            }
            for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                CollabPropertyDAO.getInstance().updateObjectState(removedPojoObject, propertiesUpdater,
                                                                  CollabPropertyDAO.Operand.REMOVE);
            }
            for (Persistent modifiedPojoObject : changeSet.modifiedPojoObjects) {
                CollabPropertyDAO.getInstance().updateObjectState(modifiedPojoObject, propertiesUpdater,
                                                                  CollabPropertyDAO.Operand.MODIFY);
            }
        }

    }
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		BondCreator creator = (BondCreator) context.getCreator();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setBondName(name);
		Identifiable sourceEntity = (Identifiable) getAttributeValue(pojoIdentifiable, RelationshipInfo.Attributes.sourceEntity.name());
		if (sourceEntity != null) {
			BeeId rootHandle = getBeeId(sourceEntity.getObjectId().getObjectId().toString());
			creator.setRoot(rootHandle);
		}
		
		Collection<ValueHolder> addedObjects = obj.getAddedObjects(RelationshipInfo.Attributes.targetEntities.name());
		if (addedObjects != null) {
			for (ValueHolder addedObject : addedObjects) {
				Identifiable pojoBondableTarget = (Identifiable) addedObject.getValue();
				if (pojoBondableTarget != sourceEntity) {
					BeeId targetId = getBeeId(pojoBondableTarget.getObjectId().getObjectId().toString());
					creator.setTarget(targetId);
					break;
				}
			}
		}
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public void loadBondsOnBondableEntity(ManagedIdentifiableProxy obj, Projection projection) {
		HashSet<Persistent> v = new HashSet<Persistent>();
		PersistenceContext context = obj.getPersistenceContext();
		HashSet<Predicate> typePreds = new HashSet<Predicate>();
		BondType[] bTypes = BondType.values();
		for (BondType bType : bTypes) {
			BondTypePredicate pred = new BondTypePredicate();
			pred.setBondType(bType);
			typePreds.add(pred);
		}
		MatchAnyPredicate pred = new MatchAnyPredicate();
		pred.getPredicates().addAll(typePreds);
		List<Object> bdkBonds = listEntities(context, Bond.class, pred, getResourceType(), projection);
		for (Object bdkBond : bdkBonds) {
			ManagedIdentifiableProxy bondObj = getEntityProxy(context, bdkBond);
			bondObj.getProviderProxy().copyLoadedProjection(bondObj, bdkBond, projection);
			Persistent pojoBond = bondObj.getPojoIdentifiable();
			v.add(pojoBond);
		}
		obj.getProviderProxy().copyLazyAttribute(obj, RelationshipBondableInfo.Attributes.relationships.name(), null, v);
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Bond.class;
	}
	
	protected BondUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new BondUpdater();
	}
	
	protected BondUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		BondUpdater updater = getBdkUpdater(obj);
		((BondCreator)creator).setBu(updater);
		return updater;
	}
	
	protected BondCreator getBdkCreator(ManagedObjectProxy obj) {
		return new BondCreator();
	}

}
