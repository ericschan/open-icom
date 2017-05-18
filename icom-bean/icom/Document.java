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

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Document", namespace="http://docs.oasis-open.org/ns/icom/document/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/document/201008")
public class Document extends Artifact implements MimeConvertible, Versionable {

	/**
	 * @$clientQualifier content
	 * @associates Content
	 * @clientNavigability NAVIGABLE
	 */
	@Embedded
	Content content;
	
	/**
	 * @$clientQualifier versionControlMetadata
	 * @associates VersionControlMetadata
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	@XmlElement(name="versionControlMetadata", type=Object.class)
	VersionControlMetadata versionControlMetadata;
	
	/**
	 * @$clientQualifier versionType
	 * @associates VersionType
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=VersionTypeEnum.class)
	VersionType versionType;  
	
	Long size;
	
	private static final long serialVersionUID = 1L;

	Document() {
		super();
	}

	public Document(Id id, Container parent, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn, userCreatedOn);
	}
	
	public Document(Container parent, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn, userCreatedOn);
		versionType = VersionTypeEnum.NonVersionControlledCopy;
	}
	
	public Document(Date createdOn, Date userCreatedOn) {
	    this(null, createdOn, userCreatedOn);
	}

	public Content getContent() {
		return content;
	}
	
	public void setContent(Content content) {
		if (getContent() != null) {
			throw new RuntimeException("Cannot replace content");
		}
		this.content = content;
	}
	
	public VersionControlMetadata getVersionControlMetadata() {
		return versionControlMetadata;
	}
	
	void setVersionControlMetadata(Version version) throws IllegalArgumentException {
		if (getVersionType() != VersionTypeEnum.NonVersionControlledCopy || getVersionControlMetadata() != null) {
			throw new IllegalArgumentException("Cannot set a version node in a document " +
					"that already has version control metadata");
		}
		this.versionControlMetadata = version;
	}
	
	public Long getSize() {
		return size;
	}

	public VersionType getVersionType() {
		return versionType;
	}
	
	public boolean isEditable() {
		VersionType versionType = getVersionType();
		return    (versionType == null 
				|| versionType == VersionTypeEnum.NonVersionControlledCopy 
				|| versionType == VersionTypeEnum.PrivateWorkingCopy);
	}

	public Document clone() {
		Document clone = (Document) super.clone();
		clone.description = description;;
		if (content != null) {
			clone.content = content.clone();
		}
		clone.versionType = VersionTypeEnum.NonVersionControlledCopy;
		return clone;
	}

}
