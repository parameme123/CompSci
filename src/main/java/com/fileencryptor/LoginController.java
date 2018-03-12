package com.fileencryptor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


/**
 * Exposes endpoints for jetty to read
 */
@Controller

/**
 * Creates a timer to ping the browser to detect it closing.
 * @author paradox
 */
public class LoginController {

	Timer timer = new Timer ();

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




	@RequestMapping(value = { "/poll" }, method = { RequestMethod.GET })
	@ResponseStatus(value = HttpStatus.OK)
	public 	void  createSession (HttpSession session) {

		Timer.lastaccessed = session.getLastAccessedTime();
	}


	/**
	 * This uploads the encrypted file 
	 * @param model
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
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
		@SuppressWarnings("unused")
		GmailEmail email = new GmailEmail();
		String  toEmail = request.getParameter("to");
		String  subject = request.getParameter("subject");
		System.out.println(toEmail+":"+subject);
		GmailEmail.run();
		try {
			GmailEmail.sendMessage("me", toEmail, subject, pass, new File(crt.getFileNameEn()));
		} catch (javax.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addObject("code", " Email Sent!");
		model.setViewName("File.html :: email");

		return model;

	}


	@RequestMapping(value = { "/delete" }, method = { RequestMethod.GET })
	public ModelAndView deleteFiles(ModelAndView model) {
		File  folder = new File ("web");
		File[] webFiles = folder.listFiles();
		String urerdir = System.getProperty("user.dir");
		File outside = new File (urerdir);  
		File[] outsidefiles = outside.listFiles();
		ArrayList<File> filesToDelete = new ArrayList<File>();
		System.out.println(outside.getAbsolutePath());
		for(int i=0; i<webFiles.length; i++) {
			if(webFiles[i].getName().contains("_enc") || webFiles[i].getName().contains("_dec")) {
				System.out.println(webFiles[i]);
				filesToDelete.add(webFiles[i]);
			}
		}

		for(int i=0; i<outsidefiles.length; i++) {
			if(outsidefiles[i].getName().contains("_dec") || outsidefiles[i].getName().contains("_enc")) {
				System.out.println(outsidefiles[i]);
				filesToDelete.add(outsidefiles[i]);
			}
		}

		for(int i=0; i<filesToDelete.size(); i++) {
			filesToDelete.get(i).delete();
		}



		model.addObject("delete", "Deleted");
		model.setViewName("File.html :: delete");
		return model;


	}


	/**
	 * Uploads the decrypted file
	 * @param model
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	@RequestMapping(value = { "/uploadDe" }, method = { RequestMethod.POST })
	public ModelAndView handleFileUploadDe(ModelAndView model,  @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response ) throws IOException, MessagingException {
		String fileName = file.getOriginalFilename().toLowerCase();
		System.out.println(fileName);
		byte[] filebytes = file.getBytes();
		FileEncryptor crt = new FileEncryptor();
		String  password = request.getParameter("pass");
		System.out.println(password);
		byte[] decry = crt.Decrypt(password, filebytes);
		crt.setFile(new File(fileName));
		crt.writeFileDe(decry);
		model.addObject("text", "Click here to download your decrypted file");
		model.addObject("download", crt.getFileNameDe());
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
