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
package icom.jpa.rt;

import icom.weaver.ClassFileTransformerImpl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;

/**
 * The <code>EntityManagerFactoryImpl</code> class is used 
 * by the application to obtain an application-managed entity 
 * manager. When the application has finished using the entity 
 * manager factory, and/or at application shutdown, the 
 * application should close the entity manager factory. 
 * Once an <code>EntityManagerFactoryImpl</code> has been closed, 
 * all its entity managers are considered to be in the closed state.
 */
public class EntityManagerFactoryImpl implements EntityManagerFactory {
	
	protected boolean isOpen = true;
	
	ObjectCacheSystemMonitor monitor;
	
	JtaTransactionBridge jtaTransactionBridge;
	
	String entityManagerName;
	PersistenceUnitInfo info;
	Map<Object, Object> map;
	
	EntityManagerFactoryImpl(PersistenceUnitInfo info, Map<Object, Object> map) {
		monitor = new ObjectCacheSystemMonitor();
		ClassTransformer transformer = new ClassFileTransformerImpl();
		info.addTransformer(transformer);
		this.info = info;
		this.map = map;
		jtaTransactionBridge = new JtaTransactionBridge();
	}
	
	EntityManagerFactoryImpl(String emName,	Map<Object, Object> map) {
		monitor = new ObjectCacheSystemMonitor();
		entityManagerName = emName;
		this.map = map;
		// not invoked by container, do not use jta bridge
		// jtaTransactionBridge = new JtaTransactionBridge(); 
	}

    /**
     * Create a new EntityManager.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     */
    public EntityManager createEntityManager() {
    	if (info != null) {
    		return new EntityManagerImpl(info.getProperties(), map, this);
    	} else {
    		return new EntityManagerImpl(map, this);
    	}
    }

    /**
     * Create a new EntityManager with the specified Map of
     * properties.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     */
    public EntityManager createEntityManager(Map map) {
    	return new EntityManagerImpl(map, this);
    }

    /**
     * Close the factory, releasing any resources that it holds.
     * After a factory instance is closed, all methods invoked on
     * it will throw an IllegalStateException, except for isOpen,
     * which will return false. Once an EntityManagerFactory has
     * been closed, all its entity managers are considered to be
     * in the closed state.
     */
    public void close() {
    	isOpen = false;
    }

    /**
    * Indicates whether or not this factory is open. Returns true
    * until a call to close has been made.
    */
    public boolean isOpen() {
    	return isOpen;
    }
    
    protected void finalize() throws Throwable {
        if (isOpen()) {
            close();
        }
    }
}