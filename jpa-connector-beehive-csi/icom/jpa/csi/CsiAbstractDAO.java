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
package icom.jpa.csi;

import icom.info.BeanHandler;

import icom.jpa.ManagedObjectProxy;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.rt.PersistenceContext;

import oracle.csi.BomTypeId;
import oracle.csi.CollabId;
import oracle.csi.Identifiable;
import oracle.csi.SnapshotId;
import oracle.csi.controls.IdFactory;
import oracle.csi.projections.Projection;

import oracle.ocs.cspi.controls.OcsIdFactory;


public abstract class CsiAbstractDAO extends AbstractDAO {
	
	public Projection load(ManagedObjectProxy obj, String attributeName) {
		return null;
	}
	
	public Projection load(ManagedObjectProxy obj, String attributeName, Object key) {
		return null;
	}
	
	public Projection loadFull(ManagedObjectProxy obj) {
		return null;
	}

	public boolean isPartOfProjection(String attributeName, Projection proj) {
		return false;
	}
	
	public boolean isPartOfProjection(String attributeName, Object proj) {
		return isPartOfProjection(attributeName, (Projection) proj);
	}
	
	public abstract void copyObjectState(ManagedObjectProxy obj, Object stateObject, Projection proj);
	
	public void copyObjectStateForAProjection(ManagedObjectProxy obj, Object stateObject, Object proj) {
		copyObjectState(obj, stateObject, (Projection) proj);
	}
	
	public CollabId createCollabId() {
		String bomTypeName = getBomTypeName();
		if (bomTypeName != null) {
			CollabId id = OcsIdFactory.getInstance().generateNewCollabId(BomTypeId.lookup(bomTypeName));
			return id;
		} else {
			return null;
		}
	}
	
	public String createObjectId(PersistenceContext context) {
		String bomTypeName = getBomTypeName();
		if (bomTypeName != null) {
			CollabId id = OcsIdFactory.getInstance().generateNewCollabId(BomTypeId.lookup(bomTypeName));
			return id.toString();
		} else {
			return null;
		}
	}
	
	public String getBomTypeName() {
		throw new RuntimeException("cannot determine bom type for non identifiable objects");
	}
	
	public CollabId getCollabId(Object id) {
		CollabId collabId = CollabId.parseCollabId(id.toString());
		return collabId;
	}
	
	public SnapshotId getSnapshotId(Object changeToken) {
		SnapshotId snapshotId = IdFactory.getInstance().parseSnapshotId(changeToken.toString());
		return snapshotId;
	}
	
	public Object getPropertyValue(Object pojoObject, String attributeName) {
		Object value = super.getPropertyValue(pojoObject, attributeName);
		if (BeanHandler.isInstanceOfObjectIdType(value)) {
			Object id = BeanHandler.getObjectId(value);
			CollabId collabId = CollabId.parseCollabId(id.toString());
			return collabId;
		}
		return value;
	}
	
	public void assignPropertyValue(Object pojoObject, String attributeName, Object value) {
		if (value instanceof CollabId) {
			value = BeanHandler.constructId(((CollabId)value).toString());
		}
		super.assignPropertyValue(pojoObject, attributeName, value);
	}
	
	public boolean isCacheable() {
		return false;
	}
	
	public boolean embedAsNonIdentifiableDependent() {
		return false;
	}
	
	protected DataAccessStateObject wrapDataAccessStateObject(Object state) {
		if (state instanceof oracle.csi.Identifiable) {
			return new CsiDataAccessIdentifiableStateObject((oracle.csi.Identifiable) state);
		} else {
			return new CsiDataAccessNonIdentifiableStateObject(state);
		}
	}
        
        protected String getProviderObjectId(Object csiObject) {
            Identifiable bdkEntity = (Identifiable) csiObject;
            return bdkEntity.getCollabId().toString();
        }
	
}
