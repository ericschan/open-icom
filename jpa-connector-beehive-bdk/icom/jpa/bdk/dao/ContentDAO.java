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

import icom.info.BeanHandler;
import icom.info.ContentInfo;
import icom.info.IcomBeanEnumeration;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.bdk.BdkIdentifiableDAO;
import icom.jpa.bdk.Projection;

import java.util.HashMap;

import com.oracle.beehive.Content;
import com.oracle.beehive.ContentDispositionType;
import com.oracle.beehive.ContentUpdater;

public abstract class ContentDAO extends BdkIdentifiableDAO implements StreamDAO {
	
	{
		fullAttributes.add(ContentInfo.Attributes.contentId);
		fullAttributes.add(ContentInfo.Attributes.mediaType);
		fullAttributes.add(ContentInfo.Attributes.contentDisposition);
	}
	
	static HashMap<String, String> bdkToPojoContentDispositionMap;
	static HashMap<String, String> pojoToBdkContentDispositionMap;
	
	{
		bdkToPojoContentDispositionMap = new HashMap<String, String>();
		pojoToBdkContentDispositionMap = new HashMap<String, String>();
		bdkToPojoContentDispositionMap.put("INLINE", "Inline");
		bdkToPojoContentDispositionMap.put("ATTACHMENT", "Attachment");
		for (String key : bdkToPojoContentDispositionMap.keySet()) {
			pojoToBdkContentDispositionMap.put(bdkToPojoContentDispositionMap.get(key), key);
		}
	}

	protected ContentDAO() {
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
	
	public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
		Content bdkContent = (Content) bdkObject;
		Object pojoObject = obj.getPojoObject();
		
		//if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.contentId.name(), lastLoadedProjection, proj)) {
		if (isPartOfProjection(ContentInfo.Attributes.contentId.name(), proj)) {
			try {
				assignAttributeValue(pojoObject, ContentInfo.Attributes.contentId.name(), bdkContent.getContentId());
			} catch (Exception ex) {
				// ignore
			}
		}

		//if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.mediaType.name(), lastLoadedProjection, proj)) {
		if (isPartOfProjection(ContentInfo.Attributes.mediaType.name(), proj)) {
			try {
				assignAttributeValue(pojoObject, ContentInfo.Attributes.mediaType.name(), bdkContent.getMediaType());
			} catch (Exception ex) {
				// ignore
			}
		}
		
		//if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.contentDisposition.name(), lastLoadedProjection, proj)) {
		if (isPartOfProjection(ContentInfo.Attributes.contentDisposition.name(), proj)) {
			try {
				ContentDispositionType bdkDispositionType = bdkContent.getContentDisposition();
				String pojoDispositionTypeName = null;
				if (bdkDispositionType != null) {
					String bdkDispositionTypeName = bdkDispositionType.name();
					pojoDispositionTypeName = bdkToPojoContentDispositionMap.get(bdkDispositionTypeName);
				}
				assignEnumConstant(pojoObject, ContentInfo.Attributes.contentDisposition.name(), BeanHandler.getBeanPackageName(), IcomBeanEnumeration.ContentDispositionTypeEnum.name(), pojoDispositionTypeName);
			} catch (Exception ex) {
				// ignore
			}
		}
		
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
			String bdkDispositionTypeName = pojoToBdkContentDispositionMap.get(pojoDispositionTypeName);
			ContentDispositionType bdkDispositionType = ContentDispositionType.valueOf(bdkDispositionTypeName);
			updater.setContentDisposition(bdkDispositionType);
		}
	}
	
	public void updateObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public void flushToOutputStream(ManagedObjectProxy mop) {
		
	}
	
}
