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

import icom.jpa.dao.DataAccessStateObject;

import com.oracle.beehive.BeeId;
import com.oracle.beehive.IdentifiableSnapshot;

public class BdkDataAccessIdentifiableStateObject extends DataAccessStateObject {
	
	IdentifiableSnapshot bdkIdentifiable;
	
	String mnemonic = null;
	
	private BdkDataAccessIdentifiableStateObject() {
		
	}
	
	public BdkDataAccessIdentifiableStateObject(IdentifiableSnapshot bdkIdentifiable) {
		this();
		this.bdkIdentifiable = bdkIdentifiable;
	}
	
	public BdkDataAccessIdentifiableStateObject(IdentifiableSnapshot bdkIdentifiable, String mnemonic) {
		this(bdkIdentifiable);
		this.mnemonic = mnemonic;
	}
	
	public String getObjectId() {
		BeeId id = bdkIdentifiable.getCollabId();
		if (mnemonic != null) {
			String idstr = id.getId().toString();
			char[] idchars = idstr.toCharArray();
			char[] mnechars = mnemonic.toCharArray();
			// change xxxx in 0123:5678:xxxx:FGHIJKLMNOPQRSTUVWXYZABCDEFGHIJK000000000000
			idchars[10] = mnechars[0];
			idchars[11] = mnechars[1];
			idchars[12] = mnechars[2];
			idchars[13] = mnechars[3];
			String newidstr = new String(idchars);
			return newidstr;
		}
		return id.getId().toString();
	}
	
	public Class<?> getProviderClass() {
		return bdkIdentifiable.getClass();
	}

}
