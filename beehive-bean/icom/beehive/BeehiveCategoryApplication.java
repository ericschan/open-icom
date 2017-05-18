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

import icom.CategoryApplication;
import icom.Entity;
import icom.Extent;
import icom.IllegalAttributionException;
import icom.Property;
import icom.PropertyDefinition;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="BeehiveCategoryApplication", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveCategoryApplication extends CategoryApplication {

	/**
	 * @$clientQualifier categoryApplicationTemplate : 
	 * BeehiveCategoryApplicationTemplate
	 * @associates BeehiveCategoryApplicationTemplate
	 */
	BeehiveCategoryApplicationTemplate categoryApplicationTemplate;
	
	private static final long serialVersionUID = 1L;
	
	BeehiveCategoryApplication() {
		super();
	}
	
	public BeehiveCategoryApplication(BeehiveCategory category, Entity categorizedEntity) throws IllegalAttributionException {
		super(category, categorizedEntity);
		Extent container = categorizedEntity.getParentExtent();
		categoryApplicationTemplate = category.getEffectiveTemplate(container);
	}
	
	public BeehiveCategory getCategory() {
		return (BeehiveCategory) super.getCategory();
	}
	
	boolean modifyProperty(BeehiveProperty property) throws IllegalAttributionException {
		if (properties == null || ! properties.contains(property)) {
			throw new IllegalAttributionException("Attribute does not belong to the category application");
		}
		
		Entity entity = getAttachedEntity();
		if (entity == null) {
			return false;
		}
		Extent container = entity.getParentExtent();
		BeehiveCategory category = getCategory();

		PropertyDefinition propertyDef = property.getPropertyDefinition();
	
		BeehivePropertyTemplate propertyTemplateToApply = null;
		while (category != null) {
			if (categoryApplicationTemplate == null) {
				categoryApplicationTemplate = category.getEffectiveTemplate(container);
			}
			if (categoryApplicationTemplate != null) {
				Collection<BeehivePropertyTemplate> propertyTemplates = categoryApplicationTemplate.getPropertyTemplates();
				for (BeehivePropertyTemplate propertyTemplate : propertyTemplates) {
					if (propertyTemplate.getPropertyDefinition() == propertyDef) {
						if (satisfy(propertyTemplate, property.getValue())) {
							propertyTemplateToApply = propertyTemplate;
							break;
						} else {
							throw new IllegalAttributionException("Attribute value does not satisfy the template");
						}
					}
				}
				if (propertyTemplateToApply != null) {
					break;
				}
			}
			category = category.getSuperCategory();
		}
		property.propertyTemplate = propertyTemplateToApply;
		return true;
	}
	
	public boolean addProperty(BeehiveProperty property) throws IllegalAttributionException {
		if (properties == null) {
			properties = new Vector<Property>();
		}
		Iterator<Property> iter = properties.iterator();
		PropertyDefinition propertyDef = property.getPropertyDefinition();
		while (iter.hasNext()) {
			if (propertyDef == iter.next().getPropertyDefinition()) {
				throw new IllegalAttributionException("Conflicts with another attribute of same definition");
			}
		}
		
		Entity entity = getAttachedEntity();
		if (entity == null) {
			return false;
		}
		Extent container = entity.getParentExtent();
		BeehiveCategory category = getCategory();
		
		BeehivePropertyTemplate propertyTemplateToApply = null;
		while (category != null) {
			if (categoryApplicationTemplate == null) {
				categoryApplicationTemplate = category.getEffectiveTemplate(container);
			}
			if (categoryApplicationTemplate != null) {
				Collection<BeehivePropertyTemplate> propertyTemplates = categoryApplicationTemplate.getPropertyTemplates();
				for (BeehivePropertyTemplate propertytTemplate : propertyTemplates) {
					if (propertytTemplate.getPropertyDefinition() == propertyDef) {
						if (satisfy(propertytTemplate, property.getValue())) {
							propertyTemplateToApply = propertytTemplate;
							break;
						} else {
							throw new IllegalAttributionException("Attribute value does not satisfy the template");
						}
					} else if (propertytTemplate.getPropertyDefinition().getName().equals(propertyDef.getName())) {
						if (satisfy(propertytTemplate, property.getValue())) {
							propertyTemplateToApply = propertytTemplate;
							break;
						} else {
							throw new IllegalAttributionException("Attribute value does not satisfy the template");
						}
					}
				}
				if (propertyTemplateToApply != null) {
					break;
				}
			}
			category = category.getSuperCategory();
		}
		
		boolean r = false;
		if (propertyTemplateToApply != null) {
			r = properties.add(property);
		}
		
		if (r) {
			property.categoryApplication = this;
			property.propertyTemplate = propertyTemplateToApply;
		}
		
		return r;
	}
	
	public boolean removeProperty(BeehiveProperty property) throws IllegalAttributionException {
		if (properties == null || ! properties.contains(property)) {
			return false;
		}
		
		Entity entity = getAttachedEntity();
		if (entity == null) {
			return false;
		}
		Extent container = entity.getParentExtent();
		BeehiveCategory category = getCategory();
		categoryApplicationTemplate = category.getEffectiveTemplate(container);
		
		PropertyDefinition propertyDef = property.getPropertyDefinition();
	
		BeehivePropertyTemplate propertyTemplateToApply = null;
		while (category != null) {
			if (categoryApplicationTemplate == null) {
				categoryApplicationTemplate = category.getEffectiveTemplate(container);
			}
			if (categoryApplicationTemplate != null) {
				Collection<BeehivePropertyTemplate> propertyTemplates = categoryApplicationTemplate.getPropertyTemplates();
				for (BeehivePropertyTemplate propertyTemplate : propertyTemplates) {
					if (propertyTemplate.getPropertyDefinition() == propertyDef) {
						if (!propertyTemplate.isRequired()) {
							propertyTemplateToApply = propertyTemplate;
							break;
						} else {
							throw new IllegalAttributionException("Cannot remove a mandatory attribute");
						}
					}
				}
				if (propertyTemplateToApply != null) {
					break;
				}
			}
			category = category.getSuperCategory();
		}
		
		boolean r = false;
		if (propertyTemplateToApply != null) {
			r = properties.remove(property);
		}
		
		if (r) {
			property.categoryApplication = null;
			property.propertyTemplate = propertyTemplateToApply;
		}

		return r;
	}
	
	protected boolean satisfy(BeehivePropertyTemplate attrTemplate, Object attrValue) {
		return true; // TODO
	}
	
	
}
