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

import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Embeddable
@XmlType(name="AccessControlEntry", namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
public class AccessControlEntry {
	
	static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier subject
	 * @associates Accessor
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	@XmlElement(type=Subject.class, namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Accessor subject;
	
	/**
	 * @$clientQualifier grant
	 * @associates AccessType
	 * @link aggregationByValue
	 */
	@XmlElement(name="grant", type=AccessTypeEnum.class)
	Set<AccessType> grants;
	
	/**
	 * @$clientQualifier deny
	 * @associates AccessType
	 * @link aggregationByValue
	 */
	@XmlElement(name="deny", type=AccessTypeEnum.class)
	Set<AccessType> denies;
	
	public AccessControlEntry() {
		super();
	}
	
	public Accessor getSubject() {
		return subject;
	}

	public void setSubject(Accessor subject) {
		this.subject = subject;
	}
	
	public Set<AccessType> getGrants() {
		if (grants != null) {
			return Collections.unmodifiableSet(grants);
		} else {
			return null;
		}
	}
	
	public boolean addGrant(AccessType accessType) {
		if (grants == null) {
			grants = new HashSet<AccessType>();
		}
		boolean r = grants.add(accessType);
		return r;
	}
	
	public boolean removeGrant(AccessType accessType) {
		boolean r = false;
		if (grants != null) {
			r = grants.remove(accessType);
		}
		return r;
	}
	
	
	public Set<AccessType> getDenies() {
		if (denies != null) {
			return Collections.unmodifiableSet(denies);
		} else {
			return null;
		}
	}
	
	public boolean addDeny(AccessType accessType) {
		if (denies == null) {
			denies = new HashSet<AccessType>();
		}
		boolean r = denies.add(accessType);
		return r;
	}
	
	public boolean removeDeny(AccessType accessType) {
		boolean r = false;
		if (denies != null) {
			r = denies.remove(accessType);
		}
		return r;
	}
	
	public AccessControlEntry clone() {
	    AccessControlEntry clone;
	    try {
            clone = (AccessControlEntry) this.getClass().newInstance();
        } catch (IllegalAccessException ex) {
            throw new PersistenceException("illegal access exception of identifiable class", ex);
        } catch (InstantiationException ex) {
            throw new PersistenceException("instantiation exception of identifiable class", ex);
        }
		clone.subject = subject;
		if (grants != null) {
			clone.grants = new HashSet<AccessType>(grants.size());
			for (AccessType accessType : grants) {
				clone.grants.add(accessType);
			}
		}
		if (denies != null) {
			clone.denies = new HashSet<AccessType>(denies.size());
			for (AccessType accessType : denies) {
				clone.denies.add(accessType);
			}
		}
		return clone;
	}
	
}
