package JavaCA.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import JavaCA.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> 
{
	public Supplier findSupplierBySupplierName(String supplierName);
}
