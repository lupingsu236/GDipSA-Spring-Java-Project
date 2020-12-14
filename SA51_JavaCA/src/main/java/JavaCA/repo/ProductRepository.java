package JavaCA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import JavaCA.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> 
{
	@Query("SELECT p FROM Product p JOIN p.brand JOIN p.supplier")
	public List<Product> findAllProducts();
	
	@Query("SELECT p FROM Product p JOIN p.brand JOIN p.supplier WHERE p.id = :pid")
	public Product findProduct(@Param("pid") long id);
}
