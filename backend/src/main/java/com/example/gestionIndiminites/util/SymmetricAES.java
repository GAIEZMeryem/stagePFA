package com.example.gestionIndiminites.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SymmetricAES {
    private SecretKey aesKey;

    public SymmetricAES() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        this.aesKey = keyGen.generateKey();
    }

    public String encrypt(String plainText) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedBytes = aesCipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = aesCipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public String getEncodedKey() {
        return Base64.getEncoder().encodeToString(aesKey.getEncoded());
    }

    public void setEncodedKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        this.aesKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
