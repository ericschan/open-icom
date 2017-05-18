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

import icom.jpa.ManagedIdentifiableProxy;

import java.util.Collection;
import java.util.Set;
import java.util.Hashtable;

/**
 * The object cache implementation with restorable weak or soft references
 */
public abstract class ObjectCacheDualImpl implements ObjectCache {

  /** The active cache is a hashtable */
  private Hashtable<Object, ManagedIdentifiableProxy> cache;

  /** 
   * A backup cache from which the swapped-out objects can be restored as long
   *  as they are not garbage collected 
   */
  private ObjectCacheBackupImpl refCache;

  private ObjectCacheAbstractMonitor monitor = null;

  public void setCacheMonitor(ObjectCacheAbstractMonitor mon) {
    monitor = mon;
  }

  /**
   *  Factory method to let the subclass provide the backup cache
   */
  abstract public ObjectCacheBackupImpl newRefCache();

  public ObjectCacheDualImpl() {
    cache = new Hashtable<Object, ManagedIdentifiableProxy>();
    refCache = this.newRefCache();
  }

  /**
   * Add a cacheable object to the cache
   * @param key the object key
   * @param object the cacheable object
   */
  public void put(Object key, ManagedIdentifiableProxy object) {
    cache.put(key, object);
  }

  /**
   * Update the time-to-live parameter of the object to support swapping
   * of LRU objects
   * @param obj the object to be touched
   */
  private void touch(ManagedIdentifiableProxy obj) {
    if ( obj != null && monitor != null ) {
      obj.setCacheTime(monitor.getCacheTime());
    }
  }

  /**
   * Get an object from the cache
   * @param key the object key
   * @return the object or null if not found
   */
  public ManagedIdentifiableProxy get(Object key) {
	ManagedIdentifiableProxy obj = cache.get(key);
    touch(obj);
    if ( obj == null ) {
      obj = refCache.get(key);
      if ( obj != null ) {
        touch(obj);
        // reinstate obj from backup cache
        cache.put(key, obj);
        refCache.remove(key);
      }
    }
    return obj;
  }
  
  /**
   * Detach an object from the cache and backup cache
   * @param key the object key
   */
  public ManagedIdentifiableProxy detach(Object key) {
	ManagedIdentifiableProxy obj = cache.get(key);
    cache.remove(key);
    refCache.remove(key);
    return obj;
  }

  /**
   * Remove an object from the cache, but placed the object in backup cache so
   * that it can be restored if not garbage collected
   * @param key the object key
   */
  public ManagedIdentifiableProxy remove(Object key) {
	ManagedIdentifiableProxy obj = cache.get(key);
    cache.remove(key);
    if ( obj != null ) {
      refCache.put(key, obj);
    }
    return obj;
  }

  /**
   * Clear the main cache and backup cache
   */
  public void clear() {
    cache.clear();
    refCache.clear();
  }

  /**
   * @return size of cache
   */
  public int size() {
    return cache.size();
  }

  /**
   * @return the enumeration of keys
   */
  public Set<Object> keys() {
    return cache.keySet();
  }

  public Collection<ManagedIdentifiableProxy> values() {
    return cache.values();
  }

  public ObjectCache getBackupCache() {
    return refCache;
  }

  public void monitorCache(ObjectCacheAbstractMonitor monitor) {
	Collection<ManagedIdentifiableProxy> elements = values();
    for (ManagedIdentifiableProxy obj : elements) {
      long time = obj.getCacheTime();
      // only handle objects where the cache time is set
      if (time > -1 && time < monitor.getInternalTime()) {
    	remove(obj.getObjectId());
        obj.uncached();
      }
    }
  }

}
