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

import icom.AccessType;
import icom.Id;
import icom.RoleDefinition;
import icom.Scope;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Entity
@XmlType(name="BeehiveRoleDefinition", namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/beehive/201008")
public class BeehiveRoleDefinition extends RoleDefinition {
	
    @XmlElement(name="grantedAccessType", type=BeehiveAccessTypeEnum.class)
	Set<AccessType> grantedAccessTypes;
	
    @XmlElement(name="deniedAccessType", type=BeehiveAccessTypeEnum.class)
	Set<AccessType> deniedAccessTypes;
	
	Boolean alwaysEnabled;
	
	private static final long serialVersionUID = 1L;
	
	BeehiveRoleDefinition() {
		super();
	}
	
	public BeehiveRoleDefinition(Id id, Scope parent, Date createdOn) {
		super(id, parent, createdOn);
	}
	
	public BeehiveRoleDefinition(Scope parent, Date createdOn) {
		super(parent, createdOn);
	}
	
	public Set<AccessType> getGrantedAccessTypes() {
		if (grantedAccessTypes != null) {
			return Collections.unmodifiableSet(grantedAccessTypes);
		} else {
			return null;
		}
	}
	
	public void setGrantedAccessTypes(Set<AccessType> accessTypes) {
		this.grantedAccessTypes = new HashSet<AccessType>(accessTypes);
	}
	
	public boolean addGrantedAccessType(AccessType accessType) {
		if (grantedAccessTypes == null) {
			grantedAccessTypes = new HashSet<AccessType>();
		}
		boolean r = grantedAccessTypes.add(accessType);
		return r;
	}
	
	public boolean removeGrantedAccessType(AccessType accessType) {
		if (grantedAccessTypes == null) {
			return false;
		}
		boolean r = grantedAccessTypes.remove(accessType);
		return r;
	}
	
	public Set<AccessType> getDeniedAccessTypes() {
		if (deniedAccessTypes != null) {
			return Collections.unmodifiableSet(deniedAccessTypes);
		} else {
			return null;
		}
	}
	
	public void setDeniedAccessTypes(Set<AccessType> accessTypes) {
		this.deniedAccessTypes = new HashSet<AccessType>(accessTypes);
	}
	
	public boolean addDeniedAccessType(AccessType accessType) {
		if (deniedAccessTypes == null) {
			deniedAccessTypes = new HashSet<AccessType>();
		}
		boolean r = deniedAccessTypes.add(accessType);
		return r;
	}
	
	public boolean removeDeniedAccessType(AccessType accessType) {
		if (deniedAccessTypes == null) {
			return false;
		}
		boolean r = deniedAccessTypes.remove(accessType);
		return r;
	}

	public Boolean isAlwaysEnabled() {
		return alwaysEnabled;
	}

	public void setAlwaysEnabled(Boolean alwaysEnabled) {
		this.alwaysEnabled = alwaysEnabled;
	}
	
}
