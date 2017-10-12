package nl.secureapps.demohacklu;

/**
 * Created by theart42 on 12/10/17.
 */

import android.util.Base64;
import android.util.Log;

/**
 * Created by theart42 on 12/10/17.
 */

/**
 * A simple utility to manage the XOR hiding routines.
 *
 * Created by Michael Ramirez on 7/27/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class Hide {
    private static final String TAG = "HidingUtil";

    /**
     * A convenience method that generates a XOR key pair for a given key. It was used to generate
     * the key for {@link MainActivity#useXorStringHiding(String)} method.
     *
     * @param key The source key to use in generating the XOR key halves
     * @return a two-value string array containing both parts of the XOR key
     */
    /*
    public static String[] generateKeyXorParts(String key){
        String[] keyParts = new String[2];

        Log.i(TAG, "Passwordvalue  " + key);
        Log.i(TAG, "Passwordlength " + String.valueOf(key.length()));

        byte[] xorRandom = new byte[key.length()];
        byte[] xorMatch = new byte[key.length()];
        byte[] keyBytes = key.getBytes();
        for(int i = 0; i < key.length(); i++){
            xorRandom[i] = (byte)(256 * Math.random());
            xorMatch[i] = (byte) (xorRandom[i] ^ keyBytes[i]);
        }
        keyParts[0] = Base64.encodeToString(xorRandom, 0);
        keyParts[1] = Base64.encodeToString(xorMatch, 0);
        Log.i(TAG, "XOR Key Part 0: " + keyParts[0]);
        Log.i(TAG, "XOR Key Part 1: " + keyParts[1]);

        return keyParts;
    }
    */

    private static int xorValues(byte[] msg, byte[] pwd){
        int i;
        // Log.i(TAG,"Message has " + msg.length + " length");
        for(i = 0; i < msg.length; i++){
            int keyOffset = i % pwd.length;
            msg[i] = (byte) (msg[i] ^ pwd[keyOffset]);
        }
        return i;
    }

    public String getPW(String myMessage) {
        // Log.i(TAG, "Password is " + myMessage);

        byte[] xorParts0 = Base64.decode(myCompositeKey[0],0);
        byte[] xorParts1 = Base64.decode(myCompositeKey[1],0);

        byte[] xorKey = new byte[xorParts0.length];
        for(int i = 0; i < xorParts1.length; i++){
            xorKey[i] = (byte) (xorParts0[i] ^ xorParts1[i]);
        }

/*
        byte[] hiddenMessage = myMessage.getBytes();
        xorValues(hiddenMessage, xorKey);

        String PW = Base64.encodeToString(hiddenMessage, 0);
        // String PW = new String(hiddenMessage);
        // String PW = Base64.encodeToString(hiddenMessage,0);
        Log.i(TAG, String.format("Hidden Message: %s", PW));
*/
        byte[] testMessage = Base64.decode(myMessage,0);
        xorValues(testMessage, xorKey);

        String dPW = new String(testMessage);
        // Log.i(TAG, String.format("Decoded password: %s", dPW));
        return dPW;
    }

    private static final String[] myCompositeKey = new String[]{
            "fXODuCqxWP5tAGAh1SbqnQ==","TEGwjB+Hb8ZUMCFjlmKv2w=="
    };
}
