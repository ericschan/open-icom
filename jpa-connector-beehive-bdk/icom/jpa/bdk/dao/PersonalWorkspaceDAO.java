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
package icom.jpa.bdk.dao;

import icom.info.EntityInfo;
import icom.jpa.Identifiable;
import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.bdk.Projection;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.EntityCreator;
import com.oracle.beehive.PersonalWorkspace;
import com.oracle.beehive.PersonalWorkspaceCreator;
import com.oracle.beehive.PersonalWorkspaceUpdater;

public class PersonalWorkspaceDAO extends WorkspaceDAO {
	
	static PersonalWorkspaceDAO singleton = new PersonalWorkspaceDAO();
	
	public static PersonalWorkspaceDAO getInstance() {
		return singleton;
	}
	
	protected PersonalWorkspaceDAO() {	
	}

	public String getResourceType() {
		return "wspr";
	}
		
	public void copyObjectState(ManagedObjectProxy managedObj, Object bdkEntity, Projection proj) {
		ManagedIdentifiableProxy obj = (ManagedIdentifiableProxy) managedObj;
		super.copyObjectState(obj, bdkEntity, proj);
	}

	public void updateObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateObjectState(obj, context);
	}

	public void updateNewObjectState(ManagedIdentifiableProxy obj, DAOContext context) {
		super.updateNewObjectState(obj, context);
		
		PersonalWorkspaceCreator creator = (PersonalWorkspaceCreator) context.getCreator();
		Identifiable pojoIdentifiable = obj.getPojoIdentifiable();
		String name = (String) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.name.name());
		creator.setName(name);
		Identifiable pojoParent = (Identifiable) getAttributeValue(pojoIdentifiable, EntityInfo.Attributes.parent.name());
		if (pojoParent != null) {
			BeeId parentHandle = getBeeId(pojoParent.getObjectId().getObjectId().toString());
			creator.setParent(parentHandle);
		}
		
	}

	protected Class<?> getBdkClass(ManagedObjectProxy obj) {
		return PersonalWorkspace.class;
	}
	
	protected PersonalWorkspaceUpdater getBdkUpdater(ManagedObjectProxy obj) {
		return new PersonalWorkspaceUpdater();
	}
	
	protected PersonalWorkspaceUpdater getBdkUpdater(ManagedObjectProxy obj, EntityCreator creator) {
		PersonalWorkspaceUpdater updater = getBdkUpdater(obj);
		((PersonalWorkspaceCreator)creator).setUpdater(updater);
		return updater;
	}
	
	protected PersonalWorkspaceCreator getBdkCreator(ManagedObjectProxy obj) {
		return new PersonalWorkspaceCreator();
	}
	
}
