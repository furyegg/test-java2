package com.lombardrisk.ocelot.spring.jdbc.sign;

import org.apache.commons.codec.binary.Base64;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by jason zhang on 3/29/2016.
 */
public class SignatureInputStream extends FilterInputStream {

	private final Signature signature;

	/**
	 * Creates a <code>FilterInputStream</code>
	 * by assigning the  argument <code>in</code>
	 * to the field <code>this.in</code> so as
	 * to remember it for later use.
	 *
	 * @param in the underlying input stream, or <code>null</code> if
	 * this instance is to be created without an underlying stream.
	 */
	public SignatureInputStream(InputStream in, String publicKey) throws SignatureException {
		super(in);
		this.signature = RSAUtils.getSignatureWithPublicKey(publicKey);
	}

	/**
	 * Creates a <code>FilterInputStream</code>
	 * by assigning the  argument <code>in</code>
	 * to the field <code>this.in</code> so as
	 * to remember it for later use.
	 *
	 * @param in the underlying input stream, or <code>null</code> if
	 * this instance is to be created without an underlying stream.
	 */
	public SignatureInputStream(InputStream in, Signature signature) throws SignatureException {
		super(in);
		this.signature = signature;
	}

	@Override
	public int read() throws IOException {
		int data = in.read();
		try {
			if (data > -1) {
				signature.update((byte) data);
			}
			return data;
		} catch (SignatureException e) {
			throw new IOException(e);
		}
	}

	@Override
	public int read(byte[] b) throws IOException {
		int numRead = in.read(b);
		try {
			if (numRead > -1) {
				signature.update(b, 0, numRead);
			}
		} catch (SignatureException e) {
			throw new IOException(e);
		}
		return numRead;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int numRead = in.read(b, off, len);
		try {
			if (numRead > 0) {
				signature.update(b, 0, numRead);
			}
		} catch (SignatureException e) {
			throw new IOException(e);
		}
		return numRead;
	}

	@Override
	public void close() throws IOException {
		this.in.close();
	}

	public boolean verify(String sign) throws SignatureException {
		return signature.verify(Base64.decodeBase64(sign));
	}
}
