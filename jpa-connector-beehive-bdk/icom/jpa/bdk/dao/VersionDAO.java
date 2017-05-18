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

import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.Version;
import com.oracle.beehive.VersionUpdater;

import icom.info.RelationshipBondableInfo;
import icom.info.VersionInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;


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

	public String getResourceType() {
		return "aver";
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

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        Version bdkVersion = (Version)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoObject();
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(VersionInfo.Attributes.checkinComment.name(), lastLoadedProjection, proj)) {
            try {
                String checkInComment = bdkVersion.getDescription();
                assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.checkinComment.name(), checkInComment);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(VersionInfo.Attributes.versionNumber.name(), lastLoadedProjection, proj)) {
            try {
                Integer versionNumber = bdkVersion.getVersionNumber();
                assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.versionNumber.name(), versionNumber);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(VersionInfo.Attributes.versionLabel.name(), lastLoadedProjection, proj)) {
            try {
                String versionLabel = bdkVersion.getVersionLabel();
                assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.versionLabel.name(), versionLabel);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(VersionInfo.Attributes.majorVersion.name(), lastLoadedProjection, proj)) {
            try {
                Boolean majorVersion = bdkVersion.isDoNotAutoPurge();
                assignAttributeValue(pojoIdentifiable, VersionInfo.Attributes.majorVersion.name(), majorVersion);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(VersionInfo.Attributes.representativeCopy.name(), lastLoadedProjection, proj)) {
            try {
                Object bdkFamilyArtifact = bdkVersion.getParentArtifact();
                marshallAssignEntity(obj, VersionInfo.Attributes.representativeCopy.name(), bdkFamilyArtifact);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name(), lastLoadedProjection,
                                 proj)) {
            try {
                Object bdkVersionArtifact = bdkVersion.getVersionArtifact();
                marshallAssignEntity(obj, VersionInfo.Attributes.versionedOrPrivateWorkingCopy.name(),
                                     bdkVersionArtifact);
            } catch (Exception ex) {
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
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}

	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
		
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Version.class;
	}
	
	protected VersionUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new VersionUpdater();
	}
	
	protected VersionUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		return null;
	}
	
	protected EntityCreator getBdkCreator(ManagedObjectProxy obj) {
		return null;
	}
	
}
