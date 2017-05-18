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
import icom.jpa.ManagedObjectProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Vector;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

class DirtyObjectsComparator implements Comparator<ManagedIdentifiableProxy>  {

   	public DirtyObjectsComparator() {
    }

    public int compare(ManagedIdentifiableProxy o1, ManagedIdentifiableProxy o2) {
        if (o1 == o2) return 0;
        int i1 = o1.getClassOrdinal();
        int i2 = o2.getClassOrdinal();
        if (i1 != i2) {
           int diff = i1 - i2;
           return (diff > 0) ? 1 : -1;
        }
        Object oid1 = o1.getObjectId();
        Object oid2 = o2.getObjectId();
        if (oid1.equals(oid2)) return 0;
        String str1 = oid1.toString();
        String str2 = oid2.toString();
        return str1.compareTo(str2);
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

}

class NewObjectsComparator implements Comparator<ManagedIdentifiableProxy>  {

   	public NewObjectsComparator() {
    }

    public int compare(ManagedIdentifiableProxy o1, ManagedIdentifiableProxy o2) {
        if (o1 == o2) return 0;
        int i1 = o1.getClassOrdinal();
        int i2 = o2.getClassOrdinal();
        if (i1 != i2) {
           int diff = i1 - i2;
           return (diff > 0) ? 1 : -1;
        }
        Object oid1 = o1.getObjectId();
        Object oid2 = o2.getObjectId();
        if (oid1.equals(oid2)) return 0;
        String str1 = oid1.toString();
        String str2 = oid2.toString();
        return str1.compareTo(str2);
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

}

class DeleteObjectsComparator implements Comparator<ManagedIdentifiableProxy>  {

   	public DeleteObjectsComparator() {
    }

    public int compare(ManagedIdentifiableProxy o1, ManagedIdentifiableProxy o2) {
        if (o1 == o2) return 0;
        int i1 = o1.getClassOrdinal();
        int i2 = o2.getClassOrdinal();
        if (i1 != i2) {
           int diff = i2 - i1;  // sort class ordinal in reverse
           return (diff > 0) ? 1 : -1;
        }
        Object oid1 = o1.getObjectId();
        Object oid2 = o2.getObjectId();
        if (oid1.equals(oid2)) return 0;
        String str1 = oid1.toString();
        String str2 = oid2.toString();
        return str1.compareTo(str2);
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

}

public class EntityTransactionImpl implements EntityTransaction, JtaResourceManager {
	
	enum Status {
		Active,
		MarkedRollback,
		Prepared,
		Committed,
		Rolledback,
		Unknown,
		NoTransaction,
		Preparing,
		Committing,
		RollingBack
	}
	
	Status status = Status.NoTransaction;
	
    /** Dirty Object table. */
    private TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> dirtyTab;
    
    /** New Object table. */
    private TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> newTab;
    
    /** Deleted Object table. */
    private TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> deleteTab;
    
    private Vector<ManagedIdentifiableProxy> flushedObjects;
    
    private EntityManagerImpl manager;
    
    JtaSynchronizationListener jtaSynchronizationListener;

    private static Comparator<ManagedIdentifiableProxy> dirtyObjCmp = new DirtyObjectsComparator();
    private static Comparator<ManagedIdentifiableProxy> newObjCmp = new NewObjectsComparator();
    private static Comparator<ManagedIdentifiableProxy> deleteObjCmp = new DeleteObjectsComparator();
    
    protected EntityTransactionImpl(EntityManagerImpl manager) {
        dirtyTab = new TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy>(dirtyObjCmp);
        newTab  = new TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy>(newObjCmp);
        deleteTab = new TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy>(deleteObjCmp);
        flushedObjects = new Vector<ManagedIdentifiableProxy>();
        this.manager = manager;
    }
    
    boolean hasJtaSynchronizationListener() {
    	if (jtaSynchronizationListener != null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void clearJtaSynchronizationListener() {
    	jtaSynchronizationListener = null;
    }
    
    JtaSynchronizationListener getJtaSynchronizationListener() {
    	if (jtaSynchronizationListener == null) {
    		synchronized(this) {
    			if (jtaSynchronizationListener == null) {
    				jtaSynchronizationListener = new JtaSynchronizationListener(this);
    			}
    		}
    	}
    	return jtaSynchronizationListener;
    }

	/**
     * Start the resource transaction.
     * @throws IllegalStateException if {@link #isActive()} is true.
     */
    public void begin() {
    	if (status == Status.Active) {
    		throw new IllegalStateException("transaction is active");
    	}
    	status = Status.Active;
    }
 
    /**
     * Commit the current transaction, writing any unflushed
     * changes to the database.
     * @throws IllegalStateException if {@link #isActive()} is false.
     * @throws RollbackException if the commit fails.
     */
    public void commit() {
        if (status == Status.MarkedRollback) {
            rollback();
            return;
        }
        
        if (status != Status.Active) {
    		//return;
    		//throw new IllegalStateException("transaction is not active");
    	}
        
        int nsz = newTab.size();
        int disz = dirtyTab.size();
        int desz = deleteTab.size();
        try {
        	status = Status.Committing;
        	if (nsz + disz + desz > 0 ) {
            	flush();
            	notifyCommitted();
            }
        } catch (Throwable ex) {
            rollback();
            throw new RuntimeException(ex);
        } finally {
        	jtaSynchronizationListener = null;
            clear();
        }
        status = Status.Committed;
    }
 
    /**
     * Roll back the current transaction
     * @throws IllegalStateException if {@link #isActive()} is false.
     * @throws PersistenceException if an unexpected error
     * condition is encountered.
     */
    public void rollback()  {
        try {
            status = Status.RollingBack;
            notifyRolledback();
        } catch (Throwable ex) {
        	
        } finally {
        	jtaSynchronizationListener = null;
        	// clear the transaction
        	clear();
        	// clear the entity manager context and detach all managed entities
        	manager.persistenceContext.clear();
        }
        status = Status.Rolledback;
    }

    /**
     * Mark the current transaction so that the only possible
     * outcome of the transaction is for the transaction to be
     * rolled back.
     * @throws IllegalStateException if {@link #isActive()} is false.
     */
    public void setRollbackOnly() {
        status = Status.MarkedRollback;
    }

    /**
     * Determine whether the current transaction has been marked
     * for rollback.
     * @throws IllegalStateException if {@link #isActive()} is false.
     */
    public boolean getRollbackOnly() {
    	return status == Status.MarkedRollback;
    }
 
    /**
     * Indicate whether a transaction is in progress.
     * @throws PersistenceException if an unexpected error
     * condition is encountered.
     */
    public boolean isActive() {
    	return status == Status.Active;
    }

    public void removeDirty(ManagedIdentifiableProxy o) {
        try {
        	if (o.isDirty() || o.isDirtyDependent()) {
        		dirtyTab.remove(o);
        	} else if (o.isNew()) {
        		newTab.remove(o);
        	} else if (o.isDeleted()) {
        		deleteTab.remove(o);
        	}
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addDirty(ManagedIdentifiableProxy o) {
        try {
        	if (o.isDirty() || o.isDirtyDependent()) {
        		dirtyTab.put(o, o);
        	} else if (o.isNew()) {
        		newTab.put(o, o);
        	} else if (o.isDeleted()) {
        		newTab.remove(o);
        		dirtyTab.remove(o);
        		deleteTab.put(o, o);
        	}
            status = Status.Active;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void undoAll(TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> tab)  {
    	Collection<ManagedIdentifiableProxy> objs = tab.values();
    	Object[] objects = objs.toArray();
    	for (int i = 0; i < objects.length; i++) {
    		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) objects[i];
    		obj.undo();
			removeDirty(obj);
		}
    }

    /**
     * Reset all dirty objects. Set them to pooled state so they reload themselves
     * from database the next time they are accessed.
     */
    protected void undoAll()  {
    	undoAll(dirtyTab);
    	undoAll(newTab);
    	undoAll(deleteTab);
    }
    
    private void flush(ArrayList<ManagedObjectProxy> list) {
    	for (int i = 0; i < list.size(); i++) {
    		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) list.get(i);
    		obj.flush();
    		if (manager.isAutoFlush()) {
    			flushedObjects.add(obj);
    		}
		}
    }
    
    private void flushNew(TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> tab) {
    	Collection<ManagedIdentifiableProxy> objs = tab.values();
    	Object[] objects = objs.toArray();
    	ArrayList<ManagedObjectProxy> list = new ArrayList<ManagedObjectProxy>(objects.length);
    	for (int i = 0; i < objects.length; i++) {
    		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) objects[i];
    		obj.sortNewPredecessors(list);
    	}
    	flush(list);
    }
    
    private void flushDirty(TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> tab) {
    	Collection<ManagedIdentifiableProxy> objs = tab.values();
    	Object[] objects = objs.toArray();
    	ArrayList<ManagedObjectProxy> list = new ArrayList<ManagedObjectProxy>(objects.length);
    	for (int i = 0; i < objects.length; i++) {
    		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) objects[i];
    		obj.sortDirtyPredecessors(list);
    	}
    	flush(list);
    }
    
    private void flushDelete(TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> tab) {
    	Collection<ManagedIdentifiableProxy> objs = tab.values();
    	Object[] objects = objs.toArray();
    	ArrayList<ManagedObjectProxy> list = new ArrayList<ManagedObjectProxy>(objects.length);
    	for (int i = 0; i < objects.length; i++) {
    		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) objects[i];
    		obj.sortDeletePredecessors(list);
    	}
    	flush(list);
    }

    /**
	 * Save all dirty objects.
	 */
    public void flush() {
    	flushNew(newTab);
    	flushDirty(dirtyTab);
    	flushDelete(deleteTab);
    }
    
    public void flush(ManagedIdentifiableProxy obj) {
        obj.flush();
        obj.committed();
    }
    
    private void notifyCommitted(TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> tab) {
    	Collection<ManagedIdentifiableProxy> objs = tab.values();
    	Object[] objects = objs.toArray();
    	for (int i = 0; i < objects.length; i++) {
    		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) objects[i];
    		obj.committed();
		}
    }
    
    /**
	 * reset all dirty and new objects to ready state.
	 */
    protected void notifyCommitted() {
    	notifyCommitted(dirtyTab);
    	notifyCommitted(newTab);
    	notifyCommitted(deleteTab);
    }
    
    private void notifyRolledback(TreeMap<ManagedIdentifiableProxy, ManagedIdentifiableProxy> tab) {
    	Collection<ManagedIdentifiableProxy> objs = tab.values();
    	Object[] objects = objs.toArray();
    	for (int i = 0; i < objects.length; i++) {
    		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) objects[i];
    		obj.rolledback();
		}
    }
    
    /**
	 * reset all dirty and new objects to ready state.
	 */
    protected void notifyRolledback() {
    	notifyRolledback(dirtyTab);
    	notifyRolledback(newTab);
    	notifyRolledback(deleteTab);
    }
    
    public boolean hasDirtyObjects() { 
    	int sz = dirtyTab.size() + newTab.size() + deleteTab.size();
        return sz > 0;
    }

    // Purge all entries in the dirty tab.
    public void clear() {
        dirtyTab.clear();
        newTab.clear();
        deleteTab.clear();
        flushedObjects.clear();
    }
  
}
