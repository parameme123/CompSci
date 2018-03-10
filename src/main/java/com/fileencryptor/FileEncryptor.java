package com.fileencryptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Random;

/**
 * 
 * 
 * <pre> 
	//////////////////////////////////////////////
	//..........................................//	
	//..……………………▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄...............//
	//..……………▄▄█▓▓▓▒▒▒▒▒▒▒▒▒▒▓▓▓▓█▄▄............//
	//..…………▄▀▀▓▒░░░░░░░░░░░░░░░░▒▓▓▀▄..........//
	//..………▄▀▓▒▒░░░░░░░░░░░░░░░░░░░▒▒▓▀▄........//
	//..…..█▓█▒░░░░░░░░░░░░░░░░░░░░░▒▓▒▓█.......//
	//....▌▓▀▒░░░░░░░░░░░░░░░░░░░░░░░░▒▀▓█......//
	//....█▌▓▒▒░░░░░░░░░░░░░░░░░░░░░░░░░▒▓█	....//
	//.�?█▓▒░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▓█▌	....//
	//.█▓▒▒░░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▓█	....//
	//.█�?▒▒░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▒█▓	....//
	//.█▓█▒░░░░░░░░░░░░░░░░░░░░░░░░░░░▒█▌▓█.....//
	//.█▓▓█▒░░░░░░░░░░░░░░░░░░░░░░░░░░▒█▓▓█.....//
	//.█▓█▒░▒▒▒▒░░▀▀█▄▄░░░░░▄▄█▀▀░░▒▒▒▒░▒█▓█....//
	//.█▓▌▒▒▓▓▓▓▄▄▄▒▒▒▀█░░░░█▀▒▒▒▄▄▄▓▓▓▓▒▒�?▓█...//
	//.██▌▒▓███▓█████▓▒�?▌░░�?▌▒▓████▓████▓▒�?██...//
	//..██▒▒▓███da ████▓▄░░░▄▓████boi ███▓▒▒██..//
	//..█▓▒▒▓██████████▓▒░░░▒▓██████████▓▒▒▓█...//
	//...█▓▒░▒▓███████▓▓▄▀░░▀▄▓▓███████▓▒▒▓█....//
	//.....█▓▒░▒▒▓▓▓▓▄▄▄▀▒░░░░░▒▀▄▄▄▓▓▓▓▒▒░▓█...//
	//......█▓▒░▒▒▒▒░░░░░▒▒▒▒▒▒░░░░░▒▒▒▒░▒▓█....//
	//.......█▓▓▒▒▒░░░░░░░▒▒▒▒░░░░░▒▒▒▓▓█.......//
	//........▀██▓▓▓▒░░▄▄▄▄▄▄▄▄▄▄░░▒▓█▀	........//
	//.........▀█▓▒▒░░░░░░▀▀▀▀▒░░▒▒▓█▀..........//
	//............██▓▓▒░░▒▒▒░▒▒▒░▒▓██...........//
	//..............█▓▒▒▒░░░░░▒▒▒▓█	............//
	//................▀▀█▓▓▓▓▓▓█▀...............//
	//..........................................//
	//////////////////////////////////////////////
	 </pre>

Create Encrypted files.


 * 
 * @author Richard 
 *
 */

@SuppressWarnings("unused")
public class FileEncryptor {

	private File file;
	private String fileNameEn;
	private String fileNameDe;

	public FileEncryptor() {

	}

	public FileEncryptor(File file) {
		this.setFile(file);
	}
	
	/**
	 * Takes in params password and Array of Files and passes to {@link #Encrypt(String , File ) Encrypt}
	 * 
	 * @param password
	 * @param files
	 * @return
	 * @throws IOException
	 */

	public ArrayList<byte[]> Encrypt(String password, File... files) throws IOException {
		byte[] temp;
		ArrayList<byte[]> encryptions = new ArrayList<byte[]>();
		for (int i = 0; i < files.length; i++) {
			temp = Encrypt(password, files[i]);
			encryptions.add(temp);
		}
		return encryptions;

	}
	
	public String getFileNameEn() {
		return fileNameEn;
	}

	public void setFileNameEn(String fileNameEn) {
		this.fileNameEn = fileNameEn;
	}
	
	/**
	 * Creataes an encrypted file using a custom cipher. Converts data in Base64 and shifts each byte by 256 <br>
	 * and by the ACSII repesentation of a random string password.
	 * 
	 * @param password
	 * @param file
	 * @return byte [] 
	 * @throws IOException
	 */

	public byte[] Encrypt(String password, File file) throws IOException {
		Encoder base = Base64.getEncoder();
		Path path = Paths.get(file.getAbsolutePath());
		byte[] data = Files.readAllBytes(path);
		byte[] encoded = base.encode(data);
		byte[] passbytes = password.getBytes();
		for (int i = 0; i < encoded.length; i++) {
			encoded[i] += (byte) 256;
			for (int j = 0; j < passbytes.length; j++) {
				encoded[i] += passbytes[j];
			}
		}
		byte[] doubleEncoded = base.encode(encoded);
		return doubleEncoded;
	}
	
	/**
	 * Decrypts the encrypted file by doing the inverse of the custom cipher. Shifting back the data in base64 and by -256 bytes <br>
	 * and reversing the ASCII representation of a randomly generated string password.
	 * @param password
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	
	public byte[] Decrypt(String password,byte [] bytes) throws IOException {
		Decoder decoded = Base64.getDecoder();
		byte[] data = bytes;
		byte[] encoded = decoded.decode(data);
		byte[] passbytes = password.getBytes();
		for (int i = 0; i < encoded.length; i++) {
			encoded[i] -= (byte) 256;
			for (int j = 0; j < passbytes.length; j++) {
				encoded[i] -= passbytes[j];
			}
		}

		byte[] doubledecoded = decoded.decode(encoded);
		return doubledecoded;
	}
	/**
	 * This is where the passowrd generation happens, it uses ASCII to create the password <br>
	 * It has conditionals to prevent characters which arent alphabetical or numerical.
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public char charGen() throws UnsupportedEncodingException {
		Random ran = new Random();
		int c = ran.nextInt(126);
		if (c <= 33) {
			c = 33;
		}

		byte b = (byte) c;
		byte[] bytes = new byte[1];
		bytes[0] = b;
		String chars = new String(bytes, "UTF-8");
		char[] chararray = chars.toCharArray();
		return chararray[0];
	}
	/**
	 * This makes the generated password 12 characters long for convinience for the user.
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String passwordGen() throws UnsupportedEncodingException {
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			password.append(charGen());
		}
		System.out.println(password.toString());
		return password.toString();
	}
/**
 * This is where the encrypted file is outputted onto the users computer. <br>
 * It takes the path of the original file, and outputs the encrypted file at the end and modifies the name putting "2_enc" so it is clear to the user which is the encrypted file.
 * @param data
 * @param file
 * @throws IOException
 */
	public void writeFileEn(byte[] data, File file) throws IOException {
		Files.write(Paths.get(file.getAbsolutePath() + "2_enc"), data, StandardOpenOption.CREATE_NEW);
		fileNameEn = file.getAbsolutePath() + "2_enc";

	}
/**
 * This allows multiple files to be encrypted.
 * @param encs
 * @param files
 * @throws IOException
 */
	public void writeFileEn(ArrayList<byte[]> encs, File [] files) throws IOException {
		for (int i = 0; i < encs.size(); i++) {
			writeFileEn(encs.get(i), files[i]);
		}

	}

	public void writeFileDe(byte[] data) throws IOException {
		Files.write(Paths.get(file.getAbsolutePath() + "2_dec"), data, StandardOpenOption.CREATE_NEW);
		fileNameDe =  file.getName()+"2_dec";
		Files.copy(Paths.get(new File(fileNameDe).toURI()), new  FileOutputStream ("web/"+fileNameDe));
	}

	public String getFileNameDe() {
		return fileNameDe;
	}

	public void setFileNameDe(String fileNameDe) {
		this.fileNameDe = fileNameDe;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
