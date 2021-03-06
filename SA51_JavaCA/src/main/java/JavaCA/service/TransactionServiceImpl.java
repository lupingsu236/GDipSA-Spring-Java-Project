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
public class TransactionServiceImpl implements TransactionService
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
		return transRepo.findById(id).orElse(null);
	}
	
	
	@Override
	public boolean saveTransaction(Transaction transaction) {
		transRepo.save(transaction);	
		return true;
	}
	
	@Override
	public boolean deleteTransaction(Transaction transaction)
	{
		List<TransactionDetail> transDForThisTransaction = transaction.getTransactionDetails();
		for (TransactionDetail td:transDForThisTransaction)
		{
			transDRepo.delete(td);
		}
		transRepo.delete(transaction);
		return true;
	}

	@Override
	public List<Transaction> listAllCarTransactions() {
		// TODO Auto-generated method stub
		return transRepo.findAllCarTransactions();
	}

	@Override
	public List<TransactionDetail> listAllProductTransactions(int id) {
		// TODO Auto-generated method stub
		return transDRepo.findAllProductTransactionsByProductId(id);
	}
	
//	@Override
//	public boolean noTransactionDetailsInNullTransaction(Transaction transaction) {
//		//to force update
//		if ((transaction.getCarPlateNo() == "") || (transaction.getCarPlateNo() == null)) {
//			if (transaction.getTransactionDetails().isEmpty()) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public List<Transaction> listAllNonCarTransactions() {
		return transRepo.findAllNonCarTransactions();
	}

	@Override
	public void deleteAllRelatedToPdt(Transaction transaction) {
		transRepo.delete(transaction);
	}
}
