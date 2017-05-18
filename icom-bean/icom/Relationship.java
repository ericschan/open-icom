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

import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Relationship", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
public class Relationship extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier relationshipDefinition
	 * @associates RelationshipDefinition
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne(targetEntity=RelationshipDefinition.class)
	RelationshipDefinition relationshipDefinition;
	
	/**
	 * @$clientQualifier sourceEntity 
	 * @associates RelationshipBondable
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne(targetEntity=RelationshipBondable.class)
	@XmlElement(name="sourceEntity", type=Object.class)
	RelationshipBondable sourceEntity;
	
	/**
	 * @$clientQualifier targetEntity
	 * @associates RelationshipBondable
	 * @link aggregationByValue
	 */
	@ManyToMany(targetEntity=RelationshipBondable.class)
	@XmlElement(name="targetEntity", type=Object.class)
	Set<RelationshipBondable> targetEntities;
	
	/**
	 * @$clientQualifier property
	 * @associates Property
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="property")
	Collection<Property> properties;
	
	Relationship() {
		super();
	}

	public Relationship(Id id, RelationshipDefinition relationshipDefinition, RelationshipBondable sourceEntity, Date createdOn) {
		super(id, sourceEntity, createdOn);
		this.relationshipDefinition = relationshipDefinition;
		this.sourceEntity = sourceEntity;
		addRelationshipToRelationshipBondable(sourceEntity);
	}
	
	public Relationship(RelationshipDefinition relationshipDefinition, RelationshipBondable sourceEntity, Date createdOn) {
		super(sourceEntity, createdOn);
		this.relationshipDefinition = relationshipDefinition;
		this.sourceEntity = sourceEntity;
		targetEntities = new HashSet<RelationshipBondable>();
		addRelationshipToRelationshipBondable(sourceEntity);
	}

	public void delete() {
		if (sourceEntity != null) {
			removeRelationshipFromRelationshipBondable(sourceEntity);
		}
		if (targetEntities != null) {
			for (RelationshipBondable relatedEntity : targetEntities.toArray(new RelationshipBondable[targetEntities.size()])) {
				removeRelationshipFromRelationshipBondable(relatedEntity);
			}
		}
		super.delete();
	}
	
	private void addRelationshipToRelationshipBondable(RelationshipBondable relatedEntity) {
		if (relatedEntity instanceof Artifact) {
			((Artifact)relatedEntity).addRelationship(this);
		} else if (relatedEntity instanceof Subject) {
			((Subject)relatedEntity).addRelationship(this);
		} else if (relatedEntity instanceof Scope) {
			((Scope)relatedEntity).addRelationship(this);
		} else if (relatedEntity instanceof ClassDefinition) {
			((ClassDefinition)relatedEntity).addRelationship(this);
		} else if (relatedEntity instanceof RoleDefinition) {
			((ClassDefinition)relatedEntity).addRelationship(this);
		} else if (relatedEntity instanceof RelationshipDefinition) {
			((ClassDefinition)relatedEntity).addRelationship(this);
		} else if (relatedEntity instanceof Version) {
			((Version)relatedEntity).addRelationship(this);
		} else if (relatedEntity instanceof VersionSeries) {
			((VersionSeries)relatedEntity).addRelationship(this);	
		}
	}
	
	private void removeRelationshipFromRelationshipBondable(RelationshipBondable relatedEntity) {
		if (relatedEntity instanceof Artifact) {
			((Artifact)relatedEntity).removeRelationship(this);
		} else if (relatedEntity instanceof Subject) {
			((Subject)relatedEntity).removeRelationship(this);
		} else if (relatedEntity instanceof Scope) {
			((Scope)relatedEntity).removeRelationship(this);
		} else if (relatedEntity instanceof ClassDefinition) {
			((ClassDefinition)relatedEntity).removeRelationship(this);
		} else if (relatedEntity instanceof RoleDefinition) {
			((ClassDefinition)relatedEntity).removeRelationship(this);
		} else if (relatedEntity instanceof RelationshipDefinition) {
			((ClassDefinition)relatedEntity).removeRelationship(this);
		} else if (relatedEntity instanceof Version) {
			((Version)relatedEntity).removeRelationship(this);
		} else if (relatedEntity instanceof VersionSeries) {
			((VersionSeries)relatedEntity).removeRelationship(this);	
		}
	}

	public RelationshipDefinition getRelationshipDefinition() {
		return relationshipDefinition;
	}
	
	public RelationshipBondable getSourceEntity() {
		return sourceEntity;
	}
	
	public Set<RelationshipBondable> getTargetEntities() {
		if (targetEntities != null) {
			return Collections.unmodifiableSet(new HashSet<RelationshipBondable>(targetEntities));
		} else {
			return null;
		}
	}

	public boolean addTargetEntity(RelationshipBondable relatedEntity) {
		boolean r = false;
		if (targetEntities != null) {
			r = targetEntities.add(relatedEntity);
			if (r) {
				addRelationshipToRelationshipBondable(relatedEntity);
			}
		}
		return r;
	}
	
	public boolean removeTargetEntity(RelationshipBondable relatedEntity) {
		boolean r = false;
		if (targetEntities != null) {
			r = targetEntities.remove(relatedEntity);
			if (r) {
				removeRelationshipFromRelationshipBondable(relatedEntity);
			}
		}
		return r;
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
	
}
