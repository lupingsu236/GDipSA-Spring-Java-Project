package JavaCA.controller;

<<<<<<< HEAD
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import JavaCA.model.User;
import JavaCA.repo.UserRepository;

public class UserController implements JpaRepository<User, Long> {
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
	
	@GetMapping("/user/add")
	public String showUserForm(Model model) {
		User user=new User();
		model.addAttribute("user",user);
		return "userForm";
	}
	
	@GetMapping("/user/save")
	public String saveUserForm(@ModelAttribute("user") @Valid User user,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "userForm";
		}
		urepo.save(user);
		return "forward:/user/listUsers";
	}
	
	@GetMapping("/user/listUsers")
	public String listUsers(Model model) {
		model.addAttribute("users",urepo.findAll());
		return "users";
	}
	
	@GetMapping("/user/edit/{id}")
	public String showEditForm(Model model,@PathVariable("id") Long id) {
		model.addAttribute("user",urepo.findById(id).get());
		return "userForm";
	}
	
	@GetMapping("/user/delete/{id}")
	public String deleteMethod(Model model,@PathVariable("id") Long id) {
		User user=urepo.findById(id).get();
		urepo.delete(user);
		return "forward:/user/listUsers";
=======
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
	
	@RequestMapping(path = {"", "/", "/login"})
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
		
>>>>>>> refs/remotes/origin/Luping
	}
}
