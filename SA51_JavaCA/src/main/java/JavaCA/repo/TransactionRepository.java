package JavaCA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;

public interface TransactionRepository extends JpaRepository<Transaction, Long> 
{
	@Query( value = "SELECT * FROM transaction t WHERE t.car_plate_no != '' AND t.car_plate_no IS NOT null", nativeQuery = true)
	List<Transaction> findAllCarTransactions();
	
	@Query( value = "SELECT * FROM transaction t WHERE t.car_plate_no IS null", nativeQuery = true)
	List<Transaction> findAllNonCarTransactions();
}
