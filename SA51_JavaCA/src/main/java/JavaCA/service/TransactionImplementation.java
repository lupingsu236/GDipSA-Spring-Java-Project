package JavaCA.service;

import java.util.ArrayList;
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
	public Transaction findTransactionById(long id)
	{
		return transRepo.findById(id).get();
	}
	
	@Override
	public ArrayList<TransactionDetail> findTransactionDetailsByProductId(long productId) {
		return (ArrayList<TransactionDetail>) transDRepo.findTransactionDetailsByProductId(productId);
	}
	
	
	@Override
	public void saveTransaction(Transaction transaction) {
		transRepo.save(transaction);		
	}

	@Override
	public void saveTransactionDetail(TransactionDetail transactionDetail) {
		transDRepo.save(transactionDetail);		
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
	public void deleteTransactionDetail(TransactionDetail transactionDetail) {
		transDRepo.delete(transactionDetail);
	}
	
}
