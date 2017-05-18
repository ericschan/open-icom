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


import com.oracle.beehive.AttributeTemplate;
import com.oracle.beehive.AttributeTemplateListUpdater;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Category;
import com.oracle.beehive.CategoryTemplate;
import com.oracle.beehive.CategoryTemplateCreateParameter;
import com.oracle.beehive.CategoryTemplateListUpdater;
import com.oracle.beehive.CategoryTemplateUpdateParameter;
import com.oracle.beehive.CategoryTemplateUpdater;

import icom.info.EntityInfo;
import icom.info.beehive.BeehiveCategoryApplicationTemplateInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Vector;


public class CategoryTemplateDAO extends BdkIdentifiableDAO {
	
	static CategoryTemplateDAO singleton = new CategoryTemplateDAO();
	
	public static CategoryTemplateDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.category);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.finale);
		basicAttributes.add(BeehiveCategoryApplicationTemplateInfo.Attributes.required);
	}

	protected CategoryTemplateDAO() {
		
	}

	public String getResourceType() {
		return "ctth";
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		CategoryTemplate bdkCategoryTemplate = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, bdkCategoryTemplate, proj);
		return proj;
	}
	
	public CategoryTemplate loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		CategoryTemplate bdkCategoryTemplate = null;
		// TODO
		return bdkCategoryTemplate;
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkIdentifiable, Projection proj) {
        Persistent pojoIdentifiable = obj.getPojoObject();
        CategoryTemplate bdkCategoryApplicationTemplate = (CategoryTemplate)bdkIdentifiable;
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(BeehiveCategoryApplicationTemplateInfo.Attributes.category.name(),
                                 lastLoadedProjection, proj)) {
            try {
                Category bdkCategory = bdkCategoryApplicationTemplate.getCategory();
                marshallAssignEntity(obj, BeehiveCategoryApplicationTemplateInfo.Attributes.category.name(),
                                     bdkCategory);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name(),
                                 lastLoadedProjection, proj)) {
            try {
                Collection<AttributeTemplate> propertyTemplates =
                    bdkCategoryApplicationTemplate.getAttributeTemplates();
                marshallMergeAssignIdentifiableDependents(obj,
                                                         BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name(),
                                                         propertyTemplates, Vector.class, proj);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name(),
                                 lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable,
                                     BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name(),
                                     new Boolean(bdkCategoryApplicationTemplate.isCopyOnVersion()));
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name(), lastLoadedProjection,
                                 proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name(),
                                     new Boolean(bdkCategoryApplicationTemplate.isFinal()));
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(BeehiveCategoryApplicationTemplateInfo.Attributes.required.name(),
                                 lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable,
                                     BeehiveCategoryApplicationTemplateInfo.Attributes.required.name(),
                                     new Boolean(bdkCategoryApplicationTemplate.isRequired()));
            } catch (Exception ex) {
                // ignore
            }
        }

    }
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		CategoryTemplateUpdater categoryTemplateUpdater = (CategoryTemplateUpdater) context.getChildUpdater();
		
		if (isChanged(obj, BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name())) {
			Boolean copyOnVersion = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.copyOnVersion.name());
			if (copyOnVersion != null) {
				categoryTemplateUpdater.setCopyOnVersion(copyOnVersion);
			} else {
				categoryTemplateUpdater.setCopyOnVersion(false);
			}
		}
		
		if (isChanged(obj, BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name())) {
			Boolean isFinal = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.finale.name());
			if (isFinal != null) {
				categoryTemplateUpdater.setFinal(isFinal);
			} else {
				categoryTemplateUpdater.setFinal(false);
			}
		}
		
		if (isChanged(obj, BeehiveCategoryApplicationTemplateInfo.Attributes.required.name())) {
			Boolean required = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.required.name());
			if (required != null) {
				categoryTemplateUpdater.setRequired(required);
			} else {
				categoryTemplateUpdater.setRequired(false);
			}
		}
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		CategoryTemplateListUpdater categoryTemplateListUpdater = (CategoryTemplateListUpdater) context.getUpdater();
		CategoryTemplateUpdateParameter param = new CategoryTemplateUpdateParameter();
		BeeId categoryTemplateId = getBeeId(obj.getObjectId().toString());
		param.setCategoryTemplateHandle(categoryTemplateId);
		CategoryTemplateUpdater categoryTemplateUpdater = new CategoryTemplateUpdater();
		param.setCategoryTemplateUpdater(categoryTemplateUpdater);
		categoryTemplateListUpdater.getUpdates().add(param);
		context.setChildUpdater(categoryTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
		
		AttributeTemplateListUpdater attributeTemplateListUpdater = categoryTemplateUpdater.getAttributeTemplatesUpdater();
		DAOContext subContext = new DAOContext(attributeTemplateListUpdater);
		
		Collection<Identifiable> pojoAttrTemplates;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		pojoAttrTemplates = getIdentifiableCollection(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name());
		if (pojoAttrTemplates != null) {
			for (Persistent pojoAttrTemplate : pojoAttrTemplates) {
				ManagedObjectProxy attrTemplateObj = pojoAttrTemplate.getManagedObjectProxy();
				if (attrTemplateObj.isDirty()) {
					AttributeTemplateDAO.getInstance().updateObjectState((ManagedIdentifiableProxy) attrTemplateObj, subContext);
				} else if (attrTemplateObj.isNew()) {
					AttributeTemplateDAO.getInstance().updateNewObjectState((ManagedIdentifiableProxy) attrTemplateObj, subContext);
				} else if (attrTemplateObj.isDeleted()) {
					throw new IllegalStateException("Deleted template should have been removed from the list");
				}
			}
		}
		
		Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name());
		if (removedObjects != null) {
			for (ValueHolder addedObjectHolder : removedObjects) {
				Persistent attrTemplate = (Persistent) addedObjectHolder.getValue();
				AttributeTemplateDAO.getInstance().updateObjectState((ManagedIdentifiableProxy) attrTemplate.getManagedObjectProxy(), subContext);
			}
		}
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		CategoryTemplateListUpdater categoryTemplateListUpdater = (CategoryTemplateListUpdater) context.getUpdater();
		CategoryTemplateCreateParameter param = new CategoryTemplateCreateParameter();
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		Persistent pojoCategory = (Persistent) getAttributeValue(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.category.name());
		BeeId categoryId = getBeeId(((ManagedIdentifiableProxy)pojoCategory.getManagedObjectProxy()).getObjectId().toString());
		param.setCategoryHandle(categoryId);
		CategoryTemplateUpdater categoryTemplateUpdater = new CategoryTemplateUpdater();
		param.setCategoryTemplateUpdater(categoryTemplateUpdater);
		String categoryName = (String) getAttributeValue(pojoCategory, EntityInfo.Attributes.name.name());
		param.setCategoryName(categoryName);
		categoryTemplateListUpdater.getAdds().add(param);
		context.setChildUpdater(categoryTemplateUpdater);
		updateNewOrOldObjectState(obj, context);
		
		Collection<Identifiable> pojoAttrTemplates;
		pojoAttrTemplates = getIdentifiableCollection(pojoIdentifiable, BeehiveCategoryApplicationTemplateInfo.Attributes.propertyTemplates.name());
		if (pojoAttrTemplates != null) {
			AttributeTemplateListUpdater attributeTemplateListUpdater = new AttributeTemplateListUpdater();
			categoryTemplateUpdater.setAttributeTemplatesUpdater(attributeTemplateListUpdater);
			DAOContext subContext = new DAOContext(attributeTemplateListUpdater);
			for (Persistent attrTemplate : pojoAttrTemplates) {
				AttributeTemplateDAO.getInstance().updateNewObjectState((ManagedIdentifiableProxy) attrTemplate.getManagedObjectProxy(), subContext);
			}
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj, DAOContext context) {
		CategoryTemplateListUpdater categoryTemplateListUpdater = (CategoryTemplateListUpdater) context.getUpdater();
		BeeId categoryTemplateId = getBeeId(obj.getObjectId().toString());
		categoryTemplateListUpdater.getRemoves().add(categoryTemplateId);
	}
	
}
