package com.example.generator.util;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author changbo
 */
@Slf4j
public class SslUtil {

    public static SSLSocketFactory createSSLSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{createTrustManager()}, new SecureRandom());
            return sc.getSocketFactory();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public static SSLSocketFactory createSSLSocketFactory(byte[] certFile, String certKey) {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, createTrustManager(certFile, certKey), new SecureRandom());
            return sc.getSocketFactory();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public static X509TrustManager createTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static TrustManager[] createTrustManager(byte[] certFile, String certKey) throws Exception {
        KeyStore keyStore = CertUtil.loadKeyStore(certFile, certKey);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        return trustManagerFactory.getTrustManagers();
    }

    public static HostnameVerifier createHostnameVerifier() {
        return (hostname, session) -> true;
    }

}
