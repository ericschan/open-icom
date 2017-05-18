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

import icom.info.BeanHandler;
import icom.info.PropertyInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.csi.CsiAbstractDAO;
import icom.jpa.rt.PersistenceContext;
import oracle.csi.AttributeApplication;
import oracle.csi.AttributeDefinition;
import oracle.csi.AttributeTemplateHandle;
import oracle.csi.projections.Projection;
import oracle.csi.updaters.AttributeApplicationListUpdater;

public class AttributeApplicationDAO extends CsiAbstractDAO  {

	static AttributeApplicationDAO singleton = new AttributeApplicationDAO();
	
	public static AttributeApplicationDAO getInstance() {
		return singleton;
	}
	
	enum Operand { ADD, REMOVE, MODIFY };

	protected AttributeApplicationDAO() {
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiIdentifiable, Projection proj) {
	}
	
	Object copyObjectState(ManagedObjectProxy obj, AttributeApplication attr) {
		String beehivePackagePrefix = BeehiveBeanEnumeration.BeehiveProperty.getPackageName() + ".";
		Object pojoAttr = BeanHandler.instantiatePojoObject(beehivePackagePrefix + BeehiveBeanEnumeration.BeehiveProperty.name());
		AttributeDefinition csiAttrDef = attr.getAttributeDefinition();
		if (csiAttrDef != null) {
			PersistenceContext context = obj.getPersistenceContext();
			ManagedIdentifiableProxy attrDefObj = AttributeDefinitionDAO.getInstance().getIdentifiableDependentProxy(context, csiAttrDef, obj, PropertyInfo.Attributes.propertyDefinition.name());
			Persistent pojoAttrDef = attrDefObj.getPojoIdentifiable();
			assignAttributeValue(pojoAttr, PropertyInfo.Attributes.propertyDefinition.name(), pojoAttrDef);
		}
		Object value = attr.getValue();
		if (value != null) {
			assignPropertyValue(pojoAttr, PropertyInfo.Attributes.value.name(), value);
		}
		return pojoAttr;
	}
	
	public void updateObjectState(Object pojoProperty, AttributeApplicationListUpdater updater, AttributeTemplateHandle attrTemplateHandle, Operand operand) {
		Object attrValue = getPropertyValue(pojoProperty, PropertyInfo.Attributes.value.name());
		if (operand == Operand.ADD) {
			updater.addValue(attrTemplateHandle, attrValue);
		} else if (operand == Operand.REMOVE) {
			updater.removeValue(attrTemplateHandle);
		} else if (operand == Operand.MODIFY) {
			updater.addValue(attrTemplateHandle, attrValue);
		}
	}

	public void committedObject(Object pojo) {
		
	}
	
	public void rolledbackObject(Object pojo) {
		
	}

}
