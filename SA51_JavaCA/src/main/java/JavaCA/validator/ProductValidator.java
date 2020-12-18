package JavaCA.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import JavaCA.model.Product;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product p = (Product) target; 
		if (p.getBrand().getName().length()<2 || p.getBrand().getName().length()>50) {
			errors.rejectValue("brand.name", "brand");
		}

	}

}
