package JavaCA.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import JavaCA.model.Brand;
import JavaCA.model.Product;
import JavaCA.model.Supplier;
import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.model.TransactionType;
import JavaCA.service.BrandServiceImpl;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.SupplierServiceImpl;
import JavaCA.service.TransactionImplementation;

@Controller
@RequestMapping("/product")
public class ProductController {
	private ProductServiceImpl pservice;
	private BrandServiceImpl bservice;
	private SupplierServiceImpl suppservice;
	private TransactionImplementation tservice;
	
	@Autowired
	public void setServices(ProductServiceImpl pservice, BrandServiceImpl bservice, 
			SupplierServiceImpl suppservice, TransactionImplementation tservice) {
		this.pservice = pservice;
		this.bservice = bservice;
		this.suppservice = suppservice;
		this.tservice = tservice;
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
		ArrayList<Brand> brands = bservice.findAllBrands();
		model.addAttribute("brands", brands);
		ArrayList<Supplier> suppliers = suppservice.findAllSuppliers();
		model.addAttribute("suppliers", suppliers);
		return "/product/productform";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public String saveProduct(@ModelAttribute("p") Product p, Model model) {
		//search for existing brand based on name
		Brand b = bservice.findBrandByName(p.getBrand().getName());
		//if no existing brand, create brand before setting into p
		if(b==null) {
			b = new Brand(p.getBrand().getName());
			bservice.saveBrand(b);
		}
		p.setBrand(b);
		
		//search for existing supplier based on name
		Supplier s = suppservice.findSupplierByName(p.getSupplier().getSupplierName());
		//if existing supplier exists, create supplier before setting into p
		if(s==null) {
			s = new Supplier(p.getSupplier().getSupplierName());
			suppservice.saveSupplier(s);
		}
		p.setSupplier(s);
		
		//save product to db
		pservice.saveProduct(p);
		
		//save first transaction if quantity >0
		if(p.getQuantity()>0) {
			//get user to set into transaction
			Transaction t = new Transaction();
			//t.setUser(user);
			tservice.saveTransaction(t);
			// Create the transaction detail and set product and transaction before persisting
			TransactionDetail td = new TransactionDetail(p.getQuantity(), TransactionType.ORDER);
			td.setProduct(p);
			td.setTransaction(t);
			tservice.saveTransactionDetail(td);
		}
		return "redirect:/product";
	}
	
}