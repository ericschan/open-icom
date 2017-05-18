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

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;


/**
 * Interface implemented by a persistence provider.
 * The implementation of this interface that is to
 * be used for a given {@link javax.persistence.EntityManager} 
 * is specified in persistence.xml file in the persistence archive.
 * This interface is invoked by the Container when it
 * needs to create an {@link javax.persistence.EntityManagerFactory}, 
 * or by the Persistence class when running outside the Container.
 */
public class EntityManagerFactoryProvider implements PersistenceProvider {
	
	public EntityManagerFactoryProvider() {      
		
    }

	/**
     * Called by the container when an 
     * {@link javax.persistence.EntityManagerFactory}
     * is to be created.
     *
     * @param info Metadata for use by the persistence provider
     * @param map A Map of integration-level properties for use
     * by the persistence provider. Can be null if there is no
     * integration-level property.
     * @return EntityManagerFactory for the persistence unit
     * specified by the metadata
     */
	public EntityManagerFactory createContainerEntityManagerFactory(
			PersistenceUnitInfo info, Map map) {
		return new EntityManagerFactoryImpl(info, map);
	}

	/**
	 * Called by Persistence class when an 
	 * {@link javax.persistence.EntityManagerFactory}
	 * is to be created.
	 *
	 * @param emName The name of the persistence unit
	 * @param map A Map of properties for use by the
	 * persistence provider. These properties may be used to
	 * override the values of the corresponding elements in
	 * the persistence.xml file or specify values for
	 * properties not specified in the persistence.xml.
	 * @return EntityManagerFactory for the persistence unit,
	 * or null if the provider is not the right provider
	 */
	public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		return new EntityManagerFactoryImpl(emName, map);
	}

}