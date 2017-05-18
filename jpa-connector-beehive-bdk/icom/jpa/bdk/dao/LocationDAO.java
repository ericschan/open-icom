package icom.jpa.bdk.dao;

import icom.jpa.ManagedObjectProxy;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;

public class LocationDAO extends BdkAbstractDAO {

	static LocationDAO singleton = new LocationDAO();
	
	public static LocationDAO getInstance() {
		return singleton;
	}
	
	LocationDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkProperty, Projection proj) {
		
	}

}
