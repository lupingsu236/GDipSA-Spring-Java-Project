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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JavaCA.model.ActiveType;
import JavaCA.model.Password;
import JavaCA.model.RoleType;
import JavaCA.model.User;
import JavaCA.service.EmailService;
import JavaCA.service.EmailServiceImpl;
import JavaCA.service.UserServiceImpl;
import JavaCA.service.UserService;

@Controller
public class LoginController {
	
	private UserService uservice;
	private EmailService eservice;
	private HttpSession session;
	
	@Autowired
	public void setServices(UserServiceImpl uservice, EmailServiceImpl eservice, HttpSession session) 
	{
		this.uservice = uservice;
		this.eservice = eservice;
		this.session = session;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	
	@RequestMapping(path = {"/", "/login"})
	public String login(Model model) 
	{
		session.setAttribute("admin", RoleType.ADMIN);
		if (session.getAttribute("usession") != null) {
			return "index";
		}
		model.addAttribute("user", new User());
		return "login/login";
	}
	
	@RequestMapping(path = "/authenticate")
	public String authenticate(@ModelAttribute("user") User user, Model model) {
		if(uservice.authenticate(user)) 
		{
			User u = uservice.findByName(user.getUsername());
			if (u.getActivetype()==ActiveType.INACTIVE) {
				model.addAttribute("errorMsg", "User has been deactivated!");
				return "login/login";
			}
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
	public String tochange(Model model,@PathVariable("id") long id) {
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		//only current user that is login can change his password 
		//prevents bypassing via url
		User currentUser = (User) session.getAttribute("usession");
		if (currentUser.getId()!=id)
		{
			return "redirect:/";
		}
		
		Password password = new Password();
		model.addAttribute("password", password);
		return "/login/changepsd";
	}
	
	@PostMapping(value = "/changePsd/{id}")
	public String changePSD(@ModelAttribute("password") Password password, Model model, 
			@PathVariable(value="id") long id, RedirectAttributes redirectfrom) {
		
		User user = uservice.findById(id); 
		
		if(!password.getOldpassword().equals(user.getPassword())) {
			model.addAttribute("errMsg_oldPass","The old password is incorrect.");
			return "login/changepsd";
		}
			
		if(password.getNewpassword().length()<6)
		{
			model.addAttribute("errMsg_length", "Password must be at least 6 characters long!");
			return "/login/changepsd";
		}
		
		if(!password.getNewpassword().equals(password.getConpassword())) {
			model.addAttribute("errMsg_noMatch","Passwords do not match!");
			return "/login/changepsd";
		}
		
		user.setPassword(password.getNewpassword());
		uservice.updateUser(user);
		redirectfrom.addFlashAttribute("from", "psdChanged");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/forgotpassword", method=RequestMethod.GET)
	public String forgotPassword(Model model) 
	{
		return "/login/forgotpassword";
	}
	
	@RequestMapping(value={"/resetpassword"}, method=RequestMethod.POST)
	public String resetPasswordAndSendEmail(@RequestParam String usernameOrEmail, RedirectAttributes model)
	{
		eservice.sendEmailToResetPassword(usernameOrEmail);
		if (!(uservice.findByUsername(usernameOrEmail) == null && uservice.findByEmail(usernameOrEmail) == null))
			model.addFlashAttribute("resetPassword", true);
		return "redirect:/";
	}
}


