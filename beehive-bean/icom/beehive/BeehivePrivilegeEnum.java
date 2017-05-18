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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import icom.Privilege;

@XmlType(name="BeehivePrivilegeEnum", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public enum BeehivePrivilegeEnum implements Privilege {

	/**
	 *  Allows a user to perform address book service level operations.
	 */
	ADDRESS_BOOK_MGR,

    /**
     *  Allows a user to use the address book features.
     */
    ADDRESS_BOOK_USER,
	
	/**
	 *  Allows a user to perform archive service level operations.
	 */
	ARCHIVE_MGR,

	/**
	 * Allows a user to create audit policies and enable/disable auditing on a
	 * specific container or user. A user with AUDIT_ADMIN can create audit trails
	 * by grabbing a set of audit records and archiving them.
	 * <p/>
	 * Users with AUDIT_ADMIN do not have the privilege to actually look at the
	 * audit record as per the security requirement.
	 */
	AUDIT_ADMIN,

	/**
	 * Allows a user to look at and analyze an audit trail and corresponding audit
	 * records created by users with the AUDIT_ADMIN privilege. Usually, an auditor
	 * will be a legal person belonging to the legal department or government
	 * organization.
	 * <p/>
	 * Users with AUDITOR do not have the privilege to create audit policies or
	 * enable/disable auditing.
	 */
	AUDITOR,

	/**
	 * Allows a user full access to all objects in the system and provides unlimited
	 * ability to perform any operation.
	 */
	BYPASS,

	/**
	 * Allows a user to perform calendar service level operations.
	 */
	CALENDAR_MGR,

	/**
	 * Allows a user to use the calendaring features.
	 */
	CALENDAR_USER,

	/**
	 * Allows a user to perform conference service level operations.
	 */
	CONF_MGR,

	/**
	 * Allows a user to use the conferencing features.
	 */
	CONF_USER,

	/**
	 * Allows a user to perform content service level operations.
	 */
	CONTENT_MGR,

	/**
	 * Allows a user to use content features.
	 */
	CONTENT_USER,

	/**
	 * Allows a user to delegate.
	 */
	DELEGATE,

	/**
	 * Allows a user to perform diagnostic activities including reading all log files.
	 */
	DIAGNOSE,

	/**
	 * Allows a user to perform Device Management service level operations.
	 */
	DM_MGR,

	/**
	 * Allows a user to perform email service level operations.
	 */
	EMAIL_MGR,

	/**
	 * Allows a user to use email features.
	 */
	EMAIL_USER,

	/**
	 * Allows a user to exceed any quota barriers that may prevent the user from completing an operation.
	 */
	EXCEED_QUOTA,

	/**
	 * Allows a user to use manage Tacit.
	 */
	EXPERTISE_MGR,
	
	/**
	 * Allows a user to create/update/delete Feed configurations.
	 */
	FEED_MGR,
	
	/**
	 * Allows a user to perform Forum service level operations.
	 */
	FORUM_MGR,
	
	/**
	 * Allows a user to write to Forum without write access
	 */
    FORUM_WRITER,
	
	/**
	 * Allows a user to use the Forum features.
	 */
	FORUM_USER,

	/**
	 * Allows a user to perform IM service level operations.
	 */
	IM_MGR,

	/**
	 * Allows a user to use the IM features.
	 */
	IM_USER,

	/**
	 * Allows a user to log into the system. Minimum privilege needed for a user to use the system non-anonymously.
	 */
	LOGIN,

	/**
	 * Allows a user to perform marker service level operations.
	 */
	MARKER_MGR,

	/**
	 * Allows a user to modify the LACL, Sensitivity and Owner attributes of an object.
	 */
	MODIFY_ACL,

	/**
	 * Allows a user to perform notification service level operations.
	 */
	NOTIFICATION_MGR,

	/**
	 * Allows a user to use notification features.
	 */
	NOTIFICATION_USER,

	/**
	 * Allows a user to manage organizations.
	 */
	ORGANIZATION_MGR,

	/**
	 * Allows a user to create and modify policies.
	 */
	POLICY_MGR,

	/**
	 * Allows a user to manage preferences.
	 */
	PREFERENCE_MGR,

	/**
	 * Allows a user to access the system via the standards-based protocols.
	 */
	PROTOCOL_USER,

	/**
	 * Allows a user to perform quota service level operations.
	 */
	QUOTA_MGR,

	/**
	 * Allows a user to read any object.
	 */
	READALL,

	/**
	 * Allows a user to perform recordization operations.
	 */
	RECORDS_MGR,

	/**
	 * Allows management of resources.
	 */
	RESOURCE_MGR,

	/**
	 * Allows a user to create and modify roles.
	 */
	ROLE_MGR,

	/**
     *  Allows a Service to be Provisioned.
     */
    S2S,
    
	/**
	 * Allows a user to perform any security related operations. Covers MODIFY_ACL.
	 */
	SECURITY,

	/**
	 * Allows a user to modify shared labels.
	 */
	SHARED_LABEL_MODIFIER,

	/**
	 * Allows a user to perform subscription service level operations.
	 */
	SUBSCRIPTION_MGR,

	/**
	 * Allows a user to use subscription features.
	 */
	SUBSCRIPTION_USER,
	
	/**
	 * Allows a user to view system configuration and monitor system performance but does not allow
	 * any operation which changes system behavior.
	 */
	SYSTEM_MONITOR,

	/**
	 * Allows a user to view and alter system configuration and monitor system performance and allows
	 * all operations which can change system behavior.
	 */
	SYSTEM_OPER,

	/**
	 * Allows a user to manage tasks.
	 */
	TASK_MGR,

	/**
	 * Allows a user to use the task features.
	 */
	TASK_USER,

	/**
	 * Allows management of timezones.
	 */
	TIMEZONE_MGR,

	/**
	 * Allows a user to manage users and groups.
	 */
	USER_MGR,

	/**
	 * Allows a user to perform version service level operations.
	 */
	VERSION_MGR,

	/**
	 * Allows a user to use the voice features.
	 */
	VOICE_USER,

	/**
	 * Allows a user to use Beekeeper.
	 */
	WEBADMIN_USER,

	/**
	 * Allows a user to use Wiki.
	 */
	WIKI_USER,

	/**
	 * Allows a user to manage Wikis.
	 */
	WIKI_MGR,

	/**
	 * Allows a user to manage workflows.
	 */
	WORKFLOW_MGR,

	/**
	 * Allows a user to manage tasks associated with workflows.
	 */
	WORKFLOWTASK_MGR,

	/**
	 * Allows a user to add team workspaces.
	 */
	WORKSPACE_ADD,

	/**
	 * Allows a user to manage a workspace.
	 */
	WORKSPACE_MGR;
	
	public String getPrivilege() {
		return this.name();
	}
	
}

