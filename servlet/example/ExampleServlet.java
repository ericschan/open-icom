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
package example;

import icom.Id;
import icom.Space;
import icom.Person;
import icom.session.FacadeLocal;
import icom.session.Icom;
import icom.session.SessionLocal;
import icom.session.UserContext;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExampleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@EJB FacadeLocal iCOMFacade;
	
	FacadeLocal icomFacade;
		
	SessionLocal icomSession;
	
	public FacadeLocal lookupSessionFacade() {
		FacadeLocal icomFacade = null;
		try {
            Context context = new InitialContext();
            icomFacade = (FacadeLocal) context.lookup("java:comp/env/iCOMFacade");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
		return icomFacade;
	}
	
	public SessionLocal lookupSessionLocal() {
		SessionLocal icomSession = null;
		try {
            Context context = new InitialContext();
            icomSession = (SessionLocal) context.lookup("java:comp/env/ejb/iCOMSession");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
		return icomSession;
	}
	
	UserContext getUserContext(Icom icom, HttpServletRequest request, HttpServletResponse response) {
	    try {
    	    String pseudonym = request.getParameter("pseudonym");
    	    String key = request.getParameter("key");
    	    CredentialStore cs = new CredentialStore();
    	    Credential c = cs.load(pseudonym, key);
    	    
    	    Map<Object, Object> properties = new HashMap<Object, Object>();
    	    properties.put("Host", c.hostName);
    	    UserContext uc = icom.setupUserContext(c.userName, c.password, properties);
    	    return uc;
	    } catch (Exception ex) {
	        throw new RuntimeException(ex);
	    }
	}
	
	Space getPersonalSpace(Icom icom, UserContext uc) {
	    Id actorid = uc.getActorId();
	    Person user = icom.find(Person.class, actorid);
	    Space space = user.getPersonalSpace();
	    return space;
	}
	
	public String getHtmlHead() {
        StringBuffer out = new StringBuffer();
        out.append("<html>");
        out.append("<head>");
        out.append("<title>ICOM Servlet</title>");
        out.append("<body>");
        out.append("<h3>Servlet Access to ICOM Entity Manager</h3>");
        out.append("<div><p>");
        return out.toString();
    }
    
    public String getHtmlTail() {
        StringBuffer out = new StringBuffer();
        out.append("</p></div>");
        out.append("</body></html>");
        return out.toString();
    }
	
    public void welcome(HttpServletResponse response, Space space) {
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println(getHtmlHead());
            out.print("Welcome to " + space.getName());
            out.println(getHtmlTail());
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		icomSession = lookupSessionLocal();
		icomFacade = lookupSessionFacade();
		UserContext uc1 = getUserContext(icomSession, request, response);
		Space space1 = getPersonalSpace(icomSession, uc1);
		welcome(response, space1);
		UserContext uc2 = getUserContext(icomFacade, request, response);
        Space space2 = getPersonalSpace(icomFacade, uc2);
        welcome(response, space2);
	}

}

