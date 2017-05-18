import icom.EntityAddress
import icom.Person

import java.net.URI
import java.net.URISyntaxException
import java.util.Collection;

import javax.persistence.EntityManager
import javax.persistence.Query

class Users {
    
    static public Collection<Person> findUsersByAddress(EntityManager manager, String address) {
        String queryText = "select d from Person d where d.primaryAddress = :address or :address member of d.addresses";
        Query query = manager.createQuery(queryText);
        query.setParameter("address", address);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Person> result = new ArrayList<Person>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Person> findUsersByEntityAddress(EntityManager manager, String address) {
        String queryText = "select d from Person d where d.primaryAddress = :address or :address member of d.addresses";
        Query query = manager.createQuery(queryText);
        try {
            URI uri = new URI((String) address);
            EntityAddress entityAddress = new EntityAddress();
            entityAddress.setAddress(uri);
            query.setParameter("address", entityAddress);
        } catch (URISyntaxException ex) {
            throw ex;
        }
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Person> result = new ArrayList<Person>(list.size());
        result.addAll(list);
        return result;
    }

    static public Collection<Person> findUsersLikeName(EntityManager manager, String pattern) {
        String queryText = "select d from Person d where d.name like :pattern";
        Query query = manager.createQuery(queryText);
        query.setParameter("pattern", pattern);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Person> result = new ArrayList<Person>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Person> findUsersLikeFamilyName(EntityManager manager, String pattern) {
        String queryText = "select d from Person d where d.familyName like :pattern";
        Query query = manager.createQuery(queryText);
        query.setParameter("pattern", pattern);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Person> result = new ArrayList<Person>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Person> findUsersLikeGivenName(EntityManager manager, String pattern) {
        String queryText = "select d from Person d where d.givenName like :pattern";
        Query query = manager.createQuery(queryText);
        query.setParameter("pattern", pattern);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Person> result = new ArrayList<Person>(list.size());
        result.addAll(list);
        return result;
    }
    
    static public Collection<Person> findUsersLikeFullName(EntityManager manager, String givenNamePattern, String familyNamePattern) {
        String queryText = "select d from Person d where d.familyName like :familyNamePattern and d.givenName like :givenNamePattern";
        Query query = manager.createQuery(queryText);
        query.setParameter("givenNamePattern", givenNamePattern);
        query.setParameter("familyNamePattern", familyNamePattern);
        List list = null;
        try {
            list = query.getResultList();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
        ArrayList<Person> result = new ArrayList<Person>(list.size());
        result.addAll(list);
        return result;
    }

    static listUsers(Collection<Person> users) {
        for (Person user : users) {
            String addrStr = null;
            EntityAddress addr = user.getPrimaryAddress();
            if (addr != null) {
                URI uri = addr.getAddress();
                addrStr = uri.toString();
            }
            println "Display Name: " + user.getName();
            println "Primary Address: " + addrStr;
            String fullName = "";
            if (user.getPrefix() != null) {
                fullName += user.getPrefix() + " ";
            }
            fullName += user.getGivenName();
            if (user.getMiddleName() != null) {
                fullName += " " + user.getMiddleName();
            }
            fullName += " " + user.getFamilyName();
            if (user.getSuffix() != null) {
                fullName += " " + user.getSuffix();
            }
            println "Full Name: " + fullName;
            Collection<String> nicknames = user.getNicknames();
            for (String nickname : nicknames) {
                println "Nick Name: " +  nickname;
            }
            println "Job Title: " + user.getJobTitle();
            if (user.getDepartment() != null) {
                println "Department: " + user.getDepartment()
            }
            if (user.getOfficeLocation() != null) {
                println "Department: " +  user.getOfficeLocation()
            }
            Collection<EntityAddress> addresses = user.getEntityAddresses();
            for (EntityAddress anAddress : addresses) {
                URI uri = anAddress.getAddress();
                addrStr = uri.toString();
                println "\t address: " + addrStr;
            }
            println();
            println();
        }
    }
    
    static public Person selectUser(EntityManager manager) {
        Collection<Person> users;
        
        System.out.print("Enter family name pattern: ");
        System.out.flush();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String familyNamePattern = input.readLine()
        
        System.out.print("Enter given name pattern: ")
        System.out.flush()
        String givenNamePattern = input.readLine()
        
        if (familyNamePattern != null && familyNamePattern.length() != 0 &&
            givenNamePattern != null && givenNamePattern.length() != 0) {
            users = Users.findUsersLikeFullName(manager, givenNamePattern, familyNamePattern);
        } else if ((familyNamePattern == null || familyNamePattern.length() == 0) &&
            givenNamePattern != null && givenNamePattern.length() != 0) {
            users = Users.findUsersLikeGivenName(manager, givenNamePattern);
        }  else if (familyNamePattern != null && familyNamePattern.length() != 0 &&
            (givenNamePattern == null || givenNamePattern.length() == 0)) {
            users = Users.findUsersLikeFamilyName(manager, familyNamePattern);
        } else {
            users = Users.findUsersLikeName(manager, "%");
        }
        listUsers(users);
            
        System.out.print("Enter email address: ")
        System.out.flush()
        String address = input.readLine();
        
        return selectUser(users, address);
    }
      
    static public Person selectUser(Collection<Person> users, String addr) {
        for (Person user : users) {
            Collection<EntityAddress> addresses = user.getEntityAddresses();
            for (EntityAddress address : addresses) {
                String addr1 = address.getAddress().toString();
                if (addr1.equalsIgnoreCase(addr)) {
                    return user;
                }
            }
        }
        return null;
    }

}
