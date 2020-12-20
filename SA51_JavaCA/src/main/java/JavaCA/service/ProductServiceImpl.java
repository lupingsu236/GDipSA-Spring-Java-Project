package JavaCA.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.Product;
import JavaCA.repo.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	private ProductRepository prepo; 
	private BrandService bservice;
	private SupplierService suppservice;
	
	@Autowired
	public void setProductRepo(ProductRepository prepo) {
		this.prepo = prepo;
	}
	
	@Autowired
	public void setServices(BrandServiceImpl bservice, SupplierServiceImpl suppservice) {
		this.bservice = bservice;
		this.suppservice = suppservice;
	}
	
	

	@Override
	public ArrayList<Product> findAllProducts() {
		ArrayList<Product> products = (ArrayList<Product>) prepo.findAllProducts();
		return products;
	}

	@Override
	public Product findProduct(long id) {
		Product p = prepo.findProduct(id);
		return p;
	}

	@Override
	public void saveProduct(Product p) {
		prepo.save(p);
		
	}

	@Override
	public void deleteProduct(Product p) {
		prepo.delete(p);
		
	}

	@Override
	public ArrayList<String> getTypes() {
		return (ArrayList<String>) prepo.getTypes();
	}

	@Override
	public ArrayList<String> getCategories() {
		return (ArrayList<String>) prepo.getCategories();
	}

	@Override
	public ArrayList<String> getSubcategories() {
		return (ArrayList<String>) prepo.getSubcategories();
	}

	@Override
	public ArrayList<Product> searchProducts(Product p) {
		return (ArrayList<Product>) prepo.searchProducts(p);
	}
	
	@Override
	public ArrayList<Product> searchProductsBelowReorderLevel(Product p) {
		return (ArrayList<Product>) prepo.searchProductsBelowReorderLevel(p);
	}

	@Override
	public ArrayList<Product> findProductsByBrandId(long bid) {
		return (ArrayList<Product>) prepo.findProductsByBrandId(bid);
	}
	
	@Override
	public ArrayList<Product> findProductsBySupplierId(long sid) {
		return (ArrayList<Product>) prepo.findProductsBySupplierId(sid);
	}

	@Override
	public Map<String, List> getDropdownValues() {
		Map<String, List> values = new HashMap<>();
		values.put("types", getTypes());
		values.put("categories", getCategories());
		values.put("subcategories", getSubcategories());
		values.put("brands", bservice.findAllBrands());
		values.put("suppliers", suppservice.findAllSuppliers());
		return values;
	}


}
