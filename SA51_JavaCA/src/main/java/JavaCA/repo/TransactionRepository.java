package JavaCA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import JavaCA.model.Transaction;
import JavaCA.model.TransactionDetail;

public interface TransactionRepository extends JpaRepository<Transaction, Long> 
{

}
