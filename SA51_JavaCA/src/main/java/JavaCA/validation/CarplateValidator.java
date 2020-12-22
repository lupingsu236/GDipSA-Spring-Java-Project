package JavaCA.validation;
//pls push
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarplateValidator implements  ConstraintValidator<CarplateContraint, String> {
	
	@Override
    public void initialize(CarplateContraint carplate) {
    }
	
	@Override
    public boolean isValid(String carplate, ConstraintValidatorContext cxt) {
		
		if (carplate == null) {return true;}
		else if (carplate.length() == 0) {return true;}
		else if (carplate.length() > 8) {return false;}
		else if (carplate.substring(0, 1).equalsIgnoreCase("S") || carplate.substring(0, 1).equalsIgnoreCase("E")) {return true;}
        return false;
    }
}
