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

import icom.info.BeanHandler;
import icom.jpa.Identifiable;
import icom.jpa.dao.DataAccessUtils;
import icom.jpa.rt.PersistenceContext;
import icom.ql.QueryContext;
import icom.ql.SchemaHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class IcomSchemaHelperImpl implements SchemaHelper {
	
	static IcomSchemaHelperImpl singleton = new IcomSchemaHelperImpl();
	
	static public IcomSchemaHelperImpl getInstance() {
		return singleton;
	}
	
	public List<Object> resolveResultList(QueryContext queryContext, Object context, List<Object> resultList) {
		PersistenceContext persistenceContext = (PersistenceContext) context;
		DataAccessUtils utils = persistenceContext.getDataAccessUtils();
		List<Object> pojoResultList = utils.resolveResultList(queryContext, persistenceContext, resultList);
		return pojoResultList;
	}
	
	public Object resolveParameterValueType(Object context, Object value, Object type) {
		PersistenceContext persistenceContext = (PersistenceContext) context;
		DataAccessUtils utils = persistenceContext.getDataAccessUtils();
		if (value instanceof EntityTrait) {
		    EntityTrait entity = (EntityTrait) value;
		    ObjectIdTrait id = (ObjectIdTrait) entity.getObjectId();
    		Object objectId = id.getObjectId();
    		return utils.resolveAttributeValueEntity(persistenceContext, objectId);
		} else if (EntityAddressTrait.class.isAssignableFrom((Class<?>) type)) {
		    if (value instanceof EntityAddressTrait) {
		        Object providerEntityAddress = null;
		        EntityAddressTrait address = (EntityAddressTrait) value;
    		    URI uri = address.getAddress();
    		    String addressType = address.getAddressType();
    		    providerEntityAddress = utils.resolveAttributeValueEntityAddress(persistenceContext, uri, addressType);
    		    return providerEntityAddress;
		    } else if (value instanceof URI) {
		        Object providerEntityAddress = utils.resolveAttributeValueEntityAddress(persistenceContext, value);
                return providerEntityAddress;
            } else if (value instanceof String) {
                Object providerEntityAddress = utils.resolveAttributeValueEntityAddress(persistenceContext, value);
                return providerEntityAddress;
            }
    	} else if (ParticipantTrait.class.isAssignableFrom((Class<?>) type)) {
    	    if (value instanceof ParticipantTrait) {
        		Object providerParticipant = null;
        		ParticipantTrait pojoParticipant = (ParticipantTrait) value;
        		if (pojoParticipant.getParticipant() != null) {
        		    EntityTrait entity = (EntityTrait) pojoParticipant.getParticipant();
        		    ObjectIdTrait id = (ObjectIdTrait) entity.getObjectId();
        			Object objectId = id.getObjectId();
        			providerParticipant = utils.resolveAttributeValueParticipant(persistenceContext, objectId);
        		} else if (pojoParticipant.getAddress() != null) {
        			providerParticipant = utils.resolveAttributeValueParticipant(persistenceContext, pojoParticipant.getAddress());
        		} else {
        			throw new QLException("Not Supported");
        		}
        		return providerParticipant;
    	    } else if (value instanceof URI) {
    	        Object providerParticipant = utils.resolveAttributeValueParticipant(persistenceContext, value);
                return providerParticipant;
            } else if (value instanceof String) {
                Object providerParticipant = utils.resolveAttributeValueParticipant(persistenceContext, value);
                return providerParticipant;
            }
    	} else if (isEnumType(value)) {
    		return ((Enum<?>)value).name();
    	} 
		return utils.resolveAttributeValueType(persistenceContext, value, type);
	}
	
	public boolean isEntityClass(Object type) {
		if (EntityTrait.class.isAssignableFrom((Class<?>) type)) {
			return true;
		} else if (Identifiable.class.isAssignableFrom((Class<?>) type)) {
			return true;
		}
		return false;
	}
	
	public boolean isEmbeddable(Object type) {
		if (isEntityClass(type)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isEnumType(Object type) {
		if (type instanceof Enum) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean isMapClass(Object type) {
		if (Map.class.isAssignableFrom((Class<?>) type)) {
			return true;
		}
		return false;
	}
	
	public boolean isEmbeddedAttribute(Object ownerClass, String attribute) {
		if (isMapClass(ownerClass)) {
			return true;
		}
		Class<?> attrType = (Class<?>) resolveAttribute(ownerClass, attribute);
		if (isEntityClass(attrType)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isSimpleStateAttribute(Object ownerClass, String attribute) {
		Class<?> attrType = (Class<?>) resolveAttribute(ownerClass, attribute);
		if (isEntityClass(attrType)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isRelationship(Object ownerClass, String attribute) {
		Class<?> attrType = (Class<?>) resolveAttribute(ownerClass, attribute);
		if (isEntityClass(attrType)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isSingleValuedRelationship(Object ownerClass, String attribute) {
		if (isRelationship(ownerClass, attribute) && ! isCollectionValuedAttribute((Class<?>) ownerClass, attribute)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isCollectionValuedRelationship(Object ownerClass, String attribute) {
		if (isRelationship(ownerClass, attribute) && isCollectionValuedAttribute((Class<?>) ownerClass, attribute)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isCollectionValuedAttribute(Class<? extends Object> ownerClass, String attribute) {
		try {
			Field field = ownerClass.getField(attribute);
			field.setAccessible(true);
			Class<? extends Object> type = field.getType();
			if (Iterable.class.isAssignableFrom(type)) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchFieldException ex) {
			throw new QLException("Not Supported", ex);
		} catch (SecurityException ex) {
			throw new QLException("Not Supported", ex);
		}
	}
	
	public boolean isOrderableType(Object type) {
		return isEntityClass(type);
	}
	
	public String getTypeName(Object type) {
		if (type instanceof Class) {
			return ((Class<?>)type).getCanonicalName();
		} else {
			return type.toString();
		}
	}
	
	public Object resolveSchema(String pojoSchemaName) {
		String normalizedName = BeanHandler.normalizePojoClassNameWithPackagePrefix(pojoSchemaName);
		try {
			Class<?> pojoClass = Class.forName(normalizedName);
			return pojoClass;
		} catch (ClassNotFoundException ex) {
		    ex.printStackTrace();
			throw new QLException("Not Supported", ex);
		}
    }
    
	public Object resolveAttribute(Object ownerClass, String attribute) {
		Class<?> cl = (Class<?>) ownerClass;
		while (cl != Object.class) {
			try {
				Field field = cl.getDeclaredField(attribute);
				field.setAccessible(true);
				if (field != null) {
					Class<?> fieldClass = field.getType();
					if (Collection.class.isAssignableFrom(fieldClass)) {
						Type genericType = field.getGenericType();
						if (genericType != null && genericType instanceof ParameterizedType) {
							ParameterizedType paramType = (ParameterizedType) genericType;
							Type[] types = paramType.getActualTypeArguments();
							if (types.length > 0) {
								Class<?> genericClass = (Class<?>) types[0];
								return genericClass;
							}
						}
					} else {					
						return fieldClass;
					}
				}
			} catch (NoSuchFieldException ex) { 
			}
			cl = cl.getSuperclass();
		}
		throw new QLException("No such field exception " + attribute + " in " + ((Class<?>) ownerClass).getCanonicalName());
    }
	
	public Object resolveTypeName(String typeName) {
		try {
			Class<?> pojoClass = Class.forName(typeName);
			return pojoClass;
		} catch (ClassNotFoundException ex) {
			throw new QLException("Not Supported", ex);
		}
    }
    
	public Object resolveEnumTypeName(String name) {
    	Object type = resolveTypeName(name);
        if (type == null) {
            // check for inner enum type
            int index = name.lastIndexOf('.');
            if (index != -1) {
                name = name.substring(0, index) + '$' + name.substring(index+1);
                type = resolveTypeName(name);
            }
        }
        return type;
    }
    
	public Object resolveEnumConstant(Object type, String constant) {
		Class<?> clazz = null;
		if (type instanceof Class) {
			clazz = (Class<?>) type;
		}
		Enum<?>[] constants = (Enum[]) clazz.getEnumConstants();
		if (constants != null) {
			for (Enum<?> con : constants) {
				if (con.name().equals(constant)) {
					return con;
				}
			}
		}
		return null;
    }
	
}
