package JavaCA.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
import org.springframework.web.bind.annotation.RequestParam;

import JavaCA.model.ActiveType;
import JavaCA.model.Password;
import JavaCA.model.RoleType;
import JavaCA.model.User;
import JavaCA.repo.UserRepository;
import JavaCA.service.UserImplementation;
import JavaCA.service.UserInterface;

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
		
		User u=uservice.findByName(user.getUsername());
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
		return "redirect:/user/ulist";
				
	}
	
	@PostMapping(value="/user/saveedit/{id}")
	public String saveeditUser(@ModelAttribute("user") @Valid User user, 
			@PathVariable("id") long id,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "/user/usereditform";
		}		
		
		if(user.getRoletype().equalsIgnoreCase("admin")) {
			user.setRole(RoleType.ADMIN);
		}else if(user.getRoletype().equalsIgnoreCase("mechanic")){
			user.setRole(RoleType.MECHANIC);
		}else {
			model.addAttribute("Errmsgrole","The roletype only includes admin and mechanic.");
			return "/user/usereditform";
		}
		
		if(user.getActive().equalsIgnoreCase("active")) {
			user.setActivetype(ActiveType.ACTIVE);;
		}else if(user.getActive().equalsIgnoreCase("inactive")){
			user.setActivetype(ActiveType.INACTIVE);
		}else if(user.getActive()=="") {
			model.addAttribute("Errmsgstate1","The state is blank.");
			return "/user/usereditform";
		}else {
			model.addAttribute("Errmsgstate2","The state only includes active and inactive.");
			return "/user/usereditform";
		}
		
		User u=urepo.findById(id).get();
		u.setEmail(user.getEmail());
		u.setFullName(user.getFullName());
		u.setUsername(user.getUsername());
		u.setRole(user.getRole());
		u.setActive(user.getActive());
		u.setActivetype(user.getActivetype());
		
		User ucheck=urepo.findUserByUsername(u.getUsername());
		if(ucheck==u||ucheck==null) {
			urepo.save(u);
			return "redirect:/user/ulist";
		}
		else {
			model.addAttribute("Errmsgname","The username has been used.");
			return "/user/usereditform";
		}		
	}
	
	@RequestMapping(value={"","/user/ulist"},method=RequestMethod.GET)
	public String listUsers(Model model) {
		ArrayList<User> users=(ArrayList<User>) uservice.listAllUser();
		model.addAttribute("users",users);
		return "/user/userlist";
	}
	
	@RequestMapping(value="/user/edit/{id}",method=RequestMethod.GET)
	public String editUser(Model model,@PathVariable("id") Long id) {
		model.addAttribute("user",urepo.findById(id).get());
		model.addAttribute("oldpsd", urepo.findById(id).get().getPassword());
		return "/user/usereditform";
	}
	
	@RequestMapping(value="/change/{id}",method=RequestMethod.GET)
	public String tochange(Model model,@PathVariable("id") Long id,HttpSession session) {
		Password password = new Password();
		User u=urepo.findById(id).get();
		model.addAttribute("password", password);
		session.setAttribute("usession",u);
		return "/changepsd";
	}
	
	@PostMapping(value = "/changePsd/{id}")
	public String changePSD(@ModelAttribute("password") Password password, Model model, 
			@PathVariable(value="id") Long id,HttpSession session) {
		
		User user=urepo.findById(id).get(); 
		
		if(!password.getOldpassword().equals(user.getPassword())) {
			model.addAttribute("Errmsgpsd1","The old password is not correct.");
			return "/changepsd";
		}else if(password.getNewpassword()=="") {
			model.addAttribute("Errmsgpsd2","Password is blank.");
			return "/changepsd";
		}else if(!password.getNewpassword().equals(password.getConpassword())) {
			model.addAttribute("Errmsgpsd3","New password is not confirmed.");
			return "/changepsd";
		}
		user.setPassword(password.getNewpassword());
		urepo.save(user);
		return "/changesuccess";
	}
	
	
	@RequestMapping(path = {"/", "/login"})
	public String login(Model model, HttpSession session) 
	{
		session.setAttribute("admin", RoleType.ADMIN);
		if (session.getAttribute("usession") != null) 
		{
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
			return "redirect:/";
		}
		else {
			model.addAttribute("errorMsg", "Incorrect username/password");
			return "login";
		}			
	}
	
	@RequestMapping(path = "/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
