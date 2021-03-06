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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="ClassDefinition", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
public class ClassDefinition extends Entity implements RelationshipBondable {

	static final long serialVersionUID = 1L;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String namespace;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String description;

	/**
	 * @$clientQualifier extendsFrom
	 * @associates ClassDefinition
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=ClassDefinition.class)
	@XmlElement(name="extendsFrom")
	Set<ClassDefinition> extendsFroms;
	
	/**
	 * @$clientQualifier stereoType
	 * @associates StereoType
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=StereoTypeEnum.class)
	StereoType stereoType;
	
	@XmlElement(name="abstract")
	Boolean abstrazt;
	
	Boolean enumeration;
	
    @XmlElement(name="instance")
	Collection<URI> instances;
	
	/**
	 * @$clientQualifier propertyDefinition
	 * @associates PropertyDefinition
	 * @link aggregationByValue
	 */
	@ManyToMany
	@XmlElement(name="propertyDefinition")
	Collection<PropertyDefinition> propertyDefinitions;
	
	/**
	 * @$clientQualifier relationship
	 * @associates Relationship
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Relationship.class)
	@XmlElement(name="relationship", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<Relationship> relationships;

	ClassDefinition() {
		super();
	}

	public ClassDefinition(Id id, Extent parent, Date createdOn) {
		super(id, parent, createdOn);
	}
	
	public ClassDefinition(Extent parent, Date createdOn) {
		super(parent, createdOn);
	}

	public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	
	public Set<? extends ClassDefinition> getExtendsFroms() {
		if (extendsFroms != null) {
			return Collections.unmodifiableSet(new HashSet<ClassDefinition>(extendsFroms));
		} else {
			return null;
		}
	}

	boolean addExtendsFrom(ClassDefinition extendsFrom) {
		boolean r = false;
		if (extendsFroms != null) {
			r = extendsFroms.add(extendsFrom);
		}
		return r;
	}
	
	boolean removeExtendsFrom(ClassDefinition extendsFrom) {
		boolean r = false;
		if (extendsFroms != null) {
			r = extendsFroms.remove(extendsFrom);
		}
		return r;
	}	
	
	public StereoType getStereoType() {
		return stereoType;
	}

	public void setStereoType(StereoType stereoType) {
		this.stereoType = stereoType;
	}

	public Collection<? extends PropertyDefinition> getPropertyDefinitions() {
		if (propertyDefinitions != null) {
			return Collections.unmodifiableCollection(new ArrayList<PropertyDefinition>(propertyDefinitions));
		} else {
			return null;
		}
	}
	
	public boolean addPropertyDefinition(PropertyDefinition attrDef) throws IllegalAttributionException {
		if (propertyDefinitions == null) {
			propertyDefinitions = new Vector<PropertyDefinition>();
		}
		if (attrDef.getName() == null) {
			throw new IllegalAttributionException("The attribute definition must have a name");
		}
		for (PropertyDefinition existingDefn : propertyDefinitions) {
			 if (attrDef.getName().equals(existingDefn.getName())) {
				throw new IllegalAttributionException("Conflicts with another attribute definition of same name " + attrDef.getName() 
						+ " description " + attrDef.getDescription());
			}
		}
		boolean r = propertyDefinitions.add(attrDef);
		return r;
	}
	
	public boolean removePropertyDefinition(PropertyDefinition attrDef) throws IllegalAttributionException {
		if (propertyDefinitions == null) {
			return false;
		}
		boolean r = propertyDefinitions.remove(attrDef);
		return r;
	}

	public Boolean isAbstrazt() {
		return abstrazt;
	}

	public void setAbstrazt(Boolean isAbstract) {
		this.abstrazt = isAbstract;
	}
	
	public Boolean isEnumeration() {
		return enumeration;
	}

	public void setEnumeration(Boolean isEnumeration) {
		this.enumeration = isEnumeration;
	}

	public Collection<URI> getInstances() {
		if (instances != null) {
			return Collections.unmodifiableCollection(new HashSet<URI>(instances));
		} else {
			return null;
		}
	}

	public void setInstances(Collection<URI> instances) {
		this.instances = instances;
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
