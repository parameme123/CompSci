import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Random;

@SuppressWarnings("unused")
public class FileEncryptor {

	private File file;

	public FileEncryptor(File file) {
		this.setFile(file);
	}

	public byte[] Encrypt(String password) throws IOException {
		// this shit is hot.
		Encoder base = Base64.getEncoder();
		Path path = Paths.get(file.getAbsolutePath());
		byte[] data = Files.readAllBytes(path);
		byte[] encoded = base.encode(data);
		byte[] passbytes = password.getBytes();
		for (int i = 0; i < encoded.length; i++) {
			encoded[i]+=(byte) 256; /* Increased obfuscation */
			for (int j = 0; j < passbytes.length; j++) {
				encoded[i] += passbytes[j];
			}
		}
		byte[] doubleEncoded = base.encode(encoded);
		return doubleEncoded;
	}

	public byte[] Decrypt(String password) throws IOException {
		Decoder decoded = Base64.getDecoder();
		Path path = Paths.get(file.getAbsolutePath());
		byte[] data = Files.readAllBytes(path);
		byte[] encoded = decoded.decode(data);
		byte[] passbytes = password.getBytes();
		for (int i = 0; i < encoded.length; i++) {
			encoded[i]-=(byte) 256; /* Increased obfuscation */
			for (int j = 0; j < passbytes.length; j++) {
				encoded[i] -= passbytes[j];
			}
		}

		byte[] doubledecoded = decoded.decode(encoded);
		return doubledecoded;

	}

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

	public String passwordGen() throws UnsupportedEncodingException {
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			password.append(charGen());
		}
		System.out.println(password.toString());
		return password.toString();
	}

	public void writeFileEn(byte[] data) throws IOException {
		Files.write(Paths.get(file.getAbsolutePath() + "2_enc"), data, StandardOpenOption.CREATE_NEW);

	}
	
	public void writeFileDe(byte[] data) throws IOException {
		Files.write(Paths.get(file.getAbsolutePath() + "2_dec"), data, StandardOpenOption.CREATE_NEW);

	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
