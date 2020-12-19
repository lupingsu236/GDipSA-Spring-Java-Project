package JavaCA.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import JavaCA.model.Product;
import JavaCA.model.RoleType;
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
	
	@RequestMapping("/new/{tid}")
	public String addProductToTransaction(@PathVariable("tid") int tid, Model model) {
		TransactionDetail transactiondetail = new TransactionDetail();
		List<Product> productList = productService.findAllProducts();
		model.addAttribute("type1", TransactionType.USAGE);
		model.addAttribute("type2", TransactionType.DAMAGED);
		model.addAttribute("pl", productList);
		model.addAttribute("td", transactiondetail);
		model.addAttribute("tid", tid);
		return "/transaction/newTransactionDetail";
	}
	
	@GetMapping("/detail/{tid}")
	public String viewTransactionDetails(Model model, @PathVariable("tid") int tid, HttpSession session)
	{
		Transaction t = transactionService.findTransactionById(tid);
		if (session.getAttribute("preView") == "all") {return "redirect:/transaction/list";}
		model.addAttribute("transaction", t);
		model.addAttribute("transactiondetail", t.getTransactionDetails());
		return "/transaction/transactiondetail";
	}
	
	@PostMapping("/save/{tid}")
	public String saveTransactionDetails(@PathVariable("tid") int tid, 
			@ModelAttribute("td") TransactionDetail td, Model model, HttpSession session) {
		Transaction t = transactionService.findTransactionById(tid);
		td.setTransaction(t);
		Product p = productService.findProduct(td.getProduct().getId());
		td.setProduct(p);
		tdService.saveTransactionDetail(td);
		if (session.getAttribute("preView") == "all") {return "redirect:/transaction/list";}
		model.addAttribute("transaction", t);
		model.addAttribute("transactiondetail", t.getTransactionDetails());
		return "/transaction/transactiondetail";
	}
	
	@RequestMapping("/edit/{tdid}")
	public String editTransactionDetails(@PathVariable("tdid") int tdid, Model model) {
		TransactionDetail td = tdService.findTransactionDetailById(tdid);
		List<Product> productList = productService.findAllProducts();
		model.addAttribute("type1", TransactionType.USAGE);
		model.addAttribute("type2", TransactionType.DAMAGED);
		model.addAttribute("pl", productList);
		model.addAttribute("td", td);
		long tid = td.getTransaction().getId();
		model.addAttribute("tid", tid);
		return "/transaction/newTransactionDetail";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTransactionDetails(@PathVariable("id") int id) {
		TransactionDetail td = tdService.findTransactionDetailById(id);
		Transaction t = td.getTransaction();
		int transactionId = (int) t.getId();
		tdService.deleteTransactionDetail(td);
		if (transactionService.noTransactionDetailsInNullTransaction(t)) {
			transactionService.deleteTransaction(t);
			return "redirect:/transaction/list";
		}
		return "redirect:/transactiondetails/detail/" + transactionId;
	}
}
