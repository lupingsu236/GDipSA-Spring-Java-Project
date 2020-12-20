package JavaCA.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.Brand;
import JavaCA.model.Supplier;
import JavaCA.repo.SupplierRepository;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
	private SupplierRepository srepo; 
	
	@Autowired
	public void setSupplierRepo(SupplierRepository srepo) {
		this.srepo = srepo;
	}

	@Override
	public ArrayList<Supplier> findAllSuppliers() {
		return (ArrayList<Supplier>) srepo.findAll();
	}

	@Override
	public Supplier findSupplierByName(String supplierName) {
		return srepo.findSupplierBySupplierName(supplierName);
	}
	
	@Override
	public Supplier findSupplier(long id) {
		return srepo.findById(id).get();
	}

	@Override
	public void saveSupplier(Supplier supplier) {
		srepo.save(supplier);		
	}

	@Override
	public void editSupplierName(long supplierId, String newSupplierName) {
		Supplier supplier = findSupplier(supplierId);
		supplier.setSupplierName(newSupplierName);
		saveSupplier(supplier);
	}

	@Override
	public void deleteSupplier(Supplier supplier) {
		srepo.delete(supplier);
		
	}
}
