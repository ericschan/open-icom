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

import icom.info.DocumentInfo;
import icom.info.RelationshipBondableInfo;
import icom.info.VersionSeriesInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiDataAccessIdentifiableStateObject;
import icom.jpa.rt.PersistenceContext;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.persistence.PersistenceException;

import oracle.csi.Actor;
import oracle.csi.CollabId;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Document;
import oracle.csi.DocumentHandle;
import oracle.csi.Entity;
import oracle.csi.EntityNotCheckedOutException;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Version;
import oracle.csi.Versionable;
import oracle.csi.VersionableHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.DocumentControl;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.VersionUpdater;
import oracle.csi.util.DeleteMode;

public class VersionSeriesToDocumentDAO extends EntityDAO implements RelationshipBondableInfo {

	static VersionSeriesToDocumentDAO singleton = new VersionSeriesToDocumentDAO();
	
	public static VersionSeriesToDocumentDAO getInstance() {
		return singleton;
	}
	
	String versionSeriesTypeMnemonic = "VSVS";
	
	{
		basicAttributes.add(VersionSeriesInfo.Attributes.totalSize);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckedOut);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckedOutBy);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckedOutOn);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckoutComment);
	}

	{
		fullAttributes.add(VersionSeriesInfo.Attributes.versionHistory);
		fullAttributes.add(VersionSeriesInfo.Attributes.versionableHistory);
		fullAttributes.add(VersionSeriesInfo.Attributes.representativeCopy);
		fullAttributes.add(VersionSeriesInfo.Attributes.latestVersionedCopy);
		fullAttributes.add(VersionSeriesInfo.Attributes.privateWorkingCopy);
		fullAttributes.add(VersionSeriesInfo.Attributes.latestVersion);
	}
	
	protected VersionSeriesToDocumentDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return DocumentHandle.class;
	}
	
	protected CsiDataAccessIdentifiableStateObject morphDataAccessStateObjectFromDocumentToVersionSeries(Object state) {
		return new CsiDataAccessIdentifiableStateObject((oracle.csi.Identifiable) state, versionSeriesTypeMnemonic);
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		return null;
	}
	
	void copyRepresentativeCopy(Persistent pojoVersionSeries, Persistent pojoRepresentativeCopy) {
		assignAttributeValue(pojoVersionSeries, VersionSeriesInfo.Attributes.representativeCopy.name(), pojoRepresentativeCopy);
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		//super.copyObjectState(obj, csiEntity, proj);
		
		Document csiDocument = (Document) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckedOut.name(), proj)) {
			try {
				Boolean versionSeriesCheckedOut = csiDocument.isCheckedOut();
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.versionSeriesCheckedOut.name(), versionSeriesCheckedOut);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckedOutBy.name(), proj)) {
			try {
				Actor csiVersionSeriesCheckedOutBy = csiDocument.getCheckedOutBy();
				if (csiVersionSeriesCheckedOutBy != null) {
					ManagedIdentifiableProxy actorObj = getEntityProxy(context, csiVersionSeriesCheckedOutBy);
					Persistent pojoVersionSeriesCheckedOutBy = actorObj.getPojoIdentifiable();
					assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.versionSeriesCheckedOutBy.name(), pojoVersionSeriesCheckedOutBy);
				}		
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckedOutOn.name(), proj)) {
			try {
				Date versionSeriesCheckedOutOn = csiDocument.getCheckedOutOn();
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.versionSeriesCheckedOutOn.name(), versionSeriesCheckedOutOn);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckoutComment.name(), proj)) {
			try {
				String versionSeriesCheckoutComment = csiDocument.getCheckoutComments();
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.versionSeriesCheckoutComment.name(), versionSeriesCheckoutComment);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.totalSize.name(), proj)) {
			try {
				Long totalSize = csiDocument.getTotalSize();
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.totalSize.name(), totalSize);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.versionHistory.name(), proj)) {
			try {
				Collection<Version> csiVersionHistory = csiDocument.getVersionHistory();
				Vector<Persistent> v = new Vector<Persistent>(csiVersionHistory.size());
				if (csiVersionHistory != null) {
					for (Version csiVersion : csiVersionHistory) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiVersion);
						v.add(childObj.getPojoIdentifiable());
					}
				}
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.versionHistory.name(), v);
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.latestVersion.name(), v.lastElement());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		Versionable csiLatestVersionableInHistory = null;
		if (isPartOfProjection(VersionSeriesInfo.Attributes.versionableHistory.name(), proj)) {
			try {
				Collection<Versionable> csiVersionableHistory = csiDocument.getVersionableHistory();
				Vector<Persistent> pojoVersionableHistory = new Vector<Persistent>(csiVersionableHistory.size());
				if (csiVersionableHistory != null) {
					for (Versionable csiVersionable : csiVersionableHistory) {
						ManagedIdentifiableProxy childObj = getEntityProxy(context, csiVersionable);
						pojoVersionableHistory.add(childObj.getPojoIdentifiable());
						csiLatestVersionableInHistory = csiVersionable;
					}
				}
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.versionableHistory.name(), pojoVersionableHistory);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.latestVersionedCopy.name(), proj)) {
			try {
				Persistent pojoLatestVersionedCopy = null;
				Versionable csiRepresentativeVersionable = csiDocument.getRepresentativeVersionable();
				if (csiRepresentativeVersionable != null) {
					if (csiLatestVersionableInHistory != null) {
						if (csiRepresentativeVersionable.getCollabId().equals(csiLatestVersionableInHistory.getCollabId())) {
							ManagedIdentifiableProxy childObj = getEntityProxy(context, csiRepresentativeVersionable);
							pojoLatestVersionedCopy = childObj.getPojoIdentifiable();
						} else {
							ManagedIdentifiableProxy childObj = getEntityProxy(context, csiLatestVersionableInHistory);
							pojoLatestVersionedCopy = childObj.getPojoIdentifiable();
						}
					}
				}
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.latestVersionedCopy.name(), pojoLatestVersionedCopy);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.privateWorkingCopy.name(), proj)) {
			try {
				Persistent pojoPrivateWorkingCopy = null;
				Versionable csiRepresentativeVersionable = csiDocument.getRepresentativeVersionable();
				if (csiRepresentativeVersionable != null) {
					if (csiLatestVersionableInHistory != null) {
						if (! csiRepresentativeVersionable.getCollabId().equals(csiLatestVersionableInHistory.getCollabId())) {
							ManagedIdentifiableProxy childObj = getEntityProxy(context, csiRepresentativeVersionable);
							pojoPrivateWorkingCopy = childObj.getPojoIdentifiable();
						}
					}
				}
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.privateWorkingCopy.name(), pojoPrivateWorkingCopy);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isPartOfProjection(VersionSeriesInfo.Attributes.representativeCopy.name(), proj)) {
			try {
				Persistent pojoRepresentativeCopy = null;
				Versionable csiFamilyVersionable = csiDocument.getParentFamily();
				if (csiFamilyVersionable != null) {
					ManagedIdentifiableProxy childObj = getEntityProxy(context, csiFamilyVersionable);
					pojoRepresentativeCopy = childObj.getPojoIdentifiable();
				}
				assignAttributeValue(pojoIdentifiable, VersionSeriesInfo.Attributes.representativeCopy.name(), pojoRepresentativeCopy);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		return null;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		return null;
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
		
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		return null;
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		
	}
	
	public void cancelCheckout(ManagedIdentifiableProxy representativeCopyOfVersionableObj) {
		CollabId familyArtifactId = getCollabId(representativeCopyOfVersionableObj.getObjectId());
		VersionableHandle csiFamilyArtifactHandle = (VersionableHandle) EntityUtils.getInstance().createHandle(familyArtifactId);
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		try {
			DeleteMode deleteMode = DeleteMode.alwaysDelete();
			Versionable csiFamilyArtifact = control.cancelCheckout(csiFamilyArtifactHandle, deleteMode, Projection.BASIC);
			representativeCopyOfVersionableObj.checkReadyAndSetPooled();
			if (csiFamilyArtifact.getProjection() != Projection.EMPTY) {
				representativeCopyOfVersionableObj.getProviderProxy().copyLoadedProjection(representativeCopyOfVersionableObj,
						csiFamilyArtifact, csiFamilyArtifact.getProjection());
			}
			Persistent pojoRepresentativeCopyOfVersionable = representativeCopyOfVersionableObj.getPojoIdentifiable();
			Persistent pojoVersionSeries = (Persistent) getAttributeValue(pojoRepresentativeCopyOfVersionable, DocumentInfo.Attributes.versionControlMetadata.name());
			if (pojoVersionSeries != null) {
				((ManagedIdentifiableProxy)pojoVersionSeries.getManagedObjectProxy()).checkReadyAndSetPooled();
			}
		} catch (EntityNotCheckedOutException ex) {
			throw new PersistenceException(ex);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	// returns a private working copy
	/*
	public Persistent checkoutVersionSeries(ManagedIdentifiableProxy versionSeriesObj, String checkoutComments) {
		Persistent pojoVersionSeries = versionSeriesObj.getPojoIdentifiable();
		Persistent pojoRepresentativeCopyOfVersionable = (Persistent) getAttributeValue(pojoVersionSeries, VersionSeriesInfo.Attributes.representativeCopy.name());
		CollabId familyArtifactId = getCollabId(((ManagedIdentifiableProxy)(pojoRepresentativeCopyOfVersionable.getManagedObjectProxy())).getObjectId());
		VersionableHandle csiFamilyArtifactHandle = (VersionableHandle) EntityUtils.getInstance().createHandle(familyArtifactId);
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		try {
			Versionable csiPrivateWorkingArtifact = control.checkout(csiFamilyArtifactHandle, checkoutComments);
			PersistenceContext context = versionSeriesObj.getPersistenceContext();
			ManagedIdentifiableProxy privateWorkingCopyOfVersionableObj = DocumentDAO.getInstance().getEntityProxy(context, csiPrivateWorkingArtifact);
			Persistent pojoPrivateWorkingCopyOfVersionable = privateWorkingCopyOfVersionableObj.getPojoIdentifiable();
			versionSeriesObj.setPooled();
			pojoRepresentativeCopyOfVersionable.getManagedObjectProxy().setPooled();
			return pojoPrivateWorkingCopyOfVersionable;
		} catch (EntityNotCheckedOutException ex) {
			throw new PersistenceException(ex);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	*/
	
	// returns version node of a private working copy
	public Persistent checkoutFamilyArtifact(ManagedIdentifiableProxy representativeCopyOfVersionableObj, String checkoutComments) {
		CollabId familyArtifactId = getCollabId(representativeCopyOfVersionableObj.getObjectId());
		VersionableHandle csiFamilyArtifactHandle = (VersionableHandle) EntityUtils.getInstance().createHandle(familyArtifactId);
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		try {
			Versionable csiPrivateWorkingArtifact = control.checkout(csiFamilyArtifactHandle, checkoutComments);
			PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
			ManagedIdentifiableProxy privateWorkingCopyOfVersionableObj = DocumentDAO.getInstance().getEntityProxy(context, csiPrivateWorkingArtifact);
			Persistent pojoPrivateWorkingCopyOfVersionable = privateWorkingCopyOfVersionableObj.getPojoIdentifiable();
			Persistent pojoRepresentativeCopyOfVersionable = representativeCopyOfVersionableObj.getPojoIdentifiable();
			Persistent pojoVersionSeries = (Persistent) getAttributeValue(pojoRepresentativeCopyOfVersionable, DocumentInfo.Attributes.versionControlMetadata.name());
			Persistent pojoVersion = (Persistent) getAttributeValue(pojoPrivateWorkingCopyOfVersionable, DocumentInfo.Attributes.versionControlMetadata.name());
			if (pojoVersionSeries != null) {
				((ManagedIdentifiableProxy)(pojoVersionSeries.getManagedObjectProxy())).checkReadyAndSetPooled();
			}
			representativeCopyOfVersionableObj.checkReadyAndSetPooled();
			return pojoVersion;
		} catch (EntityNotCheckedOutException ex) {
			throw new PersistenceException(ex);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	// this method is indirectly called via VersionDAO
	// it returns a versioned copy of the versionable artifact
	/*
	public Persistent checkin(ManagedIdentifiableProxy representativeCopyOfVersionableObj, String versionName, VersionUpdater versionUpdater) {
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		CollabId representativeCopyOfVersionableId = getCollabId(representativeCopyOfVersionableObj.getObjectId());
		VersionableHandle csiFamilyArtifactHandle = (VersionableHandle) EntityUtils.getInstance().createHandle(representativeCopyOfVersionableId);
		UpdateMode updateMode = UpdateMode.alwaysUpdate();
		try {
			Versionable csiVersionArtifact = control.checkin(csiFamilyArtifactHandle, versionName, versionUpdater, updateMode, Projection.EMPTY);
			PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
			ManagedIdentifiableProxy versionedCopyOfVersionableObj = DocumentDAO.getInstance().getEntityProxy(context, csiVersionArtifact);
			versionedCopyOfVersionableObj.setPooled();  // this should be a versioned copy; a private working copy morphs into it 
			Persistent pojoRepresentativeCopyOfVersionable = representativeCopyOfVersionableObj.getPojoIdentifiable();
			Persistent pojoVersionSeries = (Persistent) getAttributeValue(pojoRepresentativeCopyOfVersionable, DocumentInfo.Attributes.versionControlMetadata.name());
			if (pojoVersionSeries != null) {
				pojoVersionSeries.getManagedObjectProxy().setPooled();
			}
			representativeCopyOfVersionableObj.setPooled();
			return versionedCopyOfVersionableObj.getPojoIdentifiable();
		} catch (EntityNotCheckedOutException ex) {
			throw new PersistenceException(ex);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	*/
	
	// this method is indirectly called via DocumentDAO
	// it returns a version node
	public Persistent checkin(ManagedIdentifiableProxy representativeCopyOfVersionableObj, String versionName, VersionUpdater versionUpdater) {
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		CollabId representativeCopyOfVersionableId = getCollabId(representativeCopyOfVersionableObj.getObjectId());
		VersionableHandle csiFamilyArtifactHandle = (VersionableHandle) EntityUtils.getInstance().createHandle(representativeCopyOfVersionableId);
		try {
		    Projection proj = Projection.FULL;
			Version csiVersion = control.checkin(csiFamilyArtifactHandle, versionName, versionUpdater, proj);
			PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
			ManagedIdentifiableProxy versionObj = VersionDAO.getInstance().getEntityProxy(context, csiVersion);
			Persistent pojoRepresentativeCopyOfVersionable = representativeCopyOfVersionableObj.getPojoIdentifiable();
			Persistent pojoVersionSeries = (Persistent) getAttributeValue(pojoRepresentativeCopyOfVersionable, DocumentInfo.Attributes.versionControlMetadata.name());
			if (pojoVersionSeries != null) {
				((ManagedIdentifiableProxy)(pojoVersionSeries.getManagedObjectProxy())).checkReadyAndSetPooled();
			}
			representativeCopyOfVersionableObj.checkReadyAndSetPooled();
			return versionObj.getPojoIdentifiable();
		} catch (EntityNotCheckedOutException ex) {
			throw new PersistenceException(ex);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
}
