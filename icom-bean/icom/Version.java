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

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Version", namespace="http://docs.oasis-open.org/ns/icom/document/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/document/201008")
public class Version extends Entity implements RelationshipBondable, VersionControlMetadata {
	
	private static final long serialVersionUID = 1L;

	String checkinComment;
	
	Integer versionNumber;
  
	String versionLabel;

	Boolean majorVersion;
	
	/**
	 * @$clientQualifier representativeCopy
	 * @associates Versionable
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	@XmlElement(type=Document.class)
	Versionable representativeCopy;
	
	/**
	 * @$clientQualifier versionedOrPrivateWorkingCopy
	 * @associates Versionable
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	@XmlElement(type=Document.class)
	Versionable versionedOrPrivateWorkingCopy;

	/**
	 * @$clientQualifier relationship
	 * @associates Relationship
	 * @link aggregation
	 */
	@ManyToMany(targetEntity=Relationship.class)
	@XmlElement(name="relationship", namespace="http://docs.oasis-open.org/ns/icom/metadata/201008")
	Set<Relationship> relationships;
	
	Version() {
		super();
	}
	
	public Version(Id id, Versionable parent, Date createdOn) {
		super(id, parent, createdOn);
	}
	
	public Version(Versionable parent, Date createdOn) throws IllegalArgumentException {
		super(parent, createdOn);
		if (parent instanceof Document) {
			((Document)parent).setVersionControlMetadata(this);
		}
		this.versionedOrPrivateWorkingCopy = parent;
	}
	
	public String getCheckinComment() {
		return checkinComment;
	}
	
	public void setCheckinComment(String checkinComment) {
		this.checkinComment = checkinComment;
	}
	
	public Integer getVersionNumber() {
		return versionNumber;
	}
	
	public String getVersionLabel() {
		return versionLabel;
	}
	
	public void setVersionLabel(String versionLabel) {
		this.versionLabel = versionLabel;
	}
	
	public Boolean getMajorVersion() {
		return majorVersion;
	}
	
	public Versionable getRepresentativeCopy() {
		return representativeCopy;
	}
	
	public Versionable getVersionedOrPrivateWorkingCopy() {
		return versionedOrPrivateWorkingCopy;
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
