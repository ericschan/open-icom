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
package icom.jpa;

import icom.info.BeanInfo;
import icom.jpa.CachedState;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.dao.ProjectionManager;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;

public interface ManagedObjectProxy {
	
	enum Action {
		addObject,
		removeObject,
		modifyObject
	}
	
	public Persistent getPojoObject();
	
	public PersistenceContext getPersistenceContext();
	
	public ManagedObjectProxy manageDependent(Object pojoObject, String attributeName);
	
	public void detachDependent(Dependent pojoObject);
	
	public void detachIdentifiableDependent(IdentifiableDependent pojoObject);

	public String getProviderClassName();

	public void setProviderClassName(String csiInterfaceName);
	
	public BeanInfo getBeanInfo();
	
	public void markForSorted();
	
	public boolean isMarkedForSorted();
	
	public void markForLoopDetection();
	
	public boolean isMarkedForLoopDetection();
	
	public void load(String attributeName);
	
	public void load(String attributeName, Object parameter);
	
	public ProjectionManager getProviderProxy();

	public void addAttributeChangeRecord(String attributeName);
	
	public void addAttributeChangeRecord(String attributeName, Action action, 
			Object key, Object value);
	
	public Collection<ValueHolder> getAddedObjects(String attributeName);
	
	public Collection<ValueHolder> getRemovedObjects(String attributeName);
	
	public Collection<ValueHolder> getModifiedObjects(String attributeName);
	
	public boolean hasAttributeChanges();
	
	public boolean containsAttributeChangeRecord(String attributeName);
	
	public void sortDirtyPredecessors(ArrayList<ManagedObjectProxy> sorting);
	
	public void sortNewPredecessors(ArrayList<ManagedObjectProxy> sorting);
	
	public void sortDeletePredecessors(ArrayList<ManagedObjectProxy> sorting);
	
	public boolean isReady();
    
    public void setReady();
    
    public void resetReady();

    public boolean isNew();
    
    public void setNew();

    public boolean isDirty();
    
    public void setDirty();
    
    public void setDirty(String attributeName);
    
    public boolean isDirtyDependent();
    
    public void setDirtyDependent();
    
    public boolean isDeleted();
    
    public void setDeleted();

    public boolean isPooled(String attributeName);
    
    public boolean isPooled(String attributeName, Object parameter);
    
    public void setPooled();
    
    public boolean isInvalid();
    
    public void setInvalid();
    
    public void setCacheTime(long cacheTime);

    public long getCacheTime();

    public CachedState getCacheState();
    
    public int getClassOrdinal();
    
    public byte[] getStreamId();

	public void setStreamId(byte[] streamId);
	
	public String getEntityAttributeName();
	
	public boolean isManagedObjectEditable();
	
	public boolean isManagedObjectEditable(String attributeName);
	
	public DataAccessObject getDataAccessObject();
	
	public void setDataAccessObject(DataAccessObject dao);

}
