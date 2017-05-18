package view;

import icom.Artifact;
import icom.Document;
import icom.UnifiedMessage;

import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.context.AdfFacesContext;

import taskflow.BrowseContainersRegionBean;
import taskflow.ListItemsRegionBean;
import taskflow.ViewItemRegionBean;


public class ExploreSpacesHelperBean extends HelperBean {
    
    String contextId;
    
    String containerId;
    
    String itemId;
    
    String itemType;
    
    String containerStructureDefName;
    
    String itemStructureDefName;
    
    private RichRegion browseContainersRegion;
    
    BrowseContainersRegionBean browseContainersRegionBean;
    
    private RichRegion viewItemRegion;
    
    ViewItemRegionBean viewItemRegionBean;
    
    private RichRegion listItemsRegion;
    
    ListItemsRegionBean listItemsRegionBean;

    public ExploreSpacesHelperBean() {
        super();
        contextId = String.valueOf(System.currentTimeMillis());
        browseContainersRegionBean = resolveExpressionLanguage(BrowseContainersRegionBean.class, "${viewScope.BrowseContainersRegionBean}");
        viewItemRegionBean = resolveExpressionLanguage(ViewItemRegionBean.class, "${viewScope.ViewItemRegionBean}");
        listItemsRegionBean = resolveExpressionLanguage(ListItemsRegionBean.class, "${viewScope.ListItemsRegionBean}");
    }
    
    public void pprListItems() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(getListItemsRegion());
    }

    public void pprViewItem() {
        Class<?> clazz = null;
        try {
            clazz = (Class<?>) Class.forName(itemStructureDefName);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("entity class not found :" + itemStructureDefName);
        } catch (Throwable ex) {
            throw new RuntimeException("entity class not valid :" + itemStructureDefName + "\n" + ex.getMessage());
        }
        if (viewItemRegionBean != null) {
            if (Document.class.isAssignableFrom(clazz)) {
                viewItemRegionBean.viewdocumenttaskflowdefinition();
            } else if (UnifiedMessage.class.isAssignableFrom(clazz)) {
                viewItemRegionBean.viewunifiedmessagetaskflowdefinition();
            } else if (Artifact.class.isAssignableFrom(clazz)) {
                viewItemRegionBean.viewartifacttaskflowdefinition();
            }
        } 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getViewItemRegion());
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getContextId() {
        return contextId;
    }
    
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }
    
    public void setContainerStructureDefName(String containerStructureDefName) {
        this.containerStructureDefName = containerStructureDefName;
    }

    public String getContainerStructureDefName() {
        return containerStructureDefName;
    }
    
    public void setItemStructureDefName(String itemStructureDefName) {
        this.itemStructureDefName = itemStructureDefName;
        String[] tokens = itemStructureDefName.split("\\.");
        this.itemType = tokens[tokens.length-1];
    }

    public String getItemStructureDefName() {
        return itemStructureDefName;
    }

    public void setBrowseContainersRegion(RichRegion browseContainersRegion) {
        this.browseContainersRegion = browseContainersRegion;
    }

    public RichRegion getBrowseContainersRegion() {
        return browseContainersRegion;
    }
    
    public void setViewItemRegion(RichRegion viewItemRegion) {
        this.viewItemRegion = viewItemRegion;
    }

    public RichRegion getViewItemRegion() {
        return viewItemRegion;
    }

    public void setListItemsRegion(RichRegion listItemsRegion) {
        this.listItemsRegion = listItemsRegion;
    }

    public RichRegion getListItemsRegion() {
        return listItemsRegion;
    }

}
