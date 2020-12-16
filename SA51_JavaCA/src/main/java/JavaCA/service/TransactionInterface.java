package JavaCA.service;

import java.util.ArrayList;
import java.util.List;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;

public interface TransactionInterface 
{
	List<Transaction> listAllTransactions();
	Transaction findTransactionById(long id);
	ArrayList<TransactionDetail> findTransactionDetailsByProductId(long productId);
	void saveTransaction(Transaction transaction);
	void saveTransactionDetail(TransactionDetail transactionDetail);
	void deleteTransaction(Transaction transaction);
	void deleteTransactionDetail(TransactionDetail transactionDetail);
}
