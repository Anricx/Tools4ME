package com.gmail.dengtao.joe.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unicode encode and decode.
 * @author <a href="mailto:joe.dengtao@gmail.com">DengTao</a>
 * @version 1.0
 * @since 1.0
 */
public class UnicodeUtils {
	
	/**
	 * <p>Encode input string, return the Unicode style</p>
	 * @param string
	 * @return
	 * @since 1.0
	 */
	public static String encode(String string) {
		char[] chars = string.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = chars.length; i < len; i++) {
			 if(chars[i] >= 127 || chars[i] < 0) {
				 sb.append("\\u" + Integer.toHexString(chars[i]));
			 } else {
				 sb.append(chars[i]);
			 }
		}
		return sb.toString();
	}
	
	/**
	 * <p>Decode input Unicode style String, return the original string</p>
	 * @param unicode
	 * @return 
	 * @since 1.0
	 */
	public static String decode(String unicode) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
        Matcher matcher = pattern.matcher(unicode);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicode = unicode.replace(matcher.group(1), ch + "");    
        }
        return unicode;
	}

}
