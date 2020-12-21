package JavaCA.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import JavaCA.model.RoleType;
import JavaCA.model.User;
import JavaCA.service.UserImplementation;
import JavaCA.service.UserInterface;

@Controller
public class UserController 
{
	@Autowired 
	UserInterface uservice;
	
	@Autowired
	public void setUserImplementation(UserImplementation uimpl) 
	{
		this.uservice = uimpl;
	}
	
	@RequestMapping(path = {"/", "/login"})
	public String login(Model model, HttpSession session) 
	{
		if (session.getAttribute("usession") != null) 
		{
			return "index";
		}
		User u = new User();
		model.addAttribute("user", u);
		return "login";
	}
	
	@RequestMapping(path = "/authenticate")
	public String authenticate(@ModelAttribute("user") User user, HttpSession session, Model model) 
	{
		if(uservice.authenticate(user)) 
		{
			User u = uservice.findByName(user.getUsername());
			session.setAttribute("usession", u);
			session.setAttribute("admin", RoleType.ADMIN);
			return "redirect:/";
		}
		else
		{
			model.addAttribute("errorMsg", "Incorrect username/password");
			return "login";
		}
	}
	
	@RequestMapping(path = "/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) 
	{
		session.invalidate();
		return "redirect:/";
	}
}
