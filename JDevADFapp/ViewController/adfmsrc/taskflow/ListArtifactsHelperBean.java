package taskflow;

public class ListArtifactsHelperBean extends ListItemsHelperBean {
    
    String makeCurrentMethod = "#{bindings.Artifact.collectionModel.makeCurrent}";
    
    public ListArtifactsHelperBean() {
        super();
    }
    
    String getMakeCurrentMethod() {
        return makeCurrentMethod;
    }
    
    
}
