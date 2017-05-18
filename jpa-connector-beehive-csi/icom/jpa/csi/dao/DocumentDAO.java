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
import icom.info.ArtifactInfo;
import icom.info.BeanHandler;
import icom.info.DocumentInfo;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.VersionInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.PersistenceException;

import oracle.csi.CategoryTemplateHandle;
import oracle.csi.CollabId;
import oracle.csi.Content;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Document;
import oracle.csi.DocumentHandle;
import oracle.csi.DocumentOperationContext;
import oracle.csi.Entity;
import oracle.csi.EntityHandle;
import oracle.csi.HeterogeneousFolderHandle;
import oracle.csi.Identifiable;
import oracle.csi.IdentifiableHandle;
import oracle.csi.IdentifiableSimpleContent;
import oracle.csi.SnapshotId;
import oracle.csi.Version;
import oracle.csi.VersionHandle;
import oracle.csi.Versionable;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.DocumentControl;
import oracle.csi.controls.DocumentFactory;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.VersionControl;
import oracle.csi.controls.VersionFactory;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.CategoryApplicationListUpdater;
import oracle.csi.updaters.CategoryApplicationUpdater;
import oracle.csi.updaters.ContentUpdater;
import oracle.csi.updaters.DocumentUpdater;
import oracle.csi.updaters.IdentifiableSimpleContentUpdater;
import oracle.csi.updaters.VersionUpdater;
import oracle.csi.util.ConflictResolutionMode;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

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
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return DocumentHandle.class;
	}
	
	public boolean embedAsNonIdentifiableDependent() {
		return true;
	}

	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Document csiDocument = null;
		try {
			DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			DocumentHandle csiDocumentHandle = (DocumentHandle) EntityUtils.getInstance().createHandle(id);
			csiDocument = control.loadDocument(csiDocumentHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiDocument;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Document csiDocument = (Document) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(ArtifactInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(), csiDocument.getDescription());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(DocumentInfo.Attributes.size.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.size.name(), csiDocument.getTotalSize());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(DocumentInfo.Attributes.versionType.name(), lastLoadedProjection, proj)) {
			try {
				boolean isVersion = csiDocument.isVersion();
				boolean isRepresentativeCopy = csiDocument.isFamily();
				boolean isPrivateWorkingCopy = csiDocument.isWorkingCopy();
				boolean isVersionedCopy = isVersion && ! isPrivateWorkingCopy;
				boolean isVersionControlled = csiDocument.isVersionControlled();
				
				if (isRepresentativeCopy && isVersionControlled) {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.RepresentativeCopy.name());
				} else if (isPrivateWorkingCopy) {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.PrivateWorkingCopy.name());
				} else if (isVersionedCopy) {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.VersionedCopy.name());
				} else {
					assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.VersionTypeEnum.name(), VersionType.NonVersionControlledCopy.name());
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(DocumentInfo.Attributes.versionControlMetadata.name(), lastLoadedProjection, proj)) {
			try {
				boolean isFamily = csiDocument.isFamily();
				if (isFamily) {
					Versionable csiParentFamilyDocument = csiDocument;
					ManagedIdentifiableProxy versionSeriesObj = getIdentifiableDependentProxyOfVersionSeries(context, csiParentFamilyDocument, obj, DocumentInfo.Attributes.versionControlMetadata.name());
					versionSeriesObj.getProviderProxy().copyLoadedProjection(versionSeriesObj, csiParentFamilyDocument, proj);
					Persistent pojoVersionSeries = versionSeriesObj.getPojoIdentifiable();
					Persistent pojoRepresentativeVersionable = (Persistent) pojoIdentifiable;
					assignAttributeValue(pojoRepresentativeVersionable, DocumentInfo.Attributes.versionControlMetadata.name(), pojoVersionSeries);
					VersionSeriesToDocumentDAO.getInstance().copyRepresentativeCopy(pojoVersionSeries, pojoRepresentativeVersionable);
				} else {
					Version csiVersion = csiDocument.getParentVersion();
					if (csiVersion != null) {
						ManagedIdentifiableProxy versionObj = getEntityProxy(context, csiVersion);
						versionObj.getProviderProxy().copyLoadedProjection(versionObj, csiVersion, Projection.EMPTY);
						Persistent pojoVersion = versionObj.getPojoIdentifiable();
						assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.versionControlMetadata.name(), pojoVersion);
						Versionable csiParentFamilyDocument = csiDocument.getParentFamily();
						ManagedIdentifiableProxy representativeCopyObj = getEntityProxy(context, csiParentFamilyDocument);
						Projection familyProjection = csiParentFamilyDocument.getProjection();
						if (familyProjection != Projection.EMPTY) {
							representativeCopyObj.getProviderProxy().copyLoadedProjection(representativeCopyObj, csiParentFamilyDocument, familyProjection);
						}
						Persistent pojoRepresentativeCopy = representativeCopyObj.getPojoIdentifiable();
						Persistent pojoVersionedCopy = (Persistent) pojoIdentifiable;
						VersionDAO.getInstance().copyRepresentativeAndVersionedCopies(pojoVersion, pojoRepresentativeCopy, pojoVersionedCopy);
					}
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}

		if (/* always copy content  !isPartOfProjection(DocumentInfo.Attributes.content.name(), lastLoadedProjection) && */
				isPartOfProjection(DocumentInfo.Attributes.content.name(),  proj)) {
			try {
				Content csiChildContent  = csiDocument.getContent();
				if (csiChildContent != null) {
					boolean createDependentProxy = true;
					Object pojoContent = getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
					if (pojoContent != null) {
						ManagedIdentifiableProxy contentObj = (ManagedIdentifiableProxy) getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
						if (contentObj != null) {
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
							createDependentProxy = false;
						}
					}
					if (createDependentProxy) {
						ManagedObjectProxy contentObj = getIdentifiableDependentProxy(context, csiChildContent, obj, DocumentInfo.Attributes.content.name());
						// TODO how to use identifiable dependent proxy when contents of document versions share the same id
						// context.getNonIdentifiableDependentProxy((IdentifiableSimpleContent) csiChildContent, obj, DocumentInfo.Attributes.content.name());
						if (csiChildContent instanceof IdentifiableSimpleContent) {
							contentObj.getProviderProxy().copyLoadedProjection(contentObj, (Identifiable) csiChildContent, proj);
						}
						assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name(), contentObj.getPojoObject());
					}
				}
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	ManagedIdentifiableProxy getIdentifiableDependentProxyOfVersionSeries(PersistenceContext context, Versionable csiParentFamilyDocument,
			ManagedObjectProxy parent, String parentAttributeName) {
		DataAccessStateObject dataAccessStateObject = VersionSeriesToDocumentDAO.getInstance().morphDataAccessStateObjectFromDocumentToVersionSeries(csiParentFamilyDocument);
		return context.getIdentifiableDependentProxy(IcomBeanEnumeration.VersionSeries.name(), dataAccessStateObject, parent, parentAttributeName);
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		DocumentUpdater documentUpdater = (DocumentUpdater) context.getUpdater();
		Persistent pojoDocument = obj.getPojoIdentifiable();
		if (isChanged(obj, ArtifactInfo.Attributes.description.name())) {
			String description = (String) getAttributeValue(pojoDocument, ArtifactInfo.Attributes.description.name());
			documentUpdater.setDescription(description);
		}
		ContentUpdater contentUpdater = (ContentUpdater) context.getChildUpdater();
		if (contentUpdater instanceof IdentifiableSimpleContentUpdater) {
			Persistent pojoContent = getContent(pojoDocument);
			if (pojoContent != null) {
				DAOContext childContext = new DAOContext(contentUpdater);
				childContext.setOperationContext(context.getOperationContext());
				SimpleContentDAO.getInstance().updateObjectState(pojoContent.getManagedObjectProxy(), childContext);
			}
		}
	}
	
	/*
	public void updateNewOrOldEmailAttachmentObjectState(ManagedObjectProxy obj, DAOContext context) {
		Persistent pojoDocument = obj.getPojoObject();
		StreamedSimpleContentUpdater contentUpdater = (StreamedSimpleContentUpdater) context.getUpdater();
		Persistent pojoContent = getContent(pojoDocument);
		if (pojoContent != null) {
			if (isChanged(obj, EntityInfo.Attributes.name.name())) {
				String name = (String) getAttributeValue(pojoDocument, EntityInfo.Attributes.name.name());
				if (name != null) {
					RawString rawSubject = new RawString(name);
					contentUpdater.setName(rawSubject);
				} else {
					contentUpdater.setName(new RawString(""));
				}
			}
			DAOContext childContext = new DAOContext(contentUpdater);
			SimpleContentDAO.getInstance().updateObjectState(pojoContent.getManagedObjectProxy(), childContext);
		}
	}
	*/

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
			DocumentUpdater documentUpdater = (DocumentUpdater) context.getUpdater();
			if (isChanged(obj, EntityInfo.Attributes.categoryApplications.name())) {
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(EntityInfo.Attributes.categoryApplications.name());
				if (addedObjects != null) {
					Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
					while (addedObjectsIter.hasNext()) {
						ValueHolder holder = addedObjectsIter.next();
						Persistent pojoCatApp = (Persistent) holder.getValue();
						ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy) pojoCatApp.getManagedObjectProxy();
						CategoryApplicationListUpdater listUpdater = documentUpdater.getCategoryApplicationsUpdater();
						Persistent pojoCatAppTemplate = CategoryApplicationDAO.getInstance().getCategoryApplicationTemplate(pojoCatApp);
						if (pojoCatAppTemplate != null) {
							CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoCatAppTemplate.getManagedObjectProxy()).getObjectId());
							CategoryTemplateHandle categoryTemplateHandle = (CategoryTemplateHandle) EntityUtils.getInstance().createHandle(id);
							CategoryApplicationUpdater updater = listUpdater.addCategoryApplication(categoryTemplateHandle);
							DAOContext subContext = new DAOContext(updater);
							CategoryApplicationDAO.getInstance().updateNewObjectState(catAppObj, subContext);
						}
					}
				}
				Collection<ValueHolder> removedObjects = obj.getRemovedObjects(EntityInfo.Attributes.categoryApplications.name());
				if (removedObjects != null) {
					Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
					while (removedObjectsIter.hasNext()) {
						ValueHolder holder = removedObjectsIter.next();
						Persistent catApp = (Persistent) holder.getValue();
						ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy) catApp.getManagedObjectProxy();
						CategoryApplicationDAO.getInstance().delete(catAppObj);
					}
				}
			}
			
			Collection<icom.jpa.Identifiable> catApps = getCategoryApplications(obj.getPojoIdentifiable());
			if (catApps != null) {
				Iterator<icom.jpa.Identifiable> catAppsIter = catApps.iterator();
				while (catAppsIter.hasNext()) {
					Persistent pojoCatApp = catAppsIter.next();
					ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy) pojoCatApp.getManagedObjectProxy();
					if (catAppObj.isDirty()) {
						CategoryApplicationListUpdater listUpdater = documentUpdater.getCategoryApplicationsUpdater();
						Persistent pojoCatAppTemplate = CategoryApplicationDAO.getInstance().getCategoryApplicationTemplate(pojoCatApp);
						if (pojoCatAppTemplate != null) {
							CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoCatAppTemplate.getManagedObjectProxy()).getObjectId());
							CategoryTemplateHandle categoryTemplateHandle = (CategoryTemplateHandle) EntityUtils.getInstance().createHandle(id);
							CategoryApplicationUpdater updater = listUpdater.addCategoryApplication(categoryTemplateHandle);
							DAOContext subContext = new DAOContext(updater);
							CategoryApplicationDAO.getInstance().updateObjectState(catAppObj, subContext);
						}
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
		updateNewOrOldObjectState(obj, context);
		if (useCategoryApplicationListUpdater) {
			DocumentUpdater documentUpdater = (DocumentUpdater) context.getUpdater();
			if (isChanged(obj, EntityInfo.Attributes.categoryApplications.name())) {
				Collection<ValueHolder> addedObjects = obj.getAddedObjects(EntityInfo.Attributes.categoryApplications.name());
				if (addedObjects != null) {
					Iterator<ValueHolder> addedObjectsIter = addedObjects.iterator();
					while (addedObjectsIter.hasNext()) {
						ValueHolder holder = addedObjectsIter.next();
						Persistent pojoCatApp = (Persistent) holder.getValue();
						ManagedIdentifiableProxy catAppObj = (ManagedIdentifiableProxy) pojoCatApp.getManagedObjectProxy();
						CategoryApplicationListUpdater listUpdater = documentUpdater.getCategoryApplicationsUpdater();
						Persistent pojoCatAppTemplate = CategoryApplicationDAO.getInstance().getCategoryApplicationTemplate(pojoCatApp);
						if (pojoCatAppTemplate != null) {
							CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoCatAppTemplate.getManagedObjectProxy()).getObjectId());
							CategoryTemplateHandle categoryTemplateHandle = (CategoryTemplateHandle) EntityUtils.getInstance().createHandle(id);
							CategoryApplicationUpdater updater = listUpdater.addCategoryApplication(categoryTemplateHandle);
							DAOContext subContext = new DAOContext(updater);
							CategoryApplicationDAO.getInstance().updateNewObjectState(catAppObj, subContext);
						}
					}
				}
			}
		}
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		Persistent pojoDocument = obj.getPojoIdentifiable();
		DocumentUpdater documentUpdater = DocumentFactory.getInstance().createDocumentUpdater();
		DAOContext context = new DAOContext(documentUpdater);
		ContentUpdater contentUpdater = documentUpdater.getContentUpdater();
		context.setChildUpdater(contentUpdater);
		try {
			DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
			CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoDocument.getManagedObjectProxy()).getObjectId());
			DocumentHandle documentHandle = (DocumentHandle) EntityUtils.getInstance().createHandle(id);
			DocumentOperationContext docContext = control.beginUpdateDocument(documentHandle, documentUpdater, UpdateMode.alwaysUpdate());
			context.setOperationContext(docContext);
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return context;
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		DocumentUpdater documentUpdater = (DocumentUpdater) context.getUpdater();
		CollabId id = getCollabId(obj.getObjectId());
		DocumentHandle csiDocumentHandle = (DocumentHandle) EntityUtils.getInstance().createHandle(id);
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
			Document doc = control.updateDocument(csiDocumentHandle, documentUpdater, updateMode, Projection.BASIC);
			assignChangeToken(pojoIdentifiable, doc.getSnapshotId().toString());
			return doc;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		Persistent pojoDocument = obj.getPojoIdentifiable();
		DocumentUpdater documentUpdater = DocumentFactory.getInstance().createDocumentUpdater();
		DAOContext context = new DAOContext(documentUpdater);
		ContentUpdater contentUpdater = documentUpdater.getContentUpdater();
		context.setChildUpdater(contentUpdater);
		CollabId parentId = getCollabId(((ManagedIdentifiableProxy)(getParent(pojoDocument).getManagedObjectProxy())).getObjectId());
		EntityHandle entityHandle = (EntityHandle) EntityUtils.getInstance().createHandle(parentId);
		try {
			if (entityHandle instanceof HeterogeneousFolderHandle) {
				DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
				HeterogeneousFolderHandle heterogeneousFolderHandle = (HeterogeneousFolderHandle) entityHandle;
				CollabId id = getCollabId(obj.getObjectId());
				DocumentOperationContext docContext = control.beginCreateDocument(id.getEid(),
						heterogeneousFolderHandle, getName(pojoDocument),
						ConflictResolutionMode.CREATE_UNIQUE, true,
						documentUpdater);
				context.setOperationContext(docContext);
			} else {
				throw new PersistenceException("Document can be created only in heterogeneous folder");
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return context;
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		DocumentOperationContext docOpContext = (DocumentOperationContext) context.getOperationContext();
		icom.jpa.Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		try {
			Document csiDocument = control.createDocument(docOpContext, false, null, null, Document.BASIC_WITH_CONTENT);
			assignChangeToken(pojoIdentifiable, csiDocument.getSnapshotId().toString());
			IdentifiableSimpleContent csiContent = (IdentifiableSimpleContent) csiDocument.getContent();
			CollabId contentCollabId = csiContent.getCollabId();
			SnapshotId contentSnapshotId = csiDocument.getContentSnapshotId();
			String docName = csiDocument.getName();
			assignAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name(), docName);
			icom.jpa.Identifiable pojoContent = (icom.jpa.Identifiable) getContent(pojoIdentifiable);
			CsiIdentifiableDAO.assignObjectId(pojoContent, contentCollabId.toString());
			CsiIdentifiableDAO.assignChangeToken(pojoContent, contentSnapshotId.toString());
			PersistenceContext persistentContext = obj.getPersistenceContext();
			persistentContext.recacheIdentifiableDependent((ManagedIdentifiableProxy) pojoContent.getManagedObjectProxy());
			return null;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		DocumentHandle csiDocumentHandle = (DocumentHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteDocument(csiDocumentHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
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
	public Persistent checkin(ManagedIdentifiableProxy privateWorkingCopyOfVersionableObj, String versionName) {
		PersistenceContext persistenceContext = privateWorkingCopyOfVersionableObj.getPersistenceContext();
		VersionUpdater versionUpdater = VersionFactory.getInstance().createVersionUpdater();
		Persistent pojoPrivateWorkingCopyOfVersionable = privateWorkingCopyOfVersionableObj.getPojoIdentifiable();
		ManagedIdentifiableProxy representativeCopyOfVersionableObj = null;
		Persistent pojoVersion = (Persistent) getAttributeValue(pojoPrivateWorkingCopyOfVersionable, DocumentInfo.Attributes.versionControlMetadata.name());
		if (pojoVersion != null) {
	        DAOContext context = new DAOContext(versionUpdater);
	        VersionDAO.getInstance().updateObjectState((ManagedIdentifiableProxy) pojoVersion.getManagedObjectProxy(), context);    
			if (versionName == null) {
				versionName = (String) getAttributeValue(pojoVersion, EntityInfo.Attributes.name.name());
			}
			Persistent pojoRepresentativeCopyOfVersionable = (Persistent) getAttributeValue(pojoVersion, VersionInfo.Attributes.representativeCopy.name());
			if (pojoRepresentativeCopyOfVersionable != null) {
				representativeCopyOfVersionableObj = (ManagedIdentifiableProxy) pojoRepresentativeCopyOfVersionable.getManagedObjectProxy();
			}
		}
		if (pojoVersion == null  || representativeCopyOfVersionableObj == null) {
			try {
				DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
				CollabId id = getCollabId(privateWorkingCopyOfVersionableObj.getObjectId());
				DocumentHandle privateWorkingArtifactHandle = (DocumentHandle) EntityUtils.getInstance().createHandle(id);
				Document csiPrivateWorkingArtifact = control.loadDocument(privateWorkingArtifactHandle, Projection.FULL);

				Versionable csiFamilyArtifact = csiPrivateWorkingArtifact.getParentFamily();
				if (csiFamilyArtifact != null) {
					representativeCopyOfVersionableObj = getEntityProxy(persistenceContext, csiFamilyArtifact);
				} else {
					representativeCopyOfVersionableObj = privateWorkingCopyOfVersionableObj;
				}
	
				if (versionName == null) {
    				Version csiVersion = csiPrivateWorkingArtifact.getParentVersion();
    				if (csiVersion != null) {
    					VersionHandle csiVersionHandle = csiVersion.getHandle();
    					VersionControl versionControl = ControlLocator.getInstance().getControl(VersionControl.class);
    					csiVersion = versionControl.loadVersion(csiVersionHandle, Projection.BASIC);
    					versionName = csiVersion.getName();
    				}
				}
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
		} 
		pojoVersion = VersionSeriesToDocumentDAO.getInstance().checkin(representativeCopyOfVersionableObj, versionName, versionUpdater);
		return pojoVersion;
	}
	
	public void cancelCheckout(ManagedIdentifiableProxy representativeCopyOfVersionableObj) {
		VersionSeriesToDocumentDAO.getInstance().cancelCheckout(representativeCopyOfVersionableObj);
	}

}
