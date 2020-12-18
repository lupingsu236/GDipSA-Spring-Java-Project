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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JavaCA.model.Brand;
import JavaCA.model.Product;
import JavaCA.model.Supplier;
import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.model.TransactionType;
import JavaCA.model.User;
import JavaCA.service.BrandService;
import JavaCA.service.BrandServiceImpl;
import JavaCA.service.ProductService;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.SupplierService;
import JavaCA.service.SupplierServiceImpl;
import JavaCA.service.TransactionDetailsService;
import JavaCA.service.TransactionDetailsServiceImpl;
import JavaCA.service.TransactionService;
import JavaCA.service.TransactionServiceImplementation;

@Controller
@RequestMapping("/product")
public class ProductController {
	private ProductService pservice;
	private BrandService bservice;
	private SupplierService suppservice;
	private TransactionService tservice;
	private TransactionDetailsService tdservice;
	
	@Autowired
	public void setServices(ProductServiceImpl pservice, BrandServiceImpl bservice, 
			SupplierServiceImpl suppservice, TransactionServiceImplementation tservice, TransactionDetailsServiceImpl tdservice) {
		this.pservice = pservice;
		this.bservice = bservice;
		this.suppservice = suppservice;
		this.tservice = tservice;
		this.tdservice = tdservice;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		//binder.addValidators(new ProductValidator());
	}
	
	@RequestMapping(value={"","/list"}, method=RequestMethod.GET)
	public String findAllProducts(Model model) {
		
		//adding model and get values for search parameters dropdown
		Product p = new Product(); 
		model.addAttribute("p", p);
		ArrayList<String> types = pservice.getTypes();
		model.addAttribute("types", types);
		ArrayList<String> categories = pservice.getCategories();
		model.addAttribute("categories", categories);
		ArrayList<String> subcategories = pservice.getSubcategories();
		model.addAttribute("subcategories", subcategories);
		ArrayList<Brand> brands = bservice.findAllBrands();
		model.addAttribute("brands", brands);
		ArrayList<Supplier> suppliers = suppservice.findAllSuppliers();
		model.addAttribute("suppliers", suppliers);

		//for product listing
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
		
		//get values for field dropdown
		ArrayList<String> types = pservice.getTypes();
		model.addAttribute("types", types);
		ArrayList<String> categories = pservice.getCategories();
		model.addAttribute("categories", categories);
		ArrayList<String> subcategories = pservice.getSubcategories();
		model.addAttribute("subcategories", subcategories);
		ArrayList<Brand> brands = bservice.findAllBrands();
		model.addAttribute("brands", brands);
		ArrayList<Supplier> suppliers = suppservice.findAllSuppliers();
		model.addAttribute("suppliers", suppliers);
		return "/product/productform";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editProduct(@PathVariable long id, Model model) {
		//get current product details and attach to model
		Product p = pservice.findProduct(id);
		
		//get values for field dropdown
		model.addAttribute("p", p);
		ArrayList<String> types = pservice.getTypes();
		model.addAttribute("types", types);
		ArrayList<String> categories = pservice.getCategories();
		model.addAttribute("categories", categories);
		ArrayList<String> subcategories = pservice.getSubcategories();
		model.addAttribute("subcategories", subcategories);
		ArrayList<Brand> brands = bservice.findAllBrands();
		model.addAttribute("brands", brands);
		ArrayList<Supplier> suppliers = suppservice.findAllSuppliers();
		model.addAttribute("suppliers", suppliers);
		return "/product/productform";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public String saveProduct(@Valid @ModelAttribute("p") Product p, BindingResult bindingResult, 
			@RequestParam(value="editBrandName", required=false) Integer editBrandName,
			@RequestParam(value="editSupplierName", required=false) Integer editSupplierName,
			Model model, HttpSession session, RedirectAttributes redirectfrom) {
		
		if (bindingResult.hasErrors()) {
			//get values for field dropdown
			ArrayList<String> types = pservice.getTypes();
			model.addAttribute("types", types);
			ArrayList<String> categories = pservice.getCategories();
			model.addAttribute("categories", categories);
			ArrayList<String> subcategories = pservice.getSubcategories();
			model.addAttribute("subcategories", subcategories);
			ArrayList<Brand> brands = bservice.findAllBrands();
			model.addAttribute("brands", brands);
			ArrayList<Supplier> suppliers = suppservice.findAllSuppliers();
			model.addAttribute("suppliers", suppliers);
			return "/product/productform";
		}
		
		//search if product exist
		Product product = pservice.findProduct(p.getId());
		
		//if product exists, i.e. editing existing product details, check whether
		//to edit brand name and supplier name for all products
		if (product!=null)
		{
			//if user requests to edit brand name for all products 
			if(editBrandName==1) {
				bservice.editBrandName(product.getBrand().getId(), p.getBrand().getName());
			}			
			
			//if user requests to edit supplier name for all products
			if(editSupplierName==1) {
				suppservice.editSupplierName(product.getSupplier().getId(), p.getSupplier().getSupplierName());
			}
			
		}
		
		
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
		
		
		//new product
		if (product==null) {
			//save first transaction if quantity >0
			if(p.getQuantity()>0) {
				//get user from session to set into transaction
				Transaction t = new Transaction();
				User user = (User) session.getAttribute("usession");
				t.setUser(user);
				tservice.saveTransaction(t);
				// Create the transaction detail and set product and transaction before persisting
				TransactionDetail td = new TransactionDetail(p.getQuantity(), TransactionType.ORDER);
				td.setProduct(p);
				td.setTransaction(t);
				tdservice.saveTransactionDetail(td);
			}
		}
			
		//add redirect attribute for alert
		redirectfrom.addFlashAttribute("from", "save");
		return "redirect:/product";
	}
	
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable long id, Model model, RedirectAttributes redirectfrom) {
		Product p = pservice.findProduct(id);
		
		//check for existing transactionDetails to delete 
		ArrayList<TransactionDetail> transactionDetails = tdservice.findTransactionDetailsByProductId(id);
		for (TransactionDetail td : transactionDetails) {
			tdservice.deleteTransactionDetail(td);
			//if no remaining transaction detail, delete transaction as well
			if(td.getTransaction().getTransactionDetails().size()==0) {
				tservice.deleteTransaction(td.getTransaction());
			}
			
		}
		
		pservice.deleteProduct(p);
		
		//add redirect attribute for alert
		redirectfrom.addFlashAttribute("from", "delete");
		
		return "redirect:/product";
	}
	
	
	@RequestMapping(value="/search", method=RequestMethod.POST) 
	public String searchProduct(@ModelAttribute("p") Product p, Model model, 
			@RequestParam(value="belowReorderLevel", required=false) Integer belowReorderLevel) {

		//get search results and attach to model
		if(belowReorderLevel==null) {
		ArrayList<Product> products = pservice.searchProducts(p);
		model.addAttribute("products", products);
		}
		else {
			ArrayList<Product> products = pservice.searchProductsBelowReorderLevel(p);
			model.addAttribute("products", products);
		}
		
		//get values for search parameters dropdown
		ArrayList<String> types = pservice.getTypes();
		model.addAttribute("types", types);
		ArrayList<String> categories = pservice.getCategories();
		model.addAttribute("categories", categories);
		ArrayList<String> subcategories = pservice.getSubcategories();
		model.addAttribute("subcategories", subcategories);
		ArrayList<Brand> brands = bservice.findAllBrands();
		model.addAttribute("brands", brands);
		ArrayList<Supplier> suppliers = suppservice.findAllSuppliers();
		model.addAttribute("suppliers", suppliers);
		
		return "product/productlist";
	}
}
	
