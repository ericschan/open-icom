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
package icom;

import icom.info.BeanEnumeration;
import icom.info.BeanInfo;
import icom.info.BeanUtil;
import icom.info.IcomBeanEnumeration;
import icom.info.IdentifiableInfo;
import icom.info.UnknownEntityInfo;
import icom.info.UnknownIdentifiableInfo;
import icom.jpa.Identifiable;
import icom.jpa.Manageable;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.ql.SchemaHelper;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;


public class IcomBeanUtil implements BeanUtil {

	private String icomPackageName = IcomBeanEnumeration.Community.getPackageName(); // "icom"

	SchemaHelper schemaHelper;

	public Class<? extends BeanEnumeration> getBeanEnumerationClass() {
		return IcomBeanEnumeration.class;
	}

	public String getPackageName() {
		return icomPackageName;
	}
	
	public BeanInfo getUnknownBeanInfo(Class<?> beanInfoClass) {
		if (Manageable.class.isAssignableFrom(beanInfoClass)) {
			return UnknownEntityInfo.getInstance();
		} else {
			return UnknownIdentifiableInfo.getInstance();
		}
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

	public EnumSet instantiateEnumSet(String packageName, String enumClassName, Collection<String> enumConstantNames) {
		String packagePrefix = packageName + ".";
		try {
			Class enumClass = Class.forName(packagePrefix + enumClassName);
			EnumSet es = EnumSet.noneOf(enumClass);
			Enum[] enumArray = (Enum[]) enumClass.getEnumConstants();
			for (String enumConstantName : enumConstantNames) {
				for (Enum enumConstant : enumArray) {
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

	public SchemaHelper instantiateSchemaHelper() {
		if (schemaHelper != null) {
			return schemaHelper;
		}
		try {
			String className = icomPackageName + "." + "IcomSchemaHelperImpl"; 
			Class<?> schemaHelperClass = Class.forName(className);
			schemaHelper = (SchemaHelper) schemaHelperClass.newInstance();
			return schemaHelper;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("entity class not found :" + "icom.IcomSchemaHelperImpl", ex);
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of identifiable class", ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of identifiable class", ex);
		}
	}

	public  Object constructId(Object objectId) {
		String className = icomPackageName + "." + IcomBeanEnumeration.Id.name();
		try {
			Class<?> idClass = Class.forName(className);
			Field field = idClass.getDeclaredField("objectId");
			field.setAccessible(true);
			Object id = idClass.newInstance();
			field.set(id, objectId);
			return id;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("entity class not found :" + className, ex);
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of Id class", ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of Id class", ex);
		} catch (NoSuchFieldException ex) { 
			throw new PersistenceException("no such field exception of Id class", ex);
		}
	}

	public Object constructChangeToken(Object changeTokenValue) {
		String className = icomPackageName + "." + IcomBeanEnumeration.ChangeToken.name();
		try {
			Class<?> changeTokenClass = Class.forName(className);
			Field field = changeTokenClass.getDeclaredField("token");
			field.setAccessible(true);
			Object changeToken = changeTokenClass.newInstance();
			field.set(changeToken, changeTokenValue);
			return changeToken;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("entity class not found :" + className, ex);
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of ChangeToken class", ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of ChangeToken class", ex);
		} catch (NoSuchFieldException ex) { 
			throw new PersistenceException("no such field exception of ChangeToken class", ex);
		}
	}

	public void assignManagedObjectProxy(Object pojoObject, ManagedObjectProxy mop) {
		boolean success = false;
		Class<?> cl = pojoObject.getClass();
		while (cl != Object.class) {
			try {
				Field field = cl.getDeclaredField("mop");
				field.setAccessible(true);
				try {
					field.set(pojoObject, mop);
					success = true;
					break;
				} catch (IllegalAccessException ex) {
					throw new PersistenceException("illegal access exception of identifiable class", ex);
				}
			} catch (NoSuchFieldException ex) { 
			}
			cl = cl.getSuperclass();
		}
		if (! success) {
			throw new PersistenceException("failed to assign managed object proxy");
		}
	}

	public void assignObjectId(Identifiable pojoIdentifiable, Object objectId) {
		boolean success = false;
		Class<?> cl = pojoIdentifiable.getClass();
		while (cl != Object.class) {
			try {
				Field field = cl.getDeclaredField(IdentifiableInfo.Attributes.id.name());
				field.setAccessible(true);
				try {
					Object id = null;
					if (objectId != null) {
						id = constructId(objectId);
					}
					field.set(pojoIdentifiable, id);
					success = true;
					break;
				} catch (IllegalAccessException ex) {
					throw new PersistenceException("illegal access exception of identifiable class " + cl.getCanonicalName(), ex);
				}
			} catch (NoSuchFieldException ex) { 
			}
			cl = cl.getSuperclass();
		}
		if (! success) {
			throw new PersistenceException("failed to assign object id");
		}
	}

	public void assignChangeToken(Identifiable pojoIdentifiable, Object changeTokenValue) {
		Class<?> cl = pojoIdentifiable.getClass();
		while (cl != Object.class) {
			try {
				Field field = cl.getDeclaredField(IdentifiableInfo.Attributes.changeToken.name());
				field.setAccessible(true);
				try {
					Object changeToken = null;
					if (changeTokenValue != null) {
						changeToken = constructChangeToken(changeTokenValue);
					}
					field.set(pojoIdentifiable, changeToken);
					break;
				} catch (IllegalAccessException ex) {
					throw new PersistenceException("illegal access exception of identifiable class " + cl.getCanonicalName(), ex);
				}
			} catch (NoSuchFieldException ex) { 
			}
			cl = cl.getSuperclass();
		}
	}

	public void assignAttributeValue(Object pojoObject, String attributeName, Object value) {
		Field field = null;
		Class<?> cl = pojoObject.getClass();
		while (cl != Object.class) {
			try {
				field = cl.getDeclaredField(attributeName);
				field.setAccessible(true);
				try {
					field.set(pojoObject, value);
					return;
				} catch (IllegalAccessException ex) {
					throw new PersistenceException("illegal access exception of a field " 
							+ attributeName + " on " +  pojoObject.getClass().getCanonicalName(), ex);
				}
			} catch (NoSuchFieldException ex) { }
			cl = cl.getSuperclass();
		}
		if (field == null) {
			throw new PersistenceException("no such field " +  attributeName + " on " + pojoObject.getClass().getCanonicalName());
		}
	}


	public void assignPropertyValue(Object pojoObject, String attributeName, Object value) {
		assignAttributeValue(pojoObject, attributeName, value);
	}

	public void assignEnumConstant(Object pojoObject, String attributeName, String packageName, String enumSimpleClassName, String enumName) {
		try {
			String packagePrefix = packageName + ".";
			Class<?> enumClass = Class.forName(packagePrefix + enumSimpleClassName);
			Enum<?>[] enumArray = (Enum[]) enumClass.getEnumConstants();
			for (Enum<?> enumConstant : enumArray) {
				if (enumConstant.name().equals(enumName)) {
					assignAttributeValue(pojoObject, attributeName, enumConstant);
					return;
				}
			}
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("enum not found :" + enumSimpleClassName, ex);
		}
	}

	public void addEnumToEnumSet(Object pojoObject, String attributeName, String packageName, String enumClassName, String enumConstantName) {
		String packagePrefix = packageName + ".";
		EnumSet pojoFlags = getEnumSet(pojoObject, attributeName);
		if (pojoFlags != null) {
			Enum<?> flag = instantiateEnum(packageName, enumClassName, enumConstantName);
			pojoFlags.add(flag);
		} else {
			try {
				Class enumClass = Class.forName(packagePrefix + enumClassName);
				pojoFlags = EnumSet.noneOf(enumClass);
				Enum<?> flag = instantiateEnum(packageName, enumClassName, enumConstantName);
				pojoFlags.add(flag);
				assignAttributeValue(pojoObject, attributeName, pojoFlags);
			} catch (ClassNotFoundException ex) {
				throw new PersistenceException("enum not found :" + packagePrefix + enumClassName, ex);
			}
		}
	}

	public Object getAttributeValue(Object pojoObject, String attributeName) {
		Field field = null;
		Class<?> cl = pojoObject.getClass();
		while (cl != Object.class) {
			try {
				field = cl.getDeclaredField(attributeName);
				break;
			} catch (NoSuchFieldException ex) { }
			cl = cl.getSuperclass();
		}
		if (field == null) {
			throw new PersistenceException("no such field " +  attributeName + " on " + pojoObject.getClass().getCanonicalName());
		}
		try {
			field.setAccessible(true);
			return field.get(pojoObject);
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of a field " 
					+ attributeName + " on " +  pojoObject.getClass().getCanonicalName(), ex);
		}
	}

	public Object getPropertyValue(Object pojoObject, String attributeName) {
		Object o = getAttributeValue(pojoObject, attributeName);
		return o;
	}

	public ManagedObjectProxy getManagedObjectProxy(Object pojoObject) {
		return (ManagedObjectProxy) getAttributeValue(pojoObject, "mop");
	}

	public Collection<Identifiable> getIdentifiableCollection(Object pojoObject, String attributeName) {
		return (Collection<Identifiable>) getAttributeValue(pojoObject, attributeName);
	}

	public List<Object> getListOfObject(Object pojoObject, String attributeName) {
		return (List<Object>) getAttributeValue(pojoObject, attributeName);
	}

	public Collection<Object> getObjectCollection(Object pojoObject, String attributeName) {
		return (Collection<Object>) getAttributeValue(pojoObject, attributeName);
	}

	public Collection<Persistent> getPersistentCollection(Object pojoObject, String attributeName) {
		return (Collection<Persistent>) getAttributeValue(pojoObject, attributeName);
	}

	public Hashtable<Object, Object> getHashtable(Object pojoObject, String attributeName) {
		return (Hashtable<Object, Object>) getAttributeValue(pojoObject, attributeName);
	}

	public String getEnumName(Object pojoObject, String attributeName) {
		Enum<?> e = (Enum<?>) getAttributeValue(pojoObject, attributeName);
		if (e != null) {
			return e.name();
		} else {
			return null;
		}
	}

	public EnumSet<? extends Enum<?>> getEnumSet(Object pojoObject, String attributeName) {
		return (EnumSet<? extends Enum<?>>) getAttributeValue(pojoObject, attributeName);
	}

	public Set<Enum<?>> getSetOfEnum(Object pojoObject, String attributeName) {
		return (Set<Enum<?>>) getAttributeValue(pojoObject, attributeName);
	}

	public List<Persistent> getMultiContentParts(Object pojoObject, String attributeName) {
		return (List<Persistent>) getAttributeValue(pojoObject, attributeName);
	}

	public Object getObjectId(Object o) {
		Class<?> objClass = o.getClass();
		if (objClass.getCanonicalName().equals(icomPackageName + "." + "Id")) {
			try {
				Field field = objClass.getDeclaredField("objectId");
				field.setAccessible(true);
				try {
					Object id = field.get(o);
					return id;
				} catch (IllegalAccessException ex) {
					throw new PersistenceException("illegal access exception of Id class", ex);
				}
			} catch (NoSuchFieldException ex) { 
				throw new PersistenceException("no objectId field ", ex);
			}
		}
		return null;
	}

	public boolean isInstanceOfObjectIdType(Object id) {
		try {
			Class<?> idClass = Class.forName(icomPackageName + "." + "Id");
			return (idClass.isInstance(id));
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("entity class not found :" + "icom.Id", ex);
		}
	}

}
