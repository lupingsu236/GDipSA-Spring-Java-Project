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
		User user = new User("Liau Han Yang, Jonathan", "liauhyj", "password", "liau@gmail.com",RoleType.MECHANIC, ActiveType.ACTIVE);
		userRepo.save(user);
		// Create the transaction and set the user before persisting (assuming this is a new transaction)
		Transaction t1 = new Transaction("SJA1234H");
		//t1.setUser(user);
		transactionRepo.save(t1);
		// Create the transaction detail and set product and transaction before persisting
		TransactionDetail td1 = new TransactionDetail(1, TransactionType.USAGE);
		td1.setProduct(product);
		td1.setTransaction(t1);
		transactionDetailRepo.save(td1);
//		 update the quantity of the product in the db
		product.setQuantity(product.getQuantity() - td1.getQuantityChange());
		productRepo.save(product);
		
		User lup = new User("Su Luping", "sulp", "password","sulp@gmail.com", RoleType.ADMIN,ActiveType.ACTIVE);
		userRepo.save(lup);
	}
	/**
	@SuppressWarnings("deprecation")
	@Test
	void dataseeding() {
		Brand b1=new Brand("Honda");
		Brand b2=new Brand("Toyota");
		Brand b3=new Brand("Aisin");
		Brand b4=new Brand("Volkswagen");
		Brand b5=new Brand("Jeep");
		brandRepo.save(b1);
		brandRepo.save(b2);
		brandRepo.save(b3);
		brandRepo.save(b4);
		brandRepo.save(b5);
		
		Supplier s1=new Supplier("Bosch");
		Supplier s2=new Supplier("Denso");
		Supplier s3=new Supplier("Magna");
		Supplier s4=new Supplier("Continental");
		Supplier s5=new Supplier("Valeo");
//		Supplier s6=new Supplier("Faurecia");
		suppRepo.save(s1);
		suppRepo.save(s2);
		suppRepo.save(s3);
		suppRepo.save(s4);
		suppRepo.save(s5);
		
		Product p1=new Product("HR-V LX","fresh, bold style, 60/40 split 2nd-row magic seat, all-wheel drive, multi-angle rearview camera","Petrol/Gasoline","Light", "Dash light", 20920, 200,200,1000);
		Product p2=new Product("Clarity Plug-in Hybrid","streamlined Design, 60/40 split fold-down rear seatback, laser-welded roof, dual-zone automatic climate control","Hybrid","Light", "Head light", 33400, 200,200,500);
		Product p3=new Product("Civic Type R","carbon fiber wing spoiler, limited edition BBS wheels, turbocharged engine, triple outlet exhaust","Petrol/Gasoline","Light", "Dash light", 37255, 200,200,1000);
		Product p4=new Product("Pilot Touring","aerodynamic styling, die-cast running boards, seating for up to eight, one-touch power moonroof","Petrol/Gasoline","Light", "Dash light", 42920, 200,200,1000);
		Product p5=new Product("Passport Sport","gloss-black grille accents, 280-hp V6 engine, 9-speed transmission, wireless phone charger, wifi hotspot capability","Petrol/Gasoline","Light", "Dash light", 32590, 200,200,1000);
		
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
		
		Product p6=new Product("Tacoma SR","3.5-liter V6 direct-injection engine, 6800-lb towing capacity, trailer-sway control, TRD pro skid plate","Petrol/Gasoline","Light", "foglight", 26150, 200,200,1000);
		Product p7=new Product("Miria XLE","coupe-like design, color head-up display, bird's eye view camera, three hydrogen tanks","Hydrogen","Light", "LED taillight", 39520, 200,200,1000);
		Product p8=new Product("Prius Prime","Toyata safety sense 2.0, smart charging cable lock, rear cross-traffic alert","Hybrid","Light", "Dash light", 22070, 200,200,1000);
		Product p9=new Product("Toyata 86","2.0L 4-Cylinder 6-Speed Manual, sporty styling, boxer-four engine, paddle shifters, folding rear seat","Petrol/Gasoline","Light", "LED headlight", 27060, 140,200,1000);
		Product p10=new Product("Sequoia SR5","nightshade special edition, heated/ventilated front seats, tow up to 7400 lbs, all-aluminum 381-hp 5.7L V8","Petrol/Gasoline","Light", "Running light", 49200, 200,200,1000);
		
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
		
		Product p11=new Product("Fusion hybrid SE","2.0L i-VCT atkinson-cycle hybrid engine and electric motor, eCVT automatic transmission, electric power-assisted steering","Hybrid","Light", "Dash light", 28000, 200,200,1000);
		Product p12=new Product("Mustang Mach-E","hands-free, foot-activated liftgate, 360-degree camera with split view and front washer, intelligent adaptive cruise control with stop-and-go and speed sign recognition","Electric","Light", "Dash light", 42895, 200,200,1000);
		Product p13=new Product("EcoSport S","1,400 lbs. with available 1.0L EcoBoost 2,000 lbs. with 2.0L Ti-VCT, standard 6-speed selectshift automatic transmissionc, 1.0L EcoBoost engine with auto start-stop technology","Petrol/Gasoline","Light", "Dash light", 19995, 200,200,1000);
		Product p14=new Product("Escape SE","available AWD disconnect, electric parking brake, black molded-in-color lower bodyside cladding","Hybrid","Light", "Dash light", 27105, 200,200,1000);
		Product p15=new Product("Expedition XLT","ten-speed selectshift automatic transmission, PowerFold and PowerRecline 60/40 3rd-row seat, remote keyless entry system","Petrol/Gasoline","Light", "Dash light", 52810, 200,200,1000);
		
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
		
		Product p16=new Product("Tiguan R-Line 1.4 TSI","panoramic slide/tilt sunroof, R-Line bodykit, app-connect, adaptive cruise control","Petrol/Gasoline","Light", "Dash light", 180400, 200,200,1000);
		Product p17=new Product("Passat Elegance 2.0 TSI","ergocomfort seat, nappa leather upholstery & trim, discover pro navigation system","Petrol/Gasoline","Light", "Dash light", 187400, 200,200,1000);
		Product p18=new Product("Golf GTI 2.0 TSI","turbocharged TSI engine, sports suspension, dynamic turn signal, blind spot sensor with rear traffic alert","Petrol/Gasoline","Light", "Dash light", 200400, 200,200,1000);
		
		p16.setBrand(b4);
		p16.setSupplier(s4);
		productRepo.save(p16);
		p17.setBrand(b4);
		p17.setSupplier(s4);
		productRepo.save(p17);
		p18.setBrand(b4);
		p18.setSupplier(s4);
		productRepo.save(p18);
		
		Product p19=new Product("Renegade","1.4L turbo engine, six-speed dual dry clutch automatic transmission, blind spots monitoring & cross path detection","Petrol/Gasoline","Light", "Dash light", 22998, 200,200,1000);
		Product p20=new Product("Grand Cherokee","3.6L pentastar V6 engine and eight-speed automatic transmission, natura plus leather-trimmed heated seats, uconnect 8.4 system with AM/FM Radio with voice command with bluetooth","Petrol/Gasoline","Light", "Dash light", 26717, 200,200,1000);
		Product p21=new Product("Wrangler","2.0L direct-injection turbo engine, 8-speed automatic transmission, horsepower:268BHP@5250rpm","Petrol/Gasoline","Light", "Dash light", 148888, 200,200,1000);
		
		p19.setBrand(b5);
		p19.setSupplier(s5);
		productRepo.save(p19);
		p20.setBrand(b5);
		p20.setSupplier(s5);
		productRepo.save(p20);
		p21.setBrand(b5);
		p21.setSupplier(s5);
		productRepo.save(p21);
		
		User u1=new User("Oliver Smith","Oliver","password","Oliver@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u2=new User("Jack Brown","Jack","password","Jack@gmail.com",RoleType.ADMIN,"admin",ActiveType.ACTIVE,"active");
		User u3=new User("Harry Jones","Harry","password","Harry@gmail.com",RoleType.MECHANIC,"mechanic", ActiveType.ACTIVE,"active");
		User u4=new User("David Talyor","David","password","David@gmail.com",RoleType.ADMIN,"admin",ActiveType.ACTIVE,"active");
		User u5=new User("Daniel Wilson","Daniel","password","Daniel@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u6=new User("Mary Li","Mary","password","Mary@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u7=new User("Sophia Lam","Sophia","password","Sophia@gmail.com",RoleType.ADMIN,"admin",ActiveType.ACTIVE,"active");
		User u8=new User("Jennifer Martin","Jennifer","password","Jennifer@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u9=new User("Linda Roy","Linda","password","Linda@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.INACTIVE,"inactive");
		User u10=new User("Susan Lee","Susan","password","Susan@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u11=new User("Kyle Wang","Kyle","password","Kyle@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u12=new User("William White","William","password","William@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u13=new User("Joe Anderson","Joe","password","Joe@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		User u14=new User("Thomas Davis","Thomas","password","Thomas@gmail.com",RoleType.ADMIN,"admin",ActiveType.INACTIVE,"inactive");
		User u15=new User("Raymone James","Raymone","password","Raymone@gmail.com",RoleType.ADMIN,"admin",ActiveType.ACTIVE,"active");
		User u16=new User("Lily Murphy","Lily","password","Lily@gmail.com",RoleType.MECHANIC,"mechanic",ActiveType.ACTIVE,"active");
		uRepo.save(u1);
		uRepo.save(u2);
		uRepo.save(u3);
		uRepo.save(u4);
		uRepo.save(u5);
		uRepo.save(u6);
		uRepo.save(u7);
		uRepo.save(u8);
		uRepo.save(u9);
		uRepo.save(u10);
		uRepo.save(u11);
		uRepo.save(u12);
		uRepo.save(u13);
		uRepo.save(u14);
		uRepo.save(u15);
		uRepo.save(u16);
		

		Transaction t1=new Transaction("SJA1234H",new Date(110, 1, 1));
		Transaction t2=new Transaction("SMW7777X",new Date(110,11,23));
		Transaction t3=new Transaction("SMW6666P",new Date(113,6,5));
		Transaction t4=new Transaction("UXA2234I",new Date(114,3,4));
		Transaction t5=new Transaction("SOI3279H",new Date(116,7,22));
		Transaction t6=new Transaction("TJP1234I",new Date(118,10,3));
		Transaction t7=new Transaction("OPA1032E",new Date(119,11,25));
		Transaction t8=new Transaction("JHS2224R",new Date(120,1,2));
		Transaction t9=new Transaction("KIB1436T",new Date(110,9,3));
		Transaction t10=new Transaction("SLB4237Y",new Date(110,9,11));
		Transaction t11=new Transaction("SSD3635N",new Date(110,10,23));
		
		t1.setUser(u1);
		tRepo.save(t1);
		t2.setUser(u2);
		tRepo.save(t2);
		t3.setUser(u3);
		tRepo.save(t3);
		t4.setUser(u4);
		tRepo.save(t4);
		t5.setUser(u5);
		tRepo.save(t5);
		t6.setUser(u6);
		tRepo.save(t6);
		t7.setUser(u7);
		tRepo.save(t7);
		t8.setUser(u8);
		tRepo.save(t8);
		t9.setUser(u9);
		tRepo.save(t9);
		t10.setUser(u10);
		tRepo.save(t10);
		t11.setUser(u11);
		tRepo.save(t11);
		
		TransactionDetail td1 = new TransactionDetail(-10, TransactionType.USAGE);
		td1.setProduct(p1);
		td1.setTransaction(t1);
		tdRepo.save(td1);
		p1.setQuantity(p1.getQuantity()+td1.getQuantityChange());
		productRepo.save(p1);
		TransactionDetail td2 = new TransactionDetail(-10, TransactionType.USAGE);
		td2.setProduct(p2);
		td2.setTransaction(t2);
		tdRepo.save(td2);
		p2.setQuantity(p2.getQuantity()+td2.getQuantityChange());
		productRepo.save(p2);
		TransactionDetail td3 = new TransactionDetail(-10, TransactionType.USAGE);
		td3.setProduct(p3);
		td3.setTransaction(t3);
		tdRepo.save(td3);
		p3.setQuantity(p3.getQuantity()+td3.getQuantityChange());
		productRepo.save(p3);
		TransactionDetail td4 = new TransactionDetail(-10, TransactionType.USAGE);
		td4.setProduct(p4);
		td4.setTransaction(t4);
		tdRepo.save(td4);
		p4.setQuantity(p4.getQuantity()+td4.getQuantityChange());
		productRepo.save(p4);
		TransactionDetail td5 = new TransactionDetail(-10, TransactionType.USAGE);
		td5.setProduct(p5);
		td5.setTransaction(t5);
		tdRepo.save(td5);
		p5.setQuantity(p5.getQuantity()+td5.getQuantityChange());
		productRepo.save(p5);
		TransactionDetail td6 = new TransactionDetail(-10, TransactionType.USAGE);
		td6.setProduct(p6);
		td6.setTransaction(t6);
		tdRepo.save(td6);
		p6.setQuantity(p6.getQuantity()+td6.getQuantityChange());
		productRepo.save(p6);
		TransactionDetail td7 = new TransactionDetail(-10, TransactionType.USAGE);
		td7.setProduct(p7);
		td7.setTransaction(t7);
		tdRepo.save(td7);
		p7.setQuantity(p7.getQuantity()+td7.getQuantityChange());
		productRepo.save(p7);
		TransactionDetail td8 = new TransactionDetail(-10, TransactionType.USAGE);
		td8.setProduct(p8);
		td8.setTransaction(t8);
		tdRepo.save(td8);
		p8.setQuantity(p8.getQuantity()+td8.getQuantityChange());
		productRepo.save(p8);
		
		
		TransactionDetail td8 = new TransactionDetail(200, TransactionType.ORDER);
		TransactionDetail td9 = new TransactionDetail(10, TransactionType.USAGE);
		TransactionDetail td10 = new TransactionDetail(50, TransactionType.RETURN);
		td8.setProduct(p9);
		td9.setProduct(p9);
		td9.setTransaction(t9);
		tdRepo.save(td9);
		p9.setQuantity(140);
		productRepo.save(p9);
		
		
		
		TransactionDetail td10 = new TransactionDetail(-10, TransactionType.USAGE);
		td10.setProduct(p10);
		td10.setTransaction(t10);
		tdRepo.save(td10);
		p10.setQuantity(p10.getQuantity()+td10.getQuantityChange());
		productRepo.save(p10);
		TransactionDetail td11 = new TransactionDetail(-10, TransactionType.USAGE);
		td11.setProduct(p11);
		td11.setTransaction(t10);
		tdRepo.save(td11);
		p11.setQuantity(p11.getQuantity()+td11.getQuantityChange());
		productRepo.save(p11);
		TransactionDetail td12 = new TransactionDetail(-10, TransactionType.USAGE);
		td12.setProduct(p12);
		td12.setTransaction(t10);
		tdRepo.save(td12);
		p12.setQuantity(p12.getQuantity()+td12.getQuantityChange());
		productRepo.save(p12);
		TransactionDetail td13 = new TransactionDetail(-10, TransactionType.USAGE);
		td13.setProduct(p13);
		td13.setTransaction(t11);
		tdRepo.save(td13);
		p13.setQuantity(p13.getQuantity()+td13.getQuantityChange());
		productRepo.save(p13);
		TransactionDetail td14 = new TransactionDetail(-10, TransactionType.USAGE);
		td14.setProduct(p14);
		td14.setTransaction(t11);
		tdRepo.save(td14);
		p14.setQuantity(p14.getQuantity()+td14.getQuantityChange());
		productRepo.save(p14);
	}
	**/

}