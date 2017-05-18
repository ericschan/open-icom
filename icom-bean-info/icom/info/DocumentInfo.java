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
package icom.info;

import icom.jpa.Identifiable;
import icom.jpa.ManagedObjectProxy;
import icom.jpa.Persistent;

public class DocumentInfo extends ArtifactInfo implements MimeConvertibleInfo, StreamInfo {

	static DocumentInfo singleton = new DocumentInfo();
	
	public static DocumentInfo getInstance() {
		return singleton;
	}
	
	public enum Attributes {
		content,
		size,
		versionControlMetadata,
		versionType
	}

	protected DocumentInfo() {
		
	}
	
	public int getClassOrdinal() {
		return IcomBeanEnumeration.Document.ordinal();
	}
	
	{
		deletionPredecessors.add(Attributes.versionControlMetadata.name());
	}
	
	{
		referencedObjects.add(Attributes.size.name());
		referencedObjects.add(Attributes.versionControlMetadata.name());
		referencedObjects.add(Attributes.versionType.name());
	}

	{
		embeddedObjects.add(Attributes.content.name());
	}
	
	{
		inverseOfOwnedProperties.remove(EntityInfo.Attributes.categoryApplications.name());
		inverseOfOwnedProperties.remove(EntityInfo.Attributes.tagApplications.name());
	}
	
	public Persistent getContent(Persistent pojoIdentifiable) {
		return (Identifiable) getAttributeValue(pojoIdentifiable, Attributes.content.name());
	}
	
	public void prepareDetachableState(ManagedObjectProxy mop) {
		super.prepareDetachableState(mop);
		Persistent pojoDocument = mop.getPojoObject();	
		Persistent pojoContent = getContent(pojoDocument);
		if (pojoContent != null) {
		    ManagedObjectProxy contentObj = pojoContent.getManagedObjectProxy();
			if (contentObj != null) {
				StreamInfo streamInfo = (StreamInfo) mop.getPersistenceContext().getBeanInfo(pojoContent);
				streamInfo.prepareDetachableState(contentObj);
			}
		}
	}

}
