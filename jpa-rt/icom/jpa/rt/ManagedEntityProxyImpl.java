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

import icom.VersionIdTrait;
import icom.ManagedObject;
import icom.jpa.CachedState;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessObject;
import icom.jpa.dao.EntityProjectionManager;
import icom.jpa.dao.ProjectionManager;
import icom.jpa.dao.ProjectionManagerFactory;
import icom.jpa.Identifiable;

import javax.persistence.PersistenceException;

public class ManagedEntityProxyImpl extends ManagedObjectProxyImpl implements ManagedIdentifiableProxy {
	
	long cacheTime;
	CachedState cachedState;
	Object objectId;
	boolean ignoreDaoError = false;
	boolean markedForSorted = false;
	boolean markedForLoopDetection = false;
	
	EntityProjectionManager providerProxy;
	
	enum Mode { NEW, POOLED };
	
	public ManagedEntityProxyImpl(Mode mode, PersistenceContextImpl context, Identifiable pojoIdentifiable, DataAccessObject dao) {
		super(context, pojoIdentifiable, dao);
		ignoreDaoError = context.isIgnoreDaoError();
		this.objectId = pojoIdentifiable.getObjectId().getObjectId();
		ProjectionManagerFactory factory = context.getProjectionManagerFactory();
		this.providerProxy = (EntityProjectionManager) factory.createEntityProjectionManager();
		this.providerProxy.setDAO(dao);
		if (mode == Mode.NEW) {
			setNew();
		} else {
			setPooled();
		}
	}
	
	public void setPojoIdentifiable(Identifiable pojoIdentifiable) {
		this.pojo = pojoIdentifiable;
	}
	
	public Identifiable getPojoIdentifiable() {
		return (Identifiable) getPojoObject();
	}
	
	AbstractDAO getDAO() {
		return (AbstractDAO) dao;
	}
	
	public Object getObjectId() {
		return objectId;
	}
	
	public void setObjectId(Object id) {
		objectId = id;
	}
	
	public Object getChangeToken() {
		VersionIdTrait vid = getPojoIdentifiable().getVersionId();
		if (vid != null) {
			Object s = vid.getToken();
			return s;
		}
		return null;
	}
	
	public EntityTransactionImpl getEntityTransaction() {
		return context.manager.transaction;
	}
	
	public int getClassOrdinal() {
		return getBeanInfo().getClassOrdinal();
	}

	public ProjectionManager getProviderProxy() {
		return providerProxy;
	}
	
	public void markForSorted() {
		markedForSorted = true;
	}
	
	public void resetMarkForSorted() {
		markedForSorted = false;
	}
	
	public boolean isMarkedForSorted() {
		return markedForSorted;
	}
	
	public void markForLoopDetection() {
		markedForLoopDetection = true;
	}
	
	public void resetMarkForLoopDetection() {
		markedForLoopDetection = false;
	}
	
	public boolean isMarkedForLoopDetection() {
		return markedForLoopDetection;
	}
	
	public boolean isIgnoreDaoError() {
		return ignoreDaoError;
	}

	public void load(String attributeName) {
		load(attributeName, null);
	}
	
	public void load(String attributeName, Object parameter) {
		synchronized (this) {
			if (isPooled(attributeName, parameter)) {
				if (context.manager.isAutoFlush()) {
					getEntityTransaction().flush();
				}
				try {
					providerProxy.load(this, attributeName, parameter);
				} catch (PersistenceException ex) {
					if (ignoreDaoError) {
						ex.printStackTrace();
					} else {
						throw ex;
					}
				}
				setReady();
			}
		}
	}
	
	public void loadFull() {
		synchronized (this) {
			if (isPooledForFull()) {
				if (context.manager.isAutoFlush()) {
					getEntityTransaction().flush();
				}
				try {
					providerProxy.loadFull(this);
				} catch (PersistenceException ex) {
					if (ignoreDaoError) {
						ex.printStackTrace();
					} else {
						throw ex;
					}
				}
				setReady();
			}
		}
	}

    public void flush() {
		synchronized (this) {
			try {
				if ((isNew() || isDirty())) {
					getDAO().save(this);
				} else if (isDeleted()) {
					getDAO().delete(this);
				}
			} catch (PersistenceException ex) {
				if (ignoreDaoError) {
					ex.printStackTrace();
				} else {
					throw ex;
				}
			}
		}
	}
    
    /**
     * the object is uncached
     */
    public void uncached() {
    	synchronized(this) {
    		if (!isNew() && !isDirty() && !isDeleted()) { // if not part of a transaction
    			getBeanInfo().clearObject(getPojoIdentifiable());
    			if (!isInvalid()) {
    				setPooled();
    			}
    		}	
    	}
    }

    /**
	 * refresh this object from the persistent storage
	 */
    public void refresh() {
    	synchronized(this) {
    		if (!isNew() && !isInvalid()) {
    			setPooled();
    		}
    	}
    }

    /**
     * set the cache time
     */
    public void setCacheTime(long cacheTime) {
    	this.cacheTime = cacheTime;
    }

    /**
     * @return the cache time or -1 if not set
     */
    public long getCacheTime() {
    	return cacheTime;
    }

    public boolean isReady() {
    	return cachedState == CachedState.READY;
    }
    
    public void setReady() {
    	synchronized (this) {
    		setCacheState(CachedState.READY);
    	}
    }
    
    public void resetReady() {
    	synchronized (this) {
    		setCacheState(null);
    		setCacheState(CachedState.READY);
    	}
    }

    public boolean isNew() {
    	return cachedState == CachedState.NEW;
    }
    
    public void setNew() {
    	synchronized (this) {
    		setCacheState(CachedState.NEW);
    	}
    }

    public boolean isDirty() {
    	return cachedState == CachedState.DIRTY 
    		|| cachedState == CachedState.DIRTYDEPENDENT;
    }
    
    public void setDirty() {
    	synchronized (this) {
    		setCacheState(CachedState.DIRTY);
    	}
    }
    
    public void setDirty(String propertyName) {
    	synchronized (this) {
    		if (getBeanInfo().isInverseOfMappedByProperty(propertyName)) {
    			setDirtyDependent();
    		} else {
    			setCacheState(CachedState.DIRTY);
    		}
    	}
    }
    
    public boolean isDirtyDependent() {
    	return cachedState == CachedState.DIRTYDEPENDENT;
    }
    
    public void setDirtyDependent() {
    	synchronized (this) {
    		if (cachedState != CachedState.DIRTY) {
    			setCacheState(CachedState.DIRTYDEPENDENT);
    		}
    	}
    }
    
    public boolean isDeleted() {
    	return cachedState == CachedState.DELETED;
    }
    
    public void setDeleted() {
    	synchronized (this) {
    		setCacheState(CachedState.DELETED);
    	}
    }
    
    public boolean isPooledForFull() {
    	if (cachedState == CachedState.READY) {
    		return providerProxy.isPooledForFull(this);
    	} else {
    		return false;
    	}
    }
    
    public boolean isPooled(String attributeName) {
    	return isPooled(attributeName, null);
    }

    public boolean isPooled(String attributeName, Object parameter) {
    	synchronized (this) {
	    	if (cachedState == CachedState.POOLED) {
	    		return true;
	    	} else if (cachedState == CachedState.READY 
	    			|| cachedState == CachedState.DIRTYDEPENDENT
	    			|| cachedState == CachedState.DIRTY
	    			|| cachedState == CachedState.DELETED) {
	    		return providerProxy.isPooled(attributeName, parameter);
	    	} else {
	    		return false;
	    	}
    	}
    }

    public void setPooled() {
    	synchronized (this) {
	    	setCacheState(CachedState.POOLED);
	    	getBeanInfo().clearObject(getPojoIdentifiable());
	    	if (changeRecords != null) {
	    		changeRecords.clear();
	    	}
	    	providerProxy.clear();
    	}
    }
    
    public void checkReadyAndSetPooled() {
        synchronized (this) {
            if (isReady()) {
                setPooled();
            }
        }
    }
    
    public boolean isInvalid() {
    	return cachedState == CachedState.INVALID;
    }
    
    public void setInvalid() {
    	synchronized (this) {
    		setCacheState(CachedState.INVALID);
    	}
    }

    public CachedState getCacheState() {
    	return cachedState;
    }
    
    void setCacheState(CachedState cachedState) {
    	if (this.cachedState != cachedState && this.cachedState != CachedState.INVALID) {
    		if (cachedState == CachedState.POOLED) {
    			if (this.cachedState == CachedState.NEW) {
    				getEntityTransaction().removeDirty(this);
    				this.cachedState = CachedState.INVALID;
    			} else if (this.cachedState == CachedState.DELETED
    				    || this.cachedState == CachedState.DIRTYDEPENDENT
    				    || this.cachedState == CachedState.DIRTY) {	
    				getEntityTransaction().removeDirty(this);
    				this.cachedState = cachedState;
    			} else {
    				this.cachedState = cachedState;
    			}
    		} else if (cachedState == CachedState.READY) {
    			if (this.cachedState == CachedState.POOLED) {	
    				this.cachedState = cachedState;
    			} else if (this.cachedState == CachedState.NEW
    				    || this.cachedState == CachedState.DELETED
    				    || this.cachedState == CachedState.DIRTYDEPENDENT
    				    || this.cachedState == CachedState.DIRTY) {	
    				// no change
    			} else {
    				this.cachedState = cachedState;
    			}
    		} else if (cachedState == CachedState.NEW) {
    			if (this.cachedState == CachedState.DELETED
    		     || this.cachedState == CachedState.DIRTYDEPENDENT
    			 || this.cachedState == CachedState.DIRTY
    			 || this.cachedState == CachedState.READY
				 || this.cachedState == CachedState.POOLED) {	
    				getEntityTransaction().removeDirty(this);
    				this.cachedState = CachedState.INVALID;
    			} else {
    				this.cachedState = cachedState;
	    			getEntityTransaction().addDirty(this);
    			}
    		} else if (cachedState == CachedState.DIRTY) {
    			if (this.cachedState == CachedState.NEW) {
    				getEntityTransaction().removeDirty(this);
    				this.cachedState = CachedState.INVALID;
    			} else if (this.cachedState == CachedState.READY
    					|| this.cachedState == CachedState.POOLED) {
    				this.cachedState = cachedState;
	    			getEntityTransaction().addDirty(this);
    			} else if (cachedState == CachedState.DIRTYDEPENDENT) {
    				this.cachedState = cachedState;
    			} else if (this.cachedState == CachedState.DELETED) {
	    			// no change
    			} else {
    				this.cachedState = cachedState;
	    			getEntityTransaction().addDirty(this);
    			}
    		} else if (cachedState == CachedState.DIRTYDEPENDENT) {
    			if (this.cachedState == CachedState.NEW) {
    				getEntityTransaction().removeDirty(this);
    				this.cachedState = CachedState.INVALID;
    			} else if (this.cachedState == CachedState.READY
    					|| this.cachedState == CachedState.POOLED) {
    				this.cachedState = cachedState;
	    			getEntityTransaction().addDirty(this);
    			} else if (cachedState == CachedState.DIRTY) {
    				// no change
    			} else if (this.cachedState == CachedState.DELETED) {
	    			// no change
    			} else {
    				this.cachedState = cachedState;
	    			getEntityTransaction().addDirty(this);
    			}
    		} else if (cachedState == CachedState.DELETED) {
    			if (this.cachedState == CachedState.NEW) {
    				this.cachedState = CachedState.INVALID;
    				getEntityTransaction().removeDirty(this);
    			} else if (this.cachedState == CachedState.DIRTY 
    					|| this.cachedState == CachedState.DIRTYDEPENDENT) {
    				this.cachedState = cachedState;
    				getEntityTransaction().addDirty(this);
    			} else if (this.cachedState == CachedState.READY
    					|| this.cachedState == CachedState.POOLED) {
    				this.cachedState = cachedState;
    				getEntityTransaction().addDirty(this);
    			} else {
    				this.cachedState = cachedState;
	    			getEntityTransaction().addDirty(this);
    			}
    		} else if (cachedState == CachedState.INVALID) {
    			getEntityTransaction().removeDirty(this);
    			this.cachedState = cachedState;
    		} else {
    			this.cachedState = cachedState;
    		}
    	}
    }
    
    public String getEntityAttributeName() {
    	return null;
    }
    
    public boolean isManagedObjectEditable() {
        ManagedObject mo = (ManagedObject) getPojoObject();
    	return mo.isEditable();
    }
    
    public boolean isManagedObjectEditable(String attributeName) {
    	if (isManagedObjectEditable()) {
    		return true;
    	} else {
    		if (getBeanInfo().isEditableByViewer(attributeName)) {
    			return true;
    		}
    		return false;
    	}
    }
    
}
