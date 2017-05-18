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

import icom.info.RelationshipBondableInfo;
import icom.info.VersionSeriesInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkDataAccessIdentifiableStateObject;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.persistence.PersistenceException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.httpclient.methods.PostMethod;

import com.oracle.beehive.Actor;
import com.oracle.beehive.Artifact;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.Document;
import com.oracle.beehive.DocumentCreator;
import com.oracle.beehive.DocumentUpdater;
import com.oracle.beehive.Entity;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.Version;
import com.oracle.beehive.VersionUpdater;
import com.oracle.beehive.WikiPage;

public class VersionSeriesToDocumentDAO extends EntityDAO implements RelationshipBondableInfo {

	static VersionSeriesToDocumentDAO singleton = new VersionSeriesToDocumentDAO();
	
	public static VersionSeriesToDocumentDAO getInstance() {
		return singleton;
	}
	
	String versionSeriesTypeMnemonic = "VSVS";
	
	{
		basicAttributes.add(VersionSeriesInfo.Attributes.totalSize);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckedOut);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckedOutBy);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckedOutOn);
		basicAttributes.add(VersionSeriesInfo.Attributes.versionSeriesCheckoutComment);
	}

	{
		fullAttributes.add(VersionSeriesInfo.Attributes.versionHistory);
		fullAttributes.add(VersionSeriesInfo.Attributes.versionableHistory);
		fullAttributes.add(VersionSeriesInfo.Attributes.representativeCopy);
		fullAttributes.add(VersionSeriesInfo.Attributes.latestVersionedCopy);
		fullAttributes.add(VersionSeriesInfo.Attributes.privateWorkingCopy);
		fullAttributes.add(VersionSeriesInfo.Attributes.latestVersion);
	}
	
	protected VersionSeriesToDocumentDAO() {
	}
	
	public String getResourceType() {
		return versionSeriesTypeMnemonic;
	}
	
	protected BdkDataAccessIdentifiableStateObject morphDataAccessStateObjectFromDocumentToVersionSeries(Object state) {
		return new BdkDataAccessIdentifiableStateObject((com.oracle.beehive.IdentifiableSnapshot) state, versionSeriesTypeMnemonic);
	}
	
	public Entity loadObjectState(ManagedIdentifiableProxy obj, Projection proj) {
		return null;
	}
	
	void copyRepresentativeCopy(Persistent pojoVersionSeries, Persistent pojoRepresentativeCopy) {
		assignAttributeValue(pojoVersionSeries, VersionSeriesInfo.Attributes.representativeCopy.name(), pojoRepresentativeCopy);
	}
	
	public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        //super.copyObjectState(obj, bdkEntity, proj);

        Document bdkDocument = null;
        WikiPage bdkWikiPage = null;

        if (bdkEntity instanceof Document) {
            bdkDocument = (Document)bdkEntity;
        } else {
            bdkWikiPage = (WikiPage)bdkEntity;
        }

        Persistent pojoPersistent = obj.getPojoObject();
        PersistenceContext context = obj.getPersistenceContext();

        if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckedOut.name(), proj)) {
            try {
                Boolean versionSeriesCheckedOut = null;
                if (bdkDocument != null) {
                    versionSeriesCheckedOut = bdkDocument.isCheckedOut();
                } else {
                    versionSeriesCheckedOut = bdkWikiPage.isCheckedOut();
                }
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.versionSeriesCheckedOut.name(),
                                     versionSeriesCheckedOut);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckedOutBy.name(), proj)) {
            try {
                Actor bdkVersionSeriesCheckedOutBy = null;
                if (bdkDocument != null) {
                    bdkVersionSeriesCheckedOutBy = bdkDocument.getCheckedOutBy();
                } else {
                    bdkVersionSeriesCheckedOutBy = bdkWikiPage.getCheckedOutBy();
                }
                if (bdkVersionSeriesCheckedOutBy != null) {
                    ManagedIdentifiableProxy actorObj = getEntityProxy(context, bdkVersionSeriesCheckedOutBy);
                    Persistent pojoVersionSeriesCheckedOutBy = actorObj.getPojoIdentifiable();
                    assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.versionSeriesCheckedOutBy.name(),
                                         pojoVersionSeriesCheckedOutBy);
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckedOutOn.name(), proj)) {
            try {
                try {
                    XMLGregorianCalendar bdkCheckedOutOn = null;
                    if (bdkDocument != null) {
                        bdkCheckedOutOn = bdkDocument.getCheckedOutOn();
                    } else {
                        bdkCheckedOutOn = bdkWikiPage.getCheckedOutOn();
                    }
                    if (bdkCheckedOutOn != null) {
                        Date versionSeriesCheckedOutOn = bdkCheckedOutOn.toGregorianCalendar().getTime();
                        assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.versionSeriesCheckedOutOn.name(),
                                             versionSeriesCheckedOutOn);
                    }
                } catch (Exception ex) {
                    // ignore
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.versionSeriesCheckoutComment.name(), proj)) {
            try {
                String versionSeriesCheckoutComment = null;
                if (bdkDocument != null) {
                    versionSeriesCheckoutComment = bdkDocument.getCheckoutComments();
                } else {
                    versionSeriesCheckoutComment = bdkWikiPage.getCheckoutComments();
                }
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.versionSeriesCheckoutComment.name(),
                                     versionSeriesCheckoutComment);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.totalSize.name(), proj)) {
            try {
                Long totalSize = null;
                if (bdkDocument != null) {
                    totalSize = bdkDocument.getTotalSize();
                } else {
                    totalSize = bdkWikiPage.getTotalSize();
                }     
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.totalSize.name(), totalSize);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.versionHistory.name(), proj)) {
            try {
                Collection<Version> bdkVersionHistory = null;
                if (bdkDocument != null) {
                    bdkVersionHistory = bdkDocument.getVersionHistories();
                } else {
                    bdkVersionHistory = bdkWikiPage.getVersionHistories();
                }
                Vector<Persistent> v = new Vector<Persistent>(bdkVersionHistory.size());
                if (bdkVersionHistory != null) {
                    for (Version bdkVersion : bdkVersionHistory) {
                        ManagedIdentifiableProxy childObj = getEntityProxy(context, bdkVersion);
                        v.add(childObj.getPojoIdentifiable());
                    }
                }
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.versionHistory.name(), v);
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.latestVersion.name(), v.lastElement());
            } catch (Exception ex) {
                // ignore
            }
        }

        Object bdkLatestVersionableInHistory = null;
        if (isPartOfProjection(VersionSeriesInfo.Attributes.versionableHistory.name(), proj)) {
            try {
                Collection<Object> bdkVersionableHistory = null;
                if (bdkDocument != null) {
                    bdkVersionableHistory = bdkDocument.getVersionableHistories();
                } else {
                    bdkVersionableHistory = bdkWikiPage.getVersionableHistories();
                }
                Vector<Persistent> pojoVersionableHistory = new Vector<Persistent>(bdkVersionableHistory.size());
                if (bdkVersionableHistory != null) {
                    for (Object bdkVersionable : bdkVersionableHistory) {
                        ManagedIdentifiableProxy childObj = getEntityProxy(context, bdkVersionable);
                        pojoVersionableHistory.add(childObj.getPojoIdentifiable());
                        bdkLatestVersionableInHistory = bdkVersionable;
                    }
                }
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.versionableHistory.name(),
                                     pojoVersionableHistory);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.latestVersionedCopy.name(), proj)) {
            try {
                Persistent pojoLatestVersionedCopy = null;
                Object bdkRepresentativeVersionable = null;
                if (bdkDocument != null) {
                    bdkRepresentativeVersionable = bdkDocument.getRepresentativeVersionable();
                } else {
                    bdkRepresentativeVersionable = bdkWikiPage.getRepresentativeVersionable();
                }
                if (bdkRepresentativeVersionable != null) {
                    if (bdkLatestVersionableInHistory != null) {
                        String bdkRepresentativeVersionableId = null;
                        String bdkLatestVersionableInHistoryId = null;
                        if (bdkRepresentativeVersionable instanceof Document
                         || bdkRepresentativeVersionable instanceof WikiPage) {
                            bdkRepresentativeVersionableId = ((Artifact)bdkRepresentativeVersionable).getCollabId().getId();
                        }
                        if (bdkLatestVersionableInHistory instanceof Document
                         || bdkLatestVersionableInHistory instanceof WikiPage) {
                            bdkLatestVersionableInHistoryId = ((Artifact)bdkLatestVersionableInHistory).getCollabId().getId();
                        }
                        if (bdkRepresentativeVersionableId.equals(bdkLatestVersionableInHistoryId)) {
                            ManagedIdentifiableProxy childObj = getEntityProxy(context, bdkRepresentativeVersionable);
                            pojoLatestVersionedCopy = childObj.getPojoIdentifiable();
                        } else {
                            ManagedIdentifiableProxy childObj = getEntityProxy(context, bdkLatestVersionableInHistory);
                            pojoLatestVersionedCopy = childObj.getPojoIdentifiable();
                        }
                    }
                }
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.latestVersionedCopy.name(),
                                     pojoLatestVersionedCopy);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.privateWorkingCopy.name(), proj)) {
            try {
                Persistent pojoPrivateWorkingCopy = null;
                Object bdkRepresentativeVersionable = null;
                if (bdkDocument != null) {
                    bdkRepresentativeVersionable = bdkDocument.getRepresentativeVersionable();
                } else {
                    bdkRepresentativeVersionable = bdkWikiPage.getRepresentativeVersionable();
                }
                if (bdkRepresentativeVersionable != null) {
                    if (bdkLatestVersionableInHistory != null) {
                        String bdkRepresentativeVersionableId = null;
                        String bdkLatestVersionableInHistoryId = null;
                        if (bdkRepresentativeVersionable instanceof Document
                         || bdkRepresentativeVersionable instanceof WikiPage) {
                            bdkRepresentativeVersionableId = ((Artifact)bdkRepresentativeVersionable).getCollabId().getId();
                        }
                        if (bdkLatestVersionableInHistory instanceof Document
                         || bdkLatestVersionableInHistory instanceof WikiPage) {
                            bdkLatestVersionableInHistoryId = ((Artifact)bdkLatestVersionableInHistory).getCollabId().getId();
                        }
                        if (!bdkRepresentativeVersionableId.equals(bdkLatestVersionableInHistoryId)) {
                            ManagedIdentifiableProxy childObj = getEntityProxy(context, bdkRepresentativeVersionable);
                            pojoPrivateWorkingCopy = childObj.getPojoIdentifiable();
                        }
                    }
                }
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.privateWorkingCopy.name(),
                                     pojoPrivateWorkingCopy);
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isPartOfProjection(VersionSeriesInfo.Attributes.representativeCopy.name(), proj)) {
            try {
                Persistent pojoRepresentativeCopy = null;
                Object bdkFamilyVersionable = null;
                if (bdkDocument != null) {
                    bdkFamilyVersionable = bdkDocument.getParentFamily();
                } else {
                    bdkFamilyVersionable = bdkWikiPage.getParentFamily();
                }
                if (bdkFamilyVersionable != null) {
                    ManagedIdentifiableProxy childObj = getEntityProxy(context, bdkFamilyVersionable);
                    pojoRepresentativeCopy = childObj.getPojoIdentifiable();
                }
                assignAttributeValue(pojoPersistent, VersionSeriesInfo.Attributes.representativeCopy.name(),
                                     pojoRepresentativeCopy);
            } catch (Exception ex) {
                // ignore
            }
        }

    }
	
	private void updateNewOrOldObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		
	}

	public DAOContext beginUpdateObject(ManagedIdentifiableProxy obj) {
		return null;
	}
	
	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
	}
	
	public Entity concludeUpdateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		return null;
	}
	
	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		updateNewOrOldObjectState(obj, context);
		
	}
	
	public Entity concludeCreateObject(ManagedIdentifiableProxy obj, DAOContext context, Projection proj) {
		return null;
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		
	}
	
	public void cancelCheckout(ManagedIdentifiableProxy representativeCopyOfVersionableObj) {
		PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
		try {
			String resource = "adoc/checkout/cancel";
			BeeId representativeCopyOfVersionableId = getBeeId(representativeCopyOfVersionableObj.getObjectId().toString());
			String collabId = representativeCopyOfVersionableId.getId();
			String params = "snapshotid=";
			String sid = representativeCopyOfVersionableObj.getChangeToken().toString();
			params += sid;
			BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
			Projection proj = Projection.FULL;
			PostMethod postMethod = preparePostMethod(resource, collabId, userContext.antiCSRF, proj, params);
			Entity bdkDocumentFamily = (Entity) bdkHttpUtil.execute(getBdkClass(representativeCopyOfVersionableObj), postMethod, userContext.httpClient);
         // version series is updated through representativeCopyOfVersionableObj
            representativeCopyOfVersionableObj.checkReadyAndSetPooled();
            representativeCopyOfVersionableObj.getProviderProxy().copyLoadedProjection(representativeCopyOfVersionableObj, bdkDocumentFamily, proj);
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
	}
	
	// it returns bdk document family
	public Entity checkoutPrivate(ManagedIdentifiableProxy representativeCopyOfVersionableObj, String checkoutComments) {
        PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
        try {
            String resource = "adoc/checkout";
            BeeId representativeCopyOfVersionableId = getBeeId(representativeCopyOfVersionableObj.getObjectId().toString());
            String collabId = representativeCopyOfVersionableId.getId();
            String params = "checkout_comments=";
            params += URLEncoder.encode(checkoutComments, "UTF-8");
            Object changeToken = representativeCopyOfVersionableObj.getChangeToken();
            if (changeToken != null) {
                params += "&snapshotid=";
                String sid = changeToken.toString();
                params += sid;
            }
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            Projection proj = Projection.FULL;
            PostMethod postMethod = preparePostMethod(resource, collabId, userContext.antiCSRF, proj, params);
            Entity bdkDocumentFamily = (Entity) bdkHttpUtil.execute(getBdkClass(representativeCopyOfVersionableObj), postMethod, userContext.httpClient);
            ManagedIdentifiableProxy versionableObj = VersionDAO.getInstance().getEntityProxy(context, bdkDocumentFamily);
            assert(versionableObj == representativeCopyOfVersionableObj);
         // version series is updated through representativeCopyOfVersionableObj
            representativeCopyOfVersionableObj.checkReadyAndSetPooled();
            representativeCopyOfVersionableObj.getProviderProxy().copyLoadedProjection(representativeCopyOfVersionableObj, bdkDocumentFamily, proj);
            return bdkDocumentFamily;
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
	
	// it returns a version node
	public Persistent checkoutGetPojoVersionNode(ManagedIdentifiableProxy representativeCopyOfVersionableObj, String checkoutComments) {
	    PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
        Document bdkDocumentFamily = (Document) checkoutPrivate(representativeCopyOfVersionableObj, checkoutComments);
        Entity version = bdkDocumentFamily.getRepresentativeVersion();
        ManagedIdentifiableProxy versionObj = VersionDAO.getInstance().getEntityProxy(context, version);
        versionObj.checkReadyAndSetPooled();
        return versionObj.getPojoIdentifiable();
	}

	// this method is indirectly called via VersionDAO
    // it returns bdk document family
    private Entity checkinPrivate(ManagedIdentifiableProxy representativeCopyOfVersionableObj, String versionName, VersionUpdater versionUpdater) {
        PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
        try {
            String resource = "adoc/checkin";
            BeeId representativeCopyOfVersionableId = getBeeId(representativeCopyOfVersionableObj.getObjectId().toString());
            String collabId = representativeCopyOfVersionableId.getId();
            String params = "version_name=";
            params += URLEncoder.encode(versionName, "UTF-8");
            params += "&snapshotid=";
            String sid = representativeCopyOfVersionableObj.getChangeToken().toString();
            params += sid;
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            Projection proj = Projection.FULL;
            PostMethod postMethod = preparePostMethod(resource, collabId, userContext.antiCSRF, proj, params);
            Entity bdkDocumentFamily = (Entity) bdkHttpUtil.execute(getBdkClass(representativeCopyOfVersionableObj), postMethod, versionUpdater, userContext.httpClient);
            ManagedIdentifiableProxy versionableObj = VersionDAO.getInstance().getEntityProxy(context, bdkDocumentFamily);
            assert(versionableObj == representativeCopyOfVersionableObj);
            // version series is updated through representativeCopyOfVersionableObj
            representativeCopyOfVersionableObj.checkReadyAndSetPooled();
            representativeCopyOfVersionableObj.getProviderProxy().copyLoadedProjection(representativeCopyOfVersionableObj, bdkDocumentFamily, proj);
            return bdkDocumentFamily;
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
	
	// this method is indirectly called via VersionDAO
	// it returns a version node
	public Persistent checkinGetPojoVersionNode(ManagedIdentifiableProxy representativeCopyOfVersionableObj, String versionName, VersionUpdater versionUpdater) {
	    PersistenceContext context = representativeCopyOfVersionableObj.getPersistenceContext();
	    Document bdkDocumentFamily = (Document) checkinPrivate(representativeCopyOfVersionableObj, versionName, versionUpdater);
        Entity version = bdkDocumentFamily.getRepresentativeVersion();
        ManagedIdentifiableProxy versionObj = VersionDAO.getInstance().getEntityProxy(context, version);
        versionObj.checkReadyAndSetPooled();
        return versionObj.getPojoIdentifiable();
	}
	
	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return Document.class;
	}
	
	protected DocumentUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new DocumentUpdater();
	}
	
	protected DocumentUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		DocumentUpdater updater = getBdkUpdater(obj);
		((DocumentCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected DocumentCreator getBdkCreator(ManagedObjectProxy obj) {
		return new DocumentCreator();
	}
	
}
