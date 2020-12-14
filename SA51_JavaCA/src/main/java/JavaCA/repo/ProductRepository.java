package JavaCA.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import JavaCA.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> 
{

}
