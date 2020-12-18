package JavaCA.service;

import java.util.ArrayList;
import java.util.List;

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
			currentCount = currentCount - qtyChange;
			p.setQuantity(currentCount);
			prodRepo.save(p);
			transDRepo.delete(transactionDetail);
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
		// TODO Auto-generated method stub
		return transDRepo.findById(id).get();
	}

}
