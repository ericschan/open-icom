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
import icom.Id;
import icom.Identifiable;
import icom.IllegalAttributionException;
import icom.Parental;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.persistence.PersistenceException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@javax.persistence.Entity
@XmlType(name="BeehiveCategoryApplicationTemplate", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveCategoryApplicationTemplate implements Identifiable {
    
    @javax.persistence.EmbeddedId
    @XmlElement(name="objectId")
    protected Id id;

    @javax.persistence.Version
    protected ChangeToken changeToken;

	/**
	 * @$clientQualifier category : 
	 * Category
	 * @associates Category
	 */
	Category category;
	
	/**
	 * @$clientQualifier propertyTemplates :
	 * Collection<BeehivePropertyTemplate>
	 * @associates BeehivePropertyTemplate
	 * @link aggregationByValue
	 */
	Collection<BeehivePropertyTemplate> propertyTemplates;
	
	/**
	 * @$clientQualifier categoryConfiguration : 
	 * CategoryConfiguration
	 * @associates CategoryConfiguration
	 */
	BeehiveCategoryConfiguration categoryConfiguration;
	
	@XmlElement(name="bookmark", type=Object.class)
	Parental parent;
	
	Boolean copyOnVersion;
	Boolean finale;
	Boolean required;
	  
	private static final long serialVersionUID = 1L;

	BeehiveCategoryApplicationTemplate() {
		super();
	}
	
	public BeehiveCategoryApplicationTemplate(Category category, BeehiveCategoryConfiguration config) 
									throws IllegalConfigurationException {
		this.category = category;
		this.categoryConfiguration = config;
		propertyTemplates = new Vector<BeehivePropertyTemplate>();
		config.addCategoryApplicationTemplate(this);
	}
	
	public Id getId() {
        return id;
    }
    
    public ChangeToken getChangeToken() {
        return changeToken;
    }
	
	public void delete() {
		try {
			if (categoryConfiguration != null) {
				categoryConfiguration.removeCategoryApplicationTemplate(this);
			}
		} catch (IllegalConfigurationException ex) {
			throw new java.lang.IllegalStateException(ex);
		}
	}
	
	public Parental getParent() {
		return parent;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public BeehiveCategoryConfiguration getCategoryConfiguration() {
		return categoryConfiguration;
	}

	public Boolean isCopyOnVersion() {
		return copyOnVersion;
	}
	
	public void setCopyOnVersion(Boolean copyOnVersion) {
		this.copyOnVersion = copyOnVersion;
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
	
	public Collection<BeehivePropertyTemplate> getPropertyTemplates() {
		if (propertyTemplates != null) {
			return Collections.unmodifiableCollection(propertyTemplates);
		} else {
			return null;
		}
	}
	
	public boolean addPropertyTemplate(BeehivePropertyTemplate propertyTemplate) throws IllegalAttributionException {
		boolean r = false;
		if (! propertyTemplates.contains(propertyTemplate)) {
			if (propertyTemplate.getPropertyDefinition() == null) {
				throw new IllegalAttributionException("The property definition must be specified in the property template");
			}
			Iterator<BeehivePropertyTemplate> iter = propertyTemplates.iterator();
			while (iter.hasNext()) {
				 if (iter.next().getPropertyDefinition() == propertyTemplate.getPropertyDefinition()) {
					throw new IllegalAttributionException("Conflicts with another property template of same property definition " 
									+ iter.next().getPropertyDefinition().getName());
				}
			}
			r = propertyTemplates.add(propertyTemplate);
		}
		return r;
	}
	
	public boolean removePropertyTemplate(BeehivePropertyTemplate propertyTemplate) throws IllegalAttributionException {
		boolean r = false;
		if (propertyTemplates != null) {
			r = propertyTemplates.remove(propertyTemplate);
		}
		return r;
	}

	public BeehiveCategoryApplicationTemplate clone() {
		BeehiveCategoryApplicationTemplate clone;
		try {
            clone = (BeehiveCategoryApplicationTemplate) this.getClass().newInstance();
        } catch (IllegalAccessException ex) {
            throw new PersistenceException("illegal access exception of identifiable class", ex);
        } catch (InstantiationException ex) {
            throw new PersistenceException("instantiation exception of identifiable class", ex);
        }
		return clone;
	}
	
}
