package taskflow;

public class ListDocumentsHelperBean extends ListItemsHelperBean {
    
    String makeCurrentMethod = "#{bindings.Document.collectionModel.makeCurrent}";
    
    public ListDocumentsHelperBean() {
        super();
    }
    
    String getMakeCurrentMethod() {
        return makeCurrentMethod;
    }
    
}
