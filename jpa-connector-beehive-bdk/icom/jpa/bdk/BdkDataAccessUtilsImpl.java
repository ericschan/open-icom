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
package icom.jpa.bdk;

import icom.jpa.Manageable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.dao.DocumentDAO;
import icom.jpa.bdk.dao.EmailMessageDAO;
import icom.jpa.bdk.dao.EnterpriseDAO;
import icom.jpa.bdk.dao.UnknownEntityDAO;
import icom.jpa.bdk.dao.UnknownIdentifiableDAO;
import icom.jpa.bdk.dao.VersionSeriesToDocumentDAO;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessUtils;
import icom.jpa.rt.PersistenceContext;
import icom.jpql.bdk.BdkQueryContext;
import icom.jpql.bdk.BdkSchemaHelperImpl;
import icom.ql.QueryContext;

import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.httpclient.methods.GetMethod;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.Entity;
import com.oracle.beehive.EntityAddress;
import com.oracle.beehive.Participant;

public class BdkDataAccessUtilsImpl implements DataAccessUtils {
	
	BdkHttpUtil bdkHttpUtil = BdkHttpUtil.getInstance();
	
	public String enterpriseId;
	public String siteId;
	
	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public AbstractDAO getUnknownEntityDAO() {
		return UnknownEntityDAO.getInstance();
	}
	
	public AbstractDAO getUnknownIdentifiableDAO() {
		return UnknownIdentifiableDAO.getInstance();
	}
	
	public List<Object> resolveResultList(QueryContext queryContext, PersistenceContext persistenceContext, List<Object> resultList) {
		BdkQueryContext bdkQueryContext = (BdkQueryContext) queryContext;
		Projection proj = bdkQueryContext.getProjection();
		List<Object> pojoResultList = new Vector<Object>();
		Iterator<Object> iter = resultList.iterator();
		while (iter.hasNext()) {
			Entity csiIdentifiable = (Entity) iter.next();
			ManagedObjectProxy obj = EnterpriseDAO.getInstance().getEntityProxy(persistenceContext, csiIdentifiable);
			obj.getProviderProxy().copyLoadedProjection(obj, csiIdentifiable, proj);
			Object pojoObj = obj.getPojoObject();
			pojoResultList.add(pojoObj);
		}
		return pojoResultList;
	}
	
	static public BeeId getBeeId(String collabId) {
		BeeId beeId = new BeeId();
		beeId.setId(collabId);
		String resourceType = collabId.substring(10, 14);
		beeId.setResourceType(resourceType);
		return beeId;
	}
	
	public String parseObjectId(Object objectId) {
		BeeId id = getBeeId(objectId.toString());
		return id.getId();
	}
	
	static public boolean hasBeehiveObjectIdFormat(String collabId) {
	    char c1 = collabId.charAt(4);
        char c2 = collabId.charAt(9);
	    char c3 = collabId.charAt(14);
	    if (c1 == ':' && c2 == ':' && c3 == ':') {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public Entity loadBdkEntity(PersistenceContext context, Object objectId, Projection proj) {
		BeeId id = getBeeId(objectId.toString());
		String collabId = id.getId();
		String resourceType = id.getResourceType();
		Entity bdkEntity = null;
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
			GetMethod method = BdkAbstractDAO.prepareGetMethod(resourceType, collabId, proj);
			bdkEntity = (Entity) bdkHttpUtil.execute(Entity.class, method, userContext.httpClient);
		} catch (Exception ex) {
			return (Entity) null;
		}
		return bdkEntity;
	}
	
	public Manageable loadPojo(PersistenceContext context, Object objectId, Projection proj) {
		Entity bdkEntity = loadBdkEntity(context, objectId, proj);
		if (bdkEntity != null) {
			ManagedObjectProxy mop = EnterpriseDAO.getInstance().getEntityProxy(context, bdkEntity);
			mop.getProviderProxy().copyLoadedProjection(mop, bdkEntity, proj);
			Manageable pojo = (Manageable) mop.getPojoObject();
			return pojo;
		} else {
			return null;
		}
	}
	
	public boolean exists(PersistenceContext context, Object objectId) {
		Manageable pojo = loadPojo(context, objectId, Projection.EMPTY);
		if (pojo != null) {
			return true;
		} else {
			return false;
		}
	}

	public Manageable find(PersistenceContext context, Object objectId) {
		Manageable pojo = loadPojo(context, objectId, Projection.FULL);
		return pojo;
	}
	
	public Manageable getReference(PersistenceContext context, Object objectId) {
		Manageable pojo = loadPojo(context, objectId, Projection.BASIC);
		return pojo;
	}
	
	public Object resolveAttributeValueEntity(PersistenceContext context, Object value) {
		return BdkSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, Entity.class);
	}
	
	public Object resolveAttributeValueEntityAddress(PersistenceContext context, URI uri, String addressType) {
	    return BdkSchemaHelperImpl.getInstance().resolveAttributeValueEntityAddress(context, uri, addressType);
	}
	
	public Object resolveAttributeValueEntityAddress(PersistenceContext context, Object value) {
	    return BdkSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, EntityAddress.class);
	}
	
	public Object resolveAttributeValueParticipant(PersistenceContext context, Object value) {
		return BdkSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, Participant.class);
	}
	
	public Object resolveAttributeValueType(PersistenceContext context, Object value, Object type) {
		return BdkSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, type);
	}
	
	public void sendEmail(Persistent message, Persistent sentFolder) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) message.getManagedObjectProxy();
		BeeId sentFolderId = getBeeId(((ManagedIdentifiableProxy)(sentFolder.getManagedObjectProxy())).getObjectId().toString());
		EmailMessageDAO.getInstance().sendEmail(obj, sentFolderId);
	}
	
	public void sendDispositionNotification(Persistent message) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) message.getManagedObjectProxy();
		//EmailMessageDAO.getInstance().sendDispositionNotification(obj, EmailMessageDAO.DispositionNotificationType.SEND_DISPOSITION_NOTIFICATION);
	}
	
	public void sendNotReadDispositionNotification(Persistent message) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) message.getManagedObjectProxy();
		//EmailMessageDAO.getInstance().sendDispositionNotification(obj, EmailMessageDAO.DispositionNotificationType.SEND_NOT_READ_DISPOSITION_NOTIFICATION);
	}

	// checks out a representative copy of versionable artifact and returns a version node
    // if the artifact is not under version control, a new version series is started
    // the client must get the private working copy from the version node
	public Persistent checkoutRepresentativeCopyOfVersionable(Persistent representativeCopyOfVersionable, String checkoutComments) {
		ManagedIdentifiableProxy representativeCopyOfVersionableObj = (ManagedIdentifiableProxy) representativeCopyOfVersionable.getManagedObjectProxy();
		return VersionSeriesToDocumentDAO.getInstance().checkoutGetPojoVersionNode(representativeCopyOfVersionableObj, checkoutComments);
	}

	// checks in a private working copy of versionable artifact and returns a version node
	// the client must get the versioned copy from the version node
	public Persistent checkinPrivateWorkingCopyOfVersionable(Persistent privateWorkingCopyOfVersionable, String versionName) {
		ManagedIdentifiableProxy privateWorkingCopyOfVersionableObj = (ManagedIdentifiableProxy) privateWorkingCopyOfVersionable.getManagedObjectProxy();
		return DocumentDAO.getInstance().checkinGetPojoVersionNode(privateWorkingCopyOfVersionableObj, versionName);
	}
	
	public void cancelCheckout(Persistent representativeCopyOfVersionable) {
		ManagedIdentifiableProxy representativeCopyOfVersionableObj = (ManagedIdentifiableProxy) representativeCopyOfVersionable.getManagedObjectProxy();
		DocumentDAO.getInstance().cancelCheckout(representativeCopyOfVersionableObj);
	}
	
	public Object loadFreeBusyOfActor(Persistent actor, Date startDate, Date endDate) {
		ManagedIdentifiableProxy actorObj = (ManagedIdentifiableProxy) actor.getManagedObjectProxy();
		return null; // BdkFreeBusyDAO.getInstance().loadFreeBusyOfActor(actorObj, startDate, endDate);
	}

}
