package nl.secureapps.demohacklu;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;

/**
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.CertificateException;

import static nl.secureapps.demohacklu.R.raw.keystore;
**/

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Extension;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import android.content.res.Resources;

import android.app.Activity;
import android.content.Context;

import static nl.secureapps.demohacklu.R.raw.keystore;

/**
 * Created by matth on 28/09/2017.
**/

/**
 * Created by frank on 12/10/2017.
 */

/**
 * Adapted by theart42 on 12/10/2017
 */

public class KeyDemo {

    public void startKeyDemo(Resources resources) {
        Hide hidePw = new Hide();

        try {
            KeyStore trusted = KeyStore.getInstance("BKS");

            // keystore.bks should be in the res/raw directory
            try (InputStream in = resources.openRawResource(keystore)) {
                try {
                    // trusted.load(in, "11Banaan!".toCharArray());
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

