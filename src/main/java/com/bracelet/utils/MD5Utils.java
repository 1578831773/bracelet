package com.bracelet.utils;

import java.security.MessageDigest;
import java.util.Random;

public class MD5Utils
{
	//未使用
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d","e", "f" };

	final static char []codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
	final static int SALT_LENGTH = 8;

	public static String produceSalt()
	{
		StringBuilder randomString= new StringBuilder();
		Random random = new Random();

		for(int i = 0;i < SALT_LENGTH;i++)
		{
			String strRand = null;
			strRand = String.valueOf(codeSequence[random.nextInt(62)]);
			randomString.append(strRand);
		}
		return randomString.toString();
	}

	public static String MD5Encode(String source,String encoding,boolean uppercase)
	{
		String result = null;
		try {
			result = source;

			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.update(result.getBytes(encoding));

			result = byteArrayToHexString(messageDigest.digest());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return uppercase ? result.toUpperCase():result;
	}

	private static String byteArrayToHexString(byte[] bytes)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for(byte tem: bytes)
		{
			stringBuilder.append(byteToHexString(tem));
		}
		return stringBuilder.toString();
	}
	private static Object byteToHexString(byte b)
	{
		int n = b;
		if(n < 0)
		{
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
