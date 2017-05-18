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
package icom.jpa.csi.dao;

import icom.info.EntityAddressInfo;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.csi.CsiAbstractDAO;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import oracle.csi.EntityAddress;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AddressableUpdater;

public class EntityAddressDAO extends CsiAbstractDAO {
	
	static EntityAddressDAO singleton = new EntityAddressDAO();
	
	enum Operand { ADD, REMOVE, SET_PRIMARY };
	
	public static EntityAddressDAO getInstance() {
		return singleton;
	}
	
	EntityAddressDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiEntityAddress, Projection proj) {
		copyObjectState(managedObj.getPojoObject(), (EntityAddress) csiEntityAddress);
	}
	
	Object copyObjectState(Object pojoEntityAddress, EntityAddress csiEntityAddress) {
		String addressType = csiEntityAddress.getAddressType();
		if (addressType != null) {
			assignAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.addressType.name(), addressType);
		}
		/*
		EntityAddressScheme csiAddressScheme = csiEntityAddress.getAddressScheme();
		if (csiAddressScheme != null) {
			assignEnumConstant(pojoEntityAddress, BeanEnumeration.AddressScheme.name(), 
				EntityAddressInfo.Attributes.addressScheme.name(), csiAddressScheme.name());
		}
		*/
		URI uri = csiEntityAddress.getAddress();
		if (uri != null) {
			assignPropertyValue(pojoEntityAddress, EntityAddressInfo.Attributes.address.name(), uri);
		}
		return pojoEntityAddress;
	}
	
	public void updateObjectState(Object pojoEntityAddress, AddressableUpdater updater, Operand operand) {
		if (pojoEntityAddress != null) {
			String addressType = (String) getAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.addressType.name());
			URI uri = (URI) getAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.address.name());
			EntityAddress address = new EntityAddress(uri, addressType);
			if (operand == Operand.ADD) {
				updater.addAddress(address);
			} else if (operand == Operand.REMOVE) {
				updater.removeAddress(address);
			} else if (operand == Operand.SET_PRIMARY) {
				updater.setPrimaryAddress(address);
			}
		} else if (operand == Operand.SET_PRIMARY) {
			updater.setPrimaryAddress(null);
		}
	}
	
	public void updateObjectState(Collection<Object> modifiedAddresses, AddressableUpdater updater) {
		Collection<EntityAddress> addresses = new ArrayList<EntityAddress>(modifiedAddresses.size());
		for (Object pojoEntityAddress : modifiedAddresses) {
			String addressType = (String) getAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.addressType.name());
			URI uri = (URI) getAttributeValue(pojoEntityAddress, EntityAddressInfo.Attributes.address.name());
			EntityAddress address = new EntityAddress(uri, addressType);
			addresses.add(address);
		}
		updater.addAddresses(addresses);
	}

}
