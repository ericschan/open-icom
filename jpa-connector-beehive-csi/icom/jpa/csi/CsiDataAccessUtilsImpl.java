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
package icom.jpa.csi;

import icom.jpa.Manageable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.dao.BdkFreeBusyDAO;
import icom.jpa.csi.dao.DocumentDAO;
import icom.jpa.csi.dao.EmailMessageDAO;
import icom.jpa.csi.dao.EnterpriseDAO;
import icom.jpa.csi.dao.UnknownEntityDAO;
import icom.jpa.csi.dao.UnknownIdentifiableDAO;
import icom.jpa.csi.dao.VersionSeriesToDocumentDAO;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessUtils;
import icom.jpa.rt.PersistenceContext;
import icom.jpql.csi.CsiSchemaHelperImpl;
import icom.ql.QueryContext;

import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import oracle.csi.CollabId;
import oracle.csi.EntityHandle;
import oracle.csi.HeterogeneousFolderHandle;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;

public class CsiDataAccessUtilsImpl implements DataAccessUtils {
	
	public AbstractDAO getUnknownEntityDAO() {
		return UnknownEntityDAO.getInstance();
	}
	
	public AbstractDAO getUnknownIdentifiableDAO() {
		return UnknownIdentifiableDAO.getInstance();
	}
	
	public List<Object> resolveResultList(QueryContext queryContext, PersistenceContext persistenceContext, List<Object> resultList) {
		List<Object> pojoResultList = new Vector<Object>();
		Iterator<Object> iter = resultList.iterator();
		while (iter.hasNext()) {
			oracle.csi.Entity csiIdentifiable = (oracle.csi.Entity) iter.next();
			ManagedObjectProxy obj = EnterpriseDAO.getInstance().getEntityProxy(persistenceContext, csiIdentifiable);
			obj.getProviderProxy().copyLoadedProjection(obj, csiIdentifiable, csiIdentifiable.getProjection());
			Object pojoObj = obj.getPojoObject();
			pojoResultList.add(pojoObj);
		}
		return pojoResultList;
	}
	
	public String parseObjectId(Object objectId) {
		CollabId collabId = CollabId.parseCollabId(objectId.toString());
		return collabId.toString();
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
	
	public boolean exists(PersistenceContext context, Object objectId) {
		CollabId id = CollabId.parseCollabId(objectId.toString());
		EntityUtils utils = EntityUtils.getInstance();
		EntityHandle handle = (EntityHandle) utils.createHandle(id);
		try {
			oracle.csi.Entity csiEntity = utils.load(handle, Projection.EMPTY);
			if (csiEntity != null) {
				return true;
			}
		} catch (oracle.csi.CsiException ex) {
			return false;
		}
		return false;
	}

	public Manageable find(PersistenceContext context, Object objectId) {
		CollabId id = CollabId.parseCollabId(objectId.toString());
		EntityUtils utils = EntityUtils.getInstance();
		EntityHandle handle = (EntityHandle) utils.createHandle(id);
		oracle.csi.Entity csiEntity = null;
		Projection proj = Projection.BASIC;
		try {
			csiEntity = utils.load(handle, proj);
			if (csiEntity == null) {
				return (Manageable) null;
			}
		} catch (oracle.csi.CsiException ex) {
			return (Manageable) null;
		}
		ManagedObjectProxy mop = EnterpriseDAO.getInstance().getEntityProxy(context, csiEntity);
		mop.getProviderProxy().copyLoadedProjection(mop, csiEntity, proj);
		Manageable pojo = (Manageable) mop.getPojoObject();
		return pojo;
	}
	
	public Manageable getReference(PersistenceContext context, Object objectId) {
		CollabId id = CollabId.parseCollabId(objectId.toString());
		EntityUtils utils = EntityUtils.getInstance();
		EntityHandle handle = (EntityHandle) utils.createHandle(id);
		oracle.csi.Entity csiEntity = null;
		try {
			csiEntity = utils.load(handle, Projection.EMPTY);
			if (csiEntity == null) {
				return (Manageable) null;
			}
		} catch (oracle.csi.CsiException ex) {
			return (Manageable) null;
		}
		ManagedObjectProxy mop = EnterpriseDAO.getInstance().getEntityProxy(context, csiEntity);
		Manageable pojo = (Manageable) mop.getPojoObject();
		return pojo;
	}
	
	public Object resolveAttributeValueEntity(PersistenceContext context, Object value) {
		return CsiSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, oracle.csi.Entity.class);
	}
	
	public Object resolveAttributeValueEntityAddress(PersistenceContext context, URI uri, String addressType) {
        return CsiSchemaHelperImpl.getInstance().resolveAttributeValueEntityAddress(context, uri, addressType);
    }
	
	public Object resolveAttributeValueEntityAddress(PersistenceContext context, Object value) {
	    return CsiSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, oracle.csi.EntityAddress.class);
	}
	
	public Object resolveAttributeValueParticipant(PersistenceContext context, Object value) {
		return CsiSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, oracle.csi.Participant.class);
	}
	
	public Object resolveAttributeValueType(PersistenceContext context, Object value, Object type) {
		return CsiSchemaHelperImpl.getInstance().resolveParameterValueType(context, value, type);
	}
	
	public void sendEmail(Persistent message, Persistent sentFolder) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) message.getManagedObjectProxy();
		
		Object objectId = ((ManagedIdentifiableProxy)(sentFolder.getManagedObjectProxy())).getObjectId();
		CollabId sentFolderId = CollabId.parseCollabId(objectId.toString());
		HeterogeneousFolderHandle sentFolderHandle = (HeterogeneousFolderHandle) EntityUtils.getInstance().createHandle(sentFolderId);
		EmailMessageDAO.getInstance().sendEmail(obj, sentFolderHandle);
	}
	
	public void sendDispositionNotification(Persistent message) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) message.getManagedObjectProxy();
		EmailMessageDAO.getInstance().sendDispositionNotification(obj, EmailMessageDAO.DispositionNotificationType.SEND_DISPOSITION_NOTIFICATION);
	}
	
	public void sendNotReadDispositionNotification(Persistent message) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) message.getManagedObjectProxy();
		EmailMessageDAO.getInstance().sendDispositionNotification(obj, EmailMessageDAO.DispositionNotificationType.SEND_NOT_READ_DISPOSITION_NOTIFICATION);
	}
	
	/*
	public Persistent checkoutVersionSeries(Persistent versionSeries, String checkoutComments) {
		ManagedIdentifiableProxy versionSeriesObj = (ManagedIdentifiableProxy) versionSeries.getManagedObjectProxy();
		return VersionSeriesToDocumentDAO.getInstance().checkoutVersionSeries(versionSeriesObj, checkoutComments);
	}
	*/
	 
    /*
    public Persistent checkinVersion(Persistent version) {
        ManagedIdentifiableProxy versionObj = (ManagedIdentifiableProxy) version.getManagedObjectProxy();
        return VersionDAO.getInstance().checkin(versionObj);
    }
    */
	
	// checks out using a representative copy of versionable artifact and returns a version node
    // the client must get the private working copy from the version node
	public Persistent checkoutRepresentativeCopyOfVersionable(Persistent representativeCopyOfVersionable, String checkoutComments) {
		ManagedIdentifiableProxy representativeCopyOfVersionableObj = (ManagedIdentifiableProxy) representativeCopyOfVersionable.getManagedObjectProxy();
		return VersionSeriesToDocumentDAO.getInstance().checkoutFamilyArtifact(representativeCopyOfVersionableObj, checkoutComments);
	}

	// checks in a private working copy of versionable artifact and returns a version node
	// the client must get the versioned copy from the version node
	public Persistent checkinPrivateWorkingCopyOfVersionable(Persistent privateWorkingCopyOfVersionable, String versionName) {
		ManagedIdentifiableProxy privateWorkingCopyOfVersionableObj = (ManagedIdentifiableProxy) privateWorkingCopyOfVersionable.getManagedObjectProxy();
		return DocumentDAO.getInstance().checkin(privateWorkingCopyOfVersionableObj, versionName);
	}
	
	public void cancelCheckout(Persistent representativeCopyOfVersionable) {
		ManagedIdentifiableProxy representativeCopyOfVersionableObj = (ManagedIdentifiableProxy) representativeCopyOfVersionable.getManagedObjectProxy();
		DocumentDAO.getInstance().cancelCheckout(representativeCopyOfVersionableObj);
	}
	
	public Object loadFreeBusyOfActor(Persistent actor, Date startDate, Date endDate) {
		ManagedIdentifiableProxy actorObj = (ManagedIdentifiableProxy) actor.getManagedObjectProxy();
		return BdkFreeBusyDAO.getInstance().loadFreeBusyOfActor(actorObj, startDate, endDate);
	}

}
