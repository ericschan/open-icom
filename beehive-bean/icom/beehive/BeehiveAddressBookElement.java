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

import icom.Addressable;
import icom.Artifact;
import icom.AttachedItem;
import icom.Container;
import icom.EntityAddress;
import icom.Id;
import icom.IllegalArgumentException;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;

import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="BeehiveAddressBookElement", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveAddressBookElement extends Artifact implements Addressable {

	private static final long serialVersionUID = 1L;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	TimeZone timezone;
	
	/**
	 * @$clientQualifier bookmark
	 * @associates Contactable
	 * @clientNavigability NAVIGABLE
	 */
	@ManyToOne
	@XmlElement(name="bookmark", type=Object.class)
	BeehiveContactable bookmark;
	
	/**
     * @$clientQualifier entityAddress
     * @associates EntityAddress
     * @link aggregationByValue
     */
    @Embedded
    @XmlElement(name="entityAddress", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    Collection<EntityAddress> entityAddresses;
	
	/**
	 * @$clientQualifier primaryAddress
	 * @associates EntityAddress
	 */
	@Embedded
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	EntityAddress primaryAddress;
	
	/**
	 * @$clientQualifier attachment
	 * @associates Attachment
	 * @link aggregationByValue
	 */
	@Embedded
	@XmlElement(name="attachment", namespace="http://docs.oasis-open.org/ns/icom/contact/201008")
	Set<AttachedItem> attachments;

	BeehiveAddressBookElement() {
		super();
	}

	public BeehiveAddressBookElement(Id id, Container parent, BeehiveContactable bookmark, Date createdOn, Date userCreatedOn) {
		super(id, parent, createdOn, userCreatedOn);
		this.bookmark = bookmark;
	}

	public BeehiveAddressBookElement(Container parent, BeehiveContactable bookmark, Date createdOn, Date userCreatedOn) {
		super(parent, createdOn, userCreatedOn);
		this.bookmark = bookmark;
		entityAddresses = new Vector<EntityAddress>();
		attachments = new HashSet<AttachedItem>();
	}
	
	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}

	public BeehiveContactable getBookmark() {
		return bookmark;
	}

	public Collection<EntityAddress> getEntityAddresses() {
        if (entityAddresses != null) {
            return Collections.unmodifiableCollection(new HashSet<EntityAddress>(entityAddresses));
        } else {
            return null;
        }
    }
    
    public boolean addEntityAddress(EntityAddress address) {
        boolean r = false;
        if (entityAddresses != null) {
            r = entityAddresses.add(address);
        }
        return r;
    }
    
    public boolean removeEntityAddress(EntityAddress address) {
        boolean r = false;
        if (entityAddresses != null) {
            r = entityAddresses.remove(address);
        }
        return r;
    }
	
	public EntityAddress getPrimaryAddress() {
		return primaryAddress;
	}
	
	public void setPrimaryAddress(EntityAddress address) throws IllegalArgumentException {
		this.primaryAddress = address;
	}

	
	public Set<AttachedItem> getAttachments() {
		if (attachments != null) {
			return Collections.unmodifiableSet(new HashSet<AttachedItem>(attachments));
		} else {
			return null;
		}
	}
	
	public boolean addAttachment(AttachedItem attachment) {
		if (attachments == null) {
			attachments = new HashSet<AttachedItem>();
		}
		boolean r = attachments.add(attachment);
		return r;
	}
	
	public boolean removeAttachment(AttachedItem attachment) {
		boolean r = false;
		if (attachments != null) {
			r = attachments.remove(attachment);
		}
		return r;
	}
	
}
