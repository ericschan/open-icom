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

import icom.ContentStreamTrait;
import icom.info.BeanHandler;
import icom.info.SimpleContentInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiProjectionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.UUID;

import javax.persistence.PersistenceException;

import oracle.csi.CollabId;
import oracle.csi.ContentEncodingType;
import oracle.csi.CsiRuntimeException;
import oracle.csi.Eid;
import oracle.csi.EmailOperationContext;
import oracle.csi.IdentifiableHandle;
import oracle.csi.IdentifiableSimpleContent;
import oracle.csi.IdentifiableSimpleContentHandle;
import oracle.csi.OperationContext;
import oracle.csi.SimpleContent;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.IdentifiableSimpleContentUpdater;
import oracle.csi.updaters.SimpleContentUpdater;
import oracle.csi.updaters.StreamedSimpleContentUpdater;

public class SimpleContentDAO extends ContentDAO {
	
	static SimpleContentDAO singleton = new SimpleContentDAO();
	
	public static SimpleContentDAO getInstance() {
		return singleton;
	}

	String directoryPath = null;
	
	protected SimpleContentDAO() {
	}

	public Class<? extends IdentifiableHandle> getIdentifiableHandleClass() {
		return IdentifiableSimpleContentHandle.class;
	}
	
	{
		fullAttributes.add(SimpleContentInfo.Attributes.contentLanguage);
		fullAttributes.add(SimpleContentInfo.Attributes.characterEncoding);
		fullAttributes.add(SimpleContentInfo.Attributes.contentEncoding);
		fullAttributes.add(SimpleContentInfo.Attributes.contentBody);
	}
	
	public SimpleContent loadObject(ManagedObjectProxy obj, Projection proj) {
		return null;
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object csiObject, Projection proj) {
		super.copyObjectState(obj, csiObject, proj);
		
		if (csiObject instanceof IdentifiableSimpleContent) {
			IdentifiableSimpleContent csiSimpleContent = (IdentifiableSimpleContent) csiObject;
			Object pojoObject = obj.getPojoObject();
			Projection lastLoadedProjection = Projection.EMPTY;
			if (obj instanceof ManagedIdentifiableProxy) {
				CsiProjectionManager projManager = (CsiProjectionManager) obj.getProviderProxy();
				lastLoadedProjection = projManager.getLastLoadedProjection(obj);
			}
			/*
			if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.name.name(), lastLoadedProjection, proj)) {
				try {
					String name = csiSimpleContent.getName().convertToString();
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
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentLanguage.name(), csiSimpleContent.getContentLanguage());
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.characterEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.characterEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.characterEncoding.name(), csiSimpleContent.getCharacterEncoding());
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.contentEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentEncoding.name(), csiSimpleContent.getContentEncoding().name());
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.data.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentBody.name(), proj)) {
				try {
					InputStream stream = csiSimpleContent.getContentStream();
					if (stream != null) {
						/*
						if (stream.markSupported()) {
							stream.mark(dataSize + 1);
							byte[] data = new byte[dataSize];
							stream.read(data, 0, dataSize);
							assignAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.data.name(), data);
							stream.reset();
						}
						*/
						ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
						assignAttributeValue(streamHelper, SimpleContentInfo.ContentStreamAttributes.inputStream.name(), stream);
						assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
					} else {
						ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
						// TODO
						assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
					}
				/*
				} catch (IOException ex) {
					// TODO
				*/
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
		} else {
			SimpleContent csiSimpleContent = (SimpleContent) csiObject;
			Object pojoObject = obj.getPojoObject();
			/*
			if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.name.name(), lastLoadedProjection, proj)) {
				try {
					String name = csiSimpleContent.getName().convertToString();
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
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentLanguage.name(), csiSimpleContent.getContentLanguage());
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.characterEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.characterEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.characterEncoding.name(), csiSimpleContent.getCharacterEncoding());
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.contentEncoding.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentEncoding.name(), proj)) {
				try {
					assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentEncoding.name(), csiSimpleContent.getContentEncoding().name());
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.data.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentBody.name(), proj)) {
				try {
					byte[] partIdentifier = csiSimpleContent.getPartIdentifier();
					assignAttributeValue(pojoObject, SimpleContentInfo.TransientAttributes.partIdentifier.name(), partIdentifier);
				} catch (CsiRuntimeException ex) {
					// ignore
				}
			}
			
			//if (isBetweenProjections((ManagedDependentProxy) obj, SimpleContentInfo.Attributes.data.name(), lastLoadedProjection, proj)) {
			if (isPartOfProjection(SimpleContentInfo.Attributes.contentBody.name(), proj)) {
				try {
					InputStream stream = csiSimpleContent.getInputStream();
					if (stream != null) {
						/*
						if (stream.markSupported()) {
							stream.mark(dataSize + 1);
							byte[] data = new byte[dataSize];
							stream.read(data, 0, dataSize);
							assignAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.data.name(), data);
							stream.reset();
						}
						*/
						ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
						assignAttributeValue(streamHelper, SimpleContentInfo.ContentStreamAttributes.inputStream.name(), stream);
						assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
					} else {
						ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
						byte[] data = csiSimpleContent.getContentBytes();
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						try {
							buffer.write(data);
						} catch (IOException ex) {
							String ex_msg = ex.toString();
							System.err.print(ex_msg);
							//throw new PersistenceException(ex);
						}
						assignAttributeValue(streamHelper, SimpleContentInfo.ContentStreamAttributes.inputStream.name(), buffer);
						assignAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
					}
				/*
				} catch (IOException ex) {
					// TODO
				*/
				} catch (CsiRuntimeException ex) {
					// ignore
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
			updater.setContentLanguage(contentLanguage);
		}
		String contentEncoding = (String) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentEncoding.name());
		if ((obj.isNew() && contentEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentEncoding.name())) {
			updater.setContentEncoding(ContentEncodingType.OTHER);  //TODO
		}
		/*
		Integer contentLength = (Integer) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentLength.name());
		if ((obj.isNew() && contentLength != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentLength.name())) {
			updater.setSize(contentLength);
		}
		String name = (String) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.name.name());
		if ((obj.isNew() && name != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.name.name())) {
			RawString rawName = new RawString(name);
			updater.setName(rawName);
		}
		*/
		if (obj.isNew() || isChanged(obj, SimpleContentInfo.Attributes.contentBody.name())) {
			byte[] streamId = UUID.randomUUID().toString().getBytes();
			updater.registerStream(streamId);
			obj.setStreamId(streamId);
		}
	}
	
	public void flushToOutputStream(ManagedObjectProxy obj, EmailOperationContext opContext) {
		Persistent pojoObject = obj.getPojoObject();
		if (obj.isNew() || isChanged(obj, SimpleContentInfo.Attributes.contentBody.name())) {
			if (opContext != null) {
				byte[] streamId = obj.getStreamId();
				OutputStream os = opContext.getOutputStream(streamId);
				try {
					ContentStreamTrait cos = (ContentStreamTrait) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name());
					InputStream fis = null;
					if (cos != null) {
						fis = cos.getFileInputStream();
					}
					if (fis != null) {
						byte[] data = new byte[ContentStreamTrait.dataSize];
						int num = 0;
						do {
							num = fis.read(data);
							if (num != -1) {
								os.write(data, 0, num);
							}
						} while (num != -1);
						fis.close();
					}
					/*
					else {
						byte[] data = (byte[]) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.data.name());
						if (data != null) {
							os.write(data);
						} else {
							os.write(new byte[0]);
						}
					}
					*/
					try {
						os.close();
					} catch (IOException ex) {
						String ex_msg = ex.toString();
						System.err.print(ex_msg);
					}
				} catch (IOException ex) {
					String ex_msg = ex.toString();
					System.err.print(ex_msg);
					//throw new PersistenceException(ex);
				}			
			}
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
			updater.setContentLanguage(contentLanguage);
		}
		String contentEncoding = (String) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.contentEncoding.name());
		if ((obj.isNew() && contentEncoding != null) 
				|| isChanged(obj, SimpleContentInfo.Attributes.contentEncoding.name())) {
			updater.setContentEncoding(ContentEncodingType.OTHER);  //TODO
		}
		updateIdentifiableSimpleContentState(obj, context);
	}
	
	private void updateIdentifiableSimpleContentState(ManagedObjectProxy obj, DAOContext context) {
		IdentifiableSimpleContentUpdater updater = (IdentifiableSimpleContentUpdater) context.getUpdater();
		Persistent pojoObject = obj.getPojoObject();
		
		if (obj.isNew() || isChanged(obj, SimpleContentInfo.Attributes.contentBody.name())) {	
			OperationContext docContext = (OperationContext) context.getOperationContext();
			if (docContext != null) {
				updater.setPiecewiseUpdate(false);
				CollabId cid = getCollabId(((ManagedIdentifiableProxy)obj).getObjectId());
				Eid id = cid.getEid();
				OutputStream os = docContext.getOutputStream(id);
				try {
					ContentStreamTrait cos = (ContentStreamTrait) getAttributeValue(pojoObject, SimpleContentInfo.Attributes.contentBody.name());
					InputStream fis = null;
					if (cos != null) {
						fis = cos.getFileInputStream();
					}
					if (fis != null) {
						byte[] data = new byte[ContentStreamTrait.dataSize];
						int num = 0;
						do {
							num = fis.read(data);
							if (num != -1) {
								os.write(data, 0, num);
							}
						} while (num != -1);
						fis.close();
					}
					/*
					else {
						byte[] data = (byte[]) getAttributeValue(pojoIdentifiable, SimpleContentInfo.Attributes.data.name());
						if (data != null) {
							os.write(data);
						} else {
							os.write(new byte[0]);
						}
					}
					*/
					os.close();
				} catch (IOException ex) {
					throw new PersistenceException(ex);
				}			
			}
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
			updater.setContentLanguage(contentLanguage);
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

}
