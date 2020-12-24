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

import JavaCA.model.ActiveType;
import JavaCA.model.User;
import JavaCA.service.UserService;
import JavaCA.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController{

	private UserService uservice;
	private HttpSession session;
	
	@Autowired
	public void setServices(UserServiceImpl uservice, HttpSession session) {
		this.uservice = uservice;
		this.session = session;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createUser(Model model) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
				
		model.addAttribute("user", new User());
		model.addAttribute("roletypes", uservice.getRoleTypes());
		return "/user/userform";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, 
							Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("roletypes", uservice.getRoleTypes());
			return "/user/userform";
		}
		
		if(uservice.isUsernameUsed(user.getUsername())) {
			model.addAttribute("Errmsgname","The username has been used.");
			return "/user/userform";
		}
			
		user.setActivetype(ActiveType.ACTIVE);
		uservice.createUser(user);
		return "redirect:/user/list";
				
	}
	
	@PostMapping(value="/saveedit/{id}")
	public String saveeditUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
			@PathVariable("id") long id, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("roletypes", uservice.getRoleTypes());
			return "/user/usereditform";
		}		
	
		User u = uservice.findById(id);
		
		//if change in username and new username has been used
		if(!user.getUsername().equals(u.getUsername()) && uservice.isUsernameUsed(user.getUsername())) {
			model.addAttribute("errMsg_username","The username has been used.");
			model.addAttribute("roletypes", uservice.getRoleTypes());
			return "/user/usereditform";
		}
		
		//else set new info and update user
		u.setEmail(user.getEmail());
		u.setFullName(user.getFullName());
		u.setUsername(user.getUsername());
		u.setRole(user.getRole());
		u.setActivetype(user.getActivetype());

		uservice.updateUser(u);
		return "redirect:/user";
			
	}
	
	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String listUsers(Model model, HttpSession session) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
		
		ArrayList<User> users = (ArrayList<User>) uservice.listAllUser();
		model.addAttribute("users", users);
		return "/user/userlist";
	}
	
	@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
	public String editUser(Model model, @PathVariable("id") long id) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
				
		model.addAttribute("user", uservice.findById(id));
		model.addAttribute("roletypes", uservice.getRoleTypes());
		return "/user/usereditform";
	}

}
	
