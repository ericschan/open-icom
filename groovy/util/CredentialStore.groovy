package util;

import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException


public class CredentialStore {
    
    static String directoryPath;
    
    static {
        String currentDir = System.getProperty("user.dir");
        directoryPath = currentDir + "/" + "contentStreams";
        File directoryfile = new File(directoryPath);
        directoryfile.mkdir();
    }
    
    static public String getDirectory() {
        return directoryPath;
    }
    
    Credential credential;
    
    public CredentialStore() {
        super();
    }
  
    File createFile(String fileName, String content) {
        String directoryPath = getDirectory();
        String filePath = directoryPath + "/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return file;
    }
    
    File getFile(String fileName) {
        String directoryPath = getDirectory();
        String filePath = directoryPath + "/" + fileName;
        File file = new File(filePath);
        return file;
    }
    
    public void store() {
        credential = new Credential();
        CredentialDialog.credentialDialog(credential);
        SecureCredential s = new SecureCredential();
        String id = s.encrypt(credential.passcode, credential.userName.getBytes());
        String passwd = s.encrypt(credential.passcode, String.valueOf(credential.password).getBytes());
        String host = s.encrypt(credential.passcode, credential.host.getBytes());
        StringBuffer buffer = new StringBuffer();
        buffer.append(id);
        buffer.append("\n");
        buffer.append(passwd);
        buffer.append("\n");
        buffer.append(host);
        buffer.append("\n");
        createFile(credential.pseudonym, buffer.toString());
        verify(credential.pseudonym, credential.passcode);
    }
    
    public void verify(String pseudonym, String key) {
        File file = getFile(pseudonym);
        try {
            FileReader fileReader = new FileReader(file);
            char[] buffer = new char[10240];
            fileReader.read(buffer);
            String content = String.valueOf(buffer);
            String[] entries = content.split("\n");
            SecureCredential s = new SecureCredential();
            
            String encryptedId = entries[0];
            byte[] id = s.decrypt(key, encryptedId);
            String name = new String(id);
            println name;
            
            /*
            String encryptedPasswd = entries[1];
            byte[] p = s.decrypt(key, encryptedPasswd);
            String passwd = new String(p);
            */
            
            String encryptedHost = entries[2];
            byte[] host = s.decrypt(key, encryptedHost);
            String hostName = new String(host);
            println hostName;
            
            return;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
