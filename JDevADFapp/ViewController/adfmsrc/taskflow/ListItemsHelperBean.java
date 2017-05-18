package taskflow;

import java.util.Iterator;
import java.util.List;

import oracle.adf.model.bean.DCDataRow;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlHierTypeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

import view.ExploreSpacesHelperBean;
import view.HelperBean;


public abstract class ListItemsHelperBean extends HelperBean {
    
    public ListItemsHelperBean() {
        super();
    }
    
    abstract String getMakeCurrentMethod();

    public void selectTableRow(SelectionEvent selectionEvent) {
        RowKeySet selectedRowKeys = selectionEvent.getAddedSet();
        if (selectedRowKeys.size() != 1) {
            return;
        }
        RichTable table = (RichTable)selectionEvent.getSource();
        makeCurrent(table, selectedRowKeys, getMakeCurrentMethod());
        
        Iterator selectedRowKeysIterator = selectedRowKeys.iterator();
        List selectedRowKeyList = (List)selectedRowKeysIterator.next();

        CollectionModel model = (CollectionModel)table.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
        JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(selectedRowKeyList);
        Row itemRow = nodeBinding.getRow();
        String itemStructureDefName = null;
        if (itemRow instanceof DCDataRow) {
            Object item = ((DCDataRow)itemRow).getDataProvider();
            itemStructureDefName = item.getClass().getCanonicalName();
        } else {
            JUCtrlHierTypeBinding typeBinding = nodeBinding.getHierTypeBinding();
            itemStructureDefName = typeBinding.getStructureDefName();
        }
        Key selectedNodeRowKey = nodeBinding.getRowKey();
        Object[] keys = selectedNodeRowKey.getKeyValues();
        String itemId = (String) keys[0];
        
        ExploreSpacesHelperBean callback = resolveExpressionLanguage(ExploreSpacesHelperBean.class, "#{pageFlowScope.callback}");
        callback.setItemStructureDefName(itemStructureDefName);
        callback.setItemId(itemId);
        callback.pprViewItem();
    }

}
