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
@XmlType(name="Forum", namespace="http://docs.oasis-open.org/ns/icom/forum/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/forum/201008")
public class Forum extends Folder implements TopicContainer {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @$clientQualifier lastPost
	 * @associates Discussion
	 * @clientNavigability NAVIGABLE
	 */
	@OneToOne
	@XmlElement(type=DiscussionMessage.class)
	Discussion lastPost;
	
	/**
	 * @$clientQualifier forum
	 * @associates Forum
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Forum.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="forum")
	List<Forum> forums;
	
	/**
	 * @$clientQualifier topic
	 * @associates Topic
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Topic.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="topic")
	List<Topic> topics;
	
	/**
	 * @$clientQualifier announcement
	 * @associates Announcement
	 * @link aggregationByValue
	 */
	@OneToMany(targetEntity=Announcement.class, 
			   cascade={CascadeType.PERSIST}, 
			   mappedBy="parent")
	@DeferLoadOnAddRemove
	@XmlElement(name="announcement")
	List<Announcement> announcements;

	Forum() {
		super();
	}
	
	public Forum(Id id, FolderContainer parent, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn, userCreatedOn);
	}
	
	public Forum(FolderContainer parent, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn, userCreatedOn);
		forums = new Vector<Forum>();
		topics = new Vector<Topic>();
		announcements = new Vector<Announcement>();
	}
	
	public Discussion getLastPost() {
		return lastPost;
	}
	
	protected void setLastPost(Discussion discussion) {
		lastPost = discussion;
		FolderContainer myParent = getParent();
		if (myParent instanceof Forum) {
			((Forum)myParent).setLastPost(discussion);
		}
	}
	
	public Collection<Forum> getForums() {
		if (forums != null) {
			return Collections.unmodifiableCollection(new ArrayList<Forum>(forums));
		} else {
			return null;
		}
	}
	
	boolean addForum(Forum forum) {
		boolean r = true;
		if (forums != null) {
			r = forums.add(forum);
		}
		return r;
	}
	
	boolean removeForum(Forum forum) {
		boolean r = true;
		if (forums != null) {
			r = forums.remove(forum);
		}
		return r;
	}
	
	public Collection<Topic> getTopics() {
		if (topics != null) {
			return Collections.unmodifiableCollection(new ArrayList<Topic>(topics));
		} else {
			return null;
		}
	}
	
	boolean addTopic(Topic topic) {
		boolean r = true;
		if (topics != null) {
			r = topics.add(topic);
		}
		return r;
	}
	
	boolean removeTopic(Topic topic) {
		boolean r = true;
		if (topics != null) {
			r = topics.remove(topic);
		}
		return r;
	}
	
	public Collection<Announcement> getAnnouncements() {
		if (announcements != null) {
			return Collections.unmodifiableCollection(new ArrayList<Announcement>(announcements));
		} else {
			return null;
		}
	}
	
	boolean addAnnouncement(Announcement announcement) {
		boolean r = true;
		if (announcements != null) {
			r = announcements.add(announcement);
		}
		return r;
	}
	
	boolean removeAnnouncement(Announcement announcement) {
		boolean r = true;
		if (announcements != null) {
			r = announcements.remove(announcement);
		}
		return r;
	}
	
	public Collection<? extends Item> getElements() {
		int size = 0;
		Collection<Forum> forums = getForums();
		if (forums != null) {
			size += forums.size();
		}
		Collection<Topic> topics = getTopics();
		if (topics != null) {
			size += topics.size();
		}
		Collection<Announcement> announcements = getAnnouncements();
		if (announcements != null) {
			size += announcements.size();
		}
		Collection<Item> elements = new ArrayList<Item>(size);
                if (forums != null) {
                    elements.addAll(forums);
                }
                if (topics != null) {
                    elements.addAll(topics);
                }
                if (announcements != null) {
                    elements.addAll(announcements);
                }
		return Collections.unmodifiableCollection(elements);
	}
	
}
