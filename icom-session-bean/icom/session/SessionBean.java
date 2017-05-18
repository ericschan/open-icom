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

import icom.Actor;
import icom.Entity;
import icom.Extent;
import icom.FreeBusy;
import icom.HeterogeneousFolder;
import icom.Id;
import icom.Identifiable;
import icom.UnifiedMessage;
import icom.Version;
import icom.VersionTypeEnum;
import icom.Versionable;

import icom.jpa.rt.LifeCycleManager;
import icom.jpa.rt.ServiceManager;
import icom.jpa.rt.TransactionManager;
import icom.jpa.rt.UserContextManager;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;


@Stateful(name="iCOMSession")
@TransactionManagement(TransactionManagementType.BEAN)
public class SessionBean implements Session, SessionLocal {
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager bdkEntityManager;
	
	@Resource 
	SessionContext ctx;
	
	static boolean useJTABridge = true;
	
	static int transactionTimeout = 600; // seconds
	
	UserContext userContext;
	
	Extent selectedContainer;
	
	public <T> T find(Class<T> entityClass, Id id) {
		return bdkEntityManager.find(entityClass, id);
	}
	
	public <T> T getReference(Class<T> entityClass, Id id) {
		return bdkEntityManager.getReference(entityClass, id);
	}
	
	public void persist(Entity entity) {
		beginTransaction();
		bdkEntityManager.persist(entity);
	}
	
	public void beginTransaction() {
		if (useJTABridge) {
			try {
				if (ctx.getUserTransaction().getStatus() == javax.transaction.Status.STATUS_NO_TRANSACTION) {
					ctx.getUserTransaction().setTransactionTimeout(transactionTimeout);
					ctx.getUserTransaction().begin();
					bdkEntityManager.joinTransaction();
				}
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void clearEntityManager() {
		try {
			bdkEntityManager.clear();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public void setTransactionForRollbackOnly() {
		try {
			if (useJTABridge) {
				ctx.getUserTransaction().setRollbackOnly();
			} else {
				TransactionManager tm = (TransactionManager) bdkEntityManager.getDelegate();
				EntityTransaction et = tm.getEntityTransaction();
				et.setRollbackOnly();
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public void commit() {
		try {
			beginTransaction();
			if (useJTABridge) {
				ctx.getUserTransaction().commit();
			} else {
				TransactionManager tm = (TransactionManager) bdkEntityManager.getDelegate();
				EntityTransaction et = tm.getEntityTransaction();
				et.commit();
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void detachUserContext() {
		try {
			UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
			pc.detachUserContext();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public UserContext setupUserContext(String username, char[] password) {
		try {
			UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
			if (userContext == null) {
				icom.jpa.rt.UserContext ctx = pc.createUserContext(username, password);
				pc.attachUserContext(ctx);
				userContext = new UserContext(ctx);
			} else {
				pc.attachUserContext(userContext.ctx);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return userContext;
	}
	
    public UserContext setupUserContext(String username, char[] password, Map<Object, Object> properties) {
        try {
            UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
            if (userContext == null) {
                icom.jpa.rt.UserContext ctx = pc.createUserContext(username, password, properties);
                pc.attachUserContext(ctx);
                userContext = new UserContext(ctx);
            } else {
                pc.attachUserContext(userContext.ctx);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return userContext;
    }
	
	public void setupUserContext() {
		if (userContext != null) {
			UserContextManager pc = (UserContextManager) bdkEntityManager.getDelegate();
			try {
				pc.attachUserContext(userContext.ctx);
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public UserContext getUserContext() {
		return userContext;
	}
        
    public Actor getActor() {
        UserContext uc = getUserContext();
        Id actorid = uc.getActorId();
        Actor actor = find(Actor.class, actorid);
        return actor;
    }
	
	public Query createQuery(String query) {
		return bdkEntityManager.createQuery(query);
	}
	
	public void send(UnifiedMessage message, HeterogeneousFolder sentFolder) {
		ServiceManager sm = (ServiceManager) bdkEntityManager.getDelegate();
		icom.Service service = sm.getService();
		service.sendEmail(message, sentFolder);
	}
	
	public void sendDispositionNotification(UnifiedMessage message) {
		ServiceManager sm = (ServiceManager) bdkEntityManager.getDelegate();
		icom.Service service = sm.getService();
		service.sendDispositionNotification(message);
	}
	
	public void sendNotReadDispositionNotification(UnifiedMessage message) {
		ServiceManager sm = (ServiceManager) bdkEntityManager.getDelegate();
		icom.Service service = sm.getService();
		service.sendNotReadDispositionNotification(message);
	}
	
	public Version checkout(Versionable representativeCopyOfVersionable, String checkoutComments) {
		if (! (representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.RepresentativeCopy
				|| representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.NonVersionControlledCopy)) {
			throw new IllegalArgumentException("Check out argument must be representative copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) bdkEntityManager.getDelegate();
		icom.Service service = sm.getService();
		return (Version) service.checkoutRepresentativeCopyOfVersionable(representativeCopyOfVersionable, checkoutComments);
	}
	
	public Version checkin(Versionable privateWorkingCopyOfVersionable, String versionName) {
		if (! (privateWorkingCopyOfVersionable.getVersionType() == VersionTypeEnum.PrivateWorkingCopy)) {
			throw new IllegalArgumentException("Checkin argument must be private working copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) bdkEntityManager.getDelegate();
		icom.Service service = sm.getService();
		return (Version) service.checkinPrivateWorkingCopyOfVersionable(privateWorkingCopyOfVersionable, versionName);
	}
	
	public void cancelCheckout(Versionable representativeCopyOfVersionable) {
		if (! (representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.RepresentativeCopy)) {
			throw new IllegalArgumentException("Cancel check out argument must be representative copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) bdkEntityManager.getDelegate();
		icom.Service service = sm.getService();
		service.cancelCheckout(representativeCopyOfVersionable);
	}
	
	public FreeBusy loadFreeBusyOfActor(icom.jpa.Identifiable actor, Date startDate, Date endDate) {
		ServiceManager sm = (ServiceManager) bdkEntityManager.getDelegate();
		icom.Service service = sm.getService();
		return (FreeBusy) service.loadFreeBusyOfActor(actor, startDate, endDate);
	}
	
	public void refresh(Identifiable object) {
		bdkEntityManager.refresh(object);
	}
	
	public void prepareToDetach(Entity entity, boolean full) {
		LifeCycleManager lm = (LifeCycleManager) bdkEntityManager.getDelegate();
		lm.prepareToDetach(entity, full);
	}

	public Extent getSelectedContainer() {
		if (selectedContainer != null) {
			Id id = selectedContainer.getId();
			selectedContainer = getReference(Extent.class, id);
		}
		return selectedContainer;
	}

	public void setSelectedContainer(Extent container) {
		this.selectedContainer = container;
	}
	
}
