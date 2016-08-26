package com.lombardrisk.ocelot.spring.jdbc.sign;


import com.lombardrisk.ocelot.spring.jdbc.exception.EncryptionException;
import com.lombardrisk.ocelot.spring.jdbc.exception.KeyCreateException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by jason on 2016/3/26.
 */
public class RSAUtils {
    // Process input/output streams in chunks - arbitrary
    private static final int BUFFER_SIZE = 1024 * 10;

    public static final String KEY_ALGORITHM = "RSA";

    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * Get the public and private key pair
     * @return
     * @throws Exception
     */
    public static KeyPair genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        return   keyPairGen.generateKeyPair();
    }

    /**
     * sign the data by the private key
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey)
            throws NoSuchAlgorithmException, KeyCreateException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(getPrivateKey(privateKey));
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }


    public static Signature getSignatureWithPrivateKey(String privateKey) throws SignatureException {
        Signature signature = null;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(getPrivateKey(privateKey));
            return signature;
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException(e);
        } catch (InvalidKeyException e) {
            throw new SignatureException(e);
        } catch (KeyCreateException e) {
            throw new SignatureException(e);
        }
    }

    public static Signature getSignatureWithPublicKey(String publicKey) throws SignatureException  {
        Signature signature = null;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(getPublicKey(publicKey));
            return signature;
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException(e);
        } catch (InvalidKeyException e) {
            throw new SignatureException(e);
        } catch (KeyCreateException e) {
            throw new SignatureException(e);
        }
    }

    public static void  updateSignature(Signature signature, InputStream inputStream)
            throws  SignatureException {
        // read data from input into buffer, encrypt and write to output
        byte[] buffer = new byte[BUFFER_SIZE];
        int numRead;
        try {
            while ((numRead = inputStream.read(buffer)) > 0) {
                signature.update(buffer,0,numRead);

            }
        } catch (IOException e) {
            throw new SignatureException(e);
        } catch (SignatureException e) {
            throw new SignatureException(e);
        }

    }

    public static String sign(InputStream inputStream, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);

        // read data from input into buffer, encrypt and write to output
        byte[] buffer = new byte[BUFFER_SIZE];
        int numRead;
        while ((numRead = inputStream.read(buffer)) > 0) {
            signature.update(buffer,0,numRead);

        }
        return Base64.encodeBase64String(signature.sign());
    }

    public static String encodeSignature(byte[] signature) {
        return Base64.encodeBase64String(signature);
    }

    public static String sign(InputStream inputStream, String privateKey)
            throws KeyCreateException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return sign(inputStream, getPrivateKey(privateKey));
    }

    public static boolean verify(InputStream inputStream, String sign, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);

        // read data from input into buffer, encrypt and write to output
        byte[] buffer = new byte[BUFFER_SIZE];
        int numRead;
        while ((numRead = inputStream.read(buffer)) > 0) {
            signature.update(buffer,0,numRead);

        }

        return  signature.verify(Base64.decodeBase64(sign));
    }

    public static boolean verify(InputStream inputStream, String sign, String publicKey)
            throws KeyCreateException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return verify(inputStream, sign, getPublicKey(publicKey));
    }

    /**
     * verify the signature by the public key and data
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws NoSuchAlgorithmException, KeyCreateException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(getPublicKey(publicKey));
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }


    /**
     * create Private key object
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey)  throws KeyCreateException {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new KeyCreateException(e);
        } catch (InvalidKeySpecException e) {
            throw new KeyCreateException(e);
        }

    }

    private  static byte[] decrypt(byte[] encryptedData, Cipher cipher) throws EncryptionException {
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            return decryptedData;
        } catch (BadPaddingException e) {
            throw new EncryptionException(e);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException(e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    /**
     * decrypt by use private key
     * @param encryptedData
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, EncryptionException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return decrypt(encryptedData, cipher);

    }
    /**
     * decrypt by use private key
     * @param encryptedData
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws KeyCreateException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            EncryptionException {
        return decryptByPrivateKey(encryptedData, getPrivateKey(privateKey));
    }
    /**
     * decrypt by use public key
     * @param encryptedData
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, PublicKey publicKey) throws EncryptionException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return decrypt(encryptedData, cipher);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptionException(e);
        } catch (InvalidKeyException e) {
            throw new EncryptionException(e);
        }

    }

    /**
     * decrypt by use public key
     * @param encryptedData
     * @param publicKey
     * @return
     * @throws EncryptionException
     * * @throws KeyCreateException
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws EncryptionException, KeyCreateException {
        return decryptByPublicKey(encryptedData, getPublicKey(publicKey));
    }

    /**
     * get public key from string
     * @param publicKey
     * @return
     * @throws KeyCreateException
     */
    public static PublicKey getPublicKey(String publicKey) throws KeyCreateException {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return  keyFactory.generatePublic(x509KeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new KeyCreateException(e);
        }catch (InvalidKeySpecException e) {
            throw new KeyCreateException(e);
        }

    }
    /**
     * encrypt by use public key
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return encrypt(data, cipher);
        } catch (InvalidKeyException e) {
            throw new EncryptionException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptionException(e);
        }
    }

    /**
     * encrypt by use public key
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws KeyCreateException, EncryptionException {
        return encryptByPublicKey(data, getPublicKey(publicKey));
    }

    private static byte[] encrypt(byte[] data, Cipher cipher) throws EncryptionException {
        int inputLen = data.length;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return encryptedData;
        } catch (BadPaddingException e) {
            throw new EncryptionException(e);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException(e);
        } finally {
            IOUtils.closeQuietly(out);

        }
    }
    /**
     * encrypt by private key
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws EncryptionException {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return encrypt(data, cipher);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptionException(e);
        }   catch (InvalidKeyException e) {
            throw new EncryptionException(e);
        }

    }
    /**
     * encrypt by private key
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws EncryptionException,
            KeyCreateException {
        return encryptByPrivateKey(data, getPrivateKey(privateKey));
    }

    /**
     * get private key from key pair
     * @param keyPair
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(KeyPair keyPair) {
        return Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
    }

    /**
     * get the public key from key pair
     * @param keyPair
     * @return
     * @throws Exception
     */
    public static String getPublicKey(KeyPair keyPair)  {
        return Base64.encodeBase64String(keyPair.getPublic().getEncoded());
    }

}