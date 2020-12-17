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

}
