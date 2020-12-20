package JavaCA.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import JavaCA.model.Product;

public interface ProductService {

	ArrayList<Product> findAllProducts();
	Product findProduct(long id);
	void saveProduct(Product p);
	void deleteProduct(Product p);
	ArrayList<String> getTypes();
	ArrayList<String> getCategories();
	ArrayList<String> getSubcategories();
	ArrayList<Product> searchProducts(Product p);
	ArrayList<Product> searchProductsBelowReorderLevel(Product p);
	ArrayList<Product> findProductsByBrandId(long bid);
	ArrayList<Product> findProductsBySupplierId(long sid);
	Map<String, List> getDropdownValues();


}
