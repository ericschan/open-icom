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


import com.oracle.beehive.AttributeApplication;
import com.oracle.beehive.AttributeApplicationListUpdater;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.CategoryApplicationUpdater;
import com.oracle.beehive.Entity;

import icom.info.CategoryApplicationInfo;
import icom.info.EntityInfo;
import icom.info.beehive.BeehiveCategoryApplicationInfo;
import icom.info.beehive.BeehivePropertyInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.CategoryApplication;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;
import icom.jpa.dao.MetaDataApplicationDAO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import javax.persistence.PersistenceException;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;


public class CategoryApplicationDAO extends BdkIdentifiableDAO implements MetaDataApplicationDAO {
	
	static CategoryApplicationDAO singleton = new CategoryApplicationDAO();
	
	public static CategoryApplicationDAO getInstance() {
		return singleton;
	}
	
	{

	}
	
	{
		fullAttributes.add(CategoryApplicationInfo.Attributes.category);
		fullAttributes.add(CategoryApplicationInfo.Attributes.properties);
		fullAttributes.add(CategoryApplicationInfo.Attributes.attachedEntity);
	}
	
	{
		lazyAttributes.add(BeehiveCategoryApplicationInfo.Attributes.categoryApplicationTemplate);
	}

	protected CategoryApplicationDAO() {
		
	}

	public String getResourceType() {
		return "ctap";
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		CategoryApplication bdkCategoryApplication = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, bdkCategoryApplication, proj);
		return proj;
	}
	
	public CategoryApplication loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			GetMethod method = prepareGetMethod(resourceType, collabId, proj);
			CategoryApplication bdkCategoryApplication = (CategoryApplication) bdkHttpUtil.execute(CategoryApplication.class, method, userContext.httpClient);
			return bdkCategoryApplication;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

	public void copyObjectState(ManagedObjectProxy obj, Object bdkIdentifiable, Projection proj) {
		Persistent pojoIdentifiable = obj.getPojoObject();
		//Projection lastLoadedProjection = obj.getProviderProxy().getLastLoadedProjection(obj);
		
		if (/* !isPartOfProjection(CategoryApplicationInfo.Attributes.attachedEntity.name(), lastLoadedProjection) && */
				isPartOfProjection(CategoryApplicationInfo.Attributes.attachedEntity.name(),  proj)) {
			try {
				Entity bdkCategorizedEntity  = null; // bdkCategoryApplication.getCategorizedEntity();
				marshallAssignEntity(obj, CategoryApplicationInfo.Attributes.attachedEntity.name(),
                                                    bdkCategorizedEntity);
			} catch (Exception ex) {
				// ignore
			}
		}
		if (/* !isPartOfProjection(CategoryApplicationInfo.Attributes.category.name(), lastLoadedProjection) && */
				isPartOfProjection(CategoryApplicationInfo.Attributes.category.name(),  proj)) {
			try {
				/*
				Category bdkCategory = bdkCategoryApplication.getCategory();
				marshallAssignEntity(obj, CategoryApplicationInfo.Attributes.category.name(),
                                                    bdkCategory);
				*/
			} catch (Exception ex) {
				// ignore
			}
		}
		if (/* !isPartOfProjection(CategoryApplicationInfo.Attributes.properties.name(), lastLoadedProjection) && */
				isPartOfProjection(CategoryApplicationInfo.Attributes.properties.name(), proj)) {
			try {
				Collection<AttributeApplication> list = null; // bdkCategoryApplication.getAttributeApplications();
				Collection<Persistent> pojoPropertes = marshallAssignEmbeddableObjects(obj, 
                                                                            CategoryApplicationInfo.Attributes.properties.name(), list,
                                                                            Vector.class, proj);
                                for (Persistent property : pojoPropertes) {
                                    assignAttributeValue(property, BeehivePropertyInfo.TransientAttributes.categoryApplication.name(), pojoIdentifiable);
                                }	
			} catch (Exception ex) {
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
        CategoryApplicationUpdater updater = (CategoryApplicationUpdater)context.getUpdater();
        if (updater != null) {
            AttributeApplicationListUpdater attrListUpdater = updater.getAttributeValuesUpdater();

            if (isChanged(obj, CategoryApplicationInfo.Attributes.properties.name())) {
                Persistent pojoIdentifiable = obj.getPojoIdentifiable();
                Collection<Persistent> properties = getPersistentCollection(pojoIdentifiable, CategoryApplicationInfo.Attributes.properties.name());
                AttributeChangeSet changeSet = getAttributeChanges(obj, properties, CategoryApplicationInfo.Attributes.properties.name());
                for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                    Persistent attrTemplate = (Persistent)getAttributeValue(addedPojoObject, BeehivePropertyInfo.TransientAttributes.propertyTemplate.name());
                    BeeId attributeTemplateId = getBeeId(((ManagedIdentifiableProxy)attrTemplate.getManagedObjectProxy()).getObjectId().toString());
                    AttributeApplicationDAO.getInstance().updateObjectState(addedPojoObject, attrListUpdater,
                                                                            attributeTemplateId,
                                                                            AttributeApplicationDAO.Operand.ADD);
                }
                for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                    Persistent attrTemplate = (Persistent)getAttributeValue(removedPojoObject, BeehivePropertyInfo.TransientAttributes.propertyTemplate.name());
                    BeeId attributeTemplateId = getBeeId(((ManagedIdentifiableProxy)attrTemplate.getManagedObjectProxy()).getObjectId().toString());
                    AttributeApplicationDAO.getInstance().updateObjectState(removedPojoObject, attrListUpdater,
                                                                            attributeTemplateId,
                                                                            AttributeApplicationDAO.Operand.REMOVE);
                }
                for (Persistent modifiedPojoObject : changeSet.modifiedPojoObjects) {
                    Persistent attrTemplate = (Persistent)getAttributeValue(modifiedPojoObject, BeehivePropertyInfo.TransientAttributes.propertyTemplate.name());
                    BeeId attributeTemplateId = getBeeId(((ManagedIdentifiableProxy)attrTemplate.getManagedObjectProxy()).getObjectId().toString());
                    AttributeApplicationDAO.getInstance().updateObjectState(modifiedPojoObject, attrListUpdater,
                                                                            attributeTemplateId,
                                                                            AttributeApplicationDAO.Operand.MODIFY);
                }
            }
        }
    }
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		CategoryApplicationUpdater updater = new CategoryApplicationUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	// called from DocumentDAO
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public CategoryApplication concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryApplicationUpdater updater = (CategoryApplicationUpdater) context.getUpdater();
		/*
		Persistent pojoCatApp = obj.getPojoIdentifiable();
		Persistent pojoCat = getCategory(pojoCatApp);
		BeeId catId = getBeeId(((ManagedIdentifiableProxy)pojoCat.getManagedObjectProxy()).getObjectId().toString());
		Persistent pojoEntity = getCategorizedEntity(pojoCatApp);
		BeeId entityId = getBeeId(((ManagedIdentifiableProxy)pojoEntity.getManagedObjectProxy()).getObjectId().toString());
		*/
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			PutMethod putMethod = preparePutMethod(resourceType, collabId, userContext.antiCSRF, Projection.EMPTY);
			CategoryApplication bdkCategoryApplication = (CategoryApplication) bdkHttpUtil.execute(CategoryApplication.class, putMethod, updater, userContext.httpClient);
			return bdkCategoryApplication;
		} catch (Exception ex) {
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
	
	public CategoryApplication concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		CategoryApplicationUpdater updater = (CategoryApplicationUpdater) context.getUpdater();
		/*
		Identifiable pojoCatApp = obj.getPojoIdentifiable();
		Persistent pojoCat = getCategory(pojoCatApp);
		BeeId catId = getBeeId(((ManagedIdentifiableProxy)pojoCat.getManagedObjectProxy()).getObjectId().toString());
		Persistent pojoEntity = getCategorizedEntity(pojoCatApp);
		BeeId entityId = getBeeId(((ManagedIdentifiableProxy)pojoEntity.getManagedObjectProxy()).getObjectId().toString());
		*/
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			PutMethod putMethod = preparePutMethod(resourceType, collabId, userContext.antiCSRF, Projection.BASIC);
			CategoryApplication bdkCategoryApplication = (CategoryApplication) bdkHttpUtil.execute(CategoryApplication.class, putMethod, updater, userContext.httpClient);
			copyObjectState(obj, bdkCategoryApplication, Projection.BASIC);
			//BeeId newBeeId = null; // bdkCategoryApplication.getObjectId();
			//assignObjectId(pojoCatApp, newBeeId.getId());
			obj.getPersistenceContext().recacheIdentifiableDependent(obj);
			return bdkCategoryApplication;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			DeleteMethod deleteMethod = prepareDeleteMethod(resourceType, collabId, userContext.antiCSRF);
			bdkHttpUtil.execute(deleteMethod, userContext.httpClient);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
		
	}
	
	void loadCategoryApplicationsOnEntity(ManagedIdentifiableProxy obj, Projection projection) {
		HashSet<Persistent> v = new HashSet<Persistent>();
		/*
		PersistenceContext context = obj.getPersistenceContext();
		try {
			Collection<CategoryApplication> bdkCatApps = null; // TODO
			if (bdkCatApps != null) {
				Iterator<CategoryApplication> iter = bdkCatApps.iterator();
				while (iter.hasNext()) {
					CategoryApplication bdkCategoryApplication = iter.next();
					ManagedIdentifiableProxy catAppObj = getIdentifiableDependentProxy(context, bdkCategoryApplication, obj, EntityInfo.Attributes.categoryApplications.name());
					catAppObj.getProviderProxy().copyLoadedProjection(catAppObj, bdkCategoryApplication, projection);
					Persistent pojoCategoryApplication = catAppObj.getPojoIdentifiable();
					v.add(pojoCategoryApplication);
				}
			}
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
		*/
		obj.getProviderProxy().copyLazyAttribute(obj, EntityInfo.Attributes.categoryApplications.name(), null, v);
	}
	
	public Persistent getCategory(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, CategoryApplicationInfo.Attributes.category.name());
	}
	
	public Persistent getCategorizedEntity(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, CategoryApplicationInfo.Attributes.attachedEntity.name());
	}

	public Persistent getCategoryApplicationTemplate(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationInfo.Attributes.categoryApplicationTemplate.name());
	}

}
