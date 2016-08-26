package com.lombardrisk.ocelot.spring.jdbc.sign;


import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by jason zhang on 3/29/2016.
 */
public class SignatureOutputStream extends FilterOutputStream    {

    private final  Signature signature;

    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param out the underlying output stream to be assigned to
     *            the field <tt>this.out</tt> for later use, or
     *            <code>null</code> if this instance is to be
     *            created without an underlying stream.
     */
    public SignatureOutputStream(OutputStream out,String privateKey) throws SignatureException {
        super(out);
        this.signature = RSAUtils.getSignatureWithPrivateKey(privateKey);

    }

    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param out the underlying output stream to be assigned to
     *            the field <tt>this.out</tt> for later use, or
     *            <code>null</code> if this instance is to be
     *            created without an underlying stream.
     */
    public SignatureOutputStream(OutputStream out,Signature signature) throws SignatureException {
        super(out);
        this.signature = signature;

    }
    @Override
    public void write(byte[] b) throws IOException {
        this.out.write(b);
        try {
            signature.update(b,0,b.length);
        } catch (SignatureException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
        try {
            signature.update(b,off,len);
        } catch (SignatureException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
        byte[] bytes =new byte[1];
        bytes[0] = (byte)b;
        try {
            signature.update(bytes,0,1);
        } catch (SignatureException e) {
            throw new IOException(e);
        }

    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    public String getSignature() throws SignatureException {
        try {
            return RSAUtils.encodeSignature(signature.sign());
        } catch (SignatureException e) {
            throw new SignatureException(e);
        }
    }
}
