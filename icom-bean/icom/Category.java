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

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Category", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
public class Category extends Marker implements Container {

	/**
	 * @$clientQualifier superCategory
	 * @associates Category
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Category.class)
	@XmlElement(name="superCategory")
	Set<Category> superCategories;
	
	/**
	 * @$clientQualifier subCategory
	 * @associates Category
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Category.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="superCategory")
	@XmlElement(name="subCategory")
	Set<Category> subCategories;
	
	/**
	 * @$clientQualifier propertyDefinition
	 * @associates PropertyDefinition
	 * @link aggregationByValue
	 */
	@ManyToMany
	@XmlElement(name="propertyDefinition")
	Collection<PropertyDefinition> propertyDefinitions;
	
	@XmlElement(name="abstract")
	Boolean abstrazt;
	
	private static final long serialVersionUID = 1L;

	protected Category() {
		super();
	}

	public Category(Id id, Category superCategory, Date createdOn, Date userCreatedOn) {
		super(id, superCategory.getParent(), createdOn, userCreatedOn);
		superCategories = new HashSet<Category>();
		if (superCategory != null) {
			superCategories.add(superCategory);
			superCategory.addSubCategory(this);
		}
	}
	
	public Category(Id id, Space space, Date createdOn, Date userCreatedOn) {
		super(id, space, createdOn, userCreatedOn);
	}
	
	public Category(Category superCategory, Date createdOn, Date userCreatedOn) {
		super(superCategory.getParent(), createdOn, userCreatedOn);
		propertyDefinitions = new Vector<PropertyDefinition>();
		superCategories = new HashSet<Category>();
		subCategories = new HashSet<Category>();
		if (superCategory != null) {
			superCategories.add(superCategory);		
			superCategory.addSubCategory(this);
		}
	}
	
	public Category(Space space, Date createdOn, Date userCreatedOn) {
		super(space, createdOn, userCreatedOn);
		propertyDefinitions = new Vector<PropertyDefinition>();
		superCategories = new HashSet<Category>();
		subCategories = new HashSet<Category>();
	}

	public void delete() {
		if (superCategories != null) {
			for (Category superCategory : superCategories)
				superCategory.removeSubCategory(this);
		}
		super.delete();
	}
	
	public Category getSuperCategory() {
		Set<? extends Category> superCategories = getSuperCategories();
		if (superCategories != null) {
			return superCategories.iterator().next();
		} else {
			return null;
		}
	}
	
	public Set<? extends Category> getSuperCategories() {
		if (superCategories != null) {
			return Collections.unmodifiableSet(new HashSet<Category>(superCategories));
		} else {
			return null;
		}
	}

	boolean addSuperCategory(Category superCategory) {
		boolean r = false;
		if (superCategories != null) {
			r = superCategories.add(superCategory);
		}
		return r;
	}
	
	boolean removeSuperCategory(Category superCategory) {
		boolean r = false;
		if (superCategories != null) {
			r = superCategories.remove(superCategory);
		}
		return r;
	}	
	
	public Collection<? extends Category> getElements() {
		return getSubCategories();
	}
	
	public Set<? extends Category> getSubCategories() {
		if (subCategories != null) {
			return Collections.unmodifiableSet(new HashSet<Category>(subCategories));
		} else {
			return null;
		}
	}

	boolean addSubCategory(Category subCategory) {
		boolean r = false;
		if (subCategories != null) {
			r = subCategories.add(subCategory);
		}
		return r;
	}
	
	boolean removeSubCategory(Category subCategory) {
		boolean r = false;
		if (subCategories != null) {
			r = subCategories.remove(subCategory);
		}
		return r;
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

}
