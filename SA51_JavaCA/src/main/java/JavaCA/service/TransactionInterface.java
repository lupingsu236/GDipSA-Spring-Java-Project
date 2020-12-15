package JavaCA.service;

import java.util.List;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;

public interface TransactionInterface 
{
	List<Transaction> listAllTransactions();
	void deleteTransaction(Transaction transaction);
	public Transaction findTransactionById(long id);
	void saveTransaction(Transaction transaction);
	void saveTransactionDetail(TransactionDetail transactionDetail);
}
