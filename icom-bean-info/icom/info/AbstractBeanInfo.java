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

import icom.jpa.IdentifiableDependent;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.dao.ManagedObjectAttribute;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.PersistenceException;


public abstract class AbstractBeanInfo extends BeanHandler implements BeanInfo, ManagedObjectAttribute {
	
	// the creation and deletion predecessors should be configurable by service providers
	protected HashSet<String> creationPredecessors = new HashSet<String>();
	
	protected HashSet<String> creationPredecessorCollections = new HashSet<String>();
	
	protected HashSet<String> deletionPredecessors = new HashSet<String>();
	
	protected HashSet<String> deletionPredecessorCollections = new HashSet<String>();

	// the referenced and embedded object property names should be specified by pojo providers
	protected HashSet<String> referencedObjects = new HashSet<String>();
	
	protected HashSet<String> embeddedObjects = new HashSet<String>();
	
	protected HashSet<String> embeddedObjectCollections = new HashSet<String>();
	
	protected HashSet<String> cascadePersistObjects = new HashSet<String>();
	
	protected HashSet<String> cascadePersistObjectCollections = new HashSet<String>();
	
	protected HashSet<String> editableByViewer = new HashSet<String>();
	
	protected HashSet<String> inverseOfOwnedProperties = new HashSet<String>();
	
	public boolean hasAttribute(String name) {
	    return true; // TODO
	}
	
	public boolean isInverseOfMappedByProperty(String propertyName) {
		return inverseOfOwnedProperties.contains(propertyName);
	}
	
	public boolean isEditableByViewer(String propertyName) {
		return editableByViewer.contains(propertyName);
	}
	
	public void sortNewPredecessors(ArrayList<ManagedObjectProxy> sorting, ManagedObjectProxy proxy) {
		if (! proxy.isMarkedForSorted()) {
			if (proxy.isMarkedForLoopDetection()) {
				throw new PersistenceException("Detected a loop in dependency");
			}
			proxy.markForLoopDetection();
			sortNewPredecessorsRecursive(sorting, proxy);
		}
	}
	
	protected void sortNewPredecessorsRecursive(ArrayList<ManagedObjectProxy> sorting, ManagedObjectProxy proxy) {
		sortNewPredecessorsRecursive(sorting, proxy, creationPredecessors, creationPredecessorCollections);
		if (! proxy.isMarkedForSorted()) {
			sorting.add(proxy);
			proxy.markForSorted();
		}
	}
	
	protected void sortNewPredecessorsRecursive(ArrayList<ManagedObjectProxy> sorting, ManagedObjectProxy proxy,
			HashSet<String> creationPredecessors, HashSet<String> creationPredecessorCollections) {	
		for (String predecessorName : creationPredecessors) {
			Persistent pojo = proxy.getPojoObject();
			Persistent predecessor = (Persistent) getAttributeValue(pojo, predecessorName);
			if (predecessor != null) {
			    ManagedObjectProxy predecessorProxy = predecessor.getManagedObjectProxy();
				if (predecessorProxy.isNew()) {
					predecessorProxy.sortNewPredecessors(sorting);
				}
			}
		}
		for (String predecessorCollectionName : creationPredecessorCollections) {
			Collection<ValueHolder> valueHolders = proxy.getAddedObjects(predecessorCollectionName);
			if (valueHolders != null) {
				for (ValueHolder valueHolder : valueHolders) {
					Persistent predecessor = (Persistent) valueHolder.getValue();
					ManagedObjectProxy predecessorProxy = predecessor.getManagedObjectProxy();
					if (predecessorProxy.isNew()) {
						predecessorProxy.sortNewPredecessors(sorting);
					}
				}
			}
		}
	}
	
	public void sortDeletePredecessors(ArrayList<ManagedObjectProxy> sorting, ManagedObjectProxy proxy) {
		if (! proxy.isMarkedForSorted()) {
			if (proxy.isMarkedForLoopDetection()) {
				return;
			}
			proxy.markForLoopDetection();
			sortDeletePredecessorsRecursive(sorting, proxy);
		}
	}
	
	protected void sortDeletePredecessorsRecursive(ArrayList<ManagedObjectProxy> sorting, ManagedObjectProxy proxy) {
		sortDeletePredecessorsRecursive(sorting, proxy, deletionPredecessors, deletionPredecessorCollections);
		if (! proxy.isMarkedForSorted()) {
			sorting.add(proxy);
			proxy.markForSorted();
		}
	}
	
	protected void sortDeletePredecessorsRecursive(ArrayList<ManagedObjectProxy> sorting, ManagedObjectProxy proxy,
			HashSet<String> deletionPredecessors, HashSet<String> deletionPredecessorCollections) {
		for (String predecessorName : deletionPredecessors) {
			Persistent pojo = proxy.getPojoObject();
			Persistent predecessor = (Persistent) getAttributeValue(pojo, predecessorName);
			if (predecessor != null) {
			    ManagedObjectProxy predecessorProxy = predecessor.getManagedObjectProxy();
				if (predecessorProxy != null && predecessorProxy.isDeleted()) {
					predecessorProxy.sortDeletePredecessors(sorting);
				}
			}
		}
		for (String predecessorCollectionName : deletionPredecessorCollections) {
			Collection<ValueHolder> valueHolders = proxy.getRemovedObjects(predecessorCollectionName);
			if (valueHolders != null) {
				for (ValueHolder valueHolder : valueHolders) {
					Persistent removedPojo = (Persistent) valueHolder.getValue();
					ManagedObjectProxy removedProxy = removedPojo.getManagedObjectProxy();
					if (removedProxy != null && removedProxy.isDeleted()) {
						removedProxy.sortDeletePredecessors(sorting);
					}
				}
			}
		}
	}
	
	public void sortDirtyPredecessors(ArrayList<ManagedObjectProxy> sorting, ManagedObjectProxy proxy) {
		sorting.add(proxy);
	}

	public void attachHierarchy(ManagedObjectProxy mop) {
		attachHierarchyRecursive(mop);
	}
	
	protected void attachHierarchyRecursive(ManagedObjectProxy mop) {
		attachHierarchyRecursive(mop, embeddedObjects, embeddedObjectCollections);
		cascadePersist(mop, cascadePersistObjects, cascadePersistObjectCollections);
	}
	
	protected void attachHierarchyRecursive(ManagedObjectProxy mop, HashSet<String> embeddedObjects, HashSet<String> embeddedObjectCollections) {
		cascadePersist(mop, embeddedObjects, embeddedObjectCollections);
	}
	
	protected void cascadePersist(ManagedObjectProxy mop, HashSet<String> cascadePersistObjects, HashSet<String> cascadePersistObjectCollections) {
		PersistenceContext context = mop.getPersistenceContext();
		for (String cascadePersistObjectName : cascadePersistObjects) {
			Persistent cascadePersistObject = (Persistent) BeanHandler.getAttributeValue(mop.getPojoObject(), cascadePersistObjectName);
			if (cascadePersistObject != null) {
				BeanInfo beanInfo = context.getBeanInfo(cascadePersistObject);
				beanInfo.attachDependentHierarchy(mop, cascadePersistObject, cascadePersistObjectName);
			}
		}
		for (String cascadePersistObjectCollectionName : cascadePersistObjectCollections) {
			Collection<Persistent> cascadePersistObjectCollection = BeanHandler.getPersistentCollection(mop.getPojoObject(), cascadePersistObjectCollectionName);
			if (cascadePersistObjectCollection != null) {
				for (Persistent cascadePersistObject : cascadePersistObjectCollection) {
					BeanInfo beanInfo = context.getBeanInfo(cascadePersistObject);
					beanInfo.attachDependentHierarchy(mop, cascadePersistObject, cascadePersistObjectCollectionName);
				}
			}
		}
	}

	public final ManagedDependentProxy attachDependentHierarchy(ManagedObjectProxy parentProxy, 
			Persistent dependent, String parentAttributeName) {
		PersistenceContext context = parentProxy.getPersistenceContext();
		BeanInfo info = context.getBeanInfo(dependent);
		if (this != info) {
			return info.attachDependentHierarchy(parentProxy, dependent, parentAttributeName);
		} else {
			return attachDependentHierarchyRecursive(parentProxy, dependent, parentAttributeName);
		}
	}
	
	protected ManagedDependentProxy attachDependentHierarchyRecursive(ManagedObjectProxy parentProxy, 
			Persistent dependent, String parentAttributeName) {
		ManagedDependentProxy dependentProxy = getManagedDependentProxy(parentProxy, dependent, parentAttributeName);
		attachHierarchyRecursive(dependentProxy);
		return dependentProxy;
	}
	
	protected ManagedDependentProxy getManagedDependentProxy(ManagedObjectProxy parentProxy, 
			Persistent dependent, String parentAttributeName) {
		PersistenceContext context = parentProxy.getPersistenceContext();
		if (dependent instanceof IdentifiableDependent) {
			ManagedDependentProxy dependentProxy = context.manageIdentifiableDependent((IdentifiableDependent) dependent, parentProxy, parentAttributeName);
			return dependentProxy;
		} else {
			ManagedDependentProxy dependentProxy = context.manageNonIdentifiableDependent(dependent, parentProxy, parentAttributeName);
			return dependentProxy;
		}
	}
	
	public final void detachHierarchy(ManagedObjectProxy mop) {
		if (mop != null) {
			Persistent pojo = mop.getPojoObject();
			assignAttributeValue(pojo, AbstractBeanInfo.Attributes.mop.name(), null);
			if (mop instanceof ManagedIdentifiableProxy) {
				mop.getPersistenceContext().detachFromCache((ManagedIdentifiableProxy) mop);
			}
			detachHierarchyRecursive(mop);
		}
	}
	
	protected void detachHierarchyRecursive(ManagedObjectProxy mop) {
		detachHierarchyRecursive(mop, embeddedObjects, embeddedObjectCollections);
	}
	
	protected void detachHierarchyRecursive(ManagedObjectProxy mop, HashSet<String> embeddedObjects, HashSet<String> embeddedObjectCollections) {
		PersistenceContext context = mop.getPersistenceContext();
		for (String embeddedObjectName : embeddedObjects) {
			Persistent embeddedObject = (Persistent) BeanHandler.getAttributeValue(mop.getPojoObject(), embeddedObjectName);
			if (embeddedObject != null) {
				ManagedObjectProxy embeddedObjectProxy = (ManagedObjectProxy) BeanHandler.getAttributeValue(embeddedObject, AbstractBeanInfo.Attributes.mop.name());
				BeanInfo beanInfo = context.getBeanInfo(embeddedObject);
				beanInfo.detachHierarchy(embeddedObjectProxy);
			}
		}
		for (String embeddedObjectCollectionName : embeddedObjectCollections) {
			Collection<Persistent> embeddedObjectCollection = BeanHandler.getPersistentCollection(mop.getPojoObject(), embeddedObjectCollectionName);
			if (embeddedObjectCollection != null) {
				for (Persistent embeddedObject : embeddedObjectCollection) {
					ManagedObjectProxy embeddedObjectProxy = (ManagedObjectProxy) BeanHandler.getAttributeValue(embeddedObject, AbstractBeanInfo.Attributes.mop.name());
					BeanInfo beanInfo = context.getBeanInfo(embeddedObject);
					beanInfo.detachHierarchy(embeddedObjectProxy);
				}
			}
		}
	}
	
	public void prepareDetachableState(ManagedObjectProxy mop) {
	}

	public void clearObject(Persistent pojoIdentifiable) {
		clearObject(pojoIdentifiable, referencedObjects, embeddedObjects, embeddedObjectCollections);
	}
	
	public void clearObject(Persistent pojoIdentifiable, HashSet<String> referencedObjects, HashSet<String> embeddedObjects, HashSet<String> embeddedObjectCollections) {
		ManagedObjectProxy mop = (ManagedObjectProxy) BeanHandler.getAttributeValue(pojoIdentifiable, AbstractBeanInfo.Attributes.mop.name());
		PersistenceContext context = null;
		if (mop != null) {
			context = mop.getPersistenceContext();
		}
		for (String embeddedObjectName : embeddedObjects) {
			Persistent embeddedObject = (Persistent) BeanHandler.getAttributeValue(pojoIdentifiable, embeddedObjectName);
			if (embeddedObject != null) {
				if (context != null) {
					BeanInfo beanInfo = context.getBeanInfo(embeddedObject);
					beanInfo.clearObject(embeddedObject);
				}
			}
			BeanHandler.assignAttributeValue(pojoIdentifiable, embeddedObjectName, null);
		}
		for (String embeddedObjectCollectionName : embeddedObjectCollections) {
			Collection<Persistent> embeddedObjectCollection = BeanHandler.getPersistentCollection(pojoIdentifiable, embeddedObjectCollectionName);
			if (embeddedObjectCollection != null) {
				for (Persistent embeddedObject : embeddedObjectCollection) {
					if (context != null) {
						BeanInfo beanInfo = context.getBeanInfo(embeddedObject);
						beanInfo.clearObject(embeddedObject);
					}
				}
			}
			BeanHandler.assignAttributeValue(pojoIdentifiable, embeddedObjectCollectionName, null);
		}
		for (String referencedObjectName : referencedObjects) {
			BeanHandler.assignAttributeValue(pojoIdentifiable, referencedObjectName, null);
		}
	}
	
}
