package JavaCA.service;

import java.util.ArrayList;

import JavaCA.model.Brand;

public interface BrandService {
	ArrayList<Brand> findAllBrands();
	Brand findBrandByName(String name);
	Brand findBrand(long id);
	void saveBrand(Brand brand);
	void editBrandName(long brandId, String newBrandName);
}
