package icom.jpa.csi.dao;

import icom.jpa.ManagedObjectProxy;
import icom.jpa.csi.CsiAbstractDAO;
import oracle.csi.projections.Projection;

public class PhysicalLocationDAO extends CsiAbstractDAO {

	static PhysicalLocationDAO singleton = new PhysicalLocationDAO();
	
	public static PhysicalLocationDAO getInstance() {
		return singleton;
	}
	
	PhysicalLocationDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiProperty, Projection proj) {
		
	}


}
