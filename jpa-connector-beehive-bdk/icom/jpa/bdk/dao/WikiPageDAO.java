package icom.jpa.bdk.dao;


import icom.ContentStreamTrait;
import icom.info.AbstractBeanInfo;
import icom.info.ArtifactInfo;
import icom.info.BeanHandler;
import icom.info.DocumentInfo;
import icom.info.IcomBeanEnumeration;
import icom.info.SimpleContentInfo;
import icom.info.WikiPageInfo;
import icom.info.beehive.BeehiveWikiPageInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.BdkHttpUtil;
import icom.jpa.bdk.BdkProjectionManager;
import icom.jpa.bdk.BdkUserContextImpl;
import icom.jpa.bdk.Projection;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.rt.PersistenceContext;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.persistence.PersistenceException;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.Content;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.IdentifiableSimpleContent;
import com.oracle.beehive.SimpleContent;
import com.oracle.beehive.Version;
import com.oracle.beehive.WikiPage;
import com.oracle.beehive.WikiPageCreator;
import com.oracle.beehive.WikiPageUpdater;
import com.oracle.beehive.WikiRenderContext;


public class WikiPageDAO extends ArtifactDAO {
    
    static WikiPageDAO singleton = new WikiPageDAO();
    
    public static WikiPageDAO getInstance() {
        return singleton;
    }

    {
        basicAttributes.add(ArtifactInfo.Attributes.description);
        basicAttributes.add(DocumentInfo.Attributes.size);
        basicAttributes.add(DocumentInfo.Attributes.versionType);
        basicAttributes.add(WikiPageInfo.Attributes.contentDescriptor);
    }

    {
        fullAttributes.add(DocumentInfo.Attributes.versionControlMetadata);
    }

    {
        lazyAttributes.add(DocumentInfo.Attributes.content);
        lazyAttributes.add(WikiPageInfo.Attributes.renderedPage);
        lazyAttributes.add(BeehiveWikiPageInfo.Attributes.renderedData);
    }
    
    protected WikiPageDAO() {
    }

    public String getResourceType() {
        return "wiki";
    }

    public void copyObjectState(ManagedObjectProxy obj, Object bdkEntity, Projection proj) {
        super.copyObjectState(obj, bdkEntity, proj);

        WikiPage bdkWikiPage = (WikiPage)bdkEntity;
        Persistent pojoIdentifiable = obj.getPojoObject();
        PersistenceContext context = obj.getPersistenceContext();
        BdkProjectionManager projManager = (BdkProjectionManager)obj.getProviderProxy();
        Projection lastLoadedProjection = projManager.getLastLoadedProjection(obj);

        if (isBetweenProjections(ArtifactInfo.Attributes.description.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, ArtifactInfo.Attributes.description.name(), bdkWikiPage.getDescription());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(DocumentInfo.Attributes.size.name(), lastLoadedProjection, proj)) {
            try {
                assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.size.name(), bdkWikiPage.getTotalSize());
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(DocumentInfo.Attributes.versionType.name(), lastLoadedProjection, proj)) {
            try {
                boolean isVersion = bdkWikiPage.isVersion();
                boolean isRepresentativeCopy = bdkWikiPage.isFamily();
                boolean isPrivateWorkingCopy = bdkWikiPage.isWorkingCopy();
                boolean isVersionedCopy = isVersion && !isPrivateWorkingCopy;
                boolean isVersionControlled = bdkWikiPage.isVersionControlled();

                if (isRepresentativeCopy && isVersionControlled) {
                    assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(),
                                       IcomBeanEnumeration.VersionTypeEnum.name(), DocumentDAO.VersionType.RepresentativeCopy.name());
                } else if (isPrivateWorkingCopy) {
                    assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(),
                                       IcomBeanEnumeration.VersionTypeEnum.name(), DocumentDAO.VersionType.PrivateWorkingCopy.name());
                } else if (isVersionedCopy) {
                    assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(),
                                       IcomBeanEnumeration.VersionTypeEnum.name(), DocumentDAO.VersionType.VersionedCopy.name());
                } else {
                    assignEnumConstant(pojoIdentifiable, DocumentInfo.Attributes.versionType.name(), BeanHandler.getBeanPackageName(),
                                       IcomBeanEnumeration.VersionTypeEnum.name(), DocumentDAO.VersionType.NonVersionControlledCopy.name());
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        if (isBetweenProjections(DocumentInfo.Attributes.versionControlMetadata.name(), lastLoadedProjection, proj)) {
            try {
                boolean isFamily = bdkWikiPage.isFamily();
                if (isFamily) {
                    WikiPage bdkParentFamilyDocument = bdkWikiPage;
                    ManagedIdentifiableProxy versionSeriesObj =
                        getIdentifiableDependentProxyOfVersionSeries(context, bdkParentFamilyDocument, obj,
                                                                     DocumentInfo.Attributes.versionControlMetadata.name());
                    versionSeriesObj.getProviderProxy().copyLoadedProjection(versionSeriesObj, bdkParentFamilyDocument, proj);
                    Persistent pojoVersionSeries = versionSeriesObj.getPojoIdentifiable();
                    Persistent pojoRepresentativeVersionable = (Persistent)pojoIdentifiable;
                    assignAttributeValue(pojoRepresentativeVersionable, DocumentInfo.Attributes.versionControlMetadata.name(),
                                         pojoVersionSeries);
                    VersionSeriesToDocumentDAO.getInstance().copyRepresentativeCopy(pojoVersionSeries, pojoRepresentativeVersionable);
                } else {
                    Version bdkVersion = bdkWikiPage.getParentVersion();
                    if (bdkVersion != null) {
                        ManagedIdentifiableProxy versionObj = getEntityProxy(context, bdkVersion);
                        versionObj.getProviderProxy().copyLoadedProjection(versionObj, bdkVersion, Projection.EMPTY);
                        Persistent pojoVersion = versionObj.getPojoIdentifiable();
                        assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.versionControlMetadata.name(), pojoVersion);
                        Object bdkParentFamilyDocument = bdkWikiPage.getParentFamily();
                        ManagedIdentifiableProxy representativeCopyObj = getEntityProxy(context, bdkParentFamilyDocument);
                        if (proj != Projection.EMPTY) {
                            representativeCopyObj.getProviderProxy().copyLoadedProjection(representativeCopyObj, bdkParentFamilyDocument,
                                                                                          proj);
                        }
                        Persistent pojoRepresentativeCopy = representativeCopyObj.getPojoIdentifiable();
                        Persistent pojoVersionedCopy = (Persistent)pojoIdentifiable;
                        VersionDAO.getInstance().copyRepresentativeAndVersionedCopies(pojoVersion, pojoRepresentativeCopy,
                                                                                      pojoVersionedCopy);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if ( /* always copy content  !isPartOfProjection(DocumentInfo.Attributes.content.name(), lastLoadedProjection) && */
            isPartOfProjection(WikiPageInfo.Attributes.contentDescriptor.name(), proj)) {
            try {
                Content bdkChildContent = bdkWikiPage.getContent();
                if (bdkChildContent != null) {
                    boolean createDependentProxy = true;
                    Object pojoContent = getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
                    if (pojoContent != null) {
                        ManagedObjectProxy contentObj =
                            (ManagedObjectProxy)getAttributeValue(pojoContent, AbstractBeanInfo.Attributes.mop.name());
                        if (contentObj != null) {
                            contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
                            createDependentProxy = false;
                        }
                    }
                    if (createDependentProxy) {
                        ManagedObjectProxy contentObj =
                            getNonIdentifiableDependentProxy(context, bdkChildContent, obj, DocumentInfo.Attributes.content.name());
                        // TODO how to use identifiable dependent proxy when contents of document versions share the same id
                        // context.getNonIdentifiableDependentProxy((IdentifiableSimpleContent) bdkChildContent, obj, DocumentInfo.Attributes.content.name());
                        if (bdkChildContent instanceof IdentifiableSimpleContent) {
                            contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
                        } else if (bdkChildContent instanceof SimpleContent) {
                            contentObj.getProviderProxy().copyLoadedProjection(contentObj, bdkChildContent, proj);
                        }
                        assignAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name(), contentObj.getPojoObject());
                    }
                }
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    ManagedIdentifiableProxy getIdentifiableDependentProxyOfVersionSeries(PersistenceContext context, Object bdkParentFamilyDocument,
                    ManagedObjectProxy parent, String parentAttributeName) {
            DataAccessStateObject dataAccessStateObject = VersionSeriesToDocumentDAO.getInstance().morphDataAccessStateObjectFromDocumentToVersionSeries(bdkParentFamilyDocument);
            return context.getIdentifiableDependentProxy(IcomBeanEnumeration.VersionSeries.name(), dataAccessStateObject, parent, parentAttributeName);
    }
    
    protected boolean isLoadBasicWithLazy(String attributeName) {
        if (DocumentInfo.Attributes.content.name().equals(attributeName)
         || BeehiveWikiPageInfo.Attributes.renderedData.name().equals(attributeName)
         || WikiPageInfo.Attributes.renderedPage.name().equals(attributeName)) {
            return true;
        } else {
            return super.isLoadBasicWithLazy(attributeName);
        }
    }
    
    public void loadAndCopyObjectState(ManagedIdentifiableProxy obj, String attributeName, Object key) {
        super.loadAndCopyObjectState(obj, attributeName, key);

        if (DocumentInfo.Attributes.content.name().equals(attributeName)) {
            loadContentBody(obj);
        }
        if (BeehiveWikiPageInfo.Attributes.renderedData.name().equals(attributeName)) {
            loadRenderedData(obj);
        }
        if (WikiPageInfo.Attributes.renderedPage.name().equals(attributeName)) {
            loadRenderedPage(obj);
        }
    }
    
    private void loadContentBody(ManagedIdentifiableProxy wikiPageObj) {
        Persistent pojoIdentifiable = wikiPageObj.getPojoIdentifiable();
        try {
            PersistenceContext context = wikiPageObj.getPersistenceContext();
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            String resourceType = WikiPageDAO.getInstance().getResourceType();
            resourceType += "/content";
            BeeId id = getBeeId(wikiPageObj.getObjectId().toString());
            GetMethod method = BdkAbstractDAO.prepareGetMethod(resourceType, id.getId(), null, null);
            ContentStreamTrait streamHelper = (ContentStreamTrait) BeanHandler.instantiatePojoObject(SimpleContentInfo.ContentStreamClassName);
            OutputStream outputStream = streamHelper.getFileOutputStream();
            BdkHttpUtil.getInstance().execute(outputStream, method, userContext.httpClient);
            outputStream.flush();
            outputStream.close();
            Object pojoContent = getAttributeValue(pojoIdentifiable, DocumentInfo.Attributes.content.name());
            if (pojoContent != null) {
                assignAttributeValue(pojoContent, SimpleContentInfo.Attributes.contentBody.name(), streamHelper);
            }
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    private void loadRenderedData(ManagedIdentifiableProxy wikiPageObj) {
        Persistent pojoIdentifiable = wikiPageObj.getPojoIdentifiable();
        try {
            PersistenceContext context = wikiPageObj.getPersistenceContext();
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            String resourceType = WikiPageDAO.getInstance().getResourceType();
            resourceType += "/render/data";
            BeeId id = getBeeId(wikiPageObj.getObjectId().toString());
            String mode = URLEncoder.encode("RENDER_ALL");
            String params = "mode=" + mode;
            GetMethod method = BdkAbstractDAO.prepareGetMethod(resourceType, id.getId(), null, params);
            WikiPage bdkWikiPage = (WikiPage) bdkHttpUtil.execute(WikiPage.class, method, userContext.httpClient);
            Object renderedData = bdkWikiPage.getRenderedData();
            assignAttributeValue(pojoIdentifiable, BeehiveWikiPageInfo.Attributes.renderedData.name(), renderedData);
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    private void loadRenderedPage(ManagedIdentifiableProxy wikiPageObj) {
        Persistent pojoIdentifiable = wikiPageObj.getPojoIdentifiable();
        try {
            PersistenceContext context = wikiPageObj.getPersistenceContext();
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            String resourceType = WikiPageDAO.getInstance().getResourceType();
            resourceType += "/render";
            BeeId id = getBeeId(wikiPageObj.getObjectId().toString());
            String mode = URLEncoder.encode("FULL");
            //String mode = URLEncoder.encode("EDIT_XHTML");
            //String mode = URLEncoder.encode("STATIC_ONLY");
            //String mode = URLEncoder.encode("EDIT_CREOLE");
            String params = "mode=" + mode;
            GetMethod method = BdkAbstractDAO.prepareGetMethod(resourceType, id.getId(), null, params);
            WikiPage bdkWikiPage = (WikiPage) bdkHttpUtil.execute(WikiPage.class, method, userContext.httpClient);
            String renderedPage = bdkWikiPage.getRenderedPage();
            assignAttributeValue(pojoIdentifiable, WikiPageInfo.Attributes.renderedPage.name(), renderedPage);
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    private void loadRenderedPage1(ManagedIdentifiableProxy wikiPageObj) {
        Persistent pojoIdentifiable = wikiPageObj.getPojoIdentifiable();
        try {
            PersistenceContext context = wikiPageObj.getPersistenceContext();
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            String resourceType = WikiPageDAO.getInstance().getResourceType();
            BeeId id = getBeeId(wikiPageObj.getObjectId().toString());
            String projection = URLEncoder.encode("RENDER_PAGE");
            String params = "projection=" + projection;
            GetMethod method = BdkAbstractDAO.prepareGetMethod(resourceType, id.getId(), null, params);
            WikiPage bdkWikiPage = (WikiPage) bdkHttpUtil.execute(WikiPage.class, method, userContext.httpClient);
            String renderedPage = bdkWikiPage.getRenderedPage();
            assignAttributeValue(pojoIdentifiable, WikiPageInfo.Attributes.renderedPage.name(), renderedPage);
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }

    private void loadRenderedPage2(ManagedIdentifiableProxy wikiPageObj) {
        Persistent pojoIdentifiable = wikiPageObj.getPojoIdentifiable();
        try {
            PersistenceContext context = wikiPageObj.getPersistenceContext();
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            String resourceType = WikiPageDAO.getInstance().getResourceType();
            resourceType += "/context";
            BeeId id = getBeeId(wikiPageObj.getObjectId().toString());
            String mode = URLEncoder.encode("FULL");
            String params = "mode=" + mode;
            GetMethod getMethod = BdkAbstractDAO.prepareGetMethod(resourceType, null, null, params);
            WikiRenderContext bdkWikiRenderContext = (WikiRenderContext) bdkHttpUtil.execute(WikiRenderContext.class, getMethod, userContext.httpClient);
            
            resourceType = WikiPageDAO.getInstance().getResourceType();
            resourceType += "/render";
            PostMethod postMethod = BdkAbstractDAO.preparePostMethod(resourceType, id.getId(), userContext.antiCSRF, null, null);
            WikiPage bdkWikiPage = (WikiPage) bdkHttpUtil.execute(WikiPage.class, postMethod, bdkWikiRenderContext, userContext.httpClient);
            String renderedPage = bdkWikiPage.getRenderedPage();
            assignAttributeValue(pojoIdentifiable, WikiPageInfo.Attributes.renderedPage.name(), renderedPage);
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    private void loadRenderedPage3(ManagedIdentifiableProxy wikiPageObj) {
        Persistent pojoIdentifiable = wikiPageObj.getPojoIdentifiable();
        try {
            PersistenceContext context = wikiPageObj.getPersistenceContext();
            BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
            String resourceType = WikiPageDAO.getInstance().getResourceType();
            resourceType += "/context/data";
            BeeId id = getBeeId(wikiPageObj.getObjectId().toString());
            String mode = URLEncoder.encode("RENDER_ALL");
            String params = "mode=" + mode;
            GetMethod getMethod = BdkAbstractDAO.prepareGetMethod(resourceType, null, null, params);
            WikiRenderContext bdkWikiRenderContext = (WikiRenderContext) bdkHttpUtil.execute(WikiRenderContext.class, getMethod, userContext.httpClient);
            
            resourceType = WikiPageDAO.getInstance().getResourceType();
            resourceType += "/render";
            PostMethod postMethod = BdkAbstractDAO.preparePostMethod(resourceType, id.getId(), userContext.antiCSRF, null, null);
            WikiPage bdkWikiPage = (WikiPage) bdkHttpUtil.execute(WikiPage.class, postMethod, bdkWikiRenderContext, userContext.httpClient);
            String renderedPage = bdkWikiPage.getRenderedPage();
            assignAttributeValue(pojoIdentifiable, WikiPageInfo.Attributes.renderedPage.name(), renderedPage);
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }

    protected Class<?> getBdkClass(ManagedObjectProxy obj) {
        return WikiPage.class;
    }

    protected WikiPageUpdater getBdkUpdater(ManagedObjectProxy obj) {
        return new WikiPageUpdater();
    }

    protected WikiPageUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
        WikiPageUpdater updater = getBdkUpdater(obj);
        ((WikiPageCreator)creator).setUpdater(updater);
        return updater;
    }

    protected WikiPageCreator getBdkCreator(ManagedObjectProxy obj) {
        return new WikiPageCreator();
    }
    
}
