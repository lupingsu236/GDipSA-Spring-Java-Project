package JavaCA.service;

import java.util.ArrayList;
import java.util.List;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;

public interface TransactionDetailsService {

	ArrayList<TransactionDetail> findTransactionDetailsByProductId(long productId);
	boolean saveTransactionDetail(TransactionDetail transactionDetail);
	boolean deleteTransactionDetail(TransactionDetail transactionDetail);
	void deleteAllRelatedToPdt(TransactionDetail transactionDetail);
	List<TransactionDetail> findAllTransactionDetails();
	TransactionDetail findTransactionDetailById(long id);
	public static boolean isValidDateFormat(String s)
	{
		boolean output = true;
		if (s.isBlank())
			return output;
		else
		{
			String[] arr = s.split("-");
			for (int i = 0; i < arr.length; i++)
			{
				if (i == 0 && arr[i].length() != 4)
				{
					output = false;
					break;
				}
				if (i > 0 && arr[i].length() != 2)
				{
					output = false;
					break;
				}
				for (int j = 0; j < arr[i].length(); j++)
				{
					if(!Character.isDigit(arr[i].charAt(j)))
					{
						output = false;
						break;
					}
				}
				if (!output)
					break;
			}
			return output;
		}
	}
}
