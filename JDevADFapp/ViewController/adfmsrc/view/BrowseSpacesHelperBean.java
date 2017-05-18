package view;

import java.util.Iterator;
import java.util.List;

import oracle.adf.model.bean.DCDataRow;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlHierTypeBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class BrowseSpacesHelperBean extends HelperBean {
    
    private final String spaceCurrencyMethod = "#{bindings.spaces.treeModel.makeCurrent}";
    
    private final String spaceStructureType = "icom.Space";

    String containerId;
    
    String itemId;
    
    String containerStructureDefName;
    
    String itemStructureDefName;
    
    private UIXSwitcher formPanelSwitcher;
    
    private RichPanelTabbed tablePanelTabs;

    public BrowseSpacesHelperBean() {
        super();
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

    public void selectTableRow(SelectionEvent selectionEvent) {
        RowKeySet selectedRowKeys = selectionEvent.getAddedSet();
        if (selectedRowKeys.size() != 1) {
            return;
        }
        Iterator selectedRowKeysIterator = selectedRowKeys.iterator();
        List selectedRowKeyList = (List)selectedRowKeysIterator.next();
        
        RichTable table = (RichTable) selectionEvent.getSource();
        CollectionModel model = (CollectionModel)table.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
        JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(selectedRowKeyList);
        Row itemRow = nodeBinding.getRow();
        if (itemRow instanceof DCDataRow) {
            Object item = ((DCDataRow)itemRow).getDataProvider();
            itemStructureDefName = item.getClass().getCanonicalName();
        } else {
            JUCtrlHierTypeBinding typeBinding = nodeBinding.getHierTypeBinding();
            itemStructureDefName = typeBinding.getStructureDefName();
        }
        Key selectedNodeRowKey = nodeBinding.getRowKey();
        Object[] keys = selectedNodeRowKey.getKeyValues();
        itemId = (String) keys[0];
        AdfFacesContext.getCurrentInstance().addPartialTarget(getFormPanelSwitcher());
    }

    public void selectTreeNode(SelectionEvent selectionEvent) {
        RowKeySet selectedRowKeys = selectionEvent.getAddedSet();
        if (selectedRowKeys.size() != 1) {
            return;
        }
        Iterator selectedRowKeysIterator = selectedRowKeys.iterator();
        List selectedRowKey = (List)selectedRowKeysIterator.next();
        
        RichTree tree = (RichTree)selectionEvent.getSource();
        makeCurrent(tree, selectedRowKeys, spaceCurrencyMethod);
        
        CollectionModel model = (CollectionModel)tree.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
        JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(selectedRowKey);
        JUCtrlHierTypeBinding typeBinding = nodeBinding.getHierTypeBinding();
        if (! typeBinding.getStructureDefName().equals(spaceStructureType)) {   
            Key selectedNodeRowKey = nodeBinding.getRowKey();
            Object[] keys = selectedNodeRowKey.getKeyValues();
            if (containerId == null || ! containerId.equals(keys[0])) {
                containerId = (String) keys[0];
                itemId = null;
            }
        } else {
            containerId = null;
            itemId = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTablePanelTabs());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getFormPanelSwitcher());
    }

    public void discloseTreeNode(RowDisclosureEvent rowDisclosureEvent) {
        RowKeySet selectedRowKeys = rowDisclosureEvent.getAddedSet();
        if (selectedRowKeys.size() != 1) {
            return;
        }
        Iterator selectedRowKeysIterator = selectedRowKeys.iterator();
        List selectedRowKey = (List)selectedRowKeysIterator.next();
        
        RichTree tree = (RichTree)rowDisclosureEvent.getSource();
        makeCurrent(tree, selectedRowKeys, spaceCurrencyMethod);
        
        CollectionModel model = (CollectionModel)tree.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
        JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(selectedRowKey);
        JUCtrlHierTypeBinding typeBinding = nodeBinding.getHierTypeBinding();
        if (! typeBinding.getStructureDefName().equals(spaceStructureType)) {   
            Key selectedNodeRowKey = nodeBinding.getRowKey();
            Object[] keys = selectedNodeRowKey.getKeyValues();
            if (containerId == null || ! containerId.equals(keys[0])) {
                containerId = (String) keys[0];
                itemId = null;
            }
        } else {
            containerId = null;
            itemId = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTablePanelTabs());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getFormPanelSwitcher());
    }

    public void setContainerStructureDefName(String containerStructureDefName) {
        this.containerStructureDefName = containerStructureDefName;
    }

    public String getContainerStructureDefName() {
        return containerStructureDefName;
    }
    
    public void setItemStructureDefName(String itemStructureDefName) {
        this.itemStructureDefName = itemStructureDefName;
    }

    public String getItemStructureDefName() {
        return itemStructureDefName;
    }

    public void setFormPanelSwitcher(UIXSwitcher formPanelSwitcher) {
        this.formPanelSwitcher = formPanelSwitcher;
    }

    public UIXSwitcher getFormPanelSwitcher() {
        return formPanelSwitcher;
    }

    public void setTablePanelTabs(RichPanelTabbed tablePanelTabs) {
        this.tablePanelTabs = tablePanelTabs;
    }

    public RichPanelTabbed getTablePanelTabs() {
        return tablePanelTabs;
    }
}
