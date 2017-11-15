package JavaFX;

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
	public ModelAndView getToken( ModelAndView model, HttpServletRequest request)   {
		String token = request.getParameter("token");
		UserToken.setToken(token);
		model.setViewName("signedin.html");
		return model;
		

	}
	@RequestMapping(value = { "/signedin" }, method = { RequestMethod.GET })
	public ModelAndView signedin(ModelAndView model)   {
		model.setViewName("signedin.html");
		return model;
		
	}
	
	
	
	
	@RequestMapping(value = { "/uploadEn" }, method = { RequestMethod.POST })
	public ModelAndView handleFileUploadEe(ModelAndView model, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException, MessagingException {
			String fileName = file.getOriginalFilename().toLowerCase();
			System.out.println(fileName);
			byte[] filebytes = file.getBytes();
			FileEncryptor crt  = new FileEncryptor();
			String pass = crt.passwordGen();
			Files.write(filebytes, new File(fileName));
			File fileToSend = new File(fileName);
			crt.Encrypt(pass, fileToSend);
			GmailEmail email = new  GmailEmail ();
			email.run();
			GmailEmail.sendMessage("me", "sil3nt884@hotmail.co.uk", "files", pass, fileToSend);
			
			model.addObject("code", "Sent!");
			model.setViewName("File.html :: response");
			return model;
			
	}

	
	@RequestMapping(value = { "/uploaDe" }, method = { RequestMethod.POST })
	public String handleFileUploadDe(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
			String fileName = file.getOriginalFilename().toLowerCase();
			System.out.println(fileName);
			byte[] filebytes = file.getBytes();
			return "/";
			
	}

	
	@RequestMapping(value = { "/file" }, method = { RequestMethod.GET })
	public ModelAndView file(ModelAndView model)   {
		if(UserToken.getToken()!=null) {
				model.setViewName("File.html");
		}
		else {
			model.setViewName("signedin.html");
		}
		return model;
		
	}
}
