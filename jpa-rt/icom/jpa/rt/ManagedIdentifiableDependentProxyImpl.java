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

import icom.ObjectIdTrait;
import icom.jpa.CachedState;
import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableDependentProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.dao.MetaDataApplicationDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManagedIdentifiableDependentProxyImpl extends ManagedDependentProxyImpl implements ManagedIdentifiableDependentProxy {
	
	List<ParentInformation> parents = Collections.synchronizedList(new ArrayList<ParentInformation>(1));
	
	CachedState cachedState;
	boolean isMetaDataAttachment;
	boolean ignoreDaoError = false;
	
	enum Mode { NEW, POOLED };
	
	public ManagedIdentifiableDependentProxyImpl(ManagedIdentifiableDependentProxyImpl.Mode mode, Identifiable pojoIdentifiable, ManagedObjectProxy parent, String parentAttributeName, DataAccessObject dao) {
		super(parent, parentAttributeName, pojoIdentifiable, dao);
		PersistenceContextImpl context = (PersistenceContextImpl) parent.getPersistenceContext();
		ignoreDaoError = context.isIgnoreDaoError();
		ParentInformation parentInfo = new ParentInformation(parent, parentAttributeName);
		parents.add(parentInfo);
		if (dao instanceof MetaDataApplicationDAO) {
			this.setMetaDataAttachment(true);
		} else {
			this.setMetaDataAttachment(false);
		}
		if (mode == Mode.NEW) {
			setNew();
		} else {
			setPooled();
		}
	}
	
	public boolean isIgnoreDaoError() {
		return ignoreDaoError;
	}
	
	public ManagedObjectProxy getParent() {
		// TODO: pick the right parent, not just the first parent
		return parents.get(0).parent;
	}
	
	public String getParentAttributeName() {
		// TODO: pick the right parent, not just the first parent
		return parents.get(0).parentAttributeName;
	}
	
	public boolean hasParent(ManagedObjectProxy parent) {
		for (ParentInformation parentInfo : parents) {
			if (parentInfo.parent == parent) {
				return true;
			}
		}
		return false;
	}
	
	public void addAnotherParent(ManagedObjectProxy parent, String parentAttributeName) {
		ParentInformation parentInfo = new ParentInformation(parent, parentAttributeName);
		parents.add(parentInfo);
	}
	
	public void setDataAccessObject(DataAccessObject dao) {
		super.setDataAccessObject(dao);
		if (dao instanceof MetaDataApplicationDAO) {
			this.setMetaDataAttachment(true);
		} else {
			this.setMetaDataAttachment(false);
		}
	}

	AbstractDAO getDAO() {
		return (AbstractDAO) dao;
	}

	public Object getObjectId() {
		ObjectIdTrait id = getPojoIdentifiable().getObjectId();
		if (id != null) {
			Object c = id.getObjectId();
			return c;
		} else {
			return null;
		}
	}
	
	public void setObjectId(Object id) {
		
	}

	public Object getChangeToken() {
		Object s = getPojoIdentifiable().getVersionId().getToken();
		return s;
	}

	public Identifiable getPojoIdentifiable() {
		return (Identifiable) getPojoObject();
	}
	
/*
	public void copyLoadedProjection(Object csiObject, Projection proj) {
		if (isPooled(null) || isReady()) {
			dao.copyCsiObjectState(this, csiObject, proj);
			setReady();
		}
	}
	
	public void copyLazyAttribute(String attributeName, Object parameter, Object value) {
		IdentifiableDAO.assignAttributeValue(getPojoIdentifiable(), attributeName, value);
	}
*/
	public boolean isDeleted() {
		if (cachedState == CachedState.NEW) {
			return false;
		} else if (cachedState == CachedState.DIRTY) {
			return false;
		} else if (cachedState == CachedState.DELETED) {
			return true;
		} else {
			if (parents.size() == 1) {
				if (getParent() != null) {
					return getParent().isDeleted();
				} else {
					return false;
				}
			} else {
				return false; // TODO
			}
		}
	}
	
    public void setDeleted() {
    	cachedState = CachedState.DELETED;
    }

	public boolean isDirty() {
		if (cachedState == CachedState.NEW) {
			return false;
		} else if (cachedState == CachedState.DELETED) {
			return false;
		} else if (cachedState == CachedState.DIRTY) {
			return true;
		} else {
			if (parents.size() == 1) {
				if (getParent() != null) {
					return getParent().isDirty();
				} else {
					return false;
				}
			} else {
				return false; // TODO
			}
		}
	}
	
	public void setDirty() {
		cachedState = CachedState.DIRTY;
		for (ParentInformation parentInfo : parents) {
			if (isMetaDataAttachment()) {
				parentInfo.parent.setDirtyDependent();
			} else {
				parentInfo.parent.setDirty();
			}
		}
    }
	
	public void setDirty(String propertyName) {
		cachedState = CachedState.DIRTY;
		for (ParentInformation parentInfo : parents) {
			parentInfo.parent.setDirty(getParentAttributeName());
		}
	}
	
	public boolean isDirtyDependent() {
    	if (cachedState == CachedState.NEW) {
			return false;
		} else if (cachedState == CachedState.DELETED) {
			return false;
		} else if (cachedState == CachedState.DIRTY) {
			return false;
		} else if (cachedState == CachedState.DIRTYDEPENDENT) {
			return true;
		} else {
			if (parents.size() == 1) {
				if (getParent() != null) {
					return getParent().isDirtyDependent();
				} else {
					return false;
				}
			} else {
				return false;  // TODO
			}
		}
    }
	
	public void setDirtyDependent() {
		if (cachedState != CachedState.DIRTY) {
			cachedState = CachedState.DIRTYDEPENDENT;
		}
		for (ParentInformation parentInfo : parents) {
			parentInfo.parent.setDirtyDependent();
		}
	}

	public boolean isInvalid() {
		if (cachedState == CachedState.INVALID) {
			return true;
		} else {
			if (parents.size() == 1) {
				if (getParent() != null) {
					return getParent().isInvalid();
				} else {
					return false;
				}
			} else {
				return false;  // TODO
			}
		}
	}
	
	public void setInvalid() {
		cachedState = CachedState.INVALID;
    }

	public boolean isNew() {
		if (cachedState == CachedState.DELETED) {
			return false;
		} else if (cachedState == CachedState.DIRTY) {
			return false;
		} else if (cachedState == CachedState.NEW) {
			return true;
		} else {
			if (parents.size() == 1) {
				if (getParent() != null) {
					return getParent().isNew();
				} else {
					return false;
				}
			} else {
				return false; // TODO
			}
		}
	}
	
	public void setNew() {
		cachedState = CachedState.NEW;
    }

	public boolean isPooled(String attributeName) {
	    if (cachedState == CachedState.POOLED) {
	    	return true;
	    } else {
	    	if (parents.size() == 1) {
	    		if (getParentAttributeName() != null) {
	    			return getParent().isPooled(getParentAttributeName());
	    		} else {
	    			return getParent().isPooled(attributeName);
	    		}
			} else {
				return false; // TODO
			}
	    }
    }
	
	public boolean isPooled(String attributeName, Object parameter) {
	    if (cachedState == CachedState.POOLED) {
	    	return true;
	    } else {
	    	if (parents.size() == 1) {
	    		if (getParentAttributeName() != null) {
	    			return getParent().isPooled(getParentAttributeName());
	    		} else {
	    			return getParent().isPooled(attributeName);
	    		}
			} else {
				return false; // TODO
			}
	    }
    }

    public void setPooled() {
    	cachedState = CachedState.POOLED;
    	getBeanInfo().clearObject(getPojoIdentifiable());
    	if (changeRecords != null) {
    		changeRecords.clear();
    	}
    }
    
    public void checkReadyAndSetPooled() {
        if (isReady()) {
            setPooled();
        }
    }

	public boolean isReady() {
		if (cachedState == CachedState.READY) {
	    	return true;
	    } else {
	    	if (parents.size() == 1) {
	    		return getParent().isReady();
			} else {
				return false; // TODO
			}
	    }
	}
	
	public void setReady() {
		cachedState = CachedState.READY;
    }

	public void resetReady() {
		cachedState = CachedState.READY;
	}
	
	public void refresh() {
    	synchronized(this) {
    		if (!isNew() && !isInvalid()) {
    			setPooled();
    		}
    	}
    }

	public void flush() {
		// should be called on parent ManagedObjectProxy
	}

	public void uncached() {
		// The uncached callback method of parent will clear the dependent objects
    	// the dependent object should be cleared only when the parent is uncached
    }

	public boolean isMetaDataAttachment() {
		return isMetaDataAttachment;
	}

	public void setMetaDataAttachment(boolean isMetaDataAttachment) {
		this.isMetaDataAttachment = isMetaDataAttachment;
	}

}
