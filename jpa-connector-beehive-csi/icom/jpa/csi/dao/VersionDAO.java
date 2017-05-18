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
import icom.info.RelationshipBondableInfo;
import icom.info.VersionInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Entity;
import oracle.csi.EntityNotCheckedOutException;
import oracle.csi.IdentifiableHandle;
import oracle.csi.SnapshotId;
import oracle.csi.Version;
import oracle.csi.VersionHandle;
import oracle.csi.Versionable;
import oracle.csi.VersionableHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.DocumentControl;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.VersionControl;
import oracle.csi.controls.VersionFactory;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.VersionUpdater;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.UpdateMode;

public class VersionDAO extends EntityDAO {

	static VersionDAO singleton = new VersionDAO();
	
	public static VersionDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(VersionInfo.Attributes.checkinComment);
		basicAttributes.add(VersionInfo.Attributes.versionNumber);
		basicAttributes.add(VersionInfo.Attributes.versionLabel);
		basicAttributes.add(VersionInfo.Attributes.majorVersion);
		basicAttributes.add(VersionInfo.Attributes.representativeCopy);
		basicAttributes.add(VersionInfo.Attributes.versionedOrPrivateWorkingCopy);
	}
	
	{	
		fullAttributes.add(RelationshipBondableInfo.Attributes.relationships);
	}
	
	protected VersionDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return VersionHandle.class;
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		Version csiVersion = null;
		try {
			VersionControl control = ControlLocator.getInstance().getControl(VersionControl.class);
			CollabId id = getCollabId(obj.getObjectId());
			VersionHandle csiVersionHandle = (VersionHandle) EntityUtils.getInstance().createHandle(id);
			csiVersion = control.loadVersion(csiVersionHandle, proj);			
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiVersion;
	}
	
	
	// the method copy the parameters from the document to facilitate the checkin operation without having to load the version node
	void copyRepresentativeAndVersionedCopies(Persistent pojoVersion, Persistent pojoRepresentativeCopy, Persistent pojoVersionedOrPrivateWorkingCopy) {
		Persistent pojoVersionedOrPrivateWorkingCopyOfVersionable = (Persistent) getAttributeValue(pojoVersion, VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name());
		if (pojoVersionedOrPrivateWorkingCopyOfVersionable == null) {
			assignAttributeValue(pojoVersion, VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name(), pojoVersionedOrPrivateWorkingCopy);
		} else {
			// assert that pojoVersionedOrPrivateWorkingCopyOfVersionable == pojoVersionedOrPrivateWorkingCopy
		}
		Persistent pojoRepresentativeCopyOfVersionable = (Persistent) getAttributeValue(pojoVersion, VersionInfo.Attributes.representativeCopy.name());
		if (pojoRepresentativeCopyOfVersionable == null) {
			assignAttributeValue(pojoVersion, VersionInfo.Attributes.representativeCopy.name(), pojoRepresentativeCopy);
		} else {
			// assert that pojoRepresentativeCopyOfVersionable == pojoRepresentativeCopy
		}
		
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		super.copyObjectState(obj, csiEntity, proj);
		
		Version csiVersion = (Version) csiEntity;
		Persistent pojoIdentifiable = obj.getPojoObject();
		PersistenceContext context = obj.getPersistenceContext();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections(VersionInfo.Attributes.checkinComment.name(), lastLoadedProjection, proj)) {
			try {
				String checkInComment = csiVersion.getDescription();
				assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.checkinComment.name(), checkInComment);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(VersionInfo.Attributes.versionNumber.name(), lastLoadedProjection, proj)) {
			try {
				Integer versionNumber = csiVersion.getVersionNumber();
				assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.versionNumber.name(), versionNumber);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(VersionInfo.Attributes.versionLabel.name(), lastLoadedProjection, proj)) {
			try {
				String versionLabel = csiVersion.getVersionLabel();
				assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.versionLabel.name(), versionLabel);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(VersionInfo.Attributes.majorVersion.name(), lastLoadedProjection, proj)) {
			try {
				Boolean majorVersion = csiVersion.isDoNotAutoPurge();
				assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.majorVersion.name(), majorVersion);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(VersionInfo.Attributes.representativeCopy.name(), lastLoadedProjection, proj)) {
			try {
				Versionable csiFamilyArtifact = csiVersion.getParentArtifact();
				ManagedIdentifiableProxy representativeCopyOfVersionableObj = getEntityProxy(context, csiFamilyArtifact);
				Persistent pojoRepresentativeCopyOfVersionable = representativeCopyOfVersionableObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.representativeCopy.name(), pojoRepresentativeCopyOfVersionable);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name(), lastLoadedProjection, proj)) {
			try {
				Versionable csiVersionArtifact = csiVersion.getVersionArtifact();
				ManagedIdentifiableProxy versionedOrPrivateWorkingCopyOfVersionableObj = getEntityProxy(context, csiVersionArtifact);
				Persistent pojoVersionedOrPrivateWorkingCopyOfVersionable = versionedOrPrivateWorkingCopyOfVersionableObj.getPojoIdentifiable();
				assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name(), pojoVersionedOrPrivateWorkingCopyOfVersionable);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoVersion = obj.getPojoObject();
		VersionUpdater updater = (VersionUpdater) context.getUpdater();
		if (isChanged(obj, VersionInfo.Attributes.checkinComment.name())) {
			String checkinComment = (String) getAttributeValue(pojoVersion, VersionInfo.Attributes.checkinComment.name());
			updater.setDescription(checkinComment);
		}
		
		if (isChanged(obj, VersionInfo.Attributes.versionLabel.name())) {
			String versionLabel = (String) getAttributeValue(pojoVersion, VersionInfo.Attributes.versionLabel.name());
			updater.setVersionLabel(versionLabel);
		}
		
		if (isChanged(obj, VersionInfo.Attributes.majorVersion.name())) {
			Boolean majorVersion = (Boolean) getAttributeValue(pojoVersion, VersionInfo.Attributes.majorVersion.name());
			if (majorVersion != null) {
				updater.setDoNotAutoPurge(majorVersion);
			} else {
				updater.setDoNotAutoPurge(false);
			}
		}
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		VersionUpdater versionUpdater = VersionFactory.getInstance().createVersionUpdater();
		DAOContext context = new DAOContext(versionUpdater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		VersionControl control = ControlLocator.getInstance().getControl(VersionControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		VersionHandle csiVersionHandle = (VersionHandle) EntityUtils.getInstance().createHandle(id);
		VersionUpdater versionUpdater = (VersionUpdater) context.getUpdater();
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
			Version version = control.updateVersion(csiVersionHandle, versionUpdater, updateMode, proj);
			assignChangeToken(pojoIdentifiable, version.getSnapshotId().toString());
			return version;
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
	
	// This will put the document under version control for the first time by checking in a new version
	// If the document is already under version control, the application must first check out 
	// a private working copy, update the PWC version info and call the check in operation (see checkin method in VersionSeriesToDocumentDAO)
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		DocumentControl control = ControlLocator.getInstance().getControl(DocumentControl.class);
		Persistent pojoVersion = obj.getPojoIdentifiable();
		Persistent pojoVersionable = (Persistent) getAttributeValue(pojoVersion, VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name());
		// assert that versionType of pojoVersionable is none
		VersionableHandle csiVersionableHandle = null;
		if (pojoVersionable != null) {
			CollabId versionableId = getCollabId(((ManagedIdentifiableProxy)(pojoVersionable.getManagedObjectProxy())).getObjectId());
			csiVersionableHandle = (VersionableHandle) EntityUtils.getInstance().createHandle(versionableId);
		}
		String versionName = (String) getAttributeValue(pojoVersion, EntityInfo.Attributes.name.name());
		VersionUpdater versionUpdater = (VersionUpdater) context.getUpdater();
		UpdateMode updateMode = UpdateMode.alwaysUpdate();
		Versionable entity = null;
		try {
			entity = control.checkin(csiVersionableHandle, versionName, versionUpdater, updateMode, proj);
		} catch (EntityNotCheckedOutException ex) {
			throw new PersistenceException(ex);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
		return (Entity) entity;
	}
	
	// checks in a version node and returns a versioned copy of versionable artifact
	/*
	public Persistent checkin(ManagedIdentifiableProxy versionObj) {
		Persistent pojoVersion = versionObj.getPojoIdentifiable();
		VersionUpdater versionUpdater = VersionFactory.getInstance().createVersionUpdater();
		DAOContext context = new DAOContext(versionUpdater);
		VersionDAO.getInstance().updateObjectState(versionObj, context);	
		String versionName = (String) getAttributeValue(pojoVersion, EntityInfo.Attributes.name.name());
		// Persistent pojoPrivateWorkingVersionable = (Persistent) getAttributeValue(pojoVersion, VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name());
		// assert that versionType of pojoPrivateWorkingVersionable is correctly showing PrivateWorkingCopy
		Persistent pojoRepresentativeCopyOfVersionable = (Persistent) getAttributeValue(pojoVersion, VersionInfo.Attributes.representativeCopy.name());
		ManagedIdentifiableProxy representativeCopyOfVersionableObj = null;
		if (pojoRepresentativeCopyOfVersionable != null) {
			representativeCopyOfVersionableObj = (ManagedIdentifiableProxy) pojoRepresentativeCopyOfVersionable.getManagedObjectProxy();
		} else {
			try {
				VersionControl control = ControlLocator.getInstance().getControl(VersionControl.class);
				CollabId id = getCollabId(versionObj.getObjectId());
				VersionHandle csiVersionHandle = (VersionHandle) EntityUtils.getInstance().createHandle(id);
				Version csiVersion = control.loadVersion(csiVersionHandle, Projection.FULL);
				Versionable csiFamilyArtifact = csiVersion.getParentArtifact();
				PersistenceContext persistenceContext = versionObj.getPersistenceContext();
				representativeCopyOfVersionableObj = getEntityProxy(persistenceContext, csiFamilyArtifact);
			} catch (CsiException ex) {
				throw new PersistenceException(ex);
			}
		}
		Persistent pojoVersionedcopyOfVersionable = VersionSeriesToDocumentDAO.getInstance().checkin(representativeCopyOfVersionableObj, versionName, versionUpdater);
		return pojoVersionedcopyOfVersionable;
	}
	*/

	public void delete(ManagedIdentifiableProxy obj) {
		VersionControl control = ControlLocator.getInstance().getControl(VersionControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		VersionHandle csiVersionHandle = (VersionHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteVersion(csiVersionHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
}
