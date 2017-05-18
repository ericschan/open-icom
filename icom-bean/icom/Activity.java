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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@javax.persistence.Entity
@XmlType(name="Activity", namespace="http://docs.oasis-open.org/ns/icom/presence/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/presence/201008")
public class Activity implements Identifiable {

	private static final long serialVersionUID = 1L;
	
    @javax.persistence.EmbeddedId
    @XmlElement(name="objectId", namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    protected Id id;

	@javax.persistence.Version
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
    protected ChangeToken changeToken;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Date startDate;
	
	@XmlElement(namespace="http://docs.oasis-open.org/ns/icom/core/201008")
	Date endDate;
	
	/**
	 * @$clientQualifier activityType
	 * @associates ActivityType
	 * @clientNavigability NAVIGABLE
	 */
	@XmlElement(type=ActivityTypeEnum.class)
	ActivityType activityType;
	  
	String note;
	
	/**
	 * @$clientQualifier reference
	 * @associates Entity
	 * @clientNavigability NAVIGABLE
	 */
	Entity reference;
	
	Activity() {
		super();
	}

	public Activity(ActivityType activityType, Date startDate, Date endDate) {
		super();
		this.activityType = activityType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Activity(ActivityType activityType, Date startDate, Date endDate, String note, Entity reference) {
		super();
		this.activityType = activityType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.note = note;
		this.reference = reference;
	}
	
	public Id getId() {
        return id;
    }
    
    public ChangeToken getChangeToken() {
        return changeToken;
    }

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public String getNote() {
		return note;
	}

	public Entity getReference() {
		return reference;
	}
	
}
