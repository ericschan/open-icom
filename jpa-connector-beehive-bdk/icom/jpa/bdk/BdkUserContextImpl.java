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

import icom.jpa.ManagedIdentifiableProxy;
import icom.jpa.rt.PersistenceContextImpl;
import icom.jpa.rt.UserContext;

import java.net.URLEncoder;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import com.oracle.beehive.OrganizationUser;
import com.oracle.beehive.rest.AntiCSRF;

public class BdkUserContextImpl implements UserContext {
	
	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	
	static BdkHttpUtil bdkHttpUtil = BdkHttpUtil.getInstance();
	
	public HttpClient httpClient;
	public String authUserName;
	public String actorId;
	public String antiCSRF;
	
	BdkUserContextImpl(PersistenceContextImpl context, String host, int port, String protocol, String username, char[] password) throws Exception {
		HttpClient httpClient = new HttpClient(connectionManager);
		Credentials defaultCredentials = new UsernamePasswordCredentials(username, new String(password));
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getHostConfiguration().setHost(host, port, protocol);
		AuthScope authScope = new AuthScope(host, port, AuthScope.ANY_REALM);
		httpClient.getState().setCredentials(authScope, defaultCredentials);
		this.httpClient = httpClient;
		this.authUserName = username;
		OrganizationUser myUser = getMyUser(context);
		actorId = myUser.getCollabId().getId();
		String enterpriseId = actorId.substring(0, 4);
		String siteId = actorId.substring(5, 9);
		BdkDataAccessUtilsImpl bdkDataAccessUtils = (BdkDataAccessUtilsImpl) context.getDataAccessUtils();
		bdkDataAccessUtils.setEnterpriseId(enterpriseId);
		bdkDataAccessUtils.setSiteId(siteId);
		antiCSRF = getAntiCrossSiteRequestForgery();
	}
	
	OrganizationUser getMyUser(PersistenceContextImpl context) throws Exception {
		Projection proj = Projection.BASIC;
		GetMethod getUser = new GetMethod("/comb/v1/d/my/user?projection=" + proj.name().toString());
		OrganizationUser bdkUser = (OrganizationUser) bdkHttpUtil.execute(OrganizationUser.class, getUser, httpClient);
		ManagedIdentifiableProxy userObj = context.getEntityProxy(new BdkDataAccessIdentifiableStateObject(bdkUser));
		userObj.getProviderProxy().copyLoadedProjection(userObj, bdkUser, proj);
		return bdkUser;
	}
	
	String getAntiCrossSiteRequestForgery() throws Exception {
		GetMethod getAntiCsrf = new GetMethod("/comb/v1/d/session/anticsrf");
		AntiCSRF authToken= (AntiCSRF) bdkHttpUtil.execute(AntiCSRF.class, getAntiCsrf, httpClient);
		String antiCSRF = URLEncoder.encode(authToken.getToken(), "UTF-8");
		return antiCSRF;
	}
	
	public void invalidate(String user, String pass) throws Exception {
		httpClient.getState().clearCookies();
	}

	public Object getActorId() {
		return actorId;
	}

}
