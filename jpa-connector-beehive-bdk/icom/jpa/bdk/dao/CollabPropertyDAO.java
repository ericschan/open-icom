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

import icom.info.BeanHandler;
import icom.info.IcomBeanEnumeration;
import icom.info.PropertyDefinitionInfo;
import icom.info.PropertyInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;

import com.oracle.beehive.CollabPropertiesUpdater;
import com.oracle.beehive.CollabProperty;
import com.oracle.beehive.PropMod;
import com.oracle.beehive.PropModOperation;

public class CollabPropertyDAO extends BdkAbstractDAO {

	static CollabPropertyDAO singleton = new CollabPropertyDAO();
	
	public static CollabPropertyDAO getInstance() {
		return singleton;
	}
	
	enum Operand { ADD, REMOVE, MODIFY };
	
	CollabPropertyDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkProperty, Projection proj) {
		copyObjectState(managedObj.getPojoObject(), (CollabProperty) bdkProperty);
	}
	
	void copyObjectState(Object pojoProperty, CollabProperty property) {
		Object propertyDefinition = BeanHandler.instantiatePojoObject(BeanHandler.getBeanPackageName() + "." + IcomBeanEnumeration.PropertyDefinition.name());

		String name = property.getName();
		if (name != null) {
			assignAttributeValue(propertyDefinition, PropertyDefinitionInfo.Attributes.name.name(), name);
		}
		
		String description = property.getDescription();
		if (description != null) {
			assignAttributeValue(propertyDefinition, PropertyDefinitionInfo.Attributes.description.name(), description);
		}
		
		String beehivePackageName = BeehiveBeanEnumeration.BeehivePropertyTypeEnum.getPackageName();
		assignEnumConstant(propertyDefinition, PropertyDefinitionInfo.Attributes.propertyType.name(), beehivePackageName, BeehiveBeanEnumeration.BeehivePropertyTypeEnum.name(), "Any");
		
		assignAttributeValue(pojoProperty, PropertyInfo.Attributes.propertyDefinition.name(), propertyDefinition);
		
		Object value = property.getValue();
		if (value != null) {
			assignPropertyValue(pojoProperty, PropertyInfo.Attributes.value.name(), value);
		}
	}
	
	public void updateObjectState(Object pojoProperty, CollabPropertiesUpdater updater, Operand operand) {
		Object propertyDefinition = getAttributeValue(pojoProperty, PropertyInfo.Attributes.propertyDefinition.name());
		String name = (String) getAttributeValue(propertyDefinition, PropertyDefinitionInfo.Attributes.name.name());
		String description = (String) getAttributeValue(propertyDefinition, PropertyDefinitionInfo.Attributes.description.name());
		Object value = getPropertyValue(pojoProperty, PropertyInfo.Attributes.value.name());
		
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
