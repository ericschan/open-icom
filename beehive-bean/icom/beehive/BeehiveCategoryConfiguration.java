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

import icom.Category;
import icom.ChangeToken;
import icom.Extent;
import icom.Id;
import icom.Identifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import javax.persistence.PersistenceException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@javax.persistence.Entity
@XmlType(name="BeehiveCategoryConfiguration", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveCategoryConfiguration implements Identifiable {
    
    @javax.persistence.EmbeddedId
    @XmlElement(name="objectId")
    protected Id id;

    @javax.persistence.Version
    protected ChangeToken changeToken;


	/**
	 * @$clientQualifier extent : 
	 * Extent
	 * @associates Extent
	 */
    @XmlElement(name="extent", type=Object.class)
	Extent extent;
	
	/**
	 * @$clientQualifier categoryApplicationTemplates :
	 * Collection<BeehiveCategoryApplicationTemplate>
	 * @associates BeehiveCategoryApplicationTemplate
	 * @link aggregationByValue
	 */
    @XmlElement(name="categoryApplicationTemplate")
	Collection<BeehiveCategoryApplicationTemplate> categoryApplicationTemplates;
	
	Boolean required;
	Boolean allowAll;
	Boolean finale;
	
	private static final long serialVersionUID = 1L;
	
	BeehiveCategoryConfiguration() {
		super();
	}
	
	public BeehiveCategoryConfiguration(Extent extent) throws IllegalConfigurationException {
		super();
		categoryApplicationTemplates = new Vector<BeehiveCategoryApplicationTemplate>();
		this.extent = extent;
		if (extent instanceof BeehiveEnterprise) {
			BeehiveEnterprise scope = (BeehiveEnterprise) extent;
			scope.addCategoryConfiguration(this);
		} else if (extent instanceof BeehiveCommunity) {
			BeehiveCommunity scope = (BeehiveCommunity) extent;
			scope.addCategoryConfiguration(this);
		} else if (extent instanceof BeehiveHeterogeneousFolder) {
			BeehiveHeterogeneousFolder folder = (BeehiveHeterogeneousFolder) extent;
			folder.addCategoryConfiguration(this);
		} else {
			throw new IllegalConfigurationException("Cannot create category configuration in the constrained folder");
		}
	}
	
	public Id getId() {
        return id;
    }
    
    public ChangeToken getChangeToken() {
        return changeToken;
    }
	
	public void delete() {
		try {
			if (extent != null) {
				if (extent instanceof BeehiveEnterprise) {
					BeehiveEnterprise scope = (BeehiveEnterprise) extent;
					scope.removeCategoryConfiguration(this);
				} else if (extent instanceof BeehiveCommunity) {
					BeehiveCommunity scope = (BeehiveCommunity) extent;
					scope.removeCategoryConfiguration(this);
				} else if (extent instanceof BeehiveHeterogeneousFolder) {
					BeehiveHeterogeneousFolder folder = (BeehiveHeterogeneousFolder) extent;
					folder.removeCategoryConfiguration(this);
				}
			}
		} catch (IllegalConfigurationException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public Extent getExtent() {
		return extent;
	}
	
	public Boolean isFinale() {
		return finale;
	}

	public void setFinale(Boolean isFinal) {
		this.finale = isFinal;
	}

	public Boolean isRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	public Boolean isAllowAll() {
		return allowAll;
	}

	public void setAllowAll(Boolean allowAll) {
		this.allowAll = allowAll;
	}

	public Collection<BeehiveCategoryApplicationTemplate> getCategoryApplicationTemplates() {
		if (categoryApplicationTemplates != null) {
			return Collections.unmodifiableCollection(categoryApplicationTemplates);
		} else {
			return null;
		}
	}
	
	boolean addCategoryApplicationTemplate(BeehiveCategoryApplicationTemplate catAppTemplate) throws IllegalConfigurationException {
		if (categoryApplicationTemplates == null) {
			throw new IllegalConfigurationException("Cannot add category application template to detached object when category application templates vector is not loaded");
		}
		for (BeehiveCategoryApplicationTemplate template : categoryApplicationTemplates) {
			if (template.getCategory() == catAppTemplate.getCategory()) {
				throw new IllegalConfigurationException("A category application template for the same category already exists in the configuration");
			}
		}
		boolean r = false;
		if (categoryApplicationTemplates != null) {
			r = categoryApplicationTemplates.add(catAppTemplate);
		}
		return r;
	}
	
	boolean removeCategoryApplicationTemplate(BeehiveCategoryApplicationTemplate catAppTemplate) throws IllegalConfigurationException {
		if (categoryApplicationTemplates == null) {
			throw new IllegalConfigurationException("Cannot remove category application template from detached object when category application templates vector is not loaded");
		}
		boolean r = false;
		if (categoryApplicationTemplates != null) {
			r = categoryApplicationTemplates.remove(catAppTemplate);
		}
		return r;
	}
	
	public BeehiveCategoryApplicationTemplate getCategoryApplicationTemplate(Category category) {
		Collection<BeehiveCategoryApplicationTemplate> templates = getCategoryApplicationTemplates();
		for (BeehiveCategoryApplicationTemplate template : templates) {
			if (category == template.getCategory()) {
				return template;
			}
		}
		return null;
	}

	public BeehiveCategoryConfiguration clone() {
		BeehiveCategoryConfiguration clone;
		try {
            clone = (BeehiveCategoryConfiguration) this.getClass().newInstance();
        } catch (IllegalAccessException ex) {
            throw new PersistenceException("illegal access exception of identifiable class", ex);
        } catch (InstantiationException ex) {
            throw new PersistenceException("instantiation exception of identifiable class", ex);
        }
		return clone;
	}
	
}
