package nl.secureapps.demohacklu;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by matth on 28/09/2017.
 */

public class IoDemo {
    public void startIoDemo() {
        // Do your demo stuff here!!

        // Output can be passes to the main activity using this code:
        MainActivity.processOutput("IO demo results!");
        // This can be repeated during the demo process. No need to do it only once in the end.
    }
}
