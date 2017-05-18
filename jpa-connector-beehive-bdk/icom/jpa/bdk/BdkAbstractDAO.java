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
package icom.jpa.bdk;


import com.oracle.beehive.BeeId;
import com.oracle.beehive.Entity;
import com.oracle.beehive.IdentifiableSnapshot;
import com.oracle.beehive.ListResult;
import com.oracle.beehive.Predicate;
import com.oracle.beehive.PredicateAndSortListParameters;

import icom.info.BeanHandler;

import icom.jpa.ManagedObjectProxy;
import icom.jpa.dao.AbstractDAO;
import icom.jpa.dao.DataAccessStateObject;
import icom.jpa.rt.PersistenceContext;

import java.util.List;
import java.util.UUID;

import javax.persistence.PersistenceException;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;


public abstract class BdkAbstractDAO extends AbstractDAO {

	static protected BdkHttpUtil bdkHttpUtil = BdkHttpUtil.getInstance();
	
	static int QUERY_RESULT_COUNT_LIMIT = 1000;
	
	private static final long MAX_SUB_ID = 0xffffffffffffL;
	
	private Object syncObj = new Object();
	private UUID uuid = UUID.randomUUID();
	private long sequenceNumber = 0;
	
	static final char[] HEX_DIGITS_UPPER = new char[]
	   { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	public final String generateObjectId() {
		long high64Bits;
		long middle64Bits;
		long low48Bits;
		synchronized (syncObj) {
			if (sequenceNumber == MAX_SUB_ID) {
				sequenceNumber = 0;
				uuid = UUID.randomUUID();
			}
			high64Bits = uuid.getMostSignificantBits();
			middle64Bits = uuid.getLeastSignificantBits();
			low48Bits = sequenceNumber++;
		}
		char[] buffer = new char[44];
		longToHexBuffer(high64Bits, buffer, 0);
		longToHexBuffer(middle64Bits, buffer, 16);
		sixByteLongToHexBuffer(low48Bits, buffer, 32);
		String id = new String(buffer);
		return id;
	}

	static final void longToHexBuffer(final long l, final char[] buffer, final int pos) {
		int x = (int) (l >>> 32);
		buffer[pos] = HEX_DIGITS_UPPER[(int) ((x & 0xf0000000) >>> 28)];
		buffer[pos + 1] = HEX_DIGITS_UPPER[(int) ((x & 0x0f000000) >>> 24)];
		buffer[pos + 2] = HEX_DIGITS_UPPER[(int) ((x & 0x00f00000) >>> 20)];
		buffer[pos + 3] = HEX_DIGITS_UPPER[(int) ((x & 0x000f0000) >>> 16)];
		buffer[pos + 4] = HEX_DIGITS_UPPER[(int) ((x & 0x0000f000) >>> 12)];
		buffer[pos + 5] = HEX_DIGITS_UPPER[(int) ((x & 0x00000f00) >>> 8)];
		buffer[pos + 6] = HEX_DIGITS_UPPER[(int) ((x & 0x000000f0) >>> 4)];
		buffer[pos + 7] = HEX_DIGITS_UPPER[(int) ((x & 0x0000000f) >>> 0)];
		x = (int) l & 0xffffffff;
		buffer[pos + 8] = HEX_DIGITS_UPPER[(int) ((x & 0xf0000000) >>> 28)];
		buffer[pos + 9] = HEX_DIGITS_UPPER[(int) ((x & 0x0f000000) >>> 24)];
		buffer[pos + 10] = HEX_DIGITS_UPPER[(int) ((x & 0x00f00000) >>> 20)];
		buffer[pos + 11] = HEX_DIGITS_UPPER[(int) ((x & 0x000f0000) >>> 16)];
		buffer[pos + 12] = HEX_DIGITS_UPPER[(int) ((x & 0x0000f000) >>> 12)];
		buffer[pos + 13] = HEX_DIGITS_UPPER[(int) ((x & 0x00000f00) >>> 8)];
		buffer[pos + 14] = HEX_DIGITS_UPPER[(int) ((x & 0x000000f0) >>> 4)];
		buffer[pos + 15] = HEX_DIGITS_UPPER[(int) ((x & 0x0000000f) >>> 0)];
	}

	static final void sixByteLongToHexBuffer(final long l, final char[] buffer,	final int pos) {
		int x = (int) (l >>> 32);
		buffer[pos + 0] = HEX_DIGITS_UPPER[(int) ((x & 0x0000f000) >>> 12)];
		buffer[pos + 1] = HEX_DIGITS_UPPER[(int) ((x & 0x00000f00) >>> 8)];
		buffer[pos + 2] = HEX_DIGITS_UPPER[(int) ((x & 0x000000f0) >>> 4)];
		buffer[pos + 3] = HEX_DIGITS_UPPER[(int) ((x & 0x0000000f) >>> 0)];
		x = (int) l & 0xffffffff;
		buffer[pos + 4] = HEX_DIGITS_UPPER[(int) ((x & 0xf0000000) >>> 28)];
		buffer[pos + 5] = HEX_DIGITS_UPPER[(int) ((x & 0x0f000000) >>> 24)];
		buffer[pos + 6] = HEX_DIGITS_UPPER[(int) ((x & 0x00f00000) >>> 20)];
		buffer[pos + 7] = HEX_DIGITS_UPPER[(int) ((x & 0x000f0000) >>> 16)];
		buffer[pos + 8] = HEX_DIGITS_UPPER[(int) ((x & 0x0000f000) >>> 12)];
		buffer[pos + 9] = HEX_DIGITS_UPPER[(int) ((x & 0x00000f00) >>> 8)];
		buffer[pos + 10] = HEX_DIGITS_UPPER[(int) ((x & 0x000000f0) >>> 4)];
		buffer[pos + 11] = HEX_DIGITS_UPPER[(int) ((x & 0x0000000f) >>> 0)];
	}
	
	protected final String generateUUID() {
		synchronized (syncObj) {
			UUID uuid = UUID.randomUUID();
			return uuid.toString();
		}
	}
	
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
	
	public String createObjectId(PersistenceContext context) {
		String resourceType = getResourceType();
		if (resourceType != null) {
			String hexId = generateObjectId();
			BdkDataAccessUtilsImpl bdkDataAccessUtils = (BdkDataAccessUtilsImpl) ((PersistenceContext)context).getDataAccessUtils();
			String enterpriseId = bdkDataAccessUtils.getEnterpriseId();
			String siteId = bdkDataAccessUtils.getSiteId();
			return enterpriseId + ":" + siteId + ":" + resourceType + ":" + hexId;
		} else {
			return null;
		}
	}
	
	public String getResourceType() {
		throw new RuntimeException("cannot determine bom type for non identifiable objects");
	}
	
	public BeeId getBeeId(String collabId) {
		return BdkDataAccessUtilsImpl.getBeeId(collabId);
	}
	
	public Object getPropertyValue(Object pojoObject, String attributeName) {
		Object value = super.getPropertyValue(pojoObject, attributeName);
		if (BeanHandler.isInstanceOfObjectIdType(value)) {
			Object id = BeanHandler.getObjectId(value);
			BeeId beeId =  getBeeId(id.toString());
			return beeId;
		}
		return value;
	}
	
	public void assignPropertyValue(Object pojoObject, String attributeName, Object value) {
		if (value instanceof BeeId) {
			value = BeanHandler.constructId(((BeeId)value).getId());
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
		if (state instanceof IdentifiableSnapshot) {
			return new BdkDataAccessIdentifiableStateObject((IdentifiableSnapshot) state);
		} else {
			return new BdkDataAccessNonIdentifiableStateObject(state);
		}
	}
	
	// example query "/comb/v1/d/user/{collabId}?projection={projection}"
	static protected GetMethod prepareGetMethod(String resource, String collabId, Projection projection, String params) {
		String query = "/comb/v1/d/" + resource;
		if (collabId != null) {
			query += "/" + collabId;
		}
		if (projection != null) {
			query += "?projection=" + projection.name();
		}
		if (params != null) {
			if (projection == null) {
				query += "?" + params;
			} else {
				query += "&" + params;
			}
		}
		return new GetMethod(query);
	}
	
	static protected GetMethod prepareGetMethod(String resource, String collabId, Projection projection) {
		return prepareGetMethod(resource, collabId, projection, null);
	}
	
	static protected GetMethod prepareGetMethod(String resource, String collabId) {
		return prepareGetMethod(resource, collabId, null, null);
	}
	
	static protected PutMethod preparePutMethod(String resource, String collabId, String antiCSRF, Projection projection, String params) {
		String query = "/comb/v1/d/" + resource + "/" + collabId;
		if (antiCSRF != null && projection != null) {
			query += "?antiCSRF=" + antiCSRF + "&projection=" + projection.name();
		} else if (antiCSRF != null) {
			query += "?antiCSRF=" + antiCSRF;
		} else if (projection != null) {
			query += "?projection=" + projection.name();
		}
		if (params != null) {
			if (antiCSRF == null && projection == null) {
				query += "?" + params;
			} else {
				query += "&" + params;
			}
		}
		return new PutMethod(query);
	}
	
	static protected PutMethod preparePutMethod(String resource, String collabId, String antiCSRF, Projection projection) {
		return preparePutMethod(resource, collabId, antiCSRF, projection, null);
	}
	
	static protected PutMethod preparePutMethod(String resource, String antiCSRF, Projection projection, String params) {
		String query = "/comb/v1/d/" + resource;
		if (antiCSRF != null && projection != null) {
			query += "?antiCSRF=" + antiCSRF + "&projection=" + projection.name();
		} else if (antiCSRF != null) {
			query += "?antiCSRF=" + antiCSRF;
		} else if (projection != null) {
			query += "?projection=" + projection.name();
		}
		if (params != null) {
			if (antiCSRF == null && projection == null) {
				query += "?" + params;
			} else {
				query += "&" + params;
			}
		}
		return new PutMethod(query);
	}
	
	static protected PutMethod preparePutMethod(String resource, String antiCSRF, Projection projection) {
		return preparePutMethod(resource, antiCSRF, projection, null);
	}
	
	static protected PostMethod preparePostMethod(String resource, String antiCSRF, String params) {
		String query = "/comb/v1/d/" + resource;
		if (antiCSRF != null) {
			query += "?antiCSRF=" + antiCSRF;
		}
		if (params != null) {
			if (antiCSRF == null) {
				query += "?" + params;
			} else {
				query += "&" + params;
			}
		}
		return new PostMethod(query);
	}
	
	static protected PostMethod preparePostMethod(String resource, String collabId, String antiCSRF, Projection projection, String params) {
		String query = "/comb/v1/d/" + resource;
		if (collabId != null) {
			query += "/" + collabId;
		}
		if (antiCSRF != null && projection != null) {
			query += "?antiCSRF=" + antiCSRF + "&projection=" + projection.name();
		} else if (antiCSRF != null) {
			query += "?antiCSRF=" + antiCSRF;
		} else if (projection != null) {
			query += "?projection=" + projection.name();
		}
		if (params != null) {
			if (antiCSRF == null && projection == null) {
				query += "?" + params;
			} else {
				query += "&" + params;
			}
		}
		return new PostMethod(query);
	}
	
	static protected PostMethod preparePostMethod(String resource, String collabId, String antiCSRF, Projection projection) {
		return preparePostMethod(resource, collabId, antiCSRF, projection, null);
	}
	
	static public PostMethod preparePostMethod(String resource, String antiCSRF, Projection projection, String params) {
		return preparePostMethod(resource, null, antiCSRF, projection, params);
	}
	
	static protected PostMethod prepareListPostMethod(String resource, String resourceId, String antiCSRF, Projection projection, String params) {
		String query = "/comb/v1/d/" + resource + "/list";
		if (resourceId != null) {
			query += "/" + resourceId;
		}
		if (antiCSRF != null && projection != null) {
			query += "?antiCSRF=" + antiCSRF + "&projection=" + projection.name();
		} else if (antiCSRF != null) {
			query += "?antiCSRF=" + antiCSRF;
		} else if (projection != null) {
			query += "?projection=" + projection.name();
		}
		if (params != null) {
			if (antiCSRF == null && projection == null) {
				query += "?" + params;
			} else {
				query += "&" + params;
			}
		}
		return new PostMethod(query);
	}
	
	static protected PostMethod prepareListPostMethod(String resource, String resourceId, String antiCSRF, Projection projection) {
		return prepareListPostMethod(resource, resourceId, antiCSRF, projection, null);
	}
	
	static protected DeleteMethod prepareDeleteMethod(String resource, String collabId, String antiCSRF) {
		String query = "/comb/v1/d/" + resource + "/" + collabId;
		if (antiCSRF != null) {
			query += "?antiCSRF=" + antiCSRF;
		}
		return new DeleteMethod(query);
	}
	
	static public List<Object> listEntities(PersistenceContext context, Class<?> entityClass, PredicateAndSortListParameters predicateAndSortListParameters, 
			String resourceType, String resourceId, Projection proj, String query) {
		List<Object> bdkEntityList = null;
		try {
			BdkUserContextImpl userContext = (BdkUserContextImpl) context.getUserContext();
			PostMethod method = prepareListPostMethod(resourceType, resourceId, userContext.antiCSRF, proj, query);
			ListResult bdkEntityListResult = (ListResult) bdkHttpUtil.execute(ListResult.class, method, predicateAndSortListParameters, userContext.httpClient);
			bdkEntityList = (List<Object>) bdkEntityListResult.getElements();
		} catch (Exception ex) {
			throw new PersistenceException(ex);
		}
		return bdkEntityList;
	}
	
	static public List<Object> listEntities(PersistenceContext context, Class<?> entityClass, Predicate predicate, String resourceType, Projection proj, String query) {
		PredicateAndSortListParameters predicateAndSortListParameters = new PredicateAndSortListParameters();
		predicateAndSortListParameters.setPredicate(predicate);
		predicateAndSortListParameters.setCountLimit(QUERY_RESULT_COUNT_LIMIT);
		return listEntities(context, entityClass, predicateAndSortListParameters, 
				resourceType, null, proj, query);
	}
	
	static public List<Object> listEntities(PersistenceContext context, Class<?> entityClass, Predicate predicate, String resourceType, Projection proj) {
		return listEntities(context, entityClass, predicate, resourceType, proj, null);
	}
        
    protected String getProviderObjectId(Object bdkObject) {
        Entity bdkEntity = (Entity) bdkObject;
        return bdkEntity.getCollabId().getId();
    }
	
}
