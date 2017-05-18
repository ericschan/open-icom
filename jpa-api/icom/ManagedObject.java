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
package icom;

import icom.jpa.Dependent;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlTransient;


@XmlTransient
public abstract class ManagedObject {
	
	protected transient ManagedObjectProxy mop = null;
	
	public ManagedObjectProxy getManagedObjectProxy() {
		return mop;
	}
	
	public boolean isEditable() {
        return true;
    }
	
	public void delete() {
	    
	}
	
	protected void preGet(String propertyName) {
	    if (mop != null) {
                if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                    return;
                }
	    }
            if (mop != null && mop.isPooled(propertyName)) {
                mop.load(propertyName);
            }
	}
	
	protected boolean preSet(String propertyName, Object oldValue, Object newValue) {
	    if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return true;
            }
        }
		if (mop != null) {
			if (! mop.isManagedObjectEditable(propertyName)) {
				throw new RuntimeException("Property is not editable: " + propertyName);
			}
		}
		if (oldValue == newValue || 
				(oldValue != null && oldValue.equals(newValue))) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void postSet(String propertyName) {
	    if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return;
            }
        }
		if (mop != null) {
			mop.addAttributeChangeRecord(propertyName);
			if (!mop.isNew()) {
				mop.setDirty(propertyName);
			}
		}
	}
	
	protected void preFilterCollection(String propertyName, Collection<? extends Persistent> elements) {
		if (mop != null) {
			if (elements != null) {
				ArrayList<Persistent> purgeList = new ArrayList<Persistent>();
				for (Persistent persistent: elements) {
				    ManagedObject mo = (ManagedObject) persistent;
					if (mo.getManagedObjectProxy() == null) {
						purgeList.add(persistent);
					}
				}
				if (purgeList.size() > 0) {
					elements.removeAll(purgeList);
				}
			}
		}
	}
	
	protected boolean preSetBasic(String propertyName, Object oldValue, Object newValue) {
	    if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return true;
            }
        }
        if (oldValue == newValue) {
            return true;
        }
        if (newValue instanceof ManagedObject) {
            ManagedObject mo = (ManagedObject) newValue;
            ManagedObjectProxy dependentMop = mo.getManagedObjectProxy();
            if (dependentMop != null) {
                throw new RuntimeException("Cannot reassociate a managed dependent object");
            }
        }
        return preSet(propertyName, oldValue, newValue);
    }
    
    protected void postSetBasic(String propertyName, Object newValue) {
        if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return;
            }
        }
        if (mop != null) {
            if (newValue instanceof ManagedObject) {
                mop.manageDependent(newValue, propertyName);
            }
            mop.addAttributeChangeRecord(propertyName);
            if (!mop.isNew()) {
                mop.setDirty(propertyName);
            }
        }
    }
	
	protected boolean preSetEmbedded(String propertyName, Object oldValue, Object newValue) {
	    if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return true;
            }
        }
		if (oldValue == newValue) {
			return true;
		}
		ManagedObject mo = (ManagedObject) newValue;
		ManagedObjectProxy dependentMop = mo.getManagedObjectProxy();
		if (dependentMop != null) {
			throw new RuntimeException("Cannot reassociate a managed dependent object");
		}
		return preSet(propertyName, oldValue, newValue);
	}
	
	protected void postSetEmbedded(String propertyName, Object newValue) {
	    if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return;
            }
        }
		if (mop != null) {
			  mop.manageDependent(newValue, propertyName);
			  mop.addAttributeChangeRecord(propertyName);
			  if (!mop.isNew()) {
				  mop.setDirty(propertyName);
			  }
		  }
	}
	
	protected boolean preSetAmbiguous(String propertyName, Object oldValue, Object newValue) {
	    if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return true;
            }
        }
        if (oldValue == newValue) {
            return true;
        }
        if (newValue instanceof ManagedObject) {
            if (!(newValue instanceof AbstractEntity)) {
                ManagedObject mo = (ManagedObject) newValue;
                ManagedObjectProxy otherMop = mo.getManagedObjectProxy();
                if (otherMop != null) {
                    throw new RuntimeException("Cannot reassociate a managed dependent object");
                }
            }
        }
        return preSet(propertyName, oldValue, newValue);
    }
    
    protected void postSetAmbiguous(String propertyName, Object newValue) {
        if (mop != null) {
            if (! mop.getBeanInfo().hasAttribute(propertyName)) {
                return;
            }
        }
        if (mop != null) {
            if (newValue instanceof ManagedObject) {
                if (!(newValue instanceof AbstractEntity)) {
                    mop.manageDependent(newValue, propertyName);
                }
            }
            mop.addAttributeChangeRecord(propertyName);
            if (!mop.isNew()) {
                mop.setDirty(propertyName);
            }
        }
    }
    
    protected void preModifyEmbedded(String propertyName, Object newValue) {
        if (mop != null) {
            if (! mop.isManagedObjectEditable(propertyName)) {
                throw new RuntimeException("Property is not editable: " + propertyName);
            }
        }
        if (mop != null && mop.isPooled(propertyName)) {
            mop.load(propertyName);
        }
    }
    
    protected void postModifyEmbedded(String propertyName, Object template, Object newValue) {
        if (mop != null) {
            mop.addAttributeChangeRecord(propertyName,
                    ManagedObjectProxy.Action.modifyObject, (Persistent) template, newValue);
            if (!mop.isNew()) {
                mop.setDirty(propertyName);
            }
        }
    }

	protected void preAddEmbedded(String propertyName, Object newValue) {
		ManagedObject mo = (ManagedObject) newValue;
		ManagedObjectProxy dependentMop = mo.getManagedObjectProxy();
		if (dependentMop != null) {
			throw new RuntimeException("Cannot reassociate a managed dependent object");
		}
		if (mop != null) {
			if (! mop.isManagedObjectEditable(propertyName)) {
				throw new RuntimeException("Property is not editable: " + propertyName);
			}
		}
		if (mop != null && mop.isPooled(propertyName)) {
			mop.load(propertyName);
		}
	}
	
	protected void postAddEmbedded(String propertyName, Object template, Object newValue) {
		if (mop != null) {
			mop.manageDependent(newValue, propertyName);
			mop.addAttributeChangeRecord(propertyName,
					ManagedObjectProxy.Action.addObject, (Persistent) template, newValue);
			if (!mop.isNew()) {
				mop.setDirty(propertyName);
			}
		}
	}
	
	protected void preRemoveEmbedded(String propertyName, Object oldValue) {
		if (mop != null) {
			if (! mop.isManagedObjectEditable(propertyName)) {
				throw new RuntimeException("Property is not editable: " + propertyName);
			}
		}
		if (mop != null && mop.isPooled(propertyName)) {
			mop.load(propertyName);
		}
	}

	protected void postRemoveEmbedded(String propertyName, Object template, Object oldValue) {
		if (mop != null) {
			mop.detachDependent((Dependent) oldValue);
			mop.addAttributeChangeRecord(propertyName,
					ManagedObjectProxy.Action.removeObject, (Persistent) template, oldValue);
			if (!mop.isNew()) {
				mop.setDirty(propertyName);
			}
		}
	}
	
	protected void preAddReference(String propertyName, Object newValue) {
		if (mop != null) {
			if (! mop.isManagedObjectEditable(propertyName)) {
				throw new RuntimeException("Property is not editable: " + propertyName);
			}
		}
		if (mop != null && mop.isPooled(propertyName)) {
			mop.load(propertyName);
		}
	}
	
	protected void postAddReference(String propertyName, Object key, Object newValue) {
		if (mop != null) {
			mop.addAttributeChangeRecord(propertyName,
					ManagedObjectProxy.Action.addObject, key, newValue);
			if (!mop.isNew()) {
				mop.setDirty(propertyName);
			}
		}
	}
	
	protected void preRemoveReference(String propertyName, Object oldValue) {
		if (mop != null) {
			if (! mop.isManagedObjectEditable(propertyName)) {
				throw new RuntimeException("Property is not editable: " + propertyName);
			}
		}
		if (mop != null && mop.isPooled(propertyName)) {
			mop.load(propertyName);
		}
	}

	protected void postRemoveReference(String propertyName, Object template, Object oldValue) {
		if (mop != null) {
			mop.addAttributeChangeRecord(propertyName,
					ManagedObjectProxy.Action.removeObject, (Persistent) template, oldValue);
			if (!mop.isNew()) {
				mop.setDirty(propertyName);
			}
		}
	}
	
	/*
	void modifyAddress(EntityAddress address) {
		{ // this block is to be replaced by byte code injection
			if (mop != null) {
				EntityAddress remove = address.clone();
				EntityAddress add = address.clone();
				mop.addAttributeChangeRecord(AddressableInfo.Attributes.addresses.name(), 
						ManagedObjectProxy.Action.removeObject, remove, remove);
				mop.addAttributeChangeRecord(AddressableInfo.Attributes.addresses.name(), 
						ManagedObjectProxy.Action.addObject, add, add);
				if (!mop.isNew()) {
					mop.setDirty();
				}
			}
		}
	}
	*/
	
}
