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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JavaCA.model.Product;
import JavaCA.model.RoleType;
import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.model.TransactionType;
import JavaCA.model.User;
import JavaCA.service.ProductService;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.TransactionDetailsServiceImpl;
import JavaCA.service.TransactionDetailsService;
import JavaCA.service.TransactionServiceImpl;
import JavaCA.service.TransactionService;

@Controller
@RequestMapping("/transactiondetails")
public class TransactiondetailsController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private TransactionDetailsService tdService;
	
	@Autowired
	public void setImplementation(TransactionServiceImpl transImpl, ProductServiceImpl prodImpl, TransactionDetailsServiceImpl transDetailImpl)
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
		return "/transaction/TransactionDetailForm";
	}
	
	@GetMapping("/detail/{tid}")
	public String viewTransactionDetails(@PathVariable("tid") int tid, RedirectAttributes redirectModel,
			@ModelAttribute("success") String success, HttpSession session, Model model)
	{
		Transaction t = transactionService.findTransactionById(tid);
		if (session.getAttribute("preView") == "all") {
			redirectModel.addFlashAttribute("success", success);
			return "redirect:/transaction/list";
			}
		model.addAttribute("success", success);
		model.addAttribute("transaction", t);
		model.addAttribute("transactiondetail", t.getTransactionDetails());
		return "/transaction/transactiondetail";
	}
	
	@PostMapping("/save/{tid}")
	public String saveTransactionDetails(@PathVariable("tid") int tid, RedirectAttributes redirectModel,
			@ModelAttribute("td") TransactionDetail td, HttpSession session) {
		Transaction t = transactionService.findTransactionById(tid);
		td.setTransaction(t);
		Product p = productService.findProduct(td.getProduct().getId());
		td.setProduct(p);
		String success = String.valueOf(tdService.saveTransactionDetail(td));
		redirectModel.addFlashAttribute("success", success);
		return "redirect:/transactiondetails/detail/" + t.getId();
	}
	
	@RequestMapping("/edit/{tdid}")
	public String editTransactionDetails(@PathVariable("tdid") int tdid, Model model) {
		TransactionDetail td = tdService.findTransactionDetailById(tdid);
		List<Product> productList = productService.findAllProducts();
		
		if((td.getTransactionType().toString() == "ORDER")||(td.getTransactionType().toString() == "RETURN")) {
			model.addAttribute("type1", TransactionType.ORDER);
			model.addAttribute("type2", TransactionType.RETURN);
		}
		else {
			model.addAttribute("type1", TransactionType.USAGE);
			model.addAttribute("type2", TransactionType.DAMAGED);
		}
		
		model.addAttribute("pl", productList);
		model.addAttribute("td", td);
		long tid = td.getTransaction().getId();
		model.addAttribute("tid", tid);
		return "/transaction/TransactionDetailForm";
	}
	
	@RequestMapping("/delete/{id}")
	//to force update
	public String deleteTransactionDetails(@PathVariable("id") int id,  RedirectAttributes redirectModel) {
		TransactionDetail td = tdService.findTransactionDetailById(id);
		Transaction t = td.getTransaction();
		String success = String.valueOf(tdService.deleteTransactionDetail(td));
		redirectModel.addFlashAttribute("success", success);
		if (transactionService.noTransactionDetailsInNullTransaction(t)) {
			transactionService.deleteTransaction(t);
			return "redirect:/transaction/list";
		}
		return "redirect:/transactiondetails/detail/" + t.getId();
	}
}
