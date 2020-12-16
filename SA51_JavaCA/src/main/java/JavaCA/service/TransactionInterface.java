package JavaCA.service;

import java.util.ArrayList;
import java.util.List;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;

public interface TransactionInterface 
{
	List<Transaction> listAllTransactions();
	List<Transaction> listAllCarTransactions();
	Transaction findTransactionById(long id);
	void saveTransaction(Transaction transaction);
	void deleteTransaction(Transaction transaction);
}
