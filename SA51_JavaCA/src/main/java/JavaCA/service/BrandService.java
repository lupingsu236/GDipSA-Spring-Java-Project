package JavaCA.service;

import java.util.ArrayList;

import JavaCA.model.Brand;

public interface BrandService {
	ArrayList<Brand> findAllBrands();
	Brand findBrandByName(String name);
	void saveBrand(Brand brand);
}
