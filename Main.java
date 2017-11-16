package FileEncr;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.springframework.boot.SpringApplication;

public class Main extends JFrame implements WindowListener {

	/**
	 * 
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
		setSize(300, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
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

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		File creds = new File("credentails/StoredCredential");
		creds.deleteOnExit();

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		File creds = new File("credentails/StoredCredential");
		creds.deleteOnExit();

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
