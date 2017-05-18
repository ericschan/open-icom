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
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.HeterogeneousFolder;
import com.oracle.beehive.HeterogeneousFolderCreator;
import com.oracle.beehive.HeterogeneousFolderUpdater;
import com.oracle.beehive.ListResult;

import icom.info.EntityInfo;
import icom.info.HeterogeneousFolderInfo;
import icom.info.beehive.BeehiveExtentInfo;

import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.ValueHolder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


public class HeterogeneousFolderDAO extends FolderDAO {
	
	static HeterogeneousFolderDAO singleton = new HeterogeneousFolderDAO();
	
	public static HeterogeneousFolderDAO getInstance() {
		return singleton;
	}
	
	{
		fullAttributes.add(HeterogeneousFolderInfo.Attributes.elements);
	}
	
	{
		lazyAttributes.add(BeehiveExtentInfo.Attributes.versionControlConfiguration);
		lazyAttributes.add(BeehiveExtentInfo.Attributes.categoryConfiguration);
	}
	
	protected HeterogeneousFolderDAO() {
	}

	public String getResourceType() {
		return "afrh";
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        HeterogeneousFolder bdkFolder = (HeterogeneousFolder)bdkEntity;
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(HeterogeneousFolderInfo.Attributes.elements.name(), lastLoadedProjection, proj)) {
            try {
                ListResult list = bdkFolder.getElements();
                Collection<Object> bdkArtifacts = list.getElements();
                marshallMergeAssignEntities(obj, HeterogeneousFolderInfo.Attributes.elements.name(), bdkArtifacts,
                                          Vector.class);
            } catch (Exception ex) {
                // ignore
            }
        }
    }
	
	public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object parameter) {
		super.loadAndCopyObjectState(obj, attributeName, parameter);
		if (BeehiveExtentInfo.Attributes.versionControlConfiguration.name().equals(attributeName)) {
			VersionControlConfigurationDAO.getInstance().loadVersionControlConfiguration(obj, Projection.FULL);
		}
		if (BeehiveExtentInfo.Attributes.categoryConfiguration.name().equals(attributeName)) {
			CategoryConfigurationDAO.getInstance().loadCategoryConfiguration(obj, Projection.FULL);
		}
	}
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	private void updateMetadataOnNewAndOldEntity(ManagedIdentifiableProxy obj) {
		Persistent pojoScope = obj.getPojoIdentifiable();
		
		Persistent pojoVersionControlConfig = getVersionControlConfiguration(pojoScope);
		if (pojoVersionControlConfig != null) {
			VersionControlConfigurationDAO.getInstance().save((ManagedIdentifiableProxy)pojoVersionControlConfig.getManagedObjectProxy());
		} else {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveExtentInfo.Attributes.versionControlConfiguration.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent versionControlConfig = (Persistent) holder.getValue();
					ManagedIdentifiableProxy versionControlConfigObj = (ManagedIdentifiableProxy)versionControlConfig.getManagedObjectProxy();
					VersionControlConfigurationDAO.getInstance().delete(versionControlConfigObj);
				}
			}
		}
		
		Persistent pojoCatConfig = getCategoryConfiguration(pojoScope);
		if (pojoCatConfig != null) {
			CategoryConfigurationDAO.getInstance().save((ManagedIdentifiableProxy)pojoCatConfig.getManagedObjectProxy());
		} else {
			Collection<ValueHolder> removedObjects = obj.getRemovedObjects(BeehiveExtentInfo.Attributes.categoryConfiguration.name());
			if (removedObjects != null) {
				Iterator<ValueHolder> removedObjectsIter = removedObjects.iterator();
				while (removedObjectsIter.hasNext()) {
					ValueHolder holder = removedObjectsIter.next();
					Persistent catConfig = (Persistent) holder.getValue();
					ManagedIdentifiableProxy catConfigObj = (ManagedIdentifiableProxy)catConfig.getManagedObjectProxy();
					CategoryConfigurationDAO.getInstance().delete(catConfigObj);
				}
			}
		}
	}
	
	protected void updateMetadataOnEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnEntity(obj);
		updateMetadataOnNewAndOldEntity(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		HeterogeneousFolderCreator creator = (HeterogeneousFolderCreator) context.getCreator();
		Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setName(name);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setParent(parentHandle);
		}
		
		updateNewOrOldObjectState(obj, context);
	}
	
	protected void updateMetadataOnNewEntity(ManagedIdentifiableProxy obj) {
		super.updateMetadataOnNewEntity(obj);
		updateMetadataOnNewAndOldEntity(obj);
	}
	
	public Persistent getVersionControlConfiguration(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveExtentInfo.Attributes.versionControlConfiguration.name());
	}
	
	public Persistent getCategoryConfiguration(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveExtentInfo.Attributes.categoryConfiguration.name());
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return HeterogeneousFolder.class;
	}
	
	protected HeterogeneousFolderUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new HeterogeneousFolderUpdater();
	}
	
	protected HeterogeneousFolderUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		HeterogeneousFolderUpdater updater = getBdkUpdater(obj);
		((HeterogeneousFolderCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected HeterogeneousFolderCreator getBdkCreator(ManagedObjectProxy obj) {
		return new HeterogeneousFolderCreator();
	}
	
}
