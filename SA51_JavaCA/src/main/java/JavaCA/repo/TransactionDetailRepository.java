package JavaCA.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import JavaCA.model.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> 
{

}
