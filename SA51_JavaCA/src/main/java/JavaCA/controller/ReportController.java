package JavaCA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JavaCA.model.Product;
import JavaCA.model.TransactionDetail;
import JavaCA.service.ProductServiceImpl;
import JavaCA.service.TransactionDetailsService;

@Controller
@RequestMapping("/report")
public class ReportController 
{
	private ProductServiceImpl pservice;
	private TransactionDetailsService tdservice;
		
	@Autowired
	public void setServices(ProductServiceImpl pservice, TransactionDetailsService tdservice) 
	{
		this.pservice = pservice;
		this.tdservice = tdservice;
	}
	
	@RequestMapping(value={"/usage"}, method=RequestMethod.GET)
	public String usageReportForProduct(Model model)
	{
		model.addAttribute("product", new Product());
		return "report/usage";
	}
	
	@RequestMapping(value={"/nonsense"}, method=RequestMethod.GET)
	public String nonsense(@RequestParam long id)
	{
		return "redirect:/report/usage/" + id;
	}
	
	@RequestMapping(value={"/usage/{id}"}, method=RequestMethod.GET)
	public String usageReportForProductId(Model model, @PathVariable long id)
	{
		String output = "report/usage";
		List<TransactionDetail> transactionDetailsForThisProduct = tdservice.findTransactionDetailsByProductId(id);
		model.addAttribute("transactiondetails", transactionDetailsForThisProduct);
		model.addAttribute("product", pservice.findProduct(id));
		if (model.containsAttribute("print"))
			output = "report/usagereportprint";
		return output;
	}
	
	@RequestMapping(value={"/usage/{id}/print"}, method=RequestMethod.GET)
	public String printUsageReportForProductId(@PathVariable long id, RedirectAttributes model)
	{
		model.addFlashAttribute("print", true);
		return "redirect:/report/usage/" + id;
	}
}
