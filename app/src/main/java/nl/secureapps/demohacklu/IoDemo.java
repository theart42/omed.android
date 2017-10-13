package nl.secureapps.demohacklu;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Created by matth on 28/09/2017.
 */

public class IoDemo {
    public void startIoDemo() {
        try {
            InputStream inputStream = MainActivity.context.getAssets().open("omed.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;

            while ((line = reader.readLine()) != null) {
                MainActivity.processOutput(line);
            }
        }
        catch (Exception e) {
            MainActivity.processOutput(e.toString());
        }
    }
}
