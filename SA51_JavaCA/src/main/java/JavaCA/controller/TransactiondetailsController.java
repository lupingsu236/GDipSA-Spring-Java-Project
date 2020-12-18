package JavaCA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import JavaCA.model.Product;
import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.model.TransactionType;
import JavaCA.service.ProductService;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.TransactionDetailsInterface;
import JavaCA.service.TransactionImplementation;
import JavaCA.service.TransactionInterface;

@Controller
@RequestMapping("/transactiondetails")
public class TransactiondetailsController {
	
	@Autowired
	private TransactionInterface transactionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private TransactionDetailsInterface tdService;
	
	@Autowired
	public void setImplementation(TransactionImplementation transImpl, ProductServiceImpl prodImpl, TransactionDetailsInterface transDetailImpl)
	{
		this.transactionService = transImpl;
		this.productService = prodImpl;
		this.tdService = transDetailImpl;
	}
	
	@RequestMapping("/new/{id}")
	public String addProductToTransaction(@PathVariable("id") int id, Model model) {
		TransactionDetail transactiondetail = new TransactionDetail();
		List<Product> productList = productService.findAllProducts();
		model.addAttribute("type1", TransactionType.ORDER);
		model.addAttribute("type2", TransactionType.DAMAGED);
		model.addAttribute("pl", productList);
		model.addAttribute("td", transactiondetail);
		model.addAttribute("id", id);
		return "/transaction/newTransactionDetail";
	}
	
	@GetMapping("/detail/{id}")
	public String viewTransactionDetails(Model model, @PathVariable("id") int id)
	{
		Transaction thisTransaction = transactionService.findTransactionById(id);
		model.addAttribute("transaction", thisTransaction);
		model.addAttribute("transactiondetail", thisTransaction.getTransactionDetails());
		return "/transaction/transactiondetail";
	}
	
	@PostMapping("/detail/{id}")
	public String saveTransactionDetails(@PathVariable("id") int id, @ModelAttribute("td") TransactionDetail td, Model model) {
		Transaction t = transactionService.findTransactionById(id);
		td.setTransaction(t);
		Product p = productService.findProduct(td.getProduct().getId());
		td.setProduct(p);
		tdService.saveTransactionDetail(td);
		//---------
		Transaction thisTransaction = transactionService.findTransactionById(id);
		model.addAttribute("transaction", thisTransaction);
		model.addAttribute("transactiondetail", thisTransaction.getTransactionDetails());
		return "/transaction/transactiondetail";
	}
}
