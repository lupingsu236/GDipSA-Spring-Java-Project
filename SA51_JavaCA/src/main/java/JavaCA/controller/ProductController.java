package JavaCA.controller;

import java.util.ArrayList;
import java.util.Optional;

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
import JavaCA.model.TransactionDetail;
import JavaCA.service.BrandService;
import JavaCA.service.BrandServiceImpl;
import JavaCA.service.ProductService;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.SupplierService;
import JavaCA.service.SupplierServiceImpl;
import JavaCA.service.TransactionDetailsService;
import JavaCA.service.TransactionDetailsServiceImpl;
import JavaCA.service.TransactionService;
import JavaCA.service.TransactionServiceImpl;
import JavaCA.service.UserImplementation;
import JavaCA.service.UserInterface;

@Controller
@RequestMapping("/product")
public class ProductController {
	private ProductService pservice;
	private BrandService bservice;
	private SupplierService suppservice;
	private TransactionService tservice;
	private TransactionDetailsService tdservice;
	private UserInterface uservice;
	private HttpSession session;
	
	@Autowired
	public void setServices(ProductServiceImpl pservice, BrandServiceImpl bservice, 
			SupplierServiceImpl suppservice, TransactionServiceImpl tservice, 
			TransactionDetailsServiceImpl tdservice, UserImplementation uservice, HttpSession session) {
		this.pservice = pservice;
		this.bservice = bservice;
		this.suppservice = suppservice;
		this.tservice = tservice;
		this.tdservice = tdservice;
		this.uservice = uservice;
		this.session = session;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {}
	
	@RequestMapping(value={"","/list"}, method=RequestMethod.GET)
	public String findAllProducts(Model model) {
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		
		//adding model and get values for search parameters dropdown
		model.addAttribute("p", new Product());
		model.addAllAttributes(pservice.getDropdownValues());

		//for product listing
		ArrayList<Product> products = pservice.findAllProducts();
		model.addAttribute("products", products);
		
		return "/product/productlist";
	}
	
	@RequestMapping(value={"/detail/{id}"}, method=RequestMethod.GET)
	public String findProductDetail(@PathVariable long id, Model model) {
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		
		Product p = pservice.findProduct(id);
		model.addAttribute("p", p);
		return "/product/productdetail";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createProduct(Model model) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
				
		//adding model and get values for field dropdown
		model.addAttribute("p", new Product());
		model.addAllAttributes(pservice.getDropdownValues());
		return "/product/productform";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editProduct(@PathVariable long id, Model model) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
		
		//get current product details and attach to model
		Product p = pservice.findProduct(id);
		model.addAttribute("p", p);
		
		//get values for field dropdown
		model.addAllAttributes(pservice.getDropdownValues());
		return "/product/productform";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public String saveProduct(@Valid @ModelAttribute("p") Product p, BindingResult bindingResult, 
			@RequestParam(value="editBrandName", required=false) Integer editBrandName,
			@RequestParam(value="editSupplierName", required=false) Integer editSupplierName,
			Model model, RedirectAttributes redirectfrom) {
		
		//form validation check
		if (bindingResult.hasErrors() || 
				p.getBrand().getName().length()<2 || p.getBrand().getName().length()>50 ||
				p.getSupplier().getSupplierName().length()<2 || p.getSupplier().getSupplierName().length()>50) {
			if(p.getBrand().getName().length()<2 || p.getBrand().getName().length()>50) {
				model.addAttribute("errMsgBrand", "Brand name must be between 2 and 50 chars!");
			}
			if(p.getSupplier().getSupplierName().length()<2 || p.getSupplier().getSupplierName().length()>50) {
				model.addAttribute("errMsgSupplier",  "Supplier name must be between 2 and 50 chars!");
			}
			
			//get values for field dropdown
			model.addAllAttributes(pservice.getDropdownValues());
			return "/product/productform";
		}
		
		
		//search if product exist
		Product product = pservice.findProduct(p.getId());
		
		//if product exists, i.e. editing existing product details, check whether
		//to edit brand name and supplier name for all products
		if (product!=null)
		{
			//if user requests to edit brand name for all products 
			if(editBrandName!=null) {
				bservice.editBrandName(product.getBrand().getId(), p.getBrand().getName());
			}			
			
			//if user requests to edit supplier name for all products
			if(editSupplierName!=null) {
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
		

		//add redirect attribute for alert
		redirectfrom.addFlashAttribute("from", "save");
		return "redirect:/product";
	}
	
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable long id, Model model, RedirectAttributes redirectfrom) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
		
		Product p = pservice.findProduct(id);
		
		//check for existing transactionDetails to delete 
		ArrayList<TransactionDetail> transactionDetails = tdservice.findTransactionDetailsByProductId(id);
		for (TransactionDetail td : transactionDetails) {
			tdservice.deleteAllRelatedToPdt(td);
			//if no remaining transaction detail, delete transaction as well
			if(td.getTransaction().getTransactionDetails().size()==0) {
				tservice.deleteAllRelatedToPdt(td.getTransaction());
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
		model.addAllAttributes(pservice.getDropdownValues());
		
		return "product/productlist";
	}
	
	
	@RequestMapping(value="/search", method=RequestMethod.GET) 
	public String searchProduct(@RequestParam(value="sid", required=false) Optional<Long> sid,
			@RequestParam(value="bid", required=false) Optional<Long> bid, Model model) {
		
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		
		ArrayList<Product> products = new ArrayList<>();
		
		if (bid.isPresent()) 
			products = pservice.findProductsByBrandId(bid.get());
		else if (sid.isPresent()) 
			products = pservice.findProductsBySupplierId(sid.get());
		else 
			products = pservice.findAllProducts();
			
		model.addAttribute("products", products);

		//adding model and get values for search parameters dropdown
		model.addAttribute("p", new Product());
		model.addAllAttributes(pservice.getDropdownValues());
		
		return "product/productlist";
	}
}
	
