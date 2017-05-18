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

import icom.Artifact;
import icom.Content;
import icom.Document;
import icom.Entity;
import icom.Folder;
import icom.Id;
import icom.Item;
import icom.MimeConvertible;
import icom.MultiContent;
import icom.SimpleContent;
import icom.Space;

import icom.UnifiedMessage;

import icom.beehive.BeehiveOrganizationUser;

import icom.beehive.BeehivePersonalSpace;

import icom.jpa.rt.UserContextManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import javax.naming.Context;

import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import javax.persistence.PersistenceUnit;

import javax.transaction.UserTransaction;


@Stateful(name="iCOMService")
@TransactionManagement(TransactionManagementType.BEAN)
public class ServiceBean implements ServiceLocal {
    
    @PersistenceUnit(unitName="bdkEntityManager")
    private EntityManagerFactory emFactory;

    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager bdkEntityManager;
    
    @Resource 
    SessionContext ctx;
    
    Credential credential = new Credential();
    
    public ServiceBean() {
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    private <T> T find(Class<T> entityClass, Id id) {
        return bdkEntityManager.find(entityClass, id);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    private void persist(Entity entity) {
        bdkEntityManager.persist(entity);
    }
    
    public List<Space> getSpaces() {
        if (bdkEntityManager == null) {
            bdkEntityManager = emFactory.createEntityManager();
        }
        signon();
        UserContext uc = getUserContext();
        Id actorid = uc.getActorId();
        BeehiveOrganizationUser user = find(BeehiveOrganizationUser.class, actorid);
        Set<Space> spaces = user.getAccessibleSpaces();
        List<Space> list = new ArrayList<Space>(spaces.size());
        for (Space space : spaces) {
            if (! (space instanceof BeehivePersonalSpace)) {
                list.add(space);
            } else {
            	list.add(0, space);
            }
        }
        return list;
    }
    
    public List<Space> getSpaces(String contextid) {
        return getSpaces();
    }
    
    public List<Artifact> getArtifacts(String objectid) {
        setupEntityManager();
        if (objectid == null) {
            return null;
        }
        Id id = new Id(objectid);
        Folder f = find(Folder.class, id);
        return f.getArtifacts();
    }
    
    public List<Document> getDocuments(String objectid) {
        setupEntityManager();
        if (objectid == null) {
            return null;
        }
        Id id = new Id(objectid);
        Folder f = find(Folder.class, id);
        List<Artifact> artifacts = f.getArtifacts();
        List<Document> documents = new ArrayList<Document>(artifacts.size());
        if (artifacts != null) {
            for (Artifact artifact : artifacts) {
                if (artifact instanceof Document) {
                    documents.add((Document) artifact);
                }
            }
        }
        return documents;
    }
    
    public List<UnifiedMessage> getUnifiedMessages(String objectid) {
        setupEntityManager();
        if (objectid == null) {
            return null;
        }
        Id id = new Id(objectid);
        Folder f = find(Folder.class, id);
        List<Artifact> artifacts = f.getArtifacts();
        List<UnifiedMessage> messages = new ArrayList<UnifiedMessage>(artifacts.size());
        if (artifacts != null) {
            for (Artifact artifact : artifacts) {
                if (artifact instanceof UnifiedMessage) {
                    messages.add((UnifiedMessage) artifact);
                }
            }
        }
        return messages;
    }
    
    public Artifact getArtifact(String objectid) {
        setupEntityManager();
        if (objectid == null) {
            return null;
        }
        Id id = new Id(objectid);
        return find(Artifact.class, id);
    }
    
    public Document getDocument(String objectid) {
        setupEntityManager();
        if (objectid == null) {
            return null;
        }
        Id id = new Id(objectid);
        try {
            Document doc = find(Document.class, id);
            return doc;
        } catch (Exception ex) {
            // skip
        }
        return null;
    }
    
    public UnifiedMessage getUnifiedMessage(String objectid) {
        setupEntityManager();
        if (objectid == null) {
            return null;
        }
        Id id = new Id(objectid);
        try {
            UnifiedMessage msg = find(UnifiedMessage.class, id);
            return msg;
        } catch (Exception ex) {
            // skip
        }
        return null;
    }
    
    public SimpleContent getSimpleContent(String objectid) {
        setupEntityManager();
        Content content = null;
        Artifact artifact = getArtifact(objectid);
        if (artifact instanceof Document) {
            content = ((Document)artifact).getContent();
        } else if (artifact instanceof UnifiedMessage) {
            content = ((UnifiedMessage)artifact).getContent();
        }
        if (content instanceof SimpleContent) {
            return (SimpleContent) content;
        } else if (content instanceof MultiContent) {
            if (content.getMediaType().equalsIgnoreCase("multipart/alternative")) {
                Content child = (Content) ((MultiContent)content).getParts().get(0);
                if (child instanceof SimpleContent) {
                    return (SimpleContent) child;
                }
            } else if (content.getMediaType().equalsIgnoreCase("multipart/mixed")) {
                Content child = (Content) ((MultiContent)content).getParts().get(0);
                if (child instanceof SimpleContent) {
                    return (SimpleContent) child;
                } else {
                    List<MimeConvertible> parts = ((MultiContent)child).getParts();
                    for (MimeConvertible part : parts) {
                        if (part instanceof SimpleContent) {
                            return (SimpleContent) part;
                        }
                    }
                }   
            }
        }
        return null;
    }
    
    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Credential getCredential() {
        return credential;
    }
    
    public void commit() {
        try {
            bdkEntityManager.getTransaction().commit();
        } catch (Exception ex1) {
            try {
                UserTransaction transaction = ctx.getUserTransaction();
                if (transaction.getStatus() != javax.transaction.Status.STATUS_NO_TRANSACTION) {
                    ctx.getUserTransaction().commit();
                }
            } catch (Exception ex2) {
                ex1.printStackTrace();
                ex2.printStackTrace();
            }
        }
    }
    
    public void rollback() {
        try {
            bdkEntityManager.getTransaction().rollback();
        } catch (Exception ex1) {
            try {
                UserTransaction transaction = ctx.getUserTransaction();
                if (transaction.getStatus() != javax.transaction.Status.STATUS_NO_TRANSACTION) {
                    ctx.getUserTransaction().rollback();
                }
            } catch (Exception ex2) {
                ex1.printStackTrace();
                ex2.printStackTrace();
            }
        }
    }
    
    public void close() {
        rollback();
        try {
            bdkEntityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        credential.clear();
        bdkEntityManager.clear();
        bdkEntityManager = null;
    }
    
    private void setupEntityManager() {
        if (bdkEntityManager == null) {
            bdkEntityManager = emFactory.createEntityManager();
            signon();
        }
    }
    
    private void signon() {
        try {
            String userName = null;
            char[] password = null;
            String hostName = null;
            
            CredentialStore cs = new CredentialStore();
            CredentialStore.StoredCredential c = cs.load(credential.pseudonym, credential.key);
            if (c != null) {
                userName = c.userName;
                password = c.password;
                hostName = c.hostName;
            }
            if (credential.username != null && credential.username.length() != 0) {
                userName = credential.username;
            }
            if (credential.password != null && credential.password.length() != 0) {
                password = credential.password.toCharArray();
            }
            if (credential.hostName != null && credential.hostName.length() != 0) {
                hostName = credential.hostName;
            }
            
            Map<Object, Object> properties = new HashMap<Object, Object>();
            properties.put("Host", hostName);
            UserContext uc = setupUserContext(userName, password, properties);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public UserContext setupUserContext(String username, char[] password, Map<Object, Object> properties) {
        UserContextManager pc = (UserContextManager)bdkEntityManager.getDelegate();
        UserContext uc = null;
        try {
            icom.jpa.rt.UserContext ctx = pc.createUserContext(username, password, properties);
            pc.attachUserContext(ctx);
            uc = new UserContext(ctx);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return uc;
    }

    public UserContext getUserContext() {
        UserContext uc = null;
        UserContextManager pc = (UserContextManager)bdkEntityManager.getDelegate();
        try {
            icom.jpa.rt.UserContext ctx = pc.getUserContext();
            if (ctx != null) {
                uc = new UserContext(ctx);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return uc;
    }

}
