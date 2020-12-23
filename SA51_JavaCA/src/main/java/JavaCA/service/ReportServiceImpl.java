package JavaCA.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.Product;
import JavaCA.model.Supplier;

@Service
public class ReportServiceImpl implements ReportService 
{
	private SupplierService sservice;
	private ProductService pservice;
	
	@Autowired
	public void setServices(ProductServiceImpl pservice, SupplierServiceImpl sservice) 
	{
		this.sservice = sservice;
		this.pservice = pservice;
	}
	
	@Override
	public Map<Double, List<List<Product>>> reorderReportData()
	{
		Map<Double, List<List<Product>>> output = new HashMap<>();
		
		double grandTotal = 0;
		List<List<Product>> listOfListsOfProduct = new ArrayList<>();
		List<Supplier> suppliers = sservice.findAllSuppliers();
		for (Supplier s:suppliers)
		{
			List<Product> productsThatRequireReorderBySupplier = pservice.findAllProducts().stream()
					   .filter(x -> x.getQuantity() <= x.getReorderLevel())
					   .filter(x -> x.getSupplier().getId() == s.getId())
					   .collect(Collectors.toList());
			if (productsThatRequireReorderBySupplier.size() > 0)
			{
				listOfListsOfProduct.add(productsThatRequireReorderBySupplier);
				grandTotal = grandTotal + productsThatRequireReorderBySupplier.stream()
				.mapToDouble(x -> x.getOriginalPrice() * (x.getReorderLevel() - x.getQuantity() + x.getMinOrderQty()))
				.sum();
			}
		}
		
		output.put(grandTotal, listOfListsOfProduct);
		return output;
	}
}
