package icom.session;

import icom.Entity;
import icom.Id;

import java.util.Map;

public interface Icom {

    public <T> T find(Class<T> entityClass, Id id);
    
    public void persist(Entity entity);

    public UserContext setupUserContext(String username, char[] password);
    
    public UserContext setupUserContext(String username, char[] password, Map<Object, Object> properties);
    
}
