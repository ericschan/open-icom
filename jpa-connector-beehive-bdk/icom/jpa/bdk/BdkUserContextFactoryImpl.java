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

import icom.jpa.dao.UserContextFactory;
import icom.jpa.rt.PersistenceContextImpl;
import icom.jpa.rt.UserContext;

import java.lang.ref.WeakReference;
import java.util.Map;

public class BdkUserContextFactoryImpl implements UserContextFactory {
	
	String host;
	int port;
	String protocol;
    UserContext userContext; 
	
	BdkUserContextFactoryImpl(Map<Object, Object>  properties) {
		this.host = (String) properties.get("Host");
		String port = (String) properties.get("Port");
		this.port = Integer.parseInt(port);
		this.protocol = (String) properties.get("Protocol");
	}
	
	public UserContext createUserContext(PersistenceContextImpl context, String username, char[] password) throws Exception {
		BdkUserContextImpl userContext = new BdkUserContextImpl(context, host, port, protocol, username, password);
		return userContext;
	}
	
	public UserContext createUserContext(PersistenceContextImpl context, 
            String username, char[] password, Map<Object, Object> properties) throws Exception {
	    String host = (String) properties.get("Host");
	    if (host == null || host.length() == 0) {
	        host = this.host;
	    }
	    int port = this.port;
	    String portStr = (String) properties.get("Port");
	    if (portStr != null && portStr.length() != 0) {
	        port = Integer.valueOf(portStr);
        }
	    String protocol = (String) properties.get("Protocol");
	    if (protocol == null || protocol.length() == 0) {
	        protocol = this.protocol;
        }
	    BdkUserContextImpl userContext = new BdkUserContextImpl(context, host, port, protocol, username, password);
        return userContext;
	}

	public void attachUserContext(UserContext context) throws Exception {
		this.userContext = context;
	}

	public void detachUserContext() throws Exception {
		 this.userContext = null;
	}

	public UserContext getUserContext() throws Exception {
		return userContext;
	}

}
