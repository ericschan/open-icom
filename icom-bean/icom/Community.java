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
@XmlType(name="Community", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
public class Community extends Scope {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier actor
	 * @associates Actor
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Actor.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
    @DeferLoadOnAddRemove
    @XmlElement(name="actor")
	Set<Actor> actors;
	
	/**
	 * @$clientQualifier memberActor
	 * @associates Actor
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Actor.class, 
		        mappedBy="assignedCommunities")
	@DeferLoadOnAddRemove
	@XmlElement(name="memberActor")
	Set<Actor> memberActors;
	
	/**
	 * @$clientQualifier community
	 * @associates Community
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Community.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="community")
	Set<Community> communities;
	
	/**
	 * @$clientQualifier space
	 * @associates Space
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Space.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="space")
	Set<Space> spaces;

	protected Community() {
		super();
	}

	public Community(Id id, Community parent, Date createdOn) {
		super(id, parent, createdOn);
		parent.addCommunity(this);
	}
	
	public Community(Community parent, Date createdOn) {
		super(parent, createdOn);
		actors = new HashSet<Actor>();
		memberActors = new HashSet<Actor>();
		communities = new HashSet<Community>();
		spaces = new HashSet<Space>();
		parent.addCommunity(this);
	}

	public void delete() {
		if (parent != null) {
			((Community)parent).removeCommunity(this);
		}
		super.delete();
	}
	
	public Set<Actor> getActors() {
		if (actors != null) {
			return Collections.unmodifiableSet(new HashSet<Actor>(actors));
		} else {
			return null;
		}
	}
	
	boolean addActor(Actor actor) {
		boolean r = true;
		if (actors != null) {
			r = actors.add(actor);
		}
		return r;
	}
	
	boolean removeActor(Actor actor) {
		boolean r = true;
		if (actors != null) {
			r = actors.remove(actor);
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
	
	public Set<Community> getCommunities() {
		if (communities != null) {
			return Collections.unmodifiableSet(new HashSet<Community>(communities));
		} else {
			return null;
		}
	}
	
	boolean addCommunity(Community community) {
		boolean r = true;
		if (communities != null) {
			r = communities.add(community);
		}
		return r;
	}
	
	boolean removeCommunity(Community community) {
		boolean r = true;
		if (communities != null) {
			r = communities.remove(community);
		}
		return r;
	}
	
	public Set<Space> getSpaces() {
		if (spaces != null) {
			return Collections.unmodifiableSet(new HashSet<Space>(spaces));
		} else {
			return null;
		}
	}
	
	boolean addSpace(Space space) {
		boolean r = true;
		if (spaces != null) {
			r = spaces.add(space);
		}
		return r;
	}
	
	boolean removeSpace(Space space) {
		boolean r = true;
		if (spaces != null) {
			r = spaces.remove(space);
		}
		return r;
	}
	
}
