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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="RoleDefinition", namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
public class RoleDefinition extends Entity implements RelationshipBondable {
		
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String description;
	
	/**
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(name="privilege", type=PrivilegeEnum.class)
	Set<Privilege> privileges;
	
	/**
	 * @$clientQualifier relationship
	 * @associates Relationship
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Relationship.class)
	@XmlElement(name="relationship", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<Relationship> relationships;
	
	private static final long serialVersionUID = 1L;

	protected RoleDefinition() {
		super();
	}
	
	public RoleDefinition(Id id, Scope parent, Date createdOn) {
		super(id, (Extent) parent, createdOn);
		parent.addRoleDefinition(this);
	}
	
	public RoleDefinition(Scope parent, Date createdOn) {
		super((Extent) parent, createdOn);
		parent.addRoleDefinition(this);
	}
	
	public void delete() {
		if (parent != null) {
			((Scope)parent).removeRoleDefinition(this);
		}
		super.delete();
	}
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
		
	public Set<Privilege> getPrivileges() {
		if (privileges != null) {
			return Collections.unmodifiableSet(new HashSet<Privilege>(privileges));
		} else {
			return null;
		}
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = new HashSet<Privilege>(privileges);
	}
	
	public boolean addPrivilege(Privilege privilege) {
		if (privileges == null) {
			privileges = new HashSet<Privilege>();
		}
		boolean r = privileges.add(privilege);
		return r;
	}
	
	public boolean removePrivilege(Privilege privilege) {
		if (privileges == null) {
			return false;
		}
		boolean r = privileges.remove(privilege);
		return r;
	}

	public Set<Relationship> getRelationships() {
		if (relationships != null) {
			return Collections.unmodifiableSet(new HashSet<Relationship>(relationships));
		} else {
			return null;
		}
	}
	
	boolean addRelationship(Relationship relationship) {
		boolean r = false;
		if (relationships != null) {
			r = relationships.add(relationship);
		}
		return r;
	}
	
	boolean removeRelationship(Relationship relationship) {
		boolean r = false;
		if (relationship != null) {
			r = relationships.remove(relationship);
		}
		return r;
	}
	
}
