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

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Role", namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
public class Role extends Subject {
	
	/**
	 * @$clientQualifier roleDefinition
	 * @associates RoleDefinition
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	RoleDefinition roleDefinition;
	
	/**
	 * @$clientQualifier memberAccessor
	 * @associates Accessor
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Accessor.class, 
		    mappedBy="assignedRoles")
    @DeferLoadOnAddRemove
    @XmlElement(name="memberAccessor", type=Object.class)
	protected Set<Accessor> memberAccessors;
	
	/**
	 * @$clientQualifier assignedScope
	 * @associates Scope
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	Scope assignedScope;
	
	private static final long serialVersionUID = 1L;
	
	protected Role() {
		super();
	}
	
	public Role(Id id, Scope parent, RoleDefinition roleDefinition, Date createdOn) {
		super(id, parent, createdOn);
		this.roleDefinition = roleDefinition;
		parent.addRole(this);
	}
	
	public Role(Scope parent, RoleDefinition roleDefinition, Date createdOn) {
		super(parent, createdOn);
		this.roleDefinition = roleDefinition;
		memberAccessors = new HashSet<Accessor>();
		parent.addRole(this);
	}
	
	public void delete() {
		if (parent != null) {
			((Scope)parent).removeRole(this);
		}
		if (memberAccessors != null) {
			for (Accessor accessor : memberAccessors.toArray(new Accessor[memberAccessors.size()])) {
				if (accessor instanceof Group) {
					((Group)accessor).removeAssignedRole(this);
				} else if (accessor instanceof Actor) {
					((Actor)accessor).removeAssignedRole(this);
				}
			}
		}
		super.delete();
	}

	public RoleDefinition getRoleDefinition() {
		return roleDefinition;
	}
	
	public Set<Accessor> getMemberAccessors() {
		if (memberAccessors != null) {
			return Collections.unmodifiableSet(new HashSet<Accessor>(memberAccessors));
		} else {
			return null;
		}
	}
	
	protected boolean addMemberAccessor(Accessor accessor) {
		boolean r = true;
		if (memberAccessors != null) {
			r = memberAccessors.add(accessor);
		}
		return r;
	}
	
	protected boolean removeMemberAccessor(Accessor accessor) {
		boolean r = true;
		if (memberAccessors != null) {
			r = memberAccessors.remove(accessor);
		}
		return r;
	}
	
	public Scope getAssignedScope() {
		return assignedScope;
	}

	public void setAssignedScope(Scope assignedScope) {
		this.assignedScope = assignedScope;
	}
	
}
