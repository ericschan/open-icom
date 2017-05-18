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

import icom.annotation.DeferLoadOnAddRemove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="Topic", namespace="http://docs.oasis-open.org/ns/icom/forum/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/forum/201008")
public class Topic extends Folder implements DiscussionContainer {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier firstPost
	 * @associates Discussion
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	@XmlElement(type=DiscussionMessage.class)
	Discussion firstPost;
	
	/**
	 * @$clientQualifier lastPost
	 * @associates Discussion
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	@XmlElement(type=DiscussionMessage.class)
	Discussion lastPost;
	
	/**
	 * @$clientQualifier element
	 * @associates Discussion
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Discussion.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="element", type=DiscussionMessage.class, 
	        namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	List<Discussion> elements;

	Topic() {
		super();
	}
	
	public Topic(Id id, Forum parent, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn, userCreatedOn);
	}
	
	public Topic(Forum parent, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn, userCreatedOn);
		elements = new Vector<Discussion>();
	}
	
	public Discussion getFirstPost() {
		return firstPost;
	}
	
	public Discussion getLastPost() {
		return lastPost;
	}

	public Collection<Discussion> getElements() {
		if (elements != null) {
			return Collections.unmodifiableCollection(new ArrayList<Discussion>(elements));
		} else {
			return null;
		}
	}
	
	boolean addElement(Discussion discussion) {
		boolean r = true;
		if (elements != null) {
			r = elements.add(discussion);
			if (r) {
				lastPost = discussion;
				FolderContainer myParent = getParent();
				if (myParent instanceof Forum) {
					((Forum)myParent).setLastPost(discussion);
				}
			}
		}
		return r;
	}

	boolean removeElement(Discussion discussion) {
		boolean r = true;
		if (elements != null) {
			r = elements.remove(discussion);
			if (r) {
				if (firstPost == discussion) {
					firstPost = null;
				}
				if (lastPost == discussion) {
					lastPost = null;
				}
			}
		}
		return r;
	}

}
