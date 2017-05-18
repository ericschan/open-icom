package util;

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.PBEKeySpec

import org.apache.commons.codec.binary.Base64;

public class SecureCredential {
    
    SecretKey makeSecretKey(char[] secret) {
        try {
            PBEKeySpec keySpec = new PBEKeySpec(secret);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            return secretKey;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    char[] getSecret(SecretKey secretKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            PBEKeySpec keySpec = keyFactory.getKeySpec(secretKey, PBEKeySpec.class);
            return keySpec.getPassword();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

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
			byte[] encryptedCredential = cipher.doFinal(credential);
            Base64 base64encoder = new Base64(); 
            String encryped = base64encoder.encodeBase64String(encryptedCredential);
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
    
}
