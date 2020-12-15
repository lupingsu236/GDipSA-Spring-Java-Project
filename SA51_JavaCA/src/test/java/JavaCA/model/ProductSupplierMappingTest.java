package JavaCA.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import JavaCA.repo.BrandRepository;
import JavaCA.repo.ProductRepository;
import JavaCA.repo.SupplierRepository;

@SpringBootTest
class ProductSupplierMappingTest 
{
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private SupplierRepository suppRepo;

	@Test
	void creationTest() 
	{
		Brand honda = new Brand("Honda");
		brandRepo.save(honda);
		Product product1 = new Product("name1", "desc1", "type1", "cat1", "subcat1", 10, 5, 20, 30);
		product1.setBrand(honda);
		Supplier supplier1 = new Supplier("Supplier1");
		suppRepo.save(supplier1);
		product1.setSupplier(supplier1);
		productRepo.save(product1);

		Product product2 = new Product("name2", "desc2", "type2", "cat2", "subcat2", 7, 10, 15, 60);
		product2.setBrand(brandRepo.findBrandByName("Honda"));
		Supplier supplier2 = new Supplier("Supplier2");
		suppRepo.save(supplier2);
		product2.setSupplier(supplier2);
		productRepo.save(product2);

		Product product3 = new Product("name3", "desc3", "type1", "cat3", "subcat3", 3, 40, 100, 200);
		Brand toyota = new Brand("Toyota");
		brandRepo.save(toyota);
		product3.setBrand(toyota);
		product3.setSupplier(supplier1);
		productRepo.save(product3);
	}

}