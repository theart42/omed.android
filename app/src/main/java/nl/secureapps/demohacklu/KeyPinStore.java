package nl.secureapps.demohacklu;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.CertificateException;
// import javax.security.cert.X509Certificate;

import android.content.res.Resources;

import android.app.Activity;
import android.content.Context;

import static nl.secureapps.demohacklu.R.raw.keystore;

/**
 * Created by frank on 12/10/2017.
 */

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
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // Context context = Context.class;
        //context = new Context();
        // Resources resources = context.getResources();
        KeyStore trusted = KeyStore.getInstance("BKS");

        // randomCA.crt should be in the Assets directory
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
/**
        new BufferedInputStream(MainActivity.context.getAssets().open("randomCA.crt"));
        InputStream caInput = new BufferedInputStream(MainActivity.context.getAssets().open("randomCA.crt"));
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
//            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            System.out.println(" here frank");
        } finally {
            caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
**/
        // Create a TrustManager that trusts the CAs in our KeyStore
        tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(trusted);

        // Create an SSLContext that uses our TrustManager
        // SSLContext context = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        System.out.println("We made it this far Frank");

    }

    public SSLContext getContext(){
        return sslContext;
    }
}
