package FileEncr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
}
