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
package icom.jpa;

import icom.jpa.rt.ValueHolder;

import java.util.Collection;

public interface ManagedIdentifiableProxy extends ManagedObjectProxy {
	
	public Identifiable getPojoIdentifiable();
	
	public Object getObjectId();
	
	public void setObjectId(Object id);
	
	public Object getChangeToken();
	
	/**
	 * get the ordinal of the class of the object. Ordinal is used to 
	 * sort the objects during the flush of the transaction to 
	 * prevent deadlock
	 */
	public int getClassOrdinal();
	
	public boolean isIgnoreDaoError();
	
	public Collection<ValueHolder> getAddedObjects(String attributeName);
	
	public Collection<ValueHolder> getRemovedObjects(String attributeName);
	
	public Collection<ValueHolder> getModifiedObjects(String attributeName);
	
	public boolean hasAttributeChanges();
	
	public boolean containsAttributeChangeRecord(String attributeName);
	
	/**
     * save the changes to the object
     */
    public void flush();

    /**
     * refresh this object from the persistent storage
     */
    public void refresh();

    /**
     * undo the changes to the object
     */
    public void undo();

    /**
     * the object is uncached
     */
    public void uncached();
    
    /**
     * the object is committed
     */
    public void committed();
    
    /**
     * the object is rolled back
     */
    public void rolledback();

    /**
     * set the cache time
     */
    public void setCacheTime(long cacheTime);

    /**
     * @return the cache time or -1 if not set
     */
    public long getCacheTime();


    /**
     * Returns the current state.
     *
     * @return the actual state of this object.
     * @see    CachedState
     */
    public CachedState getCacheState();
    
    public void checkReadyAndSetPooled();
	
}
