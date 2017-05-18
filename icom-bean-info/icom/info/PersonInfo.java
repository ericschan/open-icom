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

public class PersonInfo extends ActorInfo {
	
	static PersonInfo singleton = new PersonInfo();
	
	public static PersonInfo getInstance() {
		return singleton;
	}

	public enum Attributes {
		personalSpace,
		presence,
		instantMessageFeed
	}
	
	{
		cascadePersistObjectCollections.add(Attributes.presence.name());
		cascadePersistObjectCollections.add(Attributes.instantMessageFeed.name());
	}
	
	// TODO referencedObjects.add(PersonalInfo.Attributes.nickname.name());

	{
		referencedObjects.add(PersonalInfo.Attributes.givenName.name());
		referencedObjects.add(PersonalInfo.Attributes.middleName.name());
		referencedObjects.add(PersonalInfo.Attributes.familyName.name());
		referencedObjects.add(PersonalInfo.Attributes.prefix.name());
		referencedObjects.add(PersonalInfo.Attributes.suffix.name());
		referencedObjects.add(PersonalInfo.Attributes.jobTitle.name());
		referencedObjects.add(PersonalInfo.Attributes.department.name());
		referencedObjects.add(PersonalInfo.Attributes.officeLocation.name());
		referencedObjects.add(PersonalInfo.Attributes.company.name());
		referencedObjects.add(PersonalInfo.Attributes.profession.name());
		referencedObjects.add(PersonalInfo.Attributes.timeZone.name());
		referencedObjects.add(Attributes.personalSpace.name());
		referencedObjects.add(Attributes.presence.name());
		referencedObjects.add(Attributes.instantMessageFeed.name());
	}

	protected PersonInfo() {
	}
	
	public int getClassOrdinal() {
		return IcomBeanEnumeration.Person.ordinal();
	}

}
