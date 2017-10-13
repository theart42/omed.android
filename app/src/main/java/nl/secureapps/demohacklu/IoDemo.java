package nl.secureapps.demohacklu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
