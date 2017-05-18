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

import icom.ContentStreamTrait;
import icom.info.BeanHandler;
import icom.info.SimpleContentInfo;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.BdkHttpUtil;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.ContentEncodingType;
import com.oracle.beehive.IdentifiableSimpleContent;
import com.oracle.beehive.IdentifiableSimpleContentUpdater;
import com.oracle.beehive.SimpleContent;
import com.oracle.beehive.SimpleContentUpdater;
import com.oracle.beehive.StreamedSimpleContentUpdater;

public class SimpleContentDAO extends ContentDAO {
	
	static SimpleContentDAO singleton = new SimpleContentDAO();
	
	public static SimpleContentDAO getInstance() {
		return singleton;
	}

	String directoryPath = null;
	
	UUID uuid = UUID.randomUUID();
	
	protected SimpleContentDAO() {
	}

	public String getResourceType() {
		return "smct";
	}
	
	{
		basicAttributes.add(SimpleContentInfo.Attributes.contentLanguage);
		basicAttributes.add(SimpleContentInfo.Attributes.characterEncoding);
		basicAttributes.add(SimpleContentInfo.Attributes.contentEncoding);
		fullAttributes.add(SimpleContentInfo.Attributes.contentBody);
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object bdkObject, Projection proj) {
		super.copyObjectState(obj, bdkObject, proj);
		
		if (bdkObject instanceof IdentifiableSimpleContent) {
			IdentifiableSimpleContent bdkSimpleContent = (IdentifiableSimpleContent) bdkObject;
			Object pojoObject = obj.getPojoObject();
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.contentLanguage.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentLanguage.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentLanguage.name(), bdkSimpleContent.getContentLanguage());
				} catch (Exception ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.characterEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.characterEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.characterEncoding.name(), bdkSimpleContent.getCharacterEncoding());
				} catch (Exception ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.contentEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentEncoding.name(), bdkSimpleContent.getContentEncoding().name());
				} catch (Exception ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.data.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentBody.name(), proj)) {
				try {
					ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
					OutputStream outputStream = streamHelper.getFileOutputStream();
					PersistenceContext context = obj.getPersistenceContext();
					BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
					String resource = "adoc/content";
					ManagedIdentifiableProxy parentObj = (ManagedIdentifiableProxy) ((ManagedDependentProxy)obj).getParent();
					String collabId = parentObj.getObjectId().toString();
					GetMethod method = prepareGetMethod(resource, collabId);
					try {
						bdkHttpUtil.execute(outputStream, method, userContext.httpClient);
					} catch (Exception ex) {
						// ignore
					}
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
				} catch (Exception ex) {
					// ignore
				}
			}
		} else {
			SimpleContent bdkSimpleContent = (SimpleContent) bdkObject;
			Object pojoObject = obj.getPojoObject();
			/*
			if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.name.name(), lastLoadedProjection, proj)) {
				try {
					String name = bdkSimpleContent.getName().convertToString();
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.name.name(), name);
				} catch (CsiRuntimeException ex) {
					// ignore
				} catch (UnsupportedEncodingException ex) {
					// TODO
				}
			}
			*/
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.contentLanguage.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentLanguage.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentLanguage.name(), bdkSimpleContent.getContentLanguage());
				} catch (Exception ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.characterEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.characterEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.characterEncoding.name(), bdkSimpleContent.getCharacterEncoding());
				} catch (Exception ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.contentEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentEncoding.name(), bdkSimpleContent.getContentEncoding().name());
				} catch (Exception ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.data.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentBody.name(), proj)) {
				try {
                                    String partIdentifierString = bdkSimpleContent.getPartIdentifier();
                                    if (partIdentifierString != null) {
                                        byte[] partIdentifier = partIdentifierString.getBytes();
                                        assignAttributeValue(pojoObject, SimpleContentInfo.TransientAttributes.partIdentifier.name(), partIdentifier);
					byte[] contentBytes = bdkSimpleContent.getContentBytes();
					if (contentBytes == null) {
						PersistenceContext context = obj.getPersistenceContext();
						BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
						String resourceType = EmailMessageDAO.getInstance().getResourceType();
						resourceType += "/content";
						ManagedIdentifiableProxy messageObj = getManagedEntityProxy((ManagedDependentProxy) obj);
						BeeId id = getBeeId(messageObj.getObjectId().toString());
						String partIdentifierStr = new String(partIdentifier);
						partIdentifierStr = URLEncoder.encode(partIdentifierStr);
						String params = "partid=" + partIdentifierStr;  // TODO
						GetMethod method = BdkAbstractDAO.prepareGetMethod(resourceType, id.getId(), null, params);
						ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
						OutputStream outputStream = streamHelper.getFileOutputStream();
						BdkHttpUtil.getInstance().execute(outputStream, method, userContext.httpClient);
						outputStream.flush();
						outputStream.close();
						assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
                                        } else {
                                            ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
                                            OutputStream outputStream = streamHelper.getFileOutputStream();
                                            outputStream.write(contentBytes);
                                            outputStream.flush();
                                            outputStream.close();
                                            assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
                                        }
                                    } else { // if partIdentifierString is null, we assume that the artifact is a wiki page
                                        assignAttributeValue(pojoObject, SimpleContentInfo.TransientAttributes.partIdentifier.name(), null);
                                    }
				} catch (Exception ex) {
					throw new PersistenceException(ex);
				}
			}
		}
		
	}
	
	private void updateNewOrOldObjectState(ManagedObjectProxy obj, DAOContext context) {
		if (context.getUpdater() instanceof IdentifiableSimpleContentUpdater) {
			updateIdentifiableObjectState(obj, context);
		} else {
			updateNonIdentifiableObjectState(obj, context);
		}
	}
	
	public void updateObjectState(ManagedObjectProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	public void updateNewObjectState(ManagedObjectProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		updateNewOrOldObjectState(obj, context);
	}
	
	private void uploadContentBody(ManagedObjectProxy obj, DAOContext context) {
		try {
			String resource = "session/upload";
			BdkUserContextImpl userContext = (BdkUserContextImpl) obj.getPersistenceContext().getUserContext();
			String antiCSRF = userContext.antiCSRF;
			String uploadScopeId = context.getUploadScopeId();
			String uploadContentStreamId = generateUUID();
			context.setUploadContentStreamId(uploadContentStreamId);
			String params = "uploadscope=scope" + uploadScopeId + "&content_id=" + uploadContentStreamId;
			PostMethod uploadContentMethod = preparePostMethod(resource, antiCSRF, params);
			Persistent pojoIdentifiable = obj.getPojoObject();
			ContentStreamTrait cos = (ContentStreamTrait) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.contentBody.name());
			if (cos != null) {
				InputStream fis = cos.getFileInputStream();
				if (fis != null) {
					RequestEntity simpleText = new InputStreamRequestEntity(fis);
					uploadContentMethod.setRequestEntity(simpleText);
				}
			}
			BdkHttpUtil util = BdkHttpUtil.getInstance();
			util.upLoadContent(uploadContentMethod, userContext.httpClient);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	private void updateNonIdentifiableObjectState(ManagedObjectProxy obj, DAOContext context) {
		StreamedSimpleContentUpdater updater = (StreamedSimpleContentUpdater) context.getUpdater();
		Persistent pojoObject = obj.getPojoObject();
		
		String characterEncoding = (String) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.characterEncoding.name());
		if ((obj.isNew() && characterEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.characterEncoding.name())) {
			updater.setCharacterEncoding(characterEncoding);
		}
		Locale contentLanguage = (Locale) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentLanguage.name());
		if ((obj.isNew() && contentLanguage != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentLanguage.name())) {
			updater.setContentLanguage(contentLanguage.getDisplayName());
		}
		String contentEncoding = (String) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentEncoding.name());
		if ((obj.isNew() && contentEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentEncoding.name())) {
			updater.setContentEncoding(ContentEncodingType.OTHER);  //TODO
		}
		if (obj.isNew() || isChanged(obj, SimpleContentInfo.Attributes.contentBody.name())) {
			uploadContentBody(obj, context);
			updater.setContentStreamId(context.getUploadContentStreamId());
		}
	}
	
	private void updateIdentifiableObjectState(ManagedObjectProxy obj, DAOContext context) {
		IdentifiableSimpleContentUpdater updater = (IdentifiableSimpleContentUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoObject();
		
		String characterEncoding = (String) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.characterEncoding.name());
		if ((obj.isNew() && characterEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.characterEncoding.name())) {
			updater.setCharacterEncoding(characterEncoding);
		}
		Locale contentLanguage = (Locale) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.contentLanguage.name());
		if ((obj.isNew() && contentLanguage != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentLanguage.name())) {
			updater.setContentLanguage(contentLanguage.getDisplayName());
		}
		String contentEncoding = (String) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.contentEncoding.name());
		if ((obj.isNew() && contentEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentEncoding.name())) {
			updater.setContentEncoding(ContentEncodingType.OTHER);  //TODO
		}
		if (obj.isNew() || isChanged(obj, SimpleContentInfo.Attributes.contentBody.name())) {
			uploadContentBody(obj, context);
		}
	}
	
	void updateObjectStateForDiscussionMessage(ManagedObjectProxy obj, DAOContext context) {
		SimpleContentUpdater updater = (SimpleContentUpdater) context.getUpdater();
		Persistent pojoIdentifiable = obj.getPojoObject();
		
		String characterEncoding = (String) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.characterEncoding.name());
		if ((obj.isNew() && characterEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.characterEncoding.name())) {
			updater.setCharacterEncoding(characterEncoding);
		}
		Locale contentLanguage = (Locale) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.contentLanguage.name());
		if ((obj.isNew() && contentLanguage != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentLanguage.name())) {
			updater.setContentLanguage(contentLanguage.getDisplayName());
		}
		String contentEncoding = (String) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.contentEncoding.name());
		if ((obj.isNew() && contentEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentEncoding.name())) {
			//updater.setContentEncoding(ContentEncodingType.EIGHT_BIT);  //TODO
		}
		if (obj.isNew() || isChanged(obj, SimpleContentInfo.Attributes.contentBody.name())) {	
			updater.setPiecewiseUpdate(false);
			try {
				ContentStreamTrait cos = (ContentStreamTrait) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.contentBody.name());
				InputStream fis = null;
				if (cos != null) {
					fis = cos.getFileInputStream();
				}
				if (fis != null) {
					try {
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						byte[] data = new byte[ContentStreamTrait.dataSize];
						int num = 0;
						do {
							num = fis.read(data);
							if (num != -1) {
								buffer.write(data, 0, num);
							}
						} while (num != -1);
						byte[] alldata = buffer.toByteArray();
						updater.setContentBytes(alldata);
					} finally {
						fis.close();
					}
				}
			} catch (IOException ex) {
				throw new PersistenceException(ex);
			}
		}
	}
	
	public void committedObject(Persistent pojoIdentifiable) {
		
	}
	
	public void rolledbackObject(Persistent pojoIdentifiable) {
		
	}
	
	public boolean isCacheable() {
		return false;
	}
	
	ManagedIdentifiableProxy getManagedEntityProxy(ManagedDependentProxy dependent) {
		ManagedObjectProxy obj = dependent.getParent();
		if (obj instanceof ManagedDependentProxy) {
			return getManagedEntityProxy((ManagedDependentProxy) obj);
		} else if (obj instanceof ManagedIdentifiableProxy) {
			return (ManagedIdentifiableProxy) obj;
		} else {
			return null;
		}
	}

}
