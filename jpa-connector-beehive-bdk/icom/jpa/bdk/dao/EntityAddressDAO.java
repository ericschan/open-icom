/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER
 * 
 * Copyright (c) 2010, Oracle Corporation All Rights Reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License ("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can obtain
 * a copy of the License at http://openjdk.java.net/legal/gplv2+ce.html.
 * See the License for the specific language governing permission and
 * limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at openICOM/bootstrap/legal/LICENSE.txt.
 * Oracle designates this particular file as subject to the "Classpath" exception
 * as provided by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [ ] replaced by your own
 * identifying information:  "Portions Copyrighted [year]
 * [name of copyright owner].
 *
 * Contributor(s): Oracle Corporation
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package icom.jpa.bdk.dao;

import com.oracle.beehive.AccessorUpdater;
import com.oracle.beehive.AddressesListUpdater;
import com.oracle.beehive.EntityAddress;
import com.oracle.beehive.EntityUpdater;

import icom.info.EntityAddressInfo;

import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Collection;


public class EntityAddressDAO extends BdkAbstractDAO {
	
	static EntityAddressDAO singleton = new EntityAddressDAO();
	
	enum Operand { ADD, REMOVE, SET_PRIMARY };
	
	public static EntityAddressDAO getInstance() {
		return singleton;
	}
	
	EntityAddressDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntityAddress, Projection proj) {
		copyObjectState(managedObj.getPojoObject(), (EntityAddress) bdkEntityAddress);
	}
	
	Object copyObjectState(Object pojoEntityAddress, EntityAddress bdkEntityAddress) {
		String addressType = bdkEntityAddress.getAddressType();
		if (addressType != null) {
			assignAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.addressType.name(), addressType);
		}
		/*
		EntityAddressScheme bdkAddressScheme = bdkEntityAddress.getAddressScheme();
		if (bdkAddressScheme != null) {
			assignEnumConstant(pojoEntityAddress, BeanEnumeration.AddressScheme.name(), 
				EntityAddressInfo.Attributes.addressScheme.name(), bdkAddressScheme.name());
		}
		*/
		String address = bdkEntityAddress.getAddress();
		if (address != null) {
			try {
				URI uri = new URI(address);
				assignPropertyValue(pojoEntityAddress, EntityAddressInfo.Attributes.address.name(), uri);
			} catch (URISyntaxException ex) {
				
			}
		}
		return pojoEntityAddress;
	}
	
	public void updateObjectState(Object pojoEntityAddress, EntityUpdater updater, Operand operand) {
		if (pojoEntityAddress != null) {
			String addressType = (String) getAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.addressType.name());
			URI uri = (URI) getAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.address.name());
			EntityAddress address = new EntityAddress();
			address.setAddress(uri.toString());
			address.setAddressType(addressType);
			AddressesListUpdater listUpdater = null;
			if (updater instanceof AccessorUpdater) {
				AccessorUpdater addressableUpdater = (AccessorUpdater) updater;
				listUpdater = addressableUpdater.getAddresses();
				if (listUpdater == null) {
					listUpdater = new AddressesListUpdater();
					addressableUpdater.setAddresses(listUpdater);
				}
			}
			if (operand == Operand.ADD) {
				listUpdater.getAdds().add(address);
			} else if (operand == Operand.REMOVE) {
				listUpdater.getRemoves().add(address);
			} else if (operand == Operand.SET_PRIMARY) {
				if (updater instanceof AccessorUpdater) {
					AccessorUpdater addressableUpdater = (AccessorUpdater) updater;
					addressableUpdater.setPrimaryAddress(address);
				}
			}
		} else if (operand == Operand.SET_PRIMARY) {
			if (updater instanceof AccessorUpdater) {
				AccessorUpdater addressableUpdater = (AccessorUpdater) updater;
				addressableUpdater.setPrimaryAddress(null);
			}
		}
	}
	
	public void updateObjectState(Collection<Persistent> modifiedAddresses, EntityUpdater updater) {
		// not supported
	}

}
