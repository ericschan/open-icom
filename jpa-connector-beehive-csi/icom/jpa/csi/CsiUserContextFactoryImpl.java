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

import java.util.Map;

import icom.jpa.dao.UserContextFactory;
import icom.jpa.rt.PersistenceContextImpl;
import icom.jpa.rt.UserContext;
import oracle.csi.AuthenticationToken;
import oracle.csi.UserContextException;
import oracle.ocs.csi.authentication.AuthenticationHandlerFactory;
import oracle.ocs.csi.authentication.framework.PasswordAuthenticationHandler;
import oracle.ocs.csi.authentication.framework.protocol.ProtocolAuthentication;
import oracle.ocs.csi.impl.OcsJaasPrincipal;
import oracle.ocs.csi.impl.util.OcsUserContextUtil;
import oracle.ocs.cspi.Password;

public class CsiUserContextFactoryImpl implements UserContextFactory {
	
	public UserContext createUserContext(PersistenceContextImpl context, String username,
			char[] password) throws Exception {
		PasswordAuthenticationHandler handler = (PasswordAuthenticationHandler) AuthenticationHandlerFactory
				.getAuthenticationHandler(ProtocolAuthentication.PASSWORD);
		Password pazzword = new Password(password);
		try {
			while (!handler.isDone()) {
				if (handler.isClientDataNeeded()) {
					handler.pushData(username, pazzword);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pazzword.destroy();
		}
		AuthenticationToken authToken = handler.getAuthenticationToken();

		if ((authToken != null) && authToken.isValid()) {
			oracle.csi.controls.UserContext csiUserContext = oracle.csi.controls.UserContext.getInstance();
			try {
				csiUserContext.enablePrincipal(authToken);
				OcsJaasPrincipal ojp = OcsUserContextUtil.copy();
				return new CsiUserContextImpl(ojp);
			} catch (UserContextException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		} else {
			throw new Exception("Authentication Failed");
		}

	}
	
	public UserContext createUserContext(PersistenceContextImpl context, 
            String username, char[] password, Map<Object, Object> properties) throws Exception {
	    return createUserContext(context, username, password);
	}
	
	public UserContext getUserContext() throws Exception {
		try {
			OcsJaasPrincipal ojp = OcsUserContextUtil.copy();
			return new CsiUserContextImpl(ojp);
		} catch (UserContextException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	public void attachUserContext(UserContext context) throws Exception {
		try {
			OcsUserContextUtil.attach(((CsiUserContextImpl)context).getJaasPrincipal());
		} catch (UserContextException e) {
			throw new Exception("UserContextException" + e);
		}
	}

	public void detachUserContext() throws Exception {
		try {
			OcsUserContextUtil.detach();
		} catch (UserContextException e) {
			throw new Exception("UserContextException: detach: " + e);
		}
	}
	
	/*
	public icom.jpa.dao.UserContext activatePrincipal(Object objectId) throws Exception {
		CollabId collabId = CollabId.parseCollabId(objectId.toString());
  		PrincipalHandle principalHandle = (PrincipalHandle) EntityUtils.getInstance().createHandle(collabId);
  		try {
  			UserContextUtilFactory.getInstance().setupUserContext(principalHandle);
  			return getUserContext();
  		} catch (CsiException ex) {
  			throw ex;
  		}
  	}
  	*/

}
