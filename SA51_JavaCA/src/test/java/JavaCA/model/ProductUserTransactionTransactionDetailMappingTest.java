package JavaCA.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import JavaCA.repo.BrandRepository;
import JavaCA.repo.ProductRepository;
import JavaCA.repo.SupplierRepository;
import JavaCA.repo.TransactionDetailRepository;
import JavaCA.repo.TransactionRepository;
import JavaCA.repo.UserRepository;

@SpringBootTest
class ProductUserTransactionTransactionDetailMappingTest 
{
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private TransactionRepository transactionRepo;
	@Autowired
	private TransactionDetailRepository transactionDetailRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Test
	void creationTest() 
	{
		// Creation of product
		// First create and persist brand (assuming the brand is not already persisted)
		Brand mazda = new Brand("Mazda");
		brandRepo.save(mazda);
		// Next create and persist supplier for this particular product (assuming the supplier is not already persisted)
		Supplier supplier = new Supplier("Supplier");
		supplierRepo.save(supplier);
		// Create the product and set the brand and suppliers that have been persisted, then persist the product.
		Product product = new Product("name", "description", "type", "category", "subcategory", 4, 80, 250, 50);
		product.setBrand(mazda);
		product.setSupplier(supplier);
		productRepo.save(product);
		
		// Creation of Transaction detail
		// First create and persist the user (assuming the user is not already persisted)
		User user = new User("Jon", "jon", "password", RoleType.ADMIN);
		userRepo.save(user);
		// Create the transaction and set the user before persisting (assuming this is a new transaction)
		Transaction t1 = new Transaction("SJA1234H");
		t1.setUser(user);
		transactionRepo.save(t1);
		// Create the transaction detail and set product and transaction before persisting
		TransactionDetail td1 = new TransactionDetail(-1, TransactionType.USAGE);
		td1.setProduct(product);
		td1.setTransaction(t1);
		transactionDetailRepo.save(td1);
		// update the quantity of the product in the db
		product.setQuantity(product.getQuantity() + td1.getQuantityChange());
		productRepo.save(product);
	}

}