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
package icom.jpa.rt;

import icom.info.BeanInfo;
import icom.jpa.IdentifiableDependent;
import icom.jpa.Manageable;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedIdentifiableDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.dao.DataAccessUtils;

public interface PersistenceContext {

	public ManagedIdentifiableProxy getEntityProxy(DataAccessStateObject dataAccessStateObject);
	
	public ManagedIdentifiableProxy getIdentifiableDependentProxy(String pojoSimpleClassName, DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName);
	
	public ManagedIdentifiableDependentProxy getIdentifiableDependentProxy(DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName);
	
	public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName);
	
	public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(String beanClassName, ManagedObjectProxy parent, String parentAttributeName);
	
	public ManagedDependentProxy manageIdentifiableDependent(IdentifiableDependent pojoIdentifiable, ManagedObjectProxy parent, String parentAttributeName);
	
	public ManagedDependentProxy manageNonIdentifiableDependent(Persistent pojoObject, ManagedObjectProxy parent, String parentAttributeName);
	
	public void registerNewEntityForPersist(Manageable entity);
	
	public void recacheIdentifiableDependent(ManagedIdentifiableProxy dependent);
	
	public ManagedObjectProxy detachFromCache(ManagedIdentifiableProxy obj);
	
	public Manageable getReference(Object objectId);
	
	public UserContext getUserContext() throws Exception;
	
	public DataAccessUtils getDataAccessUtils();
	
	public BeanInfo getBeanInfo(Object pojo);
	
	public DataAccessObject getDataAccessObject(Object pojo, Object pojoParent, String parentAttribute);
	
}
