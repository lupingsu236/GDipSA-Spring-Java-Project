//package JavaCA.model;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import JavaCA.repo.BrandRepository;
//import JavaCA.repo.ProductRepository;
//import JavaCA.repo.SupplierRepository;
//import JavaCA.repo.TransactionDetailRepository;
//import JavaCA.repo.TransactionRepository;
//import JavaCA.repo.UserRepository;
//
//@SpringBootTest
//class ProductUserTransactionTransactionDetailMappingTest 
//{
//	@Autowired
//	private UserRepository userRepo;
//	@Autowired
//	private TransactionRepository transactionRepo;
//	@Autowired
//	private TransactionDetailRepository transactionDetailRepo;
//	@Autowired
//	private ProductRepository productRepo;
//	@Autowired
//	private BrandRepository brandRepo;
//	@Autowired
//	private SupplierRepository supplierRepo;
//	
//	@Test
//	void creationTest() 
//	{
//		// Creation of product
//		// First create and persist brand (assuming the brand is not already persisted)
//		Brand mazda = new Brand("Mazda");
//		brandRepo.save(mazda);
//		// Next create and persist supplier for this particular product (assuming the supplier is not already persisted)
//		Supplier supplier = new Supplier("Supplier");
//		supplierRepo.save(supplier);
//		// Create the product and set the brand and suppliers that have been persisted, then persist the product.
//		Product product = new Product("name", "description", "type", "category", "subcategory", 4, 80, 250, 50);
//		product.setBrand(mazda);
//		product.setSupplier(supplier);
//		productRepo.save(product);
//		
//		// Creation of Transaction detail
//		// First create and persist the user (assuming the user is not already persisted)
//		User user = new User("Liau Han Yang, Jonathan", "liauhyj", "password", "liau@gmail.com",RoleType.MECHANIC, ActiveType.ACTIVE);
//		userRepo.save(user);
//		// Create the transaction and set the user before persisting (assuming this is a new transaction)
//		Transaction t1 = new Transaction("SJA1234H");
//		t1.setUser(user);
//		transactionRepo.save(t1);
//		// Create the transaction detail and set product and transaction before persisting
//		TransactionDetail td1 = new TransactionDetail(1, TransactionType.USAGE);
//		td1.setProduct(product);
//		td1.setTransaction(t1);
//		transactionDetailRepo.save(td1);
//		// update the quantity of the product in the db
//		product.setQuantity(product.getQuantity() - td1.getQuantityChange());
//		productRepo.save(product);
//		
//		User lup = new User("Su Luping", "sulp", "password","sulp@gmail.com", RoleType.ADMIN,ActiveType.ACTIVE);
//		userRepo.save(lup);
//	}
//
//}




package JavaCA.model;

import java.sql.Date;

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
		User user = new User("Liau Han Yang, Jonathan", "liauhyj", "password", "liau@gmail.com",RoleType.MECHANIC, ActiveType.ACTIVE,"active","mechanice");
		userRepo.save(user);
		// Create the transaction and set the user before persisting (assuming this is a new transaction)
		Transaction t1 = new Transaction("SJA1234H");
		t1.setUser(user);
		transactionRepo.save(t1);
		// Create the transaction detail and set product and transaction before persisting
		TransactionDetail td1 = new TransactionDetail(1, TransactionType.USAGE);
		td1.setProduct(product);
		td1.setTransaction(t1);
		transactionDetailRepo.save(td1);
//		 update the quantity of the product in the db
		product.setQuantity(product.getQuantity() - td1.getQuantityChange());
		productRepo.save(product);
		
		User lup = new User("Su Luping", "sulp", "password","sulp@gmail.com", RoleType.ADMIN,ActiveType.ACTIVE,"active","admin");
		userRepo.save(lup);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void dataseeding() {
		Brand b1=new Brand("Honda");
		Brand b2=new Brand("Toyota");
		Brand b3=new Brand("Aisin");
		Brand b4=new Brand("Valeo");
		Brand b5=new Brand("Mando");
		brandRepo.save(b1);
		brandRepo.save(b2);
		brandRepo.save(b3);
		brandRepo.save(b4);
		brandRepo.save(b5);
		
		Supplier s1=new Supplier("Min Ghee Auto Pte Ltd");
		Supplier s2=new Supplier("Transglober Auto Pte Ltd ");
		Supplier s3=new Supplier("Kheng Keng Auto Pte Ltd");
		Supplier s4=new Supplier("YSH Pte Ltd");
		Supplier s5=new Supplier("Bridgestone Tyre Sales Singapore Pte Ltd");

		supplierRepo.save(s1);
		supplierRepo.save(s2);
		supplierRepo.save(s3);
		supplierRepo.save(s4);
		supplierRepo.save(s5);
		
		Product p1=new Product("BP7440LL","Part description: T-6 bulb 3/4 dia; Color: clear; light center length(ln.): 1.250; Design volts primary: 12; Design watts primary: 21; Design amps primary: 1.75.","Electrical","Light", "Brake light", 13.08, 100,100,150);
		Product p2=new Product("D3S","Part description: T-3 bulb 3/8 dia; Color: clear; Design volts primary: 42; Design watts primary: 35; Design amps primary: 0.83.","Electrical","Light", "Headlight", 14.9, 120,50,100);
		Product p3=new Product("BPH11TVX","Color: clear; Filament quantity: 1; Design volts primary: 12; Design watts primary: 55; Design amps primary: 4.58; Related avg. life hours primary: 300.","Electrical","Light", "Fog light", 15.02, 100,50,500);
		Product p4=new Product("BPH11ND2","Part description: T-4 bulb 37/64 dia; Color: clear; Filament quantity: 1; Design volts primary: 12; Design watts primary: 55; Design amps primary: 4.58.","Electrical","Light", "Fog light", 13.81, 100,50,500);
		Product p5=new Product("BP168LL","Part description: T-3 bulb 13/32 dia; Color: clear; Filament quantity: 1; Design volts primary: 14; Design watts primary: 4.9; Design amps primary: 0.35; Related avg. life hours primary: 1500.","Electrical","Light", "Trunk light", 9.75, 100,75,500);
		
		p1.setBrand(b1);
		p1.setSupplier(s1);
		productRepo.save(p1);
		p2.setBrand(b1);
		p2.setSupplier(s1);
		productRepo.save(p2);
		p3.setBrand(b1);
		p3.setSupplier(s1);
		productRepo.save(p3);
		p4.setBrand(b1);
		p4.setSupplier(s1);
		productRepo.save(p4);
		p5.setBrand(b1);
		p5.setSupplier(s1);
		productRepo.save(p5);
		
		Product p6=new Product("FP-1650CP","2.3 L(138.0) L4 wheel tractor engines; 8 valve 3.375 bore carb 1 bbl. gasoline naturally aspirated G138;","Petrol/Gasoline","Engine", "Jet engine", 133.3, 50,50,500);
		Product p7=new Product("FP-2520CP10","2.4 L(149.0) L4 OHV crawler tractor engine; 8 valve 2.5000 bore carb 1 bbl. gasoline naturally aspirated G149;","Petrol/Gasoline","Engine", "Jet engine", 124.6, 50,50,500);
		Product p8=new Product("FP-AX1650P","1.9 L(116.0) L4 OHV harvest engine; 8 valve 3.2500 bore carb 1 bbl. gasoline naturally aspirated B15;","Petrol/Gasoline","Engine", "Jet engine", 138.2, 50,50,500);
		Product p9=new Product("OS 12237","3.3 L(200.0) L4 OHV crawler tractor engine; 8 valve 3.8750 bore FI MFI diesel naturally aspirated D2200;","Petrol/Gasoline","Engine", "Piston engine", 158.7, 50,50,500);
		Product p10=new Product("HS 7805 B-2","4.4 L(262.0) L6 OHV crawler tractor engine; 12 valve 3.5625 bore FI MFI diesel naturally aspirated D262;","Petrol/Gasoline","Engine", "Piston engine", 181.2, 50,50,500);
		
		p6.setBrand(b2);
		p6.setSupplier(s2);
		productRepo.save(p6);
		p7.setBrand(b2);
		p7.setSupplier(s2);
		productRepo.save(p7);
		p8.setBrand(b2);
		p8.setSupplier(s2);
		productRepo.save(p8);
		p9.setBrand(b2);
		p9.setSupplier(s2);
		productRepo.save(p9);
		p10.setBrand(b2);
		p10.setSupplier(s2);
		productRepo.save(p10);
		
		Product p11=new Product("48/L3 Group/H6-AGM","Weight: 45.6 kg; Voltage: 12 Volt; PHCA: 1140; Reserve capacity 25Amp draw: 137 min; Chemistry: thin plate, pure lead AGM.","Electrical","Battery", "Dual purpose battery", 283.99, 100,100,300);
		Product p12=new Product("FT16V830-24,","Weight: 59.3 kg; Voltage: 16 Volt; PHCA: 1140; Reserve capacity 25Amp draw: 120 min; Chemistry: sealed lead acid AGM.","Electrical","Battery", "Dual purpose battery", 347.32, 100,100,300);
		Product p13=new Product("DC55-12","Weight: 38.3 kg; Voltage: 12 Volt; Reserve capacity 25Amp draw: 96 min; Chemistry: sealed lead acid AGM.","Electrical","Battery", "Fullriver battery", 266.49, 100,100,300);
		Product p14=new Product("DC35-12","Weight: 25.4 kg; Voltage: 12 Volt; Reserve capacity 25Amp draw: 52 min; Chemistry: sealed lead acid AGM.","Electrical","Battery", "Fullriver battery", 210.49, 100,100,300);
		Product p15=new Product("48-723CCA","Weight: 48 kg; Voltage: 12 Volt; PHCA: 1250; Reserve capacity 25Amp draw: 130 min; Chemistry: thin plate, pure lead AGM.","Electrical","Battery", "Starting battery", 259.99, 100,100,300);
		
		p11.setBrand(b3);
		p11.setSupplier(s3);
		productRepo.save(p11);
		p12.setBrand(b3);
		p12.setSupplier(s3);
		productRepo.save(p12);
		p13.setBrand(b3);
		p13.setSupplier(s3);
		productRepo.save(p13);
		p14.setBrand(b3);
		p14.setSupplier(s3);
		productRepo.save(p14);
		p15.setBrand(b3);
		p15.setSupplier(s3);
		productRepo.save(p15);
		
		Product p16=new Product("A4TR5591ZT","Voltage: 24 V; Amp.: 110 A; G: 8 qty.; L.1: 212 mm.","Petrol/Gasoline","Alternator", "Automotive alternator", 80.4, 100,50,300);
		Product p17=new Product("UD19236A","Voltage: 24 V; Amp.: 110 A; G: 6 qty.; L.1: 218 mm.","Petrol/Gasoline","Alternator", "Automotive alternator", 76.9, 100,50,300);
		Product p18=new Product("CAL35619RS","Voltage: 24 V; Amp.: 110 A; G: 10 qty.; L.1: 213 mm.","Petrol/Gasoline","Alternator", "Automotive alternator", 100.6, 100,50,300);
		
		p16.setBrand(b4);
		p16.setSupplier(s4);
		productRepo.save(p16);
		p17.setBrand(b4);
		p17.setSupplier(s4);
		productRepo.save(p17);
		p18.setBrand(b4);
		p18.setSupplier(s4);
		productRepo.save(p18);
		
		Product p19=new Product("63528BZD","Core size: 610 mm*368 mm* 26 mm; Color: silver; Working pressure: 1.0 Mpa.","Petrol/Gasoline","Radiator", "Aluminum radiator", 58.3, 100,200,200);
		Product p20=new Product("AP-34888","Core size: 635 mm*320 mm* 30 mm; Color: black; Working pressure: 1.0 Mpa.","Petrol/Gasoline","Radiator", "Aluminum radiator", 47.8, 100,200,200);
		Product p21=new Product("HB45999I","Core size: 590 mm*432 mm* 40 mm; Color: white; Working pressure: 1.0 Mpa.","Petrol/Gasoline","Radiator", "Steel radiator", 79.2, 100,200,200);
				
		p19.setBrand(b5);
		p19.setSupplier(s5);
		productRepo.save(p19);
		p20.setBrand(b5);
		p20.setSupplier(s5);
		productRepo.save(p20);
		p21.setBrand(b5);
		p21.setSupplier(s5);
		productRepo.save(p21);
		
		User u1=new User("Oliver Smith","Oliver","password","Oliver@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u2=new User("Jack Brown","Jack","password","Jack@gmail.com",RoleType.ADMIN,ActiveType.ACTIVE,"active","admin");
		User u3=new User("Harry Jones","Harry","password","Harry@gmail.com",RoleType.MECHANIC, ActiveType.ACTIVE,"active","mechanic");
		User u4=new User("David Talyor","David","password","David@gmail.com",RoleType.ADMIN,ActiveType.ACTIVE,"active","admin");
		User u5=new User("Daniel Wilson","Daniel","password","Daniel@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u6=new User("Mary Li","Mary","password","Mary@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u7=new User("Sophia Lam","Sophia","password","Sophia@gmail.com",RoleType.ADMIN,ActiveType.ACTIVE,"active","admin");
		User u8=new User("Jennifer Martin","Jennifer","password","Jennifer@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u9=new User("Linda Roy","Linda","password","Linda@gmail.com",RoleType.MECHANIC,ActiveType.INACTIVE,"inactive","mechanic");
		User u10=new User("Susan Lee","Susan","password","Susan@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u11=new User("Kyle Wang","Kyle","password","Kyle@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u12=new User("William White","William","password","William@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u13=new User("Joe Anderson","Joe","password","Joe@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		User u14=new User("Thomas Davis","Thomas","password","Thomas@gmail.com",RoleType.ADMIN,ActiveType.INACTIVE,"inactive","admin");
		User u15=new User("Raymone James","Raymone","password","Raymone@gmail.com",RoleType.ADMIN,ActiveType.ACTIVE,"active","admin");
		User u16=new User("Lily Murphy","Lily","password","Lily@gmail.com",RoleType.MECHANIC,ActiveType.ACTIVE,"active","mechanic");
		userRepo.save(u1);
		userRepo.save(u2);
		userRepo.save(u3);
		userRepo.save(u4);
		userRepo.save(u5);
		userRepo.save(u6);
		userRepo.save(u7);
		userRepo.save(u8);
		userRepo.save(u9);
		userRepo.save(u10);
		userRepo.save(u11);
		userRepo.save(u12);
		userRepo.save(u13);
		userRepo.save(u14);
		userRepo.save(u15);
		userRepo.save(u16);
		

		Transaction t1=new Transaction("SJA1234H");
		Transaction t2=new Transaction("SMW7777X");
		Transaction t3=new Transaction("SMW6666P");
		Transaction t4=new Transaction("UXA2234I");
		Transaction t5=new Transaction("SOI3279H");
		Transaction t6=new Transaction("TJP1234I");
		Transaction t7=new Transaction("OPA1032E");
		Transaction t8=new Transaction("JHS2224R");
		Transaction t9=new Transaction("KIB1436T");
		Transaction t10=new Transaction("SLB4237Y");
		Transaction t11=new Transaction("SSD3635N");
		
		t1.setUser(u1);
		transactionRepo.save(t1);
		t2.setUser(u2);
		transactionRepo.save(t2);
		t3.setUser(u3);
		transactionRepo.save(t3);
		t4.setUser(u4);
		transactionRepo.save(t4);
		t5.setUser(u5);
		transactionRepo.save(t5);
		t6.setUser(u6);
		transactionRepo.save(t6);
		t7.setUser(u7);
		transactionRepo.save(t7);
		t8.setUser(u8);
		transactionRepo.save(t8);
		t9.setUser(u9);
		transactionRepo.save(t9);
		t10.setUser(u10);
		transactionRepo.save(t10);
		t11.setUser(u11);
		transactionRepo.save(t11);
		
		TransactionDetail td1 = new TransactionDetail(200,new Date(110,1,2), TransactionType.ORDER);
		TransactionDetail td2 = new TransactionDetail(40,new Date(110,2,2), TransactionType.RETURN);
		TransactionDetail td3 = new TransactionDetail(10,new Date(110,2,12), TransactionType.DAMAGED);
		td1.setProduct(p1);
		td2.setProduct(p1);
		td3.setProduct(p1);
		td1.setTransaction(t1);
		td2.setTransaction(t1);
		td3.setTransaction(t1);
		transactionDetailRepo.save(td1);
		transactionDetailRepo.save(td2);
		transactionDetailRepo.save(td3);

		TransactionDetail td4 = new TransactionDetail(300,new Date(112,1,2), TransactionType.ORDER);
		TransactionDetail td5 = new TransactionDetail(100,new Date(113,10,4), TransactionType.USAGE);
		TransactionDetail td6 = new TransactionDetail(100,new Date(113,11,23), TransactionType.USAGE);
		td4.setProduct(p2);
		td5.setProduct(p2);
		td6.setProduct(p2);
		td4.setTransaction(t2);
		td5.setTransaction(t2);
		td6.setTransaction(t2);
		transactionDetailRepo.save(td4);
		transactionDetailRepo.save(td5);
		transactionDetailRepo.save(td6);
		
		TransactionDetail td7 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td7.setProduct(p3);
		td7.setTransaction(t3);
		transactionDetailRepo.save(td7);
		
		TransactionDetail td8 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td8.setProduct(p4);
		td8.setTransaction(t3);
		transactionDetailRepo.save(td8);
		
		TransactionDetail td9 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td9.setProduct(p5);
		td9.setTransaction(t3);
		transactionDetailRepo.save(td9);
		
		TransactionDetail td10 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td10.setProduct(p6);
		td10.setTransaction(t3);
		transactionDetailRepo.save(td10);
		
		TransactionDetail td11 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td11.setProduct(p7);
		td11.setTransaction(t3);
		transactionDetailRepo.save(td11);
		
		TransactionDetail td12 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td12.setProduct(p8);
		td12.setTransaction(t3);
		transactionDetailRepo.save(td12);
		
		TransactionDetail td13 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td13.setProduct(p8);
		td13.setTransaction(t3);
		transactionDetailRepo.save(td13);
		
		TransactionDetail td14 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td14.setProduct(p9);
		td14.setTransaction(t3);
		transactionDetailRepo.save(td14);
		
		TransactionDetail td15 = new TransactionDetail(500,new Date(111,3,2), TransactionType.ORDER);
		td15.setProduct(p10);
		td15.setTransaction(t3);
		transactionDetailRepo.save(td15);
		
		TransactionDetail td16 = new TransactionDetail(300,new Date(114,10,12), TransactionType.ORDER);
		td16.setProduct(p11);
		td16.setTransaction(t4);
		transactionDetailRepo.save(td16);
		
		TransactionDetail td17 = new TransactionDetail(300,new Date(114,10,12), TransactionType.ORDER);
		td17.setProduct(p12);
		td17.setTransaction(t4);
		transactionDetailRepo.save(td17);
		
		TransactionDetail td18 = new TransactionDetail(300,new Date(114,10,12), TransactionType.ORDER);
		td18.setProduct(p13);
		td18.setTransaction(t4);
		transactionDetailRepo.save(td18);
		
		TransactionDetail td19 = new TransactionDetail(300,new Date(114,10,12), TransactionType.ORDER);
		td19.setProduct(p14);
		td19.setTransaction(t4);
		transactionDetailRepo.save(td19);
		
		TransactionDetail td20 = new TransactionDetail(300,new Date(114,10,12), TransactionType.ORDER);
		td20.setProduct(p15);
		td20.setTransaction(t4);
		transactionDetailRepo.save(td20);
		
		TransactionDetail td21 = new TransactionDetail(300,new Date(115,0,24), TransactionType.ORDER);
		td21.setProduct(p16);
		td21.setTransaction(t5);
		transactionDetailRepo.save(td21);
		
		TransactionDetail td22 = new TransactionDetail(300,new Date(115,0,24), TransactionType.ORDER);
		td22.setProduct(p17);
		td22.setTransaction(t5);
		transactionDetailRepo.save(td22);
		
		TransactionDetail td23 = new TransactionDetail(300,new Date(117,3,3), TransactionType.ORDER);
		td23.setProduct(p18);
		td23.setTransaction(t6);
		transactionDetailRepo.save(td23);
		
		TransactionDetail td24 = new TransactionDetail(300,new Date(118,1,27), TransactionType.ORDER);
		td24.setProduct(p19);
		td24.setTransaction(t7);
		transactionDetailRepo.save(td24);
		
		TransactionDetail td25 = new TransactionDetail(300,new Date(118,1,27), TransactionType.ORDER);
		td25.setProduct(p20);
		td25.setTransaction(t7);
		transactionDetailRepo.save(td25);
		
		TransactionDetail td26 = new TransactionDetail(300,new Date(119,5,6), TransactionType.ORDER);
		td26.setProduct(p21);
		td26.setTransaction(t8);
		transactionDetailRepo.save(td26);
		
		TransactionDetail td27 = new TransactionDetail(100,new Date(119,10,27), TransactionType.RETURN);
		td27.setProduct(p21);
		td27.setTransaction(t9);
		transactionDetailRepo.save(td27);
		
		TransactionDetail td28 = new TransactionDetail(100,new Date(120,4,9), TransactionType.DAMAGED);
		td28.setProduct(p20);
		td28.setTransaction(t10);
		transactionDetailRepo.save(td28);
		
		TransactionDetail td29 = new TransactionDetail(100,new Date(120,11,2), TransactionType.USAGE);
		td29.setProduct(p19);
		td29.setTransaction(t11);
		transactionDetailRepo.save(td29);
	}
}