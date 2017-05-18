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

import icom.jpa.ManagedNonIdentifiableDependentProxy;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;

import javax.persistence.PersistenceException;

public class ManagedNonIdentifiableDependentProxyImpl extends ManagedDependentProxyImpl implements ManagedNonIdentifiableDependentProxy {
	
	ParentInformation parentInfo = null;
	
	public ManagedNonIdentifiableDependentProxyImpl(Persistent pojoDependent, ManagedObjectProxy parent, String parentAttributeName, DataAccessObject dao) {
		super(parent, parentAttributeName, pojoDependent, dao);
		parentInfo = new ParentInformation(parent, parentAttributeName);
	}
	
	public ManagedObjectProxy getParent() {
		return parentInfo.parent;
	}
	
	public String getParentAttributeName() {
		return parentInfo.parentAttributeName;
	}
	
	public boolean hasParent(ManagedObjectProxy parent) {
		return parentInfo.parent == parent; 
	}
	
    public boolean isReady() {
    	if (getParent() != null) {
    		return getParent().isReady();
		} else {
			return false;
		}
    }
    
    public void setReady() {
    	
    }
    
    public void resetReady() {
    	
    }

    public boolean isNew() {
    	if (getParent() != null) {
			return getParent().isNew();
		} else {
			return false;
		}
    }
    
    public void setNew() {
    	
    }

    public boolean isDirty() {
    	if (getParent() != null) {
			return getParent().isDirty();
		} else {
			return false;
		}
    }
    
    public void setDirty() {
    	if (getParent() != null) {
			getParent().setDirty();
		}
    }
    
    public void setDirty(String propertyName) {
    	if (getParent() != null) {
			getParent().setDirty(getParentAttributeName());
		}
	}
    
    public boolean isDirtyDependent() {
    	if (getParent() != null) {
			return getParent().isDirtyDependent();
		} else {
			return false;
		}
    }
    
    public void setDirtyDependent() {
    	if (getParent() != null) {
			getParent().setDirtyDependent();
		}
    }
    
    public boolean isDeleted() {
    	if (getParent() != null) {
			return getParent().isDeleted();
		} else {
			return false;
		}
    }
    
    public void setDeleted() {
    	
    }

    public boolean isPooled(String attributeName) {
    	if (getParent() != null) {
    		boolean parentIsPooled = false;
    		if (getParentAttributeName() != null) {
    			parentIsPooled = getParent().isPooled(getParentAttributeName());
    		} else {
    			parentIsPooled = getParent().isPooled(attributeName);
    		}
    		if (parentIsPooled) {
    			throw new PersistenceException("The dependent state is invalid when parent entity is pooled");
    		} else {
    			return false;
    		}
		} else {
			return false;
		}
    }
    
    public boolean isPooled(String attributeName, Object parameter) {
    	if (getParent() != null) {
    		boolean parentIsPooled = false;
    		if (getParentAttributeName() != null) {
    			parentIsPooled =  getParent().isPooled(getParentAttributeName(), parameter);
    		} else {
    			parentIsPooled =  getParent().isPooled(attributeName, parameter);
    		}
    		if (parentIsPooled) {
    			throw new PersistenceException("The dependent state is invalid when parent entity is pooled");
    		} else {
    			return false;
    		}
		} else {
			return false;
		}
    }
    
    public void setPooled() {
    	
    }
    
    public boolean isInvalid() {
    	if (getParent() != null) {
			return getParent().isInvalid();
		} else {
			return false;
		}
    }
    
    public void setInvalid() {
    	
    }
    
	public void addAttributeChangeRecord(String attributeName) {
		super.addAttributeChangeRecord(attributeName);
	}
	
	public void addAttributeChangeRecord(String attributeName, Action action, 
			Object key, Object value) {
		super.addAttributeChangeRecord(attributeName, action, key, value);
	}
	
}
