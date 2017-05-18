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
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public interface BeanUtil {
	
	public Class<? extends BeanEnumeration> getBeanEnumerationClass();
	
	public String getPackageName();
	
	public BeanInfo getUnknownBeanInfo(Class<?> beanInfoClass);
	
	public Identifiable instantiatePojoIdentifiable(Class<?> pojoClass, Object objectId);

	public Object instantiatePojoObject(Class<?> pojoClass);

	public Object instantiatePojoObject(String className);

	public Enum<?> instantiateEnum(String icomPackageName, String enumClassName, String enumConstantName);

	public EnumSet<?> instantiateEnumSet(String icomPackageName, String enumClassName, Collection<String> enumConstantNames);

	public SchemaHelper instantiateSchemaHelper();

	public  Object constructId(Object objectId);

	public Object constructChangeToken(Object changeTokenValue);

	public void assignManagedObjectProxy(Object pojoObject, ManagedObjectProxy mop);

	public void assignObjectId(Identifiable pojoIdentifiable, Object objectId);

	public void assignChangeToken(Identifiable pojoIdentifiable, Object changeTokenValue);

	public void assignAttributeValue(Object pojoObject, String attributeName, Object value);

	public void assignPropertyValue(Object pojoObject, String attributeName, Object value);
	
	public void assignEnumConstant(Object pojoObject, String attributeName, String packageName, String enumSimpleClassName, String enumName);

	public void addEnumToEnumSet(Object pojoObject, String attributeName, String packageName, String enumClassName, String enumConstantName);

	public Object getAttributeValue(Object pojoObject, String attributeName);

	public Object getPropertyValue(Object pojoObject, String attributeName);

	public ManagedObjectProxy getManagedObjectProxy(Object pojoObject);

	public Collection<Identifiable> getIdentifiableCollection(Object pojoObject, String attributeName);

	public List<Object> getListOfObject(Object pojoObject, String attributeName);

	public Collection<Object> getObjectCollection(Object pojoObject, String attributeName);

	public Collection<Persistent> getPersistentCollection(Object pojoObject, String attributeName);

	public Hashtable<Object, Object> getHashtable(Object pojoObject, String attributeName);

	public String getEnumName(Object pojoObject, String attributeName);

	public EnumSet<? extends Enum<?>> getEnumSet(Object pojoObject, String attributeName);

	public Set<Enum<?>> getSetOfEnum(Object pojoObject, String attributeName);

	public List<Persistent> getMultiContentParts(Object pojoObject, String attributeName);

	public Object getObjectId(Object o);

	public boolean isInstanceOfObjectIdType(Object id);
}
