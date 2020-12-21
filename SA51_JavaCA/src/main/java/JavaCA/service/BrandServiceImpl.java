package JavaCA.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.Brand;
import JavaCA.repo.BrandRepository;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	private BrandRepository brepo; 
	
	@Autowired
	public void setBrandRepo(BrandRepository brepo) {
		this.brepo = brepo;
	}
	
	@Override
	public ArrayList<Brand> findAllBrands() {
		return (ArrayList<Brand>) brepo.findAll();
	}

	@Override
	public Brand findBrandByName(String name) {
		return brepo.findBrandByName(name);
	}

	@Override
	public Brand findBrand(long id) {
		return brepo.findById(id).get();
	}
	
	@Override
	public void saveBrand(Brand brand) {
		brepo.save(brand);		
	}

	@Override
	public void editBrandName(long brandId, String newBrandName) {
		Brand brand = findBrand(brandId);
		brand.setName(newBrandName);
		saveBrand(brand);
		
	}

	@Override
	public void deleteBrand(Brand brand) {
		brepo.delete(brand);		
	}

	
	

}
