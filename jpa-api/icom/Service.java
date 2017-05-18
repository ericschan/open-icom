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
package icom;

import icom.jpa.Manageable;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;
import icom.jpa.rt.PersistenceContext;

import java.util.Date;

public class Service {
	
	static Service singleton = new Service();
	
	public static Service getInstance() {
		return singleton;
	}
	
	public void sendEmail(PersistenceContext context, Persistent message, Persistent sentFolder) {
	    context.registerNewEntityForPersist((Manageable) message);
        context.getDataAccessUtils().sendEmail(message, sentFolder);
    }
	
	public void sendEmail(Object message, Object sentFolder) {
	    ManagedObject mo = (ManagedObject) message;
	    ManagedObjectProxy obj = mo.getManagedObjectProxy();
	    if (obj == null) {
	        if (sentFolder != null) {
	            obj = ((ManagedObject)sentFolder).getManagedObjectProxy();
	        } else {
	            throw new RuntimeException("Cannot locate PersistentContext for this operation");
	        }
	    }
	    if (obj != null) {
	        PersistenceContext context = obj.getPersistenceContext();
	        context.getDataAccessUtils().sendEmail((Persistent)message, (Persistent)sentFolder);
	    }
	}
	
	public void sendDispositionNotification(Object message) {
		ManagedObjectProxy obj = ((ManagedObject)message).getManagedObjectProxy();
		if (obj != null) {
			PersistenceContext context = obj.getPersistenceContext();
			context.getDataAccessUtils().sendDispositionNotification((Persistent)message);
		}
	}
	
	public void sendNotReadDispositionNotification(Object message) {
		ManagedObjectProxy obj = ((ManagedObject)message).getManagedObjectProxy();
		if (obj != null) {
			PersistenceContext context = obj.getPersistenceContext();
			context.getDataAccessUtils().sendNotReadDispositionNotification((Persistent)message);
		}
	}
	
	// checks out using a representative copy of versionable artifact and returns a version node of private working copy
	public Object checkoutRepresentativeCopyOfVersionable(Object representativeCopyOfVersionable, String checkoutComments) {
	    Persistent version = null;
		ManagedObjectProxy obj = ((ManagedObject)representativeCopyOfVersionable).getManagedObjectProxy();
		if (obj != null) {
			PersistenceContext context = obj.getPersistenceContext();
			version = context.getDataAccessUtils().checkoutRepresentativeCopyOfVersionable((Persistent)representativeCopyOfVersionable, checkoutComments);
		}
		return version;
	}
	
	// checks in a private working copy of versionable artifact and returns version node of versioned copy
	// the client must get the versioned copy from the version node
	public Object checkinPrivateWorkingCopyOfVersionable(Object privateWorkingCopyOfVersionable, String versionName) {
		Persistent version = null;
		ManagedObjectProxy obj = ((ManagedObject)privateWorkingCopyOfVersionable).getManagedObjectProxy();
		if (obj != null) {
			PersistenceContext context = obj.getPersistenceContext();
			version = context.getDataAccessUtils().checkinPrivateWorkingCopyOfVersionable((Persistent)privateWorkingCopyOfVersionable, versionName);
		}
		return version;
	}
	
	public void cancelCheckout(Object representativeCopyOfVersionable) {
		ManagedObjectProxy obj = ((ManagedObject)representativeCopyOfVersionable).getManagedObjectProxy();
		if (obj != null) {
			PersistenceContext context = obj.getPersistenceContext();
			context.getDataAccessUtils().cancelCheckout((Persistent)representativeCopyOfVersionable);
		}
	}
	
	public Object loadFreeBusyOfActor(Object actor, Date startDate, Date endDate) {
		Object pojoFreeBusy = null;
		ManagedObjectProxy obj = ((ManagedObject)actor).getManagedObjectProxy();
		if (obj != null) {
			PersistenceContext context = obj.getPersistenceContext();
			pojoFreeBusy = context.getDataAccessUtils().loadFreeBusyOfActor((Persistent)actor, startDate, endDate);
		}
		return pojoFreeBusy;
	}

}
