package JavaCA.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.Product;
import JavaCA.repo.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	private ProductRepository prepo; 
	
	@Autowired
	public void setProductRepo(ProductRepository prepo) {
		this.prepo = prepo;
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

}
