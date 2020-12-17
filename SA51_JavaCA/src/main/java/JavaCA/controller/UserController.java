package JavaCA.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import JavaCA.interfaceimplementation.UserImplementation;
import JavaCA.interfaceimplementation.UserInterface;
import JavaCA.model.User;
import JavaCA.repo.UserRepository;

@Controller
public class UserController{
	@Autowired
	UserRepository urepo;
	
	@Autowired
	UserInterface uservice;
	
	@Autowired
	public void setUserImplemetation(UserImplementation uimpl) {
		this.uservice=uimpl;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	@RequestMapping(value="/user/add",method=RequestMethod.GET)
	public String createUser(Model model) {
		User user=new User();
		model.addAttribute("user",user);
		return "/user/userform";
	}
	
	@RequestMapping(value="/user/save", method=RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "/user/userform";
		}
		User u=uservice.findByUsername(user.getUsername());
		if(u!=null) {
			return "/user/usernameerror";
		}
		if(user.getPassword()=="") {
			return "/user/psderror";
		}
		uservice.createUser(user);
		return "redirect:/user/ulist";
				
	}
	
	@PostMapping(value="/user/edit/{id}")
	public String saveeditUser(@ModelAttribute("user") @Valid User user, 
			@PathVariable("id") long id,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "/user/usereditform";
		}
		User u=urepo.findById(id).get();
		u.setEmail(user.getEmail());
		u.setFullName(user.getFullName());
		u.setUsername(user.getUsername());
		u.setRole(user.getRole());
		
		User ucheck=urepo.findUserByUsername(u.getUsername());
		if(ucheck==u||ucheck==null) {
			urepo.save(u);
			return "redirect:/user/ulist";
		}
		else {
			return "/user/usernameerror";
		}		
	}
	
	@RequestMapping(value={"","/user/ulist"},method=RequestMethod.GET)
	public String listUsers(Model model) {
		ArrayList<User> users=uservice.findAllUsers();
		model.addAttribute("users",users);
		return "/user/userlist";
	}
	
	@RequestMapping(value="/user/edit/{id}",method=RequestMethod.GET)
	public String editUser(Model model,@PathVariable("id") Long id) {
		model.addAttribute("user",urepo.findById(id).get());
//		model.addAttribute("psd",urepo.findById(id).get().getPassword());
		return "/user/usereditform";
	}
	
	@RequestMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		User user=urepo.findById(id).get();
		urepo.delete(user);
		return "forward:/user/ulist";
	}
	
//	@RequestMapping(path = {"", "/", "/login"})
//	public String login(Model model, HttpSession session) {
//		if (session.getAttribute("usession") != null) {
//			return "index";
//		}
//		User u = new User();
//		model.addAttribute("user", u);
//		return "login";
//	}
	
//	@RequestMapping(path = "/authenticate")
//	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session) {
//		if(uservice.authenticate(user)) 
//		{
//			User u = uservice.findByUsername(user.getUsername());
//			session.setAttribute("usession", u);
//			return "index";
//		}
//		else
//			return "login";
//	}
////	
//	@RequestMapping(path = "/logout", method=RequestMethod.GET)
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/login";
//	}
}
