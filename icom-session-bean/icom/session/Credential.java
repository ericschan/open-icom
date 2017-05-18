package icom.session;

public class Credential {
    
    String username;
    String password;
    
    String pseudonym;
    String key;
    
    String hostName;
    
    public Credential() {
        super();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }
    
    public void clear() {
        username = null;
        password = null;
        hostName = null;
    }
}
