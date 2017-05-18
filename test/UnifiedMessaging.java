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
import icom.Artifact;
import icom.Document;
import icom.Extent;
import icom.Folder;
import icom.HeterogeneousFolder;
import icom.Id;
import icom.MimeConvertible;
import icom.MultiContent;
import icom.MultiContentTypeEnum;
import icom.SimpleContent;
import icom.Space;
import icom.UnifiedMessage;
import icom.UnifiedMessageFlagEnum;
import icom.UnifiedMessageParticipant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Query;

import util.FileUtil;


public class UnifiedMessaging {
	
	static public UnifiedMessage testForwardAttachments(Session session, String pattern) {	
		Space space = session.getPersonalSpace();
        Actor sender = session.getActor();
        Collection<Actor> tos = new ArrayList<Actor>();
        tos.add(sender);
        String subject = "Send a test message with attachments";
        
        HeterogeneousFolder sentFolder = (HeterogeneousFolder) BrowseExtent.findFolder(space, "Sent Items");
        
        Folder inbox = BrowseExtent.findFolder(space, "INBOX");
        Collection<UnifiedMessage> messages = BrowseExtent.getRecentArtifacts(inbox, 2, UnifiedMessage.class);
        
        Folder library = BrowseExtent.findFolder(space, "Documents");
        Collection<Document> documents = BrowseExtent.getRecentArtifacts(library, 2, Document.class);
        
        Collection<MimeConvertible> attachments = new ArrayList<MimeConvertible>(messages.size() + documents.size());
        attachments.addAll(messages);
        attachments.addAll(documents);
  
		File body = FileUtil.createFile();
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(body);
			fileOutputStream.write(pattern.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException ex) {
			
		} catch (IOException ex) {
			
		}

		UnifiedMessage newMsg = forwardMessages(session, sender, tos, null, null, sentFolder, subject, body, attachments);
		
	    return newMsg;
	}
	
	static public Collection<UnifiedMessage> findMessages(Session session, Extent extent, String pattern) {
		String queryText = "select d from UnifiedMessage d where d.content like :pattern and d.parent = :folder";
		Query query = session.getManager().createQuery(queryText);
		query.setParameter("folder", extent);
		query.setParameter("pattern", pattern);
		List list = query.getResultList();
		ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
		result.addAll(list);
		return result;
	}
	
	static public Collection<UnifiedMessage> findMessages(Session session, String pattern) {
		String queryText = "select d from UnifiedMessage d where d.content like :pattern";
		Query query = session.getManager().createQuery(queryText);
		query.setParameter("pattern", pattern);
		List list = query.getResultList();
		ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
		result.addAll(list);
		return result;
	}
	
	static public void testUnifiedMessaging(Session session) {
		String currentDateTime = new Date().toString();
		UnifiedMessage msg1 = UnifiedMessaging.testForwardAttachments(session, currentDateTime);
		StringBuffer buf = new StringBuffer();
		String subject1 = msg1.getSubject();
		buf.append("Subject: " + subject1 + "\n");
		Id id1 = msg1.getId();
		buf.append("Id: " + id1.getObjectId().toString() + "\n");
		Space space = session.getPersonalSpace();
		Folder sendFolder = BrowseExtent.findFolder(space, "Sent Items");
		Collection<UnifiedMessage> sentMsgs = UnifiedMessaging.findMessages(session, sendFolder, currentDateTime);
		for (UnifiedMessage msg : sentMsgs) {
			String subject = msg.getSubject();
			buf.append("Subject of sent message: " + subject + "\n");
			Id id = msg.getId();
			buf.append("Id of sent message: " + id.getObjectId().toString() + "\n");
		}
		Folder inbox = BrowseExtent.findFolder(space, "INBOX");
		Collection<UnifiedMessage> receivedMsgs = UnifiedMessaging.findMessages(session, inbox, currentDateTime);
		for (UnifiedMessage msg : receivedMsgs) {
			String subject = msg.getSubject();
			buf.append("Subject of received message: " + subject + "\n");
			Id id = msg.getId();
			buf.append("Id of received message: " + id.getObjectId().toString() + "\n");
		}
		System.out.print(buf.toString());
	}

	static public UnifiedMessage forwardMessages(Session session, Actor from, 
			Collection<Actor> tos, Collection<Actor> ccs, Collection<Actor> bccs, HeterogeneousFolder sentFolder,
			String subject, File body, Collection<MimeConvertible> attachments) {
		Date dt = new Date();
		UnifiedMessage newMsg = new UnifiedMessage(sentFolder, dt, dt);
		
		UnifiedMessageParticipant sender = new UnifiedMessageParticipant(from);
		newMsg.setSender(sender);
		
		if (tos != null) {
			for (Actor to : tos) {
				UnifiedMessageParticipant p = new UnifiedMessageParticipant(to);
				newMsg.addToReceiver(p);
			}
		}
		
		if (ccs != null) {
			for (Actor cc : ccs) {
				UnifiedMessageParticipant p = new UnifiedMessageParticipant(cc);
				newMsg.addToReceiver(p);
			}
		}
		
		if (bccs != null) {
			for (Actor bcc : bccs) {
				UnifiedMessageParticipant p = new UnifiedMessageParticipant(bcc);
				newMsg.addToReceiver(p);
			}
		}
		
		newMsg.setSubject(subject);
		
		MultiContent multiContent = new MultiContent();
		multiContent.setMediaType(MultiContentTypeEnum.Mixed);
		
		SimpleContent simpleContent = new SimpleContent();
		simpleContent.setDataFile(body);
	    simpleContent.setMediaType("text/html");
	    simpleContent.setContentLanguage(Locale.getDefault());
	    simpleContent.setCharacterEncoding("UTF-8");
	    simpleContent.setContentEncoding("8bit");
	    multiContent.addPart(simpleContent);
	    
	    MultiContent forwards = new MultiContent();
	    forwards.setMediaType(MultiContentTypeEnum.Mixed);
	    for (MimeConvertible attachment : attachments) {
	    	if (attachment instanceof Artifact) {
	    		session.prepareToDetach((Artifact) attachment, true);
	    	}
	    	forwards.addPart((MimeConvertible) attachment.clone());
	    	if (attachment instanceof UnifiedMessage) {
	    		UnifiedMessage msg = (UnifiedMessage) attachment;
	    		msg.addFlag(UnifiedMessageFlagEnum.Forwarded);
	    	}
	    }
	    multiContent.addPart(forwards);
	    
	    newMsg.setContent(multiContent);
	    session.send(newMsg, sentFolder);
	    return newMsg;
	}
	
}
