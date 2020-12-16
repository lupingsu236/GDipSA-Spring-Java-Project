package JavaCA.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import JavaCA.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> 
{
	
}
