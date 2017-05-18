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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Embeddable
@XmlType(name="AccessControlList", namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
public class AccessControlList {
	
	static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier object
	 * @associates Entity
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	Entity object;
	
	/**
	 * @$clientQualifier accessControlEntry
	 * @associates AccessControlEntry
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="accessControlEntry")
	Set<AccessControlEntry> accessControlEntries;
	
	AccessControlList() {
		super();
	}
	
	public AccessControlList(Entity object) {
		super();
		this.object = object;
	}
	
	public Entity getObject() {
		return object;
	}

	/*
	public void setObject(Entity object) {
		this.object = object;
	}
	*/
	
	public Set<AccessControlEntry> getAccessControlEntries() {
		if (accessControlEntries != null) {
			return Collections.unmodifiableSet(accessControlEntries);
		} else {
			return null;
		}
	}

	public boolean addAccessControlEntry(AccessControlEntry accessControlEntry) throws IllegalArgumentException {
		boolean r = false;
		if (accessControlEntries == null) {
			accessControlEntries = new HashSet<AccessControlEntry>();
		}
		
		for (AccessControlEntry ace : accessControlEntries) {
			if (ace.getSubject() == accessControlEntry.getSubject()) {
				throw new IllegalArgumentException("Entity already has an ACE for the same subject");
			}
		}
		r = accessControlEntries.add(accessControlEntry);
		return r;
	}
	
	public boolean removeAccessControlEntry(AccessControlEntry accessControlEntry) {
		boolean r = false;
		if (accessControlEntries != null) {
			r = accessControlEntries.remove(accessControlEntry);
		}
		return r;
	}

	public AccessControlList clone() {
		AccessControlList clone;
        try {
            clone = (AccessControlList) this.getClass().newInstance();
        } catch (IllegalAccessException ex) {
            throw new PersistenceException("illegal access exception of identifiable class", ex);
        } catch (InstantiationException ex) {
            throw new PersistenceException("instantiation exception of identifiable class", ex);
        }
		clone.object = object;
		if (accessControlEntries != null) {
			clone.accessControlEntries = new HashSet<AccessControlEntry>(accessControlEntries.size());
			for (AccessControlEntry ace : accessControlEntries) {
				clone.accessControlEntries.add(ace.clone());
			}
		}
		return clone;
	}
	
}
