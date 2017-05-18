package util;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class StoreUserCredential {
    
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
    
    UserCredential credential;

    void loginDialog() {
        JFrame frame = new JFrame("Login Form");
        LoginDialog dialog = new LoginDialog(frame, credential);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        dialog.setVisible(true);
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
        credential = new UserCredential();
        loginDialog();
        SecureUserCredential s = new SecureUserCredential();
        String id = s.encrypt(credential.pin, credential.userName.getBytes());
        String passwd = s.encrypt(credential.pin, String.valueOf(credential.password).getBytes());
        String host = s.encrypt(credential.pin, credential.host.getBytes());
        StringBuffer buffer = new StringBuffer();
        buffer.append(id);
        buffer.append("\n");
        buffer.append(passwd);
        buffer.append("\n");
        buffer.append(host);
        buffer.append("\n");
        createFile(credential.pseudonym, buffer.toString());
        retrieve(credential.pseudonym, credential.pin);
    }
    
    public void retrieve(String pseudonym, String pin) {
        File file = getFile(pseudonym);
        try {
            FileReader fileReader = new FileReader(file);
            char[] buffer = new char[10240];
            fileReader.read(buffer);
            String content = String.valueOf(buffer);
            String[] entries = content.split("\n");
            SecureUserCredential s = new SecureUserCredential();
            
            String encryptedId = entries[0];
            byte[] id = s.decrypt(pin, encryptedId);
            String name = new String(id);
            
            String encryptedPasswd = entries[1];
            byte[] p = s.decrypt(pin, encryptedPasswd);
            String passwd = new String(p);
            
            String encryptedHost = entries[2];
            byte[] host = s.decrypt(pin, encryptedHost);
            String hostName = new String(host);
            
            return;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void main(String[] args) throws Exception {
        StoreUserCredential s = new StoreUserCredential();
        s.store();
    }
    
}
