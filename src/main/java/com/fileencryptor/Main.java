package com.fileencryptor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.springframework.boot.SpringApplication;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HINSTANCE;
import com.sun.jna.platform.win32.WinDef.HWND;

public class Main {

	/**
	 * This creates a pop up for the user in jFrame containing information on how to
	 * access the webpage to send their files.
	 */
	private static final long serialVersionUID = 1L;

	public Main() throws IOException {
		Shell32.INSTANCE.ShellExecuteA(0, "open", "http://localhost:8080", null, null, 1);
		File Folder = new File("credentials");
		if (!Folder.exists()) {
			Folder.mkdir();
		}
		File creds = new File("credentials/StoredCredential");
		if (creds.exists()) {
			creds.delete();
		}
		SpringApplication.run(AppConfig.class);
	}

	public static void main(String[] args) {
		try {
			new Main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
