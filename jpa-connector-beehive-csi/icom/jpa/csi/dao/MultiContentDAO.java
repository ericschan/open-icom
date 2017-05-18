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

import icom.info.ContentInfo;
import icom.info.EntityInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.MultiContentInfo;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.rt.PersistenceContext;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import oracle.csi.Content;
import oracle.csi.CsiRuntimeException;
import oracle.csi.EmailOperationContext;
import oracle.csi.IdentifiableHandle;
import oracle.csi.MultiContent;
import oracle.csi.MultiContentType;
import oracle.csi.RawString;
import oracle.csi.SimpleContent;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.EmailMessageContentUpdater;
import oracle.csi.updaters.MultiContentUpdater;
import oracle.csi.updaters.OnlineContentUpdater;
import oracle.csi.updaters.StreamedSimpleContentUpdater;

public class MultiContentDAO extends ContentDAO {

	static MultiContentDAO singleton = new MultiContentDAO();
	
	public static MultiContentDAO getInstance() {
		return singleton;
	}
	
	{
		fullAttributes.add(MultiContentInfo.Attributes.parts);
	}

	private static enum ContentClasses { MultiContent, SimpleContent, OnlineContent, UnifiedMessage, Document };

	protected MultiContentDAO() {
	}
	
	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return null;
	}
	
	public boolean embedAsNonIdentifiableDependent() {
		return true;
	}
	
	public MultiContent loadObject(ManagedObjectProxy obj, Projection proj) {
		return null;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		// do not call super copy state because we need to handle mediaType differently
		// super.copyCsiObjectState(obj, csiObject, proj);
		
		MultiContent csiMultiContent = (MultiContent) csiObject;
		PersistenceContext context = obj.getPersistenceContext();
		Object pojoObject = obj.getPojoObject();
		Projection lastLoadedProjection = Projection.EMPTY;
		if (obj instanceof ManagedIdentifiableProxy) {
			CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
			lastLoadedProjection = projManager.getLastLoadedProjection(obj);
		}
		
		if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.contentId.name(), lastLoadedProjection, proj)) {
			try {
				assignAttributeValue(pojoObject, ContentInfo.Attributes.contentId.name(), csiMultiContent.getContentId());
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}
		
		if (isBetweenProjections((ManagedDependentProxy) obj, ContentInfo.Attributes.mediaType.name(), lastLoadedProjection, proj)) {
			try {
				MultiContentType csiMultiContentType = csiMultiContent.getMultiPartType();
				String mediaType = csiMultiContentType.toString();
				assignAttributeValue(pojoObject, ContentInfo.Attributes.mediaType.name(), mediaType);
			} catch (CsiRuntimeException ex) {
				// ignore
			}
		}

		if (isBetweenProjections((ManagedDependentProxy) obj, MultiContentInfo.Attributes.parts.name(), lastLoadedProjection, proj)) {
			try {
				List<Content> csiParts = csiMultiContent.getParts();
				if (csiParts != null && csiParts.size() > 0) {
					/*
					List<Object> pojoParts = getListOfObject(pojoObject, MultiContentInfo.Attributes.parts.name());
					if (pojoParts != null && pojoParts.size() == csiParts.size()) {
						Iterator<Content> csiIter = csiParts.iterator();
						Iterator<Object> pojoIter = pojoParts.iterator();
						while (csiIter.hasNext()) {
							Content csiChildContent = csiIter.next();
							Object pojoChildContent = pojoIter.next();
							ManagedObjectProxy childObj = (ManagedObjectProxy) getAttributeValue(pojoChildContent, AbstractBeanInfo.Attributes.mop.name());
							childObj.copyLoadedProjection(csiChildContent, proj);
						}
					} else {
					*/
						Iterator<Content> iter = csiParts.iterator();
						LinkedList<Object> list = new LinkedList<Object>();
						while (iter.hasNext()) {
							Content csiChildContent = iter.next();
							if (csiChildContent instanceof SimpleContent) {
								SimpleContent csiSimpleContent = (SimpleContent) csiChildContent;
								RawString rawName = csiSimpleContent.getName();
								if (rawName != null) {
									String name = null;
									try {
										name = rawName.convertToString();
									} catch (UnsupportedEncodingException ex) {
										name = rawName.getBytes().toString();
									}
									ManagedDependentProxy dependentProxy = (ManagedDependentProxy) getNonIdentifiableDependentProxy(obj, MultiContentInfo.Attributes.parts.name());
									dependentProxy.getProviderProxy().copyLoadedProjection(dependentProxy, csiChildContent, proj);
									Persistent documentAttachment = dependentProxy.getPojoObject();
									assignAttributeValue(documentAttachment, EntityInfo.Attributes.name.name(), name);
									list.add(dependentProxy.getPojoObject());
									continue;
								}
							}
							ManagedDependentProxy dependentProxy = (ManagedDependentProxy) getNonIdentifiableDependentProxy(context, csiChildContent, obj, MultiContentInfo.Attributes.parts.name());
							dependentProxy.getProviderProxy().copyLoadedProjection(dependentProxy, csiChildContent, proj);
							list.add(dependentProxy.getPojoObject());
						}
						assignAttributeValue(pojoObject, MultiContentInfo.Attributes.parts.name(), list);
					//}
				}
			} catch (CsiRuntimeException ex) {
				// ignore
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
			MultiContentType multiContentType = MultiContentType.parseMediaType(mediaType);
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
		MultiContentUpdater multiContentUpdater = (MultiContentUpdater) context.getUpdater();
		Object pojoMultiContent = obj.getPojoObject();
		if (obj.isNew() || isChanged(obj, MultiContentInfo.Attributes.parts.name())) {
			List<?> pojoContentList = (List<?>) getAttributeValue(pojoMultiContent, MultiContentInfo.Attributes.parts.name());
			for (Object pojoContent : pojoContentList) {
				ManagedObjectProxy pojoContentProxy = AbstractDAO.getManagedObjectProxy(pojoContent);
				String contentClassName = pojoContent.getClass().getSimpleName();
				if (contentClassName.equals(ContentClasses.MultiContent.name())) {
					MultiContentUpdater childContentUpdater = multiContentUpdater.addPart(MultiContentUpdater.class);
					DAOContext childContext = new DAOContext(childContentUpdater);
					EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
					childContext.setOperationContext(opContext);
					if (isNew) {
						MultiContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
					} else {
						MultiContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
					}
				} else if (contentClassName.equals(ContentClasses.SimpleContent.name())) {
					StreamedSimpleContentUpdater childContentUpdater = multiContentUpdater.addPart(StreamedSimpleContentUpdater.class);
					DAOContext childContext = new DAOContext(childContentUpdater);
					EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
					childContext.setOperationContext(opContext);
					if (isNew) {
						SimpleContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
					} else {
						SimpleContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
					}
				} else if (contentClassName.equals(ContentClasses.OnlineContent.name())) {
					OnlineContentUpdater childContentUpdater = multiContentUpdater.addPart(OnlineContentUpdater.class);
					DAOContext childContext = new DAOContext(childContentUpdater);
					EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
					childContext.setOperationContext(opContext);
					if (isNew) {
						OnlineContentDAO.getInstance().updateNewObjectState(pojoContentProxy, childContext);
					} else {
						OnlineContentDAO.getInstance().updateObjectState(pojoContentProxy, childContext);
					}
				} else if (contentClassName.equals(ContentClasses.UnifiedMessage.name())) {
					EmailMessageContentUpdater childContentUpdater = multiContentUpdater.addPart(EmailMessageContentUpdater.class);
					DAOContext childContext = new DAOContext(childContentUpdater);
					EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
					childContext.setOperationContext(opContext);
					if (isNew) {
						EmailMessageContentUtil.updateNewObjectState(pojoContentProxy, childContext);
					} else {
						EmailMessageContentUtil.updateObjectState(pojoContentProxy, childContext);
					}
				} else if (contentClassName.equals(ContentClasses.Document.name())) {
					StreamedSimpleContentUpdater childContentUpdater = multiContentUpdater.addPart(StreamedSimpleContentUpdater.class);
					DAOContext childContext = new DAOContext(childContentUpdater);
					EmailOperationContext opContext = (EmailOperationContext) context.getOperationContext();
					childContext.setOperationContext(opContext);
					if (isNew) {
						DocumentToSimpleContentDAO.getInstance().updateNewEmailAttachmentObjectState(pojoContentProxy, childContext);
					} else {
						DocumentToSimpleContentDAO.getInstance().updateEmailAttachmentObjectState(pojoContentProxy, childContext);
					}
				}
			}
		}
	}
	
	public void committedObject(Persistent pojoIdentifiable) {

	}
	
	public void rolledbackObject(Persistent pojoIdentifiable) {

	}

	public void flushToOutputStream(ManagedObjectProxy mop, EmailOperationContext opContext) {
		Persistent pojoContent = mop.getPojoObject();
		List<Persistent> subContentList = getMultiContentParts(pojoContent, MultiContentInfo.Attributes.parts.name());
		for (Persistent subContent : subContentList) {
			StreamDAO subDao = (StreamDAO) mop.getPersistenceContext().getDataAccessObject(subContent, pojoContent, MultiContentInfo.Attributes.parts.name());
			subDao.flushToOutputStream(subContent.getManagedObjectProxy(), opContext);
		}
	}
	
}
