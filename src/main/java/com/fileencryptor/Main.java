package com.fileencryptor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.springframework.boot.SpringApplication;

public class Main extends JFrame implements WindowListener {

	/**
	 * This creates a pop up for the user in jFrame containing information on how to access the webpage to send their files.
	 */
	private static final long serialVersionUID = 1L;

	public Main() throws IOException {
		File Folder = new File("credentials");
		if (!Folder.exists()) {
			Folder.mkdir();
		}

		File creds = new File("credentials/StoredCredential");
		if (creds.exists()) {
			creds.delete();
		}
		this.setTitle("Web");
		setSize(500, 150);
		this.add(new JLabel("Web Server running  in the background please go to http://localhost:8080 in your browser"));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
		this.pack();
		this.setResizable(false);
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

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This purges credentials for user security. 
	 */
	public void windowClosed(WindowEvent arg0) {
		File creds = new File("credentails/StoredCredential");
		creds.deleteOnExit(); 

	}

	public void windowClosing(WindowEvent arg0) {
		File creds = new File("credentails/StoredCredential");
		creds.deleteOnExit();

	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}
}
