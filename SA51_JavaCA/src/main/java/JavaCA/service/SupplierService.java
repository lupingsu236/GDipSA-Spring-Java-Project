package JavaCA.service;

import java.util.ArrayList;

import JavaCA.model.Supplier;

public interface SupplierService {
	ArrayList<Supplier> findAllSuppliers();
	Supplier findSupplierByName(String supplierName);
	Supplier findSupplier(long id);
	void saveSupplier(Supplier supplier);
	void editSupplierName(long supplierId, String newSupplierName);
	void deleteSupplier(Supplier supplier);
}
