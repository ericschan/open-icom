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

import icom.info.AbstractBeanInfo;
import icom.info.DocumentInfo;
import icom.info.EntityInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiAbstractDAO;
import icom.jpa.rt.PersistenceContext;
import oracle.csi.Content;
import oracle.csi.CsiRuntimeException;
import oracle.csi.EmailOperationContext;
import oracle.csi.RawString;
import oracle.csi.SimpleContent;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.StreamedSimpleContentUpdater;

public class DocumentToSimpleContentDAO extends CsiAbstractDAO implements StreamDAO {

	static DocumentToSimpleContentDAO singleton = new DocumentToSimpleContentDAO();
	
	public static DocumentToSimpleContentDAO getInstance() {
		return singleton;
	}
	
	protected DocumentToSimpleContentDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiEntity, Projection proj) {
		if (proj == Projection.BASIC) {
			proj = Projection.FULL;
		}
		try {
			Content csiChildContent = (Content) csiEntity;
			Persistent pojoIdentifiable = obj.getPojoObject();
			if (csiChildContent != null) {
				boolean createDependentProxy = true;
				Object pojoContent = getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
				if (pojoContent != null) {
					ManagedIdentifiableProxy contentObj = (ManagedIdentifiableProxy) getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
					if (contentObj != null) {
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
						createDependentProxy = false;
					}
				}
				if (createDependentProxy) {
					PersistenceContext context = obj.getPersistenceContext();
					ManagedObjectProxy contentObj = getNonIdentifiableDependentProxy(context, (SimpleContent) csiChildContent, obj, DocumentInfo.Attributes.content.name());
					if (csiChildContent instanceof SimpleContent) {
						contentObj.getProviderProxy().copyLoadedProjection(contentObj, csiChildContent, proj);
					}
					assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name(), contentObj.getPojoObject());
				}
			}
		} catch (CsiRuntimeException ex) {
			// ignore
		}
		return;
	}	

	public void updateNewOrOldEmailAttachmentObjectState(ManagedObjectProxy obj, DAOContext context) {
		Persistent pojoDocument = obj.getPojoObject();
		StreamedSimpleContentUpdater contentUpdater = (StreamedSimpleContentUpdater) context.getUpdater();
		Persistent pojoContent = getContent(pojoDocument);
		if (pojoContent != null) {
			String name = (String) getAttributeValue(pojoDocument, EntityInfo.Attributes.name.name());
			if (name != null) {
				RawString rawSubject = new RawString(name);
				contentUpdater.setName(rawSubject);
			} else {
				contentUpdater.setName(new RawString(""));
			}
			DAOContext childContext = new DAOContext(contentUpdater);
			SimpleContentDAO.getInstance().updateObjectState(pojoContent.getManagedObjectProxy(), childContext);
		}
	}
	
	public void updateNewEmailAttachmentObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldEmailAttachmentObjectState(obj, context);
	}
	
	public void updateEmailAttachmentObjectState(ManagedObjectProxy obj, DAOContext context) {
		updateNewOrOldEmailAttachmentObjectState(obj, context);
	}

	public Persistent getContent(Persistent pojoIdentifiable) {
		return (Persistent) getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
	}
	
	public void flushToOutputStream(ManagedObjectProxy obj, EmailOperationContext opContext) {
		Persistent pojoDocument = obj.getPojoObject();
		Persistent pojoContent = getContent(pojoDocument);
		if (pojoContent != null) {
			StreamDAO dao = (StreamDAO) obj.getPersistenceContext().getDataAccessObject(pojoContent, pojoDocument, DocumentInfo.Attributes.content.name());
			dao.flushToOutputStream(pojoContent.getManagedObjectProxy(), opContext);
		}
	}
	
}
