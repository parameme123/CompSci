package com.fileencryptor;

import java.io.IOException;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

public class CustomAuthorizationCodeInstalledApp extends AuthorizationCodeInstalledApp {

	public CustomAuthorizationCodeInstalledApp(AuthorizationCodeFlow flow, VerificationCodeReceiver receiver) {
		super(flow, receiver);
		
	}
	
	/**
	 * Override Google api, opens up auth url via win32api shell 
	 */
	
	 protected void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) throws IOException {
		 	String url = authorizationUrl.build();
		 	Shell32.INSTANCE.ShellExecuteA(0, "open", url, null, null, 1);
		    browse(url);
	 }
	

}
