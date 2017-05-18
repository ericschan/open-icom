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
import icom.info.PropertyInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.bdk.BdkAbstractDAO;
import icom.jpa.bdk.Projection;
import icom.jpa.rt.PersistenceContext;

import com.oracle.beehive.AttributeApplication;
import com.oracle.beehive.AttributeApplicationListUpdater;
import com.oracle.beehive.AttributeApplicationUpdateParameter;
import com.oracle.beehive.AttributeDefinition;
import com.oracle.beehive.BeeId;

public class AttributeApplicationDAO extends BdkAbstractDAO  {

	static AttributeApplicationDAO singleton = new AttributeApplicationDAO();
	
	public static AttributeApplicationDAO getInstance() {
		return singleton;
	}
	
	enum Operand { ADD, REMOVE, MODIFY };

	protected AttributeApplicationDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkIdentifiable, Projection proj) {
	}
	
	Object copyObjectState(ManagedObjectProxy obj, AttributeApplication attr) {
		String beehivePackagePrefix = BeehiveBeanEnumeration.BeehiveProperty.getPackageName() + ".";
		Object pojoAttr = BeanHandler.instantiatePojoObject(beehivePackagePrefix + BeehiveBeanEnumeration.BeehiveProperty.name());
		AttributeDefinition bdkAttrDef = attr.getAttributeDefinition();
		if (bdkAttrDef != null) {
			PersistenceContext context = obj.getPersistenceContext();
			ManagedIdentifiableProxy attrDefObj = AttributeDefinitionDAO.getInstance().getIdentifiableDependentProxy(context, bdkAttrDef, obj, PropertyInfo.Attributes.propertyDefinition.name());
			Persistent pojoAttrDef = attrDefObj.getPojoIdentifiable();
			assignAttributeValue(pojoAttr, PropertyInfo.Attributes.propertyDefinition.name(), pojoAttrDef);
		}
		Object value = attr.getValue();
		if (value != null) {
			assignPropertyValue(pojoAttr, PropertyInfo.Attributes.value.name(), value);
		}
		return pojoAttr;
	}
	
	public void updateObjectState(Object pojoProperty, AttributeApplicationListUpdater updater, BeeId attrTemplateId, Operand operand) {
		Object attrValue = getPropertyValue(pojoProperty, PropertyInfo.Attributes.value.name());
		if (operand == Operand.ADD) {
			AttributeApplicationUpdateParameter param = new AttributeApplicationUpdateParameter();
			param.setAttributeTemplateHandle(attrTemplateId);
			param.setValue(attrValue);
			updater.getAdds().add(param);
		} else if (operand == Operand.REMOVE) {
			updater.getRemoves().add(attrTemplateId);
		} else if (operand == Operand.MODIFY) {
			// TODO
		}
	}

}
