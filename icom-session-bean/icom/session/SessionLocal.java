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
import icom.Identifiable;
import icom.UnifiedMessage;
import icom.Version;
import icom.Versionable;

import java.util.Date;

import javax.ejb.Local;
import javax.persistence.Query;


@Local
public interface SessionLocal extends Icom {
	
	public void beginTransaction();
	
	public void setTransactionForRollbackOnly();
	
	public void clearEntityManager();
	
	public void commit();
	
	public void setupUserContext();
	
	public UserContext getUserContext();
	
	public void detachUserContext();
	
	public Query createQuery(String query);
        
    public Actor getActor();
	
	public void send(UnifiedMessage message, HeterogeneousFolder sentFolder);
	
	public void sendDispositionNotification(UnifiedMessage message);
	
	public void sendNotReadDispositionNotification(UnifiedMessage message);
	
	public Version checkout(Versionable representativeCopyOfVersionable, String checkoutComments);
	
	public Version checkin(Versionable privateWorkingCopyOfVersionable, String versionName);
	
	public void cancelCheckout(Versionable representativeCopyOfVersionable);
	
	public FreeBusy loadFreeBusyOfActor(icom.jpa.Identifiable actor, Date startDate, Date endDate);
	
	public void refresh(Identifiable object);
	
	public void prepareToDetach(Entity entity, boolean full);
	
	public Extent getSelectedContainer();

	public void setSelectedContainer(Extent container);

}