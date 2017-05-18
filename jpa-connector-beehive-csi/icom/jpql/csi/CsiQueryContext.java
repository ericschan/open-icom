package icom.jpql.csi;

import icom.ql.QueryContext;
import oracle.csi.IdentifiableHandle;
import oracle.csi.projections.Projection;

public interface CsiQueryContext extends QueryContext {

    public Projection getProjection();
    
	public void setProjection(Projection projection);
    
    public IdentifiableHandle getContainer();
    
    public IdentifiableHandle getWorkspace();
    
    public IdentifiableHandle getMarker();
    
}
