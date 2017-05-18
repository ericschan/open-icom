package icom.jpql.bdk;

import com.oracle.beehive.BeeId;

import icom.jpa.bdk.Projection;
import icom.ql.QueryContext;

public interface BdkQueryContext extends QueryContext {

    public Projection getProjection();
    
	public void setProjection(Projection projection);
    
    public BeeId getContainer();
    
    public BeeId getWorkspace();
    
    public BeeId getMarker();
    
}
