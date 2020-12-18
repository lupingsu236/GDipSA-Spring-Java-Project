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
public class TransactionDetailsImpl implements TransactionDetailsInterface {
	
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
		
		int currentCount = transactionDetail.getProduct().getQuantity();
		int qtyChange = transactionDetail.getQuantityChange();
		if(transactionDetail.getTransactionType().toString() != "ORDER") {qtyChange = qtyChange * - 1;}
		
		if ((currentCount + qtyChange) >= 0){
			Product p = transactionDetail.getProduct();
			currentCount = currentCount + qtyChange;
			p.setQuantity(currentCount);
			prodRepo.save(p);
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
