import icom.Actor
import icom.Document
import icom.Extent

import java.util.Collection
import java.util.Date

import javax.persistence.EntityManager
import javax.persistence.Query


class Documents {
    
    static public Collection<Document> findDocumentsByName(EntityManager manager, Extent extent, String name) {
        String queryText = "select d from Document d where d.parent = :folder and d.name = :name";
        Query query = manager.createQuery(queryText);
        query.setParameter("folder", extent);
        query.setParameter("name", name);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsLikeName(EntityManager manager, Extent extent, String name) {
        String queryText = "select d from Document d where d.parent = :folder and d.name like :name";
        Query query = manager.createQuery(queryText);
        query.setParameter("folder", extent);
        query.setParameter("name", name);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsByCreationDates(EntityManager manager, Extent extent, Date after, Date before) {
        String queryText = "select d from Document d where d.parent = :folder and d.creationDate between :after and :before";
        queryText += " order by d.creationDate DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(100);
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
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsByLastModificationDates(EntityManager manager, Extent extent, Date after, Date before) {
        String queryText = "select d from Document d where d.parent = :folder and d.lastModificationDate between :after and :before";
        queryText += " order by d.lastModificationDate DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(100);
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
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsBySizeOver(EntityManager manager, Extent extent, int size) {
        String queryText = "select d from Document d where d.parent = :folder and d.size >= :size";
        queryText += " order by d.size DESC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(100);
        query.setParameter("folder", extent);
        query.setParameter("size", size);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsBySizeBelow(EntityManager manager, Extent extent, int size) {
        String queryText = "select d from Document d where d.parent = :folder and d.size <= :size";
        queryText += " order by d.size ASC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(100);
        query.setParameter("folder", extent);
        query.setParameter("size", size);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsBySize(EntityManager manager, Extent extent, int size) {
        String queryText = "select d from Document d where d.parent = :folder and d.size = :size";
        queryText += " order by d.size ASC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(100);
        query.setParameter("folder", extent);
        query.setParameter("size", size);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsBySizeRange(EntityManager manager, Extent extent, int size1, int size2) {
        String queryText = "select d from Document d where d.parent = :folder and d.size between :size1 and :size2";
        queryText += " order by d.size ASC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(100);
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
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Document> findDocumentsByActor(EntityManager manager, Extent extent, Actor actor) {
        String queryText = "select d from Document d where d.parent = :folder";
        queryText += " and (d.createdBy = :actor or d.lastModifiedBy = :actor or d.owner = :actor)";
        queryText += " order by d.size ASC";
        Query query = manager.createQuery(queryText);
        query.setMaxResults(100);
        query.setParameter("folder", extent);
        query.setParameter("actor", actor);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Document> result = new ArrayList<Document>(list.size());
        result.addAll(list);
        return result;
    }

}
