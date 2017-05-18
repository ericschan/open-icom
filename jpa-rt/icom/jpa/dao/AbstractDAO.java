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
package icom.jpa.dao;


import icom.info.BeanHandler;
import icom.info.BeanInfo;

import icom.jpa.Manageable;
import icom.jpa.ManagedIdentifiableDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.rt.PersistenceContext;
import icom.jpa.rt.ValueHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import javax.persistence.PersistenceException;


public abstract class AbstractDAO extends BeanHandler implements DataAccessObject, ManagedObjectAttribute {

    static public AbstractDAO getDaoInstance(Class<?> daoClass, DataAccessUtils dataAccessUtils) {
        try {
            Method method = daoClass.getDeclaredMethod("getInstance", (Class[])null);
            if (method != null) {
                AbstractDAO dao = (AbstractDAO)method.invoke(null, (Object[])null);
                return dao;
            } else {
                if (Manageable.class.isAssignableFrom(daoClass)) {
                    return (AbstractDAO)dataAccessUtils.getUnknownEntityDAO();
                } else {
                    return (AbstractDAO)dataAccessUtils.getUnknownIdentifiableDAO();
                }
            }
        } catch (InvocationTargetException ex) {
            throw new PersistenceException("unable to get dao of entity " + daoClass.getCanonicalName());
        } catch (NoSuchMethodException ex) {
            throw new PersistenceException("unable to get dao of entity " + daoClass.getCanonicalName());
        } catch (IllegalAccessException ex) {
            throw new PersistenceException("unable to get dao of entity " + daoClass.getCanonicalName());
        }
    }

    // return Projection

    public Object load(ManagedObjectProxy obj, String attributeName) {
        return null;
    }

    // return Projection

    public Object load(ManagedObjectProxy obj, String attributeName, Object key) {
        return null;
    }

    // return Projection

    public Object loadFull(ManagedObjectProxy obj) {
        return null;
    }

    public abstract Object createObjectId(PersistenceContext context);

    public abstract boolean isCacheable();

    public abstract boolean embedAsNonIdentifiableDependent();

    public abstract boolean isPartOfProjection(String attributeName, Object proj);

    public boolean isLazyLoadedAttribute(String attributeName) {
        return false;
    }

    public void committedObject(Persistent pojo) {

    }

    public void rolledbackObject(Persistent pojo) {

    }

    public void save(ManagedIdentifiableProxy obj) {
        throw new RuntimeException();
    }

    public void delete(ManagedIdentifiableProxy obj) {
        throw new RuntimeException();
    }

    public Manageable getReference(PersistenceContext context, Object objectId) {
        return context.getReference(objectId);
    }

    public ManagedIdentifiableProxy getEntityProxy(PersistenceContext context, Object state) {
        DataAccessStateObject dataAccessStateObject = wrapDataAccessStateObject(state);
        return context.getEntityProxy(dataAccessStateObject);
    }

    public ManagedIdentifiableDependentProxy getIdentifiableDependentProxy(PersistenceContext context, Object state,
                                                                           ManagedObjectProxy parent,
                                                                           String parentAttributeName) {
        DataAccessStateObject dataAccessStateObject = wrapDataAccessStateObject(state);
        return context.getIdentifiableDependentProxy(dataAccessStateObject, parent, parentAttributeName);
    }

    public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(PersistenceContext context,
                                                                                 Object state,
                                                                                 ManagedObjectProxy parent,
                                                                                 String parentAttributeName) {
        DataAccessStateObject dataAccessStateObject = wrapDataAccessStateObject(state);
        return context.getNonIdentifiableDependentProxy(dataAccessStateObject, parent, parentAttributeName);
    }

    public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(PersistenceContext context,
                                                                                 String beanClassname,
                                                                                 ManagedObjectProxy parent,
                                                                                 String parentAttributeName) {
        return context.getNonIdentifiableDependentProxy(beanClassname, parent, parentAttributeName);
    }

    protected abstract DataAccessStateObject wrapDataAccessStateObject(Object state);

    public boolean isChanged(ManagedObjectProxy obj, String attributeName) {
        if (obj.isNew() || obj.containsAttributeChangeRecord(attributeName)) {
            return true;
        } else {
            return false;
        }
    }
    
    protected abstract String getProviderObjectId(Object providerObject);

    private void mergeIdentifiables(Collection<Persistent> pojoCollection,
                                               Collection<ValueHolder> addedObjects,
                                               Collection<ValueHolder> removedObjects) {
        if (removedObjects != null) {
            Persistent[] array = pojoCollection.toArray(new Persistent[0]);
            for (Persistent pojo : array) {
                for (ValueHolder holder : removedObjects) {
                    Persistent entity = (Persistent)holder.getValue();
                    String id1 = ((ManagedIdentifiableProxy)(entity.getManagedObjectProxy())).getObjectId().toString();
                    String id2 = ((ManagedIdentifiableProxy)pojo.getManagedObjectProxy()).getObjectId().toString();
                    if (id1.equals(id2)) {
                        pojoCollection.remove(pojo);
                        break;
                    }
                }
            }
        }
        if (addedObjects != null) {
            for (ValueHolder holder : addedObjects) {
                Persistent identifiable = (Persistent)holder.getValue();
                pojoCollection.add(identifiable);
            }
        }
    }
    
    private void mergeIdentifiables(ManagedObjectProxy obj, 
                                 String attributeName, 
                                 Collection<Persistent> pojoObjects) {
        Collection<ValueHolder> removedObjects = obj.getRemovedObjects(attributeName);
        Collection<ValueHolder> addedObjects = obj.getAddedObjects(attributeName);
        mergeIdentifiables(pojoObjects, addedObjects, removedObjects);                                
    }
    
    protected void mergeAssignIdentifiables(ManagedObjectProxy obj, 
                                     String attributeName, 
                                     Collection<Persistent> pojoObjects) {
        mergeIdentifiables(obj, attributeName, pojoObjects);
        assignAttributeValue(obj.getPojoObject(), attributeName, pojoObjects);
    }
    
    private void marshallMergeEntities(PersistenceContext context, 
                                   Collection<? extends Object> providerEntities,
                                   Collection<Persistent> pojoEntities,
                                   Collection<ValueHolder> addedObjects,
                                   Collection<ValueHolder> removedObjects) {
        for (Object providerObject : providerEntities) {
            boolean isRemoved = false;
            if (removedObjects != null) {
                for (ValueHolder holder : removedObjects) {
                    Persistent entity = (Persistent)holder.getValue();
                    String id = ((ManagedIdentifiableProxy)(entity.getManagedObjectProxy())).getObjectId().toString();
                    if (id.equals(getProviderObjectId(providerObject))) {
                        isRemoved = true;
                        break;
                    }
                }
            }
            if (!isRemoved) {
                ManagedIdentifiableProxy childObj = getEntityProxy(context, providerObject);
                pojoEntities.add(childObj.getPojoIdentifiable());
            }
        }
        if (addedObjects != null) {
            for (ValueHolder holder : addedObjects) {
                Persistent identifiable = (Persistent)holder.getValue();
                pojoEntities.add(identifiable);
            }
        }
    }

    private void marshallMergeEntities(ManagedObjectProxy obj, 
                                 String attributeName, 
                                 Collection<? extends Object> providerObjects, 
                                 Collection<Persistent> pojoObjects) {
        Collection<ValueHolder> removedObjects = obj.getRemovedObjects(attributeName);
        Collection<ValueHolder> addedObjects = obj.getAddedObjects(attributeName);
        PersistenceContext context = obj.getPersistenceContext();
        marshallMergeEntities(context, providerObjects, pojoObjects, addedObjects, removedObjects);
    }

    protected <T extends Collection<?>> 
        Collection<Persistent> marshallMergeAssignEntities(ManagedObjectProxy obj, 
                                    String attributeName, 
                                    Collection<?> providerObjects, 
                                    Class<T> clazz) {
        Persistent pojoIdentifiable = obj.getPojoObject();
        Collection<Persistent> pojoObjects = null;
        Class<?> cl = clazz;
        if (cl == HashSet.class) {
            pojoObjects = new HashSet<Persistent>(providerObjects.size());
        } else if (cl == Vector.class) {
            pojoObjects = new Vector<Persistent>(providerObjects.size());
        } else {
            try {
                pojoObjects = (Collection<Persistent>) clazz.newInstance();
            } catch (InstantiationException ex) {
                throw new PersistenceException("unable to instantiate " + clazz.getCanonicalName());
            } catch (IllegalAccessException ex) {
                throw new PersistenceException("unable to instantiate " + clazz.getCanonicalName());
            }
        }
        marshallMergeEntities(obj, attributeName, providerObjects, pojoObjects);
        assignAttributeValue(pojoIdentifiable, attributeName, pojoObjects);
        return pojoObjects;
    }
    
    private void marshallMergeIdentifiableDependents(ManagedObjectProxy obj,
                                               String attributeName,
                                               Collection<?> providerObjects,
                                               Collection<Persistent> pojoObjects,
                                               Collection<ValueHolder> addedObjects,
                                               Collection<ValueHolder> removedObjects,
                                               Projection proj) {
        for (Object providerObject : providerObjects) {
            boolean isRemoved = false;
            if (removedObjects != null) {
                for (ValueHolder holder : removedObjects) {
                    Persistent entity = (Persistent)holder.getValue();
                    String id = ((ManagedIdentifiableProxy)(entity.getManagedObjectProxy())).getObjectId().toString();
                    if (id.equals(getProviderObjectId(providerObject))) {
                        isRemoved = true;
                        break;
                    }
                }
            }
            if (!isRemoved) {
                PersistenceContext context = obj.getPersistenceContext();
                ManagedIdentifiableProxy childObj = getIdentifiableDependentProxy(context, providerObject, obj, attributeName);
                childObj.getProviderProxy().copyLoadedProjection(childObj, providerObject, proj);
                pojoObjects.add(childObj.getPojoIdentifiable());
            }
        }
        if (addedObjects != null) {
            for (ValueHolder holder : addedObjects) {
                Persistent identifiable = (Persistent)holder.getValue();
                pojoObjects.add(identifiable);
            }
        }
    }
    
    private void marshallMergeIdentifiableDependents(ManagedObjectProxy obj,
                                               String attributeName,
                                               Collection<?> providerObjects,
                                               Collection<Persistent> pojoObjects,
                                               Projection proj) {
        Collection<ValueHolder> removedObjects = obj.getRemovedObjects(attributeName);
        Collection<ValueHolder> addedObjects = obj.getAddedObjects(attributeName);
        marshallMergeIdentifiableDependents(obj, attributeName, providerObjects,
                                               pojoObjects, addedObjects, removedObjects, proj);
    }
    
    protected <T extends Collection<?>> 
        Collection<? extends Persistent> marshallMergeAssignIdentifiableDependents(ManagedObjectProxy obj,
                                                    String attributeName,
                                                    Collection<?> providerObjects,
                                                    Class<T> clazz,
                                                    Projection proj) {
        Collection<Persistent> pojoObjects = null;
        Class<?> cl = clazz;
        if (cl == HashSet.class) {
            pojoObjects = new HashSet<Persistent>(providerObjects.size());
        } else if (cl == Vector.class) {
            pojoObjects = new Vector<Persistent>(providerObjects.size());
        } else {
            try {
                pojoObjects = (Collection<Persistent>) clazz.newInstance();
                
            } catch (InstantiationException ex) {
                throw new PersistenceException("unable to instantiate " + clazz.getCanonicalName());
            } catch (IllegalAccessException ex) {
                throw new PersistenceException("unable to instantiate " + clazz.getCanonicalName());
            }
        }
        marshallMergeIdentifiableDependents(obj, attributeName, providerObjects, pojoObjects, proj);
        assignAttributeValue(obj.getPojoObject(), attributeName, pojoObjects);
        return pojoObjects;
    }
    
    private void marshallEmbeddableObjects(ManagedObjectProxy obj,
                                      String attributeName,
                                      Collection<?> providerObjects,
                                      Collection<Persistent> pojoObjects,
                                      Projection proj) {
        PersistenceContext context = obj.getPersistenceContext();
        for (Object providerObject : providerObjects) {
            ManagedObjectProxy pojoObject = getNonIdentifiableDependentProxy(context, providerObject, obj, attributeName);
            pojoObject.getProviderProxy().copyLoadedProjection(pojoObject, providerObject, proj);
            pojoObjects.add(pojoObject.getPojoObject());
        }
    }
    
    private void detachPreviousEmbeddableObjects(ManagedObjectProxy obj, String attributeName) {
        PersistenceContext context = obj.getPersistenceContext();
        Persistent pojoIdentifiable = obj.getPojoObject();
        Collection<Object> previousPojoObjects = getObjectCollection(pojoIdentifiable, attributeName);
        if (previousPojoObjects != null) {
            for (Object previousPojoObject : previousPojoObjects) {
                if (previousPojoObject instanceof Persistent) {
                    BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
                    ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, Attributes.mop.name());
                    beanInfo.detachHierarchy(mop);
                }
            }
        }
    }
    
    protected <T extends Collection<?>> 
        Collection<Persistent> marshallAssignEmbeddableObjects(ManagedObjectProxy obj,
                                String attributeName,
                                Collection<?> providerObjects,
                                Class<T> clazz,
                                Projection proj) {
        Collection<Persistent> pojoObjects = null;
        Class<?> cl = clazz;
        if (cl == HashSet.class) {
            pojoObjects = new HashSet<Persistent>(providerObjects.size());
        } else if (cl == Vector.class) {
            pojoObjects = new Vector<Persistent>(providerObjects.size());
        } else {
            try {
                pojoObjects = (Collection<Persistent>) clazz.newInstance();
                
            } catch (InstantiationException ex) {
                throw new PersistenceException("unable to instantiate " + clazz.getCanonicalName());
            } catch (IllegalAccessException ex) {
                throw new PersistenceException("unable to instantiate " + clazz.getCanonicalName());
            }
        }
        marshallEmbeddableObjects(obj, attributeName, providerObjects, pojoObjects, proj);
        detachPreviousEmbeddableObjects(obj, attributeName);
        assignAttributeValue(obj.getPojoObject(), attributeName, pojoObjects);
        return pojoObjects;
    }
    
    protected void marshallAssignEntity(ManagedObjectProxy obj, 
                                     String attributeName, 
                                     Object providerObject) {
        Persistent pojoIdentifiable = obj.getPojoObject();
        if (providerObject != null) {
            PersistenceContext context = obj.getPersistenceContext();
            ManagedIdentifiableProxy marshalledObj = getEntityProxy(context, providerObject);
            Persistent pojoObject = marshalledObj.getPojoIdentifiable();
            assignAttributeValue(pojoIdentifiable, attributeName, pojoObject);
        } else {
            assignAttributeValue(pojoIdentifiable, attributeName, null);
        }
    }
    
    protected void marshallAssignIdentifiableDependent(ManagedObjectProxy obj, 
                                                 String attributeName, 
                                                 Object providerObject,
                                                 Projection proj) {
        Persistent pojoIdentifiable = obj.getPojoObject();
        if (providerObject != null) {
            PersistenceContext context = obj.getPersistenceContext();
            ManagedIdentifiableProxy marshalledObj = getIdentifiableDependentProxy(context, providerObject, obj, attributeName);
            marshalledObj.getProviderProxy().copyLoadedProjection(marshalledObj, providerObject, proj);
            Persistent pojoObject = marshalledObj.getPojoIdentifiable();
            assignAttributeValue(pojoIdentifiable, attributeName, pojoObject);
        } else {
            assignAttributeValue(pojoIdentifiable, attributeName, null);
        }
    }
    
    private void detachPreviousEmbeddableObject(ManagedObjectProxy obj, String attributeName) {
        PersistenceContext context = obj.getPersistenceContext();
        Persistent pojoIdentifiable = obj.getPojoObject();
        Object previousPojoObject = getAttributeValue(pojoIdentifiable, attributeName);
        if (previousPojoObject != null) {
                BeanInfo beanInfo = context.getBeanInfo(previousPojoObject);
                ManagedObjectProxy mop = (ManagedObjectProxy) getAttributeValue(previousPojoObject, Attributes.mop.name());
                beanInfo.detachHierarchy(mop);
        }
    }
    
    protected void marshallAssignEmbeddableObject(ManagedObjectProxy obj, 
                                         String attributeName, 
                                         Object providerObject,
                                         Projection proj) {
        Persistent pojoIdentifiable = obj.getPojoObject();
        if (providerObject != null) {
            PersistenceContext context = obj.getPersistenceContext();
            ManagedObjectProxy marshalledObj = getNonIdentifiableDependentProxy(context, providerObject, obj, attributeName);
            marshalledObj.getProviderProxy().copyLoadedProjection(marshalledObj, providerObject, proj);
            Persistent pojoObject = marshalledObj.getPojoObject();
            detachPreviousEmbeddableObject(obj, attributeName);
            assignAttributeValue(pojoIdentifiable, attributeName, pojoObject);
        } else {
            detachPreviousEmbeddableObject(obj, attributeName);
            assignAttributeValue(pojoIdentifiable, attributeName, null);
        }
    }

    protected AttributeChangeSet getAttributeChanges(ManagedObjectProxy obj, Collection<Persistent> pojoObjects,
                                       String attributeName) {
        AttributeChangeSet changeSet = new AttributeChangeSet();
        if (pojoObjects != null) {
            for (Persistent pojoObject : pojoObjects) {
                ManagedObjectProxy proxy = pojoObject.getManagedObjectProxy();
                if (proxy.hasAttributeChanges()) {
                    changeSet.modifiedPojoObjects.add(pojoObject);
                }
            }
        }
        Collection<ValueHolder> valueHolderOfAddedObjects = obj.getAddedObjects(attributeName);
        if (valueHolderOfAddedObjects != null) {
            for (ValueHolder holder : valueHolderOfAddedObjects) {
                Persistent pojoObject = (Persistent)holder.getValue();
                changeSet.modifiedPojoObjects.remove(pojoObject);
                changeSet.addedPojoObjects.add(pojoObject);
            }
        }
        Collection<ValueHolder> valueHolderOfRemovedObjects = obj.getRemovedObjects(attributeName);
        if (valueHolderOfRemovedObjects != null) {
            for (ValueHolder holder : valueHolderOfRemovedObjects) {
                Persistent pojoObject = (Persistent)holder.getValue();
                changeSet.modifiedPojoObjects.remove(pojoObject);
                changeSet.removedPojoObjects.add(pojoObject);
            }
        }
        return changeSet;
    }
	
}
