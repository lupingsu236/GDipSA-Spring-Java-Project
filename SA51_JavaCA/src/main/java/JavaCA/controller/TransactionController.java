package JavaCA.controller;

import java.util.ArrayList;
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
import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.model.TransactionType;
import JavaCA.model.User;
import JavaCA.service.TransactionDetailsServiceImpl;
import JavaCA.service.ProductService;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.TransactionDetailsService;
import JavaCA.service.TransactionServiceImpl;
import JavaCA.service.TransactionService;

@Controller
@RequestMapping("/transaction")
public class TransactionController 
{
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionDetailsService tdService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	public void setTransactionImplementation(TransactionServiceImpl transImpl, ProductServiceImpl prodImpl,
			TransactionDetailsServiceImpl transDetailImpl)
	{
		this.transactionService = transImpl;
		this.tdService = transDetailImpl;
		this.productService = prodImpl;
	}
	
	@RequestMapping("/car")
	public String viewAllCarTransactions(Model model, HttpSession session, @ModelAttribute("success") String success)
	{
		List<Transaction> carjobs = transactionService.listAllCarTransactions();
		model.addAttribute("transactions", carjobs);
		model.addAttribute("success", success);
		session.setAttribute("preView", "car");
		return "/transaction/transactions";
	}
	
	@RequestMapping("/list")
	public String viewAllTransactions(Model model, HttpSession session, @ModelAttribute("success") String success)
	{
		List<TransactionDetail> td = tdService.findAllTransactionDetails();
		model.addAttribute("transactiondetail", td);
		model.addAttribute("success", success);
		session.setAttribute("preView", "all");
		return "/transaction/alltransactiondetail";
	}
	
	@RequestMapping("/list/{productid}")
	public String viewAllProductTransactions(@PathVariable("productid") int id, Model model, 
			@ModelAttribute("success") String success)
	{
		List<TransactionDetail> td = transactionService.listAllProductTransactions(id);
		model.addAttribute("transactiondetail", td);
		model.addAttribute("message", "product");
		model.addAttribute("success", success);
		return "/transaction/alltransactiondetail";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTransactionAndTransactionDetails(@PathVariable("id") int id,  RedirectAttributes redirectModel)
	{
		
		String success = String.valueOf(transactionService.deleteTransaction(transactionService.findTransactionById(id)));
		redirectModel.addFlashAttribute("success", success);
		return "redirect:/transaction/car";
	}
	
	@RequestMapping("/new")
	public String newTransaction(HttpSession session, Model model) {
		Transaction t = new Transaction();
		model.addAttribute("t", t);
		return "/transaction/TransactionForm";
	}
	
	@GetMapping("/newStockEntry")
	public String newStockEntryTransaction(Model model) {
		TransactionDetail td = new TransactionDetail();
		List<Product> productList = productService.findAllProducts();
		model.addAttribute("type1", TransactionType.ORDER);
		model.addAttribute("type2", TransactionType.RETURN);
		model.addAttribute("pl", productList);
		model.addAttribute("td", td);
		return "/transaction/StockUsageForm";
	}
	
	@PostMapping("/newStockEntry")
	public String saveNewStockEntryTransaction(@ModelAttribute("td") TransactionDetail td, 
			RedirectAttributes redirectModel, HttpSession session) {
		//New transaction
		Transaction t = new Transaction();
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
	public String editTransaction(@PathVariable("id") int id, Model model, HttpSession session) {
		Transaction t = transactionService.findTransactionById(id);
		model.addAttribute("t", t);
		return "/transaction/TransactionForm";
	}
	
	@RequestMapping("/saveTransaction")
	public String saveTransaction(@ModelAttribute("t") Transaction t, Model model, HttpSession session) {
		User u = (User)session.getAttribute("usession");
		t.setUser(u);
		transactionService.saveTransaction(t);
		return "redirect:/transaction/car";
	}
}
