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

import icom.jpa.CachedState;
import icom.jpa.ManagedDependentProxy;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.dao.DependentProjectionManager;
import icom.jpa.dao.ProjectionManager;
import icom.jpa.dao.ProjectionManagerFactory;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;

class ParentInformation {
	ManagedObjectProxy parent;
	String parentAttributeName;
	
	ParentInformation(ManagedObjectProxy parent, String parentAttributeName) {
		this.parent = parent;
		this.parentAttributeName = parentAttributeName;
	}
}

public abstract class ManagedDependentProxyImpl extends ManagedObjectProxyImpl implements ManagedDependentProxy {
	
	DependentProjectionManager providerProxy;
	
	public ManagedDependentProxyImpl(ManagedObjectProxy parent, String parentAttributeName, Persistent pojo, DataAccessObject dao) {
		super(parent.getPersistenceContext(), pojo, dao);
		PersistenceContextImpl context = (PersistenceContextImpl) parent.getPersistenceContext();
		ProjectionManagerFactory factory = context.getProjectionManagerFactory();
		this.providerProxy = (DependentProjectionManager) factory.createDependentProjectionManager();
		this.providerProxy.setDAO(dao);
	}
	
	public abstract ManagedObjectProxy getParent();
	
	public abstract String getParentAttributeName();
	
	public abstract boolean hasParent(ManagedObjectProxy parent);
	
	public ProjectionManager getProviderProxy() {
		return providerProxy;
	}

	public int getClassOrdinal() {
		return getParent().getClassOrdinal();
	}
	
	public CachedState getCacheState() {
		return getParent().getCacheState();
	}

	public long getCacheTime() {
		return getParent().getCacheTime();
	}
	
	public void setCacheTime(long cacheTime) {
		getParent().setCacheTime(cacheTime);
	}
	
	public void addAttributeChangeRecord(String attributeName) {
		super.addAttributeChangeRecord(attributeName);
		if (getParentAttributeName() != null) {
			getParent().addAttributeChangeRecord(getParentAttributeName());
		}
	}
	
	public void addAttributeChangeRecord(String attributeName, Action action, 
			Object key, Object value) {
		super.addAttributeChangeRecord(attributeName, action, key, value);
		if (getParentAttributeName() != null) {
			getParent().addAttributeChangeRecord(getParentAttributeName());
		}
	}
	
	public void load(String attributeName) {
		Object proj = providerProxy.load(this, attributeName);
		if (proj != null) {
			// to do any generalization
		} else {
			if (getParent() != null) {
				if (getParentAttributeName() != null) {
					getParent().load(getParentAttributeName());
				} else {
					getParent().load(attributeName);
				}
			}
		}
	}
	
	public void load(String attributeName, Object parameter) {
		Object proj = providerProxy.load(this, attributeName, parameter);
		if (proj != null) {
			// to do any generalization
		} else {
			if (getParent() != null) {
				if (getParentAttributeName() != null) {
					getParent().load(getParentAttributeName(), parameter);
				} else {
					getParent().load(attributeName, parameter);
				}
			}
		}
	}
	
	public String getEntityAttributeName() {
		String name = getParent().getEntityAttributeName();
		if (name == null) {
			name = getParentAttributeName();
		} 
		return name;
	}
	
	public boolean isManagedObjectEditable() {
    	return getParent().isManagedObjectEditable();
    }
	
	public boolean isManagedObjectEditable(String attributeName) {
		return getParent().isManagedObjectEditable(getParentAttributeName());
	}
	
	public boolean isMarkedForSorted() {
		return false;
	}
	
	public boolean isMarkedForLoopDetection() {
		return false;
	}
	
/*
	public boolean isBetweenProjections(String attributeName, Projection prevProj, Projection nextProj) {
		if (getParentAttributeName() != null) {
			return parent.isBetweenProjections(getParentAttributeName(), prevProj, nextProj);
		} else {
			return false;
		}
	}
*/
}
