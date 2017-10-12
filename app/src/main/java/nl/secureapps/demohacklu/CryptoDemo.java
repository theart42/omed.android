package nl.secureapps.demohacklu;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;

import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;


public class CryptoDemo {
    private SecretKeySpec sks;
    private SecureRandom sr;
    private KeyGenerator kg;
    private String method = "AES";

    public CryptoDemo() {
        try {
            this.sr = SecureRandom.getInstance("SHA1PRNG");
            this.sr.setSeed("(FJY@H7uiIH*6ggQ^!Hs8JKO@8".getBytes());
            this.kg = KeyGenerator.getInstance(this.method);
            this.kg.init(128, this.sr);
            this.sks = new SecretKeySpec((kg.generateKey()).getEncoded(), this.method);
        }
        catch (Exception e) {
            Log.e("CryptoDemo", "exception", e);
        }
    }

    public void startCryptoDemo() {
        byte[] ciphertext = this.encrypt("HelloWorld".getBytes());
        String ciphertextb64 = Base64.encodeToString(ciphertext, Base64.DEFAULT);
        //Print the base64 ciphertext
        MainActivity.processOutput("Ciphertext (base64): " + ciphertextb64);
        byte[] plaintext = this.decrypt(ciphertext);
        //Print the decrypted plaintext
        MainActivity.processOutput("Plaintext: " + new String(plaintext));
        //Print empty line
        MainActivity.processOutput("");
    }

    private byte[] encrypt(byte[] plaintext) {
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance(this.method);
            c.init(Cipher.ENCRYPT_MODE, this.sks);
            encodedBytes = c.doFinal(plaintext);
        }
        catch (Exception e) {
            Log.e("CryptoDemo", "exception", e);
        }
        return encodedBytes;
    }

    private byte[] decrypt(byte[] ciphertext) {
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance(this.method);
            c.init(Cipher.DECRYPT_MODE, this.sks);
            decodedBytes = c.doFinal(ciphertext);
        }
        catch (Exception e) {
            Log.e("CryptoDemo", "exception", e);
        }
        return decodedBytes;

    }
}
