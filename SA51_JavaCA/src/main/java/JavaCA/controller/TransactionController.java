package JavaCA.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JavaCA.model.Product;
import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.model.TransactionType;
import JavaCA.model.User;
import JavaCA.service.TransactionDetailsServiceImpl;
import JavaCA.service.ProductService;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.TransactionDetailsService;
import JavaCA.service.TransactionServiceImpl;
import JavaCA.service.UserImplementation;
import JavaCA.service.UserInterface;
import JavaCA.service.TransactionService;

@Controller
@RequestMapping("/transaction")
public class TransactionController 
{
	private TransactionService transactionService;
	private TransactionDetailsService tdService;
	private ProductService productService;
	private UserInterface uservice;
	private HttpSession session;
	
	@Autowired
	public void setServices(TransactionServiceImpl transImpl, ProductServiceImpl prodImpl,
			TransactionDetailsServiceImpl transDetailImpl, UserImplementation uservice,
			HttpSession session)
	{
		this.transactionService = transImpl;
		this.tdService = transDetailImpl;
		this.productService = prodImpl;
		this.uservice = uservice;
		this.session = session;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	}
	
	
	@RequestMapping("/car")
	public String viewAllCarTransactions(Model model, @ModelAttribute("success") String success)
	{	
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		
		List<Transaction> carjobs = transactionService.listAllCarTransactions();
		model.addAttribute("transactions", carjobs);
		model.addAttribute("success", success);
		session.setAttribute("preView", "car");
		model.addAttribute("preView", session.getAttribute("preView"));
		return "/transaction/transactions";
	}
	
	@RequestMapping("/all")
	public String viewAllTransactions(Model model, @ModelAttribute("success") String success, HttpSession session)
	{
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		
		List<Transaction> all = transactionService.listAllTransactions();
		model.addAttribute("transactions", all);
		model.addAttribute("success", success);
		session.setAttribute("preView", "all");
		model.addAttribute("preView", session.getAttribute("preView"));
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", user);
		return "/transaction/transactions";
	}
	
	@RequestMapping("/list")
	public String viewAllTransactionsDetails(Model model, @ModelAttribute("success") String success, HttpSession session)
	{
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		
		List<TransactionDetail> td = tdService.findAllTransactionDetails();
		model.addAttribute("transactiondetail", td);
		model.addAttribute("success", success);	
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", user.getRoleName());
		session.setAttribute("preView", "alltd");
		return "/transaction/alltransactiondetail";
	}
	
	@RequestMapping("/list/{productid}")
	public String viewAllProductTransactions(@PathVariable("productid") int id, Model model, 
			@ModelAttribute("success") String success)
	{
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		
		List<TransactionDetail> td = transactionService.listAllProductTransactions(id);
		model.addAttribute("transactiondetail", td);
		model.addAttribute("message", "product");
		model.addAttribute("success", success);
		return "/transaction/alltransactiondetail";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTransactionAndTransactionDetails(@PathVariable("id") int id, RedirectAttributes redirectModel)
	{
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
			
		String success = String.valueOf(transactionService.deleteTransaction(transactionService.findTransactionById(id)));
		redirectModel.addFlashAttribute("success", success);
		if (session.getAttribute("preView") == "all") {return "redirect:/transaction/all";}
		return "redirect:/transaction/car";
	}
	
	@RequestMapping("/new")
	public String newTransaction(Model model) {
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
					
		Transaction t = new Transaction();
		model.addAttribute("t", t);
		model.addAttribute("preView", session.getAttribute("preView"));
		return "/transaction/TransactionForm";
	}
	
	@GetMapping("/newStockEntry")
	public String newStockEntryTransaction(Model model) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
					
		TransactionDetail td = new TransactionDetail();
		List<Product> productList = productService.findAllProducts();
		List<Transaction> transactionList = transactionService.listAllNonCarTransactions();
		model.addAttribute("type1", TransactionType.ORDER);
		model.addAttribute("type2", TransactionType.RETURN);
		model.addAttribute("pl", productList);
		model.addAttribute("tl", transactionList);
		model.addAttribute("td", td);
		return "/transaction/StockUsageForm";
	}
	
	@PostMapping("/newStockEntry")
	public String saveNewStockEntryTransaction(@Valid @ModelAttribute("td") TransactionDetail td, BindingResult bd, 
			RedirectAttributes redirectModel, HttpSession session, Model model) {
		//New transaction if no selected transaction
		Transaction t;
		if (bd.hasErrors()) {
			List<Product> productList = productService.findAllProducts();
			List<Transaction> transactionList = transactionService.listAllNonCarTransactions();
			model.addAttribute("type1", TransactionType.ORDER);
			model.addAttribute("type2", TransactionType.RETURN);
			model.addAttribute("pl", productList);
			model.addAttribute("tl", transactionList);
			model.addAttribute("td", td);
			return "/transaction/StockUsageForm";
		}
		if(td.getTransaction().getId() == -1) {
			t = new Transaction();
		}
		else {t = transactionService.findTransactionById(td.getTransaction().getId());}
		t.setUser((User)session.getAttribute("usession"));
		List<TransactionDetail> tdList = new ArrayList<TransactionDetail>();
		tdList.add(td);
		t.setTransactionDetails(tdList);
		transactionService.saveTransaction(t);
		//Input product (cause incomplete in form) into transaction detail
		Product p = productService.findProduct(td.getProduct().getId());
		td.setProduct(p);
		td.setTransaction(t);
		String success = String.valueOf(tdService.saveTransactionDetail(td));
		redirectModel.addFlashAttribute("success", success);
		return "redirect:/transaction/list/" + td.getProduct().getId();
	}
	
	@GetMapping("/edit/{id}")
	public String editTransaction(@PathVariable("id") int id, Model model) {
		//check if user is logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
					
		Transaction t = transactionService.findTransactionById(id);
		model.addAttribute("t", t);
		model.addAttribute("preView", session.getAttribute("preView"));
		return "/transaction/TransactionForm";
	}
	
	@RequestMapping("/saveTransaction")
	public String saveTransaction(@Valid @ModelAttribute("t") Transaction t, BindingResult bd, 
			Model model, HttpSession session, RedirectAttributes redirectModel) {
		if (bd.hasErrors()) {
			model.addAttribute("preView", session.getAttribute("preView"));
			return "/transaction/TransactionForm";
		}
		User u = (User)session.getAttribute("usession");
		t.setUser(u);
		String success = String.valueOf(transactionService.saveTransaction(t));
		if (session.getAttribute("preView") == "all") {
			redirectModel.addFlashAttribute("success", success);
			return "redirect:/transaction/all";
			}
		return "redirect:/transaction/car";
	}
}
