package nl.secureapps.demohacklu;

import android.util.Base64;

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

    private static int xorValues(byte[] msg, byte[] pwd){
        int i;
        for(i = 0; i < msg.length; i++){
            int keyOffset = i % pwd.length;
            msg[i] = (byte) (msg[i] ^ pwd[keyOffset]);
        }
        return i;
    }

    public String getPW(String myMessage) {
        byte[] xorParts0 = Base64.decode(myCompositeKey[0],0);
        byte[] xorParts1 = Base64.decode(myCompositeKey[1],0);
        byte[] xorKey = new byte[xorParts0.length];
        for(int i = 0; i < xorParts1.length; i++){
            xorKey[i] = (byte) (xorParts0[i] ^ xorParts1[i]);
        }
        byte[] testMessage = Base64.decode(myMessage,0);
        xorValues(testMessage, xorKey);
        String dPW = new String(testMessage);
        return dPW;
    }

    private static final String[] myCompositeKey = new String[]{
            "fXODuCqxWP5tAGAh1SbqnQ==","TEGwjB+Hb8ZUMCFjlmKv2w=="
    };
}
