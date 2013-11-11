package com.gmail.dengtao.joe.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import com.gmail.dengtao.joe.commons.util.Base64Utils;
import com.gmail.dengtao.joe.commons.util.HexUtils;
import com.gmail.dengtao.joe.commons.util.StringUtils;

/**
 * MD5 encrypt.
 * @author <a href="mailto:joe.dengtao@gmail.com">DengTao</a>
 * @version 1.0
 * @since 1.0
 */
public class MD5 {
	
	/** Stream buffer length */
    private static final int STREAM_BUFFER_LENGTH = 1024;
	
	/**
	 * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest
	 * @since 1.0
	 */
	public static byte[] encrypt(byte[] data) {
		return DigestUtils.getDigest(DigestUtils.MD5).digest(data);
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a base64 string
	 * @since 1.0
	 */
	public static String encryptBase64(byte[] data) {
		return Base64Utils.bytesToBase64(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a hex string
	 * @since 1.0
	 */
	public static String encryptHex(byte[] data) {
		return HexUtils.bytesToHex(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a upper case hex string
	 * @since 1.0
	 */
	public static String encryptHEX(byte[] data) {
		return HexUtils.bytesToHEX(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest
	 * @throws IOException 
	 * @since 1.0
	 */
	public static byte[] encrypt(InputStream data) throws IOException {
		byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        MessageDigest digest = DigestUtils.getDigest(DigestUtils.MD5);
        
        while (read > -1) {
        	digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }
        return digest.digest();
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a base64 string
	 * @throws IOException 
	 * @since 1.0
	 */
	public static String encryptBase64(InputStream data) throws IOException {
        return Base64Utils.bytesToBase64(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a hex string
	 * @throws IOException 
	 * @since 1.0
	 */
	public static String encryptHex(InputStream data) throws IOException {
        return HexUtils.bytesToHex(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a upper case hex string
	 * @throws IOException 
	 * @since 1.0
	 */
	public static String encryptHEX(InputStream data) throws IOException {
		return HexUtils.bytesToHEX(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest
	 * @since 1.0
	 */
	public static byte[] encrypt(String data) {
		return encrypt(data.getBytes());
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a base64 string
	 * @since 1.0
	 */
	public static String encryptBase64(String data) {
		return Base64Utils.bytesToBase64(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a hex string
	 * @since 1.0
	 */
	public static String encryptHex(String data) {
		return HexUtils.bytesToHex(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * 
	 * @param data Data to digest
	 * @return MD5 digest as a upper case hex string
	 * @since 1.0
	 */
	public static String encryptHEX(String data) {
		return HexUtils.bytesToHEX(encrypt(data));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
	 * @param data Data to digest
	 * @param charset The name of the requested charset, may be null.
	 * @return MD5 digest
	 * @since 1.0
	 */
	public static byte[] encrypt(String data, String charset) {
		return encrypt(StringUtils.getBytes(data, charset));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * @param data Data to digest
	 * @param charset The name of the requested charset, may be null.
	 * @return MD5 digest as a base64 string
	 * @since 1.0
	 */
	public static String encryptBase64(String data, String charset) {
		return Base64Utils.bytesToBase64(encrypt(data, charset));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * @param data Data to digest
	 * @param charset The name of the requested charset, may be null.
	 * @return MD5 digest as a hex string
	 * @since 1.0
	 */
	public static String encryptHex(String data, String charset) {
		return HexUtils.bytesToHex(encrypt(data, charset));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * @param data Data to digest
	 * @param charset The name of the requested charset, may be null.
	 * @return MD5 digest as a upper case hex string
	 * @since 1.0
	 */
	public static String encryptHEX(String data, String charset) {
		return HexUtils.bytesToHEX(encrypt(data, charset));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
	 * @param data Data to digest
	 * @param charset The {@link Charset} to encode the {@code String}
	 * @return MD5 digest
	 * @since 1.0
	 */
	public static byte[] encrypt(String data, Charset charset) {
		return encrypt(StringUtils.getBytes(data, charset));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * @param data Data to digest
	 * @param charset The {@link Charset} to encode the {@code String}
	 * @return MD5 digest as a base64 string
	 * @since 1.0
	 */
	public static String encryptBase64(String data, Charset charset) {
		return Base64Utils.bytesToBase64(encrypt(data, charset));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * @param data Data to digest
	 * @param charset The {@link Charset} to encode the {@code String}
	 * @return MD5 digest as a hex string
	 * @since 1.0
	 */
	public static String encryptHex(String data, Charset charset) {
		return HexUtils.bytesToHex(encrypt(data, charset));
	}
	
	/**
	 * Calculates the MD5 digest and returns the value as a hex string.
	 * @param data Data to digest
	 * @param charset The {@link Charset} to encode the {@code String}
	 * @return MD5 digest as a upper case hex string
	 * @since 1.0
	 */
	public static String encryptHEX(String data, Charset charset) {
		return HexUtils.bytesToHEX(encrypt(data, charset));
	}
}