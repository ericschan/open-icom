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
import icom.info.IcomBeanEnumeration;
import icom.info.PropertyDefinitionInfo;
import icom.info.PropertyInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.csi.CsiAbstractDAO;
import oracle.csi.MimeHeader;
import oracle.csi.projections.Projection;

public class MimeHeaderDAO extends CsiAbstractDAO {

	static MimeHeaderDAO singleton = new MimeHeaderDAO();
	
	public static MimeHeaderDAO getInstance() {
		return singleton;
	}
	
	MimeHeaderDAO() {
		
	}
	
	public void copyObjectState(ManagedObjectProxy managedObj, Object csiMimeHeader, Projection proj) {
		copyObjectState(managedObj.getPojoObject(), (MimeHeader) csiMimeHeader);
	}
	
	void copyObjectState(Object pojoProperty, MimeHeader header) {
		
		Object propertyDefinition = BeanHandler.instantiatePojoObject(BeanHandler.getBeanPackageName() + "." + IcomBeanEnumeration.PropertyDefinition.name());

		String name = header.getName();
		if (name != null) {
			assignAttributeValue(propertyDefinition, PropertyDefinitionInfo.Attributes.name.name(), name);
		}
		
		String beehivePackageName = BeehiveBeanEnumeration.BeehivePropertyTypeEnum.getPackageName();
		assignEnumConstant(propertyDefinition, PropertyDefinitionInfo.Attributes.propertyType.name(), beehivePackageName, BeehiveBeanEnumeration.BeehivePropertyTypeEnum.name(), "Any");
		
		assignAttributeValue(pojoProperty, PropertyInfo.Attributes.propertyDefinition.name(), propertyDefinition);
		
		Object value = header.getValue();
		if (value != null) {
			assignPropertyValue(pojoProperty, PropertyInfo.Attributes.value.name(), value);
		}
	}
	
	public MimeHeader updateObjectState(Object pojoProperty) {
		Object propertyDefinition = getAttributeValue(pojoProperty, PropertyInfo.Attributes.propertyDefinition.name());
		String name = (String) getAttributeValue(propertyDefinition, PropertyDefinitionInfo.Attributes.name.name());
		Object value = getPropertyValue(pojoProperty, PropertyInfo.Attributes.value.name());
		byte[] bvalue;
		if (!(value instanceof byte[])) {
			bvalue = value.toString().getBytes();
		} else {
			bvalue = (byte[]) value;
		}
		MimeHeader header = new MimeHeader();
		header.setName(name);
		header.setValue(bvalue);
		return header;
	}
	
}
