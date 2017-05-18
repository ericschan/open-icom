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
package icom.info;

import icom.jpa.Identifiable;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.ql.SchemaHelper;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

class VendorBeanHandlingUtilities {
	String vendorBeanPackageName;
	BeanUtil vendorBeanUtil;
	Class<? extends BeanEnumeration> vendorBeanEnumerationClass;
	Map<String, String> vendorBeanNormalizedNames = new HashMap<String, String>();
	HashSet<String> vendorBeanNames = new HashSet<String>();
	BeanEnumeration vendorBeanConstant;
}

public class BeanHandler {
	
	private static BeanUtil beanUtil;
	private static String beanPackageName;
	private static Map<String, String> beanNormalizedNames = new HashMap<String, String>();
	private static HashSet<String> beanNames = new HashSet<String>();

	static public void registerPrimaryBeanHandling(BeanUtil primaryBeanUtil) {
		beanUtil = primaryBeanUtil;
		beanPackageName = beanUtil.getPackageName();
		Class<? extends BeanEnumeration> beanEnumerationClass = beanUtil.getBeanEnumerationClass();
		Enum<?>[] enumArray = (Enum[]) beanEnumerationClass.getEnumConstants();
		if (enumArray.length > 0 && enumArray[0] instanceof BeanEnumeration) {
			for (Enum<?> enumConstant : enumArray) {
				beanNormalizedNames.put(enumConstant.name().toLowerCase(), enumConstant.name());
				beanNames.add(enumConstant.name());
			}
		}
	}
	
	static public String getBeanPackageName() {
		return beanPackageName;
	}
	
	private static Map<String, VendorBeanHandlingUtilities> vendorBeanUtilities = new HashMap<String, VendorBeanHandlingUtilities>();
	
	static public void registerVendorBeanHandling(BeanUtil vendorBeanUtil) {
		VendorBeanHandlingUtilities vendorBeanHandlingUtilities = new VendorBeanHandlingUtilities();
		vendorBeanHandlingUtilities.vendorBeanUtil = vendorBeanUtil;
		vendorBeanHandlingUtilities.vendorBeanPackageName = vendorBeanUtil.getPackageName();
		vendorBeanHandlingUtilities.vendorBeanEnumerationClass = vendorBeanUtil.getBeanEnumerationClass();
		Enum<?>[] enumArray = (Enum[]) vendorBeanHandlingUtilities.vendorBeanEnumerationClass.getEnumConstants();
		vendorBeanHandlingUtilities.vendorBeanNormalizedNames = new HashMap<String, String>(enumArray.length);
		vendorBeanHandlingUtilities.vendorBeanNames = new HashSet<String>(enumArray.length);
		if (enumArray.length > 0 && enumArray[0] instanceof BeanEnumeration) {
			for (Enum<?> enumConstant : enumArray) {
				vendorBeanHandlingUtilities.vendorBeanNormalizedNames.put(enumConstant.name().toLowerCase(), enumConstant.name());
				vendorBeanHandlingUtilities.vendorBeanNames.add(enumConstant.name());
			}
			vendorBeanHandlingUtilities.vendorBeanConstant = (BeanEnumeration) enumArray[0];
		}
		vendorBeanUtilities.put(vendorBeanHandlingUtilities.vendorBeanPackageName, vendorBeanHandlingUtilities);
	}
	
	/*
	static public String normalizePojoClassName(String pojoClassName) {
		String normalizedName = pojoClassNormalizedName.get(pojoClassName.toLowerCase());
		return normalizedName;
	}
	*/
	
	static public String normalizePojoClassNameWithPackagePrefix(String pojoClassName) {
		String normalizedName = beanNormalizedNames.get(pojoClassName.toLowerCase());
		if (normalizedName != null) {
			return beanPackageName + "." + normalizedName;
		}
		Set<String> keys = vendorBeanUtilities.keySet();
		for (String vendorKey : keys) {
			VendorBeanHandlingUtilities utilities = vendorBeanUtilities.get(vendorKey);
			normalizedName = utilities.vendorBeanNormalizedNames.get(pojoClassName.toLowerCase());
			if (normalizedName != null) {
				return utilities.vendorBeanPackageName + "." + normalizedName;
			}
		}
		return null;
	}

	static public Object instantiatePojoObject(String pojoClassName) {
		return beanUtil.instantiatePojoObject(pojoClassName);
	}
	
	static public Object instantiatePojoObject(Class<?> pojoClass) {
		String packageName = pojoClass.getPackage().getName();
		if (packageName.equals(beanPackageName)) {
			return beanUtil.instantiatePojoObject(pojoClass);
		} else {
			VendorBeanHandlingUtilities utilities = vendorBeanUtilities.get(packageName);
			if (utilities != null) {
				return utilities.vendorBeanUtil.instantiatePojoObject(pojoClass);
			}
		}
		return null;
	}
	
	static public Identifiable instantiatePojoIdentifiable(Class<?> pojoClass, Object id) {
		String packageName = pojoClass.getPackage().getName();
		if (packageName.equals(beanPackageName)) {
			return beanUtil.instantiatePojoIdentifiable(pojoClass, id);
		} else {
			VendorBeanHandlingUtilities utilities = vendorBeanUtilities.get(packageName);
			if (utilities != null) {
				return utilities.vendorBeanUtil.instantiatePojoIdentifiable(pojoClass, id);
			}
		}
		return null;
	}

	static public Enum<?> instantiateEnum(String packageName, String enumSimpleClassName, String enumConstantName) {
		if (packageName.equals(beanPackageName)) {
			return beanUtil.instantiateEnum(packageName, enumSimpleClassName, enumConstantName);
		} else {
			VendorBeanHandlingUtilities utilities = vendorBeanUtilities.get(packageName);
			if (utilities != null) {
				return utilities.vendorBeanUtil.instantiateEnum(packageName, enumSimpleClassName, enumConstantName);
			}
		}
		return null;
	}
	
	static public EnumSet<?> instantiateEnumSet(String packageName, String enumSimpleClassName, Collection<String> enumConstantNames) {
		if (packageName.equals(beanPackageName)) {
			return beanUtil.instantiateEnumSet(packageName, enumSimpleClassName, enumConstantNames);
		} else {
			VendorBeanHandlingUtilities utilities = vendorBeanUtilities.get(packageName);
			if (utilities != null) {
				return utilities.vendorBeanUtil.instantiateEnumSet(packageName, enumSimpleClassName, enumConstantNames);
			}
		}
		return null;
	}
	
	static public SchemaHelper instantiateSchemaHelper() {
		return beanUtil.instantiateSchemaHelper();
	}

	static public Object constructId(Object objectId) {
		return beanUtil.constructId(objectId);
	}

	static public void assignManagedObjectProxy(Object pojoDependent, ManagedObjectProxy mop) {
		beanUtil.assignManagedObjectProxy(pojoDependent, mop);
	}

	static public void assignObjectId(Identifiable pojoIdentifiable, Object objectId) {
		beanUtil.assignObjectId(pojoIdentifiable, objectId);
	}
	
	static public void assignChangeToken(Identifiable pojoIdentifiable, Object changeToken) {
		beanUtil.assignChangeToken(pojoIdentifiable, changeToken);
	}

	static public void assignAttributeValue(Object pojoObject, String attributeName, Object value) {
		beanUtil.assignAttributeValue(pojoObject, attributeName, value);
	}
	
	public void assignPropertyValue(Object pojoObject, String attributeName, Object value) {
		beanUtil.assignPropertyValue(pojoObject, attributeName, value);
	}
	
	static public void assignEnumConstant(Object pojoObject, String attributeName, String packageName, String enumSimpleClassName, String enumName)  {
		beanUtil.assignEnumConstant(pojoObject, attributeName, packageName, enumSimpleClassName, enumName);
	}

	static public void addEnumToEnumSet(Object pojoObject, String attributeName, String packageName, String enumSimpleClassName, String enumConstantName) {
		beanUtil.addEnumToEnumSet(pojoObject, attributeName, packageName, enumSimpleClassName, enumConstantName);
	}
	
	static public ManagedObjectProxy getManagedObjectProxy(Object pojoObject) {
		return beanUtil.getManagedObjectProxy(pojoObject);
	}
		
	static public Object getObjectId(Object o) {
		return beanUtil.getObjectId(o);
	}

	static public Object getAttributeValue(Object pojoObject, String attributeName) {
		return beanUtil.getAttributeValue(pojoObject, attributeName);
	}

	public Object getPropertyValue(Object pojoObject, String attributeName) {
		return beanUtil.getPropertyValue(pojoObject, attributeName);
	}

	static public Collection<Identifiable> getIdentifiableCollection(Object pojoObject, String attributeName) {
		return beanUtil.getIdentifiableCollection(pojoObject, attributeName);
	}
	
	static public List<Object> getListOfObject(Object pojoObject, String attributeName) {
		return beanUtil.getListOfObject(pojoObject, attributeName);
	}
	
	static public Collection<Object> getObjectCollection(Object pojoObject, String attributeName) {
		return beanUtil.getObjectCollection(pojoObject, attributeName);
	}
	
	static public Collection<Persistent> getPersistentCollection(Object pojoObject, String attributeName) {
		return beanUtil.getPersistentCollection(pojoObject, attributeName);
	}
	
	static public Hashtable<Object, Object> getHashtable(Object pojoObject, String attributeName) {
		return beanUtil.getHashtable(pojoObject, attributeName);
	}
	
	static public String getEnumName(Object pojoObject, String attributeName) {
		return beanUtil.getEnumName(pojoObject, attributeName);
	}
	
	static public EnumSet<? extends Enum<?>> getEnumSet(Object pojoObject, String attributeName) {
		return beanUtil.getEnumSet(pojoObject, attributeName);
	}
	
	static public Set<Enum<?>> getSetOfEnum(Object pojoObject, String attributeName) {
		return beanUtil.getSetOfEnum(pojoObject, attributeName);
	}
	
	static public List<Persistent> getMultiContentParts(Object pojoObject, String attributeName) {
		return beanUtil.getMultiContentParts(pojoObject, attributeName);
	}

	static public boolean isInstanceOfObjectIdType(Object id) {
		return beanUtil.isInstanceOfObjectIdType(id);
	}
	
}
