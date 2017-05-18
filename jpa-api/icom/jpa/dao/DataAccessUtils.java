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
package icom.jpa.dao;

import icom.jpa.Manageable;
import icom.jpa.Persistent;
import icom.jpa.rt.PersistenceContext;
import icom.ql.QueryContext;

import java.net.URI;
import java.util.Date;
import java.util.List;

public interface DataAccessUtils {
	
	public List<Object> resolveResultList(QueryContext queryContext, PersistenceContext context, List<Object> resultList);
	
	public Object resolveAttributeValueEntity(PersistenceContext context, Object value);
	
	public Object resolveAttributeValueEntityAddress(PersistenceContext context, URI uri, String addressType);
	
	public Object resolveAttributeValueEntityAddress(PersistenceContext context, Object value);
	
	public Object resolveAttributeValueParticipant(PersistenceContext context, Object value);
		
	public Object resolveAttributeValueType(PersistenceContext context, Object value, Object type);
	
	public Object parseObjectId(Object id);
	
	public boolean exists(PersistenceContext context, Object id);

	public Manageable find(PersistenceContext context, Object id);
	
	public Manageable getReference(PersistenceContext context, Object id);
	
	public DataAccessObject getUnknownEntityDAO();
	
	public DataAccessObject getUnknownIdentifiableDAO();
	
	public void sendEmail(Persistent message, Persistent sentFolder);
	
	public void sendDispositionNotification(Persistent message);
	
	public void sendNotReadDispositionNotification(Persistent message);
	
	//public Persistent checkoutVersionSeries(Persistent versionSeries, String checkoutComments);
	
	// checks out using a representative copy of versionable artifact and returns a version node
	// the client must get the private working copy from the version node
	public Persistent checkoutRepresentativeCopyOfVersionable(Persistent representativeCopyOfVersionable, String checkoutComments);
	
	//public Persistent checkinVersion(Persistent version);
	
	// checks in a private working copy of versionable artifact and returns a version node
	// the client must get the versioned copy from the version node
	public Persistent checkinPrivateWorkingCopyOfVersionable(Persistent privateWorkingCopyOfVersionable, String versionName);

	public void cancelCheckout(Persistent representativeCopyOfVersionable);
	
	public Object loadFreeBusyOfActor(Persistent actor, Date startDate, Date endDate);
}
