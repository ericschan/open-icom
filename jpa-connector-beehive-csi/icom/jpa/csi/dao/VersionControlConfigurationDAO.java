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

import icom.info.beehive.BeehiveBeanEnumeration;
import icom.info.beehive.BeehiveExtentInfo;
import icom.info.beehive.BeehiveVersionControlConfigurationInfo;
import icom.jpa.Manageable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiIdentifiableDAO;
import icom.jpa.dao.MetaDataApplicationDAO;
import icom.jpa.rt.ManagedIdentifiableDependentProxyImpl;
import icom.jpa.rt.PersistenceContext;

import java.util.HashMap;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.Container;
import oracle.csi.ContainerHandle;
import oracle.csi.CsiException;
import oracle.csi.CsiRuntimeException;
import oracle.csi.HeterogeneousFolderHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.ScopeHandle;
import oracle.csi.VersionControlConfiguration;
import oracle.csi.VersionControlConfigurationHandle;
import oracle.csi.controls.ControlLocator;
import oracle.csi.controls.EntityUtils;
import oracle.csi.controls.VersionConfigurationControl;
import oracle.csi.controls.VersionConfigurationFactory;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.VersionControlConfigurationUpdater;
import oracle.csi.util.ConflictResolutionMode;
import oracle.csi.util.DeleteMode;
import oracle.csi.util.LabelFormat;
import oracle.csi.util.UpdateMode;
import oracle.csi.util.VersionControlModel;

public class VersionControlConfigurationDAO extends CsiIdentifiableDAO implements MetaDataApplicationDAO {

	static VersionControlConfigurationDAO singleton = new VersionControlConfigurationDAO();
	
	public static VersionControlConfigurationDAO getInstance() {
		return singleton;
	}
	
	static HashMap<String, String> csiToPojoVersionControlModeNameMap;
	static HashMap<String, String> pojoToCsiVersionControlModeNameMap;
	
	{
		csiToPojoVersionControlModeNameMap = new HashMap<String, String>();
		pojoToCsiVersionControlModeNameMap = new HashMap<String, String>();
		csiToPojoVersionControlModeNameMap.put(VersionControlModel.AUTO.name(), "Auto");
		csiToPojoVersionControlModeNameMap.put(VersionControlModel.MANUAL.name(), "Manual");
		csiToPojoVersionControlModeNameMap.put(VersionControlModel.DISABLED.name(), "Disabled");
		for (String key : csiToPojoVersionControlModeNameMap.keySet()) {
			pojoToCsiVersionControlModeNameMap.put(csiToPojoVersionControlModeNameMap.get(key), key);
		}
	}
	
	static HashMap<String, String> csiToPojoVersionControlLabelFormatMap;
	static HashMap<String, String> pojoToCsiVersionControlLabelFormatMap;
	
	{
		csiToPojoVersionControlLabelFormatMap = new HashMap<String, String>();
		pojoToCsiVersionControlLabelFormatMap = new HashMap<String, String>();
		csiToPojoVersionControlLabelFormatMap.put(LabelFormat.DECIMAL_FORMAT.name(), "DecimalFormat");
		csiToPojoVersionControlLabelFormatMap.put(LabelFormat.INTEGER_FORMAT.name(), "IntegerFormat");
		csiToPojoVersionControlLabelFormatMap.put(LabelFormat.LOWERCASE_FORMAT.name(), "LowercaseFormat");
		csiToPojoVersionControlLabelFormatMap.put(LabelFormat.UPPERCASE_FORMAT.name(), "UppercaseFormat");
		csiToPojoVersionControlLabelFormatMap.put(LabelFormat.ROMAN_NUMERAL_FORMAT.name(), "RomanNumeralFormat");
		for (String key : csiToPojoVersionControlLabelFormatMap.keySet()) {
			pojoToCsiVersionControlLabelFormatMap.put(csiToPojoVersionControlLabelFormatMap.get(key), key);
		}
	}
	
	protected VersionControlConfigurationDAO() {
		
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return VersionControlConfigurationHandle.class;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		VersionControlConfiguration csiVersionControlConfiguration = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, csiVersionControlConfiguration, proj);
		return proj;
	}
	
	public VersionControlConfiguration loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		VersionConfigurationControl control = ControlLocator.getInstance().getControl(VersionConfigurationControl.class);
		ManagedIdentifiableDependentProxyImpl depObj = (ManagedIdentifiableDependentProxyImpl) obj;
		ManagedIdentifiableProxy parentObj = (ManagedIdentifiableProxy) depObj.getParent();
		CollabId id = getCollabId(parentObj.getObjectId());
		ContainerHandle containerHandle = (ContainerHandle) EntityUtils.getInstance().createHandle(id);
		VersionControlConfiguration csiVersionControlConfiguration = null;
		try {
			if (containerHandle instanceof ScopeHandle) {
				csiVersionControlConfiguration = control.getVersionControlConfiguration((ScopeHandle) containerHandle, proj);
			} else {
				csiVersionControlConfiguration = control.getVersionControlConfiguration((HeterogeneousFolderHandle) containerHandle, proj);
			}
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
		return csiVersionControlConfiguration;
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiIdentifiable, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		VersionControlConfiguration csiVersionControlConfiguration = (VersionControlConfiguration) csiIdentifiable;
		
		try {
			PersistenceContext context = obj.getPersistenceContext();
			ContainerHandle containerHandle = (ContainerHandle) csiVersionControlConfiguration.getContainer().getHandle();
			CollabId id = containerHandle.getCollabId();
			Manageable pojoContainer = context.getReference(id);
			assignAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.extent.name(), pojoContainer);
		} catch (CsiRuntimeException ex) {
			// ignore
		}

		try {
			assignAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.autoLabel.name(),
					new Boolean(csiVersionControlConfiguration.isAutoLabel()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			assignAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.finale.name(),
					new Boolean(csiVersionControlConfiguration.isFinal()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			assignAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.maximumVersionsToKeep.name(),
					new Integer(csiVersionControlConfiguration.getMaximumVersionsToKeep()));
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			VersionControlModel csiVersionControlModel = csiVersionControlConfiguration.getVersionControlModel();
			if (csiVersionControlModel != null) {
				String pojoVersionControlModeName = csiToPojoVersionControlModeNameMap.get(csiVersionControlModel.name());
				String beehivePackageName = BeehiveBeanEnumeration.BeehiveVersionControlMode.getPackageName();
				assignEnumConstant(pojoIdentifiable, 
						BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name(),
						beehivePackageName, 
						BeehiveBeanEnumeration.BeehiveVersionControlMode.name(), 
						pojoVersionControlModeName);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		
		try {
			LabelFormat csiLabelFormat = csiVersionControlConfiguration.getLabelFormat();
			if (csiLabelFormat != null) {
				String pojoVersionLabelFormatName = csiToPojoVersionControlLabelFormatMap.get(csiLabelFormat.name());
				String beehivePackageName = BeehiveBeanEnumeration.BeehiveVersionControlMode.getPackageName();
				assignEnumConstant(pojoIdentifiable, 
						BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name(), 
						beehivePackageName,
						BeehiveBeanEnumeration.BeehiveVersionLabelFormat.name(), 
						pojoVersionLabelFormatName);
			}
		} catch (CsiRuntimeException ex) {
			// ignore
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
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		VersionControlConfigurationUpdater updater = (VersionControlConfigurationUpdater) context.getUpdater();
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.autoLabel.name())) {
			Boolean isAutoLabel = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.autoLabel.name());
			if (isAutoLabel != null) {
				updater.setAutoLabel(isAutoLabel);
			} else {
				updater.setAutoLabel(false);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.finale.name())) {
			Boolean isFinal = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.finale.name());
			if (isFinal != null) {
				updater.setFinal(isFinal);
			} else {
				updater.setFinal(false);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.maximumVersionsToKeep.name())) {
			Integer maximumVersionsToKeep = (Integer) getAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.maximumVersionsToKeep.name());
			if (maximumVersionsToKeep != null) {
				updater.setMaximumVersionsToKeepWithObject(maximumVersionsToKeep);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name())) {
			String versionControlModeName = getEnumName(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name());
			if (versionControlModeName != null) {
				String csiVersionControlModelName = pojoToCsiVersionControlModeNameMap.get(versionControlModeName);
				VersionControlModel csiVersionControlModel = VersionControlModel.valueOf(csiVersionControlModelName);
				updater.setVersionControlModel(csiVersionControlModel);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name())) {
			String versionLabelFormatName = getEnumName(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name());
			if (versionLabelFormatName != null) {
				String csiVersionLabelFormatName = pojoToCsiVersionControlLabelFormatMap.get(versionLabelFormatName);
				LabelFormat csiLabelFormat = LabelFormat.valueOf(csiVersionLabelFormatName);
				updater.setLabelFormat(csiLabelFormat);
			}
		}
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		VersionControlConfigurationUpdater updater = VersionConfigurationFactory.getInstance().createVersionControlConfigurationUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public VersionControlConfiguration concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		VersionControlConfigurationUpdater updater = (VersionControlConfigurationUpdater) context.getUpdater();
		VersionConfigurationControl control = ControlLocator.getInstance().getControl(VersionConfigurationControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		VersionControlConfigurationHandle versionControlConfigHandle = (VersionControlConfigurationHandle) EntityUtils.getInstance().createHandle(id);
		try {
			UpdateMode updateMode = UpdateMode.alwaysUpdate();
			VersionControlConfiguration csiVersionControlConfig = null;
			csiVersionControlConfig = control.updateVersionControlConfiguration(versionControlConfigHandle, updater, updateMode);
			copyObjectState(obj, csiVersionControlConfig, proj);
			return csiVersionControlConfig;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		return beginUpdateObject(obj);
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public VersionControlConfiguration concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		VersionControlConfigurationUpdater updater = (VersionControlConfigurationUpdater) context.getUpdater();
		VersionConfigurationControl control = ControlLocator.getInstance().getControl(VersionConfigurationControl.class);
		Persistent pojoVersionControlConfig = obj.getPojoIdentifiable();
		Persistent pojoContainer = getContainer(pojoVersionControlConfig);
		CollabId containerId = getCollabId(((ManagedIdentifiableProxy)pojoContainer.getManagedObjectProxy()).getObjectId());
		ContainerHandle containerHandle = (ContainerHandle) EntityUtils.getInstance().createHandle(containerId);
		
		try {
			VersionControlConfiguration csiVersionControlConfig = null;
			CollabId id = getCollabId(obj.getObjectId());
			String versionControlModeName = getEnumName(pojoVersionControlConfig, BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name());
			VersionControlModel csiVersionControlModel = null;
			if (versionControlModeName != null) {
				String csiVersionControlModelName = pojoToCsiVersionControlModeNameMap.get(versionControlModeName);
				csiVersionControlModel = VersionControlModel.valueOf(csiVersionControlModelName);
			}
			Integer maximumVersionsToKeep = (Integer) getAttributeValue(pojoVersionControlConfig, BeehiveVersionControlConfigurationInfo.Attributes.maximumVersionsToKeep.name());
			if (maximumVersionsToKeep == null) {
				maximumVersionsToKeep = 100;
			}
			Boolean isAutoLabel = (Boolean) getAttributeValue(pojoVersionControlConfig, BeehiveVersionControlConfigurationInfo.Attributes.autoLabel.name());
			String versionLabelFormatName = getEnumName(pojoVersionControlConfig, BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name());
			LabelFormat csiLabelFormat = null;
			if (versionLabelFormatName != null) {
				String csiVersionLabelFormatName = pojoToCsiVersionControlLabelFormatMap.get(versionLabelFormatName);
				csiLabelFormat = LabelFormat.valueOf(csiVersionLabelFormatName);
			}
			Boolean isFinal = (Boolean) getAttributeValue(pojoVersionControlConfig, BeehiveVersionControlConfigurationInfo.Attributes.finale.name());
			if (containerHandle instanceof ScopeHandle) {
				csiVersionControlConfig = control.createVersionControlConfiguration(id.getEid(), 
						(ScopeHandle) containerHandle, csiVersionControlModel, 
						maximumVersionsToKeep, isAutoLabel, csiLabelFormat, isFinal, 
						ConflictResolutionMode.OVERWRITE, updater, proj);
			} else {
				csiVersionControlConfig = control.createVersionControlConfiguration(id.getEid(), 
						(HeterogeneousFolderHandle) containerHandle, csiVersionControlModel, 
						maximumVersionsToKeep, isAutoLabel, csiLabelFormat, isFinal, 
						ConflictResolutionMode.OVERWRITE, updater, proj);
			}
			copyObjectState(obj, csiVersionControlConfig, proj);
			return csiVersionControlConfig;
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		VersionConfigurationControl control = ControlLocator.getInstance().getControl(VersionConfigurationControl.class);
		CollabId id = getCollabId(obj.getObjectId());
		VersionControlConfigurationHandle versionControlConfigHandle = (VersionControlConfigurationHandle) EntityUtils.getInstance().createHandle(id);
		try {
			control.deleteVersionControlConfiguration(versionControlConfigHandle, DeleteMode.alwaysDelete());
		} catch (CsiException ex) {
			throw new PersistenceException(ex);
		}
	}

	void loadVersionControlConfiguration(ManagedIdentifiableProxy containerObj, Projection proj) {
		PersistenceContext context = containerObj.getPersistenceContext();
		VersionConfigurationControl control = ControlLocator.getInstance().getControl(VersionConfigurationControl.class);
		CollabId id = getCollabId(containerObj.getObjectId());
		ContainerHandle containerHandle = (ContainerHandle) EntityUtils.getInstance().createHandle(id);
		VersionControlConfiguration csiVersionControlConfig = null;
		try {
			if (containerHandle instanceof ScopeHandle) {
				csiVersionControlConfig = control.getVersionControlConfiguration((ScopeHandle) containerHandle, proj);
			} else {
				csiVersionControlConfig = control.getVersionControlConfiguration((HeterogeneousFolderHandle) containerHandle, proj);
			}
		} catch (CsiException ex) {
			// ignore throw new PersistenceException(ex);
		}
		if (csiVersionControlConfig != null) {
			try {
				String attributeName = BeehiveExtentInfo.Attributes.versionControlConfiguration.name();
				Container configContainer = csiVersionControlConfig.getContainer();
				CollabId configContainerId = configContainer.getCollabId();
				Manageable pojoConfigContainer = context.getReference(configContainerId);
				ManagedIdentifiableProxy configContainerObj = (ManagedIdentifiableProxy) pojoConfigContainer.getManagedObjectProxy();
				ManagedIdentifiableProxy versionControlConfigurationObj = getIdentifiableDependentProxy(context, csiVersionControlConfig, configContainerObj, attributeName);
				versionControlConfigurationObj.getProviderProxy().copyLoadedProjection(versionControlConfigurationObj, csiVersionControlConfig, proj);
				Persistent pojoVersionControlConfig = versionControlConfigurationObj.getPojoIdentifiable();
				configContainerObj.getProviderProxy().copyLazyAttribute(configContainerObj, attributeName, null, pojoVersionControlConfig);
				if (containerObj.getPojoIdentifiable() != pojoConfigContainer) {
					containerObj.getProviderProxy().copyLazyAttribute(containerObj, attributeName, null, null);
				}
			} catch (CsiRuntimeException ex) {
			}
		}
	}
	
	public Persistent getContainer(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.extent.name());
	}
	
}
