package nl.secureapps.demohacklu;

import android.content.res.Resources;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.KeyStore;
import java.util.Enumeration;
import static nl.secureapps.demohacklu.R.raw.keystore;

public class KeyDemo {

    public void startKeyDemo(Resources resources) {
        Hide hidePw = new Hide();

        try {
            KeyStore trusted = KeyStore.getInstance("BKS");

            // keystore.bks should be in the res/raw directory
            try (InputStream in = resources.openRawResource(keystore)) {
                try {
                    trusted.load(in, hidePw.getPW("AANxVVtXVlYY").toCharArray());
                } finally {
                    in.close();
                }
            }
            Enumeration enumeration = trusted.aliases();
            while(enumeration.hasMoreElements()) {
                String alias = (String)enumeration.nextElement();
                MainActivity.processOutput("alias: ".concat(alias));
                Certificate certificate = trusted.getCertificate(alias);
                MainActivity.processOutput(certificate.toString());
            }
        }
        catch (Exception e) {
            MainActivity.processOutput(e.toString());
        }
    }
}

