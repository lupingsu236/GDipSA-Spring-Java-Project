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
import JavaCA.service.ProductService;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.TransactionDetailsService;
import JavaCA.service.TransactionDetailsServiceImpl;
import JavaCA.service.TransactionService;
import JavaCA.service.TransactionServiceImpl;
import JavaCA.service.UserImplementation;
import JavaCA.service.UserInterface;

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
	
	@RequestMapping("/all")
	public String viewAllTransactions(Model model, @ModelAttribute("success") String success, HttpSession session)
	{
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		//List all transactions
		List<Transaction> all = transactionService.listAllTransactions();
		model.addAttribute("transactions", all);
		//Receives success/failure from redirectattr"/transaction/alltransactiondetail"ibutes model, send to view for notification
		model.addAttribute("success", success);
		//Remember page to return to this page upon cancellation of form
		session.setAttribute("preView", "allt");
		//View changes dynamically depending on the user role type
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
		//List all transaction details
		List<TransactionDetail> td = tdService.findAllTransactionDetails();
		model.addAttribute("transactiondetail", td);
		//Receives success/failure from redirectattributes model, send to view for notification
		model.addAttribute("success", success);	
		//View changes dynamically depending on the user role type
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", user);
		//Remember page to return to this page upon cancellation of form
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
		//List all PRODUCT transaction details
		List<TransactionDetail> td = transactionService.listAllProductTransactions(id);
		model.addAttribute("transactiondetail", td);
		//To display header message as "product" instead of "all"
		model.addAttribute("message", "product");
		//Receives success/failure from redirectattributes model, send to view for notification
		model.addAttribute("success", success);
		//View changes dynamically depending on the user role type
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", user);
		session.setAttribute("preView", "products");
		return "/transaction/alltransactiondetail";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTransactionAndTransactionDetails(@PathVariable("id") int id, RedirectAttributes redirectModel)
	{
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		//String.valueof(boolean of whether deletion was successful)
		String success = String.valueOf(transactionService.deleteTransaction(transactionService.findTransactionById(id)));
		//Pass success true/false by redirect model
		redirectModel.addFlashAttribute("success", success);
		return "redirect:/transaction/all";
	}
	
	@RequestMapping("/new")
	public String newTransaction(Model model) {
		//check if user has logged in, otherwise redirect
		if(!uservice.verifyLogin(session)) {
			return "redirect:/";
		}
		//New transaction
		Transaction t = new Transaction();
		model.addAttribute("t", t);
		//Set page to return to upon cancellation of form
		model.addAttribute("preView", session.getAttribute("preView"));
		return "/transaction/TransactionForm";
	}
	
	@GetMapping("/newStockEntry")
	public String newStockEntryTransaction(Model model) {
		//check if user is admin, otherwise redirect
		if(!uservice.verifyAdmin(session)) {
			return "redirect:/";
		}
		//New transaction detail
		//Product list for dropdown selection
		//Non-car transaction list for dropdown selection
		//Stock entry is only possible if you are an ADMIN role type
		//All stock entries do not have an associated carplate number
		TransactionDetail td = new TransactionDetail();
		List<Product> productList = productService.findAllProducts();
		List<Transaction> transactionList = transactionService.listAllNonCarTransactions();
		model.addAttribute("td", td);
		model.addAttribute("pl", productList);
		model.addAttribute("tl", transactionList);
		//To get the two specific transaction types
		model.addAttribute("type1", TransactionType.ORDER);
		model.addAttribute("type2", TransactionType.RETURN);
		return "/transaction/StockUsageForm";
	}
	
	@PostMapping("/newStockEntry")
	public String saveNewStockEntryTransaction(@Valid @ModelAttribute("td") TransactionDetail td, BindingResult bd, 
			RedirectAttributes redirectModel, HttpSession session, Model model) {
		//New transaction if no selected transaction
		Transaction t;
		List<TransactionDetail> tdList;
		if (bd.hasErrors()) {
			//Pass to view all the required parameters in the dropdowns
			List<Product> productList = productService.findAllProducts();
			List<Transaction> transactionList = transactionService.listAllNonCarTransactions();
			//Transaction detail td is from the previous error-ing page
			model.addAttribute("td", td);
			model.addAttribute("pl", productList);
			model.addAttribute("tl", transactionList);
			model.addAttribute("type1", TransactionType.ORDER);
			model.addAttribute("type2", TransactionType.RETURN);
			return "/transaction/StockUsageForm";
		}
		//Form value sets td's transaction.Id to -1 if "Create new transaction" is selected
		//Create new transaction & ready a new transactiondetail list to add to it
		if(td.getTransaction().getId() == -1) {
			t = new Transaction();
			tdList = new ArrayList<TransactionDetail>();
		}
		//If an old transaction already exists, find it.
		else {
			t = transactionService.findTransactionById(td.getTransaction().getId());
			tdList = t.getTransactionDetails();
			}

		//Update new/old transaction with current user 
		t.setUser((User)session.getAttribute("usession"));
		//Update new/old transaction with new addition of transactiondetails
		tdList.add(td);
		t.setTransactionDetails(tdList);
		transactionService.saveTransaction(t);
		
		//Find product (cause incomplete in form) using product id
		Product p = productService.findProduct(td.getProduct().getId());
		//Set product in transaction details
		td.setProduct(p);
		td.setTransaction(t);
		String success = String.valueOf(tdService.saveTransactionDetail(td));
		//Pass success true/false by redirect model
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
		//Set page to return to upon cancellation of form
		model.addAttribute("preView", session.getAttribute("preView"));
		return "/transaction/TransactionForm";
	}
	
	@RequestMapping("/saveTransaction") //@Valid followed directly by BindingResult
	public String saveTransaction(@Valid @ModelAttribute("t") Transaction t, BindingResult bd, 
			Model model, HttpSession session, RedirectAttributes redirectModel) {
		//If form has error
		if (bd.hasErrors()) {
			//Set page to return to upon cancellation of form
			model.addAttribute("preView", session.getAttribute("preView"));
			return "/transaction/TransactionForm";
		}
		User u = (User)session.getAttribute("usession");
		t.setUser(u);
		String success = String.valueOf(transactionService.saveTransaction(t));
		redirectModel.addFlashAttribute("success", success);
		return "redirect:/transaction/all";
	}
}
