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

import icom.Actor;
import icom.Entity;
import icom.Extent;
import icom.FreeBusy;
import icom.HeterogeneousFolder;
import icom.Id;
import icom.Person;
import icom.Space;
import icom.UnifiedMessage;
import icom.Version;
import icom.VersionTypeEnum;
import icom.Versionable;
import icom.jpa.rt.LifeCycleManager;
import icom.jpa.rt.ServiceManager;
import icom.jpa.rt.UserContext;
import icom.jpa.rt.UserContextManager;

import java.util.Date;

import javax.persistence.EntityManager;


public class Session {
	
	EntityManager manager;
	UserContext userContext;
	Extent extent;
	
	public Session(EntityManager manager) {
		this.manager = manager;
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public void setupUserContext(String username, char[] password) {
		try {
			UserContextManager pc = (UserContextManager) manager.getDelegate();
			if (userContext == null) {
				userContext = pc.createUserContext(username, password);
				pc.attachUserContext(userContext);
			} else {
				pc.attachUserContext(userContext);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public Actor getActor() {
		Id id = new Id(userContext.getActorId().toString());
		Actor actor = manager.find(Actor.class, id);
		return actor;
	}
	
	public Space getPersonalSpace() {
		Id id = new Id(userContext.getActorId().toString());
		Actor actor = manager.find(Actor.class, id);
		Space space = ((Person)actor).getPersonalSpace();
		return space;
	}
	
	public Extent getExtent() {
		if (extent == null) {
			return getPersonalSpace();
		}
		return extent;
	}

	public void setExtent(Extent extent) {
		this.extent = extent;
	}

	public void prepareToDetach(Entity entity, boolean full) {
		LifeCycleManager lm = (LifeCycleManager) manager.getDelegate();
		lm.prepareToDetach(entity, full);
	}

	public void send(UnifiedMessage message, HeterogeneousFolder sentFolder) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		icom.Service service = sm.getService();
		service.sendEmail(message, sentFolder);
	}
	
	public void sendDispositionNotification(UnifiedMessage message) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		icom.Service service = sm.getService();
		service.sendDispositionNotification(message);
	}
	
	public void sendNotReadDispositionNotification(UnifiedMessage message) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		icom.Service service = sm.getService();
		service.sendNotReadDispositionNotification(message);
	}
	
	public Version checkout(Versionable representativeCopyOfVersionable, String checkoutComments) {
		if (! (representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.RepresentativeCopy
				|| representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.NonVersionControlledCopy)) {
			throw new IllegalArgumentException("Check out argument must be representative copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		icom.Service service = sm.getService();
		return (Version) service.checkoutRepresentativeCopyOfVersionable(representativeCopyOfVersionable, checkoutComments);
	}
	
	public Version checkin(Versionable privateWorkingCopyOfVersionable, String versionName) {
		if (! (privateWorkingCopyOfVersionable.getVersionType() == VersionTypeEnum.PrivateWorkingCopy)) {
			throw new IllegalArgumentException("Checkin argument must be private working copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		icom.Service service = sm.getService();
		return (Version) service.checkinPrivateWorkingCopyOfVersionable(privateWorkingCopyOfVersionable, versionName);
	}
	
	public void cancelCheckout(Versionable representativeCopyOfVersionable) {
		if (! (representativeCopyOfVersionable.getVersionType() == VersionTypeEnum.RepresentativeCopy)) {
			throw new IllegalArgumentException("Cancel check out argument must be representative copy of versionable artifact");
		}
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		icom.Service service = sm.getService();
		service.cancelCheckout(representativeCopyOfVersionable);
	}
	
	public FreeBusy loadFreeBusyOfActor(Actor actor, Date startDate, Date endDate) {
		ServiceManager sm = (ServiceManager) manager.getDelegate();
		icom.Service service = sm.getService();
		return (FreeBusy) service.loadFreeBusyOfActor(actor, startDate, endDate);
	}
	

}
