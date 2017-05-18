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

import icom.info.PropertyChoiceTypeInfo;
import icom.info.PropertyInfo;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;

import com.oracle.beehive.CollabPropertiesUpdater;
import com.oracle.beehive.CollabProperty;
import com.oracle.beehive.PropMod;
import com.oracle.beehive.PropModOperation;

public class PropertyChoiceTypeToCollabPropertyDAO extends BdkAbstractDAO {

	static PropertyChoiceTypeToCollabPropertyDAO singleton = new PropertyChoiceTypeToCollabPropertyDAO();
	
	public static PropertyChoiceTypeToCollabPropertyDAO getInstance() {
		return singleton;
	}
	
	enum Operand { ADD, REMOVE, MODIFY };
	
	PropertyChoiceTypeToCollabPropertyDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkProperty, Projection proj) {
		copyObjectState(managedObj.getPojoObject(), (CollabProperty) bdkProperty);
	}
	
	void copyObjectState(Object pojoObject, CollabProperty property) {
		String name = property.getName();
		if (name != null) {
			assignAttributeValue(pojoObject, PropertyChoiceTypeInfo.Attributes.displayName.name(), name);
		}
		
		String description = property.getDescription();
		if (description != null) {
			assignAttributeValue(pojoObject, PropertyChoiceTypeInfo.Attributes.description.name(), description);
		}
			
		Object value = property.getValue();
		if (value != null) {
			assignPropertyValue(pojoObject, PropertyInfo.Attributes.value.name(), value);
		}
	}
	
	public void updateObjectState(Object pojoObject, CollabPropertiesUpdater updater, Operand operand) {
		String name = (String) getAttributeValue(pojoObject, PropertyChoiceTypeInfo.Attributes.displayName.name());
		String description = (String) getAttributeValue(pojoObject, PropertyChoiceTypeInfo.Attributes.description.name());
		Object value = getPropertyValue(pojoObject, PropertyChoiceTypeInfo.Attributes.value.name());
		if (operand == Operand.ADD) {
			PropMod propMod = new PropMod();
			propMod.setPropertyName(name);
			propMod.setOperation(PropModOperation.ADD);
			CollabProperty property = new CollabProperty();
			property.setDescription(description);
			property.setName(name);
			property.setValue(value);
		} else if (operand == Operand.REMOVE) {
			PropMod propMod = new PropMod();
			propMod.setPropertyName(name);
			propMod.setOperation(PropModOperation.REMOVE);
		} else if (operand == Operand.MODIFY) {
			// not supported
		}
		
	}
	
}
