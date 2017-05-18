import icom.Actor
import icom.Artifact
import icom.Extent
import icom.HeterogeneousFolder
import icom.MimeConvertible
import icom.MultiContent
import icom.MultiContentTypeEnum
import icom.Participant
import icom.PriorityEnum
import icom.SimpleContent
import icom.UnifiedMessage
import icom.UnifiedMessageChannelEnum
import icom.UnifiedMessageParticipant

import java.io.File
import java.util.ArrayList
import java.util.Collection
import java.util.Date
import java.util.Locale

import javax.persistence.EntityManager
import javax.persistence.Query

class Messages {
	
	static public Collection<UnifiedMessage> findMessagesBySubject(EntityManager manager, Extent extent, String subject) {
		String queryText = "select d from UnifiedMessage d where d.name = :pattern and d.parent = :folder";
		Query query = manager.createQuery(queryText);
		query.setParameter("folder", extent);
		query.setParameter("pattern", subject);
		List list = null;
		try {
			list = query.getResultList();
		} catch (Throwable ex) {
			ex.printStackTrace();
            throw ex;
		}
		ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
		result.addAll(list);
		return result;
	}
    
    static public Collection<UnifiedMessage> findMessagesLikeSubject(EntityManager manager, Extent extent, String subject) {
        String queryText = "select d from UnifiedMessage d where d.name like :pattern and d.parent = :folder";
        Query query = manager.createQuery(queryText);
        query.setParameter("folder", extent);
        query.setParameter("pattern", subject);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesByDeliveredTimes(EntityManager manager, Extent extent, Date after, Date before) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.deliveredTime >= :after and d.deliveredTime <= :before";
        queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("after", after);
        query.setParameter("before", before);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesByDeliveredTimesBetween(EntityManager manager, Extent extent, Date after, Date before) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.deliveredTime between :after and :before";
        queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("after", after);
        query.setParameter("before", before);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesByPriority(EntityManager manager, Extent extent, PriorityEnum priority) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.priority = :priority";
        queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("priority", priority);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesByChannel(EntityManager manager, Extent extent, UnifiedMessageChannelEnum channel) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.channel = :channel";
        queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("channel", channel);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesBySizeOver(EntityManager manager, Extent extent, int size) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.size >= :size";
        queryText += " order by d.size DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("size", size);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesBySizeBelow(EntityManager manager, Extent extent, int size) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.size <= :size";
        queryText += " order by d.size ASC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("size", size);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesBySize(EntityManager manager, Extent extent, int size) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.size = :size";
        queryText += " order by d.size ASC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("size", size);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesBySizeRange(EntityManager manager, Extent extent, int size1, int size2) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.size between :size1 and :size2";
        queryText += " order by d.size ASC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("size1", size1);
        query.setParameter("size2", size2);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesBySender(EntityManager manager, Extent extent, String sender) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.sender = :sender";
        queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("sender", sender);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesBySender(EntityManager manager, Extent extent, Participant sender) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and d.sender = :sender";
        queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("sender", sender);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesByReceiver(EntityManager manager, Extent extent, Participant receiver) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and";
        queryText += " (:receiver member of  d.toReceivers or :receiver member of  d.ccReceivers or :receiver member of  d.bccReceivers)";
        //queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("receiver", receiver);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<UnifiedMessage> findMessagesByParticipant(EntityManager manager, Extent extent, Participant participant) {
        String queryText = "select d from UnifiedMessage d where d.parent = :folder and";
        queryText += " (:participant = d.sender or :participant member of  d.toReceivers or :participant member of  d.ccReceivers or :participant member of d.bccReceivers)";
        //queryText += " order by d.deliveredTime DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(10);
        query.setParameter("folder", extent);
        query.setParameter("participant", participant);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<UnifiedMessage> result = new ArrayList<UnifiedMessage>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public UnifiedMessage sendMessage(Session session, Actor from,
        Collection<UnifiedMessageParticipant> tos, 
        Collection<UnifiedMessageParticipant> ccs, 
        Collection<UnifiedMessageParticipant> bccs, 
        HeterogeneousFolder sentFolder,
        String subject, File body, Collection<MimeConvertible> attachments) {
        
        Date dt = new Date();
        UnifiedMessage newMsg = new UnifiedMessage(dt, dt);
        
        UnifiedMessageParticipant sender = new UnifiedMessageParticipant(from);
        newMsg.setSender(sender);
        
        if (tos != null) {
            for (UnifiedMessageParticipant to : tos) {
                newMsg.addToReceiver(to);
            }
        }
        if (ccs != null) {
            for (UnifiedMessageParticipant cc : ccs) {
                newMsg.addCcReceiver(cc);
            }
        }
        if (bccs != null) {
            for (UnifiedMessageParticipant bcc : bccs) {
                newMsg.addBccReceiver(bccs);
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
        
        if (attachments != null) {
            MultiContent forwards = new MultiContent();
            forwards.setMediaType(MultiContentTypeEnum.Mixed);
            for (MimeConvertible attachment : attachments) {
                if (attachment instanceof Artifact) {
                    session.prepareToDetach((Artifact) attachment, true);
                }
                forwards.addPart((MimeConvertible) attachment.clone());
                /*
                if (attachment instanceof UnifiedMessage) {
                    UnifiedMessage msg = (UnifiedMessage) attachment;
                    msg.addFlag(UnifiedMessageFlag.Forwarded);
                }
               */
            }
            multiContent.addPart(forwards);
        }
        
        newMsg.setContent(multiContent);
        session.send(newMsg, sentFolder);
        return newMsg;
    }

}
