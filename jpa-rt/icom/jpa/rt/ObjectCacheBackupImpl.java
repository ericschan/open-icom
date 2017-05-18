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

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.WeakHashMap;

/**
 * The object cache implementation.
 */
public abstract class ObjectCacheBackupImpl implements ObjectCache {

  /** The cache is a hashtable */
  private WeakHashMap<Object, Reference<ManagedIdentifiableProxy>> cache;

  private ObjectCacheAbstractMonitor monitor = null;

  public void setCacheMonitor(ObjectCacheAbstractMonitor mon) {
  }

  /**
   * The empty constructor
   */
  public ObjectCacheBackupImpl() {
    cache = new WeakHashMap<Object, Reference<ManagedIdentifiableProxy>>();
  }

  public ObjectCacheBackupImpl(int initialCapacity) {
    cache = new WeakHashMap<Object, Reference<ManagedIdentifiableProxy>>(initialCapacity);
  }

  /**
   * Wrap the object using reference counters, soft reference, weak reference etc.
   * @param obj the object to be wrapped
   * @return the wrapper object
   */
  abstract public Reference<ManagedIdentifiableProxy> wrap(ManagedIdentifiableProxy obj);

  /**
   * Unwrap the object from reference counter, soft reference, weak reference etc.
   * @param obj the wrapper object
   * @return the object in the wrapper
   */
  abstract public ManagedIdentifiableProxy unwrap(Reference<ManagedIdentifiableProxy> obj);

  /**
   * Add an object to the cache
   * @param key the object key
   * @param object the object
   */
  public void put(Object key, ManagedIdentifiableProxy object) {
    Object oid = object.getObjectId();
    if (key != oid) { 
      if (key.equals(oid)) {
        key = oid;
      }
    }
    synchronized(cache) {
      cache.put(key, this.wrap(object));
    }
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
	Reference<ManagedIdentifiableProxy> wrapper = null;
    synchronized(cache) {
      wrapper = cache.get(key);
    }
    ManagedIdentifiableProxy obj = this.unwrap(wrapper);
    touch(obj);
    // if wrapper contains null then this key can be removed
    if (wrapper != null && obj == null) {
      synchronized(cache) {
        cache.remove(key);
      }
    }
    return obj;
  }

  /**
   * Remove an object
   * @param key the object key
   */
  Reference<ManagedIdentifiableProxy> removeReference(Object key) {
    synchronized(cache) {
      return cache.remove(key);
    }
  }

  /**
   * Remove an object
   * @param key the object key
   */
  public ManagedIdentifiableProxy remove(Object key) {
	Reference<ManagedIdentifiableProxy> wrapper = null;
    synchronized(cache) {
      wrapper = cache.remove(key);
    }
    ManagedIdentifiableProxy obj = this.unwrap(wrapper);
    return obj;
  }

  /**
   * Clear the cache
   */
  public void clear() {
    synchronized(cache) {
      cache.clear();
    }
  }

  /**
   * @return size of cache
   */
  public int size() {
    return cache.size();
  }

  /**
   * @return the keys
   */
  public Set<Object> keys() {
    int sz = cache.size();
    Set<Object> v = new HashSet<Object>(sz > 0 ? sz + 5: 1);
    Set<Object> keySet = cache.keySet();
    Iterator<Object> iter = keySet.iterator();
    while (iter.hasNext()) { 
      Object obj = iter.next();
      if (obj != null) { 
        v.add(obj);
      }
    }
    return v;
  }

  public Collection<ManagedIdentifiableProxy> values() {
	  int sz = cache.size();
	    Vector<ManagedIdentifiableProxy> v = new Vector<ManagedIdentifiableProxy>(sz > 0 ? sz + 5: 1);
	    ManagedIdentifiableProxy obj;
	    Set<Map.Entry<Object, Reference<ManagedIdentifiableProxy>>> entrySet = cache.entrySet();
	    Iterator<Map.Entry<Object, Reference<ManagedIdentifiableProxy>>> iter = entrySet.iterator();
	    while (iter.hasNext()) { 
	      Map.Entry<Object, Reference<ManagedIdentifiableProxy>> map = iter.next();
	      if (map != null) { 
	        obj = this.unwrap(map.getValue());
	        if (obj != null) {
	          v.addElement(obj);
	        }
	      }
	    }
	    return v;
    }

  public ObjectCache getBackupCache() {
      return null;
  }

  public void monitorCache(ObjectCacheAbstractMonitor monitor) {
    Set<Map.Entry<Object, Reference<ManagedIdentifiableProxy>>> entrySet = cache.entrySet();
    Iterator<Map.Entry<Object, Reference<ManagedIdentifiableProxy>>> iter = entrySet.iterator();
    while (iter.hasNext()) { 
    	Map.Entry<Object, Reference<ManagedIdentifiableProxy>> map = iter.next();
      if (map != null) { 
    	ManagedIdentifiableProxy obj = this.unwrap(map.getValue());
        if (obj != null) { 
          long time = obj.getCacheTime();
          // only handle objects where the cache time is set
          if (time > -1 && time < monitor.getInternalTime()) {
          }
        }
      }
    }
  }

}
