package JavaCA.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import JavaCA.model.Product;
import JavaCA.service.ProductServiceImpl;

@Controller
@RequestMapping("/product")
public class ProductController {
	private ProductServiceImpl pservice;
	
	@Autowired
	public void setProductService(ProductServiceImpl pservice) {
		this.pservice = pservice;
	}
	
	@RequestMapping(value={"","/list"}, method=RequestMethod.GET)
	public String findAllProducts(Model model) {
		ArrayList<Product> products = pservice.findAllProducts();
		model.addAttribute("products", products);
		return "/product/productlist";
	}
	
	@RequestMapping(value={"/detail/{id}"}, method=RequestMethod.GET)
	public String findAllProducts(@PathVariable long id, Model model) {
		Product p = pservice.findProduct(id);
		model.addAttribute("p", p);
		return "/product/productdetail";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createProduct(Model model) {
		Product p = new Product(); 
		model.addAttribute("p", p);
		return "/product/productform";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public String saveProduct(@ModelAttribute("p") Product p, Model model) {
		pservice.saveProduct(p);
		//save first transaction if quantity >0
		return "redirect:/product";
	}
	
}
