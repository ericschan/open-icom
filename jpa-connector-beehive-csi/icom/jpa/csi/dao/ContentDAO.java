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

import icom.info.BeanHandler;
import icom.info.ContentInfo;
import icom.info.IcomBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.csi.CsiProjectionManager;

import java.util.HashMap;

import oracle.csi.Content;
import oracle.csi.ContentDispositionType;
import oracle.csi.CsiRuntimeException;
import oracle.csi.EmailOperationContext;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.ContentUpdater;

public abstract class ContentDAO extends DependentDAO implements StreamDAO {
	
	static HashMap<String, String> csiToPojoContentDispositionMap;
	static HashMap<String, String> pojoToCsiContentDispositionMap;
	
	{
		csiToPojoContentDispositionMap = new HashMap<String, String>();
		pojoToCsiContentDispositionMap = new HashMap<String, String>();
		csiToPojoContentDispositionMap.put("INLINE", "Inline");
		csiToPojoContentDispositionMap.put("ATTACHMENT", "Attachment");
		for (String key : csiToPojoContentDispositionMap.keySet()) {
			pojoToCsiContentDispositionMap.put(csiToPojoContentDispositionMap.get(key), key);
		}
	}

	protected ContentDAO() {
	}
	{
		fullAttributes.add(ContentInfo.Attributes.contentId);
		fullAttributes.add(ContentInfo.Attributes.mediaType);
		fullAttributes.add(ContentInfo.Attributes.contentDisposition);
	}
	
	public boolean isChanged(ManagedObjectProxy obj, String attributeName) {
		if (/*obj.isNew() || don't check for dependent object is new*/ 
				obj.containsAttributeChangeRecord(attributeName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void load(ManagedObjectProxy obj) {
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		Content csiContent = (Content) csiObject;
		Object pojoObject = obj.getPojoObject();
		Projection lastLoadedProjection = Projection.EMPTY;
		if (obj instanceof ManagedIdentifiableProxy) {
			CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
			lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		}
		
		//if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.contentId.name(), lastLoadedProjection, proj)) {
		if (isPartOfProjection(ContentInfo.Attributes.contentId.name(), proj)) {
			try {
				assignAttributeValue(pojoObject, ContentInfo.Attributes.contentId.name(), csiContent.getContentId());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}

		//if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.mediaType.name(), lastLoadedProjection, proj)) {
		if (isPartOfProjection(ContentInfo.Attributes.mediaType.name(), proj)) {
			try {
				assignAttributeValue(pojoObject, ContentInfo.Attributes.mediaType.name(), csiContent.getMediaType());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		//if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.contentDisposition.name(), lastLoadedProjection, proj)) {
		if (isPartOfProjection(ContentInfo.Attributes.contentDisposition.name(), proj)) {
			try {
				ContentDispositionType csiDispositionType = csiContent.getContentDisposition();
				String pojoDispositionTypeName = null;
				if (csiDispositionType != null) {
					String csiDispositionTypeName = csiDispositionType.name();
					pojoDispositionTypeName = csiToPojoContentDispositionMap.get(csiDispositionTypeName);
				}
				assignEnumConstant(pojoObject, ContentInfo.Attributes.contentDisposition.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ContentDispositionTypeEnum.name(), pojoDispositionTypeName);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
	}
	
	public void save(ManagedObjectProxy obj) {
	}
	
	private void updateNewOrOldObjectState(ManagedObjectProxy obj, DAOContext context) {
		ContentUpdater updater = (ContentUpdater) context.getUpdater();
		Object pojoIdentifiable = obj.getPojoObject();
		String mediaType = (String) getAttributeValue(pojoIdentifiable, ContentInfo.Attributes.mediaType.name());
		if ((obj.isNew() && mediaType != null) || isChanged(obj, ContentInfo.Attributes.mediaType.name())) {
			updater.setMediaType(mediaType);
		}
		
		String contentId = (String) getAttributeValue(pojoIdentifiable, ContentInfo.Attributes.contentId.name());
		if ((obj.isNew() && contentId != null) || isChanged(obj, ContentInfo.Attributes.contentId.name())) {
			updater.setContentId(contentId);
		}
		
		String pojoDispositionTypeName = getEnumName(pojoIdentifiable, ContentInfo.Attributes.contentDisposition.name());
		if ((obj.isNew() && pojoDispositionTypeName != null) || isChanged(obj, ContentInfo.Attributes.contentDisposition.name())) {
			String csiDispositionTypeName = pojoToCsiContentDispositionMap.get(pojoDispositionTypeName);
			ContentDispositionType csiDispositionType = ContentDispositionType.valueOf(csiDispositionTypeName);
			updater.setContentDisposition(csiDispositionType);
		}
	}
	
	public void updateObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public void flushToOutputStream(ManagedObjectProxy mop, EmailOperationContext opContext) {
		
	}
	
}
