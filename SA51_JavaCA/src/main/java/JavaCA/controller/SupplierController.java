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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JavaCA.model.Supplier;
import JavaCA.service.SupplierService;
import JavaCA.service.SupplierServiceImpl;
import JavaCA.service.UserServiceImpl;
import JavaCA.service.UserService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
	
	private SupplierService suppservice;
	private UserService uservice;
	private HttpSession session;
	
	
	@Autowired
	public void setServices(SupplierServiceImpl suppservice, UserServiceImpl uservice, HttpSession session) {
		this.suppservice = suppservice;
		this.uservice = uservice;
		this.session = session;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	@RequestMapping(value={"","/list"}, method=RequestMethod.GET)
	public String findAllSuppliers(Model model) {
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
				
		//for supplier listing
		ArrayList<Supplier> suppliers = suppservice.findAllSuppliers();
		model.addAttribute("suppliers", suppliers);
		
		return "/supplier/supplierlist";
	}

	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createsupplier(Model model) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
		
		Supplier s = new Supplier(); 
		model.addAttribute("s", s);
		
		return "/supplier/supplierform";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editSupplier(@PathVariable long id, Model model) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
		
		//get current supplier details and attach to model
		Supplier s = suppservice.findSupplier(id);
		model.addAttribute("s", s);
				
		return "/supplier/supplierform";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public String saveSupplier(@Valid @ModelAttribute("s") Supplier s, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectfrom) {
		
		if (bindingResult.hasErrors()) {
			return "/supplier/supplierform";
		}
		
		if(suppservice.findSupplierByName(s.getSupplierName())==null)
		{
			//save supplier to db
			suppservice.saveSupplier(s);
		}
		
		//supplier with same name exists
		else
		{
			model.addAttribute("errMsg", "Supplier with same name exists!");
			return "/supplier/supplierform";
		}
		
			
		//add redirect attribute for alert
		redirectfrom.addFlashAttribute("from", "save");
		return "redirect:/supplier";
	}
	
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteSupplier(@PathVariable long id, Model model, RedirectAttributes redirectfrom) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
		
		Supplier s = suppservice.findSupplier(id);
		
		//if supplier has no products tied to it, delete supplier
		if(s.getProducts().size()==0)
		{
			suppservice.deleteSupplier(s);
			redirectfrom.addFlashAttribute("from", "delete");
		}
		
		else
		{
			redirectfrom.addFlashAttribute("from", "delete_error");
		}
		
		return "redirect:/supplier";
	}
	
}
