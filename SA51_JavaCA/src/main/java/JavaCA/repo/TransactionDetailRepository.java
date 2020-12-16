package JavaCA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import JavaCA.model.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> 
{
	@Query("SELECT td FROM TransactionDetail td JOIN td.product pdt WHERE pdt.id = :productId")
	List<TransactionDetail> findTransactionDetailsByProductId(@Param("productId") long productId);
	
	@Query(value = "SELECT * FROM transaction_detail WHERE product_id = :pid",  nativeQuery = true)
	List<TransactionDetail> findAllProductTransactionsByProductId(@Param("pid") int id);
}
