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

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.model.User;
import JavaCA.service.TransactionDetailsServiceImpl;
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
	public void setTransactionImplementation(TransactionServiceImpl transImpl, TransactionDetailsServiceImpl transDetailImpl)
	{
		this.transactionService = transImpl;
		this.tdService = transDetailImpl;
	}
	
	@RequestMapping("/car")
	public String viewAllCarTransactions(Model model, HttpSession session)
	{
		List<Transaction> carjobs = transactionService.listAllCarTransactions();
		model.addAttribute("transactions", carjobs);
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
	public String viewAllProductTransactions(@PathVariable("productid") int id, Model model)
	{
		List<TransactionDetail> td = transactionService.listAllProductTransactions(id);
		model.addAttribute("transactiondetail", td);
		model.addAttribute("message", "product");
		return "/transaction/alltransactiondetail";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTransactionAndTransactionDetails(@PathVariable("id") int id)
	{
		transactionService.deleteTransaction(transactionService.findTransactionById(id));
		return "forward:/transaction/car";
	}
	
	@RequestMapping("/new")
	public String newTransaction(HttpSession session, Model model) {
		Transaction t = new Transaction();
		model.addAttribute("t", t);
		return "/transaction/TransactionForm";
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
