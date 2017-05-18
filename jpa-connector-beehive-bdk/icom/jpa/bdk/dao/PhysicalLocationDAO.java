package icom.jpa.bdk.dao;

import icom.jpa.ManagedObjectProxy;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;

public class PhysicalLocationDAO extends BdkAbstractDAO {

	static PhysicalLocationDAO singleton = new PhysicalLocationDAO();
	
	public static PhysicalLocationDAO getInstance() {
		return singleton;
	}
	
	PhysicalLocationDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkProperty, Projection proj) {
		
	}


}
