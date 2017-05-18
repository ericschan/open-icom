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
package icom.jpql.csi;

import icom.QLException;
import icom.jpa.csi.CsiDataAccessUtilsImpl;
import icom.jpql.csi.filters.EntityPredicateFactory;
import icom.jpql.csi.filters.PredicateFactoryTable;
import icom.ql.QueryContext;
import icom.ql.SchemaHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import oracle.csi.Addressable;
import oracle.csi.CollabId;
import oracle.csi.CsiException;
import oracle.csi.DateTime;
import oracle.csi.EmailParticipant;
import oracle.csi.EntityAddress;
import oracle.csi.EntityHandle;
import oracle.csi.IdentifiableHandle;
import oracle.csi.Participant;
import oracle.csi.controls.EntityUtils;
import oracle.csi.projections.Projection;

public class CsiSchemaHelperImpl implements SchemaHelper {
	
	static CsiSchemaHelperImpl singleton = new CsiSchemaHelperImpl();
	
	static public CsiSchemaHelperImpl getInstance() {
		return singleton;
	}
	
	public List<Object> resolveResultList(QueryContext queryContext, Object context, List<Object> resultList) {
		return resultList;
	}
	
	public Object resolveAttributeValueEntityAddress(Object context, URI uri, String addressType) {
        EntityAddress address = new EntityAddress();
        address.setAddress(uri);
        address.setAddressType(addressType);
        return address;
    }
	
	public Object resolveParameterValueType(Object context, Object value, Object type) {
		if (isEntityClass(type)) {
			if (value instanceof IdentifiableHandle) {
				return value;
			}
		} else if (isEntityAddressClass(type)) {
            if (value instanceof URI) {
                URI uri = (URI) value;
                EntityAddress address = new EntityAddress();
                address.setAddress(uri);
                return address;
            } else if (value instanceof String) {
                try {
                    URI uri = new URI((String) value);
                    EntityAddress address = new EntityAddress();
                    address.setAddress(uri);
                    return address;
                } catch (URISyntaxException ex) {
                    throw new QLException("Not Supported", ex);
                }
            }
		} else if (isParticipantClass(type)) {
			if (value instanceof URI) {
				Participant csiparticipant = EmailParticipant.createParticipant((URI) value, null);
				return csiparticipant;
			} else if (value instanceof String) {
                String str = (String) value;
                if (CsiDataAccessUtilsImpl.hasBeehiveObjectIdFormat(str)) {
                    CollabId collabId = CollabId.parseCollabId(str);
                    EntityUtils utils = EntityUtils.getInstance();
                    EntityHandle handle = (EntityHandle) utils.createHandle(collabId);
                    Addressable csiEntity = null;
                    try {
                        csiEntity = (Addressable) utils.load(handle, Projection.EMPTY);
                    } catch (CsiException ex) {
                        throw new QLException("Not Supported", ex);
                    }
                    Participant csiparticipant = new Participant();
                    csiparticipant.setParticipant(csiEntity);
                    return csiparticipant;
                } else {
                    try {
                        URI uri = new URI((String) value);
                        Participant csiparticipant = new Participant();
                        csiparticipant.setAddress(uri);
                        return csiparticipant;
                    } catch (URISyntaxException ex) {       
                        throw new QLException("Not Supported", ex);
                    }
                }
            }
		} else if (isDateClass(type)) {
			if (value instanceof Date) {
				return value;
			}
		} else if (isDateTimeClass(type)) {
			if (value instanceof DateTime) {
				return value;
			}
		} else if (isLocaleClass(type)) {
			if (value instanceof Locale) {
				return value;
			}
		}
		return EntityPredicateFactory.resolveAttributeValueWithType(value, type);
	}

	public boolean isEntityClass(Object type) {
		return EntityPredicateFactory.isEntityClass(type);
	}
	
	public boolean isEntityAddressClass(Object type) {
        return EntityPredicateFactory.isEntityAddressClass(type);
    }
	
	public boolean isParticipantClass(Object type) {
		return EntityPredicateFactory.isParticipantClass(type);
	}
	
	public boolean isDateClass(Object type) {
		return EntityPredicateFactory.isDateClass(type);
	}
	
	public boolean isDateTimeClass(Object type) {
		return EntityPredicateFactory.isDateTimeClass(type);
	}
	
	public boolean isLocaleClass(Object type) {
		return EntityPredicateFactory.isLocaleClass(type);
	}
	
	public boolean isMapClass(Object type) {
		if (Map.class.isAssignableFrom((Class<?>) type)) {
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
		if (isRelationship(ownerClass, attribute) && ! isCollectionValuedAttribute(ownerClass, attribute)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isCollectionValuedRelationship(Object ownerClass, String attribute) {
		if (isRelationship(ownerClass, attribute) && isCollectionValuedAttribute(ownerClass, attribute)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getTypeName(Object type) {
		if (type instanceof Class) {
			return ((Class<?>)type).getCanonicalName();
		} else {
			return type.toString();
		}
	}
	
	public Object resolveSchema(String schemaName) {
		PredicateFactoryTable pft = PredicateFactoryTable.getInstance();
		EntityPredicateFactory epf = pft.getPredicateFactory(schemaName);
		String className = epf.resolveClassName();
		String canonicalClassName = "oracle.csi." + className;
		try {
			Class<?> pojoClass = Class.forName(canonicalClassName);
			return pojoClass;
		} catch (ClassNotFoundException ex) {
			throw new QLException("Not Supported", ex);
		}
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
    
	public Object resolveAttribute(Object ownerClass, String attribute) {
		if (isMapClass(ownerClass)) {
			return String.class;
		}
		String className = null;
		if (ownerClass instanceof Class) {
			className = ((Class<?>)ownerClass).getSimpleName();
		} else {
			className = ownerClass.toString();
		}
		PredicateFactoryTable pft = PredicateFactoryTable.getInstance();
		EntityPredicateFactory epf = pft.getPredicateFactory(className);
		return epf.resolveAttributeType(attribute);
    }
	
	public boolean isCollectionValuedAttribute(Object ownerClass, String attribute) {
		String className = null;
		if (ownerClass instanceof Class) {
			className = ((Class<?>)ownerClass).getSimpleName();
		} else {
			className = ownerClass.toString();
		}
		PredicateFactoryTable pft = PredicateFactoryTable.getInstance();
		EntityPredicateFactory epf = pft.getPredicateFactory(className);
		return epf.isCollectionValuedAttribute((Class<?>)ownerClass, attribute);
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
	
	public boolean isOrderableType(Object type) {
        return isEntityClass(type);
    }
	
}
