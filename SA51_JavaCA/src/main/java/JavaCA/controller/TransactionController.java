package JavaCA.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import JavaCA.model.Product;
import JavaCA.model.Transaction;
import JavaCA.model.User;
import JavaCA.service.TransactionImplementation;
import JavaCA.service.TransactionInterface;

@Controller
@RequestMapping("/transaction")
public class TransactionController 
{
	@Autowired
	private TransactionInterface transactionService;
	
	@Autowired
	public void setTransactionImplementation(TransactionImplementation transImpl)
	{
		this.transactionService = transImpl;
	}
	
	@RequestMapping("/list")
	public String viewAllTransactions(Model model)
	{
		model.addAttribute("transactions", transactionService.listAllTransactions());
		return "/transaction/transactions";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTransactionAndTransactionDetails(@PathVariable("id") int id)
	{
		transactionService.deleteTransaction(transactionService.findTransactionById(id));
		return "forward:/transaction/list";
	}
	
	@RequestMapping("/detail/{id}")
	public String viewTransactionDetails(Model model, @PathVariable("id") int id)
	{
		Transaction thisTransaction = transactionService.findTransactionById(id);
		model.addAttribute("transaction", thisTransaction);
		model.addAttribute("transactiondetail", thisTransaction.getTransactionDetails());
		return "/transaction/transactiondetail";
	}
	
	@RequestMapping("/new")
	public String newTransaction(HttpSession session, Model model) {
		Transaction t = new Transaction();
		model.addAttribute("t", t);
		return "/transaction/transactionForm";
	}
	
	@RequestMapping("/edit/{id}")
	public String editTransaction(@PathVariable("id") int id, Model model, HttpSession session) {
		Transaction t = transactionService.findTransactionById(id);
		model.addAttribute("t", t);
		return "/transaction/transactionForm";
	}
	
	@RequestMapping("/saveTransaction")
	public String saveTransaction(@ModelAttribute("t") Transaction t, Model model, HttpSession session) {
		User u = (User)session.getAttribute("usession");
		t.setUser(u);
		transactionService.saveTransaction(t);
		return "redirect:/transaction/list";
	}
}
