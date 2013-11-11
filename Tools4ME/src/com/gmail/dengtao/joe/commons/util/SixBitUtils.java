package com.gmail.dengtao.joe.commons.util;

import java.io.UnsupportedEncodingException;

/**
 * Encod/Decod from mir3
 * @author <a href="mailto:joe.dengtao@gmail.com">DengTao</a>
 * @version 1.0
 * @since 1.0
 */
public class SixBitUtils {
    
	public static String decode(String plain) {
    	return new String(decode(plain.getBytes()));
    }
    
    public static String decode(String plain, String charset) {
    	try {
			return new String(decode(plain.getBytes(charset)), charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
    }
	
    public static byte[] decode(byte[] source) {
        byte[] a1, a2;
        a1 = source;
        a2 = new byte[a1.length * 3 / 4];

        for (int i = 0; i < a1.length; i++) a1[i] = (byte) (a1[i] - 0x3c);

        for (int i = 0, k = a1.length / 4; i < k; i++) {
            a2[i * 3] = (byte) ((byte) (a1[i * 4] << 2) + (byte) (a1[i * 4 + 1] >> 4));
            a2[i * 3 + 1] = (byte) ((byte) (a1[i * 4 + 1] << 4) + (byte) (a1[i * 4 + 2] >> 2));
            a2[i * 3 + 2] = (byte) ((byte) (a1[i * 4 + 2] << 6) + a1[i * 4 + 3]);
        }
        if (a2.length % 3 == 2) {
            a2[a2.length - 2] = (byte) ((byte) (a1[a1.length - 3] << 2) + (byte) (a1[a1.length - 2] >> 4));
            a2[a2.length - 1] = (byte) ((byte) (a1[a1.length - 2] << 4) + (byte) (a1[a1.length - 1] >> 2));
        } else if (a2.length % 3 == 1) {
        	a2[a2.length - 1] = (byte) ((byte) (a1[a1.length - 2] << 2) + (byte) (a1[a1.length - 1] >> 4));
        }

        return a2;
    }
    
    public static String encode(String plain) {
    	return new String(encode(plain.getBytes()));
    }
    
    public static String encode(String plain, String charset) {
    	try {
			return new String(encode(plain.getBytes(charset)), charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
    }
    
    public static byte[] encode(byte[] raw) {
        byte[] a1, a2;
        a1 = raw;
        if (a1.length % 3 == 0) a2 = new byte[a1.length * 4 / 3];
        else a2 = new byte[a1.length * 4 / 3 + 1];

        for (int i = 0, k = a1.length / 3; i < k; i++) {
            a2[i * 4] = (byte) (((a1[i * 3] >> 2) & 0x3f) + 0x3c);
            a2[i * 4 + 1] = (byte) ((((a1[i * 3] << 4) & 0x3f) | ((a1[i * 3 + 1] >> 4) & 0x0f)) + 0x3c);
            a2[i * 4 + 2] = (byte) ((((a1[i * 3 + 1] << 2) & 0x3f) | ((a1[i * 3 + 2] >> 6) & 0x03)) + 0x3c);
            a2[i * 4 + 3] = (byte) ((a1[i * 3 + 2] & 0x3f) + 0x3c);
        }

        if (a1.length % 3 == 1){
            a2[a2.length - 2] = (byte) (((a1[a1.length - 1] >> 2) & 0x3f) + 0x3c);
            a2[a2.length - 1] = (byte) (((a1[a1.length - 1] << 4) & 0x3f) + 0x3c);
        } else if (a1.length % 3 == 2) {
        	a2[a2.length - 3] = (byte) (((a1[a1.length - 2] >> 2) & 0x3f) + 0x3c);
            a2[a2.length - 2] = (byte) ((((a1[a1.length - 2] << 4) & 0x3f) | ((a1[a1.length - 1] >> 4) & 0x0f)) + 0x3c);
            a2[a2.length - 1] = (byte) ((((a1[a1.length - 1] << 2) & 0x3f)) + 0x3c);
        }
        return a2;
    }
    
}
