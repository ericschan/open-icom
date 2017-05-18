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


public class OccurrenceInfo extends ArtifactInfo {

	static OccurrenceInfo singleton = new OccurrenceInfo();
	
	public static OccurrenceInfo getInstance() {
		return singleton;
	}
	
	public enum Attributes {
		editMode,
		startDate,
		startDateResolution,
		endDate,
		endDateResolution,
		location,
		organizer,
		participants,
		occurrenceSeries,
		fromRecurringOccurrenceSeries,
		exceptionToOccurrenceSeries,
		occurrenceStatus,
		occurrenceType,
		attachments,
		attendee,
		priority,
		attendeeParticipantStatus,
		transparency,
		attendeeProperties,
		conferences
	}
	
	{
		referencedObjects.add(Attributes.editMode.name());
		referencedObjects.add(Attributes.startDate.name());
		referencedObjects.add(Attributes.startDateResolution.name());
		referencedObjects.add(Attributes.endDate.name());
		referencedObjects.add(Attributes.occurrenceSeries.name());
		referencedObjects.add(Attributes.fromRecurringOccurrenceSeries.name());
		referencedObjects.add(Attributes.exceptionToOccurrenceSeries.name());
		referencedObjects.add(Attributes.occurrenceStatus.name());
		referencedObjects.add(Attributes.occurrenceType.name());
		referencedObjects.add(Attributes.priority.name());
		referencedObjects.add(Attributes.attendeeParticipantStatus.name());
		referencedObjects.add(Attributes.transparency.name());
		referencedObjects.add(Attributes.conferences.name());
	}
	
	{
		embeddedObjects.add(Attributes.location.name());
		embeddedObjects.add(Attributes.organizer.name());
		embeddedObjects.add(Attributes.attendee.name());
	}

	{
		embeddedObjectCollections.add(Attributes.participants.name());
		embeddedObjectCollections.add(Attributes.attachments.name());
		embeddedObjectCollections.add(Attributes.attendeeProperties.name());
	}
	
	{
		editableByViewer.add(Attributes.priority.name());
		editableByViewer.add(Attributes.attendeeParticipantStatus.name());
		editableByViewer.add(Attributes.transparency.name());
		editableByViewer.add(Attributes.attendeeProperties.name());
	}
	
	protected OccurrenceInfo() {
		
	}
	
	public int getClassOrdinal() {
		return IcomBeanEnumeration.Occurrence.ordinal();
	}

}
