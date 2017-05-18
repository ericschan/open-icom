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
package icom.jpa.csi;

import icom.info.IdentifiableInfo;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;

import java.util.HashMap;
import java.util.HashSet;

import oracle.csi.IdentifiableHandle;
import oracle.csi.annotation.ConcreteBomType;
import oracle.csi.projections.Projection;

public abstract class CsiIdentifiableDAO extends CsiAbstractDAO {
	
	
	protected HashSet<Enum<?>> basicAttributes = new HashSet<Enum<?>>();
	
	{
		basicAttributes.add(IdentifiableInfo.Attributes.id);
		basicAttributes.add(IdentifiableInfo.Attributes.changeToken);
	}

	protected HashSet<Enum<?>> metaAttributes = new HashSet<Enum<?>>();
	
	protected HashSet<Enum<?>> fullAttributes = new HashSet<Enum<?>>();
	
	protected HashSet<Enum<?>> securityAttributes = new HashSet<Enum<?>>();
	
	protected HashSet<Enum<?>> lazyAttributes = new HashSet<Enum<?>>();
	
	HashMap<String, Projection> basicProjectionMap;
	HashMap<String, Projection> metaProjectionMap;
	HashMap<String, Projection> fullProjectionMap;
	HashMap<String, Projection> securityProjectionMap;
	HashMap<String, Projection> lazyProjectionMap;
	
	boolean needToSetupProjectionMaps = true;
	
	protected CsiIdentifiableDAO() {
		basicProjectionMap = new HashMap<String, Projection>();
		metaProjectionMap = new HashMap<String, Projection>();
		fullProjectionMap = new HashMap<String, Projection>();
		securityProjectionMap = new HashMap<String, Projection>();
		lazyProjectionMap = new HashMap<String, Projection>();
	}
	
	void setupProjectionMaps() {
		if (!needToSetupProjectionMaps) {
			return;
		} else {
			needToSetupProjectionMaps = false;
		}
		for (Enum<?> attr :basicAttributes) {
			synchronized (basicProjectionMap) {
				basicProjectionMap.put(attr.name(), Projection.BASIC);
			}
			synchronized (metaProjectionMap) {
				metaProjectionMap.put(attr.name(), Projection.META);
			}
			synchronized (fullProjectionMap) {
				fullProjectionMap.put(attr.name(), Projection.FULL);
			}
		}
		for (Enum<?> attr : metaAttributes) {
			synchronized (metaProjectionMap) {
				metaProjectionMap.put(attr.name(), Projection.META);
			}
			synchronized (fullProjectionMap) {
				fullProjectionMap.put(attr.name(), Projection.FULL);
			}
		}
		for (Enum<?> attr : fullAttributes) {
			synchronized (fullProjectionMap) {
				fullProjectionMap.put(attr.name(), Projection.FULL);
			}
		}
		for (Enum<?> attr : securityAttributes) {
			synchronized (securityProjectionMap) {
				securityProjectionMap.put(attr.name(), Projection.SECURITY);
			}
		}
		for (Enum<?> attr : lazyAttributes) {
			synchronized (lazyProjectionMap) {
				lazyProjectionMap.put(attr.name(), Projection.EMPTY);
			}
		}
	}
	
	public Projection getProjection(String attributeName) {
		setupProjectionMaps();
		Projection proj = basicProjectionMap.get(attributeName);
		if (proj != null) {
			return proj;
		}
		proj = metaProjectionMap.get(attributeName);
		if (proj != null) {
			return proj;
		}
		proj = fullProjectionMap.get(attributeName);
		if (proj != null) {
			return proj;
		}
		proj = securityProjectionMap.get(attributeName);
		if (proj != null) {
			return proj;
		}
		proj = lazyProjectionMap.get(attributeName);
		if (proj != null) {
			return null;
		}
		return Projection.FULL;
	}
	
	public boolean isPartOfProjection(String attributeName,  Projection proj) {
		setupProjectionMaps();
		if (proj == Projection.BASIC) {
			if (basicProjectionMap.get(attributeName) != null) {
				return true;
			}
		}
		if (proj == Projection.META) {
			if (metaProjectionMap.get(attributeName) != null) {
				return true;
			}
		}
		if (proj == Projection.FULL) {
			if (fullProjectionMap.get(attributeName) != null) {
				return true;
			}
		}
		if (proj == Projection.SECURITY) {
			if (securityProjectionMap.get(attributeName) != null) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isBetweenProjections(String attributeName, Projection prevProj, Projection nextProj) {
		return (!isPartOfProjection(attributeName, prevProj) &&
				isPartOfProjection(attributeName, nextProj));
	}
	
	public boolean isLazyLoadedAttribute(String attributeName) {
		setupProjectionMaps();
		if (lazyProjectionMap.get(attributeName) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public abstract Class<? extends IdentifiableHandle> getIdentifiableHandleClass();
	
	public String getBomTypeName() {
		Class<? extends IdentifiableHandle> cls = getIdentifiableHandleClass();
		if (cls != null) {
			ConcreteBomType bomType = cls.getAnnotation(ConcreteBomType.class);
			return bomType.bomId();
		} else {
			return null;
		}
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName) {
		return load(obj, attributeName, null);
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		return null;
	}

	public void save(ManagedIdentifiableProxy obj) {
		throw new RuntimeException();
	}
	
	public void delete(ManagedIdentifiableProxy obj) {
		throw new RuntimeException();
	}
	
	public boolean isCacheable() {
		return true;
	}

}
