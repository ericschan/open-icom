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
import icom.Extent;
import icom.Id;
import icom.IllegalAttributionException;
import icom.Parental;
import icom.Space;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="BeehiveCategory", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveCategory extends Category {
	
	/**
	 * @$clientQualifier defaultTemplate : 
	 * BeehiveCategoryApplicationTemplate
	 * @associates BeehiveCategoryApplicationTemplate
	 */
	BeehiveCategoryApplicationTemplate defaultTemplate;
	
	private static final long serialVersionUID = 1L;
	
	BeehiveCategory() {
		super();
	}

	public BeehiveCategory(Id id, Category superCategory, Date createdOn, Date userCreatedOn) {
		super(id, superCategory, createdOn, userCreatedOn);
		addToEnterprise();
	}
	
	public BeehiveCategory(Id id, Space space, Date createdOn, Date userCreatedOn) {
		super(id, space, createdOn, userCreatedOn);
		addToEnterprise();
	}
	
	public BeehiveCategory(Category superCategory, Date createdOn, Date userCreatedOn) {
		super(superCategory, createdOn, userCreatedOn);
		addToEnterprise();
	}
	
	public BeehiveCategory(Space space, Date createdOn, Date userCreatedOn) {
		super(space, createdOn, userCreatedOn);
		addToEnterprise();
	}

	private void addToEnterprise() {
		BeehiveEnterprise enterprise = null;
		Parental parentEntity = getParent();
        while (! (parentEntity instanceof BeehiveEnterprise)) {
        	parentEntity = parentEntity.getParent();
        }
        enterprise = (BeehiveEnterprise) parentEntity;
        enterprise.addAvailableCategory(this);
	}

	public void delete() {
		if (parent != null) {
			BeehiveEnterprise enterprise = null;
			Parental parentEntity = parent;
	        while (! (parentEntity instanceof BeehiveEnterprise)) {
	        	parentEntity = parentEntity.getParent();
	        }
	        enterprise = (BeehiveEnterprise) parentEntity;
	        enterprise.removeAvailableCategory(this);
		}
		super.delete();
	}
	
	public Collection<BeehivePropertyDefinition> getPropertyDefinitions() {
		return (Collection<BeehivePropertyDefinition>) super.getPropertyDefinitions();
	}
	
	public BeehiveCategory getSuperCategory() {
		return (BeehiveCategory) super.getSuperCategory();
	}
	
	public Set<BeehiveCategory> getSubCategories() {
		return (Set<BeehiveCategory>) super.getSubCategories();
	}
	
	public boolean addPropertyDefinition(BeehivePropertyDefinition propDef) throws IllegalAttributionException {
		return super.addPropertyDefinition(propDef);
	}
	
	public boolean removePropertyDefinition(BeehivePropertyDefinition propDef) throws IllegalAttributionException {
		return super.removePropertyDefinition(propDef);
	}
	
	public BeehiveCategoryApplicationTemplate getDefaultTemplate() {
		return defaultTemplate;
	}
	
	BeehiveCategoryApplicationTemplate getEffectiveTemplate(Extent container) {
		BeehiveCategoryApplicationTemplate catAppTemplate = null;
		BeehiveCategoryConfiguration catConf = null;
		if (container instanceof BeehiveEnterprise) {
			catConf = ((BeehiveEnterprise)container).getCategoryConfiguration();
		} else if (container instanceof BeehiveCommunity) {
			catConf = ((BeehiveCommunity)container).getCategoryConfiguration();
		} else if (container instanceof BeehiveHeterogeneousFolder) {
			catConf = ((BeehiveHeterogeneousFolder)container).getCategoryConfiguration();
		}
		if (catConf != null) {
			catAppTemplate = catConf.getCategoryApplicationTemplate(this);
			if (catAppTemplate != null) {
				return catAppTemplate;
			}
		}
		
		Extent containerParent = (Extent) container.getParent();
		if (containerParent != null) {
			catAppTemplate = getEffectiveTemplate(containerParent);
		}
		if (catAppTemplate != null) {
			return catAppTemplate;
		} else {
			return getDefaultTemplate();
		}
	}

}
