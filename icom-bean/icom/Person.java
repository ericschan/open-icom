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
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Person", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
public abstract class Person extends Actor {
	
	private static final long serialVersionUID = 1L;

	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String givenName;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String middleName;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String familyName;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String prefix;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String suffix;
	
	@XmlElement(name="nickname", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Collection<String> nicknames;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String jobTitle;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String department;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String officeLocation;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String company;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	String profession;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	TimeZone timeZone;
	
	/**
	 * @$clientQualifier personalSpace
	 * @associates Space
	 * @clientNavigability NAVIGABLE
	 */
	Space personalSpace;
	
	/**
	 * @$clientQualifier presence
	 * @associates Presence
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/presence/201008")
	Presence presence;
	
	/**
	 * @$clientQualifier instantMessageFeed
	 * @associates InstantMessageFeed
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/message/201008")
	InstantMessageFeed instantMessageFeed;

	protected Person() {
		super();
	}

	public Person(Id id, Community parent, Date createdOn) {
		super(id, parent, createdOn);
	}
	
	public Person(Community parent, Date createdOn) {
		super(parent, createdOn);
	}
	
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public Collection<String> getNicknames() {
		return Collections.unmodifiableCollection(nicknames);
	}
	
	public boolean addNickname(String nickname) {
		if (nicknames == null) {
			nicknames = new Vector<String>();
		}
		boolean r = nicknames.add(nickname);
		return r;
	}
	
	public boolean removeNickname(String nickname) {
		boolean r = false;
		if (nicknames != null) {
			r = nicknames.remove(nickname);
		}
		return r;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timezone) {
		this.timeZone = timezone;
	}
	
	public Space getPersonalSpace() {
		return personalSpace;
	}

	public void setPersonalSpace(Space personalSpace) {
		this.personalSpace = personalSpace;
	}
	
	public Presence getPresence() {
		return presence;
	}
	
	public InstantMessageFeed getInstantMessageFeed() {
		return instantMessageFeed;
	}
	
}
