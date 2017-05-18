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

//import icom.BeanUtil;
import icom.ManagedObject;
import icom.ObjectIdTrait;
import icom.info.BeanHandler;
import icom.info.BeanInfo;
import icom.info.BeanUtil;
import icom.info.IdentifiableInfo;
import icom.jpa.Dependent;
import icom.jpa.Identifiable;
import icom.jpa.IdentifiableDependent;
import icom.jpa.Manageable;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.ManagedIdentifiableDependentProxy;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.dao.DataAccessClassHelper;
import icom.jpa.dao.DataAccessConnectorFactory;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.dao.DataAccessUtils;
import icom.jpa.dao.ProjectionManagerFactory;
import icom.jpa.dao.ProviderClassPojoClassMap;
import icom.jpa.dao.UserContextFactory;
import icom.jpql.QueryImpl;
import icom.jpql.QueryUtils;
import icom.ql.QueryContext;
import icom.ql.SchemaHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class PersistenceContextImpl implements PersistenceContext {
	
	String icomBeanUtilName = "icom.IcomBeanUtil";
	
	ObjectCacheHardWeakImpl cache;
	
	EntityManagerImpl manager;
	
	DataAccessConnectorFactory dataAccessConnectorFactory;
	
	DataAccessUtils dataAccessUtils;
	
	DataAccessClassHelper dataAccessClassHelper;
	
	QueryUtils queryUtils;
	
	ProjectionManagerFactory projectionManagerFactory;
	
	UserContextFactory userContextFactory;
	
	BeanUtil primaryBeanUtil;
	
	boolean ignoreDaoError = true;
	
	PersistenceContextImpl(Properties properties, Map<Object, Object> map, EntityManagerImpl manager) {
		this(properties, manager);
	}
	
	PersistenceContextImpl(Map<Object, Object> map, EntityManagerImpl manager) {
		cache = new ObjectCacheHardWeakImpl();
		this.manager = manager;
		
		String primaryBeanUtilName = (String) map.get("PrimaryBeanUtil");
		if (primaryBeanUtilName == null) {
			primaryBeanUtilName = icomBeanUtilName;
		}
		primaryBeanUtil = instantiateBeanUtil(primaryBeanUtilName);
		BeanHandler.registerPrimaryBeanHandling(primaryBeanUtil);
		
		String vendorBeanUtilName = (String) map.get("VendorBeanUtil");
		if (vendorBeanUtilName != null) {
			BeanUtil vendorBeanUtil = instantiateBeanUtil(vendorBeanUtilName);
			BeanHandler.registerVendorBeanHandling(vendorBeanUtil);
		}
		
		String dataAccessConnectorFactoryClassName = (String) map.get("DataAccessConnectorFactory");
		if (dataAccessConnectorFactoryClassName == null) {
			dataAccessConnectorFactoryClassName = "icom.jpa.csi.CsiConnectorFactory";
		}
		dataAccessConnectorFactory = instantiateConnectorFactory(dataAccessConnectorFactoryClassName);
		dataAccessUtils = dataAccessConnectorFactory.createDataAccessUtils(map);
		dataAccessClassHelper = dataAccessConnectorFactory.createDataAccessClassHelper(map);
		queryUtils = dataAccessConnectorFactory.createQueryUtils(map);
		projectionManagerFactory = dataAccessConnectorFactory.getProjectionManagerFactory(map);
		userContextFactory = dataAccessConnectorFactory.getUserContextFactory(map);	
	}
	
	public EntityManagerImpl getManager() {
		return manager;
	}
	
	public boolean isIgnoreDaoError() {
		return ignoreDaoError;
	}
	
	public BeanUtil instantiateBeanUtil(String className) {
		try {
			Class<?> beanUtilClass = Class.forName(className);
			Object beanUtil = beanUtilClass.newInstance();
			return (BeanUtil) beanUtil;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("data access connector not found " + className, ex);
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of data access connector class" + className, ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of data access connector class" + className, ex);
		}
	}
	
	public DataAccessConnectorFactory instantiateConnectorFactory(String className) {
		try {
			Class<?> dataAccessConnectorFactoryClass = Class.forName(className);
			Object dataAccessConnectorFactory = dataAccessConnectorFactoryClass.newInstance();
			return (DataAccessConnectorFactory) dataAccessConnectorFactory;
		} catch (ClassNotFoundException ex) {
			throw new PersistenceException("data access connector not found " + className, ex);
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of data access connector class" + className, ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of data access connector class" + className, ex);
		}
	}
	
	public DataAccessConnectorFactory getDataAccessConnectorFactory() {
		return dataAccessConnectorFactory;
	}

	public DataAccessUtils getDataAccessUtils() {
		return dataAccessUtils;
	}
	
	public ProjectionManagerFactory getProjectionManagerFactory() {
		return projectionManagerFactory;
	}

	public DataAccessClassHelper getDataAccessClassHelper() {
		return dataAccessClassHelper;
	}

	public void setManager(EntityManagerImpl manager) {
		this.manager = manager;
	}

	public Query createQuery(String qlString) {
		SchemaHelper schemaHelper = BeanHandler.instantiateSchemaHelper();
		QueryContext queryContext = queryUtils.createQueryContext();
		QueryImpl query = queryUtils.createQuery(qlString, schemaHelper, queryContext);
		query.setCacheContext(this);
		query.validate();
		return query;
	}

	/**
     * If the entity does not already exist in the repository, let the current persistence
     * context manage it.
     */
	public void registerNewEntityForPersist(Manageable entity) {
		DataAccessObject dao = getDataAccessObject(entity);
		ObjectIdTrait pojoId = entity.getObjectId();
		Object objectId = null;
		if (pojoId != null) {
			objectId = pojoId.getObjectId();
			if (exists(objectId)) {
				ManagedObjectProxy proxy = ((ManagedObject)entity).getManagedObjectProxy();
				if (proxy != null && proxy.isNew()) {
					// repeat registration of a new entity
					return;
				}
				throw new EntityExistsException();
			}
		}
		if (objectId == null) {
			objectId = dao.createObjectId(this);
			BeanHandler.assignObjectId(entity, objectId);
		} 
		ManagedEntityProxyImpl mop = new ManagedEntityProxyImpl(ManagedEntityProxyImpl.Mode.NEW, this, entity, dao);
		BeanHandler.assignManagedObjectProxy(entity, mop);
		cache.put(mop.getObjectId(), mop);
		BeanInfo beanInfo = getBeanInfo(entity);
		beanInfo.attachHierarchy(mop);
	}
	
	public ManagedObjectProxy registerNewDependentForPersist(Persistent pojo, 
			Dependent dependent, String attributeName) {
		BeanInfo beanInfo = getBeanInfo(pojo);
		ManagedObjectProxy mop = ((ManagedObject)pojo).getManagedObjectProxy();
		return beanInfo.attachDependentHierarchy(mop, dependent, attributeName);
	}
	
	void detachHierarchy(ManagedObjectProxy obj) {
		PersistenceContextImpl context = (PersistenceContextImpl) obj.getPersistenceContext();
		BeanInfo beanInfo = context.getBeanInfo(obj.getPojoObject());
		beanInfo.detachHierarchy(obj);
	}
	
	void prepareToDetach(ManagedObjectProxy obj, boolean full) {
		PersistenceContextImpl context = (PersistenceContextImpl) obj.getPersistenceContext();
		BeanInfo beanInfo = context.getBeanInfo(obj.getPojoObject());
		if (full) {
			if (obj instanceof ManagedEntityProxyImpl) {
				((ManagedEntityProxyImpl)obj).loadFull();
			}
		}
		beanInfo.prepareDetachableState(obj);
	}
	
	public ManagedObjectProxy detachFromCache(ManagedIdentifiableProxy obj) {
		return cache.detach(obj.getObjectId());
	}
	
	/**
     * Check if the instance belongs to (managed by) the current persistence
     * context.
     */
	boolean contains(Manageable entity) {
	    ObjectIdTrait pojoId = entity.getObjectId();
		Object objectId = null;
		if (pojoId != null) {
			objectId = dataAccessUtils.parseObjectId(pojoId.getObjectId());
		}
		if (objectId != null) {
			ManagedIdentifiableProxy obj = cache.get(objectId);
			if (obj != null && obj == ((ManagedObject)entity).getManagedObjectProxy()) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * Check if the instance exists in the repository.
     */
	boolean exists(Object id) {
		ManagedIdentifiableProxy obj = cache.get(id);
		if (obj != null) {
			return true;
		} else {
			dataAccessUtils.exists(this, id);
		}
		return false;
	}
	
	/**
     * Get a fully loaded instance.
     */
	public Manageable find(Class<? extends Manageable> entityClass, Object objectId) {
		ManagedIdentifiableProxy managedProxy = cache.get(objectId);
		if (managedProxy != null) {
			Manageable managedObject = (Manageable) managedProxy.getPojoIdentifiable();
			if (! entityClass.isAssignableFrom(managedObject.getClass())) {
				throw new IllegalArgumentException("entity not assignable to entity class");
			} else {
				if (managedProxy.isPooled(IdentifiableInfo.Attributes.changeToken.name())) {
					managedProxy.load(IdentifiableInfo.Attributes.changeToken.name());
				}
				return managedObject;
			}
		}
		
		Manageable pojoIdentifiable = dataAccessUtils.find(this, objectId);
		if (! entityClass.isAssignableFrom(pojoIdentifiable.getClass())) {
			throw new IllegalArgumentException("entity not assignable to entity class");
		}
		return pojoIdentifiable;
	}
	
	public Manageable find(Object objectId) {
		ManagedIdentifiableProxy managedProxy = cache.get(objectId);
		if (managedProxy != null) {
			if (managedProxy.isPooled(IdentifiableInfo.Attributes.changeToken.name())) {
				managedProxy.load(IdentifiableInfo.Attributes.changeToken.name());
			}
			Manageable managedObject = (Manageable) managedProxy.getPojoIdentifiable();
			return managedObject;
		}
		
		Manageable pojoIdentifiable = dataAccessUtils.find(this, objectId);
		return pojoIdentifiable;
	}
	
	/**
     * Get an instance, whose state may be lazily fetched.
     */
	public Manageable getReference(Class<? extends Manageable> entityClass, Object objectId) {
		ManagedIdentifiableProxy managedProxy = cache.get(objectId);
		if (managedProxy != null) {
			Manageable managedObject = (Manageable) managedProxy.getPojoIdentifiable();
			if (! entityClass.isAssignableFrom(managedObject.getClass())) {
				throw new IllegalArgumentException("entity not assignable to entity class");
			} else {
				return managedObject;
			}
		}
		Identifiable pojoIdentifiable = dataAccessUtils.getReference(this, objectId);
		if (! entityClass.isAssignableFrom(pojoIdentifiable.getClass())) {
			throw new IllegalArgumentException("entity not assignable to entity class");
		}
		return (Manageable) pojoIdentifiable;
	}
	
	public Manageable getReference(Object objectId) {
		ManagedIdentifiableProxy managedProxy = cache.get(objectId);
		if (managedProxy != null) {
			Manageable managedObject = (Manageable) managedProxy.getPojoIdentifiable();
			return managedObject;
		}
		
		return dataAccessUtils.getReference(this, objectId);
	}
	
	private void merge(Manageable detachedEntity, Manageable managedEntity) {
		// TODO
	}
	
	/**
     * Merge the state of the given detached entity into the corresponding managed
     * entity of current persistence context.
     */
	Manageable mergeToManagedObject(Manageable detachedEntity) {
	    ObjectIdTrait pojoId = detachedEntity.getObjectId();
		if (pojoId != null) {
			Object objectId = pojoId.getObjectId();
			Manageable managedEntity = find(detachedEntity.getClass(), objectId);
			merge(detachedEntity, managedEntity);
			return managedEntity;
		}
		return null;
	}
	
	/**
	 * Clear the persistence context.
	 */
	void clear() {
		Collection<ManagedIdentifiableProxy> collection = cache.values();
		Object[] array = collection.toArray();
		for (Object obj : array) {
			detachHierarchy((ManagedIdentifiableProxy) obj);
		}
	}
	
	private ManagedIdentifiableProxy lookupInCache(Object objectId) {
		return cache.get(objectId);
	}

	public void recacheIdentifiableDependent(ManagedIdentifiableProxy dependent) {
		Object id = dependent.getObjectId();
		cache.put(id, dependent);
	}	
	
	public ManagedIdentifiableProxy getEntityProxy(DataAccessStateObject dataAccessStateObject) {
		ManagedIdentifiableProxy pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
		if (pojoIdentifiable == null) {
			synchronized(this) {
				pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
				if (pojoIdentifiable == null) {
					pojoIdentifiable = manageIdentifiableProxy(dataAccessStateObject);
				}
			}
		}
		return pojoIdentifiable;
	}
	
	public ManagedIdentifiableProxy getEntityProxy(String pojoSimpleClassName, DataAccessStateObject dataAccessStateObject) {
		ManagedIdentifiableProxy pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
		if (pojoIdentifiable == null) {
			synchronized(this) {
				pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
				if (pojoIdentifiable == null) {
					pojoIdentifiable = manageIdentifiableProxy(pojoSimpleClassName, dataAccessStateObject);
				}
			}
		}
		return pojoIdentifiable;
	}
		
	public ManagedIdentifiableDependentProxy getIdentifiableDependentProxy(DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName) {
		ManagedIdentifiableProxy pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
		if (pojoIdentifiable == null) {
			synchronized(this) {
				pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
				if (pojoIdentifiable instanceof ManagedIdentifiableDependentProxy) {
					ManagedIdentifiableDependentProxy proxy = (ManagedIdentifiableDependentProxy) pojoIdentifiable;
					if (proxy.hasParent(parent)) {
						proxy.addAnotherParent(parent, parentAttributeName);
					}
				} else if (pojoIdentifiable == null) {
					pojoIdentifiable = manageIdentifiableDependentProxy(dataAccessStateObject, parent, parentAttributeName);
				}
			}
		}
		return (ManagedIdentifiableDependentProxy) pojoIdentifiable;
	}
	
	public ManagedIdentifiableProxy getIdentifiableDependentProxy(String pojoSimpleClassName, DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName) {
		ManagedIdentifiableProxy pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
		if (pojoIdentifiable == null) {
			synchronized(this) {
				pojoIdentifiable = lookupInCache(dataAccessStateObject.getObjectId());
				if (pojoIdentifiable instanceof ManagedIdentifiableDependentProxy) {
					ManagedIdentifiableDependentProxy proxy = (ManagedIdentifiableDependentProxy) pojoIdentifiable;
					if (proxy.hasParent(parent)) {
						proxy.addAnotherParent(parent, parentAttributeName);
					}
				} else if (pojoIdentifiable == null) {
					pojoIdentifiable = manageIdentifiableDependentProxy(pojoSimpleClassName, dataAccessStateObject, parent, parentAttributeName);
				}
			}
		}
		return pojoIdentifiable;
	}
	
	public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName) {
		return manageNonIdentifiableDependentProxy(dataAccessStateObject, parent, parentAttributeName);
	}

	public ManagedNonIdentifiableDependentProxy getNonIdentifiableDependentProxy(String pojoSimpleClassName, ManagedObjectProxy parent, String parentAttributeName) {
		return manageNonIdentifiableDependentProxy(pojoSimpleClassName, parent, parentAttributeName);
	}

	private ManagedIdentifiableProxy manageIdentifiableProxy(DataAccessStateObject dataAccessStateObject) {
		ProviderClassPojoClassMap map = dataAccessClassHelper.mapProviderClassToPojoClass(dataAccessStateObject.getProviderClass());
		Identifiable pojoIdentifiable = BeanHandler.instantiatePojoIdentifiable(map.pojoClass, dataAccessStateObject.getObjectId());
		DataAccessObject dao = getDataAccessObject(pojoIdentifiable);
		ManagedEntityProxyImpl mop = new ManagedEntityProxyImpl(ManagedEntityProxyImpl.Mode.POOLED, this, pojoIdentifiable, dao);
		mop.setProviderClassName(map.providerSimpleClassName);
		BeanHandler.assignManagedObjectProxy(pojoIdentifiable, mop);
		cache.put(mop.getObjectId(), mop);
		return mop;
	}
	
	private ManagedIdentifiableProxy manageIdentifiableProxy(String pojoSimpleClassName, DataAccessStateObject dataAccessStateObject) {
		ProviderClassPojoClassMap map = dataAccessClassHelper.mapProviderClassToPojoClass(pojoSimpleClassName);
		Identifiable pojoIdentifiable = BeanHandler.instantiatePojoIdentifiable(map.pojoClass, dataAccessStateObject.getObjectId());
		DataAccessObject dao = getDataAccessObject(map);
		ManagedEntityProxyImpl mop = new ManagedEntityProxyImpl(ManagedEntityProxyImpl.Mode.POOLED, this, pojoIdentifiable, dao);
		mop.setProviderClassName(map.providerSimpleClassName);
		BeanHandler.assignManagedObjectProxy(pojoIdentifiable, mop);
		cache.put(mop.getObjectId(), mop);
		return mop;
	}
	
	private ManagedIdentifiableProxy manageIdentifiableDependentProxy(DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName) {
		ProviderClassPojoClassMap map = dataAccessClassHelper.mapProviderClassToPojoClass(dataAccessStateObject.getProviderClass());
		Object id = dataAccessStateObject.getObjectId();
		Identifiable pojoIdentifiable = BeanHandler.instantiatePojoIdentifiable(map.pojoClass, id);
		Persistent pojoParentObject = parent.getPojoObject();
		DataAccessObject dao = getDataAccessObject(pojoIdentifiable, pojoParentObject, parentAttributeName);
		ManagedIdentifiableDependentProxyImpl dop = new ManagedIdentifiableDependentProxyImpl(ManagedIdentifiableDependentProxyImpl.Mode.POOLED, pojoIdentifiable, parent, parentAttributeName, dao);
		dop.setProviderClassName(map.providerSimpleClassName);
		dop.setDataAccessObject(dao);
		BeanHandler.assignManagedObjectProxy(pojoIdentifiable, dop);
		Object cacheId = dop.getObjectId();
		if (cacheId != null && dao.isCacheable()) {
			cache.put(cacheId, dop);
		}
		return dop;
	}
	
	private ManagedIdentifiableProxy manageIdentifiableDependentProxy(String pojoSimpleClassName, DataAccessStateObject dataAccessStateObject, ManagedObjectProxy parent, String parentAttributeName) {
		ProviderClassPojoClassMap map = dataAccessClassHelper.mapProviderClassToPojoClass(pojoSimpleClassName);
		Identifiable pojoIdentifiable = BeanHandler.instantiatePojoIdentifiable(map.pojoClass, dataAccessStateObject.getObjectId());
		DataAccessObject dao = getDataAccessObject(map);
		ManagedIdentifiableDependentProxyImpl dop = new ManagedIdentifiableDependentProxyImpl(ManagedIdentifiableDependentProxyImpl.Mode.POOLED, pojoIdentifiable, parent, parentAttributeName, dao);
		dop.setProviderClassName(map.providerSimpleClassName);
		dop.setDataAccessObject(dao);
		BeanHandler.assignManagedObjectProxy(pojoIdentifiable, dop);
		Object cacheId = dop.getObjectId();
		if (cacheId != null && dao.isCacheable()) {
			cache.put(cacheId, dop);
		}
		return dop;
	}
	
	private ManagedNonIdentifiableDependentProxy manageNonIdentifiableDependentProxy(DataAccessStateObject stateObject, ManagedObjectProxy parent, String parentAttributeName) {
		ProviderClassPojoClassMap map = dataAccessClassHelper.mapProviderClassToPojoClass(stateObject.getProviderClass());
		Dependent pojoObject = (Dependent) BeanHandler.instantiatePojoObject(map.pojoClass);
		Persistent pojoParentObject = parent.getPojoObject();
		DataAccessObject dao = getDataAccessObject(pojoObject, pojoParentObject, parentAttributeName);
		ManagedNonIdentifiableDependentProxyImpl dop = new ManagedNonIdentifiableDependentProxyImpl(pojoObject, parent, parentAttributeName, dao);
		dop.setProviderClassName(map.providerSimpleClassName);
		dop.setDataAccessObject(dao);
		BeanHandler.assignManagedObjectProxy(pojoObject, dop);
		return dop;
	}
	
	private ManagedNonIdentifiableDependentProxy manageNonIdentifiableDependentProxy(String pojoSimpleClassName, ManagedObjectProxy parent, String parentAttributeName) {
		ProviderClassPojoClassMap map = dataAccessClassHelper.mapProviderClassToPojoClass(pojoSimpleClassName);
		Dependent pojoObject = (Dependent) BeanHandler.instantiatePojoObject(map.pojoClass);
		DataAccessObject dao = getDataAccessObject(map);
		ManagedNonIdentifiableDependentProxyImpl dop = new ManagedNonIdentifiableDependentProxyImpl(pojoObject, parent, parentAttributeName, dao);
		dop.setProviderClassName(map.providerSimpleClassName);
		dop.setDataAccessObject(dao);
		BeanHandler.assignManagedObjectProxy(pojoObject, dop);
		return dop;
	}
	
	public ManagedDependentProxy manageIdentifiableDependent(IdentifiableDependent pojoIdentifiable, ManagedObjectProxy parent, String parentAttributeName) {
		Persistent pojoParentObject = parent.getPojoObject();
		DataAccessObject dao = getDataAccessObject(pojoIdentifiable, pojoParentObject, parentAttributeName);
		if (dao.embedAsNonIdentifiableDependent()) {
			return this.manageNonIdentifiableDependent(pojoIdentifiable, parent, parentAttributeName);
		}
		ObjectIdTrait pojoId = pojoIdentifiable.getObjectId();
		Object objectId = null;
		if (pojoId != null) {
			objectId = dataAccessUtils.parseObjectId(pojoId.getObjectId());
		}
		if (objectId == null) {
			objectId = dao.createObjectId(this);
			BeanHandler.assignObjectId(pojoIdentifiable, objectId);
		}
		ManagedIdentifiableDependentProxyImpl dop = new ManagedIdentifiableDependentProxyImpl(ManagedIdentifiableDependentProxyImpl.Mode.NEW, pojoIdentifiable, parent, parentAttributeName, dao);
		dop.setDataAccessObject(dao);
		BeanHandler.assignManagedObjectProxy(pojoIdentifiable, dop);
		Object cacheId = dop.getObjectId();
		if (cacheId != null && dao.isCacheable()) {
			cache.put(cacheId, dop);
		}
		return dop;
	}
	
	public ManagedDependentProxy manageNonIdentifiableDependent(Persistent pojoObject, ManagedObjectProxy parent, String parentAttributeName) {
		Persistent pojoParentObject = parent.getPojoObject();
		DataAccessObject dao = getDataAccessObject(pojoObject, pojoParentObject, parentAttributeName);
		ManagedNonIdentifiableDependentProxyImpl dop = new ManagedNonIdentifiableDependentProxyImpl(pojoObject, parent, parentAttributeName, dao);
		dop.setDataAccessObject(dao);
		BeanHandler.assignManagedObjectProxy(pojoObject, dop);
		return dop;
	}
	
	public BeanInfo getBeanInfo(Object pojo) {
		Class<?> clazz = pojo.getClass();
		Class<?> beanInfoClass = dataAccessClassHelper.mapPojoToBeanInfo(clazz);
		BeanInfo info = getBeanInfoInstance(beanInfoClass);
		return info;
	}
	
	public BeanInfo getBeanInfoInstance(Class<?> beanInfoClass) {
		try {
			Method method = beanInfoClass.getDeclaredMethod("getInstance", (Class[]) null);
			if (method != null) {
				BeanInfo beanInfo = (BeanInfo) method.invoke(null, (Object[]) null);
				return beanInfo;
			} else {
				return primaryBeanUtil.getUnknownBeanInfo(beanInfoClass);
			}
		} catch (InvocationTargetException ex) {
			throw new PersistenceException("unable to get bean info of entity " + beanInfoClass.getCanonicalName());
		} catch (NoSuchMethodException ex) {
			throw new PersistenceException("unable to get bean info of entity " + beanInfoClass.getCanonicalName());
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("unable to get bean info of entity " + beanInfoClass.getCanonicalName());
		}
	}
	
	private DataAccessObject getDataAccessObject(Object pojo) {
		Class<?> clazz = pojo.getClass();
		Class<?> daoClass = dataAccessClassHelper.mapPojoToDao(clazz);
		icom.jpa.dao.AbstractDAO dao = icom.jpa.dao.AbstractDAO.getDaoInstance(daoClass, dataAccessUtils);
		return dao;
	}
	
	public DataAccessObject getDataAccessObject(ProviderClassPojoClassMap map) {
		Class<?> daoClass = dataAccessClassHelper.mapPojoToDao(map);
		icom.jpa.dao.AbstractDAO dao = icom.jpa.dao.AbstractDAO.getDaoInstance(daoClass, dataAccessUtils);
		return dao;
	}
	
	public DataAccessObject getDataAccessObject(Object pojo, Object pojoParent, String parentAttribute) {
		Class<?> pojoClazz = pojo.getClass();
		Class<?> pojoParentClazz = pojoParent.getClass();
		Class<?> daoClass = dataAccessClassHelper.mapPojoToDao(pojoClazz, pojoParentClazz, parentAttribute);
		icom.jpa.dao.AbstractDAO dao = icom.jpa.dao.AbstractDAO.getDaoInstance(daoClass, dataAccessUtils);
		return dao;
	}

    public UserContext createUserContext(String username, char[] password) throws Exception {
  		return userContextFactory.createUserContext(this, username, password);
  	}
    
    public UserContext createUserContext(String username, char[] password,
            Map<Object, Object> properties) throws Exception {
        return userContextFactory.createUserContext(this, username, password, properties);
    }

  	public void attachUserContext(UserContext context) throws Exception {
  		userContextFactory.attachUserContext(context);
  	}

  	public void detachUserContext() throws Exception {
  		userContextFactory.detachUserContext();
  	}
  	
  	public UserContext getUserContext() throws Exception {
  		return userContextFactory.getUserContext();
  	}
  	
}
