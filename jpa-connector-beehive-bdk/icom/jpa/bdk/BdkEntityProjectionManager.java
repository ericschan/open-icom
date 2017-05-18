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
package icom.jpa.bdk;

import icom.jpa.CachedState;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.dao.AttributeMap;
import icom.jpa.dao.EntityProjectionManager;
import icom.jpa.rt.ManagedEntityProxyImpl;

import java.util.HashSet;
import java.util.Hashtable;

public class BdkEntityProjectionManager extends EntityProjectionManager implements BdkProjectionManager {
	
	HashSet<Projection> loadedProjections = new HashSet<Projection>(5);
	
	public BdkEntityProjectionManager() {
		super();
	}
	
	BdkAbstractDAO getDAO() {
		return (BdkAbstractDAO) dao;
	}
	
	public void loadFull(ManagedEntityProxyImpl proxy) {
		Projection proj = getDAO().loadFull(proxy);
		if (proj == Projection.FULL) {
			loadedProjections.remove(Projection.BASIC);
			loadedProjections.remove(Projection.META);
			loadedProjections.add(Projection.FULL);
			proxy.setReady();
		}
	}

	public void load(ManagedEntityProxyImpl proxy, String attributeName, Object parameter) {
		Projection proj = getDAO().load(proxy, attributeName, parameter);
		if (proj != null) {
			if (proj == Projection.FULL) {
				loadedProjections.remove(Projection.BASIC);
				loadedProjections.remove(Projection.META);
				loadedProjections.add(proj);
			} else if (proj == Projection.META) {
				loadedProjections.remove(Projection.BASIC);
				loadedProjections.add(proj);
			} else if (proj == Projection.BASIC) {
				loadedProjections.add(proj);
			} else if (proj == Projection.SECURITY) {
				loadedProjections.add(proj);
			} else if (!loadedProjections.contains(Projection.FULL)) {
				loadedProjections.add(proj);
			}
			proxy.setReady();
		} else {
			// if projection is null, it is lazy loading of a specific attribute
			if (lazyLoadedAttributes == null) {
				lazyLoadedAttributes = new Hashtable<String, AttributeMap>();
			}
			if (parameter != null) {
				AttributeMap map = lazyLoadedAttributes.get(attributeName);
				if (map != null) {
					if (! map.contains(parameter)) {
						map.addParameter(parameter);
					}
				} else {
					map = new AttributeMap(attributeName);
					map.addParameter(parameter);
					lazyLoadedAttributes.put(attributeName, map);
				}
			} else {
				AttributeMap map = new AttributeMap(attributeName);
				lazyLoadedAttributes.put(attributeName, map);
			}
			proxy.setReady();
		}
	}

	public void copyLoadedProjection(ManagedObjectProxy proxy, Object csiObject, Object projection) {
		Projection proj = (Projection) projection;
		synchronized (proxy) {
			if (isPooled(proxy, proj)) {
				dao.copyObjectStateForAProjection(proxy, csiObject, proj);
				if (proj == Projection.FULL) {
					loadedProjections.remove(Projection.BASIC);
					loadedProjections.remove(Projection.META);
					loadedProjections.add(proj);
				} else if (proj == Projection.META) {
					loadedProjections.remove(Projection.BASIC);
					loadedProjections.add(proj);
				} else if (!loadedProjections.contains(Projection.FULL)) {
					loadedProjections.add(proj);
				}
				proxy.setReady();
			}
		}
	}
	
	public Projection getLastLoadedProjection(ManagedObjectProxy proxy) {
		if (loadedProjections.contains(Projection.FULL)) {
			return Projection.FULL;
		} else if (loadedProjections.contains(Projection.META)) {
			return Projection.META;
		} else if (loadedProjections.contains(Projection.BASIC)) {
			return Projection.BASIC;
		} else {
			return Projection.EMPTY;
		}
	}
	
	public boolean isBetweenProjections(ManagedObjectProxy proxy, String attributeName, Object prevProjection, Object nextProjection) {
		Projection prevProj = (Projection) prevProjection;
		Projection nextProj = (Projection) nextProjection;
		return (! dao.isPartOfProjection(attributeName, prevProj)) && dao.isPartOfProjection(attributeName, nextProj);
	}
	
	public void committed(ManagedObjectProxy proxy) {
		if (proxy.isNew()) {
			loadedProjections.clear();
			loadedProjections.add(Projection.BASIC);
		}
	}
	
	public boolean isPooledForFull(ManagedObjectProxy proxy) {
		return isPooled(proxy, Projection.FULL);
	}
	
	public boolean isPooled(ManagedObjectProxy proxy, Projection proj) {
    	synchronized (proxy) {
    		CachedState cachedState = proxy.getCacheState();
    		if (cachedState == CachedState.POOLED) {
	    		return true;
	    	} else if (cachedState == CachedState.READY 
	    			|| cachedState == CachedState.DIRTYDEPENDENT
	    			|| cachedState == CachedState.DIRTY) {
	    		Projection lastProj = getLastLoadedProjection(proxy);
	    		return !lastProj.contains(proj);
	    	} else {
	    		return false;
	    	}
    	}
    }
	
	public boolean isPooled(String attributeName, Object parameter) {
		if (attributeName == null) {
			if (loadedProjections.size() == 0) {
				return true;
			}
		}
		if (dao.isLazyLoadedAttribute(attributeName)) {
			if (lazyLoadedAttributes != null) {
				if (parameter == null) {
					if (lazyLoadedAttributes.containsKey(attributeName)) {
						return false;
					}
				} else {
					AttributeMap map = lazyLoadedAttributes.get(attributeName);
					if (map != null) {
						if (map.contains(parameter)) {
							return false;
						}
					}
				}
    		}
			return true;
		}
		for (Projection proj : loadedProjections) {
			if (dao.isPartOfProjection(attributeName, proj)) {
				return false;
			}
		}
		return true;
    }
	
	public void clear() {
		if (loadedProjections != null) {
    		loadedProjections.clear();
    	}
    	super.clear();
	}
    
}
