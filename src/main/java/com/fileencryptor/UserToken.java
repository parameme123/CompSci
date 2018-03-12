package com.fileencryptor;
/*
 * Google provides a token, this class is used to retrieve the token for identification.
 */
public class UserToken {

	private static String token;

	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		UserToken.token = token;
	}
}
