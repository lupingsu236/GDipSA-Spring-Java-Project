package JavaCA.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;
import JavaCA.repo.TransactionDetailRepository;
import JavaCA.repo.TransactionRepository;

@Service
@Transactional
public class TransactionImplementation implements TransactionInterface
{
	@Autowired
	private TransactionRepository transRepo;
	
	@Autowired
	private TransactionDetailRepository transDRepo;
	
	@Override
	public List<Transaction> listAllTransactions()
	{
		return transRepo.findAll();
	}
	
	@Override
	public void deleteTransaction(Transaction transaction)
	{
		List<TransactionDetail> transDForThisTransaction = transaction.getTransactionDetails();
		for (TransactionDetail td:transDForThisTransaction)
		{
			transDRepo.delete(td);
		}
		transRepo.delete(transaction);
	}
	
	@Override
	public Transaction findTransactionById(long id)
	{
		return transRepo.findById(id).get();
	}
}