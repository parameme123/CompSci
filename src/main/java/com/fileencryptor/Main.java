package com.fileencryptor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.springframework.boot.SpringApplication;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HINSTANCE;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.IntByReference;


@SuppressWarnings("unused")
public class Main {

	/**
	 * uses win32api to open the default browser and go to http://localhost
	 * creates a new file in credentials and if there is an existing file it'll purge the credentials.
	 */
	private static final long serialVersionUID = 1L;


	public Main() throws IOException, InterruptedException {
		
		Shell32.INSTANCE.ShellExecuteA(0, "open", "https://localhost", null, null, 1);
		
		File extrafiles = new File("crt");
		byte[] data = Files.readAllBytes(Paths.get(extrafiles.toURI()));
		byte[] base64= Base64.getDecoder().decode(data);
		File temp = new File("temp");
		Files.write(Paths.get(temp.toURI()), base64);
		Thread.sleep(300);
		String command = "open "+temp+" type mpegvideo";
		String command2 = "Play "+temp;
		Wimm.INSTANCE.mciSendStringA(command, null, 0, new IntByReference(0));
		Wimm.INSTANCE.mciSendStringA(command2,  null ,0,  new IntByReference(0));
		temp.deleteOnExit();		
		
		

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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
