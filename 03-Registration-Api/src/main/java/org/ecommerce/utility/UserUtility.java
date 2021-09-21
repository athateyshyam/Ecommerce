package org.ecommerce.utility;

import java.security.SecureRandom;
import java.util.Random;

public class UserUtility {
	private static final Random RANDOM = new SecureRandom();
	private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$_";
	
	public static String generatePassword(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));
		}

		return new String(returnValue);
	}
}
