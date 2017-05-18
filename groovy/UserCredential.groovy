
class UserCredential {

	public String userName;
	public char[] password;
	public String hostName;
    
    
    static public UserCredential getCredential() throws Exception {
        LoadCredential lc = new LoadCredential();
        UserCredential credential = lc.load();
        if (credential != null) {
            return credential;
        }
        LogonPrompt logon = new LogonPrompt();
        credential = logon.prompt();
        return credential
    }
    
	
}
