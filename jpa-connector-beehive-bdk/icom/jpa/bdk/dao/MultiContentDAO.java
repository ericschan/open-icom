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

import icom.info.ContentInfo;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.MultiContentInfo;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.oracle.beehive.Content;
import com.oracle.beehive.EmailMessageContentUpdater;
import com.oracle.beehive.MultiContent;
import com.oracle.beehive.MultiContentListUpdater;
import com.oracle.beehive.MultiContentType;
import com.oracle.beehive.MultiContentUpdater;
import com.oracle.beehive.OnlineContentUpdater;
import com.oracle.beehive.RawString;
import com.oracle.beehive.SimpleContent;
import com.oracle.beehive.StreamedSimpleContentUpdater;

public class MultiContentDAO extends ContentDAO {

	static MultiContentDAO singleton = new MultiContentDAO();
	
	public static MultiContentDAO getInstance() {
		return singleton;
	}
	
	{
		fullAttributes.add(MultiContentInfo.Attributes.parts);
	}

	private static enum ContentClasses { MultiContent, SimpleContent, OnlineContent, UnifiedMessage, Document };

	static public HashMap<String, String> bdkToPojoMediaTypeNameMap;
	static public HashMap<String, String> pojoToBdkMediaTypeNameMap;
		
	{
		bdkToPojoMediaTypeNameMap = new HashMap<String, String>();
		pojoToBdkMediaTypeNameMap = new HashMap<String, String>();
		bdkToPojoMediaTypeNameMap.put(MultiContentType.ALTERNATIVE.name(), "multipart/alternative");
		bdkToPojoMediaTypeNameMap.put(MultiContentType.RELATED.name(), "multipart/related");
		bdkToPojoMediaTypeNameMap.put(MultiContentType.MIXED.name(), "multipart/mixed");
		bdkToPojoMediaTypeNameMap.put(MultiContentType.PARALLEL.name(), "multipart/parallel");
		bdkToPojoMediaTypeNameMap.put(MultiContentType.REPORT.name(), "multipart/report");
		bdkToPojoMediaTypeNameMap.put(MultiContentType.SIGNED.name(), "multipart/signed");
		for (String key : bdkToPojoMediaTypeNameMap.keySet()) {
			pojoToBdkMediaTypeNameMap.put(bdkToPojoMediaTypeNameMap.get(key), key);
		}
	}
		
	protected MultiContentDAO() {
	}
	
	public String getResourceType() {
		throw new RuntimeException("cannot determine bom type for non identifiable objects");
	}
	
	public boolean embedAsNonIdentifiableDependent() {
		return true;
	}
	
	public MultiContent loadObject(ManagedObjectProxy obj, Projection proj) {
		return null;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
		// do not call super copy state because we need to handle mediaType differently
		// super.copyCsiObjectState(obj, bdkObject, proj);
		
		MultiContent bdkMultiContent = (MultiContent) bdkObject;
		PersistenceContext context = obj.getPersistenceContext();
		Object pojoObject = obj.getPojoObject();
		Projection lastLoadedProjection = Projection.EMPTY;
		if (obj instanceof ManagedIdentifiableProxy) {
			BdkProjectionManager projManager = (BdkProjectionManager) obj.getProviderProxy();
			lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		}
		
		if (isBetweenProjections(ContentInfo.Attributes.contentId.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoObject, ContentInfo.Attributes.contentId.name(), bdkMultiContent.getContentId());
			} catch (Exception ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections(ContentInfo.Attributes.mediaType.name(), lastLoadedProjection, proj)) {
			try {
				MultiContentType bdkMultiContentType = bdkMultiContent.getMultiPartType();
				String mediaType = bdkMultiContentType.toString();
				String pojoMediaType = bdkToPojoMediaTypeNameMap.get(mediaType);
				assignAttributeValue(pojoObject, ContentInfo.Attributes.mediaType.name(), pojoMediaType);
			} catch (Exception ex) {
				// ignore
			}
		}

		if (isBetweenProjections(MultiContentInfo.Attributes.parts.name(), lastLoadedProjection, proj)) {
			try {
				List<Content> bdkParts = bdkMultiContent.getParts();
				if (bdkParts != null && bdkParts.size() > 0) {
					/*
					List<Object> pojoParts = getListOfObject(pojoObject, MultiContentInfo.Attributes.parts.name());
					if (pojoParts != null && pojoParts.size() == bdkParts.size()) {
						Iterator<Content> bdkIter = bdkParts.iterator();
						Iterator<Object> pojoIter = pojoParts.iterator();
						while (bdkIter.hasNext()) {
							Content bdkChildContent = bdkIter.next();
							Object pojoChildContent = pojoIter.next();
							ManagedObjectProxy childObj = (ManagedObjectProxy) getAttributeValue(pojoChildContent, AbstractBeanInfo.Attributes.mop.name());
							childObj.copyLoadedProjection(bdkChildContent, proj);
						}
					} else {
					*/
						Iterator<Content> iter = bdkParts.iterator();
						LinkedList<Object> list = new LinkedList<Object>();
						while (iter.hasNext()) {
							Content bdkChildContent = iter.next();
							if (bdkChildContent instanceof SimpleContent) {
								SimpleContent bdkSimpleContent = (SimpleContent) bdkChildContent;
								RawString rawName = bdkSimpleContent.getName();
								if (rawName != null) {
									String name = null;
									name = rawName.getString();
									ManagedDependentProxy dependentProxy = (ManagedDependentProxy) getNonIdentifiableDependentProxy(obj, MultiContentInfo.Attributes.parts.name());
									dependentProxy.getProviderProxy().copyLoadedProjection(dependentProxy, bdkChildContent, proj);
									Persistent documentAttachment = dependentProxy.getPojoObject();
									assignAttributeValue(documentAttachment, EntityInfo.Attributes.name.name(), name);
									list.add(dependentProxy.getPojoObject());
									continue;
								}
							}
							ManagedDependentProxy dependentProxy = (ManagedDependentProxy) getNonIdentifiableDependentProxy(context, bdkChildContent, obj, MultiContentInfo.Attributes.parts.name());
							dependentProxy.getProviderProxy().copyLoadedProjection(dependentProxy, bdkChildContent, proj);
							list.add(dependentProxy.getPojoObject());
						}
						assignAttributeValue(pojoObject, MultiContentInfo.Attributes.parts.name(), list);
					//}
				}
			} catch (Exception ex) {
			    ex.printStackTrace();
			}
		}
	}
	
	private ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(ManagedObjectProxy parent, String parentAttributeName) {
		return getNonIdentifiableDependentProxy(parent.getPersistenceContext(), IcomBeanEnumeration.Document.name(), parent, parentAttributeName);
	}
	
	private void updateNewOrOldObjectState(ManagedObjectProxy obj, DAOContext context) {
		MultiContentUpdater multiContentUpdater = (MultiContentUpdater) context.getUpdater();
		Object pojoMultiContent = obj.getPojoObject();
		String mediaType = (String) getAttributeValue(pojoMultiContent, ContentInfo.Attributes.mediaType.name());
		if ((obj.isNew() && mediaType != null) || isChanged(obj, ContentInfo.Attributes.mediaType.name())) {
			String bdkMediaType = pojoToBdkMediaTypeNameMap.get(mediaType);
			MultiContentType multiContentType = MultiContentType.fromValue(bdkMediaType);
			multiContentUpdater.setMultiContentType(multiContentType);
		}
	}
	
	public void updateObjectState(ManagedObjectProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
		updateChildContentState(obj, context, false);
	}
	
	public void updateNewObjectState(ManagedObjectProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
		updateChildContentState(obj, context, true);
	}
	
	private void updateChildContentState(ManagedObjectProxy obj, DAOContext context, boolean isNew) {
		String uploadScopeId = context.getUploadScopeId();
		MultiContentUpdater multiContentUpdater = (MultiContentUpdater) context.getUpdater();
		Object pojoMultiContent = obj.getPojoObject();
		if (obj.isNew() || isChanged(obj, MultiContentInfo.Attributes.parts.name())) {
			List<?> pojoContentList = (List<?>) getAttributeValue(pojoMultiContent, MultiContentInfo.Attributes.parts.name());
			if (pojoContentList != null) {
				for (Object pojoContent : pojoContentList) {
					ManagedObjectProxy pojoContentProxy = AbstractDAO.getManagedObjectProxy(pojoContent);
					String contentClassName = pojoContent.getClass().getSimpleName();
					if (contentClassName.equals(ContentClasses.MultiContent.name())) {
						MultiContentUpdater childContentUpdater = new MultiContentUpdater();
						MultiContentListUpdater listUpdater = multiContentUpdater.getParts();
						if (listUpdater == null) {
							listUpdater = new MultiContentListUpdater();
							multiContentUpdater.setParts(listUpdater);
						}
						listUpdater.getAdds().add(childContentUpdater);
						DAOContext childContext = new DAOContext(childContentUpdater);
						childContext.setUploadScopeId(uploadScopeId);
						if (isNew) {
							MultiContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
						} else {
							MultiContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
						}
					} else if (contentClassName.equals(ContentClasses.SimpleContent.name())) {
						StreamedSimpleContentUpdater childContentUpdater = new StreamedSimpleContentUpdater();
						MultiContentListUpdater listUpdater = multiContentUpdater.getParts();
						if (listUpdater == null) {
							listUpdater = new MultiContentListUpdater();
							multiContentUpdater.setParts(listUpdater);
						}
						listUpdater.getAdds().add(childContentUpdater);
						DAOContext childContext = new DAOContext(childContentUpdater);
						childContext.setUploadScopeId(uploadScopeId);
						if (isNew) {
							SimpleContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
						} else {
							SimpleContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
						}
					} else if (contentClassName.equals(ContentClasses.OnlineContent.name())) {
						OnlineContentUpdater childContentUpdater = new OnlineContentUpdater();
						MultiContentListUpdater listUpdater = multiContentUpdater.getParts();
						if (listUpdater == null) {
							listUpdater = new MultiContentListUpdater();
							multiContentUpdater.setParts(listUpdater);
						}
						listUpdater.getAdds().add(childContentUpdater);
						DAOContext childContext = new DAOContext(childContentUpdater);
						childContext.setUploadScopeId(uploadScopeId);
						if (isNew) {
							OnlineContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
						} else {
							OnlineContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
						}
					} else if (contentClassName.equals(ContentClasses.UnifiedMessage.name())) {
						EmailMessageContentUpdater childContentUpdater = new EmailMessageContentUpdater();
						MultiContentListUpdater listUpdater = multiContentUpdater.getParts();
						if (listUpdater == null) {
							listUpdater = new MultiContentListUpdater();
							multiContentUpdater.setParts(listUpdater);
						}
						listUpdater.getAdds().add(childContentUpdater);
						DAOContext childContext = new DAOContext(childContentUpdater);
						childContext.setUploadScopeId(uploadScopeId);
						if (isNew) {
							EmailMessageContentUtil.updateNewObjectState(pojoContentProxy, childContext);
						} else {
							EmailMessageContentUtil.updateObjectState(pojoContentProxy, childContext);
						}
					} else if (contentClassName.equals(ContentClasses.Document.name())) {
						StreamedSimpleContentUpdater childContentUpdater = new StreamedSimpleContentUpdater();
						MultiContentListUpdater listUpdater = multiContentUpdater.getParts();
						if (listUpdater == null) {
							listUpdater = new MultiContentListUpdater();
							multiContentUpdater.setParts(listUpdater);
						}
						listUpdater.getAdds().add(childContentUpdater);
						DAOContext childContext = new DAOContext(childContentUpdater);
						childContext.setUploadScopeId(uploadScopeId);
						if (isNew) {
							DocumentToSimpleContentDAO.getInstance().updateNewEmailAttachmentObjectState(pojoContentProxy, childContext);
						} else {
							DocumentToSimpleContentDAO.getInstance().updateEmailAttachmentObjectState(pojoContentProxy, childContext);
						}
					}
				}
			}
		}
	}
	
	public void committedObject(Persistent pojoIdentifiable) {

	}
	
	public void rolledbackObject(Persistent pojoIdentifiable) {

	}
	
}
