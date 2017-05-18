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

import icom.info.beehive.BeehiveVersionControlConfigurationInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.MetaDataApplicationDAO;

import java.util.HashMap;

import javax.persistence.PersistenceException;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.ConflictResolutionMode;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.LabelFormat;
import com.oracle.beehive.VersionControlConfigurationCreator;
import com.oracle.beehive.VersionControlConfigurationUpdater;
import com.oracle.beehive.VersionControlModel;

public class VersionControlConfigurationDAO extends BdkIdentifiableDAO implements MetaDataApplicationDAO {

	static VersionControlConfigurationDAO singleton = new VersionControlConfigurationDAO();
	
	public static VersionControlConfigurationDAO getInstance() {
		return singleton;
	}
	
	static HashMap<String, String> bdkToPojoVersionControlModeNameMap;
	static HashMap<String, String> pojoToCsiVersionControlModeNameMap;
	
	{
		bdkToPojoVersionControlModeNameMap = new HashMap<String, String>();
		pojoToCsiVersionControlModeNameMap = new HashMap<String, String>();
		bdkToPojoVersionControlModeNameMap.put(VersionControlModel.AUTO.name(), "Auto");
		bdkToPojoVersionControlModeNameMap.put(VersionControlModel.MANUAL.name(), "Manual");
		bdkToPojoVersionControlModeNameMap.put(VersionControlModel.DISABLED.name(), "Disabled");
		for (String key : bdkToPojoVersionControlModeNameMap.keySet()) {
			pojoToCsiVersionControlModeNameMap.put(bdkToPojoVersionControlModeNameMap.get(key), key);
		}
	}
	
	static HashMap<String, String> bdkToPojoVersionControlLabelFormatMap;
	static HashMap<String, String> pojoToCsiVersionControlLabelFormatMap;
	
	{
		bdkToPojoVersionControlLabelFormatMap = new HashMap<String, String>();
		pojoToCsiVersionControlLabelFormatMap = new HashMap<String, String>();
		bdkToPojoVersionControlLabelFormatMap.put(LabelFormat.DECIMAL_FORMAT.name(), "DecimalFormat");
		bdkToPojoVersionControlLabelFormatMap.put(LabelFormat.INTEGER_FORMAT.name(), "IntegerFormat");
		bdkToPojoVersionControlLabelFormatMap.put(LabelFormat.LOWERCASE_FORMAT.name(), "LowercaseFormat");
		bdkToPojoVersionControlLabelFormatMap.put(LabelFormat.UPPERCASE_FORMAT.name(), "UppercaseFormat");
		bdkToPojoVersionControlLabelFormatMap.put(LabelFormat.ROMAN_NUMERAL_FORMAT.name(), "RomanNumeralFormat");
		for (String key : bdkToPojoVersionControlLabelFormatMap.keySet()) {
			pojoToCsiVersionControlLabelFormatMap.put(bdkToPojoVersionControlLabelFormatMap.get(key), key);
		}
	}
	
	protected VersionControlConfigurationDAO() {
		
	}

	public String getResourceType() {
		return "avcg";
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		Object bdkVersionControlConfiguration = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, bdkVersionControlConfiguration, proj);
		return proj;
	}
	
	public Object loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			GetMethod method = prepareGetMethod(resourceType, collabId, proj);
			Object bdkVersionControlConfiguration = bdkHttpUtil.execute(Object.class, method, userContext.httpClient);
			return bdkVersionControlConfiguration;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkIdentifiable, Projection proj) {
		// not supported
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
				updater.setMaximumVersionsToKeep(maximumVersionsToKeep);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name())) {
			String versionControlModeName = getEnumName(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name());
			if (versionControlModeName != null) {
				String bdkVersionControlModelName = pojoToCsiVersionControlModeNameMap.get(versionControlModeName);
				VersionControlModel bdkVersionControlModel = VersionControlModel.valueOf(bdkVersionControlModelName);
				updater.setVersionControlModel(bdkVersionControlModel);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name())) {
			String versionLabelFormatName = getEnumName(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name());
			if (versionLabelFormatName != null) {
				String bdkVersionLabelFormatName = pojoToCsiVersionControlLabelFormatMap.get(versionLabelFormatName);
				LabelFormat bdkLabelFormat = LabelFormat.valueOf(bdkVersionLabelFormatName);
				updater.setLabelFormat(bdkLabelFormat);
			}
		}
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		VersionControlConfigurationUpdater updater = getBdkUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public Object concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		VersionControlConfigurationUpdater updater = (VersionControlConfigurationUpdater) context.getUpdater();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			PutMethod putMethod = preparePutMethod(resourceType, collabId, userContext.antiCSRF, Projection.EMPTY);
			Object bdkVersionControlConfiguration = bdkHttpUtil.execute(Object.class, putMethod, updater, userContext.httpClient);
			return bdkVersionControlConfiguration;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public DAOContext beginCreateObject(ManagedIdentifiableProxy obj) {
		EntityCreator creator = getBdkCreator();
		VersionControlConfigurationUpdater updater = getBdkUpdater(creator);
		DAOContext context = new DAOContext(creator, updater);
		return context;
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		Persistent pojoIdentifiable = obj.getPojoIdentifiable();
		VersionControlConfigurationCreator creator = (VersionControlConfigurationCreator) context.getCreator();
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.autoLabel.name())) {
			Boolean isAutoLabel = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.autoLabel.name());
			if (isAutoLabel != null) {
				creator.setAutoLabel(isAutoLabel);
			} else {
				creator.setAutoLabel(false);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.finale.name())) {
			Boolean isFinal = (Boolean) getAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.finale.name());
			if (isFinal != null) {
				creator.setIsFinal(isFinal);
			} else {
				creator.setIsFinal(false);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.maximumVersionsToKeep.name())) {
			Integer maximumVersionsToKeep = (Integer) getAttributeValue(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.maximumVersionsToKeep.name());
			if (maximumVersionsToKeep != null) {
				creator.setMaximumVersionsToKeep(maximumVersionsToKeep);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name())) {
			String versionControlModeName = getEnumName(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.versionControlMode.name());
			if (versionControlModeName != null) {
				String bdkVersionControlModelName = pojoToCsiVersionControlModeNameMap.get(versionControlModeName);
				VersionControlModel bdkVersionControlModel = VersionControlModel.valueOf(bdkVersionControlModelName);
				creator.setModel(bdkVersionControlModel);
			}
		}
		
		if (isChanged(obj, BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name())) {
			String versionLabelFormatName = getEnumName(pojoIdentifiable, BeehiveVersionControlConfigurationInfo.Attributes.versionLabelFormat.name());
			if (versionLabelFormatName != null) {
				String bdkVersionLabelFormatName = pojoToCsiVersionControlLabelFormatMap.get(versionLabelFormatName);
				LabelFormat bdkLabelFormat = LabelFormat.valueOf(bdkVersionLabelFormatName);
				creator.setLabelFormat(bdkLabelFormat);
			}
		}
		
		creator.setMode(ConflictResolutionMode.OVERWRITE);
		
		updateNewOrOldObjectState(obj, context);
	}
	
	public Object concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		VersionControlConfigurationCreator creator = (VersionControlConfigurationCreator) context.getCreator();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String resourceType = id.getResourceType();
			PutMethod putMethod = preparePutMethod(resourceType, userContext.antiCSRF, Projection.BASIC);
			Object bdkVersionControlConfiguration = bdkHttpUtil.execute(Object.class, putMethod, creator, userContext.httpClient);
			copyObjectState(obj, bdkVersionControlConfiguration, Projection.BASIC);
			//BeeId newBeeId = bdkVersionControlConfiguration.getid;
			//assignObjectId(pojoCatApp, newBeeId.getId());
			obj.getPersistenceContext().recacheIdentifiableDependent(obj);
			return bdkVersionControlConfiguration;
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

	void loadVersionControlConfiguration(ManagedIdentifiableProxy containerObj, Projection proj) {
		// not supported
	}
	
	protected Class<?> getBdkClass() {
		return Object.class;
	}
	
	protected VersionControlConfigurationUpdater getBdkUpdater() {
		return new VersionControlConfigurationUpdater();
	}
	
	protected VersionControlConfigurationUpdater getBdkUpdater(EntityCreator creator) {
		VersionControlConfigurationUpdater updater = getBdkUpdater();
		((VersionControlConfigurationCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected VersionControlConfigurationCreator getBdkCreator() {
		return new VersionControlConfigurationCreator();
	}
	
}
