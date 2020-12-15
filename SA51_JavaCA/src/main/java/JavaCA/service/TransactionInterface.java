package JavaCA.service;

import java.util.List;

import JavaCA.model.Transaction;

public interface TransactionInterface 
{
	List<Transaction> listAllTransactions();
	void deleteTransaction(Transaction transaction);
	public Transaction findTransactionById(long id);
}
