package JavaCA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import JavaCA.model.Transaction;
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
		return "transactions";
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
		return "transactiondetail";
	}
}
