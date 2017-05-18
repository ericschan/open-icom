package icom.jpa.csi.dao;

import icom.jpa.ManagedObjectProxy;
import icom.jpa.csi.CsiAbstractDAO;
import oracle.csi.projections.Projection;

public class LocationDAO extends CsiAbstractDAO {

	static LocationDAO singleton = new LocationDAO();
	
	public static LocationDAO getInstance() {
		return singleton;
	}
	
	LocationDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiProperty, Projection proj) {
		
	}

}
