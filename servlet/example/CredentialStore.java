package example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CredentialStore {

    SecretKey makeSecretKey(String key) {
        try {
            DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            return secretKey;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    String encrypt(String key, byte[] credential) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey secretKey = makeSecretKey(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String encryped = Base64.encodeBase64String(cipher.doFinal(credential));
            return encryped;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    byte[] decrypt(String key, String credential) {
        try {
        	byte[] encryped = Base64.decodeBase64(credential);
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey secretKey = makeSecretKey(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey); 
            byte[] decrypted = cipher.doFinal(encryped);
            return decrypted;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Credential load(String pseudonym, String key) throws Exception {
        String currentDir = System.getProperty("user.dir");
        String directoryPath = currentDir + "/" + "contentStreams";
        String fullCredentialStorePath = directoryPath + "/" + pseudonym;
        File file = new File(fullCredentialStorePath);
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                char[] buffer = new char[10240];
                fileReader.read(buffer);
                String content = String.valueOf(buffer);
                String[] entries = content.split("\n");
                
                Credential credential = new Credential();
                
                String encryptedId = entries[0];
                byte[] id = decrypt(key, encryptedId);
                credential.userName = new String(id);
                
                String encryptedPasswd = entries[1];
                byte[] p = decrypt(key, encryptedPasswd);
                credential.password = (new String(p)).toCharArray();
                
                String encryptedHost = entries[2];
                byte[] host = decrypt(key, encryptedHost);
                credential.hostName = new String(host);
                
                return credential;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
}

class Credential {

    public String userName;
    public char[] password;
    public String hostName;
    
}
