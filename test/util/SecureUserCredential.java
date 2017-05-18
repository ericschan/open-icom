package util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecureUserCredential {

    SecretKey makeSecretKey(String pin) {
        try {
            DESKeySpec keySpec = new DESKeySpec(pin.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
            SecretKey key = keyFactory.generateSecret(keySpec);
            return key;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    String encrypt(String pin, byte[] credential) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey key = makeSecretKey(pin);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            BASE64Encoder base64encoder = new BASE64Encoder(); 
            String encryped = base64encoder.encode(cipher.doFinal(credential));
            return encryped;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    byte[] decrypt(String pin, String credential) {
        try {
            sun.misc.BASE64Decoder base64decoder = new BASE64Decoder();  
            byte[] encryped = base64decoder.decodeBuffer(credential);
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey key = makeSecretKey(pin);
            cipher.init(Cipher.DECRYPT_MODE, key); 
            byte[] decrypted = cipher.doFinal(encryped);
            return decrypted;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
