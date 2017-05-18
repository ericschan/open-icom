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


import com.oracle.beehive.Actor;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Entity;
import com.oracle.beehive.Label;
import com.oracle.beehive.LabelApplication;
import com.oracle.beehive.LabelApplicationType;
import com.oracle.beehive.LabelApplicationUpdater;

import icom.info.TagApplicationInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.info.beehive.BeehiveTagApplicationInfo;

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.MetaDataApplicationDAO;

import java.util.Date;

import javax.persistence.PersistenceException;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;


public class LabelApplicationDAO extends BdkIdentifiableDAO implements MetaDataApplicationDAO {
	
	static LabelApplicationDAO singleton = new LabelApplicationDAO();
	
	public static LabelApplicationDAO getInstance() {
		return singleton;
	}
	
	{
		basicAttributes.add(TagApplicationInfo.Attributes.attachedEntity);
		basicAttributes.add(TagApplicationInfo.Attributes.tag);
		basicAttributes.add(TagApplicationInfo.Attributes.appliedBy);
		basicAttributes.add(TagApplicationInfo.Attributes.applicationDate);
		basicAttributes.add(BeehiveTagApplicationInfo.Attributes.type);
	}

	protected LabelApplicationDAO() {
		
	}

	public String getResourceType() {
		return "lbap";
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		Projection proj = getProjection(attributeName);
		LabelApplication bdkLabelApplication = loadObject((ManagedIdentifiableProxy) obj, proj);
		copyObjectState((ManagedIdentifiableProxy) obj, bdkLabelApplication, proj);
		return proj;
	}
	
	public LabelApplication loadObject(ManagedIdentifiableProxy obj, Projection proj) {
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			GetMethod method = prepareGetMethod(resourceType, collabId, proj);
			LabelApplication bdkLabelApplication = (LabelApplication) bdkHttpUtil.execute(LabelApplication.class, method, userContext.httpClient);
			return bdkLabelApplication;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

    public void copyObjectState(ManagedObjectProxy obj, Object bdkIdentifiable, Projection proj) {
        LabelApplication bdkLabelApplication = (LabelApplication)bdkIdentifiable;
        Persistent pojoIdentifiable = obj.getPojoObject();

        if ( /* !isPartOfProjection(TagApplicationInfo.Attributes.taggedEntity.name(), lastLoadedProjection) && */
            isPartOfProjection(TagApplicationInfo.Attributes.attachedEntity.name(), proj)) {
            try {
                Entity bdkLabeledEntity = bdkLabelApplication.getLabeledEntity();
                marshallAssignEntity(obj, TagApplicationInfo.Attributes.attachedEntity.name(), bdkLabeledEntity);
            } catch (Exception ex) {
                // ignore
            }
        }

        if ( /* !isPartOfProjection(TagApplicationInfo.Attributes.tag.name(), lastLoadedProjection) && */
            isPartOfProjection(TagApplicationInfo.Attributes.tag.name(), proj)) {
            try {
                Label bdkLabel = bdkLabelApplication.getLabel();
                marshallAssignEntity(obj, TagApplicationInfo.Attributes.tag.name(), bdkLabel);
            } catch (Exception ex) {
                // ignore
            }
        }

        if ( /* !isPartOfProjection(BeehiveTagApplicationInfo.Attributes.type.name(), lastLoadedProjection) && */
            isPartOfProjection(BeehiveTagApplicationInfo.Attributes.type.name(), proj)) {
            try {
                LabelApplicationType bdkLabelApplicationType = bdkLabelApplication.getLabelApplicationType();
                String labelApplicationTypeName = null;
                if (bdkLabelApplicationType != null) {
                    labelApplicationTypeName = bdkLabelApplicationType.name();
                }
                assignEnumConstant(pojoIdentifiable, BeehiveTagApplicationInfo.Attributes.type.name(),
                                   BeehiveBeanEnumeration.BeehiveTagApplicationType.getPackageName(),
                                   BeehiveBeanEnumeration.BeehiveTagApplicationType.name(), labelApplicationTypeName);
            } catch (Exception ex) {
                // ignore
            }
        }
        
        if ( /*!isPartOfProjection(TagApplicationInfo.Attributes.appliedBy.name(), lastLoadedProjection) && */
            isPartOfProjection(TagApplicationInfo.Attributes.appliedBy.name(), proj)) {
            try {
                Actor bdkActor = bdkLabelApplication.getAppliedBy();
                marshallAssignEntity(obj, TagApplicationInfo.Attributes.appliedBy.name(), bdkActor);
            } catch (Exception ex) {
                // ignore
            }
        }

        if ( /* !isPartOfProjection(TagApplicationInfo.Attributes.appliedOn.name(), lastLoadedProjection) && */
            isPartOfProjection(TagApplicationInfo.Attributes.applicationDate.name(), proj)) {
            try {
                XMLGregorianCalendar xdate = bdkLabelApplication.getAppliedOn();
                if (xdate != null) {
                    Date date = xdate.toGregorianCalendar().getTime();
                    assignAttributeValue(pojoIdentifiable, TagApplicationInfo.Attributes.applicationDate.name(), date);
                } else {
                    assignAttributeValue(pojoIdentifiable, TagApplicationInfo.Attributes.applicationDate.name(), null);
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
		LabelApplicationUpdater updater = (LabelApplicationUpdater) context.getUpdater();
		Persistent pojoTagApp = obj.getPojoIdentifiable();
		
		if (isChanged(obj, BeehiveTagApplicationInfo.Attributes.type.name())) {
			LabelApplicationType labelAppType = LabelApplicationType.PRIVATE;
			String tagApplicationTypeName = getEnumName(pojoTagApp, BeehiveTagApplicationInfo.Attributes.type.name());
			if (tagApplicationTypeName != null) {
				labelAppType = LabelApplicationType.valueOf(tagApplicationTypeName);
			}
			updater.setLabelApplicationType(labelAppType);
		}
	}
	
	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		LabelApplicationUpdater updater = new LabelApplicationUpdater();
		DAOContext context = new DAOContext(updater);
		return context;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public LabelApplication concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		LabelApplicationUpdater updater = (LabelApplicationUpdater) context.getUpdater();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			BeeId id = getBeeId(obj.getObjectId().toString());
			String collabId = id.getId();
			String resourceType = id.getResourceType();
			PutMethod putMethod = preparePutMethod(resourceType, collabId, userContext.antiCSRF, Projection.EMPTY);
			LabelApplication bdkLabelApplication = (LabelApplication) bdkHttpUtil.execute(LabelApplication.class, putMethod, updater, userContext.httpClient);
			return bdkLabelApplication;
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
	
	public Persistent getLabeledEntity(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, TagApplicationInfo.Attributes.attachedEntity.name());
	}

	
	public LabelApplication concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		LabelApplicationUpdater updater = (LabelApplicationUpdater) context.getUpdater();
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			Persistent pojoTagApp = obj.getPojoIdentifiable();
			Persistent pojoEntity = getLabeledEntity(pojoTagApp);
			BeeId entityId = getBeeId(((ManagedIdentifiableProxy)pojoEntity.getManagedObjectProxy()).getObjectId().toString());
			String resourceType = entityId.getResourceType();
			
			Persistent tag = (Persistent) getAttributeValue(pojoTagApp, TagApplicationInfo.Attributes.tag.name());
			BeeId tagId = getBeeId(((ManagedIdentifiableProxy)tag.getManagedObjectProxy()).getObjectId().toString());
			String labelId = tagId.getId();
			String params = "labelid=" + labelId;
			String tagApplicationTypeName = getEnumName(pojoTagApp, BeehiveTagApplicationInfo.Attributes.type.name());
			if (tagApplicationTypeName != null) {
				LabelApplicationType labelAppType = LabelApplicationType.valueOf(tagApplicationTypeName);
				params += "&type=" + labelAppType.name();
			} else {
				params += "&type=" + LabelApplicationType.PUBLIC.name();
			}
			PostMethod postMethod = preparePostMethod(resourceType + "/label/apply", entityId.getId(), userContext.antiCSRF, Projection.EMPTY, params);
			LabelApplication bdkLabelApplication = (LabelApplication) bdkHttpUtil.execute(LabelApplication.class, postMethod, updater, userContext.httpClient);
			return bdkLabelApplication;
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			Persistent pojoTagApp = obj.getPojoIdentifiable();
			Persistent pojoEntity = getLabeledEntity(pojoTagApp);
			BeeId entityId = getBeeId(((ManagedIdentifiableProxy)pojoEntity.getManagedObjectProxy()).getObjectId().toString());
			String resourceType = entityId.getResourceType();
			String labelId = obj.getObjectId().toString();
			String params = "labelid=" + labelId;
			PostMethod postMethod = preparePostMethod(resourceType + "/label/remove", entityId.getId(), userContext.antiCSRF, Projection.EMPTY, params);
			bdkHttpUtil.execute(LabelApplication.class, postMethod, userContext.httpClient);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}

	void loadLabelApplicationsOnEntity(ManagedIdentifiableProxy obj, Projection projection) {
		
	}

}
