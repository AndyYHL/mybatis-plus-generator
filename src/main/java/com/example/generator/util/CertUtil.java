package com.example.generator.util;

import com.example.generator.pojo.exception.BasicException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;

/**
 * CertUtil
 *
 * @author CertUtil
 */
@Slf4j
@UtilityClass
public class CertUtil {

    private static final String ALGORITHM = "RSA";
    private static final String SIGNATURE = "SHA256withRSA";
    private static final String CERT_TYPE = "X.509";
    private static final String KEY_TYPE = "PKCS12";

    /**
     * RSA算法规定：待加密的字节数不能超过密钥的长度值除以8再减去11。
     * 而加密后得到密文的字节数，正好是密钥的长度值除以 8。
     */
    private static final int RESERVE_BYTES = 11;
    private static final int BIT = 8;

    public static X509Certificate certificate(String cert) throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cert.getBytes(StandardCharsets.UTF_8));
        return (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
    }

    public static X509Certificate certificate(File cer) throws CertificateException, FileNotFoundException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        FileInputStream fileInputStream = new FileInputStream(cer);
        return (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
    }

    public static PublicKey publicKey(String cert) throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cert.getBytes(StandardCharsets.UTF_8));
        X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
        return x509Certificate.getPublicKey();
    }

    public static PublicKey publicKey(File cer) throws FileNotFoundException, CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        FileInputStream fileInputStream = new FileInputStream(cer);
        X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
        return x509Certificate.getPublicKey();
    }

    public static PrivateKey privateKey(File pfx, String password) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        KeyStore keyStore = keyStore(pfx, password);
        return privateKey(keyStore, password);
    }

    public static PrivateKey privateKey(InputStream inputStream, String password) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = keyStore(inputStream, password);
        return privateKey(keyStore, password);
    }

    private PrivateKey privateKey(KeyStore keyStore, String password) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        char[] p = password.toCharArray();
        Enumeration<String> enumeration = keyStore.aliases();
        String alias = null;
        if (enumeration != null && enumeration.hasMoreElements()) {
            alias = enumeration.nextElement();
        }
        return (PrivateKey) keyStore.getKey(alias, p);
    }

    private static KeyStore keyStore(File pfx, String password) {
        try (FileInputStream fileInputStream = new FileInputStream(pfx)) {
            char[] p = password.toCharArray();
            KeyStore keyStore = KeyStore.getInstance(KEY_TYPE);
            keyStore.load(fileInputStream, p);
            return keyStore;
        } catch (Exception e) {
            log.error("reading pfx file exception", e);
            throw new BasicException("reading pfx file exception");
        }
    }

    private static KeyStore keyStore(InputStream inputStream, String password) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        char[] p = password.toCharArray();
        KeyStore keyStore = KeyStore.getInstance(KEY_TYPE);
        keyStore.load(inputStream, p);
        return keyStore;
    }

    public static String encrypt(String cert, String plains) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        RSAPublicKey publicKey = (RSAPublicKey) publicKey(cert);
        return encrypt(publicKey, plains);
    }

    public static String encrypt(File cer, String plains) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, CertificateException, FileNotFoundException {
        RSAPublicKey publicKey = (RSAPublicKey) publicKey(cer);
        return encrypt(publicKey, plains);
    }

    public static String encrypt(PublicKey publicKey, String plains) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        return encrypt(rsaPublicKey, plains);
    }

    private String encrypt(RSAPublicKey rsaPublicKey, String plains) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        BigInteger modulus = rsaPublicKey.getModulus();
        int bitLength = modulus.bitLength();
        int block = (bitLength / BIT) - RESERVE_BYTES;
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] bytes = cipher(cipher, block, plains.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(File pfx, String password, String ciphers) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPrivateKey privateKey = (RSAPrivateKey) privateKey(pfx, password);
        return decrypt(privateKey, ciphers);
    }

    public static String decrypt(InputStream inputStream, String password, String ciphers) throws UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPrivateKey privateKey = (RSAPrivateKey) privateKey(inputStream, password);
        return decrypt(privateKey, ciphers);
    }

    public static String decrypt(PrivateKey privateKey, String ciphers) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        return decrypt(rsaPrivateKey, ciphers);
    }

    private String decrypt(RSAPrivateKey rsaPrivateKey, String ciphers) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        BigInteger modulus = rsaPrivateKey.getModulus();
        int bitLength = modulus.bitLength();
        int block = (bitLength / BIT);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] bytes = cipher(cipher, block, Base64.getDecoder().decode(ciphers));
        return new String(bytes);
    }

    public static String sign(File pfx, String password, String data) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        PrivateKey privateKey = privateKey(pfx, password);
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    public static String sign(InputStream inputStream, String password, String data) throws UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, InvalidKeyException, SignatureException {
        PrivateKey privateKey = privateKey(inputStream, password);
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    public static String sign(PrivateKey privateKey, String data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initSign(rsaPrivateKey);
        signature.update(data.getBytes());
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    public static boolean verify(String cert, String data, String sign) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        PublicKey publicKey = publicKey(cert);
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

    public static boolean verify(File cer, String data, String sign) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, FileNotFoundException {
        PublicKey publicKey = publicKey(cer);
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

    public static boolean verify(PublicKey publicKey, String data, String sign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        Signature signature = Signature.getInstance(SIGNATURE);
        signature.initVerify(rsaPublicKey);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

    /**
     * 分段加密解密
     *
     * @param cipher 加密器
     * @param input  原始数据
     * @return 分段加密或解密后的结果
     * @throws BadPaddingException       e
     * @throws IllegalBlockSizeException e
     */
    private static byte[] cipher(Cipher cipher, int block, byte[] input) throws BadPaddingException, IllegalBlockSizeException {
        int length = input.length;
        int offset = 0;
        byte[] bytes = new byte[0];
        byte[] cache;
        while (length - offset > 0) {
            if (length - offset > block) {
                cache = cipher.doFinal(input, offset, block);
                offset += block;
            } else {
                cache = cipher.doFinal(input, offset, length - offset);
                offset = length;
            }
            bytes = Arrays.copyOf(bytes, bytes.length + cache.length);
            System.arraycopy(cache, 0, bytes, bytes.length - cache.length, cache.length);
        }
        return bytes;
    }

    /**
     * 获取key
     *
     * @param fileBytes
     * @param certKey
     * @return
     * @throws IOException
     */
    public static KeyStore loadKeyStore(byte[] fileBytes, String certKey) throws IOException {
        try (ByteArrayInputStream fis = new ByteArrayInputStream(fileBytes)) {
            char[] keyChars = null;
            if (StringUtils.isNotBlank(certKey)) {
                keyChars = certKey.toCharArray();
            }
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, keyChars);
            return keyStore;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }
}
