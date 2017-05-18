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
package icom.beehive;

import icom.Artifact;
import icom.Category;
import icom.Community;
import icom.Container;
import icom.Id;
import icom.Tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="BeehiveEnterprise", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveEnterprise extends Community implements Container {
	
	/**
	 * @$clientQualifier availableCategory
	 * @associates Category
	 * @link aggregationByValue
	 */
    @icom.annotation.DeferLoadOnAddRemove
    @XmlElement(name="availableCategory")
	Set<Category> availableCategories;
	
	/**
	 * @$clientQualifier availableTag
	 * @associates Tag
	 * @link aggregationByValue
	 */
    @icom.annotation.DeferLoadOnAddRemove
    @XmlElement(name="availableTag")
	Set<Tag> availableTags;
	
	/**
	 * @$clientQualifier availableTeamWorkspaceTemplates :
	 * Set<TeamWorkspaceTemplate>
	 * @associates TeamWorkspaceTemplate
	 * @link aggregationByValue
	 */
    @icom.annotation.DeferLoadOnAddRemove
    @XmlElement(name="availableTeamWorkspaceTemplate")
	Set<BeehiveTeamSpaceTemplate> availableTeamWorkspaceTemplates;
	
	/**
	 * @$clientQualifier versionControlConfiguration : 
	 * BeehiveVersionControlConfiguration
	 * @associates BeehiveVersionControlConfiguration
	 */
	BeehiveVersionControlConfiguration versionControlConfiguration;
	
	/**
	 * @$clientQualifier categoryConfiguration : 
	 * CategoryConfiguration
	 * @associates CategoryConfiguration
	 */
	BeehiveCategoryConfiguration categoryConfiguration;
	
	private static final long serialVersionUID = 1L;

	BeehiveEnterprise() {
		super();
	}
	
	public BeehiveEnterprise(Id id, Date createdOn) {
		super(id, null, createdOn);
	}
	
	public BeehiveEnterprise(Date createdOn) {
		super(null, createdOn);
		availableCategories = new HashSet<Category>();
		availableTags = new HashSet<Tag>();
	}

	public Set<Category> getAvailableCategories() {
		if (availableCategories != null) {
			return Collections.unmodifiableSet(new HashSet<Category>(availableCategories));
		} else {
			return null;
		}
	}
	
	boolean addAvailableCategory(Category category) {
		boolean r = true;
		if (availableCategories != null) {
			r = availableCategories.add(category);
		}
		return r;
	}
	
	boolean removeAvailableCategory(Category category) {
		boolean r = true;
		if (availableCategories != null) {
			r = availableCategories.remove(category);
		}
		return r;
	}
	
	public Set<Tag> getAvailableTags() {
		if (availableTags != null) {
			return Collections.unmodifiableSet(new HashSet<Tag>(availableTags));
		}
		return null;
	}
	
	boolean addAvailableTag(Tag tag) {
		boolean r = true;
		if (availableTags != null) {
			r = availableTags.add(tag);
		}
		return r;
	}
	
	boolean removeAvailableTag(Tag tag) {
		boolean r = true;
		if (availableTags != null) {
			r = availableTags.remove(tag);
		}
		return r;
	}
	
	public BeehiveVersionControlConfiguration getVersionControlConfiguration() {
		return versionControlConfiguration;
	}
	
	boolean addVersionControlConfiguration(BeehiveVersionControlConfiguration versionControlConfiguration) throws IllegalConfigurationException {
		BeehiveVersionControlConfiguration existingVersionControlConfiguration = getVersionControlConfiguration();
		if (existingVersionControlConfiguration != null && existingVersionControlConfiguration.getExtent() == this) {
			throw new IllegalConfigurationException("A version control configuration already exists");
		}
		this.versionControlConfiguration = versionControlConfiguration;
		return true;
	}
	
	boolean removeVersionControlConfiguration(BeehiveVersionControlConfiguration versionControlConfiguration) throws IllegalConfigurationException {
		BeehiveVersionControlConfiguration existingVersionControlConfiguration = getVersionControlConfiguration();
		if (existingVersionControlConfiguration != versionControlConfiguration) {
			throw new IllegalConfigurationException("The version control configuration does not belong to the scope");
		}
		this.versionControlConfiguration = null;
		return true;
	}
	
	public BeehiveCategoryConfiguration getCategoryConfiguration() {
		return categoryConfiguration;
	}
	
	boolean addCategoryConfiguration(BeehiveCategoryConfiguration categoryConfiguration) throws IllegalConfigurationException {
		BeehiveCategoryConfiguration existingTemplateConfiguration = getCategoryConfiguration();
		if (existingTemplateConfiguration != null) {
			throw new IllegalConfigurationException("A category configuration already exists");
		}
		this.categoryConfiguration = categoryConfiguration;
		return true;
	}
	
	boolean removeCategoryConfiguration(BeehiveCategoryConfiguration categoryConfiguration) throws IllegalConfigurationException {
		BeehiveCategoryConfiguration existingTemplateConfiguration = getCategoryConfiguration();
		if (existingTemplateConfiguration != categoryConfiguration) {
			throw new IllegalConfigurationException("The category configuration does not belong to the scope");
		}
		this.categoryConfiguration = null;
		return true;
	}
	
	/*
	BeehiveCategoryApplicationTemplate getCategoryApplicationTemplate(Category category) {
		BeehiveCategoryConfiguration catConf = getCategoryConfiguration();
		if (catConf != null) {
			BeehiveCategoryApplicationTemplate catAppTemplate = catConf.getCategoryApplicationTemplate(category);
			if (catAppTemplate != null) {
				return catAppTemplate;
			}
		}
		Scope parent = (Scope) getParent();
		if (parent != null) {
			return parent.getCategoryApplicationTemplate(category);
		}
		return null;
	}
	*/
	
	public Collection<? extends Artifact> getElements() {
		Set<Category> categories = getAvailableCategories();
		Set<Tag> tags = getAvailableTags();
		Set<Artifact> elements = new HashSet<Artifact>(categories.size() + tags.size());
		elements.addAll(categories);
		elements.addAll(tags);
		return elements;
	}
}
