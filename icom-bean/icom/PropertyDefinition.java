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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.persistence.Embedded;
import javax.persistence.PersistenceException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@javax.persistence.Entity
@XmlType(name="PropertyDefinition", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
public class PropertyDefinition implements Identifiable {
    
    @javax.persistence.EmbeddedId
    @XmlElement(name="objectId", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    protected Id id;

    @javax.persistence.Version
    @XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    protected ChangeToken changeToken;

    @XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    String namespace;
    
    @XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String name;
    
    @XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String description;
	
	/**
	 * @$clientQualifier propertyType
	 * @associates PropertyType
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=PropertyTypeEnum.class)
	PropertyType propertyType;
	
	/**
	 * @$clientQualifier cardinality
	 * @associates Cardinality
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=CardinalityEnum.class)
	Cardinality cardinality;
	
	/**
	 * @$clientQualifier choice
	 * @associates PropertyChoiceType
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="choice")
	Collection<PropertyChoiceType> choices;
        
    Boolean openChoice;
	
	Object defaultValue;
	Object minValue;
	Object maxValue;
	
	Boolean minimumValueInclusive;
	Boolean maximumValueInclusive;
	Boolean queryable;
        
    Boolean inhertied;
    Boolean required;
        
    /**
     * @$clientQualifier updatability
     * @associates Updatability
     * @clientNavigability NAVIGABLE
     */
     @XmlElement(type=UpdatabilityEnum.class)
    Updatability updatability;
	
	private static final long serialVersionUID = 1L;
	
	protected PropertyDefinition() {
		super();
	}
	
	public PropertyDefinition(String name) {
		this.name = name;
	}
	
	public Id getId() {
        return id;
    }
    
    public ChangeToken getChangeToken() {
        return changeToken;
    }

	public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
	
	public Cardinality getCardinality() {
		return cardinality;
	}

	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	public Collection<PropertyChoiceType> getChoices() {
		if (choices != null) {
			return Collections.unmodifiableCollection(new HashSet<PropertyChoiceType>(choices));
		} else {
			return null;                                                
		}
	}
	
	public boolean addChoice(PropertyChoiceType propertyChoice) {
		boolean r = false;
		if (choices != null) {
			r = choices.add(propertyChoice);
		}
		return r;
	}
	
	public boolean removeChoice(PropertyChoiceType propertyChoice) {
		boolean r = false;
		if (choices != null) {
			r = choices.remove(propertyChoice);
		}
		return r;
	}

    public Boolean getOpenChoice() {
        return openChoice;
    }      
        
    public void setOpenChoice(Boolean openChoice) {
        this.openChoice = openChoice;
    }
	
	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Object getMinValue() {
		return minValue;
	}

	public void setMinValue(Object minimumValue) {
		this.minValue = minimumValue;
	}
	
	public Object getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Object maximumValue) {
		this.maxValue = maximumValue;
	}

	public Boolean isQueryable() {
		return queryable;
	}

	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}

    public Boolean getInhertied() {
        return inhertied;
    }

    public void setInhertied(Boolean inhertied) {
        this.inhertied = inhertied;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Updatability getUpdatability() {
        return updatability;
    }

    public void setUpdatability(Updatability updatability) {
        this.updatability = updatability;
    }
    
	public PropertyDefinition clone() {
		PropertyDefinition clone;
		try {
            clone = (PropertyDefinition) this.getClass().newInstance();
        } catch (IllegalAccessException ex) {
            throw new PersistenceException("illegal access exception of identifiable class", ex);
        } catch (InstantiationException ex) {
            throw new PersistenceException("instantiation exception of identifiable class", ex);
        }
		return clone;
	}

}
