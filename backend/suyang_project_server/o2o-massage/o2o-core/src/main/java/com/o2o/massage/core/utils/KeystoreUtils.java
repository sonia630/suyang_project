package com.o2o.massage.core.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class KeystoreUtils {

    public static KeyStore getKeyStore(String file, String type, String passwd) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
        if(StringUtils.isBlank(file) || StringUtils.isBlank(type) || StringUtils.isBlank(passwd)) throw new IllegalArgumentException("path, type, passwd");
        KeyStore keyStore = KeyStore.getInstance(type);
        keyStore.load(KeystoreUtils.class.getResourceAsStream(file), passwd.toCharArray());
        return keyStore;
    }

    /*
    public static KeyPair  getRomcaKeyPair() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        KeyStore keyStore = getKeyStore();
//        KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("romca", new KeyStore.PasswordProtection(password));
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("romca", "000000".toCharArray());
        Certificate certificate = keyStore.getCertificate("romca");
        PublicKey publicKey = certificate.getPublicKey();
        return new KeyPair(publicKey, privateKey);
    }

    public static KeyPair getSpacesKeyPair() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {
        KeyStore keyStore = getKeyStore();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("spaces", "000000".toCharArray());
        Certificate certificate = keyStore.getCertificate("spaces");
        PublicKey publicKey = certificate.getPublicKey();
        return new KeyPair(publicKey, privateKey);
    }
    */

    public static KeyPair getKeyPair(KeyStore keyStore, String alias, String passwd) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        if(keyStore == null) throw new NullPointerException("keystore");
        if(StringUtils.isBlank(alias) || StringUtils.isBlank(passwd)) throw new IllegalArgumentException("alias, passed");
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, passwd.toCharArray());
        Certificate certificate = keyStore.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();
        return new KeyPair(publicKey, privateKey);
    }
}
