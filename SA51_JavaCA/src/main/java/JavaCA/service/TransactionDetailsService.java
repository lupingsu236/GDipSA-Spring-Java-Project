package JavaCA.service;

import java.util.ArrayList;
import java.util.List;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;

public interface TransactionDetailsService {

	ArrayList<TransactionDetail> findTransactionDetailsByProductId(long productId);
	boolean saveTransactionDetail(TransactionDetail transactionDetail);
	boolean deleteTransactionDetail(TransactionDetail transactionDetail);
	List<TransactionDetail> findAllTransactionDetails();
	TransactionDetail findTransactionDetailById(long id);
	//to force update
}
