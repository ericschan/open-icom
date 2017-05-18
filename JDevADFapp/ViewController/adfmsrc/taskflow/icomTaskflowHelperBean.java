package taskflow;

import view.HelperBean;


public class icomTaskflowHelperBean extends HelperBean {
    
    String sessionCloseExecute = "#{bindings.close.execute}";
    
    public icomTaskflowHelperBean() {
        super();
    }
    
    public void close() {
        invokeMethodExpression(sessionCloseExecute);
    }
    
}
