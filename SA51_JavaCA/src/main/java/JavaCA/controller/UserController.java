package JavaCA.controller;

import java.util.ArrayList;

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
import JavaCA.model.RoleType;
import JavaCA.model.User;
import JavaCA.service.UserImplementation;
import JavaCA.service.UserInterface;

@Controller
@RequestMapping("/user")
public class UserController{

	@Autowired
	UserInterface uservice;
	
	@Autowired
	public void setUserImplemetation(UserImplementation uimpl) {
		this.uservice=uimpl;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "/user/userform";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "/user/userform";
		}
		
		User u = uservice.findByName(user.getUsername());
		if(u!=null) {
			model.addAttribute("Errmsgname","The username has been used.");
			return "/user/userform";
		}
		
		if(user.getPassword()=="") {
			model.addAttribute("Errmsgpsd","The password is blank.");
			return "/user/userform";
		}
		
		if(user.getRoletype().equalsIgnoreCase("admin")) {
			user.setRole(RoleType.ADMIN);
		}else if(user.getRoletype().equalsIgnoreCase("mechanic")){
			user.setRole(RoleType.MECHANIC);
		}else {
			model.addAttribute("Errmsgrole","The roletype only includes admin and mechanic.");
			return "/user/userform";
		}		
		user.setActivetype(ActiveType.ACTIVE);
		user.setActive("active");
		uservice.createUser(user);
		return "redirect:/user/list";
				
	}
	
	@PostMapping(value="/user/saveedit/{id}")
	public String saveeditUser(@ModelAttribute("user") @Valid User user, 
			@PathVariable("id") long id,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "/user/usereditform";
		}		
		
		if(user.getRoletype().equalsIgnoreCase("admin")) {
			user.setRole(RoleType.ADMIN);
			user.setRoletype("admin");
		}
		else if(user.getRoletype().equalsIgnoreCase("mechanic")){
			user.setRole(RoleType.MECHANIC);
			user.setRoletype("mechanic");
		}
		else {
			model.addAttribute("Errmsgrole","The roletype only includes admin and mechanic.");
			return "/user/usereditform";
		}
		
		if(user.getActive().equalsIgnoreCase("active")) {
			user.setActivetype(ActiveType.ACTIVE);
			user.setActive("active");
		}
		else if(user.getActive().equalsIgnoreCase("inactive")){
			user.setActivetype(ActiveType.INACTIVE);
			user.setActive("inactive");
		}else if(user.getActive()=="") {
			model.addAttribute("Errmsgstate1","The state is blank.");
			return "/user/usereditform";
		}
		else {
			model.addAttribute("Errmsgstate2","The state only includes active and inactive.");
			return "/user/usereditform";
		}
		
		User u = uservice.findById(id);
		u.setEmail(user.getEmail());
		u.setFullName(user.getFullName());
		u.setUsername(user.getUsername());
		u.setRole(user.getRole());
		u.setActive(user.getActive());
		u.setActivetype(user.getActivetype());
		
		User ucheck = uservice.findByUsername(u.getUsername());
		if(ucheck==u||ucheck==null) {
			uservice.updateUser(u);
			return "redirect:/user/list";
		}
		else {
			model.addAttribute("Errmsgname","The username has been used.");
			return "/user/usereditform";
		}		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listUsers(Model model) {
		ArrayList<User> users = (ArrayList<User>) uservice.listAllUser();
		model.addAttribute("users", users);
		return "/user/userlist";
	}
	
	@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
	public String editUser(Model model,@PathVariable("id") Long id) {
		model.addAttribute("user", uservice.findById(id));
		model.addAttribute("oldpsd", uservice.findById(id).getPassword());
		return "/user/usereditform";
	}

}
	
