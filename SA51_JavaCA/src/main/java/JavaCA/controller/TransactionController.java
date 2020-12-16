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
	public String viewAllTransactions(Model model, HttpSession session)
	{
		List<Transaction> carjobs = transactionService.listAllCarTransactions();
		model.addAttribute("transactions", carjobs);
		return "/transaction/transactions";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTransactionAndTransactionDetails(@PathVariable("id") int id)
	{
		transactionService.deleteTransaction(transactionService.findTransactionById(id));
		return "forward:/transaction/list";
	}
	
	@RequestMapping("/new")
	public String newTransaction(HttpSession session, Model model) {
		Transaction t = new Transaction();
		model.addAttribute("t", t);
		return "/transaction/newTransaction";
	}
	
	@GetMapping("/edit/{id}")
	public String editTransaction(@PathVariable("id") int id, Model model, HttpSession session) {
		Transaction t = transactionService.findTransactionById(id);
		model.addAttribute("t", t);
		return "/transaction/editTransaction";
	}
	
	@PostMapping("/edit/{id}")
	public String saveEdit(@ModelAttribute("t") Transaction t, @PathVariable("id") int id, Model model, HttpSession session) {
		Transaction t2 = transactionService.findTransactionById(id);
		t2.setCarPlateNo(t.getCarPlateNo());
		transactionService.saveTransaction(t2);
		return "redirect:/transaction/list";
	}
	
	@RequestMapping("/saveTransaction")
	public String saveTransaction(@ModelAttribute("t") Transaction t, Model model, HttpSession session) {
		User u = (User)session.getAttribute("usession");
		t.setUser(u);
		transactionService.saveTransaction(t);
		return "redirect:/transaction/list";
	}
}
