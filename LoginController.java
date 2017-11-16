package FileEncr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.io.Files;

@Controller
public class LoginController {

	@RequestMapping(value = { "/" }, method = { RequestMethod.GET })
	public ModelAndView login(ModelAndView model) {
		model.setViewName("gmail.html");
		return model;

	}

	@RequestMapping(value = { "/signedin" }, method = { RequestMethod.POST })
	public ModelAndView getToken(ModelAndView model, HttpServletRequest request) {
		String token = request.getParameter("token");
		UserToken.setToken(token);
		model.setViewName("signedin.html");
		return model;

	}

	@RequestMapping(value = { "/signedin" }, method = { RequestMethod.GET })
	public ModelAndView signedin(ModelAndView model) {
		model.setViewName("signedin.html");
		return model;

	}

	@RequestMapping(value = { "/uploadEn" }, method = { RequestMethod.POST })
	public ModelAndView handleFileUploadEe(ModelAndView model, @RequestParam("file") MultipartFile file ,  HttpServletRequest request, HttpServletResponse response ) throws IOException, MessagingException {
		String fileName = file.getOriginalFilename().toLowerCase();
		System.out.println(fileName);
		byte[] filebytes = file.getBytes();
		FileEncryptor crt = new FileEncryptor();
		String pass = crt.passwordGen();
		FileWriter write = new FileWriter(fileName);
		for (int i = 0; i < filebytes.length; i++) {
			write.write(filebytes[i]);
		}
		write.flush();
		write.close();
		File normal = new File(fileName);
		byte[] encBytes = crt.Encrypt(pass, normal);
		crt.writeFileEn(encBytes, normal);
		System.out.println (crt.getFileNameEn());
		GmailEmail email = new GmailEmail();
		String  toEmail = request.getParameter("to");
		String  subject = request.getParameter("subject");
		System.out.println(toEmail+":"+subject);
		GmailEmail.run();
		GmailEmail.sendMessage("me", toEmail, subject, pass, new File(crt.getFileNameEn()));

		model.addObject("code", "Sent!");
		model.setViewName("File.html :: response");
		return model;

	}

	@RequestMapping(value = { "/uploadDe" }, method = { RequestMethod.POST })
	public ModelAndView handleFileUploadDe(ModelAndView model,  @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response ) throws IOException, MessagingException {
		String fileName = file.getOriginalFilename().toLowerCase();
		System.out.println(fileName);
		byte[] filebytes = file.getBytes();
		FileEncryptor crt = new FileEncryptor();
		String  password = request.getParameter("pass");
		System.out.println(password);
		byte[] decry = crt.Decrypt(password, filebytes);
		crt.writeFileDe(decry);
		GmailEmail email = new GmailEmail();
		String  toEmail = request.getParameter("to");
		String  subject = request.getParameter("subject");
		System.out.println(toEmail+":"+subject);
		GmailEmail.run();
		GmailEmail.sendMessage("me", toEmail, subject, password, new File(crt.getFileNameDe()));
		model.addObject("code", "Sent!");
		model.setViewName("File.html :: response");
		return model;

	}

	@RequestMapping(value = { "/file" }, method = { RequestMethod.GET })
	public ModelAndView file(ModelAndView model) {
		if (UserToken.getToken() != null) {
			model.setViewName("File.html");
		} else {
			model.setViewName("signedin.html");
		}
		return model;

	}
}
