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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JavaCA.model.Brand;
import JavaCA.service.BrandService;
import JavaCA.service.BrandServiceImpl;

@Controller
@RequestMapping("/brand")
public class BrandController {
	
	private BrandService bservice;
	
	@Autowired
	public void setServices(BrandServiceImpl bservice) {
		this.bservice = bservice;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	@RequestMapping(value={"","/list"}, method=RequestMethod.GET)
	public String findAllBrands(Model model) {
		//for brand listing
		ArrayList<Brand> brands = bservice.findAllBrands();
		model.addAttribute("brands", brands);
		
		return "/brand/brandlist";
	}

	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createBrand(Model model) {
		Brand b = new Brand(); 
		model.addAttribute("b", b);
		
		return "/brand/brandform";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editBrand(@PathVariable long id, Model model) {
		//get current brand details and attach to model
		Brand b = bservice.findBrand(id);
		model.addAttribute("b", b);
				
		return "/brand/brandform";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public String saveBrand(@Valid @ModelAttribute("b") Brand b, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectfrom) {
		
		if (bindingResult.hasErrors()) {
			return "/brand/brandform";
		}
		
		if(bservice.findBrandByName(b.getName())==null)
		{
			//save brand to db
			bservice.saveBrand(b);
		}
		
		//brand with same name exists
		else
		{
			model.addAttribute("errMsg", "Brand with same name exists!");
			return "/brand/brandform";
		}
		
			
		//add redirect attribute for alert
		redirectfrom.addFlashAttribute("from", "save");
		return "redirect:/brand";
	}
	
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteBrand(@PathVariable long id, Model model, RedirectAttributes redirectfrom) {
		Brand b = bservice.findBrand(id);
		
		//if brand has no products tied to it, delete brand
		if(b.getProducts().size()==0)
		{
			bservice.deleteBrand(b);
			redirectfrom.addFlashAttribute("from", "delete");
		}
		
		else
		{
			redirectfrom.addFlashAttribute("from", "delete_error");
		}
		
		return "redirect:/brand";
	}
	
}
