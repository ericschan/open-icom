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

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

public class JtaTransactionBridge {
	
	static Map<String, String> knownJNDIManagers = new HashMap<String, String>();
	static {
		knownJNDIManagers.put("WebLogic", "javax.transaction.TransactionManager");
		knownJNDIManagers.put("OC4J", "java:comp/TransactionManager");
		knownJNDIManagers.put("Glassfish", "java:appserver/TransactionManager");
		knownJNDIManagers.put("Resin 3.x", "java:comp/TransactionManager");
		knownJNDIManagers.put("JBoss", "java:/TransactionManager");
		knownJNDIManagers.put("JRun4", "java:/TransactionManager");
		knownJNDIManagers.put("Borland", "java:pm/TransactionManager");
	}

	public static final String JNDI_TRANSACTION_MANAGER_NAME = knownJNDIManagers.get("WebLogic");

	//Primary point of integration with JTA 
    TransactionManager transactionManager;
    
    public JtaTransactionBridge() {
        try {
            transactionManager = acquireTransactionManager();
        } catch (Exception ex) {
            throw new TransactionException(ex);
        }
    }
    
    /**
     * Return the transaction manager used to control the JTA transactions.
     *
     * @return The JTA TransactionManager that is used to obtain transaction
     * state information and control the active transaction.
     */
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }
    
    /**
     * Obtain and return the JTA TransactionManager on this platform
     */
    protected TransactionManager acquireTransactionManager() throws Exception {
        return (TransactionManager) jndiLookup(JNDI_TRANSACTION_MANAGER_NAME);
    }
    
    /**
     * Look up a given name in JNDI. This is used to look up transaction manager 
     * in the J2EE platform, such as the OC4J.
     * The lookup is supposed to take place in the container, so that the
     * InitialContext can be used without requiring any special properties.
     *
     * @param jndiName The name to look up
     * @return The object bound in JNDI to the specified name
     * @exception TransactionException Thrown in case of lookup failure
     */
    public Object jndiLookup(String jndiName) {
        Context context = null;
        Object jndiObject = null;
        try {
            context = new InitialContext();
            jndiObject = context.lookup(jndiName);
        } catch (NamingException ex) {
            throw new TransactionException(ex);
        } finally {
            try {
                context.close();
            } catch (Exception ex) {
            	/* ignore */
            }
        }
        return jndiObject;
    }
    
    Transaction getJtaTransaction() {
    	try {
    		return transactionManager.getTransaction();
    	} catch (SystemException ex) {
    		throw new TransactionException(ex);
    	}
    }
    
    void registerListener(JtaSynchronizationListener listener) {
    	try {
    		transactionManager.getTransaction().registerSynchronization(listener);
    	} catch (SystemException ex) {
    		throw new TransactionException(ex);
    	} catch (RollbackException ex) {
    		throw new TransactionException(ex);
    	}
    }
    
    protected void beginTransaction() {
    	try {
    		getTransactionManager().begin();
    	} catch (SystemException ex) {
    		throw new TransactionException(ex);
    	} catch (NotSupportedException ex) {
    		throw new TransactionException(ex);
    	}
    }

    /**
     * Commit the external transaction.
     */
    protected void commitTransaction() {
        try {
    		getTransactionManager().commit();
    	} catch (SystemException ex) {
    		throw new TransactionException(ex);
    	} catch (HeuristicRollbackException ex) {
    		throw new TransactionException(ex);
    	} catch (HeuristicMixedException ex) {
    		throw new TransactionException(ex);
    	} catch (RollbackException ex) {
    		throw new TransactionException(ex);
    	}
    }

    /**
     * INTERNAL:
     * Roll back the external transaction.
     */
    protected void rollbackTransaction() {
    	try {
    		getTransactionManager().rollback();
    	} catch (SystemException ex) {
    		throw new TransactionException(ex);
    	}
    }

    /**
     * Mark the external transaction for rollback.
     */
    protected void markTransactionForRollback() {
    	try {
    		getTransactionManager().setRollbackOnly();
    	} catch (SystemException ex) {
    		throw new TransactionException(ex);
    	}
    }
    
}
