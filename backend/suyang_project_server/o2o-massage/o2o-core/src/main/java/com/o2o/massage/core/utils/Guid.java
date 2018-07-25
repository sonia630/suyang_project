package com.o2o.massage.core.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 几种生成唯一性ID的方法
 * 
 * @author QYB
 * 
 */
public class Guid {

	private static SecureRandom random = new SecureRandom();

	/**
	 * UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String g() {
		return g2().replaceAll("-", "");
	}

	/**
	 * UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String g2() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

}
