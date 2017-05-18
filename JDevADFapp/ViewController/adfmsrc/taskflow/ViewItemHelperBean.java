package taskflow;

import javax.faces.event.ActionEvent;

import view.ExploreSpacesHelperBean;
import view.HelperBean;

public class ViewItemHelperBean extends HelperBean {
    
    public ViewItemHelperBean() {
        super();
    }

    public void doSubmit(ActionEvent actionEvent) {
        ExploreSpacesHelperBean callback = resolveExpressionLanguage(ExploreSpacesHelperBean.class, "#{pageFlowScope.callback}");
        callback.pprListItems();
    }

}
