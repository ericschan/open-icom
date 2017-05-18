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

import java.util.ArrayList;
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
@XmlType(name="Artifact", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
public abstract class Artifact extends Entity implements Item, RelationshipBondable {
	
	String description;
	
	/**
	 * @$clientQualifier relationship
	 * @associates Relationship
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Relationship.class)
	@XmlElement(name="relationship", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<Relationship> relationships;

	Date userCreationDate;
	
	Date userLastModificationDate;
	
	/**
	 * @$clientQualifier property
	 * @associates Property
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="property", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Collection<Property> properties;
	
	/**
	 * @$clientQualifier viewerProperty
	 * @associates Property
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="viewerProperty", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Collection<Property> viewerProperties;
	
	private static final long serialVersionUID = 1L;
	
	protected Artifact() {
		super();
	}

	public Artifact(Id id, Container parent, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn);
		this.userCreationDate = userCreatedOn;
		if (parent instanceof HeterogeneousFolder) {
			((HeterogeneousFolder)parent).addElement(this);
		}
	}
	
	public Artifact(Container parent, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn);
		this.userCreationDate = userCreatedOn;
		relationships = new HashSet<Relationship>();
		properties = new Vector<Property>();
		viewerProperties = new Vector<Property>();
		if (parent instanceof HeterogeneousFolder) {
			((HeterogeneousFolder)parent).addElement(this);
		}
	}

	public void delete() {
		if (parent instanceof HeterogeneousFolder) {
			((HeterogeneousFolder)parent).removeElement(this);
		}
		super.delete();
	}
	
	public Container getParent() {
		return (FolderContainer) super.getParent();
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

	public Date getUserCreationDate() {
		return userCreationDate;
	}
	
	public void setUserCreationDate(Date userCreationDate) {
		this.userCreationDate = userCreationDate;
	}

	public Date getUserLastModificationDate() {
		return userLastModificationDate;
	}

	public void setUserLastModificationDate(Date userLastModificationDate) {
		this.userLastModificationDate = userLastModificationDate;
	}
	
	public Collection<Property> getProperties() {
		if (properties != null) {
			return Collections.unmodifiableCollection(new ArrayList<Property>(properties));
		} else {
			return null;
		}
	}
	
	public boolean addProperty(Property property) {
		boolean r = false;
		if (properties != null) {
			r = properties.add(property);
		}
		return r;
	}
	
	public boolean removeProperty(Property property) {
		boolean r = false;
		if (properties != null) {
			r = properties.remove(property);
		}
        return r;
	}
	
	public Collection<Property> getViewerProperties() {
		if (viewerProperties != null) {
			return Collections.unmodifiableCollection(new ArrayList<Property>(viewerProperties));
		} else {
			return null;
		}
	}
	
	public boolean addViewerProperty(Property property) {
		boolean r = false;
		if (viewerProperties != null) {
			r = viewerProperties.add(property);
		}
		return r;
	}
	
	public boolean removeViewerProperty(Property property) {
		boolean r = false;
		if (viewerProperties != null) {
			r = viewerProperties.remove(property);
		}
		return r;
	}
	
	public Artifact clone() {
		Artifact clone = (Artifact) super.clone();
		if (userCreationDate != null) {
			clone.userCreationDate = (Date) userCreationDate.clone();
		}
		if (userLastModificationDate != null) {
			clone.userLastModificationDate = (Date) userLastModificationDate.clone();
		}
		if (properties != null) {
			clone.properties = new Vector<Property>(properties.size());
			for (Property p : properties) {
				clone.properties.add(p.clone());
			}
		}
		if (viewerProperties != null) {
			clone.viewerProperties = new Vector<Property>(viewerProperties.size());
			for (Property p : viewerProperties) {
				clone.viewerProperties.add(p.clone());
			}
		}
		return clone;
	}
}
