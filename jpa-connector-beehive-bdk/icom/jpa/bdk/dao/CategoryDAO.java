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


import com.oracle.beehive.AttributeDefinition;
import com.oracle.beehive.AttributeDefinitionListUpdater;
import com.oracle.beehive.Category;
import com.oracle.beehive.CategoryCreator;
import com.oracle.beehive.CategoryTemplate;
import com.oracle.beehive.CategoryUpdater;
import com.oracle.beehive.EntityCreator;

import icom.info.CategoryInfo;
import icom.info.EntityInfo;
import icom.info.beehive.BeehiveCategoryInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.PersistenceException;


public class CategoryDAO extends MarkerDAO {
	
	static CategoryDAO singleton = new CategoryDAO();
	
	public static CategoryDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(CategoryInfo.Attributes.propertyDefinitions);
		basicAttributes.add(CategoryInfo.Attributes.abstrazt);
	}
	
	{
		fullAttributes.add(CategoryInfo.Attributes.superCategories);
		fullAttributes.add(CategoryInfo.Attributes.subCategories);
		fullAttributes.add(BeehiveCategoryInfo.Attributes.defaultTemplate);
	}

	protected CategoryDAO() {
	}

	public String getResourceType() {
		return "catg";
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        Category bdkCategory = (Category)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoObject();
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(CategoryInfo.Attributes.superCategories.name(), lastLoadedProjection, proj)) {
            try {
                Category bdkSuperCategory = bdkCategory.getParentCategory();
                //marshallAssignEntity(obj, CategoryInfo.Attributes.superCategory.name(), bdkSuperCategory);
                // changed cardinality of superCategory to multi for ICOM V1
                Collection<Category> bdkSuperCategories = new Vector<Category>();
                bdkSuperCategories.add(bdkSuperCategory);
                marshallMergeAssignEntities(obj, CategoryInfo.Attributes.subCategories.name(),
                		bdkSuperCategories, HashSet.class);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(CategoryInfo.Attributes.subCategories.name(), lastLoadedProjection, proj)) {
            try {
                Collection<Category> bdkSubCategories = bdkCategory.getSubCategories();
                marshallMergeAssignEntities(obj, CategoryInfo.Attributes.subCategories.name(),
                                                                  bdkSubCategories, HashSet.class);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(CategoryInfo.Attributes.propertyDefinitions.name(), lastLoadedProjection, proj)) {
            try {
                Collection<AttributeDefinition> propertyDefinitions = bdkCategory.getAttributeDefinitions();
                marshallMergeAssignIdentifiableDependents(obj, CategoryInfo.Attributes.propertyDefinitions.name(),
                                                         propertyDefinitions, Vector.class, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(CategoryInfo.Attributes.abstrazt.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.abstrazt.name(),
                                     new Boolean(bdkCategory.isAbstract()));
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(BeehiveCategoryInfo.Attributes.defaultTemplate.name(), lastLoadedProjection, proj)) {
            try {
                CategoryTemplate bdkCategoryTemplate = bdkCategory.getDefaultTemplate();
                marshallAssignEntity(obj, BeehiveCategoryInfo.Attributes.defaultTemplate.name(),
                                                      bdkCategoryTemplate);
            } catch (Exception ex) {
                // ignore
            }
        }

    }
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		
		if (isChanged(obj, CategoryInfo.Attributes.abstrazt.name())) {
			CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
			Boolean abztract = (Boolean) getAttributeValue(pojoIdentifiable, CategoryInfo.Attributes.abstrazt.name());
			categoryUpdater.setAbstract(abztract);
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
		
		CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
		AttributeDefinitionListUpdater attrDefnListUpdater = new AttributeDefinitionListUpdater();
		categoryUpdater.setAttributeDefinitionsUpdater(attrDefnListUpdater);
		DAOContext subContext = new DAOContext(attrDefnListUpdater);
		
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		Collection<Identifiable> pojoAttrDefns;
		pojoAttrDefns = getIdentifiableCollection(pojoIdentifiable, CategoryInfo.Attributes.propertyDefinitions.name());
		if (pojoAttrDefns != null) {
			for (Persistent pojoAttrDefinition : pojoAttrDefns) {
				ManagedIdentifiableProxy attrDefinitionObj = (ManagedIdentifiableProxy) pojoAttrDefinition.getManagedObjectProxy();
				if (attrDefinitionObj.isNew()) {
					AttributeDefinitionDAO.getInstance().updateNewObjectState(attrDefinitionObj, subContext);
				} else if (attrDefinitionObj.isDirty()) {
					AttributeDefinitionDAO.getInstance().updateObjectState(attrDefinitionObj, subContext);
				} else if (attrDefinitionObj.isDeleted()) {
					throw new IllegalStateException("Deleted attribute definition should have been removed from the list");
				}
			}
		}
		
		Collection<ValueHolder> removedObjects = obj.getRemovedObjects(CategoryInfo.Attributes.propertyDefinitions.name());
		if (removedObjects != null) {
			for (ValueHolder addedObjectHolder : removedObjects) {
				Persistent attrDefinition = (Persistent) addedObjectHolder.getValue();
				AttributeDefinitionDAO.getInstance().delete((ManagedIdentifiableProxy) attrDefinition.getManagedObjectProxy(), subContext);
			}
		}
		
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		CategoryCreator creator = (CategoryCreator) context.getCreator();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setCategoryName(name);
		
		updateNewOrOldObjectState(obj, context);
		
		Collection<Identifiable> pojoAttrDefns;
		pojoAttrDefns = getIdentifiableCollection(pojoIdentifiable, CategoryInfo.Attributes.propertyDefinitions.name());
		if (pojoAttrDefns != null) {
			CategoryUpdater categoryUpdater = (CategoryUpdater) context.getUpdater();
			AttributeDefinitionListUpdater attrDefnListUpdater = categoryUpdater.getAttributeDefinitionsUpdater();
			DAOContext subContext = new DAOContext(attrDefnListUpdater);
			Iterator<Identifiable> pojoAttrDefnsIter = pojoAttrDefns.iterator();
			while (pojoAttrDefnsIter.hasNext()) {
				Persistent attrDefinition = pojoAttrDefnsIter.next();
				AttributeDefinitionDAO.getInstance().updateNewObjectState((ManagedIdentifiableProxy) attrDefinition.getManagedObjectProxy(), subContext);
			}
		}
	}
	
	public Set<Persistent> loadCategories(ManagedIdentifiableProxy obj, Projection proj) {
		PersistenceContext context = obj.getPersistenceContext();
		List<Object> bdkCategories = listEntities(context, Category.class, null, getResourceType(), proj);
		try {
			if (bdkCategories != null) {
				Set<Persistent> pojoCategories = new HashSet<Persistent>(bdkCategories.size());
				for (Object bdkObject : bdkCategories) {
					Category bdkCategory = (Category) bdkObject;
					ManagedIdentifiableProxy categoryObj = getEntityProxy(context, bdkCategory);
					categoryObj.getProviderProxy().copyLoadedProjection(categoryObj, bdkCategory, proj);
					pojoCategories.add(categoryObj.getPojoIdentifiable());
				}
				return pojoCategories;
			} else {
				return new HashSet<Persistent>();
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Category.class;
	}

	protected CategoryUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new CategoryUpdater();
	}

	protected CategoryUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		CategoryUpdater updater = getBdkUpdater(obj);
		((CategoryCreator)creator).setUpdater(updater);
		return updater;
	}

	protected CategoryCreator getBdkCreator(ManagedObjectProxy obj) {
		return new CategoryCreator();
	}
	
}
