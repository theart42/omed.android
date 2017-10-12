package nl.secureapps.demohacklu;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StreamTokenizer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static nl.secureapps.demohacklu.KeyDemo.*;


/**
 * Created by matth on 28/09/2017.
 */

public class HttpsDemo {

    private static String random(int length) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(length);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void startHttpsDemo() {
        InputStream inputStream = null;
        String urlString = null;

        try {

            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented, TODO: implement!
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented, TODO: implement!
                }
            }};

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // ip address of the service URL(like.23.28.244.244)
                    // if (hostname.equals("23.28.244.244")) {
                    //     return true;
                    // }
                    return true;
                }
            });

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            urlString = "https://2017.hack.lu/?xyz=".concat(random(8));

            MainActivity.processOutput("Calling ".concat(urlString));

            URL url = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();

            MainActivity.processOutput(responseMessage);

            Certificate[] serverCerts = urlConnection.getServerCertificates();
            MainActivity.processOutput(serverCerts[0].toString());


            if(responseCode < 400) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
            }
        } catch (Exception e) {
            Log.e("HttpsURLConnection", "exception", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public void startHttpsPinDemo(Resources resources) {

        try {
            //Getting the keystore
            KeyPinStore keystore = KeyPinStore.getInstance(resources);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL( "https://www.agilesecurity.nl/?xyz=".concat(random(8)));

            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setSSLSocketFactory(keystore.getContext().getSocketFactory());
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            MainActivity.processOutput(stringBuilder.toString());

        } catch (Exception e) {
            // Log error
            MainActivity.processOutput(e.toString());
        }
    }


/**
    // Deze code wordt niet gebruikt in deze opzet. ter inspiratie ...
    private void een_startpunt_voor_https_en_certificaat_controles_en_pinning_ter_inspiratie() {
        InputStream inputStream = null;

        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented, TODO: implement!
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    // Not implemented, TODO: implement!
                }
            }};

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // ip address of the service URL(like.23.28.244.244)
                    // if (hostname.equals("23.28.244.244")) {
                    //     return true;
                    // }
                    return true;
                }
            });

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            URL url = new URL("HTTPS://....");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();

            if(responseCode == 401) {

            }
            if(responseCode < 400) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
            }
        } catch (Exception e) {
            //Do stuff
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
            }
        }
    }
**/
}


