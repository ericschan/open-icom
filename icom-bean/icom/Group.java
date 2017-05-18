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

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Group", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
public class Group extends Subject implements Owner, Addressable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier assignedScope
	 * @associates Scope
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Scope.class)
	@DeferLoadOnAddRemove
	@XmlElement(name="assignedScope")
	Set<Scope> assignedScopes;
	
	/**
	 * @$clientQualifier assignedGroup
	 * @associates Group
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Group.class)
	@DeferLoadOnAddRemove
	@XmlElement(name="assignedGroup")
	Set<Group> assignedGroups;
	
	/**
	 * @$clientQualifier assignedRole
	 * @associates Role
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Role.class)
	@DeferLoadOnAddRemove
	@XmlElement(name="assignedRole", namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
	Set<Role> assignedRoles;
	
	/**
	 * @$clientQualifier memberActor
	 * @associates Actor
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Actor.class, 
		    mappedBy="assignedGroups")
    @DeferLoadOnAddRemove
    @XmlElement(name="memberActor")
	Set<Actor> memberActors;
	
	/**
	 * @$clientQualifier memberGroup
	 * @associates Group
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Group.class, 
		    mappedBy="assignedGroups")
	@DeferLoadOnAddRemove
	@XmlElement(name="memberGroup")
	Set<Group> memberGroups;

	/**
     * @$clientQualifier entityAddress
     * @associates EntityAddress
     * @link aggregationByValue
     */
    @Embedded
    @XmlElement(name="entityAddress", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    Collection<EntityAddress> entityAddresses;
	
	/**
	 * @$clientQualifier primaryAddress
	 * @associates EntityAddress
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	EntityAddress primaryAddress;
	
	protected Group() {
		super();
	}
	
	public Group(Id id, Scope parent, Date createdOn) {
		super(id, parent, createdOn);
		parent.addGroup(this);
	}
	
	public Group(Scope parent, Date createdOn) {
		super(parent, createdOn);
		assignedScopes = new HashSet<Scope>();
		assignedGroups = new HashSet<Group>();
		assignedRoles = new HashSet<Role>();
		memberActors = new HashSet<Actor>();
		memberGroups = new HashSet<Group>();
		entityAddresses = new Vector<EntityAddress>();
		parent.addGroup(this);
	}
	
	public void delete() {
		if (parent != null) {
			((Scope)parent).removeGroup(this);
		}
		if (assignedScopes != null) {
			for (Scope scope : assignedScopes.toArray(new Scope[assignedScopes.size()])) {
				scope.removeMemberGroup(this);
			}
		}
		if (assignedGroups != null) {
			for (Group group : assignedGroups.toArray(new Group[assignedGroups.size()])) {
				group.removeMemberGroup(this);
			}
		}
		if (assignedRoles != null) {
			for (Role role : assignedRoles.toArray(new Role[assignedRoles.size()])) {
				role.removeMemberAccessor(this);
			}
		}
		if (memberActors != null) {
			for (Actor actor : memberActors.toArray(new Actor[memberActors.size()])) {
				actor.removeAssignedGroup(this);
			}
		}
		if (memberGroups != null) {
			for (Group group : memberGroups.toArray(new Group[memberGroups.size()])) {
				group.removeAssignedGroup(this);
			}
		}
		super.delete();
	}

	public Collection<Scope> getAssignedScopes() {
		if (assignedScopes != null) {
			return Collections.unmodifiableCollection(new HashSet<Scope>(assignedScopes));
		} else {
			return null;
		}
	}
	
	public boolean addAssignedScope(Scope scope) {
		boolean r = true;
		if (assignedScopes != null) {
			r = assignedScopes.add(scope);
		}
		if (r) {
			scope.addMemberGroup(this);
		}
		return r;
	}
	
	public boolean removeAssignedScope(Scope scope) {
		boolean r = true;
		if (assignedScopes != null) {
			r = assignedScopes.remove(scope);
		}
		if (r) {
			scope.removeMemberGroup(this);
		}
		return r;
	}	

	public Collection<Group> getAssignedGroups() {
		if (assignedGroups != null) {
			return Collections.unmodifiableCollection(new HashSet<Group>(assignedGroups));
		} else {
			return null;
		}
	}
	
	public boolean addAssignedGroup(Group group) {
		boolean r = true;
		if (assignedGroups != null) {
			r = assignedGroups.add(group);
		}
		if (r) {
			group.addMemberGroup(this);
		}
		return r;
	}
	
	public boolean removeAssignedGroup(Group group) {
		boolean r = true;
		if (assignedGroups != null) {
			r = assignedGroups.remove(group);
		}
		if (r) {
			group.removeMemberGroup(this);
		}
		return r;
	}
	
	public Collection<Role> getAssignedRoles() {
		if (assignedRoles != null) {
			return Collections.unmodifiableCollection(new HashSet<Role>(assignedRoles));
		} else {
			return null;
		}
	}
	
	public boolean addAssignedRole(Role role) {
		boolean r = true;
		if (assignedRoles != null) {
			r = assignedRoles.add(role);
		}
		if (r) {
			role.addMemberAccessor(this);
		}
		return r;
	}
	
	public boolean removeAssignedRole(Role role) {
		boolean r = true;
		if (assignedRoles != null) {
			r = assignedRoles.remove(role);
		}
		if (r) {
			role.removeMemberAccessor(this);
		}
		return r;
	}
	
	public Set<Actor> getMemberActors() {
		if (memberActors != null) {
			return Collections.unmodifiableSet(new HashSet<Actor>(memberActors));
		} else {
			return null;
		}
	}
	
	boolean addMemberActor(Actor actor) {
		boolean r = true;
		if (memberActors != null) {
			r = memberActors.add(actor);	
		}
		return r;
	}

	boolean removeMemberActor(Actor actor) {
		boolean r = true;
		if (memberActors != null) {
			r = memberActors.remove(actor);
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

	public Collection<EntityAddress> getEntityAddresses() {
        if (entityAddresses != null) {
            return Collections.unmodifiableCollection(new HashSet<EntityAddress>(entityAddresses));
        } else {
            return null;
        }
    }
    
    public boolean addEntityAddress(EntityAddress address) {
        boolean r = false;
        if (entityAddresses != null) {
            r = entityAddresses.add(address);
        }
        return r;
    }
    
    public boolean removeEntityAddress(EntityAddress address) {
        boolean r = false;
        if (entityAddresses != null) {
            r = entityAddresses.remove(address);
        }
        return r;
    }
	
	public EntityAddress getPrimaryAddress() {
		return primaryAddress;
	}
	
	public void setPrimaryAddress(EntityAddress address) throws IllegalArgumentException {
		this.primaryAddress = address;
	}

}
