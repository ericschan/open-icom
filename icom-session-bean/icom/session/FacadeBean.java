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
package icom.session;

import java.util.Map;

import icom.Entity;
import icom.Id;
import icom.jpa.rt.UserContextManager;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name="iCOMFacade")
public class FacadeBean implements Facade, FacadeLocal {
	
	@PersistenceContext
    private EntityManager bdkEntityManager;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> T find(Class<T> entityClass, Id id) {
		return bdkEntityManager.find(entityClass, id);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void persist(Entity entity) {
		bdkEntityManager.persist(entity);
	}
	
	public UserContext setupUserContext(String username, char[] password) {
		UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
		UserContext uc = null;
		try {
			icom.jpa.rt.UserContext ctx = pc.createUserContext(username, password);
			pc.attachUserContext(ctx);
			uc = new UserContext(ctx);
		} catch (Throwable ex) {
		    ex.printStackTrace();
		}
		return uc;
	}
	
	public UserContext setupUserContext(String username, char[] password, Map<Object, Object> properties) {
	    UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
        UserContext uc = null;
        try {
            icom.jpa.rt.UserContext ctx = pc.createUserContext(username, password, properties);
            pc.attachUserContext(ctx);
            uc = new UserContext(ctx);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return uc;
	}

	public void setupUserContext(UserContext uc) {
		UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
		try {
			pc.attachUserContext(uc.ctx);
		} catch (Throwable ex) {
		    ex.printStackTrace();
		}
	}
	
	public UserContext getUserContext() {
		UserContext uc = null;
		UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
		try {
			icom.jpa.rt.UserContext ctx = pc.getUserContext();
			if (ctx != null) {
				uc = new UserContext(ctx);
			}
		} catch (Throwable ex) {
		    ex.printStackTrace();
		}
		return uc;
	}
	
	public void signOn() {
		UserContext uc = getUserContext();
		if (uc == null) {
			String u = "jsmith";
			String p = "Welcome1";
			setupUserContext(u, p.toCharArray());
		}
	}
	
}