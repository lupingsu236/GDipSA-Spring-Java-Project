package JavaCA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import JavaCA.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> 
{
	@Query("SELECT p FROM Product p JOIN p.brand JOIN p.supplier")
	List<Product> findAllProducts();
	
	@Query("SELECT p FROM Product p JOIN p.brand JOIN p.supplier WHERE p.id = :pid")
	Product findProduct(@Param("pid") long id);
	
	@Query("SELECT p.type FROM Product p GROUP BY p.type")
	List<String> getTypes();
	
	@Query("SELECT p.category FROM Product p GROUP BY p.category")
	List<String> getCategories();
	
	@Query("SELECT p.subcategory FROM Product p GROUP BY p.subcategory")
	List<String> getSubcategories();
	
	@Query("SELECT p FROM Product p JOIN p.brand JOIN p.supplier "
			+ "WHERE p.name LIKE %:#{#pdt.name}% "
			+ "AND p.description LIKE %:#{#pdt.description}% "
			+ "AND p.type LIKE %:#{#pdt.type}% "
			+ "AND p.category LIKE %:#{#pdt.category}% "
			+ "AND p.subcategory LIKE %:#{#pdt.subcategory}% "
			+ "AND p.brand.name LIKE %:#{#pdt.brand.name}% "
			+ "AND p.supplier.supplierName LIKE %:#{#pdt.supplier.supplierName}% ")
	List<Product> searchProducts(@Param("pdt") Product pdt);
	
	@Query("SELECT p FROM Product p JOIN p.brand JOIN p.supplier "
			+ "WHERE p.name LIKE %:#{#pdt.name}% "
			+ "AND p.description LIKE %:#{#pdt.description}% "
			+ "AND p.type LIKE %:#{#pdt.type}% "
			+ "AND p.category LIKE %:#{#pdt.category}% "
			+ "AND p.subcategory LIKE %:#{#pdt.subcategory}% "
			+ "AND p.brand.name LIKE %:#{#pdt.brand.name}% "
			+ "AND p.supplier.supplierName LIKE %:#{#pdt.supplier.supplierName}% "
			+ "AND p.quantity < p.reorderLevel ")
	List<Product> searchProductsBelowReorderLevel(@Param("pdt") Product pdt);
}
