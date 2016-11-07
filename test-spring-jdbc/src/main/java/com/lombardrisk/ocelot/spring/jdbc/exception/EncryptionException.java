package com.lombardrisk.ocelot.spring.jdbc.exception;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Kyle Li on 7/4/2016.
 */
public class EncryptionException extends Exception {
	public EncryptionException(NoSuchAlgorithmException e) {
		super(e);
	}

	public EncryptionException(InvalidKeySpecException e) {
		super(e);
	}

	public EncryptionException(NoSuchPaddingException e) {
		super(e);
	}

	public EncryptionException(InvalidKeyException e) {
		super(e);
	}

	public EncryptionException(InvalidAlgorithmParameterException e) {
		super(e);
	}

	public EncryptionException(IllegalBlockSizeException e) {
		super(e);
	}

	public EncryptionException(BadPaddingException e) {
		super(e);
	}

	public EncryptionException(KeyStoreException e) {
		super(e);
	}

	public EncryptionException(CertificateException e) {
		super(e);
	}
}
