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

import icom.info.OnlineContentInfo;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.rt.PersistenceContext;
import oracle.csi.Artifact;
import oracle.csi.ArtifactHandle;
import oracle.csi.CollabId;
import oracle.csi.CsiRuntimeException;
import oracle.csi.IdentifiableHandle;
import oracle.csi.MultiContent;
import oracle.csi.OnlineContent;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.OnlineContentUpdater;

public class OnlineContentDAO extends ContentDAO {

	static OnlineContentDAO singleton = new OnlineContentDAO();
	
	public static OnlineContentDAO getInstance() {
		return singleton;
	}
	
	{
		fullAttributes.add(OnlineContentInfo.Attributes.onlineAttachment);
	}
	
	protected OnlineContentDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return null;
	}
	
	public MultiContent loadObject(ManagedObjectProxy obj, Projection proj) {
		return null;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		super.copyObjectState(obj, csiObject, proj);
		
		OnlineContent csiOnlineContent = (OnlineContent) csiObject;
		PersistenceContext context = obj.getPersistenceContext();
		Object pojoIdentifiable = obj.getPojoObject();
		CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
		Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		
		if (isBetweenProjections((ManagedDependentProxy) obj, OnlineContentInfo.Attributes.onlineAttachment.name(), lastLoadedProjection, proj)) {
			try {
				Artifact csiArtifact  = csiOnlineContent.getContent();
				Persistent pojoArtifact = null;
				if (csiArtifact != null) {
					ManagedIdentifiableProxy onlineAttachmentObj = getEntityProxy(context, csiArtifact);
					pojoArtifact = onlineAttachmentObj.getPojoIdentifiable();
				}
				assignAttributeValue(pojoIdentifiable, OnlineContentInfo.Attributes.onlineAttachment.name(), pojoArtifact);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
	}
	
	private void updateNewOrOldObjectState(ManagedObjectProxy obj, DAOContext context) {
		OnlineContentUpdater onlineContentUpdater = (OnlineContentUpdater) context.getUpdater();
		Object pojoOnlineContent = obj.getPojoObject();
		//if (isChanged(obj, Attributes.onlineAttachment.name())) {
			Persistent pojoArtifact = (Persistent) getAttributeValue(pojoOnlineContent, OnlineContentInfo.Attributes.onlineAttachment.name());
			CollabId id = getCollabId(((ManagedIdentifiableProxy)pojoArtifact.getManagedObjectProxy()).getObjectId());
			ArtifactHandle artifactHandle = (ArtifactHandle) EntityUtils.getInstance().createHandle(id);
			onlineContentUpdater.setArtifact(artifactHandle);
		//}
	}
	
	public void updateObjectState(ManagedObjectProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedObjectProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void committedObject(Persistent pojoIdentifiable) {

	}
	
	public void rolledbackObject(Persistent pojoIdentifiable) {

	}

}
