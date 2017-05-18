package taskflow;

import java.util.Iterator;
import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.jbo.Key;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlHierTypeBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

import view.ExploreSpacesHelperBean;
import view.HelperBean;

public class BrowseContainersHelperBean extends HelperBean {
    
    private final String makeCurrentMethod = "#{bindings.spaces.treeModel.makeCurrent}";
    
    private final String spaceStructureType = "icom.Space";

    public BrowseContainersHelperBean() {
        super();
    }
    
    String getMakeCurrentMethod() {
        return makeCurrentMethod;
    }
    
    public void selectTreeNode(SelectionEvent selectionEvent) {
        RowKeySet selectedRowKeys = selectionEvent.getAddedSet();
        if (selectedRowKeys.size() != 1) {
            return;
        }
        Iterator selectedRowKeysIterator = selectedRowKeys.iterator();
        List selectedRowKey = (List)selectedRowKeysIterator.next();
        
        RichTree tree = (RichTree)selectionEvent.getSource();
        makeCurrent(tree, selectedRowKeys, getMakeCurrentMethod());
        
        ExploreSpacesHelperBean callback = resolveExpressionLanguage(ExploreSpacesHelperBean.class, "#{pageFlowScope.callback}");
        
        CollectionModel model = (CollectionModel)tree.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
        JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(selectedRowKey);
        JUCtrlHierTypeBinding typeBinding = nodeBinding.getHierTypeBinding();
        String containerId = callback.getContainerId();
        String itemId = null;
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
        callback.setContainerId(containerId);
        callback.setItemId(itemId);
    }

    public void discloseTreeNode(RowDisclosureEvent rowDisclosureEvent) {
        RowKeySet selectedRowKeys = rowDisclosureEvent.getAddedSet();
        if (selectedRowKeys.size() != 1) {
            return;
        }
        Iterator selectedRowKeysIterator = selectedRowKeys.iterator();
        List selectedRowKey = (List)selectedRowKeysIterator.next();
        
        RichTree tree = (RichTree)rowDisclosureEvent.getSource();
        makeCurrent(tree, selectedRowKeys, getMakeCurrentMethod());
        
        ExploreSpacesHelperBean callback = resolveExpressionLanguage(ExploreSpacesHelperBean.class, "#{pageFlowScope.callback}");
        
        CollectionModel model = (CollectionModel)tree.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
        JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(selectedRowKey);
        JUCtrlHierTypeBinding typeBinding = nodeBinding.getHierTypeBinding();
        String containerId = callback.getContainerId();
        String itemId = null;
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
        callback.setContainerId(containerId);
        callback.setItemId(itemId);
    }
    
}
