package JavaCA.controller;

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
	}
}
