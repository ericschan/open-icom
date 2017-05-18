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

import icom.annotation.DeferLoadOnAddRemove;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Scope", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
public abstract class Scope extends Entity implements Extent, RelationshipBondable {
	
	String description;
	
	/**
	 * @$clientQualifier relationship
	 * @associates Relationship
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Relationship.class)
	@XmlElement(name="relationship", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<Relationship> relationships;
	
	/**
	 * @$clientQualifier group
	 * @associates Group
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Group.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
    @DeferLoadOnAddRemove
    @XmlElement(name="group")
	Set<Group> groups;
	
	/**
	 * @$clientQualifier memberGroup
	 * @associates Group
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Group.class, 
			    mappedBy="assignedScopes")
	@DeferLoadOnAddRemove
	@XmlElement(name="memberGroup")
	Set<Group> memberGroups;
	
	/**
	 * @$clientQualifier roleDefinition
	 * @associates RoleDefinition
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=RoleDefinition.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="roleDefinition", namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
	Set<RoleDefinition> roleDefinitions;
	
	/**
	 * @$clientQualifier role
	 * @associates Role
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Role.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="role", namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
	Set<Role> roles;
	
	private static final long serialVersionUID = 1L;

	Scope() {
		super();
	}

	public Scope(Id id, Community parent, Date createdOn) {
		super(id, (Parental)parent, createdOn);
	}
	
	public Scope(Community parent, Date createdOn) {
		super((Parental)parent, createdOn);
		relationships = new HashSet<Relationship>();
		groups = new HashSet<Group>();
		memberGroups = new HashSet<Group>();
		roleDefinitions = new HashSet<RoleDefinition>();
		roles = new HashSet<Role>();
	}
	
	public Community getParent() {
		return (Community) super.getParent();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Set<Group> getGroups() {
		if (groups != null) {
			return Collections.unmodifiableSet(new HashSet<Group>(groups));
		} else {
			return null;
		}
	}
	
	boolean addGroup(Group group) {
		boolean r = true;
		if (groups != null) {
			r = groups.add(group);
		}
		return r;
	}
	
	boolean removeGroup(Group group) {
		boolean r = true;
		if (groups != null) {
			r = groups.remove(group);
		}
		return r;
	}
	
	public Set<Group> getMemberGroups() {
		if (memberGroups != null) {
			return Collections.unmodifiableSet(new HashSet<Group>(memberGroups));
		} else {
			return null;
		}
	}
	
	boolean addMemberGroup(Group group) {
		boolean r = true;
		if (memberGroups != null) {
			r = memberGroups.add(group);
		}
		return r;
	}
	
	boolean removeMemberGroup(Group group) {
		boolean r = true;
		if (memberGroups != null) {
			r = memberGroups.remove(group);
		}
		return r;
	}
	
	public Set<RoleDefinition> getRoleDefinitions() {
		if (roleDefinitions != null) {
			return Collections.unmodifiableSet(new HashSet<RoleDefinition>(roleDefinitions));
		} else {
			return null;
		}
	}
	
	boolean addRoleDefinition(RoleDefinition roleDefinition) {
		boolean r = true;
		if (roleDefinitions != null) {
			r = roleDefinitions.add(roleDefinition);
		}
		return r;
	}
	
	boolean removeRoleDefinition(RoleDefinition roleDefinition) {
		boolean r = true;
		if (roleDefinitions != null) {
			r = roleDefinitions.remove(roleDefinition);
		}
		return r;
	}
	
	public Set<Role> getRoles() {
		if (roles != null) {
			return Collections.unmodifiableSet(new HashSet<Role>(roles));
		} else {
			return null;
		}
	}
	
	boolean addRole(Role role) {
		boolean r = true;
		if (roles != null) {
			r = roles.add(role);
		}
		return r;
	}
	
	boolean removeRole(Role role) {
		boolean r = true;
		if (roles != null) {
			r = roles.remove(role);
		}
		return r;
	}

}
