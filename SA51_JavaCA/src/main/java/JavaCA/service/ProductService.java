package JavaCA.service;

import java.util.ArrayList;

import JavaCA.model.Product;

public interface ProductService {

	ArrayList<Product> findAllProducts();
	Product findProduct(long id);
	void saveProduct(Product p);
	void deleteProduct(Product p);

}
