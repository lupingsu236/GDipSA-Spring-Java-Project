package JavaCA.service;

import java.util.ArrayList;

import JavaCA.model.Supplier;

public interface SupplierService {
	ArrayList<Supplier> findAllSuppliers();
	Supplier findSupplierByName(String supplierName);
	void saveSupplier(Supplier supplier);
}
