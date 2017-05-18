package taskflow;

public class ListUnifiedMessagesHelperBean extends ListItemsHelperBean {
    
    String makeCurrentMethod = "#{bindings.UnifiedMessage.collectionModel.makeCurrent}";
    
    public ListUnifiedMessagesHelperBean() {
        super();
    }
    
    String getMakeCurrentMethod() {
        return makeCurrentMethod;
    }
}
