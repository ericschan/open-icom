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
package icom.beehive;

import icom.IcomBeanUtil;
import icom.info.BeanEnumeration;
import icom.info.IdentifiableInfo;
import icom.info.beehive.BeehiveBeanEnumeration;
import icom.jpa.Identifiable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.EnumSet;

import javax.persistence.PersistenceException;

public class BeehiveBeanUtil extends IcomBeanUtil {
	
	private String beehivePackageName = BeehiveBeanEnumeration.BeehiveEnterprise.getPackageName(); // "icom.beehive"
	
	public Class<? extends BeanEnumeration> getBeanEnumerationClass() {
		return BeehiveBeanEnumeration.class;
	}

	public String getPackageName() {
		return beehivePackageName;
	}
	
	public Identifiable instantiatePojoIdentifiable(Class<?> pojoClass, Object objectId) {
		try {
			Field field = null;
			Class<?> cl = pojoClass;
			while (cl != Object.class) {
				try {
					field = cl.getDeclaredField(IdentifiableInfo.Attributes.id.name());
					break;
				} catch (NoSuchFieldException ex) { }
				cl = cl.getSuperclass();
			}
			Identifiable pojoIdentifiable = (Identifiable) pojoClass.newInstance();
			if (field != null) {
				field.setAccessible(true);
				Object id = constructId(objectId.toString());
				field.set(pojoIdentifiable, id);
			} else {
				throw new PersistenceException("cannot set id on " + pojoClass.getCanonicalName());
			}
			return pojoIdentifiable;
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of identifiable class " + pojoClass.getCanonicalName(), ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of identifiable class " + pojoClass.getCanonicalName(), ex);
		}
	}

	public Object instantiatePojoObject(Class<?> pojoClass) {
		try {
			Object pojoObject = pojoClass.newInstance();
			return pojoObject;
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of identifiable class " + pojoClass.getCanonicalName(), ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of identifiable class " + pojoClass.getCanonicalName(), ex);
		}
	}

	public Object instantiatePojoObject(String className) {
		try {
			Class<?> pojoClass = Class.forName(className);
			Object pojoObject = pojoClass.newInstance();
			return pojoObject;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("entity class not found " + className, ex);
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of identifiable class" + className, ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of identifiable class" + className, ex);
		}
	}

	public Enum<?> instantiateEnum(String packageName, String enumClassName, String enumConstantName) {
		String packagePrefix = packageName + ".";
		try {
			Class<?> enumClass = Class.forName(packagePrefix + enumClassName);
			Enum<?>[] enumArray = (Enum[]) enumClass.getEnumConstants();
			for (Enum<?> enumConstant : enumArray) {
				if (enumConstant.name().equals(enumConstantName)) {
					return enumConstant;
				}
			}
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("enum not found :" + packagePrefix + enumClassName, ex);
		}
		throw new PersistenceException("enum not found :" + packagePrefix + enumClassName);
	}

	public EnumSet<?> instantiateEnumSet(String packageName, String enumClassName, Collection<String> enumConstantNames) {
		String packagePrefix = packageName + ".";
		try {
			Class enumClass = Class.forName(packagePrefix + enumClassName);
			EnumSet es = EnumSet.noneOf(enumClass);
			Enum<?>[] enumArray = (Enum[]) enumClass.getEnumConstants();
			for (String enumConstantName : enumConstantNames) {
				for (Enum<?> enumConstant : enumArray) {
					if (enumConstant.name().equals(enumConstantName)) {
						es.add(enumConstant);
					}
				}
			}
			return es;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("enum not found :" + packagePrefix + enumClassName, ex);
		}
	}


}
