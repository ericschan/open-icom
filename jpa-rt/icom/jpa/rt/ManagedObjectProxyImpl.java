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

import icom.info.BeanHandler;
import icom.info.BeanInfo;
import icom.jpa.Dependent;
import icom.jpa.IdentifiableDependent;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.dao.ProjectionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

import javax.persistence.PersistenceException;

public abstract class ManagedObjectProxyImpl implements ManagedObjectProxy {
	
	Persistent pojo;
	DataAccessObject dao;
	BeanInfo beanInfo;
	PersistenceContextImpl context;
	String providerClassName;
	Hashtable<String, AttributeChangeRecord> changeRecords;
	byte[] streamId;
	
	private ManagedObjectProxyImpl() {
		
	}
	
	public ManagedObjectProxyImpl(PersistenceContext context, Persistent pojo, DataAccessObject dao) {
		this();
		this.pojo = pojo;
		this.context = (PersistenceContextImpl) context;
		this.dao = dao;
		this.beanInfo = this.context.getBeanInfo(pojo);
	}
	
	public Persistent getPojoObject() {
		return pojo;
	}
	
	public BeanInfo getBeanInfo() {
		return beanInfo;
	}
	
	public DataAccessObject getDataAccessObject() {
		return dao;
	}
	
	public void setDataAccessObject(DataAccessObject dao) {
		this.dao = (AbstractDAO) dao;
	}
	
	public PersistenceContextImpl getPersistenceContext() {
		return context;
	}
	
	public String getProviderClassName() {
		return providerClassName;
	}

	public void setProviderClassName(String providerClassName) {
		this.providerClassName = providerClassName;
	}
	
	public abstract ProjectionManager getProviderProxy();

	public ManagedObjectProxy manageDependent(Object dependent, String attributeName) {
		ManagedDependentProxy proxy = (ManagedDependentProxy) BeanHandler.getManagedObjectProxy(dependent);
		if (proxy != null) {
			throw new PersistenceException("Dependent may be already managed under another managed object");
		}
		return context.registerNewDependentForPersist(pojo, (Dependent) dependent, attributeName);
	}
	
	public void detachDependent(Dependent pojoObject) {
		BeanHandler.assignManagedObjectProxy(pojoObject, null);
	}
	
	public void detachIdentifiableDependent(IdentifiableDependent pojoObject) {
		BeanHandler.assignManagedObjectProxy(pojoObject, null);
	}
	
	void putAttributeChangeRecord(AttributeChangeRecord record) {
		if (changeRecords == null) {
			synchronized(this) {
				if (changeRecords == null) {
					changeRecords = new Hashtable<String, AttributeChangeRecord>();
				}
			}
		} 
		changeRecords.put(record.attributeName, record);
	}
	
	public void addAttributeChangeRecord(String attributeName) {
		if (! containsAttributeChangeRecord(attributeName)) {
			putAttributeChangeRecord(new AttributeChangeRecord(attributeName));
		}
	}
	
	public void addAttributeChangeRecord(String attributeName, Action action, 
			Object key, Object value) {
		AttributeChangeRecord acr = getAttributeChangeRecord(attributeName);
		if (acr == null) {
			acr = new AttributeChangeRecord(attributeName);
			putAttributeChangeRecord(acr);
		}
		if (action.equals(Action.addObject)) {
			acr.putObject(key, value);
		} else if (action.equals(Action.removeObject)) {
			acr.removeObject(key, value);
		} else if (action.equals(Action.modifyObject)) {
			acr.modifyObject(key, value);
		}
	}
	
	AttributeChangeRecord getAttributeChangeRecord(String attributeName) {
		if (changeRecords == null) {
			return null;
		}
		return changeRecords.get(attributeName);
	}	
	
	public boolean containsAttributeChangeRecord(String attributeName) {
		if (changeRecords != null) {
			return changeRecords.containsKey(attributeName);
		} else {
			return false;
		}
	}
	
	public boolean hasAttributeChanges() {
		if (changeRecords != null) {
			return changeRecords.size() > 0;
		} else {
			return false;
		}
	}
	
	public Collection<ValueHolder> getAddedObjects(String attributeName) {
		if (changeRecords != null) {
			AttributeChangeRecord acr = changeRecords.get(attributeName);
			if (acr != null) {
				Collection<ValueHolder> vhCol = acr.getAddedObjects();
				if (vhCol != null) {
					return Collections.unmodifiableCollection(vhCol);
				}
			}
		}
		return null;
	}
	
	public Collection<ValueHolder> getRemovedObjects(String attributeName) {
		if (changeRecords != null) {
			AttributeChangeRecord acr = changeRecords.get(attributeName);
			if (acr != null) {
				Collection<ValueHolder> vhCol = acr.getRemovedObjects();
				if (vhCol != null) {
					return Collections.unmodifiableCollection(vhCol);
				}
			}
		}
		return null;
	}
	
	public Collection<ValueHolder> getModifiedObjects(String attributeName) {
		if (changeRecords != null) {
			AttributeChangeRecord acr = changeRecords.get(attributeName);
			if (acr != null) {
				Collection<ValueHolder> vhCol = acr.getModifiedObjects();
				if (vhCol != null) {
					return Collections.unmodifiableCollection(vhCol);
				}
			}
		}
		return null;
	}
	
	public void sortDirtyPredecessors(ArrayList<ManagedObjectProxy> sorting) {
		getBeanInfo().sortDirtyPredecessors(sorting, this);
	}
	
	public void sortNewPredecessors(ArrayList<ManagedObjectProxy> sorting) {
		getBeanInfo().sortNewPredecessors(sorting, this);
	}
	
	public void sortDeletePredecessors(ArrayList<ManagedObjectProxy> sorting) {
		getBeanInfo().sortDeletePredecessors(sorting, this);
	}

	public byte[] getStreamId() {
		return streamId;
	}

	public void setStreamId(byte[] streamId) {
		this.streamId = streamId;
	}
	
	abstract public String getEntityAttributeName();
	
	public void markForSorted() {
		
	}
	
	public void resetMarkForSorted() {
		
	}
	
	public abstract boolean isMarkedForSorted();
	
	public void markForLoopDetection() {
		
	}
	
	public void resetMarkForLoopDetection() {
		
	}
	
	public abstract boolean isMarkedForLoopDetection();
	
	/**
     * the object is committed
     */
    public void committed() {
		synchronized (this) {
			resetMarkForSorted();
    		resetMarkForLoopDetection();
			if (changeRecords != null) {
				changeRecords.clear();
			}
			if (isNew() || isDirty()) {
				getDataAccessObject().committedObject(getPojoObject());
				getProviderProxy().committed(this);
				resetReady();
			} else if (isDeleted()) {
				setInvalid();
				context.detachHierarchy(this);
			}
		}
	}
	
	/**
     * the object is rolled back
     */
    public void rolledback() {
		synchronized (this) {
			resetMarkForSorted();
    		resetMarkForLoopDetection();
			if (changeRecords != null) {
				changeRecords.clear();
			}
			getDataAccessObject().rolledbackObject(getPojoObject());
			context.detachHierarchy(this);
		}
	}
	
	/**
     * undo the changes to the object
     */
    public void undo() {
    	synchronized (this) {
    		resetMarkForSorted();
    		resetMarkForLoopDetection();
			if (changeRecords != null) {
				changeRecords.clear();
			}
			if (isNew()) {
				getBeanInfo().clearObject(getPojoObject());
				setInvalid();
			} else if (isDirty() || isDeleted()) {
				getBeanInfo().clearObject(getPojoObject());
				setPooled();
			}
		}
    }  
	
}
