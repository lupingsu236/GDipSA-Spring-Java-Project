package JavaCA.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import JavaCA.interfaceimplementation.UserImplementation;
import JavaCA.interfaceimplementation.UserInterface;
import JavaCA.model.User;

@Controller
public class UserController {
	
	@Autowired 
	UserInterface uservice;
	
	@Autowired
	public void setUserImplementation(UserImplementation uimpl) {
		this.uservice = uimpl;
	}
	
	@RequestMapping(path = "")
	public String home() {
		//need to make it so that it redirects to login if sessions is null
		return "index";
	}
	
	@RequestMapping(path = "/login")
	public String login(Model model, HttpSession session) {
		if (session.getAttribute("usession") != null) {
			return "index";
		}
		User u = new User();
		model.addAttribute("user", u);
		return "login";
	}
	
	@RequestMapping(path = "/authenticate")
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session) {
		if(uservice.authenticate(user)) 
		{
			User u = uservice.findByName(user.getUsername());
			session.setAttribute("usession", u);
			return "index";
		}
		else
			return "login";
	}
	
	@RequestMapping(path = "/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
		
	}
}
