package com.o2o.massage.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author bieyong
 * @since 1.0
 */
public class CodecUtils {

	public static String base64Encode(String str) {

		byte[] b = Base64.encodeBase64(str.getBytes(), true);
		return new String(b);
	}

	/**
	 * 
	 * 使用Base64解密 return
	 */
	public static String base64Decode(String encodeStr) {
		byte[] b = Base64.decodeBase64(encodeStr.getBytes());
		return new String(b);
	}

	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}

}
