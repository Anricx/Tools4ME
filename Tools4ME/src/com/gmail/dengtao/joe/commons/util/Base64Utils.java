package com.gmail.dengtao.joe.commons.util;

import java.util.Arrays;

/**
 * Provides Base64 encoding and decoding as defined by <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>.
 * 
 * @author <a href="mailto:joe.dengtao@gmail.com">DengTao</a>
 * @version 1.0
 * @since 1.0
 */
public class Base64Utils {

	private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private static final int[] IA = new int[256];
	private static final char EQUAL = '=';
	private static final char NEW_LINE = '\r';
	
	static {
		Arrays.fill(IA, -1);
		for (int i = 0, iS = CA.length; i < iS; i++)
			IA[CA[i]] = i;
		IA[EQUAL] = 0;
	}
	
	/**
	 * Converts string into a base 64 encoded string.
	 * @param plain
	 * @return
	 * @since 1.0
	 */
	public static String encode(String plain) {
		return bytesToBase64(plain.getBytes());
	}
	
	/**
	 * Converts string into a base 64 encoded string.
	 * @param plain
	 * @param charset
	 * @return
	 * @since 1.0
	 */
	public static String encode(String plain, String charset) {
		return bytesToBase64(StringUtils.getBytes(plain, charset));
	}
	
	/**
	 * Decode a base64 string into plain string.
	 * @param base64
	 * @return plain string
	 * @since 1.0
	 */
	public static String decode(String base64) {
		return new String(base64ToBytes(base64));
	}
	
	/**
	 * Decode a base64 string into plain string.
	 * @param base64
	 * @param charset
	 * @return the base64 string decode
	 * @since 1.0
	 */
	public static String decode(String base64, String charset) {
		return StringUtils.toString(base64ToBytes(base64), charset);
	}

	/**
	 * Converts a byte array into a base 64 encoded string.
	 * 
	 * @param sArr
	 *            a byte array, which may be null or empty
	 * @return A BASE64 encoded array. Never <code>null</code>.
	 * @since 1.0
	 */
	public static String bytesToBase64(byte[] sArr) {
		// Check special case
		int sLen = sArr != null ? sArr.length : 0;
		if (sLen == 0)
			return StringUtils.EMPTY;

		int eLen = (sLen / 3) * 3;              // Length of even 24-bits.
		int cCnt = ((sLen - 1) / 3 + 1) << 2;   // Returned character count
		char[] dArr = new char[cCnt];

		// Encode even 24-bits
		for (int s = 0, d = 0; s < eLen;) {
			// Copy next three bytes into lower 24 bits of int, paying attension to sign.
			int i = (sArr[s++] & 0xff) << 16 | (sArr[s++] & 0xff) << 8 | (sArr[s++] & 0xff);

			// Encode the int into four chars
			dArr[d++] = CA[(i >>> 18) & 0x3f];
			dArr[d++] = CA[(i >>> 12) & 0x3f];
			dArr[d++] = CA[(i >>> 6) & 0x3f];
			dArr[d++] = CA[i & 0x3f];
		}

		// Pad and encode last bits if source isn't even 24 bits.
		int left = sLen - eLen; // 0 - 2.
		if (left > 0) {
			// Prepare the int
			int i = ((sArr[eLen] & 0xff) << 10) | (left == 2 ? ((sArr[sLen - 1] & 0xff) << 2) : 0);

			// Set last four chars
			dArr[cCnt - 4] = CA[i >> 12];
			dArr[cCnt - 3] = CA[(i >>> 6) & 0x3f];
			dArr[cCnt - 2] = left == 2 ? CA[i & 0x3f] : EQUAL;
			dArr[cCnt - 1] = EQUAL;
		}
		return new String(dArr);
	}
	
	/**
	 * Decode a base64 string into a byte array.
	 * @param s the encoded data.
	 * @return a byte array.
	 * @since 1.0
	 */
	public static byte[] base64ToBytes(String s) {
		// Check special case
		int sLen = s.length();
		if (sLen == 0)
			return new byte[0];

		int sIx = 0, eIx = sLen - 1; // Start and end index after trimming.

		// Trim illegal chars from start
		while (sIx < eIx && IA[s.charAt(sIx) & 0xff] < 0)
			sIx++;

		// Trim illegal chars from end
		while (eIx > 0 && IA[s.charAt(eIx) & 0xff] < 0)
			eIx--;

		// get the padding count (=) (0, 1 or 2)
		int pad = s.charAt(eIx) == EQUAL ? (s.charAt(eIx - 1) == EQUAL ? 2 : 1) : 0; // Count
																					// '='
																					// at
																					// end.
		int cCnt = eIx - sIx + 1; // Content count including possible separators
		int sepCnt = sLen > 76 ? (s.charAt(76) == NEW_LINE ? cCnt / 78 : 0) << 1
				: 0;

		int len = ((cCnt - sepCnt) * 6 >> 3) - pad; // The number of decoded
													// bytes
		byte[] dArr = new byte[len]; // Preallocate byte[] of exact length

		// Decode all but the last 0 - 2 bytes.
		int d = 0;
		for (int cc = 0, eLen = (len / 3) * 3; d < eLen;) {
			// Assemble three bytes into an int from four "valid" characters.
			int i = IA[s.charAt(sIx++)] << 18 | IA[s.charAt(sIx++)] << 12
					| IA[s.charAt(sIx++)] << 6 | IA[s.charAt(sIx++)];

			// Add the bytes
			dArr[d++] = (byte) (i >> 16);
			dArr[d++] = (byte) (i >> 8);
			dArr[d++] = (byte) i;

			// If line separator, jump over it.
			if (sepCnt > 0 && ++cc == 19) {
				sIx += 2;
				cc = 0;
			}
		}

		if (d < len) {
			// Decode last 1-3 bytes (incl '=') into 1-3 bytes
			int i = 0;
			for (int j = 0, con = eIx - pad; sIx <= con; j++)
				i |= IA[s.charAt(sIx++)] << (18 - j * 6);

			for (int r = 16; d < len; r -= 8)
				dArr[d++] = (byte) (i >> r);
		}

		return dArr;
	}

}
