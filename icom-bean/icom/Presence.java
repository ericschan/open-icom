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
import java.util.List;

import javax.persistence.Embedded;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@javax.persistence.Entity
@XmlType(name="Presence", namespace="http://docs.oasis-open.org/ns/icom/presence/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/presence/201008")
public class Presence implements Identifiable {

	private static final long serialVersionUID = 1L;
	
    @javax.persistence.EmbeddedId
    @XmlElement(name="objectId", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    protected Id id;

	@javax.persistence.Version
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    protected ChangeToken changeToken;
	
	/**
	 * @$clientQualifier editMode
	 * @associates PresenceEditMode
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=PresenceEditModeEnum.class)
	PresenceEditMode editMode;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Date lastModificationDate;
	
	/**
     * @$clientQualifier location
     * @associates Location
     * @clientNavigability NAVIGABLE
     */
    @Embedded
    @XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    Location location;
	
	/**
	 * @$clientQualifier activity
	 * @associates Activity
	 * @link aggregationByValue
	 */
	@XmlElement(name="activity")
	Collection<Activity> activities;
	
	/**
	 * @$clientQualifier contactMethod
	 * @associates ContactMethod
	 * @link aggregationByValue
	 */
	@XmlElement(name="contactMethod")
	List<ContactMethod> contactMethods;
	
	public Id getId() {
        return id;
    }
    
    public ChangeToken getChangeToken() {
        return changeToken;
    }
	
	public boolean isEditable() {
		return getEditMode() == PresenceEditModeEnum.PresentityCopy;
	}
	
	public PresenceEditMode getEditMode() {
		return editMode;
	}
	
	public Date getLastModificationDate() {
		return lastModificationDate;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Collection<Activity> getActivities() {
		if (activities != null) {
			return Collections.unmodifiableCollection(new ArrayList<Activity>(activities));
		} else {
			return null;
		}
	}
	
	public boolean addActivity(Activity activity) {
		boolean r = false;
		if (activities != null) {
			r = activities.add(activity);
		}
		return r;
	}
	
	public boolean removeActivity(Activity activity) {
		boolean r = false;
		if (activities != null) {
			r = activities.remove(activity);
		}
		return r;
	}

	public Collection<ContactMethod> getContactMethods() {
		if (contactMethods != null) {
			return Collections.unmodifiableCollection(new ArrayList<ContactMethod>(contactMethods));
		} else {
			return null;
		}
	}
	
}
