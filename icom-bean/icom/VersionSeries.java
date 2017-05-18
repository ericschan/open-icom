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

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="VersionSeries", namespace="http://docs.oasis-open.org/ns/icom/document/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/document/201008")
public class VersionSeries extends Entity implements RelationshipBondable, VersionControlMetadata {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier versionHistory
	 * @associates Version
	 * @link aggregation
	 */
	@OneToMany
	@XmlElement(name="versionHistory")
	Collection<Version> versionHistory;
	
	/**
	 * @$clientQualifier versionableHistory
	 * @associates Versionable
	 * @link aggregation
	 */
	@OneToMany
	@XmlElement(name="versionableHistory", type=Document.class)
	Collection<Versionable> versionableHistory;
	
	/**
	 * @$clientQualifier latestVersion
	 * @associates Version
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	Version latestVersion;
	
	/**
	 * @$clientQualifier latestVersionedCopy
	 * @associates Versionable
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	@XmlElement(type=Document.class)
	Versionable latestVersionedCopy;
	
	/**
	 * @$clientQualifier privateWorkingCopy
	 * @associates Versionable
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	@XmlElement(type=Document.class)
	Versionable privateWorkingCopy;
	
	/**
	 * @$clientQualifier representativeCopy
	 * @associates Versionable
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	@XmlElement(type=Document.class)
	Versionable representativeCopy;
	
	Boolean versionSeriesCheckedOut;

	/**
	 * @$clientQualifier versionSeriesCheckedOutBy
	 * @associates Actor
	 */
	@ManyToOne
	Actor versionSeriesCheckedOutBy;
	  
	Date versionSeriesCheckedOutOn;
	
	String versionSeriesCheckoutComment;
	
	Long totalSize;

	/**
	 * @$clientQualifier relationship
	 * @associates Relationship
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Relationship.class)
	@XmlElement(name="relationship", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<Relationship> relationships;
	
	VersionSeries() {
		super();
	}
	
	public Collection<Version> getVersionHistory() {
		if (versionHistory != null) {
			return Collections.unmodifiableCollection(new ArrayList<Version>(versionHistory));
		} else {
			return null;
		}
	}
	
	public Collection<Versionable> getVersionableHistory() {
		if (versionableHistory != null) {
			return Collections.unmodifiableCollection(new ArrayList<Versionable>(versionableHistory));
		} else {
			return null;
		}
	}

	public Version getLatestVersion() {
		return latestVersion;
	}
	
	public Versionable getLatestVersionedCopy() {
		return latestVersionedCopy;
	}
	
	public Versionable getPrivateWorkingCopy() {
		return privateWorkingCopy;
	}
	
	public Versionable getRepresentativeCopy() {
		return representativeCopy;
	}
	
	public Boolean isVersionSeriesCheckedOut() {
		return versionSeriesCheckedOut;
	}
	
	public Actor getVersionSeriesCheckedOutBy() {
		return versionSeriesCheckedOutBy;
	}
	
	public Date getVersionSeriesCheckedOutOn() {
		return versionSeriesCheckedOutOn;
	}

	public String getVersionSeriesCheckoutComment() {
		return versionSeriesCheckoutComment;
	}

	public Long getTotalSize() {
		return totalSize;
	}
	
	public Set<Relationship> getRelationships() {
		if (relationships != null) {
			return Collections.unmodifiableSet(new HashSet<Relationship>(relationships));
		} else {
			return null;
		}
	}
	
	boolean addRelationship(Relationship relationship) {
		boolean r = false;
		if (relationships != null) {
			r = relationships.add(relationship);
		}
		return r;
	}
	
	boolean removeRelationship(Relationship relationship) {
		boolean r = false;
		if (relationship != null) {
			r = relationships.remove(relationship);
		}
		return r;
	}
	  
}
