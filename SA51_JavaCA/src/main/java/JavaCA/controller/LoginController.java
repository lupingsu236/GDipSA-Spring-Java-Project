package JavaCA.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import JavaCA.model.Password;
import JavaCA.model.RoleType;
import JavaCA.model.User;
import JavaCA.service.UserImplementation;
import JavaCA.service.UserInterface;

@Controller
public class LoginController {
	
	@Autowired
	UserInterface uservice;
	
	@Autowired
	public void setUserImplemetation(UserImplementation uimpl) {
		this.uservice=uimpl;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	
	@RequestMapping(path = {"/", "/login"})
	public String login(Model model, HttpSession session) 
	{
		session.setAttribute("admin", RoleType.ADMIN);
		if (session.getAttribute("usession") != null) {
			return "index";
		}
		model.addAttribute("user", new User());
		return "login/login";
	}
	
	@RequestMapping(path = "/authenticate")
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session) {
		if(uservice.authenticate(user)) 
		{
			User u = uservice.findByName(user.getUsername());
			session.setAttribute("usession", u);
			return "redirect:/";
		}
		else {
			model.addAttribute("errorMsg", "Incorrect username/password");
			return "login/login";
		}			
	}
	
	@RequestMapping(path = "/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/change/{id}",method=RequestMethod.GET)
	public String tochange(Model model,@PathVariable("id") Long id) {
		Password password = new Password();
		model.addAttribute("password", password);
		return "/login/changepsd";
	}
	
	@PostMapping(value = "/changePsd/{id}")
	public String changePSD(@ModelAttribute("password") Password password, Model model, 
			@PathVariable(value="id") Long id, HttpSession session) {
		
		User user = uservice.findById(id); 
		
		if(!password.getOldpassword().equals(user.getPassword())) {
			model.addAttribute("Errmsgpsd1","The old password is not correct.");
			return "login/changepsd";
		}
		
		else if(password.getNewpassword()=="") {
			model.addAttribute("Errmsgpsd2","Password is blank.");
			return "login /changepsd";
		}
		
		else if(!password.getNewpassword().equals(password.getConpassword())) {
			model.addAttribute("Errmsgpsd3","New password is not confirmed.");
			return "login/changepsd";
		}
		
		user.setPassword(password.getNewpassword());
		uservice.updateUser(user);
		return "login/changesuccess";
	}
	
	
	
	
}


