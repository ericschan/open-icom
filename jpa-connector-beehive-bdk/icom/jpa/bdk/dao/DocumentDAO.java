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


import com.oracle.beehive.BeeId;
import com.oracle.beehive.CategoryApplicationListUpdater;
import com.oracle.beehive.CategoryApplicationUpdateParameter;
import com.oracle.beehive.CategoryApplicationUpdater;
import com.oracle.beehive.ConflictResolutionMode;
import com.oracle.beehive.Content;
import com.oracle.beehive.Document;
import com.oracle.beehive.DocumentCreator;
import com.oracle.beehive.DocumentUpdater;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.IdentifiableSimpleContent;
import com.oracle.beehive.IdentifiableSimpleContentUpdater;
import com.oracle.beehive.Version;
import com.oracle.beehive.VersionUpdater;

import icom.info.AbstractBeanInfo;
import icom.info.ArtifactInfo;
import icom.info.BeanHandler;
import icom.info.DocumentInfo;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.VersionInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AttributeChangeSet;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;

import javax.persistence.PersistenceException;


public class DocumentDAO extends ArtifactDAO {

	static DocumentDAO singleton = new DocumentDAO();
	
	static boolean useCategoryApplicationListUpdater = true;
	
	public static DocumentDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(ArtifactInfo.Attributes.description);
		basicAttributes.add(DocumentInfo.Attributes.size);
		basicAttributes.add(DocumentInfo.Attributes.versionType);
	}

	{
		fullAttributes.add(DocumentInfo.Attributes.content);
		fullAttributes.add(DocumentInfo.Attributes.versionControlMetadata);
	}
	
	public enum VersionType {
		VersionedCopy,	
		PrivateWorkingCopy,
		RepresentativeCopy,
		NonVersionControlledCopy
	}
	
	protected DocumentDAO() {
		
	}
	
	public String getResourceType() {
		return "adoc";
	}
	
	public boolean embedAsNonIdentifiableDependent() {
		return true;
	}

	public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
		super.copyObjectState(obj, bdkEntity, proj);
		
		Document bdkDocument = (Document) bdkEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
		BdkProjectionManager projManager = (BdkProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ArtifactInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(), bdkDocument.getDescription());
			} catch (Exception ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(DocumentInfo.Attributes.size.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.size.name(), bdkDocument.getTotalSize());
			} catch (Exception ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(DocumentInfo.Attributes.versionType.name(), lastLoadedProjection, proj)) {
			try {
				boolean isVersion = bdkDocument.isVersion();
				boolean isRepresentativeCopy = bdkDocument.isFamily();
				boolean isPrivateWorkingCopy = bdkDocument.isWorkingCopy();
				boolean isVersionedCopy = isVersion && ! isPrivateWorkingCopy;
				boolean isVersionControlled = bdkDocument.isVersionControlled();
				
				if (isRepresentativeCopy && isVersionControlled) {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.RepresentativeCopy.name());
				} else if (isPrivateWorkingCopy) {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.PrivateWorkingCopy.name());
				} else if (isVersionedCopy) {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.VersionedCopy.name());
				} else {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.NonVersionControlledCopy.name());
				}
			} catch (Exception ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(DocumentInfo.Attributes.versionControlMetadata.name(), lastLoadedProjection, proj)) {
			try {
				boolean isFamily = bdkDocument.isFamily();
				if (isFamily) {
					Document bdkParentFamilyDocument = bdkDocument;
					ManagedIdentifiableProxy versionSeriesObj = getIdentifiableDependentProxyOfVersionSeries(context, bdkParentFamilyDocument, obj, DocumentInfo.Attributes.versionControlMetadata.name());
					versionSeriesObj.getProviderProxy().copyLoadedProjection(versionSeriesObj, bdkParentFamilyDocument, proj);
					Persistent pojoVersionSeries = versionSeriesObj.getPojoIdentifiable();
					Persistent pojoRepresentativeVersionable = (Persistent) pojoIdentifiable;
					assignAttributeValue(pojoRepresentativeVersionable, DocumentInfo.Attributes.versionControlMetadata.name(), pojoVersionSeries);
					VersionSeriesToDocumentDAO.getInstance().copyRepresentativeCopy(pojoVersionSeries, pojoRepresentativeVersionable);
				} else {
					Version bdkVersion = bdkDocument.getParentVersion();
					if (bdkVersion != null) {
						ManagedIdentifiableProxy versionObj = getEntityProxy(context, bdkVersion);
						versionObj.getProviderProxy().copyLoadedProjection(versionObj, bdkVersion, Projection.EMPTY);
						Persistent pojoVersion = versionObj.getPojoIdentifiable();
						assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.versionControlMetadata.name(), pojoVersion);
						Object bdkParentFamilyDocument = bdkDocument.getParentFamily();
						ManagedIdentifiableProxy representativeCopyObj = getEntityProxy(context, bdkParentFamilyDocument);
						if (proj != Projection.EMPTY) {
							representativeCopyObj.getProviderProxy().copyLoadedProjection(representativeCopyObj, bdkParentFamilyDocument, proj);
						}
						Persistent pojoRepresentativeCopy = representativeCopyObj.getPojoIdentifiable();
						Persistent pojoVersionedCopy = (Persistent) pojoIdentifiable;
						VersionDAO.getInstance().copyRepresentativeAndVersionedCopies(pojoVersion, pojoRepresentativeCopy, pojoVersionedCopy);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (/* always copy content  !isPartOfProjection(DocumentInfo.Attributes.content.name(), lastLoadedProjection) && */
				isPartOfProjection(DocumentInfo.Attributes.content.name(),  proj)) {
			try {
				Content bdkChildContent  = bdkDocument.getContent();
				if (bdkChildContent != null) {
					boolean createDependentProxy = true;
					Object pojoContent = getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
					if (pojoContent != null) {
						ManagedIdentifiableProxy contentObj = (ManagedIdentifiableProxy) getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
						if (contentObj != null) {
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
							createDependentProxy = false;
						}
					}
					if (createDependentProxy) {
						ManagedObjectProxy contentObj = getNonIdentifiableDependentProxy(context, bdkChildContent, obj, DocumentInfo.Attributes.content.name());
						// TODO how to use identifiable dependent proxy when contents of document versions share the same id
						// context.getNonIdentifiableDependentProxy((IdentifiableSimpleContent) bdkChildContent, obj, DocumentInfo.Attributes.content.name());
						if (bdkChildContent instanceof IdentifiableSimpleContent) {
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
						}
						assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name(), contentObj.getPojoObject());
					}
				}
			} catch (Exception ex) {
				// ignore
			}
		}
	}
	
	ManagedIdentifiableProxy getIdentifiableDependentProxyOfVersionSeries(PersistenceContext context, Object bdkParentFamilyDocument,
			ManagedObjectProxy parent, String parentAttributeName) {
		DataAccessStateObject dataAccessStateObject = VersionSeriesToDocumentDAO.getInstance().morphDataAccessStateObjectFromDocumentToVersionSeries(bdkParentFamilyDocument);
		return context.getIdentifiableDependentProxy(IcomBeanEnumeration.VersionSeries.name(), dataAccessStateObject, parent, parentAttributeName);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		DocumentUpdater documentUpdater = (DocumentUpdater) context.getUpdater();
		Persistent pojoDocument = obj.getPojoIdentifiable();
		if (isChanged(obj, ArtifactInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoDocument, ArtifactInfo.Attributes.description.name());
			documentUpdater.setDescription(description);
		}
		
		if (isChanged(obj, DocumentInfo.Attributes.content.name())) {
			IdentifiableSimpleContentUpdater contentUpdater =  new IdentifiableSimpleContentUpdater(); 
			documentUpdater.setContentUpdater(contentUpdater);
			Persistent pojoContent = getContent(pojoDocument);
			if (pojoContent != null) {
				DAOContext childContext = new DAOContext(contentUpdater);
				String uploadScopeId = generateUUID();
				childContext.setUploadScopeId(uploadScopeId);
				SimpleContentDAO.getInstance().updateObjectState(pojoContent.getManagedObjectProxy(), childContext);
				context.setUploadScopeId(childContext.getUploadScopeId());
			}
		}
	}

	protected void updateCategoryApplication(ManagedIdentifiableProxy obj) {
		if (!useCategoryApplicationListUpdater) {
			// the category update is done in updateCsiObjectState
			super.updateCategoryApplication(obj);
		}
	}

    public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
        super.updateObjectState(obj, context);

        updateNewOrOldObjectState(obj, context);

        if (useCategoryApplicationListUpdater) {
            DocumentUpdater documentUpdater = (DocumentUpdater)context.getUpdater();
            if (isChanged(obj, EntityInfo.Attributes.categoryApplications.name())) {
                Persistent pojoIdentifiable = obj.getPojoIdentifiable();
                Collection<Persistent> catApps = getCategoryApplications(pojoIdentifiable);
                AttributeChangeSet changeSet = getAttributeChanges(obj, catApps, EntityInfo.Attributes.categoryApplications.name());
                for (Persistent addedPojoObject : changeSet.addedPojoObjects) {
                    ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)addedPojoObject.getManagedObjectProxy();
                    CategoryApplicationListUpdater listUpdater = documentUpdater.getCategoryApplicationsUpdater();
                    Persistent pojoCatAppTemplate = CategoryApplicationDAO.getInstance().getCategoryApplicationTemplate(addedPojoObject);
                    if (pojoCatAppTemplate != null) {
                        BeeId categoryTemplateHandle = getBeeId(((ManagedIdentifiableProxy)pojoCatAppTemplate.getManagedObjectProxy()).getObjectId().toString());
                        CategoryApplicationUpdateParameter param = new CategoryApplicationUpdateParameter();
                        param.setCategoryTemplateHandle(categoryTemplateHandle);
                        CategoryApplicationUpdater updater = new CategoryApplicationUpdater();
                        DAOContext subContext = new DAOContext(updater);
                        CategoryApplicationDAO.getInstance().updateNewObjectState(catAppObj, subContext);
                        param.setCategoryApplicationUpdater(updater);
                        listUpdater.getAdds().add(param);
                    }
                }
                for (Persistent removedPojoObject : changeSet.removedPojoObjects) {
                    ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)removedPojoObject.getManagedObjectProxy();
                    CategoryApplicationDAO.getInstance().delete(catAppObj);
                }
                for (Persistent modifiedPojoObject : changeSet.modifiedPojoObjects) {
                    ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)modifiedPojoObject.getManagedObjectProxy();
                    CategoryApplicationListUpdater listUpdater = documentUpdater.getCategoryApplicationsUpdater();
                    Persistent pojoCatAppTemplate = CategoryApplicationDAO.getInstance().getCategoryApplicationTemplate(modifiedPojoObject);
                    if (pojoCatAppTemplate != null) {
                        BeeId categoryTemplateHandle = getBeeId(((ManagedIdentifiableProxy)pojoCatAppTemplate.getManagedObjectProxy()).getObjectId().toString());
                        CategoryApplicationUpdateParameter param = new CategoryApplicationUpdateParameter();
                        param.setCategoryTemplateHandle(categoryTemplateHandle);
                        CategoryApplicationUpdater updater = new CategoryApplicationUpdater();
                        DAOContext subContext = new DAOContext(updater);
                        CategoryApplicationDAO.getInstance().updateObjectState(catAppObj, subContext);
                        param.setCategoryApplicationUpdater(updater);
                        listUpdater.getUpdates().add(param);
                    }
                }
            }
        }
    }
	
	protected void updateCategoryApplicationOnNewEntity(ManagedIdentifiableProxy obj) {
		if (!useCategoryApplicationListUpdater) {
			// the category update is done in updateNewCsiObjectState
			super.updateCategoryApplicationOnNewEntity(obj);
		}
	}

    public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
        super.updateObjectState(obj, context);

        DocumentCreator creator = (DocumentCreator)context.getCreator();
        Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
        String name = (String)getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
        creator.setName(name);
        Identifiable pojoParent = (Identifiable)getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
        if (pojoParent != null) {
            BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
            creator.setParent(parentHandle);
        }
        creator.setConflictResolutionMode(ConflictResolutionMode.OVERWRITE);
        creator.setIgnorePendingConflicts(true);

        updateNewOrOldObjectState(obj, context);

        if (useCategoryApplicationListUpdater) {
            if (isChanged(obj, EntityInfo.Attributes.categoryApplications.name())) {
                DocumentUpdater documentUpdater = (DocumentUpdater)context.getUpdater();
                AttributeChangeSet changeSet = getAttributeChanges(obj, null, EntityInfo.Attributes.categoryApplications.name());
                for (Persistent pojoCatApp : changeSet.addedPojoObjects) {
                    ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy)pojoCatApp.getManagedObjectProxy();
                    CategoryApplicationListUpdater listUpdater = documentUpdater.getCategoryApplicationsUpdater();
                    Persistent pojoCatAppTemplate = CategoryApplicationDAO.getInstance().getCategoryApplicationTemplate(pojoCatApp);
                    if (pojoCatAppTemplate != null) {
                        BeeId categoryTemplateHandle = getBeeId(((ManagedIdentifiableProxy)pojoCatAppTemplate.getManagedObjectProxy()).getObjectId().toString());
                        CategoryApplicationUpdateParameter param = new CategoryApplicationUpdateParameter();
                        param.setCategoryTemplateHandle(categoryTemplateHandle);
                        CategoryApplicationUpdater updater = new CategoryApplicationUpdater();
                        DAOContext subContext = new DAOContext(updater);
                        CategoryApplicationDAO.getInstance().updateNewObjectState(catAppObj, subContext);
                        param.setCategoryApplicationUpdater(updater);
                        listUpdater.getAdds().add(param);
                    }
                }
            }
        }
    }
	
	protected String getCreateObjectParameters(ManagedIdentifiableProxy obj, DAOContext context) {
		String params = null;
		if (isChanged(obj, DocumentInfo.Attributes.content.name())) {
			String uploadScopeId = context.getUploadScopeId();
			if (uploadScopeId != null) {
				params = "uploadscope=scope" + uploadScopeId;
			}
		}
		return params;
	}
	
	public void committedObject(Persistent pojoIdentifiable) {
		Persistent dependent;
		dependent = (Persistent) getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
		if (dependent != null) {
			SimpleContentDAO.getInstance().committedObject(dependent);
		}
	}
	
	public void rolledbackObject(Persistent pojoIdentifiable) {
		Persistent dependent;
		dependent = (Persistent) getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
		if (dependent != null) {
			SimpleContentDAO.getInstance().rolledbackObject(dependent);
		}
	}
	
	public Persistent getContent(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
	}
	
	// checks in a privateWorkingCopy and returns the version node
	public Persistent checkinGetPojoVersionNode(ManagedIdentifiableProxy privateWorkingCopyOfVersionableObj, String versionName) {
		PersistenceContext persistenceContext = privateWorkingCopyOfVersionableObj.getPersistenceContext();
		Persistent pojoPrivateWorkingCopyOfVersionable = privateWorkingCopyOfVersionableObj.getPojoIdentifiable();
		VersionUpdater versionUpdater = new VersionUpdater();
		ManagedIdentifiableProxy representativeCopyOfVersionableObj = null;
		Persistent pojoVersion = (Persistent) getAttributeValue(pojoPrivateWorkingCopyOfVersionable, DocumentInfo.Attributes.versionControlMetadata.name());
		if (pojoVersion != null) {
			if (versionName == null) {
				versionName = (String) getAttributeValue(pojoVersion, EntityInfo.Attributes.name.name());
			}
	        DAOContext context = new DAOContext(versionUpdater);
	        ManagedIdentifiableProxy versionObj = (ManagedIdentifiableProxy) pojoVersion.getManagedObjectProxy();
	        VersionDAO.getInstance().updateObjectState(versionObj, context);    
			Persistent pojoRepresentativeCopyOfVersionable = (Persistent) getAttributeValue(pojoVersion, VersionInfo.Attributes.representativeCopy.name());
			if (pojoRepresentativeCopyOfVersionable != null) {
				representativeCopyOfVersionableObj = (ManagedIdentifiableProxy) pojoRepresentativeCopyOfVersionable.getManagedObjectProxy();
			}
		}
		if (pojoVersion == null  || representativeCopyOfVersionableObj == null) {
			try {
				BeeId privateWorkingArtifactHandle = getBeeId(privateWorkingCopyOfVersionableObj.getObjectId().toString());
				Document bdkPrivateWorkingArtifact = (Document) loadObjectState(persistenceContext, privateWorkingArtifactHandle.toString(), Projection.FULL);
				Object bdkFamilyArtifact = bdkPrivateWorkingArtifact.getParentFamily();
				if (bdkFamilyArtifact != null) {
					representativeCopyOfVersionableObj = getEntityProxy(persistenceContext, bdkFamilyArtifact);
				} else {
					representativeCopyOfVersionableObj = privateWorkingCopyOfVersionableObj;
				}
	
				if (versionName == null) {
    				Version bdkVersion = bdkPrivateWorkingArtifact.getParentVersion();
    				if (bdkVersion != null) {
    					BeeId bdkVersionHandle = bdkVersion.getCollabId();
    					bdkVersion = (Version) loadObjectState(persistenceContext, bdkVersionHandle.toString(), Projection.BASIC);
    					versionName = bdkVersion.getName();
    				}
				}
			} catch (Exception ex) {
				throw new PersistenceException(ex);
			}
		} 
		pojoVersion = VersionSeriesToDocumentDAO.getInstance().checkinGetPojoVersionNode(representativeCopyOfVersionableObj, versionName, versionUpdater);
		return pojoVersion;
	}
	
	public void cancelCheckout(ManagedIdentifiableProxy representativeCopyOfVersionableObj) {
		VersionSeriesToDocumentDAO.getInstance().cancelCheckout(representativeCopyOfVersionableObj);
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Document.class;
	}
	
	protected DocumentUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new DocumentUpdater();
	}
	
	protected DocumentUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		DocumentUpdater updater = getBdkUpdater(obj);
		((DocumentCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected DocumentCreator getBdkCreator(ManagedObjectProxy obj) {
		return new DocumentCreator();
	}

}
