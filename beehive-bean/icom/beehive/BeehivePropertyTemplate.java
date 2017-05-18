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

import icom.ChangeToken;
import icom.Id;
import icom.Identifiable;
import icom.IllegalAttributionException;
import icom.PropertyChoiceType;

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
@XmlType(name="BeehivePropertyTemplate", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehivePropertyTemplate implements Identifiable {
    
    @javax.persistence.EmbeddedId
    @XmlElement(name="objectId")
    protected Id id;

    @javax.persistence.Version
    protected ChangeToken changeToken;

	/**
	 * @$clientQualifier propertyDefinition : 
	 * BeehivePropertyDefinition
	 * @associates BeehivePropertyDefinition
	 */
	BeehivePropertyDefinition propertyDefinition;
	
	@XmlElement(name="choice")
	Collection<PropertyChoiceType> choices;
	
	Object defaultValue;
	Object minValue;
	Object maxValue;
	Boolean required;
	
	Boolean finale;
	Boolean prompted;
	Boolean forceDefault;
	Boolean minValueInclusive;
	Boolean maxValueInclusive;
	
	private static final long serialVersionUID = 1L;
	
	BeehivePropertyTemplate() {
		super();
	}
	
	public BeehivePropertyTemplate(BeehivePropertyDefinition propertyDefinition) 
								throws IllegalAttributionException {
		this.propertyDefinition = propertyDefinition;
	}
	
	public BeehivePropertyDefinition getPropertyDefinition() {
		return propertyDefinition;
	}
	
	public Id getId() {
        return id;
    }
    
    public ChangeToken getChangeToken() {
        return changeToken;
    }
	
	public Collection<PropertyChoiceType> getChoices() {
		return Collections.unmodifiableCollection(choices);
	}

	public void setChoices(Collection<PropertyChoiceType> allowedValues) {
		Vector<PropertyChoiceType> updatedAllowedValues = new Vector<PropertyChoiceType>(allowedValues.size());
		Iterator<PropertyChoiceType> iter = allowedValues.iterator();
		while (iter.hasNext()) {
			updatedAllowedValues.add(iter.next());
		}
		this.choices = updatedAllowedValues;
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
	
	public Boolean isMinValueInclusive() {
		return minValueInclusive;
	}

	public void setMinValueInclusive(Boolean minimumValueInclusive) {
		this.minValueInclusive = minimumValueInclusive;
	}
	
	public Boolean isMaxValueInclusive() {
		return maxValueInclusive;
	}

	public void setMaxValueInclusive(Boolean maximumValueInclusive) {
		this.maxValueInclusive = maximumValueInclusive;
	}
	
	public Boolean isRequired() {
		return required;
	}

	public void setRequired(Boolean mandatory) {
		this.required = mandatory;
	}
	
	public Boolean isFinale() {
		return finale;
	}

	public void setFinale(Boolean isFinal) {
		this.finale = isFinal;
	}
	
	public Boolean isPrompted() {
		return prompted;
	}

	public void setPrompted(Boolean prompted) {
		this.prompted = prompted;
	}
	
	public Boolean isForceDefault() {
		return forceDefault;
	}

	public void setForceDefault(Boolean forceDefault) {
		this.forceDefault = forceDefault;
	}
	
	/*
	public void delete() {
		try {
			Identifiable parent = getParent();
			if (parent != null) {
				if (parent instanceof CategoryApplicationTemplate) {
					((CategoryApplicationTemplate)parent).removeAttribute(this);
				}
			}
		} catch (IllegalAttributionException ex) {
			throw new java.lang.IllegalStateException(ex);
		}
	}
	*/
	
	public BeehivePropertyTemplate clone() {
		BeehivePropertyTemplate clone;
		try {
            clone = (BeehivePropertyTemplate) this.getClass().newInstance();
        } catch (IllegalAccessException ex) {
            throw new PersistenceException("illegal access exception of identifiable class", ex);
        } catch (InstantiationException ex) {
            throw new PersistenceException("instantiation exception of identifiable class", ex);
        }
		return clone;
	}
	
}
