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

import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@javax.persistence.Entity
@XmlType(name="Entity", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Entity implements Identifiable {

	static final long serialVersionUID = 1L;
	
	@javax.persistence.EmbeddedId
        @XmlElement(name="objectId")
	protected Id id;

        @XmlTransient 
        private String objectId;  // a work around for ADF

	@javax.persistence.Version
	protected ChangeToken changeToken;

	String name;

	Date creationDate;

	/**
	 * @$clientQualifier createdBy
	 * @associates Actor
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	Actor createdBy;

	Date lastModificationDate;

	/**
	 * @$clientQualifier lastModifiedBy
	 * @associates Actor
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	Actor lastModifiedBy;

	/**
	 * @$clientQualifier parent
	 * @associates Parental
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	@XmlElement(name="parent", type=Object.class)
	protected Parental parent;

	/**
	 * @$clientQualifier owner
	 * @associates Owner
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	@XmlElement(name="owner", type=Object.class, namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
	Owner owner;

	/**
	 * @$clientQualifier attachedMarker
	 * @associates Marker
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Marker.class)
	@XmlElement(name="attachedMarker", 
            namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<Marker> attachedMarkers;

	/**
	 * @$clientQualifier categoryApplication
	 * @associates CategoryApplication
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="categoryApplication", 
            namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<CategoryApplication> categoryApplications;

	/**
	 * @$clientQualifier tagApplication
	 * @associates TagApplication
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="tagApplication", 
            namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<TagApplication> tagApplications;

	/**
	 * @$clientQualifier accessControlList
	 * @associates AccessControlList
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/accesscontrol/201008")
	AccessControlList accessControlList;

	protected Entity() {
		super();
	}
	
	protected Entity(Object parent) {
	 // here byte code weaver should inject super(parent) that implements cascade persist via parent
	}

	protected Entity(Id id, RelationshipBondable pseudoParent, Date createdOn) {
	    this(pseudoParent);
		this.id = id;
		this.creationDate = createdOn;
	}

	public Entity(Id id, Parental parent, Date createdOn) {
	    this(parent);
		this.id = id;
		this.parent = parent;
		this.creationDate = createdOn;
	}

	protected Entity(RelationshipBondable pseudoParent, Date createdOn) {
	    this(pseudoParent);
		this.creationDate = createdOn;
		attachedMarkers = new HashSet<Marker>();
		categoryApplications = new HashSet<CategoryApplication>();
	}

	public Entity(Parental parent, Date createdOn) {
	    this(parent);
		this.parent = parent;
		this.creationDate = createdOn;
		attachedMarkers = new HashSet<Marker>();
		categoryApplications = new HashSet<CategoryApplication>();
	}

	/**
	 * The primary key property or field of an entity.
	 */
	public Id getId() {
		return id;
	}
	
	protected void setId(Id objectId) {
		this.id = objectId;
	}

	/*
	 * The change token is the Version field or property used by the ICOM
	 * persistence provider to perform optimistic locking. It is accessed
	 * and/or set by the persistence provider in the course of performing
	 * lifecycle operations on the entity instance. Optimistic locking is a
	 * technique that is used to insure that updates to the database data
	 * corresponding to the state of an entity are made only when no
	 * intervening transaction has updated that data for the entity state
	 * since the entity state was read. This insures that updates or deletes
	 * to that data are consistent with the current state of the database
	 * and that intervening updates are not lost. Transactions that would
	 * cause this constraint to be violated result in an
	 * OptimisticLockException being thrown and transaction rollback.
	 */
	public ChangeToken getChangeToken() {
		return changeToken;
	}
	
	protected void setChangeToken(ChangeToken changeToken) {
		this.changeToken = changeToken;
	}
        
        
	
    public boolean isEditable() {
        return true;
    }
    
    public void delete() {
        //super.delete();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Actor getCreatedBy() {
		return createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Actor getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Actor lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date modifiedOn) {
		this.lastModificationDate = modifiedOn;
	}

	public Parental getParent() {
		return parent;
	}

	public void setParent(Parental parent) {
     // here byte code weaver should inject super.setParent(parent) that cascades persist via parent
		this.parent = parent;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public AccessControlList getAccessControlList() {
		return accessControlList;
	}

	public void setAccessControlList(AccessControlList accessControlList) throws IllegalArgumentException {
		if (this.accessControlList != null) {
			throw new IllegalArgumentException("Access Control List is already set");
		}
		this.accessControlList = accessControlList;
	}

	public Set<Marker> getAttachedMarkers() {
		if (attachedMarkers != null) {
			return Collections.unmodifiableSet(new HashSet<Marker>(attachedMarkers));
		} else {
			return null;
		}
	}

	public Set<CategoryApplication> getCategoryApplications() {
		if (categoryApplications != null) {
			return Collections.unmodifiableSet(new HashSet<CategoryApplication>(categoryApplications));
		} else {
			return null;
		}
	}

	boolean attachCategoryApplication(CategoryApplication categoryApplication) throws IllegalAttributionException {
		if (attachedMarkers == null) {
			attachedMarkers = new HashSet<Marker>();
		}

		Category category = categoryApplication.getCategory();
		boolean r = false;
		if (!attachedMarkers.contains(category)) {
			r = attachedMarkers.add(category);
			category.addMarkedEntity(this);
			getCategoryApplications();
			if (categoryApplications == null) {
				categoryApplications = new HashSet<CategoryApplication>();
			}
			categoryApplications.add(categoryApplication);
		} else {
			throw new IllegalAttributionException("Conflicts with category application of same category"); 
		}
		return r;
	}

	boolean detachCategoryApplication(CategoryApplication categoryApplication) throws IllegalAttributionException {
	    if (attachedMarkers == null) {
			return false;
		}

		Category category = categoryApplication.getCategory();
		boolean r = attachedMarkers.remove(category);
		if (r) {
			category.removeMarkedEntity(this);
			getCategoryApplications();
			if (categoryApplications == null) {
				categoryApplications = new HashSet<CategoryApplication>();
			}
			categoryApplications.remove(categoryApplication);
		} else {
			return false;
		}
		return r;
	}

	public Set<TagApplication> getTagApplications() {
		if (tagApplications != null) {
			return Collections.unmodifiableSet(new HashSet<TagApplication>(tagApplications));
		} else {
			return null;
		}
	}

	boolean attachTagApplication(TagApplication tagApplication) throws IllegalAttributionException {
		if (attachedMarkers == null) {
			attachedMarkers = new HashSet<Marker>();
		}

		Tag tag = tagApplication.getTag();
		boolean r = false;
		if (!attachedMarkers.contains(tag)) {
			r = attachedMarkers.add(tag);
			tag.addMarkedEntity(this);
			getTagApplications();
			if (tagApplications == null) {
				tagApplications = new HashSet<TagApplication>();
			}
			tagApplications.add(tagApplication);
		} else {
			throw new IllegalAttributionException("Conflicts with tag application of same tag"); 
		}
		return r;
	}

	boolean detachTagApplication(TagApplication tagApplication) throws IllegalAttributionException {  
	    if (attachedMarkers == null) {
			return false;
		}

		Tag tag = tagApplication.getTag();
		boolean r = attachedMarkers.remove(tag);
		if (r) {
			tag.removeMarkedEntity(this);
			getTagApplications();
			if (tagApplications == null) {
				tagApplications = new HashSet<TagApplication>();
			}
			tagApplications.remove(tagApplication);
		} else {
			return false;
		}
		return r;
	}

	public Extent getParentExtent() {
		if (this instanceof Extent) {
			return (Extent) this;
		}
		Parental parent = this.getParent();
		while (!(parent instanceof Extent)) {
			parent = parent.getParent();
		}
		return (Extent) parent;
	}

	public Entity clone() {
		Entity clone;
		try {
		    clone = (Entity) this.getClass().newInstance();
    	} catch (IllegalAccessException ex) {
            throw new PersistenceException("illegal access exception of identifiable class", ex);
        } catch (InstantiationException ex) {
            throw new PersistenceException("instantiation exception of identifiable class", ex);
        }
		clone.name = name;
		clone.createdBy = createdBy;
		if (creationDate != null) {
			clone.creationDate = (Date) creationDate.clone();
		}
		clone.lastModifiedBy = lastModifiedBy;
		if (lastModificationDate != null) {
			clone.lastModificationDate = (Date) lastModificationDate.clone();
		}
		clone.parent = parent;
		clone.owner = owner;
		if (accessControlList != null) {
			clone.accessControlList = accessControlList.clone();
		}
		return clone;
	}

    public String getObjectId() {
        return (String) id.getObjectId();
    }
}
