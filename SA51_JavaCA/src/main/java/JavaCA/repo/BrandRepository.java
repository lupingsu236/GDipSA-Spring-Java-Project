package JavaCA.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import JavaCA.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> 
{
	public Brand findBrandByName(String name);
}
