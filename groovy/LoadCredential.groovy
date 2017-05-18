import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

class LoadCredential {
    
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
            Base64 base64encoder = new Base64();
            String encryped = base64encoder.encodeBase64(cipher.doFinal(credential));
            return encryped;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    byte[] decrypt(String key, String credential) {
        try {
            Base64 base64decoder = new Base64();
            byte[] encryped = base64decoder.decodeBase64(credential);
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey secretKey = makeSecretKey(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(encryped);
            return decrypted;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public UserCredential load() throws Exception {
        String credentialStore = System.getProperty("credential.store")
        if (credentialStore != null && credentialStore.length() != 0) {
            String currentDir = System.getProperty("user.dir");
            String directoryPath = currentDir + "/" + "contentStreams";
            String fullCredentialStorePath = directoryPath + "/" + credentialStore;
            File file = new File(fullCredentialStorePath);
            if (file.exists()) {
                String key = System.getProperty("credential.key");
                try {
                    FileReader fileReader = new FileReader(file);
                    char[] buffer = new char[10240];
                    fileReader.read(buffer);
                    String content = String.valueOf(buffer);
                    String[] entries = content.split("\n");
                    
                    UserCredential credential = new UserCredential();
                    
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
 
}
