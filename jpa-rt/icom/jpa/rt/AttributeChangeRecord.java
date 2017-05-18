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

import java.util.Collection;
import java.util.Hashtable;


public class AttributeChangeRecord {
	
	String attributeName;
	Hashtable<Object, ValueHolder> addedObjects;
	Hashtable<Object, ValueHolder> removedObjects;
	Hashtable<Object, ValueHolder> modifiedObjects;
	
	public AttributeChangeRecord() {
		
	}
	
	public AttributeChangeRecord(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public void putObject(Object key, Object value) {
		boolean undoRemove = false;
		if (removedObjects != null) {
			if (removedObjects.containsKey(key)) {
				ValueHolder holder = removedObjects.remove(key);
				if (holder != null) {
					undoRemove = true;
				}
			}
		}
		if (! undoRemove) {
			if (addedObjects == null) {
				addedObjects = new Hashtable<Object, ValueHolder>();
			}
			addedObjects.put(key, new ValueHolder(key, value));
		}
	}
	
	public void removeObject(Object key, Object value) {
		boolean undoAdd = false;
		if (addedObjects != null) {
			if (addedObjects.contains(key)) {
				ValueHolder holder = addedObjects.remove(key);
				if (holder != null) {
					undoAdd = true;
				}
			}
		}
		if (! undoAdd) {
			if (removedObjects == null) {
				removedObjects = new Hashtable<Object, ValueHolder>();
			}
			removedObjects.put(key, new ValueHolder(key, value));
		}
	}
	
	public void modifyObject(Object key, Object value) {
		if (addedObjects != null) {
			ValueHolder holder = addedObjects.get(key);
			if (holder != null) {
				holder.value = value;
				return;
			}
		}
		if (modifiedObjects != null) {
			ValueHolder holder = modifiedObjects.get(key);
			if (holder != null) {
				holder.value = value;
			}
		} else {
			modifiedObjects = new Hashtable<Object, ValueHolder>();
			modifiedObjects.put(key, new ValueHolder(key, value));
		}
	}
	
	public Collection<ValueHolder> getAddedObjects() {
		if (addedObjects != null) {
			return addedObjects.values();
		} else {
			return null;
		}
	}
	
	public Collection<ValueHolder> getRemovedObjects() {
		if (removedObjects != null) {
			return removedObjects.values();
		} else {
			return null;
		}
	}
	
	public Collection<ValueHolder> getModifiedObjects() {
		if (modifiedObjects != null) {
			return modifiedObjects.values();
		} else {
			return null;
		}
	}
	
}
