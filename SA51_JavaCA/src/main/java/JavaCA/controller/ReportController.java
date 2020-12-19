package JavaCA.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	
	@RequestMapping(value={"/search"}, method=RequestMethod.POST)
	public String searchUsageReportForProduct(RedirectAttributes model, @RequestParam long id, @RequestParam String fromDate, 
						   @RequestParam String toDate)
	{
		if (!fromDate.isBlank())
			model.addFlashAttribute("fromDate", Date.valueOf(fromDate));
		if (!toDate.isBlank())
			model.addFlashAttribute("toDate", Date.valueOf(toDate));
		return "redirect:/report/usage/" + id;
	}
	
	@RequestMapping(value={"/usage/{id}"}, method=RequestMethod.GET)
	public String usageReportForProductId(Model model, @PathVariable long id)
	{
		String output = "report/usage";
		List<TransactionDetail> transactionDetailsForThisProduct = tdservice.findTransactionDetailsByProductId(id);
		if (model.getAttribute("fromDate") == null && model.getAttribute("toDate") == null)
		{
			model.addAttribute("transactiondetails", transactionDetailsForThisProduct);
		}
		else
		{
			if(model.getAttribute("fromDate") != null && model.getAttribute("toDate") != null)
			{
				Date fromDate = (Date) model.getAttribute("fromDate");
				Date toDate = (Date) model.getAttribute("toDate");
				transactionDetailsForThisProduct = transactionDetailsForThisProduct.stream().filter(x -> 
																	x.getDate().compareTo(toDate) <= 0
																	&& x.getDate().compareTo(fromDate) >= 0)
														   			.collect(Collectors.toList());
				model.addAttribute("transactiondetails", transactionDetailsForThisProduct);
			}
			else if (model.getAttribute("fromDate") != null)
			{
				Date fromDate = (Date) model.getAttribute("fromDate");
				transactionDetailsForThisProduct = transactionDetailsForThisProduct.stream().filter(x -> 
																	x.getDate().compareTo(fromDate) >= 0)
																	.collect(Collectors.toList());
				model.addAttribute("transactiondetails", transactionDetailsForThisProduct);
			}
			else
			{
				Date toDate = (Date) model.getAttribute("toDate");
				transactionDetailsForThisProduct = transactionDetailsForThisProduct.stream().filter(x -> 
																	x.getDate().compareTo(toDate) <= 0)
																	.collect(Collectors.toList());
				model.addAttribute("transactiondetails", transactionDetailsForThisProduct);
			}
		}
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
