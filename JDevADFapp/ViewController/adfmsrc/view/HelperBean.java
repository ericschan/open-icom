package view;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public abstract class HelperBean {
    
    public HelperBean() {
        super();
    }
    
    protected Object invokeMethodExpression(String methodExpression, Object[] parameters, Class[] expectedParamTypes)  {   
        FacesContext fc = FacesContext.getCurrentInstance();   
        ELContext elctx = fc.getELContext();   
        ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();   
        MethodExpression methodExpr = elFactory.createMethodExpression(elctx, methodExpression, Object.class, expectedParamTypes);   
        return methodExpr.invoke(elctx, parameters);   
    }
    
    protected Object invokeMethodExpression(String methodExpression, Object event, Class eventClass) {
        return invokeMethodExpression(methodExpression, new Object[]{event}, new Class[]{eventClass});
    }
    
    protected Object invokeMethodExpression(String methodExpression) {
        return invokeMethodExpression(methodExpression, (Object[]) null, (Class[]) new Class[0]);
    }
    
    protected <T> T resolveExpressionLanguage(Class<T> clazz, String expr) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory elFactory = fctx.getApplication().getExpressionFactory();
        ValueExpression valueExpr = elFactory.createValueExpression(elctx, expr, (Class) clazz);
        T obj = (T) valueExpr.getValue(elctx);
        return obj;
    }

    protected DCIteratorBinding resolveTargetIterWithSpel(String spelExpr) {
        return resolveExpressionLanguage(DCIteratorBinding.class, spelExpr);
    }
    
    protected void makeCurrent(RichTree tree, RowKeySet selectedRows, String spaceCurrency) {
        SelectionEvent selectionEvent = new SelectionEvent(tree, new RowKeySetImpl(), selectedRows);
        invokeMethodExpression(spaceCurrency, selectionEvent, SelectionEvent.class);
    }
    
    protected void makeCurrent(RichTable table, RowKeySet selectedRows, String spaceCurrency) {
        SelectionEvent selectionEvent = new SelectionEvent(table, new RowKeySetImpl(), selectedRows);
        invokeMethodExpression(spaceCurrency, selectionEvent, SelectionEvent.class);
    }
    
}
