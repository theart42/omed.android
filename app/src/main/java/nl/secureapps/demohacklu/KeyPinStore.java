package nl.secureapps.demohacklu;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.CertificateException;
import android.content.res.Resources;
import static nl.secureapps.demohacklu.R.raw.keystore;

public class KeyPinStore {

    private static KeyPinStore instance = null;
    private SSLContext sslContext = SSLContext.getInstance("TLS");

    public static synchronized KeyPinStore getInstance(Resources resources) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, java.security.cert.CertificateException {
        if (instance == null){
            instance = new KeyPinStore(resources);
        }
        return instance;
    }

    private KeyPinStore(Resources resources) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, java.security.cert.CertificateException {
        KeyStore trusted = KeyStore.getInstance("BKS");

        String tmfAlgorithm;
        TrustManagerFactory tmf;
        Hide hidePw = new Hide();

        try (InputStream in = resources.openRawResource(keystore)) {
            try {
                trusted.load(in, hidePw.getPW("AANxVVtXVlYY").toCharArray());
            } finally {
                in.close();
            }
        }

        // Create a TrustManager that trusts the CAs in our KeyStore
        tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(trusted);

        // Create an SSLContext that uses our TrustManager
        sslContext.init(null, tmf.getTrustManagers(), null);

        System.out.println("We made it this far Frank");

    }

    public SSLContext getContext(){
        return sslContext;
    }
}
