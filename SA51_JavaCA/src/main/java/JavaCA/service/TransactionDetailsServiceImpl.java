package JavaCA.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import JavaCA.model.Product;
import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.repo.ProductRepository;
import JavaCA.repo.TransactionDetailRepository;
import JavaCA.repo.TransactionRepository;

@Service
@Transactional
public class TransactionDetailsServiceImpl implements TransactionDetailsService {
	
	@Autowired
	private TransactionRepository transRepo;
	
	@Autowired
	private TransactionDetailRepository transDRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	private EmailService eservice;
	
	@Autowired
	public void setServices(EmailServiceImpl eservice) 
	{
		this.eservice = eservice;
	}
	
	@Override
	public ArrayList<TransactionDetail> findTransactionDetailsByProductId(long productId) {
		return (ArrayList<TransactionDetail>) transDRepo.findTransactionDetailsByProductId(productId);
	}
	
	@Override
	public boolean saveTransactionDetail(TransactionDetail transactionDetail) {
		
		boolean sameProduct = true;
		
		Product currentProduct = transactionDetail.getProduct();
		int currentProductCount = currentProduct.getQuantity();
		int currentProductQtyChange = transactionDetail.getQuantityChange();
			//ORDER is the only way to add to inventory, the rest is negative
		if(transactionDetail.getTransactionType().toString() != "ORDER") {currentProductQtyChange *= - 1;}
		
		Product previousProduct = null;
		int previousProductCount = 0;
		int previousProductQtyChange = 0;

			//check if previous transactiondetail record exists and get values
		if (transDRepo.existsById(transactionDetail.getId())) {
			previousProduct = transDRepo.findById(transactionDetail.getId()).get().getProduct();
			previousProductCount = previousProduct.getQuantity();
			previousProductQtyChange = transDRepo.findById(transactionDetail.getId()).get().getQuantityChange();
			//ORDER is the only way to add to inventory, the rest is negative
			if(transDRepo.findById(transactionDetail.getId()).get().getTransactionType().toString() != "ORDER") {previousProductQtyChange *= - 1;}
			//Checking if product is the same or changed (affects which we reverse)
			if (currentProduct.getId() != previousProduct.getId()) {sameProduct = false;}
		}
		
		if (!sameProduct) {
			//if its EDITING on a DIFFERENT product, REVERSE previous product amounts 
			if ((previousProductCount - previousProductQtyChange) >= 0) {
				previousProductCount -= previousProductQtyChange;
				previousProduct.setQuantity(previousProductCount);
			}
		}	//if its EDITING the SAME product, REVERSE current product amounts
		else {currentProductCount -= previousProductQtyChange;}
		
			//update current product amounts by adding qty change
			//put all repo-saving in the same if-method 
		if ((currentProductCount + currentProductQtyChange) >= 0){
			currentProductCount +=  currentProductQtyChange;
			currentProduct.setQuantity(currentProductCount);
			if (!sameProduct) {prodRepo.save(previousProduct);}
			prodRepo.save(currentProduct);
			transDRepo.save(transactionDetail);	
			eservice.sendReorderEmailReminderForThisProduct(currentProduct);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean deleteTransactionDetail(TransactionDetail transactionDetail) {
		
		int currentCount = transactionDetail.getProduct().getQuantity();
		int qtyChange = transactionDetail.getQuantityChange();
		if(transactionDetail.getTransactionType().toString() != "ORDER") {qtyChange = qtyChange * -1;}
		
		if ((currentCount - qtyChange) >= 0){
			Product p = transactionDetail.getProduct();
			currentCount -= qtyChange;
			p.setQuantity(currentCount);
			prodRepo.save(p);
			long tid = transactionDetail.getTransaction().getId();
			transDRepo.delete(transactionDetail);
			Transaction t = transRepo.findByTransactionId(tid);
			//If transaction is not attached to a car job, it is non-essential
			//Delete non-essential transaction if it doesn't contain any transaction details
			if ((t.getCarPlateNo() == "") || (t.getCarPlateNo() == null)) {
				if (t.getTransactionDetails().isEmpty()) {
					transRepo.delete(t);
				}
			}
			eservice.sendReorderEmailReminderForThisProduct(p);
			return true;
		}
		return false;
	}

	@Override
	public List<TransactionDetail> findAllTransactionDetails() {
		// TODO Auto-generated method stub
		return transDRepo.findAll();
	}

	@Override
	public TransactionDetail findTransactionDetailById(long id) {
		//to force update
		return transDRepo.findById(id).get();
	}

	@Override
	public void deleteAllRelatedToPdt(TransactionDetail transactionDetail) {
		transDRepo.delete(transactionDetail);
	}
	
	@Override
	public List<TransactionDetail> findAllTransactionDetailsForProductBetweenDateRange(long productId, Date fromDate, Date toDate)
	{
		return transDRepo.findTransactionDetailsByProductId(productId).stream().filter(x -> x.getDate().compareTo(toDate) <= 0
																	  && x.getDate().compareTo(fromDate) >= 0)
																	  .collect(Collectors.toList());
	}
	
	@Override
	public List<TransactionDetail> findAllTransactionDetailsForProductFromDate(long productId, Date fromDate)
	{
		return transDRepo.findTransactionDetailsByProductId(productId).stream().filter(x -> x.getDate().compareTo(fromDate) >= 0)
																			   .collect(Collectors.toList());
	}
	
	@Override
	public List<TransactionDetail> findAllTransactionDetailsForProductUpToDate(long productId, Date toDate)
	{
		return transDRepo.findTransactionDetailsByProductId(productId).stream().filter(x -> x.getDate().compareTo(toDate) <= 0)
																			   .collect(Collectors.toList());
	}

	@Override
	public List<TransactionDetail> findAllTransactionDetailsBetweenDateRange(Date fromDate, Date toDate) {
		return transDRepo.findAll().stream().filter(x -> x.getDate().compareTo(toDate) <= 0 && x.getDate().compareTo(fromDate) >= 0).collect(Collectors.toList());
	}

	@Override
	public List<TransactionDetail> findAllTransactionDetailsFromDate(Date fromDate) {
		return transDRepo.findAll().stream().filter(x -> x.getDate().compareTo(fromDate) >= 0).collect(Collectors.toList());
	}

	@Override
	public List<TransactionDetail> findAllTransactionDetailsUpToDate(Date toDate) {
		return transDRepo.findAll().stream().filter(x -> x.getDate().compareTo(toDate) <= 0).collect(Collectors.toList());
	}
}
